// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.contact.remote.edit.ContactEditFactory;
import com.echothree.control.user.contact.remote.edit.ContactPostalAddressEdit;
import com.echothree.control.user.contact.remote.form.EditContactPostalAddressForm;
import com.echothree.control.user.contact.remote.result.ContactResultFactory;
import com.echothree.control.user.contact.remote.result.EditContactPostalAddressResult;
import com.echothree.control.user.contact.remote.spec.PartyContactMechanismSpec;
import com.echothree.model.control.contact.common.ContactConstants;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismDetail;
import com.echothree.model.data.contact.server.entity.ContactPostalAddress;
import com.echothree.model.data.contact.server.value.ContactMechanismDetailValue;
import com.echothree.model.data.contact.server.value.ContactPostalAddressValue;
import com.echothree.model.data.contact.server.value.PartyContactMechanismDetailValue;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeCountry;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.remote.persistence.BasePK;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.codec.language.Soundex;

public class EditContactPostalAddressCommand
        extends BaseEditCommand<PartyContactMechanismSpec, ContactPostalAddressEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> editCustomerFieldDefinitions;
    private final static List<FieldDefinition> editOtherFieldDefinitions;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditContactPostalAddressCommand */
    public EditContactPostalAddressCommand(UserVisitPK userVisitPK, EditContactPostalAddressForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, null);
        
        String partyTypeName = getPartyTypeName();
        List<FieldDefinition> EDIT_FIELD_DEFINITIONS = partyTypeName.equals(PartyConstants.PartyType_CUSTOMER)? editCustomerFieldDefinitions: editOtherFieldDefinitions;
        
        setEditFieldDefinitions(EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        EditContactPostalAddressResult result = ContactResultFactory.getEditContactPostalAddressResult();
        String partyName = spec.getPartyName();
        Party party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
            String contactMechanismName = spec.getContactMechanismName();
            ContactMechanism contactMechanism = contactControl.getContactMechanismByName(contactMechanismName);
            
            if(contactMechanism != null) {
                PartyContactMechanismDetailValue partyContactMechanismDetailValue = contactControl.getPartyContactMechanismDetailValueForUpdate(party, contactMechanism);
                
                if(partyContactMechanismDetailValue != null) {
                    ContactMechanismDetail lastContactMechanismDetail = contactMechanism.getLastDetailForUpdate();
                    String contactMechanismTypeName = lastContactMechanismDetail.getContactMechanismType().getContactMechanismTypeName();
                    
                    result.setContactMechanism(contactControl.getContactMechanismTransfer(getUserVisit(), contactMechanism));
                    
                    if(ContactConstants.ContactMechanismType_POSTAL_ADDRESS.equals(contactMechanismTypeName)) {
                        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
                        
                        if(editMode.equals(EditMode.LOCK)) {
                            ContactPostalAddress contactPostalAddress = contactControl.getContactPostalAddress(contactMechanism);
                            
                            if(lockEntity(contactMechanism)) {
                                ContactPostalAddressEdit edit = ContactEditFactory.getContactPostalAddressEdit();
                                PersonalTitle personalTitle = contactPostalAddress.getPersonalTitle();
                                NameSuffix nameSuffix = contactPostalAddress.getNameSuffix();
                                String city = contactPostalAddress.getCity();
                                String state = contactPostalAddress.getState();
                                String postalCode = contactPostalAddress.getPostalCode();
                                
                                GeoCode cityGeoCode = city == null? contactPostalAddress.getCityGeoCode(): null;
                                GeoCode stateGeoCode = state == null? contactPostalAddress.getStateGeoCode(): null;
                                GeoCode postalCodeGeoCode = postalCode == null? contactPostalAddress.getPostalCodeGeoCode(): null;
                                
                                String cityGeoCodeDescription = cityGeoCode == null? null: geoControl.getBestGeoCodeDescription(cityGeoCode, getPreferredLanguage());
                                String stateGeoCodeDescription = stateGeoCode == null? null: geoControl.getBestGeoCodeDescription(stateGeoCode, getPreferredLanguage());
                                String postalCodeGeoCodeDescription = postalCodeGeoCode == null? null: geoControl.getBestGeoCodeDescription(postalCodeGeoCode, getPreferredLanguage());
                                
                                result.setEdit(edit);
                                edit.setAllowSolicitation(lastContactMechanismDetail.getAllowSolicitation().toString());
                                edit.setPersonalTitleId(personalTitle == null? null: personalTitle.getPrimaryKey().getEntityId().toString());
                                edit.setFirstName(contactPostalAddress.getFirstName());
                                edit.setMiddleName(contactPostalAddress.getMiddleName());
                                edit.setLastName(contactPostalAddress.getLastName());
                                edit.setNameSuffixId(nameSuffix == null? null: nameSuffix.getPrimaryKey().getEntityId().toString());
                                edit.setCompanyName(contactPostalAddress.getCompanyName());
                                edit.setAttention(contactPostalAddress.getAttention());
                                edit.setAddress1(contactPostalAddress.getAddress1());
                                edit.setAddress2(contactPostalAddress.getAddress2());
                                edit.setAddress3(contactPostalAddress.getAddress3());
                                edit.setCity(city == null? (cityGeoCode == null? null: (cityGeoCodeDescription == null? geoControl.getAliasForCity(cityGeoCode): cityGeoCodeDescription)): city);
                                edit.setState(state == null? (stateGeoCode == null? null: (stateGeoCodeDescription == null? geoControl.getAliasForState(stateGeoCode): stateGeoCodeDescription)): state);
                                edit.setPostalCode(postalCode == null? (postalCodeGeoCode == null? null: (postalCodeGeoCodeDescription == null? geoControl.getAliasForPostalCode(postalCodeGeoCode): postalCodeGeoCodeDescription)): postalCode);
                                edit.setCountryName(geoControl.getAliasForCountry(contactPostalAddress.getCountryGeoCode()));
                                edit.setIsCommercial(contactPostalAddress.getIsCommercial().toString());
                                edit.setDescription(partyContactMechanismDetailValue.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(contactMechanism));
                        } else if(editMode.equals(EditMode.ABANDON)) {
                            unlockEntity(contactMechanism);
                        } else if(editMode.equals(EditMode.UPDATE)) {
                            ContactMechanismDetailValue lastContactMechanismDetailValue = contactControl.getContactMechanismDetailValue(lastContactMechanismDetail);
                            ContactPostalAddressValue contactPostalAddressValue = contactControl.getContactPostalAddressValueForUpdate(contactMechanism);
                            String countryName = edit.getCountryName();
                            String countryAlias = StringUtils.getInstance().cleanStringToName(countryName).toUpperCase();
                            GeoCode countryGeoCode = geoControl.getCountryByAlias(countryAlias);

                            if(countryGeoCode != null) {
                                GeoCodeCountry geoCodeCountry = geoControl.getGeoCodeCountry(countryGeoCode);
                                String postalCode = edit.getPostalCode();

                                if(postalCode != null) {
                                    postalCode = postalCode.toUpperCase();
                                }

                                if(!geoCodeCountry.getPostalCodeRequired() || postalCode != null) {
                                    String postalCodePattern = geoCodeCountry.getPostalCodePattern();
                                    Integer postalCodeLength = geoCodeCountry.getPostalCodeLength();

                                    if(postalCodeLength == null) {
                                        postalCodeLength = Integer.MAX_VALUE;
                                    }

                                    if(postalCode == null || ((postalCodePattern == null || postalCode.matches(postalCodePattern)) && (postalCode.length() <= postalCodeLength))) {
                                        GeoCode postalCodeGeoCode = null;
                                        String postalCodeAlias = postalCode == null? null: StringUtils.getInstance().cleanStringToLettersOrDigits(StringUtils.getInstance().cleanStringToName(postalCode));

                                        if(postalCodeAlias != null) {
                                            int postalCodeAliasLength = postalCodeAlias.length();
                                            Integer postalCodeGeoCodeLength = geoCodeCountry.getPostalCodeGeoCodeLength();

                                            if(postalCodeGeoCodeLength == null || postalCodeAliasLength >= postalCodeGeoCodeLength) {
                                                if(postalCodeGeoCodeLength != null && postalCodeAliasLength > postalCodeGeoCodeLength) {
                                                    postalCodeAlias = postalCodeAlias.substring(0, postalCodeGeoCodeLength);
                                                }

                                                postalCodeGeoCode = geoControl.getPostalCodeByAlias(countryGeoCode, postalCodeAlias);
                                            }
                                        }

                                        if(!geoCodeCountry.getPostalCodeGeoCodeRequired() || postalCodeGeoCode != null) {
                                            String state = edit.getState();

                                            if(!geoCodeCountry.getStateRequired() || state != null) {
                                                GeoCode stateGeoCode = null;
                                                String stateAlias = state == null? null: StringUtils.getInstance().cleanStringToName(state).toUpperCase();

                                                if(stateAlias != null) {
                                                    stateGeoCode = geoControl.getStateByAlias(countryGeoCode, stateAlias);
                                                }

                                                if(!geoCodeCountry.getStateGeoCodeRequired() || stateGeoCode != null) {
                                                    String city = edit.getCity();

                                                    if(!geoCodeCountry.getCityRequired() || city != null) {
                                                        GeoCode cityGeoCode = null;
                                                        String cityAlias = city == null? null: StringUtils.getInstance().cleanStringToName(city).toUpperCase();

                                                        if(stateGeoCode != null && cityAlias != null) {
                                                            cityGeoCode = geoControl.getCityByAlias(stateGeoCode, cityAlias);
                                                        }

                                                        if(!geoCodeCountry.getCityGeoCodeRequired() || cityGeoCode != null) {
                                                            GeoCode countyGeoCode = null; // TODO
                                                            
                                                            if(lockEntityForUpdate(contactMechanism)) {
                                                                Soundex soundex = new Soundex();
                                                                String personalTitleId = edit.getPersonalTitleId();
                                                                PersonalTitle personalTitle = personalTitleId == null? null: partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
                                                                String firstName = edit.getFirstName();
                                                                String middleName = edit.getMiddleName();
                                                                String lastName = edit.getLastName();
                                                                String nameSuffixId = edit.getNameSuffixId();
                                                                NameSuffix nameSuffix = nameSuffixId == null? null: partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
                                                                Boolean allowSolicitation = Boolean.valueOf(edit.getAllowSolicitation());
                                                                Boolean isCommercial = Boolean.valueOf(edit.getIsCommercial());
                                                                
                                                                String firstNameSdx;
                                                                try {
                                                                    firstNameSdx = firstName == null? null: soundex.encode(firstName);
                                                                } catch (IllegalArgumentException iae) {
                                                                    firstNameSdx = null;
                                                                }
                                                                
                                                                String middleNameSdx;
                                                                try {
                                                                    middleNameSdx = middleName == null? null: soundex.encode(middleName);
                                                                } catch (IllegalArgumentException iae) {
                                                                    middleNameSdx = null;
                                                                }
                                                                
                                                                String lastNameSdx;
                                                                try {
                                                                    lastNameSdx = lastName == null? null: soundex.encode(lastName);
                                                                } catch (IllegalArgumentException iae) {
                                                                    lastNameSdx = null;
                                                                }
                                                                
                                                                try {
                                                                    BasePK updatedBy = getPartyPK();
                                                                    
                                                                    lastContactMechanismDetailValue.setAllowSolicitation(allowSolicitation);
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
                                                                    contactPostalAddressValue.setIsCommercial(isCommercial);
                                                                    partyContactMechanismDetailValue.setDescription(edit.getDescription());

                                                                    contactControl.updateContactMechanismFromValue(lastContactMechanismDetailValue, updatedBy);
                                                                    contactControl.updateContactPostalAddressFromValue(contactPostalAddressValue, updatedBy);
                                                                    contactControl.updatePartyContactMechanismFromValue(partyContactMechanismDetailValue, updatedBy);
                                                                } finally {
                                                                    unlockEntity(contactMechanism);
                                                                }
                                                            } else {
                                                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                                                            }
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
                            
                            if(hasExecutionErrors()) {
                                result.setContactMechanism(contactControl.getContactMechanismTransfer(getUserVisit(), contactMechanism));
                                result.setEntityLock(getEntityLockTransfer(contactMechanism));
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidContactMechanismType.name(), contactMechanismTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyContactMechanism.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return result;
    }
    
}

