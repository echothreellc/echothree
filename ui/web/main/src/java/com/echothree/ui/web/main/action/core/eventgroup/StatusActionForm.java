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

package com.echothree.ui.web.main.action.core.eventgroup;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetEventGroupStatusChoicesResult;
import com.echothree.model.control.core.common.choice.EventGroupStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EventGroupStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private EventGroupStatusChoicesBean eventGroupStatusChoices;
    
    private String eventGroupName;
    private String eventGroupStatusChoice;
    
    public void setupEventGroupStatusChoices()
            throws NamingException {
        if(eventGroupStatusChoices == null) {
            var form = CoreUtil.getHome().getGetEventGroupStatusChoicesForm();

            form.setEventGroupName(eventGroupName);
            form.setDefaultEventGroupStatusChoice(eventGroupStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = CoreUtil.getHome().getEventGroupStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetEventGroupStatusChoicesResult)executionResult.getResult();
            eventGroupStatusChoices = result.getEventGroupStatusChoices();

            if(eventGroupStatusChoice == null) {
                eventGroupStatusChoice = eventGroupStatusChoices.getDefaultValue();
            }
        }
    }
    
    public String getEventGroupName() {
        return eventGroupName;
    }
    
    public void setEventGroupName(String eventGroupName) {
        this.eventGroupName = eventGroupName;
    }
    
    public String getEventGroupStatusChoice() {
        return eventGroupStatusChoice;
    }
    
    public void setEventGroupStatusChoice(String eventGroupStatusChoice) {
        this.eventGroupStatusChoice = eventGroupStatusChoice;
    }
    
    public List<LabelValueBean> getEventGroupStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEventGroupStatusChoices();
        if(eventGroupStatusChoices != null) {
            choices = convertChoices(eventGroupStatusChoices);
        }
        
        return choices;
    }
    
}
