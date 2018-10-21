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

package com.echothree.ui.web.main.action.item.itemaliastype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.remote.form.GetItemAliasChecksumTypeChoicesForm;
import com.echothree.control.user.item.remote.result.GetItemAliasChecksumTypeChoicesResult;
import com.echothree.model.control.item.remote.choice.ItemAliasChecksumTypeChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemAliasTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ItemAliasChecksumTypeChoicesBean itemAliasChecksumTypeChoices;

    private String itemAliasTypeName;
    private String validationPattern;
    private String itemAliasChecksumTypeChoice;
    private Boolean allowMultiple;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public void setupItemAliasChecksumTypeChoices() {
        if(itemAliasChecksumTypeChoices == null) {
            try {
                GetItemAliasChecksumTypeChoicesForm form = ItemUtil.getHome().getGetItemAliasChecksumTypeChoicesForm();

                form.setDefaultItemAliasChecksumTypeChoice(itemAliasChecksumTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = ItemUtil.getHome().getItemAliasChecksumTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetItemAliasChecksumTypeChoicesResult getItemAliasChecksumTypeChoicesResult = (GetItemAliasChecksumTypeChoicesResult)executionResult.getResult();
                itemAliasChecksumTypeChoices = getItemAliasChecksumTypeChoicesResult.getItemAliasChecksumTypeChoices();

                if(itemAliasChecksumTypeChoice == null) {
                    itemAliasChecksumTypeChoice = itemAliasChecksumTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, itemAliasChecksumTypeChoices remains null, no default
            }
        }
    }

   public void setItemAliasTypeName(String itemAliasTypeName) {
        this.itemAliasTypeName = itemAliasTypeName;
    }
    
    public String getItemAliasTypeName() {
        return itemAliasTypeName;
    }
    
    public String getValidationPattern() {
        return validationPattern;
    }

    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }

    public String getItemAliasChecksumTypeChoice() {
        setupItemAliasChecksumTypeChoices();
        return itemAliasChecksumTypeChoice;
    }

    public void setItemAliasChecksumTypeChoice(String itemAliasChecksumTypeChoice) {
        this.itemAliasChecksumTypeChoice = itemAliasChecksumTypeChoice;
    }

    public List<LabelValueBean> getItemAliasChecksumTypeChoices() {
        List<LabelValueBean> choices = null;

        setupItemAliasChecksumTypeChoices();
        if(itemAliasChecksumTypeChoices != null) {
            choices = convertChoices(itemAliasChecksumTypeChoices);
        }

        return choices;
    }

    public Boolean getAllowMultiple() {
        return allowMultiple;
    }

    public void setAllowMultiple(Boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        allowMultiple = Boolean.FALSE;
        isDefault = Boolean.FALSE;
    }

}
