/**
 */
package net.w3des.extjs.core.model.basic;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.File#getAliases <em>Aliases</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.File#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getFile()
 * @model
 * @generated
 */
public interface File extends EObject {
    /**
	 * Returns the value of the '<em><b>Aliases</b></em>' containment reference list.
	 * The list contents are of type {@link net.w3des.extjs.core.model.basic.Alias}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Aliases</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Aliases</em>' containment reference list.
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getFile_Aliases()
	 * @model containment="true"
	 * @generated
	 */
    EList<Alias> getAliases();

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
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getFile_Name()
	 * @model
	 * @generated
	 */
    String getName();

    /**
	 * Sets the value of the '{@link net.w3des.extjs.core.model.basic.File#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
    void setName(String value);

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
    void cleanAliases();

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Add alias by prefix
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
    void addAlias(String name, int sourceStart, int sourceEnd, String type);

} // File
