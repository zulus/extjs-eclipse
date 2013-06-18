/**
 */
package net.w3des.extjs.core.model.basic;

import org.eclipse.emf.ecore.EObject;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.TypeItem#getSourceStart <em>Source Start</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.TypeItem#getSourceEnd <em>Source End</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.TypeItem#getTypeName <em>Type Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getTypeItem()
 * @model abstract="true"
 * @generated
 */
public interface TypeItem extends EObject {
    /**
     * Returns the value of the '<em><b>Source Start</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Start</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Source Start</em>' attribute.
     * @see #setSourceStart(int)
     * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getTypeItem_SourceStart()
     * @model
     * @generated
     */
    int getSourceStart();

    /**
     * Sets the value of the '{@link net.w3des.extjs.core.model.basic.TypeItem#getSourceStart <em>Source Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Source Start</em>' attribute.
     * @see #getSourceStart()
     * @generated
     */
    void setSourceStart(int value);

    /**
     * Returns the value of the '<em><b>Source End</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source End</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Source End</em>' attribute.
     * @see #setSourceEnd(int)
     * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getTypeItem_SourceEnd()
     * @model
     * @generated
     */
    int getSourceEnd();

    /**
     * Sets the value of the '{@link net.w3des.extjs.core.model.basic.TypeItem#getSourceEnd <em>Source End</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Source End</em>' attribute.
     * @see #getSourceEnd()
     * @generated
     */
    void setSourceEnd(int value);

    /**
     * Returns the value of the '<em><b>Type Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type Name</em>' attribute.
     * @see #setTypeName(String)
     * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getTypeItem_TypeName()
     * @model
     * @generated
     */
    String getTypeName();

    /**
     * Sets the value of the '{@link net.w3des.extjs.core.model.basic.TypeItem#getTypeName <em>Type Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type Name</em>' attribute.
     * @see #getTypeName()
     * @generated
     */
    void setTypeName(String value);

} // TypeItem
