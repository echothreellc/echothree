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

import com.echothree.control.user.forum.common.edit.ForumDescriptionEdit;
import com.echothree.control.user.forum.common.edit.ForumEditFactory;
import com.echothree.control.user.forum.common.form.EditForumDescriptionForm;
import com.echothree.control.user.forum.common.result.EditForumDescriptionResult;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.control.user.forum.common.spec.ForumDescriptionSpec;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditForumDescriptionCommand
        extends BaseAbstractEditCommand<ForumDescriptionSpec, ForumDescriptionEdit, EditForumDescriptionResult, ForumDescription, Forum> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditForumDescriptionCommand */
    public EditForumDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditForumDescriptionResult getResult() {
        return ForumResultFactory.getEditForumDescriptionResult();
    }

    @Override
    public ForumDescriptionEdit getEdit() {
        return ForumEditFactory.getForumDescriptionEdit();
    }

    @Override
    public ForumDescription getEntity(EditForumDescriptionResult result) {
        var forumControl = Session.getModelController(ForumControl.class);
        ForumDescription forumDescription = null;
        var forumName = spec.getForumName();
        var forum = forumControl.getForumByName(forumName);

        if(forum != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    forumDescription = forumControl.getForumDescription(forum, language);
                } else { // EditMode.UPDATE
                    forumDescription = forumControl.getForumDescriptionForUpdate(forum, language);
                }

                if(forumDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownForumDescription.name(), forumName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
        }

        return forumDescription;
    }

    @Override
    public Forum getLockEntity(ForumDescription forumDescription) {
        return forumDescription.getForum();
    }

    @Override
    public void fillInResult(EditForumDescriptionResult result, ForumDescription forumDescription) {
        var forumControl = Session.getModelController(ForumControl.class);

        result.setForumDescription(forumControl.getForumDescriptionTransfer(getUserVisit(), forumDescription));
    }

    @Override
    public void doLock(ForumDescriptionEdit edit, ForumDescription forumDescription) {
        edit.setDescription(forumDescription.getDescription());
    }

    @Override
    public void doUpdate(ForumDescription forumDescription) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumDescriptionValue = forumControl.getForumDescriptionValue(forumDescription);

        forumDescriptionValue.setDescription(edit.getDescription());

        forumControl.updateForumDescriptionFromValue(forumDescriptionValue, getPartyPK());
    }
    
}
