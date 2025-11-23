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

import com.echothree.control.user.sequence.common.form.CreateSequenceForm;
import com.echothree.control.user.sequence.common.result.SequenceResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.control.sequence.server.logic.SequenceTypeLogic;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateSequenceCommand
        extends BaseSimpleCommand<CreateSequenceForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateSequenceCommand */
    public CreateSequenceCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SequenceResultFactory.getCreateSequenceResult();
        var sequenceTypeName = form.getSequenceTypeName();
        var sequenceType = SequenceTypeLogic.getInstance().getSequenceTypeByName(this, sequenceTypeName);
        Sequence sequence = null;

        if(!hasExecutionErrors()) {
            var sequenceName = form.getSequenceName();
            var mask = form.getMask();
            var value = form.getValue();
            var rawChunkSize = form.getChunkSize();
            var chunkSize = rawChunkSize == null? null: Integer.valueOf(rawChunkSize);
            var isDefault = Boolean.valueOf(form.getIsDefault());
            var sortOrder = Integer.valueOf(form.getSortOrder());
            var description = form.getDescription();

            sequence = SequenceLogic.getInstance().createSequence(this, sequenceType, sequenceName, value, mask, chunkSize, isDefault,
                    sortOrder, description, getPreferredLanguage(), getPartyPK());
        }

        if(sequence != null && !hasExecutionErrors()) {
            result.setSequenceName(sequence.getLastDetail().getSequenceName());
            result.setEntityRef(sequence.getPrimaryKey().getEntityRef());
        }

        return result;
    }
    
}
