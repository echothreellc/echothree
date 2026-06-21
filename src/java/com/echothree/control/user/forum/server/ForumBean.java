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

package com.echothree.control.user.forum.server;

import com.echothree.control.user.forum.common.ForumRemote;
import com.echothree.control.user.forum.common.form.*;
import com.echothree.control.user.forum.common.result.*;
import com.echothree.control.user.forum.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class ForumBean
        extends ForumFormsImpl
        implements ForumRemote, ForumLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "ForumBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumGroup(UserVisitPK userVisitPK, CreateForumGroupForm form) {
        return CDI.current().select(CreateForumGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumGroupResult> getForumGroup(UserVisitPK userVisitPK, GetForumGroupForm form) {
        return CDI.current().select(GetForumGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumGroupsResult> getForumGroups(UserVisitPK userVisitPK, GetForumGroupsForm form) {
        return CDI.current().select(GetForumGroupsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumGroupChoicesResult> getForumGroupChoices(UserVisitPK userVisitPK, GetForumGroupChoicesForm form) {
        return CDI.current().select(GetForumGroupChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditForumGroupResult> editForumGroup(UserVisitPK userVisitPK, EditForumGroupForm form) {
        return CDI.current().select(EditForumGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteForumGroup(UserVisitPK userVisitPK, DeleteForumGroupForm form) {
        return CDI.current().select(DeleteForumGroupCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Group Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumGroupDescription(UserVisitPK userVisitPK, CreateForumGroupDescriptionForm form) {
        return CDI.current().select(CreateForumGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumGroupDescriptionsResult> getForumGroupDescriptions(UserVisitPK userVisitPK, GetForumGroupDescriptionsForm form) {
        return CDI.current().select(GetForumGroupDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditForumGroupDescriptionResult> editForumGroupDescription(UserVisitPK userVisitPK, EditForumGroupDescriptionForm form) {
        return CDI.current().select(EditForumGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteForumGroupDescription(UserVisitPK userVisitPK, DeleteForumGroupDescriptionForm form) {
        return CDI.current().select(DeleteForumGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forums
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForum(UserVisitPK userVisitPK, CreateForumForm form) {
        return CDI.current().select(CreateForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumResult> getForum(UserVisitPK userVisitPK, GetForumForm form) {
        return CDI.current().select(GetForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumsResult> getForums(UserVisitPK userVisitPK, GetForumsForm form) {
        return CDI.current().select(GetForumsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumChoicesResult> getForumChoices(UserVisitPK userVisitPK, GetForumChoicesForm form) {
        return CDI.current().select(GetForumChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditForumResult> editForum(UserVisitPK userVisitPK, EditForumForm form) {
        return CDI.current().select(EditForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteForum(UserVisitPK userVisitPK, DeleteForumForm form) {
        return CDI.current().select(DeleteForumCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumDescription(UserVisitPK userVisitPK, CreateForumDescriptionForm form) {
        return CDI.current().select(CreateForumDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumDescriptionsResult> getForumDescriptions(UserVisitPK userVisitPK, GetForumDescriptionsForm form) {
        return CDI.current().select(GetForumDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditForumDescriptionResult> editForumDescription(UserVisitPK userVisitPK, EditForumDescriptionForm form) {
        return CDI.current().select(EditForumDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteForumDescription(UserVisitPK userVisitPK, DeleteForumDescriptionForm form) {
        return CDI.current().select(DeleteForumDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Forum Group Forums
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumGroupForum(UserVisitPK userVisitPK, CreateForumGroupForumForm form) {
        return CDI.current().select(CreateForumGroupForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumGroupForumsResult> getForumGroupForums(UserVisitPK userVisitPK, GetForumGroupForumsForm form) {
        return CDI.current().select(GetForumGroupForumsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultForumGroupForum(UserVisitPK userVisitPK, SetDefaultForumGroupForumForm form) {
        return CDI.current().select(SetDefaultForumGroupForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditForumGroupForumResult> editForumGroupForum(UserVisitPK userVisitPK, EditForumGroupForumForm form) {
        return CDI.current().select(EditForumGroupForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteForumGroupForum(UserVisitPK userVisitPK, DeleteForumGroupForumForm form) {
        return CDI.current().select(DeleteForumGroupForumCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumRoleType(UserVisitPK userVisitPK, CreateForumRoleTypeForm form) {
        return CDI.current().select(CreateForumRoleTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumRoleTypeChoicesResult> getForumRoleTypeChoices(UserVisitPK userVisitPK, GetForumRoleTypeChoicesForm form) {
        return CDI.current().select(GetForumRoleTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumRoleTypeDescription(UserVisitPK userVisitPK, CreateForumRoleTypeDescriptionForm form) {
        return CDI.current().select(CreateForumRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumType(UserVisitPK userVisitPK, CreateForumTypeForm form) {
        return CDI.current().select(CreateForumTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumTypeChoicesResult> getForumTypeChoices(UserVisitPK userVisitPK, GetForumTypeChoicesForm form) {
        return CDI.current().select(GetForumTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumTypeDescription(UserVisitPK userVisitPK, CreateForumTypeDescriptionForm form) {
        return CDI.current().select(CreateForumTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Mime Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumMimeType(UserVisitPK userVisitPK, CreateForumMimeTypeForm form) {
        return CDI.current().select(CreateForumMimeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumMimeTypesResult> getForumMimeTypes(UserVisitPK userVisitPK, GetForumMimeTypesForm form) {
        return CDI.current().select(GetForumMimeTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultForumMimeType(UserVisitPK userVisitPK, SetDefaultForumMimeTypeForm form) {
        return CDI.current().select(SetDefaultForumMimeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditForumMimeTypeResult> editForumMimeType(UserVisitPK userVisitPK, EditForumMimeTypeForm form) {
        return CDI.current().select(EditForumMimeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteForumMimeType(UserVisitPK userVisitPK, DeleteForumMimeTypeForm form) {
        return CDI.current().select(DeleteForumMimeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Party Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumPartyRole(UserVisitPK userVisitPK, CreateForumPartyRoleForm form) {
        return CDI.current().select(CreateForumPartyRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumPartyRolesResult> getForumPartyRoles(UserVisitPK userVisitPK, GetForumPartyRolesForm form) {
        return CDI.current().select(GetForumPartyRolesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteForumPartyRole(UserVisitPK userVisitPK, DeleteForumPartyRoleForm form) {
        return CDI.current().select(DeleteForumPartyRoleCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Party Type Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumPartyTypeRole(UserVisitPK userVisitPK, CreateForumPartyTypeRoleForm form) {
        return CDI.current().select(CreateForumPartyTypeRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumPartyTypeRolesResult> getForumPartyTypeRoles(UserVisitPK userVisitPK, GetForumPartyTypeRolesForm form) {
        return CDI.current().select(GetForumPartyTypeRolesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteForumPartyTypeRole(UserVisitPK userVisitPK, DeleteForumPartyTypeRoleForm form) {
        return CDI.current().select(DeleteForumPartyTypeRoleCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Message Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumTypeMessageType(UserVisitPK userVisitPK, CreateForumTypeMessageTypeForm form) {
        return CDI.current().select(CreateForumTypeMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Forum Forum Threads
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumForumThread(UserVisitPK userVisitPK, CreateForumForumThreadForm form) {
        return CDI.current().select(CreateForumForumThreadCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumForumThreadsResult> getForumForumThreads(UserVisitPK userVisitPK, GetForumForumThreadsForm form) {
        return CDI.current().select(GetForumForumThreadsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultForumForumThread(UserVisitPK userVisitPK, SetDefaultForumForumThreadForm form) {
        return CDI.current().select(SetDefaultForumForumThreadCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditForumForumThreadResult> editForumForumThread(UserVisitPK userVisitPK, EditForumForumThreadForm form) {
        return CDI.current().select(EditForumForumThreadCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteForumForumThread(UserVisitPK userVisitPK, DeleteForumForumThreadForm form) {
        return CDI.current().select(DeleteForumForumThreadCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Threads
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<GetForumThreadResult> getForumThread(UserVisitPK userVisitPK, GetForumThreadForm form) {
        return CDI.current().select(GetForumThreadCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumThreadsResult> getForumThreads(UserVisitPK userVisitPK, GetForumThreadsForm form) {
        return CDI.current().select(GetForumThreadsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteForumThread(UserVisitPK userVisitPK, DeleteForumThreadForm form) {
        return CDI.current().select(DeleteForumThreadCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Messages
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<GetForumMessageResult> getForumMessage(UserVisitPK userVisitPK, GetForumMessageForm form) {
        return CDI.current().select(GetForumMessageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumMessagesResult> getForumMessages(UserVisitPK userVisitPK, GetForumMessagesForm form) {
        return CDI.current().select(GetForumMessagesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteForumMessage(UserVisitPK userVisitPK, DeleteForumMessageForm form) {
        return CDI.current().select(DeleteForumMessageCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Attachments
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createForumMessageAttachment(UserVisitPK userVisitPK, CreateForumMessageAttachmentForm form) {
        return CDI.current().select(CreateForumMessageAttachmentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetForumMessageAttachmentResult> getForumMessageAttachment(UserVisitPK userVisitPK, GetForumMessageAttachmentForm form) {
        return CDI.current().select(GetForumMessageAttachmentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetForumMessageAttachmentsResult> getForumMessageAttachments(UserVisitPK userVisitPK, GetForumMessageAttachmentsForm form) {
        return CDI.current().select(GetForumMessageAttachmentsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditForumMessageAttachmentResult> editForumMessageAttachment(UserVisitPK userVisitPK, EditForumMessageAttachmentForm form) {
        return CDI.current().select(EditForumMessageAttachmentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteForumMessageAttachment(UserVisitPK userVisitPK, DeleteForumMessageAttachmentForm form) {
        return CDI.current().select(DeleteForumMessageAttachmentCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Attachment Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createForumMessageAttachmentDescription(UserVisitPK userVisitPK, CreateForumMessageAttachmentDescriptionForm form) {
        return CDI.current().select(CreateForumMessageAttachmentDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetForumMessageAttachmentDescriptionResult> getForumMessageAttachmentDescription(UserVisitPK userVisitPK, GetForumMessageAttachmentDescriptionForm form) {
        return CDI.current().select(GetForumMessageAttachmentDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetForumMessageAttachmentDescriptionsResult> getForumMessageAttachmentDescriptions(UserVisitPK userVisitPK, GetForumMessageAttachmentDescriptionsForm form) {
        return CDI.current().select(GetForumMessageAttachmentDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditForumMessageAttachmentDescriptionResult> editForumMessageAttachmentDescription(UserVisitPK userVisitPK, EditForumMessageAttachmentDescriptionForm form) {
        return CDI.current().select(EditForumMessageAttachmentDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteForumMessageAttachmentDescription(UserVisitPK userVisitPK, DeleteForumMessageAttachmentDescriptionForm form) {
        return CDI.current().select(DeleteForumMessageAttachmentDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Part Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumMessagePartType(UserVisitPK userVisitPK, CreateForumMessagePartTypeForm form) {
        return CDI.current().select(CreateForumMessagePartTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Part Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumMessagePartTypeDescription(UserVisitPK userVisitPK, CreateForumMessagePartTypeDescriptionForm form) {
        return CDI.current().select(CreateForumMessagePartTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumMessageType(UserVisitPK userVisitPK, CreateForumMessageTypeForm form) {
        return CDI.current().select(CreateForumMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetForumMessageTypeChoicesResult> getForumMessageTypeChoices(UserVisitPK userVisitPK, GetForumMessageTypeChoicesForm form) {
        return CDI.current().select(GetForumMessageTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumMessageTypeDescription(UserVisitPK userVisitPK, CreateForumMessageTypeDescriptionForm form) {
        return CDI.current().select(CreateForumMessageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Part Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createForumMessageTypePartType(UserVisitPK userVisitPK, CreateForumMessageTypePartTypeForm form) {
        return CDI.current().select(CreateForumMessageTypePartTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Blog Entries
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateBlogEntryResult> createBlogEntry(UserVisitPK userVisitPK, CreateBlogEntryForm form) {
        return CDI.current().select(CreateBlogEntryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditBlogEntryResult> editBlogEntry(UserVisitPK userVisitPK, EditBlogEntryForm form) {
        return CDI.current().select(EditBlogEntryCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Blog Comments
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateBlogCommentResult> createBlogComment(UserVisitPK userVisitPK, CreateBlogCommentForm form) {
        return CDI.current().select(CreateBlogCommentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditBlogCommentResult> editBlogComment(UserVisitPK userVisitPK, EditBlogCommentForm form) {
        return CDI.current().select(EditBlogCommentCommand.class).get().run(userVisitPK, form);
    }
    
}
