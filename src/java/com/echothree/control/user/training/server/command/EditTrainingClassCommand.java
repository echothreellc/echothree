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

package com.echothree.control.user.training.server.command;

import com.echothree.control.user.training.common.edit.TrainingClassEdit;
import com.echothree.control.user.training.common.edit.TrainingEditFactory;
import com.echothree.control.user.training.common.form.EditTrainingClassForm;
import com.echothree.control.user.training.common.result.EditTrainingClassResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.control.user.training.common.spec.TrainingClassSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.workeffort.common.workeffort.TrainingConstants;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.PercentUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditTrainingClassCommand
        extends BaseAbstractEditCommand<TrainingClassSpec, TrainingClassEdit, EditTrainingClassResult, TrainingClass, TrainingClass> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClass.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EstimatedReadingTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("EstimatedReadingTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReadingTimeAllowed", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ReadingTimeAllowedUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EstimatedTestingTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("EstimatedTestingTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("TestingTimeAllowed", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("TestingTimeAllowedUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RequiredCompletionTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("RequiredCompletionTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkEffortScopeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultPercentageToPass", FieldType.FRACTIONAL_PERCENT, false, null, null),
                new FieldDefinition("OverallQuestionCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("TestingValidityTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("TestingValidityTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ExpiredRetentionTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ExpiredRetentionTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AlwaysReassignOnExpiration", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("OverviewMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Overview", FieldType.STRING, false, null, null),
                new FieldDefinition("IntroductionMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Introduction", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditTrainingClassCommand */
    public EditTrainingClassCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTrainingClassResult getResult() {
        return TrainingResultFactory.getEditTrainingClassResult();
    }

    @Override
    public TrainingClassEdit getEdit() {
        return TrainingEditFactory.getTrainingClassEdit();
    }

    @Override
    public TrainingClass getEntity(EditTrainingClassResult result) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        TrainingClass trainingClass;
        var trainingClassName = spec.getTrainingClassName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            trainingClass = trainingControl.getTrainingClassByName(trainingClassName);
        } else { // EditMode.UPDATE
            trainingClass = trainingControl.getTrainingClassByNameForUpdate(trainingClassName);
        }

        if(trainingClass != null) {
            result.setTrainingClass(trainingControl.getTrainingClassTransfer(getUserVisit(), trainingClass));
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return trainingClass;
    }

    @Override
    public TrainingClass getLockEntity(TrainingClass trainingClass) {
        return trainingClass;
    }

    @Override
    public void fillInResult(EditTrainingClassResult result, TrainingClass trainingClass) {
        var trainingControl = Session.getModelController(TrainingControl.class);

        result.setTrainingClass(trainingControl.getTrainingClassTransfer(getUserVisit(), trainingClass));
    }

    MimeType overviewMimeType;
    MimeType introductionMimeType;
    
    @Override
    public void doLock(TrainingClassEdit edit, TrainingClass trainingClass) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();
        var trainingClassTranslation = trainingControl.getTrainingClassTranslation(trainingClass, getPreferredLanguage());
        var trainingClassDetail = trainingClass.getLastDetail();
        var workEffortScope = trainingClassDetail.getWorkEffortScope();
        var overallQuestionCount = trainingClassDetail.getOverallQuestionCount();
        UnitOfMeasureTypeLogic.StringUnitOfMeasure stringUnitOfMeasure;

        edit.setTrainingClassName(trainingClassDetail.getTrainingClassName());
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_TIME, trainingClassDetail.getEstimatedReadingTime());
        edit.setEstimatedReadingTimeUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setEstimatedReadingTime(stringUnitOfMeasure.getValue());
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_TIME, trainingClassDetail.getReadingTimeAllowed());
        edit.setReadingTimeAllowedUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setReadingTimeAllowed(stringUnitOfMeasure.getValue());
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_TIME, trainingClassDetail.getEstimatedTestingTime());
        edit.setEstimatedTestingTimeUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setEstimatedTestingTime(stringUnitOfMeasure.getValue());
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_TIME, trainingClassDetail.getTestingTimeAllowed());
        edit.setTestingTimeAllowedUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setTestingTimeAllowed(stringUnitOfMeasure.getValue());
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_TIME, trainingClassDetail.getRequiredCompletionTime());
        edit.setRequiredCompletionTimeUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setRequiredCompletionTime(stringUnitOfMeasure.getValue());
        edit.setWorkEffortScopeName(workEffortScope == null ? null : workEffortScope.getLastDetail().getWorkEffortScopeName());
        edit.setDefaultPercentageToPass(PercentUtils.getInstance().formatFractionalPercent(trainingClassDetail.getDefaultPercentageToPass()));
        edit.setOverallQuestionCount(overallQuestionCount == null? null: overallQuestionCount.toString());
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_TIME, trainingClassDetail.getTestingValidityTime());
        edit.setTestingValidityTimeUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setTestingValidityTime(stringUnitOfMeasure.getValue());
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_TIME, trainingClassDetail.getExpiredRetentionTime());
        edit.setExpiredRetentionTimeUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setExpiredRetentionTime(stringUnitOfMeasure.getValue());
        edit.setAlwaysReassignOnExpiration(trainingClassDetail.getAlwaysReassignOnExpiration().toString());
        edit.setIsDefault(trainingClassDetail.getIsDefault().toString());
        edit.setSortOrder(trainingClassDetail.getSortOrder().toString());

        if(trainingClassTranslation != null) {
            overviewMimeType = trainingClassTranslation.getOverviewMimeType();
            introductionMimeType = trainingClassTranslation.getIntroductionMimeType();

            edit.setDescription(trainingClassTranslation.getDescription());
            edit.setOverviewMimeTypeName(overviewMimeType == null? null: overviewMimeType.getLastDetail().getMimeTypeName());
            edit.setOverview(trainingClassTranslation.getOverview());
            edit.setIntroductionMimeTypeName(introductionMimeType == null? null: introductionMimeType.getLastDetail().getMimeTypeName());
            edit.setIntroduction(trainingClassTranslation.getIntroduction());
        }
    }

    Long estimatedReadingTime;
    Long readingTimeAllowed;
    Long estimatedTestingTime;
    Long testingTimeAllowed;
    Long requiredCompletionTime;
    WorkEffortScope workEffortScope;
    Long testingValidityTime;
    Long expiredRetentionTime;
    
    @Override
    public void canUpdate(TrainingClass trainingClass) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassName = edit.getTrainingClassName();
        var duplicateTrainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(duplicateTrainingClass != null && !trainingClass.equals(duplicateTrainingClass)) {
            addExecutionError(ExecutionErrors.DuplicateTrainingClassName.name(), trainingClassName);
        } else {
            var mimeTypeLogic = MimeTypeLogic.getInstance();
            var overviewMimeTypeName = edit.getOverviewMimeTypeName();
            var overview = edit.getOverview();

            overviewMimeType = mimeTypeLogic.checkMimeType(this, overviewMimeTypeName, overview, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredOverviewMimeTypeName.name(), ExecutionErrors.MissingRequiredOverview.name(),
                    ExecutionErrors.UnknownOverviewMimeTypeName.name(), ExecutionErrors.UnknownOverviewMimeTypeUsage.name());

            if(!hasExecutionErrors()) {
                var introductionMimeTypeName = edit.getIntroductionMimeTypeName();
                var introduction = edit.getIntroduction();

                introductionMimeType = mimeTypeLogic.checkMimeType(this, introductionMimeTypeName, introduction, MimeTypeUsageTypes.TEXT.name(),
                        ExecutionErrors.MissingRequiredIntroductionMimeTypeName.name(), ExecutionErrors.MissingRequiredIntroduction.name(),
                        ExecutionErrors.UnknownIntroductionMimeTypeName.name(), ExecutionErrors.UnknownIntroductionMimeTypeUsage.name());

                if(!hasExecutionErrors()) {
                    var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();

                    estimatedReadingTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                            edit.getEstimatedReadingTime(), edit.getEstimatedReadingTimeUnitOfMeasureTypeName(),
                            null, ExecutionErrors.MissingRequiredEstimatedReadingTime.name(), null, ExecutionErrors.MissingRequiredEstimatedReadingTimeUnitOfMeasureTypeName.name(),
                            null, ExecutionErrors.UnknownEstimatedReadingTimeUnitOfMeasureTypeName.name());

                    if(!hasExecutionErrors()) {
                        readingTimeAllowed = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                edit.getReadingTimeAllowed(), edit.getReadingTimeAllowedUnitOfMeasureTypeName(),
                                null, ExecutionErrors.MissingRequiredReadingTimeAllowed.name(), null, ExecutionErrors.MissingRequiredReadingTimeAllowedUnitOfMeasureTypeName.name(),
                                null, ExecutionErrors.UnknownReadingTimeAllowedUnitOfMeasureTypeName.name());

                        if(!hasExecutionErrors()) {
                            estimatedTestingTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                    edit.getEstimatedTestingTime(), edit.getEstimatedTestingTimeUnitOfMeasureTypeName(),
                                    null, ExecutionErrors.MissingRequiredEstimatedTestingTime.name(), null, ExecutionErrors.MissingRequiredEstimatedTestingTimeUnitOfMeasureTypeName.name(),
                                    null, ExecutionErrors.UnknownEstimatedTestingTimeUnitOfMeasureTypeName.name());

                            if(!hasExecutionErrors()) {
                                testingTimeAllowed = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                        edit.getTestingTimeAllowed(), edit.getTestingTimeAllowedUnitOfMeasureTypeName(),
                                        null, ExecutionErrors.MissingRequiredTestingTimeAllowed.name(), null, ExecutionErrors.MissingRequiredTestingTimeAllowedUnitOfMeasureTypeName.name(),
                                        null, ExecutionErrors.UnknownTestingTimeAllowedUnitOfMeasureTypeName.name());

                                if(!hasExecutionErrors()) {
                                    requiredCompletionTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                            edit.getRequiredCompletionTime(), edit.getRequiredCompletionTimeUnitOfMeasureTypeName(),
                                            null, ExecutionErrors.MissingRequiredRequiredCompletionTime.name(), null, ExecutionErrors.MissingRequiredRequiredCompletionTimeUnitOfMeasureTypeName.name(),
                                            null, ExecutionErrors.UnknownRequiredCompletionTimeUnitOfMeasureTypeName.name());

                                    if(!hasExecutionErrors()) {
                                        var workEffortScopeName = edit.getWorkEffortScopeName();

                                        if(workEffortScopeName != null) {
                                            var workEffortControl = Session.getModelController(WorkEffortControl.class);
                                            var workEffortType = workEffortControl.getWorkEffortTypeByName(TrainingConstants.WorkEffortType_TRAINING);

                                            if(workEffortType != null) {
                                                workEffortScope = workEffortControl.getWorkEffortScopeByName(workEffortType, workEffortScopeName);

                                                if(workEffortScope == null) {
                                                    addExecutionError(ExecutionErrors.UnknownWorkEffortScopeName.name(), TrainingConstants.WorkEffortType_TRAINING,
                                                            workEffortScopeName);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), TrainingConstants.WorkEffortType_TRAINING);
                                            }
                                        }

                                        if(!hasExecutionErrors()) {
                                            testingValidityTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                                    edit.getTestingValidityTime(), edit.getTestingValidityTimeUnitOfMeasureTypeName(),
                                                    null, ExecutionErrors.MissingRequiredTestingValidityTime.name(), null, ExecutionErrors.MissingRequiredTestingValidityTimeUnitOfMeasureTypeName.name(),
                                                    null, ExecutionErrors.UnknownTestingValidityTimeUnitOfMeasureTypeName.name());

                                            if(!hasExecutionErrors()) {
                                                expiredRetentionTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                                        edit.getExpiredRetentionTime(), edit.getExpiredRetentionTimeUnitOfMeasureTypeName(),
                                                        null, ExecutionErrors.MissingRequiredExpiredRetentionTime.name(), null, ExecutionErrors.MissingRequiredExpiredRetentionTimeUnitOfMeasureTypeName.name(),
                                                        null, ExecutionErrors.UnknownExpiredRetentionTimeUnitOfMeasureTypeName.name());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void doUpdate(TrainingClass trainingClass) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var partyPK = getPartyPK();
        var trainingClassDetailValue = trainingControl.getTrainingClassDetailValueForUpdate(trainingClass);
        var trainingClassTranslation = trainingControl.getTrainingClassTranslationForUpdate(trainingClass, getPreferredLanguage());
        var defaultPercentageToPass = edit.getDefaultPercentageToPass();
        var overallQuestionCount = edit.getOverallQuestionCount();
        var description = edit.getDescription();
        var overview = edit.getOverview();
        var introduction = edit.getIntroduction();

        trainingClassDetailValue.setTrainingClassName(edit.getTrainingClassName());
        trainingClassDetailValue.setEstimatedReadingTime(estimatedReadingTime);
        trainingClassDetailValue.setReadingTimeAllowed(readingTimeAllowed);
        trainingClassDetailValue.setEstimatedTestingTime(estimatedTestingTime);
        trainingClassDetailValue.setTestingTimeAllowed(testingTimeAllowed);
        trainingClassDetailValue.setRequiredCompletionTime(requiredCompletionTime);
        trainingClassDetailValue.setWorkEffortScopePK(workEffortScope == null ? null : workEffortScope.getPrimaryKey());
        trainingClassDetailValue.setDefaultPercentageToPass(defaultPercentageToPass == null? null: Integer.valueOf(defaultPercentageToPass));
        trainingClassDetailValue.setOverallQuestionCount(overallQuestionCount == null? null: Integer.valueOf(overallQuestionCount));
        trainingClassDetailValue.setTestingValidityTime(testingValidityTime);
        trainingClassDetailValue.setExpiredRetentionTime(expiredRetentionTime);
        trainingClassDetailValue.setAlwaysReassignOnExpiration(Boolean.valueOf(edit.getAlwaysReassignOnExpiration()));
        trainingClassDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        trainingClassDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        trainingControl.updateTrainingClassFromValue(trainingClassDetailValue, partyPK);

        if(trainingClassTranslation == null && (description != null || overview != null || introduction != null)) {
            trainingControl.createTrainingClassTranslation(trainingClass, getPreferredLanguage(), description, overviewMimeType, overview, introductionMimeType,
                    introduction, partyPK);
        } else if(trainingClassTranslation != null && (description == null && overview == null && introduction == null)) {
            trainingControl.deleteTrainingClassTranslation(trainingClassTranslation, partyPK);
        } else if(trainingClassTranslation != null && (description != null || overview != null || introduction != null)) {
            var trainingClassTranslationValue = trainingControl.getTrainingClassTranslationValue(trainingClassTranslation);

            trainingClassTranslationValue.setDescription(description);
            trainingClassTranslationValue.setOverviewMimeTypePK(overviewMimeType == null? null: overviewMimeType.getPrimaryKey());
            trainingClassTranslationValue.setOverview(overview);
            trainingClassTranslationValue.setIntroductionMimeTypePK(introductionMimeType == null? null: introductionMimeType.getPrimaryKey());
            trainingClassTranslationValue.setIntroduction(introduction);
            trainingControl.updateTrainingClassTranslationFromValue(trainingClassTranslationValue, partyPK);
        }
    }

}
