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

package com.echothree.control.user.associate.server.command;

import com.echothree.control.user.associate.common.form.CreateAssociatePartyContactMechanismForm;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateAssociatePartyContactMechanismCommand
        extends BaseSimpleCommand<CreateAssociatePartyContactMechanismForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("AssociateName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("AssociatePartyContactMechanismName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactMechanismAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateAssociatePartyContactMechanismCommand */
    public CreateAssociatePartyContactMechanismCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var contactMechanismName = form.getContactMechanismName();
        var contactMechanismAliasTypeName = form.getContactMechanismAliasTypeName();
        var alias = form.getAlias();
        var parameterCount = (contactMechanismName == null ? 0 : 1) + (contactMechanismAliasTypeName == null && alias == null ? 0 : 1);
        
        if(parameterCount == 1) {
            var associateControl = Session.getModelController(AssociateControl.class);
            var associateProgramName = form.getAssociateProgramName();
            var associateProgram = associateControl.getAssociateProgramByName(associateProgramName);
            
            if(associateProgram != null) {
                var associateName = form.getAssociateName();
                var associate = associateControl.getAssociateByName(associateProgram, associateName);
                
                if(associate != null) {
                    var associatePartyContactMechanismName = form.getAssociatePartyContactMechanismName();
                    var associatePartyContactMechanism = associateControl.getAssociatePartyContactMechanismByName(associate,
                            associatePartyContactMechanismName);
                    
                    if(associatePartyContactMechanism == null) {
                        var contactControl = Session.getModelController(ContactControl.class);
                        ContactMechanism contactMechanism = null;
                        
                        if(contactMechanismName != null) {
                            contactMechanism = contactControl.getContactMechanismByName(contactMechanismName);
                            
                            if(contactMechanism == null) {
                                addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
                            }
                        } else {
                            var contactMechanismAliasType = contactControl.getContactMechanismAliasTypeByName(contactMechanismAliasTypeName);
                            
                            if(contactMechanismAliasType != null) {
                                var contactMechanismAlias = contactControl.getContactMechanismAliasByAlias(contactMechanismAliasType,
                                        alias);
                                
                                if(contactMechanismAlias != null) {
                                    contactMechanism = contactMechanismAlias.getContactMechanism();
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownContactMechanismAlias.name(), alias);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownContactMechanismAliasTypeName.name(), contactMechanismAliasTypeName);
                            }
                        }
                        
                        if(!hasExecutionErrors()) {
                            var partyContactMechanism = contactControl.getPartyContactMechanism(associate.getLastDetail().getParty(),
                                    contactMechanism);
                            
                            if(partyContactMechanism != null) {
                                var isDefault = Boolean.valueOf(form.getIsDefault());
                                var sortOrder = Integer.valueOf(form.getSortOrder());
                                
                                associateControl.createAssociatePartyContactMechanism(associate, associatePartyContactMechanismName,
                                        partyContactMechanism, isDefault, sortOrder, getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.UnknownPartyContactMechanism.name(), contactMechanismName);
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateAssociatePartyContactMechanismName.name(), associatePartyContactMechanismName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownAssociateName.name(), associateName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownAssociateProgramName.name(), associateProgramName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return null;
    }
    
}
