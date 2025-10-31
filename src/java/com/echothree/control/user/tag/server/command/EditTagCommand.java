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

package com.echothree.control.user.tag.server.command;

import com.echothree.control.user.tag.common.edit.TagEdit;
import com.echothree.control.user.tag.common.edit.TagEditFactory;
import com.echothree.control.user.tag.common.form.EditTagForm;
import com.echothree.control.user.tag.common.result.EditTagResult;
import com.echothree.control.user.tag.common.result.TagResultFactory;
import com.echothree.control.user.tag.common.spec.TagSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.tag.server.entity.Tag;
import com.echothree.model.data.tag.server.entity.TagScope;
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
public class EditTagCommand
        extends BaseAbstractEditCommand<TagSpec, TagEdit, EditTagResult, Tag, Tag> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Tag.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TagName", FieldType.TAG, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TagName", FieldType.TAG, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditTagCommand */
    public EditTagCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTagResult getResult() {
        return TagResultFactory.getEditTagResult();
    }

    @Override
    public TagEdit getEdit() {
        return TagEditFactory.getTagEdit();
    }

    TagScope tagScope;
    
    @Override
    public Tag getEntity(EditTagResult result) {
        var tagControl = Session.getModelController(TagControl.class);
        Tag tag = null;
        var tagScopeName = spec.getTagScopeName();
        
        tagScope = tagControl.getTagScopeByNameForUpdate(tagScopeName);
        
        if(tagScope != null) {
            var tagName = spec.getTagName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                tag = tagControl.getTagByName(tagScope, tagName);
            } else { // EditMode.UPDATE
                tag = tagControl.getTagByNameForUpdate(tagScope, tagName);
            }

            if(tag != null) {
                result.setTag(tagControl.getTagTransfer(getUserVisit(), tag));
            } else {
                addExecutionError(ExecutionErrors.UnknownTagName.name(), tagScopeName, tagName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTagScopeName.name(), tagScopeName);
        }

        return tag;
    }

    @Override
    public Tag getLockEntity(Tag tag) {
        return tag;
    }

    @Override
    public void fillInResult(EditTagResult result, Tag tag) {
        var tagControl = Session.getModelController(TagControl.class);

        result.setTag(tagControl.getTagTransfer(getUserVisit(), tag));
    }

    @Override
    public void doLock(TagEdit edit, Tag tag) {
        var tagDetail = tag.getLastDetail();

        edit.setTagName(tagDetail.getTagName());
    }

    @Override
    public void canUpdate(Tag tag) {
        var tagControl = Session.getModelController(TagControl.class);
        var tagName = edit.getTagName();
        var duplicateTag = tagControl.getTagByName(tagScope, tagName);

        if(duplicateTag != null && !tag.equals(duplicateTag)) {
            addExecutionError(ExecutionErrors.DuplicateTagName.name(), tagName);
        }
    }

    @Override
    public void doUpdate(Tag tag) {
        var tagControl = Session.getModelController(TagControl.class);
        var tagDetailValue = tagControl.getTagDetailValueForUpdate(tag);

        tagDetailValue.setTagName(edit.getTagName());

        tagControl.updateTagFromValue(tagDetailValue, getPartyPK());
    }

}
