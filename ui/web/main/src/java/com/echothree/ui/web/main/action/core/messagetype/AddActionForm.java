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

package com.echothree.ui.web.main.action.core.messagetype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeUsageTypeChoicesResult;
import com.echothree.model.control.core.common.choice.MimeTypeUsageTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="MessageTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private MimeTypeUsageTypeChoicesBean mimeTypeUsageTypeChoices;
    
    private String componentVendorName;
    private String entityTypeName;
    private String messageTypeName;
    private String mimeTypeUsageTypeChoice;
    private String sortOrder;
    private String description;
    
    public void setupMimeTypeUsageTypeChoices()
            throws NamingException {
        if(mimeTypeUsageTypeChoices == null) {
            var form = CoreUtil.getHome().getGetMimeTypeUsageTypeChoicesForm();

            form.setDefaultMimeTypeUsageTypeChoice(mimeTypeUsageTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = CoreUtil.getHome().getMimeTypeUsageTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetMimeTypeUsageTypeChoicesResult)executionResult.getResult();
            mimeTypeUsageTypeChoices = result.getMimeTypeUsageTypeChoices();

            if(mimeTypeUsageTypeChoice == null)
                mimeTypeUsageTypeChoice = mimeTypeUsageTypeChoices.getDefaultValue();
        }
    }
    
    public String getComponentVendorName() {
        return componentVendorName;
    }

    public void setComponentVendorName(String componentVendorName) {
        this.componentVendorName = componentVendorName;
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }
    
    public void setMessageTypeName(String messageTypeName) {
        this.messageTypeName = messageTypeName;
    }
    
    public String getMessageTypeName() {
        return messageTypeName;
    }
    
    public List<LabelValueBean> getMimeTypeUsageTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupMimeTypeUsageTypeChoices();
        if(mimeTypeUsageTypeChoices != null)
            choices = convertChoices(mimeTypeUsageTypeChoices);
        
        return choices;
    }
    
    public void setMimeTypeUsageTypeChoice(String mimeTypeUsageTypeChoice) {
        this.mimeTypeUsageTypeChoice = mimeTypeUsageTypeChoice;
    }
    
    public String getMimeTypeUsageTypeChoice()
            throws NamingException {
        setupMimeTypeUsageTypeChoices();
        return mimeTypeUsageTypeChoice;
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
    
}
