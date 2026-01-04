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

import com.echothree.control.user.contact.common.form.CreateContactTelephoneForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.common.workflow.TelephoneStatusConstants;
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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.string.StringUtils;
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
import java.util.Locale;
import java.util.regex.Pattern;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateContactTelephoneCommand
        extends BaseSimpleCommand<CreateContactTelephoneForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.CUSTOMER.name(), null),
                new PartyTypeDefinition(PartyTypes.VENDOR.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactMechanism.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("AreaCode", FieldType.STRING, false, 1L, 5L),
                new FieldDefinition("TelephoneNumber", FieldType.STRING, true, 1L, 25L),
                new FieldDefinition("TelephoneExtension", FieldType.NUMBERS, false, 1L, 10L),
                new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateContactTelephoneCommand */
    public CreateContactTelephoneCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected SecurityResult security() {
        var securityResult = super.security();

        return securityResult != null ? securityResult : selfOnly(form);
    }

    @Override
    protected BaseResult execute() {
        var result = ContactResultFactory.getCreateContactTelephoneResult();
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
                var areaCode = form.getAreaCode();
                
                if(!geoCodeCountry.getAreaCodeRequired() || areaCode != null) {
                    var areaCodePattern = geoCodeCountry.getAreaCodePattern();
                    var pattern = areaCodePattern == null? null: Pattern.compile(areaCodePattern);
                    
                    if(pattern == null || pattern.matcher(areaCode).matches()) {
                        var telephoneNumberPattern = geoCodeCountry.getTelephoneNumberPattern();
                        var telephoneNumber = form.getTelephoneNumber();
                        
                        pattern = telephoneNumberPattern == null? null: Pattern.compile(telephoneNumberPattern);
                        
                        if(pattern == null || pattern.matcher(telephoneNumber).matches()) {
                            var contactControl = Session.getModelController(ContactControl.class);
                            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                            var workflowControl = Session.getModelController(WorkflowControl.class);
                            BasePK createdBy = getPartyPK();
                            var telephoneExtension = form.getTelephoneExtension();
                            var allowSolicitation = Boolean.valueOf(form.getAllowSolicitation());
                            var description = form.getDescription();
                            var contactMechanismName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(null, SequenceTypes.CONTACT_MECHANISM.name());

                            var contactMechanismType = contactControl.getContactMechanismTypeByName(ContactMechanismTypes.TELECOM_ADDRESS.name());
                            var contactMechanism = contactControl.createContactMechanism(contactMechanismName,
                                    contactMechanismType, allowSolicitation, createdBy);
                            
                            contactControl.createContactTelephone(contactMechanism, countryGeoCode, areaCode, telephoneNumber,
                                    telephoneExtension, createdBy);
                            
                            contactControl.createPartyContactMechanism(party, contactMechanism, description, false, 1, createdBy);

                            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(contactMechanism.getPrimaryKey());
                            workflowControl.addEntityToWorkflowUsingNames(null, TelephoneStatusConstants.Workflow_TELEPHONE_STATUS,
                                    TelephoneStatusConstants.WorkflowEntrance_NEW_TELEPHONE, entityInstance, null, null, createdBy);
                            
                            result.setContactMechanismName(contactMechanism.getLastDetail().getContactMechanismName());
                            result.setEntityRef(contactMechanism.getPrimaryKey().getEntityRef());
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
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return result;
    }
    
}
