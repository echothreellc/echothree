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

package com.echothree.ui.web.main.action.core.entityattribute;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.model.control.core.common.choice.EntityAttributeTypeChoicesBean;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.choice.SequenceChoicesBean;
import com.echothree.model.control.workflow.common.choice.WorkflowChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureKindChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import java.util.Objects;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EntityAttributeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private EntityAttributeTypeChoicesBean entityAttributeTypeChoices;
    private SequenceChoicesBean entityListItemSequenceChoices;
    private WorkflowChoicesBean workflowChoices;
    private UnitOfMeasureKindChoicesBean unitOfMeasureKindChoices;
    private UnitOfMeasureTypeChoicesBean unitOfMeasureTypeChoices;
    
    private String componentVendorName;
    private String entityTypeName;
    private String entityAttributeName;
    private String entityAttributeTypeChoice;
    private Boolean trackRevisions;
    private Boolean checkContentWebAddress;
    private String validationPattern;
    private String entityListItemSequenceChoice;
    private String upperRangeIntegerValue;
    private String upperLimitIntegerValue;
    private String lowerLimitIntegerValue;
    private String lowerRangeIntegerValue;
    private String upperRangeLongValue;
    private String upperLimitLongValue;
    private String lowerLimitLongValue;
    private String lowerRangeLongValue;
    private String unitOfMeasureKindChoice;
    private String unitOfMeasureTypeChoice;
    private String workflowChoice;
    private String sortOrder;
    private String description;
    
    private void setupEntityAttributeTypeChoices()
            throws NamingException {
        if(entityAttributeTypeChoices == null) {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeTypeChoicesForm();

            commandForm.setDefaultEntityAttributeTypeChoice(entityAttributeTypeChoice);

            var commandResult = CoreUtil.getHome().getEntityAttributeTypeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getEntityAttributeTypeChoicesResult = executionResult.getResult();
            entityAttributeTypeChoices = getEntityAttributeTypeChoicesResult.getEntityAttributeTypeChoices();

            if(entityAttributeTypeChoice == null)
                entityAttributeTypeChoice = entityAttributeTypeChoices.getDefaultValue();
        }
    }
    
    private void setupEntityListItemSequenceChoices()
            throws NamingException {
        if(entityListItemSequenceChoices == null) {
            var commandForm = SequenceUtil.getHome().getGetSequenceChoicesForm();

            commandForm.setSequenceTypeName(SequenceTypes.ENTITY_LIST_ITEM.name());
            commandForm.setDefaultSequenceChoice(entityListItemSequenceChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getSequenceChoicesResult = executionResult.getResult();
            entityListItemSequenceChoices = getSequenceChoicesResult.getSequenceChoices();

            if(entityListItemSequenceChoice == null)
                entityListItemSequenceChoice = entityListItemSequenceChoices.getDefaultValue();
        }
    }
    
    private void setupUnitOfMeasureKindChoices()
            throws NamingException {
        if(unitOfMeasureKindChoices == null) {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindChoicesForm();

            commandForm.setDefaultUnitOfMeasureKindChoice(unitOfMeasureKindChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = UomUtil.getHome().getUnitOfMeasureKindChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = executionResult.getResult();
            unitOfMeasureKindChoices = result.getUnitOfMeasureKindChoices();

            if(unitOfMeasureKindChoice == null)
                unitOfMeasureKindChoice = unitOfMeasureKindChoices.getDefaultValue();
        }
    }

    private void setupUnitOfMeasureTypeChoices()
            throws NamingException {
        var kindChoice = getUnitOfMeasureKindChoice();

        if(kindChoice == null || kindChoice.isEmpty()) {
            clearUnitOfMeasureTypeChoice();
        } else if(unitOfMeasureTypeChoices == null) {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            commandForm.setUnitOfMeasureKindName(kindChoice);
            commandForm.setDefaultUnitOfMeasureTypeChoice(unitOfMeasureTypeChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = executionResult.getResult();
            unitOfMeasureTypeChoices = result.getUnitOfMeasureTypeChoices();

            if(unitOfMeasureTypeChoice == null)
                unitOfMeasureTypeChoice = unitOfMeasureTypeChoices.getDefaultValue();
        }
    }

    private void setupWorkflowChoices()
            throws NamingException {
        if(workflowChoices == null) {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowChoicesForm();

            commandForm.setDefaultWorkflowChoice(workflowChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = WorkflowUtil.getHome().getWorkflowChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getWorkflowChoicesResult = executionResult.getResult();
            workflowChoices = getWorkflowChoicesResult.getWorkflowChoices();

            if(workflowChoice == null)
                workflowChoice = workflowChoices.getDefaultValue();
        }
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
    
    public List<LabelValueBean> getEntityAttributeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEntityAttributeTypeChoices();
        if(entityAttributeTypeChoices != null)
            choices = convertChoices(entityAttributeTypeChoices);
        
        return choices;
    }
    
    public void setEntityAttributeTypeChoice(String entityAttributeTypeChoice) {
        this.entityAttributeTypeChoice = entityAttributeTypeChoice;
    }
    
    public String getEntityAttributeTypeChoice()
            throws NamingException {
        setupEntityAttributeTypeChoices();
        return entityAttributeTypeChoice;
    }
    
    public void setTrackRevisions(Boolean trackRevisions) {
        this.trackRevisions = trackRevisions;
    }
    
    public Boolean getTrackRevisions() {
        return trackRevisions;
    }
    
    public void setCheckContentWebAddress(Boolean checkContentWebAddress) {
        this.checkContentWebAddress = checkContentWebAddress;
    }
    
    public Boolean getCheckContentWebAddress() {
        return checkContentWebAddress;
    }
    
    public String getValidationPattern() {
        return validationPattern;
    }
    
    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }
    
    public List<LabelValueBean> getEntityListItemSequenceChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEntityListItemSequenceChoices();
        if(entityListItemSequenceChoices != null)
            choices = convertChoices(entityListItemSequenceChoices);
        
        return choices;
    }
    
    public void setEntityListItemSequenceChoice(String entityListItemSequenceChoice) {
        this.entityListItemSequenceChoice = entityListItemSequenceChoice;
    }
    
    public String getEntityListItemSequenceChoice()
            throws NamingException {
        setupEntityListItemSequenceChoices();
        return entityListItemSequenceChoice;
    }
    
    public String getUpperRangeIntegerValue() {
        return upperRangeIntegerValue;
    }

    public void setUpperRangeIntegerValue(String upperRangeIntegerValue) {
        this.upperRangeIntegerValue = upperRangeIntegerValue;
    }

    public String getUpperLimitIntegerValue() {
        return upperLimitIntegerValue;
    }

    public void setUpperLimitIntegerValue(String upperLimitIntegerValue) {
        this.upperLimitIntegerValue = upperLimitIntegerValue;
    }

    public String getLowerLimitIntegerValue() {
        return lowerLimitIntegerValue;
    }

    public void setLowerLimitIntegerValue(String lowerLimitIntegerValue) {
        this.lowerLimitIntegerValue = lowerLimitIntegerValue;
    }

    public String getLowerRangeIntegerValue() {
        return lowerRangeIntegerValue;
    }

    public void setLowerRangeIntegerValue(String lowerRangeIntegerValue) {
        this.lowerRangeIntegerValue = lowerRangeIntegerValue;
    }

    public String getUpperRangeLongValue() {
        return upperRangeLongValue;
    }

    public void setUpperRangeLongValue(String upperRangeLongValue) {
        this.upperRangeLongValue = upperRangeLongValue;
    }

    public String getUpperLimitLongValue() {
        return upperLimitLongValue;
    }

    public void setUpperLimitLongValue(String upperLimitLongValue) {
        this.upperLimitLongValue = upperLimitLongValue;
    }

    public String getLowerLimitLongValue() {
        return lowerLimitLongValue;
    }

    public void setLowerLimitLongValue(String lowerLimitLongValue) {
        this.lowerLimitLongValue = lowerLimitLongValue;
    }

    public String getLowerRangeLongValue() {
        return lowerRangeLongValue;
    }

    public void setLowerRangeLongValue(String lowerRangeLongValue) {
        this.lowerRangeLongValue = lowerRangeLongValue;
    }

    public List<LabelValueBean> getUnitOfMeasureKindChoices()
            throws NamingException {
        setupUnitOfMeasureKindChoices();
        return convertChoices(unitOfMeasureKindChoices);
    }

    public String getUnitOfMeasureKindChoice()
            throws NamingException {
        setupUnitOfMeasureKindChoices();
        return unitOfMeasureKindChoice;
    }

    public void setUnitOfMeasureKindChoice(String unitOfMeasureKindChoice) {
        if(!Objects.equals(this.unitOfMeasureKindChoice, unitOfMeasureKindChoice)) {
            unitOfMeasureTypeChoices = null;
        }
        this.unitOfMeasureKindChoice = unitOfMeasureKindChoice;
    }

    public List<LabelValueBean> getUnitOfMeasureTypeChoices()
            throws NamingException {
        setupUnitOfMeasureTypeChoices();
        return convertChoicesOrEmptyChoice(unitOfMeasureTypeChoices);
    }

    public String getUnitOfMeasureTypeChoice()
            throws NamingException {
        setupUnitOfMeasureTypeChoices();
        return unitOfMeasureTypeChoice;
    }

    public void setUnitOfMeasureTypeChoice(String unitOfMeasureTypeChoice) {
        this.unitOfMeasureTypeChoice = unitOfMeasureTypeChoice;
    }

    public void clearUnitOfMeasureTypeChoice() {
        unitOfMeasureTypeChoice = null;
        unitOfMeasureTypeChoices = null;
    }

    public List<LabelValueBean> getWorkflowChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupWorkflowChoices();
        if(workflowChoices != null)
            choices = convertChoices(workflowChoices);

        return choices;
    }

    public void setWorkflowChoice(String workflowChoice) {
        this.workflowChoice = workflowChoice;
    }

    public String getWorkflowChoice()
            throws NamingException {
        setupWorkflowChoices();
        return workflowChoice;
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
        entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
        trackRevisions = false;
        checkContentWebAddress = false;
    }
    
}
