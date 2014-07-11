/**
 */
package net.w3des.extjs.core.model.basic;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see net.w3des.extjs.core.model.basic.ExtJSFactory
 * @model kind="package"
 * @generated
 */
public interface ExtJSPackage extends EPackage {
    /**
	 * The package name.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    String eNAME = "basic";

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
    ExtJSPackage eINSTANCE = net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl.init();

    /**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.WidgetImpl <em>Widget</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.WidgetImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getWidget()
	 * @generated
	 */
    int WIDGET = 2;

    /**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.AliasImpl <em>Alias</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.AliasImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getAlias()
	 * @generated
	 */
    int ALIAS = 1;

    /**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.TypeItemImpl <em>Type Item</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.TypeItemImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getTypeItem()
	 * @generated
	 */
    int TYPE_ITEM = 0;

    /**
	 * The feature id for the '<em><b>Source Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_ITEM__SOURCE_START = 0;

    /**
	 * The feature id for the '<em><b>Source End</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_ITEM__SOURCE_END = 1;

    /**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_ITEM__TYPE_NAME = 2;

    /**
	 * The number of structural features of the '<em>Type Item</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_ITEM_FEATURE_COUNT = 3;

    /**
	 * The number of operations of the '<em>Type Item</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_ITEM_OPERATION_COUNT = 0;

    /**
	 * The feature id for the '<em><b>Source Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ALIAS__SOURCE_START = TYPE_ITEM__SOURCE_START;

    /**
	 * The feature id for the '<em><b>Source End</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ALIAS__SOURCE_END = TYPE_ITEM__SOURCE_END;

    /**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ALIAS__TYPE_NAME = TYPE_ITEM__TYPE_NAME;

    /**
	 * The feature id for the '<em><b>Raw Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ALIAS__RAW_NAME = TYPE_ITEM_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ALIAS__NAME = TYPE_ITEM_FEATURE_COUNT + 1;

    /**
	 * The number of structural features of the '<em>Alias</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ALIAS_FEATURE_COUNT = TYPE_ITEM_FEATURE_COUNT + 2;

    /**
	 * The number of operations of the '<em>Alias</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ALIAS_OPERATION_COUNT = TYPE_ITEM_OPERATION_COUNT + 0;

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
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WIDGET__TYPE_NAME = ALIAS__TYPE_NAME;

    /**
	 * The feature id for the '<em><b>Raw Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WIDGET__RAW_NAME = ALIAS__RAW_NAME;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WIDGET__NAME = ALIAS__NAME;

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
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.PluginImpl <em>Plugin</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.PluginImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getPlugin()
	 * @generated
	 */
    int PLUGIN = 3;

    /**
	 * The feature id for the '<em><b>Source Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PLUGIN__SOURCE_START = ALIAS__SOURCE_START;

    /**
	 * The feature id for the '<em><b>Source End</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PLUGIN__SOURCE_END = ALIAS__SOURCE_END;

    /**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PLUGIN__TYPE_NAME = ALIAS__TYPE_NAME;

    /**
	 * The feature id for the '<em><b>Raw Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PLUGIN__RAW_NAME = ALIAS__RAW_NAME;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PLUGIN__NAME = ALIAS__NAME;

    /**
	 * The number of structural features of the '<em>Plugin</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PLUGIN_FEATURE_COUNT = ALIAS_FEATURE_COUNT + 0;

    /**
	 * The number of operations of the '<em>Plugin</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PLUGIN_OPERATION_COUNT = ALIAS_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.ExtJSProjectImpl <em>Project</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSProjectImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getExtJSProject()
	 * @generated
	 */
    int EXT_JS_PROJECT = 4;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EXT_JS_PROJECT__NAME = 0;

    /**
	 * The feature id for the '<em><b>Files</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EXT_JS_PROJECT__FILES = 1;

    /**
	 * The number of structural features of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EXT_JS_PROJECT_FEATURE_COUNT = 2;

    /**
	 * The number of operations of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EXT_JS_PROJECT_OPERATION_COUNT = 0;

    /**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.LayoutImpl <em>Layout</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.LayoutImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getLayout()
	 * @generated
	 */
    int LAYOUT = 5;

    /**
	 * The feature id for the '<em><b>Source Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LAYOUT__SOURCE_START = ALIAS__SOURCE_START;

    /**
	 * The feature id for the '<em><b>Source End</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LAYOUT__SOURCE_END = ALIAS__SOURCE_END;

    /**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LAYOUT__TYPE_NAME = ALIAS__TYPE_NAME;

    /**
	 * The feature id for the '<em><b>Raw Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LAYOUT__RAW_NAME = ALIAS__RAW_NAME;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LAYOUT__NAME = ALIAS__NAME;

    /**
	 * The number of structural features of the '<em>Layout</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LAYOUT_FEATURE_COUNT = ALIAS_FEATURE_COUNT + 0;

    /**
	 * The number of operations of the '<em>Layout</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LAYOUT_OPERATION_COUNT = ALIAS_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.FeatureImpl <em>Feature</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.FeatureImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getFeature()
	 * @generated
	 */
    int FEATURE = 6;

    /**
	 * The feature id for the '<em><b>Source Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FEATURE__SOURCE_START = ALIAS__SOURCE_START;

    /**
	 * The feature id for the '<em><b>Source End</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FEATURE__SOURCE_END = ALIAS__SOURCE_END;

    /**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FEATURE__TYPE_NAME = ALIAS__TYPE_NAME;

    /**
	 * The feature id for the '<em><b>Raw Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FEATURE__RAW_NAME = ALIAS__RAW_NAME;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FEATURE__NAME = ALIAS__NAME;

    /**
	 * The number of structural features of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FEATURE_FEATURE_COUNT = ALIAS_FEATURE_COUNT + 0;

    /**
	 * The number of operations of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FEATURE_OPERATION_COUNT = ALIAS_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.FileImpl <em>File</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.FileImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getFile()
	 * @generated
	 */
    int FILE = 7;

    /**
	 * The feature id for the '<em><b>Aliases</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FILE__ALIASES = 0;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FILE__NAME = 1;

    /**
	 * The number of structural features of the '<em>File</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FILE_FEATURE_COUNT = 2;

    /**
	 * The operation id for the '<em>Clean Aliases</em>' operation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FILE___CLEAN_ALIASES = 0;

    /**
	 * The operation id for the '<em>Add Alias</em>' operation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FILE___ADD_ALIAS__STRING_INT_INT_STRING = 1;

    /**
	 * The number of operations of the '<em>File</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FILE_OPERATION_COUNT = 2;

    /**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.EventImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getEvent()
	 * @generated
	 */
    int EVENT = 8;

    /**
	 * The feature id for the '<em><b>Source Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT__SOURCE_START = TYPE_ITEM__SOURCE_START;

    /**
	 * The feature id for the '<em><b>Source End</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT__SOURCE_END = TYPE_ITEM__SOURCE_END;

    /**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT__TYPE_NAME = TYPE_ITEM__TYPE_NAME;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT__NAME = TYPE_ITEM_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT__PARAMETERS = TYPE_ITEM_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT__DESCRIPTION = TYPE_ITEM_FEATURE_COUNT + 2;

    /**
	 * The number of structural features of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT_FEATURE_COUNT = TYPE_ITEM_FEATURE_COUNT + 3;

    /**
	 * The number of operations of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT_OPERATION_COUNT = TYPE_ITEM_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.ParameterImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getParameter()
	 * @generated
	 */
    int PARAMETER = 9;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PARAMETER__NAME = 0;

    /**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PARAMETER__TYPE = 1;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PARAMETER__DESCRIPTION = 2;

    /**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PARAMETER_FEATURE_COUNT = 3;

    /**
	 * The number of operations of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PARAMETER_OPERATION_COUNT = 0;

    /**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.ExecutionEnvironmentImpl <em>Execution Environment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.ExecutionEnvironmentImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getExecutionEnvironment()
	 * @generated
	 */
	int EXECUTION_ENVIRONMENT = 10;

				/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_ENVIRONMENT__NAME = 0;

				/**
	 * The feature id for the '<em><b>Builtin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_ENVIRONMENT__BUILTIN = 1;

				/**
	 * The feature id for the '<em><b>Versions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_ENVIRONMENT__VERSIONS = 2;

				/**
	 * The feature id for the '<em><b>Core Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_ENVIRONMENT__CORE_PATH = 3;

				/**
	 * The feature id for the '<em><b>Core Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_ENVIRONMENT__CORE_TYPE = 4;

				/**
	 * The feature id for the '<em><b>Libraries</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_ENVIRONMENT__LIBRARIES = 5;

				/**
	 * The feature id for the '<em><b>Facet</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_ENVIRONMENT__FACET = 6;

				/**
	 * The number of structural features of the '<em>Execution Environment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_ENVIRONMENT_FEATURE_COUNT = 7;

				/**
	 * The number of operations of the '<em>Execution Environment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_ENVIRONMENT_OPERATION_COUNT = 0;

				/**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.LibraryImpl <em>Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.LibraryImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getLibrary()
	 * @generated
	 */
	int LIBRARY = 11;

				/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__NAME = 0;

				/**
	 * The feature id for the '<em><b>Builtin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__BUILTIN = 1;

				/**
	 * The feature id for the '<em><b>Versions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__VERSIONS = 2;

				/**
	 * The feature id for the '<em><b>Sources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__SOURCES = 3;

				/**
	 * The feature id for the '<em><b>Sencha Touch Versions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__SENCHA_TOUCH_VERSIONS = 4;

				/**
	 * The number of structural features of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_FEATURE_COUNT = 5;

				/**
	 * The number of operations of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_OPERATION_COUNT = 0;

				/**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.impl.LibrarySourceImpl <em>Library Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.LibrarySourceImpl
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getLibrarySource()
	 * @generated
	 */
	int LIBRARY_SOURCE = 12;

				/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_SOURCE__TYPE = 0;

				/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_SOURCE__PATH = 1;

				/**
	 * The feature id for the '<em><b>Inclusions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_SOURCE__INCLUSIONS = 2;

				/**
	 * The feature id for the '<em><b>Exclusions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_SOURCE__EXCLUSIONS = 3;

				/**
	 * The number of structural features of the '<em>Library Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_SOURCE_FEATURE_COUNT = 4;

				/**
	 * The number of operations of the '<em>Library Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_SOURCE_OPERATION_COUNT = 0;

				/**
	 * The meta object id for the '{@link net.w3des.extjs.core.model.basic.LibrarySourceType <em>Library Source Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.LibrarySourceType
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getLibrarySourceType()
	 * @generated
	 */
	int LIBRARY_SOURCE_TYPE = 13;

				/**
	 * The meta object id for the '<em>Type Item Array</em>' data type.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getTypeItemArray()
	 * @generated
	 */
    int TYPE_ITEM_ARRAY = 14;


    /**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.TypeItem <em>Type Item</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Item</em>'.
	 * @see net.w3des.extjs.core.model.basic.TypeItem
	 * @generated
	 */
    EClass getTypeItem();

    /**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.TypeItem#getSourceStart <em>Source Start</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Start</em>'.
	 * @see net.w3des.extjs.core.model.basic.TypeItem#getSourceStart()
	 * @see #getTypeItem()
	 * @generated
	 */
    EAttribute getTypeItem_SourceStart();

    /**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.TypeItem#getSourceEnd <em>Source End</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source End</em>'.
	 * @see net.w3des.extjs.core.model.basic.TypeItem#getSourceEnd()
	 * @see #getTypeItem()
	 * @generated
	 */
    EAttribute getTypeItem_SourceEnd();

    /**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.TypeItem#getTypeName <em>Type Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Name</em>'.
	 * @see net.w3des.extjs.core.model.basic.TypeItem#getTypeName()
	 * @see #getTypeItem()
	 * @generated
	 */
    EAttribute getTypeItem_TypeName();

    /**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.Alias <em>Alias</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alias</em>'.
	 * @see net.w3des.extjs.core.model.basic.Alias
	 * @generated
	 */
    EClass getAlias();

    /**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.Alias#getRawName <em>Raw Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Raw Name</em>'.
	 * @see net.w3des.extjs.core.model.basic.Alias#getRawName()
	 * @see #getAlias()
	 * @generated
	 */
    EAttribute getAlias_RawName();

    /**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.Alias#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.w3des.extjs.core.model.basic.Alias#getName()
	 * @see #getAlias()
	 * @generated
	 */
    EAttribute getAlias_Name();

    /**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.Widget <em>Widget</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget</em>'.
	 * @see net.w3des.extjs.core.model.basic.Widget
	 * @generated
	 */
    EClass getWidget();

    /**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.Plugin <em>Plugin</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plugin</em>'.
	 * @see net.w3des.extjs.core.model.basic.Plugin
	 * @generated
	 */
    EClass getPlugin();

    /**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.ExtJSProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project</em>'.
	 * @see net.w3des.extjs.core.model.basic.ExtJSProject
	 * @generated
	 */
    EClass getExtJSProject();

    /**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.ExtJSProject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.w3des.extjs.core.model.basic.ExtJSProject#getName()
	 * @see #getExtJSProject()
	 * @generated
	 */
    EAttribute getExtJSProject_Name();

    /**
	 * Returns the meta object for the containment reference list '{@link net.w3des.extjs.core.model.basic.ExtJSProject#getFiles <em>Files</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Files</em>'.
	 * @see net.w3des.extjs.core.model.basic.ExtJSProject#getFiles()
	 * @see #getExtJSProject()
	 * @generated
	 */
    EReference getExtJSProject_Files();

    /**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.Layout <em>Layout</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Layout</em>'.
	 * @see net.w3des.extjs.core.model.basic.Layout
	 * @generated
	 */
    EClass getLayout();

    /**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.Feature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature</em>'.
	 * @see net.w3des.extjs.core.model.basic.Feature
	 * @generated
	 */
    EClass getFeature();

    /**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.File <em>File</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File</em>'.
	 * @see net.w3des.extjs.core.model.basic.File
	 * @generated
	 */
    EClass getFile();

    /**
	 * Returns the meta object for the containment reference list '{@link net.w3des.extjs.core.model.basic.File#getAliases <em>Aliases</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Aliases</em>'.
	 * @see net.w3des.extjs.core.model.basic.File#getAliases()
	 * @see #getFile()
	 * @generated
	 */
    EReference getFile_Aliases();

    /**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.File#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.w3des.extjs.core.model.basic.File#getName()
	 * @see #getFile()
	 * @generated
	 */
    EAttribute getFile_Name();

    /**
	 * Returns the meta object for the '{@link net.w3des.extjs.core.model.basic.File#cleanAliases() <em>Clean Aliases</em>}' operation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Clean Aliases</em>' operation.
	 * @see net.w3des.extjs.core.model.basic.File#cleanAliases()
	 * @generated
	 */
    EOperation getFile__CleanAliases();

    /**
	 * Returns the meta object for the '{@link net.w3des.extjs.core.model.basic.File#addAlias(java.lang.String, int, int, java.lang.String) <em>Add Alias</em>}' operation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Add Alias</em>' operation.
	 * @see net.w3des.extjs.core.model.basic.File#addAlias(java.lang.String, int, int, java.lang.String)
	 * @generated
	 */
    EOperation getFile__AddAlias__String_int_int_String();

    /**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event</em>'.
	 * @see net.w3des.extjs.core.model.basic.Event
	 * @generated
	 */
    EClass getEvent();

    /**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.Event#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.w3des.extjs.core.model.basic.Event#getName()
	 * @see #getEvent()
	 * @generated
	 */
    EAttribute getEvent_Name();

    /**
	 * Returns the meta object for the containment reference list '{@link net.w3des.extjs.core.model.basic.Event#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see net.w3des.extjs.core.model.basic.Event#getParameters()
	 * @see #getEvent()
	 * @generated
	 */
    EReference getEvent_Parameters();

    /**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.Event#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see net.w3des.extjs.core.model.basic.Event#getDescription()
	 * @see #getEvent()
	 * @generated
	 */
    EAttribute getEvent_Description();

    /**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see net.w3des.extjs.core.model.basic.Parameter
	 * @generated
	 */
    EClass getParameter();

    /**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.w3des.extjs.core.model.basic.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
    EAttribute getParameter_Name();

    /**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.Parameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see net.w3des.extjs.core.model.basic.Parameter#getType()
	 * @see #getParameter()
	 * @generated
	 */
    EAttribute getParameter_Type();

    /**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.Parameter#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see net.w3des.extjs.core.model.basic.Parameter#getDescription()
	 * @see #getParameter()
	 * @generated
	 */
    EAttribute getParameter_Description();

    /**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment <em>Execution Environment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Execution Environment</em>'.
	 * @see net.w3des.extjs.core.model.basic.ExecutionEnvironment
	 * @generated
	 */
	EClass getExecutionEnvironment();

				/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.w3des.extjs.core.model.basic.ExecutionEnvironment#getName()
	 * @see #getExecutionEnvironment()
	 * @generated
	 */
	EAttribute getExecutionEnvironment_Name();

				/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#isBuiltin <em>Builtin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Builtin</em>'.
	 * @see net.w3des.extjs.core.model.basic.ExecutionEnvironment#isBuiltin()
	 * @see #getExecutionEnvironment()
	 * @generated
	 */
	EAttribute getExecutionEnvironment_Builtin();

				/**
	 * Returns the meta object for the attribute list '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getVersions <em>Versions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Versions</em>'.
	 * @see net.w3des.extjs.core.model.basic.ExecutionEnvironment#getVersions()
	 * @see #getExecutionEnvironment()
	 * @generated
	 */
	EAttribute getExecutionEnvironment_Versions();

				/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getCorePath <em>Core Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Core Path</em>'.
	 * @see net.w3des.extjs.core.model.basic.ExecutionEnvironment#getCorePath()
	 * @see #getExecutionEnvironment()
	 * @generated
	 */
	EAttribute getExecutionEnvironment_CorePath();

				/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getCoreType <em>Core Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Core Type</em>'.
	 * @see net.w3des.extjs.core.model.basic.ExecutionEnvironment#getCoreType()
	 * @see #getExecutionEnvironment()
	 * @generated
	 */
	EAttribute getExecutionEnvironment_CoreType();

				/**
	 * Returns the meta object for the attribute list '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getLibraries <em>Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Libraries</em>'.
	 * @see net.w3des.extjs.core.model.basic.ExecutionEnvironment#getLibraries()
	 * @see #getExecutionEnvironment()
	 * @generated
	 */
	EAttribute getExecutionEnvironment_Libraries();

				/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.ExecutionEnvironment#getFacet <em>Facet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Facet</em>'.
	 * @see net.w3des.extjs.core.model.basic.ExecutionEnvironment#getFacet()
	 * @see #getExecutionEnvironment()
	 * @generated
	 */
	EAttribute getExecutionEnvironment_Facet();

				/**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.Library <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library</em>'.
	 * @see net.w3des.extjs.core.model.basic.Library
	 * @generated
	 */
	EClass getLibrary();

				/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.Library#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.w3des.extjs.core.model.basic.Library#getName()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Name();

				/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.Library#isBuiltin <em>Builtin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Builtin</em>'.
	 * @see net.w3des.extjs.core.model.basic.Library#isBuiltin()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Builtin();

				/**
	 * Returns the meta object for the attribute list '{@link net.w3des.extjs.core.model.basic.Library#getVersions <em>Versions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Versions</em>'.
	 * @see net.w3des.extjs.core.model.basic.Library#getVersions()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Versions();

				/**
	 * Returns the meta object for the containment reference list '{@link net.w3des.extjs.core.model.basic.Library#getSources <em>Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sources</em>'.
	 * @see net.w3des.extjs.core.model.basic.Library#getSources()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Sources();

				/**
	 * Returns the meta object for the attribute list '{@link net.w3des.extjs.core.model.basic.Library#getSenchaTouchVersions <em>Sencha Touch Versions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Sencha Touch Versions</em>'.
	 * @see net.w3des.extjs.core.model.basic.Library#getSenchaTouchVersions()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_SenchaTouchVersions();

				/**
	 * Returns the meta object for class '{@link net.w3des.extjs.core.model.basic.LibrarySource <em>Library Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library Source</em>'.
	 * @see net.w3des.extjs.core.model.basic.LibrarySource
	 * @generated
	 */
	EClass getLibrarySource();

				/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.LibrarySource#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see net.w3des.extjs.core.model.basic.LibrarySource#getType()
	 * @see #getLibrarySource()
	 * @generated
	 */
	EAttribute getLibrarySource_Type();

				/**
	 * Returns the meta object for the attribute '{@link net.w3des.extjs.core.model.basic.LibrarySource#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see net.w3des.extjs.core.model.basic.LibrarySource#getPath()
	 * @see #getLibrarySource()
	 * @generated
	 */
	EAttribute getLibrarySource_Path();

				/**
	 * Returns the meta object for the attribute list '{@link net.w3des.extjs.core.model.basic.LibrarySource#getInclusions <em>Inclusions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Inclusions</em>'.
	 * @see net.w3des.extjs.core.model.basic.LibrarySource#getInclusions()
	 * @see #getLibrarySource()
	 * @generated
	 */
	EAttribute getLibrarySource_Inclusions();

				/**
	 * Returns the meta object for the attribute list '{@link net.w3des.extjs.core.model.basic.LibrarySource#getExclusions <em>Exclusions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Exclusions</em>'.
	 * @see net.w3des.extjs.core.model.basic.LibrarySource#getExclusions()
	 * @see #getLibrarySource()
	 * @generated
	 */
	EAttribute getLibrarySource_Exclusions();

				/**
	 * Returns the meta object for enum '{@link net.w3des.extjs.core.model.basic.LibrarySourceType <em>Library Source Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Library Source Type</em>'.
	 * @see net.w3des.extjs.core.model.basic.LibrarySourceType
	 * @generated
	 */
	EEnum getLibrarySourceType();

				/**
	 * Returns the meta object for data type '<em>Type Item Array</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Type Item Array</em>'.
	 * @model instanceClass="net.w3des.extjs.core.model.basic.TypeItem[]"
	 * @generated
	 */
    EDataType getTypeItemArray();

    /**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
    ExtJSFactory getExtJSFactory();

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
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.TypeItemImpl <em>Type Item</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.TypeItemImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getTypeItem()
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
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.AliasImpl <em>Alias</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.AliasImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getAlias()
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
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.WidgetImpl <em>Widget</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.WidgetImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getWidget()
		 * @generated
		 */
        EClass WIDGET = eINSTANCE.getWidget();

        /**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.PluginImpl <em>Plugin</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.PluginImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getPlugin()
		 * @generated
		 */
        EClass PLUGIN = eINSTANCE.getPlugin();

        /**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.ExtJSProjectImpl <em>Project</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSProjectImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getExtJSProject()
		 * @generated
		 */
        EClass EXT_JS_PROJECT = eINSTANCE.getExtJSProject();

        /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute EXT_JS_PROJECT__NAME = eINSTANCE.getExtJSProject_Name();

        /**
		 * The meta object literal for the '<em><b>Files</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference EXT_JS_PROJECT__FILES = eINSTANCE.getExtJSProject_Files();

        /**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.LayoutImpl <em>Layout</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.LayoutImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getLayout()
		 * @generated
		 */
        EClass LAYOUT = eINSTANCE.getLayout();

        /**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.FeatureImpl <em>Feature</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.FeatureImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getFeature()
		 * @generated
		 */
        EClass FEATURE = eINSTANCE.getFeature();

        /**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.FileImpl <em>File</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.FileImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getFile()
		 * @generated
		 */
        EClass FILE = eINSTANCE.getFile();

        /**
		 * The meta object literal for the '<em><b>Aliases</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference FILE__ALIASES = eINSTANCE.getFile_Aliases();

        /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute FILE__NAME = eINSTANCE.getFile_Name();

        /**
		 * The meta object literal for the '<em><b>Clean Aliases</b></em>' operation.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EOperation FILE___CLEAN_ALIASES = eINSTANCE.getFile__CleanAliases();

        /**
		 * The meta object literal for the '<em><b>Add Alias</b></em>' operation.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EOperation FILE___ADD_ALIAS__STRING_INT_INT_STRING = eINSTANCE.getFile__AddAlias__String_int_int_String();

        /**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.EventImpl <em>Event</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.EventImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getEvent()
		 * @generated
		 */
        EClass EVENT = eINSTANCE.getEvent();

        /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute EVENT__NAME = eINSTANCE.getEvent_Name();

        /**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference EVENT__PARAMETERS = eINSTANCE.getEvent_Parameters();

        /**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute EVENT__DESCRIPTION = eINSTANCE.getEvent_Description();

        /**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.ParameterImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getParameter()
		 * @generated
		 */
        EClass PARAMETER = eINSTANCE.getParameter();

        /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

        /**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PARAMETER__TYPE = eINSTANCE.getParameter_Type();

        /**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PARAMETER__DESCRIPTION = eINSTANCE.getParameter_Description();

        /**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.ExecutionEnvironmentImpl <em>Execution Environment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.ExecutionEnvironmentImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getExecutionEnvironment()
		 * @generated
		 */
		EClass EXECUTION_ENVIRONMENT = eINSTANCE.getExecutionEnvironment();

								/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_ENVIRONMENT__NAME = eINSTANCE.getExecutionEnvironment_Name();

								/**
		 * The meta object literal for the '<em><b>Builtin</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_ENVIRONMENT__BUILTIN = eINSTANCE.getExecutionEnvironment_Builtin();

								/**
		 * The meta object literal for the '<em><b>Versions</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_ENVIRONMENT__VERSIONS = eINSTANCE.getExecutionEnvironment_Versions();

								/**
		 * The meta object literal for the '<em><b>Core Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_ENVIRONMENT__CORE_PATH = eINSTANCE.getExecutionEnvironment_CorePath();

								/**
		 * The meta object literal for the '<em><b>Core Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_ENVIRONMENT__CORE_TYPE = eINSTANCE.getExecutionEnvironment_CoreType();

								/**
		 * The meta object literal for the '<em><b>Libraries</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_ENVIRONMENT__LIBRARIES = eINSTANCE.getExecutionEnvironment_Libraries();

								/**
		 * The meta object literal for the '<em><b>Facet</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_ENVIRONMENT__FACET = eINSTANCE.getExecutionEnvironment_Facet();

								/**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.LibraryImpl <em>Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.LibraryImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getLibrary()
		 * @generated
		 */
		EClass LIBRARY = eINSTANCE.getLibrary();

								/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__NAME = eINSTANCE.getLibrary_Name();

								/**
		 * The meta object literal for the '<em><b>Builtin</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__BUILTIN = eINSTANCE.getLibrary_Builtin();

								/**
		 * The meta object literal for the '<em><b>Versions</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__VERSIONS = eINSTANCE.getLibrary_Versions();

								/**
		 * The meta object literal for the '<em><b>Sources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY__SOURCES = eINSTANCE.getLibrary_Sources();

								/**
		 * The meta object literal for the '<em><b>Sencha Touch Versions</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__SENCHA_TOUCH_VERSIONS = eINSTANCE.getLibrary_SenchaTouchVersions();

								/**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.impl.LibrarySourceImpl <em>Library Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.LibrarySourceImpl
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getLibrarySource()
		 * @generated
		 */
		EClass LIBRARY_SOURCE = eINSTANCE.getLibrarySource();

								/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY_SOURCE__TYPE = eINSTANCE.getLibrarySource_Type();

								/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY_SOURCE__PATH = eINSTANCE.getLibrarySource_Path();

								/**
		 * The meta object literal for the '<em><b>Inclusions</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY_SOURCE__INCLUSIONS = eINSTANCE.getLibrarySource_Inclusions();

								/**
		 * The meta object literal for the '<em><b>Exclusions</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY_SOURCE__EXCLUSIONS = eINSTANCE.getLibrarySource_Exclusions();

								/**
		 * The meta object literal for the '{@link net.w3des.extjs.core.model.basic.LibrarySourceType <em>Library Source Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.LibrarySourceType
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getLibrarySourceType()
		 * @generated
		 */
		EEnum LIBRARY_SOURCE_TYPE = eINSTANCE.getLibrarySourceType();

								/**
		 * The meta object literal for the '<em>Type Item Array</em>' data type.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see net.w3des.extjs.core.model.basic.impl.ExtJSPackageImpl#getTypeItemArray()
		 * @generated
		 */
        EDataType TYPE_ITEM_ARRAY = eINSTANCE.getTypeItemArray();

    }

} //ExtJSPackage
