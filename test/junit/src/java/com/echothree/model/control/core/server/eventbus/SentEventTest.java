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

import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SentEventTest {

    private static class State {}

    @Test
    public void queuedFollowOnEventsRetainStateAfterTheReceiverReturns() {
        var root = new SentEvent(null);
        var state = root.getPropagationState(State.class, State::new);
        var followOn = new SentEvent[1];

        root.applyWithPropagationContext(() -> followOn[0] = new SentEvent(null));

        assertSame(state, followOn[0].getPropagationState(State.class, State::new));
        assertNotSame(state, new SentEvent(null).getPropagationState(State.class, State::new));
    }

    @Test
    public void nestedContextsRestoreThePreviousStateEvenAfterExceptions() {
        var outer = new SentEvent(null);
        var inner = new SentEvent(null);
        var outerState = outer.getPropagationState(State.class, State::new);
        var innerState = inner.getPropagationState(State.class, State::new);

        assertThrows(IllegalStateException.class, () -> outer.applyWithPropagationContext(() -> {
            assertSame(outerState, new SentEvent(null).getPropagationState(State.class, State::new));
            assertThrows(IllegalArgumentException.class, () -> inner.applyWithPropagationContext(() -> {
                assertSame(innerState, new SentEvent(null).getPropagationState(State.class, State::new));
                throw new IllegalArgumentException("Inner receiver failed");
            }));
            assertSame(outerState, new SentEvent(null).getPropagationState(State.class, State::new));
            throw new IllegalStateException("Outer receiver failed");
        }));

        var independentState = new SentEvent(null).getPropagationState(State.class, State::new);
        assertNotSame(outerState, independentState);
        assertNotSame(innerState, independentState);
    }

    @Test
    public void independentThreadsDoNotInheritAnActivePropagationContext() {
        var root = new SentEvent(null);
        var state = root.getPropagationState(State.class, State::new);

        root.applyWithPropagationContext(() -> {
            var otherState = CompletableFuture.supplyAsync(() -> new SentEvent(null).getPropagationState(State.class, State::new)).join();
            assertNotSame(state, otherState);
            assertSame(state, new SentEvent(null).getPropagationState(State.class, State::new));
        });
    }

}
