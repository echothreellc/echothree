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

package com.echothree.ui.web.main.action.item.item;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetItemAccountingCategoryChoicesResult;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.result.GetCancellationPolicyChoicesResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemCategoryChoicesResult;
import com.echothree.control.user.item.common.result.GetItemDeliveryTypeChoicesResult;
import com.echothree.control.user.item.common.result.GetItemInventoryTypeChoicesResult;
import com.echothree.control.user.item.common.result.GetItemPriceTypeChoicesResult;
import com.echothree.control.user.item.common.result.GetItemStatusChoicesResult;
import com.echothree.control.user.item.common.result.GetItemTypeChoicesResult;
import com.echothree.control.user.item.common.result.GetItemUseTypeChoicesResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetCompanyChoicesResult;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.result.GetReturnPolicyChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureKindChoicesResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.GetItemPurchasingCategoryChoicesResult;
import com.echothree.model.control.accounting.common.choice.ItemAccountingCategoryChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.item.common.choice.ItemCategoryChoicesBean;
import com.echothree.model.control.item.common.choice.ItemDeliveryTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemInventoryTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemPriceTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemStatusChoicesBean;
import com.echothree.model.control.item.common.choice.ItemTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemUseTypeChoicesBean;
import com.echothree.model.control.party.common.choice.CompanyChoicesBean;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.common.choice.ReturnPolicyChoicesBean;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureKindChoicesBean;
import com.echothree.model.control.vendor.common.choice.ItemPurchasingCategoryChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ItemTypeChoicesBean itemTypeChoices;
    private ItemUseTypeChoicesBean itemUseTypeChoices;
    private ItemCategoryChoicesBean itemCategoryChoices;
    private ItemAccountingCategoryChoicesBean itemAccountingCategoryChoices;
    private ItemPurchasingCategoryChoicesBean itemPurchasingCategoryChoices;
    private CompanyChoicesBean companyChoices;
    private ItemDeliveryTypeChoicesBean itemDeliveryTypeChoices;
    private ItemInventoryTypeChoicesBean itemInventoryTypeChoices;
    private ItemStatusChoicesBean itemStatusChoices;
    private UnitOfMeasureKindChoicesBean unitOfMeasureKindChoices;
    private ItemPriceTypeChoicesBean itemPriceTypeChoices;
    private CancellationPolicyChoicesBean cancellationPolicyChoices;
    private ReturnPolicyChoicesBean returnPolicyChoices;
    
    private String itemName;
    private String itemTypeChoice;
    private String itemUseTypeChoice;
    private String itemCategoryChoice;
    private String itemAccountingCategoryChoice;
    private String itemPurchasingCategoryChoice;
    private String companyChoice;
    private String itemDeliveryTypeChoice;
    private String itemInventoryTypeChoice;
    private Boolean inventorySerialized;
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
    private String itemStatusChoice;
    private String unitOfMeasureKindChoice;
    private String itemPriceTypeChoice;
    private String cancellationPolicyChoice;
    private String returnPolicyChoice;
    
    private void setupItemTypeChoices()
            throws NamingException {
        if(itemTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemTypeChoicesForm();

            form.setDefaultItemTypeChoice(itemTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ItemUtil.getHome().getItemTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemTypeChoicesResult)executionResult.getResult();
            itemTypeChoices = result.getItemTypeChoices();

            if(itemTypeChoice == null) {
                itemTypeChoice = itemTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupItemUseTypeChoices()
            throws NamingException {
        if(itemUseTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemUseTypeChoicesForm();

            form.setDefaultItemUseTypeChoice(itemUseTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ItemUtil.getHome().getItemUseTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemUseTypeChoicesResult)executionResult.getResult();
            itemUseTypeChoices = result.getItemUseTypeChoices();

            if(itemUseTypeChoice == null) {
                itemUseTypeChoice = itemUseTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupItemCategoryChoices()
            throws NamingException {
        if(itemCategoryChoices == null) {
            var form = ItemUtil.getHome().getGetItemCategoryChoicesForm();

            form.setDefaultItemCategoryChoice(itemCategoryChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ItemUtil.getHome().getItemCategoryChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemCategoryChoicesResult)executionResult.getResult();
            itemCategoryChoices = result.getItemCategoryChoices();

            if(itemCategoryChoice == null) {
                itemCategoryChoice = itemCategoryChoices.getDefaultValue();
            }
        }
    }
    
    private void setupItemAccountingCategoryChoices()
            throws NamingException {
        if(itemAccountingCategoryChoices == null) {
            var form = AccountingUtil.getHome().getGetItemAccountingCategoryChoicesForm();

            form.setDefaultItemAccountingCategoryChoice(itemAccountingCategoryChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = AccountingUtil.getHome().getItemAccountingCategoryChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemAccountingCategoryChoicesResult)executionResult.getResult();
            itemAccountingCategoryChoices = result.getItemAccountingCategoryChoices();

            if(itemAccountingCategoryChoice == null) {
                itemAccountingCategoryChoice = itemAccountingCategoryChoices.getDefaultValue();
            }
        }
    }
    
    private void setupItemPurchasingCategoryChoices()
            throws NamingException {
        if(itemPurchasingCategoryChoices == null) {
            var form = VendorUtil.getHome().getGetItemPurchasingCategoryChoicesForm();

            form.setDefaultItemPurchasingCategoryChoice(itemPurchasingCategoryChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = VendorUtil.getHome().getItemPurchasingCategoryChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemPurchasingCategoryChoicesResult)executionResult.getResult();
            itemPurchasingCategoryChoices = result.getItemPurchasingCategoryChoices();

            if(itemPurchasingCategoryChoice == null) {
                itemPurchasingCategoryChoice = itemPurchasingCategoryChoices.getDefaultValue();
            }
        }
    }
    
    private void setupCompanyChoices()
            throws NamingException {
        if(companyChoices == null) {
            var commandForm = PartyUtil.getHome().getGetCompanyChoicesForm();

            commandForm.setDefaultCompanyChoice(companyChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = PartyUtil.getHome().getCompanyChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCompanyChoicesResult)executionResult.getResult();
            companyChoices = result.getCompanyChoices();

            if(companyChoice == null)
                companyChoice = companyChoices.getDefaultValue();
        }
    }
    
    private void setupItemDeliveryTypeChoices()
            throws NamingException {
        if(itemDeliveryTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemDeliveryTypeChoicesForm();

            form.setDefaultItemDeliveryTypeChoice(itemDeliveryTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ItemUtil.getHome().getItemDeliveryTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemDeliveryTypeChoicesResult)executionResult.getResult();
            itemDeliveryTypeChoices = result.getItemDeliveryTypeChoices();

            if(itemDeliveryTypeChoice == null) {
                itemDeliveryTypeChoice = itemDeliveryTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupItemInventoryTypeChoices()
            throws NamingException {
        if(itemInventoryTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemInventoryTypeChoicesForm();

            form.setDefaultItemInventoryTypeChoice(itemInventoryTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ItemUtil.getHome().getItemInventoryTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemInventoryTypeChoicesResult)executionResult.getResult();
            itemInventoryTypeChoices = result.getItemInventoryTypeChoices();

            if(itemInventoryTypeChoice == null) {
                itemInventoryTypeChoice = itemInventoryTypeChoices.getDefaultValue();
            }
        }
    }
    
    public void setupItemStatusChoices()
            throws NamingException {
        if(itemStatusChoices == null) {
            var form = ItemUtil.getHome().getGetItemStatusChoicesForm();

            form.setDefaultItemStatusChoice(itemStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ItemUtil.getHome().getItemStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemStatusChoicesResult)executionResult.getResult();
            itemStatusChoices = result.getItemStatusChoices();

            if(itemStatusChoice == null) {
                itemStatusChoice = itemStatusChoices.getDefaultValue();
            }
        }
    }
    
    private void setupUnitOfMeasureKindChoices()
            throws NamingException {
        if(unitOfMeasureKindChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureKindChoicesForm();

            form.setDefaultUnitOfMeasureKindChoice(unitOfMeasureKindChoice);
            form.setAllowNullChoice(String.valueOf(false));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_QUANTITY);

            var commandResult = UomUtil.getHome().getUnitOfMeasureKindChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetUnitOfMeasureKindChoicesResult)executionResult.getResult();
            unitOfMeasureKindChoices = result.getUnitOfMeasureKindChoices();

            if(unitOfMeasureKindChoice == null) {
                unitOfMeasureKindChoice = unitOfMeasureKindChoices.getDefaultValue();
            }
        }
    }
    
    private void setupItemPriceTypeChoices()
            throws NamingException {
        if(itemPriceTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemPriceTypeChoicesForm();

            form.setDefaultItemPriceTypeChoice(itemPriceTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ItemUtil.getHome().getItemPriceTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemPriceTypeChoicesResult)executionResult.getResult();
            itemPriceTypeChoices = result.getItemPriceTypeChoices();

            if(itemPriceTypeChoice == null) {
                itemPriceTypeChoice = itemPriceTypeChoices.getDefaultValue();
            }
        }
    }
    
    public void setupCancellationPolicyChoices()
            throws NamingException {
        if(cancellationPolicyChoices == null) {
            var form = CancellationPolicyUtil.getHome().getGetCancellationPolicyChoicesForm();

            form.setCancellationKindName(CancellationKinds.CUSTOMER_CANCELLATION.name());
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

            form.setReturnKindName(ReturnKinds.CUSTOMER_RETURN.name());
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
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getItemTypeChoice()
            throws NamingException {
        setupItemTypeChoices();
        
        return itemTypeChoice;
    }
    
    public void setItemTypeChoice(String itemTypeChoice) {
        this.itemTypeChoice = itemTypeChoice;
    }
    
    public List<LabelValueBean> getItemTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemTypeChoices();
        if(itemTypeChoices != null) {
            choices = convertChoices(itemTypeChoices);
        }
        
        return choices;
    }
    
    public String getItemUseTypeChoice()
            throws NamingException {
        setupItemUseTypeChoices();
        
        return itemUseTypeChoice;
    }
    
    public void setItemUseTypeChoice(String itemUseTypeChoice) {
        this.itemUseTypeChoice = itemUseTypeChoice;
    }
    
    public List<LabelValueBean> getItemUseTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemUseTypeChoices();
        if(itemUseTypeChoices != null) {
            choices = convertChoices(itemUseTypeChoices);
        }
        
        return choices;
    }
    
    public String getItemCategoryChoice()
            throws NamingException {
        setupItemCategoryChoices();
        
        return itemCategoryChoice;
    }
    
    public void setItemCategoryChoice(String itemCategoryChoice) {
        this.itemCategoryChoice = itemCategoryChoice;
    }
    
    public List<LabelValueBean> getItemCategoryChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemCategoryChoices();
        if(itemCategoryChoices != null) {
            choices = convertChoices(itemCategoryChoices);
        }
        
        return choices;
    }
    
    public String getItemAccountingCategoryChoice()
            throws NamingException {
        setupItemAccountingCategoryChoices();
        
        return itemAccountingCategoryChoice;
    }
    
    public void setItemAccountingCategoryChoice(String itemAccountingCategoryChoice) {
        this.itemAccountingCategoryChoice = itemAccountingCategoryChoice;
    }
    
    public List<LabelValueBean> getItemAccountingCategoryChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemAccountingCategoryChoices();
        if(itemAccountingCategoryChoices != null) {
            choices = convertChoices(itemAccountingCategoryChoices);
        }
        
        return choices;
    }
    
    public String getItemPurchasingCategoryChoice()
            throws NamingException {
        setupItemPurchasingCategoryChoices();
        
        return itemPurchasingCategoryChoice;
    }
    
    public void setItemPurchasingCategoryChoice(String itemPurchasingCategoryChoice) {
        this.itemPurchasingCategoryChoice = itemPurchasingCategoryChoice;
    }
    
    public List<LabelValueBean> getItemPurchasingCategoryChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemPurchasingCategoryChoices();
        if(itemPurchasingCategoryChoices != null) {
            choices = convertChoices(itemPurchasingCategoryChoices);
        }
        
        return choices;
    }
    
    public void setCompanyChoice(String companyChoice) {
        this.companyChoice = companyChoice;
    }
    
    public String getCompanyChoice()
            throws NamingException {
        setupCompanyChoices();
        
        return companyChoice;
    }
    
    public List<LabelValueBean> getCompanyChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupCompanyChoices();
        if(companyChoices != null) {
            choices = convertChoices(companyChoices);
        }
        
        return choices;
    }
    
    public String getItemDeliveryTypeChoice()
            throws NamingException {
        setupItemDeliveryTypeChoices();
        
        return itemDeliveryTypeChoice;
    }
    
    public void setItemDeliveryTypeChoice(String itemDeliveryTypeChoice) {
        this.itemDeliveryTypeChoice = itemDeliveryTypeChoice;
    }
    
    public List<LabelValueBean> getItemDeliveryTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemDeliveryTypeChoices();
        if(itemDeliveryTypeChoices != null) {
            choices = convertChoices(itemDeliveryTypeChoices);
        }
        
        return choices;
    }
    
    public String getItemInventoryTypeChoice()
            throws NamingException {
        setupItemInventoryTypeChoices();
        
        return itemInventoryTypeChoice;
    }
    
    public void setItemInventoryTypeChoice(String itemInventoryTypeChoice) {
        this.itemInventoryTypeChoice = itemInventoryTypeChoice;
    }
    
    public List<LabelValueBean> getItemInventoryTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemInventoryTypeChoices();
        if(itemInventoryTypeChoices != null) {
            choices = convertChoices(itemInventoryTypeChoices);
        }
        
        return choices;
    }
    
    public Boolean getInventorySerialized() {
        return inventorySerialized;
    }
    
    public void setInventorySerialized(Boolean inventorySerialized) {
        this.inventorySerialized = inventorySerialized;
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
    
    public String getItemStatusChoice()
            throws NamingException {
        setupItemStatusChoices();
        
        return itemStatusChoice;
    }
    
    public void setItemStatusChoice(String itemStatusChoice) {
        this.itemStatusChoice = itemStatusChoice;
    }
    
    public List<LabelValueBean> getItemStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemStatusChoices();
        if(itemStatusChoices != null) {
            choices = convertChoices(itemStatusChoices);
        }
        
        return choices;
    }
    
    public String getUnitOfMeasureKindChoice()
            throws NamingException {
        setupUnitOfMeasureKindChoices();
        
        return unitOfMeasureKindChoice;
    }
    
    public void setUnitOfMeasureKindChoice(String unitOfMeasureKindChoice) {
        this.unitOfMeasureKindChoice = unitOfMeasureKindChoice;
    }
    
    public List<LabelValueBean> getUnitOfMeasureKindChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupUnitOfMeasureKindChoices();
        if(unitOfMeasureKindChoices != null) {
            choices = convertChoices(unitOfMeasureKindChoices);
        }
        
        return choices;
    }
    
    public String getItemPriceTypeChoice()
            throws NamingException {
        setupItemPriceTypeChoices();
        
        return itemPriceTypeChoice;
    }
    
    public void setItemPriceTypeChoice(String itemPriceTypeChoice) {
        this.itemPriceTypeChoice = itemPriceTypeChoice;
    }
    
    public List<LabelValueBean> getItemPriceTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemPriceTypeChoices();
        if(itemPriceTypeChoices != null) {
            choices = convertChoices(itemPriceTypeChoices);
        }
        
        return choices;
    }
    
    public List<LabelValueBean> getCancellationPolicyChoices()
            throws NamingException {
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
    
    public String getCancellationPolicyChoice()
            throws NamingException {
        setupCancellationPolicyChoices();
        
        return cancellationPolicyChoice;
    }
    
    public List<LabelValueBean> getReturnPolicyChoices()
            throws NamingException {
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
    
    public String getReturnPolicyChoice()
            throws NamingException {
        setupReturnPolicyChoices();
        
        return returnPolicyChoice;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setInventorySerialized(false);
        setShippingChargeExempt(false);
        setAllowClubDiscounts(false);
        setAllowCouponDiscounts(false);
        setAllowAssociatePayments(false);
    }
    
}
