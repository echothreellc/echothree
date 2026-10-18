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
import com.echothree.control.user.accounting.common.edit.TransactionTypeDescriptionEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditTransactionTypeDescriptionResult;
import com.echothree.control.user.accounting.common.spec.TransactionTypeDescriptionSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.TransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.accounting.server.entity.TransactionTypeDescription;
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
public class EditTransactionTypeDescriptionCommand
        extends BaseAbstractEditCommand<TransactionTypeDescriptionSpec, TransactionTypeDescriptionEdit, EditTransactionTypeDescriptionResult, TransactionTypeDescription, TransactionType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionType.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    TransactionTypeLogic transactionTypeLogic;

    /** Creates a new instance of EditTransactionTypeDescriptionCommand */
    public EditTransactionTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTransactionTypeDescriptionResult getResult() {
        return AccountingResultFactory.getEditTransactionTypeDescriptionResult();
    }

    @Override
    public TransactionTypeDescriptionEdit getEdit() {
        return AccountingEditFactory.getTransactionTypeDescriptionEdit();
    }

    @Override
    public TransactionTypeDescription getEntity(EditTransactionTypeDescriptionResult result) {
        return transactionTypeLogic.getTransactionTypeDescriptionBySpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public TransactionType getLockEntity(TransactionTypeDescription transactionTypeDescription) {
        return transactionTypeDescription.getTransactionType();
    }

    @Override
    public void fillInResult(EditTransactionTypeDescriptionResult result, TransactionTypeDescription transactionTypeDescription) {
        result.setTransactionTypeDescription(accountingControl.getTransactionTypeDescriptionTransfer(getUserVisit(), transactionTypeDescription));
    }

    @Override
    public void doLock(TransactionTypeDescriptionEdit edit, TransactionTypeDescription transactionTypeDescription) {
        edit.setDescription(transactionTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(TransactionTypeDescription transactionTypeDescription) {
        var transactionTypeDescriptionValue = accountingControl.getTransactionTypeDescriptionValue(transactionTypeDescription);

        transactionTypeDescriptionValue.setDescription(edit.getDescription());

        accountingControl.updateTransactionTypeDescriptionFromValue(transactionTypeDescriptionValue, getPartyPK());
    }

}
