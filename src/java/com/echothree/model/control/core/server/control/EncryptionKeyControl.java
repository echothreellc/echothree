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
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.server.entity.BaseEncryptionKey;
import com.echothree.model.data.core.server.entity.EntityEncryptionKey;
import com.echothree.model.data.core.server.factory.BaseEncryptionKeyFactory;
import com.echothree.model.data.core.server.factory.EntityEncryptionKeyFactory;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BaseKey;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.Sha1Utils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class EncryptionKeyControl
        extends BaseCoreControl {

    /** Creates a new instance of EncryptionKeyControl */
    protected EncryptionKeyControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Base Encryption Keys
    // --------------------------------------------------------------------------------

    public BaseEncryptionKey createBaseEncryptionKey(ExecutionErrorAccumulator eea, BaseKey baseKey1, BaseKey baseKey2, PartyPK createdBy) {
        var activeBaseEncryptionKey = getActiveBaseEncryptionKey();
        BaseEncryptionKey baseEncryptionKey = null;

        if(activeBaseEncryptionKey != null) {
            setBaseEncryptionKeyStatus(eea, activeBaseEncryptionKey, WorkflowDestination_BASE_ENCRYPTION_KEY_STATUS_ACTIVE_TO_INACTIVE, createdBy);
        }

        if(!eea.hasExecutionErrors()) {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.BASE_ENCRYPTION_KEY.name());
            var baseEncryptionKeyName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
            var sha1Hash = Sha1Utils.getInstance().encode(baseKey1, baseKey2);
            baseEncryptionKey = createBaseEncryptionKey(baseEncryptionKeyName, sha1Hash, createdBy);

            var entityInstance = getEntityInstanceByBaseEntity(baseEncryptionKey);
            workflowControl.addEntityToWorkflowUsingNames(null, Workflow_BASE_ENCRYPTION_KEY_STATUS, entityInstance, null, null,createdBy);
        }

        return baseEncryptionKey;
    }

    public BaseEncryptionKey createBaseEncryptionKey(String baseEncryptionKeyName, String sha1Hash, BasePK createdBy) {
        var baseEncryptionKey = BaseEncryptionKeyFactory.getInstance().create(baseEncryptionKeyName,
                sha1Hash);

        sendEvent(baseEncryptionKey.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return baseEncryptionKey;
    }

    private List<BaseEncryptionKey> getBaseEncryptionKeys(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM baseencryptionkeys " +
                    "ORDER BY bek_baseencryptionkeyname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM baseencryptionkeys " +
                    "FOR UPDATE";
        }

        var ps = BaseEncryptionKeyFactory.getInstance().prepareStatement(query);

        return BaseEncryptionKeyFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
            List<BaseEncryptionKey> baseEncryptionKeys;

            try {
                var query = new StringBuilder("SELECT _ALL_ " +
                        "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, " +
                        "baseencryptionkeys, workflowentitystatuses, entitytimes " +
                        "WHERE cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                        "AND ent_activedetailid = entdt_entitytypedetailid " +
                        "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                        "AND entdt_entitytypename = ? " +
                        "AND ent_entitytypeid = eni_ent_entitytypeid AND bek_baseencryptionkeyid = eni_entityuniqueid " +
                        "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ? " +
                        "AND eni_entityinstanceid = etim_eni_entityinstanceid ");

                if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                    query.append("ORDER BY etim_createdtime DESC");
                } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                    query.append("FOR UPDATE");
                }

                var ps = BaseEncryptionKeyFactory.getInstance().prepareStatement(query.toString());

                ps.setString(1, ComponentVendors.ECHO_THREE.name());
                ps.setString(2, EntityTypes.BaseEncryptionKey.name());
                ps.setLong(3, workflowStep.getPrimaryKey().getEntityId());
                ps.setLong(4, Session.MAX_TIME);

                baseEncryptionKeys = BaseEncryptionKeyFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }

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
        BaseEncryptionKey baseEncryptionKey;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM baseencryptionkeys " +
                        "WHERE bek_baseencryptionkeyname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM baseencryptionkeys " +
                        "WHERE bek_baseencryptionkeyname = ? " +
                        "FOR UPDATE";
            }

            var ps = BaseEncryptionKeyFactory.getInstance().prepareStatement(query);

            ps.setString(1, baseEncryptionKeyName);

            baseEncryptionKey = BaseEncryptionKeyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return baseEncryptionKey;
    }

    public BaseEncryptionKey getBaseEncryptionKeyByName(String baseEncryptionKeyName) {
        return getBaseEncryptionKeyByName(baseEncryptionKeyName, EntityPermission.READ_ONLY);
    }

    public BaseEncryptionKey getBaseEncryptionKeyByNameForUpdate(String baseEncryptionKeyName) {
        return getBaseEncryptionKeyByName(baseEncryptionKeyName, EntityPermission.READ_WRITE);
    }

    private BaseEncryptionKey getBaseEncryptionKeyBySha1Hash(String sha1Hash, EntityPermission entityPermission) {
        BaseEncryptionKey baseEncryptionKey;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM baseencryptionkeys " +
                        "WHERE bek_sha1hash = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM baseencryptionkeys " +
                        "WHERE bek_sha1hash = ? " +
                        "FOR UPDATE";
            }

            var ps = BaseEncryptionKeyFactory.getInstance().prepareStatement(query);

            ps.setString(1, sha1Hash);

            baseEncryptionKey = BaseEncryptionKeyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return baseEncryptionKey;
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

    public List<BaseEncryptionKeyTransfer> getBaseEncryptionKeyTransfers(UserVisit userVisit) {
        var baseEncryptionKeys = getBaseEncryptionKeys();
        List<BaseEncryptionKeyTransfer> baseEncryptionKeyTransfers = new ArrayList<>(baseEncryptionKeys.size());

        baseEncryptionKeys.forEach((baseEncryptionKey) ->
                baseEncryptionKeyTransfers.add(baseEncryptionKeyTransferCache.getBaseEncryptionKeyTransfer(userVisit, baseEncryptionKey))
        );

        return baseEncryptionKeyTransfers;
    }

    public BaseEncryptionKeyStatusChoicesBean getBaseEncryptionKeyStatusChoices(String defaultBaseEncryptionKeyStatusChoice,
            Language language, boolean allowNullChoice, BaseEncryptionKey baseEncryptionKey, PartyPK partyPK) {
        var baseEncryptionKeyStatusChoicesBean = new BaseEncryptionKeyStatusChoicesBean();

        if(baseEncryptionKey == null) {
            workflowControl.getWorkflowEntranceChoices(baseEncryptionKeyStatusChoicesBean, defaultBaseEncryptionKeyStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(Workflow_BASE_ENCRYPTION_KEY_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
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

    public EntityEncryptionKey createEntityEncryptionKey(String entityEncryptionKeyName, Boolean isExternal, String secretKey, String initializationVector) {
        return EntityEncryptionKeyFactory.getInstance().create(entityEncryptionKeyName, isExternal, secretKey, initializationVector);
    }

    private EntityEncryptionKey getEntityEncryptionKeyByName(String entityEncryptionKeyName, EntityPermission entityPermission) {
        EntityEncryptionKey entityEncryptionKey;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityencryptionkeys " +
                        "WHERE eek_entityencryptionkeyname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityencryptionkeys " +
                        "WHERE eek_entityencryptionkeyname = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityEncryptionKeyFactory.getInstance().prepareStatement(query);

            ps.setString(1, entityEncryptionKeyName);

            entityEncryptionKey = EntityEncryptionKeyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityEncryptionKey;
    }

    public EntityEncryptionKey getEntityEncryptionKeyByName(String entityEncryptionKeyName) {
        return getEntityEncryptionKeyByName(entityEncryptionKeyName, EntityPermission.READ_ONLY);
    }

    public EntityEncryptionKey getEntityEncryptionKeyByNameForUpdate(String entityEncryptionKeyName) {
        return getEntityEncryptionKeyByName(entityEncryptionKeyName, EntityPermission.READ_WRITE);
    }

    public List<EntityEncryptionKey> getEntityEncryptionKeysForUpdate() {
        var ps = EntityEncryptionKeyFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                        "FROM entityencryptionkeys " +
                        "FOR UPDATE");

        return EntityEncryptionKeyFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
    }

    public long countEntityEncryptionKeys() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM entityencryptionkeys");
    }

}
