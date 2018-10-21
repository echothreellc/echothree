// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.party.remote.form.GetNameSuffixChoicesForm;
import com.echothree.control.user.party.remote.form.PartyFormFactory;
import com.echothree.control.user.party.remote.result.GetNameSuffixChoicesResult;
import com.echothree.model.control.party.remote.choice.NameSuffixChoicesBean;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import javax.naming.NamingException;

public class NameSuffixesHelper {
    
    private NameSuffixesHelper() {
        super();
    }
    
    private static class NameSuffixesHelperHolder {
        static NameSuffixesHelper instance = new NameSuffixesHelper();
    }
    
    public static NameSuffixesHelper getInstance() {
        return NameSuffixesHelperHolder.instance;
    }
    
    public NameSuffixChoicesBean getNameSuffixChoices(final UserVisitPK userVisitPK, final Boolean allowNullChoice)
            throws NamingException {
        NameSuffixChoicesBean nameSuffixChoices = null;
        GetNameSuffixChoicesForm commandForm = PartyFormFactory.getGetNameSuffixChoicesForm();

        commandForm.setAllowNullChoice(allowNullChoice.toString());

        CommandResult commandResult = PartyUtil.getHome().getNameSuffixChoices(userVisitPK, commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetNameSuffixChoicesResult result = (GetNameSuffixChoicesResult)executionResult.getResult();

            nameSuffixChoices = result.getNameSuffixChoices();
        }

        return nameSuffixChoices;
    }

}
