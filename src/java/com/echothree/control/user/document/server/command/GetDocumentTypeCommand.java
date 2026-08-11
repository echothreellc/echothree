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

package com.echothree.control.user.document.server.command;

import com.echothree.control.user.document.common.form.GetDocumentTypeForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.document.server.logic.DocumentTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetDocumentTypeCommand
        extends BaseSingleEntityCommand<DocumentType, GetDocumentTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.DocumentType.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("DocumentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    DocumentControl documentControl;

    @Inject
    DocumentTypeLogic documentTypeLogic;

    /** Creates a new instance of GetDocumentTypeCommand */
    public GetDocumentTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected DocumentType getEntity() {
        var documentType = documentTypeLogic.getDocumentTypeByUniversalSpec(this, form, true);

        if(documentType != null) {
            sendEvent(documentType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return documentType;
    }

    @Override
    protected BaseResult getResult(DocumentType documentType) {
        var result = DocumentResultFactory.getGetDocumentTypeResult();

        if(documentType != null) {
            result.setDocumentType(documentControl.getDocumentTypeTransfer(getUserVisit(), documentType));
        }

        return result;
    }
    
}
