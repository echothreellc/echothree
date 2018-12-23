// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.accounting.server.logic;

import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class GlAccountLogic
        extends BaseLogic {

    private GlAccountLogic() {
        super();
    }

    private static class GlAccountLogicHolder {
        static GlAccountLogic instance = new GlAccountLogic();
    }

    public static GlAccountLogic getInstance() {
        return GlAccountLogicHolder.instance;
    }
    
    /**
     *
     * @param eea The ExecutionErrorAccumulator that any errors that are encountered should be added to.
     * @param glAccounts A list of GlAccounts that will be drawn from to find a result.
     * @param glAccountCategoryName If no glAccount is found in glAccounts, this will be used to find a default.
     * @param unknownDefaultGlAccountError If no default is found, this error will be added to the ExecutionErrorAccumulator.
     * @return The glAccount to use, unless null.
     */
    public GlAccount getDefaultGlAccountByCategory(final ExecutionErrorAccumulator eea, final GlAccount glAccounts[], final String glAccountCategoryName,
            final String unknownDefaultGlAccountError) {
        GlAccount glAccount = null;

        for(int i = 0 ; glAccount == null && i < glAccounts.length ; i++) {
            glAccount = glAccounts[i];
        }

        if(glAccount == null) {
            AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
            GlAccountCategory glAccountCategory = accountingControl.getGlAccountCategoryByName(glAccountCategoryName);

            if(glAccountCategory != null) {
                glAccount = accountingControl.getDefaultGlAccount(glAccountCategory);

                if(glAccount == null) {
                    eea.addExecutionError(unknownDefaultGlAccountError);
                }
            } else {
                eea.addExecutionError(ExecutionErrors.UnknownGlAccountCategoryName.name(), glAccountCategoryName);
            }
        }

        return glAccount;
    }

}
