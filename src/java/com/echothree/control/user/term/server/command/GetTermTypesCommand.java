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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.form.GetTermTypesForm;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.term.server.entity.TermType;
import com.echothree.model.data.term.server.factory.TermTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetTermTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<TermType, GetTermTypesForm> {

    @Inject
    TermControl termControl;

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetTermTypesCommand */
    public GetTermTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return termControl.countTermTypes();
    }

    @Override
    protected Collection<TermType> getEntities() {
        return termControl.getTermTypes();
    }

    @Override
    protected BaseResult getResult(Collection<TermType> entities) {
        var result = TermResultFactory.getGetTermTypesResult();

        if(entities != null) {
            if(session.hasLimit(TermTypeFactory.class)) {
                result.setTermTypeCount(getTotalEntities());
            }

            result.setTermTypes(termControl.getTermTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
