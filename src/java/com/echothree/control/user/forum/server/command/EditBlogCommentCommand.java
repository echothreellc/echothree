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

import com.echothree.control.user.forum.common.edit.BlogCommentEdit;
import com.echothree.control.user.forum.common.edit.ForumEditFactory;
import com.echothree.control.user.forum.common.form.EditBlogCommentForm;
import com.echothree.control.user.forum.common.result.EditBlogCommentResult;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.control.user.forum.common.spec.ForumMessageSpec;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditBlogCommentCommand
        extends BaseAbstractEditCommand<ForumMessageSpec, BlogCommentEdit, EditBlogCommentResult, ForumMessage, ForumMessage> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumMessageName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PostedTime", FieldType.DATE_TIME, true, null, null),
                new FieldDefinition("ForumMessageIconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Title", FieldType.STRING, true, 1L, 512L),
                new FieldDefinition("ContentMimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("Content", FieldType.STRING, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditBlogCommentCommand */
    public EditBlogCommentCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditBlogCommentResult getResult() {
        return ForumResultFactory.getEditBlogCommentResult();
    }

    @Override
    public BlogCommentEdit getEdit() {
        return ForumEditFactory.getBlogCommentEdit();
    }

    @Override
    public ForumMessage getEntity(EditBlogCommentResult result) {
        var forumControl = Session.getModelController(ForumControl.class);
        ForumMessage forumMessage;
        var forumMessageName = spec.getForumMessageName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            forumMessage = forumControl.getForumMessageByName(forumMessageName);
        } else { // EditMode.UPDATE
            forumMessage = forumControl.getForumMessageByNameForUpdate(forumMessageName);
        }

        if(forumMessage == null) {
            addExecutionError(ExecutionErrors.UnknownForumMessageName.name(), forumMessageName);
        }

        return forumMessage;
    }

    @Override
    public ForumMessage getLockEntity(ForumMessage forumMessage) {
        return forumMessage;
    }

    @Override
    public void fillInResult(EditBlogCommentResult result, ForumMessage forumMessage) {
        var forumControl = Session.getModelController(ForumControl.class);

        result.setForumMessage(forumControl.getForumMessageTransfer(getUserVisit(), forumMessage));
    }

    @Override
    public void doLock(BlogCommentEdit edit, ForumMessage forumMessage) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumMessageDetail = forumMessage.getLastDetail();

        forumMessageIcon = forumMessageDetail.getIcon();

        edit.setForumMessageIconName(forumMessageIcon == null? null: forumMessageIcon.getLastDetail().getIconName());
        edit.setPostedTime(DateUtils.getInstance().formatTypicalDateTime(getUserVisit(), getPreferredDateTimeFormat(), forumMessageDetail.getPostedTime()));

        var forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_TITLE);
        var preferredLanguage = getPreferredLanguage();
        var forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            var forumStringMessagePart = forumControl.getForumStringMessagePart(forumMessagePart);

            edit.setTitle(forumStringMessagePart.getString());
        }

        forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_CONTENT);
        forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            var forumClobMessagePart = forumControl.getForumClobMessagePart(forumMessagePart);

            edit.setContentMimeTypeName(forumMessagePart.getLastDetail().getMimeType().getLastDetail().getMimeTypeName());
            edit.setContent(forumClobMessagePart.getClob());
        }
    }

    Icon forumMessageIcon = null;
    MimeType contentMimeType = null;

    @Override
    public void canUpdate(ForumMessage forumMessage) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumMessageDetail = forumMessage.getLastDetail();

        if(forumMessageDetail.getForumMessageType().getForumMessageTypeName().equals(ForumConstants.ForumMessageType_BLOG_COMMENT)) {
            var iconControl = Session.getModelController(IconControl.class);
            var forumMessageIconName = edit.getForumMessageIconName();

            forumMessageIcon = forumMessageIconName == null? null: iconControl.getIconByName(forumMessageIconName);

            if(forumMessageIconName == null || forumMessageIcon != null) {
                if(forumMessageIcon != null) {
                    var iconUsageType = iconControl.getIconUsageTypeByName(IconConstants.IconUsageType_FORUM_MESSAGE);
                    var iconUsage = iconControl.getIconUsage(iconUsageType, forumMessageIcon);

                    if(iconUsage == null) {
                        addExecutionError(ExecutionErrors.UnknownIconUsage.name());
                    }
                }

                if(!hasExecutionErrors()) {
                    var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
                    var contentMimeTypeName = edit.getContentMimeTypeName();

                    contentMimeType = contentMimeTypeName == null? null: mimeTypeControl.getMimeTypeByName(contentMimeTypeName);

                    if(contentMimeType != null) {
                        var forum = forumControl.getDefaultForumForumThread(forumMessageDetail.getForumThread()).getForum();
                        var forumMimeType = forumControl.getForumMimeType(forum, contentMimeType);

                        if(forumMimeType == null) {
                            addExecutionError(ExecutionErrors.UnknownForumMimeType.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownContentMimeTypeName.name(), contentMimeTypeName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownIconName.name(), forumMessageIconName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidForumMessageType.name());
        }
    }

    @Override
    public void doUpdate(ForumMessage forumMessage) {
        var forumControl = Session.getModelController(ForumControl.class);
        var partyPK = getPartyPK();
        var forumMessageDetailValue = forumControl.getForumMessageDetailValueForUpdate(forumMessage);

        forumMessageDetailValue.setIconPK(forumMessageIcon == null? null: forumMessageIcon.getPrimaryKey());
        forumMessageDetailValue.setPostedTime(Long.valueOf(edit.getPostedTime()));

        forumControl.updateForumMessageFromValue(forumMessageDetailValue, partyPK);

        var forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_TITLE);
        var preferredLanguage = getPreferredLanguage();
        var forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            var forumStringMessagePartValue = forumControl.getForumStringMessagePartValueForUpdate(forumMessagePart);

            forumStringMessagePartValue.setString(edit.getTitle());

            forumControl.updateForumStringMessagePartFromValue(forumStringMessagePartValue, partyPK);
        }

        forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_CONTENT);
        forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            var forumMessagePartDetailValue = forumControl.getForumMessagePartDetailValueForUpdate(forumMessagePart);
            var forumClobMessagePartValue = forumControl.getForumClobMessagePartValueForUpdate(forumMessagePart);

            forumMessagePartDetailValue.setMimeTypePK(contentMimeType.getPrimaryKey());
            forumClobMessagePartValue.setClob(edit.getContent());

            forumControl.updateForumMessagePartFromValue(forumMessagePartDetailValue, partyPK);
            forumControl.updateForumClobMessagePartFromValue(forumClobMessagePartValue, partyPK);
        }
    }
    
}
