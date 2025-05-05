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

import com.echothree.control.user.batch.common.edit.BatchAliasTypeEdit;
import com.echothree.control.user.batch.common.edit.BatchEditFactory;
import com.echothree.control.user.batch.common.form.EditBatchAliasTypeForm;
import com.echothree.control.user.batch.common.result.BatchResultFactory;
import com.echothree.control.user.batch.common.result.EditBatchAliasTypeResult;
import com.echothree.control.user.batch.common.spec.BatchAliasTypeSpec;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.batch.server.entity.BatchAliasType;
import com.echothree.model.data.batch.server.entity.BatchType;
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

public class EditBatchAliasTypeCommand
        extends BaseAbstractEditCommand<BatchAliasTypeSpec, BatchAliasTypeEdit, EditBatchAliasTypeResult, BatchAliasType, BatchAliasType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.BatchAliasType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditBatchAliasTypeCommand */
    public EditBatchAliasTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditBatchAliasTypeResult getResult() {
        return BatchResultFactory.getEditBatchAliasTypeResult();
    }

    @Override
    public BatchAliasTypeEdit getEdit() {
        return BatchEditFactory.getBatchAliasTypeEdit();
    }

    BatchType batchType;

    @Override
    public BatchAliasType getEntity(EditBatchAliasTypeResult result) {
        var batchControl = Session.getModelController(BatchControl.class);
        BatchAliasType batchAliasType = null;
        var batchTypeName = spec.getBatchTypeName();

        batchType = batchControl.getBatchTypeByName(batchTypeName);

        if(batchType != null) {
            var batchAliasTypeName = spec.getBatchAliasTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                batchAliasType = batchControl.getBatchAliasTypeByName(batchType, batchAliasTypeName);
            } else { // EditMode.UPDATE
                batchAliasType = batchControl.getBatchAliasTypeByNameForUpdate(batchType, batchAliasTypeName);
            }

            if(batchAliasType != null) {
                result.setBatchAliasType(batchControl.getBatchAliasTypeTransfer(getUserVisit(), batchAliasType));
            } else {
                addExecutionError(ExecutionErrors.UnknownBatchAliasTypeName.name(), batchTypeName, batchAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
        }

        return batchAliasType;
    }

    @Override
    public BatchAliasType getLockEntity(BatchAliasType batchAliasType) {
        return batchAliasType;
    }

    @Override
    public void fillInResult(EditBatchAliasTypeResult result, BatchAliasType batchAliasType) {
        var batchControl = Session.getModelController(BatchControl.class);

        result.setBatchAliasType(batchControl.getBatchAliasTypeTransfer(getUserVisit(), batchAliasType));
    }

    @Override
    public void doLock(BatchAliasTypeEdit edit, BatchAliasType batchAliasType) {
        var batchControl = Session.getModelController(BatchControl.class);
        var batchAliasTypeDescription = batchControl.getBatchAliasTypeDescription(batchAliasType, getPreferredLanguage());
        var batchAliasTypeDetail = batchAliasType.getLastDetail();

        edit.setBatchAliasTypeName(batchAliasTypeDetail.getBatchAliasTypeName());
        edit.setValidationPattern(batchAliasTypeDetail.getValidationPattern());
        edit.setIsDefault(batchAliasTypeDetail.getIsDefault().toString());
        edit.setSortOrder(batchAliasTypeDetail.getSortOrder().toString());

        if(batchAliasTypeDescription != null) {
            edit.setDescription(batchAliasTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(BatchAliasType batchAliasType) {
        var batchControl = Session.getModelController(BatchControl.class);
        var batchAliasTypeName = edit.getBatchAliasTypeName();
        var duplicateBatchAliasType = batchControl.getBatchAliasTypeByName(batchType, batchAliasTypeName);

        if(duplicateBatchAliasType != null && !batchAliasType.equals(duplicateBatchAliasType)) {
            addExecutionError(ExecutionErrors.DuplicateBatchAliasTypeName.name(), spec.getBatchTypeName(), batchAliasTypeName);
        }
    }

    @Override
    public void doUpdate(BatchAliasType batchAliasType) {
        var batchControl = Session.getModelController(BatchControl.class);
        var partyPK = getPartyPK();
        var batchAliasTypeDetailValue = batchControl.getBatchAliasTypeDetailValueForUpdate(batchAliasType);
        var batchAliasTypeDescription = batchControl.getBatchAliasTypeDescriptionForUpdate(batchAliasType, getPreferredLanguage());
        var description = edit.getDescription();

        batchAliasTypeDetailValue.setBatchAliasTypeName(edit.getBatchAliasTypeName());
        batchAliasTypeDetailValue.setValidationPattern(edit.getValidationPattern());
        batchAliasTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        batchAliasTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        batchControl.updateBatchAliasTypeFromValue(batchAliasTypeDetailValue, partyPK);

        if(batchAliasTypeDescription == null && description != null) {
            batchControl.createBatchAliasTypeDescription(batchAliasType, getPreferredLanguage(), description, partyPK);
        } else if(batchAliasTypeDescription != null && description == null) {
            batchControl.deleteBatchAliasTypeDescription(batchAliasTypeDescription, partyPK);
        } else if(batchAliasTypeDescription != null && description != null) {
            var batchAliasTypeDescriptionValue = batchControl.getBatchAliasTypeDescriptionValue(batchAliasTypeDescription);

            batchAliasTypeDescriptionValue.setDescription(description);
            batchControl.updateBatchAliasTypeDescriptionFromValue(batchAliasTypeDescriptionValue, partyPK);
        }
    }

}
