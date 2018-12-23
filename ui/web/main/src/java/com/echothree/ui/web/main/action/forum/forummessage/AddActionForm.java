// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.core.common.form.GetMimeTypeChoicesForm;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.control.user.icon.common.IconUtil;
import com.echothree.control.user.icon.common.form.GetIconChoicesForm;
import com.echothree.control.user.icon.common.result.GetIconChoicesResult;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.common.choice.IconChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
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
    
    private void setupForumMessageIconChoices() {
        if(forumMessageIconChoices == null) {
            try {
                GetIconChoicesForm commandForm = IconUtil.getHome().getGetIconChoicesForm();
                
                commandForm.setIconUsageTypeName(IconConstants.IconUsageType_FORUM_MESSAGE);
                commandForm.setDefaultIconChoice(forumMessageIconChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = IconUtil.getHome().getIconChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetIconChoicesResult result = (GetIconChoicesResult)executionResult.getResult();
                forumMessageIconChoices = result.getIconChoices();
                
                if(forumMessageIconChoice == null) {
                    forumMessageIconChoice = forumMessageIconChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, forumMessageIconChoices remains null, no default
            }
        }
    }
    
    private void setupContentMimeTypeChoices() {
        if(contentMimeTypeChoices == null) {
            try {
                GetMimeTypeChoicesForm commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();
                
                commandForm.setDefaultMimeTypeChoice(contentMimeTypeChoice);
                commandForm.setAllowNullChoice(Boolean.FALSE.toString());
                commandForm.setForumMessageName(getParentForumMessageName());
                
                CommandResult commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetMimeTypeChoicesResult result = (GetMimeTypeChoicesResult)executionResult.getResult();
                contentMimeTypeChoices = result.getMimeTypeChoices();
                
                if(contentMimeTypeChoice == null) {
                    contentMimeTypeChoice = contentMimeTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, contentMimeTypeChoices remains null, no default
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
    
    public List<LabelValueBean> getForumMessageIconChoices() {
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
    
    public String getForumMessageIconChoice() {
        setupForumMessageIconChoices();
        
        return forumMessageIconChoice;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public List<LabelValueBean> getContentMimeTypeChoices() {
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
    
    public String getContentMimeTypeChoice() {
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
