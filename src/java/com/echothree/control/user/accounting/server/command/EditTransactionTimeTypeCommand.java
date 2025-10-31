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

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.TransactionTimeTypeEdit;
import com.echothree.control.user.accounting.common.form.EditTransactionTimeTypeForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditTransactionTimeTypeResult;
import com.echothree.control.user.accounting.common.spec.TransactionTimeTypeUniversalSpec;
import com.echothree.model.control.accounting.server.control.TransactionTimeControl;
import com.echothree.model.control.accounting.server.logic.TransactionTimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.TransactionTimeType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditTransactionTimeTypeCommand
        extends BaseAbstractEditCommand<TransactionTimeTypeUniversalSpec, TransactionTimeTypeEdit, EditTransactionTimeTypeResult, TransactionTimeType, TransactionTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.TransactionTimeType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTimeTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditTransactionTimeTypeCommand */
    public EditTransactionTimeTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTransactionTimeTypeResult getResult() {
        return AccountingResultFactory.getEditTransactionTimeTypeResult();
    }

    @Override
    public TransactionTimeTypeEdit getEdit() {
        return AccountingEditFactory.getTransactionTimeTypeEdit();
    }

    @Override
    public TransactionTimeType getEntity(EditTransactionTimeTypeResult result) {
        return TransactionTimeTypeLogic.getInstance().getTransactionTimeTypeByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public TransactionTimeType getLockEntity(TransactionTimeType transactionTimeType) {
        return transactionTimeType;
    }

    @Override
    public void fillInResult(EditTransactionTimeTypeResult result, TransactionTimeType transactionTimeType) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);

        result.setTransactionTimeType(transactionTimeControl.getTransactionTimeTypeTransfer(getUserVisit(), transactionTimeType));
    }

    @Override
    public void doLock(TransactionTimeTypeEdit edit, TransactionTimeType transactionTimeType) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeTypeDescription = transactionTimeControl.getTransactionTimeTypeDescription(transactionTimeType, getPreferredLanguage());
        var transactionTimeTypeDetail = transactionTimeType.getLastDetail();

        edit.setTransactionTimeTypeName(transactionTimeTypeDetail.getTransactionTimeTypeName());
        edit.setIsDefault(transactionTimeTypeDetail.getIsDefault().toString());
        edit.setSortOrder(transactionTimeTypeDetail.getSortOrder().toString());

        if(transactionTimeTypeDescription != null) {
            edit.setDescription(transactionTimeTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(TransactionTimeType transactionTimeType) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeTypeName = edit.getTransactionTimeTypeName();
        var duplicateTransactionTimeType = transactionTimeControl.getTransactionTimeTypeByName(transactionTimeTypeName);

        if(duplicateTransactionTimeType != null && !transactionTimeType.equals(duplicateTransactionTimeType)) {
            addExecutionError(ExecutionErrors.DuplicateTransactionTimeTypeName.name(), transactionTimeTypeName);
        }
    }

    @Override
    public void doUpdate(TransactionTimeType transactionTimeType) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var partyPK = getPartyPK();
        var transactionTimeTypeDetailValue = transactionTimeControl.getTransactionTimeTypeDetailValueForUpdate(transactionTimeType);
        var transactionTimeTypeDescription = transactionTimeControl.getTransactionTimeTypeDescriptionForUpdate(transactionTimeType, getPreferredLanguage());
        var description = edit.getDescription();

        transactionTimeTypeDetailValue.setTransactionTimeTypeName(edit.getTransactionTimeTypeName());
        transactionTimeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        transactionTimeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        transactionTimeControl.updateTransactionTimeTypeFromValue(transactionTimeTypeDetailValue, partyPK);

        if(transactionTimeTypeDescription == null && description != null) {
            transactionTimeControl.createTransactionTimeTypeDescription(transactionTimeType, getPreferredLanguage(), description, partyPK);
        } else {
            if(transactionTimeTypeDescription != null && description == null) {
                transactionTimeControl.deleteTransactionTimeTypeDescription(transactionTimeTypeDescription, partyPK);
            } else {
                if(transactionTimeTypeDescription != null && description != null) {
                    var transactionTimeTypeDescriptionValue = transactionTimeControl.getTransactionTimeTypeDescriptionValue(transactionTimeTypeDescription);

                    transactionTimeTypeDescriptionValue.setDescription(description);
                    transactionTimeControl.updateTransactionTimeTypeDescriptionFromValue(transactionTimeTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
