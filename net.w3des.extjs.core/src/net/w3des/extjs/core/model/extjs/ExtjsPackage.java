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
 * @see net.w3des.extjs.core.model.extjs.ExtjsFactory
 * @model kind="package"
 * @generated
 */
public interface ExtjsPackage extends EPackage {
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
	String eNS_URI = "http://extjs.w3des.net/1.0";

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
	ExtjsPackage eINSTANCE = net.w3des.extjs.core.model.extjs.impl.ExtjsPackageImpl.init();

	/**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.extjs.impl.WidgetImpl <em>Widget</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.extjs.impl.WidgetImpl
	 * @see net.w3des.extjs.core.model.extjs.impl.ExtjsPackageImpl#getWidget()
	 * @generated
	 */
	int WIDGET = 2;

	/**
	 * The number of structural features of the '<em>Widget</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Widget</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.extjs.impl.AliasImpl <em>Alias</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.extjs.impl.AliasImpl
	 * @see net.w3des.extjs.core.model.extjs.impl.ExtjsPackageImpl#getAlias()
	 * @generated
	 */
	int ALIAS = 1;

	/**
	 * The feature id for the '<em><b>Raw Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS__RAW_NAME = WIDGET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS__NAME = WIDGET_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Alias</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS_FEATURE_COUNT = WIDGET_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Alias</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS_OPERATION_COUNT = WIDGET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.extjs.impl.TypeItemImpl <em>Type Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.extjs.impl.TypeItemImpl
	 * @see net.w3des.extjs.core.model.extjs.impl.ExtjsPackageImpl#getTypeItem()
	 * @generated
	 */
	int TYPE_ITEM = 0;

	/**
	 * The feature id for the '<em><b>Raw Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ITEM__RAW_NAME = ALIAS__RAW_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ITEM__NAME = ALIAS__NAME;

	/**
	 * The feature id for the '<em><b>Source Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ITEM__SOURCE_START = ALIAS_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ITEM__SOURCE_END = ALIAS_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ITEM__TYPE_NAME = ALIAS_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ITEM__FILE = ALIAS_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Type Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ITEM_FEATURE_COUNT = ALIAS_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Type Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ITEM_OPERATION_COUNT = ALIAS_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.extjs.impl.PluginImpl <em>Plugin</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.extjs.impl.PluginImpl
	 * @see net.w3des.extjs.core.model.extjs.impl.ExtjsPackageImpl#getPlugin()
	 * @generated
	 */
	int PLUGIN = 3;

	/**
	 * The number of structural features of the '<em>Plugin</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Plugin</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.extjs.TypeItem <em>Type Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Item</em>'.
	 * @see net.w3des.extjs.core.model.extjs.TypeItem
	 * @generated
	 */
	EClass getTypeItem();

	/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.extjs.TypeItem#getSourceStart <em>Source Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Start</em>'.
	 * @see net.w3des.extjs.core.model.extjs.TypeItem#getSourceStart()
	 * @see #getTypeItem()
	 * @generated
	 */
	EAttribute getTypeItem_SourceStart();

	/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.extjs.TypeItem#getSourceEnd <em>Source End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source End</em>'.
	 * @see net.w3des.extjs.core.model.extjs.TypeItem#getSourceEnd()
	 * @see #getTypeItem()
	 * @generated
	 */
	EAttribute getTypeItem_SourceEnd();

	/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.extjs.TypeItem#getTypeName <em>Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Name</em>'.
	 * @see net.w3des.extjs.core.model.extjs.TypeItem#getTypeName()
	 * @see #getTypeItem()
	 * @generated
	 */
	EAttribute getTypeItem_TypeName();

	/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.extjs.TypeItem#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File</em>'.
	 * @see net.w3des.extjs.core.model.extjs.TypeItem#getFile()
	 * @see #getTypeItem()
	 * @generated
	 */
	EAttribute getTypeItem_File();

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
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.extjs.Alias#getRawName <em>Raw Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Raw Name</em>'.
	 * @see net.w3des.extjs.core.model.extjs.Alias#getRawName()
	 * @see #getAlias()
	 * @generated
	 */
	EAttribute getAlias_RawName();

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
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.extjs.Widget <em>Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget</em>'.
	 * @see net.w3des.extjs.core.model.extjs.Widget
	 * @generated
	 */
	EClass getWidget();

	/**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.extjs.Plugin <em>Plugin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plugin</em>'.
	 * @see net.w3des.extjs.core.model.extjs.Plugin
	 * @generated
	 */
	EClass getPlugin();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExtjsFactory getExtjsFactory();

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
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.extjs.impl.TypeItemImpl <em>Type Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.extjs.impl.TypeItemImpl
		 * @see net.w3des.extjs.core.model.extjs.impl.ExtjsPackageImpl#getTypeItem()
		 * @generated
		 */
		EClass TYPE_ITEM = eINSTANCE.getTypeItem();

		/**
		 * The meta object literal for the '<em><b>Source Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_ITEM__SOURCE_START = eINSTANCE.getTypeItem_SourceStart();

		/**
		 * The meta object literal for the '<em><b>Source End</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_ITEM__SOURCE_END = eINSTANCE.getTypeItem_SourceEnd();

		/**
		 * The meta object literal for the '<em><b>Type Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_ITEM__TYPE_NAME = eINSTANCE.getTypeItem_TypeName();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_ITEM__FILE = eINSTANCE.getTypeItem_File();

		/**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.extjs.impl.AliasImpl <em>Alias</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.extjs.impl.AliasImpl
		 * @see net.w3des.extjs.core.model.extjs.impl.ExtjsPackageImpl#getAlias()
		 * @generated
		 */
		EClass ALIAS = eINSTANCE.getAlias();

		/**
		 * The meta object literal for the '<em><b>Raw Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALIAS__RAW_NAME = eINSTANCE.getAlias_RawName();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALIAS__NAME = eINSTANCE.getAlias_Name();

		/**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.extjs.impl.WidgetImpl <em>Widget</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.extjs.impl.WidgetImpl
		 * @see net.w3des.extjs.core.model.extjs.impl.ExtjsPackageImpl#getWidget()
		 * @generated
		 */
		EClass WIDGET = eINSTANCE.getWidget();

		/**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.extjs.impl.PluginImpl <em>Plugin</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.extjs.impl.PluginImpl
		 * @see net.w3des.extjs.core.model.extjs.impl.ExtjsPackageImpl#getPlugin()
		 * @generated
		 */
		EClass PLUGIN = eINSTANCE.getPlugin();

	}

} //ExtjsPackage
