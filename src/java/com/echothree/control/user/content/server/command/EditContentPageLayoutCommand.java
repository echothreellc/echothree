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
import com.echothree.control.user.content.common.edit.ContentPageLayoutEdit;
import com.echothree.control.user.content.common.form.EditContentPageLayoutForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.EditContentPageLayoutResult;
import com.echothree.control.user.content.common.spec.ContentPageLayoutUniversalSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.content.server.logic.ContentPageLayoutLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
public class EditContentPageLayoutCommand
        extends BaseAbstractEditCommand<ContentPageLayoutUniversalSpec, ContentPageLayoutEdit, EditContentPageLayoutResult, ContentPageLayout, ContentPageLayout> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentPageLayout.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentPageLayoutName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentPageLayoutName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContentPageLayoutCommand */
    public EditContentPageLayoutCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentPageLayoutResult getResult() {
        return ContentResultFactory.getEditContentPageLayoutResult();
    }
    
    @Override
    public ContentPageLayoutEdit getEdit() {
        return ContentEditFactory.getContentPageLayoutEdit();
    }
    
    @Override
    public ContentPageLayout getEntity(EditContentPageLayoutResult result) {
        return ContentPageLayoutLogic.getInstance().getContentPageLayoutByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }
    
    @Override
    public ContentPageLayout getLockEntity(ContentPageLayout contentPageLayout) {
        return contentPageLayout;
    }
    
    @Override
    public void fillInResult(EditContentPageLayoutResult result, ContentPageLayout contentPageLayout) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        result.setContentPageLayout(contentControl.getContentPageLayoutTransfer(getUserVisit(), contentPageLayout));
    }
    
    @Override
    public void doLock(ContentPageLayoutEdit edit, ContentPageLayout contentPageLayout) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentPageLayoutDescription = contentControl.getContentPageLayoutDescription(contentPageLayout, getPreferredLanguage());
        var contentPageLayoutDetail = contentPageLayout.getLastDetail();
        
        edit.setContentPageLayoutName(contentPageLayoutDetail.getContentPageLayoutName());
        edit.setIsDefault(contentPageLayoutDetail.getIsDefault().toString());
        edit.setSortOrder(contentPageLayoutDetail.getSortOrder().toString());

        if(contentPageLayoutDescription != null) {
            edit.setDescription(contentPageLayoutDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(ContentPageLayout contentPageLayout) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentPageLayoutName = edit.getContentPageLayoutName();
        var duplicateContentPageLayout = contentControl.getContentPageLayoutByName(contentPageLayoutName);

        if(duplicateContentPageLayout != null && !contentPageLayout.equals(duplicateContentPageLayout)) {
            addExecutionError(ExecutionErrors.DuplicateContentPageLayoutName.name(), contentPageLayoutName);
        }
    }
    
    @Override
    public void doUpdate(ContentPageLayout contentPageLayout) {
        var contentControl = Session.getModelController(ContentControl.class);
        var partyPK = getPartyPK();
        var contentPageLayoutDetailValue = contentControl.getContentPageLayoutDetailValueForUpdate(contentPageLayout);
        var contentPageLayoutDescription = contentControl.getContentPageLayoutDescriptionForUpdate(contentPageLayout, getPreferredLanguage());
        var description = edit.getDescription();

        contentPageLayoutDetailValue.setContentPageLayoutName(edit.getContentPageLayoutName());
        contentPageLayoutDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        contentPageLayoutDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        contentControl.updateContentPageLayoutFromValue(contentPageLayoutDetailValue, partyPK);

        if(contentPageLayoutDescription == null && description != null) {
            contentControl.createContentPageLayoutDescription(contentPageLayout, getPreferredLanguage(), description, partyPK);
        } else if(contentPageLayoutDescription != null && description == null) {
            contentControl.deleteContentPageLayoutDescription(contentPageLayoutDescription, partyPK);
        } else if(contentPageLayoutDescription != null && description != null) {
            var contentPageLayoutDescriptionValue = contentControl.getContentPageLayoutDescriptionValue(contentPageLayoutDescription);

            contentPageLayoutDescriptionValue.setDescription(description);
            contentControl.updateContentPageLayoutDescriptionFromValue(contentPageLayoutDescriptionValue, partyPK);
        }
    }
    
}
