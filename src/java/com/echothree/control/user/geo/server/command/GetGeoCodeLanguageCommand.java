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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.form.GetGeoCodeLanguageForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCodeLanguage;
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
public class GetGeoCodeLanguageCommand
        extends BaseSingleEntityCommand<GeoCodeLanguage, GetGeoCodeLanguageForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeLanguage.name(), SecurityRoles.Review.name())
                        ))
                ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                );
    }
    
    /** Creates a new instance of GetGeoCodeLanguageCommand */
    public GetGeoCodeLanguageCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Inject
    GeoControl geoControl;

    @Inject
    PartyControl partyControl;

    @Override
    protected GeoCodeLanguage getEntity() {
        var geoCodeName = form.getGeoCodeName();
        var geoCode = geoControl.getGeoCodeByName(geoCodeName);
        GeoCodeLanguage geoCodeLanguage = null;

        if(geoCode != null) {
            var languageIsoName = form.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                geoCodeLanguage = geoControl.getGeoCodeLanguageForUpdate(geoCode, language);

                if(geoCodeLanguage == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeLanguage.name(), geoCodeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }

        return geoCodeLanguage;
    }

    @Override
    protected BaseResult getResult(GeoCodeLanguage geoCodeLanguage) {
        var result = GeoResultFactory.getGetGeoCodeLanguageResult();

        if(geoCodeLanguage != null) {
            result.setGeoCodeLanguage(geoControl.getGeoCodeLanguageTransfer(getUserVisit(), geoCodeLanguage));
        }

        return result;
    }
    
}
