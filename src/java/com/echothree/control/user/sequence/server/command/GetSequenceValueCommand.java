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

import com.echothree.control.user.sequence.common.form.GetSequenceValueForm;
import com.echothree.control.user.sequence.common.result.SequenceResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetSequenceValueCommand
        extends BaseSimpleCommand<GetSequenceValueForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
       COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
               new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
               new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                       new SecurityRoleDefinition(SecurityRoleGroups.SequenceValue.name(), SecurityRoles.Get.name())
               ))
       ));

         FORM_FIELD_DEFINITIONS = List.of(
                 new FieldDefinition("SequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                 new FieldDefinition("SequenceName", FieldType.ENTITY_NAME, false, null, null),
                 new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                 new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
         );
    }

    /** Creates a new instance of SetSequenceValueCommand */
    public GetSequenceValueCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Inject
    SequenceControl sequenceControl;

    @Inject
    SequenceLogic sequenceLogic;

    @Override
    protected BaseResult execute() {
        var result = SequenceResultFactory.getGetSequenceValueResult();
        var sequence = sequenceLogic.getSequenceByUniversalSpec(this, form, false);

        if(!hasExecutionErrors()) {
            var sequenceValue = sequenceControl.getSequenceValue(sequence);

            result.setValue(sequenceValue.getValue());
        }

        return result;
    }
    
}
