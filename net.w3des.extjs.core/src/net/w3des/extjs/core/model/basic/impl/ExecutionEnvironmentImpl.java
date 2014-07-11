/**
 */
package net.w3des.extjs.core.model.basic.impl;

import java.util.Collection;

import net.w3des.extjs.core.model.basic.ExecutionEnvironment;
import net.w3des.extjs.core.model.basic.ExtJSPackage;
import net.w3des.extjs.core.model.basic.LibrarySourceType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Execution Environment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.ExecutionEnvironmentImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.ExecutionEnvironmentImpl#isBuiltin <em>Builtin</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.ExecutionEnvironmentImpl#getVersions <em>Versions</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.ExecutionEnvironmentImpl#getCorePath <em>Core Path</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.ExecutionEnvironmentImpl#getCoreType <em>Core Type</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.ExecutionEnvironmentImpl#getLibraries <em>Libraries</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.ExecutionEnvironmentImpl#getFacet <em>Facet</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExecutionEnvironmentImpl extends MinimalEObjectImpl.Container implements ExecutionEnvironment {
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
	 * The default value of the '{@link #getCorePath() <em>Core Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorePath()
	 * @generated
	 * @ordered
	 */
	protected static final String CORE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCorePath() <em>Core Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorePath()
	 * @generated
	 * @ordered
	 */
	protected String corePath = CORE_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getCoreType() <em>Core Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoreType()
	 * @generated
	 * @ordered
	 */
	protected static final LibrarySourceType CORE_TYPE_EDEFAULT = LibrarySourceType.ZIP_FILE;

	/**
	 * The cached value of the '{@link #getCoreType() <em>Core Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoreType()
	 * @generated
	 * @ordered
	 */
	protected LibrarySourceType coreType = CORE_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLibraries() <em>Libraries</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLibraries()
	 * @generated
	 * @ordered
	 */
	protected EList<String> libraries;

	/**
	 * The default value of the '{@link #getFacet() <em>Facet</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFacet()
	 * @generated
	 * @ordered
	 */
	protected static final String FACET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFacet() <em>Facet</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFacet()
	 * @generated
	 * @ordered
	 */
	protected String facet = FACET_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExecutionEnvironmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtJSPackage.Literals.EXECUTION_ENVIRONMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.EXECUTION_ENVIRONMENT__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.EXECUTION_ENVIRONMENT__BUILTIN, oldBuiltin, builtin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getVersions() {
		if (versions == null) {
			versions = new EDataTypeUniqueEList<String>(String.class, this, ExtJSPackage.EXECUTION_ENVIRONMENT__VERSIONS);
		}
		return versions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCorePath() {
		return corePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorePath(String newCorePath) {
		String oldCorePath = corePath;
		corePath = newCorePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.EXECUTION_ENVIRONMENT__CORE_PATH, oldCorePath, corePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibrarySourceType getCoreType() {
		return coreType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCoreType(LibrarySourceType newCoreType) {
		LibrarySourceType oldCoreType = coreType;
		coreType = newCoreType == null ? CORE_TYPE_EDEFAULT : newCoreType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.EXECUTION_ENVIRONMENT__CORE_TYPE, oldCoreType, coreType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getLibraries() {
		if (libraries == null) {
			libraries = new EDataTypeUniqueEList<String>(String.class, this, ExtJSPackage.EXECUTION_ENVIRONMENT__LIBRARIES);
		}
		return libraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFacet() {
		return facet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFacet(String newFacet) {
		String oldFacet = facet;
		facet = newFacet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.EXECUTION_ENVIRONMENT__FACET, oldFacet, facet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExtJSPackage.EXECUTION_ENVIRONMENT__NAME:
				return getName();
			case ExtJSPackage.EXECUTION_ENVIRONMENT__BUILTIN:
				return isBuiltin();
			case ExtJSPackage.EXECUTION_ENVIRONMENT__VERSIONS:
				return getVersions();
			case ExtJSPackage.EXECUTION_ENVIRONMENT__CORE_PATH:
				return getCorePath();
			case ExtJSPackage.EXECUTION_ENVIRONMENT__CORE_TYPE:
				return getCoreType();
			case ExtJSPackage.EXECUTION_ENVIRONMENT__LIBRARIES:
				return getLibraries();
			case ExtJSPackage.EXECUTION_ENVIRONMENT__FACET:
				return getFacet();
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
			case ExtJSPackage.EXECUTION_ENVIRONMENT__NAME:
				setName((String)newValue);
				return;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__BUILTIN:
				setBuiltin((Boolean)newValue);
				return;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__VERSIONS:
				getVersions().clear();
				getVersions().addAll((Collection<? extends String>)newValue);
				return;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__CORE_PATH:
				setCorePath((String)newValue);
				return;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__CORE_TYPE:
				setCoreType((LibrarySourceType)newValue);
				return;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__LIBRARIES:
				getLibraries().clear();
				getLibraries().addAll((Collection<? extends String>)newValue);
				return;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__FACET:
				setFacet((String)newValue);
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
			case ExtJSPackage.EXECUTION_ENVIRONMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__BUILTIN:
				setBuiltin(BUILTIN_EDEFAULT);
				return;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__VERSIONS:
				getVersions().clear();
				return;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__CORE_PATH:
				setCorePath(CORE_PATH_EDEFAULT);
				return;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__CORE_TYPE:
				setCoreType(CORE_TYPE_EDEFAULT);
				return;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__LIBRARIES:
				getLibraries().clear();
				return;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__FACET:
				setFacet(FACET_EDEFAULT);
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
			case ExtJSPackage.EXECUTION_ENVIRONMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ExtJSPackage.EXECUTION_ENVIRONMENT__BUILTIN:
				return builtin != BUILTIN_EDEFAULT;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__VERSIONS:
				return versions != null && !versions.isEmpty();
			case ExtJSPackage.EXECUTION_ENVIRONMENT__CORE_PATH:
				return CORE_PATH_EDEFAULT == null ? corePath != null : !CORE_PATH_EDEFAULT.equals(corePath);
			case ExtJSPackage.EXECUTION_ENVIRONMENT__CORE_TYPE:
				return coreType != CORE_TYPE_EDEFAULT;
			case ExtJSPackage.EXECUTION_ENVIRONMENT__LIBRARIES:
				return libraries != null && !libraries.isEmpty();
			case ExtJSPackage.EXECUTION_ENVIRONMENT__FACET:
				return FACET_EDEFAULT == null ? facet != null : !FACET_EDEFAULT.equals(facet);
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
		result.append(", corePath: ");
		result.append(corePath);
		result.append(", coreType: ");
		result.append(coreType);
		result.append(", libraries: ");
		result.append(libraries);
		result.append(", facet: ");
		result.append(facet);
		result.append(')');
		return result.toString();
	}

} //ExecutionEnvironmentImpl
