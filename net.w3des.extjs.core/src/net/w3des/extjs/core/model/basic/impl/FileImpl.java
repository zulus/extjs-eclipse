/**
 */
package net.w3des.extjs.core.model.basic.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import net.w3des.extjs.core.model.basic.Alias;
import net.w3des.extjs.core.model.basic.ExtJSFactory;
import net.w3des.extjs.core.model.basic.ExtJSPackage;
import net.w3des.extjs.core.model.basic.File;
import net.w3des.extjs.internal.core.ExtJSCore;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>File</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.FileImpl#getAliases <em>Aliases</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.impl.FileImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FileImpl extends MinimalEObjectImpl.Container implements File {
    /**
     * The cached value of the '{@link #getAliases() <em>Aliases</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAliases()
     * @generated
     * @ordered
     */
    protected EList<Alias> aliases;

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
    protected FileImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ExtJSPackage.Literals.FILE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<Alias> getAliases() {
        if (aliases == null) {
            aliases = new EObjectContainmentEList<Alias>(Alias.class, this, ExtJSPackage.FILE__ALIASES);
        }
        return aliases;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setName(String newName) {
        final String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ExtJSPackage.FILE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     */
    @Override
    public synchronized void cleanAliases() {
        if (getAliases() != null && !getAliases().isEmpty()) {
            try {
                getAliases().clear();
            } catch (Throwable e) {
                ExtJSCore.warn("NPE while clean", e);
            }
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    @Override
    public void addAlias(String name, int sourceStart, int sourceEnd, String type) {
        if (name == null || name.length() == 0) {
            return;
        }
        Alias alias;
        if (name.startsWith("widget.")) {
            alias = ExtJSFactory.eINSTANCE.createWidget();
            alias.setName(name.substring(7));
            alias.setRawName(name);
        } else if (name.startsWith("plugin.")) {
            alias = ExtJSFactory.eINSTANCE.createPlugin();
            alias.setName(name.substring(7));
            alias.setRawName(name);
        } else if (name.startsWith("feature.")) {
            alias = ExtJSFactory.eINSTANCE.createFeature();
            alias.setName(name.substring(8));
            alias.setRawName(name);
        } else if (name.startsWith("layout.")) {
            alias = ExtJSFactory.eINSTANCE.createLayout();
            alias.setName(name.substring(7));
            alias.setRawName(name);
        } else {
            alias = ExtJSFactory.eINSTANCE.createAlias();
            alias.setName(name);
            alias.setRawName(name);
        }
        alias.setSourceStart(sourceStart);
        alias.setSourceEnd(sourceEnd);
        alias.setTypeName(type);
        if (!getAliases().contains(alias)) {
        	getAliases().add(alias);
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ExtJSPackage.FILE__ALIASES:
                return ((InternalEList<?>)getAliases()).basicRemove(otherEnd, msgs);
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
            case ExtJSPackage.FILE__ALIASES:
                return getAliases();
            case ExtJSPackage.FILE__NAME:
                return getName();
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
            case ExtJSPackage.FILE__ALIASES:
                getAliases().clear();
                getAliases().addAll((Collection<? extends Alias>)newValue);
                return;
            case ExtJSPackage.FILE__NAME:
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
            case ExtJSPackage.FILE__ALIASES:
                getAliases().clear();
                return;
            case ExtJSPackage.FILE__NAME:
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
            case ExtJSPackage.FILE__ALIASES:
                return aliases != null && !aliases.isEmpty();
            case ExtJSPackage.FILE__NAME:
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
    public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
        switch (operationID) {
            case ExtJSPackage.FILE___CLEAN_ALIASES:
                cleanAliases();
                return null;
            case ExtJSPackage.FILE___ADD_ALIAS__STRING_INT_INT_STRING:
                addAlias((String)arguments.get(0), (Integer)arguments.get(1), (Integer)arguments.get(2), (String)arguments.get(3));
                return null;
        }
        return super.eInvoke(operationID, arguments);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        final StringBuffer result = new StringBuffer(super.toString());
        result.append(" (name: ");
        result.append(name);
        result.append(')');
        return result.toString();
    }

} //FileImpl
