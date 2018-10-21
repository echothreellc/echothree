// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.core.remote.form.DeleteAppearanceTextTransformationForm;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.AppearanceLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.AppearanceTextTransformation;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteAppearanceTextTransformationCommand
        extends BaseSimpleCommand<DeleteAppearanceTextTransformationForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Appearance.name(), SecurityRoles.AppearanceTextTransformation.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AppearanceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TextTransformationName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteAppearanceTextTransformationCommand */
    public DeleteAppearanceTextTransformationCommand(UserVisitPK userVisitPK, DeleteAppearanceTextTransformationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        String appearanceName = form.getAppearanceName();
        Appearance appearance = AppearanceLogic.getInstance().getAppearanceByName(this, appearanceName);
        
        if(!hasExecutionErrors()) {
            String textTransformationName = form.getTextTransformationName();
            TextTransformation textTransformation = AppearanceLogic.getInstance().getTextTransformationByName(this, textTransformationName);
            
            if(!hasExecutionErrors()) {
                CoreControl coreControl = getCoreControl();
                AppearanceTextTransformation appearanceTextTransformation = coreControl.getAppearanceTextTransformationForUpdate(appearance, textTransformation);
                
                if(appearanceTextTransformation != null) {
                    coreControl.deleteAppearanceTextTransformation(appearanceTextTransformation, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownAppearanceTextTransformation.name(), appearanceName, textTransformationName);
                }
            }
        }
        
        return null;
    }
    
}
