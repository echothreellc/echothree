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

package com.echothree.ui.web.main.action.item.item;

import com.echothree.control.user.rating.common.RatingUtil;
import com.echothree.control.user.rating.common.result.GetRatingTypeListItemChoicesResult;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.rating.common.choice.RatingTypeListItemChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemRatingAdd")
public class RatingAddActionForm
        extends BaseActionForm {
    
    private RatingTypeListItemChoicesBean ratingTypeListItemChoices;
    
    private String itemName;
    private String ratingTypeName;
    private String ratingName;
    private String ratingTypeListItemChoice;
    
    private void setupRatingTypeListItemChoices() {
        if(ratingTypeListItemChoices == null) {
            try {
                var commandForm = RatingUtil.getHome().getGetRatingTypeListItemChoicesForm();
                
                if(ratingName == null) {
                    commandForm.setComponentVendorName(ComponentVendors.ECHO_THREE.name());
                    commandForm.setEntityTypeName(EntityTypes.Item.name());
                    commandForm.setRatingTypeName(ratingTypeName);
                } else {
                    commandForm.setRatingName(ratingName);
                }
                commandForm.setDefaultRatingTypeListItemChoice(ratingTypeListItemChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = RatingUtil.getHome().getRatingTypeListItemChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var getRatingTypeListItemChoicesResult = (GetRatingTypeListItemChoicesResult)executionResult.getResult();
                ratingTypeListItemChoices = getRatingTypeListItemChoicesResult.getRatingTypeListItemChoices();
                
                if(ratingTypeListItemChoice == null) {
                    ratingTypeListItemChoice = ratingTypeListItemChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, ratingTypeListItemChoices remains null, no default
            }
        }
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setRatingTypeName(String ratingTypeName) {
        this.ratingTypeName = ratingTypeName;
    }
    
    public String getRatingTypeName() {
        return ratingTypeName;
    }
    
    public void setRatingName(String ratingName) {
        this.ratingName = ratingName;
    }
    
    public String getRatingName() {
        return ratingName;
    }
    
    public List<LabelValueBean> getRatingTypeListItemChoices() {
        List<LabelValueBean> choices = null;
        
        setupRatingTypeListItemChoices();
        if(ratingTypeListItemChoices != null) {
            choices = convertChoices(ratingTypeListItemChoices);
        }
        
        return choices;
    }
    
    public void setRatingTypeListItemChoice(String ratingTypeListItemChoice) {
        this.ratingTypeListItemChoice = ratingTypeListItemChoice;
    }
    
    public String getRatingTypeListItemChoice() {
        setupRatingTypeListItemChoices();
        
        return ratingTypeListItemChoice;
    }
    
}
