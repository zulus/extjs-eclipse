/*******************************************************************************
 * Copyright (c) 2013 w3des.net and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *      w3des.net - initial API and implementation
 ******************************************************************************/
package net.w3des.extjs.core.test;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class Versioned extends Suite {
    private ExtSuite cfg;
    private int count = 0;

    private final ArrayList<Runner> runners = new ArrayList<Runner>();

    /**
     * Annotation for selecting ExtJS version
     * 
     * @author Dawid zulus Pakula <zulus@w3des.net>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public static @interface ExtSuite {
        String dir();

        String version();

        String[] sub() default {};
    }

    /**
     * @author Dawid zulus Pakula <zulus@w3des.net>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface BeforeSuite {
    }

    /**
     * 
     * @author Dawid zulus Pakula <zulus@w3des.net>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface AfterSuite {
    }

    private static final List<Runner> EMPTY_RUNNERS = Collections.<Runner> emptyList();

    public Versioned(Class<?> klass) throws Throwable {
        super(klass, EMPTY_RUNNERS);
        cfg = getConfig();
        createRunners();
    }

    private void createRunners() throws Throwable {
        Iterable<Object> params = invokeBeforeSuite();

        if (params == null) {
            return;
        }

        for (Object par : params) {
            String name = cfg.version() + " :: " + par.toString() + " ";

            TestClassRunner runner = new TestClassRunner(getTestClass().getJavaClass(), par, name);
            runners.add(runner);

            count++;
        }

    }

    @SuppressWarnings("unchecked")
    private Iterable<Object> invokeBeforeSuite() throws Throwable {
        List<FrameworkMethod> annotatedMethods = getTestClass().getAnnotatedMethods(BeforeSuite.class);

        if (annotatedMethods.size() != 1) {
            throw new Exception("Only one is allowed");
        }
        FrameworkMethod method = annotatedMethods.get(0);

        Object params = null;
        if (!method.isPublic() || !method.isStatic()) {
            throw new RuntimeException("Method have to be public and static");
        }

        if (method.getMethod().getParameterTypes().length == 0) {
            params = method.invokeExplosively(null);
        } else if (method.getMethod().getParameterTypes().length == 1) {
            params = method.invokeExplosively(null, new Object[] { cfg });
        } else {
            throw new Exception("Unable to invoke method");
        }

        if (params instanceof Iterable) {
            return ((Iterable<Object>) params);
        }

        throw new IllegalArgumentException("BeforeSuite have to return Iterable");
    }

    private void invokeAfterSuite() throws Throwable {
        List<FrameworkMethod> annotatedMethods = getTestClass().getAnnotatedMethods(AfterSuite.class);

        for (FrameworkMethod method : annotatedMethods) {
            if (!method.isPublic() || !method.isStatic()) {
                throw new RuntimeException("Method have to be public and static");
            }

            if (method.getMethod().getParameterTypes().length == 0) {
                method.invokeExplosively(null);
            } else if (method.getMethod().getParameterTypes().length == 1) {
                method.invokeExplosively(null, new Object[] { cfg });
            } else {
                throw new Exception("Unable to invoke method");
            }
        }
    }

    @Override
    protected List<Runner> getChildren() {
        return runners;
    }

    private ExtSuite getConfig() {
        Annotation[] annotations = getTestClass().getAnnotations();
        for (Annotation item : annotations) {
            if (item instanceof ExtSuite) {
                return (ExtSuite) item;
            }
        }

        throw new RuntimeException("ExtSuite is required");
    }

    class TestClassRunner extends BlockJUnit4ClassRunner {
        private String name;
        private Object param;

        public TestClassRunner(Class<?> klass, Object par, String name) throws InitializationError {
            super(klass);
            this.name = name;
            this.param = par;
        }

        @Override
        protected Object createTest() throws Exception {
            return getTestClass().getOnlyConstructor().newInstance(new Object[] { param });
        }

        @Override
        protected String getName() {
            return name;
        }

        @Override
        protected String testName(FrameworkMethod method) {
            return method.getName() + ": " + getName();
        }

        @Override
        protected Statement classBlock(RunNotifier notifier) {
            notifier.addListener(new RunListener() {
                @Override
                public void testRunFinished(Result result) throws Exception {
                    // super.testRunFinished(result);
                    if (--count < 0) {
                        try {
                            invokeAfterSuite();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            return childrenInvoker(notifier);
        }

        @Override
        protected void validateConstructor(List<Throwable> errors) {
            validateOnlyOneConstructor(errors);
        }

        @Override
        protected Annotation[] getRunnerAnnotations() {
            return new Annotation[0];
        }
    }

}
