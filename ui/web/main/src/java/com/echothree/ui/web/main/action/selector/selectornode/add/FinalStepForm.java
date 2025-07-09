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

package com.echothree.ui.web.main.action.selector.selectornode.add;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetItemAccountingCategoryChoicesResult;
import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.result.GetResponsibilityTypeChoicesResult;
import com.echothree.control.user.employee.common.result.GetSkillTypeChoicesResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemCategoryChoicesResult;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.GetSelectorBooleanTypeChoicesResult;
import com.echothree.control.user.selector.common.result.GetSelectorNodeChoicesResult;
import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.result.GetTrainingClassChoicesResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.GetItemPurchasingCategoryChoicesResult;
import com.echothree.model.control.accounting.common.choice.ItemAccountingCategoryChoicesBean;
import com.echothree.model.control.employee.common.choice.ResponsibilityTypeChoicesBean;
import com.echothree.model.control.employee.common.choice.SkillTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemCategoryChoicesBean;
import com.echothree.model.control.selector.common.choice.SelectorBooleanTypeChoicesBean;
import com.echothree.model.control.selector.common.choice.SelectorNodeChoicesBean;
import com.echothree.model.control.training.common.choice.TrainingClassChoicesBean;
import com.echothree.model.control.vendor.common.choice.ItemPurchasingCategoryChoicesBean;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="SelectorNodeAddFinalStep")
public class FinalStepForm
        extends BaseLanguageActionForm {
    
    private SelectorBooleanTypeChoicesBean selectorBooleanTypeChoices = null;
    private SelectorNodeChoicesBean leftSelectorNodeChoices = null;
    private SelectorNodeChoicesBean rightSelectorNodeChoices = null;
    private ItemCategoryChoicesBean itemCategoryChoices = null;
    private ItemAccountingCategoryChoicesBean itemAccountingCategoryChoices = null;
    private ItemPurchasingCategoryChoicesBean itemPurchasingCategoryChoices = null;
    private ResponsibilityTypeChoicesBean responsibilityTypeChoices = null;
    private SkillTypeChoicesBean skillTypeChoices = null;
    private TrainingClassChoicesBean trainingClassChoices = null;
    
    private String selectorKindName = null;
    private String selectorTypeName = null;
    private String selectorName = null;
    private String selectorNodeName = null;
    private Boolean isRootSelectorNode = null;
    private String selectorNodeTypeName = null;
    private Boolean negate = null;
    private String description = null;
    
    private String selectorBooleanTypeChoice = null;
    private String leftSelectorNodeChoice = null;
    private String rightSelectorNodeChoice = null;
    private String itemCategoryChoice = null;
    private String itemAccountingCategoryChoice = null;
    private String itemPurchasingCategoryChoice = null;
    private String responsibilityTypeChoice = null;
    private String skillTypeChoice = null;
    private String trainingClassChoice = null;
    private String workflowName = null;
    private String workflowStepName = null;
    private String componentVendorName = null;
    private String entityTypeName = null;
    private String entityAttributeName = null;
    private String entityListItemName = null;
    
    private Boolean checkParents = null;
    
    private void setupSelectorBooleanTypeChoices()
            throws NamingException {
        if(selectorBooleanTypeChoices == null) {
            var commandForm = SelectorUtil.getHome().getGetSelectorBooleanTypeChoicesForm();

            commandForm.setDefaultSelectorBooleanTypeChoice(selectorBooleanTypeChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = SelectorUtil.getHome().getSelectorBooleanTypeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSelectorBooleanTypeChoicesResult)executionResult.getResult();
            selectorBooleanTypeChoices = result.getSelectorBooleanTypeChoices();

            if(selectorBooleanTypeChoice == null)
                selectorBooleanTypeChoice = selectorBooleanTypeChoices.getDefaultValue();
        }
    }
    
    private void setupLeftSelectorNodeChoices()
            throws NamingException {
        if(leftSelectorNodeChoices == null) {
            var commandForm = SelectorUtil.getHome().getGetSelectorNodeChoicesForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setSelectorName(selectorName);
            commandForm.setDefaultSelectorNodeChoice(leftSelectorNodeChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = SelectorUtil.getHome().getSelectorNodeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSelectorNodeChoicesResult)executionResult.getResult();
            leftSelectorNodeChoices = result.getSelectorNodeChoices();

            if(leftSelectorNodeChoice == null)
                leftSelectorNodeChoice = leftSelectorNodeChoices.getDefaultValue();
        }
    }
    
    private void setupRightSelectorNodeChoices()
            throws NamingException {
        if(rightSelectorNodeChoices == null) {
            var commandForm = SelectorUtil.getHome().getGetSelectorNodeChoicesForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setSelectorName(selectorName);
            commandForm.setDefaultSelectorNodeChoice(rightSelectorNodeChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = SelectorUtil.getHome().getSelectorNodeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSelectorNodeChoicesResult)executionResult.getResult();
            rightSelectorNodeChoices = result.getSelectorNodeChoices();

            if(rightSelectorNodeChoice == null)
                rightSelectorNodeChoice = rightSelectorNodeChoices.getDefaultValue();
        }
    }
    
    private void setupItemCategoryChoices()
            throws NamingException {
        if(itemCategoryChoices == null) {
            var commandForm = ItemUtil.getHome().getGetItemCategoryChoicesForm();

            commandForm.setDefaultItemCategoryChoice(itemCategoryChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = ItemUtil.getHome().getItemCategoryChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemCategoryChoicesResult)executionResult.getResult();
            itemCategoryChoices = result.getItemCategoryChoices();

            if(itemCategoryChoice == null)
                itemCategoryChoice = itemCategoryChoices.getDefaultValue();
        }
    }
    
    private void setupItemAccountingCategoryChoices()
            throws NamingException {
        if(itemAccountingCategoryChoices == null) {
            var commandForm = AccountingUtil.getHome().getGetItemAccountingCategoryChoicesForm();

            commandForm.setDefaultItemAccountingCategoryChoice(itemAccountingCategoryChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = AccountingUtil.getHome().getItemAccountingCategoryChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemAccountingCategoryChoicesResult)executionResult.getResult();
            itemAccountingCategoryChoices = result.getItemAccountingCategoryChoices();

            if(itemAccountingCategoryChoice == null)
                itemAccountingCategoryChoice = itemAccountingCategoryChoices.getDefaultValue();
        }
    }
    
    private void setupItemPurchasingCategoryChoices()
            throws NamingException {
        if(itemPurchasingCategoryChoices == null) {
            var commandForm = VendorUtil.getHome().getGetItemPurchasingCategoryChoicesForm();

            commandForm.setDefaultItemPurchasingCategoryChoice(itemPurchasingCategoryChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = VendorUtil.getHome().getItemPurchasingCategoryChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemPurchasingCategoryChoicesResult)executionResult.getResult();
            itemPurchasingCategoryChoices = result.getItemPurchasingCategoryChoices();

            if(itemPurchasingCategoryChoice == null)
                itemPurchasingCategoryChoice = itemPurchasingCategoryChoices.getDefaultValue();
        }
    }
    
    public void setupResponsibilityTypeChoices()
            throws NamingException {
        if(responsibilityTypeChoices == null) {
            var form = EmployeeUtil.getHome().getGetResponsibilityTypeChoicesForm();

            form.setDefaultResponsibilityTypeChoice(responsibilityTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = EmployeeUtil.getHome().getResponsibilityTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetResponsibilityTypeChoicesResult)executionResult.getResult();
            responsibilityTypeChoices = result.getResponsibilityTypeChoices();

            if(responsibilityTypeChoice == null)
                responsibilityTypeChoice = responsibilityTypeChoices.getDefaultValue();
        }
    }
    
    public void setupSkillTypeChoices()
            throws NamingException {
        if(skillTypeChoices == null) {
            var form = EmployeeUtil.getHome().getGetSkillTypeChoicesForm();

            form.setDefaultSkillTypeChoice(skillTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = EmployeeUtil.getHome().getSkillTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSkillTypeChoicesResult)executionResult.getResult();
            skillTypeChoices = result.getSkillTypeChoices();

            if(skillTypeChoice == null)
                skillTypeChoice = skillTypeChoices.getDefaultValue();
        }
    }
    
    public void setupTrainingClassChoices()
            throws NamingException {
        if(trainingClassChoices == null) {
            var form = TrainingUtil.getHome().getGetTrainingClassChoicesForm();

            form.setDefaultTrainingClassChoice(trainingClassChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = TrainingUtil.getHome().getTrainingClassChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTrainingClassChoicesResult)executionResult.getResult();
            trainingClassChoices = result.getTrainingClassChoices();

            if(trainingClassChoice == null)
                trainingClassChoice = trainingClassChoices.getDefaultValue();
        }
    }
    
    public String getSelectorKindName() {
        return selectorKindName;
    }
    
    public void setSelectorKindName(String selectorKindName) {
        this.selectorKindName = selectorKindName;
    }
    
    public String getSelectorTypeName() {
        return selectorTypeName;
    }
    
    public void setSelectorTypeName(String selectorTypeName) {
        this.selectorTypeName = selectorTypeName;
    }
    
    public String getSelectorName() {
        return selectorName;
    }
    
    public void setSelectorName(String selectorName) {
        this.selectorName = selectorName;
    }
    
    public String getSelectorNodeName() {
        return selectorNodeName;
    }
    
    public void setSelectorNodeName(String selectorNodeName) {
        this.selectorNodeName = selectorNodeName;
    }
    
    public Boolean getIsRootSelectorNode() {
        return isRootSelectorNode;
    }
    
    public void setIsRootSelectorNode(Boolean isRootSelectorNode) {
        this.isRootSelectorNode = isRootSelectorNode;
    }
    
    public String getSelectorNodeTypeName() {
        return selectorNodeTypeName;
    }
    
    public void setSelectorNodeTypeName(String selectorNodeTypeName) {
        this.selectorNodeTypeName = selectorNodeTypeName;
    }
    
    public Boolean getNegate() {
        return negate;
    }
    
    public void setNegate(Boolean negate) {
        this.negate = negate;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getSelectorBooleanTypeChoice() {
        return selectorBooleanTypeChoice;
    }
    
    public void setSelectorBooleanTypeChoice(String selectorBooleanTypeChoice) {
        this.selectorBooleanTypeChoice = selectorBooleanTypeChoice;
    }
    
    public List<LabelValueBean> getSelectorBooleanTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupSelectorBooleanTypeChoices();
        if(selectorBooleanTypeChoices != null)
            choices = convertChoices(selectorBooleanTypeChoices);
        
        return choices;
    }
    
    public String getLeftSelectorNodeChoice() {
        return leftSelectorNodeChoice;
    }
    
    public void setLeftSelectorNodeChoice(String leftSelectorNodeChoice) {
        this.leftSelectorNodeChoice = leftSelectorNodeChoice;
    }
    
    public List<LabelValueBean> getLeftSelectorNodeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupLeftSelectorNodeChoices();
        if(leftSelectorNodeChoices != null)
            choices = convertChoices(leftSelectorNodeChoices);
        
        return choices;
    }
    
    public String getRightSelectorNodeChoice() {
        return rightSelectorNodeChoice;
    }
    
    public void setRightSelectorNodeChoice(String rightSelectorNodeChoice) {
        this.rightSelectorNodeChoice = rightSelectorNodeChoice;
    }
    
    public List<LabelValueBean> getRightSelectorNodeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupRightSelectorNodeChoices();
        if(rightSelectorNodeChoices != null)
            choices = convertChoices(rightSelectorNodeChoices);
        
        return choices;
    }
    
    public List<LabelValueBean> getItemCategoryChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemCategoryChoices();
        if(itemCategoryChoices != null)
            choices = convertChoices(itemCategoryChoices);
        
        return choices;
    }
    
    public void setItemCategoryChoice(String itemCategoryChoice) {
        this.itemCategoryChoice = itemCategoryChoice;
    }
    
    public String getItemCategoryChoice()
            throws NamingException {
        setupItemCategoryChoices();
        return itemCategoryChoice;
    }
    
    public List<LabelValueBean> getItemAccountingCategoryChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemAccountingCategoryChoices();
        if(itemAccountingCategoryChoices != null)
            choices = convertChoices(itemAccountingCategoryChoices);
        
        return choices;
    }
    
    public void setItemAccountingCategoryChoice(String itemAccountingCategoryChoice) {
        this.itemAccountingCategoryChoice = itemAccountingCategoryChoice;
    }
    
    public String getItemAccountingCategoryChoice()
            throws NamingException {
        setupItemAccountingCategoryChoices();
        return itemAccountingCategoryChoice;
    }
    
    public List<LabelValueBean> getItemPurchasingCategoryChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemPurchasingCategoryChoices();
        if(itemPurchasingCategoryChoices != null)
            choices = convertChoices(itemPurchasingCategoryChoices);
        
        return choices;
    }
    
    public void setItemPurchasingCategoryChoice(String itemPurchasingCategoryChoice) {
        this.itemPurchasingCategoryChoice = itemPurchasingCategoryChoice;
    }
    
    public String getItemPurchasingCategoryChoice()
            throws NamingException {
        setupItemPurchasingCategoryChoices();
        return itemPurchasingCategoryChoice;
    }
    
    public String getResponsibilityTypeChoice() {
        return responsibilityTypeChoice;
    }
    
    public void setResponsibilityTypeChoice(String responsibilityTypeChoice) {
        this.responsibilityTypeChoice = responsibilityTypeChoice;
    }
    
    public List<LabelValueBean> getResponsibilityTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupResponsibilityTypeChoices();
        if(responsibilityTypeChoices != null)
            choices = convertChoices(responsibilityTypeChoices);
        
        return choices;
    }
    
    public String getSkillTypeChoice() {
        return skillTypeChoice;
    }
    
    public void setSkillTypeChoice(String skillTypeChoice) {
        this.skillTypeChoice = skillTypeChoice;
    }
    
    public List<LabelValueBean> getSkillTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupSkillTypeChoices();
        if(skillTypeChoices != null)
            choices = convertChoices(skillTypeChoices);
        
        return choices;
    }
    
    public String getTrainingClassChoice() {
        return trainingClassChoice;
    }
    
    public void setTrainingClassChoice(String trainingClassChoice) {
        this.trainingClassChoice = trainingClassChoice;
    }
    
    public List<LabelValueBean> getTrainingClassChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupTrainingClassChoices();
        if(trainingClassChoices != null)
            choices = convertChoices(trainingClassChoices);
        
        return choices;
    }
    
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
    
    public String getWorkflowName() {
        return workflowName;
    }
    
    public void setWorkflowStepName(String workflowStepName) {
        this.workflowStepName = workflowStepName;
    }
    
    public String getWorkflowStepName() {
        return workflowStepName;
    }
    
    public void setComponentVendorName(String componentVendorName) {
        this.componentVendorName = componentVendorName;
    }
    
    public String getComponentVendorName() {
        return componentVendorName;
    }
    
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }
    
    public String getEntityTypeName() {
        return entityTypeName;
    }
    
    public void setEntityAttributeName(String entityAttributeName) {
        this.entityAttributeName = entityAttributeName;
    }
    
    public String getEntityAttributeName() {
        return entityAttributeName;
    }
    
    public void setEntityListItemName(String entityListItemName) {
        this.entityListItemName = entityListItemName;
    }
    
    public String getEntityListItemName() {
        return entityListItemName;
    }
    
    public Boolean getCheckParents() {
        return checkParents;
    }
    
    public void setCheckParents(Boolean checkParents) {
        this.checkParents = checkParents;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setIsRootSelectorNode(false);
        setNegate(false);
        setCheckParents(false);
    }
    
}
