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

package com.echothree.control.user.sequence.server.command;

import com.echothree.control.user.sequence.common.form.CreateSequenceForm;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.control.sequence.server.logic.SequenceTypeLogic;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateSequenceCommand
        extends BaseSimpleCommand<CreateSequenceForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Sequence.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SequenceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Mask", FieldType.SEQUENCE_MASK, true, null, null),
                new FieldDefinition("ChunkSize", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Value", FieldType.STRING, false, 1L, 40L),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateSequenceCommand */
    public CreateSequenceCommand(UserVisitPK userVisitPK, CreateSequenceForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        String sequenceTypeName = form.getSequenceTypeName();
        SequenceType sequenceType = SequenceTypeLogic.getInstance().getSequenceTypeByName(this, sequenceTypeName);
        
        if(!hasExecutionErrors()) {
            String sequenceName = form.getSequenceName();
            String mask = form.getMask();
            String value = form.getValue();
            String rawChunkSize = form.getChunkSize();
            Integer chunkSize = rawChunkSize == null? null: Integer.valueOf(rawChunkSize);
            Boolean isDefault = Boolean.valueOf(form.getIsDefault());
            Integer sortOrder = Integer.valueOf(form.getSortOrder());
            String description = form.getDescription();

            SequenceLogic.getInstance().createSequence(this, sequenceType, sequenceName, value, mask, chunkSize, isDefault,
                    sortOrder, description, getPreferredLanguage(), getPartyPK());
        }
        
        return null;
    }
    
}
