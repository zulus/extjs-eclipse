/**
 */
package net.w3des.extjs.core.model.basic.impl;

import net.w3des.extjs.core.model.basic.CoreVersionDefault;
import net.w3des.extjs.core.model.basic.ExtJSPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Core Version Default</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.CoreVersionDefaultImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.CoreVersionDefaultImpl#getFacet <em>Facet</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.CoreVersionDefaultImpl#getCoreLib <em>Core Lib</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CoreVersionDefaultImpl extends MinimalEObjectImpl.Container implements CoreVersionDefault {
	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

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
	 * The default value of the '{@link #getCoreLib() <em>Core Lib</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoreLib()
	 * @generated
	 * @ordered
	 */
	protected static final String CORE_LIB_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCoreLib() <em>Core Lib</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoreLib()
	 * @generated
	 * @ordered
	 */
	protected String coreLib = CORE_LIB_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CoreVersionDefaultImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtJSPackage.Literals.CORE_VERSION_DEFAULT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.CORE_VERSION_DEFAULT__VERSION, oldVersion, version));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.CORE_VERSION_DEFAULT__FACET, oldFacet, facet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCoreLib() {
		return coreLib;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCoreLib(String newCoreLib) {
		String oldCoreLib = coreLib;
		coreLib = newCoreLib;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.CORE_VERSION_DEFAULT__CORE_LIB, oldCoreLib, coreLib));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExtJSPackage.CORE_VERSION_DEFAULT__VERSION:
				return getVersion();
			case ExtJSPackage.CORE_VERSION_DEFAULT__FACET:
				return getFacet();
			case ExtJSPackage.CORE_VERSION_DEFAULT__CORE_LIB:
				return getCoreLib();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ExtJSPackage.CORE_VERSION_DEFAULT__VERSION:
				setVersion((String)newValue);
				return;
			case ExtJSPackage.CORE_VERSION_DEFAULT__FACET:
				setFacet((String)newValue);
				return;
			case ExtJSPackage.CORE_VERSION_DEFAULT__CORE_LIB:
				setCoreLib((String)newValue);
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
			case ExtJSPackage.CORE_VERSION_DEFAULT__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case ExtJSPackage.CORE_VERSION_DEFAULT__FACET:
				setFacet(FACET_EDEFAULT);
				return;
			case ExtJSPackage.CORE_VERSION_DEFAULT__CORE_LIB:
				setCoreLib(CORE_LIB_EDEFAULT);
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
			case ExtJSPackage.CORE_VERSION_DEFAULT__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case ExtJSPackage.CORE_VERSION_DEFAULT__FACET:
				return FACET_EDEFAULT == null ? facet != null : !FACET_EDEFAULT.equals(facet);
			case ExtJSPackage.CORE_VERSION_DEFAULT__CORE_LIB:
				return CORE_LIB_EDEFAULT == null ? coreLib != null : !CORE_LIB_EDEFAULT.equals(coreLib);
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
		result.append(" (version: ");
		result.append(version);
		result.append(", facet: ");
		result.append(facet);
		result.append(", coreLib: ");
		result.append(coreLib);
		result.append(')');
		return result.toString();
	}

} //CoreVersionDefaultImpl
