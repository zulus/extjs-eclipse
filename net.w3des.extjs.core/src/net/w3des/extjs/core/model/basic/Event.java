/**
 */
package net.w3des.extjs.core.model.basic;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.Event#getName <em>Name</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.Event#getParameters <em>Parameters</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.Event#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getEvent()
 * @model
 * @generated
 */
public interface Event extends TypeItem {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getEvent_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link net.w3des.extjs.core.model.basic.Event#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
     * The list contents are of type {@link net.w3des.extjs.core.model.basic.Parameter}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameters</em>' containment reference list.
     * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getEvent_Parameters()
     * @model containment="true"
     * @generated
     */
    EList<Parameter> getParameters();

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getEvent_Description()
     * @model
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link net.w3des.extjs.core.model.basic.Event#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

} // Event
