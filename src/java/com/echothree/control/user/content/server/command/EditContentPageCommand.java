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
import com.echothree.control.user.content.common.edit.ContentPageEdit;
import com.echothree.control.user.content.common.form.EditContentPageForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentPageResult;
import com.echothree.control.user.content.common.spec.ContentPageSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.content.server.entity.ContentSection;
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
public class EditContentPageCommand
        extends BaseAbstractEditCommand<ContentPageSpec, ContentPageEdit, EditContentPageResult, ContentPage, ContentPage> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentPage.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentPageName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentPageLayoutName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContentPageCommand */
    public EditContentPageCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentPageResult getResult() {
        return ContentResultFactory.getEditContentPageResult();
    }
    
    @Override
    public ContentPageEdit getEdit() {
        return ContentEditFactory.getContentPageEdit();
    }
    
    ContentSection contentSection = null;
    
    @Override
    public ContentPage getEntity(EditContentPageResult result) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentPage contentPage = null;
        var contentCollectionName = spec.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            var contentSectionName = spec.getContentSectionName();
            
            contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);
            
            if(contentSection != null) {
                var contentPageName = spec.getContentPageName();
                
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    contentPage = contentControl.getContentPageByName(contentSection, contentPageName);
                } else { // EditMode.UPDATE
                    contentPage = contentControl.getContentPageByNameForUpdate(contentSection, contentPageName);
                }

                if(contentPage != null) {
                    result.setContentPage(contentControl.getContentPageTransfer(getUserVisit(), contentPage));
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentPageName.name(), contentPageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentSectionName.name(), contentSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }

        return contentPage;
    }
    
    @Override
    public ContentPage getLockEntity(ContentPage contentPage) {
        return contentPage;
    }
    
    @Override
    public void fillInResult(EditContentPageResult result, ContentPage contentPage) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentPage(contentControl.getContentPageTransfer(getUserVisit(), contentPage));
    }
    
    @Override
    public void doLock(ContentPageEdit edit, ContentPage contentPage) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentPageDescription = contentControl.getContentPageDescription(contentPage, getPreferredLanguage());
        var contentPageDetail = contentPage.getLastDetail();

        edit.setContentPageName(contentPageDetail.getContentPageName());
        edit.setContentPageLayoutName(contentPageDetail.getContentPageLayout().getLastDetail().getContentPageLayoutName());
        edit.setIsDefault(contentPageDetail.getIsDefault().toString());
        edit.setSortOrder(contentPageDetail.getSortOrder().toString());

        if(contentPageDescription != null) {
            edit.setDescription(contentPageDescription.getDescription());
        }
    }
    
    ContentPageLayout contentPageLayout = null;
    
    @Override
    public void canUpdate(ContentPage contentPage) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentPageName = edit.getContentPageName();
        var duplicateContentPage = contentControl.getContentPageByName(contentSection, contentPageName);

        if(duplicateContentPage == null || contentPage.equals(duplicateContentPage)) {
            var contentPageLayoutName = edit.getContentPageLayoutName();
            
            contentPageLayout = contentControl.getContentPageLayoutByName(contentPageLayoutName);

            if(contentPageLayout == null) {
                addExecutionError(ExecutionErrors.UnknownContentPageLayoutName.name(), contentPageLayoutName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateContentPageName.name(), contentPageName);
        }
    }
    
    @Override
    public void doUpdate(ContentPage contentPage) {
        var contentControl = Session.getModelController(ContentControl.class);
        var partyPK = getPartyPK();
        var contentPageDetailValue = contentControl.getContentPageDetailValueForUpdate(contentPage);
        var contentPageDescription = contentControl.getContentPageDescriptionForUpdate(contentPage, getPreferredLanguage());
        var description = edit.getDescription();

        contentPageDetailValue.setContentPageName(edit.getContentPageName());
        contentPageDetailValue.setContentPageLayoutPK(contentPageLayout.getPrimaryKey());
        contentPageDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        contentPageDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        contentControl.updateContentPageFromValue(contentPageDetailValue, partyPK);

        if(contentPageDescription == null && description != null) {
            contentControl.createContentPageDescription(contentPage, getPreferredLanguage(), description, partyPK);
        } else if(contentPageDescription != null && description == null) {
            contentControl.deleteContentPageDescription(contentPageDescription, partyPK);
        } else if(contentPageDescription != null && description != null) {
            var contentPageDescriptionValue = contentControl.getContentPageDescriptionValue(contentPageDescription);

            contentPageDescriptionValue.setDescription(description);
            contentControl.updateContentPageDescriptionFromValue(contentPageDescriptionValue, partyPK);
        }
    }
    
}
