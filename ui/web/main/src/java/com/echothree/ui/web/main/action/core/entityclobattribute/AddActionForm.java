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

package com.echothree.ui.web.main.action.core.entityclobattribute;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EntityClobAttributeAdd")
public class AddActionForm
        extends BaseLanguageActionForm {
    
    private MimeTypeChoicesBean mimeTypeChoices;
    
    private String entityAttributeName;
    private String entityRef;
    private String returnUrl;
    private String mimeTypeChoice;
    private String clobAttribute;
    
    private void setupMimeTypeChoices() {
        if(mimeTypeChoices == null) {
            try {
                var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();
                
                commandForm.setDefaultMimeTypeChoice(mimeTypeChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));
                commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

                var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetMimeTypeChoicesResult)executionResult.getResult();
                mimeTypeChoices = result.getMimeTypeChoices();
                
                if(mimeTypeChoice == null) {
                    mimeTypeChoice = mimeTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, mimeTypeChoices remains null, no default
            }
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
    
    public List<LabelValueBean> getMimeTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupMimeTypeChoices();
        if(mimeTypeChoices != null)
            choices = convertChoices(mimeTypeChoices);
        
        return choices;
    }
    
    public void setMimeTypeChoice(String mimeTypeChoice) {
        this.mimeTypeChoice = mimeTypeChoice;
    }
    
    public String getMimeTypeChoice() {
        setupMimeTypeChoices();
        return mimeTypeChoice;
    }
    
    public String getClobAttribute() {
        return clobAttribute;
    }
    
    public void setClobAttribute(String clobAttribute) {
        this.clobAttribute = clobAttribute;
    }
    
}
