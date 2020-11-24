// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;

public class Session {
    
    private static final String GET_INSTANCE = "getInstance";
    private static final String GET_ALL_COLUMNS = "getAllColumns";
    private static final String GET_PK_COLUMN = "getPKColumn";
    private static final String GET_ENTITY_TYPE_NAME = "getEntityTypeName";
    
    private static final Pattern PK_FIELD_PATTERN = Pattern.compile("_PK_");
    private static final Pattern ALL_FIELDS_PATTERN = Pattern.compile("_ALL_");
    private static final Pattern LIMIT_PATTERN = Pattern.compile("_LIMIT_");
    
    private static final Map<Class, String> allColumnsCache = new ConcurrentHashMap<>();
    private static final Map<Class, String> pkColumnCache = new ConcurrentHashMap<>();
    private static final Map<Class, String> entityNameCache = new ConcurrentHashMap<>();
    
    private Log log;
    
    private DSLContext dslContext;
    private Connection connection;

    private final Map<Class, BaseModelControl> modelControllers = new HashMap<>();

    private ValueCache valueCache = ValueCacheProviderImpl.getInstance().getValueCache();
    private SessionEntityCache sessionEntityCache = new SessionEntityCache(this);

    private final Map<EntityInstancePK, Integer> eventTimeSequences = new HashMap<>();

    private Map<Class, String> limitCache;
    private Map<String, PreparedStatement> preparedStatementCache;
    
    private MimeType preferredClobMimeType;
    private Set<String> options;
    private TransferProperties transferProperties;
    private Map<String, Limit> limits;
    
    public static final long MAX_TIME = Long.MAX_VALUE;
    public static final Long MAX_TIME_LONG = Long.MAX_VALUE;
    public static final long RANGE_TIME = Long.MAX_VALUE - 1;
    public static final Long RANGE_TIME_LONG = Long.MAX_VALUE - 1;
    
    public long START_TIME;
    public Long START_TIME_LONG;
    
    /** Creates a new instance of Session */
    public Session() {
        if(PersistenceDebugFlags.LogSessions) {
            getLog().info("Session()");
        }
        
        dslContext = DslContextFactory.getInstance().getDslContext();
        connection = dslContext.parsingConnection();
        
        START_TIME = System.currentTimeMillis();
        START_TIME_LONG = START_TIME;
        
        if(PersistenceDebugFlags.LogConnections) {
            getLog().info("new connection is " + connection);
        }
    }

    public Integer getNextEventTimeSequence(final EntityInstancePK entityInstancePK) {
        Integer value = eventTimeSequences.get(entityInstancePK);

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

    final protected Log getLog() {
        if(log == null) {
            log = LogFactory.getLog(this.getClass());
        }
        
        return log;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public static <T extends BaseModelControl> T getModelController(Class<T> modelController) {
        return (T)ThreadSession.currentSession().getSessionModelController(modelController);
    }
    
    public BaseModelControl getSessionModelController(Class<? extends BaseModelControl> modelController) {
        BaseModelControl result = modelControllers.get(modelController);
        
        if(result == null) {
            try {
                result = modelController.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            
            if(result != null) {
                modelControllers.put(modelController, result);
            }
        }
        
        return result;
    }
    
    private String getStringFromBaseFactory(Class<? extends BaseFactory> entityFactory, Map<Class, String> cache, String methodName) {
        String result = cache.get(entityFactory);
        
        if(result == null) {
            try {
                Object entityInstance = entityFactory.getDeclaredMethod(GET_INSTANCE).invoke(entityFactory);

                if(entityInstance != null) {
                    result = (String)entityFactory.getDeclaredMethod(methodName).invoke(entityInstance);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            
            cache.put(entityFactory, result);
        }
        
        return result;
    }
    
    public boolean hasLimit(String entityName) {
        return limits == null? false: limits.get(entityName) != null;
    }
    
    public boolean hasLimit(Class<? extends BaseFactory> entityFactory) {
        return limits == null? false: limits.get(getStringFromBaseFactory(entityFactory, entityNameCache, GET_ENTITY_TYPE_NAME)) != null;
    }
    
    public void copyLimit(String sourceEntityName, String destinationEntityName) {
        if(limits != null) {
            Limit limit = limits.get(sourceEntityName);
            
            if(limit != null) {
                limits.put(destinationEntityName, limit);
            }
        }
    }
    
    private String getLimit(Class<? extends BaseFactory> entityFactory) {
        String result = null;
        
        if(limitCache == null) {
            if(PersistenceDebugFlags.LogLimits) {
                getLog().info("limitCache initialized");
            }

            limitCache = new HashMap<>();
        }
        
        if(limitCache.containsKey(entityFactory)) {
            if(PersistenceDebugFlags.LogLimits) {
                getLog().info("cached limit found");
            }
            
            result = limitCache.get(entityFactory);
        } else {
            if(PersistenceDebugFlags.LogLimits) {
                getLog().info("cached limit not found");
            }
            
            if(limits != null) {
                Limit limit = limits.get(getStringFromBaseFactory(entityFactory, entityNameCache, GET_ENTITY_TYPE_NAME));
                
                if(limit != null) {
                    String rawCount = limit.getCount();
                    
                    if(rawCount != null) {
                        Long count = Long.valueOf(rawCount);
                        StringBuilder limitBuilder = new StringBuilder(" LIMIT ").append(count);
                        String rawOffset = limit.getOffset();
                        
                        if(rawOffset != null) {
                            Long offset = Long.valueOf(rawOffset);
                            
                            limitBuilder.append(" OFFSET ").append(offset);
                        }
                        
                        result = limitBuilder.append(' ').toString();
                    }
                }
            }
            
            if(result == null) {
                result = "";
            }
            
            limitCache.put(entityFactory, result);
        }
        
        return result;
    }

    /**
     * @param sql SQL statement to use for the PreparedStatement
     * @return Returns a PreparedStatement
     * @throws PersistenceDatabaseException Thrown if the PreparedStatement was unable to be created
     */
    public PreparedStatement prepareStatement(final Class<? extends BaseFactory> entityFactory, final String sql) {
        PreparedStatement preparedStatement = null;
        
        if(preparedStatementCache == null) {
            preparedStatementCache = new HashMap<>();
        } else {
            preparedStatement = preparedStatementCache.get(sql);
        }
        
        if((preparedStatement == null) && (sql != null)) {
            try {
                String replacedSql = sql;

                if(entityFactory != null) {
                    // _LIMIT_ expands to any limit passed in by the client
                    Matcher matcher = LIMIT_PATTERN.matcher(replacedSql);
                    replacedSql = matcher.replaceAll(getLimit(entityFactory));
                    
                    // _ALL_ expands to all columns in table
                    matcher = ALL_FIELDS_PATTERN.matcher(replacedSql);
                    replacedSql = matcher.replaceAll(getStringFromBaseFactory(entityFactory, allColumnsCache, GET_ALL_COLUMNS));
                    
                    // _PK_ expands to PK column in table
                    matcher = PK_FIELD_PATTERN.matcher(replacedSql);
                    replacedSql = matcher.replaceAll(getStringFromBaseFactory(entityFactory, pkColumnCache, GET_PK_COLUMN));
                }
                
                preparedStatement = connection.prepareStatement(replacedSql);
                preparedStatementCache.put(sql, preparedStatement);
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        }

        return preparedStatement;
    }
    
    /**
     * @param sql SQL statement to use for the PreparedStatement
     * @return Returns a PreparedStatement
     * @throws PersistenceDatabaseException Thrown if the PreparedStatement was unable to be created
     */
    public PreparedStatement prepareStatement(final String sql) {
        return prepareStatement(null, sql);
    }

    public static void setQueryParams(final PreparedStatement ps, final Object... params) {
        try {
            for(int i = 0; i < params.length; i++) {
                if(params[i] instanceof BaseEntity) {
                    ps.setLong(i + 1, ((BaseEntity)params[i]).getPrimaryKey().getEntityId());
                } else if(params[i] instanceof BasePK) {
                    ps.setLong(i + 1, ((BasePK)params[i]).getEntityId());
                } else if(params[i] instanceof Long) {
                    ps.setLong(i + 1, ((Long)params[i]));
                } else if(params[i] instanceof Integer) {
                    ps.setInt(i + 1, ((Integer)params[i]));
                } else if(params[i] instanceof String) {
                    ps.setString(i + 1, (String)params[i]);
                } else {
                    if(params[i] == null) {
                        throw new PersistenceDatabaseException("null Object in setQueryParams, index = " + i);
                    } else {
                        throw new PersistenceDatabaseException("unsupported Object in setQueryParams, " + params[i].getClass().getCanonicalName() + ", index = " + i);
                    }
                }
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
    }
    
    public void query(final String sql, final Object... params) {
        try {
            PreparedStatement ps = prepareStatement(sql);

            setQueryParams(ps, params);

            ps.execute();
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
    }

    public Integer queryForInteger(final String sql, final Object... params) {
        Integer result = null;

        try {
            PreparedStatement ps = prepareStatement(sql);

            setQueryParams(ps, params);

            ps.executeQuery();
            
            try(ResultSet rs = ps.getResultSet()) {
                if(rs.next()) {
                    result = rs.getInt(1);
                }

                if(rs.wasNull()) {
                    result = null;
                }

                if(rs.next()) {
                    throw new PersistenceDatabaseException("queryForInteger result contains multiple ints");
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        } 

        return result;
    }

    public Long queryForLong(final String sql, final Object... params) {
        Long result = null;
        
        try {
            PreparedStatement ps = prepareStatement(sql);
            
            setQueryParams(ps, params);
            
            ps.executeQuery();
            try (ResultSet rs = ps.getResultSet()) {
                if(rs.next()) {
                    result = rs.getLong(1);
                }

                if(rs.wasNull()) {
                    result = null;
                }

                if(rs.next()) {
                    throw new PersistenceDatabaseException("queryForLong result contains multiple longs");
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return result;
    }
    
    private void freePreparedStatementCache() {
        Collection<PreparedStatement> preparedStatements = preparedStatementCache.values();

        preparedStatements.forEach((preparedStatment) -> {
            try {
                preparedStatment.close();
            } catch (SQLException se) {
                // not much to do to recover from this problem, connection is closing soon.
                throw new PersistenceDatabaseException(se);
            }
        });
        
        preparedStatementCache = null;
    }

    @SuppressWarnings("Finally")
    public void close() {
        if(PersistenceDebugFlags.LogSessions) {
            getLog().info("close()");
        }

        if(connection != null) {
            if(PersistenceDebugFlags.LogConnections) {
                getLog().info("closing connection " + connection);
            }
            
            try {
                if(PersistenceDebugFlags.LogConnections) {
                    getLog().info("flushing entities for " + connection);
                }

                sessionEntityCache = sessionEntityCache.popLastSessionEntityCache();

                if(PersistenceDebugFlags.LogValueCaches) {
                    getLog().info("discarding valueCache " + valueCache);
                }

                if(valueCache != null) {
                    valueCache = null;
                }
                
                if(PersistenceDebugFlags.LogConnections) {
                    getLog().info("freeing prepared statement cache " + connection);
                }

                if(preparedStatementCache != null) {
                    freePreparedStatementCache();
                }
            } finally {
                try {
                    if(PersistenceDebugFlags.LogConnections) {
                        getLog().info("closing connection " + connection);
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
    
    public Set<String> getOptions(boolean createifNeeded) {
        if(options == null) {
            options = new HashSet<>();
        }
        
        return options;
    }
    
    public Set<String> getOptions() {
        return getOptions(false);
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
        return limits;
    }
    
}
