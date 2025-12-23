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

package com.echothree.model.control.forum.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.transfer.BaseTransfer;

public class ForumMessagePartTransfer
        extends BaseTransfer {
    
    private ForumMessageTransfer forumMessage;
    private ForumMessagePartTypeTransfer forumMessagePartType;
    private LanguageTransfer language;
    private MimeTypeTransfer mimeType;
    private ByteArray blob;
    private String clob;
    private String string;
    
    /** Creates a new instance of ForumMessagePartTransfer */
    public ForumMessagePartTransfer(ForumMessageTransfer forumMessage, ForumMessagePartTypeTransfer forumMessagePartType,
            LanguageTransfer language, MimeTypeTransfer mimeType, ByteArray blob, String clob, String string) {
        this.forumMessage = forumMessage;
        this.forumMessagePartType = forumMessagePartType;
        this.language = language;
        this.mimeType = mimeType;
        this.blob = blob;
        this.clob = clob;
        this.string = string;
    }
    
    public ForumMessageTransfer getForumMessage() {
        return forumMessage;
    }
    
    public void setForumMessage(ForumMessageTransfer forumMessage) {
        this.forumMessage = forumMessage;
    }
    
    public ForumMessagePartTypeTransfer getForumMessagePartType() {
        return forumMessagePartType;
    }
    
    public void setForumMessagePartType(ForumMessagePartTypeTransfer forumMessagePartType) {
        this.forumMessagePartType = forumMessagePartType;
    }
    
    public LanguageTransfer getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }
    
    public MimeTypeTransfer getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(MimeTypeTransfer mimeType) {
        this.mimeType = mimeType;
    }
    
    public ByteArray getBlob() {
        return blob;
    }
    
    public void setBlob(ByteArray blob) {
        this.blob = blob;
    }
    
    public String getClob() {
        return clob;
    }
    
    public void setClob(String clob) {
        this.clob = clob;
    }
    
    public String getString() {
        return string;
    }
    
    public void setString(String string) {
        this.string = string;
    }
    
}
