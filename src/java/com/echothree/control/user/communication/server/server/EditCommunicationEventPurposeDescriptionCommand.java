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
import com.echothree.control.user.communication.common.edit.CommunicationEventPurposeDescriptionEdit;
import com.echothree.control.user.communication.common.result.CommunicationResultFactory;
import com.echothree.control.user.communication.common.result.EditCommunicationEventPurposeDescriptionResult;
import com.echothree.control.user.communication.common.spec.CommunicationEventPurposeDescriptionSpec;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.communication.server.logic.CommunicationEventPurposeLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurpose;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurposeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditCommunicationEventPurposeDescriptionCommand
        extends BaseAbstractEditCommand<CommunicationEventPurposeDescriptionSpec, CommunicationEventPurposeDescriptionEdit, EditCommunicationEventPurposeDescriptionResult, CommunicationEventPurposeDescription, CommunicationEventPurpose> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CommunicationEventPurposeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    CommunicationControl communicationControl;

    @Inject
    CommunicationEventPurposeLogic communicationEventPurposeLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditCommunicationEventPurposeDescriptionCommand */
    public EditCommunicationEventPurposeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCommunicationEventPurposeDescriptionResult getResult() {
        return CommunicationResultFactory.getEditCommunicationEventPurposeDescriptionResult();
    }

    @Override
    public CommunicationEventPurposeDescriptionEdit getEdit() {
        return CommunicationEditFactory.getCommunicationEventPurposeDescriptionEdit();
    }

    @Override
    public CommunicationEventPurposeDescription getEntity(EditCommunicationEventPurposeDescriptionResult result) {
        CommunicationEventPurposeDescription communicationEventPurposeDescription = null;
        var communicationEventPurposeName = spec.getCommunicationEventPurposeName();
        var languageIsoName = spec.getLanguageIsoName();
        var communicationEventPurpose = communicationEventPurposeLogic.getCommunicationEventPurposeByName(this, communicationEventPurposeName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            communicationEventPurposeDescription = communicationControl.getCommunicationEventPurposeDescription(communicationEventPurpose, language,
                    editModeToEntityPermission(editMode));

            if(communicationEventPurposeDescription == null) {
                addExecutionError(ExecutionErrors.UnknownCommunicationEventPurposeDescription.name(), communicationEventPurposeName, languageIsoName);
            }
        }

        return communicationEventPurposeDescription;
    }

    @Override
    public CommunicationEventPurpose getLockEntity(CommunicationEventPurposeDescription communicationEventPurposeDescription) {
        return communicationEventPurposeDescription.getCommunicationEventPurpose();
    }

    @Override
    public void fillInResult(EditCommunicationEventPurposeDescriptionResult result, CommunicationEventPurposeDescription communicationEventPurposeDescription) {
        result.setCommunicationEventPurposeDescription(communicationControl.getCommunicationEventPurposeDescriptionTransfer(getUserVisit(), communicationEventPurposeDescription));
    }

    @Override
    public void doLock(CommunicationEventPurposeDescriptionEdit edit, CommunicationEventPurposeDescription communicationEventPurposeDescription) {
        edit.setDescription(communicationEventPurposeDescription.getDescription());
    }

    @Override
    public void doUpdate(CommunicationEventPurposeDescription communicationEventPurposeDescription) {
        var communicationEventPurposeDescriptionValue = communicationControl.getCommunicationEventPurposeDescriptionValue(communicationEventPurposeDescription);

        communicationEventPurposeDescriptionValue.setDescription(edit.getDescription());

        communicationControl.updateCommunicationEventPurposeDescriptionFromValue(communicationEventPurposeDescriptionValue, getPartyPK());
    }

}
