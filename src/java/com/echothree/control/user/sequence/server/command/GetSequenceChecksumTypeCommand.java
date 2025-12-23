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

import com.echothree.control.user.sequence.common.form.GetSequenceChecksumTypeForm;
import com.echothree.control.user.sequence.common.result.SequenceResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceChecksumTypeLogic;
import com.echothree.model.data.sequence.server.entity.SequenceChecksumType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetSequenceChecksumTypeCommand
        extends BaseSingleEntityCommand<SequenceChecksumType, GetSequenceChecksumTypeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SequenceChecksumType.name(), SecurityRoles.Review.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SequenceChecksumTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }

    /** Creates a new instance of GetSequenceChecksumTypeCommand */
    public GetSequenceChecksumTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected SequenceChecksumType getEntity() {
        var sequenceChecksumTypeName = form.getSequenceChecksumTypeName();

        return SequenceChecksumTypeLogic.getInstance().getSequenceChecksumTypeByName(this, sequenceChecksumTypeName);
    }

    @Override
    protected BaseResult getResult(SequenceChecksumType sequenceChecksumType) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var result = SequenceResultFactory.getGetSequenceChecksumTypeResult();

        if(sequenceChecksumType != null) {
            result.setSequenceChecksumType(sequenceControl.getSequenceChecksumTypeTransfer(getUserVisit(), sequenceChecksumType));
        }

        return result;
    }

}
