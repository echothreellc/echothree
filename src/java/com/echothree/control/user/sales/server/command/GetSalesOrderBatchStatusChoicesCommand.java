// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.sales.server.command;

import com.echothree.control.user.sales.common.form.GetSalesOrderBatchStatusChoicesForm;
import com.echothree.control.user.sales.common.result.GetSalesOrderBatchStatusChoicesResult;
import com.echothree.control.user.sales.common.result.SalesResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.sales.server.logic.SalesOrderBatchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetSalesOrderBatchStatusChoicesCommand
        extends BaseSimpleCommand<GetSalesOrderBatchStatusChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.SalesOrderBatchStatus.name(), SecurityRoles.Choices.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultSalesOrderBatchStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetSalesOrderBatchStatusChoicesCommand */
    public GetSalesOrderBatchStatusChoicesCommand(UserVisitPK userVisitPK, GetSalesOrderBatchStatusChoicesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        GetSalesOrderBatchStatusChoicesResult result = SalesResultFactory.getGetSalesOrderBatchStatusChoicesResult();
        String batchName = form.getBatchName();
        Batch batch = batchName == null? null: SalesOrderBatchLogic.getInstance().getBatchByName(this, batchName);

        if(!this.hasExecutionErrors()) {
            String defaultSalesOrderBatchStatusChoice = form.getDefaultSalesOrderBatchStatusChoice();
            boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

            result.setSalesOrderBatchStatusChoices(SalesOrderBatchLogic.getInstance().getSalesOrderBatchStatusChoices(defaultSalesOrderBatchStatusChoice,
                    getPreferredLanguage(), allowNullChoice, batch, getPartyPK()));
        }

        return result;
    }
    
}
