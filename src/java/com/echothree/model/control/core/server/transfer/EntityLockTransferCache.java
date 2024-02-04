// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.core.server.CoreDebugFlags;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.EntityLockException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.DslContextFactory;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class EntityLockTransferCache
        extends BaseCoreTransferCache {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of EntityLockTransferCache */
    public EntityLockTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    DataSource ds = null;
    
    @SuppressWarnings("Finally")
    public EntityLockTransfer getEntityLockTransfer(BasePK lockTarget) {
        EntityLockTransfer entityLockTransfer = (EntityLockTransfer)get(lockTarget);
        
        if(entityLockTransfer == null) {
            EntityInstance lockTargetEntityInstance = coreControl.getEntityInstanceByBasePK(lockTarget);
            
            entityLockTransfer = getEntityLockTransferByEntityInstance(lockTargetEntityInstance, true);
        }
        
        return entityLockTransfer;
    }
    
    public EntityLockTransfer getEntityLockTransferByEntityInstance(EntityInstance lockTargetEntityInstance) {
        return getEntityLockTransferByEntityInstance(lockTargetEntityInstance, false);
    }
    
    private EntityLockTransfer getEntityLockTransferByEntityInstance(EntityInstance lockTargetEntityInstance, boolean putInCache) {
        EntityLockTransfer entityLockTransfer = null;
        
        try (Connection conn = DslContextFactory.getInstance().getNTDslContext().parsingConnection()) {
            long lockTargetEntityInstanceId = lockTargetEntityInstance.getPrimaryKey().getEntityId();
            long lockedByEntityInstanceId = 0;
            long lockedTime = 0;
            long expirationTime = 0;

            if(CoreDebugFlags.LogEntityLocks) {
                getLog().info(">>> getEntityLockTransfer(lockTargetEntityInstance=" + lockTargetEntityInstance.getPrimaryKey() + ")");
            }

            if(CoreDebugFlags.LogEntityLocks) {
                getLog().info("--- lockTargetEntityInstance=" + lockTargetEntityInstance);
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT lcks_lockedbyentityinstanceid, lcks_lockedtime, lcks_lockexpirationtime "
                    + "FROM entitylocks "
                    + "WHERE lcks_locktargetentityinstanceid = ?")) {
                ps.setLong(1, lockTargetEntityInstanceId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        lockedByEntityInstanceId = rs.getLong(1);
                        lockedTime = rs.getLong(2);
                        expirationTime = rs.getLong(3);
                    }
                } catch (SQLException se) {
                    throw new EntityLockException(se);
                }
            } catch (SQLException se) {
                throw new EntityLockException(se);
            }

            if(lockedByEntityInstanceId != 0) {
                EntityInstancePK lockedByEntityInstancePK = new EntityInstancePK(lockedByEntityInstanceId);
                EntityInstance lockedByEntityInstance = EntityInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, lockedByEntityInstancePK);
                EntityInstanceTransfer lockTargetEntityInstanceTransfer = coreControl.getEntityInstanceTransfer(userVisit, lockTargetEntityInstance, false, false, false, false, false, false);
                EntityInstanceTransfer lockedByEntityInstanceTransfer = coreControl.getEntityInstanceTransfer(userVisit, lockedByEntityInstance, false, false, false, false, false, false);

                if(CoreDebugFlags.LogEntityLocks) {
                    getLog().info("--- lockedByEntityInstancePK=" + lockedByEntityInstancePK);
                    getLog().info("--- lockedByEntityInstance=" + lockedByEntityInstance);
                    getLog().info("--- lockTargetEntityInstanceTransfer=" + lockTargetEntityInstanceTransfer);
                    getLog().info("--- lockTargetEntityInstanceTransfer=" + lockTargetEntityInstanceTransfer);
                }

                var lockedTimeString = formatTypicalDateTime(lockedTime);
                var expirationTimeString = formatTypicalDateTime(expirationTime);

                entityLockTransfer = new EntityLockTransfer(lockTargetEntityInstanceTransfer, lockedByEntityInstanceTransfer, lockedTime, lockedTimeString, expirationTime, expirationTimeString);
            }
            
            if(putInCache) {
                transferCache.put(PersistenceUtils.getInstance().getBasePKFromEntityInstance(lockTargetEntityInstance), entityLockTransfer);
            }
        } catch (SQLException se) {
            throw new EntityLockException(se);
        }

        if(CoreDebugFlags.LogEntityLocks) {
            getLog().info("<<< entityLockTransfer=" + entityLockTransfer);
        }

        return entityLockTransfer;
    }    
    
}
