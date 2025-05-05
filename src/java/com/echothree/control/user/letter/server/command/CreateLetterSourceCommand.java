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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.common.form.CreateLetterSourceForm;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateLetterSourceCommand
        extends BaseSimpleCommand<CreateLetterSourceForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LetterSource.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LetterSourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmailAddressContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmailAddressContactMechanismAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmailAddressContactMechanismAlias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostalAddressContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostalAddressContactMechanismAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostalAddressContactMechanismAlias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LetterSourceContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LetterSourceContactMechanismAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LetterSourceContactMechanismAlias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateLetterSourceCommand */
    public CreateLetterSourceCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var letterControl = Session.getModelController(LetterControl.class);
        var letterSourceName = form.getLetterSourceName();
        var letterSource = letterControl.getLetterSourceByName(letterSourceName);
        
        if(letterSource == null) {
            var partyName = form.getPartyName();
            var companyName = form.getCompanyName();
            var parameterCount = (partyName != null? 1: 0) + (companyName != null? 1: 0);
            
            if(parameterCount == 1) {
                var partyControl = Session.getModelController(PartyControl.class);
                Party companyParty = null;
                
                if(partyName != null) {
                    companyParty = partyControl.getPartyByName(partyName);
                    
                    if(companyParty == null) {
                        addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                    } else {
                        var partyTypeName = companyParty.getLastDetail().getPartyType().getPartyTypeName();
                        
                        if(!partyTypeName.equals(PartyTypes.COMPANY.name())) {
                            addExecutionError(ExecutionErrors.InvalidPartyType.name(), partyTypeName);
                        }
                    }
                } else {
                    var partyCompany = partyControl.getPartyCompanyByName(companyName);
                    
                    if(partyCompany == null) {
                        addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
                    } else {
                        companyParty = partyCompany.getParty();
                    }
                }
                
                if(!hasExecutionErrors()) {
                    var contactControl = Session.getModelController(ContactControl.class);
                    var letterSourceCommandUtil = LetterSourceCommandUtil.getInstance();
                    var emailAddressPartyContactMechanism = letterSourceCommandUtil.getEmailAddressContactMechanism(this, form,
                            contactControl, companyParty);
                    
                    if(!hasExecutionErrors()) {
                        var postalAddressPartyContactMechanism = letterSourceCommandUtil.getPostalAddressContactMechanism(this, form,
                                contactControl, companyParty);
                        
                        if(!hasExecutionErrors()) {
                            var letterSourcePartyContactMechanism = letterSourceCommandUtil.getLetterSourceContactMechanism(this, form,
                                    contactControl, companyParty);
                            
                            if(!hasExecutionErrors()) {
                                var partyPK = getPartyPK();
                                var isDefault = Boolean.valueOf(form.getIsDefault());
                                var sortOrder = Integer.valueOf(form.getSortOrder());
                                var description = form.getDescription();
                                
                                letterSource = letterControl.createLetterSource(letterSourceName, companyParty,
                                        emailAddressPartyContactMechanism, postalAddressPartyContactMechanism,
                                        letterSourcePartyContactMechanism, isDefault, sortOrder, partyPK);
                                
                                if(description != null) {
                                    letterControl.createLetterSourceDescription(letterSource, getPreferredLanguage(), description, partyPK);
                                }
                            }
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateLetterSourceName.name(), letterSourceName);
        }
        
        return null;
    }
    
}
