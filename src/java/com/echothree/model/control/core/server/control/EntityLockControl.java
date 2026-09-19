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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.core.server.CoreDebugFlags;
import com.echothree.model.control.core.server.transfer.EntityLockTransferCache;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.tables.core.EntityLocks.EntityLocks;
import com.echothree.util.common.exception.EntityLockException;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.BaseValue;
import com.echothree.util.server.persistence.DslContextFactory;
import javax.inject.Inject;
import org.jooq.exception.DataAccessException;

@CommandScope
public class EntityLockControl
        extends BaseModelControl {

    /** Creates a new instance of EntityLockControl */

    protected EntityLockControl() {
        super();
    }

    // -------------------------------------------------------------------------
    //   Entity Locks
    // -------------------------------------------------------------------------
    
    @Inject
    EntityLockTransferCache entityLockTransferCache;

    public EntityLockTransfer getEntityLockTransfer(UserVisit userVisit, BaseEntity lockTarget)
            throws EntityLockException {
        return getEntityLockTransfer(userVisit, lockTarget.getPrimaryKey());
    }
    
    public EntityLockTransfer getEntityLockTransfer(UserVisit userVisit, BaseValue<?> lockTarget)
            throws EntityLockException {
        return getEntityLockTransfer(userVisit, lockTarget.getPrimaryKey());
    }
    
    public EntityLockTransfer getEntityLockTransfer(UserVisit userVisit, BasePK lockTarget) {
        return entityLockTransferCache.getEntityLockTransfer(userVisit, lockTarget);
    }
    
    public EntityLockTransfer getEntityLockTransferByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
        return entityLockTransferCache.getEntityLockTransferByEntityInstance(userVisit, entityInstance);
    }
    
    private static final long defaultLockDuration = 5 * 60 * 1000; // 5 Minutes

    private long getLockDuration(EntityInstance lockTargetEntityInstance) {
        var lockTimeout = lockTargetEntityInstance.getEntityType().getLastDetail().getLockTimeout();

        return lockTimeout == null ? defaultLockDuration : lockTimeout;
    }

    /** Create a lock on a given entity.
     * @param lockTarget Entity to hold the lock on
     * @param lockedBy Entity holding the lock on lockTarget
     * @return Returns a Long value indicating the expiration time of the lock. If 0 is
     * returned, then a lock was not able to be obtained.
     */
    public long lockEntity(BaseEntity lockTarget, BasePK lockedBy)
            throws EntityLockException {
        return lockEntity(lockTarget.getPrimaryKey(), lockedBy);
    }
    
    /** Create a lock on a given entity.
     * @param lockTarget Entity to hold the lock on
     * @param lockedBy Entity holding the lock on lockTarget
     * @return Returns a Long value indicating the expiration time of the lock. If 0 is
     * returned, then a lock was not able to be obtained.
     */
    public long lockEntity(BaseValue<?> lockTarget, BasePK lockedBy)
            throws EntityLockException {
        return lockEntity(lockTarget.getPrimaryKey(), lockedBy);
    }

    // Must be held by any other EntityInstance, must have an expiration time, and that expiration time must be before the current time.
    private static boolean doesTargetEntityInstanceHaveExpiredLock(final long currentTime, final long currentLockedByEntityInstanceId, final long currentLockExpirationTime) {
        return (currentLockedByEntityInstanceId != 0) && (currentLockExpirationTime != 0) && (currentLockExpirationTime < currentTime);
    }

    // Must be held by the EntityInstance requesting the lock, and it must have an expiration time.
    private static boolean doesLockedByEntityInstanceHaveCurrentLock(final long currentLockedByEntityInstanceId, final long lockedByEntityInstanceId, final long currentLockExpirationTime) {
        return (currentLockedByEntityInstanceId == lockedByEntityInstanceId) && (currentLockExpirationTime != 0);
    }

    /** Create a lock on a given entity.
     * @param lockTarget Entity to hold the lock on
     * @param lockedBy Entity holding the lock on lockTarget
     * @return Returns a Long value indicating the expiration time of the lock. If 0 is
     * returned, then a lock was not able to be obtained.
     */
    public long lockEntity(final BasePK lockTarget, final BasePK lockedBy)
            throws EntityLockException {
        long lockExpirationTime;
        var lockTargetEntityInstance = entityInstanceControl.getEntityInstanceByBasePK(lockTarget);
        var lockTargetEntityInstanceId = lockTargetEntityInstance.getPrimaryKey().getEntityId();
        var lockedByEntityInstanceId = entityInstanceControl.getEntityInstanceByBasePK(lockedBy).getPrimaryKey().getEntityId();
        
        if(CoreDebugFlags.LogEntityLocks) {
            getLog().info(">>> lockEntity(lockTarget=" + lockTarget + ", lockedBy=" + lockedBy + ")");
            getLog().info("--- lockTargetEntityInstanceId=" + lockTargetEntityInstanceId + ", lockedByEntityInstanceId=" + lockedByEntityInstanceId);
        }
        
        if(lockTargetEntityInstanceId != 0 && lockedByEntityInstanceId != 0) {
            var currentTime = System.currentTimeMillis();
            var proposedExpirationTime = currentTime + getLockDuration(lockTargetEntityInstance);
            
            try {
                var dslContext = DslContextFactory.getInstance().getNTDslContext();
                // First, see if a lock is currently being held on the entity. If it is,
                // then retrieve enough information so that we can tell if it should have
                // already expired, and if so, grab control of it (that is the second step
                // shown below).
                var currentLockedByEntityInstanceId = 0L;
                var currentLockedTime = 0L;
                var currentLockExpirationTime = 0L;

                var lock = dslContext
                        .select(EntityLocks.LOCKED_BY_ENTITY_INSTANCE, EntityLocks.LOCKED_TIME, EntityLocks.LOCK_EXPIRATION_TIME)
                        .from(EntityLocks)
                        .where(EntityLocks.LOCK_TARGET_ENTITY_INSTANCE.eq(new EntityInstancePK(lockTargetEntityInstanceId)))
                        .fetchOne();

                if(lock != null) {
                    currentLockedByEntityInstanceId = lock.value1().getEntityId();
                    currentLockedTime = lock.value2();
                    currentLockExpirationTime = lock.value3();
                }

                // Secondly, we try to reuse an existing lock, based on one of two possible criteria passing.
                // First, try to claim an existing, expired lock, held by any Entity Instance. Secondly, we
                // will try to reclaim a lock held by ourselves - expiration status does not matter.
                // There should not be any exceptions thrown by this code, since we're only updating a record,
                // and it will either exist or it will not.
                if(doesTargetEntityInstanceHaveExpiredLock(currentTime, currentLockedByEntityInstanceId, currentLockExpirationTime)
                        || doesLockedByEntityInstanceHaveCurrentLock(currentLockedByEntityInstanceId, lockedByEntityInstanceId, currentLockExpirationTime)) {
                    var rowCount = dslContext
                            .update(EntityLocks)
                            .set(EntityLocks.LOCKED_BY_ENTITY_INSTANCE, new EntityInstancePK(lockedByEntityInstanceId))
                            .set(EntityLocks.LOCKED_TIME, currentTime)
                            .set(EntityLocks.LOCK_EXPIRATION_TIME, proposedExpirationTime)
                            .where(EntityLocks.LOCK_TARGET_ENTITY_INSTANCE.eq(new EntityInstancePK(lockTargetEntityInstanceId)),
                                    EntityLocks.LOCKED_BY_ENTITY_INSTANCE.eq(new EntityInstancePK(currentLockedByEntityInstanceId)),
                                    EntityLocks.LOCKED_TIME.eq(currentLockedTime),
                                    EntityLocks.LOCK_EXPIRATION_TIME.eq(currentLockExpirationTime))
                            .execute();

                    lockExpirationTime = rowCount == 0 ? 0 : proposedExpirationTime;
                } else {
                    lockExpirationTime = 0;
                }

                // Finally, if there was no lock already being held on it, and the lockExpirationTime
                // is 0, then we need to attempt to insert a new lock. This code may generate
                // an exception if someone else tries this between now and the time the SELECT
                // statement was tried.
                if(currentLockedByEntityInstanceId == 0) {
                    dslContext
                            .insertInto(EntityLocks)
                            .columns(EntityLocks.LOCK_TARGET_ENTITY_INSTANCE, EntityLocks.LOCKED_BY_ENTITY_INSTANCE, EntityLocks.LOCKED_TIME, EntityLocks.LOCK_EXPIRATION_TIME)
                            .values(new EntityInstancePK(lockTargetEntityInstanceId), new EntityInstancePK(lockedByEntityInstanceId), currentTime, proposedExpirationTime)
                            .execute();
                    lockExpirationTime = proposedExpirationTime;
                }
            } catch (DataAccessException se) {
                throw new EntityLockException(se);
            }
        } else {
            lockExpirationTime = 0;
        }
        
        if(CoreDebugFlags.LogEntityLocks) {
            getLog().info("<<< lockExpirationTime=" + lockExpirationTime);
        }
        
        return lockExpirationTime;
    }

    /** Converts an existing lock that is held on an entity into one that is no longer
     * time limited.
     * @return Returns a Boolean value indicating whether the lock was successful
     */
    public boolean lockEntityForUpdate(final BaseEntity lockTarget, final BasePK lockedBy)
            throws EntityLockException {
        return lockEntityForUpdate(lockTarget.getPrimaryKey(), lockedBy);
    }
    
    /** Converts an existing lock that is held on an entity into one that is no longer
     * time limited.
     * @return Returns a Boolean value indicating whether the lock was successful
     */
    public boolean lockEntityForUpdate(final BaseValue<?> lockTarget, final BasePK lockedBy)
            throws EntityLockException {
        return lockEntityForUpdate(lockTarget.getPrimaryKey(), lockedBy);
    }
    
    /** Converts an existing lock that is held on an entity into one that is no longer
     * time limited.
     * @return Returns a Boolean value indicating whether the lock was successful
     */
    public boolean lockEntityForUpdate(final BasePK lockTarget, final BasePK lockedBy)
            throws EntityLockException {
        boolean isLocked;
        var lockTargetEntityInstanceId = entityInstanceControl.getEntityInstanceByBasePK(lockTarget).getPrimaryKey().getEntityId();
        var lockedByEntityInstanceId = entityInstanceControl.getEntityInstanceByBasePK(lockedBy).getPrimaryKey().getEntityId();
        
        if(CoreDebugFlags.LogEntityLocks) {
            getLog().info(">>> lockEntity(lockTarget=" + lockTarget + ", lockedBy=" + lockedBy + ")");
            getLog().info("--- lockTargetEntityInstanceId=" + lockTargetEntityInstanceId + ", lockedByEntityInstanceId=" + lockedByEntityInstanceId);
        }
        
        if(lockTargetEntityInstanceId != 0 && lockedByEntityInstanceId != 0) {
            try {
                var dslContext = DslContextFactory.getInstance().getNTDslContext();
                // First, we check to make sure that we do in fact have a lock on the
                // entity. Also, enough information is retried so that we can use it
                // in the next step to properly identify the lock.
                var currentLockedTime = 0L;
                var currentLockExpirationTime = 0L;

                var lock = dslContext
                        .select(EntityLocks.LOCKED_TIME, EntityLocks.LOCK_EXPIRATION_TIME)
                        .from(EntityLocks)
                        .where(EntityLocks.LOCK_TARGET_ENTITY_INSTANCE.eq(new EntityInstancePK(lockTargetEntityInstanceId)),
                                EntityLocks.LOCKED_BY_ENTITY_INSTANCE.eq(new EntityInstancePK(lockedByEntityInstanceId)))
                        .fetchOne();

                if(lock != null) {
                    currentLockedTime = lock.value1();
                    currentLockExpirationTime = lock.value2();
                }

                // Secondly, we try to set the lock expiration time to 0 to indicate that
                // we're going to hold onto it indefinitely. If the lock's expiration time is
                // before the current time, we're going to allow the user to reclaim their expired
                // locked, as long as it was locked by them (indicated by the currentLockExpirationTime
                // being != 0).
                if(currentLockExpirationTime != 0) {
                    var rowCount = dslContext
                            .update(EntityLocks)
                            .set(EntityLocks.LOCK_EXPIRATION_TIME, 0L)
                            .where(EntityLocks.LOCK_TARGET_ENTITY_INSTANCE.eq(new EntityInstancePK(lockTargetEntityInstanceId)),
                                    EntityLocks.LOCKED_BY_ENTITY_INSTANCE.eq(new EntityInstancePK(lockedByEntityInstanceId)),
                                    EntityLocks.LOCKED_TIME.eq(currentLockedTime),
                                    EntityLocks.LOCK_EXPIRATION_TIME.eq(currentLockExpirationTime))
                            .execute();

                    isLocked = rowCount != 0;
                } else {
                    isLocked = false;
                }
            } catch (DataAccessException se) {
                throw new EntityLockException(se);
            }
        } else {
            isLocked = false;
        }
        
        if(CoreDebugFlags.LogEntityLocks) {
            getLog().info("<<< isLocked=" + isLocked);
        }
        
        return isLocked;
    }
    
    /** Releases a lock that is being held on a given entity.
     * @param lockTarget Entity to release the lock on
     * @param lockedBy Optional parameter if you want to specifically release a lock held by lockedBy
     * @return Returns a Boolean value indicating whether lockTarget had a lock on it
     */
    public boolean unlockEntity(final BaseEntity lockTarget, final BasePK lockedBy)
            throws EntityLockException {
        return unlockEntity(lockTarget.getPrimaryKey(), lockedBy);
    }
    
    /** Releases a lock that is being held on a given entity.
     * @param lockTarget Entity to release the lock on
     * @param lockedBy Optional parameter if you want to specifically release a lock held by lockedBy
     * @return Returns a Boolean value indicating whether lockTarget had a lock on it
     */
    public boolean unlockEntity(final BaseValue<?> lockTarget, final BasePK lockedBy)
            throws EntityLockException {
        return unlockEntity(lockTarget.getPrimaryKey(), lockedBy);
    }
    
    /** Releases a lock that is being held on a given entity.
     * @param lockTarget Entity to release the lock on
     * @param lockedBy Optional parameter if you want to specifically release a lock held by lockedBy
     * @return Returns a Boolean value indicating whether lockTarget had a lock on it
     */
    public boolean unlockEntity(final BasePK lockTarget, final BasePK lockedBy)
            throws EntityLockException {
        boolean wasUnlocked;
        var lockTargetEntityInstanceId = entityInstanceControl.getEntityInstanceByBasePK(lockTarget).getPrimaryKey().getEntityId();
        
        if(CoreDebugFlags.LogEntityLocks) {
            getLog().info(">>> lockEntity(lockTarget=" + lockTarget + ", lockedBy=" + lockedBy + ")");
            getLog().info("--- lockTargetEntityInstanceId=" + lockTargetEntityInstanceId);
        }
        
        if(lockTargetEntityInstanceId != 0) {
            try {
                var dslContext = DslContextFactory.getInstance().getNTDslContext();
                if(lockedBy == null) {
                    wasUnlocked = dslContext
                            .deleteFrom(EntityLocks)
                            .where(EntityLocks.LOCK_TARGET_ENTITY_INSTANCE.eq(new EntityInstancePK(lockTargetEntityInstanceId)))
                            .execute() > 0;
                } else {
                    var lockedByEntityInstanceId = entityInstanceControl.getEntityInstanceByBasePK(lockedBy).getPrimaryKey().getEntityId();
                    
                    if(CoreDebugFlags.LogEntityLocks) {
                        getLog().info("--- lockedByEntityInstanceId=" + lockedByEntityInstanceId);
                    }
                    
                    if(lockedByEntityInstanceId != 0) {
                        wasUnlocked = dslContext
                                .deleteFrom(EntityLocks)
                                .where(EntityLocks.LOCK_TARGET_ENTITY_INSTANCE.eq(new EntityInstancePK(lockTargetEntityInstanceId)),
                                        EntityLocks.LOCKED_BY_ENTITY_INSTANCE.eq(new EntityInstancePK(lockedByEntityInstanceId)))
                                .execute() > 0;
                    } else {
                        throw new EntityLockException(new IllegalArgumentException());
                    }
                }
            } catch (DataAccessException se) {
                throw new EntityLockException(se);
            }
        } else {
            wasUnlocked = false;
        }
        
        if(CoreDebugFlags.LogEntityLocks) {
            getLog().info("<<< wasUnlocked=" + wasUnlocked);
        }
        
        return wasUnlocked;
    }
    
    /** Tests whether a valid lock is being held on a given entity.
     * @param lockTarget Entity you are testing to see if it is locked or not
     * @param lockedBy Optional parameter if you want to see if the lock on lockTarget is being held by
     * a specific entity
     * @return Returns a Boolean value indicating whether lockTarget has a lock on it
     */
    public boolean isEntityLocked(final BaseEntity lockTarget, final BasePK lockedBy)
            throws EntityLockException {
        return isEntityLocked(lockTarget.getPrimaryKey(), lockedBy);
    }
    
    /** Tests whether  a valid lock is being held on a given entity.
     * @param lockTarget Entity you are testing to see if it is locked or not
     * @param lockedBy Optional parameter if you want to see if the lock on lockTarget is being held by
     * a specific entity
     * @return Returns a Boolean value indicating whether lockTarget has a lock on it
     */
    public boolean isEntityLocked(final BaseValue<?> lockTarget, final BasePK lockedBy)
            throws EntityLockException {
        return isEntityLocked(lockTarget.getPrimaryKey(), lockedBy);
    }
    
    /** Tests whether a valid lock is being held on a given entity.
     * @param lockTarget Entity you are testing to see if it is locked or not
     * @param lockedBy Optional parameter if you want to see if the lock on lockTarget is being held by
     * a specific entity
     * @return Returns a Boolean value indicating whether lockTarget has a lock on it
     */
    public boolean isEntityLocked(final BasePK lockTarget, final BasePK lockedBy)
            throws EntityLockException {
        boolean isLocked;
        var lockTargetEntityInstanceId = entityInstanceControl.getEntityInstanceByBasePK(lockTarget).getPrimaryKey().getEntityId();
        
        if(CoreDebugFlags.LogEntityLocks) {
            getLog().info(">>> isEntityLocked(lockTarget=" + lockTarget + ", lockedBy=" + lockedBy + ")");
            getLog().info("--- lockTargetEntityInstanceId=" + lockTargetEntityInstanceId);
        }
        
        if(lockTargetEntityInstanceId != 0) {
            try {
                var dslContext = DslContextFactory.getInstance().getNTDslContext();
                if(lockedBy == null) {
                    var lock = dslContext
                            .select(EntityLocks.LOCK_EXPIRATION_TIME)
                            .from(EntityLocks)
                            .where(EntityLocks.LOCK_TARGET_ENTITY_INSTANCE.eq(new EntityInstancePK(lockTargetEntityInstanceId)))
                            .fetchOne();

                    isLocked = lock != null && (lock.value1() == 0 || System.currentTimeMillis() < lock.value1());
                } else {
                    var lockedByEntityInstanceId = entityInstanceControl.getEntityInstanceByBasePK(lockedBy).getPrimaryKey().getEntityId();
                    if(CoreDebugFlags.LogEntityLocks) {
                        getLog().info("--- lockedByEntityInstanceId=" + lockedByEntityInstanceId);
                    }
                    
                    if(lockedByEntityInstanceId != 0) {
                        var lock = dslContext
                                .select(EntityLocks.LOCK_EXPIRATION_TIME)
                                .from(EntityLocks)
                                .where(EntityLocks.LOCK_TARGET_ENTITY_INSTANCE.eq(new EntityInstancePK(lockTargetEntityInstanceId)),
                                        EntityLocks.LOCKED_BY_ENTITY_INSTANCE.eq(new EntityInstancePK(lockedByEntityInstanceId)))
                                .fetchOne();

                        isLocked = lock != null && (lock.value1() == 0 || System.currentTimeMillis() < lock.value1());
                    } else {
                        throw new EntityLockException(new IllegalArgumentException());
                    }
                }
                
            } catch (DataAccessException se) {
                throw new EntityLockException(se);
            }
        } else {
            isLocked = false;
        }
        
        if(CoreDebugFlags.LogEntityLocks) {
            getLog().info("<<< isLocked=" + isLocked);
        }
        
        return isLocked;
    }
    
    public void removeEntityLocksByLockExpirationTime(final Long lockExpirationTime) {
        try {
            session.getDslContext()
                    .deleteFrom(EntityLocks)
                    .where(EntityLocks.LOCK_EXPIRATION_TIME.lt(lockExpirationTime))
                    .execute();
        } catch(DataAccessException e) {
            throw new PersistenceDatabaseException(e);
        }
    }
    
}
