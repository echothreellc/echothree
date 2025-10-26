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

import com.echothree.control.user.geo.common.form.GetStateForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.server.GeoDebugFlags;
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
import org.apache.commons.logging.Log;

public class GetStateCommand
        extends BaseSingleEntityCommand<GeoCode, GetStateForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.CUSTOMER.name(), null),
                new PartyTypeDefinition(PartyTypes.VENDOR.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.State.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryGeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("StateName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Postal2Letter", FieldType.UPPER_LETTER_2, false, null, null)
                ));
    }
    
    Log log = null;
    
    /** Creates a new instance of GetStateCommand */
    public GetStateCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
        
        if(GeoDebugFlags.GetStateCommand) {
            log = getLog();
        }
    }

    @Override
    protected GeoCode getEntity() {
        GeoCode geoCode = null;
        var stateName = form.getStateName();
        var postal2Letter = form.getPostal2Letter();
        var parameterCount = (stateName == null ? 0 : 1) + (postal2Letter == null ? 0 : 1);

        if(GeoDebugFlags.GetStateCommand) {
            log.info("parameterCount = " + parameterCount);
        }

        if(parameterCount == 1) {
            var geoControl = Session.getModelController(GeoControl.class);

            var countryGeoCodeName = form.getCountryGeoCodeName();
            var countryGeoCode = geoControl.getGeoCodeByName(countryGeoCodeName);

            var countryGeoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoCodeAliasTypes.ISO_2_LETTER.name());
            var countryGeoCodeAlias = geoControl.getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
            var countryIso2Letter = countryGeoCodeAlias.getAlias();

            var geoCodeScopeName = countryIso2Letter + "_STATES";
            var geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);

            if(geoCodeScope != null) {
                var geoCodeType = geoControl.getGeoCodeTypeByName(GeoCodeTypes.STATE.name());
                GeoCodeAlias geoCodeAlias;

                if(stateName != null) {
                    if(GeoDebugFlags.GetStateCommand) {
                        log.info("lookup will be by stateName");
                    }

                    var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.STATE_NAME.name());
                    geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, stateName);

                    if(geoCodeAlias == null) {
                        addExecutionError(ExecutionErrors.UnknownStateName.name(), stateName);
                    }
                } else {
                    if(GeoDebugFlags.GetStateCommand) {
                        log.info("lookup will be by postal2Letter");
                    }

                    var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.POSTAL_2_LETTER.name());
                    geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, postal2Letter);

                    if(geoCodeAlias == null) {
                        addExecutionError(ExecutionErrors.UnknownStatePostal2Letter.name());
                    }
                }

                if(geoCodeAlias != null) {
                    geoCode = geoCodeAlias.getGeoCode();

                    sendEvent(geoCode.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
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
        var result = GeoResultFactory.getGetStateResult();

        if(entity != null) {
            var geoControl = Session.getModelController(GeoControl.class);

            result.setState(geoControl.getStateTransfer(getUserVisit(), entity));
        }

        return result;
    }

}
