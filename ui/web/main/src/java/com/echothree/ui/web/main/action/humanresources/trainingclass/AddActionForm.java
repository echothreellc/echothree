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

package com.echothree.ui.web.main.action.humanresources.trainingclass;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.control.user.workeffort.common.WorkEffortUtil;
import com.echothree.control.user.workeffort.common.result.GetWorkEffortScopeChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.model.control.workeffort.common.choice.WorkEffortScopeChoicesBean;
import com.echothree.model.control.workeffort.common.workeffort.TrainingConstants;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="TrainingClassAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean estimatedReadingTimeUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean readingTimeAllowedUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean estimatedTestingTimeUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean testingTimeAllowedUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean requiredCompletionTimeUnitOfMeasureTypeChoices;
    private WorkEffortScopeChoicesBean workEffortScopeChoices;
    private UnitOfMeasureTypeChoicesBean testingValidityTimeUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean expiredRetentionTimeUnitOfMeasureTypeChoices;
    private MimeTypeChoicesBean overviewMimeTypeChoices;
    private MimeTypeChoicesBean introductionMimeTypeChoices;
    
    private String trainingClassName;
    private String estimatedReadingTime;
    private String estimatedReadingTimeUnitOfMeasureTypeChoice;
    private String readingTimeAllowed;
    private String readingTimeAllowedUnitOfMeasureTypeChoice;
    private String estimatedTestingTime;
    private String estimatedTestingTimeUnitOfMeasureTypeChoice;
    private String testingTimeAllowed;
    private String testingTimeAllowedUnitOfMeasureTypeChoice;
    private String requiredCompletionTime;
    private String requiredCompletionTimeUnitOfMeasureTypeChoice;
    private String workEffortScopeChoice;
    private String defaultPercentageToPass;
    private String overallQuestionCount;
    private String testingValidityTime;
    private String testingValidityTimeUnitOfMeasureTypeChoice;
    private String expiredRetentionTime;
    private String expiredRetentionTimeUnitOfMeasureTypeChoice;
    private Boolean alwaysReassignOnExpiration;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    private String overviewMimeTypeChoice;
    private String overview;
    private String introductionMimeTypeChoice;
    private String introduction;
    
    private void setupEstimatedReadingTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        if(estimatedReadingTimeUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(estimatedReadingTimeUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            estimatedReadingTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(estimatedReadingTimeUnitOfMeasureTypeChoice == null) {
                estimatedReadingTimeUnitOfMeasureTypeChoice = estimatedReadingTimeUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupReadingTimeAllowedUnitOfMeasureTypeChoices()
            throws NamingException {
        if(readingTimeAllowedUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(readingTimeAllowedUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            readingTimeAllowedUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(readingTimeAllowedUnitOfMeasureTypeChoice == null) {
                readingTimeAllowedUnitOfMeasureTypeChoice = readingTimeAllowedUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupEstimatedTestingTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        if(estimatedTestingTimeUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(estimatedTestingTimeUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            estimatedTestingTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(estimatedTestingTimeUnitOfMeasureTypeChoice == null) {
                estimatedTestingTimeUnitOfMeasureTypeChoice = estimatedTestingTimeUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupTestingTimeAllowedUnitOfMeasureTypeChoices()
            throws NamingException {
        if(testingTimeAllowedUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(testingTimeAllowedUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            testingTimeAllowedUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(testingTimeAllowedUnitOfMeasureTypeChoice == null) {
                testingTimeAllowedUnitOfMeasureTypeChoice = testingTimeAllowedUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }

    private void setupRequiredCompletionTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        if(requiredCompletionTimeUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(requiredCompletionTimeUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            requiredCompletionTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(requiredCompletionTimeUnitOfMeasureTypeChoice == null) {
                requiredCompletionTimeUnitOfMeasureTypeChoice = requiredCompletionTimeUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }

    public void setupWorkEffortScopeChoices()
            throws NamingException {
        if(workEffortScopeChoices == null) {
            var form = WorkEffortUtil.getHome().getGetWorkEffortScopeChoicesForm();

            form.setWorkEffortTypeName(TrainingConstants.WorkEffortType_TRAINING);
            form.setDefaultWorkEffortScopeChoice(workEffortScopeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = WorkEffortUtil.getHome().getWorkEffortScopeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetWorkEffortScopeChoicesResult)executionResult.getResult();
            workEffortScopeChoices = result.getWorkEffortScopeChoices();

            if(workEffortScopeChoice == null) {
                workEffortScopeChoice = workEffortScopeChoices.getDefaultValue();
            }
        }
    }

    private void setupTestingValidityTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        if(testingValidityTimeUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(testingValidityTimeUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            testingValidityTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(testingValidityTimeUnitOfMeasureTypeChoice == null) {
                testingValidityTimeUnitOfMeasureTypeChoice = testingValidityTimeUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupExpiredRetentionTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        if(expiredRetentionTimeUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(expiredRetentionTimeUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            expiredRetentionTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(expiredRetentionTimeUnitOfMeasureTypeChoice == null) {
                expiredRetentionTimeUnitOfMeasureTypeChoice = expiredRetentionTimeUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
     private void setupOverviewMimeTypeChoices()
             throws NamingException {
        if(overviewMimeTypeChoices == null) {
            var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            commandForm.setDefaultMimeTypeChoice(overviewMimeTypeChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));
            commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

            var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetMimeTypeChoicesResult)executionResult.getResult();
            overviewMimeTypeChoices = result.getMimeTypeChoices();

            if(overviewMimeTypeChoice == null) {
                overviewMimeTypeChoice = overviewMimeTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupIntroductionMimeTypeChoices()
            throws NamingException {
        if(introductionMimeTypeChoices == null) {
            var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            commandForm.setDefaultMimeTypeChoice(introductionMimeTypeChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));
            commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

            var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetMimeTypeChoicesResult)executionResult.getResult();
            introductionMimeTypeChoices = result.getMimeTypeChoices();

            if(introductionMimeTypeChoice == null) {
                introductionMimeTypeChoice = introductionMimeTypeChoices.getDefaultValue();
            }
        }
    }
    
   public void setTrainingClassName(String trainingClassName) {
        this.trainingClassName = trainingClassName;
    }
    
    public String getTrainingClassName() {
        return trainingClassName;
    }
    
    public String getEstimatedReadingTime() {
        return estimatedReadingTime;
    }
    
    public void setEstimatedReadingTime(String estimatedReadingTime) {
        this.estimatedReadingTime = estimatedReadingTime;
    }
    
    public List<LabelValueBean> getEstimatedReadingTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEstimatedReadingTimeUnitOfMeasureTypeChoices();
        if(estimatedReadingTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(estimatedReadingTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getEstimatedReadingTimeUnitOfMeasureTypeChoice()
            throws NamingException {
        setupEstimatedReadingTimeUnitOfMeasureTypeChoices();
        return estimatedReadingTimeUnitOfMeasureTypeChoice;
    }
    
    public void setEstimatedReadingTimeUnitOfMeasureTypeChoice(String estimatedReadingTimeUnitOfMeasureTypeChoice) {
        this.estimatedReadingTimeUnitOfMeasureTypeChoice = estimatedReadingTimeUnitOfMeasureTypeChoice;
    }
    
    public String getReadingTimeAllowed() {
        return readingTimeAllowed;
    }
    
    public void setReadingTimeAllowed(String readingTimeAllowed) {
        this.readingTimeAllowed = readingTimeAllowed;
    }
    
    public List<LabelValueBean> getReadingTimeAllowedUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupReadingTimeAllowedUnitOfMeasureTypeChoices();
        if(readingTimeAllowedUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(readingTimeAllowedUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getReadingTimeAllowedUnitOfMeasureTypeChoice()
            throws NamingException {
        setupReadingTimeAllowedUnitOfMeasureTypeChoices();
        return readingTimeAllowedUnitOfMeasureTypeChoice;
    }
    
    public void setReadingTimeAllowedUnitOfMeasureTypeChoice(String readingTimeAllowedUnitOfMeasureTypeChoice) {
        this.readingTimeAllowedUnitOfMeasureTypeChoice = readingTimeAllowedUnitOfMeasureTypeChoice;
    }
    
    public String getEstimatedTestingTime() {
        return estimatedTestingTime;
    }
    
    public void setEstimatedTestingTime(String estimatedTestingTime) {
        this.estimatedTestingTime = estimatedTestingTime;
    }
    
    public List<LabelValueBean> getEstimatedTestingTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEstimatedTestingTimeUnitOfMeasureTypeChoices();
        if(estimatedTestingTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(estimatedTestingTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getEstimatedTestingTimeUnitOfMeasureTypeChoice()
            throws NamingException {
        setupEstimatedTestingTimeUnitOfMeasureTypeChoices();
        return estimatedTestingTimeUnitOfMeasureTypeChoice;
    }
    
    public void setEstimatedTestingTimeUnitOfMeasureTypeChoice(String estimatedTestingTimeUnitOfMeasureTypeChoice) {
        this.estimatedTestingTimeUnitOfMeasureTypeChoice = estimatedTestingTimeUnitOfMeasureTypeChoice;
    }
    
    public String getTestingTimeAllowed() {
        return testingTimeAllowed;
    }

    public void setTestingTimeAllowed(String testingTimeAllowed) {
        this.testingTimeAllowed = testingTimeAllowed;
    }

    public List<LabelValueBean> getTestingTimeAllowedUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupTestingTimeAllowedUnitOfMeasureTypeChoices();
        if(testingTimeAllowedUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(testingTimeAllowedUnitOfMeasureTypeChoices);
        }

        return choices;
    }

    public String getTestingTimeAllowedUnitOfMeasureTypeChoice()
            throws NamingException {
        setupTestingTimeAllowedUnitOfMeasureTypeChoices();
        return testingTimeAllowedUnitOfMeasureTypeChoice;
    }

    public void setTestingTimeAllowedUnitOfMeasureTypeChoice(String testingTimeAllowedUnitOfMeasureTypeChoice) {
        this.testingTimeAllowedUnitOfMeasureTypeChoice = testingTimeAllowedUnitOfMeasureTypeChoice;
    }
    
    public String getRequiredCompletionTime() {
        return requiredCompletionTime;
    }

    public void setRequiredCompletionTime(String requiredCompletionTime) {
        this.requiredCompletionTime = requiredCompletionTime;
    }

    public List<LabelValueBean> getRequiredCompletionTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupRequiredCompletionTimeUnitOfMeasureTypeChoices();
        if(requiredCompletionTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(requiredCompletionTimeUnitOfMeasureTypeChoices);
        }

        return choices;
    }

    public String getRequiredCompletionTimeUnitOfMeasureTypeChoice()
            throws NamingException {
        setupRequiredCompletionTimeUnitOfMeasureTypeChoices();
        return requiredCompletionTimeUnitOfMeasureTypeChoice;
    }

    public void setRequiredCompletionTimeUnitOfMeasureTypeChoice(String requiredCompletionTimeUnitOfMeasureTypeChoice) {
        this.requiredCompletionTimeUnitOfMeasureTypeChoice = requiredCompletionTimeUnitOfMeasureTypeChoice;
    }

    public List<LabelValueBean> getWorkEffortScopeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupWorkEffortScopeChoices();
        if(workEffortScopeChoices != null) {
            choices = convertChoices(workEffortScopeChoices);
        }

        return choices;
    }

    public String getWorkEffortScopeChoice()
            throws NamingException {
        setupWorkEffortScopeChoices();
        return workEffortScopeChoice;
    }

    public void setWorkEffortScopeChoice(String workEffortScopeChoice) {
        this.workEffortScopeChoice = workEffortScopeChoice;
    }

    public String getDefaultPercentageToPass() {
        return defaultPercentageToPass;
    }

    public void setDefaultPercentageToPass(String defaultPercentageToPass) {
        this.defaultPercentageToPass = defaultPercentageToPass;
    }

    public String getOverallQuestionCount() {
        return overallQuestionCount;
    }

    public void setOverallQuestionCount(String overallQuestionCount) {
        this.overallQuestionCount = overallQuestionCount;
    }
    
    public String getTestingValidityTime() {
        return testingValidityTime;
    }
    
    public void setTestingValidityTime(String testingValidityTime) {
        this.testingValidityTime = testingValidityTime;
    }
    
    public List<LabelValueBean> getTestingValidityTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupTestingValidityTimeUnitOfMeasureTypeChoices();
        if(testingValidityTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(testingValidityTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getTestingValidityTimeUnitOfMeasureTypeChoice()
            throws NamingException {
        setupTestingValidityTimeUnitOfMeasureTypeChoices();
        return testingValidityTimeUnitOfMeasureTypeChoice;
    }
    
    public void setTestingValidityTimeUnitOfMeasureTypeChoice(String testingValidityTimeUnitOfMeasureTypeChoice) {
        this.testingValidityTimeUnitOfMeasureTypeChoice = testingValidityTimeUnitOfMeasureTypeChoice;
    }
    
    public String getExpiredRetentionTime() {
        return expiredRetentionTime;
    }
    
    public void setExpiredRetentionTime(String expiredRetentionTime) {
        this.expiredRetentionTime = expiredRetentionTime;
    }
    
    public List<LabelValueBean> getExpiredRetentionTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupExpiredRetentionTimeUnitOfMeasureTypeChoices();
        if(expiredRetentionTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(expiredRetentionTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getExpiredRetentionTimeUnitOfMeasureTypeChoice()
            throws NamingException {
        setupExpiredRetentionTimeUnitOfMeasureTypeChoices();
        return expiredRetentionTimeUnitOfMeasureTypeChoice;
    }
    
    public void setExpiredRetentionTimeUnitOfMeasureTypeChoice(String expiredRetentionTimeUnitOfMeasureTypeChoice) {
        this.expiredRetentionTimeUnitOfMeasureTypeChoice = expiredRetentionTimeUnitOfMeasureTypeChoice;
    }
    
    public Boolean getAlwaysReassignOnExpiration() {
        return alwaysReassignOnExpiration;
    }

    public void setAlwaysReassignOnExpiration(Boolean alwaysReassignOnExpiration) {
        this.alwaysReassignOnExpiration = alwaysReassignOnExpiration;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<LabelValueBean> getOverviewMimeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupOverviewMimeTypeChoices();
        if(overviewMimeTypeChoices != null) {
            choices = convertChoices(overviewMimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setOverviewMimeTypeChoice(String overviewMimeTypeChoice) {
        this.overviewMimeTypeChoice = overviewMimeTypeChoice;
    }
    
    public String getOverviewMimeTypeChoice()
            throws NamingException {
        setupOverviewMimeTypeChoices();
        
        return overviewMimeTypeChoice;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public void setOverview(String overview) {
        this.overview = overview;
    }
    
    public List<LabelValueBean> getIntroductionMimeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupIntroductionMimeTypeChoices();
        if(introductionMimeTypeChoices != null) {
            choices = convertChoices(introductionMimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setIntroductionMimeTypeChoice(String introductionMimeTypeChoice) {
        this.introductionMimeTypeChoice = introductionMimeTypeChoice;
    }
    
    public String getIntroductionMimeTypeChoice()
            throws NamingException {
        setupIntroductionMimeTypeChoices();
        
        return introductionMimeTypeChoice;
    }
    
    public String getIntroduction() {
        return introduction;
    }
    
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        alwaysReassignOnExpiration = false;
        isDefault = false;
    }

}
