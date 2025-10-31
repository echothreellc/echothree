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

import com.echothree.control.user.geo.common.form.GetCountryForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.server.GeoDebugFlags;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.geo.common.GeoCodeAliasTypes;
import com.echothree.model.control.geo.common.GeoCodeScopes;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetCountryCommand
        extends BaseSingleEntityCommand<GeoCode, GetCountryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.CUSTOMER.name(), null),
                new PartyTypeDefinition(PartyTypes.VENDOR.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Country.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Iso3Number", FieldType.NUMBER_3, false, null, null),
                new FieldDefinition("Iso3Letter", FieldType.UPPER_LETTER_3, false, null, null),
                new FieldDefinition("Iso2Letter", FieldType.UPPER_LETTER_2, false, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    Log log = null;
    
    /** Creates a new instance of GetCountryCommand */
    public GetCountryCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
        
        if(GeoDebugFlags.GetCountryCommand) {
            log = getLog();
        }
    }

    @Override
    protected GeoCode getEntity() {
        GeoCode geoCode = null;
        var geoCodeName = form.getGeoCodeName();
        var countryName = form.getCountryName();
        var iso3Number = form.getIso3Number();
        var iso3Letter = form.getIso3Letter();
        var iso2Letter = form.getIso2Letter();
        var alias = form.getAlias();
        var parameterCount = (geoCodeName == null ? 0 : 1) + (countryName == null ? 0 : 1) + (iso3Number == null ? 0 : 1) + (iso3Letter == null ? 0 : 1)
                + (iso2Letter == null ? 0 : 1) + (alias == null ? 0 : 1);

        if(GeoDebugFlags.GetCountryCommand) {
            log.info("parameterCount = " + parameterCount);
        }

        if(parameterCount < 2) {
            var geoControl = Session.getModelController(GeoControl.class);
            var geoCodeScope = geoControl.getGeoCodeScopeByName(GeoCodeScopes.COUNTRIES.name());

            if(parameterCount == 0) {
                geoCode = geoControl.getDefaultGeoCode(geoCodeScope);
            } else {
                var geoCodeType = geoControl.getGeoCodeTypeByName(GeoCodeTypes.COUNTRY.name());

                if(geoCodeName != null) {
                    if(GeoDebugFlags.GetCountryCommand) {
                        log.info("lookup will be by geoCodeName");
                    }

                    geoCode = geoControl.getGeoCodeByName(geoCodeName);

                    if(geoCode != null) {
                        var geoCodeDetail = geoCode.getLastDetail();

                        if(!geoCodeDetail.getGeoCodeType().equals(geoCodeType)) {
                            addExecutionError(ExecutionErrors.InvalidGeoCodeType.name(), geoCodeDetail.getGeoCodeType().getLastDetail().getGeoCodeTypeName());
                        } else if(!geoCodeDetail.getGeoCodeScope().equals(geoCodeScope)) {
                            addExecutionError(ExecutionErrors.InvalidGeoCodeScope.name(), geoCodeDetail.getGeoCodeScope().getLastDetail().getGeoCodeScopeName());
                        }

                        if(hasExecutionErrors()) {
                            geoCode = null;
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
                    }
                } else if(alias != null) {
                    if(GeoDebugFlags.GetCountryCommand) {
                        log.info("lookup will be by alias");
                    }

                    geoCode = geoControl.getCountryByAlias(alias);

                    if(geoCode == null) {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeAlias.name(), alias);
                    }
                } else {
                    GeoCodeAlias geoCodeAlias = null;

                    if(countryName != null) {
                        if(GeoDebugFlags.GetCountryCommand) {
                            log.info("lookup will be by countryName");
                        }

                        var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.COUNTRY_NAME.name());
                        geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, countryName);

                        if(geoCodeAlias == null) {
                            addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
                        }
                    } else if(iso3Number != null) {
                        if(GeoDebugFlags.GetCountryCommand) {
                            log.info("lookup will be by iso3Number");
                        }

                        var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.ISO_3_NUMBER.name());
                        geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, iso3Number);

                        if(geoCodeAlias == null) {
                            addExecutionError(ExecutionErrors.UnknownCountryIso3Number.name(), iso3Number);
                        }
                    } else if(iso3Letter != null) {
                        if(GeoDebugFlags.GetCountryCommand) {
                            log.info("lookup will be by iso3Letter");
                        }

                        var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.ISO_3_LETTER.name());
                        geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, iso3Letter);

                        if(geoCodeAlias == null) {
                            addExecutionError(ExecutionErrors.UnknownCountryIso3Letter.name(), iso3Letter);
                        }
                    } else if(iso2Letter != null) {
                        if(GeoDebugFlags.GetCountryCommand) {
                            log.info("lookup will be by iso2Letter");
                        }

                        var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.ISO_2_LETTER.name());
                        geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, iso2Letter);

                        if(geoCodeAlias == null) {
                            addExecutionError(ExecutionErrors.UnknownCountryIso2Letter.name(), iso2Letter);
                        }
                    }

                    if(geoCodeAlias != null) {
                        geoCode = geoCodeAlias.getGeoCode();
                    }
                }
            }

            if(geoCode != null) {
                sendEvent(geoCode.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return geoCode;
    }

    @Override
    protected BaseResult getResult(GeoCode entity) {
        var result = GeoResultFactory.getGetCountryResult();

        if(entity != null) {
            var geoControl = Session.getModelController(GeoControl.class);

            result.setCountry(geoControl.getCountryTransfer(getUserVisit(), entity));
        }

        return result;
    }

}
