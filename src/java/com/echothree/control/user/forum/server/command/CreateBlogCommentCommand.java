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

import com.echothree.control.user.forum.common.form.CreateBlogCommentForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumLogic;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateBlogCommentCommand
        extends BaseSimpleCommand<CreateBlogCommentForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Username", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("ParentForumMessageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostedTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ForumMessageIconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Title", FieldType.STRING, true, 1L, 512L),
                new FieldDefinition("ContentMimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("Content", FieldType.STRING, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateBlogCommentCommand */
    public CreateBlogCommentCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var userControl = Session.getModelController(UserControl.class);
        var result = ForumResultFactory.getCreateBlogCommentResult();
        var username = form.getUsername();
        var userLogin = username == null ? null : userControl.getUserLoginByUsername(username);

        if(username == null || userLogin != null) {
            var forumControl = Session.getModelController(ForumControl.class);
            var parentForumMessageName = form.getParentForumMessageName();
            var parentForumMessage = forumControl.getForumMessageByName(parentForumMessageName);

            if(parentForumMessage != null) {
                var parentForumMessageDetail = parentForumMessage.getLastDetail();
                var forumMessageTypeName = parentForumMessageDetail.getForumMessageType().getForumMessageTypeName();

                if(forumMessageTypeName.equals(ForumConstants.ForumMessageType_BLOG_ENTRY) || forumMessageTypeName.equals(ForumConstants.ForumMessageType_BLOG_COMMENT)) {
                    var forumRoleType = ForumLogic.getInstance().getForumRoleTypeByName(this, ForumConstants.ForumRoleType_COMMENTOR);

                    if(!hasExecutionErrors()) {
                        var party = userLogin == null ? getParty() : userLogin.getParty();
                        var forumThread = parentForumMessageDetail.getForumThread();
                        var forum = forumControl.getDefaultForumForumThread(forumThread).getForum();

                        if(ForumLogic.getInstance().isForumRoleTypePermitted(this, forum, party, forumRoleType)) {
                            var partyControl = Session.getModelController(PartyControl.class);
                            var languageIsoName = form.getLanguageIsoName();
                            var language = languageIsoName == null? getPreferredLanguage(): partyControl.getLanguageByIsoName(languageIsoName);

                            if(language != null) {
                                var iconControl = Session.getModelController(IconControl.class);
                                var forumMessageIconName = form.getForumMessageIconName();
                                var forumMessageIcon = iconControl.getIconByName(forumMessageIconName);

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
                                        var contentMimeTypeName = form.getContentMimeTypeName();
                                        var contentMimeType = contentMimeTypeName == null? null: mimeTypeControl.getMimeTypeByName(contentMimeTypeName);

                                        if(contentMimeType != null) {
                                            var forumMimeType = forumControl.getForumMimeType(forum, contentMimeType);

                                            if(forumMimeType != null) {
                                                var title = form.getTitle();
                                                var rawPostedTime = form.getPostedTime();
                                                var postedTime = rawPostedTime == null? session.START_TIME_LONG: Long.valueOf(rawPostedTime);
                                                var content = form.getContent();
                                                BasePK createdBy = getPartyPK();

                                                var forumMessageType = forumControl.getForumMessageTypeByName(ForumConstants.ForumMessageType_BLOG_COMMENT);
                                                var forumMessage = forumControl.createForumMessage(forumThread, forumMessageType, parentForumMessage, forumMessageIcon, postedTime, createdBy);
                                                forumControl.createForumMessageRole(forumMessage, forumRoleType, party, createdBy);

                                                var forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_TITLE);
                                                var forumMessagePart = forumControl.createForumMessagePart(forumMessage, forumMessagePartType, language, null, createdBy);
                                                forumControl.createForumStringMessagePart(forumMessagePart, title, createdBy);

                                                forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_CONTENT);
                                                forumMessagePart = forumControl.createForumMessagePart(forumMessage, forumMessagePartType, language, contentMimeType, createdBy);
                                                forumControl.createForumClobMessagePart(forumMessagePart, content, createdBy);

                                                result.setEntityRef(forumMessage.getPrimaryKey().getEntityRef());
                                                result.setForumMessageName(forumMessage.getLastDetail().getForumMessageName());
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownForumMimeType.name());
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownContentMimeTypeName.name(), contentMimeTypeName);
                                        }
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownForumMessageIconName.name(), forumMessageIconName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                            }
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidForumMessageType.name(), forumMessageTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownForumParentMessageName.name(), parentForumMessageName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUsername.name(), username);
        }
        
        return result;
    }
    
}
