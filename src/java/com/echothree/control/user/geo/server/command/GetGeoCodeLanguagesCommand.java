// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.geo.common.form.GetGeoCodeLanguagesForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.result.GetGeoCodeLanguagesResult;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.party.server.entity.Language;
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

public class GetGeoCodeLanguagesCommand
        extends BaseSimpleCommand<GetGeoCodeLanguagesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeLanguage.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetGeoCodeLanguagesCommand */
    public GetGeoCodeLanguagesCommand(UserVisitPK userVisitPK, GetGeoCodeLanguagesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GetGeoCodeLanguagesResult result = GeoResultFactory.getGetGeoCodeLanguagesResult();
        String geoCodeName = form.getGeoCodeName();
        String languageIsoName = form.getLanguageIsoName();
        int parameterCount = (geoCodeName != null? 1: 0) + (languageIsoName != null? 1: 0);
        
        if(parameterCount == 1) {
            if(geoCodeName != null) {
                GeoCode geoCode = geoControl.getGeoCodeByName(geoCodeName);
                
                if(geoCode != null) {
                    result.setGeoCode(geoControl.getGeoCodeTransfer(getUserVisit(), geoCode));
                    result.setGeoCodeLanguages(geoControl.getGeoCodeLanguageTransfersByGeoCode(getUserVisit(), geoCode));
                } else {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
                }
            } else if(languageIsoName != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    result.setLanguage(partyControl.getLanguageTransfer(getUserVisit(), language));
                    result.setGeoCodeLanguages(geoControl.getGeoCodeLanguageTransfersByLanguage(getUserVisit(), language));
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
