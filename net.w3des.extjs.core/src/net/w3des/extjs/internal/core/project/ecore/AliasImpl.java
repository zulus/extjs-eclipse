package net.w3des.extjs.internal.core.project.ecore;

import net.w3des.extjs.core.api.IAlias;
import net.w3des.extjs.core.model.basic.Alias;

public class AliasImpl<T extends Alias> implements IAlias {
	
	private T alias;

	public AliasImpl(T alias) {
		this.alias = alias;
	}
	
	protected T getECoreObject() {
		return this.alias;
	}

	@Override
	public String getName() {
		return this.alias.getName();
	}

	@Override
	public String getRawName() {
		return this.alias.getRawName();
	}

}
