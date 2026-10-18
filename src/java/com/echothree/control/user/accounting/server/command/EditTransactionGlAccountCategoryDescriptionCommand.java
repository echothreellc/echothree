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
import com.echothree.control.user.accounting.common.edit.TransactionGlAccountCategoryDescriptionEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditTransactionGlAccountCategoryDescriptionResult;
import com.echothree.control.user.accounting.common.spec.TransactionGlAccountCategoryDescriptionSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.TransactionGlAccountCategoryLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategoryDescription;
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
public class EditTransactionGlAccountCategoryDescriptionCommand
        extends BaseAbstractEditCommand<TransactionGlAccountCategoryDescriptionSpec, TransactionGlAccountCategoryDescriptionEdit, EditTransactionGlAccountCategoryDescriptionResult, TransactionGlAccountCategoryDescription, TransactionGlAccountCategory> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionGlAccountCategory.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TransactionGlAccountCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    TransactionGlAccountCategoryLogic transactionGlAccountCategoryLogic;

    /** Creates a new instance of EditTransactionGlAccountCategoryDescriptionCommand */
    public EditTransactionGlAccountCategoryDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTransactionGlAccountCategoryDescriptionResult getResult() {
        return AccountingResultFactory.getEditTransactionGlAccountCategoryDescriptionResult();
    }

    @Override
    public TransactionGlAccountCategoryDescriptionEdit getEdit() {
        return AccountingEditFactory.getTransactionGlAccountCategoryDescriptionEdit();
    }

    @Override
    public TransactionGlAccountCategoryDescription getEntity(EditTransactionGlAccountCategoryDescriptionResult result) {
        TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription = null;
        var transactionTypeName = spec.getTransactionTypeName();
        var transactionGlAccountCategoryName = spec.getTransactionGlAccountCategoryName();
        var transactionGlAccountCategory = transactionGlAccountCategoryLogic.getTransactionGlAccountCategoryByName(this,
                transactionTypeName, transactionGlAccountCategoryName);

        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                transactionGlAccountCategoryDescription = accountingControl.getTransactionGlAccountCategoryDescription(transactionGlAccountCategory,
                        language, editModeToEntityPermission(editMode));

                if(transactionGlAccountCategoryDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownTransactionGlAccountCategoryDescription.name(), transactionTypeName,
                            transactionGlAccountCategoryName, languageIsoName);
                }
            }
        }

        return transactionGlAccountCategoryDescription;
    }

    @Override
    public TransactionGlAccountCategory getLockEntity(TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription) {
        return transactionGlAccountCategoryDescription.getTransactionGlAccountCategory();
    }

    @Override
    public void fillInResult(EditTransactionGlAccountCategoryDescriptionResult result, TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription) {
        result.setTransactionGlAccountCategoryDescription(accountingControl.getTransactionGlAccountCategoryDescriptionTransfer(getUserVisit(), transactionGlAccountCategoryDescription));
    }

    @Override
    public void doLock(TransactionGlAccountCategoryDescriptionEdit edit, TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription) {
        edit.setDescription(transactionGlAccountCategoryDescription.getDescription());
    }

    @Override
    public void doUpdate(TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription) {
        var transactionGlAccountCategoryDescriptionValue = accountingControl.getTransactionGlAccountCategoryDescriptionValue(transactionGlAccountCategoryDescription);

        transactionGlAccountCategoryDescriptionValue.setDescription(edit.getDescription());

        accountingControl.updateTransactionGlAccountCategoryDescriptionFromValue(transactionGlAccountCategoryDescriptionValue, getPartyPK());
    }

}
