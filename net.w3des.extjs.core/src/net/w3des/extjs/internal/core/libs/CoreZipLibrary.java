/*******************************************************************************
 * Copyright (c) 2013 w3des.net and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *      w3des.net - initial API and implementation
 ******************************************************************************/
package net.w3des.extjs.internal.core.libs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.core.api.IExtJSIndex;
import net.w3des.extjs.core.api.ILibrarySource;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class CoreZipLibrary implements IExtJSCoreLibrary {
    
    /** the underlying archive */
    ZipFile zip;
    
    String baseUri;
    
    /** the handler */
    ICoreLibraryHandler handler;
    
    /** the main dir name */
    String mainDirName;
    
    /** file ext-all-debug.js */
    ZipEntry extAllDebug;
    /** file ext-all.js */
    ZipEntry extAll;
    /** file ext.js */
    ZipEntry ext;
    
    /** the version properties */
    private final Properties versionProperties = new Properties();
    
    private static final String PROP_VERSION_MAJOR = "version.major"; //$NON-NLS-1$
    private static final String PROP_VERSION_MINOR = "version.minor"; //$NON-NLS-1$
    private static final String PROP_VERSION_PATCH = "version.patch"; //$NON-NLS-1$
    private static final String PROP_VERSION_REVISION = "version.revision"; //$NON-NLS-1$
    private static final String PROP_VERSION_RELEASE = "version.release"; //$NON-NLS-1$
    private static final String PROP_VERSION_BUILD = "version.build"; //$NON-NLS-1$
    private static final String PROP_VERSION_FULL = "version.full"; //$NON-NLS-1$
    private static final String PROP_VERSION_STR = "version.str"; //$NON-NLS-1$
    private static final String PROP_VERSION_NAME = "version.name"; //$NON-NLS-1$
    private static final String PROP_VERSION_GIT_HASH = "version.git.hash"; //$NON-NLS-1$
    
	private String name;

	public CoreZipLibrary(String name, String corePath) throws CoreException {
		this.name = name;
		try {
			this.baseUri = "jar:" + new File(corePath).toURI().toURL().toString() + "!";
			this.zip = new ZipFile(corePath);
		}
		catch (IOException ex) {
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, ex.getMessage(), ex));
		}
	}

	@Override
	public IExtJSIndex getIndex() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isBuiltin() {
		return true;
	}

	@Override
	public void setName(String name) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot change name of builtin library"));
	}

	@Override
	public String[] getCompatibleVersionNames() {
		return new String[]{this.handler.getLibraryVersion()};
	}

	@Override
	public IProjectFacetVersion[] getCompatibleVersions() throws CoreException {
		final IProjectFacet facet = ProjectFacetsManager.getProjectFacet(ExtJSCore.FACET_EXT);
		final List<IProjectFacetVersion> result = new ArrayList<IProjectFacetVersion>();
		for (final String version : this.getCompatibleVersionNames()) {
			final IProjectFacetVersion v = facet.getVersion(version);
			if (v == null) throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Invalid version detected"));
			result.add(v);
		}
		return result.toArray(new IProjectFacetVersion[result.size()]);
	}

	@Override
	public void addCompatibleVersion(IProjectFacetVersion version)
			throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot change builtin library"));
	}

	@Override
	public void removeCompatibleVersion(IProjectFacetVersion version)
			throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot change builtin library"));
	}

	@Override
	public void setCompatibleVersions(IProjectFacetVersion[] versions)
			throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot change builtin library"));
	}

	@Override
	public ILibrarySource[] getSources() throws CoreException {
		try {
			final List<ILibrarySource> result = new ArrayList<ILibrarySource>();
			for (final String s : this.handler.openExtAllDebugJs()) {
				result.add(new CoreFileImpl(new Path(s)));
			}
			return result.toArray(new ILibrarySource[result.size()]);
		}
		catch (IOException ex) {
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Error opening ext-all-debug.js", ex));
		}
	}

	@Override
	public void removeAllSources() throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot change builtin library"));
	}

	@Override
	public void removeSource(ILibrarySource source) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot change builtin library"));
	}

	@Override
	public ILibrarySource createSourceFile(IPath pathToFile)
			throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot change builtin library"));
	}

	@Override
	public ILibrarySource createFolder(IPath pathToFolder, String[] exclusions,
			String[] inclusions) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot change builtin library"));
	}

	@Override
	public ILibrarySource createZip(IPath pathToZip, String[] exclusions,
			String[] inclusions) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Cannot change builtin library"));
	}

	@Override
	public Locale[] getAvailableLocales() throws CoreException {
		try {
			final List<Locale> result = new ArrayList<Locale>();
			for (final String l : this.handler.getAvailableLocales()) {
				result.add(new Locale(l));
			}
			return result.toArray(new Locale[result.size()]);
		}
		catch (IOException ex) {
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Problems reading locales", ex));
		}
	}

	@Override
	public ILibrarySource[] getLocaleSources(Locale locale) throws CoreException {
		try {
			final List<ILibrarySource> result = new ArrayList<ILibrarySource>();
			for (final String s : this.handler.openLocale(locale.toString())) {
				result.add(new CoreFileImpl(new Path(s)));
			}
			return result.toArray(new ILibrarySource[result.size()]);
		}
		catch (IOException ex) {
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, "Error opening ext-all-debug.js", ex));
		}
	}

	@Override
	public int getMajorVersion() {
		return Integer.parseInt(this.versionProperties.getProperty(PROP_VERSION_MAJOR));
	}

	@Override
	public int getMinorVersion() {
		return Integer.parseInt(this.versionProperties.getProperty(PROP_VERSION_MINOR));
	}

	@Override
	public int getFixVersion() {
		return Integer.parseInt(this.versionProperties.getProperty(PROP_VERSION_PATCH));
	}

	public void check() throws CoreException {
		try {
	        // find main dir name
	        final Enumeration<? extends ZipEntry> entries = this.zip.entries();
	        ZipEntry propsFile = null;
	        while (entries.hasMoreElements())
	        {
	            final ZipEntry entry = entries.nextElement();
	            final String name = entry.getName();
	            if (!entry.isDirectory() && name.endsWith("/ext-all-debug.js") && name.split("/").length == 2) //$NON-NLS-1$ //$NON-NLS-2$
	            {
	                this.mainDirName = name.split("/")[0]; //$NON-NLS-1$
	                this.extAllDebug = this.zip.getEntry(this.mainDirName + "/ext-all-debug.js"); //$NON-NLS-1$
	                this.extAll = this.zip.getEntry(this.mainDirName + "/ext-all.js"); //$NON-NLS-1$
	                this.ext = this.zip.getEntry(this.mainDirName + "/ext.js"); //$NON-NLS-1$
	                propsFile = this.zip.getEntry(this.mainDirName + "/version.properties"); //$NON-NLS-1$
	                break;
	            }
	        }
	        
	        if (this.mainDirName == null || this.extAllDebug == null || this.extAll == null || this.ext == null)
	        {
	            throw new IOException("Invalid archive file; cannot find extjs"); //$NON-NLS-1$
	        }
	        
	        // check version
	        if (propsFile == null)
	        {
	            final String[] main = this.mainDirName.split("-"); //$NON-NLS-1$
	            if (main.length != 2)
	            {
	                throw new IOException("Invalid archive file; unable to extract version from directory name " + this.mainDirName); //$NON-NLS-1$
	            }
	            final String[] ver = main[1].split("\\."); //$NON-NLS-1$
	            if (ver.length != 3)
	            {
	                throw new IOException("Invalid archive file; unable to extract version from directory name " + this.mainDirName); //$NON-NLS-1$
	            }
	            this.versionProperties.put(PROP_VERSION_MAJOR, ver[0]);
	            this.versionProperties.put(PROP_VERSION_MINOR, ver[1]);
	            this.versionProperties.put(PROP_VERSION_PATCH, ver[2]);
	            this.versionProperties.put(PROP_VERSION_BUILD, ""); //$NON-NLS-1$
	            this.versionProperties.put(PROP_VERSION_FULL, main[1]);
	            this.versionProperties.put(PROP_VERSION_STR, main[1]);
	            this.versionProperties.put(PROP_VERSION_NAME, this.mainDirName);
	            this.versionProperties.put(PROP_VERSION_GIT_HASH, ""); //$NON-NLS-1$
	            
	            try
	            {
	                if (Integer.parseInt(ver[0]) != 4 || (Integer.parseInt(ver[1]) != 1 && Integer.parseInt(ver[1]) != 0))
	                {
	                    throw new IOException("Invalid archive file; Only version 4.0 and 4.1 are supported for old archives"); //$NON-NLS-1$
	                }
	            }
	            catch (NumberFormatException ex)
	            {
	                throw new IOException("Invalid archive file; unable to extract version from directory name " + this.mainDirName, ex); //$NON-NLS-1$
	            }
	            
	            if (Integer.parseInt(ver[1]) == 1)
	            {
	            	this.handler = new V4_1_Handler();
	            }
	            else
	            {
	            	this.handler = new V4_0_Handler();
	            }
	        }
	        else
	        {
	            this.versionProperties.load(this.zip.getInputStream(propsFile));
	            
	            try
	            {
	                if (Integer.parseInt(this.versionProperties.getProperty(PROP_VERSION_MAJOR)) == 4 && Integer.parseInt(this.versionProperties.getProperty(PROP_VERSION_MINOR)) == 2)
	                {
	                    this.handler = new V4_2_Handler();
	                }
	                else if (Integer.parseInt(this.versionProperties.getProperty(PROP_VERSION_MAJOR)) == 5 && Integer.parseInt(this.versionProperties.getProperty(PROP_VERSION_MINOR)) == 0)
	                {
	                    this.handler = new V5_0_Handler();
	                }
	                else
	                {
	                    throw new IOException("Invalid archive file; Only version 4.2 and 5.0 are supported in version.properties"); //$NON-NLS-1$
	                }
	            }
	            catch (NumberFormatException ex)
	            {
	                throw new IOException("Invalid archive file; unable to extract version from version.properties", ex); //$NON-NLS-1$
	            }
	        }
		}
		catch (IOException ex) {
			throw new CoreException(new Status(IStatus.ERROR, ExtJSCore.PLUGIN_ID, ex.getMessage(), ex));
		}
	}
    
    abstract class BaseHandler implements ICoreLibraryHandler
    {

        @Override
        public String openExtJs() throws IOException
        {
        	return baseUri + ext.getName();
        }

        @Override
        public String[] openExtAllJs() throws IOException
        {
            return new String[]{baseUri + extAll.getName()};
        }

        @Override
        public String[] openExtAllDebugJs() throws IOException
        {
            return new String[]{baseUri + extAllDebug.getName()};
        }
    }
    
    abstract class V4BaseHandler extends BaseHandler
    {

        @Override
        public String[] getAvailableLocales() throws IOException
        {
            final String folder = mainDirName + "/locale/ext-lang-"; //$NON-NLS-1$
            final Enumeration<? extends ZipEntry> entries = zip.entries();
            final List<String> result = new ArrayList<String>();
            while (entries.hasMoreElements())
            {
                final ZipEntry entry = entries.nextElement();
                final String name = entry.getName();
                if (!entry.isDirectory() && name.startsWith(folder) && name.endsWith(".js")) //$NON-NLS-1$
                {
                    final String localeName = name.substring(folder.length(), name.length() - 3);
                    if (!localeName.contains("/")) //$NON-NLS-1$
                    {
                        result.add(localeName);
                    }
                }
            }
            return result.toArray(new String[result.size()]);
        }

        @Override
        public String[] openLocale(String name) throws IOException
        {
            return new String[]{baseUri + mainDirName + "/locale/ext-lang-" + name + ".js"};
        }
    }
    
    class V4_0_Handler extends V4BaseHandler
    {
        public V4_0_Handler() {}

        @Override
        public String getLibraryVersion()
        {
            return "4.0";
        }
    }
    
    class V4_1_Handler extends V4BaseHandler
    {
        public V4_1_Handler() {}

        @Override
        public String getLibraryVersion()
        {
            return "4.1";
        }
    }
    
    class V4_2_Handler extends V4BaseHandler
    {
        public V4_2_Handler() {}

        @Override
        public String getLibraryVersion()
        {
            return "4.2";
        }
    }
    
    class V5_0_Handler extends BaseHandler
    {
        public V5_0_Handler() {}

        @Override
        public String getLibraryVersion()
        {
            return "5.0";
        }

        @Override
        public String[] getAvailableLocales() throws IOException
        {
            final String folder = mainDirName + "/packages/ext-locale/overrides/"; //$NON-NLS-1$
            final Enumeration<? extends ZipEntry> entries = zip.entries();
            final List<String> result = new ArrayList<String>();
            while (entries.hasMoreElements())
            {
                final ZipEntry entry = entries.nextElement();
                final String name = entry.getName();
                if (!entry.isDirectory() && name.startsWith(folder) && name.endsWith(".js")) //$NON-NLS-1$
                {
                    final String localeName = name.substring(folder.length(), name.length() - 3);
                    if (localeName.contains("/")) //$NON-NLS-1$
                    {
                        final String[] split = localeName.split("/"); //$NON-NLS-1$
                        if (split.length == 2 && split[1].equals("ext-locale-" + split[0] + ".js")) //$NON-NLS-1$ //$NON-NLS-2$
                        {
                            result.add(localeName);
                        }
                    }
                }
            }
            return result.toArray(new String[result.size()]);
        }

        @Override
        public String[] openLocale(String name) throws IOException
        {
            return new String[]{baseUri + mainDirName + "/packages/ext-locale/overrides/" + name + "/ext-locale-" + name + ".js"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        }
        
        private void addAllSources(List<String> result) {
        	final Enumeration<? extends ZipEntry> enumEntries = zip.entries();
        	while (enumEntries.hasMoreElements()) {
        		final ZipEntry entry = enumEntries.nextElement();
        		if (entry.getName().startsWith(mainDirName + "/src/") || entry.getName().startsWith(mainDirName + "/overrides/")) {
        			result.add(baseUri + entry.getName());
        		}
        	}
        }

        @Override
        public String[] openExtAllJs() throws IOException
        {
        	final List<String> result = new ArrayList<String>();
        	result.add(baseUri + extAll.getName());
        	this.addAllSources(result);
            return result.toArray(new String[result.size()]);
        }

        @Override
        public String[] openExtAllDebugJs() throws IOException
        {
        	final List<String> result = new ArrayList<String>();
        	result.add(baseUri + extAllDebug.getName());
        	this.addAllSources(result);
            return result.toArray(new String[result.size()]);
        }
    }

}
