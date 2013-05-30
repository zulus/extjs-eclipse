package net.w3des.extjs.ui.editor.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.wst.jsdt.core.IField;
import org.eclipse.wst.jsdt.core.IType;

public class AliasFilter extends ViewerFilter {

	public AliasFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (parentElement instanceof IType && element instanceof IField) {
			IType type = (IType) parentElement;
			IField field = (IField)element;
			
			if (field.getElementName().equals("alias")) {
				return false;
			}
		}
		return true;
	}
}
