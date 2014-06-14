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
package net.w3des.extjs.internal.core.project.ecore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.w3des.extjs.core.api.IAlias;
import net.w3des.extjs.core.api.IExtJSFile;
import net.w3des.extjs.core.model.basic.Alias;
import net.w3des.extjs.core.model.basic.Feature;
import net.w3des.extjs.core.model.basic.File;
import net.w3des.extjs.core.model.basic.Layout;
import net.w3des.extjs.core.model.basic.Plugin;
import net.w3des.extjs.core.model.basic.Widget;

/**
 * Implementation of ExtJSFile; wraps the ecore model
 * 
 * @author mepeisen
 */
public class ExtJSFile implements IExtJSFile {

	private File file;

	public ExtJSFile(File file) {
		this.file = file;
	}

	@Override
	public void cleanAliases() {
		this.file.cleanAliases();
	}

	@Override
	public void addAlias(String name, int sourceStart, int sourceEnd, String type) {
		this.file.addAlias(name, sourceStart, sourceEnd, type);
	}

	@Override
	public Collection<IAlias> getAliases() {
		final List<IAlias> result = new ArrayList<IAlias>();
		for (final Alias alias : this.file.getAliases()) {
			// wrap content
			if (alias instanceof Widget) result.add(new WidgetImpl((Widget) alias));
			else if (alias instanceof Feature) result.add(new FeatureImpl((Feature) alias));
			else if (alias instanceof Layout) result.add(new LayoutImpl((Layout) alias));
			else if (alias instanceof Plugin) result.add(new PluginImpl((Plugin) alias));
			else result.add(new AliasImpl<Alias>(alias));
		}
		return null;
	}

}
