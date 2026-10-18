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
import com.echothree.control.user.accounting.common.edit.TransactionEntityRoleTypeDescriptionEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditTransactionEntityRoleTypeDescriptionResult;
import com.echothree.control.user.accounting.common.spec.TransactionEntityRoleTypeDescriptionSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.TransactionEntityRoleTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleTypeDescription;
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
public class EditTransactionEntityRoleTypeDescriptionCommand
        extends BaseAbstractEditCommand<TransactionEntityRoleTypeDescriptionSpec, TransactionEntityRoleTypeDescriptionEdit, EditTransactionEntityRoleTypeDescriptionResult, TransactionEntityRoleTypeDescription, TransactionEntityRoleType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionEntityRoleType.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TransactionEntityRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    TransactionEntityRoleTypeLogic transactionEntityRoleTypeLogic;

    /** Creates a new instance of EditTransactionEntityRoleTypeDescriptionCommand */
    public EditTransactionEntityRoleTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTransactionEntityRoleTypeDescriptionResult getResult() {
        return AccountingResultFactory.getEditTransactionEntityRoleTypeDescriptionResult();
    }

    @Override
    public TransactionEntityRoleTypeDescriptionEdit getEdit() {
        return AccountingEditFactory.getTransactionEntityRoleTypeDescriptionEdit();
    }

    @Override
    public TransactionEntityRoleTypeDescription getEntity(EditTransactionEntityRoleTypeDescriptionResult result) {
        return transactionEntityRoleTypeLogic.getTransactionEntityRoleTypeDescriptionBySpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public TransactionEntityRoleType getLockEntity(TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription) {
        return transactionEntityRoleTypeDescription.getTransactionEntityRoleType();
    }

    @Override
    public void fillInResult(EditTransactionEntityRoleTypeDescriptionResult result, TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription) {
        result.setTransactionEntityRoleTypeDescription(accountingControl.getTransactionEntityRoleTypeDescriptionTransfer(getUserVisit(), transactionEntityRoleTypeDescription));
    }

    @Override
    public void doLock(TransactionEntityRoleTypeDescriptionEdit edit, TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription) {
        edit.setDescription(transactionEntityRoleTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription) {
        var transactionEntityRoleTypeDescriptionValue = accountingControl.getTransactionEntityRoleTypeDescriptionValue(transactionEntityRoleTypeDescription);

        transactionEntityRoleTypeDescriptionValue.setDescription(edit.getDescription());

        accountingControl.updateTransactionEntityRoleTypeDescriptionFromValue(transactionEntityRoleTypeDescriptionValue, getPartyPK());
    }

}
