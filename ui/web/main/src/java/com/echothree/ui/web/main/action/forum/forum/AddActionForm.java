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

package com.echothree.ui.web.main.action.forum.forum;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.form.GetForumTypeChoicesForm;
import com.echothree.control.user.forum.common.result.GetForumTypeChoicesResult;
import com.echothree.control.user.icon.common.IconUtil;
import com.echothree.control.user.icon.common.form.GetIconChoicesForm;
import com.echothree.control.user.icon.common.result.GetIconChoicesResult;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.form.GetSequenceChoicesForm;
import com.echothree.control.user.sequence.common.result.GetSequenceChoicesResult;
import com.echothree.model.control.forum.common.choice.ForumTypeChoicesBean;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.common.choice.IconChoicesBean;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.choice.SequenceChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ForumAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ForumTypeChoicesBean forumTypeChoices;
    private IconChoicesBean iconChoices;
    private SequenceChoicesBean forumThreadSequenceChoices;
    private SequenceChoicesBean forumMessageSequenceChoices;
    
    private String forumName;
    private String forumTypeChoice;
    private String iconChoice;
    private String forumThreadSequenceChoice;
    private String forumMessageSequenceChoice;
    private String sortOrder;
    private String description;
    
    private void setupForumTypeChoices()
            throws NamingException {
        if(forumTypeChoices == null) {
            GetForumTypeChoicesForm commandForm = ForumUtil.getHome().getGetForumTypeChoicesForm();

            commandForm.setDefaultForumTypeChoice(forumTypeChoice);
            commandForm.setAllowNullChoice(Boolean.FALSE.toString());

            CommandResult commandResult = ForumUtil.getHome().getForumTypeChoices(userVisitPK, commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetForumTypeChoicesResult result = (GetForumTypeChoicesResult)executionResult.getResult();
            forumTypeChoices = result.getForumTypeChoices();

            if(forumTypeChoice == null)
                forumTypeChoice = forumTypeChoices.getDefaultValue();
        }
    }
    
    private void setupIconChoices()
            throws NamingException {
        if(iconChoices == null) {
            GetIconChoicesForm commandForm = IconUtil.getHome().getGetIconChoicesForm();

            commandForm.setIconUsageTypeName(IconConstants.IconUsageType_FORUM);
            commandForm.setDefaultIconChoice(iconChoice);
            commandForm.setAllowNullChoice(Boolean.TRUE.toString());

            CommandResult commandResult = IconUtil.getHome().getIconChoices(userVisitPK, commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetIconChoicesResult getIconChoicesResult = (GetIconChoicesResult)executionResult.getResult();
            iconChoices = getIconChoicesResult.getIconChoices();

            if(iconChoice == null)
                iconChoice = iconChoices.getDefaultValue();
        }
    }
    
    private void setupForumThreadSequenceChoices()
            throws NamingException {
        if(forumThreadSequenceChoices == null) {
            GetSequenceChoicesForm commandForm = SequenceUtil.getHome().getGetSequenceChoicesForm();

            commandForm.setSequenceTypeName(SequenceTypes.FORUM_THREAD.name());
            commandForm.setDefaultSequenceChoice(forumThreadSequenceChoice);
            commandForm.setAllowNullChoice(Boolean.TRUE.toString());

            CommandResult commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetSequenceChoicesResult result = (GetSequenceChoicesResult)executionResult.getResult();
            forumThreadSequenceChoices = result.getSequenceChoices();

            if(forumThreadSequenceChoice == null)
                forumThreadSequenceChoice = forumThreadSequenceChoices.getDefaultValue();
        }
    }
    
    private void setupForumMessageSequenceChoices()
            throws NamingException {
        if(forumMessageSequenceChoices == null) {
            GetSequenceChoicesForm commandForm = SequenceUtil.getHome().getGetSequenceChoicesForm();

            commandForm.setSequenceTypeName(SequenceTypes.FORUM_MESSAGE.name());
            commandForm.setDefaultSequenceChoice(forumMessageSequenceChoice);
            commandForm.setAllowNullChoice(Boolean.TRUE.toString());

            CommandResult commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetSequenceChoicesResult result = (GetSequenceChoicesResult)executionResult.getResult();
            forumMessageSequenceChoices = result.getSequenceChoices();

            if(forumMessageSequenceChoice == null)
                forumMessageSequenceChoice = forumMessageSequenceChoices.getDefaultValue();
        }
    }
    
    public void setForumName(String forumName) {
        this.forumName = forumName;
    }
    
    public String getForumName() {
        return forumName;
    }
    
    public List<LabelValueBean> getForumTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupForumTypeChoices();
        if(forumTypeChoices != null)
            choices = convertChoices(forumTypeChoices);
        
        return choices;
    }
    
    public void setForumTypeChoice(String forumTypeChoice) {
        this.forumTypeChoice = forumTypeChoice;
    }
    
    public String getForumTypeChoice()
            throws NamingException {
        setupForumTypeChoices();
        return forumTypeChoice;
    }
    
    public List<LabelValueBean> getIconChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupIconChoices();
        if(iconChoices != null)
            choices = convertChoices(iconChoices);
        
        return choices;
    }
    
    public void setIconChoice(String iconChoice) {
        this.iconChoice = iconChoice;
    }
    
    public String getIconChoice()
            throws NamingException {
        setupIconChoices();
        return iconChoice;
    }
    
    public List<LabelValueBean> getForumThreadSequenceChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupForumThreadSequenceChoices();
        if(forumThreadSequenceChoices != null)
            choices = convertChoices(forumThreadSequenceChoices);
        
        return choices;
    }
    
    public void setForumThreadSequenceChoice(String forumThreadSequenceChoice) {
        this.forumThreadSequenceChoice = forumThreadSequenceChoice;
    }
    
    public String getForumThreadSequenceChoice()
            throws NamingException {
        setupForumThreadSequenceChoices();
        return forumThreadSequenceChoice;
    }
    
    public List<LabelValueBean> getForumMessageSequenceChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupForumMessageSequenceChoices();
        if(forumMessageSequenceChoices != null)
            choices = convertChoices(forumMessageSequenceChoices);
        
        return choices;
    }
    
    public void setForumMessageSequenceChoice(String forumMessageSequenceChoice) {
        this.forumMessageSequenceChoice = forumMessageSequenceChoice;
    }
    
    public String getForumMessageSequenceChoice()
            throws NamingException {
        setupForumMessageSequenceChoices();
        return forumMessageSequenceChoice;
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
