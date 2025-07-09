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

package com.echothree.ui.web.main.action.item.itemdescriptiontypeuse;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemDescriptionTypeUseTypeChoicesResult;
import com.echothree.model.control.item.common.choice.ItemDescriptionTypeUseTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemDescriptionTypeUseAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ItemDescriptionTypeUseTypeChoicesBean itemDescriptionTypeUseTypeChoices;

    private String itemDescriptionTypeName;
    private String itemDescriptionTypeUseTypeChoice;
    
    public void setupItemDescriptionTypeUseTypeChoices()
            throws NamingException {
        if(itemDescriptionTypeUseTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemDescriptionTypeUseTypeChoicesForm();

            form.setDefaultItemDescriptionTypeUseTypeChoice(itemDescriptionTypeUseTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ItemUtil.getHome().getItemDescriptionTypeUseTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getItemDescriptionTypeUseTypeChoicesResult = (GetItemDescriptionTypeUseTypeChoicesResult)executionResult.getResult();
            itemDescriptionTypeUseTypeChoices = getItemDescriptionTypeUseTypeChoicesResult.getItemDescriptionTypeUseTypeChoices();

            if(itemDescriptionTypeUseTypeChoice == null) {
                itemDescriptionTypeUseTypeChoice = itemDescriptionTypeUseTypeChoices.getDefaultValue();
            }
        }
    }

    public void setItemDescriptionTypeName(String itemDescriptionTypeName) {
        this.itemDescriptionTypeName = itemDescriptionTypeName;
    }
    
    public String getItemDescriptionTypeName() {
        return itemDescriptionTypeName;
    }
    
    public String getItemDescriptionTypeUseTypeChoice()
            throws NamingException {
        setupItemDescriptionTypeUseTypeChoices();

        return itemDescriptionTypeUseTypeChoice;
    }

    public void setItemDescriptionTypeUseTypeChoice(String itemDescriptionTypeUseTypeChoice) {
        this.itemDescriptionTypeUseTypeChoice = itemDescriptionTypeUseTypeChoice;
    }

    public List<LabelValueBean> getItemDescriptionTypeUseTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupItemDescriptionTypeUseTypeChoices();
        if(itemDescriptionTypeUseTypeChoices != null)
            choices = convertChoices(itemDescriptionTypeUseTypeChoices);

        return choices;
    }

}
