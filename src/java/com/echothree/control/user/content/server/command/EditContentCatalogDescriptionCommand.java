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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.edit.ContentCatalogDescriptionEdit;
import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.form.EditContentCatalogDescriptionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentCatalogDescriptionResult;
import com.echothree.control.user.content.common.spec.ContentCatalogDescriptionSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogDescription;
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

public class EditContentCatalogDescriptionCommand
        extends BaseAbstractEditCommand<ContentCatalogDescriptionSpec, ContentCatalogDescriptionEdit, EditContentCatalogDescriptionResult, ContentCatalogDescription, ContentCatalog> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCatalog.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContentCatalogDescriptionCommand */
    public EditContentCatalogDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentCatalogDescriptionResult getResult() {
        return ContentResultFactory.getEditContentCatalogDescriptionResult();
    }
    
    @Override
    public ContentCatalogDescriptionEdit getEdit() {
        return ContentEditFactory.getContentCatalogDescriptionEdit();
    }
    
    @Override
    public ContentCatalogDescription getEntity(EditContentCatalogDescriptionResult result) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentCatalogDescription contentCatalogDescription = null;
        var contentCollectionName = spec.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            var contentCatalogName = spec.getContentCatalogName();
            var contentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);
            
            if(contentCatalog != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                result.setContentCatalog(contentControl.getContentCatalogTransfer(getUserVisit(), contentCatalog));

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        contentCatalogDescription = contentControl.getContentCatalogDescription(contentCatalog, language);
                    } else { // EditMode.UPDATE
                        contentCatalogDescription = contentControl.getContentCatalogDescriptionForUpdate(contentCatalog, language);
                    }

                    if(contentCatalogDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownContentCatalogDescription.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentCatalogName.name(), contentCatalogName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }

        return contentCatalogDescription;
    }
    
    @Override
    public ContentCatalog getLockEntity(ContentCatalogDescription contentCatalogDescription) {
        return contentCatalogDescription.getContentCatalog();
    }
    
    @Override
    public void fillInResult(EditContentCatalogDescriptionResult result, ContentCatalogDescription contentCatalogDescription) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentCatalogDescription(contentControl.getContentCatalogDescriptionTransfer(getUserVisit(), contentCatalogDescription));
    }
    
    @Override
    public void doLock(ContentCatalogDescriptionEdit edit, ContentCatalogDescription contentCatalogDescription) {
        edit.setDescription(contentCatalogDescription.getDescription());
    }
    
    @Override
    public void doUpdate(ContentCatalogDescription contentCatalogDescription) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCatalogDescriptionValue = contentControl.getContentCatalogDescriptionValue(contentCatalogDescription);
        contentCatalogDescriptionValue.setDescription(edit.getDescription());

        contentControl.updateContentCatalogDescriptionFromValue(contentCatalogDescriptionValue, getPartyPK());
    }
    
}
