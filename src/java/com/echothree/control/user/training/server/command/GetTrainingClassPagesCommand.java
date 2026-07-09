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

package com.echothree.control.user.training.server.command;

import com.echothree.control.user.training.common.form.GetTrainingClassPagesForm;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.training.server.logic.TrainingClassLogic;
import com.echothree.model.control.training.server.logic.TrainingClassSectionLogic;
import com.echothree.model.data.training.server.entity.TrainingClassPage;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.model.data.training.server.factory.TrainingClassPageFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetTrainingClassPagesCommand
        extends BasePaginatedMultipleEntitiesCommand<TrainingClassPage, GetTrainingClassPagesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassPage.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    private TrainingControl trainingControl;

    @Inject
    private TrainingClassLogic trainingClassLogic;

    @Inject
    private TrainingClassSectionLogic trainingClassSectionLogic;
    
    /** Creates a new instance of GetTrainingClassPagesCommand */
    public GetTrainingClassPagesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private TrainingClassSection trainingClassSection;

    @Override
    protected void handleForm() {
        var trainingClassName = form.getTrainingClassName();
        var trainingClass = trainingClassLogic.getTrainingClassByName(this, trainingClassName, false);

        if(!hasExecutionErrors()) {
            var trainingClassSectionName = form.getTrainingClassSectionName();

            trainingClassSection = trainingClassSectionLogic.getTrainingClassSectionByName(this, trainingClass, trainingClassSectionName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : trainingControl.countTrainingClassPages(trainingClassSection);
    }

    @Override
    protected Collection<TrainingClassPage> getEntities() {
        return hasExecutionErrors() ? null : trainingControl.getTrainingClassPages(trainingClassSection);
    }

    @Override
    protected BaseResult getResult(Collection<TrainingClassPage> entities) {
        var result = TrainingResultFactory.getGetTrainingClassPagesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setTrainingClassSection(trainingControl.getTrainingClassSectionTransfer(userVisit, trainingClassSection));

            if(session.hasLimit(TrainingClassPageFactory.class)) {
                result.setTrainingClassPageCount(getTotalEntities());
            }

            result.setTrainingClassPages(trainingControl.getTrainingClassPageTransfers(userVisit, entities));
        }

        return result;
    }
    
}
