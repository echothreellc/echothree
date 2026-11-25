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

package com.echothree.control.user.communication.server.server;

import com.echothree.control.user.communication.common.edit.CommunicationEditFactory;
import com.echothree.control.user.communication.common.edit.CommunicationEventPurposeEdit;
import com.echothree.control.user.communication.common.result.CommunicationResultFactory;
import com.echothree.control.user.communication.common.result.EditCommunicationEventPurposeResult;
import com.echothree.control.user.communication.common.spec.CommunicationEventPurposeUniversalSpec;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.communication.server.logic.CommunicationEventPurposeLogic;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurpose;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditCommunicationEventPurposeCommand
        extends BaseAbstractEditCommand<CommunicationEventPurposeUniversalSpec, CommunicationEventPurposeEdit, EditCommunicationEventPurposeResult, CommunicationEventPurpose, CommunicationEventPurpose> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CommunicationEventPurposeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CommunicationEventPurposeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    CommunicationControl communicationControl;

    @Inject
    CommunicationEventPurposeLogic communicationEventPurposeLogic;

    /** Creates a new instance of EditCommunicationEventPurposeCommand */
    public EditCommunicationEventPurposeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCommunicationEventPurposeResult getResult() {
        return CommunicationResultFactory.getEditCommunicationEventPurposeResult();
    }

    @Override
    public CommunicationEventPurposeEdit getEdit() {
        return CommunicationEditFactory.getCommunicationEventPurposeEdit();
    }

    @Override
    public CommunicationEventPurpose getEntity(EditCommunicationEventPurposeResult result) {
        return communicationEventPurposeLogic.getCommunicationEventPurposeByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public CommunicationEventPurpose getLockEntity(CommunicationEventPurpose communicationEventPurpose) {
        return communicationEventPurpose;
    }

    @Override
    public void fillInResult(EditCommunicationEventPurposeResult result, CommunicationEventPurpose communicationEventPurpose) {
        result.setCommunicationEventPurpose(communicationControl.getCommunicationEventPurposeTransfer(getUserVisit(), communicationEventPurpose));
    }

    @Override
    public void doLock(CommunicationEventPurposeEdit edit, CommunicationEventPurpose communicationEventPurpose) {
        var communicationEventPurposeDescription = communicationControl.getCommunicationEventPurposeDescription(communicationEventPurpose, getPreferredLanguage());
        var communicationEventPurposeDetail = communicationEventPurpose.getLastDetail();

        edit.setCommunicationEventPurposeName(communicationEventPurposeDetail.getCommunicationEventPurposeName());
        edit.setIsDefault(communicationEventPurposeDetail.getIsDefault().toString());
        edit.setSortOrder(communicationEventPurposeDetail.getSortOrder().toString());

        if(communicationEventPurposeDescription != null) {
            edit.setDescription(communicationEventPurposeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(CommunicationEventPurpose communicationEventPurpose) {
        var communicationEventPurposeName = edit.getCommunicationEventPurposeName();
        var duplicateCommunicationEventPurpose = communicationControl.getCommunicationEventPurposeByName(communicationEventPurposeName);

        if(duplicateCommunicationEventPurpose != null && !communicationEventPurpose.equals(duplicateCommunicationEventPurpose)) {
            addExecutionError(ExecutionErrors.DuplicateCommunicationEventPurposeName.name(), communicationEventPurposeName);
        }
    }

    @Override
    public void doUpdate(CommunicationEventPurpose communicationEventPurpose) {
        var partyPK = getPartyPK();
        var communicationEventPurposeDetailValue = communicationControl.getCommunicationEventPurposeDetailValueForUpdate(communicationEventPurpose);
        var communicationEventPurposeDescription = communicationControl.getCommunicationEventPurposeDescriptionForUpdate(communicationEventPurpose, getPreferredLanguage());
        var description = edit.getDescription();

        communicationEventPurposeDetailValue.setCommunicationEventPurposeName(edit.getCommunicationEventPurposeName());
        communicationEventPurposeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        communicationEventPurposeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        communicationControl.updateCommunicationEventPurposeFromValue(communicationEventPurposeDetailValue, partyPK);

        if(communicationEventPurposeDescription == null && description != null) {
            communicationControl.createCommunicationEventPurposeDescription(communicationEventPurpose, getPreferredLanguage(), description, partyPK);
        } else if(communicationEventPurposeDescription != null && description == null) {
            communicationControl.deleteCommunicationEventPurposeDescription(communicationEventPurposeDescription, partyPK);
        } else if(communicationEventPurposeDescription != null && description != null) {
            var communicationEventPurposeDescriptionValue = communicationControl.getCommunicationEventPurposeDescriptionValue(communicationEventPurposeDescription);

            communicationEventPurposeDescriptionValue.setDescription(description);

            communicationControl.updateCommunicationEventPurposeDescriptionFromValue(communicationEventPurposeDescriptionValue, partyPK);
        }
    }

}
