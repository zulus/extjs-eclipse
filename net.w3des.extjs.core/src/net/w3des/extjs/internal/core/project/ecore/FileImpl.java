package net.w3des.extjs.internal.core.project.ecore;

import net.w3des.extjs.core.api.ILibrarySource;
import net.w3des.extjs.core.api.LibrarySourceType;
import net.w3des.extjs.core.model.basic.LibrarySource;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class FileImpl implements ILibrarySource {

	private LibrarySource source;

	public FileImpl(LibrarySource source) {
		this.source = source;
	}
	
	public LibrarySource getSource() {
		return this.source;
	}

	@Override
	public LibrarySourceType getSourceType() {
		return LibrarySourceType.JAVASCRIPT_FILE;
	}

	@Override
	public IPath getFullPath() {
		return new Path(this.source.getPath());
	}

	@Override
	public String[] getExclusions() {
		return this.source.getExclusions().toArray(new String[this.source.getExclusions().size()]);
	}

	@Override
	public String[] getInclusions() {
		return this.source.getInclusions().toArray(new String[this.source.getInclusions().size()]);
	}

}
