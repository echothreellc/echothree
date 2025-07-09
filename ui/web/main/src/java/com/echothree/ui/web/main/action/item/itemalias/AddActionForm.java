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

package com.echothree.ui.web.main.action.item.itemalias;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemAliasTypeChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.item.common.choice.ItemAliasTypeChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemAliasAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean unitOfMeasureTypeChoices;
    private ItemAliasTypeChoicesBean itemAliasTypeChoices;
    
    private String itemName;
    private String unitOfMeasureTypeChoice;
    private String itemAliasTypeChoice;
    private String alias;
    
    private void setupUnitOfMeasureTypeChoices()
            throws NamingException {
        if(unitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setItemName(itemName);
            form.setDefaultUnitOfMeasureTypeChoice(unitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            unitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(unitOfMeasureTypeChoice == null) {
                unitOfMeasureTypeChoice = unitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    public void setupItemAliasTypeChoices()
            throws NamingException {
        if(itemAliasTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemAliasTypeChoicesForm();

            form.setDefaultItemAliasTypeChoice(itemAliasTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ItemUtil.getHome().getItemAliasTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getItemAliasTypeChoicesResult = (GetItemAliasTypeChoicesResult)executionResult.getResult();
            itemAliasTypeChoices = getItemAliasTypeChoicesResult.getItemAliasTypeChoices();

            if(itemAliasTypeChoice == null) {
                itemAliasTypeChoice = itemAliasTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
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
    
    public String getUnitOfMeasureTypeChoice()
            throws NamingException {
        setupUnitOfMeasureTypeChoices();
        return unitOfMeasureTypeChoice;
    }
    
    public void setUnitOfMeasureTypeChoice(String unitOfMeasureTypeChoice) {
        this.unitOfMeasureTypeChoice = unitOfMeasureTypeChoice;
    }
    
    public List<LabelValueBean> getItemAliasTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemAliasTypeChoices();
        if(itemAliasTypeChoices != null) {
            choices = convertChoices(itemAliasTypeChoices);
        }
        
        return choices;
    }
    
    public void setItemAliasTypeChoice(String itemAliasTypeChoice) {
        this.itemAliasTypeChoice = itemAliasTypeChoice;
    }
    
    public String getItemAliasTypeChoice()
            throws NamingException {
        setupItemAliasTypeChoices();
        return itemAliasTypeChoice;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
}
