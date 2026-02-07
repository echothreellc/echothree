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

import com.echothree.control.user.geo.common.form.GetGeoCodeTimeZonesForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeTimeZone;
import com.echothree.model.data.geo.server.factory.GeoCodeTimeZoneFactory;
import com.echothree.model.data.party.server.entity.TimeZone;
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
public class GetGeoCodeTimeZonesCommand
        extends BasePaginatedMultipleEntitiesCommand<GeoCodeTimeZone, GetGeoCodeTimeZonesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeTimeZone.name(), SecurityRoles.List.name())
                        ))
                ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("JavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null)
                );
    }
    
    /** Creates a new instance of GetGeoCodeTimeZonesCommand */
    public GetGeoCodeTimeZonesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Inject
    GeoControl geoControl;

    @Inject
    PartyControl partyControl;

    GeoCode geoCode;
    TimeZone timeZone;

    @Override
    protected void handleForm() {
        var geoCodeName = form.getGeoCodeName();
        var javaTimeZoneName = form.getJavaTimeZoneName();
        var parameterCount = (geoCodeName != null ? 1 : 0) + (javaTimeZoneName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(geoCodeName != null) {
                geoCode = geoControl.getGeoCodeByName(geoCodeName);

                if(geoCode == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
                }
            } else {
                timeZone = partyControl.getTimeZoneByJavaName(javaTimeZoneName);

                if(timeZone == null) {
                    addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), javaTimeZoneName);
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
                totalEntities = geoControl.countGeoCodeTimeZonesByGeoCode(geoCode);
            } else {
                totalEntities = geoControl.countGeoCodeTimeZonesByTimeZone(timeZone);
            }
        }

        return totalEntities;
    }

    @Override
    protected Collection<GeoCodeTimeZone> getEntities() {
        Collection<GeoCodeTimeZone> entities = null;

        if(!hasExecutionErrors()) {
            if(geoCode != null) {
                entities = geoControl.getGeoCodeTimeZonesByGeoCode(geoCode);
            } else {
                entities = geoControl.getGeoCodeTimeZonesByTimeZone(timeZone);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<GeoCodeTimeZone> entities) {
        var result = GeoResultFactory.getGetGeoCodeTimeZonesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(geoCode != null) {
                result.setGeoCode(geoControl.getGeoCodeTransfer(userVisit, geoCode));
            } else {
                result.setTimeZone(partyControl.getTimeZoneTransfer(userVisit, timeZone));
            }

            if(session.hasLimit(GeoCodeTimeZoneFactory.class)) {
                result.setGeoCodeTimeZoneCount(getTotalEntities());
            }

            result.setGeoCodeTimeZones(geoControl.getGeoCodeTimeZoneTransfers(userVisit, entities));
        }

        return result;
    }
    
}
