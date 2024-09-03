// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.item.partyinventorylevel;

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.GetInventoryConditionChoicesResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetCompanyChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.result.GetWarehouseChoicesResult;
import com.echothree.model.control.inventory.common.InventoryConstants;
import com.echothree.model.control.inventory.common.choice.InventoryConditionChoicesBean;
import com.echothree.model.control.party.common.choice.CompanyChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.model.control.warehouse.common.choice.WarehouseChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PartyInventoryLevelAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private CompanyChoicesBean companyChoices;
    private WarehouseChoicesBean warehouseChoices;
    private InventoryConditionChoicesBean inventoryConditionChoices;
    private UnitOfMeasureTypeChoicesBean minimumInventoryUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean maximumInventoryUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean reorderQuantityUnitOfMeasureTypeChoices;
    
    private String companyChoice;
    private String warehouseChoice;
    private String itemName;
    private String inventoryConditionChoice;
    private String minimumInventory;
    private String minimumInventoryUnitOfMeasureTypeChoice;
    private String maximumInventory;
    private String maximumInventoryUnitOfMeasureTypeChoice;
    private String reorderQuantity;
    private String reorderQuantityUnitOfMeasureTypeChoice;
    
    private void setupCompanyChoices() {
        if(companyChoices == null) {
            try {
                var commandForm = PartyUtil.getHome().getGetCompanyChoicesForm();

                commandForm.setDefaultCompanyChoice(companyChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());

                var commandResult = PartyUtil.getHome().getCompanyChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetCompanyChoicesResult)executionResult.getResult();
                companyChoices = result.getCompanyChoices();

                if(companyChoice == null) {
                    companyChoice = companyChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, companyChoices remains null, no default
            }
        }
    }

    private void setupWarehouseChoices() {
        if(warehouseChoices == null) {
            try {
                var commandForm = WarehouseUtil.getHome().getGetWarehouseChoicesForm();

                commandForm.setDefaultWarehouseChoice(warehouseChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());

                var commandResult = WarehouseUtil.getHome().getWarehouseChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetWarehouseChoicesResult)executionResult.getResult();
                warehouseChoices = result.getWarehouseChoices();

                if(warehouseChoice == null) {
                    warehouseChoice = warehouseChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, warehouseChoices remains null, no default
            }
        }
    }

    private void setupInventoryConditionChoices() {
        if(inventoryConditionChoices == null) {
            try {
                var form = InventoryUtil.getHome().getGetInventoryConditionChoicesForm();

                form.setInventoryConditionUseTypeName(InventoryConstants.InventoryConditionUseType_PURCHASE_ORDER);
                form.setDefaultInventoryConditionChoice(inventoryConditionChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());

                var commandResult = InventoryUtil.getHome().getInventoryConditionChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetInventoryConditionChoicesResult)executionResult.getResult();
                inventoryConditionChoices = result.getInventoryConditionChoices();

                if(inventoryConditionChoice == null) {
                    inventoryConditionChoice = inventoryConditionChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, inventoryConditionChoices remains null, no default
            }
        }
    }

    private void setupMinimumInventoryUnitOfMeasureTypeChoices() {
        if(minimumInventoryUnitOfMeasureTypeChoices == null) {
            try {
                var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(minimumInventoryUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setItemName(itemName);

                var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                minimumInventoryUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(minimumInventoryUnitOfMeasureTypeChoice == null) {
                    minimumInventoryUnitOfMeasureTypeChoice = minimumInventoryUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, locationChoices remains null, no default
            }
        }
    }
    
    private void setupMaximumInventoryUnitOfMeasureTypeChoices() {
        if(maximumInventoryUnitOfMeasureTypeChoices == null) {
            try {
                var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(maximumInventoryUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setItemName(itemName);

                var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                maximumInventoryUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(maximumInventoryUnitOfMeasureTypeChoice == null) {
                    maximumInventoryUnitOfMeasureTypeChoice = maximumInventoryUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, locationChoices remains null, no default
            }
        }
    }
    
    private void setupReorderQuantityUnitOfMeasureTypeChoices() {
        if(reorderQuantityUnitOfMeasureTypeChoices == null) {
            try {
                var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(reorderQuantityUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setItemName(itemName);

                var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                reorderQuantityUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(reorderQuantityUnitOfMeasureTypeChoice == null) {
                    reorderQuantityUnitOfMeasureTypeChoice = reorderQuantityUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, locationChoices remains null, no default
            }
        }
    }
    
    public String getCompanyChoice() {
        setupCompanyChoices();

        return companyChoice;
    }

    public void setCompanyChoice(String companyChoice) {
        this.companyChoice = companyChoice;
    }

    public List<LabelValueBean> getCompanyChoices() {
        List<LabelValueBean> choices = null;

        setupCompanyChoices();
        if(companyChoices != null) {
            choices = convertChoices(companyChoices);
        }

        return choices;
    }

    public String getWarehouseChoice() {
        setupWarehouseChoices();

        return warehouseChoice;
    }

    public void setWarehouseChoice(String warehouseChoice) {
        this.warehouseChoice = warehouseChoice;
    }

    public List<LabelValueBean> getWarehouseChoices() {
        List<LabelValueBean> choices = null;

        setupWarehouseChoices();
        if(warehouseChoices != null) {
            choices = convertChoices(warehouseChoices);
        }

        return choices;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getInventoryConditionChoice() {
        setupInventoryConditionChoices();

        return inventoryConditionChoice;
    }

    public void setInventoryConditionChoice(String inventoryConditionChoice) {
        this.inventoryConditionChoice = inventoryConditionChoice;
    }

    public List<LabelValueBean> getInventoryConditionChoices() {
        List<LabelValueBean> choices = null;

        setupInventoryConditionChoices();
        if(inventoryConditionChoices != null) {
            choices = convertChoices(inventoryConditionChoices);
        }

        return choices;
    }

    public String getMinimumInventory() {
        return minimumInventory;
    }
    
    public void setMinimumInventory(String minimumInventory) {
        this.minimumInventory = minimumInventory;
    }
    
    public List<LabelValueBean> getMinimumInventoryUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupMinimumInventoryUnitOfMeasureTypeChoices();
        if(minimumInventoryUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(minimumInventoryUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getMinimumInventoryUnitOfMeasureTypeChoice() {
        setupMinimumInventoryUnitOfMeasureTypeChoices();
        return minimumInventoryUnitOfMeasureTypeChoice;
    }
    
    public void setMinimumInventoryUnitOfMeasureTypeChoice(String minimumInventoryUnitOfMeasureTypeChoice) {
        this.minimumInventoryUnitOfMeasureTypeChoice = minimumInventoryUnitOfMeasureTypeChoice;
    }
    
    public String getMaximumInventory() {
        return maximumInventory;
    }
    
    public void setMaximumInventory(String maximumInventory) {
        this.maximumInventory = maximumInventory;
    }
    
    public List<LabelValueBean> getMaximumInventoryUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupMaximumInventoryUnitOfMeasureTypeChoices();
        if(maximumInventoryUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(maximumInventoryUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getMaximumInventoryUnitOfMeasureTypeChoice() {
        setupMaximumInventoryUnitOfMeasureTypeChoices();
        return maximumInventoryUnitOfMeasureTypeChoice;
    }
    
    public void setMaximumInventoryUnitOfMeasureTypeChoice(String maximumInventoryUnitOfMeasureTypeChoice) {
        this.maximumInventoryUnitOfMeasureTypeChoice = maximumInventoryUnitOfMeasureTypeChoice;
    }
    
    public String getReorderQuantity() {
        return reorderQuantity;
    }
    
    public void setReorderQuantity(String reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }
    
    public List<LabelValueBean> getReorderQuantityUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupReorderQuantityUnitOfMeasureTypeChoices();
        if(reorderQuantityUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(reorderQuantityUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getReorderQuantityUnitOfMeasureTypeChoice() {
        setupReorderQuantityUnitOfMeasureTypeChoices();
        return reorderQuantityUnitOfMeasureTypeChoice;
    }
    
    public void setReorderQuantityUnitOfMeasureTypeChoice(String reorderQuantityUnitOfMeasureTypeChoice) {
        this.reorderQuantityUnitOfMeasureTypeChoice = reorderQuantityUnitOfMeasureTypeChoice;
    }
    
}
