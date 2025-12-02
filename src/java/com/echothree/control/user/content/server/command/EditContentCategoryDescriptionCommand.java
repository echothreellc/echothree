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

import com.echothree.control.user.content.common.edit.ContentCategoryDescriptionEdit;
import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.form.EditContentCategoryDescriptionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentCategoryDescriptionResult;
import com.echothree.control.user.content.common.spec.ContentCategoryDescriptionSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryDescription;
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
public class EditContentCategoryDescriptionCommand
        extends BaseAbstractEditCommand<ContentCategoryDescriptionSpec, ContentCategoryDescriptionEdit, EditContentCategoryDescriptionResult, ContentCategoryDescription, ContentCategory> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCategory.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContentCategoryDescriptionCommand */
    public EditContentCategoryDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentCategoryDescriptionResult getResult() {
        return ContentResultFactory.getEditContentCategoryDescriptionResult();
    }
    
    @Override
    public ContentCategoryDescriptionEdit getEdit() {
        return ContentEditFactory.getContentCategoryDescriptionEdit();
    }
    
    @Override
    public ContentCategoryDescription getEntity(EditContentCategoryDescriptionResult result) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentCategoryDescription contentCategoryDescription = null;
        var contentCollectionName = spec.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            var contentCatalogName = spec.getContentCatalogName();
            var contentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);
            
            if(contentCatalog != null) {
                var contentCategoryName = spec.getContentCategoryName();
                var contentCategory = contentControl.getContentCategoryByName(contentCatalog, contentCategoryName);
                
                if(contentCategory != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var languageIsoName = spec.getLanguageIsoName();
                    var language = partyControl.getLanguageByIsoName(languageIsoName);
                    
                    result.setContentCategory(contentControl.getContentCategoryTransfer(getUserVisit(), contentCategory));
                    
                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                            contentCategoryDescription = contentControl.getContentCategoryDescription(contentCategory, language);
                        } else { // EditMode.UPDATE
                            contentCategoryDescription = contentControl.getContentCategoryDescriptionForUpdate(contentCategory, language);
                        }

                        if(contentCategoryDescription == null) {
                            addExecutionError(ExecutionErrors.UnknownContentCategoryDescription.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentCategoryName.name(), contentCategoryName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentCatalogName.name(), contentCatalogName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }

        return contentCategoryDescription;
    }
    
    @Override
    public ContentCategory getLockEntity(ContentCategoryDescription contentCategoryDescription) {
        return contentCategoryDescription.getContentCategory();
    }
    
    @Override
    public void fillInResult(EditContentCategoryDescriptionResult result, ContentCategoryDescription contentCategoryDescription) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentCategoryDescription(contentControl.getContentCategoryDescriptionTransfer(getUserVisit(), contentCategoryDescription));
    }
    
    @Override
    public void doLock(ContentCategoryDescriptionEdit edit, ContentCategoryDescription contentCategoryDescription) {
        edit.setDescription(contentCategoryDescription.getDescription());
    }
    
    @Override
    public void doUpdate(ContentCategoryDescription contentCategoryDescription) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCategoryDescriptionValue = contentControl.getContentCategoryDescriptionValue(contentCategoryDescription);
        contentCategoryDescriptionValue.setDescription(edit.getDescription());

        contentControl.updateContentCategoryDescriptionFromValue(contentCategoryDescriptionValue, getPartyPK());
    }
    
}
