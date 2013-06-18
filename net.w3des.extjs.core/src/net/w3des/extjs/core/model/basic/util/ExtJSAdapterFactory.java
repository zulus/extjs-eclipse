/**
 */
package net.w3des.extjs.core.model.basic.util;

import net.w3des.extjs.core.model.basic.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see net.w3des.extjs.core.model.basic.ExtJSPackage
 * @generated
 */
public class ExtJSAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static ExtJSPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExtJSAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = ExtJSPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ExtJSSwitch<Adapter> modelSwitch =
        new ExtJSSwitch<Adapter>() {
            @Override
            public Adapter caseTypeItem(TypeItem object) {
                return createTypeItemAdapter();
            }
            @Override
            public Adapter caseAlias(Alias object) {
                return createAliasAdapter();
            }
            @Override
            public Adapter caseWidget(Widget object) {
                return createWidgetAdapter();
            }
            @Override
            public Adapter casePlugin(Plugin object) {
                return createPluginAdapter();
            }
            @Override
            public Adapter caseExtJSProject(ExtJSProject object) {
                return createExtJSProjectAdapter();
            }
            @Override
            public Adapter caseLayout(Layout object) {
                return createLayoutAdapter();
            }
            @Override
            public Adapter caseFeature(Feature object) {
                return createFeatureAdapter();
            }
            @Override
            public Adapter caseFile(File object) {
                return createFileAdapter();
            }
            @Override
            public Adapter caseEvent(Event object) {
                return createEventAdapter();
            }
            @Override
            public Adapter caseParameter(Parameter object) {
                return createParameterAdapter();
            }
            @Override
            public Adapter defaultCase(EObject object) {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link net.w3des.extjs.core.model.basic.TypeItem <em>Type Item</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see net.w3des.extjs.core.model.basic.TypeItem
     * @generated
     */
    public Adapter createTypeItemAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link net.w3des.extjs.core.model.basic.Alias <em>Alias</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see net.w3des.extjs.core.model.basic.Alias
     * @generated
     */
    public Adapter createAliasAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link net.w3des.extjs.core.model.basic.Widget <em>Widget</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see net.w3des.extjs.core.model.basic.Widget
     * @generated
     */
    public Adapter createWidgetAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link net.w3des.extjs.core.model.basic.Plugin <em>Plugin</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see net.w3des.extjs.core.model.basic.Plugin
     * @generated
     */
    public Adapter createPluginAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link net.w3des.extjs.core.model.basic.ExtJSProject <em>Project</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see net.w3des.extjs.core.model.basic.ExtJSProject
     * @generated
     */
    public Adapter createExtJSProjectAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link net.w3des.extjs.core.model.basic.Layout <em>Layout</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see net.w3des.extjs.core.model.basic.Layout
     * @generated
     */
    public Adapter createLayoutAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link net.w3des.extjs.core.model.basic.Feature <em>Feature</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see net.w3des.extjs.core.model.basic.Feature
     * @generated
     */
    public Adapter createFeatureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link net.w3des.extjs.core.model.basic.File <em>File</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see net.w3des.extjs.core.model.basic.File
     * @generated
     */
    public Adapter createFileAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link net.w3des.extjs.core.model.basic.Event <em>Event</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see net.w3des.extjs.core.model.basic.Event
     * @generated
     */
    public Adapter createEventAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link net.w3des.extjs.core.model.basic.Parameter <em>Parameter</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see net.w3des.extjs.core.model.basic.Parameter
     * @generated
     */
    public Adapter createParameterAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //ExtJSAdapterFactory
