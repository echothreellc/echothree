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

import com.echothree.control.user.geo.common.form.GetGeoCodeDateTimeFormatsForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeDateTimeFormat;
import com.echothree.model.data.geo.server.factory.GeoCodeCurrencyFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeDateTimeFormatFactory;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
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
public class GetGeoCodeDateTimeFormatsCommand
        extends BasePaginatedMultipleEntitiesCommand<GeoCodeDateTimeFormat, GetGeoCodeDateTimeFormatsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeDateTimeFormat.name(), SecurityRoles.List.name())
                        ))
                ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DateTimeFormatName", FieldType.ENTITY_NAME, false, null, null)
                );
    }
    
    /** Creates a new instance of GetGeoCodeDateTimeFormatsCommand */
    public GetGeoCodeDateTimeFormatsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Inject
    GeoControl geoControl;

    @Inject
    PartyControl partyControl;

    GeoCode geoCode;
    DateTimeFormat dateTimeFormat;

    @Override
    protected void handleForm() {
        var geoCodeName = form.getGeoCodeName();
        var dateTimeFormatName = form.getDateTimeFormatName();
        var parameterCount = (geoCodeName != null ? 1 : 0) + (dateTimeFormatName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(geoCodeName != null) {
                geoCode = geoControl.getGeoCodeByName(geoCodeName);

                if(geoCode == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
                }
            } else {
                dateTimeFormat = partyControl.getDateTimeFormatByName(dateTimeFormatName);

                if(dateTimeFormat == null) {
                    addExecutionError(ExecutionErrors.UnknownDateTimeFormatName.name(), dateTimeFormatName);
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
                totalEntities = geoControl.countGeoCodeDateTimeFormatsByGeoCode(geoCode);
            } else {
                totalEntities = geoControl.countGeoCodeDateTimeFormatsByDateTimeFormat(dateTimeFormat);
            }
        }

        return totalEntities;
    }

    @Override
    protected Collection<GeoCodeDateTimeFormat> getEntities() {
        Collection<GeoCodeDateTimeFormat> entities = null;

        if(!hasExecutionErrors()) {
            if(geoCode != null) {
                entities = geoControl.getGeoCodeDateTimeFormatsByGeoCode(geoCode);
            } else {
                entities = geoControl.getGeoCodeDateTimeFormatsByDateTimeFormat(dateTimeFormat);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<GeoCodeDateTimeFormat> entities) {
        var result = GeoResultFactory.getGetGeoCodeDateTimeFormatsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(geoCode != null) {
                result.setGeoCode(geoControl.getGeoCodeTransfer(userVisit, geoCode));
            } else {
                result.setDateTimeFormat(partyControl.getDateTimeFormatTransfer(userVisit, dateTimeFormat));
            }

            if(session.hasLimit(GeoCodeDateTimeFormatFactory.class)) {
                result.setGeoCodeDateTimeFormatCount(getTotalEntities());
            }

            result.setGeoCodeDateTimeFormats(geoControl.getGeoCodeDateTimeFormatTransfers(userVisit, entities));
        }

        return result;
    }
    
}
