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

package com.echothree.ui.web.main.action.returnpolicy.returnkind;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.result.GetSequenceTypeChoicesResult;
import com.echothree.model.control.sequence.common.choice.SequenceTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ReturnKindAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SequenceTypeChoicesBean returnSequenceTypeChoices;
    
    private String returnKindName;
    private String returnSequenceTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public void setupReturnSequenceTypeChoices()
            throws NamingException {
        if(returnSequenceTypeChoices == null) {
            var form = SequenceUtil.getHome().getGetSequenceTypeChoicesForm();

            form.setDefaultSequenceTypeChoice(returnSequenceTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = SequenceUtil.getHome().getSequenceTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSequenceTypeChoicesResult)executionResult.getResult();
            returnSequenceTypeChoices = result.getSequenceTypeChoices();

            if(returnSequenceTypeChoice == null)
                returnSequenceTypeChoice = returnSequenceTypeChoices.getDefaultValue();
        }
    }
    
    public void setReturnKindName(String returnKindName) {
        this.returnKindName = returnKindName;
    }
    
    public String getReturnKindName() {
        return returnKindName;
    }
    
    public List<LabelValueBean> getReturnSequenceTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupReturnSequenceTypeChoices();
        if(returnSequenceTypeChoices != null) {
            choices = convertChoices(returnSequenceTypeChoices);
        }
        
        return choices;
    }
    
    public void setReturnSequenceTypeChoice(String returnSequenceTypeChoice) {
        this.returnSequenceTypeChoice = returnSequenceTypeChoice;
    }
    
    public String getReturnSequenceTypeChoice()
            throws NamingException {
        setupReturnSequenceTypeChoices();
        
        return returnSequenceTypeChoice;
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
