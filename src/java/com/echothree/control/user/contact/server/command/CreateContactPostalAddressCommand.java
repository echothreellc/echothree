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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.form.CreateContactPostalAddressForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.common.workflow.PostalAddressStatusConstants;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.apache.commons.codec.language.Soundex;

public class CreateContactPostalAddressCommand
        extends BaseSimpleCommand<CreateContactPostalAddressForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> customerFormFieldDefinitions;
    private final static List<FieldDefinition> otherFormFieldDefinitions;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.CUSTOMER.name(), null),
                new PartyTypeDefinition(PartyTypes.VENDOR.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactMechanism.name(), SecurityRoles.Create.name())
                        )))
                )));

        // customerFormFieldDefinitions differs from otherFormFieldDefinitions in that when the PartyType
        // executing this command = CUSTOMER, FirstName and LastName are required fields. For all other
        // PartyTypes, that requirement is relaxed.
        List<FieldDefinition> temp = new ArrayList<>(17);
        temp.add(new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null));
        temp.add(new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null));
        temp.add(new FieldDefinition("FirstName", FieldType.STRING, true, 1L, 20L));
        temp.add(new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L));
        temp.add(new FieldDefinition("LastName", FieldType.STRING, true, 1L, 20L));
        temp.add(new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null));
        temp.add(new FieldDefinition("CompanyName", FieldType.STRING, false, 1L, 60L));
        temp.add(new FieldDefinition("Attention", FieldType.STRING, false, 1L, 60L));
        temp.add(new FieldDefinition("Address1", FieldType.STRING, true, 1L, 40L));
        temp.add(new FieldDefinition("Address2", FieldType.STRING, false, 1L, 40L));
        temp.add(new FieldDefinition("Address3", FieldType.STRING, false, 1L, 40L));
        temp.add(new FieldDefinition("City", FieldType.STRING, false, 1L, 30L));
        temp.add(new FieldDefinition("State", FieldType.STRING, false, 1L, 30L));
        temp.add(new FieldDefinition("PostalCode", FieldType.STRING, false, 1L, 15L));
        temp.add(new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("IsCommercial", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L));
        customerFormFieldDefinitions = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(17);
        temp.add(new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null));
        temp.add(new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null));
        temp.add(new FieldDefinition("FirstName", FieldType.STRING, false, 1L, 20L));
        temp.add(new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L));
        temp.add(new FieldDefinition("LastName", FieldType.STRING, false, 1L, 20L));
        temp.add(new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null));
        temp.add(new FieldDefinition("CompanyName", FieldType.STRING, false, 1L, 60L));
        temp.add(new FieldDefinition("Attention", FieldType.STRING, false, 1L, 60L));
        temp.add(new FieldDefinition("Address1", FieldType.STRING, true, 1L, 40L));
        temp.add(new FieldDefinition("Address2", FieldType.STRING, false, 1L, 40L));
        temp.add(new FieldDefinition("Address3", FieldType.STRING, false, 1L, 40L));
        temp.add(new FieldDefinition("City", FieldType.STRING, false, 1L, 30L));
        temp.add(new FieldDefinition("State", FieldType.STRING, false, 1L, 30L));
        temp.add(new FieldDefinition("PostalCode", FieldType.STRING, false, 1L, 15L));
        temp.add(new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("IsCommercial", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L));
        otherFormFieldDefinitions = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of CreateContactPostalAddressCommand */
    public CreateContactPostalAddressCommand() {
        super(COMMAND_SECURITY_DEFINITION, null, false);
    }

    @Override
    protected SecurityResult security() {
        var securityResult = super.security();

        return securityResult != null ? securityResult : selfOnly(form);
    }

    @Override
    protected ValidationResult validate() {
        var partyTypeName = getPartyTypeName();
        var FORM_FIELD_DEFINITIONS = partyTypeName.equals(PartyTypes.CUSTOMER.name())? customerFormFieldDefinitions: otherFormFieldDefinitions;
        var validator = new Validator(this);
        var validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        return validationResult;
    }
    
    @Override
    protected BaseResult execute() {
        var result = ContactResultFactory.getCreateContactPostalAddressResult();
        var partyControl = Session.getModelController(PartyControl.class);
        var partyName = form.getPartyName();
        var party = partyName == null ? getParty() : partyControl.getPartyByName(partyName);
        
        if(party != null) {
            var geoControl = Session.getModelController(GeoControl.class);
            var countryName = form.getCountryName();
            var countryAlias = StringUtils.getInstance().cleanStringToName(countryName).toUpperCase(Locale.getDefault());
            var countryGeoCode = geoControl.getCountryByAlias(countryAlias);
            
            if(countryGeoCode != null) {
                var geoCodeCountry = geoControl.getGeoCodeCountry(countryGeoCode);
                var postalCode = form.getPostalCode();

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
                        GeoCode postalCodeGeoCode = null;
                        var postalCodeAlias = postalCode == null? null: StringUtils.getInstance().cleanStringToLettersOrDigits(StringUtils.getInstance().cleanStringToName(postalCode));
                        
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
                            var state = form.getState();
                            
                            if(!geoCodeCountry.getStateRequired() || state != null) {
                                GeoCode stateGeoCode = null;
                                var stateAlias = state == null? null: StringUtils.getInstance().cleanStringToName(state).toUpperCase(Locale.getDefault());
                                
                                if(stateAlias != null) {
                                    stateGeoCode = geoControl.getStateByAlias(countryGeoCode, stateAlias);
                                }
                                
                                if(!geoCodeCountry.getStateGeoCodeRequired() || stateGeoCode != null) {
                                    var city = form.getCity();
                                    
                                    if(!geoCodeCountry.getCityRequired() || city != null) {
                                        GeoCode cityGeoCode = null;
                                        var cityAlias = city == null? null: StringUtils.getInstance().cleanStringToName(city).toUpperCase(Locale.getDefault());
                                        
                                        if(stateGeoCode != null && cityAlias != null) {
                                            cityGeoCode = geoControl.getCityByAlias(stateGeoCode, cityAlias);
                                        }

                                        if(!geoCodeCountry.getCityGeoCodeRequired() || cityGeoCode != null) {
                                            GeoCode countyGeoCode = null;
                                            
                                            var contactControl = Session.getModelController(ContactControl.class);
                                            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                                            var workflowControl = Session.getModelController(WorkflowControl.class);
                                            var soundex = new Soundex();
                                            BasePK createdBy = getPartyPK();
                                            var personalTitleId = form.getPersonalTitleId();
                                            var personalTitle = personalTitleId == null? null: partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
                                            var firstName = form.getFirstName();
                                            var middleName = form.getMiddleName();
                                            var lastName = form.getLastName();
                                            var nameSuffixId = form.getNameSuffixId();
                                            var nameSuffix = nameSuffixId == null? null: partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
                                            var companyName = form.getCompanyName();
                                            var attention = form.getAttention();
                                            var address1 = form.getAddress1();
                                            var address2 = form.getAddress2();
                                            var address3 = form.getAddress3();
                                            var allowSolicitation = Boolean.valueOf(form.getAllowSolicitation());
                                            var isCommercial = Boolean.valueOf(form.getIsCommercial());
                                            var description = form.getDescription();
                                            var contactMechanismName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(null, SequenceTypes.CONTACT_MECHANISM.name());
                                            
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

                                            var contactMechanismType = contactControl.getContactMechanismTypeByName(ContactMechanismTypes.POSTAL_ADDRESS.name());
                                            var contactMechanism = contactControl.createContactMechanism(contactMechanismName, contactMechanismType,
                                                    allowSolicitation, createdBy);
                                            
                                            contactControl.createContactPostalAddress(contactMechanism, personalTitle, firstName, firstNameSdx, middleName,
                                                    middleNameSdx, lastName, lastNameSdx, nameSuffix, companyName, attention, address1, address2, address3, city,
                                                    cityGeoCode, countyGeoCode, state, stateGeoCode, postalCode, postalCodeGeoCode, countryGeoCode, isCommercial,
                                                    createdBy);
                                            
                                            contactControl.createPartyContactMechanism(party, contactMechanism, description,
                                                    Boolean.FALSE, 1, createdBy);

                                            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(contactMechanism.getPrimaryKey());
                                            workflowControl.addEntityToWorkflowUsingNames(null, PostalAddressStatusConstants.Workflow_POSTAL_ADDRESS_STATUS,
                                                    PostalAddressStatusConstants.WorkflowEntrance_NEW_POSTAL_ADDRESS, entityInstance, null, null, createdBy);
                                            
                                            result.setContactMechanismName(contactMechanism.getLastDetail().getContactMechanismName());
                                            result.setEntityRef(contactMechanism.getPrimaryKey().getEntityRef());
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
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return result;
    }
    
}
