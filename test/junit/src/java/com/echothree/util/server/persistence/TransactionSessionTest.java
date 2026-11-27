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

package com.echothree.util.server.persistence;

import com.echothree.model.data.party.common.pk.PartyDetailPK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.factory.PartyFactory;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScopeContext;
import com.echothree.util.server.cdi.CommandScopeExtension;
import com.echothree.util.server.control.BaseCommand;
import com.echothree.util.server.persistence.valuecache.ValueCache;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import java.lang.reflect.Field;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import org.infinispan.AdvancedCache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.transaction.TransactionMode;
import org.infinispan.transaction.lookup.EmbeddedTransactionManagerLookup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/** Real JTA/cache rollback with an enlisted XA resource standing in for database rows. */
@ResourceLock("CommandScopeContext")
@ResourceLock("EmbeddedTransactionManager")
public class TransactionSessionTest {

    private static DefaultCacheManager cacheManager;
    private static AdvancedCache<String, BaseValue> cache;
    private static TransactionManager transactionManager;

    private final Map<PartyPK, PartyDetailPK> committedRows = new HashMap<>();
    private final IdentityHashMap<Transaction, RowsResource> rowTransactions = new IdentityHashMap<>();
    private final List<Transaction> cacheWriteTransactions = new ArrayList<>();
    private final List<Session> createdSessions = new ArrayList<>();
    private final Registry registry = new Registry();
    private CommandScopeContext previousCommandContext;
    private Field commandContextField;
    private int closeCount;
    private int storeCount;
    private boolean failStore;

    private final TransactionSessionProvider provider = new TransactionSessionProvider() {
        { transactionRegistry = registry; }

        @Override
        protected Session createSession() {
            var session = new Session(new ValueCache() {
                @Override
                public void put(BaseValue value) {
                    cacheWriteTransactions.add(transaction());
                    assertNotNull(transaction(), "Cache writes must remain inside the transaction");
                    cache.put(value.getPrimaryKey().getEntityRef(), ((PartyValue)value).clone());
                }

                @Override
                public BaseValue get(BasePK primaryKey) {
                    return cache.get(primaryKey.getEntityRef());
                }

                @Override
                public void remove(BasePK primaryKey) {
                    cache.remove(primaryKey.getEntityRef());
                }
            }) {
                @Override
                public void close() {
                    closeCount++;
                    super.close();
                }
            };
            createdSessions.add(session);
            return session;
        }
    };

    @BeforeAll
    public static void startCache() {
        var configuration = new ConfigurationBuilder();
        configuration.transaction().transactionMode(TransactionMode.TRANSACTIONAL)
                .transactionManagerLookup(new EmbeddedTransactionManagerLookup()).useSynchronization(true);
        // Ant puts the entire server module tree on one flat classpath. Server-only
        // clustering service providers cannot be bootstrapped by an embedded manager.
        var originalClassLoader = Thread.currentThread().getContextClassLoader();
        var embeddedClassLoader = new ClassLoader(originalClassLoader) {
            @Override
            public Enumeration<URL> getResources(String name) throws IOException {
                var resources = Collections.list(super.getResources(name));
                if(name.startsWith("META-INF/services/")) {
                    resources.removeIf(resource -> resource.toExternalForm().contains("wildfly-clustering"));
                }
                return Collections.enumeration(resources);
            }
        };
        var global = new GlobalConfigurationBuilder().classLoader(embeddedClassLoader);
        try {
            Thread.currentThread().setContextClassLoader(embeddedClassLoader);
            cacheManager = new DefaultCacheManager(global.build());
            cacheManager.defineConfiguration("session-lifecycle-test", configuration.build());
            cache = cacheManager.<String, BaseValue>getCache("session-lifecycle-test").getAdvancedCache();
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
        transactionManager = cache.getTransactionManager();
    }

    @AfterAll
    public static void stopCache() {
        if(cacheManager != null) {
            cacheManager.stop();
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        cache.clear();
        commandContextField = CommandScopeExtension.class.getDeclaredField("commandScopeContext");
        commandContextField.setAccessible(true);
        previousCommandContext = (CommandScopeContext)commandContextField.get(null);
        commandContextField.set(null, new CommandScopeContext());
    }

    @AfterEach
    public void tearDown() throws Exception {
        try {
            if(transactionManager.getTransaction() != null) {
                transactionManager.rollback();
            }
            assertFalse(CommandScopeExtension.getCommandScopeContext().isActive());
        } finally {
            commandContextField.set(null, previousCommandContext);
        }
    }

    private Transaction transaction() {
        try {
            return transactionManager.getTransaction();
        } catch(Exception exception) {
            throw new AssertionError(exception);
        }
    }

    private class Registry implements TransactionSynchronizationRegistry {
        private final IdentityHashMap<Transaction, Map<Object, Object>> resources = new IdentityHashMap<>();

        @Override public Object getTransactionKey() { return transaction(); }
        @Override public Object getResource(Object key) { return resources.computeIfAbsent(transaction(), ignored -> new HashMap<>()).get(key); }
        @Override public void putResource(Object key, Object value) { resources.computeIfAbsent(transaction(), ignored -> new HashMap<>()).put(key, value); }
        @Override public int getTransactionStatus() {
            try { return transactionManager.getStatus(); } catch(Exception exception) { throw new AssertionError(exception); }
        }
        @Override public void setRollbackOnly() {
            try { transactionManager.setRollbackOnly(); } catch(Exception exception) { throw new AssertionError(exception); }
        }
        @Override public boolean getRollbackOnly() { return getTransactionStatus() == Status.STATUS_MARKED_ROLLBACK; }
        @Override public void registerInterposedSynchronization(Synchronization synchronization) {
            try { transaction().registerSynchronization(synchronization); } catch(Exception exception) { throw new AssertionError(exception); }
        }
    }

    private class RowsResource implements XAResource {
        private final Map<PartyPK, PartyDetailPK> rows = new HashMap<>(committedRows);

        @Override public void commit(Xid transactionId, boolean onePhase) { committedRows.clear(); committedRows.putAll(rows); }
        @Override public void rollback(Xid transactionId) { }
        @Override public int prepare(Xid transactionId) { return XA_OK; }
        @Override public void start(Xid transactionId, int flags) { }
        @Override public void end(Xid transactionId, int flags) { }
        @Override public void forget(Xid transactionId) { }
        @Override public Xid[] recover(int flags) { return new Xid[0]; }
        @Override public boolean isSameRM(XAResource other) { return this == other; }
        @Override public int getTransactionTimeout() { return 0; }
        @Override public boolean setTransactionTimeout(int seconds) { return true; }
    }

    private void writeRow(PartyPK primaryKey, PartyDetailPK lastDetail) {
        var resource = rowTransactions.get(transaction());
        if(resource == null) {
            resource = new RowsResource();
            rowTransactions.put(transaction(), resource);
            try {
                assertTrue(transaction().enlistResource(resource));
            } catch(Exception exception) {
                throw new AssertionError(exception);
            }
        }
        resource.rows.put(primaryKey, lastDetail);
    }

    private Party createParty(long partyId) {
        var session = provider.getSession();
        var primaryKey = new PartyPK(partyId);
        writeRow(primaryKey, null);
        session.getValueCache().put(new PartyValue(primaryKey, null, null));
        var factory = new PartyFactory() {
            @Override
            public void store(Collection<Party> parties) {
                storeCount++;
                if(failStore) {
                    throw new IllegalStateException("Persistence failed");
                }
                for(var party : parties) {
                    writeRow(party.getPrimaryKey(), party.getLastDetailPK());
                    session.getValueCache().put(party.getPartyValue());
                    party.getPartyValue().clearHasBeenModified();
                }
            }
        };
        var party = new Party(new PartyValue(primaryKey, null, null), EntityPermission.READ_WRITE) {
            @Override public PartyFactory getBaseFactoryInstance() { return factory; }
        };
        session.putReadWriteEntity(primaryKey, party);
        party.setActiveDetailPK(new PartyDetailPK(partyId));
        party.setLastDetailPK(new PartyDetailPK(partyId));
        return party;
    }

    private class Command extends BaseCommand {
        private final Runnable action;

        Command(Runnable action) { super(null); this.action = action; setUpdateLastCommandTime(false); }
        @Override protected void setupSession() { session = provider.getSession(); }
        @Override public UserVisit getUserVisit() { return null; }
        @Override protected SecurityResult security() { return null; }
        @Override protected ValidationResult validate() { return null; }
        @Override protected BaseResult execute() { action.run(); return null; }
    }

    @Test
    public void nestedCommandsShareEntitiesAndFlushWithoutCommitting() throws Exception {
        transactionManager.begin();
        var owner = transaction();
        new Command(() -> {
            var session = provider.getSession();
            var party = createParty(1L);
            new Command(() -> {
                assertSame(session, provider.getSession());
                assertSame(party, provider.getSession().getEntity(party.getPrimaryKey()));
                createParty(2L);
            }).run(null);
            assertEquals(0, closeCount);
            assertTrue(committedRows.isEmpty(), "Child completion must not commit database work");
            assertSame(party, session.getEntity(party.getPrimaryKey()), "Flushing must preserve outer entity references");
            party.setLastDetailPK(new PartyDetailPK(3L));
        }).run(null);
        assertEquals(1, createdSessions.size());
        assertTrue(cacheWriteTransactions.stream().allMatch(owner::equals));
        assertEquals(0, closeCount);
        assertTrue(committedRows.isEmpty());
        transactionManager.commit();
        assertEquals(new PartyDetailPK(3L), committedRows.get(new PartyPK(1L)));
        assertEquals(new PartyDetailPK(2L), committedRows.get(new PartyPK(2L)));
        assertEquals(new PartyDetailPK(3L), ((PartyValue)cache.get(new PartyPK(1L).getEntityRef())).getLastDetailPK());
        assertEquals(1, closeCount);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void outerExtremeFailureRollsBackAlreadyFlushedChildren(boolean errorInsteadOfException) throws Exception {
        transactionManager.begin();
        var failure = errorInsteadOfException ? new AssertionError("Outer failure") : new IllegalStateException("Outer failure");
        var caught = assertThrows(Throwable.class, () -> new Command(() -> {
            createParty(1L);
            new Command(() -> createParty(2L)).run(null);
            if(failure instanceof Error error) {
                throw error;
            }
            throw (RuntimeException)failure;
        }).run(null));
        assertSame(failure, caught);
        assertTrue(storeCount > 0, "The nested command must have flushed before the failure");
        assertEquals(Status.STATUS_MARKED_ROLLBACK, registry.getTransactionStatus());
        transactionManager.rollback();
        assertTrue(committedRows.isEmpty());
        assertTrue(cache.isEmpty());
        assertEquals(1, closeCount);
    }

    @Test
    public void graphQlConvertingChildFailureToAnErrorStillPreventsOuterCommit() throws Exception {
        transactionManager.begin();
        new Command(() -> {
            createParty(1L);
            var query = GraphQLObjectType.newObject().name("Query")
                    .field(field -> field.name("value").type(Scalars.GraphQLString).dataFetcher(environment -> "ok"))
                    .build();
            var mutation = GraphQLObjectType.newObject().name("Mutation")
                    .field(field -> field.name("nested").type(Scalars.GraphQLString).dataFetcher(environment -> {
                        new Command(() -> {
                            createParty(2L);
                            throw new IllegalStateException("Child failure converted to a GraphQL error");
                        }).run(null);
                        return "unreachable";
                    }))
                    .build();
            var graphQl = GraphQL.newGraphQL(GraphQLSchema.newSchema().query(query).mutation(mutation).build()).build();
            var response = graphQl.execute("mutation { nested }");
            assertEquals(1, response.getErrors().size(), "GraphQL must catch the child exception and return an error");
        }).run(null);
        assertEquals(Status.STATUS_MARKED_ROLLBACK, registry.getTransactionStatus());
        assertThrows(RollbackException.class, transactionManager::commit);
        assertTrue(committedRows.isEmpty());
        assertTrue(cache.isEmpty());
        assertEquals(0, storeCount, "A rollback-only session must not flush at command exit or cleanup");
        assertEquals(1, closeCount);
    }

    @Test
    public void flushFailurePropagatesAndCleanupDoesNotRetryWrites() throws Exception {
        transactionManager.begin();
        failStore = true;
        assertThrows(IllegalStateException.class, () -> new Command(() -> createParty(1L)).run(null));
        assertEquals(Status.STATUS_MARKED_ROLLBACK, registry.getTransactionStatus());
        assertEquals(1, storeCount);
        transactionManager.rollback();
        assertEquals(1, storeCount);
        assertTrue(committedRows.isEmpty());
        assertTrue(cache.isEmpty());
    }

    @Test
    public void unflushedWorkCannotCommitThroughCleanup() throws Exception {
        transactionManager.begin();
        createParty(1L); // Deliberately bypass command completion.
        assertThrows(RollbackException.class, transactionManager::commit);
        assertTrue(committedRows.isEmpty());
        assertTrue(cache.isEmpty());
        assertEquals(0, storeCount);
        assertEquals(1, closeCount);
    }

    @Test
    public void rollbackPreservesPreviouslyCommittedDatabaseAndCacheValues() throws Exception {
        transactionManager.begin();
        new Command(() -> createParty(1L)).run(null);
        transactionManager.commit();

        transactionManager.begin();
        assertThrows(IllegalStateException.class, () -> new Command(() -> {
            var party = createParty(1L);
            new Command(() -> party.setLastDetailPK(new PartyDetailPK(99L))).run(null);
            throw new IllegalStateException("Abort after updating an existing party");
        }).run(null));
        transactionManager.rollback();

        assertEquals(new PartyDetailPK(1L), committedRows.get(new PartyPK(1L)));
        assertEquals(new PartyDetailPK(1L), ((PartyValue)cache.get(new PartyPK(1L).getEntityRef())).getLastDetailPK());
        assertEquals(2, closeCount);
    }

    @Test
    public void indexingCacheLayersCanFlushAndPopWithoutDetachingTheParent() throws Exception {
        transactionManager.begin();
        new Command(() -> {
            var session = provider.getSession();
            var parentParty = createParty(1L);
            session.pushSessionEntityCache();
            createParty(2L);
            session.flush();
            session.popSessionEntityCache();
            assertSame(parentParty, session.getEntity(parentParty.getPrimaryKey()));
            parentParty.setLastDetailPK(new PartyDetailPK(3L));
        }).run(null);
        transactionManager.commit();
        assertEquals(new PartyDetailPK(3L), committedRows.get(new PartyPK(1L)));
        assertEquals(new PartyDetailPK(2L), committedRows.get(new PartyPK(2L)));
        assertEquals(1, closeCount);
    }

    @Test
    public void suspendedTransactionsHaveSeparateSessionsAndRestoreTheParent() throws Exception {
        transactionManager.begin();
        var outerSession = provider.getSession();
        new Command(() -> createParty(1L)).run(null);
        var suspended = transactionManager.suspend();
        transactionManager.begin();
        var innerSession = provider.getSession();
        assertNotSame(outerSession, innerSession);
        assertThrows(IllegalStateException.class, outerSession::flush);
        new Command(() -> createParty(2L)).run(null);
        transactionManager.commit();
        assertEquals(1, closeCount);
        transactionManager.resume(suspended);
        assertSame(outerSession, provider.getSession());
        transactionManager.rollback();
        assertFalse(committedRows.containsKey(new PartyPK(1L)));
        assertEquals(new PartyDetailPK(2L), committedRows.get(new PartyPK(2L)));
        assertNull(cache.get(new PartyPK(1L).getEntityRef()));
        assertNotNull(cache.get(new PartyPK(2L).getEntityRef()));
        assertEquals(2, closeCount);
    }

    @Test
    public void sessionsCannotBeCreatedOutsideTransactionsOrReusedAfterCompletion() throws Exception {
        assertThrows(IllegalStateException.class, provider::getSession);
        transactionManager.begin();
        var first = provider.getSession();
        transactionManager.commit();
        assertThrows(IllegalStateException.class, first::flush);
        transactionManager.begin();
        assertNotSame(first, provider.getSession());
        transactionManager.commit();
        assertEquals(2, closeCount);
    }

}
