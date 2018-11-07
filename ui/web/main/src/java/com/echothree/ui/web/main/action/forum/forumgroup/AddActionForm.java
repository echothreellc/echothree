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

package com.echothree.ui.web.main.action.forum.forumgroup;

import com.echothree.control.user.icon.common.IconUtil;
import com.echothree.control.user.icon.common.form.GetIconChoicesForm;
import com.echothree.control.user.icon.common.result.GetIconChoicesResult;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.common.choice.IconChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ForumGroupAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private IconChoicesBean iconChoices;
    
    private String forumGroupName;
    private String iconChoice;
    private String sortOrder;
    private String description;
    
    private void setupIconChoices() {
        if(iconChoices == null) {
            try {
                GetIconChoicesForm commandForm = IconUtil.getHome().getGetIconChoicesForm();
                
                // TODO: iconUsageType
                commandForm.setIconUsageTypeName(IconConstants.IconUsageType_FORUM_GROUP);
                commandForm.setDefaultIconChoice(iconChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = IconUtil.getHome().getIconChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetIconChoicesResult getIconChoicesResult = (GetIconChoicesResult)executionResult.getResult();
                iconChoices = getIconChoicesResult.getIconChoices();
                
                if(iconChoice == null)
                    iconChoice = iconChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, iconChoices remains null, no default
            }
        }
    }
    
    public void setForumGroupName(String forumGroupName) {
        this.forumGroupName = forumGroupName;
    }
    
    public String getForumGroupName() {
        return forumGroupName;
    }
    
    public List<LabelValueBean> getIconChoices() {
        List<LabelValueBean> choices = null;
        
        setupIconChoices();
        if(iconChoices != null)
            choices = convertChoices(iconChoices);
        
        return choices;
    }
    
    public void setIconChoice(String iconChoice) {
        this.iconChoice = iconChoice;
    }
    
    public String getIconChoice() {
        setupIconChoices();
        return iconChoice;
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
    }
    
}
