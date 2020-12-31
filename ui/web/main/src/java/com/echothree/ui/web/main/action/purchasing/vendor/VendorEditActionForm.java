// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.control.user.accounting.common.form.GetGlAccountChoicesForm;
import com.echothree.control.user.accounting.common.result.GetGlAccountChoicesResult;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.form.GetCancellationPolicyChoicesForm;
import com.echothree.control.user.cancellationpolicy.common.result.GetCancellationPolicyChoicesResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.GetItemAliasTypeChoicesForm;
import com.echothree.control.user.item.common.result.GetItemAliasTypeChoicesResult;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.form.GetReturnPolicyChoicesForm;
import com.echothree.control.user.returnpolicy.common.result.GetReturnPolicyChoicesResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.form.GetVendorTypeChoicesForm;
import com.echothree.control.user.vendor.common.result.GetVendorTypeChoicesResult;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.common.choice.GlAccountChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.item.common.choice.ItemAliasTypeChoicesBean;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.common.choice.ReturnPolicyChoicesBean;
import com.echothree.model.control.vendor.common.choice.VendorTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BasePersonActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="VendorEdit")
public class VendorEditActionForm
        extends BasePersonActionForm {
    
    private VendorTypeChoicesBean vendorTypeChoices;
    private ItemAliasTypeChoicesBean defaultItemAliasTypeChoices;
    private CancellationPolicyChoicesBean cancellationPolicyChoices;
    private ReturnPolicyChoicesBean returnPolicyChoices;
    private GlAccountChoicesBean apGlAccountChoices;
    
    private String originalVendorName;
    private String vendorName;
    private String vendorTypeChoice;
    private String minimumPurchaseOrderLines;
    private String maximumPurchaseOrderLines;
    private String minimumPurchaseOrderAmount;
    private String maximumPurchaseOrderAmount;
    private Boolean useItemPurchasingCategories;
    private String defaultItemAliasTypeChoice;
    private String name;
    private String cancellationPolicyChoice;
    private String returnPolicyChoice;
    private String apGlAccountChoice;
    private Boolean holdUntilComplete;
    private Boolean allowBackorders;
    private Boolean allowSubstitutions;
    private Boolean allowCombiningShipments;
    private Boolean requireReference;
    private Boolean allowReferenceDuplicates;
    private String referenceValidationPattern;
    
    public void setupVendorTypeChoices() {
        if(vendorTypeChoices == null) {
            try {
                GetVendorTypeChoicesForm form = VendorUtil.getHome().getGetVendorTypeChoicesForm();
                
                form.setDefaultVendorTypeChoice(vendorTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = VendorUtil.getHome().getVendorTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetVendorTypeChoicesResult getVendorTypeChoicesResult = (GetVendorTypeChoicesResult)executionResult.getResult();
                vendorTypeChoices = getVendorTypeChoicesResult.getVendorTypeChoices();
                
                if(vendorTypeChoice == null) {
                    vendorTypeChoice = vendorTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, vendorTypeChoices remains null, no default
            }
        }
    }
    
    public void setupDefaultItemAliasTypeChoices() {
        if(defaultItemAliasTypeChoices == null) {
            try {
                GetItemAliasTypeChoicesForm form = ItemUtil.getHome().getGetItemAliasTypeChoicesForm();
                
                form.setDefaultItemAliasTypeChoice(defaultItemAliasTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = ItemUtil.getHome().getItemAliasTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetItemAliasTypeChoicesResult getItemAliasTypeChoicesResult = (GetItemAliasTypeChoicesResult)executionResult.getResult();
                defaultItemAliasTypeChoices = getItemAliasTypeChoicesResult.getItemAliasTypeChoices();
                
                if(defaultItemAliasTypeChoice == null) {
                    defaultItemAliasTypeChoice = defaultItemAliasTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, defaultItemAliasTypeChoices remains null, no default
            }
        }
    }
    
    public void setupCancellationPolicyChoices() {
        if(cancellationPolicyChoices == null) {
            try {
                GetCancellationPolicyChoicesForm form = CancellationPolicyUtil.getHome().getGetCancellationPolicyChoicesForm();

                form.setCancellationKindName(CancellationKinds.VENDOR_CANCELLATION.name());
                form.setDefaultCancellationPolicyChoice(cancellationPolicyChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());

                CommandResult commandResult = CancellationPolicyUtil.getHome().getCancellationPolicyChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCancellationPolicyChoicesResult result = (GetCancellationPolicyChoicesResult)executionResult.getResult();
                cancellationPolicyChoices = result.getCancellationPolicyChoices();

                if(cancellationPolicyChoice == null)
                    cancellationPolicyChoice = cancellationPolicyChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, cancellationPolicyChoices remains null, no
            }
        }
    }

    public void setupReturnPolicyChoices() {
        if(returnPolicyChoices == null) {
            try {
                GetReturnPolicyChoicesForm form = ReturnPolicyUtil.getHome().getGetReturnPolicyChoicesForm();

                form.setReturnKindName(ReturnKinds.VENDOR_RETURN.name());
                form.setDefaultReturnPolicyChoice(returnPolicyChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());

                CommandResult commandResult = ReturnPolicyUtil.getHome().getReturnPolicyChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetReturnPolicyChoicesResult result = (GetReturnPolicyChoicesResult)executionResult.getResult();
                returnPolicyChoices = result.getReturnPolicyChoices();

                if(returnPolicyChoice == null)
                    returnPolicyChoice = returnPolicyChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, returnPolicyChoices remains null, no
            }
        }
    }

    public void setupApGlAccountChoices() {
        if(apGlAccountChoices == null) {
            try {
                GetGlAccountChoicesForm form = AccountingUtil.getHome().getGetGlAccountChoicesForm();
                
                form.setGlAccountCategoryName(AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE);
                form.setDefaultGlAccountChoice(apGlAccountChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getGlAccountChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetGlAccountChoicesResult getGlAccountChoicesResult = (GetGlAccountChoicesResult)executionResult.getResult();
                apGlAccountChoices = getGlAccountChoicesResult.getGlAccountChoices();
                
                if(apGlAccountChoice == null) {
                    apGlAccountChoice = apGlAccountChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, apGlAccountChoices remains null, no default
            }
        }
    }
    
    public String getOriginalVendorName() {
        return originalVendorName;
    }

    public void setOriginalVendorName(String originalVendorName) {
        this.originalVendorName = originalVendorName;
    }

    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    public List<LabelValueBean> getVendorTypeChoices() {
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
    
    public String getVendorTypeChoice() {
        setupVendorTypeChoices();
        
        return vendorTypeChoice;
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
    
    public List<LabelValueBean> getDefaultItemAliasTypeChoices() {
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
    
    public String getDefaultItemAliasTypeChoice() {
        setupDefaultItemAliasTypeChoices();
        return defaultItemAliasTypeChoice;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<LabelValueBean> getCancellationPolicyChoices() {
        List<LabelValueBean> choices = null;

        setupCancellationPolicyChoices();
        if(cancellationPolicyChoices != null)
            choices = convertChoices(cancellationPolicyChoices);

        return choices;
    }

    public void setCancellationPolicyChoice(String cancellationPolicyChoice) {
        this.cancellationPolicyChoice = cancellationPolicyChoice;
    }

    public String getCancellationPolicyChoice() {
        setupCancellationPolicyChoices();

        return cancellationPolicyChoice;
    }

    public List<LabelValueBean> getReturnPolicyChoices() {
        List<LabelValueBean> choices = null;

        setupReturnPolicyChoices();
        if(returnPolicyChoices != null)
            choices = convertChoices(returnPolicyChoices);

        return choices;
    }

    public void setReturnPolicyChoice(String returnPolicyChoice) {
        this.returnPolicyChoice = returnPolicyChoice;
    }

    public String getReturnPolicyChoice() {
        setupReturnPolicyChoices();

        return returnPolicyChoice;
    }

    public List<LabelValueBean> getApGlAccountChoices() {
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
    
    public String getApGlAccountChoice() {
        setupApGlAccountChoices();
        
        return apGlAccountChoice;
    }
    
    public Boolean getRequireReference() {
        return requireReference;
    }

    public Boolean getHoldUntilComplete() {
        return holdUntilComplete;
    }

    public void setHoldUntilComplete(Boolean holdUntilComplete) {
        this.holdUntilComplete = holdUntilComplete;
    }

    public Boolean getAllowBackorders() {
        return allowBackorders;
    }

    public void setAllowBackorders(Boolean allowBackorders) {
        this.allowBackorders = allowBackorders;
    }

    public Boolean getAllowSubstitutions() {
        return allowSubstitutions;
    }

    public void setAllowSubstitutions(Boolean allowSubstitutions) {
        this.allowSubstitutions = allowSubstitutions;
    }

    public Boolean getAllowCombiningShipments() {
        return allowCombiningShipments;
    }

    public void setAllowCombiningShipments(Boolean allowCombiningShipments) {
        this.allowCombiningShipments = allowCombiningShipments;
    }

    public void setRequireReference(Boolean requireReference) {
        this.requireReference = requireReference;
    }

    public Boolean getAllowReferenceDuplicates() {
        return allowReferenceDuplicates;
    }

    public void setAllowReferenceDuplicates(Boolean allowReferenceDuplicates) {
        this.allowReferenceDuplicates = allowReferenceDuplicates;
    }
    
    public String getReferenceValidationPattern() {
        return referenceValidationPattern;
    }

    public void setReferenceValidationPattern(String referenceValidationPattern) {
        this.referenceValidationPattern = referenceValidationPattern;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setUseItemPurchasingCategories(Boolean.FALSE);
        setHoldUntilComplete(Boolean.FALSE);
        setAllowBackorders(Boolean.FALSE);
        setAllowSubstitutions(Boolean.FALSE);
        setAllowCombiningShipments(Boolean.FALSE);
        setRequireReference(Boolean.FALSE);
        setAllowReferenceDuplicates(Boolean.FALSE);
    }

}
