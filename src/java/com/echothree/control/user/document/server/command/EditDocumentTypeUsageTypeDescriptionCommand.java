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

import com.echothree.control.user.document.common.edit.DocumentEditFactory;
import com.echothree.control.user.document.common.edit.DocumentTypeUsageTypeDescriptionEdit;
import com.echothree.control.user.document.common.form.EditDocumentTypeUsageTypeDescriptionForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.control.user.document.common.result.EditDocumentTypeUsageTypeDescriptionResult;
import com.echothree.control.user.document.common.spec.DocumentTypeUsageTypeDescriptionSpec;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageType;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageTypeDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditDocumentTypeUsageTypeDescriptionCommand
        extends BaseAbstractEditCommand<DocumentTypeUsageTypeDescriptionSpec, DocumentTypeUsageTypeDescriptionEdit, EditDocumentTypeUsageTypeDescriptionResult, DocumentTypeUsageTypeDescription, DocumentTypeUsageType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.DocumentTypeUsageType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DocumentTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditDocumentTypeUsageTypeDescriptionCommand */
    public EditDocumentTypeUsageTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditDocumentTypeUsageTypeDescriptionResult getResult() {
        return DocumentResultFactory.getEditDocumentTypeUsageTypeDescriptionResult();
    }

    @Override
    public DocumentTypeUsageTypeDescriptionEdit getEdit() {
        return DocumentEditFactory.getDocumentTypeUsageTypeDescriptionEdit();
    }

    @Override
    public DocumentTypeUsageTypeDescription getEntity(EditDocumentTypeUsageTypeDescriptionResult result) {
        var documentControl = Session.getModelController(DocumentControl.class);
        DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription = null;
        var documentTypeUsageTypeName = spec.getDocumentTypeUsageTypeName();
        var documentTypeUsageType = documentControl.getDocumentTypeUsageTypeByName(documentTypeUsageTypeName);

        if(documentTypeUsageType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    documentTypeUsageTypeDescription = documentControl.getDocumentTypeUsageTypeDescription(documentTypeUsageType, language);
                } else { // EditMode.UPDATE
                    documentTypeUsageTypeDescription = documentControl.getDocumentTypeUsageTypeDescriptionForUpdate(documentTypeUsageType, language);
                }

                if(documentTypeUsageTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownDocumentTypeUsageTypeDescription.name(), documentTypeUsageTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownDocumentTypeUsageTypeName.name(), documentTypeUsageTypeName);
        }

        return documentTypeUsageTypeDescription;
    }

    @Override
    public DocumentTypeUsageType getLockEntity(DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription) {
        return documentTypeUsageTypeDescription.getDocumentTypeUsageType();
    }

    @Override
    public void fillInResult(EditDocumentTypeUsageTypeDescriptionResult result, DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription) {
        var documentControl = Session.getModelController(DocumentControl.class);

        result.setDocumentTypeUsageTypeDescription(documentControl.getDocumentTypeUsageTypeDescriptionTransfer(getUserVisit(), documentTypeUsageTypeDescription));
    }

    @Override
    public void doLock(DocumentTypeUsageTypeDescriptionEdit edit, DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription) {
        edit.setDescription(documentTypeUsageTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription) {
        var documentControl = Session.getModelController(DocumentControl.class);
        var documentTypeUsageTypeDescriptionValue = documentControl.getDocumentTypeUsageTypeDescriptionValue(documentTypeUsageTypeDescription);
        documentTypeUsageTypeDescriptionValue.setDescription(edit.getDescription());

        documentControl.updateDocumentTypeUsageTypeDescriptionFromValue(documentTypeUsageTypeDescriptionValue, getPartyPK());
    }
    
}
