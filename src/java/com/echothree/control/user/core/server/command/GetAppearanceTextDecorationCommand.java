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

import com.echothree.control.user.core.common.form.GetAppearanceTextDecorationForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.logic.AppearanceLogic;
import com.echothree.model.control.core.server.logic.TextLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.AppearanceTextDecoration;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetAppearanceTextDecorationCommand
        extends BaseSingleEntityCommand<AppearanceTextDecoration, GetAppearanceTextDecorationForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Appearance.name(), SecurityRoles.AppearanceTextDecoration.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("AppearanceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TextDecorationName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    AppearanceControl appearanceControl;

    @Inject
    AppearanceLogic appearanceLogic;

    @Inject
    TextLogic textLogic;

    /** Creates a new instance of GetAppearanceTextDecorationCommand */
    public GetAppearanceTextDecorationCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected AppearanceTextDecoration getEntity() {
        var appearanceName = form.getAppearanceName();
        var appearance = appearanceLogic.getAppearanceByName(this, appearanceName);
        AppearanceTextDecoration appearanceTextDecoration = null;
        
        if(!hasExecutionErrors()) {
            var textDecorationName = form.getTextDecorationName();
            var textDecoration = textLogic.getTextDecorationByName(this, textDecorationName);
            
            if(!hasExecutionErrors()) {
                appearanceTextDecoration = appearanceControl.getAppearanceTextDecoration(appearance, textDecoration);

                if(appearanceTextDecoration == null) {
                    addExecutionError(ExecutionErrors.UnknownAppearanceTextDecoration.name(), appearanceName, textDecorationName);
                }
            }
        }

        return appearanceTextDecoration;
    }

    @Override
    protected BaseResult getResult(AppearanceTextDecoration appearanceTextDecoration) {
        var result = CoreResultFactory.getGetAppearanceTextDecorationResult();

        if(appearanceTextDecoration != null) {
            result.setAppearanceTextDecoration(appearanceControl.getAppearanceTextDecorationTransfer(getUserVisit(), appearanceTextDecoration));
        }

        return result;
    }
    
}
