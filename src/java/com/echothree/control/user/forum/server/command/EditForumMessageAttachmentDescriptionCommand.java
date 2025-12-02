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
import com.echothree.control.user.forum.common.edit.ForumMessageAttachmentDescriptionEdit;
import com.echothree.control.user.forum.common.form.EditForumMessageAttachmentDescriptionForm;
import com.echothree.control.user.forum.common.result.EditForumMessageAttachmentDescriptionResult;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.control.user.forum.common.spec.ForumMessageAttachmentDescriptionSpec;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachment;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachmentDescription;
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
public class EditForumMessageAttachmentDescriptionCommand
        extends BaseAbstractEditCommand<ForumMessageAttachmentDescriptionSpec, ForumMessageAttachmentDescriptionEdit, EditForumMessageAttachmentDescriptionResult, ForumMessageAttachmentDescription, ForumMessageAttachment> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumMessageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ForumMessageAttachmentSequence", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditForumMessageAttachmentDescriptionCommand */
    public EditForumMessageAttachmentDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditForumMessageAttachmentDescriptionResult getResult() {
        return ForumResultFactory.getEditForumMessageAttachmentDescriptionResult();
    }

    @Override
    public ForumMessageAttachmentDescriptionEdit getEdit() {
        return ForumEditFactory.getForumMessageAttachmentDescriptionEdit();
    }

    @Override
    public ForumMessageAttachmentDescription getEntity(EditForumMessageAttachmentDescriptionResult result) {
        var forumControl = Session.getModelController(ForumControl.class);
        ForumMessageAttachmentDescription forumMessageAttachmentDescription = null;
        var forumMessageName = spec.getForumMessageName();
        var forumMessage = forumControl.getForumMessageByNameForUpdate(forumMessageName);

        if(forumMessage != null) {
            var forumMessageAttachmentSequence = Integer.valueOf(spec.getForumMessageAttachmentSequence());
            var forumMessageAttachment = forumControl.getForumMessageAttachmentBySequence(forumMessage, forumMessageAttachmentSequence);

            if(forumMessageAttachment != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        forumMessageAttachmentDescription = forumControl.getForumMessageAttachmentDescription(forumMessageAttachment, language);
                    } else { // EditMode.UPDATE
                        forumMessageAttachmentDescription = forumControl.getForumMessageAttachmentDescriptionForUpdate(forumMessageAttachment, language);
                    }

                    if(forumMessageAttachmentDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownForumMessageAttachmentDescription.name(), forumMessageName,
                                forumMessageAttachmentSequence.toString(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownForumMessageAttachment.name(), forumMessageName, forumMessageAttachmentSequence.toString());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumMessageName.name(), forumMessageName);
        }

        return forumMessageAttachmentDescription;
    }

    @Override
    public ForumMessageAttachment getLockEntity(ForumMessageAttachmentDescription forumMessageAttachmentDescription) {
        return forumMessageAttachmentDescription.getForumMessageAttachment();
    }

    @Override
    public void fillInResult(EditForumMessageAttachmentDescriptionResult result, ForumMessageAttachmentDescription forumMessageAttachmentDescription) {
        var forumControl = Session.getModelController(ForumControl.class);

        result.setForumMessageAttachmentDescription(forumControl.getForumMessageAttachmentDescriptionTransfer(getUserVisit(), forumMessageAttachmentDescription));
    }

    @Override
    public void doLock(ForumMessageAttachmentDescriptionEdit edit, ForumMessageAttachmentDescription forumMessageAttachmentDescription) {
        edit.setDescription(forumMessageAttachmentDescription.getDescription());
    }

    @Override
    public void doUpdate(ForumMessageAttachmentDescription forumMessageAttachmentDescription) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumMessageAttachmentDescriptionValue = forumControl.getForumMessageAttachmentDescriptionValue(forumMessageAttachmentDescription);

        forumMessageAttachmentDescriptionValue.setDescription(edit.getDescription());

        forumControl.updateForumMessageAttachmentDescriptionFromValue(forumMessageAttachmentDescriptionValue, getPartyPK());
    }
    
}
