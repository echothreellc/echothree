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

package com.echothree.ui.web.main.action.forum.forummessage;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.control.user.icon.common.IconUtil;
import com.echothree.control.user.icon.common.result.GetIconChoicesResult;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.common.choice.IconChoicesBean;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ForumMessageAdd")
public class AddActionForm
        extends BaseLanguageActionForm {
    
    private IconChoicesBean forumMessageIconChoices;
    private MimeTypeChoicesBean contentMimeTypeChoices;
    
    private String forumName;
    private String parentForumMessageName;
    private String postedTime;
    private String forumMessageIconChoice;
    private String title;
    private String contentMimeTypeChoice;
    private String content;
    
    private void setupForumMessageIconChoices()
            throws NamingException {
        if(forumMessageIconChoices == null) {
            var commandForm = IconUtil.getHome().getGetIconChoicesForm();

            commandForm.setIconUsageTypeName(IconConstants.IconUsageType_FORUM_MESSAGE);
            commandForm.setDefaultIconChoice(forumMessageIconChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = IconUtil.getHome().getIconChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetIconChoicesResult)executionResult.getResult();
            forumMessageIconChoices = result.getIconChoices();

            if(forumMessageIconChoice == null) {
                forumMessageIconChoice = forumMessageIconChoices.getDefaultValue();
            }
        }
    }
    
    private void setupContentMimeTypeChoices()
            throws NamingException {
        if(contentMimeTypeChoices == null) {
            var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            commandForm.setDefaultMimeTypeChoice(contentMimeTypeChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));
            commandForm.setForumMessageName(getParentForumMessageName());

            var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetMimeTypeChoicesResult)executionResult.getResult();
            contentMimeTypeChoices = result.getMimeTypeChoices();

            if(contentMimeTypeChoice == null) {
                contentMimeTypeChoice = contentMimeTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getForumName() {
        return forumName;
    }
    
    public void setForumName(String forumName) {
        this.forumName = forumName;
    }
    
    public String getParentForumMessageName() {
        return parentForumMessageName;
    }
    
    public void setParentForumMessageName(String parentForumMessageName) {
        this.parentForumMessageName = parentForumMessageName;
    }
    
    public String getPostedTime() {
        return postedTime;
    }
    
    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }
    
    public List<LabelValueBean> getForumMessageIconChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupForumMessageIconChoices();
        if(forumMessageIconChoices != null) {
            choices = convertChoices(forumMessageIconChoices);
        }
        
        return choices;
    }
    
    public void setForumMessageIconChoice(String forumMessageIconChoice) {
        this.forumMessageIconChoice = forumMessageIconChoice;
    }
    
    public String getForumMessageIconChoice()
            throws NamingException {
        setupForumMessageIconChoices();
        
        return forumMessageIconChoice;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public List<LabelValueBean> getContentMimeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupContentMimeTypeChoices();
        if(contentMimeTypeChoices != null) {
            choices = convertChoices(contentMimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setContentMimeTypeChoice(String contentMimeTypeChoice) {
        this.contentMimeTypeChoice = contentMimeTypeChoice;
    }
    
    public String getContentMimeTypeChoice()
            throws NamingException {
        setupContentMimeTypeChoices();
        
        return contentMimeTypeChoice;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
}
