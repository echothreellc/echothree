// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.geo.common.form.CreateCountyForm;
import com.echothree.control.user.geo.common.result.CreateCountyResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeRelationship;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
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

public class CreateCountyCommand
        extends BaseSimpleCommand<CreateCountyForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.County.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("StateGeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CountyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CountyNumber", FieldType.NUMBER_3, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateCountyCommand */
    public CreateCountyCommand(UserVisitPK userVisitPK, CreateCountyForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CreateCountyResult result = GeoResultFactory.getCreateCountyResult();
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        BasePK createdBy = getPartyPK();
        GeoCode geoCode;
        
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
            geoCodeScope = geoControl.createGeoCodeScope(geoCodeScopeName, Boolean.FALSE, 0, getPartyPK());
        }
        
        GeoCodeType geoCodeType = geoControl.getGeoCodeTypeByName(GeoConstants.GeoCodeType_COUNTY);
        GeoCodeAliasType geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_COUNTY_NAME);
        String countyName = form.getCountyName();
        GeoCodeAlias geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, countyName);
        
        if(geoCodeAlias == null) {
            String geoCodeName = SequenceLogic.getInstance().getNextSequenceValue(null, SequenceConstants.SequenceType_GEO_CODE);
            String countyNumber = form.getCountyNumber();
            Boolean isDefault = Boolean.valueOf(form.getIsDefault());
            Integer sortOrder = Integer.valueOf(form.getSortOrder());
            String description = form.getDescription();
            
            geoCode = geoControl.createGeoCode(geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder, createdBy);
            geoControl.createGeoCodeRelationship(geoCode, stateGeoCode, createdBy);
            geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, countyName, createdBy);
            geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_COUNTY_NUMBER);
            geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, countyNumber, createdBy);
            
            if(description != null) {
                Language language = getPreferredLanguage();
                
                geoControl.createGeoCodeDescription(geoCode, language, description, createdBy);
            }
        } else {
            geoCode = geoCodeAlias.getGeoCode();
        }
        
        if(geoCode != null) {
            result.setEntityRef(geoCode.getPrimaryKey().getEntityRef());
            result.setGeoCodeName(geoCode.getLastDetail().getGeoCodeName());
        }
        
        return result;
    }
    
}
