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

import com.echothree.control.user.document.common.edit.DocumentDescriptionEdit;
import com.echothree.control.user.document.common.edit.DocumentEditFactory;
import com.echothree.control.user.document.common.form.EditDocumentDescriptionForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.control.user.document.common.result.EditDocumentDescriptionResult;
import com.echothree.control.user.document.common.spec.DocumentDescriptionSpec;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.document.server.entity.DocumentDescription;
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
public class EditDocumentDescriptionCommand
        extends BaseAbstractEditCommand<DocumentDescriptionSpec, DocumentDescriptionEdit, EditDocumentDescriptionResult, DocumentDescription, Document> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Document.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DocumentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditDocumentDescriptionCommand */
    public EditDocumentDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditDocumentDescriptionResult getResult() {
        return DocumentResultFactory.getEditDocumentDescriptionResult();
    }

    @Override
    public DocumentDescriptionEdit getEdit() {
        return DocumentEditFactory.getDocumentDescriptionEdit();
    }

    @Override
    public DocumentDescription getEntity(EditDocumentDescriptionResult result) {
        var documentControl = Session.getModelController(DocumentControl.class);
        DocumentDescription documentDescription = null;
        var documentName = spec.getDocumentName();
        var document = documentControl.getDocumentByName(documentName);

        if(document != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    documentDescription = documentControl.getDocumentDescription(document, language);
                } else { // EditMode.UPDATE
                    documentDescription = documentControl.getDocumentDescriptionForUpdate(document, language);
                }

                if(documentDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownDocumentDescription.name(), documentName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownDocumentName.name(), documentName);
        }

        return documentDescription;
    }

    @Override
    public Document getLockEntity(DocumentDescription documentDescription) {
        return documentDescription.getDocument();
    }

    @Override
    public void fillInResult(EditDocumentDescriptionResult result, DocumentDescription documentDescription) {
        var documentControl = Session.getModelController(DocumentControl.class);

        result.setDocumentDescription(documentControl.getDocumentDescriptionTransfer(getUserVisit(), documentDescription));
    }

    @Override
    public void doLock(DocumentDescriptionEdit edit, DocumentDescription documentDescription) {
        edit.setDescription(documentDescription.getDescription());
    }

    @Override
    public void doUpdate(DocumentDescription documentDescription) {
        var documentControl = Session.getModelController(DocumentControl.class);
        var documentDescriptionValue = documentControl.getDocumentDescriptionValue(documentDescription);
        documentDescriptionValue.setDescription(edit.getDescription());

        documentControl.updateDocumentDescriptionFromValue(documentDescriptionValue, getPartyPK());
    }
    
}
