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

import com.echothree.control.user.forum.common.edit.BlogEntryEdit;
import com.echothree.control.user.forum.common.edit.ForumEditFactory;
import com.echothree.control.user.forum.common.form.EditBlogEntryForm;
import com.echothree.control.user.forum.common.result.EditBlogEntryResult;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.control.user.forum.common.spec.ForumMessageSpec;
import com.echothree.model.control.core.common.EventTypes;
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
import com.echothree.model.data.forum.server.entity.ForumThreadDetail;
import com.echothree.model.data.forum.server.value.ForumClobMessagePartValue;
import com.echothree.model.data.forum.server.value.ForumMessageDetailValue;
import com.echothree.model.data.forum.server.value.ForumMessagePartDetailValue;
import com.echothree.model.data.forum.server.value.ForumStringMessagePartValue;
import com.echothree.model.data.forum.server.value.ForumThreadDetailValue;
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

public class EditBlogEntryCommand
        extends BaseAbstractEditCommand<ForumMessageSpec, BlogEntryEdit, EditBlogEntryResult, ForumMessage, ForumMessage> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumMessageName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumThreadIconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostedTime", FieldType.DATE_TIME, true, null, null),
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
    
    /** Creates a new instance of EditBlogEntryCommand */
    public EditBlogEntryCommand(UserVisitPK userVisitPK, EditBlogEntryForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditBlogEntryResult getResult() {
        return ForumResultFactory.getEditBlogEntryResult();
    }

    @Override
    public BlogEntryEdit getEdit() {
        return ForumEditFactory.getBlogEntryEdit();
    }

    @Override
    public ForumMessage getEntity(EditBlogEntryResult result) {
        var forumControl = (ForumControl)Session.getModelController(ForumControl.class);
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
    public void fillInResult(EditBlogEntryResult result, ForumMessage forumMessage) {
        var forumControl = (ForumControl)Session.getModelController(ForumControl.class);

        result.setForumMessage(forumControl.getForumMessageTransfer(getUserVisit(), forumMessage));
    }

    @Override
    public void doLock(BlogEntryEdit edit, ForumMessage forumMessage) {
        var forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        ForumMessageDetail forumMessageDetail = forumMessage.getLastDetail();
        ForumThreadDetail forumThreadDetail = forumMessageDetail.getForumThread().getLastDetail();

        forumThreadIcon = forumThreadDetail.getIcon();
        forumMessageIcon = forumMessageDetail.getIcon();

        sendEventUsingNames(forumMessage.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());

        edit.setForumThreadIconName(forumThreadIcon == null? null: forumThreadIcon.getLastDetail().getIconName());
        edit.setSortOrder(forumThreadDetail.getSortOrder().toString());
        edit.setForumMessageIconName(forumMessageIcon == null? null: forumMessageIcon.getLastDetail().getIconName());
        edit.setPostedTime(DateUtils.getInstance().formatTypicalDateTime(getUserVisit(), getPreferredDateTimeFormat(), forumMessageDetail.getPostedTime()));

        ForumMessagePartType forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_TITLE);
        Language preferredLanguage = getPreferredLanguage();
        ForumMessagePart forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            ForumStringMessagePart forumStringMessagePart = forumControl.getForumStringMessagePart(forumMessagePart);

            edit.setTitle(forumStringMessagePart.getString());
        }

        forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_FEED_SUMMARY);
        forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            ForumClobMessagePart forumClobMessagePart = forumControl.getForumClobMessagePart(forumMessagePart);

            edit.setFeedSummaryMimeTypeName(forumMessagePart.getLastDetail().getMimeType().getLastDetail().getMimeTypeName());
            edit.setFeedSummary(forumClobMessagePart.getClob());
        }

        forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_SUMMARY);
        forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            ForumClobMessagePart forumClobMessagePart = forumControl.getForumClobMessagePart(forumMessagePart);

            edit.setSummaryMimeTypeName(forumMessagePart.getLastDetail().getMimeType().getLastDetail().getMimeTypeName());
            edit.setSummary(forumClobMessagePart.getClob());
        }

        forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_CONTENT);
        forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            ForumClobMessagePart forumClobMessagePart = forumControl.getForumClobMessagePart(forumMessagePart);

            edit.setContentMimeTypeName(forumMessagePart.getLastDetail().getMimeType().getLastDetail().getMimeTypeName());
            edit.setContent(forumClobMessagePart.getClob());
        }
    }

    Icon forumThreadIcon = null;
    Icon forumMessageIcon = null;
    MimeType feedSummaryMimeType = null;
    MimeType summaryMimeType = null;
    MimeType contentMimeType = null;

    @Override
    public void canUpdate(ForumMessage forumMessage) {
        var forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        ForumMessageDetail forumMessageDetail = forumMessage.getLastDetail();

        if(forumMessageDetail.getForumMessageType().getForumMessageTypeName().equals(ForumConstants.ForumMessageType_BLOG_ENTRY)) {
            var iconControl = (IconControl)Session.getModelController(IconControl.class);
            String forumThreadIconName = edit.getForumThreadIconName();

            forumThreadIcon = iconControl.getIconByName(forumThreadIconName);

            if(forumThreadIconName == null || forumThreadIcon != null) {
                if(forumThreadIcon != null) {
                    IconUsageType iconUsageType = iconControl.getIconUsageTypeByName(IconConstants.IconUsageType_FORUM_THREAD);
                    IconUsage iconUsage = iconControl.getIconUsage(iconUsageType, forumThreadIcon);

                    if(iconUsage == null) {
                        addExecutionError(ExecutionErrors.UnknownIconUsage.name());
                    }
                }

                if(!hasExecutionErrors()) {
                    String forumMessageIconName = edit.getForumMessageIconName();

                    forumMessageIcon = iconControl.getIconByName(forumMessageIconName);

                    if(forumMessageIconName == null || forumMessageIcon != null) {
                        if(forumMessageIcon != null) {
                            IconUsageType iconUsageType = iconControl.getIconUsageTypeByName(IconConstants.IconUsageType_FORUM_MESSAGE);
                            IconUsage iconUsage = iconControl.getIconUsage(iconUsageType, forumMessageIcon);

                            if(iconUsage == null) {
                                addExecutionError(ExecutionErrors.UnknownIconUsage.name());
                            }
                        }

                        if(!hasExecutionErrors()) {
                            String feedSummaryMimeTypeName = edit.getFeedSummaryMimeTypeName();
                            String feedSummary = edit.getFeedSummary();
                            int feedSummaryParameterCount = (feedSummaryMimeTypeName == null? 0: 1) + (feedSummary == null? 0: 1);

                            if(feedSummaryParameterCount == 0 || feedSummaryParameterCount == 2) {
                                var coreControl = getCoreControl();

                                feedSummaryMimeType = feedSummaryMimeTypeName == null? null: coreControl.getMimeTypeByName(feedSummaryMimeTypeName);

                                if(feedSummaryMimeTypeName == null || feedSummaryMimeType != null) {
                                    Forum forum = forumControl.getDefaultForumForumThread(forumMessageDetail.getForumThread()).getForum();
                                    ForumMimeType forumMimeType = feedSummaryMimeType == null? null: forumControl.getForumMimeType(forum, feedSummaryMimeType);

                                    if(feedSummaryMimeType == null || forumMimeType != null) {
                                        String summaryMimeTypeName = edit.getSummaryMimeTypeName();
                                        String summary = edit.getSummary();
                                        int summaryParameterCount = (summaryMimeTypeName == null? 0: 1) + (summary == null? 0: 1);

                                        if(summaryParameterCount == 0 || summaryParameterCount == 2) {
                                            summaryMimeType = summaryMimeTypeName == null? null: coreControl.getMimeTypeByName(summaryMimeTypeName);

                                            if(summaryMimeTypeName == null || summaryMimeType != null) {
                                                forumMimeType = summaryMimeType == null? null: forumControl.getForumMimeType(forum, summaryMimeType);

                                                if(summaryMimeType == null || forumMimeType != null) {
                                                    String contentMimeTypeName = edit.getContentMimeTypeName();

                                                    contentMimeType = contentMimeTypeName == null? null: coreControl.getMimeTypeByName(contentMimeTypeName);

                                                    if(contentMimeType != null) {
                                                        forumMimeType = forumControl.getForumMimeType(forum, contentMimeType);

                                                        if(forumMimeType == null) {
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
            addExecutionError(ExecutionErrors.InvalidForumMessageType.name());
        }
    }

    @Override
    public void doUpdate(ForumMessage forumMessage) {
        var forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        PartyPK partyPK = getPartyPK();
        ForumMessageDetailValue forumMessageDetailValue = forumControl.getForumMessageDetailValueForUpdate(forumMessage);
        ForumThreadDetailValue forumThreadDetailValue = forumControl.getForumThreadDetailValueForUpdate(forumMessage.getLastDetail().getForumThreadForUpdate());
        Long postedTime = Long.valueOf(edit.getPostedTime());

        if(forumMessageDetailValue.getPostedTime().equals(forumThreadDetailValue.getPostedTime())) {
            forumThreadDetailValue.setPostedTime(postedTime);
        }

        forumThreadDetailValue.setIconPK(forumThreadIcon == null? null: forumThreadIcon.getPrimaryKey());
        forumThreadDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
        forumControl.updateForumThreadFromValue(forumThreadDetailValue, partyPK);

        forumMessageDetailValue.setIconPK(forumMessageIcon == null? null: forumMessageIcon.getPrimaryKey());
        forumMessageDetailValue.setPostedTime(postedTime);
        forumControl.updateForumMessageFromValue(forumMessageDetailValue, partyPK);

        ForumMessagePartType forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_TITLE);
        Language preferredLanguage = getPreferredLanguage();
        ForumMessagePart forumMessagePart = forumControl.getForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart != null) {
            ForumStringMessagePartValue forumStringMessagePartValue = forumControl.getForumStringMessagePartValueForUpdate(forumMessagePart);

            forumStringMessagePartValue.setString(edit.getTitle());

            forumControl.updateForumStringMessagePartFromValue(forumStringMessagePartValue, partyPK);
        }

        forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_FEED_SUMMARY);
        forumMessagePart = forumControl.getForumMessagePartForUpdate(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart == null && feedSummaryMimeType != null) {
            forumMessagePart = forumControl.createForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage, feedSummaryMimeType, partyPK);
            forumControl.createForumClobMessagePart(forumMessagePart, edit.getFeedSummary(), partyPK);
        } else if(forumMessagePart != null && feedSummaryMimeType == null) {
            forumControl.deleteForumMessagePart(forumMessagePart, partyPK);
        } else if(forumMessagePart != null && feedSummaryMimeType != null) {
            ForumMessagePartDetailValue forumMessagePartValue = forumControl.getForumMessagePartDetailValueForUpdate(forumMessagePart);
            ForumClobMessagePartValue forumClobMessagePartValue = forumControl.getForumClobMessagePartValueForUpdate(forumMessagePart);

            forumMessagePartValue.setMimeTypePK(feedSummaryMimeType.getPrimaryKey());
            forumClobMessagePartValue.setClob(edit.getFeedSummary());

            forumControl.updateForumMessagePartFromValue(forumMessagePartValue, partyPK);
            forumControl.updateForumClobMessagePartFromValue(forumClobMessagePartValue, partyPK);
        }

        forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_SUMMARY);
        forumMessagePart = forumControl.getForumMessagePartForUpdate(forumMessage, forumMessagePartType, preferredLanguage);
        if(forumMessagePart == null && summaryMimeType != null) {
            forumMessagePart = forumControl.createForumMessagePart(forumMessage, forumMessagePartType, preferredLanguage, summaryMimeType, partyPK);
            forumControl.createForumClobMessagePart(forumMessagePart, edit.getSummary(), partyPK);
        } else if(forumMessagePart != null && summaryMimeType == null) {
            forumControl.deleteForumMessagePart(forumMessagePart, partyPK);
        } else if(forumMessagePart != null && summaryMimeType != null) {
            ForumMessagePartDetailValue forumMessagePartValue = forumControl.getForumMessagePartDetailValueForUpdate(forumMessagePart);
            ForumClobMessagePartValue forumClobMessagePartValue = forumControl.getForumClobMessagePartValueForUpdate(forumMessagePart);

            forumMessagePartValue.setMimeTypePK(summaryMimeType.getPrimaryKey());
            forumClobMessagePartValue.setClob(edit.getSummary());

            forumControl.updateForumMessagePartFromValue(forumMessagePartValue, partyPK);
            forumControl.updateForumClobMessagePartFromValue(forumClobMessagePartValue, partyPK);
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
