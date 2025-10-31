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

import com.echothree.control.user.geo.common.form.GetGeoCodeTimeZonesForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetGeoCodeTimeZonesCommand
        extends BaseSimpleCommand<GetGeoCodeTimeZonesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeTimeZone.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("JavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetGeoCodeTimeZonesCommand */
    public GetGeoCodeTimeZonesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = Session.getModelController(GeoControl.class);
        var result = GeoResultFactory.getGetGeoCodeTimeZonesResult();
        var geoCodeName = form.getGeoCodeName();
        var javaTimeZoneName = form.getJavaTimeZoneName();
        var parameterCount = (geoCodeName != null? 1: 0) + (javaTimeZoneName != null? 1: 0);
        
        if(parameterCount == 1) {
            if(geoCodeName != null) {
                var geoCode = geoControl.getGeoCodeByName(geoCodeName);
                
                if(geoCode != null) {
                    result.setGeoCode(geoControl.getGeoCodeTransfer(getUserVisit(), geoCode));
                    result.setGeoCodeTimeZones(geoControl.getGeoCodeTimeZoneTransfersByGeoCode(getUserVisit(), geoCode));
                } else {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
                }
            } else if(javaTimeZoneName != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var timeZone = partyControl.getTimeZoneByJavaName(javaTimeZoneName);
                
                if(timeZone != null) {
                    result.setTimeZone(partyControl.getTimeZoneTransfer(getUserVisit(), timeZone));
                    result.setGeoCodeTimeZones(geoControl.getGeoCodeTimeZoneTransfersByTimeZone(getUserVisit(), timeZone));
                } else {
                    addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), javaTimeZoneName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
