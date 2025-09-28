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

package com.echothree.ui.web.main.action.item.itemvolume;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemVolumeTypeChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.item.common.choice.ItemVolumeTypeChoicesBean;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemVolumeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean unitOfMeasureTypeChoices;
    private ItemVolumeTypeChoicesBean itemVolumeTypeChoices;
    private UnitOfMeasureTypeChoicesBean heightUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean widthUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean depthUnitOfMeasureTypeChoices;
    
    private String itemName;
    private String unitOfMeasureTypeChoice;
    private String itemVolumeTypeChoice;
    private String height;
    private String heightUnitOfMeasureTypeChoice;
    private String width;
    private String widthUnitOfMeasureTypeChoice;
    private String depth;
    private String depthUnitOfMeasureTypeChoice;
    
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

    private void setupItemVolumeTypeChoices()
            throws NamingException {
        if(itemVolumeTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemVolumeTypeChoicesForm();

            form.setDefaultItemVolumeTypeChoice(itemVolumeTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ItemUtil.getHome().getItemVolumeTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemVolumeTypeChoicesResult)executionResult.getResult();
            itemVolumeTypeChoices = result.getItemVolumeTypeChoices();

            if(itemVolumeTypeChoice == null) {
                itemVolumeTypeChoice = itemVolumeTypeChoices.getDefaultValue();
            }
        }
    }

    private void setupHeightUnitOfMeasureTypeChoices()
            throws NamingException {
        if(heightUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(heightUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_VOLUME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            heightUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(heightUnitOfMeasureTypeChoice == null) {
                heightUnitOfMeasureTypeChoice = heightUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupWidthUnitOfMeasureTypeChoices()
            throws NamingException {
        if(widthUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(widthUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_VOLUME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            widthUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(widthUnitOfMeasureTypeChoice == null) {
                widthUnitOfMeasureTypeChoice = widthUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupDepthUnitOfMeasureTypeChoices()
            throws NamingException {
        if(depthUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(depthUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_VOLUME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            depthUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(depthUnitOfMeasureTypeChoice == null) {
                depthUnitOfMeasureTypeChoice = depthUnitOfMeasureTypeChoices.getDefaultValue();
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

    public String getItemVolumeTypeChoice()
            throws NamingException {
        setupItemVolumeTypeChoices();

        return itemVolumeTypeChoice;
    }

    public void setItemVolumeTypeChoice(String itemVolumeTypeChoice) {
        this.itemVolumeTypeChoice = itemVolumeTypeChoice;
    }

    public List<LabelValueBean> getItemVolumeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupItemVolumeTypeChoices();
        if(itemVolumeTypeChoices != null) {
            choices = convertChoices(itemVolumeTypeChoices);
        }

        return choices;
    }

    public String getHeight() {
        return height;
    }
    
    public void setHeight(String height) {
        this.height = height;
    }
    
    public List<LabelValueBean> getHeightUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupHeightUnitOfMeasureTypeChoices();
        if(heightUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(heightUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getHeightUnitOfMeasureTypeChoice()
            throws NamingException {
        setupHeightUnitOfMeasureTypeChoices();
        return heightUnitOfMeasureTypeChoice;
    }
    
    public void setHeightUnitOfMeasureTypeChoice(String heightUnitOfMeasureTypeChoice) {
        this.heightUnitOfMeasureTypeChoice = heightUnitOfMeasureTypeChoice;
    }
    
    public String getWidth() {
        return width;
    }
    
    public void setWidth(String width) {
        this.width = width;
    }
    
    public List<LabelValueBean> getWidthUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupWidthUnitOfMeasureTypeChoices();
        if(widthUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(widthUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getWidthUnitOfMeasureTypeChoice()
            throws NamingException {
        setupWidthUnitOfMeasureTypeChoices();
        return widthUnitOfMeasureTypeChoice;
    }
    
    public void setWidthUnitOfMeasureTypeChoice(String widthUnitOfMeasureTypeChoice) {
        this.widthUnitOfMeasureTypeChoice = widthUnitOfMeasureTypeChoice;
    }
    
    public String getDepth() {
        return depth;
    }
    
    public void setDepth(String depth) {
        this.depth = depth;
    }
    
    public List<LabelValueBean> getDepthUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupDepthUnitOfMeasureTypeChoices();
        if(depthUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(depthUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getDepthUnitOfMeasureTypeChoice()
            throws NamingException {
        setupDepthUnitOfMeasureTypeChoices();
        return depthUnitOfMeasureTypeChoice;
    }
    
    public void setDepthUnitOfMeasureTypeChoice(String depthUnitOfMeasureTypeChoice) {
        this.depthUnitOfMeasureTypeChoice = depthUnitOfMeasureTypeChoice;
    }
    
}
