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

import com.echothree.control.user.geo.common.form.CreateCountryForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.geo.server.logic.GeoCodeLogic;
import com.echothree.model.control.geo.server.logic.GeoCodeScopeLogic;
import com.echothree.model.control.geo.server.logic.GeoCodeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateCountryCommand */
    public CreateCountryCommand(UserVisitPK userVisitPK, CreateCountryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var geoCodeTypeLogic = GeoCodeTypeLogic.getInstance();
        var result = GeoResultFactory.getCreateCountryResult();
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GeoCode geoCode = null;
        GeoCodeType geoCodeType = geoCodeTypeLogic.getGeoCodeTypeByName(this, GeoConstants.GeoCodeType_COUNTRY);

        if(!hasExecutionErrors()) {
            var geoCodeScopeLogic = GeoCodeScopeLogic.getInstance();
            GeoCodeScope geoCodeScope = geoCodeScopeLogic.getGeoCodeScopeByName(this, GeoConstants.GeoCodeScope_COUNTRIES);

            if(!hasExecutionErrors()) {
                var geoCodeLogic = GeoCodeLogic.getInstance();
                String iso3Number = form.getIso3Number();

                geoCode = geoCodeLogic.getGeoCodeByAlias(this, geoCodeType, geoCodeScope, GeoConstants.GeoCodeAliasType_ISO_3_NUMBER, iso3Number);

                if(geoCode == null && !hasExecutionErrors()) {
                    String iso3Letter = form.getIso3Letter();

                    geoCode = geoCodeLogic.getGeoCodeByAlias(this, geoCodeType, geoCodeScope, GeoConstants.GeoCodeAliasType_ISO_3_LETTER, iso3Letter);

                    if(geoCode == null && !hasExecutionErrors()) {
                        String iso2Letter = form.getIso2Letter();

                        geoCode = geoCodeLogic.getGeoCodeByAlias(this, geoCodeType, geoCodeScope, GeoConstants.GeoCodeAliasType_ISO_2_LETTER, iso2Letter);

                        if(geoCode == null && !hasExecutionErrors()) {
                            String countryName = form.getCountryName();

                            geoCode = geoCodeLogic.getGeoCodeByAlias(this, geoCodeType, geoCodeScope, GeoConstants.GeoCodeAliasType_COUNTRY_NAME, countryName);

                            if(geoCode == null && !hasExecutionErrors()) {
                                var contactControl = (ContactControl)Session.getModelController(ContactControl.class);
                                String postalAddressFormatName = form.getPostalAddressFormatName();
                                PostalAddressFormat postalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName);

                                if(postalAddressFormat != null) {
                                    BasePK createdBy = getPartyPK();
                                    String geoCodeName = SequenceLogic.getInstance().getNextSequenceValue(null, SequenceTypes.GEO_CODE.toString());
                                    String telephoneCode = form.getTelephoneCode();
                                    String areaCodePattern = form.getAreaCodePattern();
                                    Boolean areaCodeRequired = Boolean.valueOf(form.getAreaCodeRequired());
                                    String areaCodeExample = form.getAreaCodeExample();
                                    String telephoneNumberPattern = form.getTelephoneNumberPattern();
                                    String telephoneNumberExample = form.getTelephoneNumberExample();
                                    Boolean cityRequired = Boolean.valueOf(form.getCityRequired());
                                    Boolean cityGeoCodeRequired = Boolean.valueOf(form.getCityGeoCodeRequired());
                                    Boolean stateRequired = Boolean.valueOf(form.getStateRequired());
                                    Boolean stateGeoCodeRequired = Boolean.valueOf(form.getStateGeoCodeRequired());
                                    String postalCodePattern = form.getPostalCodePattern();
                                    Boolean postalCodeRequired = Boolean.valueOf(form.getPostalCodeRequired());
                                    Boolean postalCodeGeoCodeRequired = Boolean.valueOf(form.getPostalCodeGeoCodeRequired());
                                    String strPostalCodeLength = form.getPostalCodeLength();
                                    Integer postalCodeLength = strPostalCodeLength == null ? null : Integer.valueOf(strPostalCodeLength);
                                    String strPostalCodeGeoCodeLength = form.getPostalCodeGeoCodeLength();
                                    Integer postalCodeGeoCodeLength = strPostalCodeGeoCodeLength == null ? null : Integer.valueOf(strPostalCodeGeoCodeLength);
                                    String postalCodeExample = form.getPostalCodeExample();
                                    Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                                    Integer sortOrder = Integer.valueOf(form.getSortOrder());
                                    String description = form.getDescription();

                                    geoCode = geoControl.createGeoCode(geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder, createdBy);
                                    geoControl.createGeoCodeCountry(geoCode, telephoneCode, areaCodePattern, areaCodeRequired, areaCodeExample,
                                            telephoneNumberPattern, telephoneNumberExample, postalAddressFormat, cityRequired, cityGeoCodeRequired,
                                            stateRequired, stateGeoCodeRequired, postalCodePattern, postalCodeRequired, postalCodeGeoCodeRequired,
                                            postalCodeLength, postalCodeGeoCodeLength, postalCodeExample, createdBy);

                                    GeoCodeAliasType geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_ISO_3_NUMBER);
                                    geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, iso3Number, createdBy);
                                    geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_ISO_3_LETTER);
                                    geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, iso3Letter, createdBy);
                                    geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
                                    geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, iso2Letter, createdBy);
                                    geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, GeoConstants.GeoCodeAliasType_COUNTRY_NAME);
                                    geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, countryName, createdBy);

                                    if(description != null) {
                                        Language language = getPreferredLanguage();

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
