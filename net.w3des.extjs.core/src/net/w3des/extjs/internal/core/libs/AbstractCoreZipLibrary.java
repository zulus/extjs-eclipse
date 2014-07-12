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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.w3des.extjs.core.api.IExtJSCoreLibrary;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.core.runtime.IPath;

abstract class AbstractCoreZipLibrary implements IExtJSCoreLibrary {
	
	/**
	 * Returns the file path; extracts the file on demand
	 * @param zipFile
	 * @param entry
	 * @param zipLastModified
	 * @return
	 */
	protected String getJsFilePath(ZipFile zipFile, ZipEntry entry, long zipLastModified) throws IOException {
		final IPath path = ExtJSCore.getDefault().getStateLocation().append(".jscore").append(this.getName()).append(entry.getName());
		final File file = path.toFile();
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				throw new IOException("Unable to create dir " + file.getParent());
			}
		}
		
		final long lastModified = entry.getTime() < 0 ? zipLastModified : entry.getTime();
		
		if (file.exists() && file.lastModified() < lastModified) {
			file.delete();
		}
		if (!file.exists()) {
			// extract file
			final byte[] buf = new byte[1024*32];
			final InputStream is = zipFile.getInputStream(entry);
			final FileOutputStream fos = new FileOutputStream(file);
			try {
				int bytesRead = 0;
				while ((bytesRead = is.read(buf)) > 0) {
					fos.write(buf, 0, bytesRead);
				}
				fos.flush();
			}
			catch (IOException ex) {
				throw ex;
			}
			finally {
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
			}
		}
		return file.getAbsolutePath();
	}

}
