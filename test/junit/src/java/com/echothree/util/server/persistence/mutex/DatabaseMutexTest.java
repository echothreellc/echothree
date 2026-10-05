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

import java.sql.Connection;
import java.sql.SQLException;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import static org.junit.jupiter.api.Assertions.*;

@Isolated("The session fixture temporarily replaces the global value-cache provider")
public class DatabaseMutexTest {

    @SuppressWarnings("UnnecessaryAssignment") // Supply the CDI injection explicitly in this unit test.
    private DatabaseMySqlMutex mutexFor(final Connection connection) {
        var mutex = new DatabaseMySqlMutex();
        mutex.session = DatabaseMutexTestSupport.sessionFor(DSL.using(connection, SQLDialect.MYSQL));
        return mutex;
    }

    @Test
    public void acquisitionResultsAndBoundParameters() {
        var lockName = "mutex'quoted";

        for(Integer value : new Integer[] {1, 0, null}) {
            var connection = new MockConnection(context -> {
                assertEquals("select GET_LOCK(?, ?)", context.sql());
                assertArrayEquals(new Object[] {lockName, 7}, context.bindings());
                var field = DSL.field("result", Integer.class);
                var result = DSL.using(SQLDialect.MYSQL).newResult(field);
                result.add(DSL.using(SQLDialect.MYSQL).newRecord(field).value1(value));
                return new MockResult[] {new MockResult(1, result)};
            });

            assertEquals(Integer.valueOf(1).equals(value),
                    mutexFor(connection).acquireLock(lockName, 7));
        }
    }

    @Test
    public void defaultTimeoutIsFifteenSeconds() {
        var connection = new MockConnection(context -> {
            assertArrayEquals(new Object[] {"mutex", 15}, context.bindings());
            var ctx = DSL.using(SQLDialect.MYSQL);
            var field = DSL.field("result", Integer.class);
            var result = ctx.newResult(field);
            result.add(ctx.newRecord(field).value1(1));
            return new MockResult[] {new MockResult(1, result)};
        });

        assertTrue(mutexFor(connection).acquireLock("mutex"));
    }

    @Test
    @SuppressWarnings("UnnecessaryAssignment") // Supply the CDI injection explicitly in this unit test.
    public void derivesSupportedFlavorsAndRejectsUnsupportedDialects() {
        var producer = new DatabaseFlavorProducer();
        for(var dialect : new SQLDialect[] {SQLDialect.MYSQL, SQLDialect.MARIADB, SQLDialect.POSTGRES}) {
            producer.session = DatabaseMutexTestSupport.sessionFor(DSL.using(dialect));
            assertEquals(dialect == SQLDialect.POSTGRES ? DatabaseFlavor.Flavor.POSTGRES : DatabaseFlavor.Flavor.MYSQL,
                    producer.deriveFlavor());
        }

        producer.session = DatabaseMutexTestSupport.sessionFor(DSL.using(SQLDialect.H2));
        assertThrows(IllegalStateException.class, producer::deriveFlavor);
    }

    @Test
    public void releaseResultsAndBoundName() {
        for(Integer value : new Integer[] {1, 0, null}) {
            var calls = new java.util.ArrayList<String>();
            var connection = new MockConnection(context -> {
                calls.add(context.sql());
                assertArrayEquals(new Object[] {"mutex'quoted"}, context.bindings());
                var field = DSL.field("result", Integer.class);
                var result = DSL.using(SQLDialect.MYSQL).newResult(field);
                result.add(DSL.using(SQLDialect.MYSQL).newRecord(field).value1(value));
                return new MockResult[] {new MockResult(1, result)};
            });

            assertEquals(Integer.valueOf(1).equals(value), mutexFor(connection).releaseLock("mutex'quoted"));
            assertEquals(java.util.List.of("select RELEASE_LOCK(?)"), calls);
        }
    }

    @Test
    public void acquisitionAndReleaseLeaveConnectionOpen() throws SQLException {
        var calls = new java.util.ArrayList<String>();
        var connection = new MockConnection(context -> {
            calls.add(context.sql());
            var field = DSL.field("result", Integer.class);
            var result = DSL.using(SQLDialect.MYSQL).newResult(field);
            result.add(DSL.using(SQLDialect.MYSQL).newRecord(field).value1(1));
            return new MockResult[] {new MockResult(1, result)};
        });
        var mutex = mutexFor(connection);

        assertTrue(mutex.acquireLock("mutex", 0));
        assertTrue(mutex.releaseLock("mutex"));

        assertEquals(java.util.List.of("select GET_LOCK(?, ?)", "select RELEASE_LOCK(?)"), calls);
        assertFalse(connection.isClosed());
    }

    @Test
    public void sqlErrorsPropagate() {
        var connection = new MockConnection(context -> {
            throw new SQLException("lock failed");
        });
        var mutex = mutexFor(connection);

        assertThrows(DataAccessException.class, () -> mutex.acquireLock("mutex", 0));
        assertThrows(DataAccessException.class, () -> mutex.releaseLock("mutex"));
    }

    @Test
    public void selectsBothSupportedFlavors() {
        var producer = new DatabaseMutexProducer();
        var mutex = new DatabaseMySqlMutex();

        var postgresMutex = new DatabasePostgresMutex();

        assertSame(mutex, producer.produceMutex(DatabaseFlavor.Flavor.MYSQL, mutex, postgresMutex));
        assertSame(postgresMutex, producer.produceMutex(DatabaseFlavor.Flavor.POSTGRES, mutex, postgresMutex));
    }

}
