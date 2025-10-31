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

import com.echothree.control.user.geo.common.form.CreateStateForm;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
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
    public CreateStateCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = GeoResultFactory.getCreateStateResult();
        var geoControl = Session.getModelController(GeoControl.class);
        GeoCode geoCode = null;

        var countryGeoCodeName = form.getCountryGeoCodeName();
        var countryGeoCode = geoControl.getGeoCodeByName(countryGeoCodeName);

        var countryGeoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoCodeAliasTypes.ISO_2_LETTER.name());
        var countryGeoCodeAlias = geoControl.getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();

        var geoCodeScopeName = countryIso2Letter + "_STATES";
        var geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);
        
        if(geoCodeScope != null) {
            var geoCodeType = geoControl.getGeoCodeTypeByName(GeoCodeTypes.STATE.name());
            var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.POSTAL_2_LETTER.name());
            var postal2Letter = form.getPostal2Letter();
            var geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, postal2Letter);
            
            if(geoCodeAlias == null) {
                BasePK createdBy = getPartyPK();
                var geoCodeName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(null, SequenceTypes.GEO_CODE.name());
                var stateName = form.getStateName();
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                
                geoCode = geoControl.createGeoCode(geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder, createdBy);
                geoControl.createGeoCodeRelationship(geoCode, countryGeoCode, createdBy);
                geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, postal2Letter, createdBy);
                geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.STATE_NAME.name());
                geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, stateName, createdBy);
                
                if(description != null) {
                    var language = getPreferredLanguage();
                    
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
