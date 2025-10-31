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

import com.echothree.control.user.geo.common.form.GetCountyForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.geo.common.GeoCodeAliasTypes;
import com.echothree.model.control.geo.common.GeoCodeTypes;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetCountyCommand
        extends BaseSingleEntityCommand<GeoCode, GetCountyForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.CUSTOMER.name(), null),
                new PartyTypeDefinition(PartyTypes.VENDOR.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.County.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("StateGeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CountyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CountyNumber", FieldType.NUMBER_3, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetCountyCommand */
    public GetCountyCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected GeoCode getEntity() {
        GeoCode geoCode = null;
        var countyName = form.getCountyName();
        var countyNumber = form.getCountyNumber();
        var parameterCount = (countyName == null ? 0 : 1) + (countyNumber == null ? 0 : 1);

        if(parameterCount == 1) {
            var geoControl = Session.getModelController(GeoControl.class);
            var createdBy = getPartyPK();

            var stateGeoCodeName = form.getStateGeoCodeName();
            var stateGeoCode = geoControl.getGeoCodeByName(stateGeoCodeName);

            var stateGeoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(stateGeoCode.getLastDetail().getGeoCodeType(), GeoCodeAliasTypes.POSTAL_2_LETTER.name());
            var stateGeoCodeAlias = geoControl.getGeoCodeAlias(stateGeoCode, stateGeoCodeAliasType);
            var statePostal2Letter = stateGeoCodeAlias.getAlias();

            var countryGeoCodeType = geoControl.getGeoCodeTypeByName(GeoCodeTypes.COUNTRY.name());
            GeoCode countryGeoCode = null;
            var stateRelationships = geoControl.getGeoCodeRelationshipsByFromGeoCode(stateGeoCode);
            for(var geoCodeRelationship : stateRelationships) {
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
            if(geoCodeScope == null) {
                geoCodeScope = geoControl.createGeoCodeScope(geoCodeScopeName, false, 0, createdBy);
            }

            if(geoCodeScope != null) {
                var geoCodeType = geoControl.getGeoCodeTypeByName(GeoCodeTypes.COUNTY.name());
                GeoCodeAlias geoCodeAlias;

                if(countyName != null) {
                    var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.COUNTY_NAME.name());
                    geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, countyName);

                    if(geoCodeAlias == null) {
                        addExecutionError(ExecutionErrors.UnknownCountyName.name(), countyName);
                    }
                } else {
                    var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.COUNTY_NUMBER.name());
                    geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, countyNumber);

                    if(geoCodeAlias == null) {
                        addExecutionError(ExecutionErrors.UnknownCountyNumber.name());
                    }
                }

                if(geoCodeAlias != null) {
                    geoCode = geoCodeAlias.getGeoCode();

                    sendEvent(geoCode.getPrimaryKey(), EventTypes.READ, null, null, createdBy);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGeoCodeScopeName.name(), geoCodeScopeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return geoCode;
    }

    @Override
    protected BaseResult getResult(GeoCode entity) {
        var result = GeoResultFactory.getGetCountyResult();

        if(entity != null) {
            var geoControl = Session.getModelController(GeoControl.class);

            result.setCounty(geoControl.getCountyTransfer(getUserVisit(), entity));
        }

        return result;
    }

}
