// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.geo.common.form.CreateStateForm;
import com.echothree.control.user.geo.common.result.CreateStateResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
import java.util.Collections;
import java.util.List;

public class CreateStateCommand
        extends BaseSimpleCommand<CreateStateForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.State.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryGeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("StateName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Postal2Letter", FieldType.UPPER_LETTER_2, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateStateCommand */
    public CreateStateCommand(UserVisitPK userVisitPK, CreateStateForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CreateStateResult result = GeoResultFactory.getCreateStateResult();
        var geoControl = Session.getModelController(GeoControl.class);
        GeoCode geoCode = null;
        
        String countryGeoCodeName = form.getCountryGeoCodeName();
        GeoCode countryGeoCode = geoControl.getGeoCodeByName(countryGeoCodeName);
        
        GeoCodeAliasType countryGeoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        GeoCodeAlias countryGeoCodeAlias = geoControl.getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        String countryIso2Letter = countryGeoCodeAlias.getAlias();
        
        String geoCodeScopeName = new StringBuilder().append(countryIso2Letter).append("_STATES").toString();
        GeoCodeScope geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);
        
        if(geoCodeScope != null) {
            GeoCodeType geoCodeType = geoControl.getGeoCodeTypeByName(GeoConstants.GeoCodeType_STATE);
            GeoCodeAliasType geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_POSTAL_2_LETTER);
            String postal2Letter = form.getPostal2Letter();
            GeoCodeAlias geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, postal2Letter);
            
            if(geoCodeAlias == null) {
                BasePK createdBy = getPartyPK();
                String geoCodeName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(null, SequenceTypes.GEO_CODE.name());
                String stateName = form.getStateName();
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                
                geoCode = geoControl.createGeoCode(geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder, createdBy);
                geoControl.createGeoCodeRelationship(geoCode, countryGeoCode, createdBy);
                geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, postal2Letter, createdBy);
                geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_STATE_NAME);
                geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, stateName, createdBy);
                
                if(description != null) {
                    Language language = getPreferredLanguage();
                    
                    geoControl.createGeoCodeDescription(geoCode, language, description, createdBy);
                }
            } else {
                geoCode = geoCodeAlias.getGeoCode();
            }
        } // TODO: error, unknown geoCodeScopeName
        
        if(geoCode != null) {
            result.setEntityRef(geoCode.getPrimaryKey().getEntityRef());
            result.setGeoCodeName(geoCode.getLastDetail().getGeoCodeName());
        }
        
        return result;
    }
    
}
