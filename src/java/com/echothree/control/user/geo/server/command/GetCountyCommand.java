// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.geo.remote.form.GetCountyForm;
import com.echothree.control.user.geo.remote.result.GeoResultFactory;
import com.echothree.control.user.geo.remote.result.GetCountyResult;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeRelationship;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GetCountyCommand
        extends BaseSimpleCommand<GetCountyForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.County.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("StateGeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CountyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CountyNumber", FieldType.NUMBER_3, Boolean.FALSE, null, null)
                ));
    }
    
    /** Creates a new instance of GetCountyCommand */
    public GetCountyCommand(UserVisitPK userVisitPK, GetCountyForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetCountyResult result = GeoResultFactory.getGetCountyResult();
        String countyName = form.getCountyName();
        String countyNumber = form.getCountyNumber();
        int parameterCount = (countyName == null? 0: 1) + (countyNumber == null? 0: 1);
        
        if(parameterCount == 1) {
            GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
            BasePK createdBy = getPartyPK();
            
            String stateGeoCodeName = form.getStateGeoCodeName();
            GeoCode stateGeoCode = geoControl.getGeoCodeByName(stateGeoCodeName);
            
            GeoCodeAliasType stateGeoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(stateGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_POSTAL_2_LETTER);
            GeoCodeAlias stateGeoCodeAlias = geoControl.getGeoCodeAlias(stateGeoCode, stateGeoCodeAliasType);
            String statePostal2Letter = stateGeoCodeAlias.getAlias();
            
            GeoCodeType countryGeoCodeType = geoControl.getGeoCodeTypeByName(GeoConstants.GeoCodeType_COUNTRY);
            GeoCode countryGeoCode = null;
            Collection stateRelationships = geoControl.getGeoCodeRelationshipsByFromGeoCode(stateGeoCode);
            for(Iterator iter = stateRelationships.iterator(); iter.hasNext();) {
                GeoCodeRelationship geoCodeRelationship = (GeoCodeRelationship)iter.next();
                GeoCode toGeoCode = geoCodeRelationship.getToGeoCode();
                if(toGeoCode.getLastDetail().getGeoCodeType().equals(countryGeoCodeType)) {
                    countryGeoCode = toGeoCode;
                    break;
                }
            }
            
            GeoCodeAliasType countryGeoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
            GeoCodeAlias countryGeoCodeAlias = geoControl.getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
            String countryIso2Letter = countryGeoCodeAlias.getAlias();
            
            String geoCodeScopeName = new StringBuilder().append(countryIso2Letter).append("_").append(statePostal2Letter).append("_COUNTIES").toString();
            GeoCodeScope geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);
            if(geoCodeScope == null) {
                geoCodeScope = geoControl.createGeoCodeScope(geoCodeScopeName, Boolean.FALSE, 0, createdBy);
            }
            
            if(geoCodeScope != null) {
                GeoCodeType geoCodeType = geoControl.getGeoCodeTypeByName(GeoConstants.GeoCodeType_COUNTY);
                GeoCodeAlias geoCodeAlias = null;
                
                if(countyName != null) {
                    GeoCodeAliasType geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_COUNTY_NAME);
                    geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, countyName);
                    
                    if(geoCodeAlias == null) {
                        addExecutionError(ExecutionErrors.UnknownCountyName.name(), countyName);
                    }
                } else if(countyNumber != null) {
                    GeoCodeAliasType geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_COUNTY_NUMBER);
                    geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, countyNumber);
                    
                    if(geoCodeAlias == null) {
                        addExecutionError(ExecutionErrors.UnknownCountyNumber.name());
                    }
                }
                
                if(geoCodeAlias != null) {
                    GeoCode geoCode = geoCodeAlias.getGeoCode();
                    
                    result.setEntityRef(geoCode.getPrimaryKey().getEntityRef());
                    result.setGeoCodeName(geoCode.getLastDetail().getGeoCodeName());
                    
                    sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.READ.name(), null, null, createdBy);
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
