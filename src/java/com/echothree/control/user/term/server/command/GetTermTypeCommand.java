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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.form.GetTermTypeForm;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.term.server.logic.TermTypeLogic;
import com.echothree.model.data.term.server.entity.TermType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetTermTypeCommand
        extends BaseSingleEntityCommand<TermType, GetTermTypeForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TermTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of GetTermTypeCommand */
    public GetTermTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected TermType getEntity() {
        var termType = TermTypeLogic.getInstance().getTermTypeByUniversalSpec(this, form, true);

        if(termType != null) {
            sendEvent(termType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return termType;
    }

    @Override
    protected BaseResult getResult(TermType termType) {
        var result = TermResultFactory.getGetTermTypeResult();
        var termControl = Session.getModelController(TermControl.class);

        if(termType != null) {
            result.setTermType(termControl.getTermTypeTransfer(getUserVisit(), termType));
        }

        return result;
    }
    
}
