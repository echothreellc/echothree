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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetAppearanceTextTransformationsForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.logic.AppearanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.AppearanceTextTransformation;
import com.echothree.model.data.core.server.factory.AppearanceTextTransformationFactory;
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
public class GetAppearanceTextTransformationsCommand
        extends BasePaginatedMultipleEntitiesCommand<AppearanceTextTransformation, GetAppearanceTextTransformationsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Appearance.name(), SecurityRoles.AppearanceTextTransformation.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("AppearanceName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    AppearanceControl appearanceControl;

    @Inject
    AppearanceLogic appearanceLogic;
    
    /** Creates a new instance of GetAppearanceTextTransformationsCommand */
    public GetAppearanceTextTransformationsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    Appearance appearance;

    @Override
    protected void handleForm() {
        appearance = appearanceLogic.getAppearanceByName(this, form.getAppearanceName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : appearanceControl.countAppearanceTextTransformationsByAppearance(appearance);
    }

    @Override
    protected Collection<AppearanceTextTransformation> getEntities() {
        return hasExecutionErrors() ? null : appearanceControl.getAppearanceTextTransformationsByAppearance(appearance);
    }

    @Override
    protected BaseResult getResult(Collection<AppearanceTextTransformation> entities) {
        var result = CoreResultFactory.getGetAppearanceTextTransformationsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setAppearance(appearanceControl.getAppearanceTransfer(userVisit, appearance));

            if(session.hasLimit(AppearanceTextTransformationFactory.class)) {
                result.setAppearanceTextTransformationCount(getTotalEntities());
            }

            result.setAppearanceTextTransformations(appearanceControl.getAppearanceTextTransformationTransfers(userVisit, entities));
        }

        return result;
    }
    
}
