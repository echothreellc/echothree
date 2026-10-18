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
import com.echothree.control.user.accounting.common.edit.TransactionEntityRoleTypeEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditTransactionEntityRoleTypeResult;
import com.echothree.control.user.accounting.common.spec.TransactionEntityRoleTypeSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.TransactionEntityRoleTypeLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.core.server.entity.EntityType;
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
public class EditTransactionEntityRoleTypeCommand
        extends BaseAbstractEditCommand<TransactionEntityRoleTypeSpec, TransactionEntityRoleTypeEdit, EditTransactionEntityRoleTypeResult, TransactionEntityRoleType, TransactionEntityRoleType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionEntityRoleType.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TransactionEntityRoleTypeName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TransactionEntityRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    EntityTypeLogic entityTypeLogic;

    @Inject
    TransactionEntityRoleTypeLogic transactionEntityRoleTypeLogic;

    /** Creates a new instance of EditTransactionEntityRoleTypeCommand */
    public EditTransactionEntityRoleTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTransactionEntityRoleTypeResult getResult() {
        return AccountingResultFactory.getEditTransactionEntityRoleTypeResult();
    }

    @Override
    public TransactionEntityRoleTypeEdit getEdit() {
        return AccountingEditFactory.getTransactionEntityRoleTypeEdit();
    }

    @Override
    public TransactionEntityRoleType getEntity(EditTransactionEntityRoleTypeResult result) {
        return transactionEntityRoleTypeLogic.getTransactionEntityRoleTypeByName(this, spec.getTransactionTypeName(),
                spec.getTransactionEntityRoleTypeName(), editModeToEntityPermission(editMode));
    }

    @Override
    public TransactionEntityRoleType getLockEntity(TransactionEntityRoleType transactionEntityRoleType) {
        return transactionEntityRoleType;
    }

    @Override
    public void fillInResult(EditTransactionEntityRoleTypeResult result, TransactionEntityRoleType transactionEntityRoleType) {
        result.setTransactionEntityRoleType(accountingControl.getTransactionEntityRoleTypeTransfer(getUserVisit(), transactionEntityRoleType));
    }

    private EntityType entityType;

    @Override
    public void doLock(TransactionEntityRoleTypeEdit edit, TransactionEntityRoleType transactionEntityRoleType) {
        var transactionEntityRoleTypeDescription = accountingControl.getTransactionEntityRoleTypeDescription(transactionEntityRoleType, getPreferredLanguage());
        var transactionEntityRoleTypeDetail = transactionEntityRoleType.getLastDetail();
        var entityTypeDetail = transactionEntityRoleTypeDetail.getEntityType().getLastDetail();
        var componentVendorDetail = entityTypeDetail.getComponentVendor().getLastDetail();

        edit.setTransactionEntityRoleTypeName(transactionEntityRoleTypeDetail.getTransactionEntityRoleTypeName());
        edit.setComponentVendorName(componentVendorDetail.getComponentVendorName());
        edit.setEntityTypeName(entityTypeDetail.getEntityTypeName());
        edit.setSortOrder(transactionEntityRoleTypeDetail.getSortOrder().toString());

        if(transactionEntityRoleTypeDescription != null) {
            edit.setDescription(transactionEntityRoleTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(TransactionEntityRoleType transactionEntityRoleType) {
        var transactionType = transactionEntityRoleType.getLastDetail().getTransactionType();
        var transactionEntityRoleTypeName = edit.getTransactionEntityRoleTypeName();
        var duplicateTransactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeByName(transactionType, transactionEntityRoleTypeName);

        if(duplicateTransactionEntityRoleType == null || transactionEntityRoleType.equals(duplicateTransactionEntityRoleType)) {
            entityType = entityTypeLogic.getEntityTypeByName(this, edit.getComponentVendorName(), edit.getEntityTypeName());
        } else {
            addExecutionError(ExecutionErrors.DuplicateTransactionEntityRoleTypeName.name(),
                    transactionType.getLastDetail().getTransactionTypeName(), transactionEntityRoleTypeName);
        }
    }

    @Override
    public void doUpdate(TransactionEntityRoleType transactionEntityRoleType) {
        var partyPK = getPartyPK();
        var transactionEntityRoleTypeDetailValue = accountingControl.getTransactionEntityRoleTypeDetailValueForUpdate(transactionEntityRoleType);
        var transactionEntityRoleTypeDescription = accountingControl.getTransactionEntityRoleTypeDescriptionForUpdate(transactionEntityRoleType, getPreferredLanguage());
        var description = edit.getDescription();

        transactionEntityRoleTypeDetailValue.setTransactionEntityRoleTypeName(edit.getTransactionEntityRoleTypeName());
        transactionEntityRoleTypeDetailValue.setEntityTypePK(entityType.getPrimaryKey());
        transactionEntityRoleTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        accountingControl.updateTransactionEntityRoleTypeFromValue(transactionEntityRoleTypeDetailValue, partyPK);

        if(transactionEntityRoleTypeDescription == null && description != null) {
            accountingControl.createTransactionEntityRoleTypeDescription(transactionEntityRoleType, getPreferredLanguage(), description, partyPK);
        } else if(transactionEntityRoleTypeDescription != null && description == null) {
            accountingControl.deleteTransactionEntityRoleTypeDescription(transactionEntityRoleTypeDescription, partyPK);
        } else if(transactionEntityRoleTypeDescription != null && description != null) {
            var transactionEntityRoleTypeDescriptionValue = accountingControl.getTransactionEntityRoleTypeDescriptionValue(transactionEntityRoleTypeDescription);

            transactionEntityRoleTypeDescriptionValue.setDescription(description);
            accountingControl.updateTransactionEntityRoleTypeDescriptionFromValue(transactionEntityRoleTypeDescriptionValue, partyPK);
        }
    }

}
