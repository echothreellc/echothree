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

import com.echothree.control.user.contact.common.edit.ContactEditFactory;
import com.echothree.control.user.contact.common.edit.ContactTelephoneEdit;
import com.echothree.control.user.contact.common.form.EditContactTelephoneForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditContactTelephoneResult;
import com.echothree.control.user.contact.common.spec.PartyContactMechanismSpec;
import com.echothree.model.control.contact.common.ContactConstants;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismDetail;
import com.echothree.model.data.contact.server.entity.ContactTelephone;
import com.echothree.model.data.contact.server.value.ContactMechanismDetailValue;
import com.echothree.model.data.contact.server.value.ContactTelephoneValue;
import com.echothree.model.data.contact.server.value.PartyContactMechanismDetailValue;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeCountry;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class EditContactTelephoneCommand
        extends BaseEditCommand<PartyContactMechanismSpec, ContactTelephoneEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AreaCode", FieldType.STRING, false, 1L, 5L),
                new FieldDefinition("TelephoneNumber", FieldType.STRING, true, 1L, 25L),
                new FieldDefinition("TelephoneExtension", FieldType.NUMBERS, false, 1L, 10L),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditContactTelephoneCommand */
    public EditContactTelephoneCommand(UserVisitPK userVisitPK, EditContactTelephoneForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        EditContactTelephoneResult result = ContactResultFactory.getEditContactTelephoneResult();
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
                    
                    if(ContactConstants.ContactMechanismType_TELECOM_ADDRESS.equals(contactMechanismTypeName)) {
                        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
                        
                        if(editMode.equals(EditMode.LOCK)) {
                            ContactTelephone contactTelephone = contactControl.getContactTelephone(contactMechanism);
                            
                            if(lockEntity(contactMechanism)) {
                                ContactTelephoneEdit edit = ContactEditFactory.getContactTelephoneEdit();
                                
                                result.setEdit(edit);
                                edit.setCountryName(geoControl.getAliasForCountry(contactTelephone.getCountryGeoCode()));
                                edit.setAllowSolicitation(lastContactMechanismDetail.getAllowSolicitation().toString());
                                edit.setAreaCode(contactTelephone.getAreaCode());
                                edit.setTelephoneNumber(contactTelephone.getTelephoneNumber());
                                edit.setTelephoneExtension(contactTelephone.getTelephoneExtension());
                                edit.setDescription(partyContactMechanismDetailValue.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(contactMechanism));
                        } else if(editMode.equals(EditMode.ABANDON)) {
                            unlockEntity(contactMechanism);
                        } else if(editMode.equals(EditMode.UPDATE)) {
                            ContactMechanismDetailValue lastContactMechanismDetailValue = contactControl.getContactMechanismDetailValue(lastContactMechanismDetail);
                            ContactTelephoneValue contactTelephoneValue = contactControl.getContactTelephoneValueForUpdate(contactMechanism);
                            String countryName = edit.getCountryName();
                            String countryAlias = StringUtils.getInstance().cleanStringToName(countryName).toUpperCase();
                            GeoCode countryGeoCode = geoControl.getCountryByAlias(countryAlias);
                            
                            if(countryGeoCode != null) {
                                GeoCodeCountry geoCodeCountry = geoControl.getGeoCodeCountry(countryGeoCode);
                                String areaCode = edit.getAreaCode();
                                
                                if(!geoCodeCountry.getAreaCodeRequired() || areaCode != null) {
                                    String areaCodePattern = geoCodeCountry.getAreaCodePattern();
                                    Pattern pattern = areaCodePattern == null? null: Pattern.compile(areaCodePattern);
                                    
                                    if(pattern == null || pattern.matcher(areaCode).matches()) {
                                        String telephoneNumberPattern = geoCodeCountry.getTelephoneNumberPattern();
                                        String telephoneNumber = edit.getTelephoneNumber();
                                        
                                        pattern = telephoneNumberPattern == null? null: Pattern.compile(telephoneNumberPattern);
                                        
                                        if(pattern == null || pattern.matcher(telephoneNumber).matches()) {
                                            if(lockEntityForUpdate(contactMechanism)) {
                                                String telephoneExtension = edit.getTelephoneExtension();
                                                Boolean allowSolicitation = Boolean.valueOf(edit.getAllowSolicitation());
                                                
                                                try {
                                                    BasePK updatedBy = getPartyPK();
                                                    
                                                    lastContactMechanismDetailValue.setAllowSolicitation(allowSolicitation);
                                                    contactTelephoneValue.setCountryGeoCodePK(countryGeoCode.getPrimaryKey());
                                                    contactTelephoneValue.setAreaCode(areaCode);
                                                    contactTelephoneValue.setTelephoneNumber(telephoneNumber);
                                                    contactTelephoneValue.setTelephoneExtension(telephoneExtension);
                                                    partyContactMechanismDetailValue.setDescription(edit.getDescription());
                                                    
                                                    contactControl.updateContactMechanismFromValue(lastContactMechanismDetailValue, updatedBy);
                                                    contactControl.updateContactTelephoneFromValue(contactTelephoneValue, updatedBy);
                                                    contactControl.updatePartyContactMechanismFromValue(partyContactMechanismDetailValue, updatedBy);
                                                } finally {
                                                    unlockEntity(contactMechanism);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.InvalidTelephoneNumber.name(), telephoneNumber);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.InvalidAreaCode.name(), areaCode);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.MissingAreaCode.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
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

