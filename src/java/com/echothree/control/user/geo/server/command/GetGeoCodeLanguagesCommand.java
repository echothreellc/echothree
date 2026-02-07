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

import com.echothree.control.user.geo.common.form.GetGeoCodeLanguagesForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeLanguage;
import com.echothree.model.data.geo.server.factory.GeoCodeLanguageFactory;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
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
public class GetGeoCodeLanguagesCommand
        extends BasePaginatedMultipleEntitiesCommand<GeoCodeLanguage, GetGeoCodeLanguagesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeLanguage.name(), SecurityRoles.List.name())
                        ))
                ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null)
                );
    }
    
    /** Creates a new instance of GetGeoCodeLanguagesCommand */
    public GetGeoCodeLanguagesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Inject
    GeoControl geoControl;

    @Inject
    PartyControl partyControl;

    GeoCode geoCode;
    Language language;

    @Override
    protected void handleForm() {
        var geoCodeName = form.getGeoCodeName();
        var languageIsoName = form.getLanguageIsoName();
        var parameterCount = (geoCodeName != null ? 1 : 0) + (languageIsoName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(geoCodeName != null) {
                geoCode = geoControl.getGeoCodeByName(geoCodeName);

                if(geoCode == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
                }
            } else {
                language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language == null) {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long totalEntities = null;

        if(!hasExecutionErrors()) {
            if(geoCode != null) {
                totalEntities = geoControl.countGeoCodeLanguagesByGeoCode(geoCode);
            } else {
                totalEntities = geoControl.countGeoCodeLanguagesByLanguage(language);
            }
        }

        return totalEntities;
    }

    @Override
    protected Collection<GeoCodeLanguage> getEntities() {
        Collection<GeoCodeLanguage> entities = null;

        if(!hasExecutionErrors()) {
            if(geoCode != null) {
                entities = geoControl.getGeoCodeLanguagesByGeoCode(geoCode);
            } else {
                entities = geoControl.getGeoCodeLanguagesByLanguage(language);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<GeoCodeLanguage> entities) {
        var result = GeoResultFactory.getGetGeoCodeLanguagesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(geoCode != null) {
                result.setGeoCode(geoControl.getGeoCodeTransfer(userVisit, geoCode));
            } else {
                result.setLanguage(partyControl.getLanguageTransfer(userVisit, language));
            }

            if(session.hasLimit(GeoCodeLanguageFactory.class)) {
                result.setGeoCodeLanguageCount(getTotalEntities());
            }

            result.setGeoCodeLanguages(geoControl.getGeoCodeLanguageTransfers(userVisit, entities));
        }

        return result;
    }
    
}
