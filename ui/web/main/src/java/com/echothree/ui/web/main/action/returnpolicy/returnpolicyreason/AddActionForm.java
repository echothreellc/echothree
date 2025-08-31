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

package com.echothree.ui.web.main.action.returnpolicy.returnpolicyreason;

import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.result.GetReturnReasonChoicesResult;
import com.echothree.model.control.returnpolicy.common.choice.ReturnReasonChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ReturnPolicyReasonAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ReturnReasonChoicesBean returnReasonChoices;
    
    private String returnKindName;
    private String returnPolicyName;
    private String returnReasonChoice;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupReturnReasonChoices()
            throws NamingException {
        if(returnReasonChoices == null) {
            var form = ReturnPolicyUtil.getHome().getGetReturnReasonChoicesForm();

            form.setReturnKindName(returnKindName);
            form.setDefaultReturnReasonChoice(returnReasonChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ReturnPolicyUtil.getHome().getReturnReasonChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getReturnReasonChoicesResult = (GetReturnReasonChoicesResult)executionResult.getResult();
            returnReasonChoices = getReturnReasonChoicesResult.getReturnReasonChoices();

            if(returnReasonChoice == null) {
                returnReasonChoice = returnReasonChoices.getDefaultValue();
            }
        }
    }
    
    public String getReturnKindName() {
        return returnKindName;
    }
    
    public void setReturnKindName(String returnKindName) {
        this.returnKindName = returnKindName;
    }
    
    public String getReturnPolicyName() {
        return returnPolicyName;
    }
    
    public void setReturnPolicyName(String returnPolicyName) {
        this.returnPolicyName = returnPolicyName;
    }
    
    public String getReturnReasonChoice()
            throws NamingException {
        setupReturnReasonChoices();
        
        return returnReasonChoice;
    }
    
    public void setReturnReasonChoice(String returnReasonChoice) {
        this.returnReasonChoice = returnReasonChoice;
    }
    
    public List<LabelValueBean> getReturnReasonChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupReturnReasonChoices();
        if(returnReasonChoices != null) {
            choices = convertChoices(returnReasonChoices);
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }
    
}
