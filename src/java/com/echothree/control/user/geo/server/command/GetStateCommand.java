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

import com.echothree.control.user.geo.common.form.GetStateForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.result.GetStateResult;
import com.echothree.control.user.geo.server.GeoDebugFlags;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
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
import org.apache.commons.logging.Log;

public class GetStateCommand
        extends BaseSimpleCommand<GetStateForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_CUSTOMER, null),
                new PartyTypeDefinition(PartyConstants.PartyType_VENDOR, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.State.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryGeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("StateName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Postal2Letter", FieldType.UPPER_LETTER_2, Boolean.FALSE, null, null)
                ));
    }
    
    Log log = null;
    
    /** Creates a new instance of GetStateCommand */
    public GetStateCommand(UserVisitPK userVisitPK, GetStateForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
        
        if(GeoDebugFlags.GetStateCommand) {
            log = getLog();
        }
    }
    
    @Override
    protected BaseResult execute() {
        GetStateResult result = GeoResultFactory.getGetStateResult();
        String stateName = form.getStateName();
        String postal2Letter = form.getPostal2Letter();
        int parameterCount = (stateName == null? 0: 1) + (postal2Letter == null? 0: 1);
        
        if(GeoDebugFlags.GetStateCommand) {
            log.info("parameterCount = " + parameterCount);
        }
        
        if(parameterCount == 1) {
            var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
            
            String countryGeoCodeName = form.getCountryGeoCodeName();
            GeoCode countryGeoCode = geoControl.getGeoCodeByName(countryGeoCodeName);
            
            GeoCodeAliasType countryGeoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
            GeoCodeAlias countryGeoCodeAlias = geoControl.getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
            String countryIso2Letter = countryGeoCodeAlias.getAlias();
            
            String geoCodeScopeName = new StringBuilder().append(countryIso2Letter).append("_STATES").toString();
            GeoCodeScope geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);
            
            if(geoCodeScope != null) {
                GeoCodeType geoCodeType = geoControl.getGeoCodeTypeByName(GeoConstants.GeoCodeType_STATE);
                GeoCodeAlias geoCodeAlias = null;
                
                if(stateName != null) {
                    if(GeoDebugFlags.GetStateCommand) {
                        log.info("lookup will be by stateName");
                    }
                    
                    GeoCodeAliasType geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_STATE_NAME);
                    geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, stateName);
                    
                    if(geoCodeAlias == null) {
                        addExecutionError(ExecutionErrors.UnknownStateName.name(), stateName);
                    }
                } else if(postal2Letter != null) {
                    if(GeoDebugFlags.GetStateCommand) {
                        log.info("lookup will be by postal2Letter");
                    }
                    
                    GeoCodeAliasType geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_POSTAL_2_LETTER);
                    geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, postal2Letter);
                    
                    if(geoCodeAlias == null) {
                        addExecutionError(ExecutionErrors.UnknownStatePostal2Letter.name());
                    }
                }
                
                if(geoCodeAlias != null) {
                    GeoCode geoCode = geoCodeAlias.getGeoCode();
                    
                    result.setEntityRef(geoCode.getPrimaryKey().getEntityRef());
                    result.setGeoCodeName(geoCode.getLastDetail().getGeoCodeName());
                    
                    sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGeoCodeScopeName.name(), geoCodeScopeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
