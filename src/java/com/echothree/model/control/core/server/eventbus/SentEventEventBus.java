// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.model.control.core.server.eventbus;

import com.google.common.eventbus.EventBus;
import io.github.classgraph.ClassGraph;
import java.lang.reflect.InvocationTargetException;

public class SentEventEventBus {

    public static EventBus eventBus;

    static {
        eventBus = new EventBus();

        try(var scanResult = new ClassGraph()
                .enableAnnotationInfo()
                .scan()) {
            final var sentEventSubscriberClasses = scanResult
                    .getClassesWithAnnotation(SentEventSubscriber.class.getName());

            for(var sentEventSubscriberClass : sentEventSubscriberClasses) {
                var clazz = sentEventSubscriberClass.loadClass();
                var constructor = clazz.getDeclaredConstructor();

                eventBus.register(constructor.newInstance());
            }
        } catch(InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

}
