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

package com.echothree.model.control.training.server.control;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
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
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.training.common.pk.PartyTrainingClassPK;
import com.echothree.model.data.training.common.pk.TrainingClassPK;
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
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class TrainingControl
        extends BaseModelControl {
    
    /** Creates a new instance of TrainingControl */
    protected TrainingControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Training Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    TrainingClassTransferCache trainingClassTransferCache;

    @Inject
    TrainingClassTranslationTransferCache trainingClassTranslationTransferCache;

    @Inject
    TrainingClassSectionTransferCache trainingClassSectionTransferCache;

    @Inject
    TrainingClassSectionTranslationTransferCache trainingClassSectionTranslationTransferCache;

    @Inject
    TrainingClassPageTransferCache trainingClassPageTransferCache;

    @Inject
    TrainingClassPageTranslationTransferCache trainingClassPageTranslationTransferCache;

    @Inject
    TrainingClassQuestionTransferCache trainingClassQuestionTransferCache;

    @Inject
    TrainingClassQuestionTranslationTransferCache trainingClassQuestionTranslationTransferCache;

    @Inject
    TrainingClassAnswerTransferCache trainingClassAnswerTransferCache;

    @Inject
    TrainingClassAnswerTranslationTransferCache trainingClassAnswerTranslationTransferCache;

    @Inject
    PartyTrainingClassTransferCache partyTrainingClassTransferCache;

    @Inject
    PartyTrainingClassSessionTransferCache partyTrainingClassSessionTransferCache;

    @Inject
    PartyTrainingClassSessionSectionTransferCache partyTrainingClassSessionSectionTransferCache;

    @Inject
    PartyTrainingClassSessionPageTransferCache partyTrainingClassSessionPageTransferCache;

    @Inject
    PartyTrainingClassSessionQuestionTransferCache partyTrainingClassSessionQuestionTransferCache;

    @Inject
    PartyTrainingClassSessionAnswerTransferCache partyTrainingClassSessionAnswerTransferCache;

    // --------------------------------------------------------------------------------
    //   Training Classes
    // --------------------------------------------------------------------------------
    
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

        var trainingClass = TrainingClassFactory.getInstance().create();
        var trainingClassDetail = TrainingClassDetailFactory.getInstance().create(trainingClass, trainingClassName, estimatedReadingTime,
                readingTimeAllowed, estimatedTestingTime, testingTimeAllowed, requiredCompletionTime, workEffortScope, defaultPercentageToPass,
                overallQuestionCount, testingValidityTime, expiredRetentionTime, alwaysReassignOnExpiration, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        trainingClass = TrainingClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, trainingClass.getPrimaryKey());
        trainingClass.setActiveDetail(trainingClassDetail);
        trainingClass.setLastDetail(trainingClassDetail);
        trainingClass.store();
        
        sendEvent(trainingClass.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return trainingClass;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TrainingClass */
    public TrainingClass getTrainingClassByEntityInstance(EntityInstance entityInstance) {
        var pk = new TrainingClassPK(entityInstance.getEntityUniqueId());
        var trainingClass = TrainingClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return trainingClass;
    }

    private static final Map<EntityPermission, String> getTrainingClassByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasses, trainingclassdetails " +
                "WHERE trncls_activedetailid = trnclsdt_trainingclassdetailid AND trnclsdt_trainingclassname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasses, trainingclassdetails " +
                "WHERE trncls_activedetailid = trnclsdt_trainingclassdetailid AND trnclsdt_trainingclassname = ? " +
                "FOR UPDATE");
        getTrainingClassByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public TrainingClass getTrainingClassByName(String trainingClassName, EntityPermission entityPermission) {
        return TrainingClassFactory.getInstance().getEntityFromQuery(entityPermission, getTrainingClassByNameQueries, trainingClassName);
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
    
    private static final Map<EntityPermission, String> getDefaultTrainingClassQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasses, trainingclassdetails " +
                "WHERE trncls_activedetailid = trnclsdt_trainingclassdetailid AND trnclsdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasses, trainingclassdetails " +
                "WHERE trncls_activedetailid = trnclsdt_trainingclassdetailid AND trnclsdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultTrainingClassQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private TrainingClass getDefaultTrainingClass(EntityPermission entityPermission) {
        return TrainingClassFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultTrainingClassQueries);
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
    
    private static final Map<EntityPermission, String> getTrainingClassesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasses, trainingclassdetails " +
                "WHERE trncls_activedetailid = trnclsdt_trainingclassdetailid " +
                "ORDER BY trnclsdt_sortorder, trnclsdt_trainingclassname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasses, trainingclassdetails " +
                "WHERE trncls_activedetailid = trnclsdt_trainingclassdetailid " +
                "FOR UPDATE");
        getTrainingClassesQueries = Collections.unmodifiableMap(queryMap);
    }
    
   private List<TrainingClass> getTrainingClasses(EntityPermission entityPermission) {
        return TrainingClassFactory.getInstance().getEntitiesFromQuery(entityPermission, getTrainingClassesQueries);
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
    
    public List<TrainingClassTransfer> getTrainingClassTransfers(UserVisit userVisit) {
        var trainingClasses = getTrainingClasses();
        List<TrainingClassTransfer> trainingClassTransfers = new ArrayList<>(trainingClasses.size());
        
        trainingClasses.forEach((trainingClass) ->
                trainingClassTransfers.add(trainingClassTransferCache.getTrainingClassTransfer(userVisit, trainingClass))
        );
        
        return trainingClassTransfers;
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
            var trainingClass = TrainingClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     trainingClassDetailValue.getTrainingClassPK());
            var trainingClassDetail = trainingClass.getActiveDetailForUpdate();
            
            trainingClassDetail.setThruTime(session.START_TIME_LONG);
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
            
            trainingClassDetail = TrainingClassDetailFactory.getInstance().create(trainingClassPK, trainingClassName, estimatedReadingTime, readingTimeAllowed,
                    estimatedTestingTime, testingTimeAllowed, requiredCompletionTime, workEffortScopePK, defaultPercentageToPass, overallQuestionCount,
                    testingValidityTime, expiredRetentionTime, alwaysReassignOnExpiration, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        PartySecurityRoleTemplateLogic.getInstance().deletePartySecurityRoleTemplateTrainingClassesByTrainingClass(trainingClass, deletedBy);

        var trainingClassDetail = trainingClass.getLastDetailForUpdate();
        trainingClassDetail.setThruTime(session.START_TIME_LONG);
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
    
    public TrainingClassTranslation createTrainingClassTranslation(TrainingClass trainingClass, Language language, String description, MimeType overviewMimeType,
            String overview, MimeType introductionMimeType, String introduction, BasePK createdBy) {
        var trainingClassTranslation = TrainingClassTranslationFactory.getInstance().create(trainingClass, language, description,
                overviewMimeType, overview, introductionMimeType, introduction, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(trainingClass.getPrimaryKey(), EventTypes.MODIFY, trainingClassTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return trainingClassTranslation;
    }
    
    private static final Map<EntityPermission, String> getTrainingClassTranslationQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasstranslations " +
                "WHERE trnclstr_trncls_trainingclassid = ? AND trnclstr_lang_languageid = ? AND trnclstr_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasstranslations " +
                "WHERE trnclstr_trncls_trainingclassid = ? AND trnclstr_lang_languageid = ? AND trnclstr_thrutime = ? " +
                "FOR UPDATE");
        getTrainingClassTranslationQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private TrainingClassTranslation getTrainingClassTranslation(TrainingClass trainingClass, Language language, EntityPermission entityPermission) {
        return TrainingClassTranslationFactory.getInstance().getEntityFromQuery(entityPermission, getTrainingClassTranslationQueries, trainingClass, language,
                Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getTrainingClassTranslationsByTrainingClassQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasstranslations, languages " +
                "WHERE trnclstr_trncls_trainingclassid = ? AND trnclstr_thrutime = ? AND trnclstr_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasstranslations " +
                "WHERE trnclstr_trncls_trainingclassid = ? AND trnclstr_thrutime = ? " +
                "FOR UPDATE");
        getTrainingClassTranslationsByTrainingClassQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<TrainingClassTranslation> getTrainingClassTranslationsByTrainingClass(TrainingClass trainingClass, EntityPermission entityPermission) {
        return TrainingClassTranslationFactory.getInstance().getEntitiesFromQuery(entityPermission, getTrainingClassTranslationsByTrainingClassQueries,
                trainingClass, Session.MAX_TIME);
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
            var trainingClassTranslation = TrainingClassTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    trainingClassTranslationValue.getPrimaryKey());
            
            trainingClassTranslation.setThruTime(session.START_TIME_LONG);
            trainingClassTranslation.store();

            var trainingClassPK = trainingClassTranslation.getTrainingClassPK();
            var languagePK = trainingClassTranslation.getLanguagePK();
            var description = trainingClassTranslationValue.getDescription();
            var overviewMimeTypePK = trainingClassTranslationValue.getOverviewMimeTypePK();
            var overview = trainingClassTranslationValue.getOverview();
            var introductionMimeTypePK = trainingClassTranslationValue.getIntroductionMimeTypePK();
            var introduction = trainingClassTranslationValue.getIntroduction();
            
            trainingClassTranslation = TrainingClassTranslationFactory.getInstance().create(trainingClassPK, languagePK, description, overviewMimeTypePK,
                    overview, introductionMimeTypePK, introduction, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(trainingClassPK, EventTypes.MODIFY, trainingClassTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTrainingClassTranslation(TrainingClassTranslation trainingClassTranslation, BasePK deletedBy) {
        trainingClassTranslation.setThruTime(session.START_TIME_LONG);
        
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
    
    public TrainingClassSection createTrainingClassSection(TrainingClass trainingClass, String trainingClassSectionName, Integer percentageToPass,
            Integer questionCount, Integer sortOrder, BasePK createdBy) {
        var trainingClassSection = TrainingClassSectionFactory.getInstance().create();
        var trainingClassSectionDetail = TrainingClassSectionDetailFactory.getInstance().create(trainingClassSection, trainingClass,
                trainingClassSectionName, percentageToPass, questionCount, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        trainingClassSection = TrainingClassSectionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, trainingClassSection.getPrimaryKey());
        trainingClassSection.setActiveDetail(trainingClassSectionDetail);
        trainingClassSection.setLastDetail(trainingClassSectionDetail);
        trainingClassSection.store();
        
        sendEvent(trainingClassSection.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return trainingClassSection;
    }
    
    private static final Map<EntityPermission, String> getTrainingClassSectionByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasssections, trainingclasssectiondetails " +
                "WHERE trnclss_activedetailid = trnclssdt_trainingclasssectiondetailid " +
                "AND trnclssdt_trncls_trainingclassid = ? AND trnclssdt_trainingclasssectionname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasssections, trainingclasssectiondetails " +
                "WHERE trnclss_activedetailid = trnclssdt_trainingclasssectiondetailid " +
                "AND trnclssdt_trncls_trainingclassid = ? AND trnclssdt_trainingclasssectionname = ? " +
                "FOR UPDATE");
        getTrainingClassSectionByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public TrainingClassSection getTrainingClassSectionByName(TrainingClass trainingClass, String trainingClassSectionName, EntityPermission entityPermission) {
        return TrainingClassSectionFactory.getInstance().getEntityFromQuery(entityPermission, getTrainingClassSectionByNameQueries, trainingClass,
                trainingClassSectionName);
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
    
    private static final Map<EntityPermission, String> getTrainingClassSectionsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasssections, trainingclasssectiondetails " +
                "WHERE trnclss_activedetailid = trnclssdt_trainingclasssectiondetailid " +
                "AND trnclssdt_trncls_trainingclassid = ? " +
                "ORDER BY trnclssdt_sortorder, trnclssdt_trainingclasssectionname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasssections, trainingclasssectiondetails " +
                "WHERE trnclss_activedetailid = trnclssdt_trainingclasssectiondetailid " +
                "AND trnclssdt_trncls_trainingclassid = ? " +
                "FOR UPDATE");
        getTrainingClassSectionsQueries = Collections.unmodifiableMap(queryMap);
    }
    
   private List<TrainingClassSection> getTrainingClassSections(EntityPermission entityPermission, TrainingClass trainingClass) {
        return TrainingClassSectionFactory.getInstance().getEntitiesFromQuery(entityPermission, getTrainingClassSectionsQueries, trainingClass);
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
            var trainingClassSection = TrainingClassSectionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     trainingClassSectionDetailValue.getTrainingClassSectionPK());
            var trainingClassSectionDetail = trainingClassSection.getActiveDetailForUpdate();
            
            trainingClassSectionDetail.setThruTime(session.START_TIME_LONG);
            trainingClassSectionDetail.store();

            var trainingClassSectionPK = trainingClassSectionDetail.getTrainingClassSectionPK(); // Not updated
            var trainingClassPK = trainingClassSectionDetail.getTrainingClassPK(); // Not updated
            var trainingClassSectionName = trainingClassSectionDetailValue.getTrainingClassSectionName();
            var percentageToPass = trainingClassSectionDetailValue.getPercentageToPass();
            var questionCount = trainingClassSectionDetailValue.getQuestionCount();
            var sortOrder = trainingClassSectionDetailValue.getSortOrder();
            
            trainingClassSectionDetail = TrainingClassSectionDetailFactory.getInstance().create(trainingClassSectionPK, trainingClassPK,
                    trainingClassSectionName, percentageToPass, questionCount, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        trainingClassSectionDetail.setThruTime(session.START_TIME_LONG);
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
    
    public TrainingClassSectionTranslation createTrainingClassSectionTranslation(TrainingClassSection trainingClassSection, Language language,
            String description, MimeType overviewMimeType, String overview, MimeType introductionMimeType, String introduction, BasePK createdBy) {
        var trainingClassSectionTranslation = TrainingClassSectionTranslationFactory.getInstance().create(trainingClassSection,
                language, description, overviewMimeType, overview, introductionMimeType, introduction, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(trainingClassSection.getPrimaryKey(), EventTypes.MODIFY, trainingClassSectionTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return trainingClassSectionTranslation;
    }
    
    private static final Map<EntityPermission, String> getTrainingClassSectionTranslationQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasssectiontranslations " +
                "WHERE trnclsstr_trnclss_trainingclasssectionid = ? AND trnclsstr_lang_languageid = ? AND trnclsstr_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasssectiontranslations " +
                "WHERE trnclsstr_trnclss_trainingclasssectionid = ? AND trnclsstr_lang_languageid = ? AND trnclsstr_thrutime = ? " +
                "FOR UPDATE");
        getTrainingClassSectionTranslationQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private TrainingClassSectionTranslation getTrainingClassSectionTranslation(TrainingClassSection trainingClassSection, Language language, EntityPermission entityPermission) {
        return TrainingClassSectionTranslationFactory.getInstance().getEntityFromQuery(entityPermission, getTrainingClassSectionTranslationQueries, trainingClassSection, language,
                Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getTrainingClassSectionTranslationsByTrainingClassSectionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasssectiontranslations, languages " +
                "WHERE trnclsstr_trnclss_trainingclasssectionid = ? AND trnclsstr_thrutime = ? AND trnclsstr_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasssectiontranslations " +
                "WHERE trnclsstr_trnclss_trainingclasssectionid = ? AND trnclsstr_thrutime = ? " +
                "FOR UPDATE");
        getTrainingClassSectionTranslationsByTrainingClassSectionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<TrainingClassSectionTranslation> getTrainingClassSectionTranslationsByTrainingClassSection(TrainingClassSection trainingClassSection, EntityPermission entityPermission) {
        return TrainingClassSectionTranslationFactory.getInstance().getEntitiesFromQuery(entityPermission, getTrainingClassSectionTranslationsByTrainingClassSectionQueries,
                trainingClassSection, Session.MAX_TIME);
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
            var trainingClassSectionTranslation = TrainingClassSectionTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    trainingClassSectionTranslationValue.getPrimaryKey());
            
            trainingClassSectionTranslation.setThruTime(session.START_TIME_LONG);
            trainingClassSectionTranslation.store();

            var trainingClassSectionPK = trainingClassSectionTranslation.getTrainingClassSectionPK();
            var languagePK = trainingClassSectionTranslation.getLanguagePK();
            var description = trainingClassSectionTranslationValue.getDescription();
            var overviewMimeTypePK = trainingClassSectionTranslationValue.getOverviewMimeTypePK();
            var overview = trainingClassSectionTranslationValue.getOverview();
            var introductionMimeTypePK = trainingClassSectionTranslationValue.getIntroductionMimeTypePK();
            var introduction = trainingClassSectionTranslationValue.getIntroduction();
            
            trainingClassSectionTranslation = TrainingClassSectionTranslationFactory.getInstance().create(trainingClassSectionPK, languagePK, description,
                    overviewMimeTypePK, overview, introductionMimeTypePK, introduction, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(trainingClassSectionPK, EventTypes.MODIFY, trainingClassSectionTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTrainingClassSectionTranslation(TrainingClassSectionTranslation trainingClassSectionTranslation, BasePK deletedBy) {
        trainingClassSectionTranslation.setThruTime(session.START_TIME_LONG);
        
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
    
    public TrainingClassPage createTrainingClassPage(TrainingClassSection trainingClassSection, String trainingClassPageName, Integer sortOrder,
            BasePK createdBy) {
        var trainingClassPage = TrainingClassPageFactory.getInstance().create();
        var trainingClassPageDetail = TrainingClassPageDetailFactory.getInstance().create(trainingClassPage, trainingClassSection,
                trainingClassPageName, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        trainingClassPage = TrainingClassPageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, trainingClassPage.getPrimaryKey());
        trainingClassPage.setActiveDetail(trainingClassPageDetail);
        trainingClassPage.setLastDetail(trainingClassPageDetail);
        trainingClassPage.store();
        
        sendEvent(trainingClassPage.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return trainingClassPage;
    }
    
    private static final Map<EntityPermission, String> getTrainingClassPageByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasspages, trainingclasspagedetails " +
                "WHERE trnclsp_activedetailid = trnclspdt_trainingclasspagedetailid " +
                "AND trnclspdt_trnclss_trainingclasssectionid = ? AND trnclspdt_trainingclasspagename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasspages, trainingclasspagedetails " +
                "WHERE trnclsp_activedetailid = trnclspdt_trainingclasspagedetailid " +
                "AND trnclspdt_trnclss_trainingclasssectionid = ? AND trnclspdt_trainingclasspagename = ? " +
                "FOR UPDATE");
        getTrainingClassPageByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public TrainingClassPage getTrainingClassPageByName(TrainingClassSection trainingClassSection, String trainingClassPageName,
            EntityPermission entityPermission) {
        return TrainingClassPageFactory.getInstance().getEntityFromQuery(entityPermission, getTrainingClassPageByNameQueries, trainingClassSection,
                trainingClassPageName);
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
    
    public long countTrainingClassPages(TrainingClassSection trainingClassSection) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM trainingclasspages, trainingclasspagedetails " +
                "WHERE trnclsp_activedetailid = trnclspdt_trainingclasspagedetailid " +
                "AND trnclspdt_trnclss_trainingclasssectionid = ?",
                trainingClassSection);
    }

    public long countTrainingClassPages(List<TrainingClassSection> trainingClassSections) {
        long total = 0;

        total = trainingClassSections.stream().map((trainingClassSection) -> countTrainingClassPages(trainingClassSection)).reduce(total, (accumulator, _item) -> accumulator + _item);

        return total;
    }

    public long countTrainingClassPages(TrainingClass trainingClass) {
        return countTrainingClassPages(getTrainingClassSections(trainingClass));
    }

    private static final Map<EntityPermission, String> getTrainingClassPagesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasspages, trainingclasspagedetails " +
                "WHERE trnclsp_activedetailid = trnclspdt_trainingclasspagedetailid " +
                "AND trnclspdt_trnclss_trainingclasssectionid = ? " +
                "ORDER BY trnclspdt_sortorder, trnclspdt_trainingclasspagename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasspages, trainingclasspagedetails " +
                "WHERE trnclsp_activedetailid = trnclspdt_trainingclasspagedetailid " +
                "AND trnclspdt_trnclss_trainingclasssectionid = ? " +
                "FOR UPDATE");
        getTrainingClassPagesQueries = Collections.unmodifiableMap(queryMap);
    }
    
   private List<TrainingClassPage> getTrainingClassPages(EntityPermission entityPermission, TrainingClassSection trainingClassSection) {
        return TrainingClassPageFactory.getInstance().getEntitiesFromQuery(entityPermission, getTrainingClassPagesQueries, trainingClassSection);
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
            var trainingClassPage = TrainingClassPageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     trainingClassPageDetailValue.getTrainingClassPagePK());
            var trainingClassPageDetail = trainingClassPage.getActiveDetailForUpdate();
            
            trainingClassPageDetail.setThruTime(session.START_TIME_LONG);
            trainingClassPageDetail.store();

            var trainingClassPagePK = trainingClassPageDetail.getTrainingClassPagePK(); // Not updated
            var trainingClassSectionPK = trainingClassPageDetail.getTrainingClassSectionPK(); // Not updated
            var trainingClassPageName = trainingClassPageDetailValue.getTrainingClassPageName();
            var sortOrder = trainingClassPageDetailValue.getSortOrder();
            
            trainingClassPageDetail = TrainingClassPageDetailFactory.getInstance().create(trainingClassPagePK, trainingClassSectionPK, trainingClassPageName,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            trainingClassPage.setActiveDetail(trainingClassPageDetail);
            trainingClassPage.setLastDetail(trainingClassPageDetail);
            
            sendEvent(trainingClassPagePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteTrainingClassPage(TrainingClassPage trainingClassPage, BasePK deletedBy) {
        deleteTrainingClassPageTranslationsByTrainingClassPage(trainingClassPage, deletedBy);
        deletePartyTrainingClassSessionPagesByTrainingClassPage(trainingClassPage, deletedBy);

        var trainingClassPageDetail = trainingClassPage.getLastDetailForUpdate();
        trainingClassPageDetail.setThruTime(session.START_TIME_LONG);
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
    
    public TrainingClassPageTranslation createTrainingClassPageTranslation(TrainingClassPage trainingClassPage, Language language, String description,
            MimeType pageMimeType, String page, BasePK createdBy) {
        var trainingClassPageTranslation = TrainingClassPageTranslationFactory.getInstance().create(trainingClassPage, language,
                description, pageMimeType, page, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(trainingClassPage.getPrimaryKey(), EventTypes.MODIFY, trainingClassPageTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return trainingClassPageTranslation;
    }
    
    private static final Map<EntityPermission, String> getTrainingClassPageTranslationQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasspagetranslations " +
                "WHERE trnclsptr_trnclsp_trainingclasspageid = ? AND trnclsptr_lang_languageid = ? AND trnclsptr_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasspagetranslations " +
                "WHERE trnclsptr_trnclsp_trainingclasspageid = ? AND trnclsptr_lang_languageid = ? AND trnclsptr_thrutime = ? " +
                "FOR UPDATE");
        getTrainingClassPageTranslationQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private TrainingClassPageTranslation getTrainingClassPageTranslation(TrainingClassPage trainingClassPage, Language language,
            EntityPermission entityPermission) {
        return TrainingClassPageTranslationFactory.getInstance().getEntityFromQuery(entityPermission, getTrainingClassPageTranslationQueries, trainingClassPage,
                language, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getTrainingClassPageTranslationsByTrainingClassPageQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclasspagetranslations, languages " +
                "WHERE trnclsptr_trnclsp_trainingclasspageid = ? AND trnclsptr_thrutime = ? AND trnclsptr_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclasspagetranslations " +
                "WHERE trnclsptr_trnclsp_trainingclasspageid = ? AND trnclsptr_thrutime = ? " +
                "FOR UPDATE");
        getTrainingClassPageTranslationsByTrainingClassPageQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<TrainingClassPageTranslation> getTrainingClassPageTranslationsByTrainingClassPage(TrainingClassPage trainingClassPage,
            EntityPermission entityPermission) {
        return TrainingClassPageTranslationFactory.getInstance().getEntitiesFromQuery(entityPermission, getTrainingClassPageTranslationsByTrainingClassPageQueries,
                trainingClassPage, Session.MAX_TIME);
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
            var trainingClassPageTranslation = TrainingClassPageTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    trainingClassPageTranslationValue.getPrimaryKey());
            
            trainingClassPageTranslation.setThruTime(session.START_TIME_LONG);
            trainingClassPageTranslation.store();

            var trainingClassPagePK = trainingClassPageTranslation.getTrainingClassPagePK();
            var languagePK = trainingClassPageTranslation.getLanguagePK();
            var description = trainingClassPageTranslationValue.getDescription();
            var pageMimeTypePK = trainingClassPageTranslationValue.getPageMimeTypePK();
            var page = trainingClassPageTranslationValue.getPage();
            
            trainingClassPageTranslation = TrainingClassPageTranslationFactory.getInstance().create(trainingClassPagePK, languagePK, description,
                    pageMimeTypePK, page, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(trainingClassPagePK, EventTypes.MODIFY, trainingClassPageTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTrainingClassPageTranslation(TrainingClassPageTranslation trainingClassPageTranslation, BasePK deletedBy) {
        trainingClassPageTranslation.setThruTime(session.START_TIME_LONG);
        
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
    
    public TrainingClassQuestion createTrainingClassQuestion(TrainingClassSection trainingClassSection, String trainingClassQuestionName, Boolean askingRequired,
            Boolean passingRequired, Integer sortOrder, BasePK createdBy) {
        var trainingClassQuestion = TrainingClassQuestionFactory.getInstance().create();
        var trainingClassQuestionDetail = TrainingClassQuestionDetailFactory.getInstance().create(trainingClassQuestion,
                trainingClassSection, trainingClassQuestionName, askingRequired, passingRequired, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        trainingClassQuestion = TrainingClassQuestionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, trainingClassQuestion.getPrimaryKey());
        trainingClassQuestion.setActiveDetail(trainingClassQuestionDetail);
        trainingClassQuestion.setLastDetail(trainingClassQuestionDetail);
        trainingClassQuestion.store();
        
        sendEvent(trainingClassQuestion.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return trainingClassQuestion;
    }
    
    private static final Map<EntityPermission, String> getTrainingClassQuestionByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclassquestions, trainingclassquestiondetails " +
                "WHERE trnclsqus_activedetailid = trnclsqusdt_trainingclassquestiondetailid " +
                "AND trnclsqusdt_trnclss_trainingclasssectionid = ? AND trnclsqusdt_trainingclassquestionname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclassquestions, trainingclassquestiondetails " +
                "WHERE trnclsqus_activedetailid = trnclsqusdt_trainingclassquestiondetailid " +
                "AND trnclsqusdt_trnclss_trainingclasssectionid = ? AND trnclsqusdt_trainingclassquestionname = ? " +
                "FOR UPDATE");
        getTrainingClassQuestionByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public TrainingClassQuestion getTrainingClassQuestionByName(TrainingClassSection trainingClassSection, String trainingClassQuestionName,
            EntityPermission entityPermission) {
        return TrainingClassQuestionFactory.getInstance().getEntityFromQuery(entityPermission, getTrainingClassQuestionByNameQueries, trainingClassSection,
                trainingClassQuestionName);
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
    
    public long countTrainingClassQuestions(TrainingClassSection trainingClassSection) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM trainingclassquestions, trainingclassquestiondetails " +
                "WHERE trnclsqus_activedetailid = trnclsqusdt_trainingclassquestiondetailid " +
                "AND trnclsqusdt_trnclss_trainingclasssectionid = ?",
                trainingClassSection);
    }

    public long countTrainingClassQuestions(List<TrainingClassSection> trainingClassSections) {
        long total = 0;

        total = trainingClassSections.stream().map((trainingClassSection) -> countTrainingClassQuestions(trainingClassSection)).reduce(total, (accumulator, _item) -> accumulator + _item);

        return total;
    }

    public long countTrainingClassQuestions(TrainingClass trainingClass) {
        return countTrainingClassQuestions(getTrainingClassSections(trainingClass));
    }

    private static final Map<EntityPermission, String> getTrainingClassQuestionsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclassquestions, trainingclassquestiondetails " +
                "WHERE trnclsqus_activedetailid = trnclsqusdt_trainingclassquestiondetailid " +
                "AND trnclsqusdt_trnclss_trainingclasssectionid = ? " +
                "ORDER BY trnclsqusdt_sortorder, trnclsqusdt_trainingclassquestionname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclassquestions, trainingclassquestiondetails " +
                "WHERE trnclsqus_activedetailid = trnclsqusdt_trainingclassquestiondetailid " +
                "AND trnclsqusdt_trnclss_trainingclasssectionid = ? " +
                "FOR UPDATE");
        getTrainingClassQuestionsQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<TrainingClassQuestion> getTrainingClassQuestions(EntityPermission entityPermission, TrainingClassSection trainingClassSection) {
        return TrainingClassQuestionFactory.getInstance().getEntitiesFromQuery(entityPermission, getTrainingClassQuestionsQueries, trainingClassSection);
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
            var trainingClassQuestion = TrainingClassQuestionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     trainingClassQuestionDetailValue.getTrainingClassQuestionPK());
            var trainingClassQuestionDetail = trainingClassQuestion.getActiveDetailForUpdate();
            
            trainingClassQuestionDetail.setThruTime(session.START_TIME_LONG);
            trainingClassQuestionDetail.store();

            var trainingClassQuestionPK = trainingClassQuestionDetail.getTrainingClassQuestionPK(); // Not updated
            var trainingClassSectionPK = trainingClassQuestionDetail.getTrainingClassSectionPK(); // Not updated
            var trainingClassQuestionName = trainingClassQuestionDetailValue.getTrainingClassQuestionName();
            var askingRequired = trainingClassQuestionDetailValue.getAskingRequired();
            var passingRequired = trainingClassQuestionDetailValue.getPassingRequired();
            var sortOrder = trainingClassQuestionDetailValue.getSortOrder();
            
            trainingClassQuestionDetail = TrainingClassQuestionDetailFactory.getInstance().create(trainingClassQuestionPK, trainingClassSectionPK,
                    trainingClassQuestionName, askingRequired, passingRequired, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        trainingClassQuestionDetail.setThruTime(session.START_TIME_LONG);
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
    
    public TrainingClassQuestionTranslation createTrainingClassQuestionTranslation(TrainingClassQuestion trainingClassQuestion, Language language,
            MimeType questionMimeType, String question, BasePK createdBy) {
        var trainingClassQuestionTranslation = TrainingClassQuestionTranslationFactory.getInstance().create(trainingClassQuestion,
                language, questionMimeType, question, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(trainingClassQuestion.getPrimaryKey(), EventTypes.MODIFY, trainingClassQuestionTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return trainingClassQuestionTranslation;
    }
    
    private static final Map<EntityPermission, String> getTrainingClassQuestionTranslationQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclassquestiontranslations " +
                "WHERE trnclsqustr_trnclsqus_trainingclassquestionid = ? AND trnclsqustr_lang_languageid = ? AND trnclsqustr_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclassquestiontranslations " +
                "WHERE trnclsqustr_trnclsqus_trainingclassquestionid = ? AND trnclsqustr_lang_languageid = ? AND trnclsqustr_thrutime = ? " +
                "FOR UPDATE");
        getTrainingClassQuestionTranslationQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private TrainingClassQuestionTranslation getTrainingClassQuestionTranslation(TrainingClassQuestion trainingClassQuestion, Language language,
            EntityPermission entityPermission) {
        return TrainingClassQuestionTranslationFactory.getInstance().getEntityFromQuery(entityPermission, getTrainingClassQuestionTranslationQueries,
                trainingClassQuestion, language, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getTrainingClassQuestionTranslationsByTrainingClassQuestionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclassquestiontranslations, languages " +
                "WHERE trnclsqustr_trnclsqus_trainingclassquestionid = ? AND trnclsqustr_thrutime = ? AND trnclsqustr_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclassquestiontranslations " +
                "WHERE trnclsqustr_trnclsqus_trainingclassquestionid = ? AND trnclsqustr_thrutime = ? " +
                "FOR UPDATE");
        getTrainingClassQuestionTranslationsByTrainingClassQuestionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<TrainingClassQuestionTranslation> getTrainingClassQuestionTranslationsByTrainingClassQuestion(TrainingClassQuestion trainingClassQuestion,
            EntityPermission entityPermission) {
        return TrainingClassQuestionTranslationFactory.getInstance().getEntitiesFromQuery(entityPermission, getTrainingClassQuestionTranslationsByTrainingClassQuestionQueries,
                trainingClassQuestion, Session.MAX_TIME);
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
            var trainingClassQuestionTranslation = TrainingClassQuestionTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, trainingClassQuestionTranslationValue.getPrimaryKey());
            
            trainingClassQuestionTranslation.setThruTime(session.START_TIME_LONG);
            trainingClassQuestionTranslation.store();

            var trainingClassQuestionPK = trainingClassQuestionTranslation.getTrainingClassQuestionPK();
            var languagePK = trainingClassQuestionTranslation.getLanguagePK();
            var questionMimeTypePK = trainingClassQuestionTranslationValue.getQuestionMimeTypePK();
            var question = trainingClassQuestionTranslationValue.getQuestion();
            
            trainingClassQuestionTranslation = TrainingClassQuestionTranslationFactory.getInstance().create(trainingClassQuestionPK, languagePK,
                    questionMimeTypePK, question, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(trainingClassQuestionPK, EventTypes.MODIFY, trainingClassQuestionTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTrainingClassQuestionTranslation(TrainingClassQuestionTranslation trainingClassQuestionTranslation, BasePK deletedBy) {
        trainingClassQuestionTranslation.setThruTime(session.START_TIME_LONG);
        
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
    
    public TrainingClassAnswer createTrainingClassAnswer(TrainingClassQuestion trainingClassQuestion, String trainingClassAnswerName, Boolean isCorrect,
            Integer sortOrder, BasePK createdBy) {
        var trainingClassAnswer = TrainingClassAnswerFactory.getInstance().create();
        var trainingClassAnswerDetail = TrainingClassAnswerDetailFactory.getInstance().create(trainingClassAnswer, trainingClassQuestion,
                trainingClassAnswerName, isCorrect, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        trainingClassAnswer = TrainingClassAnswerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, trainingClassAnswer.getPrimaryKey());
        trainingClassAnswer.setActiveDetail(trainingClassAnswerDetail);
        trainingClassAnswer.setLastDetail(trainingClassAnswerDetail);
        trainingClassAnswer.store();
        
        sendEvent(trainingClassAnswer.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return trainingClassAnswer;
    }
    
    private static final Map<EntityPermission, String> getTrainingClassAnswerByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclassanswers, trainingclassanswerdetails " +
                "WHERE trnclsans_activedetailid = trnclsansdt_trainingclassanswerdetailid " +
                "AND trnclsansdt_trnclsqus_trainingclassquestionid = ? AND trnclsansdt_trainingclassanswername = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclassanswers, trainingclassanswerdetails " +
                "WHERE trnclsans_activedetailid = trnclsansdt_trainingclassanswerdetailid " +
                "AND trnclsansdt_trnclsqus_trainingclassquestionid = ? AND trnclsansdt_trainingclassanswername = ? " +
                "FOR UPDATE");
        getTrainingClassAnswerByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public TrainingClassAnswer getTrainingClassAnswerByName(TrainingClassQuestion trainingClassQuestion, String trainingClassAnswerName, EntityPermission entityPermission) {
        return TrainingClassAnswerFactory.getInstance().getEntityFromQuery(entityPermission, getTrainingClassAnswerByNameQueries, trainingClassQuestion, trainingClassAnswerName);
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
    
    private static final Map<EntityPermission, String> getTrainingClassAnswersQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclassanswers, trainingclassanswerdetails " +
                "WHERE trnclsans_activedetailid = trnclsansdt_trainingclassanswerdetailid " +
                "AND trnclsansdt_trnclsqus_trainingclassquestionid = ? " +
                "ORDER BY trnclsansdt_sortorder, trnclsansdt_trainingclassanswername " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclassanswers, trainingclassanswerdetails " +
                "WHERE trnclsans_activedetailid = trnclsansdt_trainingclassanswerdetailid " +
                "AND trnclsansdt_trnclsqus_trainingclassquestionid = ? " +
                "FOR UPDATE");
        getTrainingClassAnswersQueries = Collections.unmodifiableMap(queryMap);
    }
    
   private List<TrainingClassAnswer> getTrainingClassAnswers(EntityPermission entityPermission, TrainingClassQuestion trainingClassQuestion) {
        return TrainingClassAnswerFactory.getInstance().getEntitiesFromQuery(entityPermission, getTrainingClassAnswersQueries, trainingClassQuestion);
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
            var trainingClassAnswer = TrainingClassAnswerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     trainingClassAnswerDetailValue.getTrainingClassAnswerPK());
            var trainingClassAnswerDetail = trainingClassAnswer.getActiveDetailForUpdate();
            
            trainingClassAnswerDetail.setThruTime(session.START_TIME_LONG);
            trainingClassAnswerDetail.store();

            var trainingClassAnswerPK = trainingClassAnswerDetail.getTrainingClassAnswerPK(); // Not updated
            var trainingClassQuestionPK = trainingClassAnswerDetail.getTrainingClassQuestionPK(); // Not updated
            var trainingClassAnswerName = trainingClassAnswerDetailValue.getTrainingClassAnswerName();
            var isCorrect = trainingClassAnswerDetailValue.getIsCorrect();
            var sortOrder = trainingClassAnswerDetailValue.getSortOrder();
            
            trainingClassAnswerDetail = TrainingClassAnswerDetailFactory.getInstance().create(trainingClassAnswerPK, trainingClassQuestionPK,
                    trainingClassAnswerName, isCorrect, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            trainingClassAnswer.setActiveDetail(trainingClassAnswerDetail);
            trainingClassAnswer.setLastDetail(trainingClassAnswerDetail);
            
            sendEvent(trainingClassAnswerPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteTrainingClassAnswer(TrainingClassAnswer trainingClassAnswer, BasePK deletedBy) {
        deleteTrainingClassAnswerTranslationsByTrainingClassAnswer(trainingClassAnswer, deletedBy);
        deletePartyTrainingClassSessionAnswersByTrainingClassAnswer(trainingClassAnswer, deletedBy);

        var trainingClassAnswerDetail = trainingClassAnswer.getLastDetailForUpdate();
        trainingClassAnswerDetail.setThruTime(session.START_TIME_LONG);
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
    
    public TrainingClassAnswerTranslation createTrainingClassAnswerTranslation(TrainingClassAnswer trainingClassAnswer, Language language,
            MimeType answerMimeType, String answer, MimeType selectedMimeType, String selected, BasePK createdBy) {
        var trainingClassAnswerTranslation = TrainingClassAnswerTranslationFactory.getInstance().create(trainingClassAnswer,
                language, answerMimeType, answer, selectedMimeType, selected, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(trainingClassAnswer.getPrimaryKey(), EventTypes.MODIFY, trainingClassAnswerTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return trainingClassAnswerTranslation;
    }
    
    private static final Map<EntityPermission, String> getTrainingClassAnswerTranslationQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclassanswertranslations " +
                "WHERE trnclsanstr_trnclsans_trainingclassanswerid = ? AND trnclsanstr_lang_languageid = ? AND trnclsanstr_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclassanswertranslations " +
                "WHERE trnclsanstr_trnclsans_trainingclassanswerid = ? AND trnclsanstr_lang_languageid = ? AND trnclsanstr_thrutime = ? " +
                "FOR UPDATE");
        getTrainingClassAnswerTranslationQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private TrainingClassAnswerTranslation getTrainingClassAnswerTranslation(TrainingClassAnswer trainingClassAnswer, Language language, EntityPermission entityPermission) {
        return TrainingClassAnswerTranslationFactory.getInstance().getEntityFromQuery(entityPermission, getTrainingClassAnswerTranslationQueries,
                trainingClassAnswer, language, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getTrainingClassAnswerTranslationsByTrainingClassAnswerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trainingclassanswertranslations, languages " +
                "WHERE trnclsanstr_trnclsans_trainingclassanswerid = ? AND trnclsanstr_thrutime = ? AND trnclsanstr_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trainingclassanswertranslations " +
                "WHERE trnclsanstr_trnclsans_trainingclassanswerid = ? AND trnclsanstr_thrutime = ? " +
                "FOR UPDATE");
        getTrainingClassAnswerTranslationsByTrainingClassAnswerQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<TrainingClassAnswerTranslation> getTrainingClassAnswerTranslationsByTrainingClassAnswer(TrainingClassAnswer trainingClassAnswer,
            EntityPermission entityPermission) {
        return TrainingClassAnswerTranslationFactory.getInstance().getEntitiesFromQuery(entityPermission, getTrainingClassAnswerTranslationsByTrainingClassAnswerQueries,
                trainingClassAnswer, Session.MAX_TIME);
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
            var trainingClassAnswerTranslation = TrainingClassAnswerTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    trainingClassAnswerTranslationValue.getPrimaryKey());
            
            trainingClassAnswerTranslation.setThruTime(session.START_TIME_LONG);
            trainingClassAnswerTranslation.store();

            var trainingClassAnswerPK = trainingClassAnswerTranslation.getTrainingClassAnswerPK();
            var languagePK = trainingClassAnswerTranslation.getLanguagePK();
            var answerMimeTypePK = trainingClassAnswerTranslationValue.getAnswerMimeTypePK();
            var answer = trainingClassAnswerTranslationValue.getAnswer();
            var selectedMimeTypePK = trainingClassAnswerTranslationValue.getSelectedMimeTypePK();
            var selected = trainingClassAnswerTranslationValue.getSelected();
            
            trainingClassAnswerTranslation = TrainingClassAnswerTranslationFactory.getInstance().create(trainingClassAnswerPK, languagePK, answerMimeTypePK,
                    answer, selectedMimeTypePK, selected, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(trainingClassAnswerPK, EventTypes.MODIFY, trainingClassAnswerTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTrainingClassAnswerTranslation(TrainingClassAnswerTranslation trainingClassAnswerTranslation, BasePK deletedBy) {
        trainingClassAnswerTranslation.setThruTime(session.START_TIME_LONG);
        
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
    
    public PartyTrainingClass createPartyTrainingClass(Party party, TrainingClass trainingClass, Long completedTime, Long validUntilTime, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.PARTY_TRAINING_CLASS.name());
        var partyTrainingClassName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createPartyTrainingClass(partyTrainingClassName, party, trainingClass, completedTime, validUntilTime, createdBy);
    }
    
    public PartyTrainingClass createPartyTrainingClass(String partyTrainingClassName, Party party, TrainingClass trainingClass, Long completedTime,
            Long validUntilTime, BasePK createdBy) {
        var partyTrainingClass = PartyTrainingClassFactory.getInstance().create();
        var partyTrainingClassDetail = PartyTrainingClassDetailFactory.getInstance().create(partyTrainingClass, partyTrainingClassName,
                party, trainingClass, completedTime, validUntilTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        partyTrainingClass = PartyTrainingClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyTrainingClass.getPrimaryKey());
        partyTrainingClass.setActiveDetail(partyTrainingClassDetail);
        partyTrainingClass.setLastDetail(partyTrainingClassDetail);
        partyTrainingClass.store();

        var partyTrainingClassPK = partyTrainingClass.getPrimaryKey();
        sendEvent(partyTrainingClassPK, EventTypes.CREATE, null, null, createdBy);
        sendEvent(party.getPrimaryKey(), EventTypes.TOUCH, partyTrainingClassPK, EventTypes.CREATE, createdBy);
        
        return partyTrainingClass;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartyTrainingClass */
    public PartyTrainingClass getPartyTrainingClassByEntityInstance(EntityInstance entityInstance) {
        var pk = new PartyTrainingClassPK(entityInstance.getEntityUniqueId());
        var partyTrainingClass = PartyTrainingClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return partyTrainingClass;
    }

    private PartyTrainingClass convertEntityInstanceToPartyTrainingClass(final EntityInstance entityInstance, final EntityPermission entityPermission) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        PartyTrainingClass partyTrainingClass = null;
        
        if(entityInstanceControl.verifyEntityInstance(entityInstance, ComponentVendors.ECHO_THREE.name(), EntityTypes.PartyTrainingClass.name())) {
            partyTrainingClass = PartyTrainingClassFactory.getInstance().getEntityFromPK(entityPermission, new PartyTrainingClassPK(entityInstance.getEntityUniqueId()));
        }
        
        return partyTrainingClass;
    }
    
    public PartyTrainingClass convertEntityInstanceToPartyTrainingClass(final EntityInstance entityInstance) {
        return convertEntityInstanceToPartyTrainingClass(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClass convertEntityInstanceToPartyTrainingClassForUpdate(final EntityInstance entityInstance) {
        return convertEntityInstanceToPartyTrainingClass(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasses, partytrainingclassdetails " +
                "WHERE ptrncls_activedetailid = ptrnclsdt_partytrainingclassdetailid AND ptrnclsdt_partytrainingclassname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasses, partytrainingclassdetails " +
                "WHERE ptrncls_activedetailid = ptrnclsdt_partytrainingclassdetailid AND ptrnclsdt_partytrainingclassname = ? " +
                "FOR UPDATE");
        getPartyTrainingClassByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private PartyTrainingClass getPartyTrainingClassByName(String partyTrainingClassName, EntityPermission entityPermission) {
        return PartyTrainingClassFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTrainingClassByNameQueries, partyTrainingClassName);
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
    
    private static final Map<EntityPermission, String> getPartyTrainingClassesByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasses, partytrainingclassdetails, trainingclasses, trainingclassdetails " +
                "WHERE ptrncls_activedetailid = ptrnclsdt_partytrainingclassdetailid AND ptrnclsdt_par_partyid = ? " +
                "AND ptrnclsdt_trncls_trainingclassid = trncls_trainingclassid AND trncls_lastdetailid = trnclsdt_trainingclassdetailid " +
                "ORDER BY trnclsdt_sortorder, trnclsdt_trainingclassname, ptrnclsdt_partytrainingclassname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasses, partytrainingclassdetails " +
                "WHERE ptrncls_activedetailid = ptrnclsdt_partytrainingclassdetailid AND ptrnclsdt_par_partyid = ? " +
                "FOR UPDATE");
        getPartyTrainingClassesByPartyQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<PartyTrainingClass> getPartyTrainingClassesByParty(Party party, EntityPermission entityPermission) {
        return PartyTrainingClassFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassesByPartyQueries, party);
    }
    
    public List<PartyTrainingClass> getPartyTrainingClassesByParty(Party party) {
        return getPartyTrainingClassesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClass> getPartyTrainingClassesByPartyForUpdate(Party party) {
        return getPartyTrainingClassesByParty(party, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassesByTrainingClassQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasses, partytrainingclassdetails, parties, partydetails " +
                "WHERE ptrncls_activedetailid = ptrnclsdt_partytrainingclassdetailid AND ptrnclsdt_trncls_trainingclassid = ? " +
                "AND ptrnclsdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                "ORDER BY pardt_partyname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasses, partytrainingclassdetails " +
                "WHERE ptrncls_activedetailid = ptrnclsdt_partytrainingclassdetailid AND ptrnclsdt_trncls_trainingclassid = ? " +
                "FOR UPDATE");
        getPartyTrainingClassesByTrainingClassQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<PartyTrainingClass> getPartyTrainingClassesByTrainingClass(TrainingClass trainingClass, EntityPermission entityPermission) {
        return PartyTrainingClassFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassesByTrainingClassQueries, trainingClass);
    }
    
    public List<PartyTrainingClass> getPartyTrainingClassesByTrainingClass(TrainingClass trainingClass) {
        return getPartyTrainingClassesByTrainingClass(trainingClass, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClass> getPartyTrainingClassesByTrainingClassForUpdate(TrainingClass trainingClass) {
        return getPartyTrainingClassesByTrainingClass(trainingClass, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassesByStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasses, partytrainingclassdetails, workflows, workflowdetails, workflowsteps, workflowstepdetails, componentvendors, " +
                "componentvendordetails, entitytypes, entitytypedetails, entityinstances, workflowentitystatuses " +
                "WHERE ptrncls_activedetailid = ptrnclsdt_partytrainingclassdetailid AND ptrnclsdt_par_partyid = ? AND ptrnclsdt_trncls_trainingclassid = ? " +
                "AND wkfl_activedetailid = wkfldt_workflowdetailid AND wkfldt_workflowname = ? " +
                "AND wkfl_workflowid = wkflsdt_wkfl_workflowid AND wkfls_activedetailid = wkflsdt_workflowstepdetailid AND wkflsdt_workflowstepname = ? " +
                "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid AND ent_activedetailid = entdt_entitytypedetailid AND entdt_entitytypename = ? " +
                "AND ent_entitytypeid = eni_ent_entitytypeid AND ptrncls_partytrainingclassid = eni_entityuniqueid " +
                "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfls_workflowstepid = wkfles_wkfls_workflowstepid AND wkfles_thrutime = ? " +
                "ORDER BY ptrnclsdt_partytrainingclassname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasses, partytrainingclassdetails, workflows, workflowdetails, workflowsteps, workflowstepdetails, componentvendors, " +
                "componentvendordetails, entitytypes, entitytypedetails, entityinstances, workflowentitystatuses " +
                "WHERE ptrncls_activedetailid = ptrnclsdt_partytrainingclassdetailid AND ptrnclsdt_par_partyid = ? AND ptrnclsdt_trncls_trainingclassid = ? " +
                "AND wkfl_activedetailid = wkfldt_workflowdetailid AND wkfldt_workflowname = ? " +
                "AND wkfl_workflowid = wkflsdt_wkfl_workflowid AND wkfls_activedetailid = wkflsdt_workflowstepdetailid AND wkflsdt_workflowstepname = ? " +
                "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid AND ent_activedetailid = entdt_entitytypedetailid AND entdt_entitytypename = ? " +
                "AND ent_entitytypeid = eni_ent_entitytypeid AND ptrncls_partytrainingclassid = eni_entityuniqueid " +
                "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfls_workflowstepid = wkfles_wkfls_workflowstepid AND wkfles_thrutime = ? " +
                "FOR UPDATE");
        getPartyTrainingClassesByStatusQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<PartyTrainingClass> getPartyTrainingClassesByStatus(Party party, TrainingClass trainingClass, String workflowStepName,
            EntityPermission entityPermission) {
        return PartyTrainingClassFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassesByStatusQueries, party,
                trainingClass, PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS, workflowStepName, ComponentVendors.ECHO_THREE.name(),
                EntityTypes.PartyTrainingClass.name(), Session.MAX_TIME_LONG);
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

    public long countPartyTrainingClasses(Party party, TrainingClass trainingClass, WorkflowStep workflowStep) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partytrainingclasses, partytrainingclassdetails, componentvendors, componentvendordetails, entitytypes, entitytypedetails, " +
                "entityinstances, workflowentitystatuses " +
                "WHERE ptrncls_activedetailid = ptrnclsdt_partytrainingclassdetailid " +
                "AND ptrnclsdt_par_partyid = ? AND ptrnclsdt_trncls_trainingclassid = ? " +
                "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                "AND ent_activedetailid = entdt_entitytypedetailid " +
                "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                "AND entdt_entitytypename = ? " +
                "AND ent_entitytypeid = eni_ent_entitytypeid AND ptrncls_partytrainingclassid = eni_entityuniqueid " +
                "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ?",
                party, trainingClass, ComponentVendors.ECHO_THREE.name(), EntityTypes.PartyTrainingClass.name(), workflowStep,
                Session.MAX_TIME_LONG);
    }
    
    public long countPartyTrainingClassesUsingNames(Party party, TrainingClass trainingClass, String workflowStepName) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflow = workflowControl.getWorkflowByName(PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS);
        var workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);
        
        return countPartyTrainingClasses(party, trainingClass, workflowStep);
    }
    
    public PartyTrainingClassStatusChoicesBean getPartyTrainingClassStatusChoices(String defaultPartyTrainingClassStatusChoice, Language language,
            boolean allowNullChoice, PartyTrainingClass partyTrainingClass, PartyPK partyPK) {
        var partyTrainingClassStatusChoicesBean = new PartyTrainingClassStatusChoicesBean();

        if(partyTrainingClass == null) {
            workflowControl.getWorkflowEntranceChoices(partyTrainingClassStatusChoicesBean, defaultPartyTrainingClassStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
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
            var partyTrainingClass = PartyTrainingClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTrainingClassDetailValue.getPartyTrainingClassPK());
            var partyTrainingClassDetail = partyTrainingClass.getActiveDetailForUpdate();
            
            partyTrainingClassDetail.setThruTime(session.START_TIME_LONG);
            partyTrainingClassDetail.store();

            var partyTrainingClassPK = partyTrainingClassDetail.getPartyTrainingClassPK();
            var partyTrainingClassName = partyTrainingClassDetail.getPartyTrainingClassName(); // Not updated
            var party = partyTrainingClassDetail.getParty();
            var partyPK = party.getPrimaryKey(); // Not updated
            var trainingClassPK = partyTrainingClassDetail.getTrainingClassPK(); // Not updated
            var completedTime = partyTrainingClassDetailValue.getCompletedTime();
            var validUntilTime = partyTrainingClassDetailValue.getValidUntilTime();
            
            partyTrainingClassDetail = PartyTrainingClassDetailFactory.getInstance().create(partyTrainingClassPK, partyTrainingClassName, partyPK,
                    trainingClassPK, completedTime, validUntilTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        
        partyTrainingClassDetail.setThruTime(session.START_TIME_LONG);
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

    public PartyTrainingClassStatus createPartyTrainingClassStatus(PartyTrainingClass partyTrainingClass) {
        return PartyTrainingClassStatusFactory.getInstance().create(partyTrainingClass, 0, null);
    }

    private static final Map<EntityPermission, String> getPartyTrainingClassStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclassstatuses " +
                "WHERE ptrnclsst_ptrncls_partytrainingclassid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclassstatuses " +
                "WHERE ptrnclsst_ptrncls_partytrainingclassid = ? " +
                "FOR UPDATE");
        getPartyTrainingClassStatusQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyTrainingClassStatus getPartyTrainingClassStatus(PartyTrainingClass partyTrainingClass, EntityPermission entityPermission) {
        return PartyTrainingClassStatusFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTrainingClassStatusQueries, partyTrainingClass);
    }

    public PartyTrainingClassStatus getPartyTrainingClassStatus(PartyTrainingClass partyTrainingClass) {
        var partyTrainingClassStatus = getPartyTrainingClassStatus(partyTrainingClass, EntityPermission.READ_ONLY);

        return partyTrainingClassStatus == null ? createPartyTrainingClassStatus(partyTrainingClass) : partyTrainingClassStatus;
    }

    public PartyTrainingClassStatus getPartyTrainingClassStatusForUpdate(PartyTrainingClass partyTrainingClass) {
        var partyTrainingClassStatus = getPartyTrainingClassStatus(partyTrainingClass, EntityPermission.READ_WRITE);

        return partyTrainingClassStatus == null
                ? PartyTrainingClassStatusFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, createPartyTrainingClassStatus(partyTrainingClass).getPrimaryKey())
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
    
    public PartyTrainingClassSession createPartyTrainingClassSession(PartyTrainingClass partyTrainingClass, BasePK createdBy) {
        var partyTrainingClassStatus = getPartyTrainingClassStatusForUpdate(partyTrainingClass);
        Integer partyTrainingClassSessionSequence = partyTrainingClassStatus.getPartyTrainingClassSessionSequence() + 1;

        partyTrainingClassStatus.setPartyTrainingClassSessionSequence(partyTrainingClassSessionSequence);

        return createPartyTrainingClassSession(partyTrainingClass, partyTrainingClassSessionSequence, createdBy);
    }

    public PartyTrainingClassSession createPartyTrainingClassSession(PartyTrainingClass partyTrainingClass, Integer partyTrainingClassSessionSequence,
            BasePK createdBy) {
        var partyTrainingClassSession = PartyTrainingClassSessionFactory.getInstance().create();
        var partyTrainingClassSessionDetail = PartyTrainingClassSessionDetailFactory.getInstance().create(partyTrainingClassSession,
                partyTrainingClass, partyTrainingClassSessionSequence, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        partyTrainingClassSession = PartyTrainingClassSessionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyTrainingClassSession.getPrimaryKey());
        partyTrainingClassSession.setActiveDetail(partyTrainingClassSessionDetail);
        partyTrainingClassSession.setLastDetail(partyTrainingClassSessionDetail);
        partyTrainingClassSession.store();
        
        sendEvent(partyTrainingClassSession.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        createPartyTrainingClassSessionStatus(partyTrainingClassSession);

        return partyTrainingClassSession;
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionBySequenceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessions, partytrainingclasssessiondetails " +
                "WHERE ptrnclssess_activedetailid = ptrnclssessdt_partytrainingclasssessiondetailid " +
                "AND ptrnclssessdt_ptrncls_partytrainingclassid = ? AND ptrnclssessdt_partytrainingclasssessionsequence = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessions, partytrainingclasssessiondetails " +
                "WHERE ptrnclssess_activedetailid = ptrnclssessdt_partytrainingclasssessiondetailid " +
                "AND ptrnclssessdt_ptrncls_partytrainingclassid = ? AND ptrnclssessdt_partytrainingclasssessionsequence = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionBySequenceQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public PartyTrainingClassSession getPartyTrainingClassSessionBySequence(PartyTrainingClass partyTrainingClass, Integer partyTrainingClassSessionSequence,
            EntityPermission entityPermission) {
        return PartyTrainingClassSessionFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTrainingClassSessionBySequenceQueries,
                partyTrainingClass, partyTrainingClassSessionSequence);
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
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionsByPartyTrainingClassQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessions, partytrainingclasssessiondetails " +
                "WHERE ptrnclssess_activedetailid = ptrnclssessdt_partytrainingclasssessiondetailid " +
                "AND ptrnclssessdt_ptrncls_partytrainingclassid = ? " +
                "ORDER BY ptrnclssessdt_partytrainingclasssessionsequence " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessions, partytrainingclasssessiondetails " +
                "WHERE ptrnclssess_activedetailid = ptrnclssessdt_partytrainingclasssessiondetailid " +
                "AND ptrnclssessdt_ptrncls_partytrainingclassid = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionsByPartyTrainingClassQueries = Collections.unmodifiableMap(queryMap);
    }
    
   private List<PartyTrainingClassSession> getPartyTrainingClassSessionsByPartyTrainingClass(PartyTrainingClass partyTrainingClass,
           EntityPermission entityPermission) {
        return PartyTrainingClassSessionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassSessionsByPartyTrainingClassQueries,
                partyTrainingClass);
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
            var partyTrainingClassSession = PartyTrainingClassSessionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTrainingClassSessionDetailValue.getPartyTrainingClassSessionPK());
            var partyTrainingClassSessionDetail = partyTrainingClassSession.getActiveDetailForUpdate();
            
            partyTrainingClassSessionDetail.setThruTime(session.START_TIME_LONG);
            partyTrainingClassSessionDetail.store();

            var partyTrainingClassSessionPK = partyTrainingClassSessionDetail.getPartyTrainingClassSessionPK();
            var partyTrainingClassPK = partyTrainingClassSessionDetail.getPartyTrainingClassPK(); // Not updated
            var partyTrainingClassSessionSequence = partyTrainingClassSessionDetailValue.getPartyTrainingClassSessionSequence();
            
            partyTrainingClassSessionDetail = PartyTrainingClassSessionDetailFactory.getInstance().create(partyTrainingClassSessionPK,
                    partyTrainingClassPK, partyTrainingClassSessionSequence, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        partyTrainingClassSessionDetail.setThruTime(session.START_TIME_LONG);
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
    
    public PartyTrainingClassSessionStatus createPartyTrainingClassSessionStatus(PartyTrainingClassSession partyTrainingClassSession) {
        return PartyTrainingClassSessionStatusFactory.getInstance().create(partyTrainingClassSession, 0, 0, null, null, null);
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionstatuses " +
                "WHERE ptrnclssessst_ptrnclssess_partytrainingclasssessionid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionstatuses " +
                "WHERE ptrnclssessst_ptrnclssess_partytrainingclasssessionid = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionStatusQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private PartyTrainingClassSessionStatus getPartyTrainingClassSessionStatus(PartyTrainingClassSession partyTrainingClassSession,
            EntityPermission entityPermission) {
        return PartyTrainingClassSessionStatusFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTrainingClassSessionStatusQueries,
                partyTrainingClassSession);
    }
    
    public PartyTrainingClassSessionStatus getPartyTrainingClassSessionStatus(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionStatus(partyTrainingClassSession, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSessionStatus getPartyTrainingClassSessionStatusForUpdate(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionStatus(partyTrainingClassSession, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionSectionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionstatuses " +
                "WHERE ptrnclssessst_lastpartytrainingclasssessionsectionid = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionSectionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<PartyTrainingClassSessionStatus> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionSection(PartyTrainingClassSessionSection lastPartyTrainingClassSessionSection,
            EntityPermission entityPermission) {
        return PartyTrainingClassSessionStatusFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionSectionQueries,
                lastPartyTrainingClassSessionSection);
    }
    
    public List<PartyTrainingClassSessionStatus> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionSectionForUpdate(PartyTrainingClassSessionSection lastPartyTrainingClassSessionSection) {
        return getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionSection(lastPartyTrainingClassSessionSection, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionPageQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionstatuses " +
                "WHERE ptrnclssessst_lastpartytrainingclasssessionpageid = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionPageQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<PartyTrainingClassSessionStatus> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionPage(PartyTrainingClassSessionPage lastPartyTrainingClassSessionPage,
            EntityPermission entityPermission) {
        return PartyTrainingClassSessionStatusFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionPageQueries,
                lastPartyTrainingClassSessionPage);
    }
    
    public List<PartyTrainingClassSessionStatus> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionPageForUpdate(PartyTrainingClassSessionPage lastPartyTrainingClassSessionPage) {
        return getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionPage(lastPartyTrainingClassSessionPage, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionQuestionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionstatuses " +
                "WHERE ptrnclssessst_lastpartytrainingclasssessionquestionid = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionQuestionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<PartyTrainingClassSessionStatus> getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionQuestion(PartyTrainingClassSessionQuestion lastPartyTrainingClassSessionQuestion,
            EntityPermission entityPermission) {
        return PartyTrainingClassSessionStatusFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassSessionStatusesByLastPartyTrainingClassSessionQuestionQueries,
                lastPartyTrainingClassSessionQuestion);
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
        var partyTrainingClassSessionSection = PartyTrainingClassSessionSectionFactory.getInstance().create(partyTrainingClassSession,
                partyTrainingClassSessionSectionSequence, trainingClassSection, readingStartTime, readingEndTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(partyTrainingClassSession.getPrimaryKey(), EventTypes.MODIFY, partyTrainingClassSessionSection.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyTrainingClassSessionSection;
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionSectionQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionsections " +
                "WHERE ptrnclss_trnclsp_trainingclasssectionid = ? AND ptrnclss_partytrainingclasssessionsectionsequence = ? AND ptrnclss_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionsections " +
                "WHERE ptrnclss_trnclsp_trainingclasssectionid = ? AND ptrnclss_partytrainingclasssessionsectionsequence = ? AND ptrnclss_thrutime = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionSectionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public PartyTrainingClassSessionSection getPartyTrainingClassSessionSection(PartyTrainingClassSession partyTrainingClassSession,
            Integer partyTrainingClassSessionSectionSequence, EntityPermission entityPermission) {
        return PartyTrainingClassSessionSectionFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTrainingClassSessionSectionQueries,
                partyTrainingClassSession, partyTrainingClassSessionSectionSequence, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionSectionsByPartyTrainingClassSessionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionsections " +
                "WHERE ptrnclss_ptrnclssess_partytrainingclasssessionid = ? AND ptrnclss_thrutime = ? " +
                "ORDER BY ptrnclss_partytrainingclasssessionsectionsequence " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionsections " +
                "WHERE ptrnclss_ptrnclssess_partytrainingclasssessionid = ? AND ptrnclss_thrutime = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionSectionsByPartyTrainingClassSessionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<PartyTrainingClassSessionSection> getPartyTrainingClassSessionSectionsByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession,
            EntityPermission entityPermission) {
        return PartyTrainingClassSessionSectionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassSessionSectionsByPartyTrainingClassSessionQueries,
                partyTrainingClassSession, Session.MAX_TIME);
    }
    
    public List<PartyTrainingClassSessionSection> getPartyTrainingClassSessionSectionsByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionSectionsByPartyTrainingClassSession(partyTrainingClassSession, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSessionSection> getPartyTrainingClassSessionSectionsByPartyTrainingClassSessionForUpdate(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionSectionsByPartyTrainingClassSession(partyTrainingClassSession, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionSectionsByTrainingClassSectionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionsections, trainingclasssections, trainingclasssectiondetails, trainingclasssections, trainingclasssectiondetails " +
                "WHERE ptrnclss_trnclsp_trainingclasssectionid = ? AND ptrnclss_thrutime = ? " +
                "AND ptrnclss_trnclsp_trainingclasssectionid = trnclsp_trainingclasssectionid AND trnclsp_lastdetailid = trnclspdt_trainingclasssectiondetailid " +
                "AND trnclspdt_trnclss_trainingclasssectionid = trnclss_trainingclasssectionid AND trnclss_lastdetailid = trnclssdt_trainingclasssectiondetailid " +
                "ORDER BY ptrnclss_partytrainingclasssessionsectionsequence, trnclspdt_sortorder, trnclspdt_trainingclasssectionname, trnclssdt_sortorder, trnclssdt_trainingclasssectionname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionsections " +
                "WHERE ptrnclss_trnclsp_trainingclasssectionid = ? AND ptrnclss_thrutime = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionSectionsByTrainingClassSectionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<PartyTrainingClassSessionSection> getPartyTrainingClassSessionSectionsByTrainingClassSection(TrainingClassSection trainingClassSection,
            EntityPermission entityPermission) {
        return PartyTrainingClassSessionSectionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassSessionSectionsByTrainingClassSectionQueries,
                trainingClassSection, Session.MAX_TIME);
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
            var partyTrainingClassSessionSection = PartyTrainingClassSessionSectionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    partyTrainingClassSessionSectionValue.getPrimaryKey());
            
            partyTrainingClassSessionSection.setThruTime(session.START_TIME_LONG);
            partyTrainingClassSessionSection.store();

            var partyTrainingClassSessionPK = partyTrainingClassSessionSection.getPartyTrainingClassSessionPK(); // Not updated
            var partyTrainingClassSessionSectionSequence = partyTrainingClassSessionSection.getPartyTrainingClassSessionSectionSequence(); // Not updated
            var trainingClassSectionPK = partyTrainingClassSessionSectionValue.getTrainingClassSectionPK();
            var readingStartTime = partyTrainingClassSessionSectionValue.getReadingStartTime();
            var readingEndTime = partyTrainingClassSessionSectionValue.getReadingEndTime();
            
            partyTrainingClassSessionSection = PartyTrainingClassSessionSectionFactory.getInstance().create(partyTrainingClassSessionPK,
                    partyTrainingClassSessionSectionSequence, trainingClassSectionPK, readingStartTime, readingEndTime, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(partyTrainingClassSessionPK, EventTypes.MODIFY, partyTrainingClassSessionSection.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyTrainingClassSessionSection(PartyTrainingClassSessionSection partyTrainingClassSessionSection, BasePK deletedBy) {
        clearPartyTrainingClassSessionSectionFromPartyTrainingClassSessionStatuses(partyTrainingClassSessionSection);
        
        partyTrainingClassSessionSection.setThruTime(session.START_TIME_LONG);
        
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
        var partyTrainingClassSessionPage = PartyTrainingClassSessionPageFactory.getInstance().create(partyTrainingClassSession,
                partyTrainingClassSessionPageSequence, trainingClassPage, readingStartTime, readingEndTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(partyTrainingClassSession.getPrimaryKey(), EventTypes.MODIFY, partyTrainingClassSessionPage.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyTrainingClassSessionPage;
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionPageQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionpages " +
                "WHERE ptrnclsp_trnclsp_trainingclasspageid = ? AND ptrnclsp_partytrainingclasssessionpagesequence = ? AND ptrnclsp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionpages " +
                "WHERE ptrnclsp_trnclsp_trainingclasspageid = ? AND ptrnclsp_partytrainingclasssessionpagesequence = ? AND ptrnclsp_thrutime = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionPageQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public PartyTrainingClassSessionPage getPartyTrainingClassSessionPage(PartyTrainingClassSession partyTrainingClassSession,
            Integer partyTrainingClassSessionPageSequence, EntityPermission entityPermission) {
        return PartyTrainingClassSessionPageFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTrainingClassSessionPageQueries,
                partyTrainingClassSession, partyTrainingClassSessionPageSequence, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionPagesByPartyTrainingClassSessionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionpages " +
                "WHERE ptrnclsp_ptrnclssess_partytrainingclasssessionid = ? AND ptrnclsp_thrutime = ? " +
                "ORDER BY ptrnclsp_partytrainingclasssessionpagesequence " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionpages " +
                "WHERE ptrnclsp_ptrnclssess_partytrainingclasssessionid = ? AND ptrnclsp_thrutime = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionPagesByPartyTrainingClassSessionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<PartyTrainingClassSessionPage> getPartyTrainingClassSessionPagesByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession,
            EntityPermission entityPermission) {
        return PartyTrainingClassSessionPageFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassSessionPagesByPartyTrainingClassSessionQueries,
                partyTrainingClassSession, Session.MAX_TIME);
    }
    
    public List<PartyTrainingClassSessionPage> getPartyTrainingClassSessionPagesByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionPagesByPartyTrainingClassSession(partyTrainingClassSession, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSessionPage> getPartyTrainingClassSessionPagesByPartyTrainingClassSessionForUpdate(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionPagesByPartyTrainingClassSession(partyTrainingClassSession, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionPagesByTrainingClassPageQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionpages, trainingclasspages, trainingclasspagedetails, trainingclasssections, trainingclasssectiondetails " +
                "WHERE ptrnclsp_trnclsp_trainingclasspageid = ? AND ptrnclsp_thrutime = ? " +
                "AND ptrnclsp_trnclsp_trainingclasspageid = trnclsp_trainingclasspageid AND trnclsp_lastdetailid = trnclspdt_trainingclasspagedetailid " +
                "AND trnclspdt_trnclss_trainingclasssectionid = trnclss_trainingclasssectionid AND trnclss_lastdetailid = trnclssdt_trainingclasssectiondetailid " +
                "ORDER BY ptrnclsp_partytrainingclasssessionpagesequence, trnclspdt_sortorder, trnclspdt_trainingclasspagename, trnclssdt_sortorder, trnclssdt_trainingclasssectionname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionpages " +
                "WHERE ptrnclsp_trnclsp_trainingclasspageid = ? AND ptrnclsp_thrutime = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionPagesByTrainingClassPageQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<PartyTrainingClassSessionPage> getPartyTrainingClassSessionPagesByTrainingClassPage(TrainingClassPage trainingClassPage,
            EntityPermission entityPermission) {
        return PartyTrainingClassSessionPageFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassSessionPagesByTrainingClassPageQueries,
                trainingClassPage, Session.MAX_TIME);
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
            var partyTrainingClassSessionPage = PartyTrainingClassSessionPageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    partyTrainingClassSessionPageValue.getPrimaryKey());
            
            partyTrainingClassSessionPage.setThruTime(session.START_TIME_LONG);
            partyTrainingClassSessionPage.store();

            var partyTrainingClassSessionPK = partyTrainingClassSessionPage.getPartyTrainingClassSessionPK(); // Not updated
            var partyTrainingClassSessionPageSequence = partyTrainingClassSessionPage.getPartyTrainingClassSessionPageSequence(); // Not updated
            var trainingClassPagePK = partyTrainingClassSessionPageValue.getTrainingClassPagePK();
            var readingStartTime = partyTrainingClassSessionPageValue.getReadingStartTime();
            var readingEndTime = partyTrainingClassSessionPageValue.getReadingEndTime();
            
            partyTrainingClassSessionPage = PartyTrainingClassSessionPageFactory.getInstance().create(partyTrainingClassSessionPK,
                    partyTrainingClassSessionPageSequence, trainingClassPagePK, readingStartTime, readingEndTime, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(partyTrainingClassSessionPK, EventTypes.MODIFY, partyTrainingClassSessionPage.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyTrainingClassSessionPage(PartyTrainingClassSessionPage partyTrainingClassSessionPage, BasePK deletedBy) {
        clearPartyTrainingClassSessionPageFromPartyTrainingClassSessionStatuses(partyTrainingClassSessionPage);
        
        partyTrainingClassSessionPage.setThruTime(session.START_TIME_LONG);
        
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
    
    public PartyTrainingClassSessionQuestion createPartyTrainingClassSessionQuestion(PartyTrainingClassSession partyTrainingClassSession,
            TrainingClassQuestion trainingClassQuestion, Integer sortOrder, BasePK createdBy) {
        var partyTrainingClassSessionQuestion = PartyTrainingClassSessionQuestionFactory.getInstance().create();
        var partyTrainingClassSessionQuestionDetail = PartyTrainingClassSessionQuestionDetailFactory.getInstance().create(partyTrainingClassSessionQuestion,
                partyTrainingClassSession, trainingClassQuestion, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        partyTrainingClassSessionQuestion = PartyTrainingClassSessionQuestionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyTrainingClassSessionQuestion.getPrimaryKey());
        partyTrainingClassSessionQuestion.setActiveDetail(partyTrainingClassSessionQuestionDetail);
        partyTrainingClassSessionQuestion.setLastDetail(partyTrainingClassSessionQuestionDetail);
        partyTrainingClassSessionQuestion.store();
        
        sendEvent(partyTrainingClassSessionQuestion.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        createPartyTrainingClassSessionQuestionStatus(partyTrainingClassSessionQuestion);

        return partyTrainingClassSessionQuestion;
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionQuestionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionquestions, partytrainingclasssessionquestiondetails " +
                "WHERE ptrnclsqus_activedetailid = ptrnclsqusdt_partytrainingclasssessionquestiondetailid " +
                "AND ptrnclsqusdt_ptrnclssess_partytrainingclasssessionid = ? AND ptrnclsqusdt_trnclsqus_trainingclassquestionid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionquestions, partytrainingclasssessionquestiondetails " +
                "WHERE ptrnclsqus_activedetailid = ptrnclsqusdt_partytrainingclasssessionquestiondetailid " +
                "AND ptrnclsqusdt_ptrnclssess_partytrainingclasssessionid = ? AND ptrnclsqusdt_trnclsqus_trainingclassquestionid = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionQuestionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public PartyTrainingClassSessionQuestion getPartyTrainingClassSessionQuestion(PartyTrainingClassSession partyTrainingClassSession,
            TrainingClassQuestion trainingClassQuestion, EntityPermission entityPermission) {
        return PartyTrainingClassSessionQuestionFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTrainingClassSessionQuestionQueries,
                partyTrainingClassSession, trainingClassQuestion);
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
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionQuestionsByPartyTrainingClassSessionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partytrainingclasssessionquestions, partytrainingclasssessionquestiondetails, trainingclassquestions, trainingclassquestiondetails, trainingclasssections, trainingclasssectiondetails "
                + "WHERE ptrnclsqus_activedetailid = ptrnclsqusdt_partytrainingclasssessionquestiondetailid "
                + "AND ptrnclsqusdt_trnclsqus_trainingclassquestionid = trnclsqus_trainingclassquestionid AND trnclsqus_lastdetailid = trnclsqusdt_trainingclassquestiondetailid "
                + "AND trnclsqusdt_trnclss_trainingclasssectionid = trnclss_trainingclasssectionid AND trnclss_lastdetailid = trnclssdt_trainingclasssectiondetailid "
                + "AND ptrnclsqusdt_ptrnclssess_partytrainingclasssessionid = ? "
                + "ORDER BY ptrnclsqusdt_sortorder, trnclsqusdt_sortorder, trnclsqusdt_trainingclassquestionname, trnclssdt_sortorder, trnclssdt_trainingclasssectionname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partytrainingclasssessionquestions, partytrainingclasssessionquestiondetails "
                + "WHERE ptrnclsqus_activedetailid = ptrnclsqusdt_partytrainingclasssessionquestiondetailid "
                + "AND ptrnclsqusdt_ptrnclssess_partytrainingclasssessionid = ? "
                + "FOR UPDATE");
        getPartyTrainingClassSessionQuestionsByPartyTrainingClassSessionQueries = Collections.unmodifiableMap(queryMap);
    }
    
   private List<PartyTrainingClassSessionQuestion> getPartyTrainingClassSessionQuestionsByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession,
           EntityPermission entityPermission) {
        return PartyTrainingClassSessionQuestionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassSessionQuestionsByPartyTrainingClassSessionQueries,
                partyTrainingClassSession);
    }
    
    public List<PartyTrainingClassSessionQuestion> getPartyTrainingClassSessionQuestionsByPartyTrainingClassSession(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionQuestionsByPartyTrainingClassSession(partyTrainingClassSession, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSessionQuestion> getPartyTrainingClassSessionQuestionsByPartyTrainingClassSessionForUpdate(PartyTrainingClassSession partyTrainingClassSession) {
        return getPartyTrainingClassSessionQuestionsByPartyTrainingClassSession(partyTrainingClassSession, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionQuestionsByTrainingClassQuestionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partytrainingclasssessionquestions, partytrainingclasssessions, partytrainingclasssessiondetails, partytrainingclasses, partytrainingclassdetails "
                + "WHERE ptrnclsqus_activedetailid = ptrnclsqusdt_partytrainingclasssessionquestiondetailid "
                + "AND ptrnclsqusdt_ptrnclssess_partytrainingclasssessionid = ptrnclssess_partytrainingclasssessionid AND ptrnclssess_lastdetailid = ptrnclssessdt_partytrainingclasssessiondetailid "
                + "AND ptrnclssessdt_ptrncls_partytrainingclassid = ptrncls_partytrainingclassid AND ptrncls_lastdetailid = ptrnclsdt_partytrainingclassdetailid "
                + "ORDER BY ptrnclsqusdt_sortorder, ptrnclssessdt_partytrainingclasssessionsequence, ptrnclsdt_partytrainingclassname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partytrainingclasssessionquestions, partytrainingclasssessionquestiondetails "
                + "WHERE ptrnclsqus_activedetailid = ptrnclsqusdt_partytrainingclasssessionquestiondetailid "
                + "AND ptrnclsqusdt_trnclsqus_trainingclassquestionid = ? "
                + "FOR UPDATE");
        getPartyTrainingClassSessionQuestionsByTrainingClassQuestionQueries = Collections.unmodifiableMap(queryMap);
    }
    
   private List<PartyTrainingClassSessionQuestion> getPartyTrainingClassSessionQuestionsByTrainingClassQuestion(TrainingClassQuestion trainingClassQuestion,
           EntityPermission entityPermission) {
        return PartyTrainingClassSessionQuestionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassSessionQuestionsByTrainingClassQuestionQueries,
                trainingClassQuestion);
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
            var partyTrainingClassSessionQuestion = PartyTrainingClassSessionQuestionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTrainingClassSessionQuestionDetailValue.getPartyTrainingClassSessionQuestionPK());
            var partyTrainingClassSessionQuestionDetail = partyTrainingClassSessionQuestion.getActiveDetailForUpdate();
            
            partyTrainingClassSessionQuestionDetail.setThruTime(session.START_TIME_LONG);
            partyTrainingClassSessionQuestionDetail.store();

            var partyTrainingClassSessionQuestionPK = partyTrainingClassSessionQuestionDetail.getPartyTrainingClassSessionQuestionPK();
            var partyTrainingClassSessionPK = partyTrainingClassSessionQuestionDetail.getPartyTrainingClassSessionPK(); // Not updated
            var trainingClassQuestionPK = partyTrainingClassSessionQuestionDetail.getTrainingClassQuestionPK();
            var sortOrder = partyTrainingClassSessionQuestionDetail.getSortOrder();
            
            partyTrainingClassSessionQuestionDetail = PartyTrainingClassSessionQuestionDetailFactory.getInstance().create(partyTrainingClassSessionQuestionPK,
                    partyTrainingClassSessionPK, trainingClassQuestionPK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        partyTrainingClassSessionQuestionDetail.setThruTime(session.START_TIME_LONG);
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
    
    public PartyTrainingClassSessionQuestionStatus createPartyTrainingClassSessionQuestionStatus(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        return PartyTrainingClassSessionQuestionStatusFactory.getInstance().create(partyTrainingClassSessionQuestion, 0);
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionQuestionStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionquestionstatuses " +
                "WHERE ptrnclsqusst_ptrnclsqus_partytrainingclasssessionquestionid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionquestionstatuses " +
                "WHERE ptrnclsqusst_ptrnclsqus_partytrainingclasssessionquestionid = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionQuestionStatusQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private PartyTrainingClassSessionQuestionStatus getPartyTrainingClassSessionQuestionStatus(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion,
            EntityPermission entityPermission) {
        return PartyTrainingClassSessionQuestionStatusFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTrainingClassSessionQuestionStatusQueries,
                partyTrainingClassSessionQuestion);
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
        var partyTrainingClassSessionAnswer = PartyTrainingClassSessionAnswerFactory.getInstance().create(partyTrainingClassSessionQuestion,
            partyTrainingClassSessionAnswerSequence, trainingClassAnswer, questionStartTime, questionEndTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(partyTrainingClassSessionQuestion.getPrimaryKey(), EventTypes.MODIFY, partyTrainingClassSessionAnswer.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyTrainingClassSessionAnswer;
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionAnswerQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionanswers " +
                "WHERE ptrnclsans_ptrnclsqus_partytrainingclasssessionquestionid = ? AND ptrnclsans_partytrainingclasssessionanswersequence = ? AND ptrnclsans_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionanswers " +
                "WHERE ptrnclsans_ptrnclsqus_partytrainingclasssessionquestionid = ? AND ptrnclsans_partytrainingclasssessionanswersequence = ? AND ptrnclsans_thrutime = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionAnswerQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public PartyTrainingClassSessionAnswer getPartyTrainingClassSessionAnswer(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion,
            Integer partyTrainingClassSessionAnswerSequence, EntityPermission entityPermission) {
        return PartyTrainingClassSessionAnswerFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTrainingClassSessionAnswerQueries,
                partyTrainingClassSessionQuestion, partyTrainingClassSessionAnswerSequence, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionanswers " +
                "WHERE ptrnclsans_ptrnclsqus_partytrainingclasssessionquestionid = ? AND ptrnclsans_thrutime = ? " +
                "ORDER BY ptrnclsans_partytrainingclasssessionanswersequence " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytrainingclasssessionanswers " +
                "WHERE ptrnclsans_ptrnclsqus_partytrainingclasssessionquestionid = ? AND ptrnclsans_thrutime = ? " +
                "FOR UPDATE");
        getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<PartyTrainingClassSessionAnswer> getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestion(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion,
            EntityPermission entityPermission) {
        return PartyTrainingClassSessionAnswerFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestionQueries,
                partyTrainingClassSessionQuestion, Session.MAX_TIME);
    }
    
    public List<PartyTrainingClassSessionAnswer> getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestion(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        return getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestion(partyTrainingClassSessionQuestion, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTrainingClassSessionAnswer> getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestionForUpdate(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        return getPartyTrainingClassSessionAnswersByPartyTrainingClassSessionQuestion(partyTrainingClassSessionQuestion, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyTrainingClassSessionAnswersByTrainingClassAnswerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partytrainingclasssessionanswers, partytrainingclasssessionquestions, partytrainingclasssessionquestiondetails, partytrainingclasssessions, partytrainingclasssessiondetails, partytrainingclass, partytrainingclassdetails "
                + "WHERE ptrnclsans_trnclsans_trainingclassanswerid = ? AND ptrnclsans_thrutime = ? "
                + "AND ptrnclsans_ptrnclsqus_partytrainingclasssessionquestionid = ptrnclsqus_partytrainingclasssessionquestionid AND ptrnclsqus_lastdetailid = ptrnclsqusdt_partytrainingclasssessionquestiondetailid "
                + "AND ptrnclsqusdt_ptrnclssess_partytrainingclasssessionid = ptrnclssess_partytrainingclasssessionid AND ptrnclssess_lastdetailid = ptrnclssessdt_partytrainingclasssessiondetailid "
                + "AND ptrnclssessdt_ptrncls_partytrainingclassid = ptrncls_partytrainingclassid AND ptrncls_lastdetailid = ptrnclsdt_partytrainingclassdetailid "
                + "ORER BY ptrnclsans_partytrainingclasssessionanswersequence, ptrnclsqusdt_sortorder, ptrnclssessdt_partytrainingclasssessionsequence, ptrnclsdt_partytrainingclassname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partytrainingclasssessionanswers "
                + "WHERE ptrnclsans_trnclsans_trainingclassanswerid = ? AND ptrnclsans_thrutime = ? "
                + "FOR UPDATE");
        getPartyTrainingClassSessionAnswersByTrainingClassAnswerQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<PartyTrainingClassSessionAnswer> getPartyTrainingClassSessionAnswersByTrainingClassAnswer(TrainingClassAnswer trainingClassAnswer,
            EntityPermission entityPermission) {
        return PartyTrainingClassSessionAnswerFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTrainingClassSessionAnswersByTrainingClassAnswerQueries,
                trainingClassAnswer, Session.MAX_TIME);
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
            var partyTrainingClassSessionAnswer = PartyTrainingClassSessionAnswerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    partyTrainingClassSessionAnswerValue.getPrimaryKey());
            
            partyTrainingClassSessionAnswer.setThruTime(session.START_TIME_LONG);
            partyTrainingClassSessionAnswer.store();

            var partyTrainingClassSessionQuestionPK = partyTrainingClassSessionAnswer.getPartyTrainingClassSessionQuestionPK(); // Not updated
            var partyTrainingClassSessionAnswerSequence = partyTrainingClassSessionAnswer.getPartyTrainingClassSessionAnswerSequence(); // Not updated
            var trainingClassAnswerPK = partyTrainingClassSessionAnswerValue.getTrainingClassAnswerPK();
            var questionStartTime = partyTrainingClassSessionAnswerValue.getQuestionStartTime();
            var questionEndTime = partyTrainingClassSessionAnswerValue.getQuestionEndTime();
            
            partyTrainingClassSessionAnswer = PartyTrainingClassSessionAnswerFactory.getInstance().create(partyTrainingClassSessionQuestionPK,
                    partyTrainingClassSessionAnswerSequence, trainingClassAnswerPK, questionStartTime, questionEndTime, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(partyTrainingClassSessionQuestionPK, EventTypes.MODIFY, partyTrainingClassSessionAnswer.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyTrainingClassSessionAnswer(PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer, BasePK deletedBy) {
        partyTrainingClassSessionAnswer.setThruTime(session.START_TIME_LONG);
        
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
