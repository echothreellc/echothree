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

import static com.echothree.model.jooq.server.tables.core.EntityIds.EntityIds;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityIdGenerator {

    private static final Logger log = LoggerFactory.getLogger(EntityIdGenerator.class);

    static private final int defaultChunkSize = 100;
    static private final int retryCount = 5;

    private final ReentrantLock lock = new ReentrantLock(true);

    private String componentVendorName;
    private String entityTypeName;
    private int chunkSize;
    private long guardedValue = 0;
    private long currentValue = 0;
    
    private void init(String componentVendorName, String entityTypeName, int chunkSize) {
        log.debug("EntityIdGenerator(componentVendorName = {}, entityTypeName = {}, chunkSize = {})",
                componentVendorName, entityTypeName, chunkSize);

        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.chunkSize = chunkSize;
    }
    
    /** Creates a new instance of EntityIdGenerator */
    public EntityIdGenerator(String componentVendorName, String entityTypeName, int chunkSize) {
        init(componentVendorName, entityTypeName, chunkSize);
    }
    
    /** Creates a new instance of EntityIdGenerator */
    public EntityIdGenerator(String componentVendorName, String entityTypeName) {
        init(componentVendorName, entityTypeName, defaultChunkSize);
    }
    
    private boolean getNewChunk() {
        log.debug("getNewChunk(), componentVendorName = {}, entityTypeName = {}",
                componentVendorName, entityTypeName);

        var context = DslContextFactory.getInstance().getNTDslContext();

        // Keep the read and conditional write on one nontransactional connection.
        // Do not enlist ID reservations in the caller's transaction.
        return context.connectionResult(connection -> {
            var dslContext = DSL.using(context.configuration().derive(connection));
            var currentMax = dslContext
                    .select(EntityIds.LAST_ENTITY)
                    .from(EntityIds)
                    .where(EntityIds.COMPONENT_VENDOR_NAME.eq(componentVendorName),
                            EntityIds.ENTITY_TYPE_NAME.eq(entityTypeName))
                    .fetchOne();

            if(currentMax != null) {
                var lastEntityId = currentMax.value1();
                var nextMax = lastEntityId + chunkSize;
                var rowCount = dslContext
                        .update(EntityIds)
                        .set(EntityIds.LAST_ENTITY, nextMax)
                        .where(EntityIds.COMPONENT_VENDOR_NAME.eq(componentVendorName),
                                EntityIds.ENTITY_TYPE_NAME.eq(entityTypeName),
                                EntityIds.LAST_ENTITY.eq(lastEntityId))
                        .execute();

                // A competing allocator may have reserved this range first.
                if(rowCount != 1) {
                    return false;
                }

                guardedValue = nextMax;
                currentValue = lastEntityId;
            } else {
                var rowCount = dslContext
                        .insertInto(EntityIds, EntityIds.COMPONENT_VENDOR_NAME, EntityIds.ENTITY_TYPE_NAME, EntityIds.LAST_ENTITY)
                        .values(componentVendorName, entityTypeName, (long)chunkSize)
                        .execute();

                if(rowCount != 1) {
                    return false;
                }

                guardedValue = chunkSize;
                currentValue = 0;
            }

            return true;
        });
    }

    public long getNextEntityId() {
        long nextEntityId;

        lock.lock();
        try {
            if(currentValue == guardedValue) {
                try {
                    for(var iter = 0; iter <= retryCount; iter++) {
                        if(getNewChunk())
                            break;
                    }
                } catch (DataAccessException ex) {
                    var sqlException = ex.getCause(SQLException.class);
                    throw new PersistenceDatabaseException(sqlException == null ? ex : sqlException);
                }
            }

            if(currentValue == guardedValue) {
                throw new PersistenceDatabaseException("Could not get next increment value");
            }

            nextEntityId = ++currentValue;
        } finally {
            lock.unlock();
        }

        log.debug("getNextEntityId(), nextEntityId = {}, componentVendorName = {}, entityTypeName = {}",
                nextEntityId, componentVendorName, entityTypeName);

        return nextEntityId;
    }
    
}
