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

import com.echothree.control.user.term.common.form.GetTermForm;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.term.server.logic.TermLogic;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetTermCommand
        extends BaseSingleEntityCommand<Term, GetTermForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TermName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of GetTermCommand */
    public GetTermCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Term getEntity() {
        var term = TermLogic.getInstance().getTermByUniversalSpec(this, form, true);

        if(term != null) {
            sendEvent(term.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return term;
    }

    @Override
    protected BaseResult getResult(Term term) {
        var result = TermResultFactory.getGetTermResult();
        var termControl = Session.getModelController(TermControl.class);

        if(term != null) {
            result.setTerm(termControl.getTermTransfer(getUserVisit(), term));
        }

        return result;
    }
    
}
