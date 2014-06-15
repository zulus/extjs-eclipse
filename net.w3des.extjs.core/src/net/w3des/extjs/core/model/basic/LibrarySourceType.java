/**
 */
package net.w3des.extjs.core.model.basic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Library Source Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see net.w3des.extjs.core.model.basic.ExtJSPackage#getLibrarySourceType()
 * @model
 * @generated
 */
public enum LibrarySourceType implements Enumerator {
	/**
	 * The '<em><b>Zip File</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ZIP_FILE_VALUE
	 * @generated
	 * @ordered
	 */
	ZIP_FILE(0, "ZipFile", "ZipFile"),

	/**
	 * The '<em><b>Folder</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FOLDER_VALUE
	 * @generated
	 * @ordered
	 */
	FOLDER(1, "Folder", "Folder"),

	/**
	 * The '<em><b>Javascript File</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JAVASCRIPT_FILE_VALUE
	 * @generated
	 * @ordered
	 */
	JAVASCRIPT_FILE(2, "JavascriptFile", "JavascriptFile");

	/**
	 * The '<em><b>Zip File</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Zip File</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ZIP_FILE
	 * @model name="ZipFile"
	 * @generated
	 * @ordered
	 */
	public static final int ZIP_FILE_VALUE = 0;

	/**
	 * The '<em><b>Folder</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Folder</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FOLDER
	 * @model name="Folder"
	 * @generated
	 * @ordered
	 */
	public static final int FOLDER_VALUE = 1;

	/**
	 * The '<em><b>Javascript File</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Javascript File</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JAVASCRIPT_FILE
	 * @model name="JavascriptFile"
	 * @generated
	 * @ordered
	 */
	public static final int JAVASCRIPT_FILE_VALUE = 2;

	/**
	 * An array of all the '<em><b>Library Source Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final LibrarySourceType[] VALUES_ARRAY =
		new LibrarySourceType[] {
			ZIP_FILE,
			FOLDER,
			JAVASCRIPT_FILE,
		};

	/**
	 * A public read-only list of all the '<em><b>Library Source Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<LibrarySourceType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Library Source Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LibrarySourceType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			LibrarySourceType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Library Source Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LibrarySourceType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			LibrarySourceType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Library Source Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LibrarySourceType get(int value) {
		switch (value) {
			case ZIP_FILE_VALUE: return ZIP_FILE;
			case FOLDER_VALUE: return FOLDER;
			case JAVASCRIPT_FILE_VALUE: return JAVASCRIPT_FILE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private LibrarySourceType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
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
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //LibrarySourceType
