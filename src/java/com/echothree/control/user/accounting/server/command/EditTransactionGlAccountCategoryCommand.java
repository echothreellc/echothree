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
import com.echothree.control.user.accounting.common.edit.TransactionGlAccountCategoryEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditTransactionGlAccountCategoryResult;
import com.echothree.control.user.accounting.common.spec.TransactionGlAccountCategorySpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.GlAccountCategoryLogic;
import com.echothree.model.control.accounting.server.logic.GlAccountLogic;
import com.echothree.model.control.accounting.server.logic.TransactionGlAccountCategoryLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
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
public class EditTransactionGlAccountCategoryCommand
        extends BaseAbstractEditCommand<TransactionGlAccountCategorySpec, TransactionGlAccountCategoryEdit, EditTransactionGlAccountCategoryResult, TransactionGlAccountCategory, TransactionGlAccountCategory> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionGlAccountCategory.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TransactionGlAccountCategoryName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TransactionGlAccountCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    GlAccountCategoryLogic glAccountCategoryLogic;

    @Inject
    GlAccountLogic glAccountLogic;

    @Inject
    TransactionGlAccountCategoryLogic transactionGlAccountCategoryLogic;

    /** Creates a new instance of EditTransactionGlAccountCategoryCommand */
    public EditTransactionGlAccountCategoryCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTransactionGlAccountCategoryResult getResult() {
        return AccountingResultFactory.getEditTransactionGlAccountCategoryResult();
    }

    @Override
    public TransactionGlAccountCategoryEdit getEdit() {
        return AccountingEditFactory.getTransactionGlAccountCategoryEdit();
    }

    @Override
    public TransactionGlAccountCategory getEntity(EditTransactionGlAccountCategoryResult result) {
        return transactionGlAccountCategoryLogic.getTransactionGlAccountCategoryByName(this, spec.getTransactionTypeName(),
                spec.getTransactionGlAccountCategoryName(), editModeToEntityPermission(editMode));
    }

    @Override
    public TransactionGlAccountCategory getLockEntity(TransactionGlAccountCategory transactionGlAccountCategory) {
        return transactionGlAccountCategory;
    }

    @Override
    public void fillInResult(EditTransactionGlAccountCategoryResult result, TransactionGlAccountCategory transactionGlAccountCategory) {
        result.setTransactionGlAccountCategory(accountingControl.getTransactionGlAccountCategoryTransfer(getUserVisit(), transactionGlAccountCategory));
    }

    private GlAccountCategory glAccountCategory;
    private GlAccount glAccount;

    @Override
    public void doLock(TransactionGlAccountCategoryEdit edit, TransactionGlAccountCategory transactionGlAccountCategory) {
        var transactionGlAccountCategoryDescription = accountingControl.getTransactionGlAccountCategoryDescription(transactionGlAccountCategory, getPreferredLanguage());
        var transactionGlAccount = accountingControl.getTransactionGlAccount(transactionGlAccountCategory);
        var transactionGlAccountCategoryDetail = transactionGlAccountCategory.getLastDetail();
        var glAccountCategory = transactionGlAccountCategoryDetail.getGlAccountCategory();

        edit.setTransactionGlAccountCategoryName(transactionGlAccountCategoryDetail.getTransactionGlAccountCategoryName());
        edit.setGlAccountCategoryName(glAccountCategory == null? null: glAccountCategory.getLastDetail().getGlAccountCategoryName());
        edit.setSortOrder(transactionGlAccountCategoryDetail.getSortOrder().toString());
        edit.setGlAccountName(transactionGlAccount == null? null: transactionGlAccount.getGlAccount().getLastDetail().getGlAccountName());

        if(transactionGlAccountCategoryDescription != null) {
            edit.setDescription(transactionGlAccountCategoryDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(TransactionGlAccountCategory transactionGlAccountCategory) {
        var transactionType = transactionGlAccountCategory.getLastDetail().getTransactionType();
        var transactionGlAccountCategoryName = edit.getTransactionGlAccountCategoryName();
        var duplicateTransactionGlAccountCategory = accountingControl.getTransactionGlAccountCategoryByName(transactionType, transactionGlAccountCategoryName);

        if(duplicateTransactionGlAccountCategory == null || transactionGlAccountCategory.equals(duplicateTransactionGlAccountCategory)) {
            var glAccountCategoryName = edit.getGlAccountCategoryName();

            glAccountCategory = glAccountCategoryName == null? null: glAccountCategoryLogic.getGlAccountCategoryByName(this, glAccountCategoryName);

            if(!hasExecutionErrors()) {
                var glAccountName = edit.getGlAccountName();

                glAccount = glAccountName == null? null: glAccountLogic.getGlAccountByName(this, glAccountName);

                if(!hasExecutionErrors()) {
                    if(glAccountCategory != null && glAccount != null && !glAccountCategory.equals(glAccount.getLastDetail().getGlAccountCategory())) {
                        addExecutionError(ExecutionErrors.InvalidGlAccountCategory.name());
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateTransactionGlAccountCategoryName.name(),
                    transactionType.getLastDetail().getTransactionTypeName(), transactionGlAccountCategoryName);
        }
    }

    @Override
    public void doUpdate(TransactionGlAccountCategory transactionGlAccountCategory) {
        var partyPK = getPartyPK();
        var transactionGlAccountCategoryDetailValue = accountingControl.getTransactionGlAccountCategoryDetailValueForUpdate(transactionGlAccountCategory);
        var transactionGlAccountCategoryDescription = accountingControl.getTransactionGlAccountCategoryDescriptionForUpdate(transactionGlAccountCategory, getPreferredLanguage());
        var transactionGlAccount = accountingControl.getTransactionGlAccount(transactionGlAccountCategory);
        var description = edit.getDescription();

        transactionGlAccountCategoryDetailValue.setTransactionGlAccountCategoryName(edit.getTransactionGlAccountCategoryName());
        transactionGlAccountCategoryDetailValue.setGlAccountCategoryPK(glAccountCategory == null? null: glAccountCategory.getPrimaryKey());
        transactionGlAccountCategoryDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        accountingControl.updateTransactionGlAccountCategoryFromValue(transactionGlAccountCategoryDetailValue, partyPK);

        if(transactionGlAccountCategoryDescription == null && description != null) {
            accountingControl.createTransactionGlAccountCategoryDescription(transactionGlAccountCategory, getPreferredLanguage(), description, partyPK);
        } else if(transactionGlAccountCategoryDescription != null && description == null) {
            accountingControl.deleteTransactionGlAccountCategoryDescription(transactionGlAccountCategoryDescription, partyPK);
        } else if(transactionGlAccountCategoryDescription != null && description != null) {
            var transactionGlAccountCategoryDescriptionValue = accountingControl.getTransactionGlAccountCategoryDescriptionValue(transactionGlAccountCategoryDescription);

            transactionGlAccountCategoryDescriptionValue.setDescription(description);
            accountingControl.updateTransactionGlAccountCategoryDescriptionFromValue(transactionGlAccountCategoryDescriptionValue, partyPK);
        }

        if(transactionGlAccount == null && glAccount != null) {
            accountingControl.createTransactionGlAccount(transactionGlAccountCategory, glAccount, partyPK);
        } else if(transactionGlAccount != null && glAccount == null) {
            accountingControl.deleteTransactionGlAccount(transactionGlAccount, partyPK);
        } else if(transactionGlAccount != null && glAccount != null) {
            var transactionGlAccountValue = accountingControl.getTransactionGlAccountValue(transactionGlAccount);

            transactionGlAccountValue.setGlAccountPK(glAccount.getPrimaryKey());
            accountingControl.updateTransactionGlAccountFromValue(transactionGlAccountValue, partyPK);
        }
    }

}
