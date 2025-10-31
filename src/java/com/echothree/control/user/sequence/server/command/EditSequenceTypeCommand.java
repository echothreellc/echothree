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

import com.echothree.control.user.sequence.common.edit.SequenceEditFactory;
import com.echothree.control.user.sequence.common.edit.SequenceTypeEdit;
import com.echothree.control.user.sequence.common.form.EditSequenceTypeForm;
import com.echothree.control.user.sequence.common.result.EditSequenceTypeResult;
import com.echothree.control.user.sequence.common.result.SequenceResultFactory;
import com.echothree.control.user.sequence.common.spec.SequenceTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.SequenceChecksumType;
import com.echothree.model.data.sequence.server.entity.SequenceEncoderType;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditSequenceTypeCommand
        extends BaseAbstractEditCommand<SequenceTypeSpec, SequenceTypeEdit, EditSequenceTypeResult, SequenceType, SequenceType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SequenceType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SequenceTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SequenceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Prefix", FieldType.STRING, false, 1L, 10L),
                new FieldDefinition("Suffix", FieldType.STRING, false, 1L, 10L),
                new FieldDefinition("SequenceEncoderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SequenceChecksumTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChunkSize", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSequenceTypeCommand */
    public EditSequenceTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditSequenceTypeResult getResult() {
        return SequenceResultFactory.getEditSequenceTypeResult();
    }
    
    @Override
    public SequenceTypeEdit getEdit() {
        return SequenceEditFactory.getSequenceTypeEdit();
    }
    
    @Override
    public SequenceType getEntity(EditSequenceTypeResult result) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        SequenceType sequenceType;
        var sequenceTypeName = spec.getSequenceTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            sequenceType = sequenceControl.getSequenceTypeByName(sequenceTypeName);
        } else { // EditMode.UPDATE
            sequenceType = sequenceControl.getSequenceTypeByNameForUpdate(sequenceTypeName);
        }

        if(sequenceType != null) {
            result.setSequenceType(sequenceControl.getSequenceTypeTransfer(getUserVisit(), sequenceType));
        } else {
            addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), sequenceTypeName);
        }

        return sequenceType;
    }
    
    @Override
    public SequenceType getLockEntity(SequenceType sequenceType) {
        return sequenceType;
    }
    
    @Override
    public void fillInResult(EditSequenceTypeResult result, SequenceType sequenceType) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        
        result.setSequenceType(sequenceControl.getSequenceTypeTransfer(getUserVisit(), sequenceType));
    }
    
    SequenceType parentSequenceType = null;
    
    @Override
    public void doLock(SequenceTypeEdit edit, SequenceType sequenceType) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceTypeDescription = sequenceControl.getSequenceTypeDescription(sequenceType, getPreferredLanguage());
        var sequenceTypeDetail = sequenceType.getLastDetail();
        var chunkSize = sequenceTypeDetail.getChunkSize();
        
        edit.setSequenceTypeName(sequenceTypeDetail.getSequenceTypeName());
        edit.setPrefix(sequenceTypeDetail.getPrefix());
        edit.setSuffix(sequenceTypeDetail.getSuffix());
        edit.setSequenceEncoderTypeName(sequenceTypeDetail.getSequenceEncoderType().getSequenceEncoderTypeName());
        edit.setSequenceChecksumTypeName(sequenceTypeDetail.getSequenceChecksumType().getSequenceChecksumTypeName());
        edit.setChunkSize(chunkSize == null? null: chunkSize.toString());
        edit.setIsDefault(sequenceTypeDetail.getIsDefault().toString());
        edit.setSortOrder(sequenceTypeDetail.getSortOrder().toString());

        if(sequenceTypeDescription != null) {
            edit.setDescription(sequenceTypeDescription.getDescription());
        }
    }
        
    SequenceEncoderType sequenceEncoderType;
    SequenceChecksumType sequenceChecksumType;
    
    @Override
    public void canUpdate(SequenceType sequenceType) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceTypeName = edit.getSequenceTypeName();
        var duplicateSequenceType = sequenceControl.getSequenceTypeByName(sequenceTypeName);

        if(duplicateSequenceType != null && !sequenceType.equals(duplicateSequenceType)) {
            addExecutionError(ExecutionErrors.DuplicateSequenceTypeName.name(), sequenceTypeName);
        } else {
            var prefix = edit.getPrefix();
            
            duplicateSequenceType = prefix == null ? null : sequenceControl.getSequenceTypeByPrefix(prefix);
            
            if(duplicateSequenceType != null && !sequenceType.equals(duplicateSequenceType)) {
                addExecutionError(ExecutionErrors.DuplicatePrefix.name(), prefix);
            } else {
                var suffix = edit.getSuffix();

                duplicateSequenceType = suffix == null ? null : sequenceControl.getSequenceTypeBySuffix(suffix);

                if(duplicateSequenceType != null && !sequenceType.equals(duplicateSequenceType)) {
                    addExecutionError(ExecutionErrors.DuplicateSuffix.name(), suffix);
                } else {
                    var sequenceEncoderTypeName = edit.getSequenceEncoderTypeName();

                    sequenceEncoderType = sequenceControl.getSequenceEncoderTypeByName(sequenceEncoderTypeName);

                    if(sequenceEncoderType == null) {
                        addExecutionError(ExecutionErrors.UnknownSequenceEncoderTypeName.name(), sequenceEncoderTypeName);
                    } else {
                        var sequenceChecksumTypeName = edit.getSequenceChecksumTypeName();

                        sequenceChecksumType = sequenceControl.getSequenceChecksumTypeByName(sequenceChecksumTypeName);

                        if(sequenceChecksumType == null) {
                            addExecutionError(ExecutionErrors.UnknownSequenceChecksumTypeName.name(), sequenceChecksumTypeName);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void doUpdate(SequenceType sequenceType) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var partyPK = getPartyPK();
        var sequenceTypeDetailValue = sequenceControl.getSequenceTypeDetailValueForUpdate(sequenceType);
        var sequenceTypeDescription = sequenceControl.getSequenceTypeDescriptionForUpdate(sequenceType, getPreferredLanguage());
        var rawChunkSize = edit.getChunkSize();
        var chunkSize = rawChunkSize == null? null: Integer.valueOf(rawChunkSize);
        var description = edit.getDescription();

        sequenceTypeDetailValue.setSequenceTypeName(edit.getSequenceTypeName());
        sequenceTypeDetailValue.setPrefix(edit.getPrefix());
        sequenceTypeDetailValue.setSuffix(edit.getSuffix());
        sequenceTypeDetailValue.setSequenceEncoderTypePK(sequenceEncoderType.getPrimaryKey());
        sequenceTypeDetailValue.setSequenceChecksumTypePK(sequenceChecksumType.getPrimaryKey());
        sequenceTypeDetailValue.setChunkSize(chunkSize);
        sequenceTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        sequenceTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        sequenceControl.updateSequenceTypeFromValue(sequenceTypeDetailValue, partyPK);

        if(sequenceTypeDescription == null && description != null) {
            sequenceControl.createSequenceTypeDescription(sequenceType, getPreferredLanguage(), description, partyPK);
        } else if(sequenceTypeDescription != null && description == null) {
            sequenceControl.deleteSequenceTypeDescription(sequenceTypeDescription, partyPK);
        } else if(sequenceTypeDescription != null && description != null) {
            var sequenceTypeDescriptionValue = sequenceControl.getSequenceTypeDescriptionValue(sequenceTypeDescription);

            sequenceTypeDescriptionValue.setDescription(description);
            sequenceControl.updateSequenceTypeDescriptionFromValue(sequenceTypeDescriptionValue, partyPK);
        }
    }
    
}
