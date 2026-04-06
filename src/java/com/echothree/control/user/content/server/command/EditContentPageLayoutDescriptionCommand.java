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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.edit.ContentPageLayoutDescriptionEdit;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentPageLayoutDescriptionResult;
import com.echothree.control.user.content.common.spec.ContentPageLayoutDescriptionSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.content.server.entity.ContentPageLayoutDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditContentPageLayoutDescriptionCommand
        extends BaseAbstractEditCommand<ContentPageLayoutDescriptionSpec, ContentPageLayoutDescriptionEdit, EditContentPageLayoutDescriptionResult, ContentPageLayoutDescription, ContentPageLayout> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentPageLayout.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ContentPageLayoutName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditContentPageLayoutDescriptionCommand */
    public EditContentPageLayoutDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Inject
    ContentControl contentControl;

    @Inject
    PartyControl partyControl;

    @Override
    public EditContentPageLayoutDescriptionResult getResult() {
        return ContentResultFactory.getEditContentPageLayoutDescriptionResult();
    }

    @Override
    public ContentPageLayoutDescriptionEdit getEdit() {
        return ContentEditFactory.getContentPageLayoutDescriptionEdit();
    }

    @Override
    public ContentPageLayoutDescription getEntity(EditContentPageLayoutDescriptionResult result) {
        ContentPageLayoutDescription contentPageLayoutDescription = null;
        var contentPageLayoutName = spec.getContentPageLayoutName();
        var contentPageLayout = contentControl.getContentPageLayoutByName(contentPageLayoutName);

        if(contentPageLayout != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                contentPageLayoutDescription = contentControl.getContentPageLayoutDescription(contentPageLayout, language,
                        editModeToEntityPermission(editMode));

                if(contentPageLayoutDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownContentPageLayoutDescription.name(),
                            contentPageLayout.getLastDetail().getContentPageLayoutName(), language.getLanguageIsoName());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentPageLayoutName.name(), contentPageLayoutName);
        }

        return contentPageLayoutDescription;
    }

    @Override
    public ContentPageLayout getLockEntity(ContentPageLayoutDescription contentPageLayoutDescription) {
        return contentPageLayoutDescription.getContentPageLayout();
    }

    @Override
    public void fillInResult(EditContentPageLayoutDescriptionResult result, ContentPageLayoutDescription contentPageLayoutDescription) {
        result.setContentPageLayoutDescription(contentControl.getContentPageLayoutDescriptionTransfer(getUserVisit(), contentPageLayoutDescription));
    }

    @Override
    public void doLock(ContentPageLayoutDescriptionEdit edit, ContentPageLayoutDescription contentPageLayoutDescription) {
        edit.setDescription(contentPageLayoutDescription.getDescription());
    }

    @Override
    public void doUpdate(ContentPageLayoutDescription contentPageLayoutDescription) {
        var contentPageLayoutDescriptionValue = contentControl.getContentPageLayoutDescriptionValue(contentPageLayoutDescription);

        contentPageLayoutDescriptionValue.setDescription(edit.getDescription());

        contentControl.updateContentPageLayoutDescriptionFromValue(contentPageLayoutDescriptionValue, getPartyPK());
    }
    
}
