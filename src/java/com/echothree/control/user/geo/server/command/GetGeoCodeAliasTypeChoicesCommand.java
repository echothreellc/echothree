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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.form.GetGeoCodeAliasTypeChoicesForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetGeoCodeAliasTypeChoicesCommand
        extends BaseSimpleCommand<GetGeoCodeAliasTypeChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeAliasType.name(), SecurityRoles.Choices.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultGeoCodeAliasTypeChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetGeoCodeAliasTypeChoicesCommand */
    public GetGeoCodeAliasTypeChoicesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = Session.getModelController(GeoControl.class);
        var result = GeoResultFactory.getGetGeoCodeAliasTypeChoicesResult();
        var geoCodeTypeName = form.getGeoCodeTypeName();
        var geoCodeName = form.getGeoCodeName();
        var parameterCount = (geoCodeTypeName == null ? 0 : 1) + (geoCodeName == null ? 0 : 1);
        
        if(parameterCount == 1) {
            GeoCodeType geoCodeType = null;

            if(geoCodeTypeName != null) {
                geoCodeType = geoControl.getGeoCodeTypeByName(geoCodeTypeName);

                if(geoCodeType == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeTypeName.name(), geoCodeTypeName);
                }
            } else {
                var geoCode = geoControl.getGeoCodeByName(geoCodeName);
                
                if(geoCode == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
                } else {
                    geoCodeType = geoCode.getLastDetail().getGeoCodeType();
                }
            }

            if(geoCodeType != null) {
                var defaultGeoCodeAliasTypeChoice = form.getDefaultGeoCodeAliasTypeChoice();
                var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

                result.setGeoCodeAliasTypeChoices(geoControl.getGeoCodeAliasTypeChoices(defaultGeoCodeAliasTypeChoice, getPreferredLanguage(), allowNullChoice,
                        geoCodeType));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
