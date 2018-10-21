// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.core.remote.form.GetEntityAttributeTypeChoicesForm;
import com.echothree.control.user.core.remote.result.GetEntityAttributeTypeChoicesResult;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.remote.form.GetSequenceChoicesForm;
import com.echothree.control.user.sequence.remote.result.GetSequenceChoicesResult;
import com.echothree.model.control.core.remote.choice.EntityAttributeTypeChoicesBean;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.remote.choice.SequenceChoicesBean;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
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
    
    private void setupEntityAttributeTypeChoices() {
        if(entityAttributeTypeChoices == null) {
            try {
                GetEntityAttributeTypeChoicesForm commandForm = CoreUtil.getHome().getGetEntityAttributeTypeChoicesForm();
                
                commandForm.setDefaultEntityAttributeTypeChoice(entityAttributeTypeChoice);
                
                CommandResult commandResult = CoreUtil.getHome().getEntityAttributeTypeChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetEntityAttributeTypeChoicesResult getEntityAttributeTypeChoicesResult = (GetEntityAttributeTypeChoicesResult)executionResult.getResult();
                entityAttributeTypeChoices = getEntityAttributeTypeChoicesResult.getEntityAttributeTypeChoices();
                
                if(entityAttributeTypeChoice == null)
                    entityAttributeTypeChoice = entityAttributeTypeChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, entityAttributeTypeChoices remains null, no default
            }
        }
    }
    
    private void setupEntityListItemSequenceChoices() {
        if(entityListItemSequenceChoices == null) {
            try {
                GetSequenceChoicesForm commandForm = SequenceUtil.getHome().getGetSequenceChoicesForm();
                
                commandForm.setSequenceTypeName(SequenceConstants.SequenceType_ENTITY_LIST_ITEM);
                commandForm.setDefaultSequenceChoice(entityListItemSequenceChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSequenceChoicesResult getSequenceChoicesResult = (GetSequenceChoicesResult)executionResult.getResult();
                entityListItemSequenceChoices = getSequenceChoicesResult.getSequenceChoices();
                
                if(entityListItemSequenceChoice == null)
                    entityListItemSequenceChoice = entityListItemSequenceChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, entityListItemSequenceChoices remains null, no default
            }
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
    
    public List<LabelValueBean> getEntityAttributeTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupEntityAttributeTypeChoices();
        if(entityAttributeTypeChoices != null)
            choices = convertChoices(entityAttributeTypeChoices);
        
        return choices;
    }
    
    public void setEntityAttributeTypeChoice(String entityAttributeTypeChoice) {
        this.entityAttributeTypeChoice = entityAttributeTypeChoice;
    }
    
    public String getEntityAttributeTypeChoice() {
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
    
    public List<LabelValueBean> getEntityListItemSequenceChoices() {
        List<LabelValueBean> choices = null;
        
        setupEntityListItemSequenceChoices();
        if(entityListItemSequenceChoices != null)
            choices = convertChoices(entityListItemSequenceChoices);
        
        return choices;
    }
    
    public void setEntityListItemSequenceChoice(String entityListItemSequenceChoice) {
        this.entityListItemSequenceChoice = entityListItemSequenceChoice;
    }
    
    public String getEntityListItemSequenceChoice() {
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
        trackRevisions = Boolean.FALSE;
        checkContentWebAddress = Boolean.FALSE;
    }
    
}
