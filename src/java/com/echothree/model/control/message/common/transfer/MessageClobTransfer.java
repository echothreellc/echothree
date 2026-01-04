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

package com.echothree.model.control.message.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class MessageClobTransfer
        extends BaseTransfer {
    
    private MessageTransfer message;
    private LanguageTransfer language;
    private MimeTypeTransfer mimeType;
    private String clob;
    
    /** Creates a new instance of MessageClobTransfer */
    public MessageClobTransfer(MessageTransfer message, LanguageTransfer language, MimeTypeTransfer mimeType, String clob) {
        this.message = message;
        this.language = language;
        this.mimeType = mimeType;
        this.clob = clob;
    }
    
    public MessageTransfer getMessage() {
        return message;
    }
    
    public void setMessage(MessageTransfer message) {
        this.message = message;
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
    
    public String getClob() {
        return clob;
    }
    
    public void setClob(String clob) {
        this.clob = clob;
    }
    
}
