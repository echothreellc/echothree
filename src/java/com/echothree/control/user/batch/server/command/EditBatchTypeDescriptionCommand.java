// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.control.user.batch.server.command;

import com.echothree.control.user.batch.remote.edit.BatchEditFactory;
import com.echothree.control.user.batch.remote.edit.BatchTypeDescriptionEdit;
import com.echothree.control.user.batch.remote.form.EditBatchTypeDescriptionForm;
import com.echothree.control.user.batch.remote.result.BatchResultFactory;
import com.echothree.control.user.batch.remote.result.EditBatchTypeDescriptionResult;
import com.echothree.control.user.batch.remote.spec.BatchTypeDescriptionSpec;
import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.batch.server.entity.BatchTypeDescription;
import com.echothree.model.data.batch.server.value.BatchTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditBatchTypeDescriptionCommand
        extends BaseAbstractEditCommand<BatchTypeDescriptionSpec, BatchTypeDescriptionEdit, EditBatchTypeDescriptionResult, BatchTypeDescription, BatchType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.BatchType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditBatchTypeDescriptionCommand */
    public EditBatchTypeDescriptionCommand(UserVisitPK userVisitPK, EditBatchTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditBatchTypeDescriptionResult getResult() {
        return BatchResultFactory.getEditBatchTypeDescriptionResult();
    }

    @Override
    public BatchTypeDescriptionEdit getEdit() {
        return BatchEditFactory.getBatchTypeDescriptionEdit();
    }

    @Override
    public BatchTypeDescription getEntity(EditBatchTypeDescriptionResult result) {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        BatchTypeDescription batchTypeDescription = null;
        String batchTypeName = spec.getBatchTypeName();
        BatchType batchType = batchControl.getBatchTypeByName(batchTypeName);

        if(batchType != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    batchTypeDescription = batchControl.getBatchTypeDescription(batchType, language);
                } else { // EditMode.UPDATE
                    batchTypeDescription = batchControl.getBatchTypeDescriptionForUpdate(batchType, language);
                }

                if(batchTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownBatchTypeDescription.name(), batchTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
        }

        return batchTypeDescription;
    }

    @Override
    public BatchType getLockEntity(BatchTypeDescription batchTypeDescription) {
        return batchTypeDescription.getBatchType();
    }

    @Override
    public void fillInResult(EditBatchTypeDescriptionResult result, BatchTypeDescription batchTypeDescription) {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);

        result.setBatchTypeDescription(batchControl.getBatchTypeDescriptionTransfer(getUserVisit(), batchTypeDescription));
    }

    @Override
    public void doLock(BatchTypeDescriptionEdit edit, BatchTypeDescription batchTypeDescription) {
        edit.setDescription(batchTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(BatchTypeDescription batchTypeDescription) {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        BatchTypeDescriptionValue batchTypeDescriptionValue = batchControl.getBatchTypeDescriptionValue(batchTypeDescription);
        batchTypeDescriptionValue.setDescription(edit.getDescription());

        batchControl.updateBatchTypeDescriptionFromValue(batchTypeDescriptionValue, getPartyPK());
    }
    
}
