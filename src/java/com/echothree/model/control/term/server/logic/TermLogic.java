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

package com.echothree.model.control.term.server.logic;

import com.echothree.control.user.term.common.spec.TermUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.term.common.exception.DuplicateTermNameException;
import com.echothree.model.control.term.common.exception.UnknownDefaultTermException;
import com.echothree.model.control.term.common.exception.UnknownPartyTermException;
import com.echothree.model.control.term.common.exception.UnknownTermNameException;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.term.server.entity.PartyTerm;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.term.server.entity.TermType;
import com.echothree.model.data.term.server.value.TermDetailValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class TermLogic
        extends BaseLogic {

    protected TermLogic() {
        super();
    }

    public static TermLogic getInstance() {
        return CDI.current().select(TermLogic.class).get();
    }

    public Term createTerm(final ExecutionErrorAccumulator eea, final String termName, final TermType termType,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var termControl = Session.getModelController(TermControl.class);
        var term = termControl.getTermByName(termName);

        if(term == null) {
            term = termControl.createTerm(termName, termType, isDefault, sortOrder, createdBy);

            if(description != null) {
                termControl.createTermDescription(term, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateTermNameException.class, eea, ExecutionErrors.DuplicateTermName.name(), termName);
        }

        return term;
    }

    public Term getTermByName(final ExecutionErrorAccumulator eea, final String termName,
            final EntityPermission entityPermission) {
        var termControl = Session.getModelController(TermControl.class);
        var term = termControl.getTermByName(termName, entityPermission);

        if(term == null) {
            handleExecutionError(UnknownTermNameException.class, eea, ExecutionErrors.UnknownTermName.name(), termName);
        }

        return term;
    }

    public Term getTermByName(final ExecutionErrorAccumulator eea, final String termName) {
        return getTermByName(eea, termName, EntityPermission.READ_ONLY);
    }

    public Term getTermByNameForUpdate(final ExecutionErrorAccumulator eea, final String termName) {
        return getTermByName(eea, termName, EntityPermission.READ_WRITE);
    }

    public Term getTermByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TermUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        Term term = null;
        var termControl = Session.getModelController(TermControl.class);
        var termName = universalSpec.getTermName();
        var parameterCount = (termName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    term = termControl.getDefaultTerm(entityPermission);

                    if(term == null) {
                        handleExecutionError(UnknownDefaultTermException.class, eea, ExecutionErrors.UnknownDefaultTerm.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(termName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Term.name());

                    if(!eea.hasExecutionErrors()) {
                        term = termControl.getTermByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    term = getTermByName(eea, termName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return term;
    }

    public Term getTermByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TermUniversalSpec universalSpec, boolean allowDefault) {
        return getTermByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public Term getTermByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final TermUniversalSpec universalSpec, boolean allowDefault) {
        return getTermByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public PartyTerm getPartyTerm(final ExecutionErrorAccumulator eea, final Party party) {
        var termControl = Session.getModelController(TermControl.class);
        var partyTerm = termControl.getPartyTerm(party);

        if(partyTerm == null) {
            handleExecutionError(UnknownPartyTermException.class, eea, ExecutionErrors.UnknownPartyTerm.name(), party.getLastDetail().getPartyName());
        }

        return partyTerm;
    }
    
    public Term getDefaultTerm(final ExecutionErrorAccumulator eea) {
        var termControl = Session.getModelController(TermControl.class);
        var term = termControl.getDefaultTerm();

        if(term == null) {
            handleExecutionError(UnknownDefaultTermException.class, eea, ExecutionErrors.UnknownDefaultTerm.name());
        }

        return term;
    }

    public void updateTermFromValue(final ExecutionErrorAccumulator eea, TermDetailValue termDetailValue, BasePK updatedBy) {
        var termControl = Session.getModelController(TermControl.class);

        termControl.updateTermFromValue(termDetailValue, updatedBy);
    }

    public void deleteTerm(final ExecutionErrorAccumulator eea, Term term, BasePK deletedBy) {
        var termControl = Session.getModelController(TermControl.class);

        termControl.deleteTerm(term, deletedBy);
    }

}
