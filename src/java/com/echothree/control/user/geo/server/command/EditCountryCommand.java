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

import com.echothree.control.user.geo.common.edit.CountryEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.result.EditCountryResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.geo.common.GeoCodeAliasTypes;
import com.echothree.model.control.geo.common.GeoCodeScopes;
import com.echothree.model.control.geo.common.GeoCodeTypes;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.geo.server.logic.GeoCodeAliasLogic;
import com.echothree.model.control.geo.server.logic.GeoCodeLogic;
import com.echothree.model.control.geo.server.logic.GeoCodeScopeLogic;
import com.echothree.model.control.geo.server.logic.GeoCodeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditCountryCommand
        extends BaseAbstractEditCommand<GeoCodeSpec, CountryEdit, EditCountryResult, GeoCode, GeoCode> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Country.name(), SecurityRoles.Edit.name())
                    )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
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

    /** Creates a new instance of EditCountryCommand */
    public EditCountryCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCountryResult getResult() {
        return GeoResultFactory.getEditCountryResult();
    }

    @Override
    public CountryEdit getEdit() {
        return GeoEditFactory.getCountryEdit();
    }

    @Override
    public GeoCode getEntity(EditCountryResult result) {
        var geoControl = Session.getModelController(GeoControl.class);
        GeoCode geoCode;
        var geoCodeName = spec.getGeoCodeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            geoCode = geoControl.getGeoCodeByName(geoCodeName);
        } else { // EditMode.UPDATE
            geoCode = geoControl.getGeoCodeByNameForUpdate(geoCodeName);
        }

        if(geoCode != null) {
            var geoCodeTypeName = geoCode.getLastDetail().getGeoCodeType().getLastDetail().getGeoCodeTypeName();
            
            if(!geoCodeTypeName.equals(GeoCodeTypes.COUNTRY.name())) {
                addExecutionError(ExecutionErrors.InvalidGeoCodeType.name(), geoCodeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }

        return geoCode;
    }

    @Override
    public GeoCode getLockEntity(GeoCode geoCode) {
        return geoCode;
    }

    @Override
    public void fillInResult(EditCountryResult result, GeoCode geoCode) {
        var geoControl = Session.getModelController(GeoControl.class);

        result.setCountry(geoControl.getCountryTransfer(getUserVisit(), geoCode));
    }

    @Override
    public void doLock(CountryEdit edit, GeoCode geoCode) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeAliasLogic = GeoCodeAliasLogic.getInstance();
        var geoCodeDescription = geoControl.getGeoCodeDescription(geoCode, getPreferredLanguage());
        var geoCodeDetail = geoCode.getLastDetail();
        var geoCodeCountry = geoControl.getGeoCodeCountry(geoCode);
        var postalCodeLength = geoCodeCountry.getPostalCodeGeoCodeLength();
        var postalCodeGeoCodeLength = geoCodeCountry.getPostalCodeGeoCodeLength();

        var geoCodeAlias = geoCodeAliasLogic.getGeoCodeAliasUsingNames(null, geoCode, GeoCodeAliasTypes.COUNTRY_NAME.name());
        edit.setCountryName(geoCodeAlias.getAlias());

        geoCodeAlias = geoCodeAliasLogic.getGeoCodeAliasUsingNames(null, geoCode, GeoCodeAliasTypes.ISO_3_NUMBER.name());
        edit.setIso3Number(geoCodeAlias.getAlias());

        geoCodeAlias = geoCodeAliasLogic.getGeoCodeAliasUsingNames(null, geoCode, GeoCodeAliasTypes.ISO_3_LETTER.name());
        edit.setIso3Letter(geoCodeAlias.getAlias());

        geoCodeAlias = geoCodeAliasLogic.getGeoCodeAliasUsingNames(null, geoCode, GeoCodeAliasTypes.ISO_2_LETTER.name());
        edit.setIso2Letter(geoCodeAlias.getAlias());

        edit.setTelephoneCode(geoCodeCountry.getTelephoneCode());
        edit.setAreaCodePattern(geoCodeCountry.getAreaCodePattern());
        edit.setAreaCodeRequired(geoCodeCountry.getAreaCodeRequired().toString());
        edit.setAreaCodeExample(geoCodeCountry.getAreaCodeExample());
        edit.setTelephoneNumberPattern(geoCodeCountry.getTelephoneNumberPattern());
        edit.setTelephoneNumberExample(geoCodeCountry.getTelephoneNumberExample());
        edit.setPostalAddressFormatName(geoCodeCountry.getPostalAddressFormat().getLastDetail().getPostalAddressFormatName());
        edit.setCityRequired(geoCodeCountry.getCityRequired().toString());
        edit.setCityGeoCodeRequired(geoCodeCountry.getCityGeoCodeRequired().toString());
        edit.setStateRequired(geoCodeCountry.getStateRequired().toString());
        edit.setStateGeoCodeRequired(geoCodeCountry.getStateGeoCodeRequired().toString());
        edit.setPostalCodePattern(geoCodeCountry.getPostalCodePattern());
        edit.setPostalCodeRequired(geoCodeCountry.getPostalCodeRequired().toString());
        edit.setPostalCodeGeoCodeRequired(geoCodeCountry.getPostalCodeGeoCodeRequired().toString());
        edit.setPostalCodeLength(postalCodeLength == null ? null : postalCodeLength.toString());
        edit.setPostalCodeGeoCodeLength(postalCodeGeoCodeLength == null ? null : postalCodeGeoCodeLength.toString());
        edit.setPostalCodeExample(geoCodeCountry.getPostalCodeExample());
        edit.setIsDefault(geoCodeDetail.getIsDefault().toString());
        edit.setSortOrder(geoCodeDetail.getSortOrder().toString());

        if(geoCodeDescription != null) {
            edit.setDescription(geoCodeDescription.getDescription());
        }
    }

    PostalAddressFormat postalAddressFormat;

    @Override
    public void canUpdate(GeoCode geoCode) {
        var geoCodeTypeLogic = GeoCodeTypeLogic.getInstance();
        var geoCodeType = geoCodeTypeLogic.getGeoCodeTypeByName(this, GeoCodeTypes.COUNTRY.name());

        if(!hasExecutionErrors()) {
            var geoCodeScopeLogic = GeoCodeScopeLogic.getInstance();
            var geoCodeScope = geoCodeScopeLogic.getGeoCodeScopeByName(this, GeoCodeScopes.COUNTRIES.name());

            if(!hasExecutionErrors()) {
                var geoCodeLogic = GeoCodeLogic.getInstance();
                var iso3Number = edit.getIso3Number();
                var duplicateGeoCode = geoCodeLogic.getGeoCodeByAlias(this, geoCodeType, geoCodeScope, GeoCodeAliasTypes.ISO_3_NUMBER.name(), iso3Number);

                if((duplicateGeoCode == null || duplicateGeoCode.equals(geoCode)) && !hasExecutionErrors()) {
                    var iso3Letter = edit.getIso3Letter();

                    duplicateGeoCode = geoCodeLogic.getGeoCodeByAlias(this, geoCodeType, geoCodeScope, GeoCodeAliasTypes.ISO_3_LETTER.name(), iso3Letter);

                    if((duplicateGeoCode == null || duplicateGeoCode.equals(geoCode)) && !hasExecutionErrors()) {
                        var iso2Letter = edit.getIso2Letter();

                        duplicateGeoCode = geoCodeLogic.getGeoCodeByAlias(this, geoCodeType, geoCodeScope, GeoCodeAliasTypes.ISO_2_LETTER.name(), iso2Letter);

                        if((duplicateGeoCode == null || duplicateGeoCode.equals(geoCode)) && !hasExecutionErrors()) {
                            var countryName = edit.getCountryName();

                            duplicateGeoCode = geoCodeLogic.getGeoCodeByAlias(this, geoCodeType, geoCodeScope, GeoCodeAliasTypes.COUNTRY_NAME.name(), countryName);

                            if((duplicateGeoCode == null || duplicateGeoCode.equals(geoCode)) && !hasExecutionErrors()) {
                                var contactControl = Session.getModelController(ContactControl.class);
                                var postalAddressFormatName = edit.getPostalAddressFormatName();

                                postalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName);

                                if(postalAddressFormat == null) {
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
    }

    @Override
    public void doUpdate(GeoCode geoCode) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeAliasLogic = GeoCodeAliasLogic.getInstance();
        var partyPK = getPartyPK();
        var geoCodeDetailValue = geoControl.getGeoCodeDetailValueForUpdate(geoCode);
        var geoCodeDescription = geoControl.getGeoCodeDescriptionForUpdate(geoCode, getPreferredLanguage());
        var geoCodeCountryValue = geoControl.getGeoCodeCountryValueForUpdate(geoCode);
        var strPostalCodeLength = edit.getPostalCodeLength();
        var postalCodeLength = strPostalCodeLength == null ? null : Integer.valueOf(strPostalCodeLength);
        var strPostalCodeGeoCodeLength = edit.getPostalCodeGeoCodeLength();
        var postalCodeGeoCodeLength = strPostalCodeGeoCodeLength == null ? null : Integer.valueOf(strPostalCodeGeoCodeLength);
        var description = edit.getDescription();

        var geoCodeAliasValue = geoCodeAliasLogic.getGeoCodeAliasValueUsingNames(null, geoCode, GeoCodeAliasTypes.COUNTRY_NAME.name());
        geoCodeAliasValue.setAlias(edit.getCountryName());
        geoControl.updateGeoCodeAliasFromValue(geoCodeAliasValue, partyPK);

        geoCodeAliasValue = geoCodeAliasLogic.getGeoCodeAliasValueUsingNames(null, geoCode, GeoCodeAliasTypes.ISO_3_NUMBER.name());
        geoCodeAliasValue.setAlias(edit.getIso3Number());
        geoControl.updateGeoCodeAliasFromValue(geoCodeAliasValue, partyPK);

        geoCodeAliasValue = geoCodeAliasLogic.getGeoCodeAliasValueUsingNames(null, geoCode, GeoCodeAliasTypes.ISO_3_LETTER.name());
        geoCodeAliasValue.setAlias(edit.getIso3Letter());
        geoControl.updateGeoCodeAliasFromValue(geoCodeAliasValue, partyPK);

        geoCodeAliasValue = geoCodeAliasLogic.getGeoCodeAliasValueUsingNames(null, geoCode, GeoCodeAliasTypes.ISO_2_LETTER.name());
        geoCodeAliasValue.setAlias(edit.getIso2Letter());
        geoControl.updateGeoCodeAliasFromValue(geoCodeAliasValue, partyPK);

        geoCodeCountryValue.setTelephoneCode(edit.getTelephoneCode());
        geoCodeCountryValue.setAreaCodePattern(edit.getAreaCodePattern());
        geoCodeCountryValue.setAreaCodeRequired(Boolean.valueOf(edit.getAreaCodeRequired()));
        geoCodeCountryValue.setAreaCodeExample(edit.getAreaCodeExample());
        geoCodeCountryValue.setTelephoneNumberPattern(edit.getTelephoneNumberPattern());
        geoCodeCountryValue.setTelephoneNumberExample(edit.getTelephoneNumberExample());
        geoCodeCountryValue.setPostalAddressFormatPK(postalAddressFormat.getPrimaryKey());
        geoCodeCountryValue.setCityRequired(Boolean.valueOf(edit.getCityRequired()));
        geoCodeCountryValue.setCityGeoCodeRequired(Boolean.valueOf(edit.getCityGeoCodeRequired()));
        geoCodeCountryValue.setStateRequired(Boolean.valueOf(edit.getStateRequired()));
        geoCodeCountryValue.setStateGeoCodeRequired(Boolean.valueOf(edit.getStateGeoCodeRequired()));
        geoCodeCountryValue.setPostalCodePattern(edit.getPostalCodePattern());
        geoCodeCountryValue.setPostalCodeRequired(Boolean.valueOf(edit.getPostalCodeRequired()));
        geoCodeCountryValue.setPostalCodeGeoCodeRequired(Boolean.valueOf(edit.getPostalCodeGeoCodeRequired()));
        geoCodeCountryValue.setPostalCodeLength(postalCodeLength);
        geoCodeCountryValue.setPostalCodeGeoCodeLength(postalCodeGeoCodeLength);
        geoCodeCountryValue.setPostalCodeExample(edit.getPostalCodeExample());
        
        geoCodeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        geoCodeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        geoControl.updateGeoCodeFromValue(geoCodeDetailValue, partyPK);
        geoControl.updateGeoCodeCountryFromValue(geoCodeCountryValue, partyPK);

        if(geoCodeDescription == null && description != null) {
            geoControl.createGeoCodeDescription(geoCode, getPreferredLanguage(), description, partyPK);
        } else {
            if(geoCodeDescription != null && description == null) {
                geoControl.deleteGeoCodeDescription(geoCodeDescription, partyPK);
            } else {
                if(geoCodeDescription != null && description != null) {
                    var geoCodeDescriptionValue = geoControl.getGeoCodeDescriptionValue(geoCodeDescription);

                    geoCodeDescriptionValue.setDescription(description);
                    geoControl.updateGeoCodeDescriptionFromValue(geoCodeDescriptionValue, partyPK);
                }
            }
        }
    }

}
