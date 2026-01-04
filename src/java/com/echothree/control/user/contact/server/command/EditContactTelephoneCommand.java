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
import com.echothree.control.user.contact.common.edit.ContactTelephoneEdit;
import com.echothree.control.user.contact.common.form.EditContactTelephoneForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditContactTelephoneResult;
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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.enterprise.context.Dependent;

@Dependent
public class EditContactTelephoneCommand
        extends BaseAbstractEditCommand<PartyContactMechanismSpec, ContactTelephoneEdit, EditContactTelephoneResult, PartyContactMechanism, ContactMechanism> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
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
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AreaCode", FieldType.STRING, false, 1L, 5L),
                new FieldDefinition("TelephoneNumber", FieldType.STRING, true, 1L, 25L),
                new FieldDefinition("TelephoneExtension", FieldType.NUMBERS, false, 1L, 10L),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContactTelephoneCommand */
    public EditContactTelephoneCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected SecurityResult security() {
        var securityResult = super.security();

        return securityResult != null ? securityResult : selfOnly(spec);
    }

    @Override
    public EditContactTelephoneResult getResult() {
        return ContactResultFactory.getEditContactTelephoneResult();
    }

    @Override
    public ContactTelephoneEdit getEdit() {
        return ContactEditFactory.getContactTelephoneEdit();
    }

    @Override
    public PartyContactMechanism getEntity(EditContactTelephoneResult result) {
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

                    if(!ContactMechanismTypes.TELECOM_ADDRESS.name().equals(contactMechanismTypeName)) {
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
    public void fillInResult(EditContactTelephoneResult result, PartyContactMechanism partyContactMechanism) {
        var contactControl = Session.getModelController(ContactControl.class);

        result.setContactMechanism(contactControl.getContactMechanismTransfer(getUserVisit(),
                partyContactMechanism.getLastDetail().getContactMechanism()));
    }

    @Override
    public void doLock(ContactTelephoneEdit edit, PartyContactMechanism partyContactMechanism) {
        var contactControl = Session.getModelController(ContactControl.class);
        var geoControl = Session.getModelController(GeoControl.class);
        var contactMechanism = partyContactMechanism.getLastDetail().getContactMechanism();
        var contactMechanismDetail = contactMechanism.getLastDetail();
        var contactTelephone = contactControl.getContactTelephone(contactMechanism);
        var partyContactMechanismDetail = partyContactMechanism.getLastDetail();

        edit.setAllowSolicitation(contactMechanismDetail.getAllowSolicitation().toString());
        edit.setCountryName(geoControl.getAliasForCountry(contactTelephone.getCountryGeoCode()));
        edit.setAreaCode(contactTelephone.getAreaCode());
        edit.setTelephoneNumber(contactTelephone.getTelephoneNumber());
        edit.setTelephoneExtension(contactTelephone.getTelephoneExtension());
        edit.setDescription(partyContactMechanismDetail.getDescription());
    }

    GeoCode countryGeoCode;

    @Override
    public void canUpdate(PartyContactMechanism partyContactMechanism) {
        var geoControl = Session.getModelController(GeoControl.class);
        var countryName = edit.getCountryName();
        var countryAlias = StringUtils.getInstance().cleanStringToName(countryName).toUpperCase(Locale.getDefault());

        countryGeoCode = geoControl.getCountryByAlias(countryAlias);

        if(countryGeoCode != null) {
            var geoCodeCountry = geoControl.getGeoCodeCountry(countryGeoCode);
            var areaCode = edit.getAreaCode();

            if(!geoCodeCountry.getAreaCodeRequired() || areaCode != null) {
                var areaCodePattern = geoCodeCountry.getAreaCodePattern();
                var pattern = areaCodePattern == null? null: Pattern.compile(areaCodePattern);

                if(pattern == null || pattern.matcher(areaCode).matches()) {
                    var telephoneNumberPattern = geoCodeCountry.getTelephoneNumberPattern();
                    var telephoneNumber = edit.getTelephoneNumber();

                    pattern = telephoneNumberPattern == null? null: Pattern.compile(telephoneNumberPattern);

                    if(!(pattern == null || pattern.matcher(telephoneNumber).matches())) {
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
    }

    @Override
    public void doUpdate(PartyContactMechanism partyContactMechanism) {
        var contactControl = Session.getModelController(ContactControl.class);
        var updatedBy = getPartyPK();
        var contactMechanism = partyContactMechanism.getLastDetail().getContactMechanism();
        var contactMechanismDetailValue = contactControl.getContactMechanismDetailValue(contactMechanism.getLastDetail());
        var contactTelephoneValue = contactControl.getContactTelephoneValueForUpdate(contactMechanism);
        var partyContactMechanismDetailValue = contactControl.getPartyContactMechanismDetailValueForUpdate(partyContactMechanism);

        contactMechanismDetailValue.setAllowSolicitation(Boolean.valueOf(edit.getAllowSolicitation()));
        contactTelephoneValue.setCountryGeoCodePK(countryGeoCode.getPrimaryKey());
        contactTelephoneValue.setAreaCode(edit.getAreaCode());
        contactTelephoneValue.setTelephoneNumber(edit.getTelephoneNumber());
        contactTelephoneValue.setTelephoneExtension(edit.getTelephoneExtension());
        partyContactMechanismDetailValue.setDescription(edit.getDescription());

        contactControl.updateContactMechanismFromValue(contactMechanismDetailValue, updatedBy);
        contactControl.updateContactTelephoneFromValue(contactTelephoneValue, updatedBy);
        contactControl.updatePartyContactMechanismFromValue(partyContactMechanismDetailValue, updatedBy);
    }

}

