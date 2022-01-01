// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.ui.web.main.action.warehouse.warehouse;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.form.GetPrinterGroupChoicesForm;
import com.echothree.control.user.printer.common.result.GetPrinterGroupChoicesResult;
import com.echothree.model.control.printer.common.choice.PrinterGroupChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BasePartyActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="WarehouseAdd")
public class AddActionForm
        extends BasePartyActionForm {
    
    private PrinterGroupChoicesBean inventoryMovePrinterGroupChoices;
    private PrinterGroupChoicesBean picklistPrinterGroupChoices;
    private PrinterGroupChoicesBean packingListPrinterGroupChoices;
    private PrinterGroupChoicesBean shippingManifestPrinterGroupChoices;
    
    private String warehouseName;
    private Boolean isDefault;
    private String sortOrder;
    private String name;
    private String inventoryMovePrinterGroupChoice;
    private String picklistPrinterGroupChoice;
    private String packingListPrinterGroupChoice;
    private String shippingManifestPrinterGroupChoice;
    
    private void setupInventoryMovePrinterGroupChoices() {
        if(inventoryMovePrinterGroupChoices == null) {
            try {
                GetPrinterGroupChoicesForm form = PrinterUtil.getHome().getGetPrinterGroupChoicesForm();
                
                form.setDefaultPrinterGroupChoice(inventoryMovePrinterGroupChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = PrinterUtil.getHome().getPrinterGroupChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPrinterGroupChoicesResult result = (GetPrinterGroupChoicesResult)executionResult.getResult();
                inventoryMovePrinterGroupChoices = result.getPrinterGroupChoices();
                
                if(inventoryMovePrinterGroupChoice == null) {
                    inventoryMovePrinterGroupChoice = inventoryMovePrinterGroupChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, inventoryMovePrinterGroupChoices remains null, no default
            }
        }
    }
    
    private void setupPicklistPrinterGroupChoices() {
        if(picklistPrinterGroupChoices == null) {
            try {
                GetPrinterGroupChoicesForm form = PrinterUtil.getHome().getGetPrinterGroupChoicesForm();
                
                form.setDefaultPrinterGroupChoice(picklistPrinterGroupChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = PrinterUtil.getHome().getPrinterGroupChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPrinterGroupChoicesResult result = (GetPrinterGroupChoicesResult)executionResult.getResult();
                picklistPrinterGroupChoices = result.getPrinterGroupChoices();
                
                if(picklistPrinterGroupChoice == null) {
                    picklistPrinterGroupChoice = picklistPrinterGroupChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, picklistPrinterGroupChoices remains null, no default
            }
        }
    }
    
    private void setupPackingListPrinterGroupChoices() {
        if(packingListPrinterGroupChoices == null) {
            try {
                GetPrinterGroupChoicesForm form = PrinterUtil.getHome().getGetPrinterGroupChoicesForm();
                
                form.setDefaultPrinterGroupChoice(packingListPrinterGroupChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = PrinterUtil.getHome().getPrinterGroupChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPrinterGroupChoicesResult result = (GetPrinterGroupChoicesResult)executionResult.getResult();
                packingListPrinterGroupChoices = result.getPrinterGroupChoices();
                
                if(packingListPrinterGroupChoice == null) {
                    packingListPrinterGroupChoice = packingListPrinterGroupChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, packingListPrinterGroupChoices remains null, no default
            }
        }
    }
    
    private void setupShippingManifestPrinterGroupChoices() {
        if(shippingManifestPrinterGroupChoices == null) {
            try {
                GetPrinterGroupChoicesForm form = PrinterUtil.getHome().getGetPrinterGroupChoicesForm();
                
                form.setDefaultPrinterGroupChoice(shippingManifestPrinterGroupChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = PrinterUtil.getHome().getPrinterGroupChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPrinterGroupChoicesResult result = (GetPrinterGroupChoicesResult)executionResult.getResult();
                shippingManifestPrinterGroupChoices = result.getPrinterGroupChoices();
                
                if(shippingManifestPrinterGroupChoice == null) {
                    shippingManifestPrinterGroupChoice = shippingManifestPrinterGroupChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, shippingManifestPrinterGroupChoices remains null, no default
            }
        }
    }
    
    public String getWarehouseName() {
        return warehouseName;
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<LabelValueBean> getInventoryMovePrinterGroupChoices() {
        List<LabelValueBean> choices = null;
        
        setupInventoryMovePrinterGroupChoices();
        if(inventoryMovePrinterGroupChoices != null) {
            choices = convertChoices(inventoryMovePrinterGroupChoices);
        }
        
        return choices;
    }
    
    public void setInventoryMovePrinterGroupChoice(String inventoryMovePrinterGroupChoice) {
        this.inventoryMovePrinterGroupChoice = inventoryMovePrinterGroupChoice;
    }
    
    public String getInventoryMovePrinterGroupChoice() {
        setupInventoryMovePrinterGroupChoices();
        
        return inventoryMovePrinterGroupChoice;
    }
    
    public List<LabelValueBean> getPicklistPrinterGroupChoices() {
        List<LabelValueBean> choices = null;
        
        setupPicklistPrinterGroupChoices();
        if(picklistPrinterGroupChoices != null) {
            choices = convertChoices(picklistPrinterGroupChoices);
        }
        
        return choices;
    }
    
    public void setPicklistPrinterGroupChoice(String picklistPrinterGroupChoice) {
        this.picklistPrinterGroupChoice = picklistPrinterGroupChoice;
    }
    
    public String getPicklistPrinterGroupChoice() {
        setupPicklistPrinterGroupChoices();
        
        return picklistPrinterGroupChoice;
    }
    
    public List<LabelValueBean> getPackingListPrinterGroupChoices() {
        List<LabelValueBean> choices = null;
        
        setupPackingListPrinterGroupChoices();
        if(packingListPrinterGroupChoices != null) {
            choices = convertChoices(packingListPrinterGroupChoices);
        }
        
        return choices;
    }
    
    public void setPackingListPrinterGroupChoice(String packingListPrinterGroupChoice) {
        this.packingListPrinterGroupChoice = packingListPrinterGroupChoice;
    }
    
    public String getPackingListPrinterGroupChoice() {
        setupPackingListPrinterGroupChoices();
        
        return packingListPrinterGroupChoice;
    }
    
    public List<LabelValueBean> getShippingManifestPrinterGroupChoices() {
        List<LabelValueBean> choices = null;
        
        setupShippingManifestPrinterGroupChoices();
        if(shippingManifestPrinterGroupChoices != null) {
            choices = convertChoices(shippingManifestPrinterGroupChoices);
        }
        
        return choices;
    }
    
    public void setShippingManifestPrinterGroupChoice(String shippingManifestPrinterGroupChoice) {
        this.shippingManifestPrinterGroupChoice = shippingManifestPrinterGroupChoice;
    }
    
    public String getShippingManifestPrinterGroupChoice() {
        setupShippingManifestPrinterGroupChoices();
        
        return shippingManifestPrinterGroupChoice;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setIsDefault(Boolean.FALSE);
    }
    
}
