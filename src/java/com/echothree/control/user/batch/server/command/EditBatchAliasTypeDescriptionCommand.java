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

package com.echothree.control.user.batch.server.command;

import com.echothree.control.user.batch.common.edit.BatchAliasTypeDescriptionEdit;
import com.echothree.control.user.batch.common.edit.BatchEditFactory;
import com.echothree.control.user.batch.common.form.EditBatchAliasTypeDescriptionForm;
import com.echothree.control.user.batch.common.result.BatchResultFactory;
import com.echothree.control.user.batch.common.result.EditBatchAliasTypeDescriptionResult;
import com.echothree.control.user.batch.common.spec.BatchAliasTypeDescriptionSpec;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.batch.server.entity.BatchAliasType;
import com.echothree.model.data.batch.server.entity.BatchAliasTypeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
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
public class EditBatchAliasTypeDescriptionCommand
        extends BaseAbstractEditCommand<BatchAliasTypeDescriptionSpec, BatchAliasTypeDescriptionEdit, EditBatchAliasTypeDescriptionResult, BatchAliasTypeDescription, BatchAliasType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.BatchAliasType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditBatchAliasTypeDescriptionCommand */
    public EditBatchAliasTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditBatchAliasTypeDescriptionResult getResult() {
        return BatchResultFactory.getEditBatchAliasTypeDescriptionResult();
    }

    @Override
    public BatchAliasTypeDescriptionEdit getEdit() {
        return BatchEditFactory.getBatchAliasTypeDescriptionEdit();
    }

    @Override
    public BatchAliasTypeDescription getEntity(EditBatchAliasTypeDescriptionResult result) {
        var batchControl = Session.getModelController(BatchControl.class);
        BatchAliasTypeDescription batchAliasTypeDescription = null;
        var batchTypeName = spec.getBatchTypeName();
        var batchType = batchControl.getBatchTypeByName(batchTypeName);

        if(batchType != null) {
            var batchAliasTypeName = spec.getBatchAliasTypeName();
            var batchAliasType = batchControl.getBatchAliasTypeByName(batchType, batchAliasTypeName);

            if(batchAliasType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        batchAliasTypeDescription = batchControl.getBatchAliasTypeDescription(batchAliasType, language);
                    } else { // EditMode.UPDATE
                        batchAliasTypeDescription = batchControl.getBatchAliasTypeDescriptionForUpdate(batchAliasType, language);
                    }

                    if(batchAliasTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownBatchAliasTypeDescription.name(), batchTypeName, batchAliasTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownBatchAliasTypeName.name(), batchTypeName, batchAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
        }

        return batchAliasTypeDescription;
    }

    @Override
    public BatchAliasType getLockEntity(BatchAliasTypeDescription batchAliasTypeDescription) {
        return batchAliasTypeDescription.getBatchAliasType();
    }

    @Override
    public void fillInResult(EditBatchAliasTypeDescriptionResult result, BatchAliasTypeDescription batchAliasTypeDescription) {
        var batchControl = Session.getModelController(BatchControl.class);

        result.setBatchAliasTypeDescription(batchControl.getBatchAliasTypeDescriptionTransfer(getUserVisit(), batchAliasTypeDescription));
    }

    @Override
    public void doLock(BatchAliasTypeDescriptionEdit edit, BatchAliasTypeDescription batchAliasTypeDescription) {
        edit.setDescription(batchAliasTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(BatchAliasTypeDescription batchAliasTypeDescription) {
        var batchControl = Session.getModelController(BatchControl.class);
        var batchAliasTypeDescriptionValue = batchControl.getBatchAliasTypeDescriptionValue(batchAliasTypeDescription);

        batchAliasTypeDescriptionValue.setDescription(edit.getDescription());

        batchControl.updateBatchAliasTypeDescriptionFromValue(batchAliasTypeDescriptionValue, getPartyPK());
    }


}
