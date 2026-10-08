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

package com.echothree.model.control.user.server.control;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.EventTypes;
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
import com.echothree.model.control.user.server.transfer.RecoveryAnswerTransferCache;
import com.echothree.model.control.user.server.transfer.RecoveryQuestionDescriptionTransferCache;
import com.echothree.model.control.user.server.transfer.RecoveryQuestionTransferCache;
import com.echothree.model.control.user.server.transfer.UserKeyTransferCache;
import com.echothree.model.control.user.server.transfer.UserLoginPasswordEncoderTypeTransferCache;
import com.echothree.model.control.user.server.transfer.UserLoginPasswordTransferCache;
import com.echothree.model.control.user.server.transfer.UserLoginPasswordTypeTransferCache;
import com.echothree.model.control.user.server.transfer.UserLoginTransferCache;
import com.echothree.model.control.user.server.transfer.UserSessionTransferCache;
import com.echothree.model.control.user.server.transfer.UserVisitGroupTransferCache;
import com.echothree.model.control.user.server.transfer.UserVisitTransferCache;
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
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TIMES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTY_DETAILS_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.user.UserForeignKeys.RECOVERY_ANSWERS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.user.UserForeignKeys.RECOVERY_ANSWER_DETAILS_PARTY_FK;
import static com.echothree.model.jooq.server.keys.user.UserForeignKeys.RECOVERY_QUESTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.user.UserForeignKeys.RECOVERY_QUESTION_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.user.UserForeignKeys.USER_KEYS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.user.UserForeignKeys.USER_KEY_STATUSES_USER_KEY_FK;
import static com.echothree.model.jooq.server.keys.user.UserForeignKeys.USER_LOGIN_PASSWORDS_USER_LOGIN_PASSWORD_TYPE_FK;
import static com.echothree.model.jooq.server.keys.user.UserForeignKeys.USER_VISIT_GROUPS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTimes.EntityTimes;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.party.PartyTypes.PartyTypes;
import static com.echothree.model.jooq.server.tables.user.RecoveryAnswerDetails.RecoveryAnswerDetails;
import static com.echothree.model.jooq.server.tables.user.RecoveryAnswers.RecoveryAnswers;
import static com.echothree.model.jooq.server.tables.user.RecoveryQuestionDescriptions.RecoveryQuestionDescriptions;
import static com.echothree.model.jooq.server.tables.user.RecoveryQuestionDetails.RecoveryQuestionDetails;
import static com.echothree.model.jooq.server.tables.user.RecoveryQuestions.RecoveryQuestions;
import static com.echothree.model.jooq.server.tables.user.UserKeyDetails.UserKeyDetails;
import static com.echothree.model.jooq.server.tables.user.UserKeyStatuses.UserKeyStatuses;
import static com.echothree.model.jooq.server.tables.user.UserKeys.UserKeys;
import static com.echothree.model.jooq.server.tables.user.UserLoginPasswordEncoderTypeDescriptions.UserLoginPasswordEncoderTypeDescriptions;
import static com.echothree.model.jooq.server.tables.user.UserLoginPasswordEncoderTypes.UserLoginPasswordEncoderTypes;
import static com.echothree.model.jooq.server.tables.user.UserLoginPasswordStrings.UserLoginPasswordStrings;
import static com.echothree.model.jooq.server.tables.user.UserLoginPasswordTypeDescriptions.UserLoginPasswordTypeDescriptions;
import static com.echothree.model.jooq.server.tables.user.UserLoginPasswordTypes.UserLoginPasswordTypes;
import static com.echothree.model.jooq.server.tables.user.UserLoginPasswords.UserLoginPasswords;
import static com.echothree.model.jooq.server.tables.user.UserLoginStatuses.UserLoginStatuses;
import static com.echothree.model.jooq.server.tables.user.UserLogins.UserLogins;
import static com.echothree.model.jooq.server.tables.user.UserSessions.UserSessions;
import static com.echothree.model.jooq.server.tables.user.UserVisitGroupDetails.UserVisitGroupDetails;
import static com.echothree.model.jooq.server.tables.user.UserVisitGroups.UserVisitGroups;
import static com.echothree.model.jooq.server.tables.user.UserVisitStatuses.UserVisitStatuses;
import static com.echothree.model.jooq.server.tables.user.UserVisits.UserVisits;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntityStatuses.WorkflowEntityStatuses;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.RandomUtils;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.Sha1Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class UserControl
        extends BaseModelControl {

    @Inject
    AccountingControl accountingControl;

    @Inject
    SequenceControl sequenceControl;

    @Inject
    SequenceGeneratorLogic sequenceGeneratorLogic;

    @Inject
    RandomUtils randomUtils;

    @Inject
    Sha1Utils sha1Utils;

    /** Creates a new instance of UserControl */
    protected UserControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   User Keys
    // --------------------------------------------------------------------------------

    @Inject
    protected UserKeyDetailFactory userKeyDetailFactory;

    @Inject
    protected UserKeyFactory userKeyFactory;

    @Inject
    UserKeyTransferCache userKeyTransferCache;

    protected static final String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-=";
    protected static final char []characterArray = characters.toCharArray();
    protected static final int characterCount = characters.length();
    
    public UserKey createUserKey() {
        UserKey userKey;
        String userKeyName;
        
        do {
            var userKeyNameBuilder = new StringBuilder(40);
            var random = randomUtils.getRandom();
            
            for(var i = 0; i < 40; i++) {
                userKeyNameBuilder.append(characterArray[random.nextInt(characterCount)]);
            }
            
            userKeyName = userKeyNameBuilder.toString();
            var userKeyDetail = getUserKeyDetailByName(userKeyName);
            userKey = userKeyDetail == null? null: userKeyDetail.getUserKey();
        } while(userKey != null);
        
        userKey = userKeyFactory.create();
        var userKeyDetail = userKeyDetailFactory.create(userKey, userKeyName, (Party)null, (PartyRelationship)null,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        userKey = userKeyFactory.getEntityFromPK(EntityPermission.READ_WRITE, userKey.getPrimaryKey());
        userKey.setActiveDetail(userKeyDetail);
        userKey.setLastDetail(userKeyDetail);
        userKey.store();
        
        createUserKeyStatus(userKey, session.getStartTime());
        
        return userKey;
    }

    private List<UserKey> getUserKeysByParty(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserKeys.fields())
                .from(UserKeys)
                .join(UserKeyDetails).onKey(USER_KEYS_ACTIVE_DETAIL_FK)
                .where(UserKeyDetails.PARTY.eq(party.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UserKeyDetails.USER_KEY_NAME),
                    UserKeyFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userKeyFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<UserKey> getUserKeysByParty(Party party) {
        return getUserKeysByParty(party, EntityPermission.READ_ONLY);
    }

    public List<UserKey> getUserKeysByPartyForUpdate(Party party) {
        return getUserKeysByParty(party, EntityPermission.READ_WRITE);
    }

    private List<UserKey> getUserKeysByPartyRelationship(PartyRelationship partyRelationship, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserKeys.fields())
                .from(UserKeys)
                .join(UserKeyDetails).onKey(USER_KEYS_ACTIVE_DETAIL_FK)
                .where(UserKeyDetails.PARTY_RELATIONSHIP.eq(partyRelationship.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UserKeyDetails.USER_KEY_NAME),
                    UserKeyFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userKeyFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<UserKey> getUserKeysByPartyRelationship(PartyRelationship partyRelationship) {
        return getUserKeysByPartyRelationship(partyRelationship, EntityPermission.READ_ONLY);
    }

    public List<UserKey> getUserKeysByPartyRelationshipForUpdate(PartyRelationship partyRelationship) {
        return getUserKeysByPartyRelationship(partyRelationship, EntityPermission.READ_WRITE);
    }

    private UserKeyDetail getUserKeyDetailByName(String userKeyName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserKeyDetails.fields())
                .from(UserKeyDetails)
                .where(UserKeyDetails.USER_KEY_NAME.eq(userKeyName),
                        UserKeyDetails.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return userKeyDetailFactory.getEntityFromQuery(entityPermission, query);
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
        return userKeyDetailFactory.getEntityFromPK(EntityPermission.READ_WRITE, userKeyDetailPK).getUserKeyDetailValue().clone();
    }
    
    public UserKeyTransfer getUserKeyTransfer(UserVisit userVisit, UserKey userKey) {
        return userKeyTransferCache.getUserKeyTransfer(userVisit, userKey);
    }
    
    public void updateUserKeyFromValue(UserKeyDetailValue userKeyDetailValue) {
        var userKeyPK = userKeyDetailValue.getUserKeyPK();
        var userKey = userKeyFactory.getEntityFromPK(EntityPermission.READ_WRITE, userKeyPK);
        var userKeyDetail = userKey.getLastDetailForUpdate();
        
        userKeyDetail.setThruTime(session.getStartTime());
        userKeyDetail.store();

        var partyPK = userKeyDetailValue.getPartyPK();
        var partyRelationshipPK = userKeyDetailValue.getPartyRelationshipPK();
        
        userKeyDetail = userKeyDetailFactory.create(userKeyPK, userKeyDetail.getUserKeyName(), partyPK, partyRelationshipPK,
                session.getStartTime(), Session.MAX_TIME);
        
        userKey.setActiveDetail(userKeyDetail);
        userKey.setLastDetail(userKeyDetail);
        userKey.store();
    }
    
    public List<UserKey> getInactiveUserKeys(Long inactiveTime) {
        var query = session.applyLimit(session.getDslContext()
                .select(UserKeys.fields())
                .from(UserKeys)
                .join(UserKeyDetails).onKey(USER_KEYS_ACTIVE_DETAIL_FK)
                .join(UserKeyStatuses).onKey(USER_KEY_STATUSES_USER_KEY_FK)
                .where(UserKeyStatuses.LAST_SEEN_TIME.lt(session.getStartTime() - inactiveTime))
                .orderBy(UserKeys.USER_KEY),
                UserKeyFactory.class);

        return userKeyFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public void removeUserKey(final UserKey userKey) {
        for(var userVisit: getUserVisitsByUserKey(userKey)) {
            userVisit = userVisitFactory.getEntityFromPK(EntityPermission.READ_WRITE, userVisit.getPrimaryKey());
            
            userVisit.setUserKey(null);
            userVisit.store();
        }
        
        userKey.remove();
    }
    
    // --------------------------------------------------------------------------------
    //   User Key Statuses
    // --------------------------------------------------------------------------------

    @Inject
    protected UserKeyStatusFactory userKeyStatusFactory;

    public UserKeyStatus createUserKeyStatus(UserKey userKey, Long lastSeenTime) {
        return userKeyStatusFactory.create(userKey, lastSeenTime);
    }
    
    public UserKeyStatus getUserKeyStatusForUpdate(UserKey userKey) {
        var query = session.getDslContext()
                .select(UserKeyStatuses.fields())
                .from(UserKeyStatuses)
                .where(UserKeyStatuses.USER_KEY.eq(userKey.getPrimaryKey()))
                .forUpdate();
        
        return userKeyStatusFactory.getEntityFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected UserVisitGroupDetailFactory userVisitGroupDetailFactory;

    @Inject
    protected UserVisitGroupFactory userVisitGroupFactory;

    @Inject
    UserVisitGroupTransferCache userVisitGroupTransferCache;

    public UserVisitGroup getActiveUserVisitGroup() {
        UserVisitGroup userVisitGroup = null;
        var workflowStep = workflowControl.getWorkflowStepUsingNames(Workflow_USER_VISIT_GROUP_STATUS, WorkflowStep_USER_VISIT_GROUP_STATUS_ACTIVE);

        if(workflowStep != null) {
            var query = session.getDslContext()
                    .select(UserVisitGroups.fields())
                    .from(UserVisitGroups)
                    .join(EntityInstances).on(UserVisitGroups.USER_VISIT_GROUP.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                    .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                    .join(UserVisitGroupDetails).onKey(USER_VISIT_GROUPS_ACTIVE_DETAIL_FK)
                    .join(WorkflowEntityStatuses).onKey(WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK)
                    .join(EntityTimes).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                    .where(ComponentVendorDetails.COMPONENT_VENDOR_NAME.eq(com.echothree.model.control.core.common.ComponentVendors.ECHO_THREE.name()),
                            EntityTypeDetails.ENTITY_TYPE_NAME.eq(com.echothree.model.control.core.common.EntityTypes.UserVisitGroup.name()),
                            WorkflowEntityStatuses.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                            WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EntityTimes.CREATED_TIME.desc())
                    .limit(1);

            var userVisitGroups = userVisitGroupFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);

            if(userVisitGroups != null && !userVisitGroups.isEmpty()) {
                userVisitGroup = userVisitGroups.iterator().next();
            } else {
                userVisitGroup = createUserVisitGroup(null);
            }
        }
        
        return userVisitGroup;
    }
    
    public UserVisitGroup createUserVisitGroup(BasePK createdBy) {
        UserVisitGroup userVisitGroup = null;
        var workflow = workflowControl.getWorkflowByName(Workflow_USER_VISIT_GROUP_STATUS);
        
        if(workflow != null) {
            var workflowEntrance = workflowControl.getDefaultWorkflowEntrance(workflow);
            
            if(workflowEntrance != null && (workflowControl.countWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance) > 0)) {
                var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.USER_VISIT_GROUP.name());
                var userVisitGroupName = sequenceGeneratorLogic.getNextSequenceValue(sequence);
                
                userVisitGroup = createUserVisitGroup(userVisitGroupName, createdBy);

                var entityInstance = getEntityInstanceByBaseEntity(userVisitGroup);
                workflowControl.addEntityToWorkflow(workflowEntrance, entityInstance, null, null, createdBy);
            }
        }
        
        return userVisitGroup;
    }
    
    public UserVisitGroup createUserVisitGroup(String userVisitGroupName, BasePK createdBy) {
        var userVisitGroup = userVisitGroupFactory.create();
        var userVisitGroupDetail = userVisitGroupDetailFactory.create(userVisitGroup, userVisitGroupName,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        userVisitGroup = userVisitGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                userVisitGroup.getPrimaryKey());
        userVisitGroup.setActiveDetail(userVisitGroupDetail);
        userVisitGroup.setLastDetail(userVisitGroupDetail);
        userVisitGroup.store();
        
        sendEvent(userVisitGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return userVisitGroup;
    }

    public long countUserVisitGroups() {
        return session.getDslContext()
                .selectCount()
                .from(UserVisitGroups)
                .join(UserVisitGroupDetails).onKey(USER_VISIT_GROUPS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.UserVisitGroup */
    public UserVisitGroup getUserVisitGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UserVisitGroupPK(entityInstance.getEntityUniqueId());

        return userVisitGroupFactory.getEntityFromPK(entityPermission, pk);
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
        var baseQuery = session.getDslContext()
                .select(UserVisitGroups.fields())
                .from(UserVisitGroups)
                .join(UserVisitGroupDetails).onKey(USER_VISIT_GROUPS_ACTIVE_DETAIL_FK)
                .where(UserVisitGroupDetails.USER_VISIT_GROUP_NAME.eq(userVisitGroupName));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return userVisitGroupFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(UserVisitGroups.fields())
                .from(UserVisitGroups)
                .join(UserVisitGroupDetails).onKey(USER_VISIT_GROUPS_ACTIVE_DETAIL_FK);
        
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UserVisitGroupDetails.USER_VISIT_GROUP_NAME),
                    UserVisitGroupFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userVisitGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UserVisitGroup> getUserVisitGroups() {
        return getUserVisitGroups(EntityPermission.READ_ONLY);
    }
    
    public List<UserVisitGroup> getUserVisitGroupsForUpdate() {
        return getUserVisitGroups(EntityPermission.READ_WRITE);
    }
    
    public UserVisitGroupTransfer getUserVisitGroupTransfer(UserVisit userVisit, UserVisitGroup userVisitGroup) {
        return userVisitGroupTransferCache.getUserVisitGroupTransfer(userVisit, userVisitGroup);
    }
    
    public List<UserVisitGroupTransfer> getUserVisitGroupTransfers(UserVisit userVisit, Collection<UserVisitGroup> userVisitGroups) {
        List<UserVisitGroupTransfer> userVisitGroupTransfers = new ArrayList<>(userVisitGroups.size());
        
        userVisitGroups.forEach((userVisitGroup) ->
                userVisitGroupTransfers.add(userVisitGroupTransferCache.getUserVisitGroupTransfer(userVisit, userVisitGroup))
        );
        
        return userVisitGroupTransfers;
    }
    
    public List<UserVisitGroupTransfer> getUserVisitGroupTransfers(UserVisit userVisit) {
        return getUserVisitGroupTransfers(userVisit, getUserVisitGroups());
    }
    
    public UserVisitGroupStatusChoicesBean getUserVisitGroupStatusChoices(String defaultUserVisitGroupStatusChoice, Language language, boolean allowNullChoice,
            UserVisitGroup userVisitGroup, PartyPK partyPK) {
        var userVisitGroupStatusChoicesBean = new UserVisitGroupStatusChoicesBean();
        
        if(userVisitGroup == null) {
            workflowControl.getWorkflowEntranceChoices(userVisitGroupStatusChoicesBean, defaultUserVisitGroupStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(Workflow_USER_VISIT_GROUP_STATUS), partyPK);
        } else {
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(userVisitGroup.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(Workflow_USER_VISIT_GROUP_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(userVisitGroupStatusChoicesBean, defaultUserVisitGroupStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return userVisitGroupStatusChoicesBean;
    }
    
    public void setUserVisitGroupStatus(ExecutionErrorAccumulator eea, UserVisitGroup userVisitGroup, String userVisitGroupStatusChoice, PartyPK modifiedBy) {
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

    @Inject
    protected UserVisitFactory userVisitFactory;

    @Inject
    UserVisitTransferCache userVisitTransferCache;
    public UserVisit createUserVisit(UserKey userKey, Language preferredLanguage, Currency preferredCurrency, TimeZone preferredTimeZone,
            DateTimeFormat preferredDateTimeFormat, OfferUse offerUse, AssociateReferral associateReferral, Long retainUntilTime) {
        var userVisitGroup = getActiveUserVisitGroup();

        if(preferredCurrency == null) {
            preferredCurrency = accountingControl.getDefaultCurrency();
        }

        if(preferredLanguage == null) {
            preferredLanguage = partyControl.getDefaultLanguage();
        }

        if(preferredTimeZone == null) {
            preferredTimeZone = partyControl.getDefaultTimeZone();
        }

        if(preferredDateTimeFormat == null) {
            preferredDateTimeFormat = partyControl.getDefaultDateTimeFormat();
        }

        var userVisit = userVisitFactory.create(userVisitGroup, userKey, preferredLanguage, preferredCurrency,
                preferredTimeZone, preferredDateTimeFormat, session.getStartTime(), offerUse, associateReferral, retainUntilTime,
                session.getStartTime(), Session.MAX_TIME);

        createUserVisitStatus(userVisit);

        return userVisit;
    }

    public long countUserVisitsByUserVisitGroup(UserVisitGroup userVisitGroup) {
        return session.getDslContext()
                .selectCount()
                .from(UserVisits)
                .where(UserVisits.USER_VISIT_GROUP.eq(userVisitGroup.getPrimaryKey()),
                        UserVisits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countUserVisitsByUserKey(UserKey userKey) {
        return session.getDslContext()
                .selectCount()
                .from(UserVisits)
                .where(UserVisits.USER_KEY.eq(userKey.getPrimaryKey()),
                        UserVisits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countUserVisitsByPreferredLanguage(Language preferredLanguage) {
        return session.getDslContext()
                .selectCount()
                .from(UserVisits)
                .where(UserVisits.PREFERRED_LANGUAGE.eq(preferredLanguage.getPrimaryKey()),
                        UserVisits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countUserVisitsByPreferredCurrency(Currency preferredCurrency) {
        return session.getDslContext()
                .selectCount()
                .from(UserVisits)
                .where(UserVisits.PREFERRED_CURRENCY.eq(preferredCurrency.getPrimaryKey()),
                        UserVisits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countUserVisitsByPreferredTimeZone(TimeZone preferredTimeZone) {
        return session.getDslContext()
                .selectCount()
                .from(UserVisits)
                .where(UserVisits.PREFERRED_TIME_ZONE.eq(preferredTimeZone.getPrimaryKey()),
                        UserVisits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countUserVisitsByPreferredDateTimeFormat(DateTimeFormat preferredDateTimeFormat) {
        return session.getDslContext()
                .selectCount()
                .from(UserVisits)
                .where(UserVisits.PREFERRED_DATE_TIME_FORMAT.eq(preferredDateTimeFormat.getPrimaryKey()),
                        UserVisits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countUserVisitsByOfferUse(OfferUse offerUse) {
        return session.getDslContext()
                .selectCount()
                .from(UserVisits)
                .where(UserVisits.OFFER_USE.eq(offerUse.getPrimaryKey()),
                        UserVisits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countUserVisitsByAssociateReferral(AssociateReferral associateReferral) {
        return session.getDslContext()
                .selectCount()
                .from(UserVisits)
                .where(UserVisits.ASSOCIATE_REFERRAL.eq(associateReferral.getPrimaryKey()),
                        UserVisits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public UserVisit getUserVisitByPK(UserVisitPK userVisitPK) {
        return userVisitFactory.getEntityFromPK(EntityPermission.READ_ONLY, userVisitPK);
    }
    
    public UserVisit getUserVisitByPKForUpdate(UserVisitPK userVisitPK) {
        return userVisitFactory.getEntityFromPK(EntityPermission.READ_WRITE, userVisitPK);
    }

    public void deleteUserVisit(UserVisit userVisit) {
        removeUserVisitStatusByUserVisit(userVisit);
        
        userVisit.setThruTime(session.getStartTime());
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
                var partyDetailValue = partyControl.getPartyDetailValueByPKForUpdate(userSession.getPartyPK());
                
                partyDetailValue.setPreferredLanguagePK(language.getIsDefault() ? null : language.getPrimaryKey());
                partyControl.updatePartyFromValue(partyDetailValue, updatedBy);
            }
        }
    }
    
    public Language getPreferredLanguageFromUserVisit(UserVisit userVisit) {
        Language language = null;
        
        if(userVisit != null) {
            language = userVisit.getPreferredLanguage();
        }
        
        if(language == null) {
            language = partyControl.getDefaultLanguage();
        }
        
        return language;
    }
    
    public Language getPreferredLanguageFromParty(Party party) {
        Language language = null;
        
        if(party != null) {
            language = party.getLastDetail().getPreferredLanguage();
        }
        
        if(language == null) {
            language = partyControl.getDefaultLanguage();
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
                var partyDetailValue = partyControl.getPartyDetailValueByPKForUpdate(userSession.getPartyPK());
                
                partyDetailValue.setPreferredCurrencyPK(currency.getIsDefault() ? null : currency.getPrimaryKey());
                partyControl.updatePartyFromValue(partyDetailValue, updatedBy);
            }
        }
    }
    
    public Currency getPreferredCurrencyFromUserVisit(UserVisit userVisit) {
        Currency currency = null;
        
        if(userVisit != null) {
            currency = userVisit.getPreferredCurrency();
        }
        
        if(currency == null) {
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
                var partyDetailValue = partyControl.getPartyDetailValueByPKForUpdate(userSession.getPartyPK());
                var timeZoneDetail = timeZone.getLastDetail();

                partyDetailValue.setPreferredTimeZonePK(timeZoneDetail.getIsDefault() ? null : timeZone.getPrimaryKey());
                partyControl.updatePartyFromValue(partyDetailValue, updatedBy);
            }
        }
    }
    
    public TimeZone getPreferredTimeZoneFromUserVisit(UserVisit userVisit) {
        TimeZone timeZone = null;
        
        if(userVisit != null) {
            timeZone = userVisit.getPreferredTimeZone();
        }
        
        if(timeZone == null) {
            timeZone = partyControl.getDefaultTimeZone();
        }
        
        return timeZone;
    }
    
    public TimeZone getPreferredTimeZoneFromParty(Party party) {
        TimeZone timeZone = null;
        
        if(party != null) {
            timeZone = party.getLastDetail().getPreferredTimeZone();
        }
        
        if(timeZone == null) {
            timeZone = partyControl.getDefaultTimeZone();
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
                var partyDetailValue = partyControl.getPartyDetailValueByPKForUpdate(userSession.getPartyPK());
                
                partyDetailValue.setPreferredDateTimeFormatPK(dateTimeFormatDetail.getIsDefault() ? null : dateTimeFormat.getPrimaryKey());
                partyControl.updatePartyFromValue(partyDetailValue, updatedBy);
            }
        }
    }
    
    public DateTimeFormat getPreferredDateTimeFormatFromUserVisit(UserVisit userVisit) {
        DateTimeFormat dateTimeFormat = null;
        
        if(userVisit != null) {
            dateTimeFormat = userVisit.getPreferredDateTimeFormat();
        }
        
        if(dateTimeFormat == null) {
            dateTimeFormat = partyControl.getDefaultDateTimeFormat();
        }
        
        return dateTimeFormat;
    }
    
    public DateTimeFormat getPreferredDateTimeFormatFromParty(Party party) {
        DateTimeFormat dateTimeFormat = null;
        
        if(party != null) {
            dateTimeFormat = party.getLastDetail().getPreferredDateTimeFormat();
        }
        
        if(dateTimeFormat == null) {
            dateTimeFormat = partyControl.getDefaultDateTimeFormat();
        }
        
        return dateTimeFormat;
    }
    
    /** Associate a Party with a UserVisit, copy all Preferred* properties to the UserVisit.
     */
    public UserSession associatePartyToUserVisit(UserVisit userVisit, Party party, PartyRelationship partyRelationship, Long identityVerifiedTime) {
        var userSession = getUserSessionByUserVisitForUpdate(userVisit);
        var partyDetail = party.getLastDetail();
        
        if(userSession != null) {
            userSession.setThruTime(session.getStartTime());
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
        return userVisitTransferCache.getUserVisitTransfer(userVisit, userVisitEntity);
    }

    public List<UserVisit> getAbandonedUserVisits(Long abandonedTime) {
        var query = session.applyLimit(session.getDslContext()
                .select(UserVisits.fields())
                .from(UserVisits)
                .where(UserVisits.THRU_TIME.eq(Session.MAX_TIME),
                        UserVisits.LAST_COMMAND_TIME.lt(session.getStartTime() - abandonedTime))
                .orderBy(UserVisits.USER_VISIT),
                UserVisitFactory.class);

        return userVisitFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public List<UserVisit> getUserVisitsByUserVisitGroup(UserVisitGroup userVisitGroup, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserVisits.fields())
                .from(UserVisits)
                .where(UserVisits.USER_VISIT_GROUP.eq(userVisitGroup.getPrimaryKey()),
                        UserVisits.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UserVisits.USER_VISIT),
                    UserVisitFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userVisitFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<UserVisit> getUserVisitsByUserVisitGroup(UserVisitGroup userVisitGroup) {
        return getUserVisitsByUserVisitGroup(userVisitGroup, EntityPermission.READ_ONLY);
    }

    public List<UserVisit> getUserVisitsByUserVisitGroupForUpdate(UserVisitGroup userVisitGroup) {
        return getUserVisitsByUserVisitGroup(userVisitGroup, EntityPermission.READ_WRITE);
    }

    public List<UserVisit> getUserVisitsByUserKey(UserKey userKey, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserVisits.fields())
                .from(UserVisits)
                .where(UserVisits.USER_KEY.eq(userKey.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UserVisits.USER_VISIT),
                    UserVisitFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userVisitFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<UserVisit> getUserVisitsByUserKey(UserKey userKey) {
        return getUserVisitsByUserKey(userKey, EntityPermission.READ_ONLY);
    }

    public List<UserVisit> getUserVisitsByUserKeyForUpdate(UserKey userKey) {
        return getUserVisitsByUserKey(userKey, EntityPermission.READ_WRITE);
    }

    public List<UserVisit> getInvalidatedUserVisits() {
        var query = session.applyLimit(session.getDslContext()
                .select(UserVisits.fields())
                .from(UserVisits)
                .where(UserVisits.RETAIN_UNTIL_TIME.lt(session.getStartTime()),
                        UserVisits.THRU_TIME.ne(Session.MAX_TIME))
                .orderBy(UserVisits.USER_VISIT),
                UserVisitFactory.class);

        return userVisitFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    // --------------------------------------------------------------------------------
    //   User Visit Statuses
    // --------------------------------------------------------------------------------

    @Inject
    protected UserVisitStatusFactory userVisitStatusFactory;

    public UserVisitStatus createUserVisitStatus(UserVisit userVisit) {
        return userVisitStatusFactory.create(userVisit, 0, 0, 0, 0);
    }
    
    public UserVisitStatus getUserVisitStatus(UserVisit userVisit, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserVisitStatuses.fields())
                .from(UserVisitStatuses)
                .where(UserVisitStatuses.USER_VISIT.eq(userVisit.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userVisitStatusFactory.getEntityFromQuery(entityPermission, query);
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

    @Inject
    protected UserVisitCommandFactory userVisitCommandFactory;

    public UserVisitCommand createUserVisitCommand(UserVisit userVisit, Integer userVisitCommandSequence, Party party, Command command, Long startTime,
            Long endTime, Boolean hadSecurityErrors, Boolean hadValidationErrors, Boolean hadExecutionErrors) {
        return userVisitCommandFactory.create(userVisit, userVisitCommandSequence, party, command, startTime, endTime, hadSecurityErrors,
                hadValidationErrors, hadExecutionErrors);
    }
    
    // --------------------------------------------------------------------------------
    //   User Sessions
    // --------------------------------------------------------------------------------

    @Inject
    protected UserSessionFactory userSessionFactory;

    @Inject
    UserSessionTransferCache userSessionTransferCache;

    /** Use associatePartyToUserVisit to associate a Party with a UserVisit, rather than using this
     * function directly.
     */
    public UserSession createUserSession(UserVisit userVisit, Party party, PartyRelationship partyRelationship, Long identityVerifiedTime) {
        var partyType = party.getLastDetail().getPartyType();
        var partyTypeAuditPolicy = partyControl.getPartyTypeAuditPolicy(partyType);
        var retainUserVisitsTime = partyTypeAuditPolicy == null? null: partyTypeAuditPolicy.getLastDetail().getRetainUserVisitsTime();
        
        if(retainUserVisitsTime != null) {
            var retainUntilTime = session.getStartTime() + retainUserVisitsTime;
            var currentRetainUntilTime = userVisit.getRetainUntilTime();
            
            if(currentRetainUntilTime == null || (retainUntilTime > currentRetainUntilTime)) {
                userVisit.setRetainUntilTime(retainUntilTime);
            }
        }
        
        return userSessionFactory.create(userVisit, party, partyRelationship, identityVerifiedTime, session.getStartTime(), Session.MAX_TIME);
    }
    
    private List<UserSession> getUserSessionsByParty(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserSessions.fields())
                .from(UserSessions)
                .where(UserSessions.PARTY.eq(party.getPrimaryKey()),
                        UserSessions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UserSessions.FROM_TIME),
                    UserSessionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userSessionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<UserSession> getUserSessionsByParty(Party party) {
        return getUserSessionsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<UserSession> getUserSessionsByPartyForUpdate(Party party) {
        return getUserSessionsByParty(party, EntityPermission.READ_WRITE);
    }

    private List<UserSession> getUserSessionsByPartyRelationship(PartyRelationship partyRelationship, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserSessions.fields())
                .from(UserSessions)
                .where(UserSessions.PARTY_RELATIONSHIP.eq(partyRelationship.getPrimaryKey()),
                        UserSessions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UserSessions.FROM_TIME),
                    UserSessionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userSessionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<UserSession> getUserSessionsByPartyRelationship(PartyRelationship partyRelationship) {
        return getUserSessionsByPartyRelationship(partyRelationship, EntityPermission.READ_ONLY);
    }

    public List<UserSession> getUserSessionsByPartyRelationshipForUpdate(PartyRelationship partyRelationship) {
        return getUserSessionsByPartyRelationship(partyRelationship, EntityPermission.READ_WRITE);
    }

    public UserSession getUserSessionByUserVisit(UserVisit userVisit) {
        var query = session.getDslContext()
                .select(UserSessions.fields())
                .from(UserSessions)
                .where(UserSessions.USER_VISIT.eq(userVisit.getPrimaryKey()),
                        UserSessions.THRU_TIME.eq(Session.MAX_TIME));
        
        return userSessionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public UserSession getUserSessionByUserVisitForUpdate(UserVisit userVisit) {
        var query = session.getDslContext()
                .select(UserSessions.fields())
                .from(UserSessions)
                .where(UserSessions.USER_VISIT.eq(userVisit.getPrimaryKey()),
                        UserSessions.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return userSessionFactory.getEntityFromQuery(EntityPermission.READ_WRITE, query);
    }

    public UserSessionTransfer getUserSessionTransfer(UserVisit userVisit, UserSession userSession) {
        return userSessionTransferCache.getUserSessionTransfer(userVisit, userSession);
    }
    
    public void deleteUserSession(UserSession userSession) {
        userSession.setThruTime(session.getStartTime());
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

    @Inject
    protected RecoveryQuestionDetailFactory recoveryQuestionDetailFactory;

    @Inject
    protected RecoveryQuestionFactory recoveryQuestionFactory;

    @Inject
    RecoveryQuestionTransferCache recoveryQuestionTransferCache;

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

        var recoveryQuestion = recoveryQuestionFactory.create();
        var recoveryQuestionDetail = recoveryQuestionDetailFactory.create(recoveryQuestion,
                recoveryQuestionName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        recoveryQuestion = recoveryQuestionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                recoveryQuestion.getPrimaryKey());
        recoveryQuestion.setActiveDetail(recoveryQuestionDetail);
        recoveryQuestion.setLastDetail(recoveryQuestionDetail);
        recoveryQuestion.store();
        
        sendEvent(recoveryQuestion.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return recoveryQuestion;
    }

    public long countRecoveryQuestions() {
        return session.getDslContext()
                .selectCount()
                .from(RecoveryQuestions)
                .join(RecoveryQuestionDetails).onKey(RECOVERY_QUESTIONS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.RecoveryQuestion */
    public RecoveryQuestion getRecoveryQuestionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new RecoveryQuestionPK(entityInstance.getEntityUniqueId());

        return recoveryQuestionFactory.getEntityFromPK(entityPermission, pk);
    }

    public RecoveryQuestion getRecoveryQuestionByEntityInstance(EntityInstance entityInstance) {
        return getRecoveryQuestionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public RecoveryQuestion getRecoveryQuestionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getRecoveryQuestionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<RecoveryQuestion> getRecoveryQuestions(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RecoveryQuestions.fields())
                .from(RecoveryQuestions)
                .join(RecoveryQuestionDetails).onKey(RECOVERY_QUESTIONS_ACTIVE_DETAIL_FK);
        
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(RecoveryQuestionDetails.SORT_ORDER, RecoveryQuestionDetails.RECOVERY_QUESTION_NAME),
                    RecoveryQuestionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return recoveryQuestionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<RecoveryQuestion> getRecoveryQuestions() {
        return getRecoveryQuestions(EntityPermission.READ_ONLY);
    }
    
    public List<RecoveryQuestion> getRecoveryQuestionsForUpdate() {
        return getRecoveryQuestions(EntityPermission.READ_WRITE);
    }
    
    private RecoveryQuestion getDefaultRecoveryQuestion(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RecoveryQuestions.fields())
                .from(RecoveryQuestions)
                .join(RecoveryQuestionDetails).onKey(RECOVERY_QUESTIONS_ACTIVE_DETAIL_FK)
                .where(RecoveryQuestionDetails.IS_DEFAULT.eq(true));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return recoveryQuestionFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(RecoveryQuestions.fields())
                .from(RecoveryQuestions)
                .join(RecoveryQuestionDetails).onKey(RECOVERY_QUESTIONS_ACTIVE_DETAIL_FK)
                .where(RecoveryQuestionDetails.RECOVERY_QUESTION_NAME.eq(recoveryQuestionName));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return recoveryQuestionFactory.getEntityFromQuery(entityPermission, query);
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
        return recoveryQuestionTransferCache.getRecoveryQuestionTransfer(userVisit, recoveryQuestion);
    }
    
    public List<RecoveryQuestionTransfer> getRecoveryQuestionTransfers(UserVisit userVisit, Collection<RecoveryQuestion> recoveryQuestions) {
        List<RecoveryQuestionTransfer> recoveryQuestionTransfers = new ArrayList<>(recoveryQuestions.size());
        
        recoveryQuestions.forEach((recoveryQuestion) ->
                recoveryQuestionTransfers.add(recoveryQuestionTransferCache.getRecoveryQuestionTransfer(userVisit, recoveryQuestion))
        );
        
        return recoveryQuestionTransfers;
    }
    
    public List<RecoveryQuestionTransfer> getRecoveryQuestionTransfers(UserVisit userVisit) {
        return getRecoveryQuestionTransfers(userVisit, getRecoveryQuestions());
    }
    
    private void updateRecoveryQuestionFromValue(RecoveryQuestionDetailValue recoveryQuestionDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(recoveryQuestionDetailValue.hasBeenModified()) {
            var recoveryQuestion = recoveryQuestionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     recoveryQuestionDetailValue.getRecoveryQuestionPK());
            var recoveryQuestionDetail = recoveryQuestion.getActiveDetailForUpdate();
            
            recoveryQuestionDetail.setThruTime(session.getStartTime());
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
            
            recoveryQuestionDetail = recoveryQuestionDetailFactory.create(recoveryQuestionPK,
                    recoveryQuestionName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        recoveryQuestionDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected RecoveryQuestionDescriptionFactory recoveryQuestionDescriptionFactory;

    @Inject
    RecoveryQuestionDescriptionTransferCache recoveryQuestionDescriptionTransferCache;

    public RecoveryQuestionDescription createRecoveryQuestionDescription(RecoveryQuestion recoveryQuestion, Language language,
            String description, BasePK createdBy) {
        var recoveryQuestionDescription = recoveryQuestionDescriptionFactory.create(
                recoveryQuestion, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(recoveryQuestion.getPrimaryKey(), EventTypes.MODIFY, recoveryQuestionDescription.getPrimaryKey(),
                null, createdBy);
        
        return recoveryQuestionDescription;
    }
    
    private RecoveryQuestionDescription getRecoveryQuestionDescription(RecoveryQuestion recoveryQuestion, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RecoveryQuestionDescriptions.fields())
                .from(RecoveryQuestionDescriptions)
                .where(RecoveryQuestionDescriptions.RECOVERY_QUESTION.eq(recoveryQuestion.getPrimaryKey()),
                        RecoveryQuestionDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        RecoveryQuestionDescriptions.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return recoveryQuestionDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(RecoveryQuestionDescriptions.fields())
                    .from(RecoveryQuestionDescriptions)
                    .join(Languages).onKey(RECOVERY_QUESTION_DESCRIPTIONS_LANGUAGE_FK)
                    .where(RecoveryQuestionDescriptions.RECOVERY_QUESTION.eq(recoveryQuestion.getPrimaryKey()),
                            RecoveryQuestionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    RecoveryQuestionDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(RecoveryQuestionDescriptions.fields())
                    .from(RecoveryQuestionDescriptions)
                    .where(RecoveryQuestionDescriptions.RECOVERY_QUESTION.eq(recoveryQuestion.getPrimaryKey()),
                            RecoveryQuestionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return recoveryQuestionDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            recoveryQuestionDescription = getRecoveryQuestionDescription(recoveryQuestion, partyControl.getDefaultLanguage());
        }
        
        if(recoveryQuestionDescription == null) {
            description = recoveryQuestion.getLastDetail().getRecoveryQuestionName();
        } else {
            description = recoveryQuestionDescription.getDescription();
        }
        
        return description;
    }
    
    public RecoveryQuestionDescriptionTransfer getRecoveryQuestionDescriptionTransfer(UserVisit userVisit, RecoveryQuestionDescription recoveryQuestionDescription) {
        return recoveryQuestionDescriptionTransferCache.getRecoveryQuestionDescriptionTransfer(userVisit, recoveryQuestionDescription);
    }
    
    public List<RecoveryQuestionDescriptionTransfer> getRecoveryQuestionDescriptionTransfers(UserVisit userVisit, RecoveryQuestion recoveryQuestion) {
        var recoveryQuestionDescriptions = getRecoveryQuestionDescriptionsByRecoveryQuestion(recoveryQuestion);
        List<RecoveryQuestionDescriptionTransfer> recoveryQuestionDescriptionTransfers = new ArrayList<>(recoveryQuestionDescriptions.size());
        
        recoveryQuestionDescriptions.forEach((recoveryQuestionDescription) ->
                recoveryQuestionDescriptionTransfers.add(recoveryQuestionDescriptionTransferCache.getRecoveryQuestionDescriptionTransfer(userVisit, recoveryQuestionDescription))
        );
        
        return recoveryQuestionDescriptionTransfers;
    }
    
    public void updateRecoveryQuestionDescriptionFromValue(RecoveryQuestionDescriptionValue recoveryQuestionDescriptionValue, BasePK updatedBy) {
        if(recoveryQuestionDescriptionValue.hasBeenModified()) {
            var recoveryQuestionDescription = recoveryQuestionDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     recoveryQuestionDescriptionValue.getPrimaryKey());
            
            recoveryQuestionDescription.setThruTime(session.getStartTime());
            recoveryQuestionDescription.store();

            var recoveryQuestion = recoveryQuestionDescription.getRecoveryQuestion();
            var language = recoveryQuestionDescription.getLanguage();
            var description = recoveryQuestionDescriptionValue.getDescription();
            
            recoveryQuestionDescription = recoveryQuestionDescriptionFactory.create(recoveryQuestion, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(recoveryQuestion.getPrimaryKey(), EventTypes.MODIFY, recoveryQuestionDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteRecoveryQuestionDescription(RecoveryQuestionDescription recoveryQuestionDescription, BasePK deletedBy) {
        recoveryQuestionDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected RecoveryAnswerDetailFactory recoveryAnswerDetailFactory;

    @Inject
    protected RecoveryAnswerFactory recoveryAnswerFactory;

    @Inject
    RecoveryAnswerTransferCache recoveryAnswerTransferCache;

    public RecoveryAnswer createRecoveryAnswer(Party party, RecoveryQuestion recoveryQuestion, String answer, BasePK createdBy) {
        var recoveryAnswer = recoveryAnswerFactory.create();
        var recoveryAnswerDetail = recoveryAnswerDetailFactory.create(recoveryAnswer,
                party, recoveryQuestion, answer, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        recoveryAnswer = recoveryAnswerFactory.getEntityFromPK(EntityPermission.READ_WRITE, recoveryAnswer.getPrimaryKey());
        recoveryAnswer.setActiveDetail(recoveryAnswerDetail);
        recoveryAnswer.setLastDetail(recoveryAnswerDetail);
        recoveryAnswer.store();
        
        sendEvent(recoveryAnswer.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return recoveryAnswer;
    }
    
    public long countRecoveryAnswersByRecoveryQuestion(RecoveryQuestion recoveryQuestion) {
        return session.getDslContext()
                .selectCount()
                .from(RecoveryAnswers)
                .join(RecoveryAnswerDetails).onKey(RECOVERY_ANSWERS_ACTIVE_DETAIL_FK)
                .where(RecoveryAnswerDetails.RECOVERY_QUESTION.eq(recoveryQuestion.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private RecoveryAnswer getRecoveryAnswer(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RecoveryAnswers.fields())
                .from(RecoveryAnswers)
                .join(RecoveryAnswerDetails).onKey(RECOVERY_ANSWERS_ACTIVE_DETAIL_FK)
                .where(RecoveryAnswerDetails.PARTY.eq(party.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return recoveryAnswerFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<RecoveryAnswer> getRecoveryAnswersByRecoveryQuestion(RecoveryQuestion recoveryQuestion, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(RecoveryAnswers.fields())
                    .from(RecoveryAnswers)
                    .join(RecoveryAnswerDetails).onKey(RECOVERY_ANSWERS_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(RECOVERY_ANSWER_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .join(PartyTypes).onKey(PARTY_DETAILS_PARTY_TYPE_FK)
                    .where(RecoveryAnswerDetails.RECOVERY_QUESTION.eq(recoveryQuestion.getPrimaryKey()))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME),
                    RecoveryAnswerFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(RecoveryAnswers.fields())
                    .from(RecoveryAnswers)
                    .join(RecoveryAnswerDetails).onKey(RECOVERY_ANSWERS_ACTIVE_DETAIL_FK)
                    .where(RecoveryAnswerDetails.RECOVERY_QUESTION.eq(recoveryQuestion.getPrimaryKey()))
                    .forUpdate();
        };

        return recoveryAnswerFactory.getEntitiesFromQuery(entityPermission, query);
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
        return recoveryAnswerTransferCache.getRecoveryAnswerTransfer(userVisit, recoveryAnswer);
    }
    
    public void updateRecoveryAnswerFromValue(RecoveryAnswerDetailValue recoveryAnswerDetailValue, BasePK updatedBy) {
        if(recoveryAnswerDetailValue.hasBeenModified()) {
            var recoveryAnswer = recoveryAnswerFactory.getEntityFromPK(EntityPermission.READ_WRITE, recoveryAnswerDetailValue.getRecoveryAnswerPK());
            var recoveryAnswerDetail = recoveryAnswer.getActiveDetailForUpdate();

            recoveryAnswerDetail.setThruTime(session.getStartTime());
            recoveryAnswerDetail.store();

            var recoveryAnswerPK = recoveryAnswerDetail.getRecoveryAnswerPK();
            var partyPK = recoveryAnswerDetail.getPartyPK(); // Not updated
            var recoveryQuestionPK = recoveryAnswerDetailValue.getRecoveryQuestionPK();
            var answer = recoveryAnswerDetailValue.getAnswer();

            recoveryAnswerDetail = recoveryAnswerDetailFactory.create(recoveryAnswerPK, partyPK, recoveryQuestionPK, answer,
                    session.getStartTime(), Session.MAX_TIME);

            recoveryAnswer.setActiveDetail(recoveryAnswerDetail);
            recoveryAnswer.setLastDetail(recoveryAnswerDetail);

            sendEvent(recoveryAnswerPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteRecoveryAnswer(RecoveryAnswer recoveryAnswer, BasePK deletedBy) {
        var recoveryAnswerDetail = recoveryAnswer.getLastDetailForUpdate();
        recoveryAnswerDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected UserLoginPasswordEncoderTypeFactory userLoginPasswordEncoderTypeFactory;

    @Inject
    UserLoginPasswordEncoderTypeTransferCache userLoginPasswordEncoderTypeTransferCache;

    public UserLoginPasswordEncoderType createUserLoginPasswordEncoderType(String sequenceEncoderTypeName) {
        return userLoginPasswordEncoderTypeFactory.create(sequenceEncoderTypeName);
    }
    
    public List<UserLoginPasswordEncoderType> getUserLoginPasswordEncoderTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(UserLoginPasswordEncoderTypes.fields())
                .from(UserLoginPasswordEncoderTypes)
                .orderBy(UserLoginPasswordEncoderTypes.USER_LOGIN_PASSWORD_ENCODER_TYPE_NAME),
                UserLoginPasswordEncoderTypeFactory.class);
        
        return userLoginPasswordEncoderTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public UserLoginPasswordEncoderType getUserLoginPasswordEncoderTypeByName(String sequenceEncoderTypeName) {
        var query = session.getDslContext()
                .select(UserLoginPasswordEncoderTypes.fields())
                .from(UserLoginPasswordEncoderTypes)
                .where(UserLoginPasswordEncoderTypes.USER_LOGIN_PASSWORD_ENCODER_TYPE_NAME.eq(sequenceEncoderTypeName));
        
        return userLoginPasswordEncoderTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public UserLoginPasswordEncoderTypeTransfer getUserLoginPasswordEncoderTypeTransfer(UserVisit userVisit, UserLoginPasswordEncoderType userLoginPasswordEncoderType) {
        return userLoginPasswordEncoderTypeTransferCache.getUserLoginPasswordEncoderTypeTransfer(userVisit, userLoginPasswordEncoderType);
    }

    // --------------------------------------------------------------------------------
    //   User Login Password Encoder Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected UserLoginPasswordEncoderTypeDescriptionFactory userLoginPasswordEncoderTypeDescriptionFactory;

    public UserLoginPasswordEncoderTypeDescription createUserLoginPasswordEncoderTypeDescription(UserLoginPasswordEncoderType sequenceEncoderType,
            Language language, String description) {
        return userLoginPasswordEncoderTypeDescriptionFactory.create(sequenceEncoderType, language, description);
    }
    
    public UserLoginPasswordEncoderTypeDescription getUserLoginPasswordEncoderTypeDescription(UserLoginPasswordEncoderType sequenceEncoderType,
            Language language) {
        var query = session.getDslContext()
                .select(UserLoginPasswordEncoderTypeDescriptions.fields())
                .from(UserLoginPasswordEncoderTypeDescriptions)
                .where(UserLoginPasswordEncoderTypeDescriptions.USER_LOGIN_PASSWORD_ENCODER_TYPE.eq(sequenceEncoderType.getPrimaryKey()),
                        UserLoginPasswordEncoderTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));
        
        return userLoginPasswordEncoderTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestUserLoginPasswordEncoderTypeDescription(UserLoginPasswordEncoderType userLoginPasswordEncoderType, Language language) {
        String description;
        var userLoginPasswordEncoderTypeDescription = getUserLoginPasswordEncoderTypeDescription(userLoginPasswordEncoderType,
                language);
        
        if(userLoginPasswordEncoderTypeDescription == null && !language.getIsDefault()) {
            userLoginPasswordEncoderTypeDescription = getUserLoginPasswordEncoderTypeDescription(userLoginPasswordEncoderType,
                    partyControl.getDefaultLanguage());
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

    @Inject
    protected UserLoginPasswordTypeFactory userLoginPasswordTypeFactory;

    @Inject
    UserLoginPasswordTypeTransferCache userLoginPasswordTypeTransferCache;

    public UserLoginPasswordType createUserLoginPasswordType(String userLoginPasswordTypeName,
            UserLoginPasswordEncoderType userLoginPasswordEncoderType) {
        return userLoginPasswordTypeFactory.create(userLoginPasswordTypeName, userLoginPasswordEncoderType);
    }
    
    public UserLoginPasswordType getUserLoginPasswordTypeByName(String userLoginPasswordTypeName) {
        var query = session.getDslContext()
                .select(UserLoginPasswordTypes.fields())
                .from(UserLoginPasswordTypes)
                .where(UserLoginPasswordTypes.USER_LOGIN_PASSWORD_TYPE_NAME.eq(userLoginPasswordTypeName));
        
        return userLoginPasswordTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public UserLoginPasswordTypeTransfer getUserLoginPasswordTypeTransfer(UserVisit userVisit, UserLoginPasswordType userLoginPasswordType) {
        return userLoginPasswordTypeTransferCache.getUserLoginPasswordTypeTransfer(userVisit, userLoginPasswordType);
    }

    // --------------------------------------------------------------------------------
    //   User Login Password Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected UserLoginPasswordTypeDescriptionFactory userLoginPasswordTypeDescriptionFactory;

    public UserLoginPasswordTypeDescription createUserLoginPasswordTypeDescription(UserLoginPasswordType userLoginPasswordType, Language language, String description) {
        return userLoginPasswordTypeDescriptionFactory.create(userLoginPasswordType, language, description);
    }
    
    public UserLoginPasswordTypeDescription getUserLoginPasswordTypeDescription(UserLoginPasswordType userLoginPasswordType, Language language) {
        var query = session.getDslContext()
                .select(UserLoginPasswordTypeDescriptions.fields())
                .from(UserLoginPasswordTypeDescriptions)
                .where(UserLoginPasswordTypeDescriptions.USER_LOGIN_PASSWORD_TYPE.eq(userLoginPasswordType.getPrimaryKey()),
                        UserLoginPasswordTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));
        
        return userLoginPasswordTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestUserLoginPasswordTypeDescription(UserLoginPasswordType userLoginPasswordType, Language language) {
        String description;
        var userLoginPasswordTypeDescription = getUserLoginPasswordTypeDescription(userLoginPasswordType,
                language);

        if(userLoginPasswordTypeDescription == null && !language.getIsDefault()) {
            userLoginPasswordTypeDescription = getUserLoginPasswordTypeDescription(userLoginPasswordType,
                    partyControl.getDefaultLanguage());
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

    @Inject
    protected UserLoginPasswordFactory userLoginPasswordFactory;

    @Inject
    UserLoginPasswordTransferCache userLoginPasswordTransferCache;

    public UserLoginPassword createUserLoginPassword(Party party, UserLoginPasswordType userLoginPasswordType, BasePK createdBy) {
        var userLoginPassword = userLoginPasswordFactory.create(party, userLoginPasswordType,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, userLoginPassword.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return userLoginPassword;
    }
    
    public long countUserLoginPasswords(Party party, UserLoginPasswordType userLoginPasswordType) {
        return session.getDslContext()
                .selectCount()
                .from(UserLoginPasswords)
                .where(UserLoginPasswords.PARTY.eq(party.getPrimaryKey()),
                        UserLoginPasswords.USER_LOGIN_PASSWORD_TYPE.eq(userLoginPasswordType.getPrimaryKey()),
                        UserLoginPasswords.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private UserLoginPassword getUserLoginPassword(Party party, UserLoginPasswordType userLoginPasswordType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserLoginPasswords.fields())
                .from(UserLoginPasswords)
                .where(UserLoginPasswords.PARTY.eq(party.getPrimaryKey()),
                        UserLoginPasswords.USER_LOGIN_PASSWORD_TYPE.eq(userLoginPasswordType.getPrimaryKey()),
                        UserLoginPasswords.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userLoginPasswordFactory.getEntityFromQuery(entityPermission, query);
    }

    public UserLoginPassword getUserLoginPassword(Party party, UserLoginPasswordType userLoginPasswordType) {
        return getUserLoginPassword(party, userLoginPasswordType, EntityPermission.READ_ONLY);
    }
    
    public UserLoginPassword getUserLoginPasswordForUpdate(Party party, UserLoginPasswordType userLoginPasswordType) {
        return getUserLoginPassword(party, userLoginPasswordType, EntityPermission.READ_WRITE);
    }
    
    private List<UserLoginPassword> getUserLoginPasswordsByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(UserLoginPasswords.fields())
                    .from(UserLoginPasswords)
                    .join(UserLoginPasswordTypes).onKey(USER_LOGIN_PASSWORDS_USER_LOGIN_PASSWORD_TYPE_FK)
                    .where(UserLoginPasswords.PARTY.eq(party.getPrimaryKey()),
                            UserLoginPasswords.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UserLoginPasswordTypes.USER_LOGIN_PASSWORD_TYPE_NAME),
                    UserLoginPasswordFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(UserLoginPasswords.fields())
                    .from(UserLoginPasswords)
                    .where(UserLoginPasswords.PARTY.eq(party.getPrimaryKey()),
                            UserLoginPasswords.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return userLoginPasswordFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<UserLoginPassword> getUserLoginPasswordsByParty(Party party) {
        return getUserLoginPasswordsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<UserLoginPassword> getUserLoginPasswordsByPartyForUpdate(Party party) {
        return getUserLoginPasswordsByParty(party, EntityPermission.READ_WRITE);
    }

    public UserLoginPasswordTransfer getUserLoginPasswordTransfer(UserVisit userVisit, UserLoginPassword userLoginPassword) {
        return userLoginPasswordTransferCache.getUserLoginPasswordTransfer(userVisit, userLoginPassword);
    }

    public List<UserLoginPasswordTransfer> getUserLoginPasswordTransfers(UserVisit userVisit, Collection<UserLoginPassword> userLoginPasswords) {
        List<UserLoginPasswordTransfer> userLoginPasswordTransfers = new ArrayList<>(userLoginPasswords.size());

        userLoginPasswords.forEach((userLoginPassword) ->
                userLoginPasswordTransfers.add(userLoginPasswordTransferCache.getUserLoginPasswordTransfer(userVisit, userLoginPassword))
        );

        return userLoginPasswordTransfers;
    }

    public List<UserLoginPasswordTransfer> getUserLoginPasswordTransfersByParty(UserVisit userVisit, Party party) {
        return getUserLoginPasswordTransfers(userVisit, getUserLoginPasswordsByParty(party));
    }

    public void deleteUserLoginPassword(UserLoginPassword userLoginPassword, BasePK deletedBy) {
        var userLoginPasswordTypeName = userLoginPassword.getUserLoginPasswordType().getUserLoginPasswordTypeName();
        
        userLoginPassword.setThruTime(session.getStartTime());
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

    @Inject
    protected UserLoginPasswordStringFactory userLoginPasswordStringFactory;

    public UserLoginPasswordString createUserLoginPasswordString(UserLoginPassword userLoginPassword, String password, Long changedTime, Boolean wasReset,
            BasePK createdBy) {
        var userLoginPasswordEncoderType = userLoginPassword.getUserLoginPasswordType().getUserLoginPasswordEncoderType();
        String salt = null;
        
        if(userLoginPasswordEncoderType != null) {
            var userLoginPasswordEncoderTypeName = userLoginPasswordEncoderType.getUserLoginPasswordEncoderTypeName();
            
            if(userLoginPasswordEncoderTypeName.equals(UserConstants.UserLoginPasswordEncoderType_SHA1)) {
                salt = sha1Utils.generateSalt();
                password = sha1Utils.encode(salt, password);
            }
            
            // UserLoginPasswordEncoderType_TEXT requires no further action.
        }

        var userLoginPasswordString = userLoginPasswordStringFactory.create(
                userLoginPassword, salt, password, changedTime, wasReset, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(userLoginPassword.getPartyPK(), EventTypes.MODIFY, userLoginPasswordString.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return userLoginPasswordString;
    }
    
    private UserLoginPasswordString getUserLoginPasswordString(UserLoginPassword userLoginPassword, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserLoginPasswordStrings.fields())
                .from(UserLoginPasswordStrings)
                .where(UserLoginPasswordStrings.USER_LOGIN_PASSWORD.eq(userLoginPassword.getPrimaryKey()),
                        UserLoginPasswordStrings.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userLoginPasswordStringFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(UserLoginPasswordStrings.fields())
                .from(UserLoginPasswordStrings)
                .where(UserLoginPasswordStrings.USER_LOGIN_PASSWORD.eq(userLoginPassword.getPrimaryKey()))
                .orderBy(UserLoginPasswordStrings.FROM_TIME.desc())
                .limit(limit);
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userLoginPasswordStringFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UserLoginPasswordString> getUserLoginPasswordStringHistory(UserLoginPassword userLoginPassword, long limit) {
        return getUserLoginPasswordStringHistory(userLoginPassword, limit, EntityPermission.READ_ONLY);
    }
    
    public List<UserLoginPasswordString> getUserLoginPasswordStringHistoryForUpdate(UserLoginPassword userLoginPassword, long limit) {
        return getUserLoginPasswordStringHistory(userLoginPassword, limit, EntityPermission.READ_WRITE);
    }
    
    public void updateUserLoginPasswordStringFromValue(UserLoginPasswordStringValue userLoginPasswordStringValue, BasePK updatedBy) {
        if(userLoginPasswordStringValue.hasBeenModified()) {
            var userLoginPasswordString = userLoginPasswordStringFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     userLoginPasswordStringValue.getPrimaryKey());
            
            userLoginPasswordString.setThruTime(session.getStartTime());
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
                        salt = sha1Utils.generateSalt();
                        password = sha1Utils.encode(salt, password);
                    } else if(userLoginPasswordEncoderTypeName.equals(UserConstants.UserLoginPasswordEncoderType_TEXT)) {
                        salt = null;
                    }
                }
            }
            
            userLoginPasswordString = userLoginPasswordStringFactory.create(userLoginPasswordPK, salt, password, changedTime, wasReset,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(userLoginPasswordString.getUserLoginPassword().getPartyPK(), EventTypes.MODIFY, userLoginPasswordString.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUserLoginPasswordString(UserLoginPasswordString userLoginPasswordString, BasePK deletedBy) {
        userLoginPasswordString.setThruTime(session.getStartTime());
        userLoginPasswordString.store();
        
        sendEvent(userLoginPasswordString.getUserLoginPassword().getPartyPK(), EventTypes.MODIFY, userLoginPasswordString.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   User Logins
    // --------------------------------------------------------------------------------

    @Inject
    protected UserLoginFactory userLoginFactory;

    @Inject
    UserLoginTransferCache userLoginTransferCache;

    public UserLogin createUserLogin(Party party, String username, BasePK createdBy) {
        var userLogin = userLoginFactory.create(party, username, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, userLogin.getPrimaryKey(), null, createdBy);
        
        createUserLoginStatus(party);
        
        return userLogin;
    }
    
    private UserLogin getUserLogin(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserLogins.fields())
                .from(UserLogins)
                .where(UserLogins.PARTY.eq(party.getPrimaryKey()),
                        UserLogins.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return userLoginFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(UserLogins.fields())
                .from(UserLogins)
                .where(UserLogins.USERNAME.eq(username),
                        UserLogins.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return userLoginFactory.getEntityFromQuery(entityPermission, query);
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
        return userLoginTransferCache.getUserLoginTransfer(userVisit, userLogin);
    }
    
    public void updateUserLoginFromValue(UserLoginValue userLoginValue, BasePK updatedBy) {
        if(userLoginValue.hasBeenModified()) {
            var userLogin = userLoginFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    userLoginValue.getPrimaryKey());
            
            userLogin.setThruTime(session.getStartTime());
            userLogin.store();

            var partyPK = userLogin.getPartyPK(); // Not updated
            var username = userLoginValue.getUsername();
            
            userLogin = userLoginFactory.create(partyPK, username, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(partyPK, EventTypes.MODIFY, userLogin.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUserLogin(UserLogin userLogin, BasePK deletedBy) {
        var party = userLogin.getParty();
        
        deleteUserLoginPasswordsByParty(party, deletedBy);
        deleteRecoveryAnswerByParty(party, deletedBy);
        removeUserLoginStatusByParty(party);
        
        userLogin.setThruTime(session.getStartTime());
        
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

    @Inject
    protected UserLoginStatusFactory userLoginStatusFactory;

    public UserLoginStatus createUserLoginStatus(Party party) {
        return userLoginStatusFactory.create(party, null, 0, null, null, 0, false);
    }
    
    private UserLoginStatus getUserLoginStatus(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserLoginStatuses.fields())
                .from(UserLoginStatuses)
                .where(UserLoginStatuses.PARTY.eq(party.getPrimaryKey()));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return userLoginStatusFactory.getEntityFromQuery(entityPermission, query);
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
