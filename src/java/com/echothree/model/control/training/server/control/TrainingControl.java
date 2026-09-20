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

package com.echothree.model.control.training.server.control;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.security.server.logic.PartySecurityRoleTemplateLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.training.common.choice.PartyTrainingClassStatusChoicesBean;
import com.echothree.model.control.training.common.choice.TrainingClassChoicesBean;
import com.echothree.model.control.training.common.training.PartyTrainingClassStatusConstants;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionAnswerTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionPageTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionQuestionTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionSectionTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassAnswerTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassAnswerTranslationTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassPageTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassPageTranslationTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassQuestionTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassQuestionTranslationTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassSectionTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassSectionTranslationTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassTranslationTransfer;
import com.echothree.model.control.training.server.transfer.PartyTrainingClassSessionAnswerTransferCache;
import com.echothree.model.control.training.server.transfer.PartyTrainingClassSessionPageTransferCache;
import com.echothree.model.control.training.server.transfer.PartyTrainingClassSessionQuestionTransferCache;
import com.echothree.model.control.training.server.transfer.PartyTrainingClassSessionSectionTransferCache;
import com.echothree.model.control.training.server.transfer.PartyTrainingClassSessionTransferCache;
import com.echothree.model.control.training.server.transfer.PartyTrainingClassTransferCache;
import com.echothree.model.control.training.server.transfer.TrainingClassAnswerTransferCache;
import com.echothree.model.control.training.server.transfer.TrainingClassAnswerTranslationTransferCache;
import com.echothree.model.control.training.server.transfer.TrainingClassPageTransferCache;
import com.echothree.model.control.training.server.transfer.TrainingClassPageTranslationTransferCache;
import com.echothree.model.control.training.server.transfer.TrainingClassQuestionTransferCache;
import com.echothree.model.control.training.server.transfer.TrainingClassQuestionTranslationTransferCache;
import com.echothree.model.control.training.server.transfer.TrainingClassSectionTransferCache;
import com.echothree.model.control.training.server.transfer.TrainingClassSectionTranslationTransferCache;
import com.echothree.model.control.training.server.transfer.TrainingClassTransferCache;
import com.echothree.model.control.training.server.transfer.TrainingClassTranslationTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.training.common.pk.PartyTrainingClassPK;
import com.echothree.model.data.training.common.pk.PartyTrainingClassSessionPK;
import com.echothree.model.data.training.common.pk.PartyTrainingClassSessionQuestionPK;
import com.echothree.model.data.training.common.pk.TrainingClassAnswerPK;
import com.echothree.model.data.training.common.pk.TrainingClassPK;
import com.echothree.model.data.training.common.pk.TrainingClassPagePK;
import com.echothree.model.data.training.common.pk.TrainingClassQuestionPK;
import com.echothree.model.data.training.common.pk.TrainingClassSectionPK;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSession;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionAnswer;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionPage;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionQuestion;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionQuestionStatus;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionSection;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionStatus;
import com.echothree.model.data.training.server.entity.PartyTrainingClassStatus;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassAnswer;
import com.echothree.model.data.training.server.entity.TrainingClassAnswerTranslation;
import com.echothree.model.data.training.server.entity.TrainingClassPage;
import com.echothree.model.data.training.server.entity.TrainingClassPageTranslation;
import com.echothree.model.data.training.server.entity.TrainingClassQuestion;
import com.echothree.model.data.training.server.entity.TrainingClassQuestionTranslation;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.model.data.training.server.entity.TrainingClassSectionTranslation;
import com.echothree.model.data.training.server.entity.TrainingClassTranslation;
import com.echothree.model.data.training.server.factory.PartyTrainingClassDetailFactory;
import com.echothree.model.data.training.server.factory.PartyTrainingClassFactory;
import com.echothree.model.data.training.server.factory.PartyTrainingClassSessionAnswerFactory;
import com.echothree.model.data.training.server.factory.PartyTrainingClassSessionDetailFactory;
import com.echothree.model.data.training.server.factory.PartyTrainingClassSessionFactory;
import com.echothree.model.data.training.server.factory.PartyTrainingClassSessionPageFactory;
import com.echothree.model.data.training.server.factory.PartyTrainingClassSessionQuestionDetailFactory;
import com.echothree.model.data.training.server.factory.PartyTrainingClassSessionQuestionFactory;
import com.echothree.model.data.training.server.factory.PartyTrainingClassSessionQuestionStatusFactory;
import com.echothree.model.data.training.server.factory.PartyTrainingClassSessionSectionFactory;
import com.echothree.model.data.training.server.factory.PartyTrainingClassSessionStatusFactory;
import com.echothree.model.data.training.server.factory.PartyTrainingClassStatusFactory;
import com.echothree.model.data.training.server.factory.TrainingClassAnswerDetailFactory;
import com.echothree.model.data.training.server.factory.TrainingClassAnswerFactory;
import com.echothree.model.data.training.server.factory.TrainingClassAnswerTranslationFactory;
import com.echothree.model.data.training.server.factory.TrainingClassDetailFactory;
import com.echothree.model.data.training.server.factory.TrainingClassFactory;
import com.echothree.model.data.training.server.factory.TrainingClassPageDetailFactory;
import com.echothree.model.data.training.server.factory.TrainingClassPageFactory;
import com.echothree.model.data.training.server.factory.TrainingClassPageTranslationFactory;
import com.echothree.model.data.training.server.factory.TrainingClassQuestionDetailFactory;
import com.echothree.model.data.training.server.factory.TrainingClassQuestionFactory;
import com.echothree.model.data.training.server.factory.TrainingClassQuestionTranslationFactory;
import com.echothree.model.data.training.server.factory.TrainingClassSectionDetailFactory;
import com.echothree.model.data.training.server.factory.TrainingClassSectionFactory;
import com.echothree.model.data.training.server.factory.TrainingClassSectionTranslationFactory;
import com.echothree.model.data.training.server.factory.TrainingClassTranslationFactory;
import com.echothree.model.data.training.server.value.PartyTrainingClassDetailValue;
import com.echothree.model.data.training.server.value.PartyTrainingClassSessionAnswerValue;
import com.echothree.model.data.training.server.value.PartyTrainingClassSessionDetailValue;
import com.echothree.model.data.training.server.value.PartyTrainingClassSessionPageValue;
import com.echothree.model.data.training.server.value.PartyTrainingClassSessionQuestionDetailValue;
import com.echothree.model.data.training.server.value.PartyTrainingClassSessionSectionValue;
import com.echothree.model.data.training.server.value.TrainingClassAnswerDetailValue;
import com.echothree.model.data.training.server.value.TrainingClassAnswerTranslationValue;
import com.echothree.model.data.training.server.value.TrainingClassDetailValue;
import com.echothree.model.data.training.server.value.TrainingClassPageDetailValue;
import com.echothree.model.data.training.server.value.TrainingClassPageTranslationValue;
import com.echothree.model.data.training.server.value.TrainingClassQuestionDetailValue;
import com.echothree.model.data.training.server.value.TrainingClassQuestionTranslationValue;
import com.echothree.model.data.training.server.value.TrainingClassSectionDetailValue;
import com.echothree.model.data.training.server.value.TrainingClassSectionTranslationValue;
import com.echothree.model.data.training.server.value.TrainingClassTranslationValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASSES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASSES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_DETAILS_PARTY_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_DETAILS_TRAINING_CLASS_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_SESSIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_SESSIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_SESSION_ANSWERS_PARTY_TRAINING_CLASS_SESSION_QUESTION_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_SESSION_DETAILS_PARTY_TRAINING_CLASS_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_SESSION_PAGES_PARTY_TRAINING_CLASS_SESSION_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_SESSION_QUESTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_SESSION_QUESTIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_SESSION_QUESTION_DETAILS_PARTY_TRAINING_CLASS_SESSION_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_SESSION_QUESTION_DETAILS_TRAINING_CLASS_QUESTION_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_SESSION_SECTIONS_PARTY_TRAINING_CLASS_SESSION_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.PARTY_TRAINING_CLASS_SESSION_STATUSES_PARTY_TRAINING_CLASS_SESSION_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASSES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASSES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASS_ANSWERS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASS_ANSWER_TRANSLATIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASS_PAGES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASS_PAGE_TRANSLATIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASS_QUESTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASS_QUESTIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASS_QUESTION_DETAILS_TRAINING_CLASS_SECTION_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASS_QUESTION_TRANSLATIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASS_SECTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASS_SECTIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASS_SECTION_TRANSLATIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASS_TRANSLATIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOWS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTITY_STATUSES_WORKFLOW_STEP_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_STEPS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_STEP_DETAILS_WORKFLOW_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.training.PartyTrainingClassDetails.PartyTrainingClassDetails;
import static com.echothree.model.jooq.server.tables.training.PartyTrainingClassSessionAnswers.PartyTrainingClassSessionAnswers;
import static com.echothree.model.jooq.server.tables.training.PartyTrainingClassSessionDetails.PartyTrainingClassSessionDetails;
import static com.echothree.model.jooq.server.tables.training.PartyTrainingClassSessionPages.PartyTrainingClassSessionPages;
import static com.echothree.model.jooq.server.tables.training.PartyTrainingClassSessionQuestionDetails.PartyTrainingClassSessionQuestionDetails;
import static com.echothree.model.jooq.server.tables.training.PartyTrainingClassSessionQuestionStatuses.PartyTrainingClassSessionQuestionStatuses;
import static com.echothree.model.jooq.server.tables.training.PartyTrainingClassSessionQuestions.PartyTrainingClassSessionQuestions;
import static com.echothree.model.jooq.server.tables.training.PartyTrainingClassSessionSections.PartyTrainingClassSessionSections;
import static com.echothree.model.jooq.server.tables.training.PartyTrainingClassSessionStatuses.PartyTrainingClassSessionStatuses;
import static com.echothree.model.jooq.server.tables.training.PartyTrainingClassSessions.PartyTrainingClassSessions;
import static com.echothree.model.jooq.server.tables.training.PartyTrainingClassStatuses.PartyTrainingClassStatuses;
import static com.echothree.model.jooq.server.tables.training.PartyTrainingClasses.PartyTrainingClasses;
import static com.echothree.model.jooq.server.tables.training.TrainingClassAnswerDetails.TrainingClassAnswerDetails;
import static com.echothree.model.jooq.server.tables.training.TrainingClassAnswerTranslations.TrainingClassAnswerTranslations;
import static com.echothree.model.jooq.server.tables.training.TrainingClassAnswers.TrainingClassAnswers;
import static com.echothree.model.jooq.server.tables.training.TrainingClassDetails.TrainingClassDetails;
import static com.echothree.model.jooq.server.tables.training.TrainingClassPageDetails.TrainingClassPageDetails;
import static com.echothree.model.jooq.server.tables.training.TrainingClassPageTranslations.TrainingClassPageTranslations;
import static com.echothree.model.jooq.server.tables.training.TrainingClassPages.TrainingClassPages;
import static com.echothree.model.jooq.server.tables.training.TrainingClassQuestionDetails.TrainingClassQuestionDetails;
import static com.echothree.model.jooq.server.tables.training.TrainingClassQuestionTranslations.TrainingClassQuestionTranslations;
import static com.echothree.model.jooq.server.tables.training.TrainingClassQuestions.TrainingClassQuestions;
import static com.echothree.model.jooq.server.tables.training.TrainingClassSectionDetails.TrainingClassSectionDetails;
import static com.echothree.model.jooq.server.tables.training.TrainingClassSectionTranslations.TrainingClassSectionTranslations;
import static com.echothree.model.jooq.server.tables.training.TrainingClassSections.TrainingClassSections;
import static com.echothree.model.jooq.server.tables.training.TrainingClassTranslations.TrainingClassTranslations;
import static com.echothree.model.jooq.server.tables.training.TrainingClasses.TrainingClasses;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowDetails.WorkflowDetails;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntityStatuses.WorkflowEntityStatuses;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowStepDetails.WorkflowStepDetails;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowSteps.WorkflowSteps;
import static com.echothree.model.jooq.server.tables.workflow.Workflows.Workflows;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;

@CommandScope
public class TrainingControl
        extends BaseModelControl {
    
    @Inject
    protected SequenceControl sequenceControl;


    @Inject
    protected PartySecurityRoleTemplateLogic partySecurityRoleTemplateLogic;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    /** Creates a new instance of TrainingControl */
    protected TrainingControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Training Classes
    // --------------------------------------------------------------------------------

    @Inject
    protected TrainingClassDetailFactory trainingClassDetailFactory;

    @Inject
    protected TrainingClassFactory trainingClassFactory;

    @Inject
    private TrainingClassTransferCache trainingClassTransferCache;

    public TrainingClass createTrainingClass(String trainingClassName, Long estimatedReadingTime, Long readingTimeAllowed, Long estimatedTestingTime,
            Long testingTimeAllowed, Long requiredCompletionTime, WorkEffortScope workEffortScope, Integer defaultPercentageToPass,
            Integer overallQuestionCount, Long testingValidityTime, Long expiredRetentionTime, Boolean alwaysReassignOnExpiration, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultTrainingClass = getDefaultTrainingClass();
        var defaultFound = defaultTrainingClass != null;
        
        if(defaultFound && isDefault) {
            var defaultTrainingClassDetailValue = getDefaultTrainingClassDetailValueForUpdate();
            
            defaultTrainingClassDetailValue.setIsDefault(false);
            updateTrainingClassFromValue(defaultTrainingClassDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var trainingClass = trainingClassFactory.create();
        var trainingClassDetail = trainingClassDetailFactory.create(trainingClass, trainingClassName, estimatedReadingTime,
                readingTimeAllowed, estimatedTestingTime, testingTimeAllowed, requiredCompletionTime, workEffortScope, defaultPercentageToPass,
                overallQuestionCount, testingValidityTime, expiredRetentionTime, alwaysReassignOnExpiration, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        trainingClass = trainingClassFactory.getEntityFromPK(EntityPermission.READ_WRITE, trainingClass.getPrimaryKey());
        trainingClass.setActiveDetail(trainingClassDetail);
        trainingClass.setLastDetail(trainingClassDetail);
        trainingClass.store();
        
        sendEvent(trainingClass.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return trainingClass;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TrainingClass */
    public TrainingClass getTrainingClassByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TrainingClassPK(entityInstance.getEntityUniqueId());

        return trainingClassFactory.getEntityFromPK(entityPermission, pk);
    }

    public TrainingClass getTrainingClassByEntityInstance(EntityInstance entityInstance) {
        return getTrainingClassByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TrainingClass getTrainingClassByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTrainingClassByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTrainingClasses() {
        return session.getDslContext()
                .selectCount()
                .from(TrainingClasses)
                .join(TrainingClassDetails).onKey(TRAINING_CLASSES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public TrainingClass getTrainingClassByName(String trainingClassName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrainingClasses.fields())
                .from(TrainingClasses)
                .join(TrainingClassDetails).onKey(TRAINING_CLASSES_ACTIVE_DETAIL_FK)
                .where(TrainingClassDetails.TRAINING_CLASS_NAME.eq(trainingClassName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TrainingClass getTrainingClassByName(String trainingClassName) {
        return getTrainingClassByName(trainingClassName, EntityPermission.READ_ONLY);
    }
    
    public TrainingClass getTrainingClassByNameForUpdate(String trainingClassName) {
        return getTrainingClassByName(trainingClassName, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassDetailValue getTrainingClassDetailValueForUpdate(TrainingClass trainingClass) {
        return trainingClass == null? null: trainingClass.getLastDetailForUpdate().getTrainingClassDetailValue().clone();
    }
    
    public TrainingClassDetailValue getTrainingClassDetailValueByNameForUpdate(String trainingClassName) {
        return getTrainingClassDetailValueForUpdate(getTrainingClassByNameForUpdate(trainingClassName));
    }
    
    public TrainingClass getDefaultTrainingClass(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrainingClasses.fields())
                .from(TrainingClasses)
                .join(TrainingClassDetails).onKey(TRAINING_CLASSES_ACTIVE_DETAIL_FK)
                .where(TrainingClassDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TrainingClass getDefaultTrainingClass() {
        return getDefaultTrainingClass(EntityPermission.READ_ONLY);
    }
    
    public TrainingClass getDefaultTrainingClassForUpdate() {
        return getDefaultTrainingClass(EntityPermission.READ_WRITE);
    }
    
    public TrainingClassDetailValue getDefaultTrainingClassDetailValueForUpdate() {
        return getDefaultTrainingClassForUpdate().getLastDetailForUpdate().getTrainingClassDetailValue().clone();
    }
    
    private List<TrainingClass> getTrainingClasses(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrainingClasses.fields())
                .from(TrainingClasses)
                .join(TrainingClassDetails).onKey(TRAINING_CLASSES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TrainingClassDetails.SORT_ORDER, TrainingClassDetails.TRAINING_CLASS_NAME),
                    TrainingClassFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TrainingClass> getTrainingClasses() {
        return getTrainingClasses(EntityPermission.READ_ONLY);
    }
    
    public List<TrainingClass> getTrainingClassesForUpdate() {
        return getTrainingClasses(EntityPermission.READ_WRITE);
    }
    
    public TrainingClassTransfer getTrainingClassTransfer(UserVisit userVisit, TrainingClass trainingClass) {
        return trainingClassTransferCache.getTrainingClassTransfer(userVisit, trainingClass);
    }
    
    public List<TrainingClassTransfer> getTrainingClassTransfers(UserVisit userVisit, Collection<TrainingClass> trainingClasses) {
        List<TrainingClassTransfer> trainingClassTransfers = new ArrayList<>(trainingClasses.size());

        trainingClasses.forEach((trainingClass) ->
                trainingClassTransfers.add(trainingClassTransferCache.getTrainingClassTransfer(userVisit, trainingClass))
        );

        return trainingClassTransfers;
    }

    public List<TrainingClassTransfer> getTrainingClassTransfers(UserVisit userVisit) {
        return getTrainingClassTransfers(userVisit, getTrainingClasses());
    }
    
    public TrainingClassChoicesBean getTrainingClassChoices(String defaultTrainingClassChoice, Language language, boolean allowNullChoice) {
        var trainingClasses = getTrainingClasses();
        var size = trainingClasses.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultTrainingClassChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var trainingClass : trainingClasses) {
            var trainingClassDetail = trainingClass.getLastDetail();
            var trainingClassName = trainingClassDetail.getTrainingClassName();
            var trainingClassTranslation = getBestTrainingClassTranslation(trainingClass, language);
            
            var label = trainingClassTranslation == null ? trainingClassName : trainingClassTranslation.getDescription();
            var value = trainingClassName;
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultTrainingClassChoice != null && defaultTrainingClassChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && trainingClassDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new TrainingClassChoicesBean(labels, values, defaultValue);
    }
    
    private void updateTrainingClassFromValue(TrainingClassDetailValue trainingClassDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(trainingClassDetailValue.hasBeenModified()) {
            var trainingClass = trainingClassFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     trainingClassDetailValue.getTrainingClassPK());
            var trainingClassDetail = trainingClass.getActiveDetailForUpdate();
            
            trainingClassDetail.setThruTime(session.getStartTime());
            trainingClassDetail.store();

            var trainingClassPK = trainingClassDetail.getTrainingClassPK();
            var trainingClassName = trainingClassDetailValue.getTrainingClassName();
            var estimatedReadingTime = trainingClassDetailValue.getEstimatedReadingTime();
            var readingTimeAllowed = trainingClassDetailValue.getReadingTimeAllowed();
            var estimatedTestingTime = trainingClassDetailValue.getEstimatedTestingTime();
            var testingTimeAllowed = trainingClassDetailValue.getEstimatedTestingTime();
            var requiredCompletionTime = trainingClassDetailValue.getRequiredCompletionTime();
            var workEffortScopePK = trainingClassDetailValue.getWorkEffortScopePK();
            var defaultPercentageToPass = trainingClassDetailValue.getDefaultPercentageToPass();
            var overallQuestionCount = trainingClassDetailValue.getOverallQuestionCount();
            var testingValidityTime = trainingClassDetailValue.getTestingValidityTime();
            var expiredRetentionTime = trainingClassDetailValue.getExpiredRetentionTime();
            var alwaysReassignOnExpiration = trainingClassDetailValue.getAlwaysReassignOnExpiration();
            var isDefault = trainingClassDetailValue.getIsDefault();
            var sortOrder = trainingClassDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultTrainingClass = getDefaultTrainingClass();
                var defaultFound = defaultTrainingClass != null && !defaultTrainingClass.equals(trainingClass);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultTrainingClassDetailValue = getDefaultTrainingClassDetailValueForUpdate();
                    
                    defaultTrainingClassDetailValue.setIsDefault(false);
                    updateTrainingClassFromValue(defaultTrainingClassDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            trainingClassDetail = trainingClassDetailFactory.create(trainingClassPK, trainingClassName, estimatedReadingTime, readingTimeAllowed,
                    estimatedTestingTime, testingTimeAllowed, requiredCompletionTime, workEffortScopePK, defaultPercentageToPass, overallQuestionCount,
                    testingValidityTime, expiredRetentionTime, alwaysReassignOnExpiration, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            trainingClass.setActiveDetail(trainingClassDetail);
            trainingClass.setLastDetail(trainingClassDetail);
            
            sendEvent(trainingClassPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateTrainingClassFromValue(TrainingClassDetailValue trainingClassDetailValue, BasePK updatedBy) {
        updateTrainingClassFromValue(trainingClassDetailValue, true, updatedBy);
    }
    
    public void deleteTrainingClass(TrainingClass trainingClass, BasePK deletedBy) {
        deleteTrainingClassTranslationsByTrainingClass(trainingClass, deletedBy);
        deletePartyTrainingClassesByTrainingClass(trainingClass, deletedBy);
        partySecurityRoleTemplateLogic.deletePartySecurityRoleTemplateTrainingClassesByTrainingClass(trainingClass, deletedBy);

        var trainingClassDetail = trainingClass.getLastDetailForUpdate();
        trainingClassDetail.setThruTime(session.getStartTime());
        trainingClass.setActiveDetail(null);
        trainingClass.store();
        
        // Check for default, and pick one if necessary
        var defaultTrainingClass = getDefaultTrainingClass();
        if(defaultTrainingClass == null) {
            var trainingClasses = getTrainingClassesForUpdate();
            
            if(!trainingClasses.isEmpty()) {
                Iterator iter = trainingClasses.iterator();
                if(iter.hasNext()) {
                    defaultTrainingClass = (TrainingClass)iter.next();
                }
                var trainingClassDetailValue = Objects.requireNonNull(defaultTrainingClass).getLastDetailForUpdate().getTrainingClassDetailValue().clone();
                
                trainingClassDetailValue.setIsDefault(true);
                updateTrainingClassFromValue(trainingClassDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(trainingClass.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Training Class Translations
    // --------------------------------------------------------------------------------

    @Inject
    protected TrainingClassTranslationFactory trainingClassTranslationFactory;

    @Inject
    private TrainingClassTranslationTransferCache trainingClassTranslationTransferCache;

    public TrainingClassTranslation createTrainingClassTranslation(TrainingClass trainingClass, Language language, String description, MimeType overviewMimeType,
            String overview, MimeType introductionMimeType, String introduction, BasePK createdBy) {
        var trainingClassTranslation = trainingClassTranslationFactory.create(trainingClass, language, description,
                overviewMimeType, overview, introductionMimeType, introduction, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(trainingClass.getPrimaryKey(), EventTypes.MODIFY, trainingClassTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return trainingClassTranslation;
    }
    
    private TrainingClassTranslation getTrainingClassTranslation(TrainingClass trainingClass, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassTranslations.fields())
                .from(TrainingClassTranslations)
                .where(TrainingClassTranslations.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()),
                        TrainingClassTranslations.LANGUAGE.eq(language.getPrimaryKey()),
                        TrainingClassTranslations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassTranslationFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TrainingClassTranslation getTrainingClassTranslation(TrainingClass trainingClass, Language language) {
        return getTrainingClassTranslation(trainingClass, language, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassTranslation getTrainingClassTranslationForUpdate(TrainingClass trainingClass, Language language) {
        return getTrainingClassTranslation(trainingClass, language, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassTranslationValue getTrainingClassTranslationValue(TrainingClassTranslation trainingClassTranslation) {
        return trainingClassTranslation == null? null: trainingClassTranslation.getTrainingClassTranslationValue().clone();
    }
    
    public TrainingClassTranslationValue getTrainingClassTranslationValueForUpdate(TrainingClass trainingClass, Language language) {
        return getTrainingClassTranslationValue(getTrainingClassTranslationForUpdate(trainingClass, language));
    }
    
    private List<TrainingClassTranslation> getTrainingClassTranslationsByTrainingClass(TrainingClass trainingClass, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TrainingClassTranslations.fields())
                    .from(TrainingClassTranslations)
                    .join(Languages).onKey(TRAINING_CLASS_TRANSLATIONS_LANGUAGE_FK)
                    .where(TrainingClassTranslations.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()),
                            TrainingClassTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    TrainingClassTranslationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TrainingClassTranslations.fields())
                    .from(TrainingClassTranslations)
                    .where(TrainingClassTranslations.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()),
                            TrainingClassTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return trainingClassTranslationFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TrainingClassTranslation> getTrainingClassTranslationsByTrainingClass(TrainingClass trainingClass) {
        return getTrainingClassTranslationsByTrainingClass(trainingClass, EntityPermission.READ_ONLY);
    }
    
    public List<TrainingClassTranslation> getTrainingClassTranslationsByTrainingClassForUpdate(TrainingClass trainingClass) {
        return getTrainingClassTranslationsByTrainingClass(trainingClass, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassTranslation getBestTrainingClassTranslation(TrainingClass trainingClass, Language language) {
        var trainingClassTranslation = getTrainingClassTranslation(trainingClass, language);
        
        if(trainingClassTranslation == null && !language.getIsDefault()) {
            trainingClassTranslation = getTrainingClassTranslation(trainingClass, partyControl.getDefaultLanguage());
        }
        
        return trainingClassTranslation;
    }
    
    public TrainingClassTranslationTransfer getTrainingClassTranslationTransfer(UserVisit userVisit, TrainingClassTranslation trainingClassTranslation) {
        return trainingClassTranslationTransferCache.getTrainingClassTranslationTransfer(userVisit, trainingClassTranslation);
    }
    
    public List<TrainingClassTranslationTransfer> getTrainingClassTranslationTransfers(UserVisit userVisit, TrainingClass trainingClass) {
        var trainingClassTranslations = getTrainingClassTranslationsByTrainingClass(trainingClass);
        List<TrainingClassTranslationTransfer> trainingClassTranslationTransfers = new ArrayList<>(trainingClassTranslations.size());
        
        trainingClassTranslations.forEach((trainingClassTranslation) ->
                trainingClassTranslationTransfers.add(trainingClassTranslationTransferCache.getTrainingClassTranslationTransfer(userVisit, trainingClassTranslation))
        );
        
        return trainingClassTranslationTransfers;
    }
    
    public void updateTrainingClassTranslationFromValue(TrainingClassTranslationValue trainingClassTranslationValue, BasePK updatedBy) {
        if(trainingClassTranslationValue.hasBeenModified()) {
            var trainingClassTranslation = trainingClassTranslationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    trainingClassTranslationValue.getPrimaryKey());
            
            trainingClassTranslation.setThruTime(session.getStartTime());
            trainingClassTranslation.store();

            var trainingClassPK = trainingClassTranslation.getTrainingClassPK();
            var languagePK = trainingClassTranslation.getLanguagePK();
            var description = trainingClassTranslationValue.getDescription();
            var overviewMimeTypePK = trainingClassTranslationValue.getOverviewMimeTypePK();
            var overview = trainingClassTranslationValue.getOverview();
            var introductionMimeTypePK = trainingClassTranslationValue.getIntroductionMimeTypePK();
            var introduction = trainingClassTranslationValue.getIntroduction();
            
            trainingClassTranslation = trainingClassTranslationFactory.create(trainingClassPK, languagePK, description, overviewMimeTypePK,
                    overview, introductionMimeTypePK, introduction, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(trainingClassPK, EventTypes.MODIFY, trainingClassTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTrainingClassTranslation(TrainingClassTranslation trainingClassTranslation, BasePK deletedBy) {
        trainingClassTranslation.setThruTime(session.getStartTime());
        
        sendEvent(trainingClassTranslation.getTrainingClassPK(), EventTypes.MODIFY, trainingClassTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteTrainingClassTranslationsByTrainingClass(TrainingClass trainingClass, BasePK deletedBy) {
        var trainingClassTranslations = getTrainingClassTranslationsByTrainingClassForUpdate(trainingClass);
        
        trainingClassTranslations.forEach((trainingClassTranslation) -> 
                deleteTrainingClassTranslation(trainingClassTranslation, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Training Class Sections
    // --------------------------------------------------------------------------------

    @Inject
    protected TrainingClassSectionDetailFactory trainingClassSectionDetailFactory;

    @Inject
    protected TrainingClassSectionFactory trainingClassSectionFactory;

    @Inject
    private TrainingClassSectionTransferCache trainingClassSectionTransferCache;

    public TrainingClassSection createTrainingClassSection(TrainingClass trainingClass, String trainingClassSectionName, Integer percentageToPass,
            Integer questionCount, Integer sortOrder, BasePK createdBy) {
        var trainingClassSection = trainingClassSectionFactory.create();
        var trainingClassSectionDetail = trainingClassSectionDetailFactory.create(trainingClassSection, trainingClass,
                trainingClassSectionName, percentageToPass, questionCount, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        trainingClassSection = trainingClassSectionFactory.getEntityFromPK(EntityPermission.READ_WRITE, trainingClassSection.getPrimaryKey());
        trainingClassSection.setActiveDetail(trainingClassSectionDetail);
        trainingClassSection.setLastDetail(trainingClassSectionDetail);
        trainingClassSection.store();
        
        sendEvent(trainingClassSection.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return trainingClassSection;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TrainingClassSection */
    public TrainingClassSection getTrainingClassSectionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TrainingClassSectionPK(entityInstance.getEntityUniqueId());

        return trainingClassSectionFactory.getEntityFromPK(entityPermission, pk);
    }

    public TrainingClassSection getTrainingClassSectionByEntityInstance(EntityInstance entityInstance) {
        return getTrainingClassSectionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TrainingClassSection getTrainingClassSectionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTrainingClassSectionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTrainingClassSectionsByTrainingClass(final TrainingClass trainingClass) {
        return session.getDslContext()
                .selectCount()
                .from(TrainingClassSections)
                .join(TrainingClassSectionDetails).onKey(TRAINING_CLASS_SECTIONS_ACTIVE_DETAIL_FK)
                .where(TrainingClassSectionDetails.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public TrainingClassSection getTrainingClassSectionByName(TrainingClass trainingClass, String trainingClassSectionName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassSections.fields())
                .from(TrainingClassSections)
                .join(TrainingClassSectionDetails).onKey(TRAINING_CLASS_SECTIONS_ACTIVE_DETAIL_FK)
                .where(TrainingClassSectionDetails.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()),
                        TrainingClassSectionDetails.TRAINING_CLASS_SECTION_NAME.eq(trainingClassSectionName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassSectionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TrainingClassSection getTrainingClassSectionByName(TrainingClass trainingClass, String trainingClassSectionName) {
        return getTrainingClassSectionByName(trainingClass, trainingClassSectionName, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassSection getTrainingClassSectionByNameForUpdate(TrainingClass trainingClass, String trainingClassSectionName) {
        return getTrainingClassSectionByName(trainingClass, trainingClassSectionName, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassSectionDetailValue getTrainingClassSectionDetailValueForUpdate(TrainingClassSection trainingClassSection) {
        return trainingClassSection == null? null: trainingClassSection.getLastDetailForUpdate().getTrainingClassSectionDetailValue().clone();
    }
    
    public TrainingClassSectionDetailValue getTrainingClassSectionDetailValueByNameForUpdate(TrainingClass trainingClass, String trainingClassSectionName) {
        return getTrainingClassSectionDetailValueForUpdate(getTrainingClassSectionByNameForUpdate(trainingClass, trainingClassSectionName));
    }
    
    private List<TrainingClassSection> getTrainingClassSections(EntityPermission entityPermission, TrainingClass trainingClass) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassSections.fields())
                .from(TrainingClassSections)
                .join(TrainingClassSectionDetails).onKey(TRAINING_CLASS_SECTIONS_ACTIVE_DETAIL_FK)
                .where(TrainingClassSectionDetails.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TrainingClassSectionDetails.SORT_ORDER, TrainingClassSectionDetails.TRAINING_CLASS_SECTION_NAME),
                    TrainingClassSectionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassSectionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TrainingClassSection> getTrainingClassSections(TrainingClass trainingClass) {
        return getTrainingClassSections(EntityPermission.READ_ONLY, trainingClass);
    }
    
    public List<TrainingClassSection> getTrainingClassSectionsForUpdate(TrainingClass trainingClass) {
        return getTrainingClassSections(EntityPermission.READ_WRITE, trainingClass);
    }
    
    public TrainingClassSectionTransfer getTrainingClassSectionTransfer(UserVisit userVisit, TrainingClassSection trainingClassSection) {
        return trainingClassSectionTransferCache.getTrainingClassSectionTransfer(userVisit, trainingClassSection);
    }
    
    public List<TrainingClassSectionTransfer> getTrainingClassSectionTransfers(UserVisit userVisit, Collection<TrainingClassSection> trainingClassSections) {
        List<TrainingClassSectionTransfer> trainingClassSectionTransfers = new ArrayList<>(trainingClassSections.size());
        
        trainingClassSections.forEach((trainingClassSection) ->
                trainingClassSectionTransfers.add(trainingClassSectionTransferCache.getTrainingClassSectionTransfer(userVisit, trainingClassSection))
        );
        
        return trainingClassSectionTransfers;
    }
    
    public List<TrainingClassSectionTransfer> getTrainingClassSectionTransfers(UserVisit userVisit, TrainingClass trainingClass) {
        return getTrainingClassSectionTransfers(userVisit, getTrainingClassSections(trainingClass));
    }
    
    public void updateTrainingClassSectionFromValue(TrainingClassSectionDetailValue trainingClassSectionDetailValue, BasePK updatedBy) {
        if(trainingClassSectionDetailValue.hasBeenModified()) {
            var trainingClassSection = trainingClassSectionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     trainingClassSectionDetailValue.getTrainingClassSectionPK());
            var trainingClassSectionDetail = trainingClassSection.getActiveDetailForUpdate();
            
            trainingClassSectionDetail.setThruTime(session.getStartTime());
            trainingClassSectionDetail.store();

            var trainingClassSectionPK = trainingClassSectionDetail.getTrainingClassSectionPK(); // Not updated
            var trainingClassPK = trainingClassSectionDetail.getTrainingClassPK(); // Not updated
            var trainingClassSectionName = trainingClassSectionDetailValue.getTrainingClassSectionName();
            var percentageToPass = trainingClassSectionDetailValue.getPercentageToPass();
            var questionCount = trainingClassSectionDetailValue.getQuestionCount();
            var sortOrder = trainingClassSectionDetailValue.getSortOrder();
            
            trainingClassSectionDetail = trainingClassSectionDetailFactory.create(trainingClassSectionPK, trainingClassPK,
                    trainingClassSectionName, percentageToPass, questionCount, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            trainingClassSection.setActiveDetail(trainingClassSectionDetail);
            trainingClassSection.setLastDetail(trainingClassSectionDetail);
            
            sendEvent(trainingClassSectionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteTrainingClassSection(TrainingClassSection trainingClassSection, BasePK deletedBy) {
        deleteTrainingClassSectionTranslationsByTrainingClassSection(trainingClassSection, deletedBy);
        deleteTrainingClassPagesByTrainingClassSection(trainingClassSection, deletedBy);
        deleteTrainingClassQuestionsByTrainingClassSection(trainingClassSection, deletedBy);
        deletePartyTrainingClassSessionSectionsByTrainingClassSection(trainingClassSection, deletedBy);

        var trainingClassSectionDetail = trainingClassSection.getLastDetailForUpdate();
        trainingClassSectionDetail.setThruTime(session.getStartTime());
        trainingClassSection.setActiveDetail(null);
        trainingClassSection.store();
        
        sendEvent(trainingClassSection.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteTrainingClassSections(List<TrainingClassSection> trainingClassSections, BasePK deletedBy) {
        trainingClassSections.forEach((trainingClassSection) -> 
                deleteTrainingClassSection(trainingClassSection, deletedBy)
        );
    }
    
    public void deleteTrainingClassSectionsByTrainingClass(TrainingClass trainingClass, BasePK deletedBy) {
        deleteTrainingClassSections(getTrainingClassSectionsForUpdate(trainingClass), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Training Class Section Translations
    // --------------------------------------------------------------------------------

    @Inject
    protected TrainingClassSectionTranslationFactory trainingClassSectionTranslationFactory;

    @Inject
    private TrainingClassSectionTranslationTransferCache trainingClassSectionTranslationTransferCache;

    public TrainingClassSectionTranslation createTrainingClassSectionTranslation(TrainingClassSection trainingClassSection, Language language,
            String description, MimeType overviewMimeType, String overview, MimeType introductionMimeType, String introduction, BasePK createdBy) {
        var trainingClassSectionTranslation = trainingClassSectionTranslationFactory.create(trainingClassSection,
                language, description, overviewMimeType, overview, introductionMimeType, introduction, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(trainingClassSection.getPrimaryKey(), EventTypes.MODIFY, trainingClassSectionTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return trainingClassSectionTranslation;
    }
    
    private TrainingClassSectionTranslation getTrainingClassSectionTranslation(TrainingClassSection trainingClassSection, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassSectionTranslations.fields())
                .from(TrainingClassSectionTranslations)
                .where(TrainingClassSectionTranslations.TRAINING_CLASS_SECTION.eq(trainingClassSection.getPrimaryKey()),
                        TrainingClassSectionTranslations.LANGUAGE.eq(language.getPrimaryKey()),
                        TrainingClassSectionTranslations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassSectionTranslationFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TrainingClassSectionTranslation getTrainingClassSectionTranslation(TrainingClassSection trainingClassSection, Language language) {
        return getTrainingClassSectionTranslation(trainingClassSection, language, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassSectionTranslation getTrainingClassSectionTranslationForUpdate(TrainingClassSection trainingClassSection, Language language) {
        return getTrainingClassSectionTranslation(trainingClassSection, language, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassSectionTranslationValue getTrainingClassSectionTranslationValue(TrainingClassSectionTranslation trainingClassSectionTranslation) {
        return trainingClassSectionTranslation == null? null: trainingClassSectionTranslation.getTrainingClassSectionTranslationValue().clone();
    }
    
    public TrainingClassSectionTranslationValue getTrainingClassSectionTranslationValueForUpdate(TrainingClassSection trainingClassSection, Language language) {
        return getTrainingClassSectionTranslationValue(getTrainingClassSectionTranslationForUpdate(trainingClassSection, language));
    }
    
    private List<TrainingClassSectionTranslation> getTrainingClassSectionTranslationsByTrainingClassSection(TrainingClassSection trainingClassSection, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TrainingClassSectionTranslations.fields())
                    .from(TrainingClassSectionTranslations)
                    .join(Languages).onKey(TRAINING_CLASS_SECTION_TRANSLATIONS_LANGUAGE_FK)
                    .where(TrainingClassSectionTranslations.TRAINING_CLASS_SECTION.eq(trainingClassSection.getPrimaryKey()),
                            TrainingClassSectionTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    TrainingClassSectionTranslationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TrainingClassSectionTranslations.fields())
                    .from(TrainingClassSectionTranslations)
                    .where(TrainingClassSectionTranslations.TRAINING_CLASS_SECTION.eq(trainingClassSection.getPrimaryKey()),
                            TrainingClassSectionTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return trainingClassSectionTranslationFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TrainingClassSectionTranslation> getTrainingClassSectionTranslationsByTrainingClassSection(TrainingClassSection trainingClassSection) {
        return getTrainingClassSectionTranslationsByTrainingClassSection(trainingClassSection, EntityPermission.READ_ONLY);
    }
    
    public List<TrainingClassSectionTranslation> getTrainingClassSectionTranslationsByTrainingClassSectionForUpdate(TrainingClassSection trainingClassSection) {
        return getTrainingClassSectionTranslationsByTrainingClassSection(trainingClassSection, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassSectionTranslation getBestTrainingClassSectionTranslation(TrainingClassSection trainingClassSection, Language language) {
        var trainingClassSectionTranslation = getTrainingClassSectionTranslation(trainingClassSection, language);
        
        if(trainingClassSectionTranslation == null && !language.getIsDefault()) {
            trainingClassSectionTranslation = getTrainingClassSectionTranslation(trainingClassSection, partyControl.getDefaultLanguage());
        }
        
        return trainingClassSectionTranslation;
    }
    
    public TrainingClassSectionTranslationTransfer getTrainingClassSectionTranslationTransfer(UserVisit userVisit, TrainingClassSectionTranslation trainingClassSectionTranslation) {
        return trainingClassSectionTranslationTransferCache.getTrainingClassSectionTranslationTransfer(userVisit, trainingClassSectionTranslation);
    }
    
    public List<TrainingClassSectionTranslationTransfer> getTrainingClassSectionTranslationTransfers(UserVisit userVisit, TrainingClassSection trainingClassSection) {
        var trainingClassSectionTranslations = getTrainingClassSectionTranslationsByTrainingClassSection(trainingClassSection);
        List<TrainingClassSectionTranslationTransfer> trainingClassSectionTranslationTransfers = new ArrayList<>(trainingClassSectionTranslations.size());
        
        trainingClassSectionTranslations.forEach((trainingClassSectionTranslation) ->
                trainingClassSectionTranslationTransfers.add(trainingClassSectionTranslationTransferCache.getTrainingClassSectionTranslationTransfer(userVisit, trainingClassSectionTranslation))
        );
        
        return trainingClassSectionTranslationTransfers;
    }
    
    public void updateTrainingClassSectionTranslationFromValue(TrainingClassSectionTranslationValue trainingClassSectionTranslationValue, BasePK updatedBy) {
        if(trainingClassSectionTranslationValue.hasBeenModified()) {
            var trainingClassSectionTranslation = trainingClassSectionTranslationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    trainingClassSectionTranslationValue.getPrimaryKey());
            
            trainingClassSectionTranslation.setThruTime(session.getStartTime());
            trainingClassSectionTranslation.store();

            var trainingClassSectionPK = trainingClassSectionTranslation.getTrainingClassSectionPK();
            var languagePK = trainingClassSectionTranslation.getLanguagePK();
            var description = trainingClassSectionTranslationValue.getDescription();
            var overviewMimeTypePK = trainingClassSectionTranslationValue.getOverviewMimeTypePK();
            var overview = trainingClassSectionTranslationValue.getOverview();
            var introductionMimeTypePK = trainingClassSectionTranslationValue.getIntroductionMimeTypePK();
            var introduction = trainingClassSectionTranslationValue.getIntroduction();
            
            trainingClassSectionTranslation = trainingClassSectionTranslationFactory.create(trainingClassSectionPK, languagePK, description,
                    overviewMimeTypePK, overview, introductionMimeTypePK, introduction, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(trainingClassSectionPK, EventTypes.MODIFY, trainingClassSectionTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTrainingClassSectionTranslation(TrainingClassSectionTranslation trainingClassSectionTranslation, BasePK deletedBy) {
        trainingClassSectionTranslation.setThruTime(session.getStartTime());
        
        sendEvent(trainingClassSectionTranslation.getTrainingClassSectionPK(), EventTypes.MODIFY, trainingClassSectionTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteTrainingClassSectionTranslationsByTrainingClassSection(TrainingClassSection trainingClassSection, BasePK deletedBy) {
        var trainingClassSectionTranslations = getTrainingClassSectionTranslationsByTrainingClassSectionForUpdate(trainingClassSection);
        
        trainingClassSectionTranslations.forEach((trainingClassSectionTranslation) -> 
                deleteTrainingClassSectionTranslation(trainingClassSectionTranslation, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Training Class Pages
    // --------------------------------------------------------------------------------

    @Inject
    protected TrainingClassPageDetailFactory trainingClassPageDetailFactory;

    @Inject
    protected TrainingClassPageFactory trainingClassPageFactory;

    @Inject
    private TrainingClassPageTransferCache trainingClassPageTransferCache;

    public TrainingClassPage createTrainingClassPage(TrainingClassSection trainingClassSection, String trainingClassPageName, Integer sortOrder,
            BasePK createdBy) {
        var trainingClassPage = trainingClassPageFactory.create();
        var trainingClassPageDetail = trainingClassPageDetailFactory.create(trainingClassPage, trainingClassSection,
                trainingClassPageName, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        trainingClassPage = trainingClassPageFactory.getEntityFromPK(EntityPermission.READ_WRITE, trainingClassPage.getPrimaryKey());
        trainingClassPage.setActiveDetail(trainingClassPageDetail);
        trainingClassPage.setLastDetail(trainingClassPageDetail);
        trainingClassPage.store();
        
        sendEvent(trainingClassPage.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return trainingClassPage;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TrainingClassPage */
    public TrainingClassPage getTrainingClassPageByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TrainingClassPagePK(entityInstance.getEntityUniqueId());

        return trainingClassPageFactory.getEntityFromPK(entityPermission, pk);
    }

    public TrainingClassPage getTrainingClassPageByEntityInstance(EntityInstance entityInstance) {
        return getTrainingClassPageByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TrainingClassPage getTrainingClassPageByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTrainingClassPageByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTrainingClassPagesByTrainingClassSection(final TrainingClassSection trainingClassSection) {
        return session.getDslContext()
                .selectCount()
                .from(TrainingClassPages)
                .join(TrainingClassPageDetails).onKey(TRAINING_CLASS_PAGES_ACTIVE_DETAIL_FK)
                .where(TrainingClassPageDetails.TRAINING_CLASS_SECTION.eq(trainingClassSection.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countTrainingClassPages(List<TrainingClassSection> trainingClassSections) {
        long total = 0;

        total = trainingClassSections.stream().map(this::countTrainingClassPagesByTrainingClassSection).reduce(total, Long::sum);

        return total;
    }

    public long countTrainingClassPages(TrainingClass trainingClass) {
        return countTrainingClassPages(getTrainingClassSections(trainingClass));
    }

    public TrainingClassPage getTrainingClassPageByName(TrainingClassSection trainingClassSection, String trainingClassPageName,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassPages.fields())
                .from(TrainingClassPages)
                .join(TrainingClassPageDetails).onKey(TRAINING_CLASS_PAGES_ACTIVE_DETAIL_FK)
                .where(TrainingClassPageDetails.TRAINING_CLASS_SECTION.eq(trainingClassSection.getPrimaryKey()),
                        TrainingClassPageDetails.TRAINING_CLASS_PAGE_NAME.eq(trainingClassPageName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassPageFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TrainingClassPage getTrainingClassPageByName(TrainingClassSection trainingClassSection, String trainingClassPageName) {
        return getTrainingClassPageByName(trainingClassSection, trainingClassPageName, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassPage getTrainingClassPageByNameForUpdate(TrainingClassSection trainingClassSection, String trainingClassPageName) {
        return getTrainingClassPageByName(trainingClassSection, trainingClassPageName, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassPageDetailValue getTrainingClassPageDetailValueForUpdate(TrainingClassPage trainingClassPage) {
        return trainingClassPage == null? null: trainingClassPage.getLastDetailForUpdate().getTrainingClassPageDetailValue().clone();
    }
    
    public TrainingClassPageDetailValue getTrainingClassPageDetailValueByNameForUpdate(TrainingClassSection trainingClassSection, String trainingClassPageName) {
        return getTrainingClassPageDetailValueForUpdate(getTrainingClassPageByNameForUpdate(trainingClassSection, trainingClassPageName));
    }
    
    private List<TrainingClassPage> getTrainingClassPages(EntityPermission entityPermission, TrainingClassSection trainingClassSection) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassPages.fields())
                .from(TrainingClassPages)
                .join(TrainingClassPageDetails).onKey(TRAINING_CLASS_PAGES_ACTIVE_DETAIL_FK)
                .where(TrainingClassPageDetails.TRAINING_CLASS_SECTION.eq(trainingClassSection.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TrainingClassPageDetails.SORT_ORDER, TrainingClassPageDetails.TRAINING_CLASS_PAGE_NAME),
                    TrainingClassPageFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassPageFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TrainingClassPage> getTrainingClassPages(TrainingClassSection trainingClassSection) {
        return getTrainingClassPages(EntityPermission.READ_ONLY, trainingClassSection);
    }
    
    public List<TrainingClassPage> getTrainingClassPagesForUpdate(TrainingClassSection trainingClassSection) {
        return getTrainingClassPages(EntityPermission.READ_WRITE, trainingClassSection);
    }
    
    public TrainingClassPageTransfer getTrainingClassPageTransfer(UserVisit userVisit, TrainingClassPage trainingClassPage) {
        return trainingClassPageTransferCache.getTrainingClassPageTransfer(userVisit, trainingClassPage);
    }
    
    public List<TrainingClassPageTransfer> getTrainingClassPageTransfers(UserVisit userVisit, Collection<TrainingClassPage> trainingClassPages) {
        List<TrainingClassPageTransfer> trainingClassPageTransfers = new ArrayList<>(trainingClassPages.size());
        
        trainingClassPages.forEach((trainingClassPage) ->
                trainingClassPageTransfers.add(trainingClassPageTransferCache.getTrainingClassPageTransfer(userVisit, trainingClassPage))
        );
        
        return trainingClassPageTransfers;
    }
    
    public List<TrainingClassPageTransfer> getTrainingClassPageTransfers(UserVisit userVisit, TrainingClassSection trainingClassSection) {
        return getTrainingClassPageTransfers(userVisit, getTrainingClassPages(trainingClassSection));
    }
    
    public void updateTrainingClassPageFromValue(TrainingClassPageDetailValue trainingClassPageDetailValue, BasePK updatedBy) {
        if(trainingClassPageDetailValue.hasBeenModified()) {
            var trainingClassPage = trainingClassPageFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     trainingClassPageDetailValue.getTrainingClassPagePK());
            var trainingClassPageDetail = trainingClassPage.getActiveDetailForUpdate();
            
            trainingClassPageDetail.setThruTime(session.getStartTime());
            trainingClassPageDetail.store();

            var trainingClassPagePK = trainingClassPageDetail.getTrainingClassPagePK(); // Not updated
            var trainingClassSectionPK = trainingClassPageDetail.getTrainingClassSectionPK(); // Not updated
            var trainingClassPageName = trainingClassPageDetailValue.getTrainingClassPageName();
            var sortOrder = trainingClassPageDetailValue.getSortOrder();
            
            trainingClassPageDetail = trainingClassPageDetailFactory.create(trainingClassPagePK, trainingClassSectionPK, trainingClassPageName,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            trainingClassPage.setActiveDetail(trainingClassPageDetail);
            trainingClassPage.setLastDetail(trainingClassPageDetail);
            
            sendEvent(trainingClassPagePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteTrainingClassPage(TrainingClassPage trainingClassPage, BasePK deletedBy) {
        deleteTrainingClassPageTranslationsByTrainingClassPage(trainingClassPage, deletedBy);
        deletePartyTrainingClassSessionPagesByTrainingClassPage(trainingClassPage, deletedBy);

        var trainingClassPageDetail = trainingClassPage.getLastDetailForUpdate();
        trainingClassPageDetail.setThruTime(session.getStartTime());
        trainingClassPage.setActiveDetail(null);
        trainingClassPage.store();
        
        sendEvent(trainingClassPage.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteTrainingClassPages(List<TrainingClassPage> trainingClassPages, BasePK deletedBy) {
        trainingClassPages.forEach((trainingClassPage) -> 
                deleteTrainingClassPage(trainingClassPage, deletedBy)
        );
    }
    
    public void deleteTrainingClassPagesByTrainingClassSection(TrainingClassSection trainingClassSection, BasePK deletedBy) {
        deleteTrainingClassPages(getTrainingClassPagesForUpdate(trainingClassSection), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Training Class Page Translations
    // --------------------------------------------------------------------------------

    @Inject
    protected TrainingClassPageTranslationFactory trainingClassPageTranslationFactory;

    @Inject
    private TrainingClassPageTranslationTransferCache trainingClassPageTranslationTransferCache;

    public TrainingClassPageTranslation createTrainingClassPageTranslation(TrainingClassPage trainingClassPage, Language language, String description,
            MimeType pageMimeType, String page, BasePK createdBy) {
        var trainingClassPageTranslation = trainingClassPageTranslationFactory.create(trainingClassPage, language,
                description, pageMimeType, page, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(trainingClassPage.getPrimaryKey(), EventTypes.MODIFY, trainingClassPageTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return trainingClassPageTranslation;
    }
    
    private TrainingClassPageTranslation getTrainingClassPageTranslation(TrainingClassPage trainingClassPage, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassPageTranslations.fields())
                .from(TrainingClassPageTranslations)
                .where(TrainingClassPageTranslations.TRAINING_CLASS_PAGE.eq(trainingClassPage.getPrimaryKey()),
                        TrainingClassPageTranslations.LANGUAGE.eq(language.getPrimaryKey()),
                        TrainingClassPageTranslations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassPageTranslationFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TrainingClassPageTranslation getTrainingClassPageTranslation(TrainingClassPage trainingClassPage, Language language) {
        return getTrainingClassPageTranslation(trainingClassPage, language, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassPageTranslation getTrainingClassPageTranslationForUpdate(TrainingClassPage trainingClassPage, Language language) {
        return getTrainingClassPageTranslation(trainingClassPage, language, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassPageTranslationValue getTrainingClassPageTranslationValue(TrainingClassPageTranslation trainingClassPageTranslation) {
        return trainingClassPageTranslation == null? null: trainingClassPageTranslation.getTrainingClassPageTranslationValue().clone();
    }
    
    public TrainingClassPageTranslationValue getTrainingClassPageTranslationValueForUpdate(TrainingClassPage trainingClassPage, Language language) {
        return getTrainingClassPageTranslationValue(getTrainingClassPageTranslationForUpdate(trainingClassPage, language));
    }
    
    private List<TrainingClassPageTranslation> getTrainingClassPageTranslationsByTrainingClassPage(TrainingClassPage trainingClassPage,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TrainingClassPageTranslations.fields())
                    .from(TrainingClassPageTranslations)
                    .join(Languages).onKey(TRAINING_CLASS_PAGE_TRANSLATIONS_LANGUAGE_FK)
                    .where(TrainingClassPageTranslations.TRAINING_CLASS_PAGE.eq(trainingClassPage.getPrimaryKey()),
                            TrainingClassPageTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    TrainingClassPageTranslationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TrainingClassPageTranslations.fields())
                    .from(TrainingClassPageTranslations)
                    .where(TrainingClassPageTranslations.TRAINING_CLASS_PAGE.eq(trainingClassPage.getPrimaryKey()),
                            TrainingClassPageTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return trainingClassPageTranslationFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TrainingClassPageTranslation> getTrainingClassPageTranslationsByTrainingClassPage(TrainingClassPage trainingClassPage) {
        return getTrainingClassPageTranslationsByTrainingClassPage(trainingClassPage, EntityPermission.READ_ONLY);
    }
    
    public List<TrainingClassPageTranslation> getTrainingClassPageTranslationsByTrainingClassPageForUpdate(TrainingClassPage trainingClassPage) {
        return getTrainingClassPageTranslationsByTrainingClassPage(trainingClassPage, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassPageTranslation getBestTrainingClassPageTranslation(TrainingClassPage trainingClassPage, Language language) {
        var trainingClassPageTranslation = getTrainingClassPageTranslation(trainingClassPage, language);
        
        if(trainingClassPageTranslation == null && !language.getIsDefault()) {
            trainingClassPageTranslation = getTrainingClassPageTranslation(trainingClassPage, partyControl.getDefaultLanguage());
        }
        
        return trainingClassPageTranslation;
    }
    
    public TrainingClassPageTranslationTransfer getTrainingClassPageTranslationTransfer(UserVisit userVisit, TrainingClassPageTranslation trainingClassPageTranslation) {
        return trainingClassPageTranslationTransferCache.getTrainingClassPageTranslationTransfer(userVisit, trainingClassPageTranslation);
    }
    
    public List<TrainingClassPageTranslationTransfer> getTrainingClassPageTranslationTransfers(UserVisit userVisit, TrainingClassPage trainingClassPage) {
        var trainingClassPageTranslations = getTrainingClassPageTranslationsByTrainingClassPage(trainingClassPage);
        List<TrainingClassPageTranslationTransfer> trainingClassPageTranslationTransfers = new ArrayList<>(trainingClassPageTranslations.size());
        
        trainingClassPageTranslations.forEach((trainingClassPageTranslation) ->
                trainingClassPageTranslationTransfers.add(trainingClassPageTranslationTransferCache.getTrainingClassPageTranslationTransfer(userVisit, trainingClassPageTranslation))
        );
        
        return trainingClassPageTranslationTransfers;
    }
    
    public void updateTrainingClassPageTranslationFromValue(TrainingClassPageTranslationValue trainingClassPageTranslationValue, BasePK updatedBy) {
        if(trainingClassPageTranslationValue.hasBeenModified()) {
            var trainingClassPageTranslation = trainingClassPageTranslationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    trainingClassPageTranslationValue.getPrimaryKey());
            
            trainingClassPageTranslation.setThruTime(session.getStartTime());
            trainingClassPageTranslation.store();

            var trainingClassPagePK = trainingClassPageTranslation.getTrainingClassPagePK();
            var languagePK = trainingClassPageTranslation.getLanguagePK();
            var description = trainingClassPageTranslationValue.getDescription();
            var pageMimeTypePK = trainingClassPageTranslationValue.getPageMimeTypePK();
            var page = trainingClassPageTranslationValue.getPage();
            
            trainingClassPageTranslation = trainingClassPageTranslationFactory.create(trainingClassPagePK, languagePK, description,
                    pageMimeTypePK, page, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(trainingClassPagePK, EventTypes.MODIFY, trainingClassPageTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTrainingClassPageTranslation(TrainingClassPageTranslation trainingClassPageTranslation, BasePK deletedBy) {
        trainingClassPageTranslation.setThruTime(session.getStartTime());
        
        sendEvent(trainingClassPageTranslation.getTrainingClassPagePK(), EventTypes.MODIFY, trainingClassPageTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteTrainingClassPageTranslationsByTrainingClassPage(TrainingClassPage trainingClassPage, BasePK deletedBy) {
        var trainingClassPageTranslations = getTrainingClassPageTranslationsByTrainingClassPageForUpdate(trainingClassPage);
        
        trainingClassPageTranslations.forEach((trainingClassPageTranslation) -> 
                deleteTrainingClassPageTranslation(trainingClassPageTranslation, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Training Class Questions
    // --------------------------------------------------------------------------------

    @Inject
    protected TrainingClassQuestionDetailFactory trainingClassQuestionDetailFactory;

    @Inject
    protected TrainingClassQuestionFactory trainingClassQuestionFactory;

    @Inject
    private TrainingClassQuestionTransferCache trainingClassQuestionTransferCache;

    public TrainingClassQuestion createTrainingClassQuestion(TrainingClassSection trainingClassSection, String trainingClassQuestionName, Boolean askingRequired,
            Boolean passingRequired, Integer sortOrder, BasePK createdBy) {
        var trainingClassQuestion = trainingClassQuestionFactory.create();
        var trainingClassQuestionDetail = trainingClassQuestionDetailFactory.create(trainingClassQuestion,
                trainingClassSection, trainingClassQuestionName, askingRequired, passingRequired, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        trainingClassQuestion = trainingClassQuestionFactory.getEntityFromPK(EntityPermission.READ_WRITE, trainingClassQuestion.getPrimaryKey());
        trainingClassQuestion.setActiveDetail(trainingClassQuestionDetail);
        trainingClassQuestion.setLastDetail(trainingClassQuestionDetail);
        trainingClassQuestion.store();
        
        sendEvent(trainingClassQuestion.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return trainingClassQuestion;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TrainingClassQuestion */
    public TrainingClassQuestion getTrainingClassQuestionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TrainingClassQuestionPK(entityInstance.getEntityUniqueId());

        return trainingClassQuestionFactory.getEntityFromPK(entityPermission, pk);
    }

    public TrainingClassQuestion getTrainingClassQuestionByEntityInstance(EntityInstance entityInstance) {
        return getTrainingClassQuestionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TrainingClassQuestion getTrainingClassQuestionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTrainingClassQuestionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTrainingClassQuestionsByTrainingClassSection(final TrainingClassSection trainingClassSection) {
        return session.getDslContext()
                .selectCount()
                .from(TrainingClassQuestions)
                .join(TrainingClassQuestionDetails).onKey(TRAINING_CLASS_QUESTIONS_ACTIVE_DETAIL_FK)
                .where(TrainingClassQuestionDetails.TRAINING_CLASS_SECTION.eq(trainingClassSection.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countTrainingClassQuestions(List<TrainingClassSection> trainingClassSections) {
        long total = 0;

        total = trainingClassSections.stream().map(this::countTrainingClassQuestionsByTrainingClassSection).reduce(total, Long::sum);

        return total;
    }

    public long countTrainingClassQuestions(TrainingClass trainingClass) {
        return countTrainingClassQuestions(getTrainingClassSections(trainingClass));
    }

    public TrainingClassQuestion getTrainingClassQuestionByName(TrainingClassSection trainingClassSection, String trainingClassQuestionName,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassQuestions.fields())
                .from(TrainingClassQuestions)
                .join(TrainingClassQuestionDetails).onKey(TRAINING_CLASS_QUESTIONS_ACTIVE_DETAIL_FK)
                .where(TrainingClassQuestionDetails.TRAINING_CLASS_SECTION.eq(trainingClassSection.getPrimaryKey()),
                        TrainingClassQuestionDetails.TRAINING_CLASS_QUESTION_NAME.eq(trainingClassQuestionName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassQuestionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TrainingClassQuestion getTrainingClassQuestionByName(TrainingClassSection trainingClassSection, String trainingClassQuestionName) {
        return getTrainingClassQuestionByName(trainingClassSection, trainingClassQuestionName, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassQuestion getTrainingClassQuestionByNameForUpdate(TrainingClassSection trainingClassSection, String trainingClassQuestionName) {
        return getTrainingClassQuestionByName(trainingClassSection, trainingClassQuestionName, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassQuestionDetailValue getTrainingClassQuestionDetailValueForUpdate(TrainingClassQuestion trainingClassQuestion) {
        return trainingClassQuestion == null? null: trainingClassQuestion.getLastDetailForUpdate().getTrainingClassQuestionDetailValue().clone();
    }
    
    public TrainingClassQuestionDetailValue getTrainingClassQuestionDetailValueByNameForUpdate(TrainingClassSection trainingClassSection,
            String trainingClassQuestionName) {
        return getTrainingClassQuestionDetailValueForUpdate(getTrainingClassQuestionByNameForUpdate(trainingClassSection, trainingClassQuestionName));
    }
    
    private List<TrainingClassQuestion> getTrainingClassQuestions(EntityPermission entityPermission, TrainingClassSection trainingClassSection) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassQuestions.fields())
                .from(TrainingClassQuestions)
                .join(TrainingClassQuestionDetails).onKey(TRAINING_CLASS_QUESTIONS_ACTIVE_DETAIL_FK)
                .where(TrainingClassQuestionDetails.TRAINING_CLASS_SECTION.eq(trainingClassSection.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TrainingClassQuestionDetails.SORT_ORDER, TrainingClassQuestionDetails.TRAINING_CLASS_QUESTION_NAME),
                    TrainingClassQuestionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassQuestionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TrainingClassQuestion> getTrainingClassQuestions(TrainingClassSection trainingClassSection) {
        return getTrainingClassQuestions(EntityPermission.READ_ONLY, trainingClassSection);
    }
    
    public List<TrainingClassQuestion> getTrainingClassQuestionsForUpdate(TrainingClassSection trainingClassSection) {
        return getTrainingClassQuestions(EntityPermission.READ_WRITE, trainingClassSection);
    }
    
    public TrainingClassQuestionTransfer getTrainingClassQuestionTransfer(UserVisit userVisit, TrainingClassQuestion trainingClassQuestion) {
        return trainingClassQuestionTransferCache.getTrainingClassQuestionTransfer(userVisit, trainingClassQuestion);
    }
    
    public List<TrainingClassQuestionTransfer> getTrainingClassQuestionTransfers(UserVisit userVisit, Collection<TrainingClassQuestion> trainingClassQuestions) {
        List<TrainingClassQuestionTransfer> trainingClassQuestionTransfers = new ArrayList<>(trainingClassQuestions.size());
        
        trainingClassQuestions.forEach((trainingClassQuestion) ->
                trainingClassQuestionTransfers.add(trainingClassQuestionTransferCache.getTrainingClassQuestionTransfer(userVisit, trainingClassQuestion))
        );
        
        return trainingClassQuestionTransfers;
    }
    
    public List<TrainingClassQuestionTransfer> getTrainingClassQuestionTransfers(UserVisit userVisit, TrainingClassSection trainingClassSection) {
        return getTrainingClassQuestionTransfers(userVisit, getTrainingClassQuestions(trainingClassSection));
    }
    
    public void updateTrainingClassQuestionFromValue(TrainingClassQuestionDetailValue trainingClassQuestionDetailValue, BasePK updatedBy) {
        if(trainingClassQuestionDetailValue.hasBeenModified()) {
            var trainingClassQuestion = trainingClassQuestionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     trainingClassQuestionDetailValue.getTrainingClassQuestionPK());
            var trainingClassQuestionDetail = trainingClassQuestion.getActiveDetailForUpdate();
            
            trainingClassQuestionDetail.setThruTime(session.getStartTime());
            trainingClassQuestionDetail.store();

            var trainingClassQuestionPK = trainingClassQuestionDetail.getTrainingClassQuestionPK(); // Not updated
            var trainingClassSectionPK = trainingClassQuestionDetail.getTrainingClassSectionPK(); // Not updated
            var trainingClassQuestionName = trainingClassQuestionDetailValue.getTrainingClassQuestionName();
            var askingRequired = trainingClassQuestionDetailValue.getAskingRequired();
            var passingRequired = trainingClassQuestionDetailValue.getPassingRequired();
            var sortOrder = trainingClassQuestionDetailValue.getSortOrder();
            
            trainingClassQuestionDetail = trainingClassQuestionDetailFactory.create(trainingClassQuestionPK, trainingClassSectionPK,
                    trainingClassQuestionName, askingRequired, passingRequired, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            trainingClassQuestion.setActiveDetail(trainingClassQuestionDetail);
            trainingClassQuestion.setLastDetail(trainingClassQuestionDetail);
            
            sendEvent(trainingClassQuestionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteTrainingClassQuestion(TrainingClassQuestion trainingClassQuestion, BasePK deletedBy) {
        deleteTrainingClassQuestionTranslationsByTrainingClassQuestion(trainingClassQuestion, deletedBy);
        deleteTrainingClassAnswersByTrainingClassQuestion(trainingClassQuestion, deletedBy);
        deletePartyTrainingClassSessionQuestionsByTrainingClassQuestion(trainingClassQuestion, deletedBy);

        var trainingClassQuestionDetail = trainingClassQuestion.getLastDetailForUpdate();
        trainingClassQuestionDetail.setThruTime(session.getStartTime());
        trainingClassQuestion.setActiveDetail(null);
        trainingClassQuestion.store();
        
        sendEvent(trainingClassQuestion.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteTrainingClassQuestions(List<TrainingClassQuestion> trainingClassQuestions, BasePK deletedBy) {
        trainingClassQuestions.forEach((trainingClassQuestion) -> 
                deleteTrainingClassQuestion(trainingClassQuestion, deletedBy)
        );
    }
    
    public void deleteTrainingClassQuestionsByTrainingClassSection(TrainingClassSection trainingClassSection, BasePK deletedBy) {
        deleteTrainingClassQuestions(getTrainingClassQuestionsForUpdate(trainingClassSection), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Training Class Question Translations
    // --------------------------------------------------------------------------------

    @Inject
    protected TrainingClassQuestionTranslationFactory trainingClassQuestionTranslationFactory;

    @Inject
    private TrainingClassQuestionTranslationTransferCache trainingClassQuestionTranslationTransferCache;

    public TrainingClassQuestionTranslation createTrainingClassQuestionTranslation(TrainingClassQuestion trainingClassQuestion, Language language,
            MimeType questionMimeType, String question, BasePK createdBy) {
        var trainingClassQuestionTranslation = trainingClassQuestionTranslationFactory.create(trainingClassQuestion,
                language, questionMimeType, question, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(trainingClassQuestion.getPrimaryKey(), EventTypes.MODIFY, trainingClassQuestionTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return trainingClassQuestionTranslation;
    }
    
    private TrainingClassQuestionTranslation getTrainingClassQuestionTranslation(TrainingClassQuestion trainingClassQuestion, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassQuestionTranslations.fields())
                .from(TrainingClassQuestionTranslations)
                .where(TrainingClassQuestionTranslations.TRAINING_CLASS_QUESTION.eq(trainingClassQuestion.getPrimaryKey()),
                        TrainingClassQuestionTranslations.LANGUAGE.eq(language.getPrimaryKey()),
                        TrainingClassQuestionTranslations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassQuestionTranslationFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TrainingClassQuestionTranslation getTrainingClassQuestionTranslation(TrainingClassQuestion trainingClassQuestion, Language language) {
        return getTrainingClassQuestionTranslation(trainingClassQuestion, language, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassQuestionTranslation getTrainingClassQuestionTranslationForUpdate(TrainingClassQuestion trainingClassQuestion, Language language) {
        return getTrainingClassQuestionTranslation(trainingClassQuestion, language, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassQuestionTranslationValue getTrainingClassQuestionTranslationValue(TrainingClassQuestionTranslation trainingClassQuestionTranslation) {
        return trainingClassQuestionTranslation == null? null: trainingClassQuestionTranslation.getTrainingClassQuestionTranslationValue().clone();
    }
    
    public TrainingClassQuestionTranslationValue getTrainingClassQuestionTranslationValueForUpdate(TrainingClassQuestion trainingClassQuestion, Language language) {
        return getTrainingClassQuestionTranslationValue(getTrainingClassQuestionTranslationForUpdate(trainingClassQuestion, language));
    }
    
    private List<TrainingClassQuestionTranslation> getTrainingClassQuestionTranslationsByTrainingClassQuestion(TrainingClassQuestion trainingClassQuestion,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TrainingClassQuestionTranslations.fields())
                    .from(TrainingClassQuestionTranslations)
                    .join(Languages).onKey(TRAINING_CLASS_QUESTION_TRANSLATIONS_LANGUAGE_FK)
                    .where(TrainingClassQuestionTranslations.TRAINING_CLASS_QUESTION.eq(trainingClassQuestion.getPrimaryKey()),
                            TrainingClassQuestionTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    TrainingClassQuestionTranslationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TrainingClassQuestionTranslations.fields())
                    .from(TrainingClassQuestionTranslations)
                    .where(TrainingClassQuestionTranslations.TRAINING_CLASS_QUESTION.eq(trainingClassQuestion.getPrimaryKey()),
                            TrainingClassQuestionTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return trainingClassQuestionTranslationFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TrainingClassQuestionTranslation> getTrainingClassQuestionTranslationsByTrainingClassQuestion(TrainingClassQuestion trainingClassQuestion) {
        return getTrainingClassQuestionTranslationsByTrainingClassQuestion(trainingClassQuestion, EntityPermission.READ_ONLY);
    }
    
    public List<TrainingClassQuestionTranslation> getTrainingClassQuestionTranslationsByTrainingClassQuestionForUpdate(TrainingClassQuestion trainingClassQuestion) {
        return getTrainingClassQuestionTranslationsByTrainingClassQuestion(trainingClassQuestion, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassQuestionTranslation getBestTrainingClassQuestionTranslation(TrainingClassQuestion trainingClassQuestion, Language language) {
        var trainingClassQuestionTranslation = getTrainingClassQuestionTranslation(trainingClassQuestion, language);
        
        if(trainingClassQuestionTranslation == null && !language.getIsDefault()) {
            trainingClassQuestionTranslation = getTrainingClassQuestionTranslation(trainingClassQuestion, partyControl.getDefaultLanguage());
        }
        
        return trainingClassQuestionTranslation;
    }
    
    
    public TrainingClassQuestionTranslationTransfer getTrainingClassQuestionTranslationTransfer(UserVisit userVisit, TrainingClassQuestionTranslation trainingClassQuestionTranslation) {
        return trainingClassQuestionTranslationTransferCache.getTrainingClassQuestionTranslationTransfer(userVisit, trainingClassQuestionTranslation);
    }
    
    public List<TrainingClassQuestionTranslationTransfer> getTrainingClassQuestionTranslationTransfers(UserVisit userVisit, TrainingClassQuestion trainingClassQuestion) {
        var trainingClassQuestionTranslations = getTrainingClassQuestionTranslationsByTrainingClassQuestion(trainingClassQuestion);
        List<TrainingClassQuestionTranslationTransfer> trainingClassQuestionTranslationTransfers = new ArrayList<>(trainingClassQuestionTranslations.size());
        
        trainingClassQuestionTranslations.forEach((trainingClassQuestionTranslation) ->
                trainingClassQuestionTranslationTransfers.add(trainingClassQuestionTranslationTransferCache.getTrainingClassQuestionTranslationTransfer(userVisit, trainingClassQuestionTranslation))
        );
        
        return trainingClassQuestionTranslationTransfers;
    }
    
    public void updateTrainingClassQuestionTranslationFromValue(TrainingClassQuestionTranslationValue trainingClassQuestionTranslationValue, BasePK updatedBy) {
        if(trainingClassQuestionTranslationValue.hasBeenModified()) {
            var trainingClassQuestionTranslation = trainingClassQuestionTranslationFactory.getEntityFromPK(EntityPermission.READ_WRITE, trainingClassQuestionTranslationValue.getPrimaryKey());
            
            trainingClassQuestionTranslation.setThruTime(session.getStartTime());
            trainingClassQuestionTranslation.store();

            var trainingClassQuestionPK = trainingClassQuestionTranslation.getTrainingClassQuestionPK();
            var languagePK = trainingClassQuestionTranslation.getLanguagePK();
            var questionMimeTypePK = trainingClassQuestionTranslationValue.getQuestionMimeTypePK();
            var question = trainingClassQuestionTranslationValue.getQuestion();
            
            trainingClassQuestionTranslation = trainingClassQuestionTranslationFactory.create(trainingClassQuestionPK, languagePK,
                    questionMimeTypePK, question, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(trainingClassQuestionPK, EventTypes.MODIFY, trainingClassQuestionTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTrainingClassQuestionTranslation(TrainingClassQuestionTranslation trainingClassQuestionTranslation, BasePK deletedBy) {
        trainingClassQuestionTranslation.setThruTime(session.getStartTime());
        
        sendEvent(trainingClassQuestionTranslation.getTrainingClassQuestionPK(), EventTypes.MODIFY, trainingClassQuestionTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteTrainingClassQuestionTranslationsByTrainingClassQuestion(TrainingClassQuestion trainingClassQuestion, BasePK deletedBy) {
        var trainingClassQuestionTranslations = getTrainingClassQuestionTranslationsByTrainingClassQuestionForUpdate(trainingClassQuestion);
        
        trainingClassQuestionTranslations.forEach((trainingClassQuestionTranslation) -> 
                deleteTrainingClassQuestionTranslation(trainingClassQuestionTranslation, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Training Class Answers
    // --------------------------------------------------------------------------------

    @Inject
    protected TrainingClassAnswerDetailFactory trainingClassAnswerDetailFactory;

    @Inject
    protected TrainingClassAnswerFactory trainingClassAnswerFactory;

    @Inject
    private TrainingClassAnswerTransferCache trainingClassAnswerTransferCache;

    public TrainingClassAnswer createTrainingClassAnswer(TrainingClassQuestion trainingClassQuestion, String trainingClassAnswerName, Boolean isCorrect,
            Integer sortOrder, BasePK createdBy) {
        var trainingClassAnswer = trainingClassAnswerFactory.create();
        var trainingClassAnswerDetail = trainingClassAnswerDetailFactory.create(trainingClassAnswer, trainingClassQuestion,
                trainingClassAnswerName, isCorrect, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        trainingClassAnswer = trainingClassAnswerFactory.getEntityFromPK(EntityPermission.READ_WRITE, trainingClassAnswer.getPrimaryKey());
        trainingClassAnswer.setActiveDetail(trainingClassAnswerDetail);
        trainingClassAnswer.setLastDetail(trainingClassAnswerDetail);
        trainingClassAnswer.store();
        
        sendEvent(trainingClassAnswer.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return trainingClassAnswer;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TrainingClassAnswer */
    public TrainingClassAnswer getTrainingClassAnswerByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TrainingClassAnswerPK(entityInstance.getEntityUniqueId());

        return trainingClassAnswerFactory.getEntityFromPK(entityPermission, pk);
    }

    public TrainingClassAnswer getTrainingClassAnswerByEntityInstance(EntityInstance entityInstance) {
        return getTrainingClassAnswerByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TrainingClassAnswer getTrainingClassAnswerByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTrainingClassAnswerByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTrainingClassAnswersByTrainingClassQuestion(final TrainingClassQuestion trainingClassQuestion) {
        return session.getDslContext()
                .selectCount()
                .from(TrainingClassAnswers)
                .join(TrainingClassAnswerDetails).onKey(TRAINING_CLASS_ANSWERS_ACTIVE_DETAIL_FK)
                .where(TrainingClassAnswerDetails.TRAINING_CLASS_QUESTION.eq(trainingClassQuestion.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public TrainingClassAnswer getTrainingClassAnswerByName(TrainingClassQuestion trainingClassQuestion, String trainingClassAnswerName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassAnswers.fields())
                .from(TrainingClassAnswers)
                .join(TrainingClassAnswerDetails).onKey(TRAINING_CLASS_ANSWERS_ACTIVE_DETAIL_FK)
                .where(TrainingClassAnswerDetails.TRAINING_CLASS_QUESTION.eq(trainingClassQuestion.getPrimaryKey()),
                        TrainingClassAnswerDetails.TRAINING_CLASS_ANSWER_NAME.eq(trainingClassAnswerName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassAnswerFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TrainingClassAnswer getTrainingClassAnswerByName(TrainingClassQuestion trainingClassQuestion, String trainingClassAnswerName) {
        return getTrainingClassAnswerByName(trainingClassQuestion, trainingClassAnswerName, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassAnswer getTrainingClassAnswerByNameForUpdate(TrainingClassQuestion trainingClassQuestion, String trainingClassAnswerName) {
        return getTrainingClassAnswerByName(trainingClassQuestion, trainingClassAnswerName, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassAnswerDetailValue getTrainingClassAnswerDetailValueForUpdate(TrainingClassAnswer trainingClassAnswer) {
        return trainingClassAnswer == null? null: trainingClassAnswer.getLastDetailForUpdate().getTrainingClassAnswerDetailValue().clone();
    }
    
    public TrainingClassAnswerDetailValue getTrainingClassAnswerDetailValueByNameForUpdate(TrainingClassQuestion trainingClassQuestion, String trainingClassAnswerName) {
        return getTrainingClassAnswerDetailValueForUpdate(getTrainingClassAnswerByNameForUpdate(trainingClassQuestion, trainingClassAnswerName));
    }
    
    private List<TrainingClassAnswer> getTrainingClassAnswers(EntityPermission entityPermission, TrainingClassQuestion trainingClassQuestion) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassAnswers.fields())
                .from(TrainingClassAnswers)
                .join(TrainingClassAnswerDetails).onKey(TRAINING_CLASS_ANSWERS_ACTIVE_DETAIL_FK)
                .where(TrainingClassAnswerDetails.TRAINING_CLASS_QUESTION.eq(trainingClassQuestion.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TrainingClassAnswerDetails.SORT_ORDER, TrainingClassAnswerDetails.TRAINING_CLASS_ANSWER_NAME),
                    TrainingClassAnswerFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassAnswerFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TrainingClassAnswer> getTrainingClassAnswers(TrainingClassQuestion trainingClassQuestion) {
        return getTrainingClassAnswers(EntityPermission.READ_ONLY, trainingClassQuestion);
    }
    
    public List<TrainingClassAnswer> getTrainingClassAnswersForUpdate(TrainingClassQuestion trainingClassQuestion) {
        return getTrainingClassAnswers(EntityPermission.READ_WRITE, trainingClassQuestion);
    }
    
    public TrainingClassAnswerTransfer getTrainingClassAnswerTransfer(UserVisit userVisit, TrainingClassAnswer trainingClassAnswer) {
        return trainingClassAnswerTransferCache.getTrainingClassAnswerTransfer(userVisit, trainingClassAnswer);
    }
    
    public List<TrainingClassAnswerTransfer> getTrainingClassAnswerTransfers(UserVisit userVisit, Collection<TrainingClassAnswer> trainingClassAnswers) {
        List<TrainingClassAnswerTransfer> trainingClassAnswerTransfers = new ArrayList<>(trainingClassAnswers.size());
        
        trainingClassAnswers.forEach((trainingClassAnswer) ->
                trainingClassAnswerTransfers.add(trainingClassAnswerTransferCache.getTrainingClassAnswerTransfer(userVisit, trainingClassAnswer))
        );
        
        return trainingClassAnswerTransfers;
    }
    
    public List<TrainingClassAnswerTransfer> getTrainingClassAnswerTransfers(UserVisit userVisit, TrainingClassQuestion trainingClassQuestion) {
        return getTrainingClassAnswerTransfers(userVisit, getTrainingClassAnswers(trainingClassQuestion));
    }
    
    public void updateTrainingClassAnswerFromValue(TrainingClassAnswerDetailValue trainingClassAnswerDetailValue, BasePK updatedBy) {
        if(trainingClassAnswerDetailValue.hasBeenModified()) {
            var trainingClassAnswer = trainingClassAnswerFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     trainingClassAnswerDetailValue.getTrainingClassAnswerPK());
            var trainingClassAnswerDetail = trainingClassAnswer.getActiveDetailForUpdate();
            
            trainingClassAnswerDetail.setThruTime(session.getStartTime());
            trainingClassAnswerDetail.store();

            var trainingClassAnswerPK = trainingClassAnswerDetail.getTrainingClassAnswerPK(); // Not updated
            var trainingClassQuestionPK = trainingClassAnswerDetail.getTrainingClassQuestionPK(); // Not updated
            var trainingClassAnswerName = trainingClassAnswerDetailValue.getTrainingClassAnswerName();
            var isCorrect = trainingClassAnswerDetailValue.getIsCorrect();
            var sortOrder = trainingClassAnswerDetailValue.getSortOrder();
            
            trainingClassAnswerDetail = trainingClassAnswerDetailFactory.create(trainingClassAnswerPK, trainingClassQuestionPK,
                    trainingClassAnswerName, isCorrect, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            trainingClassAnswer.setActiveDetail(trainingClassAnswerDetail);
            trainingClassAnswer.setLastDetail(trainingClassAnswerDetail);
            
            sendEvent(trainingClassAnswerPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteTrainingClassAnswer(TrainingClassAnswer trainingClassAnswer, BasePK deletedBy) {
        deleteTrainingClassAnswerTranslationsByTrainingClassAnswer(trainingClassAnswer, deletedBy);
        deletePartyTrainingClassSessionAnswersByTrainingClassAnswer(trainingClassAnswer, deletedBy);

        var trainingClassAnswerDetail = trainingClassAnswer.getLastDetailForUpdate();
        trainingClassAnswerDetail.setThruTime(session.getStartTime());
        trainingClassAnswer.setActiveDetail(null);
        trainingClassAnswer.store();
        
        sendEvent(trainingClassAnswer.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteTrainingClassAnswers(List<TrainingClassAnswer> trainingClassAnswers, BasePK deletedBy) {
        trainingClassAnswers.forEach((trainingClassAnswer) -> 
                deleteTrainingClassAnswer(trainingClassAnswer, deletedBy)
        );
    }
    
    public void deleteTrainingClassAnswersByTrainingClassQuestion(TrainingClassQuestion trainingClassQuestion, BasePK deletedBy) {
        deleteTrainingClassAnswers(getTrainingClassAnswersForUpdate(trainingClassQuestion), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Training Class Answer Translations
    // --------------------------------------------------------------------------------

    @Inject
    protected TrainingClassAnswerTranslationFactory trainingClassAnswerTranslationFactory;

    @Inject
    private TrainingClassAnswerTranslationTransferCache trainingClassAnswerTranslationTransferCache;

    public TrainingClassAnswerTranslation createTrainingClassAnswerTranslation(TrainingClassAnswer trainingClassAnswer, Language language,
            MimeType answerMimeType, String answer, MimeType selectedMimeType, String selected, BasePK createdBy) {
        var trainingClassAnswerTranslation = trainingClassAnswerTranslationFactory.create(trainingClassAnswer,
                language, answerMimeType, answer, selectedMimeType, selected, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(trainingClassAnswer.getPrimaryKey(), EventTypes.MODIFY, trainingClassAnswerTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return trainingClassAnswerTranslation;
    }
    
    private TrainingClassAnswerTranslation getTrainingClassAnswerTranslation(TrainingClassAnswer trainingClassAnswer, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrainingClassAnswerTranslations.fields())
                .from(TrainingClassAnswerTranslations)
                .where(TrainingClassAnswerTranslations.TRAINING_CLASS_ANSWER.eq(trainingClassAnswer.getPrimaryKey()),
                        TrainingClassAnswerTranslations.LANGUAGE.eq(language.getPrimaryKey()),
                        TrainingClassAnswerTranslations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trainingClassAnswerTranslationFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TrainingClassAnswerTranslation getTrainingClassAnswerTranslation(TrainingClassAnswer trainingClassAnswer, Language language) {
        return getTrainingClassAnswerTranslation(trainingClassAnswer, language, EntityPermission.READ_ONLY);
    }
    
    public TrainingClassAnswerTranslation getTrainingClassAnswerTranslationForUpdate(TrainingClassAnswer trainingClassAnswer, Language language) {
        return getTrainingClassAnswerTranslation(trainingClassAnswer, language, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassAnswerTranslationValue getTrainingClassAnswerTranslationValue(TrainingClassAnswerTranslation trainingClassAnswerTranslation) {
        return trainingClassAnswerTranslation == null? null: trainingClassAnswerTranslation.getTrainingClassAnswerTranslationValue().clone();
    }
    
    public TrainingClassAnswerTranslationValue getTrainingClassAnswerTranslationValueForUpdate(TrainingClassAnswer trainingClassAnswer, Language language) {
        return getTrainingClassAnswerTranslationValue(getTrainingClassAnswerTranslationForUpdate(trainingClassAnswer, language));
    }
    
    private List<TrainingClassAnswerTranslation> getTrainingClassAnswerTranslationsByTrainingClassAnswer(TrainingClassAnswer trainingClassAnswer,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TrainingClassAnswerTranslations.fields())
                    .from(TrainingClassAnswerTranslations)
                    .join(Languages).onKey(TRAINING_CLASS_ANSWER_TRANSLATIONS_LANGUAGE_FK)
                    .where(TrainingClassAnswerTranslations.TRAINING_CLASS_ANSWER.eq(trainingClassAnswer.getPrimaryKey()),
                            TrainingClassAnswerTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    TrainingClassAnswerTranslationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TrainingClassAnswerTranslations.fields())
                    .from(TrainingClassAnswerTranslations)
                    .where(TrainingClassAnswerTranslations.TRAINING_CLASS_ANSWER.eq(trainingClassAnswer.getPrimaryKey()),
                            TrainingClassAnswerTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return trainingClassAnswerTranslationFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TrainingClassAnswerTranslation> getTrainingClassAnswerTranslationsByTrainingClassAnswer(TrainingClassAnswer trainingClassAnswer) {
        return getTrainingClassAnswerTranslationsByTrainingClassAnswer(trainingClassAnswer, EntityPermission.READ_ONLY);
    }
    
    public List<TrainingClassAnswerTranslation> getTrainingClassAnswerTranslationsByTrainingClassAnswerForUpdate(TrainingClassAnswer trainingClassAnswer) {
        return getTrainingClassAnswerTranslationsByTrainingClassAnswer(trainingClassAnswer, EntityPermission.READ_WRITE);
    }
    
    public TrainingClassAnswerTranslation getBestTrainingClassAnswerTranslation(TrainingClassAnswer trainingClassAnswer, Language language) {
        var trainingClassAnswerTranslation = getTrainingClassAnswerTranslation(trainingClassAnswer, language);
        
        if(trainingClassAnswerTranslation == null && !language.getIsDefault()) {
            trainingClassAnswerTranslation = getTrainingClassAnswerTranslation(trainingClassAnswer, partyControl.getDefaultLanguage());
        }
        
        return trainingClassAnswerTranslation;
    }
    
    public TrainingClassAnswerTranslationTransfer getTrainingClassAnswerTranslationTransfer(UserVisit userVisit, TrainingClassAnswerTranslation trainingClassAnswerTranslation) {
        return trainingClassAnswerTranslationTransferCache.getTrainingClassAnswerTranslationTransfer(userVisit, trainingClassAnswerTranslation);
    }
    
    public List<TrainingClassAnswerTranslationTransfer> getTrainingClassAnswerTranslationTransfers(UserVisit userVisit, TrainingClassAnswer trainingClassAnswer) {
        var trainingClassAnswerTranslations = getTrainingClassAnswerTranslationsByTrainingClassAnswer(trainingClassAnswer);
        List<TrainingClassAnswerTranslationTransfer> trainingClassAnswerTranslationTransfers = new ArrayList<>(trainingClassAnswerTranslations.size());
        
        trainingClassAnswerTranslations.forEach((trainingClassAnswerTranslation) ->
                trainingClassAnswerTranslationTransfers.add(trainingClassAnswerTranslationTransferCache.getTrainingClassAnswerTranslationTransfer(userVisit, trainingClassAnswerTranslation))
        );
        
        return trainingClassAnswerTranslationTransfers;
    }
    
    public void updateTrainingClassAnswerTranslationFromValue(TrainingClassAnswerTranslationValue trainingClassAnswerTranslationValue, BasePK updatedBy) {
        if(trainingClassAnswerTranslationValue.hasBeenModified()) {
            var trainingClassAnswerTranslation = trainingClassAnswerTranslationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    trainingClassAnswerTranslationValue.getPrimaryKey());
            
            trainingClassAnswerTranslation.setThruTime(session.getStartTime());
            trainingClassAnswerTranslation.store();

            var trainingClassAnswerPK = trainingClassAnswerTranslation.getTrainingClassAnswerPK();
            var languagePK = trainingClassAnswerTranslation.getLanguagePK();
            var answerMimeTypePK = trainingClassAnswerTranslationValue.getAnswerMimeTypePK();
            var answer = trainingClassAnswerTranslationValue.getAnswer();
            var selectedMimeTypePK = trainingClassAnswerTranslationValue.getSelectedMimeTypePK();
            var selected = trainingClassAnswerTranslationValue.getSelected();
            
            trainingClassAnswerTranslation = trainingClassAnswerTranslationFactory.create(trainingClassAnswerPK, languagePK, answerMimeTypePK,
                    answer, selectedMimeTypePK, selected, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(trainingClassAnswerPK, EventTypes.MODIFY, trainingClassAnswerTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTrainingClassAnswerTranslation(TrainingClassAnswerTranslation trainingClassAnswerTranslation, BasePK deletedBy) {
        trainingClassAnswerTranslation.setThruTime(session.getStartTime());
        
        sendEvent(trainingClassAnswerTranslation.getTrainingClassAnswerPK(), EventTypes.MODIFY, trainingClassAnswerTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteTrainingClassAnswerTranslationsByTrainingClassAnswer(TrainingClassAnswer trainingClassAnswer, BasePK deletedBy) {
        var trainingClassAnswerTranslations = getTrainingClassAnswerTranslationsByTrainingClassAnswerForUpdate(trainingClassAnswer);
        
        trainingClassAnswerTranslations.forEach((trainingClassAnswerTranslation) -> 
                deleteTrainingClassAnswerTranslation(trainingClassAnswerTranslation, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Training Classes
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyTrainingClassDetailFactory partyTrainingClassDetailFactory;

    @Inject
    protected PartyTrainingClassFactory partyTrainingClassFactory;

    @Inject
    private PartyTrainingClassTransferCache partyTrainingClassTransferCache;

    public PartyTrainingClass createPartyTrainingClass(Party party, TrainingClass trainingClass, Long completedTime, Long validUntilTime, BasePK createdBy) {
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.PARTY_TRAINING_CLASS.name());
        var partyTrainingClassName = sequenceGeneratorLogic.getNextSequenceValue(sequence);
        
        return createPartyTrainingClass(partyTrainingClassName, party, trainingClass, completedTime, validUntilTime, createdBy);
    }
    
    public PartyTrainingClass createPartyTrainingClass(String partyTrainingClassName, Party party, TrainingClass trainingClass, Long completedTime,
            Long validUntilTime, BasePK createdBy) {
        var partyTrainingClass = partyTrainingClassFactory.create();
        var partyTrainingClassDetail = partyTrainingClassDetailFactory.create(partyTrainingClass, partyTrainingClassName,
                party, trainingClass, completedTime, validUntilTime, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        partyTrainingClass = partyTrainingClassFactory.getEntityFromPK(EntityPermission.READ_WRITE, partyTrainingClass.getPrimaryKey());
        partyTrainingClass.setActiveDetail(partyTrainingClassDetail);
        partyTrainingClass.setLastDetail(partyTrainingClassDetail);
        partyTrainingClass.store();

        var partyTrainingClassPK = partyTrainingClass.getPrimaryKey();
        sendEvent(partyTrainingClassPK, EventTypes.CREATE, null, null, createdBy);
        sendEvent(party.getPrimaryKey(), EventTypes.TOUCH, partyTrainingClassPK, EventTypes.CREATE, createdBy);
        
        return partyTrainingClass;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartyTrainingClass */
    public PartyTrainingClass getPartyTrainingClassByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartyTrainingClassPK(entityInstance.getEntityUniqueId());

        return partyTrainingClassFactory.getEntityFromPK(entityPermission, pk);
    }

    public PartyTrainingClass getPartyTrainingClassByEntityInstance(EntityInstance entityInstance) {
        return getPartyTrainingClassByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PartyTrainingClass getPartyTrainingClassByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyTrainingClassByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPartyTrainingClassesByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTrainingClasses)
                .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_ACTIVE_DETAIL_FK)
                .where(PartyTrainingClassDetails.PARTY.eq(party.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyTrainingClassesByTrainingClass(final TrainingClass trainingClass) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTrainingClasses)
                .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_ACTIVE_DETAIL_FK)
                .where(PartyTrainingClassDetails.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyTrainingClasses(Party party, TrainingClass trainingClass, WorkflowStep workflowStep) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTrainingClasses)
                .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_ACTIVE_DETAIL_FK)
                .join(EntityInstances).on(PartyTrainingClasses.PARTY_TRAINING_CLASS.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                .join(WorkflowEntityStatuses).onKey(WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK)
                .where(PartyTrainingClassDetails.PARTY.eq(party.getPrimaryKey()),
                        PartyTrainingClassDetails.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()),
                        ComponentVendorDetails.COMPONENT_VENDOR_NAME.eq(com.echothree.model.control.core.common.ComponentVendors.ECHO_THREE.name()),
                        EntityTypeDetails.ENTITY_TYPE_NAME.eq(com.echothree.model.control.core.common.EntityTypes.PartyTrainingClass.name()),
                        WorkflowEntityStatuses.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyTrainingClassesUsingNames(Party party, TrainingClass trainingClass, String workflowStepName) {
        var workflow = workflowControl.getWorkflowByName(PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS);
        var workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);

        return countPartyTrainingClasses(party, trainingClass, workflowStep);
    }

    public PartyTrainingClass getPartyTrainingClassByName(String partyTrainingClassName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClasses.fields())
                .from(PartyTrainingClasses)
                .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_ACTIVE_DETAIL_FK)
                .where(PartyTrainingClassDetails.PARTY_TRAINING_CLASS_NAME.eq(partyTrainingClassName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyTrainingClass getPartyTrainingClassByName(String partyTrainingClassName) {
        return getPartyTrainingClassByName(partyTrainingClassName, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClass getPartyTrainingClassByNameForUpdate(String partyTrainingClassName) {
        return getPartyTrainingClassByName(partyTrainingClassName, EntityPermission.READ_WRITE);
    }
    
    public PartyTrainingClassDetailValue getPartyTrainingClassDetailValueForUpdate(PartyTrainingClass partyTrainingClass) {
        return partyTrainingClass == null? null: partyTrainingClass.getLastDetailForUpdate().getPartyTrainingClassDetailValue().clone();
    }
    
    public PartyTrainingClassDetailValue getPartyTrainingClassDetailValueByNameForUpdate(String partyTrainingClassName) {
        return getPartyTrainingClassDetailValueForUpdate(getPartyTrainingClassByNameForUpdate(partyTrainingClassName));
    }
    
    private List<PartyTrainingClass> getPartyTrainingClassesByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTrainingClasses.fields())
                    .from(PartyTrainingClasses)
                    .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_ACTIVE_DETAIL_FK)
                    .join(TrainingClasses).onKey(PARTY_TRAINING_CLASS_DETAILS_TRAINING_CLASS_FK)
                    .join(TrainingClassDetails).onKey(TRAINING_CLASSES_LAST_DETAIL_FK)
                    .where(PartyTrainingClassDetails.PARTY.eq(party.getPrimaryKey()))
                    .orderBy(TrainingClassDetails.SORT_ORDER, TrainingClassDetails.TRAINING_CLASS_NAME, PartyTrainingClassDetails.PARTY_TRAINING_CLASS_NAME),
                    PartyTrainingClassFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTrainingClasses.fields())
                    .from(PartyTrainingClasses)
                    .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_ACTIVE_DETAIL_FK)
                    .where(PartyTrainingClassDetails.PARTY.eq(party.getPrimaryKey()))
                    .forUpdate();
        };

        return partyTrainingClassFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClass> getPartyTrainingClassesByParty(Party party) {
        return getPartyTrainingClassesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClass> getPartyTrainingClassesByPartyForUpdate(Party party) {
        return getPartyTrainingClassesByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyTrainingClass> getPartyTrainingClassesByTrainingClass(TrainingClass trainingClass, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTrainingClasses.fields())
                    .from(PartyTrainingClasses)
                    .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(PARTY_TRAINING_CLASS_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartyTrainingClassDetails.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyTrainingClassFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTrainingClasses.fields())
                    .from(PartyTrainingClasses)
                    .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_ACTIVE_DETAIL_FK)
                    .where(PartyTrainingClassDetails.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()))
                    .forUpdate();
        };

        return partyTrainingClassFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClass> getPartyTrainingClassesByTrainingClass(TrainingClass trainingClass) {
        return getPartyTrainingClassesByTrainingClass(trainingClass, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClass> getPartyTrainingClassesByTrainingClassForUpdate(TrainingClass trainingClass) {
        return getPartyTrainingClassesByTrainingClass(trainingClass, EntityPermission.READ_WRITE);
    }
    
    private List<PartyTrainingClass> getPartyTrainingClassesByStatus(Party party, TrainingClass trainingClass, String workflowStepName,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClasses.fields())
                .from(PartyTrainingClasses)
                .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_ACTIVE_DETAIL_FK)
                .join(EntityInstances).on(PartyTrainingClasses.PARTY_TRAINING_CLASS.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                .join(WorkflowEntityStatuses).onKey(WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK)
                .join(WorkflowSteps).onKey(WORKFLOW_ENTITY_STATUSES_WORKFLOW_STEP_FK)
                .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_ACTIVE_DETAIL_FK)
                .join(Workflows).onKey(WORKFLOW_STEP_DETAILS_WORKFLOW_FK)
                .join(WorkflowDetails).onKey(WORKFLOWS_ACTIVE_DETAIL_FK)
                .where(PartyTrainingClassDetails.PARTY.eq(party.getPrimaryKey()),
                        PartyTrainingClassDetails.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()),
                        WorkflowDetails.WORKFLOW_NAME.eq(PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS),
                        WorkflowStepDetails.WORKFLOW_STEP_NAME.eq(workflowStepName),
                        ComponentVendorDetails.COMPONENT_VENDOR_NAME.eq(com.echothree.model.control.core.common.ComponentVendors.ECHO_THREE.name()),
                        EntityTypeDetails.ENTITY_TYPE_NAME.eq(com.echothree.model.control.core.common.EntityTypes.PartyTrainingClass.name()),
                        WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PartyTrainingClassDetails.PARTY_TRAINING_CLASS_NAME),
                    PartyTrainingClassFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClass> getPartyTrainingClassesByStatus(Party party, TrainingClass trainingClass, String workflowStepName) {
        return getPartyTrainingClassesByStatus(party, trainingClass, workflowStepName, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClass> getPartyTrainingClassesByStatusForUpdate(Party party, TrainingClass trainingClass, String workflowStepName) {
        return getPartyTrainingClassesByStatus(party, trainingClass, workflowStepName, EntityPermission.READ_WRITE);
    }
    
    private Set<PartyTrainingClass> getPartyTrainingClassesByStatuses(EntityPermission entityPermission, Party party, TrainingClass trainingClass,
            final String ... workflowStepNames) {
        Set<PartyTrainingClass> partyTrainingClasses = new HashSet<>();

        for(var i = 0; i < workflowStepNames.length ; i++) {
            partyTrainingClasses.addAll(getPartyTrainingClassesByStatus(party, trainingClass, workflowStepNames[i], entityPermission));
        }

        return partyTrainingClasses;
    }

    public Set<PartyTrainingClass> getPartyTrainingClassesByStatuses(Party party, TrainingClass trainingClass, final String ... workflowStepNames) {
        return getPartyTrainingClassesByStatuses(EntityPermission.READ_ONLY, party, trainingClass, workflowStepNames);
    }

    public Set<PartyTrainingClass> getPartyTrainingClassesByStatusesForUpdate(Party party, TrainingClass trainingClass, final String ... workflowStepNames) {
        return getPartyTrainingClassesByStatuses(EntityPermission.READ_WRITE, party, trainingClass, workflowStepNames);
    }

    public PartyTrainingClassStatusChoicesBean getPartyTrainingClassStatusChoices(String defaultPartyTrainingClassStatusChoice, Language language,
            boolean allowNullChoice, PartyTrainingClass partyTrainingClass, PartyPK partyPK) {
        var partyTrainingClassStatusChoicesBean = new PartyTrainingClassStatusChoicesBean();

        if(partyTrainingClass == null) {
            workflowControl.getWorkflowEntranceChoices(partyTrainingClassStatusChoicesBean, defaultPartyTrainingClassStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS), partyPK);
        } else {
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyTrainingClass.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(partyTrainingClassStatusChoicesBean, defaultPartyTrainingClassStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return partyTrainingClassStatusChoicesBean;
    }

    public void setPartyTrainingClassStatus(ExecutionErrorAccumulator eea, PartyTrainingClass partyTrainingClass, String partyTrainingClassStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(partyTrainingClass);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS,
                entityInstance);
        var workflowDestination = partyTrainingClassStatusChoice == null ? null
                : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), partyTrainingClassStatusChoice);

        if(workflowDestination != null || partyTrainingClassStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPartyTrainingClassStatusChoice.name(), partyTrainingClassStatusChoice);
        }
    }
    
    public PartyTrainingClassTransfer getPartyTrainingClassTransfer(UserVisit userVisit, PartyTrainingClass partyTrainingClass) {
        return partyTrainingClassTransferCache.getPartyTrainingClassTransfer(userVisit, partyTrainingClass);
    }
    
    public List<PartyTrainingClassTransfer> getPartyTrainingClassTransfers(UserVisit userVisit, Collection<PartyTrainingClass> partyTrainingClasses) {
        List<PartyTrainingClassTransfer> partyTrainingClassTransfers = new ArrayList<>(partyTrainingClasses.size());
        
        partyTrainingClasses.forEach((partyTrainingClass) ->
                partyTrainingClassTransfers.add(partyTrainingClassTransferCache.getPartyTrainingClassTransfer(userVisit, partyTrainingClass))
        );
        
        return partyTrainingClassTransfers;
    }
    
    public List<PartyTrainingClassTransfer> getPartyTrainingClassTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyTrainingClassTransfers(userVisit, getPartyTrainingClassesByParty(party));
    }
    
    public List<PartyTrainingClassTransfer> getPartyTrainingClassTransfersByTrainingClass(UserVisit userVisit, TrainingClass trainingClass) {
        return getPartyTrainingClassTransfers(userVisit, getPartyTrainingClassesByTrainingClass(trainingClass));
    }
    
    public void updatePartyTrainingClassFromValue(PartyTrainingClassDetailValue partyTrainingClassDetailValue, BasePK updatedBy) {
        if(partyTrainingClassDetailValue.hasBeenModified()) {
            var partyTrainingClass = partyTrainingClassFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTrainingClassDetailValue.getPartyTrainingClassPK());
            var partyTrainingClassDetail = partyTrainingClass.getActiveDetailForUpdate();
            
            partyTrainingClassDetail.setThruTime(session.getStartTime());
            partyTrainingClassDetail.store();

            var partyTrainingClassPK = partyTrainingClassDetail.getPartyTrainingClassPK();
            var partyTrainingClassName = partyTrainingClassDetail.getPartyTrainingClassName(); // Not updated
            var party = partyTrainingClassDetail.getParty();
            var partyPK = party.getPrimaryKey(); // Not updated
            var trainingClassPK = partyTrainingClassDetail.getTrainingClassPK(); // Not updated
            var completedTime = partyTrainingClassDetailValue.getCompletedTime();
            var validUntilTime = partyTrainingClassDetailValue.getValidUntilTime();
            
            partyTrainingClassDetail = partyTrainingClassDetailFactory.create(partyTrainingClassPK, partyTrainingClassName, partyPK,
                    trainingClassPK, completedTime, validUntilTime, session.getStartTime(), Session.MAX_TIME);
            
            partyTrainingClass.setActiveDetail(partyTrainingClassDetail);
            partyTrainingClass.setLastDetail(partyTrainingClassDetail);
            
            sendEvent(partyTrainingClassPK, EventTypes.MODIFY, null, null, updatedBy);
            sendEvent(partyPK, EventTypes.TOUCH, partyTrainingClassPK, EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyTrainingClass(PartyTrainingClass partyTrainingClass, BasePK deletedBy) {
        var partyTrainingClassDetail = partyTrainingClass.getLastDetailForUpdate();

        removePartyTrainingClassStatusByPartyTrainingClass(partyTrainingClass);
        deletePartyTrainingClassSessionsByPartyTrainingClass(partyTrainingClass, deletedBy);
        
        partyTrainingClassDetail.setThruTime(session.getStartTime());
        partyTrainingClass.setActiveDetail(null);
        partyTrainingClass.store();

        var partyTrainingClassPK = partyTrainingClass.getPrimaryKey();
        sendEvent(partyTrainingClassPK, EventTypes.DELETE, null, null, deletedBy);
        sendEvent(partyTrainingClassDetail.getParty().getPrimaryKey(), EventTypes.TOUCH, partyTrainingClassPK, EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyTrainingClassByParty(Party party, BasePK deletedBy) {
        var partyTrainingClasses = getPartyTrainingClassesByPartyForUpdate(party);
        
        partyTrainingClasses.forEach((partyTrainingClass) -> 
                deletePartyTrainingClass(partyTrainingClass, deletedBy)
        );
    }
    
    public void deletePartyTrainingClassesByTrainingClass(TrainingClass trainingClass, BasePK deletedBy) {
        var partyTrainingClasses = getPartyTrainingClassesByTrainingClassForUpdate(trainingClass);
        
        partyTrainingClasses.forEach((partyTrainingClass) -> 
                deletePartyTrainingClass(partyTrainingClass, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Training Class Statuses
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyTrainingClassStatusFactory partyTrainingClassStatusFactory;

    public PartyTrainingClassStatus createPartyTrainingClassStatus(PartyTrainingClass partyTrainingClass) {
        return partyTrainingClassStatusFactory.create(partyTrainingClass, 0, null);
    }

    private PartyTrainingClassStatus getPartyTrainingClassStatus(PartyTrainingClass partyTrainingClass, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassStatuses.fields())
                .from(PartyTrainingClassStatuses)
                .where(PartyTrainingClassStatuses.PARTY_TRAINING_CLASS.eq(partyTrainingClass.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassStatusFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyTrainingClassStatus getPartyTrainingClassStatus(PartyTrainingClass partyTrainingClass) {
        var partyTrainingClassStatus = getPartyTrainingClassStatus(partyTrainingClass, EntityPermission.READ_ONLY);

        return partyTrainingClassStatus == null ? createPartyTrainingClassStatus(partyTrainingClass) : partyTrainingClassStatus;
    }

    public PartyTrainingClassStatus getPartyTrainingClassStatusForUpdate(PartyTrainingClass partyTrainingClass) {
        var partyTrainingClassStatus = getPartyTrainingClassStatus(partyTrainingClass, EntityPermission.READ_WRITE);

        return partyTrainingClassStatus == null
                ? partyTrainingClassStatusFactory.getEntityFromPK(EntityPermission.READ_WRITE, createPartyTrainingClassStatus(partyTrainingClass).getPrimaryKey())
                : partyTrainingClassStatus;
    }

    public void removePartyTrainingClassStatusByPartyTrainingClass(PartyTrainingClass partyTrainingClass) {
        var partyTrainingClassStatus = getPartyTrainingClassStatusForUpdate(partyTrainingClass);

        if(partyTrainingClassStatus != null) {
            partyTrainingClassStatus.remove();
        }
    }

    // --------------------------------------------------------------------------------
    //   Party Training Class Sessions
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyTrainingClassSessionDetailFactory partyTrainingClassSessionDetailFactory;

    @Inject
    protected PartyTrainingClassSessionFactory partyTrainingClassSessionFactory;

    @Inject
    private PartyTrainingClassSessionTransferCache partyTrainingClassSessionTransferCache;

    public PartyTrainingClassSession createPartyTrainingClassSession(PartyTrainingClass partyTrainingClass, BasePK createdBy) {
        var partyTrainingClassStatus = getPartyTrainingClassStatusForUpdate(partyTrainingClass);
        Integer partyTrainingClassSessionSequence = partyTrainingClassStatus.getPartyTrainingClassSessionSequence() + 1;

        partyTrainingClassStatus.setPartyTrainingClassSessionSequence(partyTrainingClassSessionSequence);

        return createPartyTrainingClassSession(partyTrainingClass, partyTrainingClassSessionSequence, createdBy);
    }

    public PartyTrainingClassSession createPartyTrainingClassSession(PartyTrainingClass partyTrainingClass, Integer partyTrainingClassSessionSequence,
            BasePK createdBy) {
        var partyTrainingClassSession = partyTrainingClassSessionFactory.create();
        var partyTrainingClassSessionDetail = partyTrainingClassSessionDetailFactory.create(partyTrainingClassSession,
                partyTrainingClass, partyTrainingClassSessionSequence, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        partyTrainingClassSession = partyTrainingClassSessionFactory.getEntityFromPK(EntityPermission.READ_WRITE, partyTrainingClassSession.getPrimaryKey());
        partyTrainingClassSession.setActiveDetail(partyTrainingClassSessionDetail);
        partyTrainingClassSession.setLastDetail(partyTrainingClassSessionDetail);
        partyTrainingClassSession.store();
        
        sendEvent(partyTrainingClassSession.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        createPartyTrainingClassSessionStatus(partyTrainingClassSession);

        return partyTrainingClassSession;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartyTrainingClassSession */
    public PartyTrainingClassSession getPartyTrainingClassSessionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartyTrainingClassSessionPK(entityInstance.getEntityUniqueId());

        return partyTrainingClassSessionFactory.getEntityFromPK(entityPermission, pk);
    }

    public PartyTrainingClassSession getPartyTrainingClassSessionByEntityInstance(EntityInstance entityInstance) {
        return getPartyTrainingClassSessionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PartyTrainingClassSession getPartyTrainingClassSessionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyTrainingClassSessionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPartyTrainingClassSessionsByPartyTrainingClass(final PartyTrainingClass partyTrainingClass) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTrainingClassSessions)
                .join(PartyTrainingClassSessionDetails).onKey(PARTY_TRAINING_CLASS_SESSIONS_ACTIVE_DETAIL_FK)
                .where(PartyTrainingClassSessionDetails.PARTY_TRAINING_CLASS.eq(partyTrainingClass.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }
    public PartyTrainingClassSession getPartyTrainingClassSessionBySequence(PartyTrainingClass partyTrainingClass, Integer partyTrainingClassSessionSequence,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessions.fields())
                .from(PartyTrainingClassSessions)
                .join(PartyTrainingClassSessionDetails).onKey(PARTY_TRAINING_CLASS_SESSIONS_ACTIVE_DETAIL_FK)
                .where(PartyTrainingClassSessionDetails.PARTY_TRAINING_CLASS.eq(partyTrainingClass.getPrimaryKey()),
                        PartyTrainingClassSessionDetails.PARTY_TRAINING_CLASS_SESSION_SEQUENCE.eq(partyTrainingClassSessionSequence));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyTrainingClassSession getPartyTrainingClassSessionBySequence(PartyTrainingClass partyTrainingClass, Integer partyTrainingClassSessionSequence) {
        return getPartyTrainingClassSessionBySequence(partyTrainingClass, partyTrainingClassSessionSequence, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSession getPartyTrainingClassSessionBySequenceForUpdate(PartyTrainingClass partyTrainingClass, Integer partyTrainingClassSessionSequence) {
        return getPartyTrainingClassSessionBySequence(partyTrainingClass, partyTrainingClassSessionSequence, EntityPermission.READ_WRITE);
    }
    
    public PartyTrainingClassSessionDetailValue getPartyTrainingClassSessionDetailValueForUpdate(PartyTrainingClassSession partyTrainingClassSession) {
        return partyTrainingClassSession == null? null: partyTrainingClassSession.getLastDetailForUpdate().getPartyTrainingClassSessionDetailValue().clone();
    }
    
    public PartyTrainingClassSessionDetailValue getPartyTrainingClassSessionDetailValueByNameForUpdate(PartyTrainingClass partyTrainingClass, Integer partyTrainingClassSessionSequence) {
        return getPartyTrainingClassSessionDetailValueForUpdate(getPartyTrainingClassSessionBySequenceForUpdate(partyTrainingClass, partyTrainingClassSessionSequence));
    }
    
    private List<PartyTrainingClassSession> getPartyTrainingClassSessionsByPartyTrainingClass(PartyTrainingClass partyTrainingClass,
           EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessions.fields())
                .from(PartyTrainingClassSessions)
                .join(PartyTrainingClassSessionDetails).onKey(PARTY_TRAINING_CLASS_SESSIONS_ACTIVE_DETAIL_FK)
                .where(PartyTrainingClassSessionDetails.PARTY_TRAINING_CLASS.eq(partyTrainingClass.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PartyTrainingClassSessionDetails.PARTY_TRAINING_CLASS_SESSION_SEQUENCE),
                    PartyTrainingClassSessionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClassSession> getPartyTrainingClassSessionsByPartyTrainingClass(PartyTrainingClass partyTrainingClass) {
        return getPartyTrainingClassSessionsByPartyTrainingClass(partyTrainingClass, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSession> getPartyTrainingClassSessionsByPartyTrainingClassForUpdate(PartyTrainingClass partyTrainingClass) {
        return getPartyTrainingClassSessionsByPartyTrainingClass(partyTrainingClass, EntityPermission.READ_WRITE);
    }
    
    public PartyTrainingClassSessionTransfer getPartyTrainingClassSessionTransfer(UserVisit userVisit, PartyTrainingClassSession partyTrainingClassSession) {
        return partyTrainingClassSessionTransferCache.getPartyTrainingClassSessionTransfer(userVisit, partyTrainingClassSession);
    }
    
    public List<PartyTrainingClassSessionTransfer> getPartyTrainingClassSessionTransfers(UserVisit userVisit, Collection<PartyTrainingClassSession> partyTrainingClassSessions) {
        List<PartyTrainingClassSessionTransfer> partyTrainingClassSessionTransfers = new ArrayList<>(partyTrainingClassSessions.size());
        
        partyTrainingClassSessions.forEach((partyTrainingClassSession) ->
                partyTrainingClassSessionTransfers.add(partyTrainingClassSessionTransferCache.getPartyTrainingClassSessionTransfer(userVisit, partyTrainingClassSession))
        );
        
        return partyTrainingClassSessionTransfers;
    }
    
    public List<PartyTrainingClassSessionTransfer> getPartyTrainingClassSessionTransfersByPartyTrainingClass(UserVisit userVisit, PartyTrainingClass partyTrainingClass) {
        return getPartyTrainingClassSessionTransfers(userVisit, getPartyTrainingClassSessionsByPartyTrainingClass(partyTrainingClass));
    }
    
    public void updatePartyTrainingClassSessionFromValue(PartyTrainingClassSessionDetailValue partyTrainingClassSessionDetailValue, BasePK updatedBy) {
        if(partyTrainingClassSessionDetailValue.hasBeenModified()) {
            var partyTrainingClassSession = partyTrainingClassSessionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTrainingClassSessionDetailValue.getPartyTrainingClassSessionPK());
            var partyTrainingClassSessionDetail = partyTrainingClassSession.getActiveDetailForUpdate();
            
            partyTrainingClassSessionDetail.setThruTime(session.getStartTime());
            partyTrainingClassSessionDetail.store();

            var partyTrainingClassSessionPK = partyTrainingClassSessionDetail.getPartyTrainingClassSessionPK();
            var partyTrainingClassPK = partyTrainingClassSessionDetail.getPartyTrainingClassPK(); // Not updated
            var partyTrainingClassSessionSequence = partyTrainingClassSessionDetailValue.getPartyTrainingClassSessionSequence();
            
            partyTrainingClassSessionDetail = partyTrainingClassSessionDetailFactory.create(partyTrainingClassSessionPK,
                    partyTrainingClassPK, partyTrainingClassSessionSequence, session.getStartTime(), Session.MAX_TIME);
            
            partyTrainingClassSession.setActiveDetail(partyTrainingClassSessionDetail);
            partyTrainingClassSession.setLastDetail(partyTrainingClassSessionDetail);
            
            sendEvent(partyTrainingClassSessionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deletePartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession, BasePK deletedBy) {
        removePartyTrainingClassSessionStatusByPartyTrainingClassSession(partyTrainingClassSession);
        deletePartyTrainingClassSessionPagesByPartyTrainingClassSession(partyTrainingClassSession, deletedBy);
        deletePartyTrainingClassSessionQuestionsByPartyTrainingClassSession(partyTrainingClassSession, deletedBy);

        var partyTrainingClassSessionDetail = partyTrainingClassSession.getLastDetailForUpdate();
        partyTrainingClassSessionDetail.setThruTime(session.getStartTime());
        partyTrainingClassSession.setActiveDetail(null);
        partyTrainingClassSession.store();
        
        sendEvent(partyTrainingClassSession.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deletePartyTrainingClassSessions(List<PartyTrainingClassSession> partyTrainingClassSessions, BasePK deletedBy) {
        partyTrainingClassSessions.forEach((partyTrainingClassSession) -> 
                deletePartyTrainingClassSession(partyTrainingClassSession, deletedBy)
        );
    }
    
    public void deletePartyTrainingClassSessionsByPartyTrainingClass(PartyTrainingClass partyTrainingClass, BasePK deletedBy) {
        deletePartyTrainingClassSessions(getPartyTrainingClassSessionsByPartyTrainingClassForUpdate(partyTrainingClass), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Training Class Session Statuses
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyTrainingClassSessionStatusFactory partyTrainingClassSessionStatusFactory;

    public PartyTrainingClassSessionStatus createPartyTrainingClassSessionStatus(PartyTrainingClassSession partyTrainingClassSession) {
        return partyTrainingClassSessionStatusFactory.create(partyTrainingClassSession, 0, 0, null, null, null);
    }
    
    private PartyTrainingClassSessionStatus getPartyTrainingClassSessionStatus(PartyTrainingClassSession partyTrainingClassSession,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessionStatuses.fields())
                .from(PartyTrainingClassSessionStatuses)
                .where(PartyTrainingClassSessionStatuses.PARTY_TRAINING_CLASS_SESSION.eq(partyTrainingClassSession.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionStatusFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyTrainingClassSessionStatus getPartyTrainingClassSessionStatus(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionStatus(partyTrainingClassSession, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSessionStatus getPartyTrainingClassSessionStatusForUpdate(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionStatus(partyTrainingClassSession, EntityPermission.READ_WRITE);
    }
    
    private List<PartyTrainingClassSessionStatus> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionSection(PartyTrainingClassSessionSection lastPartyTrainingClassSessionSection,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessionStatuses.fields())
                .from(PartyTrainingClassSessionStatuses)
                .where(PartyTrainingClassSessionStatuses.LAST_PARTY_TRAINING_CLASS_SESSION_SECTION.eq(lastPartyTrainingClassSessionSection.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTrainingClassSessionStatuses.fields())
                    .from(PartyTrainingClassSessionStatuses)
                    .join(PartyTrainingClassSessions).onKey(PARTY_TRAINING_CLASS_SESSION_STATUSES_PARTY_TRAINING_CLASS_SESSION_FK)
                    .join(PartyTrainingClassSessionDetails).onKey(PARTY_TRAINING_CLASS_SESSIONS_LAST_DETAIL_FK)
                    .join(PartyTrainingClasses).onKey(PARTY_TRAINING_CLASS_SESSION_DETAILS_PARTY_TRAINING_CLASS_FK)
                    .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_LAST_DETAIL_FK)
                    .where(PartyTrainingClassSessionStatuses.LAST_PARTY_TRAINING_CLASS_SESSION_SECTION.eq(lastPartyTrainingClassSessionSection.getPrimaryKey()))
                    .orderBy(PartyTrainingClassDetails.PARTY_TRAINING_CLASS_NAME, PartyTrainingClassSessionDetails.PARTY_TRAINING_CLASS_SESSION_SEQUENCE),
                    PartyTrainingClassSessionStatusFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionStatusFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClassSessionStatus> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionSectionForUpdate(PartyTrainingClassSessionSection lastPartyTrainingClassSessionSection) {
        return getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionSection(lastPartyTrainingClassSessionSection, EntityPermission.READ_WRITE);
    }
    
    private List<PartyTrainingClassSessionStatus> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionPage(PartyTrainingClassSessionPage lastPartyTrainingClassSessionPage,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessionStatuses.fields())
                .from(PartyTrainingClassSessionStatuses)
                .where(PartyTrainingClassSessionStatuses.LAST_PARTY_TRAINING_CLASS_SESSION_PAGE.eq(lastPartyTrainingClassSessionPage.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTrainingClassSessionStatuses.fields())
                    .from(PartyTrainingClassSessionStatuses)
                    .join(PartyTrainingClassSessions).onKey(PARTY_TRAINING_CLASS_SESSION_STATUSES_PARTY_TRAINING_CLASS_SESSION_FK)
                    .join(PartyTrainingClassSessionDetails).onKey(PARTY_TRAINING_CLASS_SESSIONS_LAST_DETAIL_FK)
                    .join(PartyTrainingClasses).onKey(PARTY_TRAINING_CLASS_SESSION_DETAILS_PARTY_TRAINING_CLASS_FK)
                    .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_LAST_DETAIL_FK)
                    .where(PartyTrainingClassSessionStatuses.LAST_PARTY_TRAINING_CLASS_SESSION_PAGE.eq(lastPartyTrainingClassSessionPage.getPrimaryKey()))
                    .orderBy(PartyTrainingClassDetails.PARTY_TRAINING_CLASS_NAME, PartyTrainingClassSessionDetails.PARTY_TRAINING_CLASS_SESSION_SEQUENCE),
                    PartyTrainingClassSessionStatusFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionStatusFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClassSessionStatus> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionPageForUpdate(PartyTrainingClassSessionPage lastPartyTrainingClassSessionPage) {
        return getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionPage(lastPartyTrainingClassSessionPage, EntityPermission.READ_WRITE);
    }
    
    private List<PartyTrainingClassSessionStatus> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionQuestion(PartyTrainingClassSessionQuestion lastPartyTrainingClassSessionQuestion,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessionStatuses.fields())
                .from(PartyTrainingClassSessionStatuses)
                .where(PartyTrainingClassSessionStatuses.LAST_PARTY_TRAINING_CLASS_SESSION_QUESTION.eq(lastPartyTrainingClassSessionQuestion.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTrainingClassSessionStatuses.fields())
                    .from(PartyTrainingClassSessionStatuses)
                    .join(PartyTrainingClassSessions).onKey(PARTY_TRAINING_CLASS_SESSION_STATUSES_PARTY_TRAINING_CLASS_SESSION_FK)
                    .join(PartyTrainingClassSessionDetails).onKey(PARTY_TRAINING_CLASS_SESSIONS_LAST_DETAIL_FK)
                    .join(PartyTrainingClasses).onKey(PARTY_TRAINING_CLASS_SESSION_DETAILS_PARTY_TRAINING_CLASS_FK)
                    .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_LAST_DETAIL_FK)
                    .where(PartyTrainingClassSessionStatuses.LAST_PARTY_TRAINING_CLASS_SESSION_QUESTION.eq(lastPartyTrainingClassSessionQuestion.getPrimaryKey()))
                    .orderBy(PartyTrainingClassDetails.PARTY_TRAINING_CLASS_NAME, PartyTrainingClassSessionDetails.PARTY_TRAINING_CLASS_SESSION_SEQUENCE),
                    PartyTrainingClassSessionStatusFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionStatusFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClassSessionStatus> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionQuestionForUpdate(PartyTrainingClassSessionQuestion lastPartyTrainingClassSessionQuestion) {
        return getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionQuestion(lastPartyTrainingClassSessionQuestion, EntityPermission.READ_WRITE);
    }
    
    public void removePartyTrainingClassSessionStatusByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession) {
        var partyTrainingClassSessionStatus = getPartyTrainingClassSessionStatusForUpdate(partyTrainingClassSession);
        
        if(partyTrainingClassSessionStatus != null) {
            partyTrainingClassSessionStatus.remove();
        }
    }
    
    public void clearPartyTrainingClassSessionSectionFromPartyTrainingClassSessionStatuses(PartyTrainingClassSessionSection partyTrainingClassSessionSection) {
        getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionSectionForUpdate(partyTrainingClassSessionSection).forEach((partyTrainingClassSessionStatus) -> {
            partyTrainingClassSessionStatus.setLastPartyTrainingClassSessionSection(null);
        });
    }
            
    public void clearPartyTrainingClassSessionPageFromPartyTrainingClassSessionStatuses(PartyTrainingClassSessionPage partyTrainingClassSessionPage) {
        getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionPageForUpdate(partyTrainingClassSessionPage).forEach((partyTrainingClassSessionStatus) -> {
            partyTrainingClassSessionStatus.setLastPartyTrainingClassSessionPage(null);
        });
    }
            
    public void clearPartyTrainingClassSessionQuestionFromPartyTrainingClassSessionStatuses(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionQuestionForUpdate(partyTrainingClassSessionQuestion).forEach((partyTrainingClassSessionStatus) -> {
            partyTrainingClassSessionStatus.setLastPartyTrainingClassSessionQuestion(null);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Party Training Class Session Sections
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyTrainingClassSessionSectionFactory partyTrainingClassSessionSectionFactory;

    @Inject
    private PartyTrainingClassSessionSectionTransferCache partyTrainingClassSessionSectionTransferCache;

    public PartyTrainingClassSessionSection createPartyTrainingClassSessionSection(PartyTrainingClassSession partyTrainingClassSession,
            TrainingClassSection trainingClassSection, Long readingStartTime, Long readingEndTime, BasePK createdBy) {
        var partyTrainingClassSessionStatus = getPartyTrainingClassSessionStatusForUpdate(partyTrainingClassSession);
        Integer partyTrainingClassSessionSectionSequence = partyTrainingClassSessionStatus.getPartyTrainingClassSessionSectionSequence() + 1;

        partyTrainingClassSessionStatus.setPartyTrainingClassSessionSectionSequence(partyTrainingClassSessionSectionSequence);

        return createPartyTrainingClassSessionSection(partyTrainingClassSession, partyTrainingClassSessionSectionSequence, trainingClassSection, readingStartTime,
                readingEndTime, createdBy);
    }

    public PartyTrainingClassSessionSection createPartyTrainingClassSessionSection(PartyTrainingClassSession partyTrainingClassSession,
            Integer partyTrainingClassSessionSectionSequence, TrainingClassSection trainingClassSection, Long readingStartTime, Long readingEndTime, BasePK createdBy) {
        var partyTrainingClassSessionSection = partyTrainingClassSessionSectionFactory.create(partyTrainingClassSession,
                partyTrainingClassSessionSectionSequence, trainingClassSection, readingStartTime, readingEndTime, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(partyTrainingClassSession.getPrimaryKey(), EventTypes.MODIFY, partyTrainingClassSessionSection.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyTrainingClassSessionSection;
    }

    public long countPartyTrainingClassSessionSectionsByPartyTrainingClassSession(final PartyTrainingClassSession partyTrainingClassSession) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTrainingClassSessionSections)
                .where(PartyTrainingClassSessionSections.PARTY_TRAINING_CLASS_SESSION.eq(partyTrainingClassSession.getPrimaryKey()),
                        PartyTrainingClassSessionSections.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyTrainingClassSessionSectionsByTrainingClassSection(final TrainingClassSection trainingClassSection) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTrainingClassSessionSections)
                .where(PartyTrainingClassSessionSections.TRAINING_CLASS_SECTION.eq(trainingClassSection.getPrimaryKey()),
                        PartyTrainingClassSessionSections.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public PartyTrainingClassSessionSection getPartyTrainingClassSessionSection(PartyTrainingClassSession partyTrainingClassSession,
            Integer partyTrainingClassSessionSectionSequence, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessionSections.fields())
                .from(PartyTrainingClassSessionSections)
                .where(PartyTrainingClassSessionSections.PARTY_TRAINING_CLASS_SESSION.eq(partyTrainingClassSession.getPrimaryKey()),
                        PartyTrainingClassSessionSections.PARTY_TRAINING_CLASS_SESSION_SECTION_SEQUENCE.eq(partyTrainingClassSessionSectionSequence),
                        PartyTrainingClassSessionSections.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionSectionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyTrainingClassSessionSection getPartyTrainingClassSessionSection(PartyTrainingClassSession partyTrainingClassSession,
            Integer partyTrainingClassSessionSectionSequence) {
        return getPartyTrainingClassSessionSection(partyTrainingClassSession, partyTrainingClassSessionSectionSequence, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSessionSection getPartyTrainingClassSessionSectionForUpdate(PartyTrainingClassSession partyTrainingClassSession,
            Integer partyTrainingClassSessionSectionSequence) {
        return getPartyTrainingClassSessionSection(partyTrainingClassSession, partyTrainingClassSessionSectionSequence, EntityPermission.READ_WRITE);
    }
    
    public PartyTrainingClassSessionSectionValue getPartyTrainingClassSessionSectionValue(PartyTrainingClassSessionSection partyTrainingClassSessionSection) {
        return partyTrainingClassSessionSection == null? null: partyTrainingClassSessionSection.getPartyTrainingClassSessionSectionValue().clone();
    }
    
    public PartyTrainingClassSessionSectionValue getPartyTrainingClassSessionSectionValueForUpdate(PartyTrainingClassSession partyTrainingClassSession,
            Integer partyTrainingClassSessionSectionSequence) {
        return getPartyTrainingClassSessionSectionValue(getPartyTrainingClassSessionSectionForUpdate(partyTrainingClassSession, partyTrainingClassSessionSectionSequence));
    }
    
    private List<PartyTrainingClassSessionSection> getPartyTrainingClassSessionSectionsByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessionSections.fields())
                .from(PartyTrainingClassSessionSections)
                .where(PartyTrainingClassSessionSections.PARTY_TRAINING_CLASS_SESSION.eq(partyTrainingClassSession.getPrimaryKey()),
                        PartyTrainingClassSessionSections.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PartyTrainingClassSessionSections.PARTY_TRAINING_CLASS_SESSION_SECTION_SEQUENCE),
                    PartyTrainingClassSessionSectionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionSectionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClassSessionSection> getPartyTrainingClassSessionSectionsByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionSectionsByPartyTrainingClassSession(partyTrainingClassSession, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSessionSection> getPartyTrainingClassSessionSectionsByPartyTrainingClassSessionForUpdate(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionSectionsByPartyTrainingClassSession(partyTrainingClassSession, EntityPermission.READ_WRITE);
    }
    
    private List<PartyTrainingClassSessionSection> getPartyTrainingClassSessionSectionsByTrainingClassSection(TrainingClassSection trainingClassSection,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTrainingClassSessionSections.fields())
                    .from(PartyTrainingClassSessionSections)
                    .join(PartyTrainingClassSessions).onKey(PARTY_TRAINING_CLASS_SESSION_SECTIONS_PARTY_TRAINING_CLASS_SESSION_FK)
                    .join(PartyTrainingClassSessionDetails).onKey(PARTY_TRAINING_CLASS_SESSIONS_LAST_DETAIL_FK)
                    .join(PartyTrainingClasses).onKey(PARTY_TRAINING_CLASS_SESSION_DETAILS_PARTY_TRAINING_CLASS_FK)
                    .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_LAST_DETAIL_FK)
                    .where(PartyTrainingClassSessionSections.TRAINING_CLASS_SECTION.eq(trainingClassSection.getPrimaryKey()),
                            PartyTrainingClassSessionSections.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTrainingClassDetails.PARTY_TRAINING_CLASS_NAME, PartyTrainingClassSessionDetails.PARTY_TRAINING_CLASS_SESSION_SEQUENCE, PartyTrainingClassSessionSections.PARTY_TRAINING_CLASS_SESSION_SECTION_SEQUENCE),
                    PartyTrainingClassSessionSectionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTrainingClassSessionSections.fields())
                    .from(PartyTrainingClassSessionSections)
                    .where(PartyTrainingClassSessionSections.TRAINING_CLASS_SECTION.eq(trainingClassSection.getPrimaryKey()),
                            PartyTrainingClassSessionSections.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyTrainingClassSessionSectionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClassSessionSection> getPartyTrainingClassSessionSectionsByTrainingClassSection(TrainingClassSection trainingClassSection) {
        return getPartyTrainingClassSessionSectionsByTrainingClassSection(trainingClassSection, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSessionSection> getPartyTrainingClassSessionSectionsByTrainingClassSectionForUpdate(TrainingClassSection trainingClassSection) {
        return getPartyTrainingClassSessionSectionsByTrainingClassSection(trainingClassSection, EntityPermission.READ_WRITE);
    }
    
    public PartyTrainingClassSessionSectionTransfer getPartyTrainingClassSessionSectionTransfer(UserVisit userVisit, PartyTrainingClassSessionSection partyTrainingClassSessionSection) {
        return partyTrainingClassSessionSectionTransferCache.getPartyTrainingClassSessionSectionTransfer(userVisit, partyTrainingClassSessionSection);
    }
    
    public List<PartyTrainingClassSessionSectionTransfer> getPartyTrainingClassSessionSectionTransfers(UserVisit userVisit, Collection<PartyTrainingClassSessionSection> partyTrainingClassSessionSections) {
        List<PartyTrainingClassSessionSectionTransfer> partyTrainingClassSessionSectionTransfers = new ArrayList<>(partyTrainingClassSessionSections.size());
        
        partyTrainingClassSessionSections.forEach((partyTrainingClassSessionSection) ->
                partyTrainingClassSessionSectionTransfers.add(partyTrainingClassSessionSectionTransferCache.getPartyTrainingClassSessionSectionTransfer(userVisit, partyTrainingClassSessionSection))
        );
        
        return partyTrainingClassSessionSectionTransfers;
    }
    
    public List<PartyTrainingClassSessionSectionTransfer> getPartyTrainingClassSessionSectionTransfersByPartyTrainingClassSession(UserVisit userVisit,
            PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionSectionTransfers(userVisit, getPartyTrainingClassSessionSectionsByPartyTrainingClassSession(partyTrainingClassSession));
    }
    
    public void updatePartyTrainingClassSessionSectionFromValue(PartyTrainingClassSessionSectionValue partyTrainingClassSessionSectionValue, BasePK updatedBy) {
        if(partyTrainingClassSessionSectionValue.hasBeenModified()) {
            var partyTrainingClassSessionSection = partyTrainingClassSessionSectionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    partyTrainingClassSessionSectionValue.getPrimaryKey());
            
            partyTrainingClassSessionSection.setThruTime(session.getStartTime());
            partyTrainingClassSessionSection.store();

            var partyTrainingClassSessionPK = partyTrainingClassSessionSection.getPartyTrainingClassSessionPK(); // Not updated
            var partyTrainingClassSessionSectionSequence = partyTrainingClassSessionSection.getPartyTrainingClassSessionSectionSequence(); // Not updated
            var trainingClassSectionPK = partyTrainingClassSessionSectionValue.getTrainingClassSectionPK();
            var readingStartTime = partyTrainingClassSessionSectionValue.getReadingStartTime();
            var readingEndTime = partyTrainingClassSessionSectionValue.getReadingEndTime();
            
            partyTrainingClassSessionSection = partyTrainingClassSessionSectionFactory.create(partyTrainingClassSessionPK,
                    partyTrainingClassSessionSectionSequence, trainingClassSectionPK, readingStartTime, readingEndTime, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(partyTrainingClassSessionPK, EventTypes.MODIFY, partyTrainingClassSessionSection.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyTrainingClassSessionSection(PartyTrainingClassSessionSection partyTrainingClassSessionSection, BasePK deletedBy) {
        clearPartyTrainingClassSessionSectionFromPartyTrainingClassSessionStatuses(partyTrainingClassSessionSection);
        
        partyTrainingClassSessionSection.setThruTime(session.getStartTime());
        
        sendEvent(partyTrainingClassSessionSection.getPartyTrainingClassSessionPK(), EventTypes.MODIFY, partyTrainingClassSessionSection.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyTrainingClassSessionSections(List<PartyTrainingClassSessionSection> partyTrainingClassSessionSections, BasePK deletedBy) {
        partyTrainingClassSessionSections.forEach((partyTrainingClassSessionSection) -> 
                deletePartyTrainingClassSessionSection(partyTrainingClassSessionSection, deletedBy)
        );
    }
    
    public void deletePartyTrainingClassSessionSectionsByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession, BasePK deletedBy) {
        deletePartyTrainingClassSessionSections(getPartyTrainingClassSessionSectionsByPartyTrainingClassSessionForUpdate(partyTrainingClassSession), deletedBy);
    }
    
    public void deletePartyTrainingClassSessionSectionsByTrainingClassSection(TrainingClassSection trainingClassSection, BasePK deletedBy) {
        deletePartyTrainingClassSessionSections(getPartyTrainingClassSessionSectionsByTrainingClassSectionForUpdate(trainingClassSection), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Training Class Session Pages
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyTrainingClassSessionPageFactory partyTrainingClassSessionPageFactory;

    @Inject
    private PartyTrainingClassSessionPageTransferCache partyTrainingClassSessionPageTransferCache;

    public PartyTrainingClassSessionPage createPartyTrainingClassSessionPage(PartyTrainingClassSession partyTrainingClassSession,
            TrainingClassPage trainingClassPage, Long readingStartTime, Long readingEndTime, BasePK createdBy) {
        var partyTrainingClassSessionStatus = getPartyTrainingClassSessionStatusForUpdate(partyTrainingClassSession);
        Integer partyTrainingClassSessionPageSequence = partyTrainingClassSessionStatus.getPartyTrainingClassSessionPageSequence() + 1;

        partyTrainingClassSessionStatus.setPartyTrainingClassSessionPageSequence(partyTrainingClassSessionPageSequence);

        return createPartyTrainingClassSessionPage(partyTrainingClassSession, partyTrainingClassSessionPageSequence, trainingClassPage, readingStartTime,
                readingEndTime, createdBy);
    }

    public PartyTrainingClassSessionPage createPartyTrainingClassSessionPage(PartyTrainingClassSession partyTrainingClassSession,
            Integer partyTrainingClassSessionPageSequence, TrainingClassPage trainingClassPage, Long readingStartTime, Long readingEndTime, BasePK createdBy) {
        var partyTrainingClassSessionPage = partyTrainingClassSessionPageFactory.create(partyTrainingClassSession,
                partyTrainingClassSessionPageSequence, trainingClassPage, readingStartTime, readingEndTime, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(partyTrainingClassSession.getPrimaryKey(), EventTypes.MODIFY, partyTrainingClassSessionPage.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyTrainingClassSessionPage;
    }

    public long countPartyTrainingClassSessionPagesByPartyTrainingClassSession(final PartyTrainingClassSession partyTrainingClassSession) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTrainingClassSessionPages)
                .where(PartyTrainingClassSessionPages.PARTY_TRAINING_CLASS_SESSION.eq(partyTrainingClassSession.getPrimaryKey()),
                        PartyTrainingClassSessionPages.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyTrainingClassSessionPagesByTrainingClassPage(final TrainingClassPage trainingClassPage) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTrainingClassSessionPages)
                .where(PartyTrainingClassSessionPages.TRAINING_CLASS_PAGE.eq(trainingClassPage.getPrimaryKey()),
                        PartyTrainingClassSessionPages.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public PartyTrainingClassSessionPage getPartyTrainingClassSessionPage(PartyTrainingClassSession partyTrainingClassSession,
            Integer partyTrainingClassSessionPageSequence, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessionPages.fields())
                .from(PartyTrainingClassSessionPages)
                .where(PartyTrainingClassSessionPages.PARTY_TRAINING_CLASS_SESSION.eq(partyTrainingClassSession.getPrimaryKey()),
                        PartyTrainingClassSessionPages.PARTY_TRAINING_CLASS_SESSION_PAGE_SEQUENCE.eq(partyTrainingClassSessionPageSequence),
                        PartyTrainingClassSessionPages.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionPageFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyTrainingClassSessionPage getPartyTrainingClassSessionPage(PartyTrainingClassSession partyTrainingClassSession,
            Integer partyTrainingClassSessionPageSequence) {
        return getPartyTrainingClassSessionPage(partyTrainingClassSession, partyTrainingClassSessionPageSequence, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSessionPage getPartyTrainingClassSessionPageForUpdate(PartyTrainingClassSession partyTrainingClassSession,
            Integer partyTrainingClassSessionPageSequence) {
        return getPartyTrainingClassSessionPage(partyTrainingClassSession, partyTrainingClassSessionPageSequence, EntityPermission.READ_WRITE);
    }
    
    public PartyTrainingClassSessionPageValue getPartyTrainingClassSessionPageValue(PartyTrainingClassSessionPage partyTrainingClassSessionPage) {
        return partyTrainingClassSessionPage == null? null: partyTrainingClassSessionPage.getPartyTrainingClassSessionPageValue().clone();
    }
    
    public PartyTrainingClassSessionPageValue getPartyTrainingClassSessionPageValueForUpdate(PartyTrainingClassSession partyTrainingClassSession,
            Integer partyTrainingClassSessionPageSequence) {
        return getPartyTrainingClassSessionPageValue(getPartyTrainingClassSessionPageForUpdate(partyTrainingClassSession, partyTrainingClassSessionPageSequence));
    }
    
    private List<PartyTrainingClassSessionPage> getPartyTrainingClassSessionPagesByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessionPages.fields())
                .from(PartyTrainingClassSessionPages)
                .where(PartyTrainingClassSessionPages.PARTY_TRAINING_CLASS_SESSION.eq(partyTrainingClassSession.getPrimaryKey()),
                        PartyTrainingClassSessionPages.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PartyTrainingClassSessionPages.PARTY_TRAINING_CLASS_SESSION_PAGE_SEQUENCE),
                    PartyTrainingClassSessionPageFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionPageFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClassSessionPage> getPartyTrainingClassSessionPagesByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionPagesByPartyTrainingClassSession(partyTrainingClassSession, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSessionPage> getPartyTrainingClassSessionPagesByPartyTrainingClassSessionForUpdate(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionPagesByPartyTrainingClassSession(partyTrainingClassSession, EntityPermission.READ_WRITE);
    }
    
    private List<PartyTrainingClassSessionPage> getPartyTrainingClassSessionPagesByTrainingClassPage(TrainingClassPage trainingClassPage,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTrainingClassSessionPages.fields())
                    .from(PartyTrainingClassSessionPages)
                    .join(PartyTrainingClassSessions).onKey(PARTY_TRAINING_CLASS_SESSION_PAGES_PARTY_TRAINING_CLASS_SESSION_FK)
                    .join(PartyTrainingClassSessionDetails).onKey(PARTY_TRAINING_CLASS_SESSIONS_LAST_DETAIL_FK)
                    .join(PartyTrainingClasses).onKey(PARTY_TRAINING_CLASS_SESSION_DETAILS_PARTY_TRAINING_CLASS_FK)
                    .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_LAST_DETAIL_FK)
                    .where(PartyTrainingClassSessionPages.TRAINING_CLASS_PAGE.eq(trainingClassPage.getPrimaryKey()),
                            PartyTrainingClassSessionPages.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTrainingClassDetails.PARTY_TRAINING_CLASS_NAME, PartyTrainingClassSessionDetails.PARTY_TRAINING_CLASS_SESSION_SEQUENCE, PartyTrainingClassSessionPages.PARTY_TRAINING_CLASS_SESSION_PAGE_SEQUENCE),
                    PartyTrainingClassSessionPageFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTrainingClassSessionPages.fields())
                    .from(PartyTrainingClassSessionPages)
                    .where(PartyTrainingClassSessionPages.TRAINING_CLASS_PAGE.eq(trainingClassPage.getPrimaryKey()),
                            PartyTrainingClassSessionPages.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyTrainingClassSessionPageFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClassSessionPage> getPartyTrainingClassSessionPagesByTrainingClassPage(TrainingClassPage trainingClassPage) {
        return getPartyTrainingClassSessionPagesByTrainingClassPage(trainingClassPage, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSessionPage> getPartyTrainingClassSessionPagesByTrainingClassPageForUpdate(TrainingClassPage trainingClassPage) {
        return getPartyTrainingClassSessionPagesByTrainingClassPage(trainingClassPage, EntityPermission.READ_WRITE);
    }
    
    public PartyTrainingClassSessionPageTransfer getPartyTrainingClassSessionPageTransfer(UserVisit userVisit, PartyTrainingClassSessionPage partyTrainingClassSessionPage) {
        return partyTrainingClassSessionPageTransferCache.getPartyTrainingClassSessionPageTransfer(userVisit, partyTrainingClassSessionPage);
    }
    
    public List<PartyTrainingClassSessionPageTransfer> getPartyTrainingClassSessionPageTransfers(UserVisit userVisit, Collection<PartyTrainingClassSessionPage> partyTrainingClassSessionPages) {
        List<PartyTrainingClassSessionPageTransfer> partyTrainingClassSessionPageTransfers = new ArrayList<>(partyTrainingClassSessionPages.size());
        
        partyTrainingClassSessionPages.forEach((partyTrainingClassSessionPage) ->
                partyTrainingClassSessionPageTransfers.add(partyTrainingClassSessionPageTransferCache.getPartyTrainingClassSessionPageTransfer(userVisit, partyTrainingClassSessionPage))
        );
        
        return partyTrainingClassSessionPageTransfers;
    }
    
    public List<PartyTrainingClassSessionPageTransfer> getPartyTrainingClassSessionPageTransfersByPartyTrainingClassSession(UserVisit userVisit,
            PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionPageTransfers(userVisit, getPartyTrainingClassSessionPagesByPartyTrainingClassSession(partyTrainingClassSession));
    }
    
    public void updatePartyTrainingClassSessionPageFromValue(PartyTrainingClassSessionPageValue partyTrainingClassSessionPageValue, BasePK updatedBy) {
        if(partyTrainingClassSessionPageValue.hasBeenModified()) {
            var partyTrainingClassSessionPage = partyTrainingClassSessionPageFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    partyTrainingClassSessionPageValue.getPrimaryKey());
            
            partyTrainingClassSessionPage.setThruTime(session.getStartTime());
            partyTrainingClassSessionPage.store();

            var partyTrainingClassSessionPK = partyTrainingClassSessionPage.getPartyTrainingClassSessionPK(); // Not updated
            var partyTrainingClassSessionPageSequence = partyTrainingClassSessionPage.getPartyTrainingClassSessionPageSequence(); // Not updated
            var trainingClassPagePK = partyTrainingClassSessionPageValue.getTrainingClassPagePK();
            var readingStartTime = partyTrainingClassSessionPageValue.getReadingStartTime();
            var readingEndTime = partyTrainingClassSessionPageValue.getReadingEndTime();
            
            partyTrainingClassSessionPage = partyTrainingClassSessionPageFactory.create(partyTrainingClassSessionPK,
                    partyTrainingClassSessionPageSequence, trainingClassPagePK, readingStartTime, readingEndTime, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(partyTrainingClassSessionPK, EventTypes.MODIFY, partyTrainingClassSessionPage.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyTrainingClassSessionPage(PartyTrainingClassSessionPage partyTrainingClassSessionPage, BasePK deletedBy) {
        clearPartyTrainingClassSessionPageFromPartyTrainingClassSessionStatuses(partyTrainingClassSessionPage);
        
        partyTrainingClassSessionPage.setThruTime(session.getStartTime());
        
        sendEvent(partyTrainingClassSessionPage.getPartyTrainingClassSessionPK(), EventTypes.MODIFY, partyTrainingClassSessionPage.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyTrainingClassSessionPages(List<PartyTrainingClassSessionPage> partyTrainingClassSessionPages, BasePK deletedBy) {
        partyTrainingClassSessionPages.forEach((partyTrainingClassSessionPage) -> 
                deletePartyTrainingClassSessionPage(partyTrainingClassSessionPage, deletedBy)
        );
    }
    
    public void deletePartyTrainingClassSessionPagesByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession, BasePK deletedBy) {
        deletePartyTrainingClassSessionPages(getPartyTrainingClassSessionPagesByPartyTrainingClassSessionForUpdate(partyTrainingClassSession), deletedBy);
    }
    
    public void deletePartyTrainingClassSessionPagesByTrainingClassPage(TrainingClassPage trainingClassPage, BasePK deletedBy) {
        deletePartyTrainingClassSessionPages(getPartyTrainingClassSessionPagesByTrainingClassPageForUpdate(trainingClassPage), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Training Class Session Questions
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyTrainingClassSessionQuestionDetailFactory partyTrainingClassSessionQuestionDetailFactory;

    @Inject
    protected PartyTrainingClassSessionQuestionFactory partyTrainingClassSessionQuestionFactory;

    @Inject
    private PartyTrainingClassSessionQuestionTransferCache partyTrainingClassSessionQuestionTransferCache;

    public PartyTrainingClassSessionQuestion createPartyTrainingClassSessionQuestion(PartyTrainingClassSession partyTrainingClassSession,
            TrainingClassQuestion trainingClassQuestion, Integer sortOrder, BasePK createdBy) {
        var partyTrainingClassSessionQuestion = partyTrainingClassSessionQuestionFactory.create();
        var partyTrainingClassSessionQuestionDetail = partyTrainingClassSessionQuestionDetailFactory.create(partyTrainingClassSessionQuestion,
                partyTrainingClassSession, trainingClassQuestion, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        partyTrainingClassSessionQuestion = partyTrainingClassSessionQuestionFactory.getEntityFromPK(EntityPermission.READ_WRITE, partyTrainingClassSessionQuestion.getPrimaryKey());
        partyTrainingClassSessionQuestion.setActiveDetail(partyTrainingClassSessionQuestionDetail);
        partyTrainingClassSessionQuestion.setLastDetail(partyTrainingClassSessionQuestionDetail);
        partyTrainingClassSessionQuestion.store();
        
        sendEvent(partyTrainingClassSessionQuestion.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        createPartyTrainingClassSessionQuestionStatus(partyTrainingClassSessionQuestion);

        return partyTrainingClassSessionQuestion;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartyTrainingClassSessionQuestion */
    public PartyTrainingClassSessionQuestion getPartyTrainingClassSessionQuestionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartyTrainingClassSessionQuestionPK(entityInstance.getEntityUniqueId());

        return partyTrainingClassSessionQuestionFactory.getEntityFromPK(entityPermission, pk);
    }

    public PartyTrainingClassSessionQuestion getPartyTrainingClassSessionQuestionByEntityInstance(EntityInstance entityInstance) {
        return getPartyTrainingClassSessionQuestionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PartyTrainingClassSessionQuestion getPartyTrainingClassSessionQuestionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyTrainingClassSessionQuestionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPartyTrainingClassSessionQuestionsByPartyTrainingClassSession(final PartyTrainingClassSession partyTrainingClassSession) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTrainingClassSessionQuestions)
                .join(PartyTrainingClassSessionQuestionDetails).onKey(PARTY_TRAINING_CLASS_SESSION_QUESTIONS_ACTIVE_DETAIL_FK)
                .where(PartyTrainingClassSessionQuestionDetails.PARTY_TRAINING_CLASS_SESSION.eq(partyTrainingClassSession.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyTrainingClassSessionQuestionsByTrainingClassQuestion(final TrainingClassQuestion trainingClassQuestion) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTrainingClassSessionQuestions)
                .join(PartyTrainingClassSessionQuestionDetails).onKey(PARTY_TRAINING_CLASS_SESSION_QUESTIONS_ACTIVE_DETAIL_FK)
                .where(PartyTrainingClassSessionQuestionDetails.TRAINING_CLASS_QUESTION.eq(trainingClassQuestion.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public PartyTrainingClassSessionQuestion getPartyTrainingClassSessionQuestion(PartyTrainingClassSession partyTrainingClassSession,
            TrainingClassQuestion trainingClassQuestion, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessionQuestions.fields())
                .from(PartyTrainingClassSessionQuestions)
                .join(PartyTrainingClassSessionQuestionDetails).onKey(PARTY_TRAINING_CLASS_SESSION_QUESTIONS_ACTIVE_DETAIL_FK)
                .where(PartyTrainingClassSessionQuestionDetails.PARTY_TRAINING_CLASS_SESSION.eq(partyTrainingClassSession.getPrimaryKey()),
                        PartyTrainingClassSessionQuestionDetails.TRAINING_CLASS_QUESTION.eq(trainingClassQuestion.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionQuestionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyTrainingClassSessionQuestion getPartyTrainingClassSessionQuestion(PartyTrainingClassSession partyTrainingClassSession,
            TrainingClassQuestion trainingClassQuestion) {
        return getPartyTrainingClassSessionQuestion(partyTrainingClassSession, trainingClassQuestion, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSessionQuestion getPartyTrainingClassSessionQuestionForUpdate(PartyTrainingClassSession partyTrainingClassSession,
            TrainingClassQuestion trainingClassQuestion) {
        return getPartyTrainingClassSessionQuestion(partyTrainingClassSession, trainingClassQuestion, EntityPermission.READ_WRITE);
    }
    
    public PartyTrainingClassSessionQuestionDetailValue getPartyTrainingClassSessionQuestionDetailValueForUpdate(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        return partyTrainingClassSessionQuestion == null? null: partyTrainingClassSessionQuestion.getLastDetailForUpdate().getPartyTrainingClassSessionQuestionDetailValue().clone();
    }
    
    public PartyTrainingClassSessionQuestionDetailValue getPartyTrainingClassSessionQuestionDetailValueForUpdate(PartyTrainingClassSession partyTrainingClassSession,
            TrainingClassQuestion trainingClassQuestion) {
        return getPartyTrainingClassSessionQuestionDetailValueForUpdate(getPartyTrainingClassSessionQuestionForUpdate(partyTrainingClassSession, trainingClassQuestion));
    }
    
    private List<PartyTrainingClassSessionQuestion> getPartyTrainingClassSessionQuestionsByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession,
           EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTrainingClassSessionQuestions.fields())
                    .from(PartyTrainingClassSessionQuestions)
                    .join(PartyTrainingClassSessionQuestionDetails).onKey(PARTY_TRAINING_CLASS_SESSION_QUESTIONS_ACTIVE_DETAIL_FK)
                    .join(TrainingClassQuestions).onKey(PARTY_TRAINING_CLASS_SESSION_QUESTION_DETAILS_TRAINING_CLASS_QUESTION_FK)
                    .join(TrainingClassQuestionDetails).onKey(TRAINING_CLASS_QUESTIONS_LAST_DETAIL_FK)
                    .join(TrainingClassSections).onKey(TRAINING_CLASS_QUESTION_DETAILS_TRAINING_CLASS_SECTION_FK)
                    .join(TrainingClassSectionDetails).onKey(TRAINING_CLASS_SECTIONS_LAST_DETAIL_FK)
                    .where(PartyTrainingClassSessionQuestionDetails.PARTY_TRAINING_CLASS_SESSION.eq(partyTrainingClassSession.getPrimaryKey()))
                    .orderBy(PartyTrainingClassSessionQuestionDetails.SORT_ORDER, TrainingClassQuestionDetails.SORT_ORDER, TrainingClassQuestionDetails.TRAINING_CLASS_QUESTION_NAME, TrainingClassSectionDetails.SORT_ORDER, TrainingClassSectionDetails.TRAINING_CLASS_SECTION_NAME),
                    PartyTrainingClassSessionQuestionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTrainingClassSessionQuestions.fields())
                    .from(PartyTrainingClassSessionQuestions)
                    .join(PartyTrainingClassSessionQuestionDetails).onKey(PARTY_TRAINING_CLASS_SESSION_QUESTIONS_ACTIVE_DETAIL_FK)
                    .where(PartyTrainingClassSessionQuestionDetails.PARTY_TRAINING_CLASS_SESSION.eq(partyTrainingClassSession.getPrimaryKey()))
                    .forUpdate();
        };

        return partyTrainingClassSessionQuestionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClassSessionQuestion> getPartyTrainingClassSessionQuestionsByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionQuestionsByPartyTrainingClassSession(partyTrainingClassSession, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSessionQuestion> getPartyTrainingClassSessionQuestionsByPartyTrainingClassSessionForUpdate(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionQuestionsByPartyTrainingClassSession(partyTrainingClassSession, EntityPermission.READ_WRITE);
    }
    
    private List<PartyTrainingClassSessionQuestion> getPartyTrainingClassSessionQuestionsByTrainingClassQuestion(TrainingClassQuestion trainingClassQuestion,
           EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTrainingClassSessionQuestions.fields())
                    .from(PartyTrainingClassSessionQuestions)
                    .join(PartyTrainingClassSessionQuestionDetails).onKey(PARTY_TRAINING_CLASS_SESSION_QUESTIONS_ACTIVE_DETAIL_FK)
                    .join(PartyTrainingClassSessions).onKey(PARTY_TRAINING_CLASS_SESSION_QUESTION_DETAILS_PARTY_TRAINING_CLASS_SESSION_FK)
                    .join(PartyTrainingClassSessionDetails).onKey(PARTY_TRAINING_CLASS_SESSIONS_LAST_DETAIL_FK)
                    .join(PartyTrainingClasses).onKey(PARTY_TRAINING_CLASS_SESSION_DETAILS_PARTY_TRAINING_CLASS_FK)
                    .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_LAST_DETAIL_FK)
                    .where(PartyTrainingClassSessionQuestionDetails.TRAINING_CLASS_QUESTION.eq(trainingClassQuestion.getPrimaryKey()))
                    .orderBy(PartyTrainingClassSessionQuestionDetails.SORT_ORDER, PartyTrainingClassSessionDetails.PARTY_TRAINING_CLASS_SESSION_SEQUENCE, PartyTrainingClassDetails.PARTY_TRAINING_CLASS_NAME),
                    PartyTrainingClassSessionQuestionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTrainingClassSessionQuestions.fields())
                    .from(PartyTrainingClassSessionQuestions)
                    .join(PartyTrainingClassSessionQuestionDetails).onKey(PARTY_TRAINING_CLASS_SESSION_QUESTIONS_ACTIVE_DETAIL_FK)
                    .where(PartyTrainingClassSessionQuestionDetails.TRAINING_CLASS_QUESTION.eq(trainingClassQuestion.getPrimaryKey()))
                    .forUpdate();
        };

        return partyTrainingClassSessionQuestionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClassSessionQuestion> getPartyTrainingClassSessionQuestionsByTrainingClassQuestion(TrainingClassQuestion trainingClassQuestion) {
        return getPartyTrainingClassSessionQuestionsByTrainingClassQuestion(trainingClassQuestion, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSessionQuestion> getPartyTrainingClassSessionQuestionsByTrainingClassQuestionForUpdate(TrainingClassQuestion trainingClassQuestion) {
        return getPartyTrainingClassSessionQuestionsByTrainingClassQuestion(trainingClassQuestion, EntityPermission.READ_WRITE);
    }
    
    public PartyTrainingClassSessionQuestionTransfer getPartyTrainingClassSessionQuestionTransfer(UserVisit userVisit, PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        return partyTrainingClassSessionQuestionTransferCache.getPartyTrainingClassSessionQuestionTransfer(userVisit, partyTrainingClassSessionQuestion);
    }
    
    public List<PartyTrainingClassSessionQuestionTransfer> getPartyTrainingClassSessionQuestionTransfers(UserVisit userVisit, Collection<PartyTrainingClassSessionQuestion> partyTrainingClassSessionQuestions) {
        List<PartyTrainingClassSessionQuestionTransfer> partyTrainingClassSessionQuestionTransfers = new ArrayList<>(partyTrainingClassSessionQuestions.size());
        
        partyTrainingClassSessionQuestions.forEach((partyTrainingClassSessionQuestion) ->
                partyTrainingClassSessionQuestionTransfers.add(partyTrainingClassSessionQuestionTransferCache.getPartyTrainingClassSessionQuestionTransfer(userVisit, partyTrainingClassSessionQuestion))
        );
        
        return partyTrainingClassSessionQuestionTransfers;
    }
    
    public List<PartyTrainingClassSessionQuestionTransfer> getPartyTrainingClassSessionQuestionTransfersByPartyTrainingClassSession(UserVisit userVisit, PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionQuestionTransfers(userVisit, getPartyTrainingClassSessionQuestionsByPartyTrainingClassSession(partyTrainingClassSession));
    }
    
    public List<PartyTrainingClassSessionQuestionTransfer> getPartyTrainingClassSessionQuestionTransfersByTrainingClassQuestion(UserVisit userVisit, TrainingClassQuestion trainingClassQuestion) {
        return getPartyTrainingClassSessionQuestionTransfers(userVisit, getPartyTrainingClassSessionQuestionsByTrainingClassQuestion(trainingClassQuestion));
    }
    
    public void updatePartyTrainingClassSessionQuestionFromValue(PartyTrainingClassSessionQuestionDetailValue partyTrainingClassSessionQuestionDetailValue, BasePK updatedBy) {
        if(partyTrainingClassSessionQuestionDetailValue.hasBeenModified()) {
            var partyTrainingClassSessionQuestion = partyTrainingClassSessionQuestionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTrainingClassSessionQuestionDetailValue.getPartyTrainingClassSessionQuestionPK());
            var partyTrainingClassSessionQuestionDetail = partyTrainingClassSessionQuestion.getActiveDetailForUpdate();
            
            partyTrainingClassSessionQuestionDetail.setThruTime(session.getStartTime());
            partyTrainingClassSessionQuestionDetail.store();

            var partyTrainingClassSessionQuestionPK = partyTrainingClassSessionQuestionDetail.getPartyTrainingClassSessionQuestionPK();
            var partyTrainingClassSessionPK = partyTrainingClassSessionQuestionDetail.getPartyTrainingClassSessionPK(); // Not updated
            var trainingClassQuestionPK = partyTrainingClassSessionQuestionDetail.getTrainingClassQuestionPK();
            var sortOrder = partyTrainingClassSessionQuestionDetail.getSortOrder();
            
            partyTrainingClassSessionQuestionDetail = partyTrainingClassSessionQuestionDetailFactory.create(partyTrainingClassSessionQuestionPK,
                    partyTrainingClassSessionPK, trainingClassQuestionPK, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            partyTrainingClassSessionQuestion.setActiveDetail(partyTrainingClassSessionQuestionDetail);
            partyTrainingClassSessionQuestion.setLastDetail(partyTrainingClassSessionQuestionDetail);
            
            sendEvent(partyTrainingClassSessionQuestionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deletePartyTrainingClassSessionQuestion(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion, BasePK deletedBy) {
        removePartyTrainingClassSessionQuestionStatusByPartyTrainingClassSessionQuestion(partyTrainingClassSessionQuestion);
        deletePartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestion(partyTrainingClassSessionQuestion, deletedBy);
        clearPartyTrainingClassSessionQuestionFromPartyTrainingClassSessionStatuses(partyTrainingClassSessionQuestion);

        var partyTrainingClassSessionQuestionDetail = partyTrainingClassSessionQuestion.getLastDetailForUpdate();
        partyTrainingClassSessionQuestionDetail.setThruTime(session.getStartTime());
        partyTrainingClassSessionQuestion.setActiveDetail(null);
        partyTrainingClassSessionQuestion.store();
        
        sendEvent(partyTrainingClassSessionQuestion.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deletePartyTrainingClassSessionQuestions(List<PartyTrainingClassSessionQuestion> partyTrainingClassSessionQuestions, BasePK deletedBy) {
        partyTrainingClassSessionQuestions.forEach((partyTrainingClassSessionQuestion) -> 
                deletePartyTrainingClassSessionQuestion(partyTrainingClassSessionQuestion, deletedBy)
        );
    }
    
    public void deletePartyTrainingClassSessionQuestionsByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession, BasePK deletedBy) {
        deletePartyTrainingClassSessionQuestions(getPartyTrainingClassSessionQuestionsByPartyTrainingClassSessionForUpdate(partyTrainingClassSession), deletedBy);
    }
    
    public void deletePartyTrainingClassSessionQuestionsByTrainingClassQuestion(TrainingClassQuestion trainingClassQuestion, BasePK deletedBy) {
        deletePartyTrainingClassSessionQuestions(getPartyTrainingClassSessionQuestionsByTrainingClassQuestionForUpdate(trainingClassQuestion), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Training Class Session Question Statuses
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyTrainingClassSessionQuestionStatusFactory partyTrainingClassSessionQuestionStatusFactory;

    public PartyTrainingClassSessionQuestionStatus createPartyTrainingClassSessionQuestionStatus(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        return partyTrainingClassSessionQuestionStatusFactory.create(partyTrainingClassSessionQuestion, 0);
    }
    
    private PartyTrainingClassSessionQuestionStatus getPartyTrainingClassSessionQuestionStatus(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessionQuestionStatuses.fields())
                .from(PartyTrainingClassSessionQuestionStatuses)
                .where(PartyTrainingClassSessionQuestionStatuses.PARTY_TRAINING_CLASS_SESSION_QUESTION.eq(partyTrainingClassSessionQuestion.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionQuestionStatusFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyTrainingClassSessionQuestionStatus getPartyTrainingClassSessionQuestionStatus(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        return getPartyTrainingClassSessionQuestionStatus(partyTrainingClassSessionQuestion, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSessionQuestionStatus getPartyTrainingClassSessionQuestionStatusForUpdate(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        return getPartyTrainingClassSessionQuestionStatus(partyTrainingClassSessionQuestion, EntityPermission.READ_WRITE);
    }
    
    public void removePartyTrainingClassSessionQuestionStatusByPartyTrainingClassSessionQuestion(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        var partyTrainingClassSessionQuestionStatus = getPartyTrainingClassSessionQuestionStatusForUpdate(partyTrainingClassSessionQuestion);
        
        if(partyTrainingClassSessionQuestionStatus != null) {
            partyTrainingClassSessionQuestionStatus.remove();
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Party Training Class Session Answers
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyTrainingClassSessionAnswerFactory partyTrainingClassSessionAnswerFactory;

    @Inject
    private PartyTrainingClassSessionAnswerTransferCache partyTrainingClassSessionAnswerTransferCache;

    public PartyTrainingClassSessionAnswer createPartyTrainingClassSessionAnswer(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion,
            TrainingClassAnswer trainingClassAnswer, Long readingStartTime, Long readingEndTime, BasePK createdBy) {
        var partyTrainingClassSessionQuestionStatus = getPartyTrainingClassSessionQuestionStatusForUpdate(partyTrainingClassSessionQuestion);
        Integer partyTrainingClassSessionAnswerSequence = partyTrainingClassSessionQuestionStatus.getPartyTrainingClassSessionAnswerSequence() + 1;

        partyTrainingClassSessionQuestionStatus.setPartyTrainingClassSessionAnswerSequence(partyTrainingClassSessionAnswerSequence);

        return createPartyTrainingClassSessionAnswer(partyTrainingClassSessionQuestion, partyTrainingClassSessionAnswerSequence, trainingClassAnswer, readingStartTime,
                readingEndTime, createdBy);
    }

    public PartyTrainingClassSessionAnswer createPartyTrainingClassSessionAnswer(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion,
            Integer partyTrainingClassSessionAnswerSequence, TrainingClassAnswer trainingClassAnswer, Long questionStartTime, Long questionEndTime, BasePK createdBy) {
        var partyTrainingClassSessionAnswer = partyTrainingClassSessionAnswerFactory.create(partyTrainingClassSessionQuestion,
            partyTrainingClassSessionAnswerSequence, trainingClassAnswer, questionStartTime, questionEndTime, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(partyTrainingClassSessionQuestion.getPrimaryKey(), EventTypes.MODIFY, partyTrainingClassSessionAnswer.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyTrainingClassSessionAnswer;
    }
    
    public PartyTrainingClassSessionAnswer getPartyTrainingClassSessionAnswer(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion,
            Integer partyTrainingClassSessionAnswerSequence, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessionAnswers.fields())
                .from(PartyTrainingClassSessionAnswers)
                .where(PartyTrainingClassSessionAnswers.PARTY_TRAINING_CLASS_SESSION_QUESTION.eq(partyTrainingClassSessionQuestion.getPrimaryKey()),
                        PartyTrainingClassSessionAnswers.PARTY_TRAINING_CLASS_SESSION_ANSWER_SEQUENCE.eq(partyTrainingClassSessionAnswerSequence),
                        PartyTrainingClassSessionAnswers.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionAnswerFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyTrainingClassSessionAnswer getPartyTrainingClassSessionAnswer(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion,
            Integer partyTrainingClassSessionAnswerSequence) {
        return getPartyTrainingClassSessionAnswer(partyTrainingClassSessionQuestion, partyTrainingClassSessionAnswerSequence, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSessionAnswer getPartyTrainingClassSessionAnswerForUpdate(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion,
            Integer partyTrainingClassSessionAnswerSequence) {
        return getPartyTrainingClassSessionAnswer(partyTrainingClassSessionQuestion, partyTrainingClassSessionAnswerSequence, EntityPermission.READ_WRITE);
    }
    
    public PartyTrainingClassSessionAnswerValue getPartyTrainingClassSessionAnswerValue(PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer) {
        return partyTrainingClassSessionAnswer == null? null: partyTrainingClassSessionAnswer.getPartyTrainingClassSessionAnswerValue().clone();
    }
    
    public PartyTrainingClassSessionAnswerValue getPartyTrainingClassSessionAnswerValueForUpdate(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion,
            Integer partyTrainingClassSessionAnswerSequence) {
        return getPartyTrainingClassSessionAnswerValue(getPartyTrainingClassSessionAnswerForUpdate(partyTrainingClassSessionQuestion, partyTrainingClassSessionAnswerSequence));
    }
    
    private List<PartyTrainingClassSessionAnswer> getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestion(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTrainingClassSessionAnswers.fields())
                .from(PartyTrainingClassSessionAnswers)
                .where(PartyTrainingClassSessionAnswers.PARTY_TRAINING_CLASS_SESSION_QUESTION.eq(partyTrainingClassSessionQuestion.getPrimaryKey()),
                        PartyTrainingClassSessionAnswers.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PartyTrainingClassSessionAnswers.PARTY_TRAINING_CLASS_SESSION_ANSWER_SEQUENCE),
                    PartyTrainingClassSessionAnswerFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTrainingClassSessionAnswerFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClassSessionAnswer> getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestion(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        return getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestion(partyTrainingClassSessionQuestion, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSessionAnswer> getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestionForUpdate(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        return getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestion(partyTrainingClassSessionQuestion, EntityPermission.READ_WRITE);
    }
    
    private List<PartyTrainingClassSessionAnswer> getPartyTrainingClassSessionAnswersByTrainingClassAnswer(TrainingClassAnswer trainingClassAnswer,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTrainingClassSessionAnswers.fields())
                    .from(PartyTrainingClassSessionAnswers)
                    .join(PartyTrainingClassSessionQuestions).onKey(PARTY_TRAINING_CLASS_SESSION_ANSWERS_PARTY_TRAINING_CLASS_SESSION_QUESTION_FK)
                    .join(PartyTrainingClassSessionQuestionDetails).onKey(PARTY_TRAINING_CLASS_SESSION_QUESTIONS_LAST_DETAIL_FK)
                    .join(PartyTrainingClassSessions).onKey(PARTY_TRAINING_CLASS_SESSION_QUESTION_DETAILS_PARTY_TRAINING_CLASS_SESSION_FK)
                    .join(PartyTrainingClassSessionDetails).onKey(PARTY_TRAINING_CLASS_SESSIONS_LAST_DETAIL_FK)
                    .join(PartyTrainingClasses).onKey(PARTY_TRAINING_CLASS_SESSION_DETAILS_PARTY_TRAINING_CLASS_FK)
                    .join(PartyTrainingClassDetails).onKey(PARTY_TRAINING_CLASSES_LAST_DETAIL_FK)
                    .where(PartyTrainingClassSessionAnswers.TRAINING_CLASS_ANSWER.eq(trainingClassAnswer.getPrimaryKey()),
                            PartyTrainingClassSessionAnswers.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTrainingClassSessionAnswers.PARTY_TRAINING_CLASS_SESSION_ANSWER_SEQUENCE, PartyTrainingClassSessionQuestionDetails.SORT_ORDER, PartyTrainingClassSessionDetails.PARTY_TRAINING_CLASS_SESSION_SEQUENCE, PartyTrainingClassDetails.PARTY_TRAINING_CLASS_NAME),
                    PartyTrainingClassSessionAnswerFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTrainingClassSessionAnswers.fields())
                    .from(PartyTrainingClassSessionAnswers)
                    .where(PartyTrainingClassSessionAnswers.TRAINING_CLASS_ANSWER.eq(trainingClassAnswer.getPrimaryKey()),
                            PartyTrainingClassSessionAnswers.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyTrainingClassSessionAnswerFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyTrainingClassSessionAnswer> getPartyTrainingClassSessionAnswersByTrainingClassAnswer(TrainingClassAnswer trainingClassAnswer) {
        return getPartyTrainingClassSessionAnswersByTrainingClassAnswer(trainingClassAnswer, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSessionAnswer> getPartyTrainingClassSessionAnswersByTrainingClassAnswerForUpdate(TrainingClassAnswer trainingClassAnswer) {
        return getPartyTrainingClassSessionAnswersByTrainingClassAnswer(trainingClassAnswer, EntityPermission.READ_WRITE);
    }
    
    public PartyTrainingClassSessionAnswerTransfer getPartyTrainingClassSessionAnswerTransfer(UserVisit userVisit, PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer) {
        return partyTrainingClassSessionAnswerTransferCache.getPartyTrainingClassSessionAnswerTransfer(userVisit, partyTrainingClassSessionAnswer);
    }
    
    public List<PartyTrainingClassSessionAnswerTransfer> getPartyTrainingClassSessionAnswerTransfers(UserVisit userVisit, Collection<PartyTrainingClassSessionAnswer> partyTrainingClassSessionAnswers) {
        List<PartyTrainingClassSessionAnswerTransfer> partyTrainingClassSessionAnswerTransfers = new ArrayList<>(partyTrainingClassSessionAnswers.size());
        
        partyTrainingClassSessionAnswers.forEach((partyTrainingClassSessionAnswer) ->
                partyTrainingClassSessionAnswerTransfers.add(partyTrainingClassSessionAnswerTransferCache.getPartyTrainingClassSessionAnswerTransfer(userVisit, partyTrainingClassSessionAnswer))
        );
        
        return partyTrainingClassSessionAnswerTransfers;
    }
    
    public List<PartyTrainingClassSessionAnswerTransfer> getPartyTrainingClassSessionAnswerTransfersByPartyTrainingClassSessionQuestion(UserVisit userVisit,
            PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        return getPartyTrainingClassSessionAnswerTransfers(userVisit, getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestion(partyTrainingClassSessionQuestion));
    }
    
    public void updatePartyTrainingClassSessionAnswerFromValue(PartyTrainingClassSessionAnswerValue partyTrainingClassSessionAnswerValue, BasePK updatedBy) {
        if(partyTrainingClassSessionAnswerValue.hasBeenModified()) {
            var partyTrainingClassSessionAnswer = partyTrainingClassSessionAnswerFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    partyTrainingClassSessionAnswerValue.getPrimaryKey());
            
            partyTrainingClassSessionAnswer.setThruTime(session.getStartTime());
            partyTrainingClassSessionAnswer.store();

            var partyTrainingClassSessionQuestionPK = partyTrainingClassSessionAnswer.getPartyTrainingClassSessionQuestionPK(); // Not updated
            var partyTrainingClassSessionAnswerSequence = partyTrainingClassSessionAnswer.getPartyTrainingClassSessionAnswerSequence(); // Not updated
            var trainingClassAnswerPK = partyTrainingClassSessionAnswerValue.getTrainingClassAnswerPK();
            var questionStartTime = partyTrainingClassSessionAnswerValue.getQuestionStartTime();
            var questionEndTime = partyTrainingClassSessionAnswerValue.getQuestionEndTime();
            
            partyTrainingClassSessionAnswer = partyTrainingClassSessionAnswerFactory.create(partyTrainingClassSessionQuestionPK,
                    partyTrainingClassSessionAnswerSequence, trainingClassAnswerPK, questionStartTime, questionEndTime, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(partyTrainingClassSessionQuestionPK, EventTypes.MODIFY, partyTrainingClassSessionAnswer.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyTrainingClassSessionAnswer(PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer, BasePK deletedBy) {
        partyTrainingClassSessionAnswer.setThruTime(session.getStartTime());
        
        sendEvent(partyTrainingClassSessionAnswer.getPartyTrainingClassSessionQuestionPK(), EventTypes.MODIFY, partyTrainingClassSessionAnswer.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyTrainingClassSessionAnswers(List<PartyTrainingClassSessionAnswer> partyTrainingClassSessionAnswers, BasePK deletedBy) {
        partyTrainingClassSessionAnswers.forEach((partyTrainingClassSessionAnswer) -> 
                deletePartyTrainingClassSessionAnswer(partyTrainingClassSessionAnswer, deletedBy)
        );
    }
    
    public void deletePartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestion(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion, BasePK deletedBy) {
        deletePartyTrainingClassSessionAnswers(getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestionForUpdate(partyTrainingClassSessionQuestion), deletedBy);
    }
    
    public void deletePartyTrainingClassSessionAnswersByTrainingClassAnswer(TrainingClassAnswer trainingClassAnswer, BasePK deletedBy) {
        deletePartyTrainingClassSessionAnswers(getPartyTrainingClassSessionAnswersByTrainingClassAnswerForUpdate(trainingClassAnswer), deletedBy);
    }
    
}
