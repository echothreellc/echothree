// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.server.IconControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumClobMessagePart;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumMessageDetail;
import com.echothree.model.data.forum.server.entity.ForumMessagePart;
import com.echothree.model.data.forum.server.entity.ForumMessagePartType;
import com.echothree.model.data.forum.server.entity.ForumMimeType;
import com.echothree.model.data.forum.server.entity.ForumStringMessagePart;
import com.echothree.model.data.forum.server.value.ForumClobMessagePartValue;
import com.echothree.model.data.forum.server.value.ForumMessageDetailValue;
import com.echothree.model.data.forum.server.value.ForumMessagePartDetailValue;
import com.echothree.model.data.forum.server.value.ForumStringMessagePartValue;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.icon.server.entity.IconUsage;
import com.echothree.model.data.icon.server.entity.IconUsageType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public EditBlogCommentCommand(UserVisitPK userVisitPK, EditBlogCommentForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        ForumControl forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        ForumMessage forumMessage = null;
        String forumMessageName = spec.getForumMessageName();

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
        ForumControl forumControl = (ForumControl)Session.getModelController(ForumControl.class);

        result.setForumMessage(forumControl.getForumMessageTransfer(getUserVisit(), forumMessage));
    }

    @Override
    public void doLock(BlogCommentEdit edit, ForumMessage forumMessage) {
        ForumControl forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        ForumMessageDetail forumMessageDetail = forumMessage.getLastDetail();

        forumMessageIcon = forumMessageDetail.getIcon();

        edit.setForumMessageIconName(forumMessageIcon == null? null: forumMessageIcon.getLastDetail().getIconName());
        edit.setPostedTime(DateUtils.getInstance().formatTypicalDateTime(getUserVisit(), getPreferredDateTimeFormat(), forumMessageDetail.getPostedTime()));

        ForumMessagePartType forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_TITLE);
        Language preferredLanguage = getPreferredLanguage();
        ForumMessagePart forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            ForumStringMessagePart forumStringMessagePart = forumControl.getForumStringMessagePart(forumMessagePart);

            edit.setTitle(forumStringMessagePart.getString());
        }

        forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_CONTENT);
        forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            ForumClobMessagePart forumClobMessagePart = forumControl.getForumClobMessagePart(forumMessagePart);

            edit.setContentMimeTypeName(forumMessagePart.getLastDetail().getMimeType().getLastDetail().getMimeTypeName());
            edit.setContent(forumClobMessagePart.getClob());
        }
    }

    Icon forumMessageIcon = null;
    MimeType contentMimeType = null;

    @Override
    public void canUpdate(ForumMessage forumMessage) {
        ForumControl forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        ForumMessageDetail forumMessageDetail = forumMessage.getLastDetail();

        if(forumMessageDetail.getForumMessageType().getForumMessageTypeName().equals(ForumConstants.ForumMessageType_BLOG_COMMENT)) {
            IconControl iconControl = (IconControl)Session.getModelController(IconControl.class);
            String forumMessageIconName = edit.getForumMessageIconName();

            forumMessageIcon = forumMessageIconName == null? null: iconControl.getIconByName(forumMessageIconName);

            if(forumMessageIconName == null || forumMessageIcon != null) {
                if(forumMessageIcon != null) {
                    IconUsageType iconUsageType = iconControl.getIconUsageTypeByName(IconConstants.IconUsageType_FORUM_MESSAGE);
                    IconUsage iconUsage = iconControl.getIconUsage(iconUsageType, forumMessageIcon);

                    if(iconUsage == null) {
                        addExecutionError(ExecutionErrors.UnknownIconUsage.name());
                    }
                }

                if(!hasExecutionErrors()) {
                    CoreControl coreControl = getCoreControl();
                    String contentMimeTypeName = edit.getContentMimeTypeName();

                    contentMimeType = contentMimeTypeName == null? null: coreControl.getMimeTypeByName(contentMimeTypeName);

                    if(contentMimeType != null) {
                        Forum forum = forumControl.getDefaultForumForumThread(forumMessageDetail.getForumThread()).getForum();
                        ForumMimeType forumMimeType = forumControl.getForumMimeType(forum, contentMimeType);

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
        ForumControl forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        PartyPK partyPK = getPartyPK();
        ForumMessageDetailValue forumMessageDetailValue = forumControl.getForumMessageDetailValueForUpdate(forumMessage);

        forumMessageDetailValue.setIconPK(forumMessageIcon == null? null: forumMessageIcon.getPrimaryKey());
        forumMessageDetailValue.setPostedTime(Long.valueOf(edit.getPostedTime()));

        forumControl.updateForumMessageFromValue(forumMessageDetailValue, partyPK);

        ForumMessagePartType forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_TITLE);
        Language preferredLanguage = getPreferredLanguage();
        ForumMessagePart forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            ForumStringMessagePartValue forumStringMessagePartValue = forumControl.getForumStringMessagePartValueForUpdate(forumMessagePart);

            forumStringMessagePartValue.setString(edit.getTitle());

            forumControl.updateForumStringMessagePartFromValue(forumStringMessagePartValue, partyPK);
        }

        forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_CONTENT);
        forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            ForumMessagePartDetailValue forumMessagePartDetailValue = forumControl.getForumMessagePartDetailValueForUpdate(forumMessagePart);
            ForumClobMessagePartValue forumClobMessagePartValue = forumControl.getForumClobMessagePartValueForUpdate(forumMessagePart);

            forumMessagePartDetailValue.setMimeTypePK(contentMimeType.getPrimaryKey());
            forumClobMessagePartValue.setClob(edit.getContent());

            forumControl.updateForumMessagePartFromValue(forumMessagePartDetailValue, partyPK);
            forumControl.updateForumClobMessagePartFromValue(forumClobMessagePartValue, partyPK);
        }
    }
    
}
