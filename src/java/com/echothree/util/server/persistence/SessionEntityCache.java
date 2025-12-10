// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import static com.echothree.util.server.persistence.PersistenceDebugFlags.LogSessionEntityCacheActions;
import static com.echothree.util.server.persistence.PersistenceDebugFlags.LogSessionEntityCacheStatistics;
import com.echothree.util.common.exception.PersistenceSessionEntityCacheException;
import com.echothree.util.common.persistence.BasePK;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SessionEntityCache {
    
    private Log log;
    private Session session;

    private SessionEntityCache parentSessionEntityCache;

    private Map<BasePK, BaseEntity> entitiesReadOnly = new HashMap<>();
    private Map<BasePK, BaseEntity> entitiesReadWrite = new HashMap<>();

    SessionEntityCacheStatistics cumulativeSessionEntityCacheStatistics;
    SessionEntityCacheStatistics sessionEntityCacheStatistics;
    
    private void init(Session session, SessionEntityCache parentSessionEntityCache) {
        if(LogSessionEntityCacheStatistics) {
            getLog().info("SessionEntityCache(session = " + session + ", parentSessionEntityCache = " + parentSessionEntityCache + ")");
        }

        this.session = session;
        this.parentSessionEntityCache = parentSessionEntityCache;
        
        if(LogSessionEntityCacheStatistics) {
            if(parentSessionEntityCache == null) {
                cumulativeSessionEntityCacheStatistics = new SessionEntityCacheStatistics();
            }
            
            sessionEntityCacheStatistics = new SessionEntityCacheStatistics();
        }
    }

    /** Creates a new instance of SessionEntityCache */
    public SessionEntityCache(SessionEntityCache parentSessionEntityCache) {
        if(parentSessionEntityCache == null) {
            throw new PersistenceSessionEntityCacheException("parentSessionEntityCache cannot be null");
        }

        init(parentSessionEntityCache.session, parentSessionEntityCache);
    }

    /** Creates a new instance of SessionEntityCache */
    public SessionEntityCache(Session session) {
        if(session == null) {
            throw new PersistenceSessionEntityCacheException("session cannot be null");
        }

        init(session, null);
    }

    protected Log getLog() {
        if(log == null) {
            log = LogFactory.getLog(this.getClass());
        }
        
        return log;
    }

    // Removed the RO entity, and drops it into the RW cache
    private void replaceReadOnlyEntity(BaseEntity baseEntity) {
        var basePK = baseEntity.getPrimaryKey();

        entitiesReadOnly.remove(basePK);
        entitiesReadWrite.put(basePK, baseEntity);

        if(LogSessionEntityCacheStatistics) {
            sessionEntityCacheStatistics.readOnlyUpgradedToReadWrite++;
        }
    }

    private boolean entitiesReadOnlyContains(BasePK basePK) {
        return entitiesReadOnly.containsKey(basePK);
    }

    public void putReadOnlyEntity(BasePK basePk, BaseEntity baseEntity) {
        if(LogSessionEntityCacheStatistics) {
            sessionEntityCacheStatistics.putReadOnlyEntity++;
        }
        
        if(LogSessionEntityCacheActions) {
            getLog().info("putReadOnlyEntity(" + baseEntity.getPrimaryKey() + ")");
        }

        entitiesReadOnly.put(basePk, baseEntity);
    }

    public void putReadWriteEntity(BasePK basePK, BaseEntity baseEntity) {
        var cacheToExamine = this;
        var addedToCache = false;

        if(LogSessionEntityCacheActions) {
            getLog().info("putReadWriteEntity(" + baseEntity.getPrimaryKey() + ")");
        }

        // Check this cache, and all parent caches, and see if one of them contains a RO copy of this
        // entity. If so, replace it in the cache it was found in, otherwise, please it in our RW cache.
        do {
            if(cacheToExamine.entitiesReadOnlyContains(basePK)) {
                cacheToExamine.replaceReadOnlyEntity(baseEntity);
                addedToCache = true;
                break;
            }

            cacheToExamine = cacheToExamine.parentSessionEntityCache;
        } while(cacheToExamine != null);

        if(addedToCache) {
            if(LogSessionEntityCacheStatistics) {
                sessionEntityCacheStatistics.putReadWriteEntityToParent++;
            }
        } else {
            if(LogSessionEntityCacheStatistics) {
                sessionEntityCacheStatistics.putReadWriteEntity++;
            }

            entitiesReadWrite.put(basePK, baseEntity);
        }
    }

    public BaseEntity getEntity(BasePK basePK) {
        BaseEntity entity;

        entity = entitiesReadWrite.get(basePK);

        if(LogSessionEntityCacheStatistics && entity != null) {
            sessionEntityCacheStatistics.gotEntityFromReadWrite++;
        }
        
        if(entity == null) {
            entity = entitiesReadOnly.get(basePK);
        }

        if(LogSessionEntityCacheStatistics && entity != null) {
            sessionEntityCacheStatistics.gotEntityFromReadOnly++;
        }

        // If it isn't found in either of our caches, and there is a parentSessionEntityCache, check it.
        if(entity == null && parentSessionEntityCache != null) {
            entity = parentSessionEntityCache.getEntity(basePK);

            if(LogSessionEntityCacheStatistics && entity != null) {
                sessionEntityCacheStatistics.gotEntityFromParent++;
            }
        }

        if(LogSessionEntityCacheStatistics && entity == null) {
            sessionEntityCacheStatistics.entityNotGotten++;
        }

        if(LogSessionEntityCacheActions) {
            getLog().info("getEntity(" + basePK + ") = " + (entity == null ? null : entity.getPrimaryKey()));
        }

        return entity;
    }
    
    public void removed(BasePK basePK, boolean missingPermitted) {
        if(LogSessionEntityCacheActions) {
            getLog().info("removed(" + basePK + ")");
        }

        if(entitiesReadWrite.remove(basePK) == null) {
            if(entitiesReadOnly.remove(basePK) == null) {
                // If the basePK wasn't in either of our caches, check the parentSessionEntityCache.
                if(parentSessionEntityCache == null) {
                    if(!missingPermitted) {
                        throw new PersistenceSessionEntityCacheException("removed(...) called on BasePK that is not in a cache");
                    }
                } else {
                    parentSessionEntityCache.removed(basePK, missingPermitted);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void flushEntities() {
        Map<Class, List<BaseEntity>> values = new HashMap<>();
        
        entitiesReadWrite.values().stream().filter((baseEntity) -> baseEntity.hasBeenModified()).forEach((baseEntity) -> {
            Class baseEntityClass = baseEntity.getClass();
            var baseEntities = values.get(baseEntity.getClass());
            
            if(baseEntities == null) {
                baseEntities = new ArrayList<>();
                values.put(baseEntityClass, baseEntities);
            }
            
            baseEntities.add(baseEntity);
        });

        values.entrySet().stream().map((entry) -> entry.getValue()).forEach((baseEntities) -> {
            var firstBaseEntity = baseEntities.get(0);
            var baseFactory = firstBaseEntity.getBaseFactoryInstance();
            
            baseFactory.store(baseEntities);
        });
        
        if(LogSessionEntityCacheStatistics) {
            sessionEntityCacheStatistics.finalReadWriteCount = entitiesReadWrite.size();
            sessionEntityCacheStatistics.finalReadOnlyCount = entitiesReadOnly.size();
        }

        entitiesReadOnly = null;
        entitiesReadWrite = null;
    }

    private void dumpStats() {
        var myLog = getLog();

        myLog.info("--------------------------------------------------------------------------------");
        myLog.info("this = " + this);
        myLog.info("parentSessionEntityCache = " + parentSessionEntityCache);
        sessionEntityCacheStatistics.dumpStats();
        myLog.info("--------------------------------------------------------------------------------");
    }

    private void dumpCumulativeStats() {
        var myLog = getLog();

        myLog.info("--------------------------------------------------------------------------------");
        myLog.info("this = " + this);
        cumulativeSessionEntityCacheStatistics.dumpStats();
        myLog.info("--------------------------------------------------------------------------------");
    }

    private SessionEntityCache pop(boolean lastInStack) {
        if(LogSessionEntityCacheStatistics) {
            getLog().info("pop(lastInStack = " + lastInStack + ")");
        }

        if(parentSessionEntityCache == null && !lastInStack) {
            throw new PersistenceSessionEntityCacheException("Cannot call popSessionEntityCache() on last SessionEntityCache");
        }

        if(parentSessionEntityCache != null && lastInStack) {
            throw new PersistenceSessionEntityCacheException("Can only call popLastSessionEntityCache() on last SessionEntityCache");
        }

        flushEntities();

        
        if(LogSessionEntityCacheStatistics) {
            addStatisticsToRootCache(this, sessionEntityCacheStatistics);

            dumpStats();
            
            if(parentSessionEntityCache == null) {
                dumpCumulativeStats();
            }
        }

        return parentSessionEntityCache;
    }
    
    private void addStatisticsToRootCache(SessionEntityCache parentSessionEntityCache, SessionEntityCacheStatistics sessionEntityCacheStatistics) {
        if(parentSessionEntityCache.parentSessionEntityCache != null) {
            addStatisticsToRootCache(parentSessionEntityCache.parentSessionEntityCache, sessionEntityCacheStatistics);
        } else {
            parentSessionEntityCache.cumulativeSessionEntityCacheStatistics.Add(sessionEntityCacheStatistics);
        }
    }

    public SessionEntityCache popSessionEntityCache() {
        return pop(false);
    }

    public SessionEntityCache popLastSessionEntityCache() {
        return pop(true);
    }

}
