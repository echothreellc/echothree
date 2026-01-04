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

package com.echothree.ui.web.main.action.forum.forumthread;

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

@SproutForm(name="ForumThreadAdd")
public class AddActionForm
        extends BaseLanguageActionForm {
    
    private IconChoicesBean forumThreadIconChoices;
    private IconChoicesBean forumMessageIconChoices;
    private MimeTypeChoicesBean feedSummaryMimeTypeChoices;
    private MimeTypeChoicesBean summaryMimeTypeChoices;
    private MimeTypeChoicesBean contentMimeTypeChoices;
    
    private String forumName;
    private String forumThreadIconChoice;
    private String postedTime;
    private String sortOrder;
    private String forumMessageIconChoice;
    private String title;
    private String feedSummaryMimeTypeChoice;
    private String feedSummary;
    private String summaryMimeTypeChoice;
    private String summary;
    private String contentMimeTypeChoice;
    private String content;
    
    private void setupForumThreadIconChoices()
            throws NamingException {
        if(forumThreadIconChoices == null) {
            var commandForm = IconUtil.getHome().getGetIconChoicesForm();

            commandForm.setIconUsageTypeName(IconConstants.IconUsageType_FORUM_THREAD);
            commandForm.setDefaultIconChoice(forumThreadIconChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = IconUtil.getHome().getIconChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetIconChoicesResult)executionResult.getResult();
            forumThreadIconChoices = result.getIconChoices();

            if(forumThreadIconChoice == null) {
                forumThreadIconChoice = forumThreadIconChoices.getDefaultValue();
            }
        }
    }
    
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
    
    private void setupFeedSummaryMimeTypeChoices()
            throws NamingException {
        if(feedSummaryMimeTypeChoices == null) {
            var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            commandForm.setDefaultMimeTypeChoice(feedSummaryMimeTypeChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));
            commandForm.setForumName(getForumName());

            var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetMimeTypeChoicesResult)executionResult.getResult();
            feedSummaryMimeTypeChoices = result.getMimeTypeChoices();

            if(feedSummaryMimeTypeChoice == null) {
                feedSummaryMimeTypeChoice = feedSummaryMimeTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupSummaryMimeTypeChoices()
            throws NamingException {
        if(summaryMimeTypeChoices == null) {
            var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            commandForm.setDefaultMimeTypeChoice(summaryMimeTypeChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));
            commandForm.setForumName(getForumName());

            var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetMimeTypeChoicesResult)executionResult.getResult();
            summaryMimeTypeChoices = result.getMimeTypeChoices();

            if(summaryMimeTypeChoice == null) {
                summaryMimeTypeChoice = summaryMimeTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupContentMimeTypeChoices()
            throws NamingException {
        if(contentMimeTypeChoices == null) {
            var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            commandForm.setDefaultMimeTypeChoice(contentMimeTypeChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));
            commandForm.setForumName(getForumName());

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
    
    public List<LabelValueBean> getForumThreadIconChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupForumThreadIconChoices();
        if(forumThreadIconChoices != null) {
            choices = convertChoices(forumThreadIconChoices);
        }
        
        return choices;
    }
    
    public void setForumThreadIconChoice(String forumThreadIconChoice) {
        this.forumThreadIconChoice = forumThreadIconChoice;
    }
    
    public String getForumThreadIconChoice()
            throws NamingException {
        setupForumThreadIconChoices();
        
        return forumThreadIconChoice;
    }
    
    public String getPostedTime() {
        return postedTime;
    }
    
    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
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
    
    public List<LabelValueBean> getFeedSummaryMimeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupFeedSummaryMimeTypeChoices();
        if(feedSummaryMimeTypeChoices != null) {
            choices = convertChoices(feedSummaryMimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setFeedSummaryMimeTypeChoice(String feedSummaryMimeTypeChoice) {
        this.feedSummaryMimeTypeChoice = feedSummaryMimeTypeChoice;
    }
    
    public String getFeedSummaryMimeTypeChoice()
            throws NamingException {
        setupFeedSummaryMimeTypeChoices();
        
        return feedSummaryMimeTypeChoice;
    }
    
    public String getFeedSummary() {
        return feedSummary;
    }
    
    public void setFeedSummary(String feedSummary) {
        this.feedSummary = feedSummary;
    }
    
    public List<LabelValueBean> getSummaryMimeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupSummaryMimeTypeChoices();
        if(summaryMimeTypeChoices != null) {
            choices = convertChoices(summaryMimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setSummaryMimeTypeChoice(String summaryMimeTypeChoice) {
        this.summaryMimeTypeChoice = summaryMimeTypeChoice;
    }
    
    public String getSummaryMimeTypeChoice()
            throws NamingException {
        setupSummaryMimeTypeChoices();
        
        return summaryMimeTypeChoice;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
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
