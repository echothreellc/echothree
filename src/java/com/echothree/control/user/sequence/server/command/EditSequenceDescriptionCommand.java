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

package com.echothree.control.user.sequence.server.command;

import com.echothree.control.user.sequence.common.edit.SequenceDescriptionEdit;
import com.echothree.control.user.sequence.common.edit.SequenceEditFactory;
import com.echothree.control.user.sequence.common.form.EditSequenceDescriptionForm;
import com.echothree.control.user.sequence.common.result.EditSequenceDescriptionResult;
import com.echothree.control.user.sequence.common.result.SequenceResultFactory;
import com.echothree.control.user.sequence.common.spec.SequenceDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceDescription;
import com.echothree.model.data.sequence.server.entity.SequenceType;
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

public class EditSequenceDescriptionCommand
        extends BaseAbstractEditCommand<SequenceDescriptionSpec, SequenceDescriptionEdit, EditSequenceDescriptionResult, SequenceDescription, Sequence> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Sequence.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SequenceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SequenceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditSequenceDescriptionCommand */
    public EditSequenceDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSequenceDescriptionResult getResult() {
        return SequenceResultFactory.getEditSequenceDescriptionResult();
    }

    @Override
    public SequenceDescriptionEdit getEdit() {
        return SequenceEditFactory.getSequenceDescriptionEdit();
    }

    SequenceType sequenceType;
    
    @Override
    public SequenceDescription getEntity(EditSequenceDescriptionResult result) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        SequenceDescription sequenceDescription = null;
        var sequenceTypeName = spec.getSequenceTypeName();
        
        sequenceType = sequenceControl.getSequenceTypeByName(sequenceTypeName);

        if(sequenceType != null) {
            var sequenceName = spec.getSequenceName();
            var sequence = sequenceControl.getSequenceByName(sequenceType, sequenceName);

            if(sequence != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        sequenceDescription = sequenceControl.getSequenceDescription(sequence, language);
                    } else { // EditMode.UPDATE
                        sequenceDescription = sequenceControl.getSequenceDescriptionForUpdate(sequence, language);
                    }

                    if(sequenceDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownSequenceDescription.name(), sequenceTypeName, sequenceName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSequenceName.name(), sequenceTypeName, sequenceName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), sequenceTypeName);
        }

        return sequenceDescription;
    }

    @Override
    public Sequence getLockEntity(SequenceDescription sequenceDescription) {
        return sequenceDescription.getSequence();
    }

    @Override
    public void fillInResult(EditSequenceDescriptionResult result, SequenceDescription sequenceDescription) {
        var sequenceControl = Session.getModelController(SequenceControl.class);

        result.setSequenceDescription(sequenceControl.getSequenceDescriptionTransfer(getUserVisit(), sequenceDescription));
    }

    @Override
    public void doLock(SequenceDescriptionEdit edit, SequenceDescription sequenceDescription) {
        edit.setDescription(sequenceDescription.getDescription());
    }

    @Override
    public void doUpdate(SequenceDescription sequenceDescription) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceDescriptionValue = sequenceControl.getSequenceDescriptionValue(sequenceDescription);
        
        sequenceDescriptionValue.setDescription(edit.getDescription());
        
        sequenceControl.updateSequenceDescriptionFromValue(sequenceDescriptionValue, getPartyPK());
    }

}
