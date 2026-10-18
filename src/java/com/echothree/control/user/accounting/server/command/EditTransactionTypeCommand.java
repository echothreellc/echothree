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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.TransactionTypeEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditTransactionTypeResult;
import com.echothree.control.user.accounting.common.spec.TransactionTypeSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.TransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditTransactionTypeCommand
        extends BaseAbstractEditCommand<TransactionTypeSpec, TransactionTypeEdit, EditTransactionTypeResult, TransactionType, TransactionType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionType.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    TransactionTypeLogic transactionTypeLogic;

    /** Creates a new instance of EditTransactionTypeCommand */
    public EditTransactionTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTransactionTypeResult getResult() {
        return AccountingResultFactory.getEditTransactionTypeResult();
    }

    @Override
    public TransactionTypeEdit getEdit() {
        return AccountingEditFactory.getTransactionTypeEdit();
    }

    @Override
    public TransactionType getEntity(EditTransactionTypeResult result) {
        return transactionTypeLogic.getTransactionTypeByName(this, spec.getTransactionTypeName(), editModeToEntityPermission(editMode));
    }

    @Override
    public TransactionType getLockEntity(TransactionType transactionType) {
        return transactionType;
    }

    @Override
    public void fillInResult(EditTransactionTypeResult result, TransactionType transactionType) {
        result.setTransactionType(accountingControl.getTransactionTypeTransfer(getUserVisit(), transactionType));
    }

    @Override
    public void doLock(TransactionTypeEdit edit, TransactionType transactionType) {
        var transactionTypeDescription = accountingControl.getTransactionTypeDescription(transactionType, getPreferredLanguage());
        var transactionTypeDetail = transactionType.getLastDetail();

        edit.setTransactionTypeName(transactionTypeDetail.getTransactionTypeName());
        edit.setSortOrder(transactionTypeDetail.getSortOrder().toString());

        if(transactionTypeDescription != null) {
            edit.setDescription(transactionTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(TransactionType transactionType) {
        var transactionTypeName = edit.getTransactionTypeName();
        var duplicateTransactionType = accountingControl.getTransactionTypeByName(transactionTypeName);

        if(duplicateTransactionType != null && !transactionType.equals(duplicateTransactionType)) {
            addExecutionError(ExecutionErrors.DuplicateTransactionTypeName.name(), transactionTypeName);
        }
    }

    @Override
    public void doUpdate(TransactionType transactionType) {
        var partyPK = getPartyPK();
        var transactionTypeDetailValue = accountingControl.getTransactionTypeDetailValueForUpdate(transactionType);
        var transactionTypeDescription = accountingControl.getTransactionTypeDescriptionForUpdate(transactionType, getPreferredLanguage());
        var description = edit.getDescription();

        transactionTypeDetailValue.setTransactionTypeName(edit.getTransactionTypeName());
        transactionTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        accountingControl.updateTransactionTypeFromValue(transactionTypeDetailValue, partyPK);

        if(transactionTypeDescription == null && description != null) {
            accountingControl.createTransactionTypeDescription(transactionType, getPreferredLanguage(), description, partyPK);
        } else if(transactionTypeDescription != null && description == null) {
            accountingControl.deleteTransactionTypeDescription(transactionTypeDescription, partyPK);
        } else if(transactionTypeDescription != null && description != null) {
            var transactionTypeDescriptionValue = accountingControl.getTransactionTypeDescriptionValue(transactionTypeDescription);

            transactionTypeDescriptionValue.setDescription(description);
            accountingControl.updateTransactionTypeDescriptionFromValue(transactionTypeDescriptionValue, partyPK);
        }
    }

}
