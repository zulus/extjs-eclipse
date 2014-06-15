/**
 */
package net.w3des.extjs.core.model.basic.impl;

import net.w3des.extjs.core.model.basic.Alias;
import net.w3des.extjs.core.model.basic.ExtJSPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alias</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.AliasImpl#getRawName <em>Raw Name</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.AliasImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AliasImpl extends TypeItemImpl implements Alias {
    /**
	 * The default value of the '{@link #getRawName() <em>Raw Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getRawName()
	 * @generated
	 * @ordered
	 */
    protected static final String RAW_NAME_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getRawName() <em>Raw Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getRawName()
	 * @generated
	 * @ordered
	 */
    protected String rawName = RAW_NAME_EDEFAULT;

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
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected AliasImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return ExtJSPackage.Literals.ALIAS;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getRawName() {
		return rawName;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setRawName(String newRawName) {
		String oldRawName = rawName;
		rawName = newRawName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.ALIAS__RAW_NAME, oldRawName, rawName));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.ALIAS__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExtJSPackage.ALIAS__RAW_NAME:
				return getRawName();
			case ExtJSPackage.ALIAS__NAME:
				return getName();
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
			case ExtJSPackage.ALIAS__RAW_NAME:
				setRawName((String)newValue);
				return;
			case ExtJSPackage.ALIAS__NAME:
				setName((String)newValue);
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
			case ExtJSPackage.ALIAS__RAW_NAME:
				setRawName(RAW_NAME_EDEFAULT);
				return;
			case ExtJSPackage.ALIAS__NAME:
				setName(NAME_EDEFAULT);
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
			case ExtJSPackage.ALIAS__RAW_NAME:
				return RAW_NAME_EDEFAULT == null ? rawName != null : !RAW_NAME_EDEFAULT.equals(rawName);
			case ExtJSPackage.ALIAS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (rawName: ");
		result.append(rawName);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}
    
    @Override
    public boolean equals(Object obj) {
    	if (!(obj instanceof Alias)) {
    		return false;
    	}
    	
    	return ((Alias)obj).getRawName().equals(obj);
    }

} //AliasImpl
