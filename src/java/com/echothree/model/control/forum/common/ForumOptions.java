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

package com.echothree.model.control.forum.common;

public interface ForumOptions {
    
    String ForumGroupIncludeForums = "ForumGroupIncludeForums";
    String ForumGroupIncludeEntityAttributeGroups = "ForumGroupIncludeEntityAttributeGroups";
    String ForumGroupIncludeTagScopes = "ForumGroupIncludeTagScopes";
    
    String ForumIncludeUuid = "ForumIncludeUuid";
    String ForumIncludeForumGroups = "ForumIncludeForumGroups";
    String ForumIncludeForumThreads = "ForumIncludeForumThreads";
    String ForumIncludeFutureForumThreads = "ForumIncludeFutureForumThreads";
    String ForumIncludeEntityAttributeGroups = "ForumIncludeEntityAttributeGroups";
    String ForumIncludeTagScopes = "ForumIncludeTagScopes";
    
    String ForumThreadIncludeUuid = "ForumThreadIncludeUuid";
    String ForumThreadIncludeForumMessages = "ForumThreadIncludeForumMessages";
    String ForumThreadIncludeForumForumThreads = "ForumThreadIncludeForumForumThreads";
    String ForumThreadIncludeEntityAttributeGroups = "ForumThreadIncludeEntityAttributeGroups";
    String ForumThreadIncludeTagScopes = "ForumThreadIncludeTagScopes";
    
    String ForumMessageIncludeUuid = "ForumMessageIncludeUuid";
    String ForumMessageIncludeForumMessageRoles = "ForumMessageIncludeForumMessageRoles";
    String ForumMessageIncludeForumMessageParts = "ForumMessageIncludeForumMessageParts";
    String ForumMessageIncludeForumMessageAttachments = "ForumMessageIncludeForumMessageAttachments";
    String ForumMessageIncludeEntityAttributeGroups = "ForumMessageIncludeEntityAttributeGroups";
    String ForumMessageIncludeTagScopes = "ForumMessageIncludeTagScopes";

    String ForumMessageAttachmentIncludeBlob = "ForumMessageAttachmentIncludeBlob";
    String ForumMessageAttachmentIncludeClob = "ForumMessageAttachmentIncludeClob";
    String ForumMessageAttachmentIncludeETag = "ForumMessageAttachmentIncludeETag";
    
    String ForumMessagePartIncludeBlob = "ForumMessagePartIncludeBlob";
    String ForumMessagePartIncludeClob = "ForumMessagePartIncludeClob";
    String ForumMessagePartIncludeString = "ForumMessagePartIncludeString";
    
}
