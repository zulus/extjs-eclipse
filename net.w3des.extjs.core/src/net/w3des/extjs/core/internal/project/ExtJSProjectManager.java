package net.w3des.extjs.core.internal.project;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.w3des.extjs.core.ExtJSNature;
import net.w3des.extjs.core.IExtJSProjectManager;
import net.w3des.extjs.core.internal.ExtJSCore;
import net.w3des.extjs.core.model.basic.ExtJSFactory;
import net.w3des.extjs.core.model.basic.ExtJSPackage;
import net.w3des.extjs.core.model.basic.ExtJSProject;
import net.w3des.extjs.core.model.basic.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.IPackageFragmentRoot;
import org.eclipse.wst.jsdt.core.JavaScriptCore;

final public class ExtJSProjectManager implements IExtJSProjectManager, IResourceChangeListener {
    private Map<IProject, ExtJSProject> projects;

    private Map<String, File> files;

    public ExtJSProjectManager() {
    }

    @Override
    public ExtJSProject createProject(final IProject project) {
        return createProject(project, false);
    }

    @Override
    public ExtJSProject createProject(final IProject project, final boolean force) {
        if (projects.containsKey(project)) {
            return projects.get(project);
        }

        if (isExtJSProject(project)) {
            final ExtJSProject extProject = ExtJSFactory.eINSTANCE.createExtJSProject();
            extProject.setName(project.getName());

            projects.put(project, extProject);
            return projects.get(project);
        }

        if (force) {
            final ExtJSProject extProject = ExtJSFactory.eINSTANCE.createExtJSProject();
            extProject.setName(project.getName());

            projects.put(project, extProject);
            final IProgressMonitor monitor = new NullProgressMonitor();

            try {
                final IProjectDescription description = project.getDescription();
                final List<String> nature = new LinkedList<String>();
                nature.add(ExtJSNature.NATURE_ID);
                nature.addAll(Arrays.asList(description.getNatureIds()));
                description.setNatureIds(nature.toArray(new String[nature.size()]));
                project.setDescription(description, monitor);
            } catch (final CoreException e) {
                ExtJSCore.error(e);
            }

            return projects.get(project);
        }

        return null;
    }

    @Override
    public boolean isExtJSProject(final IProject project) {
        try {
            if (project.isAccessible()) {
                return project.hasNature(ExtJSNature.NATURE_ID);
            }

            return false;
        } catch (final CoreException e) {
            ExtJSCore.error(e);
            return false;
        }
    }

    @Override
    public void deleteProject(final IProject project) {
        try {
            if (project.hasNature(ExtJSNature.NATURE_ID)) {
                final IProjectDescription description = project.getDescription();
                final List<String> nature = new LinkedList<String>();
                nature.addAll(Arrays.asList(description.getNatureIds()));
                nature.remove(ExtJSNature.NATURE_ID);

                description.setNatureIds(nature.toArray(new String[nature.size()]));
                project.setDescription(description, new NullProgressMonitor());
            }
        } catch (final CoreException e) {
            ExtJSCore.error(e);
        }

        if (projects.containsKey(project)) {
            projects.remove(project);
        }
    }

    @Override
    public void resourceChanged(final IResourceChangeEvent event) {
        if (event.getResource() instanceof IProject) {
            projectChanged((IProject) event.getResource(), event);
        } else if (event.getType() == IResourceChangeEvent.POST_CHANGE && event.getDelta() != null) {
            try {
                event.getDelta().accept(new IResourceDeltaVisitor() {
                    @Override
                    public boolean visit(IResourceDelta delta) throws CoreException {
                        if (delta.getResource() instanceof IFile && delta.getKind() == IResourceDelta.REMOVED
                                && files.containsKey(delta.getResource().getFullPath().toString())) {
                            final File remove = files.remove(delta.getResource().getFullPath().toString());
                            for (final ExtJSProject pr : projects.values()) {
                                if (pr.getFiles().contains(remove)) {
                                    pr.getFiles().remove(remove);
                                }
                            }
                        }

                        return true;
                    }
                });
            } catch (final CoreException e) {
                ExtJSCore.error(e);
            }
        }

    }

    private void projectChanged(IProject project, IResourceChangeEvent event) {
        if (!isExtJSProject(project)) {
            return;
        }
        switch (event.getType()) {
            case IResourceChangeEvent.PRE_DELETE:
            case IResourceChangeEvent.PRE_CLOSE:
                projects.remove(project);
                break;
            case IResourceChangeEvent.POST_CHANGE:
                projects.get(project).setName(project.getName());
                break;
        }
    }

    @Override
    public ExtJSProject[] getProjects() {
        return projects.values().toArray(new ExtJSProject[projects.size()]);
    }

    public void activate() {
        projects = new HashMap<IProject, ExtJSProject>();
        files = new HashMap<String, File>();
        ExtJSPackage.eINSTANCE.eClass();
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
        try {
            final Resource resource = getResource();

            for (final Object o : resource.getContents()) {
                if (o instanceof ExtJSProject) {
                    final ExtJSProject extProject = (ExtJSProject) o;
                    final IProject project = ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(((ExtJSProject) o).getName());

                    if (project != null && project.isOpen() && isExtJSProject(project)) {
                        projects.put(project, extProject);
                        final Iterator<File> iterator = extProject.getFiles().iterator();
                        while (iterator.hasNext()) {
                            final File item = iterator.next();
                            if (!inProject(project, item.getName())) {
                                iterator.remove();
                                continue;
                            }

                            if (!files.containsKey(item.getName())) {
                                files.put(item.getName(), item);
                            } else {
                                iterator.remove();
                                extProject.getFiles().add(item);
                            }
                        }
                    }
                }
            }
        } catch (final Throwable e) {
            // ignore all errors
        }

        // scan
        for (final IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
            createProject(project);
        }
    }

    private Resource createResource() {
        final java.io.File file = ExtJSCore.getDefault().getStateLocation().append("index.extjsproject").toFile(); //$NON-NLS-1$

        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("extjsproject", new XMIResourceFactoryImpl()); //$NON-NLS-1$

        final ResourceSet resSet = new ResourceSetImpl();

        return resSet.createResource(URI.createFileURI(file.getAbsolutePath()));

    }

    private Resource getResource() {
        final java.io.File file = ExtJSCore.getDefault().getStateLocation().append("index.extjsproject").toFile(); //$NON-NLS-1$

        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("extjsproject", new XMIResourceFactoryImpl()); //$NON-NLS-1$

        final ResourceSet resSet = new ResourceSetImpl();

        return resSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);

    }

    @Override
    public void save() {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);

        final Resource resource = createResource();

        for (final Entry<IProject, ExtJSProject> entry : projects.entrySet()) {
            final IJavaScriptProject create = JavaScriptCore.create(entry.getKey());
            for (final File f : entry.getValue().getFiles()) {
                if (create.getPackageFragmentRoot(f.getName()) == null
                        || !create.getPackageFragmentRoot(f.getName()).exists()) {
                    entry.getValue().getFiles().remove(f);
                }
            }

            resource.getContents().add(entry.getValue());
        }

        try {
            resource.save(Collections.EMPTY_MAP);
        } catch (final IOException e) {
            ExtJSCore.error(e);
        }
    }

    @Override
    public File getFile(String filePath) {
        if (files.containsKey(filePath)) {
            return files.get(filePath);
        }
        final File file = ExtJSFactory.eINSTANCE.createFile();
        file.setName(filePath);
        files.put(filePath, file);
        // find and connect projects
        for (final Entry<IProject, ExtJSProject> entry : projects.entrySet()) {
            if (inProject(entry.getKey(), filePath)) {
                entry.getValue().getFiles().add(file);
            }

        }

        return file;
    }

    private boolean inProject(IProject project, String filePath) {
        final IJavaScriptProject pr = JavaScriptCore.create(project);
        final IPath path = new Path(filePath);
        try {
            final IPackageFragmentRoot packageFragment = pr.findPackageFragmentRoot(path);
            if (packageFragment != null) {
                return true;
            }
        } catch (final Throwable e) {
            ExtJSCore.info(e);
        }

        final IResource findMember = project.findMember(path.removeFirstSegments(1));

        if (findMember != null) {
            return true;
        }

        return false;
    }
}
