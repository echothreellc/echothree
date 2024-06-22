// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.advertising.use;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.form.GetUseTypeChoicesForm;
import com.echothree.control.user.offer.common.result.GetUseTypeChoicesResult;
import com.echothree.model.control.offer.common.choice.UseTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="UseAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UseTypeChoicesBean useTypeChoices;
    
    private String useName;
    private String useTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupUseTypeChoices() {
        if(useTypeChoices == null) {
            try {
                GetUseTypeChoicesForm form = OfferUtil.getHome().getGetUseTypeChoicesForm();
                
                form.setDefaultUseTypeChoice(useTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = OfferUtil.getHome().getUseTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetUseTypeChoicesResult result = (GetUseTypeChoicesResult)executionResult.getResult();
                useTypeChoices = result.getUseTypeChoices();
                
                if(useTypeChoice == null)
                    useTypeChoice = useTypeChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, useTypeChoices remains null, no default
            }
        }
    }
    
    public void setUseName(String useName) {
        this.useName = useName;
    }
    
    public String getUseName() {
        return useName;
    }
    
    public String getUseTypeChoice() {
        setupUseTypeChoices();
        
        return useTypeChoice;
    }
    
    public void setUseTypeChoice(String useTypeChoice) {
        this.useTypeChoice = useTypeChoice;
    }
    
    public List<LabelValueBean> getUseTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupUseTypeChoices();
        if(useTypeChoices != null) {
            choices = convertChoices(useTypeChoices);
        }
        
        return choices;
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
        
        isDefault = Boolean.FALSE;
    }
    
}
