// Copyright 2012 Jeeeyul Lee, Seoul, Korea
// https://github.com/jeeeyul/pde-tools
//
// This module is multi-licensed and may be used under the terms
// of any of the following licenses:
//
// EPL, Eclipse Public License, V1.0 or later, http://www.eclipse.org/legal
// LGPL, GNU Lesser General Public License, V2.1 or later, http://www.gnu.org/licenses/lgpl.html
// GPL, GNU General Public License, V2 or later, http://www.gnu.org/licenses/gpl.html
// AL, Apache License, V2.0 or later, http://www.apache.org/licenses
// BSD, BSD License, http://www.opensource.org/licenses/bsd-license.php
// MIT, MIT License, http://www.opensource.org/licenses/MIT
//
// Please contact the author if you need another license.
// This module is provided "as is", without warranties of any kind.
package net.w3des.extjs.ui;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

/*
 * Generated by PDE Tools.
 */
public class SharedImages{
	/**
	 * Constants set for folder 'icons/obj16'
	 */
	public static interface OBJ16{
		/**
		 * Image constant for icons/obj16/alias.png
		 */
		public static final String ALIAS = "icons/obj16/alias.png";
		
		/**
		 * Image constant for icons/obj16/cmd.png
		 */
		public static final String CMD = "icons/obj16/cmd.png";
		
		/**
		 * Image constant for icons/obj16/delext.png
		 */
		public static final String DELEXT = "icons/obj16/delext.png";
		
		/**
		 * Image constant for icons/obj16/event.png
		 */
		public static final String EVENT = "icons/obj16/event.png";
		
		/**
		 * Image constant for icons/obj16/ext.png
		 */
		public static final String EXT = "icons/obj16/ext.png";
		
		/**
		 * Image constant for icons/obj16/feature.png
		 */
		public static final String FEATURE = "icons/obj16/feature.png";
		
		/**
		 * Image constant for icons/obj16/layout.png
		 */
		public static final String LAYOUT = "icons/obj16/layout.png";
		
		/**
		 * Image constant for icons/obj16/plugin.png
		 */
		public static final String PLUGIN = "icons/obj16/plugin.png";
		
		/**
		 * Image constant for icons/obj16/widget.png
		 */
		public static final String WIDGET = "icons/obj16/widget.png";
	}
	
	/**
	 * Constants set for folder 'icons/ovr16'
	 */
	public static interface OVR16{
		/**
		 * Image constant for icons/ovr16/ext.png
		 */
		public static final String EXT = "icons/ovr16/ext.png";
	}
	
	/**
	 * Constants set for folder 'icons/wizban'
	 */
	public static interface WIZBAN{
		/**
		 * Image constant for icons/wizban/ext_wiz.png
		 */
		public static final String EXT_WIZ = "icons/wizban/ext_wiz.png";
	}
	
	private static final ImageRegistry REGISTRY = new ImageRegistry(Display.getDefault());
	
	public static Image getImage(String key){
		Image result = REGISTRY.get(key);
		if(result == null){
			result = loadImage(key);
			REGISTRY.put(key, result);
		}
		return result;
	}
	
	public static ImageDescriptor getImageDescriptor(String key){
		ImageDescriptor result = REGISTRY.getDescriptor(key);
		if(result == null){
			result = loadImageDescriptor(key);
			REGISTRY.put(key, result);
		}
		return result;
	}
	
	private static Image loadImage(String key) {
		try {
			Bundle bundle = Platform.getBundle("net.w3des.extjs.ui");
			URL resource = null;
			
			if(bundle != null){
				resource = Platform.getBundle("net.w3des.extjs.ui").getResource(key);
			}else{
				resource = new File(key).toURI().toURL();	
			}
			
			Image image = new Image(null, resource.openStream());
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		}
	}
	
	private static ImageDescriptor loadImageDescriptor(String key) {
		try {
			Bundle bundle = Platform.getBundle("net.w3des.extjs.ui");
			URL resource = null;
			
			if(bundle != null){
				resource = Platform.getBundle("net.w3des.extjs.ui").getResource(key);
			}else{
				resource = new File(key).toURI().toURL();	
			}
			
			ImageDescriptor descriptor = ImageDescriptor.createFromURL(resource);
			return descriptor;
		} catch (Exception e) {
			e.printStackTrace();
			return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_ERROR_TSK);
		}
	}
}