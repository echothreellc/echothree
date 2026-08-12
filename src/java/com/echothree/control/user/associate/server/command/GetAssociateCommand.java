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

package com.echothree.control.user.associate.server.command;

import com.echothree.control.user.associate.common.form.GetAssociateForm;
import com.echothree.control.user.associate.common.result.AssociateResultFactory;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.associate.server.logic.AssociateLogic;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.associate.server.entity.Associate;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetAssociateCommand
        extends BaseSingleEntityCommand<Associate, GetAssociateForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AssociateName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    AssociateControl associateControl;

    @Inject
    AssociateLogic associateLogic;
    
    /** Creates a new instance of GetAssociateCommand */
    public GetAssociateCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Associate getEntity() {
        var associate = associateLogic.getAssociateByUniversalSpec(this, form, true);

        if(associate != null) {
            sendEvent(associate.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return associate;
    }

    @Override
    protected BaseResult getResult(final Associate associate) {
        var result = AssociateResultFactory.getGetAssociateResult();

        if(associate != null) {
            result.setAssociate(associateControl.getAssociateTransfer(getUserVisit(), associate));
        }

        return result;
    }
    
}
