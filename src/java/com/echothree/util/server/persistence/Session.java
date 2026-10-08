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

import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.valuecache.ValueCache;
import com.echothree.util.server.persistence.valuecache.ValueCacheProviderImpl;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.CDI;
import org.jooq.DSLContext;
import org.jooq.Select;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class Session {
    
    private static final String GET_INSTANCE = "getInstance";
    private static final String GET_ENTITY_TYPE_NAME = "getEntityTypeName";
    
    private static final Map<Class<? extends BaseFactory<? extends BasePK, ? extends BaseEntity>>, String> entityNameCache = new ConcurrentHashMap<>();

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private DSLContext dslContext;
    private Connection connection;

    private ValueCache valueCache = ValueCacheProviderImpl.getInstance().getValueCache();
    private SessionEntityCache sessionEntityCache = new SessionEntityCache(this);

    private final Map<EntityInstancePK, Integer> eventTimeSequences = new HashMap<>();

    private Map<String, PreparedStatement> preparedStatementCache;
    
    private MimeType preferredClobMimeType;
    private Set<String> options;
    private TransferProperties transferProperties;
    private Map<String, Limit> limits;
    
    public static final long MAX_TIME = Long.MAX_VALUE;
    private final long START_TIME;

    public long getStartTime() {
        return START_TIME;
    }

    /**
     * Creates a new instance of Session
     */
    public Session() {
        START_TIME = System.currentTimeMillis();
    }

    @PostConstruct
    public void init() {
        if(PersistenceDebugFlags.LogSessions) {
            log.info("Session()");
        }

        dslContext = DslContextFactory.getInstance().getDslContext();
        connection = dslContext.parsingConnection();

        if(PersistenceDebugFlags.LogConnections) {
            log.info("new connection is " + connection);
        }
    }

    public Integer getNextEventTimeSequence(final EntityInstancePK entityInstancePK) {
        var value = eventTimeSequences.get(entityInstancePK);

        if(value == null) {
            value = 1;
        } else {
            value++;
        }

        eventTimeSequences.put(entityInstancePK, value);

        return value;
    }

    public ValueCache getValueCache() {
        return valueCache;
    }

    public void pushSessionEntityCache() {
        sessionEntityCache = new SessionEntityCache(sessionEntityCache);
    }

    public void popSessionEntityCache() {
        sessionEntityCache = sessionEntityCache.popSessionEntityCache();
    }

    public DSLContext getDslContext() {
        return dslContext;
    }

    public Connection getConnection() {
        return connection;
    }
    
    public static <T extends BaseModelControl> T getModelController(Class<T> modelController) {
        return ThreadSession.currentSession().getSessionModelController(modelController);
    }
    
    public <T extends BaseModelControl> T getSessionModelController(Class<T> modelController) {
        return CDI.current().select(modelController).get();
    }
    
    private String getEntityTypeNameFromFactory(final Class<? extends BaseFactory<? extends BasePK, ? extends BaseEntity>> entityFactory) {
        var result = Session.entityNameCache.get(entityFactory);
        
        if(result == null) {
            try {
                var entityInstance = entityFactory.getDeclaredMethod(GET_INSTANCE).invoke(entityFactory);

                if(entityInstance != null) {
                    result = (String)entityFactory.getDeclaredMethod(Session.GET_ENTITY_TYPE_NAME).invoke(entityInstance);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            
            Session.entityNameCache.put(entityFactory, result);
        }
        
        return result;
    }

    public boolean hasLimits() {
        return limits != null;
    }

    public boolean hasLimit(final String entityName) {
        return hasLimits() && limits.get(entityName) != null;
    }
    
    public boolean hasLimit(final Class<? extends BaseFactory<? extends BasePK, ? extends BaseEntity>> entityFactory) {
        return hasLimits() && limits.get(getEntityTypeNameFromFactory(entityFactory)) != null;
    }
    
    public void copyLimit(final String sourceEntityName, final String destinationEntityName) {
        if(hasLimits()) {
            var limit = limits.get(sourceEntityName);
            
            if(limit != null) {
                limits.put(destinationEntityName, limit);
            }
        }
    }
    
    public Select<?> applyLimit(final Select<?> query,
            final Class<? extends BaseFactory<? extends BasePK, ? extends BaseEntity>> entityFactory) {
        var result = query;

        if(hasLimits()) {
            var limit = limits.get(getEntityTypeNameFromFactory(entityFactory));

            if(limit != null && limit.getCount() != null) {
                var count = Long.valueOf(limit.getCount());
                var rawOffset = limit.getOffset();

                // The QOM methods preserve the completed Select<?> type while adding
                // optional pagination. Keep this isolated here because the API is experimental.
                result = result.$limit(DSL.val(count));

                if(rawOffset != null) {
                    result = result.$offset(DSL.val(Long.valueOf(rawOffset)));
                }
            }
        }

        return result;
    }

    /**
     * Creates a <code>PreparedStatement</code> object for sending
     * parameterized SQL statements to the database.
     * @param sql SQL statement to use for the PreparedStatement
     * @return Returns a PreparedStatement
     * @throws PersistenceDatabaseException Thrown if the PreparedStatement was unable to be created
     */
    public PreparedStatement prepareStatement(final String sql) {
        PreparedStatement preparedStatement = null;

        if(sql != null) {
            // Attempt to get a PreparedStatement from preparedStatementCache...
            if(preparedStatementCache == null) {
                preparedStatementCache = new HashMap<>();
            } else {
                preparedStatement = preparedStatementCache.get(sql);
            }

            if(preparedStatement == null) {
                // If it hasn't been cached before, go ahead and cache it for future use...
                try {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatementCache.put(sql, preparedStatement);
                } catch(SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } else {
                // Cached PreparedStatement was found, call clearParameters() to clean out any previous usage of it.
                // Clearing of batch parameters happens after executing of each batch.
                try {
                    preparedStatement.clearParameters();
                } catch(SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            }
        }

        return preparedStatement;
    }
    
    public static void setQueryParams(final PreparedStatement ps, final Object... params) {
        try {
            for(var param = 0; param < params.length; param++) {
                switch(params[param]) {
                    case BaseEntity baseEntity -> ps.setLong(param + 1, baseEntity.getPrimaryKey().getEntityId());
                    case BasePK basePK -> ps.setLong(param + 1, basePK.getEntityId());
                    case Long l -> ps.setLong(param + 1, l);
                    case Integer i -> ps.setInt(param + 1, i);
                    case String s -> ps.setString(param + 1, s);
                    case byte[] bytes -> ps.setBytes(param + 1, bytes);
                    case Boolean b -> ps.setBoolean(param + 1, b);
                    case null -> throw new PersistenceDatabaseException("null Object in setQueryParams, index = " + param);
                    default -> throw new PersistenceDatabaseException("unsupported Object in setQueryParams, " + params[param].getClass().getCanonicalName() + ", index = " + param);
                }
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
    }
    
    public void query(final String sql, final Object... params) {
        try {
            var ps = prepareStatement(sql);

            setQueryParams(ps, params);

            ps.execute();
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
    }
    
    private void freePreparedStatementCache() {
        var preparedStatements = preparedStatementCache.values();

        preparedStatements.forEach((preparedStatement) -> {
            try {
                preparedStatement.close();
            } catch (SQLException se) {
                // not much to do to recover from this problem, connection is closing soon.
                throw new PersistenceDatabaseException(se);
            }
        });
        
        preparedStatementCache = null;
    }

    @SuppressWarnings("Finally")
    @PreDestroy
    public void close() {
        if(PersistenceDebugFlags.LogSessions) {
            log.info("close()");
        }

        if(connection != null) {
            if(PersistenceDebugFlags.LogConnections) {
                log.info("closing connection " + connection);
            }
            
            try {
                if(PersistenceDebugFlags.LogConnections) {
                    log.info("flushing entities for " + connection);
                }

                sessionEntityCache = sessionEntityCache.popLastSessionEntityCache();

                if(PersistenceDebugFlags.LogValueCaches) {
                    log.info("discarding valueCache " + valueCache);
                }

                if(valueCache != null) {
                    valueCache = null;
                }
                
                if(PersistenceDebugFlags.LogConnections) {
                    log.info("freeing prepared statement cache " + connection);
                }

                if(preparedStatementCache != null) {
                    freePreparedStatementCache();
                }
            } finally {
                try {
                    if(PersistenceDebugFlags.LogConnections) {
                        log.info("closing connection " + connection);
                    }

                    connection.close();
                    connection = null;
                    dslContext = null;
                } catch(SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            }
        }
    }

    public void putReadOnlyEntity(BasePK basePK, BaseEntity baseEntity) {
        sessionEntityCache.putReadOnlyEntity(basePK, baseEntity);
    }
    
    public void putReadWriteEntity(BasePK basePK, BaseEntity baseEntity) {
        sessionEntityCache.putReadWriteEntity(basePK, baseEntity);
    }
    
    public BaseEntity getEntity(BasePK basePK) {
        return sessionEntityCache.getEntity(basePK);
    }
    
    public void removed(BasePK basePK, boolean missingPermitted) {
        sessionEntityCache.removed(basePK, missingPermitted);
    }
    
    public void setPreferredClobMimeType(MimeType preferredClobMimeType) {
        this.preferredClobMimeType = preferredClobMimeType;
    }
    
    public MimeType getPreferredClobMimeType() {
        return preferredClobMimeType;
    }
    
    public void setOptions(Set<String> options) {
        this.options = options;
    }
    
    public Set<String> getOptions() {
        if(options == null) {
            options = new HashSet<>();
        }
        
        return options;
    }

    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }
    
    public TransferProperties getTransferProperties() {
        return transferProperties;
    }
    
    public void setLimits(Map<String, Limit> limits) {
        this.limits = limits;
    }
    
    public Map<String, Limit> getLimits() {
        if(limits == null) {
            limits = new HashMap<>();
        }

        return limits;
    }
    
}
