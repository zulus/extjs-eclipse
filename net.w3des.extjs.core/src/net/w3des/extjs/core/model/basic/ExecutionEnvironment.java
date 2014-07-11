/**
 */
package net.w3des.extjs.core.model.basic;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Execution Environment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getName <em>Name</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#isBuiltin <em>Builtin</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getVersions <em>Versions</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getCorePath <em>Core Path</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getCoreType <em>Core Type</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getLibraries <em>Libraries</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getFacet <em>Facet</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getExecutionEnvironment()
 * @model
 * @generated
 */
public interface ExecutionEnvironment extends EObject {
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
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getExecutionEnvironment_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getName <em>Name</em>}' attribute.
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
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getExecutionEnvironment_Builtin()
	 * @model
	 * @generated
	 */
	boolean isBuiltin();

	/**
	 * Sets the value of the '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#isBuiltin <em>Builtin</em>}' attribute.
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
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getExecutionEnvironment_Versions()
	 * @model
	 * @generated
	 */
	EList<String> getVersions();

	/**
	 * Returns the value of the '<em><b>Core Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Core Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Core Path</em>' attribute.
	 * @see #setCorePath(String)
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getExecutionEnvironment_CorePath()
	 * @model
	 * @generated
	 */
	String getCorePath();

	/**
	 * Sets the value of the '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getCorePath <em>Core Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Core Path</em>' attribute.
	 * @see #getCorePath()
	 * @generated
	 */
	void setCorePath(String value);

	/**
	 * Returns the value of the '<em><b>Core Type</b></em>' attribute.
	 * The literals are from the enumeration {@link net.w3des.extjs.core.model.basic.LibrarySourceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Core Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Core Type</em>' attribute.
	 * @see net.w3des.extjs.core.model.basic.LibrarySourceType
	 * @see #setCoreType(LibrarySourceType)
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getExecutionEnvironment_CoreType()
	 * @model
	 * @generated
	 */
	LibrarySourceType getCoreType();

	/**
	 * Sets the value of the '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getCoreType <em>Core Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Core Type</em>' attribute.
	 * @see net.w3des.extjs.core.model.basic.LibrarySourceType
	 * @see #getCoreType()
	 * @generated
	 */
	void setCoreType(LibrarySourceType value);

	/**
	 * Returns the value of the '<em><b>Libraries</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Libraries</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Libraries</em>' attribute list.
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getExecutionEnvironment_Libraries()
	 * @model
	 * @generated
	 */
	EList<String> getLibraries();

	/**
	 * Returns the value of the '<em><b>Facet</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Facet</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Facet</em>' attribute.
	 * @see #setFacet(String)
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getExecutionEnvironment_Facet()
	 * @model
	 * @generated
	 */
	String getFacet();

	/**
	 * Sets the value of the '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getFacet <em>Facet</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Facet</em>' attribute.
	 * @see #getFacet()
	 * @generated
	 */
	void setFacet(String value);

} // ExecutionEnvironment
