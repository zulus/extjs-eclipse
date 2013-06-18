/**
 */
package net.w3des.extjs.core.model.basic.impl;

import net.w3des.extjs.core.model.basic.ExtJSPackage;
import net.w3des.extjs.core.model.basic.TypeItem;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.TypeItemImpl#getSourceStart <em>Source Start</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.TypeItemImpl#getSourceEnd <em>Source End</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.TypeItemImpl#getTypeName <em>Type Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class TypeItemImpl extends MinimalEObjectImpl.Container implements TypeItem {
    /**
     * The default value of the '{@link #getSourceStart() <em>Source Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSourceStart()
     * @generated
     * @ordered
     */
    protected static final int SOURCE_START_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getSourceStart() <em>Source Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSourceStart()
     * @generated
     * @ordered
     */
    protected int sourceStart = SOURCE_START_EDEFAULT;

    /**
     * The default value of the '{@link #getSourceEnd() <em>Source End</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSourceEnd()
     * @generated
     * @ordered
     */
    protected static final int SOURCE_END_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getSourceEnd() <em>Source End</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSourceEnd()
     * @generated
     * @ordered
     */
    protected int sourceEnd = SOURCE_END_EDEFAULT;

    /**
     * The default value of the '{@link #getTypeName() <em>Type Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypeName()
     * @generated
     * @ordered
     */
    protected static final String TYPE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTypeName() <em>Type Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypeName()
     * @generated
     * @ordered
     */
    protected String typeName = TYPE_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TypeItemImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ExtJSPackage.Literals.TYPE_ITEM;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getSourceStart() {
        return sourceStart;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSourceStart(int newSourceStart) {
        int oldSourceStart = sourceStart;
        sourceStart = newSourceStart;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.TYPE_ITEM__SOURCE_START, oldSourceStart, sourceStart));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getSourceEnd() {
        return sourceEnd;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSourceEnd(int newSourceEnd) {
        int oldSourceEnd = sourceEnd;
        sourceEnd = newSourceEnd;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.TYPE_ITEM__SOURCE_END, oldSourceEnd, sourceEnd));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTypeName(String newTypeName) {
        String oldTypeName = typeName;
        typeName = newTypeName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.TYPE_ITEM__TYPE_NAME, oldTypeName, typeName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ExtJSPackage.TYPE_ITEM__SOURCE_START:
                return getSourceStart();
            case ExtJSPackage.TYPE_ITEM__SOURCE_END:
                return getSourceEnd();
            case ExtJSPackage.TYPE_ITEM__TYPE_NAME:
                return getTypeName();
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
            case ExtJSPackage.TYPE_ITEM__SOURCE_START:
                setSourceStart((Integer)newValue);
                return;
            case ExtJSPackage.TYPE_ITEM__SOURCE_END:
                setSourceEnd((Integer)newValue);
                return;
            case ExtJSPackage.TYPE_ITEM__TYPE_NAME:
                setTypeName((String)newValue);
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
            case ExtJSPackage.TYPE_ITEM__SOURCE_START:
                setSourceStart(SOURCE_START_EDEFAULT);
                return;
            case ExtJSPackage.TYPE_ITEM__SOURCE_END:
                setSourceEnd(SOURCE_END_EDEFAULT);
                return;
            case ExtJSPackage.TYPE_ITEM__TYPE_NAME:
                setTypeName(TYPE_NAME_EDEFAULT);
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
            case ExtJSPackage.TYPE_ITEM__SOURCE_START:
                return sourceStart != SOURCE_START_EDEFAULT;
            case ExtJSPackage.TYPE_ITEM__SOURCE_END:
                return sourceEnd != SOURCE_END_EDEFAULT;
            case ExtJSPackage.TYPE_ITEM__TYPE_NAME:
                return TYPE_NAME_EDEFAULT == null ? typeName != null : !TYPE_NAME_EDEFAULT.equals(typeName);
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
        result.append(" (sourceStart: ");
        result.append(sourceStart);
        result.append(", sourceEnd: ");
        result.append(sourceEnd);
        result.append(", typeName: ");
        result.append(typeName);
        result.append(')');
        return result.toString();
    }

} //TypeItemImpl
