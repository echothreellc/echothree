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

package com.echothree.ui.web.main.action.inventory.inventorylocationgroup;

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.remote.form.GetInventoryLocationGroupStatusChoicesForm;
import com.echothree.control.user.inventory.remote.result.GetInventoryLocationGroupStatusChoicesResult;
import com.echothree.model.control.inventory.remote.choice.InventoryLocationGroupStatusChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="InventoryLocationGroupStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private InventoryLocationGroupStatusChoicesBean inventoryLocationGroupStatusChoices;
    
    private String warehouseName;
    private String inventoryLocationGroupName;
    private String inventoryLocationGroupStatusChoice;
    
    public void setupInventoryLocationGroupStatusChoices() {
        if(inventoryLocationGroupStatusChoices == null) {
            try {
                GetInventoryLocationGroupStatusChoicesForm form = InventoryUtil.getHome().getGetInventoryLocationGroupStatusChoicesForm();
                
                form.setWarehouseName(warehouseName);
                form.setInventoryLocationGroupName(inventoryLocationGroupName);
                form.setDefaultInventoryLocationGroupStatusChoice(inventoryLocationGroupStatusChoice);
                
                CommandResult commandResult = InventoryUtil.getHome().getInventoryLocationGroupStatusChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetInventoryLocationGroupStatusChoicesResult getInventoryLocationGroupStatusChoicesResult = (GetInventoryLocationGroupStatusChoicesResult)executionResult.getResult();
                inventoryLocationGroupStatusChoices = getInventoryLocationGroupStatusChoicesResult.getInventoryLocationGroupStatusChoices();
                
                if(inventoryLocationGroupStatusChoice == null)
                    inventoryLocationGroupStatusChoice = inventoryLocationGroupStatusChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, inventoryLocationGroupStatusChoices remains null, no default
            }
        }
    }
    
    public String getWarehouseName() {
        return warehouseName;
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    
    public String getInventoryLocationGroupName() {
        return inventoryLocationGroupName;
    }
    
    public void setInventoryLocationGroupName(String inventoryLocationGroupName) {
        this.inventoryLocationGroupName = inventoryLocationGroupName;
    }
    
    public String getInventoryLocationGroupStatusChoice() {
        return inventoryLocationGroupStatusChoice;
    }
    
    public void setInventoryLocationGroupStatusChoice(String inventoryLocationGroupStatusChoice) {
        this.inventoryLocationGroupStatusChoice = inventoryLocationGroupStatusChoice;
    }
    
    public List<LabelValueBean> getInventoryLocationGroupStatusChoices() {
        List<LabelValueBean> choices = null;
        
        setupInventoryLocationGroupStatusChoices();
        if(inventoryLocationGroupStatusChoices != null)
            choices = convertChoices(inventoryLocationGroupStatusChoices);
        
        return choices;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}
