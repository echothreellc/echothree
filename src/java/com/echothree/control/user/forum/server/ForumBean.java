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

package com.echothree.control.user.forum.server;

import com.echothree.control.user.forum.common.ForumRemote;
import com.echothree.control.user.forum.common.form.*;
import com.echothree.control.user.forum.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

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
    public CommandResult createForumGroup(UserVisitPK userVisitPK, CreateForumGroupForm form) {
        return new CreateForumGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumGroup(UserVisitPK userVisitPK, GetForumGroupForm form) {
        return new GetForumGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumGroups(UserVisitPK userVisitPK, GetForumGroupsForm form) {
        return new GetForumGroupsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumGroupChoices(UserVisitPK userVisitPK, GetForumGroupChoicesForm form) {
        return new GetForumGroupChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editForumGroup(UserVisitPK userVisitPK, EditForumGroupForm form) {
        return new EditForumGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteForumGroup(UserVisitPK userVisitPK, DeleteForumGroupForm form) {
        return new DeleteForumGroupCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Group Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumGroupDescription(UserVisitPK userVisitPK, CreateForumGroupDescriptionForm form) {
        return new CreateForumGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumGroupDescriptions(UserVisitPK userVisitPK, GetForumGroupDescriptionsForm form) {
        return new GetForumGroupDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editForumGroupDescription(UserVisitPK userVisitPK, EditForumGroupDescriptionForm form) {
        return new EditForumGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteForumGroupDescription(UserVisitPK userVisitPK, DeleteForumGroupDescriptionForm form) {
        return new DeleteForumGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forums
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForum(UserVisitPK userVisitPK, CreateForumForm form) {
        return new CreateForumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForum(UserVisitPK userVisitPK, GetForumForm form) {
        return new GetForumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForums(UserVisitPK userVisitPK, GetForumsForm form) {
        return new GetForumsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumChoices(UserVisitPK userVisitPK, GetForumChoicesForm form) {
        return new GetForumChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editForum(UserVisitPK userVisitPK, EditForumForm form) {
        return new EditForumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteForum(UserVisitPK userVisitPK, DeleteForumForm form) {
        return new DeleteForumCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumDescription(UserVisitPK userVisitPK, CreateForumDescriptionForm form) {
        return new CreateForumDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumDescriptions(UserVisitPK userVisitPK, GetForumDescriptionsForm form) {
        return new GetForumDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editForumDescription(UserVisitPK userVisitPK, EditForumDescriptionForm form) {
        return new EditForumDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteForumDescription(UserVisitPK userVisitPK, DeleteForumDescriptionForm form) {
        return new DeleteForumDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Forum Group Forums
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumGroupForum(UserVisitPK userVisitPK, CreateForumGroupForumForm form) {
        return new CreateForumGroupForumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumGroupForums(UserVisitPK userVisitPK, GetForumGroupForumsForm form) {
        return new GetForumGroupForumsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultForumGroupForum(UserVisitPK userVisitPK, SetDefaultForumGroupForumForm form) {
        return new SetDefaultForumGroupForumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editForumGroupForum(UserVisitPK userVisitPK, EditForumGroupForumForm form) {
        return new EditForumGroupForumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteForumGroupForum(UserVisitPK userVisitPK, DeleteForumGroupForumForm form) {
        return new DeleteForumGroupForumCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumRoleType(UserVisitPK userVisitPK, CreateForumRoleTypeForm form) {
        return new CreateForumRoleTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumRoleTypeChoices(UserVisitPK userVisitPK, GetForumRoleTypeChoicesForm form) {
        return new GetForumRoleTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumRoleTypeDescription(UserVisitPK userVisitPK, CreateForumRoleTypeDescriptionForm form) {
        return new CreateForumRoleTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumType(UserVisitPK userVisitPK, CreateForumTypeForm form) {
        return new CreateForumTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumTypeChoices(UserVisitPK userVisitPK, GetForumTypeChoicesForm form) {
        return new GetForumTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumTypeDescription(UserVisitPK userVisitPK, CreateForumTypeDescriptionForm form) {
        return new CreateForumTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Mime Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMimeType(UserVisitPK userVisitPK, CreateForumMimeTypeForm form) {
        return new CreateForumMimeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumMimeTypes(UserVisitPK userVisitPK, GetForumMimeTypesForm form) {
        return new GetForumMimeTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultForumMimeType(UserVisitPK userVisitPK, SetDefaultForumMimeTypeForm form) {
        return new SetDefaultForumMimeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editForumMimeType(UserVisitPK userVisitPK, EditForumMimeTypeForm form) {
        return new EditForumMimeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteForumMimeType(UserVisitPK userVisitPK, DeleteForumMimeTypeForm form) {
        return new DeleteForumMimeTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Party Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumPartyRole(UserVisitPK userVisitPK, CreateForumPartyRoleForm form) {
        return new CreateForumPartyRoleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumPartyRoles(UserVisitPK userVisitPK, GetForumPartyRolesForm form) {
        return new GetForumPartyRolesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteForumPartyRole(UserVisitPK userVisitPK, DeleteForumPartyRoleForm form) {
        return new DeleteForumPartyRoleCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Party Type Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumPartyTypeRole(UserVisitPK userVisitPK, CreateForumPartyTypeRoleForm form) {
        return new CreateForumPartyTypeRoleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumPartyTypeRoles(UserVisitPK userVisitPK, GetForumPartyTypeRolesForm form) {
        return new GetForumPartyTypeRolesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteForumPartyTypeRole(UserVisitPK userVisitPK, DeleteForumPartyTypeRoleForm form) {
        return new DeleteForumPartyTypeRoleCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Message Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumTypeMessageType(UserVisitPK userVisitPK, CreateForumTypeMessageTypeForm form) {
        return new CreateForumTypeMessageTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Forum Forum Threads
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumForumThread(UserVisitPK userVisitPK, CreateForumForumThreadForm form) {
        return new CreateForumForumThreadCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumForumThreads(UserVisitPK userVisitPK, GetForumForumThreadsForm form) {
        return new GetForumForumThreadsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultForumForumThread(UserVisitPK userVisitPK, SetDefaultForumForumThreadForm form) {
        return new SetDefaultForumForumThreadCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editForumForumThread(UserVisitPK userVisitPK, EditForumForumThreadForm form) {
        return new EditForumForumThreadCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteForumForumThread(UserVisitPK userVisitPK, DeleteForumForumThreadForm form) {
        return new DeleteForumForumThreadCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Threads
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getForumThread(UserVisitPK userVisitPK, GetForumThreadForm form) {
        return new GetForumThreadCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumThreads(UserVisitPK userVisitPK, GetForumThreadsForm form) {
        return new GetForumThreadsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteForumThread(UserVisitPK userVisitPK, DeleteForumThreadForm form) {
        return new DeleteForumThreadCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Messages
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getForumMessage(UserVisitPK userVisitPK, GetForumMessageForm form) {
        return new GetForumMessageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumMessages(UserVisitPK userVisitPK, GetForumMessagesForm form) {
        return new GetForumMessagesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteForumMessage(UserVisitPK userVisitPK, DeleteForumMessageForm form) {
        return new DeleteForumMessageCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Attachments
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createForumMessageAttachment(UserVisitPK userVisitPK, CreateForumMessageAttachmentForm form) {
        return new CreateForumMessageAttachmentCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getForumMessageAttachment(UserVisitPK userVisitPK, GetForumMessageAttachmentForm form) {
        return new GetForumMessageAttachmentCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getForumMessageAttachments(UserVisitPK userVisitPK, GetForumMessageAttachmentsForm form) {
        return new GetForumMessageAttachmentsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editForumMessageAttachment(UserVisitPK userVisitPK, EditForumMessageAttachmentForm form) {
        return new EditForumMessageAttachmentCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteForumMessageAttachment(UserVisitPK userVisitPK, DeleteForumMessageAttachmentForm form) {
        return new DeleteForumMessageAttachmentCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Attachment Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createForumMessageAttachmentDescription(UserVisitPK userVisitPK, CreateForumMessageAttachmentDescriptionForm form) {
        return new CreateForumMessageAttachmentDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getForumMessageAttachmentDescription(UserVisitPK userVisitPK, GetForumMessageAttachmentDescriptionForm form) {
        return new GetForumMessageAttachmentDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getForumMessageAttachmentDescriptions(UserVisitPK userVisitPK, GetForumMessageAttachmentDescriptionsForm form) {
        return new GetForumMessageAttachmentDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editForumMessageAttachmentDescription(UserVisitPK userVisitPK, EditForumMessageAttachmentDescriptionForm form) {
        return new EditForumMessageAttachmentDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteForumMessageAttachmentDescription(UserVisitPK userVisitPK, DeleteForumMessageAttachmentDescriptionForm form) {
        return new DeleteForumMessageAttachmentDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Part Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessagePartType(UserVisitPK userVisitPK, CreateForumMessagePartTypeForm form) {
        return new CreateForumMessagePartTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Part Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessagePartTypeDescription(UserVisitPK userVisitPK, CreateForumMessagePartTypeDescriptionForm form) {
        return new CreateForumMessagePartTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessageType(UserVisitPK userVisitPK, CreateForumMessageTypeForm form) {
        return new CreateForumMessageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getForumMessageTypeChoices(UserVisitPK userVisitPK, GetForumMessageTypeChoicesForm form) {
        return new GetForumMessageTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessageTypeDescription(UserVisitPK userVisitPK, CreateForumMessageTypeDescriptionForm form) {
        return new CreateForumMessageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Part Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessageTypePartType(UserVisitPK userVisitPK, CreateForumMessageTypePartTypeForm form) {
        return new CreateForumMessageTypePartTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Blog Entries
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createBlogEntry(UserVisitPK userVisitPK, CreateBlogEntryForm form) {
        return new CreateBlogEntryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editBlogEntry(UserVisitPK userVisitPK, EditBlogEntryForm form) {
        return new EditBlogEntryCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Blog Comments
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createBlogComment(UserVisitPK userVisitPK, CreateBlogCommentForm form) {
        return new CreateBlogCommentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editBlogComment(UserVisitPK userVisitPK, EditBlogCommentForm form) {
        return new EditBlogCommentCommand(userVisitPK, form).run();
    }
    
}
