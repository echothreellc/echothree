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
import com.echothree.control.user.forum.common.edit.ForumGroupDescriptionEdit;
import com.echothree.control.user.forum.common.form.EditForumGroupDescriptionForm;
import com.echothree.control.user.forum.common.result.EditForumGroupDescriptionResult;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.control.user.forum.common.spec.ForumGroupDescriptionSpec;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.forum.server.entity.ForumGroupDescription;
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

public class EditForumGroupDescriptionCommand
        extends BaseAbstractEditCommand<ForumGroupDescriptionSpec, ForumGroupDescriptionEdit, EditForumGroupDescriptionResult, ForumGroupDescription, ForumGroup> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditForumGroupDescriptionCommand */
    public EditForumGroupDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditForumGroupDescriptionResult getResult() {
        return ForumResultFactory.getEditForumGroupDescriptionResult();
    }

    @Override
    public ForumGroupDescriptionEdit getEdit() {
        return ForumEditFactory.getForumGroupDescriptionEdit();
    }

    @Override
    public ForumGroupDescription getEntity(EditForumGroupDescriptionResult result) {
        var forumControl = Session.getModelController(ForumControl.class);
        ForumGroupDescription forumGroupDescription = null;
        var forumGroupName = spec.getForumGroupName();
        var forumGroup = forumControl.getForumGroupByName(forumGroupName);

        if(forumGroup != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    forumGroupDescription = forumControl.getForumGroupDescription(forumGroup, language);
                } else { // EditMode.UPDATE
                    forumGroupDescription = forumControl.getForumGroupDescriptionForUpdate(forumGroup, language);
                }

                if(forumGroupDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownForumGroupDescription.name(), forumGroupName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumGroupName.name(), forumGroupName);
        }

        return forumGroupDescription;
    }

    @Override
    public ForumGroup getLockEntity(ForumGroupDescription forumGroupDescription) {
        return forumGroupDescription.getForumGroup();
    }

    @Override
    public void fillInResult(EditForumGroupDescriptionResult result, ForumGroupDescription forumGroupDescription) {
        var forumControl = Session.getModelController(ForumControl.class);

        result.setForumGroupDescription(forumControl.getForumGroupDescriptionTransfer(getUserVisit(), forumGroupDescription));
    }

    @Override
    public void doLock(ForumGroupDescriptionEdit edit, ForumGroupDescription forumGroupDescription) {
        edit.setDescription(forumGroupDescription.getDescription());
    }

    @Override
    public void doUpdate(ForumGroupDescription forumGroupDescription) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumGroupDescriptionValue = forumControl.getForumGroupDescriptionValue(forumGroupDescription);

        forumGroupDescriptionValue.setDescription(edit.getDescription());

        forumControl.updateForumGroupDescriptionFromValue(forumGroupDescriptionValue, getPartyPK());
    }
    
}
