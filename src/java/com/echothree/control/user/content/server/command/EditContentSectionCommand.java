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
import com.echothree.control.user.content.common.edit.ContentSectionEdit;
import com.echothree.control.user.content.common.form.EditContentSectionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentSectionResult;
import com.echothree.control.user.content.common.spec.ContentSectionSpec;
import com.echothree.model.control.content.common.ContentSections;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentCollection;
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
public class EditContentSectionCommand
        extends BaseAbstractEditCommand<ContentSectionSpec, ContentSectionEdit, EditContentSectionResult, ContentSection, ContentSection> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentSection.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentContentSectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContentSectionCommand */
    public EditContentSectionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentSectionResult getResult() {
        return ContentResultFactory.getEditContentSectionResult();
    }
    
    @Override
    public ContentSectionEdit getEdit() {
        return ContentEditFactory.getContentSectionEdit();
    }
    
    ContentCollection contentCollection = null;
    
    @Override
    public ContentSection getEntity(EditContentSectionResult result) {
        var contentControl = Session.getModelController(ContentControl.class);
        ContentSection contentSection = null;
        var contentCollectionName = spec.getContentCollectionName();
        
        contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            var contentSectionName = spec.getContentSectionName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);
            } else { // EditMode.UPDATE
                contentSection = contentControl.getContentSectionByNameForUpdate(contentCollection, contentSectionName);
            }

            if(contentSection != null) {
                result.setContentSection(contentControl.getContentSectionTransfer(getUserVisit(), contentSection));
            } else {
                addExecutionError(ExecutionErrors.UnknownContentSectionName.name(), contentSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }

        return contentSection;
    }
    
    @Override
    public ContentSection getLockEntity(ContentSection contentSection) {
        return contentSection;
    }
    
    @Override
    public void fillInResult(EditContentSectionResult result, ContentSection contentSection) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentSection(contentControl.getContentSectionTransfer(getUserVisit(), contentSection));
    }
    
    @Override
    public void doLock(ContentSectionEdit edit, ContentSection contentSection) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentSectionDescription = contentControl.getContentSectionDescription(contentSection, getPreferredLanguage());
        var contentSectionDetail = contentSection.getLastDetail();
        var parentContentSection = contentSectionDetail.getParentContentSection();

        edit.setContentSectionName(contentSectionDetail.getContentSectionName());
        edit.setParentContentSectionName(parentContentSection == null? null: parentContentSection.getLastDetail().getContentSectionName());
        edit.setIsDefault(contentSectionDetail.getIsDefault().toString());
        edit.setSortOrder(contentSectionDetail.getSortOrder().toString());

        if(contentSectionDescription != null) {
            edit.setDescription(contentSectionDescription.getDescription());
        }
    }
    
    ContentSection parentContentSection = null;
    
    @Override
    public void canUpdate(ContentSection contentSection) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentSectionName = edit.getContentSectionName();
        var duplicateContentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);

        if(duplicateContentSection == null || contentSection.equals(duplicateContentSection)) {
            var parentContentSectionName = edit.getParentContentSectionName();
            
            parentContentSection = contentControl.getContentSectionByName(contentCollection, parentContentSectionName == null ?
                    ContentSections.ROOT.toString() : parentContentSectionName);

            if(parentContentSection != null) {
                if(!contentControl.isParentContentSectionSafe(contentSection, parentContentSection)) {
                    addExecutionError(ExecutionErrors.InvalidParentContentSection.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentContentSectionName.name(), parentContentSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateContentSectionName.name(), contentSectionName);
        }
    }
    
    @Override
    public void doUpdate(ContentSection contentSection) {
        var contentControl = Session.getModelController(ContentControl.class);
        var partyPK = getPartyPK();
        var contentSectionDetailValue = contentControl.getContentSectionDetailValueForUpdate(contentSection);
        var contentSectionDescription = contentControl.getContentSectionDescriptionForUpdate(contentSection, getPreferredLanguage());
        var description = edit.getDescription();

        contentSectionDetailValue.setContentSectionName(edit.getContentSectionName());
        contentSectionDetailValue.setParentContentSectionPK(parentContentSection == null? null: parentContentSection.getPrimaryKey());
        contentSectionDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        contentSectionDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        contentControl.updateContentSectionFromValue(contentSectionDetailValue, partyPK);

        if(contentSectionDescription == null && description != null) {
            contentControl.createContentSectionDescription(contentSection, getPreferredLanguage(), description, partyPK);
        } else if(contentSectionDescription != null && description == null) {
            contentControl.deleteContentSectionDescription(contentSectionDescription, partyPK);
        } else if(contentSectionDescription != null && description != null) {
            var contentSectionDescriptionValue = contentControl.getContentSectionDescriptionValue(contentSectionDescription);

            contentSectionDescriptionValue.setDescription(description);
            contentControl.updateContentSectionDescriptionFromValue(contentSectionDescriptionValue, partyPK);
        }
    }
    
}
