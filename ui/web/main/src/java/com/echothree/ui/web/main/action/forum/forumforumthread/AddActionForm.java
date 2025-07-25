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

package com.echothree.ui.web.main.action.forum.forumforumthread;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.result.GetForumChoicesResult;
import com.echothree.model.control.forum.common.choice.ForumChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ForumForumThreadAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ForumChoicesBean forumChoices;
    
    private String forumChoice;
    private String forumThreadName;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupForumChoices()
            throws NamingException {
        if(forumChoices == null) {
            var form = ForumUtil.getHome().getGetForumChoicesForm();

            form.setDefaultForumChoice(forumChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ForumUtil.getHome().getForumChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getForumChoicesResult = (GetForumChoicesResult)executionResult.getResult();
            forumChoices = getForumChoicesResult.getForumChoices();

            if(forumChoice == null) {
                forumChoice = forumChoices.getDefaultValue();
            }
        }
    }
    
    public String getForumChoice()
            throws NamingException {
        setupForumChoices();
        
        return forumChoice;
    }
    
    public void setForumChoice(String forumChoice) {
        this.forumChoice = forumChoice;
    }
    
    public List<LabelValueBean> getForumChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupForumChoices();
        if(forumChoices != null) {
            choices = convertChoices(forumChoices);
        }
        
        return choices;
    }
    
    public String getForumThreadName() {
        return forumThreadName;
    }
    
    public void setForumThreadName(String forumThreadName) {
        this.forumThreadName = forumThreadName;
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
