// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.item.item.DescriptionAdd;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetMimeTypeChoicesForm;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.GetItemImageTypeChoicesForm;
import com.echothree.control.user.item.common.result.GetItemImageTypeChoicesResult;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemImageTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemDescriptionAdd")
public class DescriptionAddActionForm
        extends BaseLanguageActionForm {
    
    private MimeTypeChoicesBean mimeTypeChoices;
    private ItemImageTypeChoicesBean itemImageTypeChoices;
    
    private String itemName;
    private String itemDescriptionTypeName;
    private String mimeTypeChoice;
    private String itemImageTypeChoice;
    private String stringDescription;
    private String clobDescription;
    private FormFile blobDescription;
    
    private void setupMimeTypeChoices() {
        if(mimeTypeChoices == null) {
            try {
                GetMimeTypeChoicesForm commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

                commandForm.setDefaultMimeTypeChoice(mimeTypeChoice);
                commandForm.setAllowNullChoice(Boolean.FALSE.toString());
                commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);

                CommandResult commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetMimeTypeChoicesResult result = (GetMimeTypeChoicesResult)executionResult.getResult();
                mimeTypeChoices = result.getMimeTypeChoices();

                if(mimeTypeChoice == null) {
                    mimeTypeChoice = mimeTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, mimeTypeChoices remains null, no default
            }
        }
    }

    private void setupItemImageTypeChoices() {
        if(itemImageTypeChoices == null) {
            try {
                GetItemImageTypeChoicesForm commandForm = ItemUtil.getHome().getGetItemImageTypeChoicesForm();

                commandForm.setDefaultItemImageTypeChoice(itemImageTypeChoice);
                commandForm.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = ItemUtil.getHome().getItemImageTypeChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetItemImageTypeChoicesResult result = (GetItemImageTypeChoicesResult)executionResult.getResult();
                itemImageTypeChoices = result.getItemImageTypeChoices();

                if(itemImageTypeChoice == null) {
                    itemImageTypeChoice = itemImageTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, itemImageTypeChoices remains null, no default
            }
        }
    }

    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getItemDescriptionTypeName() {
        return itemDescriptionTypeName;
    }
    
    public void setItemDescriptionTypeName(String itemDescriptionTypeName) {
        this.itemDescriptionTypeName = itemDescriptionTypeName;
    }
    
    public List<LabelValueBean> getMimeTypeChoices() {
        List<LabelValueBean> choices = null;

        setupMimeTypeChoices();
        if(mimeTypeChoices != null) {
            choices = convertChoices(mimeTypeChoices);
        }

        return choices;
    }

    public void setMimeTypeChoice(String mimeTypeChoice) {
        this.mimeTypeChoice = mimeTypeChoice;
    }

    public String getMimeTypeChoice() {
        setupMimeTypeChoices();

        return mimeTypeChoice;
    }

    public List<LabelValueBean> getItemImageTypeChoices() {
        List<LabelValueBean> choices = null;

        setupItemImageTypeChoices();
        if(itemImageTypeChoices != null) {
            choices = convertChoices(itemImageTypeChoices);
        }

        return choices;
    }

    public void setItemImageTypeChoice(String itemImageTypeChoice) {
        this.itemImageTypeChoice = itemImageTypeChoice;
    }

    public String getItemImageTypeChoice() {
        setupItemImageTypeChoices();

        return itemImageTypeChoice;
    }

    public String getStringDescription() {
        return stringDescription;
    }
    
    public void setStringDescription(String stringDescription) {
        this.stringDescription = stringDescription;
    }
    
    public String getClobDescription() {
        return clobDescription;
    }
    
    public void setClobDescription(String clobDescription) {
        this.clobDescription = clobDescription;
    }
    
    public FormFile getBlobDescription() {
        return blobDescription;
    }
    
    public void setBlobDescription(FormFile blobDescription) {
        this.blobDescription = blobDescription;
    }
    
}
