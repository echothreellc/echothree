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

import com.echothree.control.user.document.common.form.GetDocumentTypeUsagesForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.document.server.logic.DocumentTypeLogic;
import com.echothree.model.control.document.server.logic.DocumentTypeUsageTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.document.server.entity.DocumentTypeUsage;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageType;
import com.echothree.model.data.document.server.factory.DocumentTypeUsageFactory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetDocumentTypeUsagesCommand
        extends BasePaginatedMultipleEntitiesCommand<DocumentTypeUsage, GetDocumentTypeUsagesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.DocumentTypeUsage.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("DocumentTypeUsageTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DocumentTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    DocumentControl documentControl;

    @Inject
    DocumentTypeLogic documentTypeLogic;

    @Inject
    DocumentTypeUsageTypeLogic documentTypeUsageTypeLogic;

    /** Creates a new instance of GetDocumentTypeUsagesCommand */
    public GetDocumentTypeUsagesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private DocumentTypeUsageType documentTypeUsageType;
    private DocumentType documentType;

    @Override
    protected void handleForm() {
        var documentTypeUsageTypeName = form.getDocumentTypeUsageTypeName();
        var documentTypeName = form.getDocumentTypeName();
        var parameterCount = (documentTypeUsageTypeName == null ? 0 : 1) + (documentTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(documentTypeUsageTypeName != null) {
                documentTypeUsageType = documentTypeUsageTypeLogic.getDocumentTypeUsageTypeByName(this,
                        documentTypeUsageTypeName);
            } else {
                documentType = documentTypeLogic.getDocumentTypeByName(this, documentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : documentTypeUsageType == null
                ? documentControl.countDocumentTypeUsagesByDocumentType(documentType)
                : documentControl.countDocumentTypeUsagesByDocumentTypeUsageType(documentTypeUsageType);
    }

    @Override
    protected Collection<DocumentTypeUsage> getEntities() {
        return hasExecutionErrors() ? null : documentTypeUsageType == null
                ? documentControl.getDocumentTypeUsagesByDocumentType(documentType)
                : documentControl.getDocumentTypeUsagesByDocumentTypeUsageType(documentTypeUsageType);
    }

    @Override
    protected BaseResult getResult(Collection<DocumentTypeUsage> entities) {
        var result = DocumentResultFactory.getGetDocumentTypeUsagesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(documentTypeUsageType == null) {
                result.setDocumentType(documentControl.getDocumentTypeTransfer(userVisit, documentType));
            } else {
                result.setDocumentTypeUsageType(documentControl.getDocumentTypeUsageTypeTransfer(userVisit,
                        documentTypeUsageType));
            }

            if(session.hasLimit(DocumentTypeUsageFactory.class)) {
                result.setDocumentTypeUsageCount(getTotalEntities());
            }

            result.setDocumentTypeUsages(documentControl.getDocumentTypeUsageTransfers(userVisit, entities));
        }

        return result;
    }
    
}
