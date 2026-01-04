// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.edit.ContactEditFactory;
import com.echothree.control.user.contact.common.edit.ContactPostalAddressEdit;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditContactPostalAddressResult;
import com.echothree.control.user.contact.common.spec.PartyContactMechanismSpec;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.apache.commons.codec.language.Soundex;
import javax.enterprise.context.Dependent;

@Dependent
public class EditContactPostalAddressCommand
        extends BaseAbstractEditCommand<PartyContactMechanismSpec, ContactPostalAddressEdit, EditContactPostalAddressResult, PartyContactMechanism, ContactMechanism> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> editCustomerFieldDefinitions;
    private final static List<FieldDefinition> editOtherFieldDefinitions;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.CUSTOMER.name(), null),
                new PartyTypeDefinition(PartyTypes.VENDOR.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactMechanism.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        // customerFormFieldDefinitions differs from otherFormFieldDefinitions in that when the PartyType
        // executing this command = CUSTOMER, FirstName and LastName are required fields. For all other
        // PartyTypes, that requirement is relaxed.
        editCustomerFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null),
                new FieldDefinition("FirstName", FieldType.STRING, true, 1L, 20L),
                new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("LastName", FieldType.STRING, true, 1L, 20L),
                new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null),
                new FieldDefinition("CompanyName", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("Attention", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("Address1", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("Address2", FieldType.STRING, false, 1L, 40L),
                new FieldDefinition("Address3", FieldType.STRING, false, 1L, 40L),
                new FieldDefinition("City", FieldType.STRING, false, 1L, 30L),
                new FieldDefinition("State", FieldType.STRING, false, 1L, 30L),
                new FieldDefinition("PostalCode", FieldType.STRING, false, 1L, 15L),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsCommercial", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
        
        editOtherFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null),
                new FieldDefinition("FirstName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("LastName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null),
                new FieldDefinition("CompanyName", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("Attention", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("Address1", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("Address2", FieldType.STRING, false, 1L, 40L),
                new FieldDefinition("Address3", FieldType.STRING, false, 1L, 40L),
                new FieldDefinition("City", FieldType.STRING, false, 1L, 30L),
                new FieldDefinition("State", FieldType.STRING, false, 1L, 30L),
                new FieldDefinition("PostalCode", FieldType.STRING, false, 1L, 15L),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsCommercial", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditContactPostalAddressCommand */
    public EditContactPostalAddressCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, null);
    }

    @Override
    protected List<FieldDefinition> getEditFieldDefinitions() {
        var partyTypeName = getPartyTypeName();

        return partyTypeName.equals(PartyTypes.CUSTOMER.name()) ? editCustomerFieldDefinitions : editOtherFieldDefinitions;
    }

    @Override
    protected SecurityResult security() {
        var securityResult = super.security();

        return securityResult != null ? securityResult : selfOnly(spec);
    }

    @Override
    public EditContactPostalAddressResult getResult() {
        return ContactResultFactory.getEditContactPostalAddressResult();
    }

    @Override
    public ContactPostalAddressEdit getEdit() {
        return ContactEditFactory.getContactPostalAddressEdit();
    }

    @Override
    public PartyContactMechanism getEntity(EditContactPostalAddressResult result) {
        var partyControl = Session.getModelController(PartyControl.class);
        PartyContactMechanism partyContactMechanism = null;
        var partyName = spec.getPartyName();
        var party = partyName == null ? getParty() : partyControl.getPartyByName(partyName);

        if(party != null) {
            var contactControl = Session.getModelController(ContactControl.class);
            var contactMechanismName = spec.getContactMechanismName();
            var contactMechanism = contactControl.getContactMechanismByName(contactMechanismName);

            if(contactMechanism != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    partyContactMechanism = contactControl.getPartyContactMechanism(party, contactMechanism);
                } else { // EditMode.UPDATE
                    partyContactMechanism = contactControl.getPartyContactMechanismForUpdate(party, contactMechanism);
                }

                if(partyContactMechanism != null) {
                    var lastContactMechanismDetail = contactMechanism.getLastDetail();
                    var contactMechanismTypeName = lastContactMechanismDetail.getContactMechanismType().getContactMechanismTypeName();

                    result.setContactMechanism(contactControl.getContactMechanismTransfer(getUserVisit(), contactMechanism));

                    if(!ContactMechanismTypes.POSTAL_ADDRESS.name().equals(contactMechanismTypeName)) {
                        addExecutionError(ExecutionErrors.InvalidContactMechanismType.name(), contactMechanismTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyContactMechanism.name(), partyName, contactMechanismName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return partyContactMechanism;
    }

    @Override
    public ContactMechanism getLockEntity(PartyContactMechanism partyContactMechanism) {
        return partyContactMechanism.getLastDetail().getContactMechanism();
    }

    @Override
    public void fillInResult(EditContactPostalAddressResult result, PartyContactMechanism partyContactMechanism) {
        var contactControl = Session.getModelController(ContactControl.class);

        result.setContactMechanism(contactControl.getContactMechanismTransfer(getUserVisit(),
                partyContactMechanism.getLastDetail().getContactMechanism()));
    }

    @Override
    public void doLock(ContactPostalAddressEdit edit, PartyContactMechanism partyContactMechanism) {
        var contactControl = Session.getModelController(ContactControl.class);
        var geoControl = Session.getModelController(GeoControl.class);
        var contactMechanism = partyContactMechanism.getLastDetail().getContactMechanism();
        var contactMechanismDetail = contactMechanism.getLastDetail();
        var contactPostalAddress = contactControl.getContactPostalAddress(contactMechanism);
        var partyContactMechanismDetail = partyContactMechanism.getLastDetail();

        var personalTitle = contactPostalAddress.getPersonalTitle();
        var nameSuffix = contactPostalAddress.getNameSuffix();

        var city = contactPostalAddress.getCity();
        var state = contactPostalAddress.getState();
        var postalCode = contactPostalAddress.getPostalCode();

        var cityGeoCode = city == null ? contactPostalAddress.getCityGeoCode() : null;
        var stateGeoCode = state == null ? contactPostalAddress.getStateGeoCode() : null;
        var postalCodeGeoCode = postalCode == null ? contactPostalAddress.getPostalCodeGeoCode() : null;

        var cityGeoCodeDescription = cityGeoCode == null ? null : geoControl.getBestGeoCodeDescription(cityGeoCode, getPreferredLanguage());
        var stateGeoCodeDescription = stateGeoCode == null ? null : geoControl.getBestGeoCodeDescription(stateGeoCode, getPreferredLanguage());
        var postalCodeGeoCodeDescription = postalCodeGeoCode == null ? null : geoControl.getBestGeoCodeDescription(postalCodeGeoCode, getPreferredLanguage());

        edit.setAllowSolicitation(contactMechanismDetail.getAllowSolicitation().toString());
        edit.setPersonalTitleId(personalTitle == null ? null : personalTitle.getPrimaryKey().getEntityId().toString());
        edit.setFirstName(contactPostalAddress.getFirstName());
        edit.setMiddleName(contactPostalAddress.getMiddleName());
        edit.setLastName(contactPostalAddress.getLastName());
        edit.setNameSuffixId(nameSuffix == null ? null : nameSuffix.getPrimaryKey().getEntityId().toString());
        edit.setCompanyName(contactPostalAddress.getCompanyName());
        edit.setAttention(contactPostalAddress.getAttention());
        edit.setAddress1(contactPostalAddress.getAddress1());
        edit.setAddress2(contactPostalAddress.getAddress2());
        edit.setAddress3(contactPostalAddress.getAddress3());
        edit.setCity(city == null ? (cityGeoCode == null ? null : (cityGeoCodeDescription == null ? geoControl.getAliasForCity(cityGeoCode) : cityGeoCodeDescription)) : city);
        edit.setState(state == null ? (stateGeoCode == null ? null : (stateGeoCodeDescription == null ? geoControl.getAliasForState(stateGeoCode) : stateGeoCodeDescription)) : state);
        edit.setPostalCode(postalCode == null ? (postalCodeGeoCode == null ? null : (postalCodeGeoCodeDescription == null ? geoControl.getAliasForPostalCode(postalCodeGeoCode) : postalCodeGeoCodeDescription)) : postalCode);
        edit.setCountryName(geoControl.getAliasForCountry(contactPostalAddress.getCountryGeoCode()));
        edit.setIsCommercial(contactPostalAddress.getIsCommercial().toString());
        edit.setDescription(partyContactMechanismDetail.getDescription());
    }

    GeoCode countryGeoCode;
    String state;
    GeoCode stateGeoCode;
    String city;
    GeoCode cityGeoCode;
    String postalCode;
    GeoCode postalCodeGeoCode;
    GeoCode countyGeoCode;

    @Override
    public void canUpdate(PartyContactMechanism partyContactMechanism) {
        var geoControl = Session.getModelController(GeoControl.class);
        var countryName = edit.getCountryName();
        var countryAlias = StringUtils.getInstance().cleanStringToName(countryName).toUpperCase(Locale.getDefault());

        countryGeoCode = geoControl.getCountryByAlias(countryAlias);

        if(countryGeoCode != null) {
            var geoCodeCountry = geoControl.getGeoCodeCountry(countryGeoCode);

            postalCode = edit.getPostalCode();

            if(postalCode != null) {
                postalCode = postalCode.toUpperCase(Locale.getDefault());
            }

            if(!geoCodeCountry.getPostalCodeRequired() || postalCode != null) {
                var postalCodePattern = geoCodeCountry.getPostalCodePattern();
                var postalCodeLength = geoCodeCountry.getPostalCodeLength();

                if(postalCodeLength == null) {
                    postalCodeLength = Integer.MAX_VALUE;
                }

                if(postalCode == null || ((postalCodePattern == null || postalCode.matches(postalCodePattern)) && (postalCode.length() <= postalCodeLength))) {
                    var postalCodeAlias = postalCode == null ? null : StringUtils.getInstance().cleanStringToLettersOrDigits(StringUtils.getInstance().cleanStringToName(postalCode));

                    if(postalCodeAlias != null) {
                        var postalCodeAliasLength = postalCodeAlias.length();
                        var postalCodeGeoCodeLength = geoCodeCountry.getPostalCodeGeoCodeLength();

                        if(postalCodeGeoCodeLength == null || postalCodeAliasLength >= postalCodeGeoCodeLength) {
                            if(postalCodeGeoCodeLength != null && postalCodeAliasLength > postalCodeGeoCodeLength) {
                                postalCodeAlias = postalCodeAlias.substring(0, postalCodeGeoCodeLength);
                            }

                            postalCodeGeoCode = geoControl.getPostalCodeByAlias(countryGeoCode, postalCodeAlias);
                        }
                    }

                    if(!geoCodeCountry.getPostalCodeGeoCodeRequired() || postalCodeGeoCode != null) {
                        state = edit.getState();

                        if(!geoCodeCountry.getStateRequired() || state != null) {
                            var stateAlias = state == null ? null : StringUtils.getInstance().cleanStringToName(state).toUpperCase(Locale.getDefault());

                            if(stateAlias != null) {
                                stateGeoCode = geoControl.getStateByAlias(countryGeoCode, stateAlias);
                            }

                            if(!geoCodeCountry.getStateGeoCodeRequired() || stateGeoCode != null) {
                                city = edit.getCity();

                                if(!geoCodeCountry.getCityRequired() || city != null) {
                                    var cityAlias = city == null ? null : StringUtils.getInstance().cleanStringToName(city).toUpperCase(Locale.getDefault());

                                    if(stateGeoCode != null && cityAlias != null) {
                                        cityGeoCode = geoControl.getCityByAlias(stateGeoCode, cityAlias);
                                    }

                                    if(!geoCodeCountry.getCityGeoCodeRequired() || cityGeoCode != null) {
                                        // TODO: countyGeoCode
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownCity.name(), city, cityAlias);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.MissingCity.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownState.name(), state, stateAlias);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.MissingState.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPostalCode.name(), postalCode);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidPostalCode.name(), postalCode);
                }
            } else {
                addExecutionError(ExecutionErrors.MissingPostalCode.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName, countryAlias);
        }
    }

    @Override
    public void doUpdate(PartyContactMechanism partyContactMechanism) {
        var contactControl = Session.getModelController(ContactControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var soundex = new Soundex();
        var updatedBy = getPartyPK();
        var contactMechanism = partyContactMechanism.getLastDetail().getContactMechanism();
        var contactMechanismDetailValue = contactControl.getContactMechanismDetailValue(contactMechanism.getLastDetail());
        var contactPostalAddressValue = contactControl.getContactPostalAddressValueForUpdate(contactMechanism);
        var partyContactMechanismDetailValue = contactControl.getPartyContactMechanismDetailValueForUpdate(partyContactMechanism);

        var personalTitleId = edit.getPersonalTitleId();
        var personalTitle = personalTitleId == null ? null : partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
        var firstName = edit.getFirstName();
        var middleName = edit.getMiddleName();
        var lastName = edit.getLastName();
        var nameSuffixId = edit.getNameSuffixId();
        var nameSuffix = nameSuffixId == null ? null : partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);

        String firstNameSdx;
        try {
            firstNameSdx = firstName == null ? null : soundex.encode(firstName);
        } catch(IllegalArgumentException iae) {
            firstNameSdx = null;
        }

        String middleNameSdx;
        try {
            middleNameSdx = middleName == null ? null : soundex.encode(middleName);
        } catch(IllegalArgumentException iae) {
            middleNameSdx = null;
        }

        String lastNameSdx;
        try {
            lastNameSdx = lastName == null ? null : soundex.encode(lastName);
        } catch(IllegalArgumentException iae) {
            lastNameSdx = null;
        }

        contactMechanismDetailValue.setAllowSolicitation(Boolean.valueOf(edit.getAllowSolicitation()));
        contactPostalAddressValue.setCountryGeoCodePK(countryGeoCode.getPrimaryKey());
        contactPostalAddressValue.setPersonalTitlePK(personalTitle == null ? null : personalTitle.getPrimaryKey());
        contactPostalAddressValue.setFirstName(firstName);
        contactPostalAddressValue.setFirstNameSdx(firstNameSdx);
        contactPostalAddressValue.setMiddleName(middleName);
        contactPostalAddressValue.setMiddleNameSdx(middleNameSdx);
        contactPostalAddressValue.setLastName(lastName);
        contactPostalAddressValue.setLastNameSdx(lastNameSdx);
        contactPostalAddressValue.setNameSuffixPK(nameSuffix == null ? null : nameSuffix.getPrimaryKey());
        contactPostalAddressValue.setCompanyName(edit.getCompanyName());
        contactPostalAddressValue.setAttention(edit.getAttention());
        contactPostalAddressValue.setAddress1(edit.getAddress1());
        contactPostalAddressValue.setAddress2(edit.getAddress2());
        contactPostalAddressValue.setAddress3(edit.getAddress3());
        contactPostalAddressValue.setCity(city);
        contactPostalAddressValue.setCityGeoCodePK(cityGeoCode == null ? null : cityGeoCode.getPrimaryKey());
        contactPostalAddressValue.setCountyGeoCodePK(countyGeoCode == null ? null : countyGeoCode.getPrimaryKey());
        contactPostalAddressValue.setState(state);
        contactPostalAddressValue.setStateGeoCodePK(stateGeoCode == null ? null : stateGeoCode.getPrimaryKey());
        contactPostalAddressValue.setPostalCode(postalCode);
        contactPostalAddressValue.setPostalCodeGeoCodePK(postalCodeGeoCode == null ? null : postalCodeGeoCode.getPrimaryKey());
        contactPostalAddressValue.setCountryGeoCodePK(countryGeoCode == null ? null : countryGeoCode.getPrimaryKey());
        contactPostalAddressValue.setIsCommercial(Boolean.valueOf(edit.getIsCommercial()));
        partyContactMechanismDetailValue.setDescription(edit.getDescription());

        contactControl.updateContactMechanismFromValue(contactMechanismDetailValue, updatedBy);
        contactControl.updateContactPostalAddressFromValue(contactPostalAddressValue, updatedBy);
        contactControl.updatePartyContactMechanismFromValue(partyContactMechanismDetailValue, updatedBy);
    }

}

