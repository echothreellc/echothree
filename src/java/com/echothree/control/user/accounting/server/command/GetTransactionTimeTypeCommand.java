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

import com.echothree.control.user.accounting.common.form.GetTransactionTimeTypeForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.model.control.accounting.server.control.TransactionTimeControl;
import com.echothree.model.control.accounting.server.logic.TransactionTimeTypeLogic;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.TransactionTimeType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetTransactionTimeTypeCommand
        extends BaseSingleEntityCommand<TransactionTimeType, GetTransactionTimeTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionTimeType.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTimeTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetTransactionTimeTypeCommand */
    public GetTransactionTimeTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected TransactionTimeType getEntity() {
        var transactionTimeType = TransactionTimeTypeLogic.getInstance().getTransactionTimeTypeByUniversalSpec(this, form, true);

        if(transactionTimeType != null) {
            sendEvent(transactionTimeType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return transactionTimeType;
    }

    @Override
    protected BaseResult getResult(TransactionTimeType transactionTimeType) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var result = AccountingResultFactory.getGetTransactionTimeTypeResult();

        if(transactionTimeType != null) {
            result.setTransactionTimeType(transactionTimeControl.getTransactionTimeTypeTransfer(getUserVisit(), transactionTimeType));
        }

        return result;
    }
    
}
