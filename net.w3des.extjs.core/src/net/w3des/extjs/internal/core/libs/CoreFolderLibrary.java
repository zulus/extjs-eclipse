package net.w3des.extjs.internal.core.libs;

import java.util.Locale;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.core.api.IExtJSIndex;
import net.w3des.extjs.core.api.ILibrarySource;

public class CoreFolderLibrary implements IExtJSCoreLibrary {

	public CoreFolderLibrary(String corePath) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IExtJSIndex getIndex() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBuiltin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setName(String name) throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getCompatibleVersionNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProjectFacetVersion[] getCompatibleVersions() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCompatibleVersion(IProjectFacetVersion version)
			throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCompatibleVersion(IProjectFacetVersion version)
			throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCompatibleVersions(IProjectFacetVersion[] versions)
			throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public ILibrarySource[] getSources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAllSources() throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeSource(ILibrarySource source) throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public ILibrarySource createSourceFile(IPath pathToFile)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILibrarySource createFolder(IPath pathToFolder, String[] exclusions,
			String[] inclusions) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILibrarySource createZip(IPath pathToZip, String[] exclusions,
			String[] inclusions) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale[] getAvailableLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILibrarySource[] getLocaleSources(Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFixVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void check() throws CoreException {
		// TODO Auto-generated method stub
		
	}

}
