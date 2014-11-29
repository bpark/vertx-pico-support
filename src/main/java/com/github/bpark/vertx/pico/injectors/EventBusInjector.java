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
package com.github.bpark.vertx.pico.injectors;

import org.picocontainer.PicoCompositionException;
import org.picocontainer.PicoContainer;
import org.picocontainer.injectors.FactoryInjector;
import org.vertx.java.core.eventbus.EventBus;

import java.lang.reflect.Type;

/**
 * @author bpark
 */
public class EventBusInjector extends FactoryInjector<EventBus> {

    private EventBus vertxEventBus;

    public EventBusInjector(EventBus vertxEventBus) throws PicoCompositionException {
        this.vertxEventBus = vertxEventBus;
    }

    public EventBus getComponentInstance(PicoContainer container, final Type into) throws PicoCompositionException {
        return vertxEventBus;
    }
}
