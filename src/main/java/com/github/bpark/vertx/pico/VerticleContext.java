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

import org.vertx.java.core.Vertx;
import org.vertx.java.platform.Container;

/**
 * @author bpark
 */
public class VerticleContext {

    /**
     * A reference to the vert.x runtime
     */
    public Vertx vertx;

    /**
     * A reference to the vert.x container
     */
    public Container container;

    public VerticleContext(Vertx vertx, Container container) {
        this.vertx = vertx;
        this.container = container;
    }

    public Vertx getVertx() {
        return vertx;
    }

    public Container getContainer() {
        return container;
    }
}
