/*
 * Copyright 2014 Burt Parkers
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.bpark.vertx.pico;

import com.github.bpark.vertx.pico.injectors.*;
import com.google.common.reflect.ClassPath;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;
import org.vertx.java.core.Vertx;
import org.vertx.java.platform.Container;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author bpark
 */
public class ApplicationContext {

    private Vertx vertx;

    private Container container;

    private Set<Class<?>> classes = new HashSet<>();

    private Set<String> scans = new HashSet<>();

    private MutablePicoContainer pico;

    private Class<? extends Annotation> injectAnnotation;


    public static ApplicationContext create() {
        return new ApplicationContext();
    }

    private ApplicationContext() {
    }

    public ApplicationContext withInjectAnnotation(Class<? extends Annotation> injectAnnotation) {
        this.injectAnnotation = injectAnnotation;
        return this;
    }

    public ApplicationContext withVertx(Vertx vertx) {
        this.vertx = vertx;
        return this;
    }

    public ApplicationContext withContainer(Container container) {
        this.container = container;
        return this;
    }

    public ApplicationContext withClass(Class<?> tClass) {
        classes.add(tClass);
        return this;
    }

    public ApplicationContext withPath(String scanPath) {
        scans.add(scanPath);
        return this;
    }

    public ApplicationContext build() {
        try {
            if (injectAnnotation != null) {
                pico = new PicoBuilder().withAnnotatedFieldInjection(injectAnnotation).build();
            } else {
                pico = new PicoBuilder().withAnnotatedFieldInjection().build();
            }
            for (String scan : scans) {
                ClassPath classpath = ClassPath.from(ApplicationContext.class.getClassLoader());
                for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses(scan)) {
                    Class<?> loadedClass = classInfo.load();
                    classes.add(loadedClass);
                }
            }
            for (Class<?> aClass : classes) {
                pico.addComponent(aClass);
            }
            pico.addAdapter(new ContextInjector(vertx, container));
            pico.addAdapter(new VertxInjector(vertx));
            pico.addAdapter(new ContainerInjector(container));
            pico.addAdapter(new LoggerInjector(container.logger()));
            pico.addAdapter(new EventBusInjector(vertx.eventBus()));
            pico.addAdapter(new BusModInjector(container));
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getComponent(Class<T> tClass) {
        return pico.getComponent(tClass);
    }

}
