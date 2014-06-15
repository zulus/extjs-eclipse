/**
 */
package net.w3des.extjs.core.model.basic;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Library Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.w3des.extjs.core.model.basic.LibrarySource#getType <em>Type</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.LibrarySource#getPath <em>Path</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.LibrarySource#getInclusions <em>Inclusions</em>}</li>
 *   <li>{@link net.w3des.extjs.core.model.basic.LibrarySource#getExclusions <em>Exclusions</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getLibrarySource()
 * @model
 * @generated
 */
public interface LibrarySource extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link net.w3des.extjs.core.model.basic.LibrarySourceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see net.w3des.extjs.core.model.basic.LibrarySourceType
	 * @see #setType(LibrarySourceType)
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getLibrarySource_Type()
	 * @model
	 * @generated
	 */
	LibrarySourceType getType();

	/**
	 * Sets the value of the '{@link net.w3des.extjs.core.model.basic.LibrarySource#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see net.w3des.extjs.core.model.basic.LibrarySourceType
	 * @see #getType()
	 * @generated
	 */
	void setType(LibrarySourceType value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getLibrarySource_Path()
	 * @model
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link net.w3des.extjs.core.model.basic.LibrarySource#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Inclusions</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inclusions</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inclusions</em>' attribute list.
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getLibrarySource_Inclusions()
	 * @model
	 * @generated
	 */
	EList<String> getInclusions();

	/**
	 * Returns the value of the '<em><b>Exclusions</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclusions</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclusions</em>' attribute list.
	 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getLibrarySource_Exclusions()
	 * @model
	 * @generated
	 */
	EList<String> getExclusions();

} // LibrarySource
