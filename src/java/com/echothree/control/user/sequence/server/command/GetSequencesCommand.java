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

import com.echothree.control.user.sequence.common.form.GetSequencesForm;
import com.echothree.control.user.sequence.common.result.SequenceResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceTypeLogic;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.sequence.server.factory.SequenceFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetSequencesCommand
        extends BaseMultipleEntitiesCommand<Sequence, GetSequencesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Sequence.name(), SecurityRoles.List.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SequenceTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }

    /** Creates a new instance of GetSequencesCommand */
    public GetSequencesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    SequenceType sequenceType;

    @Override
    protected Collection<Sequence> getEntities() {
        var sequenceTypeName = form.getSequenceTypeName();
        Collection<Sequence> sequences = null;

        sequenceType = SequenceTypeLogic.getInstance().getSequenceTypeByName(this, sequenceTypeName);

        if(!hasExecutionErrors()) {
            var sequenceControl = Session.getModelController(SequenceControl.class);

            sequences = sequenceControl.getSequencesBySequenceType(sequenceType);
        }

        return sequences;
    }

    @Override
    protected BaseResult getResult(Collection<Sequence> entities) {
        var result = SequenceResultFactory.getGetSequencesResult();

        if(entities != null) {
            var sequenceControl = Session.getModelController(SequenceControl.class);

            if(session.hasLimit(SequenceFactory.class)) {
                result.setSequenceCount(sequenceControl.countSequencesBySequenceType(sequenceType));
            }

            result.setSequenceType(sequenceControl.getSequenceTypeTransfer(getUserVisit(), sequenceType));
            result.setSequences(sequenceControl.getSequenceTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
