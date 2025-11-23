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
import com.echothree.control.user.document.common.edit.DocumentTypeEdit;
import com.echothree.control.user.document.common.form.EditDocumentTypeForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.control.user.document.common.result.EditDocumentTypeResult;
import com.echothree.control.user.document.common.spec.DocumentTypeSpec;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditDocumentTypeCommand
        extends BaseAbstractEditCommand<DocumentTypeSpec, DocumentTypeEdit, EditDocumentTypeResult, DocumentType, DocumentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.DocumentType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DocumentTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DocumentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentDocumentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MaximumPages", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditDocumentTypeCommand */
    public EditDocumentTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditDocumentTypeResult getResult() {
        return DocumentResultFactory.getEditDocumentTypeResult();
    }

    @Override
    public DocumentTypeEdit getEdit() {
        return DocumentEditFactory.getDocumentTypeEdit();
    }

    @Override
    public DocumentType getEntity(EditDocumentTypeResult result) {
        var documentControl = Session.getModelController(DocumentControl.class);
        DocumentType documentType;
        var documentTypeName = spec.getDocumentTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            documentType = documentControl.getDocumentTypeByName(documentTypeName);
        } else { // EditMode.UPDATE
            documentType = documentControl.getDocumentTypeByNameForUpdate(documentTypeName);
        }

        if(documentType != null) {
            result.setDocumentType(documentControl.getDocumentTypeTransfer(getUserVisit(), documentType));
        } else {
            addExecutionError(ExecutionErrors.UnknownDocumentTypeName.name(), documentTypeName);
        }

        return documentType;
    }

    @Override
    public DocumentType getLockEntity(DocumentType documentType) {
        return documentType;
    }

    @Override
    public void fillInResult(EditDocumentTypeResult result, DocumentType documentType) {
        var documentControl = Session.getModelController(DocumentControl.class);

        result.setDocumentType(documentControl.getDocumentTypeTransfer(getUserVisit(), documentType));
    }

    DocumentType parentDocumentType = null;
    MimeTypeUsageType mimeTypeUsageType = null;

    @Override
    public void doLock(DocumentTypeEdit edit, DocumentType documentType) {
        var documentControl = Session.getModelController(DocumentControl.class);
        var documentTypeDescription = documentControl.getDocumentTypeDescription(documentType, getPreferredLanguage());
        var documentTypeDetail = documentType.getLastDetail();

        parentDocumentType = documentTypeDetail.getParentDocumentType();
        mimeTypeUsageType = documentTypeDetail.getMimeTypeUsageType();

        edit.setDocumentTypeName(documentTypeDetail.getDocumentTypeName());
        edit.setParentDocumentTypeName(parentDocumentType == null ? null : parentDocumentType.getLastDetail().getDocumentTypeName());
        edit.setMimeTypeUsageTypeName(mimeTypeUsageType == null ? null : mimeTypeUsageType.getMimeTypeUsageTypeName());
        edit.setMaximumPages(documentTypeDetail.getMaximumPages().toString());
        edit.setIsDefault(documentTypeDetail.getIsDefault().toString());
        edit.setSortOrder(documentTypeDetail.getSortOrder().toString());

        if(documentTypeDescription != null) {
            edit.setDescription(documentTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(DocumentType documentType) {
        var documentControl = Session.getModelController(DocumentControl.class);
        var documentTypeName = edit.getDocumentTypeName();
        var duplicateDocumentType = documentControl.getDocumentTypeByName(documentTypeName);

        if(duplicateDocumentType == null || documentType.equals(duplicateDocumentType)) {
            var parentDocumentTypeName = edit.getParentDocumentTypeName();

            if(parentDocumentTypeName != null) {
                parentDocumentType = documentControl.getDocumentTypeByName(parentDocumentTypeName);
            }

            if(parentDocumentTypeName == null || parentDocumentType != null) {
                if(documentControl.isParentDocumentTypeSafe(documentType, parentDocumentType)) {
                    var mimeTypeUsageTypeName = edit.getMimeTypeUsageTypeName();

                    if(mimeTypeUsageTypeName != null) {
                        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);

                        mimeTypeUsageType = mimeTypeControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);
                    }

                    if(mimeTypeUsageTypeName != null && mimeTypeUsageType == null) {
                        addExecutionError(ExecutionErrors.UnknownMimeTypeUsageTypeName.name(), mimeTypeUsageTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParentDocumentType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentDocumentTypeName.name(), parentDocumentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateDocumentTypeName.name(), documentTypeName);
        }
    }

    @Override
    public void doUpdate(DocumentType documentType) {
        var documentControl = Session.getModelController(DocumentControl.class);
        var partyPK = getPartyPK();
        var documentTypeDetailValue = documentControl.getDocumentTypeDetailValueForUpdate(documentType);
        var documentTypeDescription = documentControl.getDocumentTypeDescriptionForUpdate(documentType, getPreferredLanguage());
        var description = edit.getDescription();

        documentTypeDetailValue.setDocumentTypeName(edit.getDocumentTypeName());
        documentTypeDetailValue.setParentDocumentTypePK(parentDocumentType == null ? null : parentDocumentType.getPrimaryKey());
        documentTypeDetailValue.setMimeTypeUsageTypePK(mimeTypeUsageType == null ? null : mimeTypeUsageType.getPrimaryKey());
        documentTypeDetailValue.setMaximumPages(Integer.valueOf(edit.getMaximumPages()));
        documentTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        documentTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        documentControl.updateDocumentTypeFromValue(documentTypeDetailValue, partyPK);

        if(documentTypeDescription == null && description != null) {
            documentControl.createDocumentTypeDescription(documentType, getPreferredLanguage(), description, partyPK);
        } else {
            if(documentTypeDescription != null && description == null) {
                documentControl.deleteDocumentTypeDescription(documentTypeDescription, partyPK);
            } else {
                if(documentTypeDescription != null && description != null) {
                    var documentTypeDescriptionValue = documentControl.getDocumentTypeDescriptionValue(documentTypeDescription);

                    documentTypeDescriptionValue.setDescription(description);
                    documentControl.updateDocumentTypeDescriptionFromValue(documentTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
