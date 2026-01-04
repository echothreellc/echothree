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
import com.echothree.control.user.core.common.result.GetEntityAttributeTypeChoicesResult;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.result.GetSequenceChoicesResult;
import com.echothree.model.control.core.common.choice.EntityAttributeTypeChoicesBean;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.choice.SequenceChoicesBean;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EntityAttributeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private EntityAttributeTypeChoicesBean entityAttributeTypeChoices;
    private SequenceChoicesBean entityListItemSequenceChoices;
    
    private String componentVendorName;
    private String entityTypeName;
    private String entityAttributeName;
    private String entityAttributeTypeChoice;
    private Boolean trackRevisions;
    private Boolean checkContentWebAddress;
    private String validationPattern;
    private String entityListItemSequenceChoice;
    private String sortOrder;
    private String description;
    
    private void setupEntityAttributeTypeChoices()
            throws NamingException {
        if(entityAttributeTypeChoices == null) {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeTypeChoicesForm();

            commandForm.setDefaultEntityAttributeTypeChoice(entityAttributeTypeChoice);

            var commandResult = CoreUtil.getHome().getEntityAttributeTypeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getEntityAttributeTypeChoicesResult = (GetEntityAttributeTypeChoicesResult)executionResult.getResult();
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
            var getSequenceChoicesResult = (GetSequenceChoicesResult)executionResult.getResult();
            entityListItemSequenceChoices = getSequenceChoicesResult.getSequenceChoices();

            if(entityListItemSequenceChoice == null)
                entityListItemSequenceChoice = entityListItemSequenceChoices.getDefaultValue();
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
