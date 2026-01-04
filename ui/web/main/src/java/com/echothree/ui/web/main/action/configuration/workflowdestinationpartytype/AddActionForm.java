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

package com.echothree.ui.web.main.action.configuration.workflowdestinationpartytype;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetPartyTypeChoicesResult;
import com.echothree.model.control.party.common.choice.PartyTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="WorkflowDestinationPartyTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private PartyTypeChoicesBean partyTypeChoices;
    
    private String workflowName;
    private String workflowStepName;
    private String workflowDestinationName;
    private String partyTypeChoice;
    
    private void setupPartyTypeChoices() {
        if(partyTypeChoices == null) {
            try {
                var commandForm = PartyUtil.getHome().getGetPartyTypeChoicesForm();
                
                commandForm.setDefaultPartyTypeChoice(partyTypeChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = PartyUtil.getHome().getPartyTypeChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetPartyTypeChoicesResult)executionResult.getResult();
                partyTypeChoices = result.getPartyTypeChoices();
                
                if(partyTypeChoice == null) {
                    partyTypeChoice = partyTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, partyTypeChoices remains null, no default
            }
        }
    }
    
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
    
    public String getWorkflowName() {
        return workflowName;
    }
    
    public void setWorkflowStepName(String workflowStepName) {
        this.workflowStepName = workflowStepName;
    }
    
    public String getWorkflowStepName() {
        return workflowStepName;
    }
    
    public void setWorkflowDestinationName(String workflowDestinationName) {
        this.workflowDestinationName = workflowDestinationName;
    }
    
    public String getWorkflowDestinationName() {
        return workflowDestinationName;
    }
    
    public List<LabelValueBean> getPartyTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupPartyTypeChoices();
        if(partyTypeChoices != null) {
            choices = convertChoices(partyTypeChoices);
        }
        
        return choices;
    }
    
    public void setPartyTypeChoice(String partyTypeChoice) {
        this.partyTypeChoice = partyTypeChoice;
    }
    
    public String getPartyTypeChoice() {
        setupPartyTypeChoices();
        
        return partyTypeChoice;
    }

}
