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

package com.echothree.ui.web.main.action.warehouse.warehousecontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetContactMechanismChoicesResult;
import com.echothree.model.control.contact.common.choice.ContactMechanismChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="WarehousePartyContactMechanismRelationshipAdd")
public class PartyContactMechanismRelationshipAddActionForm
        extends BaseActionForm {
    
    private ContactMechanismChoicesBean toContactMechanismChoices;
    
    private String partyName;
    private String fromContactMechanismName;
    private String toContactMechanismChoice;
    
    public void setupToContactMechanismChoices()
            throws NamingException {
        if(toContactMechanismChoices == null) {
            var form = ContactUtil.getHome().getGetContactMechanismChoicesForm();
                
                form.setPartyName(partyName);
                form.setDefaultContactMechanismChoice(toContactMechanismChoice);
                form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ContactUtil.getHome().getContactMechanismChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getToContactMechanismChoicesResult = (GetContactMechanismChoicesResult)executionResult.getResult();
                toContactMechanismChoices = getToContactMechanismChoicesResult.getContactMechanismChoices();
                
                if(toContactMechanismChoice == null) {
                    toContactMechanismChoice = toContactMechanismChoices.getDefaultValue();
                }
        }
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getFromContactMechanismName() {
        return fromContactMechanismName;
    }
    
    public void setFromContactMechanismName(String fromContactMechanismName) {
        this.fromContactMechanismName = fromContactMechanismName;
    }
    
    public String getToContactMechanismChoice()
            throws NamingException {
        setupToContactMechanismChoices();
        
        return toContactMechanismChoice;
    }
    
    public void setToContactMechanismChoice(String toContactMechanismChoice) {
        this.toContactMechanismChoice = toContactMechanismChoice;
    }
    
    public List<LabelValueBean> getToContactMechanismChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupToContactMechanismChoices();
        if(toContactMechanismChoices != null) {
            choices = convertChoices(toContactMechanismChoices);
        }
        
        return choices;
    }
    
}
