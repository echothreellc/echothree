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

import com.echothree.control.user.associate.common.form.GetAssociatesForm;
import com.echothree.control.user.associate.common.result.AssociateResultFactory;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.associate.server.logic.AssociateProgramLogic;
import com.echothree.model.data.associate.server.entity.Associate;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
import com.echothree.model.data.associate.server.factory.AssociateFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetAssociatesCommand
        extends BasePaginatedMultipleEntitiesCommand<Associate, GetAssociatesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    AssociateControl associateControl;

    @Inject
    AssociateProgramLogic associateProgramLogic;
    
    /** Creates a new instance of GetAssociatesCommand */
    public GetAssociatesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    AssociateProgram associateProgram;

    @Override
    protected void handleForm() {
        associateProgram = associateProgramLogic.getAssociateProgramByName(this, form.getAssociateProgramName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : associateControl.countAssociatesByAssociateProgram(associateProgram);
    }

    @Override
    protected Collection<Associate> getEntities() {
        return hasExecutionErrors() ? null : associateControl.getAssociates(associateProgram);
    }

    @Override
    protected BaseResult getResult(final Collection<Associate> entities) {
        var result = AssociateResultFactory.getGetAssociatesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setAssociateProgram(associateControl.getAssociateProgramTransfer(userVisit, associateProgram));

            if(session.hasLimit(AssociateFactory.class)) {
                result.setAssociateCount(getTotalEntities());
            }

            result.setAssociates(associateControl.getAssociateTransfers(entities, userVisit));
        }

        return result;
    }
    
}
