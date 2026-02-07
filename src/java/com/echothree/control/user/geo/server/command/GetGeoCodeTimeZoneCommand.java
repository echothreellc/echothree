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

import com.echothree.control.user.geo.common.form.GetGeoCodeTimeZoneForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCodeTimeZone;
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
public class GetGeoCodeTimeZoneCommand
        extends BaseSingleEntityCommand<GeoCodeTimeZone, GetGeoCodeTimeZoneForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeTimeZone.name(), SecurityRoles.Review.name())
                        ))
                ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("JavaTimeZoneName", FieldType.TIME_ZONE_NAME, true, null, null)
                );
    }
    
    /** Creates a new instance of GetGeoCodeTimeZoneCommand */
    public GetGeoCodeTimeZoneCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Inject
    GeoControl geoControl;

    @Inject
    PartyControl partyControl;

    @Override
    protected GeoCodeTimeZone getEntity() {
        var geoCodeName = form.getGeoCodeName();
        var geoCode = geoControl.getGeoCodeByName(geoCodeName);
        GeoCodeTimeZone geoCodeTimeZone = null;

        if(geoCode != null) {
            var javaTimeZoneName = form.getJavaTimeZoneName();
            var timeZone = partyControl.getTimeZoneByJavaName(javaTimeZoneName);

            if(timeZone != null) {
                geoCodeTimeZone = geoControl.getGeoCodeTimeZoneForUpdate(geoCode, timeZone);

                if(geoCodeTimeZone == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeTimeZone.name(), geoCodeName, javaTimeZoneName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), javaTimeZoneName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }

        return geoCodeTimeZone;
    }

    @Override
    protected BaseResult getResult(GeoCodeTimeZone geoCodeTimeZone) {
        var result = GeoResultFactory.getGetGeoCodeTimeZoneResult();

        if(geoCodeTimeZone != null) {
            result.setGeoCodeTimeZone(geoControl.getGeoCodeTimeZoneTransfer(getUserVisit(), geoCodeTimeZone));
        }

        return result;
    }
    
}
