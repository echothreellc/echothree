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

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="LocationVolumeAdd")
public class VolumeAddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean heightUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean widthUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean depthUnitOfMeasureTypeChoices;
    
    private String warehouseName;
    private String locationName;
    private String height;
    private String heightUnitOfMeasureTypeChoice;
    private String width;
    private String widthUnitOfMeasureTypeChoice;
    private String depth;
    private String depthUnitOfMeasureTypeChoice;
    
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
    
    public String getWarehouseName() {
        return warehouseName;
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    
    public String getLocationName() {
        return locationName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
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
