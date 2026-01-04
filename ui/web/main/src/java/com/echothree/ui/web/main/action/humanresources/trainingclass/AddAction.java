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
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/TrainingClass/Add",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClass/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclass/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupDefaults(AddActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }
    
    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = TrainingUtil.getHome().getCreateTrainingClassForm();

        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        commandForm.setEstimatedReadingTime(actionForm.getEstimatedReadingTime());
        commandForm.setEstimatedReadingTimeUnitOfMeasureTypeName(actionForm.getEstimatedReadingTimeUnitOfMeasureTypeChoice());
        commandForm.setReadingTimeAllowed(actionForm.getReadingTimeAllowed());
        commandForm.setReadingTimeAllowedUnitOfMeasureTypeName(actionForm.getReadingTimeAllowedUnitOfMeasureTypeChoice());
        commandForm.setEstimatedTestingTime(actionForm.getEstimatedTestingTime());
        commandForm.setEstimatedTestingTimeUnitOfMeasureTypeName(actionForm.getEstimatedTestingTimeUnitOfMeasureTypeChoice());
        commandForm.setTestingTimeAllowed(actionForm.getTestingTimeAllowed());
        commandForm.setTestingTimeAllowedUnitOfMeasureTypeName(actionForm.getTestingTimeAllowedUnitOfMeasureTypeChoice());
        commandForm.setRequiredCompletionTime(actionForm.getRequiredCompletionTime());
        commandForm.setRequiredCompletionTimeUnitOfMeasureTypeName(actionForm.getRequiredCompletionTimeUnitOfMeasureTypeChoice());
        commandForm.setWorkEffortScopeName(actionForm.getWorkEffortScopeChoice());
        commandForm.setDefaultPercentageToPass(actionForm.getDefaultPercentageToPass());
        commandForm.setOverallQuestionCount(actionForm.getOverallQuestionCount());
        commandForm.setTestingValidityTime(actionForm.getTestingValidityTime());
        commandForm.setTestingValidityTimeUnitOfMeasureTypeName(actionForm.getTestingValidityTimeUnitOfMeasureTypeChoice());
        commandForm.setExpiredRetentionTime(actionForm.getExpiredRetentionTime());
        commandForm.setExpiredRetentionTimeUnitOfMeasureTypeName(actionForm.getExpiredRetentionTimeUnitOfMeasureTypeChoice());
        commandForm.setAlwaysReassignOnExpiration(actionForm.getAlwaysReassignOnExpiration().toString());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());
        commandForm.setDescription(actionForm.getDescription());
        commandForm.setOverviewMimeTypeName(actionForm.getOverviewMimeTypeChoice());
        commandForm.setOverview(actionForm.getOverview());
        commandForm.setIntroductionMimeTypeName(actionForm.getIntroductionMimeTypeChoice());
        commandForm.setIntroduction(actionForm.getIntroduction());

        return TrainingUtil.getHome().createTrainingClass(getUserVisitPK(request), commandForm);
    }
    
}
