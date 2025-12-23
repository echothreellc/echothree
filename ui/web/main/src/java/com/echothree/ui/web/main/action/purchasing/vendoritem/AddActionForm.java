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

package com.echothree.ui.web.main.action.purchasing.vendoritem;

import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.result.GetCancellationPolicyChoicesResult;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.result.GetReturnPolicyChoicesResult;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.common.choice.ReturnPolicyChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="VendorItemAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private CancellationPolicyChoicesBean cancellationPolicyChoices;
    private ReturnPolicyChoicesBean returnPolicyChoices;
    
    private String itemName;
    private String vendorName;
    private String vendorItemName;
    private String description;
    private String priority;
    private String cancellationPolicyChoice;
    private String returnPolicyChoice;
    
    public void setupCancellationPolicyChoices()
            throws NamingException {
        if(cancellationPolicyChoices == null) {
            var form = CancellationPolicyUtil.getHome().getGetCancellationPolicyChoicesForm();

            form.setCancellationKindName(CancellationKinds.VENDOR_CANCELLATION.name());
            form.setDefaultCancellationPolicyChoice(cancellationPolicyChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = CancellationPolicyUtil.getHome().getCancellationPolicyChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCancellationPolicyChoicesResult)executionResult.getResult();
            cancellationPolicyChoices = result.getCancellationPolicyChoices();

            if(cancellationPolicyChoice == null) {
                cancellationPolicyChoice = cancellationPolicyChoices.getDefaultValue();
            }
        }
    }
    
    public void setupReturnPolicyChoices()
            throws NamingException {
        if(returnPolicyChoices == null) {
            var form = ReturnPolicyUtil.getHome().getGetReturnPolicyChoicesForm();

            form.setReturnKindName(ReturnKinds.VENDOR_RETURN.name());
            form.setDefaultReturnPolicyChoice(returnPolicyChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ReturnPolicyUtil.getHome().getReturnPolicyChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetReturnPolicyChoicesResult)executionResult.getResult();
            returnPolicyChoices = result.getReturnPolicyChoices();

            if(returnPolicyChoice == null) {
                returnPolicyChoice = returnPolicyChoices.getDefaultValue();
            }
        }
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    public String getVendorItemName() {
        return vendorItemName;
    }
    
    public void setVendorItemName(String vendorItemName) {
        this.vendorItemName = vendorItemName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public List<LabelValueBean> getCancellationPolicyChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupCancellationPolicyChoices();
        if(cancellationPolicyChoices != null)
            choices = convertChoices(cancellationPolicyChoices);
        
        return choices;
    }
    
    public void setCancellationPolicyChoice(String cancellationPolicyChoice) {
        this.cancellationPolicyChoice = cancellationPolicyChoice;
    }
    
    public String getCancellationPolicyChoice()
            throws NamingException {
        setupCancellationPolicyChoices();
        
        return cancellationPolicyChoice;
    }
    
    public List<LabelValueBean> getReturnPolicyChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupReturnPolicyChoices();
        if(returnPolicyChoices != null)
            choices = convertChoices(returnPolicyChoices);
        
        return choices;
    }
    
    public void setReturnPolicyChoice(String returnPolicyChoice) {
        this.returnPolicyChoice = returnPolicyChoice;
    }
    
    public String getReturnPolicyChoice()
            throws NamingException {
        setupReturnPolicyChoices();
        
        return returnPolicyChoice;
    }
    
}
