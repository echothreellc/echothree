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

import com.echothree.control.user.geo.common.form.CreateCountyForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.geo.common.GeoCodeAliasTypes;
import com.echothree.model.control.geo.common.GeoCodeTypes;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeRelationship;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
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

public class CreateCountyCommand
        extends BaseSimpleCommand<CreateCountyForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.County.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("StateGeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CountyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CountyNumber", FieldType.NUMBER_3, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateCountyCommand */
    public CreateCountyCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = GeoResultFactory.getCreateCountyResult();
        var geoControl = Session.getModelController(GeoControl.class);
        BasePK createdBy = getPartyPK();
        GeoCode geoCode;

        var stateGeoCodeName = form.getStateGeoCodeName();
        var stateGeoCode = geoControl.getGeoCodeByName(stateGeoCodeName);

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
        if(geoCodeScope == null) {
            geoCodeScope = geoControl.createGeoCodeScope(geoCodeScopeName, false, 0, getPartyPK());
        }

        var geoCodeType = geoControl.getGeoCodeTypeByName(GeoCodeTypes.COUNTY.name());
        var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.COUNTY_NAME.name());
        var countyName = form.getCountyName();
        var geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, countyName);
        
        if(geoCodeAlias == null) {
            var geoCodeName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(null, SequenceTypes.GEO_CODE.name());
            var countyNumber = form.getCountyNumber();
            var isDefault = Boolean.valueOf(form.getIsDefault());
            var sortOrder = Integer.valueOf(form.getSortOrder());
            var description = form.getDescription();
            
            geoCode = geoControl.createGeoCode(geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder, createdBy);
            geoControl.createGeoCodeRelationship(geoCode, stateGeoCode, createdBy);
            geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, countyName, createdBy);
            geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.COUNTY_NUMBER.name());
            geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, countyNumber, createdBy);
            
            if(description != null) {
                var language = getPreferredLanguage();
                
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
