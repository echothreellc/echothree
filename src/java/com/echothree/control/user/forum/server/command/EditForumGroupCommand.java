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

package com.echothree.control.user.forum.server.command;

import com.echothree.control.user.forum.common.edit.ForumEditFactory;
import com.echothree.control.user.forum.common.edit.ForumGroupEdit;
import com.echothree.control.user.forum.common.form.EditForumGroupForm;
import com.echothree.control.user.forum.common.result.EditForumGroupResult;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.control.user.forum.common.spec.ForumGroupSpec;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditForumGroupCommand
        extends BaseAbstractEditCommand<ForumGroupSpec, ForumGroupEdit, EditForumGroupResult, ForumGroup, ForumGroup> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditForumGroupCommand */
    public EditForumGroupCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditForumGroupResult getResult() {
        return ForumResultFactory.getEditForumGroupResult();
    }

    @Override
    public ForumGroupEdit getEdit() {
        return ForumEditFactory.getForumGroupEdit();
    }

    @Override
    public ForumGroup getEntity(EditForumGroupResult result) {
        var forumControl = Session.getModelController(ForumControl.class);
        ForumGroup forumGroup;
        var forumGroupName = spec.getForumGroupName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            forumGroup = forumControl.getForumGroupByName(forumGroupName);
        } else { // EditMode.UPDATE
            forumGroup = forumControl.getForumGroupByNameForUpdate(forumGroupName);
        }

        if(forumGroup == null) {
            addExecutionError(ExecutionErrors.UnknownForumGroupName.name(), forumGroupName);
        }

        return forumGroup;
    }

    @Override
    public ForumGroup getLockEntity(ForumGroup forumGroup) {
        return forumGroup;
    }

    @Override
    public void fillInResult(EditForumGroupResult result, ForumGroup forumGroup) {
        var forumControl = Session.getModelController(ForumControl.class);

        result.setForumGroup(forumControl.getForumGroupTransfer(getUserVisit(), forumGroup));
    }

    @Override
    public void doLock(ForumGroupEdit edit, ForumGroup forumGroup) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumGroupDescription = forumControl.getForumGroupDescription(forumGroup, getPreferredLanguage());
        var forumGroupDetail = forumGroup.getLastDetail();

        icon = forumGroupDetail.getIcon();

        edit.setForumGroupName(forumGroupDetail.getForumGroupName());
        edit.setIconName(icon == null? null: icon.getLastDetail().getIconName());
        edit.setSortOrder(forumGroupDetail.getSortOrder().toString());

        if(forumGroupDescription != null) {
            edit.setDescription(forumGroupDescription.getDescription());
        }
    }

    Icon icon = null;

    @Override
    public void canUpdate(ForumGroup forumGroup) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumGroupName = edit.getForumGroupName();
        var duplicateForumGroup = forumControl.getForumGroupByName(forumGroupName);

        if(duplicateForumGroup == null || forumGroup.equals(duplicateForumGroup)) {
            var iconControl = Session.getModelController(IconControl.class);
            var iconName = edit.getIconName();

            icon = iconName == null? null: iconControl.getIconByName(iconName);

            if(iconName != null && icon == null) {
                addExecutionError(ExecutionErrors.UnknownIconName.name(), iconName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateForumGroupName.name(), forumGroupName);
        }
    }

    @Override
    public void doUpdate(ForumGroup forumGroup) {
        var forumControl = Session.getModelController(ForumControl.class);
        var partyPK = getPartyPK();
        var forumGroupDetailValue = forumControl.getForumGroupDetailValueForUpdate(forumGroup);
        var forumGroupDescription = forumControl.getForumGroupDescriptionForUpdate(forumGroup, getPreferredLanguage());
        var description = edit.getDescription();

        forumGroupDetailValue.setForumGroupName(edit.getForumGroupName());
        forumGroupDetailValue.setIconPK(icon == null? null: icon.getPrimaryKey());
        forumGroupDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        forumControl.updateForumGroupFromValue(forumGroupDetailValue, partyPK);

        if(forumGroupDescription == null && description != null) {
            forumControl.createForumGroupDescription(forumGroup, getPreferredLanguage(), description, partyPK);
        } else if(forumGroupDescription != null && description == null) {
            forumControl.deleteForumGroupDescription(forumGroupDescription, partyPK);
        } else if(forumGroupDescription != null && description != null) {
            var forumGroupDescriptionValue = forumControl.getForumGroupDescriptionValue(forumGroupDescription);

            forumGroupDescriptionValue.setDescription(description);
            forumControl.updateForumGroupDescriptionFromValue(forumGroupDescriptionValue, partyPK);
        }
    }

}
