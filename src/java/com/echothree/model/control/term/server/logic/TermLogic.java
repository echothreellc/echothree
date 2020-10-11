// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.term.server.logic;

import com.echothree.model.control.term.common.exception.UnknownDefaultTermException;
import com.echothree.model.control.term.common.exception.UnknownPartyTermException;
import com.echothree.model.control.term.common.exception.UnknownTermNameException;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.term.server.entity.PartyTerm;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class TermLogic
        extends BaseLogic {

    private TermLogic() {
        super();
    }

    private static class TermLogicHolder {
        static TermLogic instance = new TermLogic();
    }

    public static TermLogic getInstance() {
        return TermLogicHolder.instance;
    }
    
    public Term getTermByName(final ExecutionErrorAccumulator eea, final String termName) {
        var termControl = (TermControl)Session.getModelController(TermControl.class);
        var term = termControl.getTermByName(termName);

        if(term == null) {
            handleExecutionError(UnknownTermNameException.class, eea, ExecutionErrors.UnknownTermName.name(), termName);
        }

        return term;
    }
    
    public PartyTerm getPartyTerm(final ExecutionErrorAccumulator eea, final Party party) {
        var termControl = (TermControl)Session.getModelController(TermControl.class);
        var partyTerm = termControl.getPartyTerm(party);

        if(partyTerm == null) {
            handleExecutionError(UnknownPartyTermException.class, eea, ExecutionErrors.UnknownPartyTerm.name(), party.getLastDetail().getPartyName());
        }

        return partyTerm;
    }
    
    public Term getDefaultTerm(final ExecutionErrorAccumulator eea) {
        var termControl = (TermControl)Session.getModelController(TermControl.class);
        var term = termControl.getDefaultTerm();

        if(term == null) {
            handleExecutionError(UnknownDefaultTermException.class, eea, ExecutionErrors.UnknownDefaultTerm.name());
        }

        return term;
    }

}
