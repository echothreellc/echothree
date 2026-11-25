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
import com.echothree.control.user.communication.common.edit.CommunicationSourceDescriptionEdit;
import com.echothree.control.user.communication.common.result.CommunicationResultFactory;
import com.echothree.control.user.communication.common.result.EditCommunicationSourceDescriptionResult;
import com.echothree.control.user.communication.common.spec.CommunicationSourceDescriptionSpec;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.communication.server.logic.CommunicationSourceLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.communication.server.entity.CommunicationSource;
import com.echothree.model.data.communication.server.entity.CommunicationSourceDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditCommunicationSourceDescriptionCommand
        extends BaseAbstractEditCommand<CommunicationSourceDescriptionSpec, CommunicationSourceDescriptionEdit, EditCommunicationSourceDescriptionResult, CommunicationSourceDescription, CommunicationSource> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CommunicationSourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    CommunicationControl communicationControl;

    @Inject
    CommunicationSourceLogic communicationSourceLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditCommunicationSourceDescriptionCommand */
    public EditCommunicationSourceDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCommunicationSourceDescriptionResult getResult() {
        return CommunicationResultFactory.getEditCommunicationSourceDescriptionResult();
    }

    @Override
    public CommunicationSourceDescriptionEdit getEdit() {
        return CommunicationEditFactory.getCommunicationSourceDescriptionEdit();
    }

    @Override
    public CommunicationSourceDescription getEntity(EditCommunicationSourceDescriptionResult result) {
        CommunicationSourceDescription communicationSourceDescription = null;
        var communicationSourceName = spec.getCommunicationSourceName();
        var languageIsoName = spec.getLanguageIsoName();
        var communicationSource = communicationSourceLogic.getCommunicationSourceByName(this, communicationSourceName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            communicationSourceDescription = communicationControl.getCommunicationSourceDescription(communicationSource, language,
                    editModeToEntityPermission(editMode));

            if(communicationSourceDescription == null) {
                addExecutionError(ExecutionErrors.UnknownCommunicationSourceDescription.name(), communicationSourceName, languageIsoName);
            }
        }

        return communicationSourceDescription;
    }

    @Override
    public CommunicationSource getLockEntity(CommunicationSourceDescription communicationSourceDescription) {
        return communicationSourceDescription.getCommunicationSource();
    }

    @Override
    public void fillInResult(EditCommunicationSourceDescriptionResult result, CommunicationSourceDescription communicationSourceDescription) {
        result.setCommunicationSourceDescription(communicationControl.getCommunicationSourceDescriptionTransfer(getUserVisit(), communicationSourceDescription));
    }

    @Override
    public void doLock(CommunicationSourceDescriptionEdit edit, CommunicationSourceDescription communicationSourceDescription) {
        edit.setDescription(communicationSourceDescription.getDescription());
    }

    @Override
    public void doUpdate(CommunicationSourceDescription communicationSourceDescription) {
        var communicationSourceDescriptionValue = communicationControl.getCommunicationSourceDescriptionValue(communicationSourceDescription);

        communicationSourceDescriptionValue.setDescription(edit.getDescription());

        communicationControl.updateCommunicationSourceDescriptionFromValue(communicationSourceDescriptionValue, getPartyPK());
    }

}
