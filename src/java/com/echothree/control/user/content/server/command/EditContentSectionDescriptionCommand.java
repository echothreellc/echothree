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

import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.edit.ContentSectionDescriptionEdit;
import com.echothree.control.user.content.common.form.EditContentSectionDescriptionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentSectionDescriptionResult;
import com.echothree.control.user.content.common.spec.ContentSectionDescriptionSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.content.server.entity.ContentSectionDescription;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditContentSectionDescriptionCommand
        extends BaseAbstractEditCommand<ContentSectionDescriptionSpec, ContentSectionDescriptionEdit, EditContentSectionDescriptionResult, ContentSectionDescription, ContentSection> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentSection.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContentSectionDescriptionCommand */
    public EditContentSectionDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentSectionDescriptionResult getResult() {
        return ContentResultFactory.getEditContentSectionDescriptionResult();
    }
    
    @Override
    public ContentSectionDescriptionEdit getEdit() {
        return ContentEditFactory.getContentSectionDescriptionEdit();
    }
    
    @Override
    public ContentSectionDescription getEntity(EditContentSectionDescriptionResult result) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentSectionDescription contentSectionDescription = null;
        var contentCollectionName = spec.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            var contentSectionName = spec.getContentSectionName();
            var contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);
            
            if(contentSection != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                result.setContentSection(contentControl.getContentSectionTransfer(getUserVisit(), contentSection));

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        contentSectionDescription = contentControl.getContentSectionDescription(contentSection, language);
                    } else { // EditMode.UPDATE
                        contentSectionDescription = contentControl.getContentSectionDescriptionForUpdate(contentSection, language);
                    }

                    if(contentSectionDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownContentSectionDescription.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentSectionName.name(), contentSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }

        return contentSectionDescription;
    }
    
    @Override
    public ContentSection getLockEntity(ContentSectionDescription contentSectionDescription) {
        return contentSectionDescription.getContentSection();
    }
    
    @Override
    public void fillInResult(EditContentSectionDescriptionResult result, ContentSectionDescription contentSectionDescription) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentSectionDescription(contentControl.getContentSectionDescriptionTransfer(getUserVisit(), contentSectionDescription));
    }
    
    @Override
    public void doLock(ContentSectionDescriptionEdit edit, ContentSectionDescription contentSectionDescription) {
        edit.setDescription(contentSectionDescription.getDescription());
    }
    
    @Override
    public void doUpdate(ContentSectionDescription contentSectionDescription) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentSectionDescriptionValue = contentControl.getContentSectionDescriptionValue(contentSectionDescription);
        contentSectionDescriptionValue.setDescription(edit.getDescription());

        contentControl.updateContentSectionDescriptionFromValue(contentSectionDescriptionValue, getPartyPK());
    }
    
}
