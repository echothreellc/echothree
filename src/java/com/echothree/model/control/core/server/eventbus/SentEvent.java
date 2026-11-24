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

import com.echothree.model.data.core.server.entity.Event;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SentEvent {

    private static final ThreadLocal<Map<Class<?>, Object>> CURRENT_PROPAGATION_STATE = new ThreadLocal<>();

    private final Event event;
    private final Map<Class<?>, Object> propagationState;

    public SentEvent(Event event) {
        this.event = event;

        var currentPropagationState = CURRENT_PROPAGATION_STATE.get();
        propagationState = currentPropagationState == null ? new HashMap<>() : currentPropagationState;
    }

    public Event getEvent() {
        return event;
    }

    <T> T getPropagationState(Class<T> stateClass, Supplier<T> stateFactory) {
        return stateClass.cast(propagationState.computeIfAbsent(stateClass, ignored -> stateFactory.get()));
    }

    // Follow-on events retain this state even when EventBus queues delivery until the receiver returns.
    void applyWithPropagationContext(Runnable action) {
        var previousPropagationState = CURRENT_PROPAGATION_STATE.get();
        CURRENT_PROPAGATION_STATE.set(propagationState);

        try {
            action.run();
        } finally {
            if(previousPropagationState == null) {
                CURRENT_PROPAGATION_STATE.remove();
            } else {
                CURRENT_PROPAGATION_STATE.set(previousPropagationState);
            }
        }
    }

}
