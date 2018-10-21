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

package com.echothree.ui.web.main.action.purchasing.vendoritem;

import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.remote.form.GetVendorItemStatusChoicesForm;
import com.echothree.control.user.vendor.remote.result.GetVendorItemStatusChoicesResult;
import com.echothree.model.control.vendor.remote.choice.VendorItemStatusChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="VendorItemStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private VendorItemStatusChoicesBean vendorItemStatusChoices;
    
    private String returnUrl;
    private String vendorName;
    private String vendorItemName;
    private String vendorItemStatusChoice;
    
    public void setupVendorItemStatusChoices() {
        if(vendorItemStatusChoices == null) {
            try {
                GetVendorItemStatusChoicesForm form = VendorUtil.getHome().getGetVendorItemStatusChoicesForm();
                
                form.setVendorName(vendorName);
                form.setVendorItemName(vendorItemName);
                form.setDefaultVendorItemStatusChoice(vendorItemStatusChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = VendorUtil.getHome().getVendorItemStatusChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetVendorItemStatusChoicesResult result = (GetVendorItemStatusChoicesResult)executionResult.getResult();
                vendorItemStatusChoices = result.getVendorItemStatusChoices();
                
                if(vendorItemStatusChoice == null) {
                    vendorItemStatusChoice = vendorItemStatusChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, vendorItemStatusChoices remains null, no default
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

    public String getVendorItemName() {
        return vendorItemName;
    }

    public void setVendorItemName(String vendorItemName) {
        this.vendorItemName = vendorItemName;
    }

    public String getVendorItemStatusChoice() {
        return vendorItemStatusChoice;
    }
    
    public void setVendorItemStatusChoice(String vendorItemStatusChoice) {
        this.vendorItemStatusChoice = vendorItemStatusChoice;
    }
    
    public List<LabelValueBean> getVendorItemStatusChoices() {
        List<LabelValueBean> choices = null;
        
        setupVendorItemStatusChoices();
        if(vendorItemStatusChoices != null) {
            choices = convertChoices(vendorItemStatusChoices);
        }
        
        return choices;
    }
    
}
