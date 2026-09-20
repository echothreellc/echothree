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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.core.server.CoreDebugFlags;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.tables.core.EntityLocks.EntityLocks;
import com.echothree.util.common.exception.EntityLockException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.DslContextFactory;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.PersistenceUtils;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import org.jooq.exception.DataAccessException;

@RequestScoped
public class EntityLockTransferCache
        extends BaseCoreTransferCache {


    /** Creates a new instance of EntityLockTransferCache */
    protected EntityLockTransferCache() {
        super();
    }
    
    @SuppressWarnings("Finally")
    public EntityLockTransfer getEntityLockTransfer(final UserVisit userVisit, BasePK lockTarget) {
        var entityLockTransfer = (EntityLockTransfer)get(lockTarget);
        
        if(entityLockTransfer == null) {
            var lockTargetEntityInstance = entityInstanceControl.getEntityInstanceByBasePK(lockTarget);
            
            entityLockTransfer = getEntityLockTransferByEntityInstance(userVisit, lockTargetEntityInstance, true);
        }
        
        return entityLockTransfer;
    }
    
    public EntityLockTransfer getEntityLockTransferByEntityInstance(final UserVisit userVisit, EntityInstance lockTargetEntityInstance) {
        return getEntityLockTransferByEntityInstance(userVisit, lockTargetEntityInstance, false);
    }
    
    private EntityLockTransfer getEntityLockTransferByEntityInstance(final UserVisit userVisit, EntityInstance lockTargetEntityInstance, boolean putInCache) {
        EntityLockTransfer entityLockTransfer = null;
        
        try {
            var dslContext = DslContextFactory.getInstance().getNTDslContext();

            if(CoreDebugFlags.LogEntityLocks) {
                getLog().info(">>> getEntityLockTransfer(lockTargetEntityInstance=" + lockTargetEntityInstance.getPrimaryKey() + ")");
            }

            if(CoreDebugFlags.LogEntityLocks) {
                getLog().info("--- lockTargetEntityInstance=" + lockTargetEntityInstance);
            }

            var lock = dslContext
                    .select(EntityLocks.LOCKED_BY_ENTITY_INSTANCE, EntityLocks.LOCKED_TIME, EntityLocks.LOCK_EXPIRATION_TIME)
                    .from(EntityLocks)
                    .where(EntityLocks.LOCK_TARGET_ENTITY_INSTANCE.eq(lockTargetEntityInstance.getPrimaryKey()))
                    .fetchOne();

            if(lock != null && lock.value1().getEntityId() != 0) {
                var lockedByEntityInstancePK = lock.value1();
                var lockedTime = lock.value2();
                var expirationTime = lock.value3();
                var lockedByEntityInstance = EntityInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, lockedByEntityInstancePK);
                var lockTargetEntityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, lockTargetEntityInstance, false, false, false, false);
                var lockedByEntityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, lockedByEntityInstance, false, false, false, false);

                if(CoreDebugFlags.LogEntityLocks) {
                    getLog().info("--- lockedByEntityInstancePK = " + lockedByEntityInstancePK);
                    getLog().info("--- lockedByEntityInstance = " + lockedByEntityInstance);
                    getLog().info("--- lockTargetEntityInstanceTransfer = " + lockTargetEntityInstanceTransfer.getEntityRef());
                    getLog().info("--- lockTargetEntityInstanceTransfer = " + lockTargetEntityInstanceTransfer.getEntityRef());
                }

                var lockedTimeString = formatTypicalDateTime(userVisit, lockedTime);
                var expirationTimeString = formatTypicalDateTime(userVisit, expirationTime);

                entityLockTransfer = new EntityLockTransfer(lockTargetEntityInstanceTransfer, lockedByEntityInstanceTransfer, lockedTime, lockedTimeString, expirationTime, expirationTimeString);
            }
            
            if(putInCache) {
                transferCache.put(PersistenceUtils.getInstance().getBasePKFromEntityInstance(lockTargetEntityInstance), entityLockTransfer);
            }
        } catch (DataAccessException ex) {
            var sqlException = ex.getCause(SQLException.class);
            throw new EntityLockException(sqlException == null ? ex : sqlException);
        }

        if(CoreDebugFlags.LogEntityLocks) {
            getLog().info("<<< entityLockTransfer=" + entityLockTransfer);
        }

        return entityLockTransfer;
    }    
    
}
