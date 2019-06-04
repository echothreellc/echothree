// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.content.common.edit.ContentCollectionDescriptionEdit;
import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.form.EditContentCollectionDescriptionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentCollectionDescriptionResult;
import com.echothree.control.user.content.common.spec.ContentCollectionDescriptionSpec;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentCollectionDescription;
import com.echothree.model.data.content.server.value.ContentCollectionDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditContentCollectionDescriptionCommand
        extends BaseAbstractEditCommand<ContentCollectionDescriptionSpec, ContentCollectionDescriptionEdit, EditContentCollectionDescriptionResult, ContentCollectionDescription, ContentCollection> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCollection.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditContentCollectionDescriptionCommand */
    public EditContentCollectionDescriptionCommand(UserVisitPK userVisitPK, EditContentCollectionDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentCollectionDescriptionResult getResult() {
        return ContentResultFactory.getEditContentCollectionDescriptionResult();
    }
    
    @Override
    public ContentCollectionDescriptionEdit getEdit() {
        return ContentEditFactory.getContentCollectionDescriptionEdit();
    }
    
    @Override
    public ContentCollectionDescription getEntity(EditContentCollectionDescriptionResult result) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        ContentCollectionDescription contentCollectionDescription = null;
        String contentCollectionName = spec.getContentCollectionName();
        ContentCollection contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            result.setContentCollection(contentControl.getContentCollectionTransfer(getUserVisit(), contentCollection));

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    contentCollectionDescription = contentControl.getContentCollectionDescription(contentCollection, language);
                } else { // EditMode.UPDATE
                    contentCollectionDescription = contentControl.getContentCollectionDescriptionForUpdate(contentCollection, language);
                }

                if(contentCollectionDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownContentCollectionDescription.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }

        return contentCollectionDescription;
    }
    
    @Override
    public ContentCollection getLockEntity(ContentCollectionDescription contentCollectionDescription) {
        return contentCollectionDescription.getContentCollection();
    }
    
    @Override
    public void fillInResult(EditContentCollectionDescriptionResult result, ContentCollectionDescription contentCollectionDescription) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        
        result.setContentCollectionDescription(contentControl.getContentCollectionDescriptionTransfer(getUserVisit(), contentCollectionDescription));
    }
    
    @Override
    public void doLock(ContentCollectionDescriptionEdit edit, ContentCollectionDescription contentCollectionDescription) {
        edit.setDescription(contentCollectionDescription.getDescription());
    }
    
    @Override
    public void doUpdate(ContentCollectionDescription contentCollectionDescription) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        ContentCollectionDescriptionValue contentCollectionDescriptionValue = contentControl.getContentCollectionDescriptionValue(contentCollectionDescription);
        contentCollectionDescriptionValue.setDescription(edit.getDescription());

        contentControl.updateContentCollectionDescriptionFromValue(contentCollectionDescriptionValue, getPartyPK());
    }
    
}
