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

package com.echothree.util.server.persistence.mutex;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import static org.junit.jupiter.api.Assertions.*;

@Isolated("The session fixture temporarily replaces the global value-cache provider")
public class DatabasePostgresMutexTest {

    @SuppressWarnings("UnnecessaryAssignment") // Supply the CDI injection explicitly in this unit test.
    private DatabasePostgresMutex mutexFor(final MockDataProvider provider) {
        var ctx = DSL.using(new MockConnection(provider), SQLDialect.POSTGRES);
        var mutex = new DatabasePostgresMutex();
        mutex.session = DatabaseMutexTestSupport.sessionFor(ctx);
        return mutex;
    }

    private MockResult[] result(final Boolean value) {
        var ctx = DSL.using(SQLDialect.POSTGRES);
        var field = DSL.field("result", Boolean.class);
        var records = ctx.newResult(field);
        records.add(ctx.newRecord(field).value1(value));
        return new MockResult[] {new MockResult(1, records)};
    }

    @Test
    public void hashesUtf8NamesConsistentlyForAcquisitionAndRelease() {
        var calls = new ArrayList<String>();
        var mutex = mutexFor(context -> {
            calls.add(context.sql());
            assertArrayEquals(new Object[] {-7311685953035192437L}, context.bindings());
            return result(true);
        });

        assertTrue(mutex.acquireLock("mutex'雪", 0));
        assertTrue(mutex.releaseLock("mutex'雪"));
        assertEquals(List.of("select pg_try_advisory_lock(?)", "select pg_advisory_unlock(?)"), calls);
    }

    @Test
    public void doesNotReuseJavaStringHashCollisions() {
        assertEquals("Aa".hashCode(), "BB".hashCode());
        var keys = new ArrayList<Object>();
        var mutex = mutexFor(context -> {
            keys.add(context.bindings()[0]);
            return result(true);
        });

        assertTrue(mutex.acquireLock("Aa", 0));
        assertTrue(mutex.acquireLock("BB", 0));
        assertNotEquals(keys.get(0), keys.get(1));
    }

    @Test
    public void zeroTimeoutAndNullResultsDoNotRetry() {
        for(Boolean value : new Boolean[] {false, null}) {
            var calls = new AtomicInteger();
            var mutex = mutexFor(context -> {
                calls.incrementAndGet();
                return result(value);
            });

            assertFalse(mutex.acquireLock("mutex", 0));
            assertEquals(1, calls.get());
        }
    }

    @Test
    public void retriesUntilAcquiredForFiniteAndInfiniteTimeouts() {
        for(int timeout : new int[] {1, -1}) {
            var calls = new AtomicInteger();
            var mutex = mutexFor(context -> result(calls.incrementAndGet() == 2));

            assertTrue(mutex.acquireLock("mutex", timeout));
            assertEquals(2, calls.get());
        }
    }

    @Test
    public void stopsRetryingAtTimeout() {
        var calls = new AtomicInteger();
        var mutex = mutexFor(context -> {
            calls.incrementAndGet();
            return result(false);
        });

        assertFalse(mutex.acquireLock("mutex", 1));
        assertTrue(calls.get() > 1);
    }

    @Test
    public void interruptionStopsWaitingAndPreservesFlag() {
        var calls = new AtomicInteger();
        var mutex = mutexFor(context -> {
            calls.incrementAndGet();
            Thread.currentThread().interrupt();
            return result(false);
        });

        try {
            assertFalse(mutex.acquireLock("mutex", -1));
            assertTrue(Thread.currentThread().isInterrupted());
            assertEquals(1, calls.get());
        } finally {
            Thread.interrupted();
        }
    }

    @Test
    public void alreadyInterruptedCallerDoesNotQuery() {
        var calls = new AtomicInteger();
        var mutex = mutexFor(context -> {
            calls.incrementAndGet();
            return result(true);
        });

        Thread.currentThread().interrupt();
        try {
            assertFalse(mutex.acquireLock("mutex"));
            assertTrue(Thread.currentThread().isInterrupted());
            assertEquals(0, calls.get());
        } finally {
            Thread.interrupted();
        }
    }

    @Test
    public void nullResultStopsInfiniteWait() {
        var calls = new AtomicInteger();
        var mutex = mutexFor(context -> {
            calls.incrementAndGet();
            return result(null);
        });

        assertFalse(mutex.acquireLock("mutex", -1));
        assertEquals(1, calls.get());
    }

    @Test
    public void releaseReturnsTrueOnlyOnConfirmedSuccess() {
        for(Boolean value : new Boolean[] {true, false, null}) {
            var mutex = mutexFor(context -> {
                assertEquals("select pg_advisory_unlock(?)", context.sql());
                return result(value);
            });

            assertEquals(Boolean.TRUE.equals(value), mutex.releaseLock("mutex"));
        }
    }

    @Test
    public void sqlErrorsPropagate() {
        var mutex = mutexFor(context -> {
            throw new SQLException("lock failed");
        });

        assertThrows(DataAccessException.class, () -> mutex.acquireLock("mutex", 0));
        assertThrows(DataAccessException.class, () -> mutex.releaseLock("mutex"));
    }

}
