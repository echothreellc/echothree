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

package com.echothree.ui.web.main.action.item.itemimagetype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemImageTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private MimeTypeChoicesBean preferredMimeTypeChoices;

    private String itemImageTypeName;
    private String preferredMimeTypeChoice;
    private String quality;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public void setupPreferredMimeTypeChoices()
            throws NamingException {
        if(preferredMimeTypeChoices == null) {
            var form = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            form.setMimeTypeUsageTypeName(MimeTypeUsageTypes.IMAGE.name());
            form.setDefaultMimeTypeChoice(preferredMimeTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetMimeTypeChoicesResult)executionResult.getResult();
            preferredMimeTypeChoices = result.getMimeTypeChoices();

            if(preferredMimeTypeChoice == null) {
                preferredMimeTypeChoice = preferredMimeTypeChoices.getDefaultValue();
            }
        }
    }

    public void setItemImageTypeName(String itemImageTypeName) {
        this.itemImageTypeName = itemImageTypeName;
    }
    
    public String getItemImageTypeName() {
        return itemImageTypeName;
    }
    
    public List<LabelValueBean> getPreferredMimeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupPreferredMimeTypeChoices();
        if(preferredMimeTypeChoices != null)
            choices = convertChoices(preferredMimeTypeChoices);

        return choices;
    }

    public void setPreferredMimeTypeChoice(String preferredMimeTypeChoice) {
        this.preferredMimeTypeChoice = preferredMimeTypeChoice;
    }

    public String getPreferredMimeTypeChoice()
            throws NamingException {
        setupPreferredMimeTypeChoices();
        return preferredMimeTypeChoice;
    }

    /**
     * Returns the quality.
     * @return the quality
     */
    public String getQuality() {
        return quality;
    }

    /**
     * Sets the quality.
     * @param quality the quality to set
     */
    public void setQuality(String quality) {
        this.quality = quality;
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
        
        isDefault = false;
    }

}
