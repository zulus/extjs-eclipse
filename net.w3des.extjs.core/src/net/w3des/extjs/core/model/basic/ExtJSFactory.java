/**
 */
package net.w3des.extjs.core.model.basic;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see net.w3des.extjs.core.model.basic.ExtJSPackage
 * @generated
 */
public interface ExtJSFactory extends EFactory {
    /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    ExtJSFactory eINSTANCE = net.w3des.extjs.core.model.basic.impl.ExtJSFactoryImpl.init();

    /**
	 * Returns a new object of class '<em>Alias</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Alias</em>'.
	 * @generated
	 */
    Alias createAlias();

    /**
	 * Returns a new object of class '<em>Widget</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Widget</em>'.
	 * @generated
	 */
    Widget createWidget();

    /**
	 * Returns a new object of class '<em>Plugin</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Plugin</em>'.
	 * @generated
	 */
    Plugin createPlugin();

    /**
	 * Returns a new object of class '<em>Project</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project</em>'.
	 * @generated
	 */
    ExtJSProject createExtJSProject();

    /**
	 * Returns a new object of class '<em>Layout</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Layout</em>'.
	 * @generated
	 */
    Layout createLayout();

    /**
	 * Returns a new object of class '<em>Feature</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature</em>'.
	 * @generated
	 */
    Feature createFeature();

    /**
	 * Returns a new object of class '<em>File</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>File</em>'.
	 * @generated
	 */
    File createFile();

    /**
	 * Returns a new object of class '<em>Event</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event</em>'.
	 * @generated
	 */
    Event createEvent();

    /**
	 * Returns a new object of class '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter</em>'.
	 * @generated
	 */
    Parameter createParameter();

    /**
	 * Returns a new object of class '<em>Execution Environment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Execution Environment</em>'.
	 * @generated
	 */
	ExecutionEnvironment createExecutionEnvironment();

				/**
	 * Returns a new object of class '<em>Library</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Library</em>'.
	 * @generated
	 */
	Library createLibrary();

				/**
	 * Returns a new object of class '<em>Library Source</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Library Source</em>'.
	 * @generated
	 */
	LibrarySource createLibrarySource();

				/**
	 * Returns a new object of class '<em>Core Version Default</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Core Version Default</em>'.
	 * @generated
	 */
	CoreVersionDefault createCoreVersionDefault();

				/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
    ExtJSPackage getExtJSPackage();

} //ExtJSFactory
