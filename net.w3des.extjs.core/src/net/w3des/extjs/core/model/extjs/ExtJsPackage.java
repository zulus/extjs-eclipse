/**
 */
package net.w3des.extjs.core.model.extjs;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see net.w3des.extjs.core.model.extjs.ExtJsFactory
 * @model kind="package"
 * @generated
 */
public interface ExtJsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "extjs";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://net.w3des.extjs/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "extjs";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExtJsPackage eINSTANCE = net.w3des.extjs.core.model.extjs.impl.ExtJsPackageImpl.init();

	/**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.extjs.impl.AliasImpl <em>Alias</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.extjs.impl.AliasImpl
	 * @see net.w3des.extjs.core.model.extjs.impl.ExtJsPackageImpl#getAlias()
	 * @generated
	 */
	int ALIAS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS__NAME = 0;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS__CLASS_NAME = 1;

	/**
	 * The feature id for the '<em><b>Source Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS__SOURCE_START = 2;

	/**
	 * The feature id for the '<em><b>Source End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS__SOURCE_END = 3;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS__PATH = 4;

	/**
	 * The number of structural features of the '<em>Alias</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Alias</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.extjs.impl.WidgetImpl <em>Widget</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.extjs.impl.WidgetImpl
	 * @see net.w3des.extjs.core.model.extjs.impl.ExtJsPackageImpl#getWidget()
	 * @generated
	 */
	int WIDGET = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__NAME = ALIAS__NAME;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__CLASS_NAME = ALIAS__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Source Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__SOURCE_START = ALIAS__SOURCE_START;

	/**
	 * The feature id for the '<em><b>Source End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__SOURCE_END = ALIAS__SOURCE_END;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__PATH = ALIAS__PATH;

	/**
	 * The number of structural features of the '<em>Widget</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_FEATURE_COUNT = ALIAS_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Widget</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_OPERATION_COUNT = ALIAS_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.extjs.Alias <em>Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alias</em>'.
	 * @see net.w3des.extjs.core.model.extjs.Alias
	 * @generated
	 */
	EClass getAlias();

	/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.extjs.Alias#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.w3des.extjs.core.model.extjs.Alias#getName()
	 * @see #getAlias()
	 * @generated
	 */
	EAttribute getAlias_Name();

	/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.extjs.Alias#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see net.w3des.extjs.core.model.extjs.Alias#getClassName()
	 * @see #getAlias()
	 * @generated
	 */
	EAttribute getAlias_ClassName();

	/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.extjs.Alias#getSourceStart <em>Source Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Start</em>'.
	 * @see net.w3des.extjs.core.model.extjs.Alias#getSourceStart()
	 * @see #getAlias()
	 * @generated
	 */
	EAttribute getAlias_SourceStart();

	/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.extjs.Alias#getSourceEnd <em>Source End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source End</em>'.
	 * @see net.w3des.extjs.core.model.extjs.Alias#getSourceEnd()
	 * @see #getAlias()
	 * @generated
	 */
	EAttribute getAlias_SourceEnd();

	/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.extjs.Alias#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see net.w3des.extjs.core.model.extjs.Alias#getPath()
	 * @see #getAlias()
	 * @generated
	 */
	EAttribute getAlias_Path();

	/**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.extjs.Widget <em>Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget</em>'.
	 * @see net.w3des.extjs.core.model.extjs.Widget
	 * @generated
	 */
	EClass getWidget();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExtJsFactory getExtJsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.extjs.impl.AliasImpl <em>Alias</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.extjs.impl.AliasImpl
		 * @see net.w3des.extjs.core.model.extjs.impl.ExtJsPackageImpl#getAlias()
		 * @generated
		 */
		EClass ALIAS = eINSTANCE.getAlias();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALIAS__NAME = eINSTANCE.getAlias_Name();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALIAS__CLASS_NAME = eINSTANCE.getAlias_ClassName();

		/**
		 * The meta object literal for the '<em><b>Source Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALIAS__SOURCE_START = eINSTANCE.getAlias_SourceStart();

		/**
		 * The meta object literal for the '<em><b>Source End</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALIAS__SOURCE_END = eINSTANCE.getAlias_SourceEnd();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALIAS__PATH = eINSTANCE.getAlias_Path();

		/**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.extjs.impl.WidgetImpl <em>Widget</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.extjs.impl.WidgetImpl
		 * @see net.w3des.extjs.core.model.extjs.impl.ExtJsPackageImpl#getWidget()
		 * @generated
		 */
		EClass WIDGET = eINSTANCE.getWidget();

	}

} //ExtJsPackage
