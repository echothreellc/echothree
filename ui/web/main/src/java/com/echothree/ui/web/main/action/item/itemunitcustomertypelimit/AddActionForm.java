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

package com.echothree.ui.web.main.action.item.itemunitcustomertypelimit;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerTypeChoicesResult;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.GetInventoryConditionChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.customer.common.choice.CustomerTypeChoicesBean;
import com.echothree.model.control.inventory.common.InventoryConstants;
import com.echothree.model.control.inventory.common.choice.InventoryConditionChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemUnitCustomerTypeLimitAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private InventoryConditionChoicesBean inventoryConditionChoices;
    private UnitOfMeasureTypeChoicesBean unitOfMeasureTypeChoices;
    private CustomerTypeChoicesBean customerTypeChoices;
    
    private String itemName;
    private String inventoryConditionChoice;
    private String unitOfMeasureTypeChoice;
    private String customerTypeChoice;
    private String minimumQuantity;
    private String maximumQuantity;
    
    private void setupInventoryConditionChoices()
            throws NamingException {
        if(inventoryConditionChoices == null) {
            var form = InventoryUtil.getHome().getGetInventoryConditionChoicesForm();

            form.setInventoryConditionUseTypeName(InventoryConstants.InventoryConditionUseType_PURCHASE_ORDER);
            form.setDefaultInventoryConditionChoice(inventoryConditionChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = InventoryUtil.getHome().getInventoryConditionChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetInventoryConditionChoicesResult)executionResult.getResult();
            inventoryConditionChoices = result.getInventoryConditionChoices();

            if(inventoryConditionChoice == null) {
                inventoryConditionChoice = inventoryConditionChoices.getDefaultValue();
            }
        }
    }
    
    private void setupUnitOfMeasureTypeChoices()
            throws NamingException {
        if(unitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setItemName(itemName);
            form.setDefaultUnitOfMeasureTypeChoice(unitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            unitOfMeasureTypeChoices = result.getUnitOfMeasureTypeChoices();

            if(unitOfMeasureTypeChoice == null) {
                unitOfMeasureTypeChoice = unitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupCustomerTypeChoices()
            throws NamingException {
        if(customerTypeChoices == null) {
            var form = CustomerUtil.getHome().getGetCustomerTypeChoicesForm();

            form.setDefaultCustomerTypeChoice(customerTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = CustomerUtil.getHome().getCustomerTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerTypeChoicesResult)executionResult.getResult();
            customerTypeChoices = result.getCustomerTypeChoices();

            if(customerTypeChoice == null) {
                customerTypeChoice = customerTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getInventoryConditionChoice()
            throws NamingException {
        setupInventoryConditionChoices();
        
        return inventoryConditionChoice;
    }
    
    public void setInventoryConditionChoice(String inventoryConditionChoice) {
        this.inventoryConditionChoice = inventoryConditionChoice;
    }
    
    public List<LabelValueBean> getInventoryConditionChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupInventoryConditionChoices();
        if(inventoryConditionChoices != null) {
            choices = convertChoices(inventoryConditionChoices);
        }
        
        return choices;
    }
    
    public String getUnitOfMeasureTypeChoice()
            throws NamingException {
        setupUnitOfMeasureTypeChoices();
        
        return unitOfMeasureTypeChoice;
    }
    
    public void setUnitOfMeasureTypeChoice(String unitOfMeasureTypeChoice) {
        this.unitOfMeasureTypeChoice = unitOfMeasureTypeChoice;
    }
    
    public List<LabelValueBean> getUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupUnitOfMeasureTypeChoices();
        if(unitOfMeasureTypeChoices != null) {
            choices = convertChoices(unitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public List<LabelValueBean> getCustomerTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupCustomerTypeChoices();
        if(customerTypeChoices != null) {
            choices = convertChoices(customerTypeChoices);
        }
        
        return choices;
    }
    
    public void setCustomerTypeChoice(String customerTypeChoice) {
        this.customerTypeChoice = customerTypeChoice;
    }
    
    public String getCustomerTypeChoice()
            throws NamingException {
        setupCustomerTypeChoices();
        
        return customerTypeChoice;
    }
    
    public String getMinimumQuantity() {
        return minimumQuantity;
    }
    
    public void setMinimumQuantity(String minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }
    
    public String getMaximumQuantity() {
        return maximumQuantity;
    }
    
    public void setMaximumQuantity(String maximumQuantity) {
        this.maximumQuantity = maximumQuantity;
    }
    
}
