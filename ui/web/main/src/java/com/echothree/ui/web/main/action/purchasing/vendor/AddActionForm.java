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

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetGlAccountChoicesResult;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.result.GetCancellationPolicyChoicesResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemAliasTypeChoicesResult;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.result.GetReturnPolicyChoicesResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.GetVendorTypeChoicesResult;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.common.choice.GlAccountChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.item.common.choice.ItemAliasTypeChoicesBean;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.common.choice.ReturnPolicyChoicesBean;
import com.echothree.model.control.vendor.common.choice.VendorTypeChoicesBean;
import com.echothree.view.client.web.struts.BasePersonActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="VendorAdd")
public class AddActionForm
        extends BasePersonActionForm {
    
    private VendorTypeChoicesBean vendorTypeChoices;
    private ItemAliasTypeChoicesBean defaultItemAliasTypeChoices;
    private CancellationPolicyChoicesBean cancellationPolicyChoices;
    private ReturnPolicyChoicesBean returnPolicyChoices;
    private GlAccountChoicesBean apGlAccountChoices;
    
    private String vendorName;
    private String vendorTypeChoice;
    private String cancellationPolicyChoice;
    private String returnPolicyChoice;
    private String apGlAccountChoice;
    private String minimumPurchaseOrderLines;
    private String maximumPurchaseOrderLines;
    private String minimumPurchaseOrderAmount;
    private String maximumPurchaseOrderAmount;
    private Boolean useItemPurchasingCategories;
    private String defaultItemAliasTypeChoice;
    private String name;
    private String emailAddress;
    private Boolean allowSolicitation;

    private void setupVendorTypeChoices()
            throws NamingException {
        if(vendorTypeChoices == null) {
            var form = VendorUtil.getHome().getGetVendorTypeChoicesForm();

            form.setDefaultVendorTypeChoice(vendorTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = VendorUtil.getHome().getVendorTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetVendorTypeChoicesResult)executionResult.getResult();
            vendorTypeChoices = result.getVendorTypeChoices();

            if(vendorTypeChoice == null) {
                vendorTypeChoice = vendorTypeChoices.getDefaultValue();
            }
        }
    }
    
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

            if(cancellationPolicyChoice == null)
                cancellationPolicyChoice = cancellationPolicyChoices.getDefaultValue();
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

            if(returnPolicyChoice == null)
                returnPolicyChoice = returnPolicyChoices.getDefaultValue();
        }
    }

    public void setupApGlAccountChoices()
            throws NamingException {
        if(apGlAccountChoices == null) {
            var form = AccountingUtil.getHome().getGetGlAccountChoicesForm();

            form.setGlAccountCategoryName(AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE);
            form.setDefaultGlAccountChoice(apGlAccountChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = AccountingUtil.getHome().getGlAccountChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getGlAccountChoicesResult = (GetGlAccountChoicesResult)executionResult.getResult();
            apGlAccountChoices = getGlAccountChoicesResult.getGlAccountChoices();

            if(apGlAccountChoice == null) {
                apGlAccountChoice = apGlAccountChoices.getDefaultValue();
            }
        }
    }

    public void setupDefaultItemAliasTypeChoices()
            throws NamingException {
        if(defaultItemAliasTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemAliasTypeChoicesForm();

            form.setDefaultItemAliasTypeChoice(defaultItemAliasTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ItemUtil.getHome().getItemAliasTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getItemAliasTypeChoicesResult = (GetItemAliasTypeChoicesResult)executionResult.getResult();
            defaultItemAliasTypeChoices = getItemAliasTypeChoicesResult.getItemAliasTypeChoices();

            if(defaultItemAliasTypeChoice == null) {
                defaultItemAliasTypeChoice = defaultItemAliasTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    public List<LabelValueBean> getVendorTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupVendorTypeChoices();
        if(vendorTypeChoices != null) {
            choices = convertChoices(vendorTypeChoices);
        }
        
        return choices;
    }
    
    public void setVendorTypeChoice(String vendorTypeChoice) {
        this.vendorTypeChoice = vendorTypeChoice;
    }
    
    public String getVendorTypeChoice()
            throws NamingException {
        setupVendorTypeChoices();
        
        return vendorTypeChoice;
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

    public List<LabelValueBean> getApGlAccountChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupApGlAccountChoices();
        if(apGlAccountChoices != null) {
            choices = convertChoices(apGlAccountChoices);
        }

        return choices;
    }

    public void setApGlAccountChoice(String apGlAccountChoice) {
        this.apGlAccountChoice = apGlAccountChoice;
    }

    public String getApGlAccountChoice()
            throws NamingException {
        setupApGlAccountChoices();

        return apGlAccountChoice;
    }

   public String getMinimumPurchaseOrderLines() {
        return minimumPurchaseOrderLines;
    }
    
    public void setMinimumPurchaseOrderLines(String minimumPurchaseOrderLines) {
        this.minimumPurchaseOrderLines = minimumPurchaseOrderLines;
    }
    
    public String getMaximumPurchaseOrderLines() {
        return maximumPurchaseOrderLines;
    }
    
    public void setMaximumPurchaseOrderLines(String maximumPurchaseOrderLines) {
        this.maximumPurchaseOrderLines = maximumPurchaseOrderLines;
    }
    
    public String getMinimumPurchaseOrderAmount() {
        return minimumPurchaseOrderAmount;
    }
    
    public void setMinimumPurchaseOrderAmount(String minimumPurchaseOrderAmount) {
        this.minimumPurchaseOrderAmount = minimumPurchaseOrderAmount;
    }
    
    public String getMaximumPurchaseOrderAmount() {
        return maximumPurchaseOrderAmount;
    }
    
    public void setMaximumPurchaseOrderAmount(String maximumPurchaseOrderAmount) {
        this.maximumPurchaseOrderAmount = maximumPurchaseOrderAmount;
    }
    
    public Boolean getUseItemPurchasingCategories() {
        return useItemPurchasingCategories;
    }
    
    public void setUseItemPurchasingCategories(Boolean useItemPurchasingCategories) {
        this.useItemPurchasingCategories = useItemPurchasingCategories;
    }
    
    public List<LabelValueBean> getDefaultItemAliasTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupDefaultItemAliasTypeChoices();
        if(defaultItemAliasTypeChoices != null) {
            choices = convertChoices(defaultItemAliasTypeChoices);
        }
        
        return choices;
    }
    
    public void setDefaultItemAliasTypeChoice(String defaultItemAliasTypeChoice) {
        this.defaultItemAliasTypeChoice = defaultItemAliasTypeChoice;
    }
    
    public String getDefaultItemAliasTypeChoice()
            throws NamingException {
        setupDefaultItemAliasTypeChoices();
        return defaultItemAliasTypeChoice;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public Boolean getAllowSolicitation() {
        return allowSolicitation;
    }
    
    public void setAllowSolicitation(Boolean allowSolicitation) {
        this.allowSolicitation = allowSolicitation;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setAllowSolicitation(false);
        setUseItemPurchasingCategories(false);
    }
    
}
