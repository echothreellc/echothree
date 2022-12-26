// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.forum.forumpartytyperole;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.form.GetForumRoleTypeChoicesForm;
import com.echothree.control.user.forum.common.result.GetForumRoleTypeChoicesResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetPartyTypeChoicesForm;
import com.echothree.control.user.party.common.result.GetPartyTypeChoicesResult;
import com.echothree.model.control.forum.common.choice.ForumRoleTypeChoicesBean;
import com.echothree.model.control.party.common.choice.PartyTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ForumPartyTypeRoleAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private PartyTypeChoicesBean partyTypeChoices;
    private ForumRoleTypeChoicesBean forumRoleTypeChoices;
    
    private String forumName;
    private String partyTypeChoice;
    private String forumRoleTypeChoice;
    
    private void setupPartyTypeChoices()
            throws NamingException {
        if(partyTypeChoices == null) {
            GetPartyTypeChoicesForm form = PartyUtil.getHome().getGetPartyTypeChoicesForm();

            form.setDefaultPartyTypeChoice(partyTypeChoice);
            form.setAllowNullChoice(Boolean.FALSE.toString());

            CommandResult commandResult = PartyUtil.getHome().getPartyTypeChoices(userVisitPK, form);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetPartyTypeChoicesResult getPartyTypeChoicesResult = (GetPartyTypeChoicesResult)executionResult.getResult();
            partyTypeChoices = getPartyTypeChoicesResult.getPartyTypeChoices();

            if(partyTypeChoice == null) {
                partyTypeChoice = partyTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupForumRoleTypeChoices()
            throws NamingException {
        if(forumRoleTypeChoices == null) {
            GetForumRoleTypeChoicesForm form = ForumUtil.getHome().getGetForumRoleTypeChoicesForm();

            form.setDefaultForumRoleTypeChoice(forumRoleTypeChoice);
            form.setAllowNullChoice(Boolean.FALSE.toString());

            CommandResult commandResult = ForumUtil.getHome().getForumRoleTypeChoices(userVisitPK, form);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetForumRoleTypeChoicesResult getForumRoleTypeChoicesResult = (GetForumRoleTypeChoicesResult)executionResult.getResult();
            forumRoleTypeChoices = getForumRoleTypeChoicesResult.getForumRoleTypeChoices();

            if(forumRoleTypeChoice == null) {
                forumRoleTypeChoice = forumRoleTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getForumName() {
        return forumName;
    }
    
    public void setForumName(String forumName) {
        this.forumName = forumName;
    }
    
    public String getPartyTypeChoice()
            throws NamingException {
        setupPartyTypeChoices();
        
        return partyTypeChoice;
    }
    
    public void setPartyTypeChoice(String partyTypeChoice) {
        this.partyTypeChoice = partyTypeChoice;
    }
    
    public List<LabelValueBean> getPartyTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupPartyTypeChoices();
        if(partyTypeChoices != null) {
            choices = convertChoices(partyTypeChoices);
        }
        
        return choices;
    }
    
    public String getForumRoleTypeChoice()
            throws NamingException {
        setupForumRoleTypeChoices();
        
        return forumRoleTypeChoice;
    }
    
    public void setForumRoleTypeChoice(String forumRoleTypeChoice) {
        this.forumRoleTypeChoice = forumRoleTypeChoice;
    }
    
    public List<LabelValueBean> getForumRoleTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupForumRoleTypeChoices();
        if(forumRoleTypeChoices != null) {
            choices = convertChoices(forumRoleTypeChoices);
        }
        
        return choices;
    }
    
}
