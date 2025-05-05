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
        return new CreateForumGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumGroup(UserVisitPK userVisitPK, GetForumGroupForm form) {
        return new GetForumGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumGroups(UserVisitPK userVisitPK, GetForumGroupsForm form) {
        return new GetForumGroupsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumGroupChoices(UserVisitPK userVisitPK, GetForumGroupChoicesForm form) {
        return new GetForumGroupChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForumGroup(UserVisitPK userVisitPK, EditForumGroupForm form) {
        return new EditForumGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumGroup(UserVisitPK userVisitPK, DeleteForumGroupForm form) {
        return new DeleteForumGroupCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Group Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumGroupDescription(UserVisitPK userVisitPK, CreateForumGroupDescriptionForm form) {
        return new CreateForumGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumGroupDescriptions(UserVisitPK userVisitPK, GetForumGroupDescriptionsForm form) {
        return new GetForumGroupDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForumGroupDescription(UserVisitPK userVisitPK, EditForumGroupDescriptionForm form) {
        return new EditForumGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumGroupDescription(UserVisitPK userVisitPK, DeleteForumGroupDescriptionForm form) {
        return new DeleteForumGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forums
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForum(UserVisitPK userVisitPK, CreateForumForm form) {
        return new CreateForumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForum(UserVisitPK userVisitPK, GetForumForm form) {
        return new GetForumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForums(UserVisitPK userVisitPK, GetForumsForm form) {
        return new GetForumsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumChoices(UserVisitPK userVisitPK, GetForumChoicesForm form) {
        return new GetForumChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForum(UserVisitPK userVisitPK, EditForumForm form) {
        return new EditForumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForum(UserVisitPK userVisitPK, DeleteForumForm form) {
        return new DeleteForumCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumDescription(UserVisitPK userVisitPK, CreateForumDescriptionForm form) {
        return new CreateForumDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumDescriptions(UserVisitPK userVisitPK, GetForumDescriptionsForm form) {
        return new GetForumDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForumDescription(UserVisitPK userVisitPK, EditForumDescriptionForm form) {
        return new EditForumDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumDescription(UserVisitPK userVisitPK, DeleteForumDescriptionForm form) {
        return new DeleteForumDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Forum Group Forums
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumGroupForum(UserVisitPK userVisitPK, CreateForumGroupForumForm form) {
        return new CreateForumGroupForumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumGroupForums(UserVisitPK userVisitPK, GetForumGroupForumsForm form) {
        return new GetForumGroupForumsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultForumGroupForum(UserVisitPK userVisitPK, SetDefaultForumGroupForumForm form) {
        return new SetDefaultForumGroupForumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForumGroupForum(UserVisitPK userVisitPK, EditForumGroupForumForm form) {
        return new EditForumGroupForumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumGroupForum(UserVisitPK userVisitPK, DeleteForumGroupForumForm form) {
        return new DeleteForumGroupForumCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumRoleType(UserVisitPK userVisitPK, CreateForumRoleTypeForm form) {
        return new CreateForumRoleTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumRoleTypeChoices(UserVisitPK userVisitPK, GetForumRoleTypeChoicesForm form) {
        return new GetForumRoleTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumRoleTypeDescription(UserVisitPK userVisitPK, CreateForumRoleTypeDescriptionForm form) {
        return new CreateForumRoleTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumType(UserVisitPK userVisitPK, CreateForumTypeForm form) {
        return new CreateForumTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumTypeChoices(UserVisitPK userVisitPK, GetForumTypeChoicesForm form) {
        return new GetForumTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumTypeDescription(UserVisitPK userVisitPK, CreateForumTypeDescriptionForm form) {
        return new CreateForumTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Mime Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMimeType(UserVisitPK userVisitPK, CreateForumMimeTypeForm form) {
        return new CreateForumMimeTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumMimeTypes(UserVisitPK userVisitPK, GetForumMimeTypesForm form) {
        return new GetForumMimeTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultForumMimeType(UserVisitPK userVisitPK, SetDefaultForumMimeTypeForm form) {
        return new SetDefaultForumMimeTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForumMimeType(UserVisitPK userVisitPK, EditForumMimeTypeForm form) {
        return new EditForumMimeTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumMimeType(UserVisitPK userVisitPK, DeleteForumMimeTypeForm form) {
        return new DeleteForumMimeTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Party Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumPartyRole(UserVisitPK userVisitPK, CreateForumPartyRoleForm form) {
        return new CreateForumPartyRoleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumPartyRoles(UserVisitPK userVisitPK, GetForumPartyRolesForm form) {
        return new GetForumPartyRolesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumPartyRole(UserVisitPK userVisitPK, DeleteForumPartyRoleForm form) {
        return new DeleteForumPartyRoleCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Party Type Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumPartyTypeRole(UserVisitPK userVisitPK, CreateForumPartyTypeRoleForm form) {
        return new CreateForumPartyTypeRoleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumPartyTypeRoles(UserVisitPK userVisitPK, GetForumPartyTypeRolesForm form) {
        return new GetForumPartyTypeRolesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumPartyTypeRole(UserVisitPK userVisitPK, DeleteForumPartyTypeRoleForm form) {
        return new DeleteForumPartyTypeRoleCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Message Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumTypeMessageType(UserVisitPK userVisitPK, CreateForumTypeMessageTypeForm form) {
        return new CreateForumTypeMessageTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Forum Forum Threads
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumForumThread(UserVisitPK userVisitPK, CreateForumForumThreadForm form) {
        return new CreateForumForumThreadCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumForumThreads(UserVisitPK userVisitPK, GetForumForumThreadsForm form) {
        return new GetForumForumThreadsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultForumForumThread(UserVisitPK userVisitPK, SetDefaultForumForumThreadForm form) {
        return new SetDefaultForumForumThreadCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForumForumThread(UserVisitPK userVisitPK, EditForumForumThreadForm form) {
        return new EditForumForumThreadCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumForumThread(UserVisitPK userVisitPK, DeleteForumForumThreadForm form) {
        return new DeleteForumForumThreadCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Threads
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getForumThread(UserVisitPK userVisitPK, GetForumThreadForm form) {
        return new GetForumThreadCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumThreads(UserVisitPK userVisitPK, GetForumThreadsForm form) {
        return new GetForumThreadsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumThread(UserVisitPK userVisitPK, DeleteForumThreadForm form) {
        return new DeleteForumThreadCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Messages
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getForumMessage(UserVisitPK userVisitPK, GetForumMessageForm form) {
        return new GetForumMessageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumMessages(UserVisitPK userVisitPK, GetForumMessagesForm form) {
        return new GetForumMessagesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumMessage(UserVisitPK userVisitPK, DeleteForumMessageForm form) {
        return new DeleteForumMessageCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Attachments
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createForumMessageAttachment(UserVisitPK userVisitPK, CreateForumMessageAttachmentForm form) {
        return new CreateForumMessageAttachmentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getForumMessageAttachment(UserVisitPK userVisitPK, GetForumMessageAttachmentForm form) {
        return new GetForumMessageAttachmentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getForumMessageAttachments(UserVisitPK userVisitPK, GetForumMessageAttachmentsForm form) {
        return new GetForumMessageAttachmentsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editForumMessageAttachment(UserVisitPK userVisitPK, EditForumMessageAttachmentForm form) {
        return new EditForumMessageAttachmentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteForumMessageAttachment(UserVisitPK userVisitPK, DeleteForumMessageAttachmentForm form) {
        return new DeleteForumMessageAttachmentCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Attachment Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createForumMessageAttachmentDescription(UserVisitPK userVisitPK, CreateForumMessageAttachmentDescriptionForm form) {
        return new CreateForumMessageAttachmentDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getForumMessageAttachmentDescription(UserVisitPK userVisitPK, GetForumMessageAttachmentDescriptionForm form) {
        return new GetForumMessageAttachmentDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getForumMessageAttachmentDescriptions(UserVisitPK userVisitPK, GetForumMessageAttachmentDescriptionsForm form) {
        return new GetForumMessageAttachmentDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editForumMessageAttachmentDescription(UserVisitPK userVisitPK, EditForumMessageAttachmentDescriptionForm form) {
        return new EditForumMessageAttachmentDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteForumMessageAttachmentDescription(UserVisitPK userVisitPK, DeleteForumMessageAttachmentDescriptionForm form) {
        return new DeleteForumMessageAttachmentDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Part Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessagePartType(UserVisitPK userVisitPK, CreateForumMessagePartTypeForm form) {
        return new CreateForumMessagePartTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Part Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessagePartTypeDescription(UserVisitPK userVisitPK, CreateForumMessagePartTypeDescriptionForm form) {
        return new CreateForumMessagePartTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessageType(UserVisitPK userVisitPK, CreateForumMessageTypeForm form) {
        return new CreateForumMessageTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumMessageTypeChoices(UserVisitPK userVisitPK, GetForumMessageTypeChoicesForm form) {
        return new GetForumMessageTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessageTypeDescription(UserVisitPK userVisitPK, CreateForumMessageTypeDescriptionForm form) {
        return new CreateForumMessageTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Part Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessageTypePartType(UserVisitPK userVisitPK, CreateForumMessageTypePartTypeForm form) {
        return new CreateForumMessageTypePartTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Blog Entries
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createBlogEntry(UserVisitPK userVisitPK, CreateBlogEntryForm form) {
        return new CreateBlogEntryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editBlogEntry(UserVisitPK userVisitPK, EditBlogEntryForm form) {
        return new EditBlogEntryCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Blog Comments
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createBlogComment(UserVisitPK userVisitPK, CreateBlogCommentForm form) {
        return new CreateBlogCommentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editBlogComment(UserVisitPK userVisitPK, EditBlogCommentForm form) {
        return new EditBlogCommentCommand().run(userVisitPK, form);
    }
    
}
