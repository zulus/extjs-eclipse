/**
 */
package net.w3des.extjs.core.model.basic.impl;

import net.w3des.extjs.core.model.basic.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExtJSFactoryImpl extends EFactoryImpl implements ExtJSFactory {
    /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static ExtJSFactory init() {
		try {
			ExtJSFactory theExtJSFactory = (ExtJSFactory)EPackage.Registry.INSTANCE.getEFactory(ExtJSPackage.eNS_URI);
			if (theExtJSFactory != null) {
				return theExtJSFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ExtJSFactoryImpl();
	}

    /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ExtJSFactoryImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ExtJSPackage.ALIAS: return createAlias();
			case ExtJSPackage.WIDGET: return createWidget();
			case ExtJSPackage.PLUGIN: return createPlugin();
			case ExtJSPackage.EXT_JS_PROJECT: return createExtJSProject();
			case ExtJSPackage.LAYOUT: return createLayout();
			case ExtJSPackage.FEATURE: return createFeature();
			case ExtJSPackage.FILE: return createFile();
			case ExtJSPackage.EVENT: return createEvent();
			case ExtJSPackage.PARAMETER: return createParameter();
			case ExtJSPackage.EXECUTION_ENVIRONMENT: return createExecutionEnvironment();
			case ExtJSPackage.LIBRARY: return createLibrary();
			case ExtJSPackage.LIBRARY_SOURCE: return createLibrarySource();
			case ExtJSPackage.CORE_VERSION_DEFAULT: return createCoreVersionDefault();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ExtJSPackage.LIBRARY_SOURCE_TYPE:
				return createLibrarySourceTypeFromString(eDataType, initialValue);
			case ExtJSPackage.TYPE_ITEM_ARRAY:
				return createTypeItemArrayFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ExtJSPackage.LIBRARY_SOURCE_TYPE:
				return convertLibrarySourceTypeToString(eDataType, instanceValue);
			case ExtJSPackage.TYPE_ITEM_ARRAY:
				return convertTypeItemArrayToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Alias createAlias() {
		AliasImpl alias = new AliasImpl();
		return alias;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Widget createWidget() {
		WidgetImpl widget = new WidgetImpl();
		return widget;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Plugin createPlugin() {
		PluginImpl plugin = new PluginImpl();
		return plugin;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ExtJSProject createExtJSProject() {
		ExtJSProjectImpl extJSProject = new ExtJSProjectImpl();
		return extJSProject;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Layout createLayout() {
		LayoutImpl layout = new LayoutImpl();
		return layout;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Feature createFeature() {
		FeatureImpl feature = new FeatureImpl();
		return feature;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public File createFile() {
		FileImpl file = new FileImpl();
		return file;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Event createEvent() {
		EventImpl event = new EventImpl();
		return event;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionEnvironment createExecutionEnvironment() {
		ExecutionEnvironmentImpl executionEnvironment = new ExecutionEnvironmentImpl();
		return executionEnvironment;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Library createLibrary() {
		LibraryImpl library = new LibraryImpl();
		return library;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibrarySource createLibrarySource() {
		LibrarySourceImpl librarySource = new LibrarySourceImpl();
		return librarySource;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CoreVersionDefault createCoreVersionDefault() {
		CoreVersionDefaultImpl coreVersionDefault = new CoreVersionDefaultImpl();
		return coreVersionDefault;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibrarySourceType createLibrarySourceTypeFromString(EDataType eDataType, String initialValue) {
		LibrarySourceType result = LibrarySourceType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLibrarySourceTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public TypeItem[] createTypeItemArrayFromString(EDataType eDataType, String initialValue) {
		return (TypeItem[])super.createFromString(initialValue);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String convertTypeItemArrayToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ExtJSPackage getExtJSPackage() {
		return (ExtJSPackage)getEPackage();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
    @Deprecated
    public static ExtJSPackage getPackage() {
		return ExtJSPackage.eINSTANCE;
	}

} //ExtJSFactoryImpl
