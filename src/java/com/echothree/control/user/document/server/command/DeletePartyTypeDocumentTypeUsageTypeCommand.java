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

package com.echothree.control.user.document.server.command;

import com.echothree.control.user.document.common.form.DeletePartyTypeDocumentTypeUsageTypeForm;
import com.echothree.model.control.document.server.DocumentControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageType;
import com.echothree.model.data.document.server.entity.PartyTypeDocumentTypeUsageType;
import com.echothree.model.data.party.server.entity.PartyType;
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

public class DeletePartyTypeDocumentTypeUsageTypeCommand
        extends BaseSimpleCommand<DeletePartyTypeDocumentTypeUsageTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyTypeDocumentTypeUsageType.name(), SecurityRoles.Delete.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DocumentTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeletePartyTypeDocumentTypeUsageTypeCommand */
    public DeletePartyTypeDocumentTypeUsageTypeCommand(UserVisitPK userVisitPK, DeletePartyTypeDocumentTypeUsageTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String partyTypeName = form.getPartyTypeName();
        PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);
        
        if(partyType != null) {
            var documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
            String documentTypeUsageTypeName = form.getDocumentTypeUsageTypeName();
            DocumentTypeUsageType documentTypeUsageType = documentControl.getDocumentTypeUsageTypeByName(documentTypeUsageTypeName);
            
            if(documentTypeUsageType != null) {
                PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType = documentControl.getPartyTypeDocumentTypeUsageTypeForUpdate(partyType, documentTypeUsageType);
                
                if(partyTypeDocumentTypeUsageType != null) {
                    documentControl.deletePartyTypeDocumentTypeUsageType(partyTypeDocumentTypeUsageType, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyTypeDocumentTypeUsageType.name(), partyTypeName, documentTypeUsageTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownDocumentTypeUsageTypeName.name(), documentTypeUsageTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
        }
        
        return null;
    }
    
}
