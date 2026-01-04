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

package com.echothree.ui.web.main.action.item.itemweight;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemWeightTypeChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.item.common.choice.ItemWeightTypeChoicesBean;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemWeightAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean unitOfMeasureTypeChoices;
    private ItemWeightTypeChoicesBean itemWeightTypeChoices;
    private UnitOfMeasureTypeChoicesBean weightUnitOfMeasureTypeChoices;
    
    private String itemName;
    private String unitOfMeasureTypeChoice;
    private String itemWeightTypeChoice;
    private String weight;
    private String weightUnitOfMeasureTypeChoice;

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

    private void setupItemWeightTypeChoices()
            throws NamingException {
        if(itemWeightTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemWeightTypeChoicesForm();

            form.setDefaultItemWeightTypeChoice(itemWeightTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ItemUtil.getHome().getItemWeightTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemWeightTypeChoicesResult)executionResult.getResult();
            itemWeightTypeChoices = result.getItemWeightTypeChoices();

            if(itemWeightTypeChoice == null) {
                itemWeightTypeChoice = itemWeightTypeChoices.getDefaultValue();
            }
        }
    }

    private void setupWeightUnitOfMeasureTypeChoices()
            throws NamingException {
        if(weightUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(weightUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_WEIGHT);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            weightUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(weightUnitOfMeasureTypeChoice == null) {
                weightUnitOfMeasureTypeChoice = weightUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public String getItemWeightTypeChoice()
            throws NamingException {
        setupItemWeightTypeChoices();

        return itemWeightTypeChoice;
    }

    public void setItemWeightTypeChoice(String itemWeightTypeChoice) {
        this.itemWeightTypeChoice = itemWeightTypeChoice;
    }

    public List<LabelValueBean> getItemWeightTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupItemWeightTypeChoices();
        if(itemWeightTypeChoices != null) {
            choices = convertChoices(itemWeightTypeChoices);
        }

        return choices;
    }

    public String getWeight() {
        return weight;
    }
    
    public void setWeight(String weight) {
        this.weight = weight;
    }
    
    public List<LabelValueBean> getWeightUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupWeightUnitOfMeasureTypeChoices();
        if(weightUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(weightUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getWeightUnitOfMeasureTypeChoice()
            throws NamingException {
        setupWeightUnitOfMeasureTypeChoices();
        return weightUnitOfMeasureTypeChoice;
    }
    
    public void setWeightUnitOfMeasureTypeChoice(String weightUnitOfMeasureTypeChoice) {
        this.weightUnitOfMeasureTypeChoice = weightUnitOfMeasureTypeChoice;
    }
    
}
