// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.uom.unitofmeasureequivalent;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureTypeChoicesForm;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="UnitOfMeasureEquivalentAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean fromUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean toUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean depthUnitOfMeasureTypeChoices;
    
    private String unitOfMeasureKindName;
    private String fromUnitOfMeasureTypeChoice;
    private String toUnitOfMeasureTypeChoice;
    private String toQuantity;
    
    private void setupFromUnitOfMeasureTypeChoices() {
        if(fromUnitOfMeasureTypeChoices == null) {
            try {
                GetUnitOfMeasureTypeChoicesForm form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(fromUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                form.setUnitOfMeasureKindName(unitOfMeasureKindName);
                
                CommandResult commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetUnitOfMeasureTypeChoicesResult getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                fromUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(fromUnitOfMeasureTypeChoice == null) {
                    fromUnitOfMeasureTypeChoice = fromUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, locationChoices remains null, no default
            }
        }
    }
    
    private void setupToUnitOfMeasureTypeChoices() {
        if(toUnitOfMeasureTypeChoices == null) {
            try {
                GetUnitOfMeasureTypeChoicesForm form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(toUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                form.setUnitOfMeasureKindName(unitOfMeasureKindName);
                
                CommandResult commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetUnitOfMeasureTypeChoicesResult getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                toUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(toUnitOfMeasureTypeChoice == null) {
                    toUnitOfMeasureTypeChoice = toUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, locationChoices remains null, no default
            }
        }
    }
    
    public void setUnitOfMeasureKindName(String unitOfMeasureKindName) {
        this.unitOfMeasureKindName = unitOfMeasureKindName;
    }
    
    public String getUnitOfMeasureKindName() {
        return unitOfMeasureKindName;
    }
    
    public List<LabelValueBean> getFromUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupFromUnitOfMeasureTypeChoices();
        if(fromUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(fromUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getFromUnitOfMeasureTypeChoice() {
        setupFromUnitOfMeasureTypeChoices();
        return fromUnitOfMeasureTypeChoice;
    }
    
    public void setFromUnitOfMeasureTypeChoice(String fromUnitOfMeasureTypeChoice) {
        this.fromUnitOfMeasureTypeChoice = fromUnitOfMeasureTypeChoice;
    }
    
    public List<LabelValueBean> getToUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupToUnitOfMeasureTypeChoices();
        if(toUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(toUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getToUnitOfMeasureTypeChoice() {
        setupToUnitOfMeasureTypeChoices();
        return toUnitOfMeasureTypeChoice;
    }
    
    public void setToUnitOfMeasureTypeChoice(String toUnitOfMeasureTypeChoice) {
        this.toUnitOfMeasureTypeChoice = toUnitOfMeasureTypeChoice;
    }
    
    public String getToQuantity() {
        return toQuantity;
    }
    
    public void setToQuantity(String toQuantity) {
        this.toQuantity = toQuantity;
    }
    
}
