/**
 */
package net.w3des.extjs.core.model.basic.impl;

import java.util.Collection;

import net.w3des.extjs.core.model.basic.ExtJSPackage;
import net.w3des.extjs.core.model.basic.LibrarySource;
import net.w3des.extjs.core.model.basic.LibrarySourceType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Library Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.LibrarySourceImpl#getType <em>Type</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.LibrarySourceImpl#getPath <em>Path</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.LibrarySourceImpl#getInclusions <em>Inclusions</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.LibrarySourceImpl#getExclusions <em>Exclusions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LibrarySourceImpl extends MinimalEObjectImpl.Container implements LibrarySource {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final LibrarySourceType TYPE_EDEFAULT = LibrarySourceType.ZIP_FILE;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected LibrarySourceType type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInclusions() <em>Inclusions</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInclusions()
	 * @generated
	 * @ordered
	 */
	protected EList<String> inclusions;

	/**
	 * The cached value of the '{@link #getExclusions() <em>Exclusions</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExclusions()
	 * @generated
	 * @ordered
	 */
	protected EList<String> exclusions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LibrarySourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtJSPackage.Literals.LIBRARY_SOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibrarySourceType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(LibrarySourceType newType) {
		LibrarySourceType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.LIBRARY_SOURCE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.LIBRARY_SOURCE__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getInclusions() {
		if (inclusions == null) {
			inclusions = new EDataTypeUniqueEList<String>(String.class, this, ExtJSPackage.LIBRARY_SOURCE__INCLUSIONS);
		}
		return inclusions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getExclusions() {
		if (exclusions == null) {
			exclusions = new EDataTypeUniqueEList<String>(String.class, this, ExtJSPackage.LIBRARY_SOURCE__EXCLUSIONS);
		}
		return exclusions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExtJSPackage.LIBRARY_SOURCE__TYPE:
				return getType();
			case ExtJSPackage.LIBRARY_SOURCE__PATH:
				return getPath();
			case ExtJSPackage.LIBRARY_SOURCE__INCLUSIONS:
				return getInclusions();
			case ExtJSPackage.LIBRARY_SOURCE__EXCLUSIONS:
				return getExclusions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ExtJSPackage.LIBRARY_SOURCE__TYPE:
				setType((LibrarySourceType)newValue);
				return;
			case ExtJSPackage.LIBRARY_SOURCE__PATH:
				setPath((String)newValue);
				return;
			case ExtJSPackage.LIBRARY_SOURCE__INCLUSIONS:
				getInclusions().clear();
				getInclusions().addAll((Collection<? extends String>)newValue);
				return;
			case ExtJSPackage.LIBRARY_SOURCE__EXCLUSIONS:
				getExclusions().clear();
				getExclusions().addAll((Collection<? extends String>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ExtJSPackage.LIBRARY_SOURCE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case ExtJSPackage.LIBRARY_SOURCE__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case ExtJSPackage.LIBRARY_SOURCE__INCLUSIONS:
				getInclusions().clear();
				return;
			case ExtJSPackage.LIBRARY_SOURCE__EXCLUSIONS:
				getExclusions().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ExtJSPackage.LIBRARY_SOURCE__TYPE:
				return type != TYPE_EDEFAULT;
			case ExtJSPackage.LIBRARY_SOURCE__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case ExtJSPackage.LIBRARY_SOURCE__INCLUSIONS:
				return inclusions != null && !inclusions.isEmpty();
			case ExtJSPackage.LIBRARY_SOURCE__EXCLUSIONS:
				return exclusions != null && !exclusions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (type: ");
		result.append(type);
		result.append(", path: ");
		result.append(path);
		result.append(", inclusions: ");
		result.append(inclusions);
		result.append(", exclusions: ");
		result.append(exclusions);
		result.append(')');
		return result.toString();
	}

} //LibrarySourceImpl
