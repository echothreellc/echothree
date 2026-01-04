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

package com.echothree.control.user.sequence.server.command;

import com.echothree.control.user.sequence.common.edit.SequenceEdit;
import com.echothree.control.user.sequence.common.edit.SequenceEditFactory;
import com.echothree.control.user.sequence.common.form.EditSequenceForm;
import com.echothree.control.user.sequence.common.result.EditSequenceResult;
import com.echothree.control.user.sequence.common.result.SequenceResultFactory;
import com.echothree.control.user.sequence.common.spec.SequenceSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditSequenceCommand
        extends BaseAbstractEditCommand<SequenceSpec, SequenceEdit, EditSequenceResult, Sequence, Sequence> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Sequence.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SequenceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SequenceName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SequenceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Mask", FieldType.SEQUENCE_MASK, true, null, null),
                new FieldDefinition("ChunkSize", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSequenceCommand */
    public EditSequenceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditSequenceResult getResult() {
        return SequenceResultFactory.getEditSequenceResult();
    }
    
    @Override
    public SequenceEdit getEdit() {
        return SequenceEditFactory.getSequenceEdit();
    }
    
    SequenceType sequenceType = null;
    
    @Override
    public Sequence getEntity(EditSequenceResult result) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        Sequence sequence = null;
        var sequenceTypeName = spec.getSequenceTypeName();
        
        sequenceType = sequenceControl.getSequenceTypeByName(sequenceTypeName);
        
        if(sequenceType != null) {
            var sequenceName = spec.getSequenceName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                sequence = sequenceControl.getSequenceByName(sequenceType, sequenceName);
            } else { // EditMode.UPDATE
                sequence = sequenceControl.getSequenceByNameForUpdate(sequenceType, sequenceName);
            }

            if(sequence != null) {
                result.setSequence(sequenceControl.getSequenceTransfer(getUserVisit(), sequence));
            } else {
                addExecutionError(ExecutionErrors.UnknownSequenceName.name(), sequenceTypeName, sequenceTypeName, sequenceName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), sequenceTypeName);
        }

        return sequence;
    }
    
    @Override
    public Sequence getLockEntity(Sequence sequence) {
        return sequence;
    }
    
    @Override
    public void fillInResult(EditSequenceResult result, Sequence sequence) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        
        result.setSequence(sequenceControl.getSequenceTransfer(getUserVisit(), sequence));
    }
    
    @Override
    public void doLock(SequenceEdit edit, Sequence sequence) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceDescription = sequenceControl.getSequenceDescription(sequence, getPreferredLanguage());
        var sequenceDetail = sequence.getLastDetail();
        var chunkSize = sequenceDetail.getChunkSize();

        edit.setSequenceName(sequenceDetail.getSequenceName());
        edit.setMask(sequenceDetail.getMask());
        edit.setChunkSize(chunkSize == null? null: chunkSize.toString());
        edit.setIsDefault(sequenceDetail.getIsDefault().toString());
        edit.setSortOrder(sequenceDetail.getSortOrder().toString());

        if(sequenceDescription != null) {
            edit.setDescription(sequenceDescription.getDescription());
        }
    }
    
    @Override
    public void canUpdate(Sequence sequence) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceName = edit.getSequenceName();
        var duplicateSequence = sequenceControl.getSequenceByName(sequenceType, sequenceName);

        if(duplicateSequence != null && !sequence.equals(duplicateSequence)) {
            addExecutionError(ExecutionErrors.DuplicateSequenceName.name(), sequenceType.getLastDetail().getSequenceTypeName(), sequenceName);
        }
    }
    
    @Override
    public void doUpdate(Sequence sequence) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var partyPK = getPartyPK();
        var sequenceDetailValue = sequenceControl.getSequenceDetailValueForUpdate(sequence);
        var sequenceDescription = sequenceControl.getSequenceDescriptionForUpdate(sequence, getPreferredLanguage());
        var rawChunkSize = edit.getChunkSize();
        var chunkSize = rawChunkSize == null? null: Integer.valueOf(rawChunkSize);
        var description = edit.getDescription();

        sequenceDetailValue.setSequenceName(edit.getSequenceName());
        sequenceDetailValue.setMask(edit.getMask());
        sequenceDetailValue.setChunkSize(chunkSize);
        sequenceDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        sequenceDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        sequenceControl.updateSequenceFromValue(sequenceDetailValue, partyPK);

        if(sequenceDescription == null && description != null) {
            sequenceControl.createSequenceDescription(sequence, getPreferredLanguage(), description, partyPK);
        } else if(sequenceDescription != null && description == null) {
            sequenceControl.deleteSequenceDescription(sequenceDescription, partyPK);
        } else if(sequenceDescription != null && description != null) {
            var sequenceDescriptionValue = sequenceControl.getSequenceDescriptionValue(sequenceDescription);

            sequenceDescriptionValue.setDescription(description);
            sequenceControl.updateSequenceDescriptionFromValue(sequenceDescriptionValue, partyPK);
        }
    }
    
}
