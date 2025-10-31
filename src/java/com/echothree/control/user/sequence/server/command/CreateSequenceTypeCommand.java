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

import com.echothree.control.user.sequence.common.form.CreateSequenceTypeForm;
import com.echothree.control.user.sequence.common.result.SequenceResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateSequenceTypeCommand
        extends BaseSimpleCommand<CreateSequenceTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.SequenceType.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
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
    
    /** Creates a new instance of CreateSequenceTypeCommand */
    public CreateSequenceTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SequenceResultFactory.getCreateSequenceTypeResult();
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceTypeName = form.getSequenceTypeName();
        var sequenceType = sequenceControl.getSequenceTypeByName(sequenceTypeName);
        
        if(sequenceType == null) {
            var prefix = form.getPrefix();
            
            sequenceType = prefix == null ? null : sequenceControl.getSequenceTypeByPrefix(prefix);

            if(sequenceType == null) {
                var suffix = form.getSuffix();
                
                sequenceType = suffix == null ? null : sequenceControl.getSequenceTypeBySuffix(suffix);

                if(sequenceType == null) {
                    var sequenceEncoderTypeName = form.getSequenceEncoderTypeName();
                    var sequenceEncoderType = sequenceControl.getSequenceEncoderTypeByName(sequenceEncoderTypeName);

                    if(sequenceEncoderType != null) {
                        var sequenceChecksumTypeName = form.getSequenceChecksumTypeName();
                        var sequenceChecksumType = sequenceControl.getSequenceChecksumTypeByName(sequenceChecksumTypeName);

                        if(sequenceChecksumType != null) {
                            var partyPK = getPartyPK();
                            var rawChunkSize = form.getChunkSize();
                            var chunkSize = rawChunkSize == null? null: Integer.valueOf(rawChunkSize);
                            var isDefault = Boolean.valueOf(form.getIsDefault());
                            var sortOrder = Integer.valueOf(form.getSortOrder());
                            var description = form.getDescription();

                            sequenceType = sequenceControl.createSequenceType(sequenceTypeName, prefix, suffix, sequenceEncoderType,
                                    sequenceChecksumType, chunkSize, isDefault, sortOrder, partyPK);

                            if(description != null) {
                                sequenceControl.createSequenceTypeDescription(sequenceType, getPreferredLanguage(), description, partyPK);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSequenceChecksumTypeName.name(), sequenceChecksumTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSequenceEncoderTypeName.name(), sequenceEncoderTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateSuffix.name(), suffix);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicatePrefix.name(), prefix);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateSequenceTypeName.name(), sequenceTypeName);
        }

        if(sequenceType != null && !hasExecutionErrors()) {
            result.setSequenceTypeName(sequenceType.getLastDetail().getSequenceTypeName());
            result.setEntityRef(sequenceType.getPrimaryKey().getEntityRef());
        }

        return result;
    }
    
}
