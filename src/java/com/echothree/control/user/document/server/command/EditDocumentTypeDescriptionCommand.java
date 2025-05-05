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
import com.echothree.control.user.document.common.edit.DocumentTypeDescriptionEdit;
import com.echothree.control.user.document.common.form.EditDocumentTypeDescriptionForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.control.user.document.common.result.EditDocumentTypeDescriptionResult;
import com.echothree.control.user.document.common.spec.DocumentTypeDescriptionSpec;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.document.server.entity.DocumentTypeDescription;
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

public class EditDocumentTypeDescriptionCommand
        extends BaseAbstractEditCommand<DocumentTypeDescriptionSpec, DocumentTypeDescriptionEdit, EditDocumentTypeDescriptionResult, DocumentTypeDescription, DocumentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.DocumentType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DocumentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditDocumentTypeDescriptionCommand */
    public EditDocumentTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditDocumentTypeDescriptionResult getResult() {
        return DocumentResultFactory.getEditDocumentTypeDescriptionResult();
    }

    @Override
    public DocumentTypeDescriptionEdit getEdit() {
        return DocumentEditFactory.getDocumentTypeDescriptionEdit();
    }

    @Override
    public DocumentTypeDescription getEntity(EditDocumentTypeDescriptionResult result) {
        var documentControl = Session.getModelController(DocumentControl.class);
        DocumentTypeDescription documentTypeDescription = null;
        var documentTypeName = spec.getDocumentTypeName();
        var documentType = documentControl.getDocumentTypeByName(documentTypeName);

        if(documentType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    documentTypeDescription = documentControl.getDocumentTypeDescription(documentType, language);
                } else { // EditMode.UPDATE
                    documentTypeDescription = documentControl.getDocumentTypeDescriptionForUpdate(documentType, language);
                }

                if(documentTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownDocumentTypeDescription.name(), documentTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownDocumentTypeName.name(), documentTypeName);
        }

        return documentTypeDescription;
    }

    @Override
    public DocumentType getLockEntity(DocumentTypeDescription documentTypeDescription) {
        return documentTypeDescription.getDocumentType();
    }

    @Override
    public void fillInResult(EditDocumentTypeDescriptionResult result, DocumentTypeDescription documentTypeDescription) {
        var documentControl = Session.getModelController(DocumentControl.class);

        result.setDocumentTypeDescription(documentControl.getDocumentTypeDescriptionTransfer(getUserVisit(), documentTypeDescription));
    }

    @Override
    public void doLock(DocumentTypeDescriptionEdit edit, DocumentTypeDescription documentTypeDescription) {
        edit.setDescription(documentTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(DocumentTypeDescription documentTypeDescription) {
        var documentControl = Session.getModelController(DocumentControl.class);
        var documentTypeDescriptionValue = documentControl.getDocumentTypeDescriptionValue(documentTypeDescription);
        documentTypeDescriptionValue.setDescription(edit.getDescription());

        documentControl.updateDocumentTypeDescriptionFromValue(documentTypeDescriptionValue, getPartyPK());
    }
    
}
