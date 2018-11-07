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

import com.echothree.control.user.forum.common.form.CreateBlogCommentForm;
import com.echothree.control.user.forum.common.result.CreateBlogCommentResult;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumLogic;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.server.IconControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumMessageDetail;
import com.echothree.model.data.forum.server.entity.ForumMessagePart;
import com.echothree.model.data.forum.server.entity.ForumMessagePartType;
import com.echothree.model.data.forum.server.entity.ForumMessageType;
import com.echothree.model.data.forum.server.entity.ForumMimeType;
import com.echothree.model.data.forum.server.entity.ForumRoleType;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.icon.server.entity.IconUsage;
import com.echothree.model.data.icon.server.entity.IconUsageType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
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
    public CreateBlogCommentCommand(UserVisitPK userVisitPK, CreateBlogCommentForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
        CreateBlogCommentResult result = ForumResultFactory.getCreateBlogCommentResult();
        String username = form.getUsername();
        UserLogin userLogin = username == null ? null : userControl.getUserLoginByUsername(username);

        if(username == null || userLogin != null) {
            ForumControl forumControl = (ForumControl)Session.getModelController(ForumControl.class);
            String parentForumMessageName = form.getParentForumMessageName();
            ForumMessage parentForumMessage = forumControl.getForumMessageByName(parentForumMessageName);

            if(parentForumMessage != null) {
                ForumMessageDetail parentForumMessageDetail = parentForumMessage.getLastDetail();
                String forumMessageTypeName = parentForumMessageDetail.getForumMessageType().getForumMessageTypeName();

                if(forumMessageTypeName.equals(ForumConstants.ForumMessageType_BLOG_ENTRY) || forumMessageTypeName.equals(ForumConstants.ForumMessageType_BLOG_COMMENT)) {
                    ForumRoleType forumRoleType = ForumLogic.getInstance().getForumRoleTypeByName(this, ForumConstants.ForumRoleType_COMMENTOR);

                    if(!hasExecutionErrors()) {
                        Party party = userLogin == null ? getParty() : userLogin.getParty();
                        ForumThread forumThread = parentForumMessageDetail.getForumThread();
                        Forum forum = forumControl.getDefaultForumForumThread(forumThread).getForum();

                        if(ForumLogic.getInstance().isForumRoleTypePermitted(this, forum, party, forumRoleType)) {
                            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                            String languageIsoName = form.getLanguageIsoName();
                            Language language = languageIsoName == null? getPreferredLanguage(): partyControl.getLanguageByIsoName(languageIsoName);

                            if(language != null) {
                                IconControl iconControl = (IconControl)Session.getModelController(IconControl.class);
                                String forumMessageIconName = form.getForumMessageIconName();
                                Icon forumMessageIcon = iconControl.getIconByName(forumMessageIconName);

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
                                        String contentMimeTypeName = form.getContentMimeTypeName();
                                        MimeType contentMimeType = contentMimeTypeName == null? null: coreControl.getMimeTypeByName(contentMimeTypeName);

                                        if(contentMimeType != null) {
                                            ForumMimeType forumMimeType = forumControl.getForumMimeType(forum, contentMimeType);

                                            if(forumMimeType != null) {
                                                String title = form.getTitle();
                                                String rawPostedTime = form.getPostedTime();
                                                Long postedTime = rawPostedTime == null? session.START_TIME_LONG: Long.valueOf(rawPostedTime);
                                                String content = form.getContent();
                                                BasePK createdBy = getPartyPK();

                                                ForumMessageType forumMessageType = forumControl.getForumMessageTypeByName(ForumConstants.ForumMessageType_BLOG_COMMENT);
                                                ForumMessage forumMessage = forumControl.createForumMessage(forumThread, forumMessageType, parentForumMessage, forumMessageIcon, postedTime, createdBy);
                                                forumControl.createForumMessageRole(forumMessage, forumRoleType, party, createdBy);

                                                ForumMessagePartType forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_TITLE);
                                                ForumMessagePart forumMessagePart = forumControl.createForumMessagePart(forumMessage, forumMessagePartType, language, null, createdBy);
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
