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

package com.echothree.ui.web.main.action.content.contentforum;

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

@SproutForm(name="ContentForumAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ForumChoicesBean forumChoices;
    
    private String contentCollectionName;
    private String forumChoice;
    private Boolean isDefault;
    
    private void setupForumChoices()
            throws NamingException {
        if(forumChoices == null) {
            var form = ForumUtil.getHome().getGetForumChoicesForm();

            form.setDefaultForumChoice(getForumChoice());
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ForumUtil.getHome().getForumChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetForumChoicesResult)executionResult.getResult();
            forumChoices = result.getForumChoices();

            if(getForumChoice() == null)
                setForumChoice(forumChoices.getDefaultValue());
        }
    }
    
    public List<LabelValueBean> getForumChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupForumChoices();
        if(forumChoices != null)
            choices = convertChoices(forumChoices);
        
        return choices;
    }
    
    public String getContentCollectionName() {
        return contentCollectionName;
    }
    
    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
    }
    
    public String getForumChoice() {
        return forumChoice;
    }
    
    public void setForumChoice(String forumChoice) {
        this.forumChoice = forumChoice;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setIsDefault(false);
    }
    
}
