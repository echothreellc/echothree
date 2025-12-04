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

import com.echothree.control.user.term.common.form.GetTermsForm;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.term.server.factory.TermFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetTermsCommand
        extends BasePaginatedMultipleEntitiesCommand<Term, GetTermsForm> {

    @Inject
    TermControl termControl;

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetTermsCommand */
    public GetTermsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return termControl.countTerms();
    }

    @Override
    protected Collection<Term> getEntities() {
        return termControl.getTerms();
    }

    @Override
    protected BaseResult getResult(Collection<Term> entities) {
        var result = TermResultFactory.getGetTermsResult();

        if(entities != null) {
            if(session.hasLimit(TermFactory.class)) {
                result.setTermCount(getTotalEntities());
            }

            result.setTerms(termControl.getTermTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
