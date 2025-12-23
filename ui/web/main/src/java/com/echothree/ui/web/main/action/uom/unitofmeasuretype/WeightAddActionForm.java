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

package com.echothree.ui.web.main.action.uom.unitofmeasuretype;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="UnitOfMeasureTypeWeightAdd")
public class WeightAddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean weightUnitOfMeasureTypeChoices;
    
    private String unitOfMeasureKindName;
    private String unitOfMeasureTypeName;
    private String weight;
    private String weightUnitOfMeasureTypeChoice;
    
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
    
    public String getUnitOfMeasureKindName() {
        return unitOfMeasureKindName;
    }
    
    public void setUnitOfMeasureKindName(String unitOfMeasureKindName) {
        this.unitOfMeasureKindName = unitOfMeasureKindName;
    }
    
    public String getUnitOfMeasureTypeName() {
        return unitOfMeasureTypeName;
    }
    
    public void setUnitOfMeasureTypeName(String unitOfMeasureTypeName) {
        this.unitOfMeasureTypeName = unitOfMeasureTypeName;
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
