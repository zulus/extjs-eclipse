/**
 */
package net.w3des.extjs.core.model.basic;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.Library#getName <em>Name</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.Library#isBuiltin <em>Builtin</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.Library#getVersions <em>Versions</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.Library#getSources <em>Sources</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getLibrary()
 * @model
 * @generated
 */
public interface Library extends EObject {
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
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getLibrary_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link net.w3des.extjs.core.model.basic.Library#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Builtin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Builtin</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Builtin</em>' attribute.
	 * @see #setBuiltin(boolean)
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getLibrary_Builtin()
	 * @model
	 * @generated
	 */
	boolean isBuiltin();

	/**
	 * Sets the value of the '{@link net.w3des.extjs.core.model.basic.Library#isBuiltin <em>Builtin</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Builtin</em>' attribute.
	 * @see #isBuiltin()
	 * @generated
	 */
	void setBuiltin(boolean value);

	/**
	 * Returns the value of the '<em><b>Versions</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Versions</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Versions</em>' attribute list.
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getLibrary_Versions()
	 * @model
	 * @generated
	 */
	EList<String> getVersions();

	/**
	 * Returns the value of the '<em><b>Sources</b></em>' containment reference list.
	 * The list contents are of type {@link net.w3des.extjs.core.model.basic.LibrarySource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sources</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sources</em>' containment reference list.
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getLibrary_Sources()
	 * @model containment="true"
	 * @generated
	 */
	EList<LibrarySource> getSources();

} // Library
