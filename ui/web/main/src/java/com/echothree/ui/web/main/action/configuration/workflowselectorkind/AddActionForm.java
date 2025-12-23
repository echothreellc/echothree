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

package com.echothree.ui.web.main.action.configuration.workflowselectorkind;

import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.GetSelectorKindChoicesResult;
import com.echothree.model.control.selector.common.choice.SelectorKindChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="WorkflowSelectorKindAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SelectorKindChoicesBean selectorKindChoices;
    
    private String workflowName;
    private String selectorKindChoice;
    
    private void setupSelectorKindChoices() {
        if(selectorKindChoices == null) {
            try {
                var commandForm = SelectorUtil.getHome().getGetSelectorKindChoicesForm();
                
                commandForm.setDefaultSelectorKindChoice(selectorKindChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = SelectorUtil.getHome().getSelectorKindChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetSelectorKindChoicesResult)executionResult.getResult();
                selectorKindChoices = result.getSelectorKindChoices();
                
                if(selectorKindChoice == null)
                    selectorKindChoice = selectorKindChoices.getDefaultValue();
            } catch (NamingException ne) {
                // failed, selectorKindChoices remains null, no default
            }
        }
    }
    
    public List<LabelValueBean> getSelectorKindChoices() {
        List<LabelValueBean> choices = null;
        
        setupSelectorKindChoices();
        if(selectorKindChoices != null)
            choices = convertChoices(selectorKindChoices);
        
        return choices;
    }
    
    public void setSelectorKindChoice(String selectorKindChoice) {
        this.selectorKindChoice = selectorKindChoice;
    }
    
    public String getSelectorKindChoice() {
        setupSelectorKindChoices();
        return selectorKindChoice;
    }
    
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
    
    public String getWorkflowName() {
        return workflowName;
    }
    
}
