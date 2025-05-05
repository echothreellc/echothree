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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.CreateContentWebAddressForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
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

public class CreateContentWebAddressCommand
        extends BaseSimpleCommand<CreateContentWebAddressForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentWebAddress.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, true, null, null),
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateContentWebAddressCommand */
    public CreateContentWebAddressCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = ContentResultFactory.getCreateContentWebAddressResult();
        var contentControl = Session.getModelController(ContentControl.class);
        var contentWebAddressName = form.getContentWebAddressName();
        var contentWebAddress = contentControl.getContentWebAddressByName(contentWebAddressName);
        
        if(contentWebAddress == null) {
            var contentCollectionName = form.getContentCollectionName();
            var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
            
            if(contentCollection != null) {
                var description = form.getDescription();
                var partyPK = getPartyPK();
                
                contentWebAddress = contentControl.createContentWebAddress(contentWebAddressName, contentCollection, partyPK);
                
                if(description != null) {
                    var language = getPreferredLanguage();
                    
                    contentControl.createContentWebAddressDescription(contentWebAddress, language, description, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateContentWebAddressName.name(), contentWebAddressName);
        }


        if(contentWebAddress != null) {
            result.setContentWebAddressName(contentWebAddress.getLastDetail().getContentWebAddressName());
            result.setEntityRef(contentWebAddress.getPrimaryKey().getEntityRef());
        }

        return result;
    }
    
}
