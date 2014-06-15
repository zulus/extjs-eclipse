/**
 */
package net.w3des.extjs.core.model.basic.impl;

import java.util.Collection;

import net.w3des.extjs.core.model.basic.ExtJSPackage;
import net.w3des.extjs.core.model.basic.Library;
import net.w3des.extjs.core.model.basic.LibrarySource;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.LibraryImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.LibraryImpl#isBuiltin <em>Builtin</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.LibraryImpl#getVersions <em>Versions</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.LibraryImpl#getSources <em>Sources</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LibraryImpl extends MinimalEObjectImpl.Container implements Library {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isBuiltin() <em>Builtin</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBuiltin()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BUILTIN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBuiltin() <em>Builtin</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBuiltin()
	 * @generated
	 * @ordered
	 */
	protected boolean builtin = BUILTIN_EDEFAULT;

	/**
	 * The cached value of the '{@link #getVersions() <em>Versions</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersions()
	 * @generated
	 * @ordered
	 */
	protected EList<String> versions;

	/**
	 * The cached value of the '{@link #getSources() <em>Sources</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSources()
	 * @generated
	 * @ordered
	 */
	protected EList<LibrarySource> sources;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LibraryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtJSPackage.Literals.LIBRARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.LIBRARY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBuiltin() {
		return builtin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuiltin(boolean newBuiltin) {
		boolean oldBuiltin = builtin;
		builtin = newBuiltin;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.LIBRARY__BUILTIN, oldBuiltin, builtin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getVersions() {
		if (versions == null) {
			versions = new EDataTypeUniqueEList<String>(String.class, this, ExtJSPackage.LIBRARY__VERSIONS);
		}
		return versions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LibrarySource> getSources() {
		if (sources == null) {
			sources = new EObjectContainmentEList<LibrarySource>(LibrarySource.class, this, ExtJSPackage.LIBRARY__SOURCES);
		}
		return sources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ExtJSPackage.LIBRARY__SOURCES:
				return ((InternalEList<?>)getSources()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExtJSPackage.LIBRARY__NAME:
				return getName();
			case ExtJSPackage.LIBRARY__BUILTIN:
				return isBuiltin();
			case ExtJSPackage.LIBRARY__VERSIONS:
				return getVersions();
			case ExtJSPackage.LIBRARY__SOURCES:
				return getSources();
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
			case ExtJSPackage.LIBRARY__NAME:
				setName((String)newValue);
				return;
			case ExtJSPackage.LIBRARY__BUILTIN:
				setBuiltin((Boolean)newValue);
				return;
			case ExtJSPackage.LIBRARY__VERSIONS:
				getVersions().clear();
				getVersions().addAll((Collection<? extends String>)newValue);
				return;
			case ExtJSPackage.LIBRARY__SOURCES:
				getSources().clear();
				getSources().addAll((Collection<? extends LibrarySource>)newValue);
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
			case ExtJSPackage.LIBRARY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ExtJSPackage.LIBRARY__BUILTIN:
				setBuiltin(BUILTIN_EDEFAULT);
				return;
			case ExtJSPackage.LIBRARY__VERSIONS:
				getVersions().clear();
				return;
			case ExtJSPackage.LIBRARY__SOURCES:
				getSources().clear();
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
			case ExtJSPackage.LIBRARY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ExtJSPackage.LIBRARY__BUILTIN:
				return builtin != BUILTIN_EDEFAULT;
			case ExtJSPackage.LIBRARY__VERSIONS:
				return versions != null && !versions.isEmpty();
			case ExtJSPackage.LIBRARY__SOURCES:
				return sources != null && !sources.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", builtin: ");
		result.append(builtin);
		result.append(", versions: ");
		result.append(versions);
		result.append(')');
		return result.toString();
	}

} //LibraryImpl
