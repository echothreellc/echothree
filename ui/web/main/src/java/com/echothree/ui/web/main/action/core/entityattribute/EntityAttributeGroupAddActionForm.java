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
import com.echothree.control.user.core.common.result.GetEntityAttributeGroupChoicesResult;
import com.echothree.model.control.core.common.choice.EntityAttributeGroupChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EntityAttributeEntityAttributeGroupAdd")
public class EntityAttributeGroupAddActionForm
        extends BaseActionForm {
    
    private EntityAttributeGroupChoicesBean entityAttributeGroupChoices;
    
    private String componentVendorName;
    private String entityTypeName;
    private String entityAttributeName;
    private String entityAttributeGroupChoice;
    private String sortOrder;
    
    private void setupEntityAttributeGroupChoices()
            throws NamingException {
        if(entityAttributeGroupChoices == null) {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeGroupChoicesForm();

            commandForm.setDefaultEntityAttributeGroupChoice(entityAttributeGroupChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = CoreUtil.getHome().getEntityAttributeGroupChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getEntityAttributeGroupChoicesResult = (GetEntityAttributeGroupChoicesResult)executionResult.getResult();
            entityAttributeGroupChoices = getEntityAttributeGroupChoicesResult.getEntityAttributeGroupChoices();

            if(entityAttributeGroupChoice == null) {
                entityAttributeGroupChoice = entityAttributeGroupChoices.getDefaultValue();
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
    
    public List<LabelValueBean> getEntityAttributeGroupChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEntityAttributeGroupChoices();
        if(entityAttributeGroupChoices != null)
            choices = convertChoices(entityAttributeGroupChoices);
        
        return choices;
    }
    
    public void setEntityAttributeGroupChoice(String entityAttributeGroupChoice) {
        this.entityAttributeGroupChoice = entityAttributeGroupChoice;
    }
    
    public String getEntityAttributeGroupChoice()
            throws NamingException {
        setupEntityAttributeGroupChoices();
        return entityAttributeGroupChoice;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
