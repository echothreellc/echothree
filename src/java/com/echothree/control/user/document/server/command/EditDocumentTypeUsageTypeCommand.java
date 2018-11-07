// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.document.common.edit.DocumentTypeUsageTypeEdit;
import com.echothree.control.user.document.common.form.EditDocumentTypeUsageTypeForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.control.user.document.common.result.EditDocumentTypeUsageTypeResult;
import com.echothree.control.user.document.common.spec.DocumentTypeUsageTypeSpec;
import com.echothree.model.control.document.server.DocumentControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageType;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageTypeDescription;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageTypeDetail;
import com.echothree.model.data.document.server.value.DocumentTypeUsageTypeDescriptionValue;
import com.echothree.model.data.document.server.value.DocumentTypeUsageTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditDocumentTypeUsageTypeCommand
        extends BaseAbstractEditCommand<DocumentTypeUsageTypeSpec, DocumentTypeUsageTypeEdit, EditDocumentTypeUsageTypeResult, DocumentTypeUsageType, DocumentTypeUsageType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.DocumentTypeUsageType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DocumentTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DocumentTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditDocumentTypeUsageTypeCommand */
    public EditDocumentTypeUsageTypeCommand(UserVisitPK userVisitPK, EditDocumentTypeUsageTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditDocumentTypeUsageTypeResult getResult() {
        return DocumentResultFactory.getEditDocumentTypeUsageTypeResult();
    }

    @Override
    public DocumentTypeUsageTypeEdit getEdit() {
        return DocumentEditFactory.getDocumentTypeUsageTypeEdit();
    }

    @Override
    public DocumentTypeUsageType getEntity(EditDocumentTypeUsageTypeResult result) {
        DocumentControl documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
        DocumentTypeUsageType documentTypeUsageType = null;
        String documentTypeUsageTypeName = spec.getDocumentTypeUsageTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            documentTypeUsageType = documentControl.getDocumentTypeUsageTypeByName(documentTypeUsageTypeName);
        } else { // EditMode.UPDATE
            documentTypeUsageType = documentControl.getDocumentTypeUsageTypeByNameForUpdate(documentTypeUsageTypeName);
        }

        if(documentTypeUsageType != null) {
            result.setDocumentTypeUsageType(documentControl.getDocumentTypeUsageTypeTransfer(getUserVisit(), documentTypeUsageType));
        } else {
            addExecutionError(ExecutionErrors.UnknownDocumentTypeUsageTypeName.name(), documentTypeUsageTypeName);
        }

        return documentTypeUsageType;
    }

    @Override
    public DocumentTypeUsageType getLockEntity(DocumentTypeUsageType documentTypeUsageType) {
        return documentTypeUsageType;
    }

    @Override
    public void fillInResult(EditDocumentTypeUsageTypeResult result, DocumentTypeUsageType documentTypeUsageType) {
        DocumentControl documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);

        result.setDocumentTypeUsageType(documentControl.getDocumentTypeUsageTypeTransfer(getUserVisit(), documentTypeUsageType));
    }

    @Override
    public void doLock(DocumentTypeUsageTypeEdit edit, DocumentTypeUsageType documentTypeUsageType) {
        DocumentControl documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
        DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription = documentControl.getDocumentTypeUsageTypeDescription(documentTypeUsageType, getPreferredLanguage());
        DocumentTypeUsageTypeDetail documentTypeUsageTypeDetail = documentTypeUsageType.getLastDetail();

        edit.setDocumentTypeUsageTypeName(documentTypeUsageTypeDetail.getDocumentTypeUsageTypeName());
        edit.setIsDefault(documentTypeUsageTypeDetail.getIsDefault().toString());
        edit.setSortOrder(documentTypeUsageTypeDetail.getSortOrder().toString());

        if(documentTypeUsageTypeDescription != null) {
            edit.setDescription(documentTypeUsageTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(DocumentTypeUsageType documentTypeUsageType) {
        DocumentControl documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
        String documentTypeUsageTypeName = edit.getDocumentTypeUsageTypeName();
        DocumentTypeUsageType duplicateDocumentTypeUsageType = documentControl.getDocumentTypeUsageTypeByName(documentTypeUsageTypeName);

        if(duplicateDocumentTypeUsageType != null && !documentTypeUsageType.equals(duplicateDocumentTypeUsageType)) {
            addExecutionError(ExecutionErrors.DuplicateDocumentTypeUsageTypeName.name(), documentTypeUsageTypeName);
        }
    }

    @Override
    public void doUpdate(DocumentTypeUsageType documentTypeUsageType) {
        DocumentControl documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
        PartyPK partyPK = getPartyPK();
        DocumentTypeUsageTypeDetailValue documentTypeUsageTypeDetailValue = documentControl.getDocumentTypeUsageTypeDetailValueForUpdate(documentTypeUsageType);
        DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription = documentControl.getDocumentTypeUsageTypeDescriptionForUpdate(documentTypeUsageType, getPreferredLanguage());
        String description = edit.getDescription();

        documentTypeUsageTypeDetailValue.setDocumentTypeUsageTypeName(edit.getDocumentTypeUsageTypeName());
        documentTypeUsageTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        documentTypeUsageTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        documentControl.updateDocumentTypeUsageTypeFromValue(documentTypeUsageTypeDetailValue, partyPK);

        if(documentTypeUsageTypeDescription == null && description != null) {
            documentControl.createDocumentTypeUsageTypeDescription(documentTypeUsageType, getPreferredLanguage(), description, partyPK);
        } else {
            if(documentTypeUsageTypeDescription != null && description == null) {
                documentControl.deleteDocumentTypeUsageTypeDescription(documentTypeUsageTypeDescription, partyPK);
            } else {
                if(documentTypeUsageTypeDescription != null && description != null) {
                    DocumentTypeUsageTypeDescriptionValue documentTypeUsageTypeDescriptionValue = documentControl.getDocumentTypeUsageTypeDescriptionValue(documentTypeUsageTypeDescription);

                    documentTypeUsageTypeDescriptionValue.setDescription(description);
                    documentControl.updateDocumentTypeUsageTypeDescriptionFromValue(documentTypeUsageTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
