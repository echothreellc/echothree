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

package com.echothree.ui.web.main.action.core.mimetype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetEntityAttributeTypeChoicesResult;
import com.echothree.model.control.core.common.choice.EntityAttributeTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="MimeTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private EntityAttributeTypeChoicesBean entityAttributeTypeChoices;

    private String mimeTypeName;
    private String entityAttributeTypeChoice;
    private Boolean isDefault;
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

    public void setMimeTypeName(String mimeTypeName) {
        this.mimeTypeName = mimeTypeName;
    }
    
    public String getMimeTypeName() {
        return mimeTypeName;
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }

}
