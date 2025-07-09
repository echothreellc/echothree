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

package com.echothree.ui.web.main.action.core.entitymultiplelistitemattribute;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetEntityListItemChoicesResult;
import com.echothree.model.control.core.common.choice.EntityListItemChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EntityMultipleListItemAttributeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private EntityListItemChoicesBean entityListItemChoices;
    
    private String entityAttributeName;
    private String entityRef;
    private String returnUrl;
    private String entityListItemChoice;
    
    private void setupEntityListItemChoices()
            throws NamingException {
        if(entityListItemChoices == null) {
            var commandForm = CoreUtil.getHome().getGetEntityListItemChoicesForm();

            commandForm.setEntityRef(entityRef);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setDefaultEntityListItemChoice(entityListItemChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = CoreUtil.getHome().getEntityListItemChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getEntityListItemChoicesResult = (GetEntityListItemChoicesResult)executionResult.getResult();
            entityListItemChoices = getEntityListItemChoicesResult.getEntityListItemChoices();

            if(entityListItemChoice == null)
                entityListItemChoice = entityListItemChoices.getDefaultValue();
        }
    }
    
    public String getEntityAttributeName() {
        return entityAttributeName;
    }
    
    public void setEntityAttributeName(String entityAttributeName) {
        this.entityAttributeName = entityAttributeName;
    }
    
    public String getEntityRef() {
        return entityRef;
    }
    
    public void setEntityRef(String entityRef) {
        this.entityRef = entityRef;
    }
    
    public String getReturnUrl() {
        return returnUrl;
    }
    
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    
    public List<LabelValueBean> getEntityListItemChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEntityListItemChoices();
        if(entityListItemChoices != null)
            choices = convertChoices(entityListItemChoices);
        
        return choices;
    }
    
    public void setEntityListItemChoice(String entityListItemChoice) {
        this.entityListItemChoice = entityListItemChoice;
    }
    
    public String getEntityListItemChoice()
            throws NamingException {
        setupEntityListItemChoices();
        return entityListItemChoice;
    }
    
}
