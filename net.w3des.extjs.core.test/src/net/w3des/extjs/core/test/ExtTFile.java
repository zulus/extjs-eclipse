package net.w3des.extjs.core.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

public class ExtTFile {
	protected enum STATES {
		TEST("TEST"),
		FILE("FILE"),
		EXPECT("EXPECT"),
		PREFERENCES("PREFERENCES");
		
		private static class Names {
			private static Map<String, STATES> map = new HashMap<String, STATES>();
		}
		
		String name;
		
		STATES(String name) {
			this.name = name;
			Names.map.put(name.toLowerCase(), this);
		}
		
		public static STATES byName(String name) {
			return Names.map.get(name.toLowerCase());
		}
	}
	
	protected Bundle testBundle;
	protected Path path;
	protected String file = "";
	protected String description = "";
	protected String expect = "";
	protected String targetDir;
	
	public ExtTFile(Bundle testBundle, String targetDir, String path) throws Exception {
		this.testBundle = testBundle;
		this.path = new Path(path);
		this.targetDir = targetDir;
		
		InputStream stream = openResource();
		
		description = "";
		
		try {
			parse(stream);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	protected void parse(InputStream stream) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line = null;
		STATES state = null;
		Pattern regex = Pattern.compile("^--([A-Z]+)--$");
		
		while ((line = reader.readLine()) != null) {
			Matcher match = regex.matcher(line);
			if (match.find()) {
				state = STATES.byName(match.group(1));
			} else if(state != null) {
				onState(state, line);
			}
		}
	}

	protected void onState(STATES state, String line) throws Exception {
		switch(state) {
		case FILE:
			this.file += line + "\n";
			break;
		case EXPECT:
			this.expect += line + "\n";
			break;
		case TEST:
			this.description += line + " ";
			break;
		default:
			break;
		}
	}

	protected InputStream openResource() throws IOException {
		File file = new File(path.toString());
		if (file.exists()) {
			return new FileInputStream(file);
		}
		
		URL url = testBundle.getEntry(path.toString());
		
		return new BufferedInputStream(url.openStream());
	}
	
	public String getProjectName() {
		IPath p = new Path(getFilename());
		
		return targetDir + p.removeFileExtension() + ".js";
	}
	
	public String getFilename() {
		return path.removeFirstSegments(path.segmentCount()-1).toString();
	}
	
	@Override
	public String toString() {
		return targetDir + getFilename();
	}
	
	public IFile createFile(IProject project) throws CoreException {
		IFile file = project.getFile(getProjectName());
		
		if (file.exists()) {
			file.setContents(new ByteArrayInputStream(this.file.getBytes()), IResource.FORCE, null);
		} else {
			file.create(new ByteArrayInputStream(this.file.getBytes()), IResource.FORCE, null);
		}
		
		return file;
	}
	
	public String getContents() {
		return file;
	}
	
	public String getExpect() {
		return expect;
	}
}
