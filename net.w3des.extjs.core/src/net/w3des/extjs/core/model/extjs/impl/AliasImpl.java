/**
 */
package net.w3des.extjs.core.model.extjs.impl;

import net.w3des.extjs.core.model.extjs.Alias;
import net.w3des.extjs.core.model.extjs.ExtJsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alias</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.extjs.impl.AliasImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.extjs.impl.AliasImpl#getClassName <em>Class Name</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.extjs.impl.AliasImpl#getSourceStart <em>Source Start</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.extjs.impl.AliasImpl#getSourceEnd <em>Source End</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.extjs.impl.AliasImpl#getPath <em>Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AliasImpl extends MinimalEObjectImpl.Container implements Alias {
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
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

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
		return ExtJsPackage.Literals.ALIAS;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJsPackage.ALIAS__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClassName(String newClassName) {
		String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJsPackage.ALIAS__CLASS_NAME, oldClassName, className));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJsPackage.ALIAS__SOURCE_START, oldSourceStart, sourceStart));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJsPackage.ALIAS__SOURCE_END, oldSourceEnd, sourceEnd));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ExtJsPackage.ALIAS__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExtJsPackage.ALIAS__NAME:
				return getName();
			case ExtJsPackage.ALIAS__CLASS_NAME:
				return getClassName();
			case ExtJsPackage.ALIAS__SOURCE_START:
				return getSourceStart();
			case ExtJsPackage.ALIAS__SOURCE_END:
				return getSourceEnd();
			case ExtJsPackage.ALIAS__PATH:
				return getPath();
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
			case ExtJsPackage.ALIAS__NAME:
				setName((String)newValue);
				return;
			case ExtJsPackage.ALIAS__CLASS_NAME:
				setClassName((String)newValue);
				return;
			case ExtJsPackage.ALIAS__SOURCE_START:
				setSourceStart((Integer)newValue);
				return;
			case ExtJsPackage.ALIAS__SOURCE_END:
				setSourceEnd((Integer)newValue);
				return;
			case ExtJsPackage.ALIAS__PATH:
				setPath((String)newValue);
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
			case ExtJsPackage.ALIAS__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ExtJsPackage.ALIAS__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
			case ExtJsPackage.ALIAS__SOURCE_START:
				setSourceStart(SOURCE_START_EDEFAULT);
				return;
			case ExtJsPackage.ALIAS__SOURCE_END:
				setSourceEnd(SOURCE_END_EDEFAULT);
				return;
			case ExtJsPackage.ALIAS__PATH:
				setPath(PATH_EDEFAULT);
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
			case ExtJsPackage.ALIAS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ExtJsPackage.ALIAS__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
			case ExtJsPackage.ALIAS__SOURCE_START:
				return sourceStart != SOURCE_START_EDEFAULT;
			case ExtJsPackage.ALIAS__SOURCE_END:
				return sourceEnd != SOURCE_END_EDEFAULT;
			case ExtJsPackage.ALIAS__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
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
		result.append(", className: ");
		result.append(className);
		result.append(", sourceStart: ");
		result.append(sourceStart);
		result.append(", sourceEnd: ");
		result.append(sourceEnd);
		result.append(", path: ");
		result.append(path);
		result.append(')');
		return result.toString();
	}

} //AliasImpl
