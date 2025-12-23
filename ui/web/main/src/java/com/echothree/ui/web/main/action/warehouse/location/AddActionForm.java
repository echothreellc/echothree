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

package com.echothree.ui.web.main.action.warehouse.location;

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.GetInventoryLocationGroupChoicesResult;
import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.result.GetLocationTypeChoicesResult;
import com.echothree.control.user.warehouse.common.result.GetLocationUseTypeChoicesResult;
import com.echothree.model.control.inventory.common.choice.InventoryLocationGroupChoicesBean;
import com.echothree.model.control.warehouse.common.choice.LocationTypeChoicesBean;
import com.echothree.model.control.warehouse.common.choice.LocationUseTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="LocationAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private LocationTypeChoicesBean locationTypeChoices;
    private LocationUseTypeChoicesBean locationUseTypeChoices;
    private InventoryLocationGroupChoicesBean inventoryLocationGroupChoices;
    
    private String warehouseName;
    private String locationName;
    private String locationTypeChoice;
    private String locationUseTypeChoice;
    private String velocity;
    private String inventoryLocationGroupChoice;
    private String description;
    
    public void setupLocationTypeChoices()
            throws NamingException {
        if(locationTypeChoices == null) {
            var form = WarehouseUtil.getHome().getGetLocationTypeChoicesForm();

            form.setWarehouseName(warehouseName);
            form.setDefaultLocationTypeChoice(locationTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = WarehouseUtil.getHome().getLocationTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetLocationTypeChoicesResult)executionResult.getResult();
            locationTypeChoices = result.getLocationTypeChoices();

            if(locationTypeChoice == null) {
                locationTypeChoice = locationTypeChoices.getDefaultValue();
            }
        }
    }
    
    public void setupLocationUseTypeChoices()
            throws NamingException {
        if(locationUseTypeChoices == null) {
            var form = WarehouseUtil.getHome().getGetLocationUseTypeChoicesForm();

            form.setDefaultLocationUseTypeChoice(locationUseTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = WarehouseUtil.getHome().getLocationUseTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetLocationUseTypeChoicesResult)executionResult.getResult();
            locationUseTypeChoices = result.getLocationUseTypeChoices();

            if(locationUseTypeChoice == null) {
                locationUseTypeChoice = locationUseTypeChoices.getDefaultValue();
            }
        }
    }
    
    public void setupInventoryLocationGroupChoices()
            throws NamingException {
        if(inventoryLocationGroupChoices == null) {
            var form = InventoryUtil.getHome().getGetInventoryLocationGroupChoicesForm();

            form.setWarehouseName(warehouseName);
            form.setDefaultInventoryLocationGroupChoice(inventoryLocationGroupChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = InventoryUtil.getHome().getInventoryLocationGroupChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetInventoryLocationGroupChoicesResult)executionResult.getResult();
            inventoryLocationGroupChoices = result.getInventoryLocationGroupChoices();

            if(inventoryLocationGroupChoice == null) {
                inventoryLocationGroupChoice = inventoryLocationGroupChoices.getDefaultValue();
            }
        }
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    
    public String getWarehouseName() {
        return warehouseName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    public String getLocationName() {
        return locationName;
    }
    
    public String getLocationTypeChoice() {
        return locationTypeChoice;
    }
    
    public void setLocationTypeChoice(String locationTypeChoice) {
        this.locationTypeChoice = locationTypeChoice;
    }
    
    public List<LabelValueBean> getLocationTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupLocationTypeChoices();
        if(locationTypeChoices != null) {
            choices = convertChoices(locationTypeChoices);
        }
        
        return choices;
    }
    
    public String getLocationUseTypeChoice() {
        return locationUseTypeChoice;
    }
    
    public void setLocationUseTypeChoice(String locationUseTypeChoice) {
        this.locationUseTypeChoice = locationUseTypeChoice;
    }
    
    public List<LabelValueBean> getLocationUseTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupLocationUseTypeChoices();
        if(locationUseTypeChoices != null) {
            choices = convertChoices(locationUseTypeChoices);
        }
        
        return choices;
    }
    
    public String getVelocity() {
        return velocity;
    }
    
    public void setVelocity(String velocity) {
        this.velocity = velocity;
    }
    
    public String getInventoryLocationGroupChoice() {
        return inventoryLocationGroupChoice;
    }
    
    public void setInventoryLocationGroupChoice(String inventoryLocationGroupChoice) {
        this.inventoryLocationGroupChoice = inventoryLocationGroupChoice;
    }
    
    public List<LabelValueBean> getInventoryLocationGroupChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupInventoryLocationGroupChoices();
        if(inventoryLocationGroupChoices != null) {
            choices = convertChoices(inventoryLocationGroupChoices);
        }
        
        return choices;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
}
