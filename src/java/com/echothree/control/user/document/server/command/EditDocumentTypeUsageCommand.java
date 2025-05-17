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

package com.echothree.control.user.document.server.command;

import com.echothree.control.user.document.common.edit.DocumentEditFactory;
import com.echothree.control.user.document.common.edit.DocumentTypeUsageEdit;
import com.echothree.control.user.document.common.form.EditDocumentTypeUsageForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.control.user.document.common.result.EditDocumentTypeUsageResult;
import com.echothree.control.user.document.common.spec.DocumentTypeUsageSpec;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.document.server.entity.DocumentTypeUsage;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
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

public class EditDocumentTypeUsageCommand
        extends BaseAbstractEditCommand<DocumentTypeUsageSpec, DocumentTypeUsageEdit, EditDocumentTypeUsageResult, DocumentTypeUsage, DocumentTypeUsageType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.DocumentTypeUsage.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DocumentTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DocumentTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("MaximumInstances", FieldType.SIGNED_INTEGER, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditDocumentTypeUsageCommand */
    public EditDocumentTypeUsageCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditDocumentTypeUsageResult getResult() {
        return DocumentResultFactory.getEditDocumentTypeUsageResult();
    }

    @Override
    public DocumentTypeUsageEdit getEdit() {
        return DocumentEditFactory.getDocumentTypeUsageEdit();
    }

    @Override
    public DocumentTypeUsage getEntity(EditDocumentTypeUsageResult result) {
        var documentControl = Session.getModelController(DocumentControl.class);
        DocumentTypeUsage documentTypeUsage = null;
        var documentTypeUsageTypeName = spec.getDocumentTypeUsageTypeName();
        var documentTypeUsageType = documentControl.getDocumentTypeUsageTypeByName(documentTypeUsageTypeName);

        if(documentTypeUsageType != null) {
            var documentTypeName = spec.getDocumentTypeName();
            var documentType = documentControl.getDocumentTypeByName(documentTypeName);

            if(documentType != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    documentTypeUsage = documentControl.getDocumentTypeUsage(documentTypeUsageType, documentType);
                } else { // EditMode.UPDATE
                    documentTypeUsage = documentControl.getDocumentTypeUsageForUpdate(documentTypeUsageType, documentType);
                }

                if(documentTypeUsage == null) {
                    addExecutionError(ExecutionErrors.UnknownDocumentTypeUsage.name(), documentTypeUsageTypeName, documentTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownDocumentTypeName.name(), documentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownDocumentTypeUsageTypeName.name(), documentTypeUsageTypeName);
        }

        return documentTypeUsage;
    }

    @Override
    public DocumentTypeUsageType getLockEntity(DocumentTypeUsage documentTypeUsage) {
        return documentTypeUsage.getDocumentTypeUsageType();
    }

    @Override
    public void fillInResult(EditDocumentTypeUsageResult result, DocumentTypeUsage documentTypeUsage) {
        var documentControl = Session.getModelController(DocumentControl.class);

        result.setDocumentTypeUsage(documentControl.getDocumentTypeUsageTransfer(getUserVisit(), documentTypeUsage));
    }

    @Override
    public void doLock(DocumentTypeUsageEdit edit, DocumentTypeUsage documentTypeUsage) {
        edit.setIsDefault(documentTypeUsage.getIsDefault().toString());
        edit.setSortOrder(documentTypeUsage.getSortOrder().toString());
        edit.setMaximumInstances(documentTypeUsage.getMaximumInstances().toString());
    }

    @Override
    public void doUpdate(DocumentTypeUsage documentTypeUsage) {
        var documentControl = Session.getModelController(DocumentControl.class);
        var documentTypeUsageValue = documentControl.getDocumentTypeUsageValueForUpdate(documentTypeUsage);

        documentTypeUsageValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        documentTypeUsageValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
        documentTypeUsageValue.setMaximumInstances(Integer.valueOf(edit.getMaximumInstances()));

        documentControl.updateDocumentTypeUsageFromValue(documentTypeUsageValue, getPartyPK());
    }
    
}
