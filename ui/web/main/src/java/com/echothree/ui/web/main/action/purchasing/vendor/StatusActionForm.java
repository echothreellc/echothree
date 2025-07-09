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

package com.echothree.ui.web.main.action.purchasing.vendor;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetVendorStatusChoicesResult;
import com.echothree.model.control.vendor.common.choice.VendorStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="VendorStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private VendorStatusChoicesBean vendorStatusChoices;
    
    private String returnUrl;
    private String vendorName;
    private String vendorStatusChoice;
    
    public void setupVendorStatusChoices()
            throws NamingException {
        if(vendorStatusChoices == null) {
            var form = PartyUtil.getHome().getGetVendorStatusChoicesForm();

            form.setVendorName(vendorName);
            form.setDefaultVendorStatusChoice(vendorStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = PartyUtil.getHome().getVendorStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetVendorStatusChoicesResult)executionResult.getResult();
            vendorStatusChoices = result.getVendorStatusChoices();

            if(vendorStatusChoice == null) {
                vendorStatusChoice = vendorStatusChoices.getDefaultValue();
            }
        }
    }
    
    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    public String getVendorStatusChoice() {
        return vendorStatusChoice;
    }
    
    public void setVendorStatusChoice(String vendorStatusChoice) {
        this.vendorStatusChoice = vendorStatusChoice;
    }
    
    public List<LabelValueBean> getVendorStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupVendorStatusChoices();
        if(vendorStatusChoices != null) {
            choices = convertChoices(vendorStatusChoices);
        }
        
        return choices;
    }
    
}
