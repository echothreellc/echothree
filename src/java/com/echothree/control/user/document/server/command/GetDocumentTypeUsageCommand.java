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

import com.echothree.control.user.document.common.form.GetDocumentTypeUsageForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.document.server.logic.DocumentTypeLogic;
import com.echothree.model.control.document.server.logic.DocumentTypeUsageTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.document.server.entity.DocumentTypeUsage;
import com.echothree.util.common.message.ExecutionErrors;
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
public class GetDocumentTypeUsageCommand
        extends BaseSingleEntityCommand<DocumentTypeUsage, GetDocumentTypeUsageForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.DocumentTypeUsage.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("DocumentTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DocumentTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    DocumentControl documentControl;

    @Inject
    DocumentTypeLogic documentTypeLogic;

    @Inject
    DocumentTypeUsageTypeLogic documentTypeUsageTypeLogic;

    /** Creates a new instance of GetDocumentTypeUsageCommand */
    public GetDocumentTypeUsageCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected DocumentTypeUsage getEntity() {
        DocumentTypeUsage documentTypeUsage = null;
        var documentTypeUsageTypeName = form.getDocumentTypeUsageTypeName();
        var documentTypeUsageType = documentTypeUsageTypeLogic.getDocumentTypeUsageTypeByName(this,
                documentTypeUsageTypeName);

        if(!hasExecutionErrors()) {
            var documentTypeName = form.getDocumentTypeName();
            var documentType = documentTypeLogic.getDocumentTypeByName(this, documentTypeName);

            if(!hasExecutionErrors()) {
                documentTypeUsage = documentControl.getDocumentTypeUsage(documentTypeUsageType, documentType);

                if(documentTypeUsage == null) {
                    addExecutionError(ExecutionErrors.UnknownDocumentTypeUsage.name(), documentTypeUsageType.getLastDetail().getDocumentTypeUsageTypeName(),
                            documentType.getLastDetail().getDocumentTypeName());
                }
            }
        }

        return documentTypeUsage;
    }

    @Override
    protected BaseResult getResult(DocumentTypeUsage documentTypeUsage) {
        var result = DocumentResultFactory.getGetDocumentTypeUsageResult();

        if(documentTypeUsage != null) {
            result.setDocumentTypeUsage(documentControl.getDocumentTypeUsageTransfer(getUserVisit(), documentTypeUsage));
        }

        return result;
    }
    
}
