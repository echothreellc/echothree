// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.forum.common.form.CreateBlogEntryForm;
import com.echothree.control.user.forum.common.result.CreateBlogEntryResult;
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

public class CreateBlogEntryCommand
        extends BaseSimpleCommand<CreateBlogEntryForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Username", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ForumThreadIconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostedTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("ForumMessageIconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Title", FieldType.STRING, true, 1L, 512L),
                new FieldDefinition("FeedSummaryMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("FeedSummary", FieldType.STRING, false, null, null),
                new FieldDefinition("SummaryMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Summary", FieldType.STRING, false, null, null),
                new FieldDefinition("ContentMimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("Content", FieldType.STRING, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateBlogEntryCommand */
    public CreateBlogEntryCommand(UserVisitPK userVisitPK, CreateBlogEntryForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        CreateBlogEntryResult result = ForumResultFactory.getCreateBlogEntryResult();
        String username = form.getUsername();
        UserLogin userLogin = username == null ? null : userControl.getUserLoginByUsername(username);
        
        if(username == null || userLogin != null) {
            var forumControl = (ForumControl)Session.getModelController(ForumControl.class);
            String forumName = form.getForumName();
            Forum forum = forumControl.getForumByName(forumName);

            if(forum != null) {
                String forumTypeName = forum.getLastDetail().getForumType().getForumTypeName();

                if(forumTypeName.equals(ForumConstants.ForumType_BLOG)) {
                    Party party = userLogin == null ? getParty() : userLogin.getParty();
                    ForumRoleType forumRoleType = ForumLogic.getInstance().getForumRoleTypeByName(this, ForumConstants.ForumRoleType_AUTHOR);

                    if(!hasExecutionErrors()) {
                        if(ForumLogic.getInstance().isForumRoleTypePermitted(this, forum, party, forumRoleType)) {
                            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                            String languageIsoName = form.getLanguageIsoName();
                            Language language = languageIsoName == null? getPreferredLanguage(): partyControl.getLanguageByIsoName(languageIsoName);

                            if(language != null) {
                                var iconControl = (IconControl)Session.getModelController(IconControl.class);
                                String forumThreadIconName = form.getForumThreadIconName();
                                Icon forumThreadIcon = iconControl.getIconByName(forumThreadIconName);

                                if(forumThreadIconName == null || forumThreadIcon != null) {
                                    if(forumThreadIcon != null) {
                                        IconUsageType iconUsageType = iconControl.getIconUsageTypeByName(IconConstants.IconUsageType_FORUM_THREAD);
                                        IconUsage iconUsage = iconControl.getIconUsage(iconUsageType, forumThreadIcon);

                                        if(iconUsage == null) {
                                            addExecutionError(ExecutionErrors.UnknownIconUsage.name());
                                        }
                                    }

                                    if(!hasExecutionErrors()) {
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
                                                String feedSummaryMimeTypeName = form.getFeedSummaryMimeTypeName();
                                                String feedSummary = form.getFeedSummary();
                                                int feedSummaryParameterCount = (feedSummaryMimeTypeName == null? 0: 1) + (feedSummary == null? 0: 1);

                                                if(feedSummaryParameterCount == 0 || feedSummaryParameterCount == 2) {
                                                    var coreControl = getCoreControl();
                                                    MimeType feedSummaryMimeType = feedSummaryMimeTypeName == null? null: coreControl.getMimeTypeByName(feedSummaryMimeTypeName);

                                                    if(feedSummaryMimeTypeName == null || feedSummaryMimeType != null) {
                                                        ForumMimeType forumMimeType = feedSummaryMimeType == null? null: forumControl.getForumMimeType(forum, feedSummaryMimeType);

                                                        if(feedSummaryMimeType == null || forumMimeType != null) {
                                                            String summaryMimeTypeName = form.getSummaryMimeTypeName();
                                                            String summary = form.getSummary();
                                                            int summaryParameterCount = (summaryMimeTypeName == null? 0: 1) + (summary == null? 0: 1);

                                                            if(summaryParameterCount == 0 || summaryParameterCount == 2) {
                                                                MimeType summaryMimeType = summaryMimeTypeName == null? null: coreControl.getMimeTypeByName(summaryMimeTypeName);

                                                                if(summaryMimeTypeName == null || summaryMimeType != null) {
                                                                    forumMimeType = summaryMimeType == null? null: forumControl.getForumMimeType(forum, summaryMimeType);

                                                                    if(summaryMimeType == null || forumMimeType != null) {
                                                                        String contentMimeTypeName = form.getContentMimeTypeName();
                                                                        MimeType contentMimeType = contentMimeTypeName == null? null: coreControl.getMimeTypeByName(contentMimeTypeName);

                                                                        if(contentMimeType != null) {
                                                                            forumMimeType = forumControl.getForumMimeType(forum, contentMimeType);

                                                                            if(forumMimeType != null) {
                                                                                String title = form.getTitle();
                                                                                String rawPostedTime = form.getPostedTime();
                                                                                Long postedTime = rawPostedTime == null? session.START_TIME_LONG: Long.valueOf(rawPostedTime);
                                                                                Integer sortOrder = Integer.valueOf(form.getSortOrder());
                                                                                String content = form.getContent();
                                                                                BasePK createdBy = getPartyPK();

                                                                                ForumThread forumThread = forumControl.createForumThread(forum, forumThreadIcon, postedTime, sortOrder, createdBy);
                                                                                forumControl.createForumForumThread(forum, forumThread, Boolean.TRUE, 1, createdBy);
                                                                                ForumMessageType forumMessageType = forumControl.getForumMessageTypeByName(ForumConstants.ForumMessageType_BLOG_ENTRY);
                                                                                ForumMessage forumMessage = forumControl.createForumMessage(forumThread, forumMessageType, null, forumMessageIcon, postedTime, createdBy);
                                                                                forumControl.createForumMessageRole(forumMessage, forumRoleType, party, createdBy);

                                                                                ForumMessagePartType forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_TITLE);
                                                                                ForumMessagePart forumMessagePart = forumControl.createForumMessagePart(forumMessage, forumMessagePartType, language, null, createdBy);
                                                                                forumControl.createForumStringMessagePart(forumMessagePart, title, createdBy);

                                                                                if(feedSummary != null) {
                                                                                    forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_FEED_SUMMARY);
                                                                                    forumMessagePart = forumControl.createForumMessagePart(forumMessage, forumMessagePartType, language, feedSummaryMimeType, createdBy);
                                                                                    forumControl.createForumClobMessagePart(forumMessagePart, feedSummary, createdBy);
                                                                                }

                                                                                if(summary != null) {
                                                                                    forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_SUMMARY);
                                                                                    forumMessagePart = forumControl.createForumMessagePart(forumMessage, forumMessagePartType, language, summaryMimeType, createdBy);
                                                                                    forumControl.createForumClobMessagePart(forumMessagePart, summary, createdBy);
                                                                                }

                                                                                forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_CONTENT);
                                                                                forumMessagePart = forumControl.createForumMessagePart(forumMessage, forumMessagePartType, language, contentMimeType, createdBy);
                                                                                forumControl.createForumClobMessagePart(forumMessagePart, content, createdBy);

                                                                                result.setForumThreadName(forumThread.getLastDetail().getForumThreadName());
                                                                                result.setForumMessageName(forumMessage.getLastDetail().getForumMessageName());
                                                                                result.setEntityRef(forumMessage.getPrimaryKey().getEntityRef());
                                                                            } else {
                                                                                addExecutionError(ExecutionErrors.UnknownForumMimeType.name());
                                                                            }
                                                                        } else {
                                                                            addExecutionError(ExecutionErrors.UnknownContentMimeTypeName.name(), contentMimeTypeName);
                                                                        }
                                                                    } else {
                                                                        addExecutionError(ExecutionErrors.UnknownForumMimeType.name());
                                                                    }
                                                                } else {
                                                                    addExecutionError(ExecutionErrors.UnknownSummaryMimeTypeName.name(), summaryMimeTypeName);
                                                                }
                                                            } else {
                                                                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                                                            }
                                                        } else {
                                                            addExecutionError(ExecutionErrors.UnknownForumMimeType.name());
                                                        }
                                                    } else {
                                                        addExecutionError(ExecutionErrors.UnknownFeedSummaryMimeTypeName.name(), feedSummaryMimeTypeName);
                                                    }
                                                } else {
                                                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                                                }
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownForumMessageIconName.name(), forumMessageIconName);
                                        }
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownForumThreadIconName.name(), forumThreadIconName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                            }
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidForumType.name(), forumTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUsername.name(), username);
        }
        
        return result;
    }
    
}
