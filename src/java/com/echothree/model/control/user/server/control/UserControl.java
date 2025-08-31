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

package com.echothree.model.control.user.server.control;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.control.user.common.choice.RecoveryQuestionChoicesBean;
import com.echothree.model.control.user.common.choice.UserVisitGroupStatusChoicesBean;
import com.echothree.model.control.user.common.transfer.RecoveryAnswerTransfer;
import com.echothree.model.control.user.common.transfer.RecoveryQuestionDescriptionTransfer;
import com.echothree.model.control.user.common.transfer.RecoveryQuestionTransfer;
import com.echothree.model.control.user.common.transfer.UserKeyTransfer;
import com.echothree.model.control.user.common.transfer.UserLoginPasswordEncoderTypeTransfer;
import com.echothree.model.control.user.common.transfer.UserLoginPasswordTransfer;
import com.echothree.model.control.user.common.transfer.UserLoginPasswordTypeTransfer;
import com.echothree.model.control.user.common.transfer.UserLoginTransfer;
import com.echothree.model.control.user.common.transfer.UserSessionTransfer;
import com.echothree.model.control.user.common.transfer.UserVisitGroupTransfer;
import com.echothree.model.control.user.common.transfer.UserVisitTransfer;
import static com.echothree.model.control.user.common.workflow.UserVisitGroupStatusConstants.WorkflowStep_USER_VISIT_GROUP_STATUS_ACTIVE;
import static com.echothree.model.control.user.common.workflow.UserVisitGroupStatusConstants.Workflow_USER_VISIT_GROUP_STATUS;
import com.echothree.model.control.user.server.transfer.UserTransferCaches;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.core.server.entity.Command;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.common.pk.RecoveryQuestionPK;
import com.echothree.model.data.user.common.pk.UserKeyDetailPK;
import com.echothree.model.data.user.common.pk.UserVisitGroupPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.RecoveryAnswer;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.entity.RecoveryQuestionDescription;
import com.echothree.model.data.user.server.entity.UserKey;
import com.echothree.model.data.user.server.entity.UserKeyDetail;
import com.echothree.model.data.user.server.entity.UserKeyStatus;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.user.server.entity.UserLoginPassword;
import com.echothree.model.data.user.server.entity.UserLoginPasswordEncoderType;
import com.echothree.model.data.user.server.entity.UserLoginPasswordEncoderTypeDescription;
import com.echothree.model.data.user.server.entity.UserLoginPasswordString;
import com.echothree.model.data.user.server.entity.UserLoginPasswordType;
import com.echothree.model.data.user.server.entity.UserLoginPasswordTypeDescription;
import com.echothree.model.data.user.server.entity.UserLoginStatus;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.user.server.entity.UserVisitCommand;
import com.echothree.model.data.user.server.entity.UserVisitGroup;
import com.echothree.model.data.user.server.entity.UserVisitStatus;
import com.echothree.model.data.user.server.factory.RecoveryAnswerDetailFactory;
import com.echothree.model.data.user.server.factory.RecoveryAnswerFactory;
import com.echothree.model.data.user.server.factory.RecoveryQuestionDescriptionFactory;
import com.echothree.model.data.user.server.factory.RecoveryQuestionDetailFactory;
import com.echothree.model.data.user.server.factory.RecoveryQuestionFactory;
import com.echothree.model.data.user.server.factory.UserKeyDetailFactory;
import com.echothree.model.data.user.server.factory.UserKeyFactory;
import com.echothree.model.data.user.server.factory.UserKeyStatusFactory;
import com.echothree.model.data.user.server.factory.UserLoginFactory;
import com.echothree.model.data.user.server.factory.UserLoginPasswordEncoderTypeDescriptionFactory;
import com.echothree.model.data.user.server.factory.UserLoginPasswordEncoderTypeFactory;
import com.echothree.model.data.user.server.factory.UserLoginPasswordFactory;
import com.echothree.model.data.user.server.factory.UserLoginPasswordStringFactory;
import com.echothree.model.data.user.server.factory.UserLoginPasswordTypeDescriptionFactory;
import com.echothree.model.data.user.server.factory.UserLoginPasswordTypeFactory;
import com.echothree.model.data.user.server.factory.UserLoginStatusFactory;
import com.echothree.model.data.user.server.factory.UserSessionFactory;
import com.echothree.model.data.user.server.factory.UserVisitCommandFactory;
import com.echothree.model.data.user.server.factory.UserVisitFactory;
import com.echothree.model.data.user.server.factory.UserVisitGroupDetailFactory;
import com.echothree.model.data.user.server.factory.UserVisitGroupFactory;
import com.echothree.model.data.user.server.factory.UserVisitStatusFactory;
import com.echothree.model.data.user.server.value.RecoveryAnswerDetailValue;
import com.echothree.model.data.user.server.value.RecoveryQuestionDescriptionValue;
import com.echothree.model.data.user.server.value.RecoveryQuestionDetailValue;
import com.echothree.model.data.user.server.value.UserKeyDetailValue;
import com.echothree.model.data.user.server.value.UserLoginPasswordStringValue;
import com.echothree.model.data.user.server.value.UserLoginValue;
import com.echothree.model.data.user.server.value.UserVisitGroupDetailValue;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EncryptionUtils;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.Sha1Utils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserControl
        extends BaseModelControl {
    
    /** Creates a new instance of UserControl */
    public UserControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   User Transfer Caches
    // --------------------------------------------------------------------------------
    
    private UserTransferCaches userTransferCaches;
    
    public UserTransferCaches getUserTransferCaches(UserVisit userVisit) {
        if(userTransferCaches == null) {
            userTransferCaches = new UserTransferCaches(userVisit, this);
        }
        
        return userTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   User Keys
    // --------------------------------------------------------------------------------
    
    protected static final String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-=";
    protected static final char []characterArray = characters.toCharArray();
    protected static final int characterCount = characters.length();
    
    public UserKey createUserKey() {
        UserKey userKey;
        String userKeyName;
        
        do {
            var userKeyNameBuilder = new StringBuilder(40);
            var random = EncryptionUtils.getInstance().getRandom();
            
            for(var i = 0; i < 40; i++) {
                userKeyNameBuilder.append(characterArray[random.nextInt(characterCount)]);
            }
            
            userKeyName = userKeyNameBuilder.toString();
            var userKeyDetail = getUserKeyDetailByName(userKeyName);
            userKey = userKeyDetail == null? null: userKeyDetail.getUserKey();
        } while(userKey != null);
        
        userKey = UserKeyFactory.getInstance().create();
        var userKeyDetail = UserKeyDetailFactory.getInstance().create(userKey, userKeyName, (Party)null, (PartyRelationship)null,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        userKey = UserKeyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, userKey.getPrimaryKey());
        userKey.setActiveDetail(userKeyDetail);
        userKey.setLastDetail(userKeyDetail);
        userKey.store();
        
        createUserKeyStatus(userKey, session.START_TIME_LONG);
        
        return userKey;
    }

    private static final Map<EntityPermission, String> getUserKeysByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM userkeys, userkeydetails " +
                "WHERE ukey_activedetailid = ukeydt_userkeydetailid AND ukeydt_par_partyid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM userkeys, userkeydetails " +
                "WHERE ukey_activedetailid = ukeydt_userkeydetailid AND ukeydt_par_partyid = ? " +
                "FOR UPDATE");
        getUserKeysByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserKey> getUserKeysByParty(Party party, EntityPermission entityPermission) {
        return UserKeyFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserKeysByPartyQueries,
                party);
    }

    public List<UserKey> getUserKeysByParty(Party party) {
        return getUserKeysByParty(party, EntityPermission.READ_ONLY);
    }

    public List<UserKey> getUserKeysByPartyForUpdate(Party party) {
        return getUserKeysByParty(party, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getUserKeysByPartyRelationshipQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM userkeys, userkeydetails " +
                "WHERE ukey_activedetailid = ukeydt_userkeydetailid AND ukeydt_prel_partyrelationshipid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM userkeys, userkeydetails " +
                "WHERE ukey_activedetailid = ukeydt_userkeydetailid AND ukeydt_prel_partyrelationshipid = ? " +
                "FOR UPDATE");
        getUserKeysByPartyRelationshipQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserKey> getUserKeysByPartyRelationship(PartyRelationship partyRelationship, EntityPermission entityPermission) {
        return UserKeyFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserKeysByPartyRelationshipQueries,
                partyRelationship);
    }

    public List<UserKey> getUserKeysByPartyRelationship(PartyRelationship partyRelationship) {
        return getUserKeysByPartyRelationship(partyRelationship, EntityPermission.READ_ONLY);
    }

    public List<UserKey> getUserKeysByPartyRelationshipForUpdate(PartyRelationship partyRelationship) {
        return getUserKeysByPartyRelationship(partyRelationship, EntityPermission.READ_WRITE);
    }

    private UserKeyDetail getUserKeyDetailByName(String userKeyName, EntityPermission entityPermission) {
        UserKeyDetail userKeyDetail;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM userkeydetails " +
                        "WHERE ukeydt_userkeyname = ? AND ukeydt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM userkeydetails " +
                        "WHERE ukeydt_userkeyname = ? AND ukeydt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UserKeyDetailFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, userKeyName);
            ps.setLong(2, Session.MAX_TIME);
            
            userKeyDetail = UserKeyDetailFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return userKeyDetail;
    }
    
    public UserKeyDetail getUserKeyDetailByName(String userKeyName) {
        return getUserKeyDetailByName(userKeyName, EntityPermission.READ_ONLY);
    }
    
    public UserKeyDetail getUserKeyDetailByNameForUpdate(String userKeyName) {
        return getUserKeyDetailByName(userKeyName, EntityPermission.READ_WRITE);
    }
    
    public UserKeyDetailValue getUserKeyDetailValueByNameForUpdate(String userKeyName) {
        var userKeyDetail = getUserKeyDetailByNameForUpdate(userKeyName);
        
        return userKeyDetail == null? null: userKeyDetail.getUserKeyDetailValue().clone();
    }
    
    public UserKeyDetailValue getUserKeyDetailValueForUpdate(UserKey userKey) {
        return userKey == null ? null : userKey.getLastDetailForUpdate().getUserKeyDetailValue().clone();
    }

    public UserKeyDetailValue getUserKeyDetailValueByPKForUpdate(UserKeyDetailPK userKeyDetailPK) {
        return UserKeyDetailFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, userKeyDetailPK).getUserKeyDetailValue().clone();
    }
    
    public UserKeyTransfer getUserKeyTransfer(UserVisit userVisit, UserKey userKey) {
        return getUserTransferCaches(userVisit).getUserKeyTransferCache().getUserKeyTransfer(userKey);
    }
    
    public void updateUserKeyFromValue(UserKeyDetailValue userKeyDetailValue) {
        var userKeyPK = userKeyDetailValue.getUserKeyPK();
        var userKey = UserKeyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, userKeyPK);
        var userKeyDetail = userKey.getLastDetailForUpdate();
        
        userKeyDetail.setThruTime(session.START_TIME_LONG);
        userKeyDetail.store();

        var partyPK = userKeyDetailValue.getPartyPK();
        var partyRelationshipPK = userKeyDetailValue.getPartyRelationshipPK();
        
        userKeyDetail = UserKeyDetailFactory.getInstance().create(userKeyPK, userKeyDetail.getUserKeyName(), partyPK, partyRelationshipPK,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        userKey.setActiveDetail(userKeyDetail);
        userKey.setLastDetail(userKeyDetail);
        userKey.store();
    }
    
    private static final Map<EntityPermission, String> getInactiveUserKeysQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM userkeys, userkeydetails, userkeystatuses "
                + "WHERE ukey_activedetailid = ukeydt_userkeydetailid AND ukeydt_ukey_userkeyid = ukeyst_userkeystatusid "
                + "AND ukeyst_lastseentime < ? - ? "
                + "ORDER BY ukey_userkeyid");
        getInactiveUserKeysQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<UserKey> getInactiveUserKeys(Long inactiveTime) {
        return UserKeyFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getInactiveUserKeysQueries,
                session.START_TIME, inactiveTime);
    }

    public void removeUserKey(final UserKey userKey) {
        for(var userVisit: getUserVisitsByUserKey(userKey)) {
            userVisit = UserVisitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, userVisit.getPrimaryKey());
            
            userVisit.setUserKey(null);
            userVisit.store();
        }
        
        userKey.remove();
    }
    
    // --------------------------------------------------------------------------------
    //   User Key Statuses
    // --------------------------------------------------------------------------------
    
    public UserKeyStatus createUserKeyStatus(UserKey userKey, Long lastSeenTime) {
        return UserKeyStatusFactory.getInstance().create(userKey, lastSeenTime);
    }
    
    public UserKeyStatus getUserKeyStatusForUpdate(UserKey userKey) {
        UserKeyStatus userKeyStatus;
        
        try {
            var ps = UserKeyStatusFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM userkeystatuses " +
                    "WHERE ukeyst_ukey_userkeyid = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, userKey.getPrimaryKey().getEntityId());
            
            userKeyStatus = UserKeyStatusFactory.getInstance().getEntityFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return userKeyStatus;
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Groups
    // --------------------------------------------------------------------------------
    
    public UserVisitGroup getActiveUserVisitGroup() {
        UserVisitGroup userVisitGroup = null;
        var workflowStep = getWorkflowControl().getWorkflowStepUsingNames(Workflow_USER_VISIT_GROUP_STATUS, WorkflowStep_USER_VISIT_GROUP_STATUS_ACTIVE);

        if(workflowStep != null) {
            List<UserVisitGroup> userVisitGroups;

            try {
                var ps = UserVisitGroupFactory.getInstance().prepareStatement(
                        "SELECT _ALL_ " +
                        "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, " +
                        "uservisitgroups, uservisitgroupdetails, workflowentitystatuses, entitytimes " +
                        "WHERE uvisgrp_activedetailid = uvisgrpdt_uservisitgroupdetailid " +
                        "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                        "AND ent_activedetailid = entdt_entitytypedetailid " +
                        "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                        "AND entdt_entitytypename = ? " +
                        "AND ent_entitytypeid = eni_ent_entitytypeid AND uvisgrp_uservisitgroupid = eni_entityuniqueid " +
                        "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ? " +
                        "AND eni_entityinstanceid = etim_eni_entityinstanceid " +
                        "ORDER BY etim_createdtime DESC");

                ps.setString(1, ComponentVendors.ECHO_THREE.name());
                ps.setString(2, EntityTypes.UserVisitGroup.name());
                ps.setLong(3, workflowStep.getPrimaryKey().getEntityId());
                ps.setLong(4, Session.MAX_TIME);

                userVisitGroups = UserVisitGroupFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }

            if(userVisitGroups != null && !userVisitGroups.isEmpty()) {
                userVisitGroup = userVisitGroups.iterator().next();
            } else {
                userVisitGroup = createUserVisitGroup(null);
            }
        }
        
        return userVisitGroup;
    }
    
    public UserVisitGroup createUserVisitGroup(BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var workflowControl = getWorkflowControl();
        UserVisitGroup userVisitGroup = null;
        var workflow = workflowControl.getWorkflowByName(Workflow_USER_VISIT_GROUP_STATUS);
        
        if(workflow != null) {
            var workflowEntrance = workflowControl.getDefaultWorkflowEntrance(workflow);
            
            if(workflowEntrance != null && (workflowControl.countWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance) > 0)) {
                var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.USER_VISIT_GROUP.name());
                var userVisitGroupName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
                
                userVisitGroup = createUserVisitGroup(userVisitGroupName, createdBy);

                var entityInstance = getEntityInstanceByBaseEntity(userVisitGroup);
                getWorkflowControl().addEntityToWorkflow(workflowEntrance, entityInstance, null, null, createdBy);
            }
        }
        
        return userVisitGroup;
    }
    
    public UserVisitGroup createUserVisitGroup(String userVisitGroupName, BasePK createdBy) {
        var userVisitGroup = UserVisitGroupFactory.getInstance().create();
        var userVisitGroupDetail = UserVisitGroupDetailFactory.getInstance().create(userVisitGroup, userVisitGroupName,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        userVisitGroup = UserVisitGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                userVisitGroup.getPrimaryKey());
        userVisitGroup.setActiveDetail(userVisitGroupDetail);
        userVisitGroup.setLastDetail(userVisitGroupDetail);
        userVisitGroup.store();
        
        sendEvent(userVisitGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return userVisitGroup;
    }

    public long countUserVisitGroups() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM uservisitgroups, uservisitgroupdetails
                WHERE uvisgrp_activedetailid = uvisgrpdt_uservisitgroupdetailid
                """);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.UserVisitGroup */
    public UserVisitGroup getUserVisitGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UserVisitGroupPK(entityInstance.getEntityUniqueId());

        return UserVisitGroupFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public UserVisitGroup getUserVisitGroupByEntityInstance(EntityInstance entityInstance) {
        return getUserVisitGroupByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UserVisitGroup getUserVisitGroupByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUserVisitGroupByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public UserVisitGroupDetailValue getUserVisitGroupDetailValueForUpdate(UserVisitGroup userVisitGroup) {
        return userVisitGroup.getLastDetailForUpdate().getUserVisitGroupDetailValue().clone();
    }
    
    public UserVisitGroup getUserVisitGroupByName(String userVisitGroupName, EntityPermission entityPermission) {
        UserVisitGroup userVisitGroup;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM uservisitgroups, uservisitgroupdetails " +
                        "WHERE uvisgrp_activedetailid = uvisgrpdt_uservisitgroupdetailid AND uvisgrpdt_uservisitgroupname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM uservisitgroups, uservisitgroupdetails " +
                        "WHERE uvisgrp_activedetailid = uvisgrpdt_uservisitgroupdetailid AND uvisgrpdt_uservisitgroupname = ? " +
                        "FOR UPDATE";
            }

            var ps = UserVisitGroupFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, userVisitGroupName);
            
            userVisitGroup = UserVisitGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return userVisitGroup;
    }
    
    public UserVisitGroup getUserVisitGroupByName(String userVisitGroupName) {
        return getUserVisitGroupByName(userVisitGroupName, EntityPermission.READ_ONLY);
    }
    
    public UserVisitGroup getUserVisitGroupByNameForUpdate(String userVisitGroupName) {
        return getUserVisitGroupByName(userVisitGroupName, EntityPermission.READ_WRITE);
    }
    
    public UserVisitGroupDetailValue getUserVisitGroupDetailValueByNameForUpdate(String userVisitGroupName) {
        return getUserVisitGroupDetailValueForUpdate(getUserVisitGroupByNameForUpdate(userVisitGroupName));
    }
    
    private List<UserVisitGroup> getUserVisitGroups(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM uservisitgroups, uservisitgroupdetails " +
                    "WHERE uvisgrp_activedetailid = uvisgrpdt_uservisitgroupdetailid " +
                    "ORDER BY uvisgrpdt_uservisitgroupname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM uservisitgroups, uservisitgroupdetails " +
                    "WHERE uvisgrp_activedetailid = uvisgrpdt_uservisitgroupdetailid " +
                    "FOR UPDATE";
        }

        var ps = UserVisitGroupFactory.getInstance().prepareStatement(query);
        
        return UserVisitGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<UserVisitGroup> getUserVisitGroups() {
        return getUserVisitGroups(EntityPermission.READ_ONLY);
    }
    
    public List<UserVisitGroup> getUserVisitGroupsForUpdate() {
        return getUserVisitGroups(EntityPermission.READ_WRITE);
    }
    
    public UserVisitGroupTransfer getUserVisitGroupTransfer(UserVisit userVisit, UserVisitGroup userVisitGroup) {
        return getUserTransferCaches(userVisit).getUserVisitGroupTransferCache().getUserVisitGroupTransfer(userVisitGroup);
    }
    
    public List<UserVisitGroupTransfer> getUserVisitGroupTransfers(UserVisit userVisit, Collection<UserVisitGroup> userVisitGroups) {
        List<UserVisitGroupTransfer> userVisitGroupTransfers = new ArrayList<>(userVisitGroups.size());
        var userVisitGroupTransferCache = getUserTransferCaches(userVisit).getUserVisitGroupTransferCache();
        
        userVisitGroups.forEach((userVisitGroup) ->
                userVisitGroupTransfers.add(userVisitGroupTransferCache.getUserVisitGroupTransfer(userVisitGroup))
        );
        
        return userVisitGroupTransfers;
    }
    
    public List<UserVisitGroupTransfer> getUserVisitGroupTransfers(UserVisit userVisit) {
        return getUserVisitGroupTransfers(userVisit, getUserVisitGroups());
    }
    
    public UserVisitGroupStatusChoicesBean getUserVisitGroupStatusChoices(String defaultUserVisitGroupStatusChoice, Language language, boolean allowNullChoice,
            UserVisitGroup userVisitGroup, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        var userVisitGroupStatusChoicesBean = new UserVisitGroupStatusChoicesBean();
        
        if(userVisitGroup == null) {
            workflowControl.getWorkflowEntranceChoices(userVisitGroupStatusChoicesBean, defaultUserVisitGroupStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(Workflow_USER_VISIT_GROUP_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(userVisitGroup.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(Workflow_USER_VISIT_GROUP_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(userVisitGroupStatusChoicesBean, defaultUserVisitGroupStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return userVisitGroupStatusChoicesBean;
    }
    
    public void setUserVisitGroupStatus(ExecutionErrorAccumulator eea, UserVisitGroup userVisitGroup, String userVisitGroupStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        var entityInstance = getEntityInstanceByBaseEntity(userVisitGroup);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(Workflow_USER_VISIT_GROUP_STATUS, entityInstance);
        var workflowDestination = userVisitGroupStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), userVisitGroupStatusChoice);
        
        if(workflowDestination != null || userVisitGroupStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownUserVisitGroupStatusChoice.name(), userVisitGroupStatusChoice);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   User Visits
    // --------------------------------------------------------------------------------
    
    public UserVisit createUserVisit(UserKey userKey, Language preferredLanguage, Currency preferredCurrency, TimeZone preferredTimeZone,
            DateTimeFormat preferredDateTimeFormat, OfferUse offerUse, AssociateReferral associateReferral, Long retainUntilTime) {
        var userVisitGroup = getActiveUserVisitGroup();

        if(preferredCurrency == null) {
            var accountingControl = Session.getModelController(AccountingControl.class);

            preferredCurrency = accountingControl.getDefaultCurrency();
        }

        if(preferredLanguage == null) {
            preferredLanguage = getPartyControl().getDefaultLanguage();
        }

        if(preferredTimeZone == null) {
            preferredTimeZone = getPartyControl().getDefaultTimeZone();
        }

        if(preferredDateTimeFormat == null) {
            preferredDateTimeFormat = getPartyControl().getDefaultDateTimeFormat();
        }

        var userVisit = UserVisitFactory.getInstance().create(userVisitGroup, userKey, preferredLanguage, preferredCurrency,
                preferredTimeZone, preferredDateTimeFormat, session.START_TIME_LONG, offerUse, associateReferral, retainUntilTime,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        createUserVisitStatus(userVisit);

        return userVisit;
    }

    public long countUserVisitsByUserVisitGroup(UserVisitGroup userVisitGroup) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM uservisits
                WHERE uvis_uvisgrp_uservisitgroupid = ? AND uvis_thrutime = ?
                """, userVisitGroup, Session.MAX_TIME);
    }

    public long countUserVisitsByUserKey(UserKey userKey) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM uservisits " +
                        "WHERE uvis_ukey_userkeyid = ? AND uvis_thrutime = ?",
                userKey, Session.MAX_TIME);
    }

    public long countUserVisitsByPreferredLanguage(Language preferredLanguage) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM uservisits " +
                        "WHERE uvis_preferredlanguageid = ? AND uvis_thrutime = ?",
                preferredLanguage, Session.MAX_TIME);
    }

    public long countUserVisitsByPreferredCurrency(Currency preferredCurrency) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM uservisits " +
                        "WHERE uvis_preferredcurrencyid = ? AND uvis_thrutime = ?",
                preferredCurrency, Session.MAX_TIME);
    }

    public long countUserVisitsByPreferredTimeZone(TimeZone preferredTimeZone) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM uservisits " +
                        "WHERE uvis_preferredtimezoneid = ? AND uvis_thrutime = ?",
                preferredTimeZone, Session.MAX_TIME);
    }

    public long countUserVisitsByPreferredDateTimeFormat(DateTimeFormat preferredDateTimeFormat) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM uservisits " +
                        "WHERE uvis_preferreddatetimeformatid = ? AND uvis_thrutime = ?",
                preferredDateTimeFormat, Session.MAX_TIME);
    }

    public long countUserVisitsByOfferUse(OfferUse offerUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM uservisits " +
                        "WHERE uvis_ofruse_offeruseid = ? AND uvis_thrutime = ?",
                offerUse, Session.MAX_TIME);
    }

    public long countUserVisitsByAssociateReferral(AssociateReferral associateReferral) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM uservisits " +
                        "WHERE uvis_ascrfr_associatereferralid = ? AND uvis_thrutime = ?",
                associateReferral, Session.MAX_TIME);
    }

    public UserVisit getUserVisitByPK(UserVisitPK userVisitPK) {
        return UserVisitFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, userVisitPK);
    }
    
    public UserVisit getUserVisitByPKForUpdate(UserVisitPK userVisitPK) {
        return UserVisitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, userVisitPK);
    }

    public void deleteUserVisit(UserVisit userVisit) {
        removeUserVisitStatusByUserVisit(userVisit);
        
        userVisit.setThruTime(session.START_TIME_LONG);
        userVisit.store();
    }

    public void removeUserVisit(UserVisit userVisit) {
        userVisit.remove();
    }
    
    /** Updates the UserVisit to have the preferred Language specified. Also updates the Party connected
     * to the UserSession, if there is one.
     */
    public void setUserVisitPreferredLanguage(final UserVisit userVisit, final Language language, final BasePK updatedBy) {
        var userSession = getUserSessionByUserVisit(userVisit);
        
        userVisit.setPreferredLanguage(language);
        
        if(userSession != null) {
            var partyPK = userSession.getPartyPK();
            
            if(partyPK != null) {
                var partyDetailValue = getPartyControl().getPartyDetailValueByPKForUpdate(userSession.getPartyPK());
                
                partyDetailValue.setPreferredLanguagePK(language.getIsDefault() ? null : language.getPrimaryKey());
                getPartyControl().updatePartyFromValue(partyDetailValue, updatedBy);
            }
        }
    }
    
    public Language getPreferredLanguageFromUserVisit(UserVisit userVisit) {
        Language language = null;
        
        if(userVisit != null) {
            language = userVisit.getPreferredLanguage();
        }
        
        if(language == null) {
            language = getPartyControl().getDefaultLanguage();
        }
        
        return language;
    }
    
    public Language getPreferredLanguageFromParty(Party party) {
        Language language = null;
        
        if(party != null) {
            language = party.getLastDetail().getPreferredLanguage();
        }
        
        if(language == null) {
            language = getPartyControl().getDefaultLanguage();
        }
        
        return language;
    }
    
    /** Updates the UserVisit to have the preferred Currency specified. Also updates the Party connected
     * to the UserSession, if there is one.
     */
    public void setUserVisitPreferredCurrency(final UserVisit userVisit, final Currency currency, final BasePK updatedBy) {
        var userSession = getUserSessionByUserVisit(userVisit);
        
        userVisit.setPreferredCurrency(currency);
        
        if(userSession != null) {
            var partyPK = userSession.getPartyPK();
            
            if(partyPK != null) {
                var partyDetailValue = getPartyControl().getPartyDetailValueByPKForUpdate(userSession.getPartyPK());
                
                partyDetailValue.setPreferredCurrencyPK(currency.getIsDefault() ? null : currency.getPrimaryKey());
                getPartyControl().updatePartyFromValue(partyDetailValue, updatedBy);
            }
        }
    }
    
    public Currency getPreferredCurrencyFromUserVisit(UserVisit userVisit) {
        Currency currency = null;
        
        if(userVisit != null) {
            currency = userVisit.getPreferredCurrency();
        }
        
        if(currency == null) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            
            currency = accountingControl.getDefaultCurrency();
        }
        
        return currency;
    }
    
    public Currency getPreferredCurrencyFromParty(Party party) {
        Currency currency = null;
        
        if(party != null) {
            currency = party.getLastDetail().getPreferredCurrency();
        }
        
        if(currency == null) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            
            currency = accountingControl.getDefaultCurrency();
        }
        
        return currency;
    }
    
    /** Updates the UserVisit to have the preferred TimeZone specified. Also updates the Party connected
     * to the UserSession, if there is one.
     */
    public void setUserVisitPreferredTimeZone(final UserVisit userVisit, final TimeZone timeZone, final BasePK updatedBy) {
        var userSession = getUserSessionByUserVisit(userVisit);

        userVisit.setPreferredTimeZone(timeZone);
        
        if(userSession != null) {
            var partyPK = userSession.getPartyPK();
            
            if(partyPK != null) {
                var partyDetailValue = getPartyControl().getPartyDetailValueByPKForUpdate(userSession.getPartyPK());
                var timeZoneDetail = timeZone.getLastDetail();

                partyDetailValue.setPreferredTimeZonePK(timeZoneDetail.getIsDefault() ? null : timeZone.getPrimaryKey());
                getPartyControl().updatePartyFromValue(partyDetailValue, updatedBy);
            }
        }
    }
    
    public TimeZone getPreferredTimeZoneFromUserVisit(UserVisit userVisit) {
        TimeZone timeZone = null;
        
        if(userVisit != null) {
            timeZone = userVisit.getPreferredTimeZone();
        }
        
        if(timeZone == null) {
            timeZone = getPartyControl().getDefaultTimeZone();
        }
        
        return timeZone;
    }
    
    public TimeZone getPreferredTimeZoneFromParty(Party party) {
        TimeZone timeZone = null;
        
        if(party != null) {
            timeZone = party.getLastDetail().getPreferredTimeZone();
        }
        
        if(timeZone == null) {
            timeZone = getPartyControl().getDefaultTimeZone();
        }
        
        return timeZone;
    }
    
    /** Updates the UserVisit to have the preferred DateTimeFormat specified. Also updates the Party connected
     * to the UserSession, if there is one.
     */
    public void setUserVisitPreferredDateTimeFormat(final UserVisit userVisit, final DateTimeFormat dateTimeFormat, final BasePK updatedBy) {
        var userSession = getUserSessionByUserVisit(userVisit);
        var dateTimeFormatDetail = dateTimeFormat.getLastDetail();
        
        userVisit.setPreferredDateTimeFormat(dateTimeFormat);
        
        if(userSession != null) {
            var partyPK = userSession.getPartyPK();
            
            if(partyPK != null) {
                var partyDetailValue = getPartyControl().getPartyDetailValueByPKForUpdate(userSession.getPartyPK());
                
                partyDetailValue.setPreferredDateTimeFormatPK(dateTimeFormatDetail.getIsDefault() ? null : dateTimeFormat.getPrimaryKey());
                getPartyControl().updatePartyFromValue(partyDetailValue, updatedBy);
            }
        }
    }
    
    public DateTimeFormat getPreferredDateTimeFormatFromUserVisit(UserVisit userVisit) {
        DateTimeFormat dateTimeFormat = null;
        
        if(userVisit != null) {
            dateTimeFormat = userVisit.getPreferredDateTimeFormat();
        }
        
        if(dateTimeFormat == null) {
            dateTimeFormat = getPartyControl().getDefaultDateTimeFormat();
        }
        
        return dateTimeFormat;
    }
    
    public DateTimeFormat getPreferredDateTimeFormatFromParty(Party party) {
        DateTimeFormat dateTimeFormat = null;
        
        if(party != null) {
            dateTimeFormat = party.getLastDetail().getPreferredDateTimeFormat();
        }
        
        if(dateTimeFormat == null) {
            dateTimeFormat = getPartyControl().getDefaultDateTimeFormat();
        }
        
        return dateTimeFormat;
    }
    
    /** Associate a Party with a UserVisit, copy all Preferred* properties to the UserVisit.
     */
    public UserSession associatePartyToUserVisit(UserVisit userVisit, Party party, PartyRelationship partyRelationship, Long identityVerifiedTime) {
        var userSession = getUserSessionByUserVisitForUpdate(userVisit);
        var partyDetail = party.getLastDetail();
        
        if(userSession != null) {
            userSession.setThruTime(session.START_TIME_LONG);
            userSession.store();
        }
        
        userSession = createUserSession(userVisit, party, partyRelationship, identityVerifiedTime);

        var preferredLanguage = partyDetail.getPreferredLanguage();
        if(preferredLanguage != null) {
            userVisit.setPreferredLanguage(preferredLanguage);
        }

        var preferredCurrency = partyDetail.getPreferredCurrency();
        if(preferredCurrency != null) {
            userVisit.setPreferredCurrency(preferredCurrency);
        }

        var timeZone = partyDetail.getPreferredTimeZone();
        if(timeZone != null) {
            userVisit.setPreferredTimeZone(timeZone);
        }

        var dateTimeFormat = partyDetail.getPreferredDateTimeFormat();
        if(dateTimeFormat != null) {
            userVisit.setPreferredDateTimeFormat(dateTimeFormat);
        }
        
        return userSession;
    }
    
    public UserVisitTransfer getUserVisitTransfer(UserVisit userVisit, UserVisit userVisitEntity) {
        return getUserTransferCaches(userVisit).getUserVisitTransferCache().getUserVisitTransfer(userVisitEntity);
    }

    private static final Map<EntityPermission, String> getAbandonedUserVisitsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM uservisits "
                + "WHERE uvis_thrutime = ? AND uvis_lastcommandtime < ? - ? "
                + "ORDER BY uvis_uservisitid");
        getAbandonedUserVisitsQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<UserVisit> getAbandonedUserVisits(Long abandonedTime) {
        return UserVisitFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getAbandonedUserVisitsQueries,
                Session.MAX_TIME, session.START_TIME, abandonedTime);
    }

    private static final Map<EntityPermission, String> getUserVisitsByUserVisitGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM uservisits "
                        + "WHERE uvis_uvisgrp_uservisitgroupid = ? AND uvis_thrutime = ? "
                        + "ORDER BY uvis_uservisitid " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM uservisits "
                        + "WHERE uvis_uvisgrp_uservisitgroupid = ? AND uvis_thrutime = ? "
                        + "FOR UPDATE");
        getUserVisitsByUserVisitGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<UserVisit> getUserVisitsByUserVisitGroup(UserVisitGroup userVisitGroup, EntityPermission entityPermission) {
        return UserVisitFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitsByUserVisitGroupQueries,
                userVisitGroup, Session.MAX_TIME);
    }

    public List<UserVisit> getUserVisitsByUserVisitGroup(UserVisitGroup userVisitGroup) {
        return getUserVisitsByUserVisitGroup(userVisitGroup, EntityPermission.READ_ONLY);
    }

    public List<UserVisit> getUserVisitsByUserVisitGroupForUpdate(UserVisitGroup userVisitGroup) {
        return getUserVisitsByUserVisitGroup(userVisitGroup, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getUserVisitsByUserKeyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM uservisits "
                + "WHERE uvis_ukey_userkeyid = ? "
                + "ORDER BY uvis_uservisitid");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM uservisits "
                + "WHERE uvis_ukey_userkeyid = ? "
                + "FOR UPDATE");
        getUserVisitsByUserKeyQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<UserVisit> getUserVisitsByUserKey(UserKey userKey, EntityPermission entityPermission) {
        return UserVisitFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitsByUserKeyQueries,
                userKey);
    }

    public List<UserVisit> getUserVisitsByUserKey(UserKey userKey) {
        return getUserVisitsByUserKey(userKey, EntityPermission.READ_ONLY);
    }

    public List<UserVisit> getUserVisitsByUserKeyForUpdate(UserKey userKey) {
        return getUserVisitsByUserKey(userKey, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getInvalidatedUserVisitsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM uservisits "
                + "WHERE uvis_retainuntiltime < ? AND uvis_thrutime <> ? "
                + "ORDER BY uvis_uservisitid");
        getInvalidatedUserVisitsQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<UserVisit> getInvalidatedUserVisits() {
        return UserVisitFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getInvalidatedUserVisitsQueries,
                session.START_TIME, Session.MAX_TIME);
    }

    // --------------------------------------------------------------------------------
    //   User Visit Statuses
    // --------------------------------------------------------------------------------
    
    public UserVisitStatus createUserVisitStatus(UserVisit userVisit) {
        return UserVisitStatusFactory.getInstance().create(userVisit, 0, 0, 0, 0);
    }
    
    private static final Map<EntityPermission, String> getUserVisitStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM uservisitstatuses "
                + "WHERE uvisst_uvis_uservisitid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM uservisitstatuses "
                + "WHERE uvisst_uvis_uservisitid = ? "
                + "FOR UPDATE");
        getUserVisitStatusQueries = Collections.unmodifiableMap(queryMap);
    }

    public UserVisitStatus getUserVisitStatus(UserVisit userVisit, EntityPermission entityPermission) {
        return UserVisitStatusFactory.getInstance().getEntityFromQuery(entityPermission, getUserVisitStatusQueries,
                userVisit);
    }
    
    public UserVisitStatus getUserVisitStatus(UserVisit userVisit) {
        return getUserVisitStatus(userVisit, EntityPermission.READ_ONLY);
    }
    
    public UserVisitStatus getUserVisitStatusForUpdate(UserVisit userVisit) {
        return getUserVisitStatus(userVisit, EntityPermission.READ_WRITE);
    }
    
    public void removeUserVisitStatusByUserVisit(UserVisit userVisit) {
        var userVisitStatus = getUserVisitStatusForUpdate(userVisit);
        
        if(userVisitStatus != null) {
            userVisitStatus.remove();
        }
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Commands
    // --------------------------------------------------------------------------------
    
    public UserVisitCommand createUserVisitCommand(UserVisit userVisit, Integer userVisitCommandSequence, Party party, Command command, Long startTime,
            Long endTime, Boolean hadSecurityErrors, Boolean hadValidationErrors, Boolean hadExecutionErrors) {
        return UserVisitCommandFactory.getInstance().create(userVisit, userVisitCommandSequence, party, command, startTime, endTime, hadSecurityErrors,
                hadValidationErrors, hadExecutionErrors);
    }
    
    // --------------------------------------------------------------------------------
    //   User Sessions
    // --------------------------------------------------------------------------------
    
    /** Use associatePartyToUserVisit to associate a Party with a UserVisit, rather than using this
     * function directly.
     */
    public UserSession createUserSession(UserVisit userVisit, Party party, PartyRelationship partyRelationship, Long identityVerifiedTime) {
        var partyType = party.getLastDetail().getPartyType();
        var partyTypeAuditPolicy = getPartyControl().getPartyTypeAuditPolicy(partyType);
        var retainUserVisitsTime = partyTypeAuditPolicy == null? null: partyTypeAuditPolicy.getLastDetail().getRetainUserVisitsTime();
        
        if(retainUserVisitsTime != null) {
            var retainUntilTime = session.START_TIME + retainUserVisitsTime;
            var currentRetainUntilTime = userVisit.getRetainUntilTime();
            
            if(currentRetainUntilTime == null || (retainUntilTime > currentRetainUntilTime)) {
                userVisit.setRetainUntilTime(retainUntilTime);
            }
        }
        
        return UserSessionFactory.getInstance().create(userVisit, party, partyRelationship, identityVerifiedTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
    }
    
    private static final Map<EntityPermission, String> getUserSessionsByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM usersessions " +
                "WHERE usess_par_partyid = ? AND usess_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM usersessions " +
                "WHERE usess_par_partyid = ? AND usess_thrutime = ? " +
                "FOR UPDATE");
        getUserSessionsByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserSession> getUserSessionsByParty(Party party, EntityPermission entityPermission) {
        return UserSessionFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserSessionsByPartyQueries,
                party, Session.MAX_TIME);
    }

    public List<UserSession> getUserSessionsByParty(Party party) {
        return getUserSessionsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<UserSession> getUserSessionsByPartyForUpdate(Party party) {
        return getUserSessionsByParty(party, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getUserSessionsByPartyRelationshipQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM usersessions " +
                "WHERE usess_prel_partyrelationshipid = ? AND usess_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM usersessions " +
                "WHERE usess_prel_partyrelationshipid = ? AND usess_thrutime = ? " +
                "FOR UPDATE");
        getUserSessionsByPartyRelationshipQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserSession> getUserSessionsByPartyRelationship(PartyRelationship partyRelationship, EntityPermission entityPermission) {
        return UserSessionFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserSessionsByPartyRelationshipQueries,
                partyRelationship, Session.MAX_TIME);
    }

    public List<UserSession> getUserSessionsByPartyRelationship(PartyRelationship partyRelationship) {
        return getUserSessionsByPartyRelationship(partyRelationship, EntityPermission.READ_ONLY);
    }

    public List<UserSession> getUserSessionsByPartyRelationshipForUpdate(PartyRelationship partyRelationship) {
        return getUserSessionsByPartyRelationship(partyRelationship, EntityPermission.READ_WRITE);
    }

    public UserSession getUserSessionByUserVisit(UserVisit userVisit) {
        UserSession userSession;
        
        try {
            var ps = UserSessionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM usersessions " +
                    "WHERE usess_uvis_uservisitid = ? AND usess_thrutime = ?");
            
            ps.setLong(1, userVisit.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            userSession = UserSessionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return userSession;
    }
    
    public UserSession getUserSessionByUserVisitForUpdate(UserVisit userVisit) {
        UserSession userSession;
        
        try {
            var ps = UserSessionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM usersessions " +
                    "WHERE usess_uvis_uservisitid = ? AND usess_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, userVisit.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            userSession = UserSessionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return userSession;
    }

    public UserSessionTransfer getUserSessionTransfer(UserVisit userVisit, UserSession userSession) {
        return getUserTransferCaches(userVisit).getUserSessionTransferCache().getUserSessionTransfer(userSession);
    }
    
    public void deleteUserSession(UserSession userSession) {
        userSession.setThruTime(session.START_TIME_LONG);
        userSession.store();
    }

    /** Calls deleteUserSession(...) for each UserSession in the List.
     */
    public void deleteUserSessions(List<UserSession> userSessions) {
        userSessions.forEach((userSession) -> {
            deleteUserSession(userSession);
        });
    }

    // --------------------------------------------------------------------------------
    //   Recovery Questions
    // --------------------------------------------------------------------------------
    
    public RecoveryQuestion createRecoveryQuestion(String recoveryQuestionName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultRecoveryQuestion = getDefaultRecoveryQuestion();
        var defaultFound = defaultRecoveryQuestion != null;
        
        if(defaultFound && isDefault) {
            var defaultRecoveryQuestionDetailValue = getDefaultRecoveryQuestionDetailValueForUpdate();
            
            defaultRecoveryQuestionDetailValue.setIsDefault(false);
            updateRecoveryQuestionFromValue(defaultRecoveryQuestionDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var recoveryQuestion = RecoveryQuestionFactory.getInstance().create();
        var recoveryQuestionDetail = RecoveryQuestionDetailFactory.getInstance().create(recoveryQuestion,
                recoveryQuestionName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        recoveryQuestion = RecoveryQuestionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                recoveryQuestion.getPrimaryKey());
        recoveryQuestion.setActiveDetail(recoveryQuestionDetail);
        recoveryQuestion.setLastDetail(recoveryQuestionDetail);
        recoveryQuestion.store();
        
        sendEvent(recoveryQuestion.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return recoveryQuestion;
    }
    
    private List<RecoveryQuestion> getRecoveryQuestions(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM recoveryquestions, recoveryquestiondetails " +
                    "WHERE rqus_activedetailid = rqusdt_recoveryquestiondetailid " +
                    "ORDER BY rqusdt_sortorder, rqusdt_recoveryquestionname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM recoveryquestions, recoveryquestiondetails " +
                    "WHERE rqus_activedetailid = rqusdt_recoveryquestiondetailid " +
                    "FOR UPDATE";
        }

        var ps = RecoveryQuestionFactory.getInstance().prepareStatement(query);
        
        return RecoveryQuestionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<RecoveryQuestion> getRecoveryQuestions() {
        return getRecoveryQuestions(EntityPermission.READ_ONLY);
    }
    
    public List<RecoveryQuestion> getRecoveryQuestionsForUpdate() {
        return getRecoveryQuestions(EntityPermission.READ_WRITE);
    }
    
    private RecoveryQuestion getDefaultRecoveryQuestion(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM recoveryquestions, recoveryquestiondetails " +
                    "WHERE rqus_activedetailid = rqusdt_recoveryquestiondetailid AND rqusdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM recoveryquestions, recoveryquestiondetails " +
                    "WHERE rqus_activedetailid = rqusdt_recoveryquestiondetailid AND rqusdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = RecoveryQuestionFactory.getInstance().prepareStatement(query);
        
        return RecoveryQuestionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public RecoveryQuestion getDefaultRecoveryQuestion() {
        return getDefaultRecoveryQuestion(EntityPermission.READ_ONLY);
    }
    
    public RecoveryQuestion getDefaultRecoveryQuestionForUpdate() {
        return getDefaultRecoveryQuestion(EntityPermission.READ_WRITE);
    }
    
    public RecoveryQuestionDetailValue getDefaultRecoveryQuestionDetailValueForUpdate() {
        return getDefaultRecoveryQuestionForUpdate().getLastDetailForUpdate().getRecoveryQuestionDetailValue().clone();
    }
    
    private RecoveryQuestion getRecoveryQuestionByName(String recoveryQuestionName, EntityPermission entityPermission) {
        RecoveryQuestion recoveryQuestion;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM recoveryquestions, recoveryquestiondetails " +
                        "WHERE rqus_activedetailid = rqusdt_recoveryquestiondetailid AND rqusdt_recoveryquestionname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM recoveryquestions, recoveryquestiondetails " +
                        "WHERE rqus_activedetailid = rqusdt_recoveryquestiondetailid AND rqusdt_recoveryquestionname = ? " +
                        "FOR UPDATE";
            }

            var ps = RecoveryQuestionFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, recoveryQuestionName);
            
            recoveryQuestion = RecoveryQuestionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return recoveryQuestion;
    }
    
    public RecoveryQuestion getRecoveryQuestionByName(String recoveryQuestionName) {
        return getRecoveryQuestionByName(recoveryQuestionName, EntityPermission.READ_ONLY);
    }
    
    public RecoveryQuestion getRecoveryQuestionByNameForUpdate(String recoveryQuestionName) {
        return getRecoveryQuestionByName(recoveryQuestionName, EntityPermission.READ_WRITE);
    }
    
    public RecoveryQuestionDetailValue getRecoveryQuestionDetailValueForUpdate(RecoveryQuestion recoveryQuestion) {
        return recoveryQuestion == null? null: recoveryQuestion.getLastDetailForUpdate().getRecoveryQuestionDetailValue().clone();
    }
    
    public RecoveryQuestionDetailValue getRecoveryQuestionDetailValueByNameForUpdate(String recoveryQuestionName) {
        return getRecoveryQuestionDetailValueForUpdate(getRecoveryQuestionByNameForUpdate(recoveryQuestionName));
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.RecoveryQuestion */
    public RecoveryQuestion getRecoveryQuestionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new RecoveryQuestionPK(entityInstance.getEntityUniqueId());

        return RecoveryQuestionFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public RecoveryQuestion getRecoveryQuestionByEntityInstance(EntityInstance entityInstance) {
        return getRecoveryQuestionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public RecoveryQuestion getRecoveryQuestionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getRecoveryQuestionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public RecoveryQuestionChoicesBean getRecoveryQuestionChoices(String defaultRecoveryQuestionChoice, Language language,
            boolean allowNullChoice) {
        var recoveryQuestions = getRecoveryQuestions();
        var size = recoveryQuestions.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(var recoveryQuestion : recoveryQuestions) {
            var recoveryQuestionDetail = recoveryQuestion.getLastDetail();
            var label = getBestRecoveryQuestionDescription(recoveryQuestion, language);
            var value = recoveryQuestionDetail.getRecoveryQuestionName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultRecoveryQuestionChoice != null && defaultRecoveryQuestionChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && recoveryQuestionDetail.getIsDefault()))
                defaultValue = value;
        }
        
        return new RecoveryQuestionChoicesBean(labels, values, defaultValue);
    }
    
    public RecoveryQuestionTransfer getRecoveryQuestionTransfer(UserVisit userVisit, RecoveryQuestion recoveryQuestion) {
        return getUserTransferCaches(userVisit).getRecoveryQuestionTransferCache().getRecoveryQuestionTransfer(recoveryQuestion);
    }
    
    public List<RecoveryQuestionTransfer> getRecoveryQuestionTransfers(UserVisit userVisit, Collection<RecoveryQuestion> recoveryQuestions) {
        List<RecoveryQuestionTransfer> recoveryQuestionTransfers = new ArrayList<>(recoveryQuestions.size());
        var recoveryQuestionTransferCache = getUserTransferCaches(userVisit).getRecoveryQuestionTransferCache();
        
        recoveryQuestions.forEach((recoveryQuestion) ->
                recoveryQuestionTransfers.add(recoveryQuestionTransferCache.getRecoveryQuestionTransfer(recoveryQuestion))
        );
        
        return recoveryQuestionTransfers;
    }
    
    public List<RecoveryQuestionTransfer> getRecoveryQuestionTransfers(UserVisit userVisit) {
        return getRecoveryQuestionTransfers(userVisit, getRecoveryQuestions());
    }
    
    private void updateRecoveryQuestionFromValue(RecoveryQuestionDetailValue recoveryQuestionDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(recoveryQuestionDetailValue.hasBeenModified()) {
            var recoveryQuestion = RecoveryQuestionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     recoveryQuestionDetailValue.getRecoveryQuestionPK());
            var recoveryQuestionDetail = recoveryQuestion.getActiveDetailForUpdate();
            
            recoveryQuestionDetail.setThruTime(session.START_TIME_LONG);
            recoveryQuestionDetail.store();

            var recoveryQuestionPK = recoveryQuestionDetail.getRecoveryQuestionPK();
            var recoveryQuestionName = recoveryQuestionDetailValue.getRecoveryQuestionName();
            var isDefault = recoveryQuestionDetailValue.getIsDefault();
            var sortOrder = recoveryQuestionDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultRecoveryQuestion = getDefaultRecoveryQuestion();
                var defaultFound = defaultRecoveryQuestion != null && !defaultRecoveryQuestion.equals(recoveryQuestion);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultRecoveryQuestionDetailValue = getDefaultRecoveryQuestionDetailValueForUpdate();
                    
                    defaultRecoveryQuestionDetailValue.setIsDefault(false);
                    updateRecoveryQuestionFromValue(defaultRecoveryQuestionDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            recoveryQuestionDetail = RecoveryQuestionDetailFactory.getInstance().create(recoveryQuestionPK,
                    recoveryQuestionName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            recoveryQuestion.setActiveDetail(recoveryQuestionDetail);
            recoveryQuestion.setLastDetail(recoveryQuestionDetail);
            
            sendEvent(recoveryQuestionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateRecoveryQuestionFromValue(RecoveryQuestionDetailValue recoveryQuestionDetailValue, BasePK updatedBy) {
        updateRecoveryQuestionFromValue(recoveryQuestionDetailValue, true, updatedBy);
    }
    
    public void deleteRecoveryQuestion(RecoveryQuestion recoveryQuestion, BasePK deletedBy) {
        deleteRecoveryQuestionDescriptionsByRecoveryQuestion(recoveryQuestion, deletedBy);
        deleteRecoveryAnswersByRecoveryQuestion(recoveryQuestion, deletedBy);

        var recoveryQuestionDetail = recoveryQuestion.getLastDetailForUpdate();
        recoveryQuestionDetail.setThruTime(session.START_TIME_LONG);
        recoveryQuestion.setActiveDetail(null);
        recoveryQuestion.store();
        
        // Check for default, and pick one if necessary
        var defaultRecoveryQuestion = getDefaultRecoveryQuestion();
        if(defaultRecoveryQuestion == null) {
            var recoveryQuestions = getRecoveryQuestionsForUpdate();
            
            if(!recoveryQuestions.isEmpty()) {
                var iter = recoveryQuestions.iterator();
                if(iter.hasNext()) {
                    defaultRecoveryQuestion = iter.next();
                }
                var recoveryQuestionDetailValue = Objects.requireNonNull(defaultRecoveryQuestion).getLastDetailForUpdate().getRecoveryQuestionDetailValue().clone();
                
                recoveryQuestionDetailValue.setIsDefault(true);
                updateRecoveryQuestionFromValue(recoveryQuestionDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(recoveryQuestion.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void removeUserSession(UserSession userSession) {
        userSession.remove();
    }
    
    // --------------------------------------------------------------------------------
    //   Recovery Question Descriptions
    // --------------------------------------------------------------------------------
    
    public RecoveryQuestionDescription createRecoveryQuestionDescription(RecoveryQuestion recoveryQuestion, Language language,
            String description, BasePK createdBy) {
        var recoveryQuestionDescription = RecoveryQuestionDescriptionFactory.getInstance().create(session,
                recoveryQuestion, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(recoveryQuestion.getPrimaryKey(), EventTypes.MODIFY, recoveryQuestionDescription.getPrimaryKey(),
                null, createdBy);
        
        return recoveryQuestionDescription;
    }
    
    private RecoveryQuestionDescription getRecoveryQuestionDescription(RecoveryQuestion recoveryQuestion, Language language,
            EntityPermission entityPermission) {
        RecoveryQuestionDescription recoveryQuestionDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM recoveryquestiondescriptions " +
                        "WHERE rqusd_rqus_recoveryquestionid = ? AND rqusd_lang_languageid = ? AND rqusd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM recoveryquestiondescriptions " +
                        "WHERE rqusd_rqus_recoveryquestionid = ? AND rqusd_lang_languageid = ? AND rqusd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = RecoveryQuestionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, recoveryQuestion.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            recoveryQuestionDescription = RecoveryQuestionDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return recoveryQuestionDescription;
    }
    
    public RecoveryQuestionDescription getRecoveryQuestionDescription(RecoveryQuestion recoveryQuestion, Language language) {
        return getRecoveryQuestionDescription(recoveryQuestion, language, EntityPermission.READ_ONLY);
    }
    
    public RecoveryQuestionDescription getRecoveryQuestionDescriptionForUpdate(RecoveryQuestion recoveryQuestion, Language language) {
        return getRecoveryQuestionDescription(recoveryQuestion, language, EntityPermission.READ_WRITE);
    }
    
    public RecoveryQuestionDescriptionValue getRecoveryQuestionDescriptionValue(RecoveryQuestionDescription recoveryQuestionDescription) {
        return recoveryQuestionDescription == null? null: recoveryQuestionDescription.getRecoveryQuestionDescriptionValue().clone();
    }
    
    public RecoveryQuestionDescriptionValue getRecoveryQuestionDescriptionValueForUpdate(RecoveryQuestion recoveryQuestion, Language language) {
        return getRecoveryQuestionDescriptionValue(getRecoveryQuestionDescriptionForUpdate(recoveryQuestion, language));
    }
    
    private List<RecoveryQuestionDescription> getRecoveryQuestionDescriptionsByRecoveryQuestion(RecoveryQuestion recoveryQuestion,
            EntityPermission entityPermission) {
        List<RecoveryQuestionDescription> recoveryQuestionDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM recoveryquestiondescriptions, languages " +
                        "WHERE rqusd_rqus_recoveryquestionid = ? AND rqusd_thrutime = ? AND rqusd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM recoveryquestiondescriptions " +
                        "WHERE rqusd_rqus_recoveryquestionid = ? AND rqusd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = RecoveryQuestionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, recoveryQuestion.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            recoveryQuestionDescriptions = RecoveryQuestionDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return recoveryQuestionDescriptions;
    }
    
    public List<RecoveryQuestionDescription> getRecoveryQuestionDescriptionsByRecoveryQuestion(RecoveryQuestion recoveryQuestion) {
        return getRecoveryQuestionDescriptionsByRecoveryQuestion(recoveryQuestion, EntityPermission.READ_ONLY);
    }
    
    public List<RecoveryQuestionDescription> getRecoveryQuestionDescriptionsByRecoveryQuestionForUpdate(RecoveryQuestion recoveryQuestion) {
        return getRecoveryQuestionDescriptionsByRecoveryQuestion(recoveryQuestion, EntityPermission.READ_WRITE);
    }
    
    public String getBestRecoveryQuestionDescription(RecoveryQuestion recoveryQuestion, Language language) {
        String description;
        var recoveryQuestionDescription = getRecoveryQuestionDescription(recoveryQuestion, language);
        
        if(recoveryQuestionDescription == null && !language.getIsDefault()) {
            recoveryQuestionDescription = getRecoveryQuestionDescription(recoveryQuestion, getPartyControl().getDefaultLanguage());
        }
        
        if(recoveryQuestionDescription == null) {
            description = recoveryQuestion.getLastDetail().getRecoveryQuestionName();
        } else {
            description = recoveryQuestionDescription.getDescription();
        }
        
        return description;
    }
    
    public RecoveryQuestionDescriptionTransfer getRecoveryQuestionDescriptionTransfer(UserVisit userVisit, RecoveryQuestionDescription recoveryQuestionDescription) {
        return getUserTransferCaches(userVisit).getRecoveryQuestionDescriptionTransferCache().getRecoveryQuestionDescriptionTransfer(recoveryQuestionDescription);
    }
    
    public List<RecoveryQuestionDescriptionTransfer> getRecoveryQuestionDescriptionTransfers(UserVisit userVisit, RecoveryQuestion recoveryQuestion) {
        var recoveryQuestionDescriptions = getRecoveryQuestionDescriptionsByRecoveryQuestion(recoveryQuestion);
        List<RecoveryQuestionDescriptionTransfer> recoveryQuestionDescriptionTransfers = new ArrayList<>(recoveryQuestionDescriptions.size());
        var recoveryQuestionDescriptionTransferCache = getUserTransferCaches(userVisit).getRecoveryQuestionDescriptionTransferCache();
        
        recoveryQuestionDescriptions.forEach((recoveryQuestionDescription) ->
                recoveryQuestionDescriptionTransfers.add(recoveryQuestionDescriptionTransferCache.getRecoveryQuestionDescriptionTransfer(recoveryQuestionDescription))
        );
        
        return recoveryQuestionDescriptionTransfers;
    }
    
    public void updateRecoveryQuestionDescriptionFromValue(RecoveryQuestionDescriptionValue recoveryQuestionDescriptionValue, BasePK updatedBy) {
        if(recoveryQuestionDescriptionValue.hasBeenModified()) {
            var recoveryQuestionDescription = RecoveryQuestionDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     recoveryQuestionDescriptionValue.getPrimaryKey());
            
            recoveryQuestionDescription.setThruTime(session.START_TIME_LONG);
            recoveryQuestionDescription.store();

            var recoveryQuestion = recoveryQuestionDescription.getRecoveryQuestion();
            var language = recoveryQuestionDescription.getLanguage();
            var description = recoveryQuestionDescriptionValue.getDescription();
            
            recoveryQuestionDescription = RecoveryQuestionDescriptionFactory.getInstance().create(recoveryQuestion, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(recoveryQuestion.getPrimaryKey(), EventTypes.MODIFY, recoveryQuestionDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteRecoveryQuestionDescription(RecoveryQuestionDescription recoveryQuestionDescription, BasePK deletedBy) {
        recoveryQuestionDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(recoveryQuestionDescription.getRecoveryQuestionPK(), EventTypes.MODIFY,
                recoveryQuestionDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteRecoveryQuestionDescriptionsByRecoveryQuestion(RecoveryQuestion recoveryQuestion, BasePK deletedBy) {
        var recoveryQuestionDescriptions = getRecoveryQuestionDescriptionsByRecoveryQuestionForUpdate(recoveryQuestion);
        
        recoveryQuestionDescriptions.forEach((recoveryQuestionDescription) -> 
                deleteRecoveryQuestionDescription(recoveryQuestionDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Recovery Answers
    // --------------------------------------------------------------------------------
    
    public RecoveryAnswer createRecoveryAnswer(Party party, RecoveryQuestion recoveryQuestion, String answer, BasePK createdBy) {
        var recoveryAnswer = RecoveryAnswerFactory.getInstance().create();
        var recoveryAnswerDetail = RecoveryAnswerDetailFactory.getInstance().create(recoveryAnswer,
                party, recoveryQuestion, answer, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        recoveryAnswer = RecoveryAnswerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, recoveryAnswer.getPrimaryKey());
        recoveryAnswer.setActiveDetail(recoveryAnswerDetail);
        recoveryAnswer.setLastDetail(recoveryAnswerDetail);
        recoveryAnswer.store();
        
        sendEvent(recoveryAnswer.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return recoveryAnswer;
    }
    
    public long countRecoveryAnswersByRecoveryQuestion(RecoveryQuestion recoveryQuestion) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM recoveryanswers, recoveryanswerdetails "
                + "WHERE rans_activedetailid = ransdt_recoveryanswerdetailid "
                + "AND ransdt_rqus_recoveryquestionid = ?",
                recoveryQuestion);
    }

    private static final Map<EntityPermission, String> getRecoveryAnswerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM recoveryanswers, recoveryanswerdetails "
                + "WHERE rans_activedetailid = ransdt_recoveryanswerdetailid "
                + "AND ransdt_par_partyid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM recoveryanswers, recoveryanswerdetails "
                + "WHERE rans_activedetailid = ransdt_recoveryanswerdetailid "
                + "AND ransdt_par_partyid = ? "
                + "FOR UPDATE");
        getRecoveryAnswerQueries = Collections.unmodifiableMap(queryMap);
    }

    private RecoveryAnswer getRecoveryAnswer(Party party, EntityPermission entityPermission) {
        return RecoveryAnswerFactory.getInstance().getEntityFromQuery(entityPermission, getRecoveryAnswerQueries,
                party);
    }

    public RecoveryAnswer getRecoveryAnswer(Party party) {
        return getRecoveryAnswer(party, EntityPermission.READ_ONLY);
    }
    
    public RecoveryAnswer getRecoveryAnswerForUpdate(Party party) {
        return getRecoveryAnswer(party, EntityPermission.READ_WRITE);
    }
    
    public RecoveryAnswerDetailValue getRecoveryAnswerDetailValueForUpdate(RecoveryAnswer recoveryAnswer) {
        return recoveryAnswer == null? null: recoveryAnswer.getLastDetailForUpdate().getRecoveryAnswerDetailValue().clone();
    }
    
    public RecoveryAnswerDetailValue getRecoveryAnswerValueForUpdate(Party party) {
        return getRecoveryAnswerDetailValueForUpdate(getRecoveryAnswerForUpdate(party));
    }
    
    private static final Map<EntityPermission, String> getRecoveryAnswersByRecoveryQuestionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM recoveryanswers, recoveryanswerdetails, party, partydetails, partytypes "
                + "WHERE rans_activedetailid = ransdt_recoveryanswerdetailid "
                + "AND ransdt_rqus_recoveryquestionid = ? "
                + "AND ransdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ptyp_partytypeid "
                + "ORDER BY ptyp_sortorder, ptyp_partytypename, pardt_partyname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM recoveryanswers, recoveryanswerdetails "
                + "WHERE rans_activedetailid = ransdt_recoveryanswerdetailid "
                + "AND ransdt_rqus_recoveryquestionid = ? "
                + "FOR UPDATE");
        getRecoveryAnswersByRecoveryQuestionQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<RecoveryAnswer> getRecoveryAnswersByRecoveryQuestion(RecoveryQuestion recoveryQuestion, EntityPermission entityPermission) {
        return RecoveryAnswerFactory.getInstance().getEntitiesFromQuery(entityPermission, getRecoveryAnswersByRecoveryQuestionQueries,
                recoveryQuestion);
    }
    
    public List<RecoveryAnswer> getRecoveryAnswersByRecoveryQuestion(RecoveryQuestion recoveryQuestion) {
        return getRecoveryAnswersByRecoveryQuestion(recoveryQuestion, EntityPermission.READ_ONLY);
    }
    
    public List<RecoveryAnswer> getRecoveryAnswersByRecoveryQuestionForUpdate(RecoveryQuestion recoveryQuestion) {
        return getRecoveryAnswersByRecoveryQuestion(recoveryQuestion, EntityPermission.READ_WRITE);
    }
    
    public RecoveryAnswerTransfer getRecoveryAnswerTransfer(UserVisit userVisit, Party party) {
        var recoveryAnswer = getRecoveryAnswer(party);
        
        return recoveryAnswer == null? null: getRecoveryAnswerTransfer(userVisit, recoveryAnswer);
    }
    
    public RecoveryAnswerTransfer getRecoveryAnswerTransfer(UserVisit userVisit, RecoveryAnswer recoveryAnswer) {
        return getUserTransferCaches(userVisit).getRecoveryAnswerTransferCache().getRecoveryAnswerTransfer(recoveryAnswer);
    }
    
    public void updateRecoveryAnswerFromValue(RecoveryAnswerDetailValue recoveryAnswerDetailValue, BasePK updatedBy) {
        if(recoveryAnswerDetailValue.hasBeenModified()) {
            var recoveryAnswer = RecoveryAnswerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, recoveryAnswerDetailValue.getRecoveryAnswerPK());
            var recoveryAnswerDetail = recoveryAnswer.getActiveDetailForUpdate();

            recoveryAnswerDetail.setThruTime(session.START_TIME_LONG);
            recoveryAnswerDetail.store();

            var recoveryAnswerPK = recoveryAnswerDetail.getRecoveryAnswerPK();
            var partyPK = recoveryAnswerDetail.getPartyPK(); // Not updated
            var recoveryQuestionPK = recoveryAnswerDetailValue.getRecoveryQuestionPK();
            var answer = recoveryAnswerDetailValue.getAnswer();

            recoveryAnswerDetail = RecoveryAnswerDetailFactory.getInstance().create(recoveryAnswerPK, partyPK, recoveryQuestionPK, answer,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            recoveryAnswer.setActiveDetail(recoveryAnswerDetail);
            recoveryAnswer.setLastDetail(recoveryAnswerDetail);

            sendEvent(recoveryAnswerPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteRecoveryAnswer(RecoveryAnswer recoveryAnswer, BasePK deletedBy) {
        var recoveryAnswerDetail = recoveryAnswer.getLastDetailForUpdate();
        recoveryAnswerDetail.setThruTime(session.START_TIME_LONG);
        recoveryAnswer.setActiveDetail(null);
        recoveryAnswer.store();
        
        sendEvent(recoveryAnswer.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteRecoveryAnswerByParty(Party party, BasePK deletedBy) {
        var recoveryAnswer = getRecoveryAnswerForUpdate(party);
        
        if(recoveryAnswer != null) {
            deleteRecoveryAnswer(recoveryAnswer, deletedBy);
        }
    }
    
    public void deleteRecoveryAnswers(List<RecoveryAnswer> recoveryAnswers, BasePK deletedBy) {
        recoveryAnswers.forEach((recoveryAnswer) -> 
                deleteRecoveryAnswer(recoveryAnswer, deletedBy)
        );
    }
    
    public void deleteRecoveryAnswersByRecoveryQuestion(RecoveryQuestion recoveryQuestion, BasePK deletedBy) {
        deleteRecoveryAnswers(getRecoveryAnswersByRecoveryQuestionForUpdate(recoveryQuestion), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   User Login Password Encoder Types
    // --------------------------------------------------------------------------------
    
    public UserLoginPasswordEncoderType createUserLoginPasswordEncoderType(String sequenceEncoderTypeName) {
        return UserLoginPasswordEncoderTypeFactory.getInstance().create(sequenceEncoderTypeName);
    }
    
    public List<UserLoginPasswordEncoderType> getUserLoginPasswordEncoderTypes() {
        var ps = UserLoginPasswordEncoderTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM userloginpasswordencodertypes " +
                "ORDER BY ulogpet_userloginpasswordencodertypename");
        
        return UserLoginPasswordEncoderTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public UserLoginPasswordEncoderType getUserLoginPasswordEncoderTypeByName(String sequenceEncoderTypeName) {
        UserLoginPasswordEncoderType sequenceEncoderType;
        
        try {
            var ps = UserLoginPasswordEncoderTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM userloginpasswordencodertypes " +
                    "WHERE ulogpet_userloginpasswordencodertypename = ?");
            
            ps.setString(1, sequenceEncoderTypeName);
            
            sequenceEncoderType = UserLoginPasswordEncoderTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceEncoderType;
    }
    
    public UserLoginPasswordEncoderTypeTransfer getUserLoginPasswordEncoderTypeTransfer(UserVisit userVisit, UserLoginPasswordEncoderType userLoginPasswordEncoderType) {
        return getUserTransferCaches(userVisit).getUserLoginPasswordEncoderTypeTransferCache().getUserLoginPasswordEncoderTypeTransfer(userLoginPasswordEncoderType);
    }

    // --------------------------------------------------------------------------------
    //   User Login Password Encoder Type Descriptions
    // --------------------------------------------------------------------------------
    
    public UserLoginPasswordEncoderTypeDescription createUserLoginPasswordEncoderTypeDescription(UserLoginPasswordEncoderType sequenceEncoderType,
            Language language, String description) {
        return UserLoginPasswordEncoderTypeDescriptionFactory.getInstance().create(sequenceEncoderType, language, description);
    }
    
    public UserLoginPasswordEncoderTypeDescription getUserLoginPasswordEncoderTypeDescription(UserLoginPasswordEncoderType sequenceEncoderType,
            Language language) {
        UserLoginPasswordEncoderTypeDescription sequenceEncoderTypeDescription;
        
        try {
            var ps = UserLoginPasswordEncoderTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM userloginpasswordencodertypedescriptions " +
                    "WHERE ulogpetd_ulogpet_userloginpasswordencodertypeid = ? AND ulogpetd_lang_languageid = ?");
            
            ps.setLong(1, sequenceEncoderType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            sequenceEncoderTypeDescription = UserLoginPasswordEncoderTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceEncoderTypeDescription;
    }
    
    public String getBestUserLoginPasswordEncoderTypeDescription(UserLoginPasswordEncoderType userLoginPasswordEncoderType, Language language) {
        String description;
        var userLoginPasswordEncoderTypeDescription = getUserLoginPasswordEncoderTypeDescription(userLoginPasswordEncoderType,
                language);
        
        if(userLoginPasswordEncoderTypeDescription == null && !language.getIsDefault()) {
            userLoginPasswordEncoderTypeDescription = getUserLoginPasswordEncoderTypeDescription(userLoginPasswordEncoderType,
                    getPartyControl().getDefaultLanguage());
        }
        
        if(userLoginPasswordEncoderTypeDescription == null) {
            description = userLoginPasswordEncoderType.getUserLoginPasswordEncoderTypeName();
        } else {
            description = userLoginPasswordEncoderTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   User Login Password Types
    // --------------------------------------------------------------------------------
    
    public UserLoginPasswordType createUserLoginPasswordType(String userLoginPasswordTypeName,
            UserLoginPasswordEncoderType userLoginPasswordEncoderType) {
        return UserLoginPasswordTypeFactory.getInstance().create(userLoginPasswordTypeName, userLoginPasswordEncoderType);
    }
    
    public UserLoginPasswordType getUserLoginPasswordTypeByName(String userLoginPasswordTypeName) {
        UserLoginPasswordType userLoginPasswordType;
        
        try {
            var ps = UserLoginPasswordTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM userloginpasswordtypes " +
                    "WHERE ulogpt_userloginpasswordtypename = ?");
            
            ps.setString(1, userLoginPasswordTypeName);
            
            userLoginPasswordType = UserLoginPasswordTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return userLoginPasswordType;
    }
    
    public UserLoginPasswordTypeTransfer getUserLoginPasswordTypeTransfer(UserVisit userVisit, UserLoginPasswordType userLoginPasswordType) {
        return getUserTransferCaches(userVisit).getUserLoginPasswordTypeTransferCache().getUserLoginPasswordTypeTransfer(userLoginPasswordType);
    }

    // --------------------------------------------------------------------------------
    //   User Login Password Type Descriptions
    // --------------------------------------------------------------------------------
    
    public UserLoginPasswordTypeDescription createUserLoginPasswordTypeDescription(UserLoginPasswordType userLoginPasswordType, Language language, String description) {
        return UserLoginPasswordTypeDescriptionFactory.getInstance().create(userLoginPasswordType, language, description);
    }
    
    public UserLoginPasswordTypeDescription getUserLoginPasswordTypeDescription(UserLoginPasswordType userLoginPasswordType, Language language) {
        UserLoginPasswordTypeDescription userLoginPasswordTypeDescription;
        
        try {
            var ps = UserLoginPasswordTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM userloginpasswordtypedescriptions " +
                    "WHERE ulogptd_ulogpt_userloginpasswordtypeid = ? AND ulogptd_lang_languageid = ?");
            
            ps.setLong(1, userLoginPasswordType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            userLoginPasswordTypeDescription = UserLoginPasswordTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return userLoginPasswordTypeDescription;
    }
    
    public String getBestUserLoginPasswordTypeDescription(UserLoginPasswordType userLoginPasswordType, Language language) {
        String description;
        var userLoginPasswordTypeDescription = getUserLoginPasswordTypeDescription(userLoginPasswordType,
                language);

        if(userLoginPasswordTypeDescription == null && !language.getIsDefault()) {
            userLoginPasswordTypeDescription = getUserLoginPasswordTypeDescription(userLoginPasswordType,
                    getPartyControl().getDefaultLanguage());
        }

        if(userLoginPasswordTypeDescription == null) {
            description = userLoginPasswordType.getUserLoginPasswordTypeName();
        } else {
            description = userLoginPasswordTypeDescription.getDescription();
        }

        return description;
    }

    // --------------------------------------------------------------------------------
    //   User Login Passwords
    // --------------------------------------------------------------------------------
    
    public UserLoginPassword createUserLoginPassword(Party party, UserLoginPasswordType userLoginPasswordType, BasePK createdBy) {
        var userLoginPassword = UserLoginPasswordFactory.getInstance().create(party, userLoginPasswordType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, userLoginPassword.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return userLoginPassword;
    }
    
    public long countUserLoginPasswords(Party party, UserLoginPasswordType userLoginPasswordType) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM userloginpasswords "
                + "WHERE ulogp_par_partyid = ? AND ulogp_ulogpt_userloginpasswordtypeid = ? AND ulogp_thrutime = ?",
                party, userLoginPasswordType, Session.MAX_TIME);
    }
    
    private static final Map<EntityPermission, String> getUserLoginPasswordQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM userloginpasswords "
                + "WHERE ulogp_par_partyid = ? AND ulogp_ulogpt_userloginpasswordtypeid = ? AND ulogp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM userloginpasswords "
                + "WHERE ulogp_par_partyid = ? AND ulogp_ulogpt_userloginpasswordtypeid = ? AND ulogp_thrutime = ? "
                + "FOR UPDATE");
        getUserLoginPasswordQueries = Collections.unmodifiableMap(queryMap);
    }

    private UserLoginPassword getUserLoginPassword(Party party, UserLoginPasswordType userLoginPasswordType, EntityPermission entityPermission) {
        return UserLoginPasswordFactory.getInstance().getEntityFromQuery(entityPermission, getUserLoginPasswordQueries,
                party, userLoginPasswordType, Session.MAX_TIME);
    }

    public UserLoginPassword getUserLoginPassword(Party party, UserLoginPasswordType userLoginPasswordType) {
        return getUserLoginPassword(party, userLoginPasswordType, EntityPermission.READ_ONLY);
    }
    
    public UserLoginPassword getUserLoginPasswordForUpdate(Party party, UserLoginPasswordType userLoginPasswordType) {
        return getUserLoginPassword(party, userLoginPasswordType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getUserLoginPasswordsByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM userloginpasswords, userloginpasswordtypes "
                + "WHERE ulogp_par_partyid = ? AND ulogp_thrutime = ? "
                + "AND ulogp_ulogpt_userloginpasswordtypeid = ulogpt_userloginpasswordtypeid "
                + "ORDER BY ulogpt_userloginpasswordtypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM userloginpasswords "
                + "WHERE ulogp_par_partyid = ? AND ulogp_thrutime = ? "
                + "FOR UPDATE");
        getUserLoginPasswordsByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserLoginPassword> getUserLoginPasswordsByParty(Party party, EntityPermission entityPermission) {
        return UserLoginPasswordFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserLoginPasswordsByPartyQueries,
                party, Session.MAX_TIME);
    }

    public List<UserLoginPassword> getUserLoginPasswordsByParty(Party party) {
        return getUserLoginPasswordsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<UserLoginPassword> getUserLoginPasswordsByPartyForUpdate(Party party) {
        return getUserLoginPasswordsByParty(party, EntityPermission.READ_WRITE);
    }

    public UserLoginPasswordTransfer getUserLoginPasswordTransfer(UserVisit userVisit, UserLoginPassword userLoginPassword) {
        return getUserTransferCaches(userVisit).getUserLoginPasswordTransferCache().getUserLoginPasswordTransfer(userLoginPassword);
    }

    public List<UserLoginPasswordTransfer> getUserLoginPasswordTransfers(UserVisit userVisit, Collection<UserLoginPassword> userLoginPasswords) {
        List<UserLoginPasswordTransfer> userLoginPasswordTransfers = new ArrayList<>(userLoginPasswords.size());
        var userLoginPasswordTransferCache = getUserTransferCaches(userVisit).getUserLoginPasswordTransferCache();

        userLoginPasswords.forEach((userLoginPassword) ->
                userLoginPasswordTransfers.add(userLoginPasswordTransferCache.getUserLoginPasswordTransfer(userLoginPassword))
        );

        return userLoginPasswordTransfers;
    }

    public List<UserLoginPasswordTransfer> getUserLoginPasswordTransfersByParty(UserVisit userVisit, Party party) {
        return getUserLoginPasswordTransfers(userVisit, getUserLoginPasswordsByParty(party));
    }

    public void deleteUserLoginPassword(UserLoginPassword userLoginPassword, BasePK deletedBy) {
        var userLoginPasswordTypeName = userLoginPassword.getUserLoginPasswordType().getUserLoginPasswordTypeName();
        
        userLoginPassword.setThruTime(session.START_TIME_LONG);
        userLoginPassword.store();
        
        if(userLoginPasswordTypeName.equals(UserConstants.UserLoginPasswordType_STRING) ||
                userLoginPasswordTypeName.equals(UserConstants.UserLoginPasswordType_RECOVERED_STRING)) {
            deleteUserLoginPasswordString(getUserLoginPasswordStringForUpdate(userLoginPassword), deletedBy);
        }
        
        sendEvent(userLoginPassword.getPartyPK(), EventTypes.MODIFY, userLoginPassword.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUserLoginPasswords(List<UserLoginPassword> userLoginPasswords, BasePK deletedBy) {
        userLoginPasswords.forEach((userLoginPassword) -> 
                deleteUserLoginPassword(userLoginPassword, deletedBy)
        );
    }
    
    public void deleteUserLoginPasswordsByParty(Party party, BasePK deletedBy) {
        deleteUserLoginPasswords(getUserLoginPasswordsByPartyForUpdate(party), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   User Login Password Strings
    // --------------------------------------------------------------------------------
    
    public UserLoginPasswordString createUserLoginPasswordString(UserLoginPassword userLoginPassword, String password, Long changedTime, Boolean wasReset,
            BasePK createdBy) {
        var userLoginPasswordEncoderType = userLoginPassword.getUserLoginPasswordType().getUserLoginPasswordEncoderType();
        String salt = null;
        
        if(userLoginPasswordEncoderType != null) {
            var userLoginPasswordEncoderTypeName = userLoginPasswordEncoderType.getUserLoginPasswordEncoderTypeName();
            
            if(userLoginPasswordEncoderTypeName.equals(UserConstants.UserLoginPasswordEncoderType_SHA1)) {
                var passwordUtils = Sha1Utils.getInstance();
                
                salt = passwordUtils.generateSalt();
                password = passwordUtils.encode(salt, password);
            }
            
            // UserLoginPasswordEncoderType_TEXT requires no further action.
        }

        var userLoginPasswordString = UserLoginPasswordStringFactory.getInstance().create(session,
                userLoginPassword, salt, password, changedTime, wasReset, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(userLoginPassword.getPartyPK(), EventTypes.MODIFY, userLoginPasswordString.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return userLoginPasswordString;
    }
    
    private UserLoginPasswordString getUserLoginPasswordString(UserLoginPassword userLoginPassword, EntityPermission entityPermission) {
        UserLoginPasswordString userLoginPasswordString;
        
        try {
            var ps = UserLoginPasswordStringFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM userloginpasswordstrings " +
                    "WHERE ulogps_ulogp_userloginpasswordid = ? AND ulogps_thrutime = ?");
            
            ps.setLong(1, userLoginPassword.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            userLoginPasswordString = UserLoginPasswordStringFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return userLoginPasswordString;
    }
    
    public UserLoginPasswordString getUserLoginPasswordString(UserLoginPassword userLoginPassword) {
        return getUserLoginPasswordString(userLoginPassword, EntityPermission.READ_ONLY);
    }
    
    public UserLoginPasswordString getUserLoginPasswordStringForUpdate(UserLoginPassword userLoginPassword) {
        return getUserLoginPasswordString(userLoginPassword, EntityPermission.READ_WRITE);
    }
    
    public UserLoginPasswordStringValue getUserLoginPasswordStringValueForUpdate(UserLoginPasswordString userLoginPasswordString) {
        return userLoginPasswordString == null? null: userLoginPasswordString.getUserLoginPasswordStringValue().clone();
    }
    
    public UserLoginPasswordStringValue getUserLoginPasswordStringValueForUpdate(UserLoginPassword userLoginPassword) {
        return getUserLoginPasswordStringValueForUpdate(getUserLoginPasswordStringForUpdate(userLoginPassword));
    }
    
    private List<UserLoginPasswordString> getUserLoginPasswordStringHistory(UserLoginPassword userLoginPassword, long limit,
            EntityPermission entityPermission) {
        List<UserLoginPasswordString> userLoginPasswordStrings;
        
        try {
            var ps = UserLoginPasswordStringFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM userloginpasswordstrings " +
                    "WHERE ulogps_ulogp_userloginpasswordid = ? ORDER BY ulogps_fromtime DESC LIMIT " + limit);
            
            ps.setLong(1, userLoginPassword.getPrimaryKey().getEntityId());
            
            userLoginPasswordStrings = UserLoginPasswordStringFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return userLoginPasswordStrings;
    }
    
    public List<UserLoginPasswordString> getUserLoginPasswordStringHistory(UserLoginPassword userLoginPassword, long limit) {
        return getUserLoginPasswordStringHistory(userLoginPassword, limit, EntityPermission.READ_ONLY);
    }
    
    public List<UserLoginPasswordString> getUserLoginPasswordStringHistoryForUpdate(UserLoginPassword userLoginPassword, long limit) {
        return getUserLoginPasswordStringHistory(userLoginPassword, limit, EntityPermission.READ_WRITE);
    }
    
    public void updateUserLoginPasswordStringFromValue(UserLoginPasswordStringValue userLoginPasswordStringValue, BasePK updatedBy) {
        if(userLoginPasswordStringValue.hasBeenModified()) {
            var userLoginPasswordString = UserLoginPasswordStringFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     userLoginPasswordStringValue.getPrimaryKey());
            
            userLoginPasswordString.setThruTime(session.START_TIME_LONG);
            userLoginPasswordString.store();

            var userLoginPassword = userLoginPasswordString.getUserLoginPassword();
            var userLoginPasswordPK = userLoginPassword.getPrimaryKey();
            var salt = userLoginPasswordStringValue.getSalt();
            var password = userLoginPasswordStringValue.getPassword();
            var changedTime = userLoginPasswordStringValue.getChangedTime();
            var wasReset = userLoginPasswordStringValue.getWasReset();
            
            if(userLoginPasswordStringValue.getSaltHasBeenModified() || userLoginPasswordStringValue.getPasswordHasBeenModified()) {
                var userLoginPasswordEncoderType = userLoginPassword.getUserLoginPasswordType().getUserLoginPasswordEncoderType();
                
                if(userLoginPasswordEncoderType != null) {
                    var userLoginPasswordEncoderTypeName = userLoginPasswordEncoderType.getUserLoginPasswordEncoderTypeName();
                    
                    if(userLoginPasswordEncoderTypeName.equals(UserConstants.UserLoginPasswordEncoderType_SHA1)) {
                        var passwordUtils = Sha1Utils.getInstance();
                        
                        salt = passwordUtils.generateSalt();
                        password = passwordUtils.encode(salt, password);
                    } else if(userLoginPasswordEncoderTypeName.equals(UserConstants.UserLoginPasswordEncoderType_TEXT)) {
                        salt = null;
                    }
                }
            }
            
            userLoginPasswordString = UserLoginPasswordStringFactory.getInstance().create(userLoginPasswordPK, salt, password, changedTime, wasReset,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(userLoginPasswordString.getUserLoginPassword().getPartyPK(), EventTypes.MODIFY, userLoginPasswordString.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUserLoginPasswordString(UserLoginPasswordString userLoginPasswordString, BasePK deletedBy) {
        userLoginPasswordString.setThruTime(session.START_TIME_LONG);
        userLoginPasswordString.store();
        
        sendEvent(userLoginPasswordString.getUserLoginPassword().getPartyPK(), EventTypes.MODIFY, userLoginPasswordString.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   User Logins
    // --------------------------------------------------------------------------------
    
    public UserLogin createUserLogin(Party party, String username, BasePK createdBy) {
        var userLogin = UserLoginFactory.getInstance().create(party, username, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, userLogin.getPrimaryKey(), null, createdBy);
        
        createUserLoginStatus(party);
        
        return userLogin;
    }
    
    private UserLogin getUserLogin(Party party, EntityPermission entityPermission) {
        UserLogin userLogin;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM userlogins " +
                        "WHERE ulog_par_partyid = ? AND ulog_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM userlogins " +
                        "WHERE ulog_par_partyid = ? AND ulog_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UserLoginFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            userLogin = UserLoginFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return userLogin;
    }
    
    public UserLogin getUserLogin(Party party) {
        return getUserLogin(party, EntityPermission.READ_ONLY);
    }
    
    public UserLogin getUserLoginForUpdate(Party party) {
        return getUserLogin(party, EntityPermission.READ_WRITE);
    }
    
    public UserLoginValue getUserLoginValue(UserLogin userLogin) {
        return userLogin == null? null: userLogin.getUserLoginValue().clone();
    }
    
    public UserLoginValue getUserLoginValueForUpdate(Party party) {
        return getUserLoginValue(getUserLoginForUpdate(party));
    }
    
    private UserLogin getUserLoginByUsername(String username, EntityPermission entityPermission) {
        UserLogin userLogin;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM userlogins " +
                        "WHERE ulog_username = ? AND ulog_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM userlogins " +
                        "WHERE ulog_username = ? AND ulog_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UserLoginFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, username);
            ps.setLong(2, Session.MAX_TIME);
            
            userLogin = UserLoginFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return userLogin;
    }
    
    public UserLogin getUserLoginByUsername(String username) {
        return getUserLoginByUsername(username, EntityPermission.READ_ONLY);
    }
    
    public UserLogin getUserLoginByUsernameForUpdate(String username) {
        return getUserLoginByUsername(username, EntityPermission.READ_WRITE);
    }
    
    public UserLoginTransfer getUserLoginTransfer(UserVisit userVisit, Party party) {
        var userLogin = getUserLogin(party);
        
        return userLogin == null? null: getUserLoginTransfer(userVisit, userLogin);
    }
    
    public UserLoginTransfer getUserLoginTransfer(UserVisit userVisit, UserLogin userLogin) {
        return getUserTransferCaches(userVisit).getUserLoginTransferCache().getUserLoginTransfer(userLogin);
    }
    
    public void updateUserLoginFromValue(UserLoginValue userLoginValue, BasePK updatedBy) {
        if(userLoginValue.hasBeenModified()) {
            var userLogin = UserLoginFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    userLoginValue.getPrimaryKey());
            
            userLogin.setThruTime(session.START_TIME_LONG);
            userLogin.store();

            var partyPK = userLogin.getPartyPK(); // Not updated
            var username = userLoginValue.getUsername();
            
            userLogin = UserLoginFactory.getInstance().create(partyPK, username, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(partyPK, EventTypes.MODIFY, userLogin.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUserLogin(UserLogin userLogin, BasePK deletedBy) {
        var party = userLogin.getParty();
        
        deleteUserLoginPasswordsByParty(party, deletedBy);
        deleteRecoveryAnswerByParty(party, deletedBy);
        removeUserLoginStatusByParty(party);
        
        userLogin.setThruTime(session.START_TIME_LONG);
        
        sendEvent(userLogin.getPartyPK(), EventTypes.MODIFY, userLogin.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUserLoginByParty(Party party, BasePK deletedBy) {
        var userLogin = getUserLoginForUpdate(party);
        
        if(userLogin != null) {
            deleteUserLogin(userLogin, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   User Login Statuses
    // --------------------------------------------------------------------------------
    
    public UserLoginStatus createUserLoginStatus(Party party) {
        return UserLoginStatusFactory.getInstance().create(party, null, 0, null, null, 0, false);
    }
    
    private UserLoginStatus getUserLoginStatus(Party party, EntityPermission entityPermission) {
        UserLoginStatus userLoginStatus;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM userloginstatuses " +
                        "WHERE ulogst_par_partyid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM userloginstatuses " +
                        "WHERE ulogst_par_partyid = ? " +
                        "FOR UPDATE";
            }

            var ps = UserLoginStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            
            userLoginStatus = UserLoginStatusFactory.getInstance().getEntityFromQuery(session,entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return userLoginStatus;
    }
    
    public UserLoginStatus getUserLoginStatus(Party party) {
        return getUserLoginStatus(party, EntityPermission.READ_ONLY);
    }
    
    public UserLoginStatus getUserLoginStatusForUpdate(Party party) {
        return getUserLoginStatus(party, EntityPermission.READ_WRITE);
    }
    
    public void removeUserLoginStatus(UserLoginStatus userLoginStatus) {
        userLoginStatus.remove();
    }
    
    public void removeUserLoginStatusByParty(Party party) {
        var userLoginStatus = getUserLoginStatusForUpdate(party);
        
        if(userLoginStatus != null) {
            removeUserLoginStatus(userLoginStatus);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Utilities
    // --------------------------------------------------------------------------------
    
    public Party getPartyFromUserVisit(UserVisit userVisit) {
        Party party = null;

        if(userVisit != null) {
            var userSession = getUserSessionByUserVisit(userVisit);

            if(userSession != null) {
                party = userSession.getParty();
            }
        }

        return party;
    }

    public Party getPartyFromUserVisitPK(UserVisitPK userVisitPK) {
        Party party = null;

        if(userVisitPK != null) {
            party = getPartyFromUserVisit(getUserVisitByPK(userVisitPK));
        }

        return party;
    }

    public PartyPK getPartyPKFromUserVisitPK(UserVisitPK userVisitPK) {
        var party = getPartyFromUserVisitPK(userVisitPK);
        return party == null? null: party.getPrimaryKey();
    }
    
}
