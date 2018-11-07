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

package com.echothree.ui.web.main.action.cancellationpolicy.cancellationpolicyreason;

import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.form.GetCancellationReasonChoicesForm;
import com.echothree.control.user.cancellationpolicy.common.result.GetCancellationReasonChoicesResult;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationReasonChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CancellationPolicyReasonAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private CancellationReasonChoicesBean cancellationReasonChoices;
    
    private String cancellationKindName;
    private String cancellationPolicyName;
    private String cancellationReasonChoice;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupCancellationReasonChoices() {
        if(cancellationReasonChoices == null) {
            try {
                GetCancellationReasonChoicesForm form = CancellationPolicyUtil.getHome().getGetCancellationReasonChoicesForm();
                
                form.setCancellationKindName(cancellationKindName);
                form.setDefaultCancellationReasonChoice(cancellationReasonChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = CancellationPolicyUtil.getHome().getCancellationReasonChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCancellationReasonChoicesResult getCancellationReasonChoicesResult = (GetCancellationReasonChoicesResult)executionResult.getResult();
                cancellationReasonChoices = getCancellationReasonChoicesResult.getCancellationReasonChoices();
                
                if(cancellationReasonChoice == null) {
                    cancellationReasonChoice = cancellationReasonChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, cancellationReasonChoices remains null, no default
            }
        }
    }
    
    public String getCancellationKindName() {
        return cancellationKindName;
    }
    
    public void setCancellationKindName(String cancellationKindName) {
        this.cancellationKindName = cancellationKindName;
    }
    
    public String getCancellationPolicyName() {
        return cancellationPolicyName;
    }
    
    public void setCancellationPolicyName(String cancellationPolicyName) {
        this.cancellationPolicyName = cancellationPolicyName;
    }
    
    public String getCancellationReasonChoice() {
        setupCancellationReasonChoices();
        
        return cancellationReasonChoice;
    }
    
    public void setCancellationReasonChoice(String cancellationReasonChoice) {
        this.cancellationReasonChoice = cancellationReasonChoice;
    }
    
    public List<LabelValueBean> getCancellationReasonChoices() {
        List<LabelValueBean> choices = null;
        
        setupCancellationReasonChoices();
        if(cancellationReasonChoices != null) {
            choices = convertChoices(cancellationReasonChoices);
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
        
        isDefault = Boolean.FALSE;
    }
    
}
