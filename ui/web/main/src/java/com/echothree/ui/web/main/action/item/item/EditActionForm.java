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

package com.echothree.ui.web.main.action.item.item;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.GetItemAccountingCategoryChoicesForm;
import com.echothree.control.user.accounting.common.result.GetItemAccountingCategoryChoicesResult;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.form.GetCancellationPolicyChoicesForm;
import com.echothree.control.user.cancellationpolicy.common.result.GetCancellationPolicyChoicesResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.GetItemCategoryChoicesForm;
import com.echothree.control.user.item.common.result.GetItemCategoryChoicesResult;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.form.GetReturnPolicyChoicesForm;
import com.echothree.control.user.returnpolicy.common.result.GetReturnPolicyChoicesResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.form.GetItemPurchasingCategoryChoicesForm;
import com.echothree.control.user.vendor.common.result.GetItemPurchasingCategoryChoicesResult;
import com.echothree.model.control.accounting.common.choice.ItemAccountingCategoryChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.item.common.choice.ItemCategoryChoicesBean;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.common.choice.ReturnPolicyChoicesBean;
import com.echothree.model.control.vendor.common.choice.ItemPurchasingCategoryChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemEdit")
public class EditActionForm
        extends BaseActionForm {
    
    private ItemCategoryChoicesBean itemCategoryChoices;
    private ItemAccountingCategoryChoicesBean itemAccountingCategoryChoices;
    private ItemPurchasingCategoryChoicesBean itemPurchasingCategoryChoices;
    private CancellationPolicyChoicesBean cancellationPolicyChoices;
    private ReturnPolicyChoicesBean returnPolicyChoices;
    
    private String originalItemName;
    private String itemName;
    private String itemCategoryChoice;
    private String itemAccountingCategoryChoice;
    private String itemPurchasingCategoryChoice;
    private Boolean shippingChargeExempt;
    private String salesOrderStartTime;
    private String salesOrderEndTime;
    private String purchaseOrderStartTime;
    private String purchaseOrderEndTime;
    private String shippingStartTime;
    private String shippingEndTime;
    private Boolean allowClubDiscounts;
    private Boolean allowCouponDiscounts;
    private Boolean allowAssociatePayments;
    private String cancellationPolicyChoice;
    private String returnPolicyChoice;
    
    private void setupItemCategoryChoices() {
        if(itemCategoryChoices == null) {
            try {
                GetItemCategoryChoicesForm form = ItemUtil.getHome().getGetItemCategoryChoicesForm();
                
                form.setDefaultItemCategoryChoice(itemCategoryChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = ItemUtil.getHome().getItemCategoryChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetItemCategoryChoicesResult result = (GetItemCategoryChoicesResult)executionResult.getResult();
                itemCategoryChoices = result.getItemCategoryChoices();
                
                if(itemCategoryChoice == null) {
                    itemCategoryChoice = itemCategoryChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, itemCategoryChoices remains null, no default
            }
        }
    }
    
    private void setupItemAccountingCategoryChoices() {
        if(itemAccountingCategoryChoices == null) {
            try {
                GetItemAccountingCategoryChoicesForm form = AccountingUtil.getHome().getGetItemAccountingCategoryChoicesForm();
                
                form.setDefaultItemAccountingCategoryChoice(itemAccountingCategoryChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getItemAccountingCategoryChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetItemAccountingCategoryChoicesResult result = (GetItemAccountingCategoryChoicesResult)executionResult.getResult();
                itemAccountingCategoryChoices = result.getItemAccountingCategoryChoices();
                
                if(itemAccountingCategoryChoice == null) {
                    itemAccountingCategoryChoice = itemAccountingCategoryChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, itemAccountingCategoryChoices remains null, no default
            }
        }
    }
    
    private void setupItemPurchasingCategoryChoices() {
        if(itemPurchasingCategoryChoices == null) {
            try {
                GetItemPurchasingCategoryChoicesForm form = VendorUtil.getHome().getGetItemPurchasingCategoryChoicesForm();
                
                form.setDefaultItemPurchasingCategoryChoice(itemPurchasingCategoryChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = VendorUtil.getHome().getItemPurchasingCategoryChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetItemPurchasingCategoryChoicesResult result = (GetItemPurchasingCategoryChoicesResult)executionResult.getResult();
                itemPurchasingCategoryChoices = result.getItemPurchasingCategoryChoices();
                
                if(itemPurchasingCategoryChoice == null) {
                    itemPurchasingCategoryChoice = itemPurchasingCategoryChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, itemPurchasingCategoryChoices remains null, no default
            }
        }
    }
    
    public void setupCancellationPolicyChoices() {
        if(cancellationPolicyChoices == null) {
            try {
                GetCancellationPolicyChoicesForm form = CancellationPolicyUtil.getHome().getGetCancellationPolicyChoicesForm();
                
                form.setCancellationKindName(CancellationKinds.CUSTOMER_CANCELLATION.name());
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
                // failed, cancellationPolicyChoices remains null, no default
            }
        }
    }
    
    public void setupReturnPolicyChoices() {
        if(returnPolicyChoices == null) {
            try {
                GetReturnPolicyChoicesForm form = ReturnPolicyUtil.getHome().getGetReturnPolicyChoicesForm();
                
                form.setReturnKindName(ReturnKinds.CUSTOMER_RETURN.name());
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
                // failed, returnPolicyChoices remains null, no default
            }
        }
    }
    
    public String getOriginalItemName() {
        return originalItemName;
    }
    
    public void setOriginalItemName(String originalItemName) {
        this.originalItemName = originalItemName;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getItemCategoryChoice() {
        setupItemCategoryChoices();
        
        return itemCategoryChoice;
    }
    
    public void setItemCategoryChoice(String itemCategoryChoice) {
        this.itemCategoryChoice = itemCategoryChoice;
    }
    
    public List<LabelValueBean> getItemCategoryChoices() {
        List<LabelValueBean> choices = null;
        
        setupItemCategoryChoices();
        if(itemCategoryChoices != null) {
            choices = convertChoices(itemCategoryChoices);
        }
        
        return choices;
    }
    
    public String getItemAccountingCategoryChoice() {
        setupItemAccountingCategoryChoices();
        
        return itemAccountingCategoryChoice;
    }
    
    public void setItemAccountingCategoryChoice(String itemAccountingCategoryChoice) {
        this.itemAccountingCategoryChoice = itemAccountingCategoryChoice;
    }
    
    public List<LabelValueBean> getItemAccountingCategoryChoices() {
        List<LabelValueBean> choices = null;
        
        setupItemAccountingCategoryChoices();
        if(itemAccountingCategoryChoices != null) {
            choices = convertChoices(itemAccountingCategoryChoices);
        }
        
        return choices;
    }
    
    public String getItemPurchasingCategoryChoice() {
        setupItemPurchasingCategoryChoices();
        
        return itemPurchasingCategoryChoice;
    }
    
    public void setItemPurchasingCategoryChoice(String itemPurchasingCategoryChoice) {
        this.itemPurchasingCategoryChoice = itemPurchasingCategoryChoice;
    }
    
    public List<LabelValueBean> getItemPurchasingCategoryChoices() {
        List<LabelValueBean> choices = null;
        
        setupItemPurchasingCategoryChoices();
        if(itemPurchasingCategoryChoices != null) {
            choices = convertChoices(itemPurchasingCategoryChoices);
        }
        
        return choices;
    }
    
    public Boolean getShippingChargeExempt() {
        return shippingChargeExempt;
    }
    
    public void setShippingChargeExempt(Boolean shippingChargeExempt) {
        this.shippingChargeExempt = shippingChargeExempt;
    }
    
    public String getSalesOrderStartTime() {
        return salesOrderStartTime;
    }
    
    public void setSalesOrderStartTime(String salesOrderStartTime) {
        this.salesOrderStartTime = salesOrderStartTime;
    }
    
    public String getSalesOrderEndTime() {
        return salesOrderEndTime;
    }
    
    public void setSalesOrderEndTime(String salesOrderEndTime) {
        this.salesOrderEndTime = salesOrderEndTime;
    }
    
    public String getPurchaseOrderStartTime() {
        return purchaseOrderStartTime;
    }
    
    public void setPurchaseOrderStartTime(String purchaseOrderStartTime) {
        this.purchaseOrderStartTime = purchaseOrderStartTime;
    }
    
    public String getPurchaseOrderEndTime() {
        return purchaseOrderEndTime;
    }
    
    public void setPurchaseOrderEndTime(String purchaseOrderEndTime) {
        this.purchaseOrderEndTime = purchaseOrderEndTime;
    }
    
    public String getShippingStartTime() {
        return shippingStartTime;
    }
    
    public void setShippingStartTime(String shippingStartTime) {
        this.shippingStartTime = shippingStartTime;
    }
    
    public String getShippingEndTime() {
        return shippingEndTime;
    }
    
    public void setShippingEndTime(String shippingEndTime) {
        this.shippingEndTime = shippingEndTime;
    }
    
    public Boolean getAllowClubDiscounts() {
        return allowClubDiscounts;
    }
    
    public void setAllowClubDiscounts(Boolean allowClubDiscounts) {
        this.allowClubDiscounts = allowClubDiscounts;
    }
    
    public Boolean getAllowCouponDiscounts() {
        return allowCouponDiscounts;
    }
    
    public void setAllowCouponDiscounts(Boolean allowCouponDiscounts) {
        this.allowCouponDiscounts = allowCouponDiscounts;
    }
    
    public Boolean getAllowAssociatePayments() {
        return allowAssociatePayments;
    }
    
    public void setAllowAssociatePayments(Boolean allowAssociatePayments) {
        this.allowAssociatePayments = allowAssociatePayments;
    }
    
    public List<LabelValueBean> getCancellationPolicyChoices() {
        List<LabelValueBean> choices = null;
        
        setupCancellationPolicyChoices();
        if(cancellationPolicyChoices != null) {
            choices = convertChoices(cancellationPolicyChoices);
        }
        
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
        if(returnPolicyChoices != null) {
            choices = convertChoices(returnPolicyChoices);
        }
        
        return choices;
    }
    
    public void setReturnPolicyChoice(String returnPolicyChoice) {
        this.returnPolicyChoice = returnPolicyChoice;
    }
    
    public String getReturnPolicyChoice() {
        setupReturnPolicyChoices();
        
        return returnPolicyChoice;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setShippingChargeExempt(Boolean.FALSE);
        setAllowClubDiscounts(Boolean.FALSE);
        setAllowCouponDiscounts(Boolean.FALSE);
        setAllowAssociatePayments(Boolean.FALSE);
    }
    
}
