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

package com.echothree.control.user.party.client.helper;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.control.user.party.common.result.GetPersonalTitleChoicesResult;
import com.echothree.model.control.party.common.choice.PersonalTitleChoicesBean;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import javax.naming.NamingException;

public class PersonalTitlesHelper {
    
    private PersonalTitlesHelper() {
        super();
    }
    
    private static class PersonalTitlesHelperHolder {
        static PersonalTitlesHelper instance = new PersonalTitlesHelper();
    }
    
    public static PersonalTitlesHelper getInstance() {
        return PersonalTitlesHelperHolder.instance;
    }
    
    public PersonalTitleChoicesBean getPersonalTitleChoices(final UserVisitPK userVisitPK, final Boolean allowNullChoice)
            throws NamingException {
        PersonalTitleChoicesBean personalTitleChoices = null;
        var commandForm = PartyFormFactory.getGetPersonalTitleChoicesForm();

        commandForm.setAllowNullChoice(allowNullChoice.toString());

        var commandResult = PartyUtil.getHome().getPersonalTitleChoices(userVisitPK, commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPersonalTitleChoicesResult)executionResult.getResult();

            personalTitleChoices = result.getPersonalTitleChoices();
        }

        return personalTitleChoices;
    }

}
