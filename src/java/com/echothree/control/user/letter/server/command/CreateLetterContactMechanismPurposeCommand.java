// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.letter.common.form.CreateLetterContactMechanismPurposeForm;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.letter.server.LetterControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.entity.LetterContactMechanismPurpose;
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

public class CreateLetterContactMechanismPurposeCommand
        extends BaseSimpleCommand<CreateLetterContactMechanismPurposeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                new SecurityRoleDefinition(SecurityRoleGroups.LetterContactMechanismPurpose.name(), SecurityRoles.Create.name())
                )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LetterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Priority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("ContactMechanismPurposeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateLetterContactMechanismPurposeCommand */
    public CreateLetterContactMechanismPurposeCommand(UserVisitPK userVisitPK, CreateLetterContactMechanismPurposeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        String chainKindName = form.getChainKindName();
        ChainKind chainKind = chainControl.getChainKindByName(chainKindName);
        
        if(chainKind != null) {
            String chainTypeName = form.getChainTypeName();
            ChainType chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);
            
            if(chainType != null) {
                LetterControl letterControl = (LetterControl)Session.getModelController(LetterControl.class);
                String letterName = form.getLetterName();
                Letter letter = letterControl.getLetterByName(chainType, letterName);
                
                if(letter != null) {
                    Integer priority = Integer.valueOf(form.getPriority());
                    LetterContactMechanismPurpose letterContactMechanismPurpose = letterControl.getLetterContactMechanismPurpose(letter,
                            priority);
                    
                    if(letterContactMechanismPurpose == null) {
                        ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
                        String contactMechanismPurposeName = form.getContactMechanismPurposeName();
                        ContactMechanismPurpose contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(contactMechanismPurposeName);
                        
                        if(contactMechanismPurpose != null) {
                            String contactMechanismTypeName = contactMechanismPurpose.getContactMechanismType().getContactMechanismTypeName();
                            
                            if(contactMechanismTypeName.equals(ContactMechanismTypes.EMAIL_ADDRESS.name())
                                    || contactMechanismTypeName.equals(ContactMechanismTypes.POSTAL_ADDRESS.name())) {
                                letterControl.createLetterContactMechanismPurpose(letter, priority, contactMechanismPurpose,
                                        getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.InvalidContactMechanismType.name(), contactMechanismTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownContactMechanismPurposeName.name(), contactMechanismPurposeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateLetterContactMechanismPurpose.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLetterName.name(), letterName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }
        
        return null;
    }
    
}
