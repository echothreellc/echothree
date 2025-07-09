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

package com.echothree.ui.web.main.action.forum.forumgroupforum;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.result.GetForumGroupChoicesResult;
import com.echothree.model.control.forum.common.choice.ForumGroupChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ForumGroupForumAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ForumGroupChoicesBean forumGroupChoices;
    
    private String forumGroupChoice;
    private String forumName;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupForumGroupChoices()
            throws NamingException {
        if(forumGroupChoices == null) {
            var form = ForumUtil.getHome().getGetForumGroupChoicesForm();

            form.setDefaultForumGroupChoice(forumGroupChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ForumUtil.getHome().getForumGroupChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getForumGroupChoicesResult = (GetForumGroupChoicesResult)executionResult.getResult();
            forumGroupChoices = getForumGroupChoicesResult.getForumGroupChoices();

            if(forumGroupChoice == null) {
                forumGroupChoice = forumGroupChoices.getDefaultValue();
            }
        }
    }
    
    public String getForumGroupChoice()
            throws NamingException {
        setupForumGroupChoices();
        
        return forumGroupChoice;
    }
    
    public void setForumGroupChoice(String forumGroupChoice) {
        this.forumGroupChoice = forumGroupChoice;
    }
    
    public List<LabelValueBean> getForumGroupChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupForumGroupChoices();
        if(forumGroupChoices != null) {
            choices = convertChoices(forumGroupChoices);
        }
        
        return choices;
    }
    
    public String getForumName() {
        return forumName;
    }
    
    public void setForumName(String forumName) {
        this.forumName = forumName;
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }
    
}
