// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.form.GetTransactionTimeTypeChoicesForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.model.control.accounting.server.control.TransactionTimeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetTransactionTimeTypeChoicesCommand
        extends BaseSimpleCommand<GetTransactionTimeTypeChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionTimeType.name(), SecurityRoles.Choices.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DefaultTransactionTimeTypeChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetTransactionTimeTypeChoicesCommand */
    public GetTransactionTimeTypeChoicesCommand(UserVisitPK userVisitPK, GetTransactionTimeTypeChoicesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = AccountingResultFactory.getGetTransactionTimeTypeChoicesResult();
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var defaultTransactionTimeTypeChoice = form.getDefaultTransactionTimeTypeChoice();
        var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

        result.setTransactionTimeTypeChoices(transactionTimeControl.getTransactionTimeTypeChoices(defaultTransactionTimeTypeChoice, getPreferredLanguage(), allowNullChoice));

        return result;
    }
    
}
