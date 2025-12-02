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

import com.echothree.control.user.geo.common.form.CreateCountryForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.geo.common.GeoCodeAliasTypes;
import com.echothree.model.control.geo.common.GeoCodeScopes;
import com.echothree.model.control.geo.common.GeoCodeTypes;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.geo.server.logic.GeoCodeLogic;
import com.echothree.model.control.geo.server.logic.GeoCodeScopeLogic;
import com.echothree.model.control.geo.server.logic.GeoCodeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreateCountryCommand
        extends BaseSimpleCommand<CreateCountryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Country.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Iso3Number", FieldType.NUMBER_3, true, null, null),
                new FieldDefinition("Iso3Letter", FieldType.UPPER_LETTER_3, true, null, null),
                new FieldDefinition("Iso2Letter", FieldType.UPPER_LETTER_2, true, null, null),
                new FieldDefinition("TelephoneCode", FieldType.STRING, false, 1L, 5L),
                new FieldDefinition("AreaCodePattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("AreaCodeRequired", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("AreaCodeExample", FieldType.STRING, false, 1L, 5L),
                new FieldDefinition("TelephoneNumberPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("TelephoneNumberExample", FieldType.STRING, false, 1L, 25L),
                new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CityRequired", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("CityGeoCodeRequired", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("StateRequired", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("StateGeoCodeRequired", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("PostalCodePattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("PostalCodeRequired", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("PostalCodeGeoCodeRequired", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("PostalCodeLength", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("PostalCodeGeoCodeLength", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("PostalCodeExample", FieldType.STRING, false, 1L, 15L),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateCountryCommand */
    public CreateCountryCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var geoCodeTypeLogic = GeoCodeTypeLogic.getInstance();
        var result = GeoResultFactory.getCreateCountryResult();
        var geoControl = Session.getModelController(GeoControl.class);
        GeoCode geoCode = null;
        var geoCodeType = geoCodeTypeLogic.getGeoCodeTypeByName(this, GeoCodeTypes.COUNTRY.name());

        if(!hasExecutionErrors()) {
            var geoCodeScopeLogic = GeoCodeScopeLogic.getInstance();
            var geoCodeScope = geoCodeScopeLogic.getGeoCodeScopeByName(this, GeoCodeScopes.COUNTRIES.name());

            if(!hasExecutionErrors()) {
                var geoCodeLogic = GeoCodeLogic.getInstance();
                var iso3Number = form.getIso3Number();

                geoCode = geoCodeLogic.getGeoCodeByAlias(this, geoCodeType, geoCodeScope, GeoCodeAliasTypes.ISO_3_NUMBER.name(), iso3Number);

                if(geoCode == null && !hasExecutionErrors()) {
                    var iso3Letter = form.getIso3Letter();

                    geoCode = geoCodeLogic.getGeoCodeByAlias(this, geoCodeType, geoCodeScope, GeoCodeAliasTypes.ISO_3_LETTER.name(), iso3Letter);

                    if(geoCode == null && !hasExecutionErrors()) {
                        var iso2Letter = form.getIso2Letter();

                        geoCode = geoCodeLogic.getGeoCodeByAlias(this, geoCodeType, geoCodeScope, GeoCodeAliasTypes.ISO_2_LETTER.name(), iso2Letter);

                        if(geoCode == null && !hasExecutionErrors()) {
                            var countryName = form.getCountryName();

                            geoCode = geoCodeLogic.getGeoCodeByAlias(this, geoCodeType, geoCodeScope, GeoCodeAliasTypes.COUNTRY_NAME.name(), countryName);

                            if(geoCode == null && !hasExecutionErrors()) {
                                var contactControl = Session.getModelController(ContactControl.class);
                                var postalAddressFormatName = form.getPostalAddressFormatName();
                                var postalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName);

                                if(postalAddressFormat != null) {
                                    BasePK createdBy = getPartyPK();
                                    var geoCodeName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(null, SequenceTypes.GEO_CODE.name());
                                    var telephoneCode = form.getTelephoneCode();
                                    var areaCodePattern = form.getAreaCodePattern();
                                    var areaCodeRequired = Boolean.valueOf(form.getAreaCodeRequired());
                                    var areaCodeExample = form.getAreaCodeExample();
                                    var telephoneNumberPattern = form.getTelephoneNumberPattern();
                                    var telephoneNumberExample = form.getTelephoneNumberExample();
                                    var cityRequired = Boolean.valueOf(form.getCityRequired());
                                    var cityGeoCodeRequired = Boolean.valueOf(form.getCityGeoCodeRequired());
                                    var stateRequired = Boolean.valueOf(form.getStateRequired());
                                    var stateGeoCodeRequired = Boolean.valueOf(form.getStateGeoCodeRequired());
                                    var postalCodePattern = form.getPostalCodePattern();
                                    var postalCodeRequired = Boolean.valueOf(form.getPostalCodeRequired());
                                    var postalCodeGeoCodeRequired = Boolean.valueOf(form.getPostalCodeGeoCodeRequired());
                                    var strPostalCodeLength = form.getPostalCodeLength();
                                    var postalCodeLength = strPostalCodeLength == null ? null : Integer.valueOf(strPostalCodeLength);
                                    var strPostalCodeGeoCodeLength = form.getPostalCodeGeoCodeLength();
                                    var postalCodeGeoCodeLength = strPostalCodeGeoCodeLength == null ? null : Integer.valueOf(strPostalCodeGeoCodeLength);
                                    var postalCodeExample = form.getPostalCodeExample();
                                    var isDefault = Boolean.valueOf(form.getIsDefault());
                                    var sortOrder = Integer.valueOf(form.getSortOrder());
                                    var description = form.getDescription();

                                    geoCode = geoControl.createGeoCode(geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder, createdBy);
                                    geoControl.createGeoCodeCountry(geoCode, telephoneCode, areaCodePattern, areaCodeRequired, areaCodeExample,
                                            telephoneNumberPattern, telephoneNumberExample, postalAddressFormat, cityRequired, cityGeoCodeRequired,
                                            stateRequired, stateGeoCodeRequired, postalCodePattern, postalCodeRequired, postalCodeGeoCodeRequired,
                                            postalCodeLength, postalCodeGeoCodeLength, postalCodeExample, createdBy);

                                    var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.ISO_3_NUMBER.name());
                                    geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, iso3Number, createdBy);
                                    geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.ISO_3_LETTER.name());
                                    geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, iso3Letter, createdBy);
                                    geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.ISO_2_LETTER.name());
                                    geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, iso2Letter, createdBy);
                                    geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoCodeAliasTypes.COUNTRY_NAME.name());
                                    geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, countryName, createdBy);

                                    if(description != null) {
                                        var language = getPreferredLanguage();

                                        geoControl.createGeoCodeDescription(geoCode, language, description, createdBy);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownPostalAddressFormatName.name(), postalAddressFormatName);
                                }
                            } else {
                                if(geoCode != null) {
                                    addExecutionError(ExecutionErrors.DuplicateCountryName.name(), countryName);
                                }
                            }
                        } else {
                            if(geoCode != null) {
                                addExecutionError(ExecutionErrors.DuplicateIso2Letter.name(), iso2Letter);
                            }
                        }
                    } else {
                        if(geoCode != null) {
                            addExecutionError(ExecutionErrors.DuplicateIso3Letter.name(), iso3Letter);
                        }
                    }
                } else {
                    if(geoCode != null) {
                        addExecutionError(ExecutionErrors.DuplicateIso3Number.name(), iso3Number);
                    }
                }
            }
        }

        if(geoCode != null) {
            result.setEntityRef(geoCode.getPrimaryKey().getEntityRef());
            result.setGeoCodeName(geoCode.getLastDetail().getGeoCodeName());
        }
        
        return result;
    }
    
}
