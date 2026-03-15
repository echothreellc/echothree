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

package com.echothree.ui.web.main.action.core.event;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetEventTypeChoicesResult;
import com.echothree.model.control.core.common.choice.EventTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EventSend")
public class SendActionForm
        extends BaseActionForm {
    
    private EventTypeChoicesBean eventTypeChoices;

    private String componentVendorName;
    private String entityTypeName;
    private String entityRef;
    private String eventTypeChoice;
    
    public void setupEventTypeChoices()
            throws NamingException {
        if(eventTypeChoices == null) {
            var form = CoreUtil.getHome().getGetEventTypeChoicesForm();

            form.setDefaultEventTypeChoice(eventTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = CoreUtil.getHome().getEventTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetEventTypeChoicesResult)executionResult.getResult();
            eventTypeChoices = result.getEventTypeChoices();

            if(eventTypeChoice == null) {
                eventTypeChoice = eventTypeChoices.getDefaultValue();
            }
        }
    }

    public String getComponentVendorName() {
        return componentVendorName;
    }

    public void setComponentVendorName(final String componentVendorName) {
        this.componentVendorName = componentVendorName;
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public void setEntityTypeName(final String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getEntityRef() {
        return entityRef;
    }
    
    public void setEntityRef(String entityRef) {
        this.entityRef = entityRef;
    }
    
    public String getEventTypeChoice() {
        return eventTypeChoice;
    }
    
    public void setEventTypeChoice(String eventTypeChoice) {
        this.eventTypeChoice = eventTypeChoice;
    }
    
    public List<LabelValueBean> getEventTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEventTypeChoices();
        if(eventTypeChoices != null) {
            choices = convertChoices(eventTypeChoices);
        }
        
        return choices;
    }
    
}
