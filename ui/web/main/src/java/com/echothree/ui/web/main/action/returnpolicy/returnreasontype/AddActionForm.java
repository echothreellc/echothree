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

package com.echothree.ui.web.main.action.returnpolicy.returnreasontype;

import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.result.GetReturnTypeChoicesResult;
import com.echothree.model.control.returnpolicy.common.choice.ReturnTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ReturnReasonTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ReturnTypeChoicesBean returnTypeChoices;
    
    private String returnKindName;
    private String returnReasonName;
    private String returnTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupReturnTypeChoices()
            throws NamingException {
        if(returnTypeChoices == null) {
            var form = ReturnPolicyUtil.getHome().getGetReturnTypeChoicesForm();

            form.setReturnKindName(returnKindName);
            form.setDefaultReturnTypeChoice(returnTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ReturnPolicyUtil.getHome().getReturnTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getReturnTypeChoicesResult = (GetReturnTypeChoicesResult)executionResult.getResult();
            returnTypeChoices = getReturnTypeChoicesResult.getReturnTypeChoices();

            if(returnTypeChoice == null) {
                returnTypeChoice = returnTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getReturnKindName() {
        return returnKindName;
    }
    
    public void setReturnKindName(String returnKindName) {
        this.returnKindName = returnKindName;
    }
    
    public String getReturnReasonName() {
        return returnReasonName;
    }
    
    public void setReturnReasonName(String returnReasonName) {
        this.returnReasonName = returnReasonName;
    }
    
    public String getReturnTypeChoice()
            throws NamingException {
        setupReturnTypeChoices();
        
        return returnTypeChoice;
    }
    
    public void setReturnTypeChoice(String returnTypeChoice) {
        this.returnTypeChoice = returnTypeChoice;
    }
    
    public List<LabelValueBean> getReturnTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupReturnTypeChoices();
        if(returnTypeChoices != null) {
            choices = convertChoices(returnTypeChoices);
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
