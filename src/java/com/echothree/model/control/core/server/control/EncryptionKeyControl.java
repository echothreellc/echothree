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

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.BaseEncryptionKeyStatusChoicesBean;
import com.echothree.model.control.core.common.transfer.BaseEncryptionKeyTransfer;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.WorkflowDestination_BASE_ENCRYPTION_KEY_STATUS_ACTIVE_TO_INACTIVE;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_ACTIVE;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.Workflow_BASE_ENCRYPTION_KEY_STATUS;
import com.echothree.model.control.core.server.transfer.BaseEncryptionKeyTransferCache;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.common.pk.BaseEncryptionKeyPK;
import com.echothree.model.data.core.server.entity.BaseEncryptionKey;
import com.echothree.model.data.core.server.entity.EntityEncryptionKey;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.factory.BaseEncryptionKeyFactory;
import com.echothree.model.data.core.server.factory.EntityEncryptionKeyFactory;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TIMES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.BaseEncryptionKeys.BaseEncryptionKeys;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityEncryptionKeys.EntityEncryptionKeys;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTimes.EntityTimes;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntityStatuses.WorkflowEntityStatuses;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BaseKey;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.Sha1Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class EncryptionKeyControl
        extends BaseModelControl {

    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    /** Creates a new instance of EncryptionKeyControl */
    protected EncryptionKeyControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Base Encryption Keys
    // --------------------------------------------------------------------------------

    @Inject
    protected BaseEncryptionKeyFactory baseEncryptionKeyFactory;

    @Inject
    BaseEncryptionKeyTransferCache baseEncryptionKeyTransferCache;

    public BaseEncryptionKey createBaseEncryptionKey(ExecutionErrorAccumulator eea, BaseKey baseKey1, BaseKey baseKey2, PartyPK createdBy) {
        var activeBaseEncryptionKey = getActiveBaseEncryptionKey();
        BaseEncryptionKey baseEncryptionKey = null;

        if(activeBaseEncryptionKey != null) {
            setBaseEncryptionKeyStatus(eea, activeBaseEncryptionKey, WorkflowDestination_BASE_ENCRYPTION_KEY_STATUS_ACTIVE_TO_INACTIVE, createdBy);
        }

        if(!eea.hasExecutionErrors()) {
            var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.BASE_ENCRYPTION_KEY.name());
            var baseEncryptionKeyName = sequenceGeneratorLogic.getNextSequenceValue(sequence);
            var sha1Hash = Sha1Utils.getInstance().encode(baseKey1, baseKey2);
            baseEncryptionKey = createBaseEncryptionKey(baseEncryptionKeyName, sha1Hash, createdBy);

            var entityInstance = getEntityInstanceByBaseEntity(baseEncryptionKey);
            workflowControl.addEntityToWorkflowUsingNames(null, Workflow_BASE_ENCRYPTION_KEY_STATUS, entityInstance, null, null,createdBy);
        }

        return baseEncryptionKey;
    }

    public BaseEncryptionKey createBaseEncryptionKey(String baseEncryptionKeyName, String sha1Hash, BasePK createdBy) {
        var baseEncryptionKey = baseEncryptionKeyFactory.create(baseEncryptionKeyName,
                sha1Hash);

        sendEvent(baseEncryptionKey.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return baseEncryptionKey;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.BaseEncryptionKey */
    public BaseEncryptionKey getBaseEncryptionKeyByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new BaseEncryptionKeyPK(entityInstance.getEntityUniqueId());

        return baseEncryptionKeyFactory.getEntityFromPK(entityPermission, pk);
    }

    public BaseEncryptionKey getBaseEncryptionKeyByEntityInstance(EntityInstance entityInstance) {
        return getBaseEncryptionKeyByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public BaseEncryptionKey getBaseEncryptionKeyByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getBaseEncryptionKeyByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countBaseEncryptionKeys() {
        return session.getDslContext()
                .selectCount()
                .from(BaseEncryptionKeys)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<BaseEncryptionKey> getBaseEncryptionKeys(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(BaseEncryptionKeys.fields())
                .from(BaseEncryptionKeys);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(BaseEncryptionKeys.BASE_ENCRYPTION_KEY_NAME),
                    BaseEncryptionKeyFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return baseEncryptionKeyFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<BaseEncryptionKey> getBaseEncryptionKeys() {
        return getBaseEncryptionKeys(EntityPermission.READ_ONLY);
    }

    public List<BaseEncryptionKey> getBaseEncryptionKeysForUpdate() {
        return getBaseEncryptionKeys(EntityPermission.READ_WRITE);
    }

    private BaseEncryptionKey getActiveBaseEncryptionKey(EntityPermission entityPermission) {
        var workflowStep = workflowControl.getWorkflowStepUsingNames(Workflow_BASE_ENCRYPTION_KEY_STATUS,
                WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_ACTIVE);
        BaseEncryptionKey baseEncryptionKey = null;

        if(workflowStep != null) {
            var query = switch(entityPermission) {
                case READ_ONLY -> session.getDslContext()
                        .select(BaseEncryptionKeys.fields())
                        .from(BaseEncryptionKeys)
                        .join(EntityInstances).on(BaseEncryptionKeys.BASE_ENCRYPTION_KEY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                        .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                        .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                        .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                        .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                        .join(WorkflowEntityStatuses).onKey(WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK)
                        .join(EntityTimes).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                        .where(ComponentVendorDetails.COMPONENT_VENDOR_NAME.eq(com.echothree.model.control.core.common.ComponentVendors.ECHO_THREE.name()),
                                EntityTypeDetails.ENTITY_TYPE_NAME.eq(com.echothree.model.control.core.common.EntityTypes.BaseEncryptionKey.name()),
                                WorkflowEntityStatuses.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                                WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                        .orderBy(EntityTimes.CREATED_TIME.desc());
                case READ_WRITE -> session.getDslContext()
                        .select(BaseEncryptionKeys.fields())
                        .from(BaseEncryptionKeys)
                        .join(EntityInstances).on(BaseEncryptionKeys.BASE_ENCRYPTION_KEY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                        .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                        .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                        .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                        .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                        .join(WorkflowEntityStatuses).onKey(WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK)
                        .join(EntityTimes).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                        .where(ComponentVendorDetails.COMPONENT_VENDOR_NAME.eq(com.echothree.model.control.core.common.ComponentVendors.ECHO_THREE.name()),
                                EntityTypeDetails.ENTITY_TYPE_NAME.eq(com.echothree.model.control.core.common.EntityTypes.BaseEncryptionKey.name()),
                                WorkflowEntityStatuses.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                                WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                        .forUpdate();
            };

            var baseEncryptionKeys = baseEncryptionKeyFactory.getEntitiesFromQuery(entityPermission, query);

            if(!baseEncryptionKeys.isEmpty()) {
                baseEncryptionKey = baseEncryptionKeys.iterator().next();
            }
        }

        return baseEncryptionKey;
    }

    public BaseEncryptionKey getActiveBaseEncryptionKey() {
        return getActiveBaseEncryptionKey(EntityPermission.READ_ONLY);
    }

    public BaseEncryptionKey getActiveBaseEncryptionKeyForUpdate() {
        return getActiveBaseEncryptionKey(EntityPermission.READ_WRITE);
    }

    private BaseEncryptionKey getBaseEncryptionKeyByName(String baseEncryptionKeyName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(BaseEncryptionKeys.fields())
                .from(BaseEncryptionKeys)
                .where(BaseEncryptionKeys.BASE_ENCRYPTION_KEY_NAME.eq(baseEncryptionKeyName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return baseEncryptionKeyFactory.getEntityFromQuery(entityPermission, query);
    }

    public BaseEncryptionKey getBaseEncryptionKeyByName(String baseEncryptionKeyName) {
        return getBaseEncryptionKeyByName(baseEncryptionKeyName, EntityPermission.READ_ONLY);
    }

    public BaseEncryptionKey getBaseEncryptionKeyByNameForUpdate(String baseEncryptionKeyName) {
        return getBaseEncryptionKeyByName(baseEncryptionKeyName, EntityPermission.READ_WRITE);
    }

    private BaseEncryptionKey getBaseEncryptionKeyBySha1Hash(String sha1Hash, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(BaseEncryptionKeys.fields())
                .from(BaseEncryptionKeys)
                .where(BaseEncryptionKeys.SHA1_HASH.eq(sha1Hash));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return baseEncryptionKeyFactory.getEntityFromQuery(entityPermission, query);
    }

    public BaseEncryptionKey getBaseEncryptionKeyBySha1Hash(String sha1Hash) {
        return getBaseEncryptionKeyBySha1Hash(sha1Hash, EntityPermission.READ_ONLY);
    }

    public BaseEncryptionKey getBaseEncryptionKeyBySha1HashForUpdate(String sha1Hash) {
        return getBaseEncryptionKeyBySha1Hash(sha1Hash, EntityPermission.READ_WRITE);
    }

    public BaseEncryptionKeyTransfer getBaseEncryptionKeyTransfer(UserVisit userVisit, BaseEncryptionKey baseEncryptionKey) {
        return baseEncryptionKeyTransferCache.getBaseEncryptionKeyTransfer(userVisit, baseEncryptionKey);
    }

    public BaseEncryptionKeyTransfer getActiveBaseEncryptionKeyTransfer(UserVisit userVisit) {
        return baseEncryptionKeyTransferCache.getBaseEncryptionKeyTransfer(userVisit, getActiveBaseEncryptionKey());
    }

    public List<BaseEncryptionKeyTransfer> getBaseEncryptionKeyTransfers(UserVisit userVisit, Collection<BaseEncryptionKey> baseEncryptionKeys) {
        List<BaseEncryptionKeyTransfer> baseEncryptionKeyTransfers = new ArrayList<>(baseEncryptionKeys.size());

        baseEncryptionKeys.forEach((baseEncryptionKey) ->
                baseEncryptionKeyTransfers.add(baseEncryptionKeyTransferCache.getBaseEncryptionKeyTransfer(userVisit, baseEncryptionKey))
        );

        return baseEncryptionKeyTransfers;
    }

    public List<BaseEncryptionKeyTransfer> getBaseEncryptionKeyTransfers(UserVisit userVisit) {
        return getBaseEncryptionKeyTransfers(userVisit, getBaseEncryptionKeys());
    }

    public BaseEncryptionKeyStatusChoicesBean getBaseEncryptionKeyStatusChoices(String defaultBaseEncryptionKeyStatusChoice,
            Language language, boolean allowNullChoice, BaseEncryptionKey baseEncryptionKey, PartyPK partyPK) {
        var baseEncryptionKeyStatusChoicesBean = new BaseEncryptionKeyStatusChoicesBean();

        if(baseEncryptionKey == null) {
            workflowControl.getWorkflowEntranceChoices(baseEncryptionKeyStatusChoicesBean, defaultBaseEncryptionKeyStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(Workflow_BASE_ENCRYPTION_KEY_STATUS), partyPK);
        } else {
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(baseEncryptionKey.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(Workflow_BASE_ENCRYPTION_KEY_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(baseEncryptionKeyStatusChoicesBean, defaultBaseEncryptionKeyStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return baseEncryptionKeyStatusChoicesBean;
    }

    public void setBaseEncryptionKeyStatus(ExecutionErrorAccumulator eea, BaseEncryptionKey baseEncryptionKey, String baseEncryptionKeyStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(baseEncryptionKey);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(Workflow_BASE_ENCRYPTION_KEY_STATUS,
                entityInstance);
        var workflowDestination = baseEncryptionKeyStatusChoice == null? null:
                workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), baseEncryptionKeyStatusChoice);

        if(workflowDestination != null || baseEncryptionKeyStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownBaseEncryptionKeyStatusChoice.name(), baseEncryptionKeyStatusChoice);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Encryption Keys
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityEncryptionKeyFactory entityEncryptionKeyFactory;

    public EntityEncryptionKey createEntityEncryptionKey(String entityEncryptionKeyNameHash, Boolean isExternal, String secretKey) {
        return entityEncryptionKeyFactory.create(entityEncryptionKeyNameHash, isExternal, secretKey);
    }

    private EntityEncryptionKey getEntityEncryptionKeyByNameHash(String entityEncryptionKeyNameHash, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityEncryptionKeys.fields())
                .from(EntityEncryptionKeys)
                .where(EntityEncryptionKeys.ENTITY_ENCRYPTION_KEY_NAME_HASH.eq(entityEncryptionKeyNameHash));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityEncryptionKeyFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityEncryptionKey getEntityEncryptionKeyByNameHash(String entityEncryptionKeyNameHash) {
        return getEntityEncryptionKeyByNameHash(entityEncryptionKeyNameHash, EntityPermission.READ_ONLY);
    }

    public EntityEncryptionKey getEntityEncryptionKeyByNameHashForUpdate(String entityEncryptionKeyNameHash) {
        return getEntityEncryptionKeyByNameHash(entityEncryptionKeyNameHash, EntityPermission.READ_WRITE);
    }

    public List<EntityEncryptionKey> getEntityEncryptionKeysForUpdate() {
        var query = session.getDslContext()
                .select(EntityEncryptionKeys.fields())
                .from(EntityEncryptionKeys)
                .forUpdate();

        return entityEncryptionKeyFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }

    public long countEntityEncryptionKeys() {
        return session.getDslContext()
                .selectCount()
                .from(EntityEncryptionKeys)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

}
