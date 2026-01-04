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

package com.echothree.ui.web.main.action.humanresources.trainingclass;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.edit.TrainingClassEdit;
import com.echothree.control.user.training.common.form.EditTrainingClassForm;
import com.echothree.control.user.training.common.result.EditTrainingClassResult;
import com.echothree.control.user.training.common.spec.TrainingClassSpec;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/TrainingClass/Edit",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClass/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclass/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, TrainingClassSpec, TrainingClassEdit, EditTrainingClassForm, EditTrainingClassResult> {
    
    @Override
    protected TrainingClassSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = TrainingUtil.getHome().getTrainingClassSpec();
        var originalTrainingClassName = request.getParameter(ParameterConstants.ORIGINAL_TRAINING_CLASS_NAME);

        if(originalTrainingClassName == null) {
            originalTrainingClassName = actionForm.getOriginalTrainingClassName();
        }

        spec.setTrainingClassName(originalTrainingClassName);
        
        return spec;
    }
    
    @Override
    protected TrainingClassEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = TrainingUtil.getHome().getTrainingClassEdit();

        edit.setTrainingClassName(actionForm.getTrainingClassName());
        edit.setEstimatedReadingTime(actionForm.getEstimatedReadingTime());
        edit.setEstimatedReadingTimeUnitOfMeasureTypeName(actionForm.getEstimatedReadingTimeUnitOfMeasureTypeChoice());
        edit.setReadingTimeAllowed(actionForm.getReadingTimeAllowed());
        edit.setReadingTimeAllowedUnitOfMeasureTypeName(actionForm.getReadingTimeAllowedUnitOfMeasureTypeChoice());
        edit.setEstimatedTestingTime(actionForm.getEstimatedTestingTime());
        edit.setEstimatedTestingTimeUnitOfMeasureTypeName(actionForm.getEstimatedTestingTimeUnitOfMeasureTypeChoice());
        edit.setTestingTimeAllowed(actionForm.getTestingTimeAllowed());
        edit.setTestingTimeAllowedUnitOfMeasureTypeName(actionForm.getTestingTimeAllowedUnitOfMeasureTypeChoice());
        edit.setRequiredCompletionTime(actionForm.getRequiredCompletionTime());
        edit.setRequiredCompletionTimeUnitOfMeasureTypeName(actionForm.getRequiredCompletionTimeUnitOfMeasureTypeChoice());
        edit.setWorkEffortScopeName(actionForm.getWorkEffortScopeChoice());
        edit.setDefaultPercentageToPass(actionForm.getDefaultPercentageToPass());
        edit.setOverallQuestionCount(actionForm.getOverallQuestionCount());
        edit.setTestingValidityTime(actionForm.getTestingValidityTime());
        edit.setTestingValidityTimeUnitOfMeasureTypeName(actionForm.getTestingValidityTimeUnitOfMeasureTypeChoice());
        edit.setExpiredRetentionTime(actionForm.getExpiredRetentionTime());
        edit.setExpiredRetentionTimeUnitOfMeasureTypeName(actionForm.getExpiredRetentionTimeUnitOfMeasureTypeChoice());
        edit.setAlwaysReassignOnExpiration(actionForm.getAlwaysReassignOnExpiration().toString());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());
        edit.setOverviewMimeTypeName(actionForm.getOverviewMimeTypeChoice());
        edit.setOverview(actionForm.getOverview());
        edit.setIntroductionMimeTypeName(actionForm.getIntroductionMimeTypeChoice());
        edit.setIntroduction(actionForm.getIntroduction());

        return edit;
    }
    
    @Override
    protected EditTrainingClassForm getForm()
            throws NamingException {
        return TrainingUtil.getHome().getEditTrainingClassForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditTrainingClassResult result, TrainingClassSpec spec, TrainingClassEdit edit) {
        actionForm.setOriginalTrainingClassName(spec.getTrainingClassName());
        actionForm.setTrainingClassName(edit.getTrainingClassName());
        actionForm.setEstimatedReadingTime(edit.getEstimatedReadingTime());
        actionForm.setEstimatedReadingTimeUnitOfMeasureTypeChoice(edit.getEstimatedReadingTimeUnitOfMeasureTypeName());
        actionForm.setReadingTimeAllowed(edit.getReadingTimeAllowed());
        actionForm.setReadingTimeAllowedUnitOfMeasureTypeChoice(edit.getReadingTimeAllowedUnitOfMeasureTypeName());
        actionForm.setEstimatedTestingTime(edit.getEstimatedTestingTime());
        actionForm.setEstimatedTestingTimeUnitOfMeasureTypeChoice(edit.getEstimatedTestingTimeUnitOfMeasureTypeName());
        actionForm.setTestingTimeAllowed(edit.getTestingTimeAllowed());
        actionForm.setTestingTimeAllowedUnitOfMeasureTypeChoice(edit.getTestingTimeAllowedUnitOfMeasureTypeName());
        actionForm.setRequiredCompletionTime(edit.getRequiredCompletionTime());
        actionForm.setRequiredCompletionTimeUnitOfMeasureTypeChoice(edit.getRequiredCompletionTimeUnitOfMeasureTypeName());
        actionForm.setWorkEffortScopeChoice(edit.getWorkEffortScopeName());
        actionForm.setDefaultPercentageToPass(edit.getDefaultPercentageToPass());
        actionForm.setOverallQuestionCount(edit.getOverallQuestionCount());
        actionForm.setTestingValidityTime(edit.getTestingValidityTime());
        actionForm.setTestingValidityTimeUnitOfMeasureTypeChoice(edit.getTestingValidityTimeUnitOfMeasureTypeName());
        actionForm.setExpiredRetentionTime(edit.getExpiredRetentionTime());
        actionForm.setExpiredRetentionTimeUnitOfMeasureTypeChoice(edit.getExpiredRetentionTimeUnitOfMeasureTypeName());
        actionForm.setAlwaysReassignOnExpiration(Boolean.valueOf(edit.getAlwaysReassignOnExpiration()));
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
        actionForm.setOverviewMimeTypeChoice(edit.getOverviewMimeTypeName());
        actionForm.setOverview(edit.getOverview());
        actionForm.setIntroductionMimeTypeChoice(edit.getIntroductionMimeTypeName());
        actionForm.setIntroduction(edit.getIntroduction());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTrainingClassForm commandForm)
            throws Exception {
        return TrainingUtil.getHome().editTrainingClass(getUserVisitPK(request), commandForm);
    }
    
}
