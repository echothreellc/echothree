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

import com.echothree.control.user.geo.common.form.GetZipCodeForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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

public class GetZipCodeCommand
        extends BaseSingleEntityCommand<GeoCode, GetZipCodeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_CUSTOMER, null),
                new PartyTypeDefinition(PartyConstants.PartyType_VENDOR, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ZipCode.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryGeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ZipCodeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetZipCodeCommand */
    public GetZipCodeCommand(UserVisitPK userVisitPK, GetZipCodeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected GeoCode getEntity() {
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GeoCode geoCode = null;

        var countryGeoCodeName = form.getCountryGeoCodeName();
        var countryGeoCode = geoControl.getGeoCodeByName(countryGeoCodeName);

        var countryGeoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(),
                GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        var countryGeoCodeAlias = geoControl.getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();

        var geoCodeScopeName = countryIso2Letter + "_ZIP_CODES";
        var geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);

        if(geoCodeScope != null) {
            var geoCodeType = geoControl.getGeoCodeTypeByName(GeoConstants.GeoCodeType_ZIP_CODE);
            var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_ZIP_CODE);
            var zipCodeName = form.getZipCodeName();
            var geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, zipCodeName);

            if(geoCodeAlias != null) {
                geoCode = geoCodeAlias.getGeoCode();

                sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownZipCodeName.name(), zipCodeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeScopeName.name(), geoCodeScopeName);
        }

        return geoCode;
    }

    @Override
    protected BaseResult getTransfer(GeoCode entity) {
        var result = GeoResultFactory.getGetZipCodeResult();

        if(entity != null) {
            var geoControl = (GeoControl)Session.getModelController(GeoControl.class);

            result.setPostalCode(geoControl.getPostalCodeTransfer(getUserVisit(), entity));
        }

        return result;
    }

}
