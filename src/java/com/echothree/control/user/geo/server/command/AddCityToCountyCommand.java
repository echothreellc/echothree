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

import com.echothree.control.user.geo.common.form.AddCityToCountyForm;
import com.echothree.model.control.geo.common.GeoCodeAliasTypes;
import com.echothree.model.control.geo.common.GeoCodeTypes;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeRelationship;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AddCityToCountyCommand
        extends BaseSimpleCommand<AddCityToCountyForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.County.name(), SecurityRoles.Edit.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CityGeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CountyGeoCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CountyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of AddCityToCountyCommand */
    public AddCityToCountyCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var countyGeoCodeName = form.getCountyGeoCodeName();
        var countyName = form.getCountyName();
        var parameterCount = (countyGeoCodeName == null ? 0 : 1) + (countyName == null ? 0 : 1);
        
        if(parameterCount == 1) {
            var geoControl = Session.getModelController(GeoControl.class);
            var geoCodeType = geoControl.getGeoCodeTypeByName(GeoCodeTypes.COUNTY.name());
            var cityGeoCodeName = form.getCityGeoCodeName();
            var cityGeoCode = geoControl.getGeoCodeByName(cityGeoCodeName);
            GeoCode countyGeoCode = null;
            
            if(countyGeoCodeName != null) {
                countyGeoCode = geoControl.getGeoCodeByName(countyGeoCodeName);
                
                if(!countyGeoCode.getLastDetail().getGeoCodeType().equals(geoCodeType)) {
                    countyGeoCode = null;
                }
                
                if(countyGeoCode == null) {
                    addExecutionError(ExecutionErrors.UnknownCountyGeoCodeName.name(), countyGeoCodeName);
                }
            } else if(countyName != null) {
                var stateGeoCodeType = geoControl.getGeoCodeTypeByName(GeoCodeTypes.STATE.name());
                GeoCode stateGeoCode = null;
                Collection cityRelationships = geoControl.getGeoCodeRelationshipsByFromGeoCode(cityGeoCode);
                for(var iter = cityRelationships.iterator(); iter.hasNext();) {
                    var geoCodeRelationship = (GeoCodeRelationship)iter.next();
                    var toGeoCode = geoCodeRelationship.getToGeoCode();
                    if(toGeoCode.getLastDetail().getGeoCodeType().equals(stateGeoCodeType)) {
                        stateGeoCode = toGeoCode;
                        break;
                    }
                }

                var stateGeoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(stateGeoCode.getLastDetail().getGeoCodeType(), GeoCodeAliasTypes.POSTAL_2_LETTER.name());
                var stateGeoCodeAlias = geoControl.getGeoCodeAlias(stateGeoCode, stateGeoCodeAliasType);
                var statePostal2Letter = stateGeoCodeAlias.getAlias();

                var countryGeoCodeType = geoControl.getGeoCodeTypeByName(GeoCodeTypes.COUNTRY.name());
                GeoCode countryGeoCode = null;
                Collection stateRelationships = geoControl.getGeoCodeRelationshipsByFromGeoCode(stateGeoCode);
                for(var iter = stateRelationships.iterator(); iter.hasNext();) {
                    var geoCodeRelationship = (GeoCodeRelationship)iter.next();
                    var toGeoCode = geoCodeRelationship.getToGeoCode();
                    if(toGeoCode.getLastDetail().getGeoCodeType().equals(countryGeoCodeType)) {
                        countryGeoCode = toGeoCode;
                        break;
                    }
                }

                var countryGeoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoCodeAliasTypes.ISO_2_LETTER.name());
                var countryGeoCodeAlias = geoControl.getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
                var countryIso2Letter = countryGeoCodeAlias.getAlias();

                var geoCodeScopeName = countryIso2Letter + "_" + statePostal2Letter + "_COUNTIES";
                var geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);

                var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.COUNTY_NAME.name());
                var geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, countyName);
                countyGeoCode = geoCodeAlias.getGeoCode();
                
                if(countyGeoCode == null) {
                    addExecutionError(ExecutionErrors.UnknownCountyName.name(), countyName);
                }
            }
            
            if(countyGeoCode != null) {
                var geoCodeRelationship = geoControl.getGeoCodeRelationship(cityGeoCode, countyGeoCode);
                
                if(geoCodeRelationship == null) {
                    geoControl.createGeoCodeRelationship(cityGeoCode, countyGeoCode, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.DuplicateGeoCodeRelationship.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return null;
    }
    
}
