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
import com.echothree.control.user.content.common.edit.ContentPageDescriptionEdit;
import com.echothree.control.user.content.common.form.EditContentPageDescriptionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentPageDescriptionResult;
import com.echothree.control.user.content.common.spec.ContentPageDescriptionSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageDescription;
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
public class EditContentPageDescriptionCommand
        extends BaseAbstractEditCommand<ContentPageDescriptionSpec, ContentPageDescriptionEdit, EditContentPageDescriptionResult, ContentPageDescription, ContentPage> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentPage.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContentPageDescriptionCommand */
    public EditContentPageDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentPageDescriptionResult getResult() {
        return ContentResultFactory.getEditContentPageDescriptionResult();
    }
    
    @Override
    public ContentPageDescriptionEdit getEdit() {
        return ContentEditFactory.getContentPageDescriptionEdit();
    }
    
    @Override
    public ContentPageDescription getEntity(EditContentPageDescriptionResult result) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentPageDescription contentPageDescription = null;
        var contentCollectionName = spec.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            var contentSectionName = spec.getContentSectionName();
            var contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);
            
            if(contentSection != null) {
                var contentPageName = spec.getContentPageName();
                var contentPage = contentControl.getContentPageByName(contentSection, contentPageName);
                
                if(contentPage != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var languageIsoName = spec.getLanguageIsoName();
                    var language = partyControl.getLanguageByIsoName(languageIsoName);
                    
                    result.setContentPage(contentControl.getContentPageTransfer(getUserVisit(), contentPage));
                    
                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                            contentPageDescription = contentControl.getContentPageDescription(contentPage, language);
                        } else { // EditMode.UPDATE
                            contentPageDescription = contentControl.getContentPageDescriptionForUpdate(contentPage, language);
                        }

                        if(contentPageDescription == null) {
                            addExecutionError(ExecutionErrors.UnknownContentPageDescription.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentPageName.name(), contentPageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentSectionName.name(), contentSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }

        return contentPageDescription;
    }
    
    @Override
    public ContentPage getLockEntity(ContentPageDescription contentPageDescription) {
        return contentPageDescription.getContentPage();
    }
    
    @Override
    public void fillInResult(EditContentPageDescriptionResult result, ContentPageDescription contentPageDescription) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentPageDescription(contentControl.getContentPageDescriptionTransfer(getUserVisit(), contentPageDescription));
    }
    
    @Override
    public void doLock(ContentPageDescriptionEdit edit, ContentPageDescription contentPageDescription) {
        edit.setDescription(contentPageDescription.getDescription());
    }
    
    @Override
    public void doUpdate(ContentPageDescription contentPageDescription) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentPageDescriptionValue = contentControl.getContentPageDescriptionValue(contentPageDescription);
        contentPageDescriptionValue.setDescription(edit.getDescription());

        contentControl.updateContentPageDescriptionFromValue(contentPageDescriptionValue, getPartyPK());
    }
    
}
