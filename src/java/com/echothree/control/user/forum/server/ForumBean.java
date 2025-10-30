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
    public CommandResult createForumGroup(UserVisitPK userVisitPK, CreateForumGroupForm form) {
        return CDI.current().select(CreateForumGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumGroup(UserVisitPK userVisitPK, GetForumGroupForm form) {
        return CDI.current().select(GetForumGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumGroups(UserVisitPK userVisitPK, GetForumGroupsForm form) {
        return CDI.current().select(GetForumGroupsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumGroupChoices(UserVisitPK userVisitPK, GetForumGroupChoicesForm form) {
        return CDI.current().select(GetForumGroupChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForumGroup(UserVisitPK userVisitPK, EditForumGroupForm form) {
        return CDI.current().select(EditForumGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumGroup(UserVisitPK userVisitPK, DeleteForumGroupForm form) {
        return CDI.current().select(DeleteForumGroupCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Group Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumGroupDescription(UserVisitPK userVisitPK, CreateForumGroupDescriptionForm form) {
        return CDI.current().select(CreateForumGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumGroupDescriptions(UserVisitPK userVisitPK, GetForumGroupDescriptionsForm form) {
        return CDI.current().select(GetForumGroupDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForumGroupDescription(UserVisitPK userVisitPK, EditForumGroupDescriptionForm form) {
        return CDI.current().select(EditForumGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumGroupDescription(UserVisitPK userVisitPK, DeleteForumGroupDescriptionForm form) {
        return CDI.current().select(DeleteForumGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forums
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForum(UserVisitPK userVisitPK, CreateForumForm form) {
        return CDI.current().select(CreateForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForum(UserVisitPK userVisitPK, GetForumForm form) {
        return CDI.current().select(GetForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForums(UserVisitPK userVisitPK, GetForumsForm form) {
        return CDI.current().select(GetForumsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumChoices(UserVisitPK userVisitPK, GetForumChoicesForm form) {
        return CDI.current().select(GetForumChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForum(UserVisitPK userVisitPK, EditForumForm form) {
        return CDI.current().select(EditForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForum(UserVisitPK userVisitPK, DeleteForumForm form) {
        return CDI.current().select(DeleteForumCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumDescription(UserVisitPK userVisitPK, CreateForumDescriptionForm form) {
        return CDI.current().select(CreateForumDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumDescriptions(UserVisitPK userVisitPK, GetForumDescriptionsForm form) {
        return CDI.current().select(GetForumDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForumDescription(UserVisitPK userVisitPK, EditForumDescriptionForm form) {
        return CDI.current().select(EditForumDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumDescription(UserVisitPK userVisitPK, DeleteForumDescriptionForm form) {
        return CDI.current().select(DeleteForumDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Forum Group Forums
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumGroupForum(UserVisitPK userVisitPK, CreateForumGroupForumForm form) {
        return CDI.current().select(CreateForumGroupForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumGroupForums(UserVisitPK userVisitPK, GetForumGroupForumsForm form) {
        return CDI.current().select(GetForumGroupForumsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultForumGroupForum(UserVisitPK userVisitPK, SetDefaultForumGroupForumForm form) {
        return CDI.current().select(SetDefaultForumGroupForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForumGroupForum(UserVisitPK userVisitPK, EditForumGroupForumForm form) {
        return CDI.current().select(EditForumGroupForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumGroupForum(UserVisitPK userVisitPK, DeleteForumGroupForumForm form) {
        return CDI.current().select(DeleteForumGroupForumCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumRoleType(UserVisitPK userVisitPK, CreateForumRoleTypeForm form) {
        return CDI.current().select(CreateForumRoleTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumRoleTypeChoices(UserVisitPK userVisitPK, GetForumRoleTypeChoicesForm form) {
        return CDI.current().select(GetForumRoleTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumRoleTypeDescription(UserVisitPK userVisitPK, CreateForumRoleTypeDescriptionForm form) {
        return CDI.current().select(CreateForumRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumType(UserVisitPK userVisitPK, CreateForumTypeForm form) {
        return CDI.current().select(CreateForumTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumTypeChoices(UserVisitPK userVisitPK, GetForumTypeChoicesForm form) {
        return CDI.current().select(GetForumTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumTypeDescription(UserVisitPK userVisitPK, CreateForumTypeDescriptionForm form) {
        return CDI.current().select(CreateForumTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Mime Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMimeType(UserVisitPK userVisitPK, CreateForumMimeTypeForm form) {
        return CDI.current().select(CreateForumMimeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumMimeTypes(UserVisitPK userVisitPK, GetForumMimeTypesForm form) {
        return CDI.current().select(GetForumMimeTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultForumMimeType(UserVisitPK userVisitPK, SetDefaultForumMimeTypeForm form) {
        return CDI.current().select(SetDefaultForumMimeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForumMimeType(UserVisitPK userVisitPK, EditForumMimeTypeForm form) {
        return CDI.current().select(EditForumMimeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumMimeType(UserVisitPK userVisitPK, DeleteForumMimeTypeForm form) {
        return CDI.current().select(DeleteForumMimeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Party Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumPartyRole(UserVisitPK userVisitPK, CreateForumPartyRoleForm form) {
        return CDI.current().select(CreateForumPartyRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumPartyRoles(UserVisitPK userVisitPK, GetForumPartyRolesForm form) {
        return CDI.current().select(GetForumPartyRolesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumPartyRole(UserVisitPK userVisitPK, DeleteForumPartyRoleForm form) {
        return CDI.current().select(DeleteForumPartyRoleCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Party Type Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumPartyTypeRole(UserVisitPK userVisitPK, CreateForumPartyTypeRoleForm form) {
        return CDI.current().select(CreateForumPartyTypeRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumPartyTypeRoles(UserVisitPK userVisitPK, GetForumPartyTypeRolesForm form) {
        return CDI.current().select(GetForumPartyTypeRolesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumPartyTypeRole(UserVisitPK userVisitPK, DeleteForumPartyTypeRoleForm form) {
        return CDI.current().select(DeleteForumPartyTypeRoleCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Message Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumTypeMessageType(UserVisitPK userVisitPK, CreateForumTypeMessageTypeForm form) {
        return CDI.current().select(CreateForumTypeMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Forum Forum Threads
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumForumThread(UserVisitPK userVisitPK, CreateForumForumThreadForm form) {
        return CDI.current().select(CreateForumForumThreadCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumForumThreads(UserVisitPK userVisitPK, GetForumForumThreadsForm form) {
        return CDI.current().select(GetForumForumThreadsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultForumForumThread(UserVisitPK userVisitPK, SetDefaultForumForumThreadForm form) {
        return CDI.current().select(SetDefaultForumForumThreadCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editForumForumThread(UserVisitPK userVisitPK, EditForumForumThreadForm form) {
        return CDI.current().select(EditForumForumThreadCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumForumThread(UserVisitPK userVisitPK, DeleteForumForumThreadForm form) {
        return CDI.current().select(DeleteForumForumThreadCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Threads
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getForumThread(UserVisitPK userVisitPK, GetForumThreadForm form) {
        return CDI.current().select(GetForumThreadCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumThreads(UserVisitPK userVisitPK, GetForumThreadsForm form) {
        return CDI.current().select(GetForumThreadsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumThread(UserVisitPK userVisitPK, DeleteForumThreadForm form) {
        return CDI.current().select(DeleteForumThreadCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Messages
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getForumMessage(UserVisitPK userVisitPK, GetForumMessageForm form) {
        return CDI.current().select(GetForumMessageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumMessages(UserVisitPK userVisitPK, GetForumMessagesForm form) {
        return CDI.current().select(GetForumMessagesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteForumMessage(UserVisitPK userVisitPK, DeleteForumMessageForm form) {
        return CDI.current().select(DeleteForumMessageCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Attachments
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createForumMessageAttachment(UserVisitPK userVisitPK, CreateForumMessageAttachmentForm form) {
        return CDI.current().select(CreateForumMessageAttachmentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getForumMessageAttachment(UserVisitPK userVisitPK, GetForumMessageAttachmentForm form) {
        return CDI.current().select(GetForumMessageAttachmentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getForumMessageAttachments(UserVisitPK userVisitPK, GetForumMessageAttachmentsForm form) {
        return CDI.current().select(GetForumMessageAttachmentsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editForumMessageAttachment(UserVisitPK userVisitPK, EditForumMessageAttachmentForm form) {
        return CDI.current().select(EditForumMessageAttachmentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteForumMessageAttachment(UserVisitPK userVisitPK, DeleteForumMessageAttachmentForm form) {
        return CDI.current().select(DeleteForumMessageAttachmentCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Attachment Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createForumMessageAttachmentDescription(UserVisitPK userVisitPK, CreateForumMessageAttachmentDescriptionForm form) {
        return CDI.current().select(CreateForumMessageAttachmentDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getForumMessageAttachmentDescription(UserVisitPK userVisitPK, GetForumMessageAttachmentDescriptionForm form) {
        return CDI.current().select(GetForumMessageAttachmentDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getForumMessageAttachmentDescriptions(UserVisitPK userVisitPK, GetForumMessageAttachmentDescriptionsForm form) {
        return CDI.current().select(GetForumMessageAttachmentDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editForumMessageAttachmentDescription(UserVisitPK userVisitPK, EditForumMessageAttachmentDescriptionForm form) {
        return CDI.current().select(EditForumMessageAttachmentDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteForumMessageAttachmentDescription(UserVisitPK userVisitPK, DeleteForumMessageAttachmentDescriptionForm form) {
        return CDI.current().select(DeleteForumMessageAttachmentDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Part Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessagePartType(UserVisitPK userVisitPK, CreateForumMessagePartTypeForm form) {
        return CDI.current().select(CreateForumMessagePartTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Part Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessagePartTypeDescription(UserVisitPK userVisitPK, CreateForumMessagePartTypeDescriptionForm form) {
        return CDI.current().select(CreateForumMessagePartTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessageType(UserVisitPK userVisitPK, CreateForumMessageTypeForm form) {
        return CDI.current().select(CreateForumMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getForumMessageTypeChoices(UserVisitPK userVisitPK, GetForumMessageTypeChoicesForm form) {
        return CDI.current().select(GetForumMessageTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessageTypeDescription(UserVisitPK userVisitPK, CreateForumMessageTypeDescriptionForm form) {
        return CDI.current().select(CreateForumMessageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Part Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createForumMessageTypePartType(UserVisitPK userVisitPK, CreateForumMessageTypePartTypeForm form) {
        return CDI.current().select(CreateForumMessageTypePartTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Blog Entries
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createBlogEntry(UserVisitPK userVisitPK, CreateBlogEntryForm form) {
        return CDI.current().select(CreateBlogEntryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editBlogEntry(UserVisitPK userVisitPK, EditBlogEntryForm form) {
        return CDI.current().select(EditBlogEntryCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Blog Comments
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createBlogComment(UserVisitPK userVisitPK, CreateBlogCommentForm form) {
        return CDI.current().select(CreateBlogCommentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editBlogComment(UserVisitPK userVisitPK, EditBlogCommentForm form) {
        return CDI.current().select(EditBlogCommentCommand.class).get().run(userVisitPK, form);
    }
    
}
