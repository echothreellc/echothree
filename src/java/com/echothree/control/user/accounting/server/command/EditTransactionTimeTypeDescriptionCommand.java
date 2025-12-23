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
import com.echothree.control.user.accounting.common.edit.TransactionTimeTypeDescriptionEdit;
import com.echothree.control.user.accounting.common.form.EditTransactionTimeTypeDescriptionForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditTransactionTimeTypeDescriptionResult;
import com.echothree.control.user.accounting.common.spec.TransactionTimeTypeDescriptionSpec;
import com.echothree.model.control.accounting.server.control.TransactionTimeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.TransactionTimeType;
import com.echothree.model.data.accounting.server.entity.TransactionTimeTypeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditTransactionTimeTypeDescriptionCommand
        extends BaseAbstractEditCommand<TransactionTimeTypeDescriptionSpec, TransactionTimeTypeDescriptionEdit, EditTransactionTimeTypeDescriptionResult, TransactionTimeTypeDescription, TransactionTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionTimeType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditTransactionTimeTypeDescriptionCommand */
    public EditTransactionTimeTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTransactionTimeTypeDescriptionResult getResult() {
        return AccountingResultFactory.getEditTransactionTimeTypeDescriptionResult();
    }

    @Override
    public TransactionTimeTypeDescriptionEdit getEdit() {
        return AccountingEditFactory.getTransactionTimeTypeDescriptionEdit();
    }

    @Override
    public TransactionTimeTypeDescription getEntity(EditTransactionTimeTypeDescriptionResult result) {
        TransactionTimeTypeDescription transactionTimeTypeDescription = null;
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeTypeName = spec.getTransactionTimeTypeName();
        var transactionTimeType = transactionTimeControl.getTransactionTimeTypeByName(transactionTimeTypeName);

        if(transactionTimeType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    transactionTimeTypeDescription = transactionTimeControl.getTransactionTimeTypeDescription(transactionTimeType, language);
                } else { // EditMode.UPDATE
                    transactionTimeTypeDescription = transactionTimeControl.getTransactionTimeTypeDescriptionForUpdate(transactionTimeType, language);
                }

                if(transactionTimeTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownTransactionTimeTypeDescription.name(), transactionTimeTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTransactionTimeTypeName.name(), transactionTimeTypeName);
        }

        return transactionTimeTypeDescription;
    }

    @Override
    public TransactionTimeType getLockEntity(TransactionTimeTypeDescription transactionTimeTypeDescription) {
        return transactionTimeTypeDescription.getTransactionTimeType();
    }

    @Override
    public void fillInResult(EditTransactionTimeTypeDescriptionResult result, TransactionTimeTypeDescription transactionTimeTypeDescription) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);

        result.setTransactionTimeTypeDescription(transactionTimeControl.getTransactionTimeTypeDescriptionTransfer(getUserVisit(), transactionTimeTypeDescription));
    }

    @Override
    public void doLock(TransactionTimeTypeDescriptionEdit edit, TransactionTimeTypeDescription transactionTimeTypeDescription) {
        edit.setDescription(transactionTimeTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(TransactionTimeTypeDescription transactionTimeTypeDescription) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var transactionTimeTypeDescriptionValue = transactionTimeControl.getTransactionTimeTypeDescriptionValue(transactionTimeTypeDescription);
        
        transactionTimeTypeDescriptionValue.setDescription(edit.getDescription());

        transactionTimeControl.updateTransactionTimeTypeDescriptionFromValue(transactionTimeTypeDescriptionValue, getPartyPK());
    }
    
}
