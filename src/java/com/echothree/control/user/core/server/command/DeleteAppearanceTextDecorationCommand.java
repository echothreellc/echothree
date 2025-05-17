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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.DeleteAppearanceTextDecorationForm;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.logic.AppearanceLogic;
import com.echothree.model.control.core.server.logic.TextLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteAppearanceTextDecorationCommand
        extends BaseSimpleCommand<DeleteAppearanceTextDecorationForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Appearance.name(), SecurityRoles.AppearanceTextDecoration.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AppearanceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TextDecorationName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteAppearanceTextDecorationCommand */
    public DeleteAppearanceTextDecorationCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var appearanceName = form.getAppearanceName();
        var appearance = AppearanceLogic.getInstance().getAppearanceByName(this, appearanceName);
        
        if(!hasExecutionErrors()) {
            var textDecorationName = form.getTextDecorationName();
            var textDecoration = TextLogic.getInstance().getTextDecorationByName(this, textDecorationName);
            
            if(!hasExecutionErrors()) {
                var appearanceControl = Session.getModelController(AppearanceControl.class);
                var appearanceTextDecoration = appearanceControl.getAppearanceTextDecorationForUpdate(appearance, textDecoration);
                
                if(appearanceTextDecoration != null) {
                    appearanceControl.deleteAppearanceTextDecoration(appearanceTextDecoration, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownAppearanceTextDecoration.name(), appearanceName, textDecorationName);
                }
            }
        }
        
        return null;
    }
    
}
