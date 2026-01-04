// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.control.user.forum.common;

import com.echothree.control.user.forum.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface ForumService
        extends ForumForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Forum Groups
    // --------------------------------------------------------------------------------
    
    CommandResult createForumGroup(UserVisitPK userVisitPK, CreateForumGroupForm form);
    
    CommandResult getForumGroup(UserVisitPK userVisitPK, GetForumGroupForm form);
    
    CommandResult getForumGroups(UserVisitPK userVisitPK, GetForumGroupsForm form);
    
    CommandResult getForumGroupChoices(UserVisitPK userVisitPK, GetForumGroupChoicesForm form);
    
    CommandResult editForumGroup(UserVisitPK userVisitPK, EditForumGroupForm form);
    
    CommandResult deleteForumGroup(UserVisitPK userVisitPK, DeleteForumGroupForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Group Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createForumGroupDescription(UserVisitPK userVisitPK, CreateForumGroupDescriptionForm form);
    
    CommandResult getForumGroupDescriptions(UserVisitPK userVisitPK, GetForumGroupDescriptionsForm form);
    
    CommandResult editForumGroupDescription(UserVisitPK userVisitPK, EditForumGroupDescriptionForm form);
    
    CommandResult deleteForumGroupDescription(UserVisitPK userVisitPK, DeleteForumGroupDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Forums
    // --------------------------------------------------------------------------------
    
    CommandResult createForum(UserVisitPK userVisitPK, CreateForumForm form);
    
    CommandResult getForum(UserVisitPK userVisitPK, GetForumForm form);
    
    CommandResult getForums(UserVisitPK userVisitPK, GetForumsForm form);
    
    CommandResult getForumChoices(UserVisitPK userVisitPK, GetForumChoicesForm form);
    
    CommandResult editForum(UserVisitPK userVisitPK, EditForumForm form);
    
    CommandResult deleteForum(UserVisitPK userVisitPK, DeleteForumForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createForumDescription(UserVisitPK userVisitPK, CreateForumDescriptionForm form);
    
    CommandResult getForumDescriptions(UserVisitPK userVisitPK, GetForumDescriptionsForm form);
    
    CommandResult editForumDescription(UserVisitPK userVisitPK, EditForumDescriptionForm form);
    
    CommandResult deleteForumDescription(UserVisitPK userVisitPK, DeleteForumDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Forum Group Forums
    // -------------------------------------------------------------------------
    
    CommandResult createForumGroupForum(UserVisitPK userVisitPK, CreateForumGroupForumForm form);
    
    CommandResult getForumGroupForums(UserVisitPK userVisitPK, GetForumGroupForumsForm form);
    
    CommandResult setDefaultForumGroupForum(UserVisitPK userVisitPK, SetDefaultForumGroupForumForm form);
    
    CommandResult editForumGroupForum(UserVisitPK userVisitPK, EditForumGroupForumForm form);
    
    CommandResult deleteForumGroupForum(UserVisitPK userVisitPK, DeleteForumGroupForumForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Role Types
    // --------------------------------------------------------------------------------
    
    CommandResult createForumRoleType(UserVisitPK userVisitPK, CreateForumRoleTypeForm form);
    
    CommandResult getForumRoleTypeChoices(UserVisitPK userVisitPK, GetForumRoleTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createForumRoleTypeDescription(UserVisitPK userVisitPK, CreateForumRoleTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Types
    // --------------------------------------------------------------------------------
    
    CommandResult createForumType(UserVisitPK userVisitPK, CreateForumTypeForm form);
    
    CommandResult getForumTypeChoices(UserVisitPK userVisitPK, GetForumTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createForumTypeDescription(UserVisitPK userVisitPK, CreateForumTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Mime Types
    // --------------------------------------------------------------------------------
    
    CommandResult createForumMimeType(UserVisitPK userVisitPK, CreateForumMimeTypeForm form);
    
    CommandResult getForumMimeTypes(UserVisitPK userVisitPK, GetForumMimeTypesForm form);
    
    CommandResult setDefaultForumMimeType(UserVisitPK userVisitPK, SetDefaultForumMimeTypeForm form);
    
    CommandResult editForumMimeType(UserVisitPK userVisitPK, EditForumMimeTypeForm form);
    
    CommandResult deleteForumMimeType(UserVisitPK userVisitPK, DeleteForumMimeTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Party Roles
    // --------------------------------------------------------------------------------
    
    CommandResult createForumPartyRole(UserVisitPK userVisitPK, CreateForumPartyRoleForm form);
    
    CommandResult getForumPartyRoles(UserVisitPK userVisitPK, GetForumPartyRolesForm form);
    
    CommandResult deleteForumPartyRole(UserVisitPK userVisitPK, DeleteForumPartyRoleForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Party Type Roles
    // --------------------------------------------------------------------------------
    
    CommandResult createForumPartyTypeRole(UserVisitPK userVisitPK, CreateForumPartyTypeRoleForm form);
    
    CommandResult getForumPartyTypeRoles(UserVisitPK userVisitPK, GetForumPartyTypeRolesForm form);
    
    CommandResult deleteForumPartyTypeRole(UserVisitPK userVisitPK, DeleteForumPartyTypeRoleForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Type Message Types
    // --------------------------------------------------------------------------------

    CommandResult createForumTypeMessageType(UserVisitPK userVisitPK, CreateForumTypeMessageTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Forum Forum Threads
    // -------------------------------------------------------------------------
    
    CommandResult createForumForumThread(UserVisitPK userVisitPK, CreateForumForumThreadForm form);
    
    CommandResult getForumForumThreads(UserVisitPK userVisitPK, GetForumForumThreadsForm form);
    
    CommandResult setDefaultForumForumThread(UserVisitPK userVisitPK, SetDefaultForumForumThreadForm form);
    
    CommandResult editForumForumThread(UserVisitPK userVisitPK, EditForumForumThreadForm form);
    
    CommandResult deleteForumForumThread(UserVisitPK userVisitPK, DeleteForumForumThreadForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Threads
    // --------------------------------------------------------------------------------
    
    CommandResult getForumThread(UserVisitPK userVisitPK, GetForumThreadForm form);
    
    CommandResult getForumThreads(UserVisitPK userVisitPK, GetForumThreadsForm form);
    
    CommandResult deleteForumThread(UserVisitPK userVisitPK, DeleteForumThreadForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Messages
    // --------------------------------------------------------------------------------
    
    CommandResult getForumMessage(UserVisitPK userVisitPK, GetForumMessageForm form);
    
    CommandResult getForumMessages(UserVisitPK userVisitPK, GetForumMessagesForm form);
    
    CommandResult deleteForumMessage(UserVisitPK userVisitPK, DeleteForumMessageForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Message Attachments
    // --------------------------------------------------------------------------------

    CommandResult createForumMessageAttachment(UserVisitPK userVisitPK, CreateForumMessageAttachmentForm form);

    CommandResult getForumMessageAttachment(UserVisitPK userVisitPK, GetForumMessageAttachmentForm form);

    CommandResult getForumMessageAttachments(UserVisitPK userVisitPK, GetForumMessageAttachmentsForm form);

    CommandResult editForumMessageAttachment(UserVisitPK userVisitPK, EditForumMessageAttachmentForm form);

    CommandResult deleteForumMessageAttachment(UserVisitPK userVisitPK, DeleteForumMessageAttachmentForm form);

    // --------------------------------------------------------------------------------
    //   Forum Message Attachment Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createForumMessageAttachmentDescription(UserVisitPK userVisitPK, CreateForumMessageAttachmentDescriptionForm form);

    CommandResult getForumMessageAttachmentDescription(UserVisitPK userVisitPK, GetForumMessageAttachmentDescriptionForm form);

    CommandResult getForumMessageAttachmentDescriptions(UserVisitPK userVisitPK, GetForumMessageAttachmentDescriptionsForm form);

    CommandResult editForumMessageAttachmentDescription(UserVisitPK userVisitPK, EditForumMessageAttachmentDescriptionForm form);

    CommandResult deleteForumMessageAttachmentDescription(UserVisitPK userVisitPK, DeleteForumMessageAttachmentDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Forum Message Part Types
    // --------------------------------------------------------------------------------
    
    CommandResult createForumMessagePartType(UserVisitPK userVisitPK, CreateForumMessagePartTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Message Part Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createForumMessagePartTypeDescription(UserVisitPK userVisitPK, CreateForumMessagePartTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Message Types
    // --------------------------------------------------------------------------------
    
    CommandResult createForumMessageType(UserVisitPK userVisitPK, CreateForumMessageTypeForm form);
    
    CommandResult getForumMessageTypeChoices(UserVisitPK userVisitPK, GetForumMessageTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createForumMessageTypeDescription(UserVisitPK userVisitPK, CreateForumMessageTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Part Types
    // --------------------------------------------------------------------------------
    
    CommandResult createForumMessageTypePartType(UserVisitPK userVisitPK, CreateForumMessageTypePartTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Blog Entries
    // --------------------------------------------------------------------------------
    
    CommandResult createBlogEntry(UserVisitPK userVisitPK, CreateBlogEntryForm form);
    
    CommandResult editBlogEntry(UserVisitPK userVisitPK, EditBlogEntryForm form);
    
    // --------------------------------------------------------------------------------
    //   Blog Comments
    // --------------------------------------------------------------------------------
    
    CommandResult createBlogComment(UserVisitPK userVisitPK, CreateBlogCommentForm form);
    
    CommandResult editBlogComment(UserVisitPK userVisitPK, EditBlogCommentForm form);
    
}
