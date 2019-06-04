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

import com.echothree.control.user.geo.common.form.GetGeoCodeTimeZoneForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.result.GetGeoCodeTimeZoneResult;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeTimeZone;
import com.echothree.model.data.party.server.entity.TimeZone;
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

public class GetGeoCodeTimeZoneCommand
        extends BaseSimpleCommand<GetGeoCodeTimeZoneForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeTimeZone.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("JavaTimeZoneName", FieldType.TIME_ZONE_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetGeoCodeTimeZoneCommand */
    public GetGeoCodeTimeZoneCommand(UserVisitPK userVisitPK, GetGeoCodeTimeZoneForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GetGeoCodeTimeZoneResult result = GeoResultFactory.getGetGeoCodeTimeZoneResult();
        String geoCodeName = form.getGeoCodeName();
        GeoCode geoCode = geoControl.getGeoCodeByName(geoCodeName);
        
        if(geoCode != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String javaTimeZoneName = form.getJavaTimeZoneName();
            TimeZone timeZone = partyControl.getTimeZoneByJavaName(javaTimeZoneName);
            
            if(timeZone != null) {
                GeoCodeTimeZone geoCodeTimeZone = geoControl.getGeoCodeTimeZoneForUpdate(geoCode, timeZone);
                
                if(geoCodeTimeZone != null) {
                    result.setGeoCodeTimeZone(geoControl.getGeoCodeTimeZoneTransfer(getUserVisit(), geoCodeTimeZone));
                } else {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeTimeZone.name(), geoCodeName, javaTimeZoneName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), javaTimeZoneName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }
        
        return result;
    }
    
}
