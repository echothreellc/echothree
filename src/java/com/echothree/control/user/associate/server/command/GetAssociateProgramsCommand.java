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

import com.echothree.control.user.associate.common.form.GetAssociateProgramsForm;
import com.echothree.control.user.associate.common.result.AssociateResultFactory;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
import com.echothree.model.data.associate.server.factory.AssociateProgramFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetAssociateProgramsCommand
        extends BasePaginatedMultipleEntitiesCommand<AssociateProgram, GetAssociateProgramsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    @Inject
    AssociateControl associateControl;

    /** Creates a new instance of GetAssociateProgramsCommand */
    public GetAssociateProgramsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected void handleForm() {
        // No form fields to handle
    }

    @Override
    protected Long getTotalEntities() {
        return associateControl.countAssociatePrograms();
    }

    @Override
    protected Collection<AssociateProgram> getEntities() {
        return associateControl.getAssociatePrograms();
    }

    @Override
    protected BaseResult getResult(Collection<AssociateProgram> entities) {
        var result = AssociateResultFactory.getGetAssociateProgramsResult();
        
        if(entities != null) {
            if(session.hasLimit(AssociateProgramFactory.class)) {
                result.setAssociateProgramCount(getTotalEntities());
            }

            result.setAssociatePrograms(associateControl.getAssociateProgramTransfers(getUserVisit(), entities));
        }
        
        return result;
    }
    
}
