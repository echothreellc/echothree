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

import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class MessageTransfer
        extends BaseTransfer {
    
    private MessageTypeTransfer messageType;
    private String messageName;
    private Boolean includeByDefault;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    private ListWrapper<MessageStringTransfer> messageStrings;
    private ListWrapper<MessageBlobTransfer> messageBlobs;
    private ListWrapper<MessageClobTransfer> messageClobs;
    
    /** Creates a new instance of MessageTransfer */
    public MessageTransfer(MessageTypeTransfer messageType, String messageName, Boolean includeByDefault, Boolean isDefault,
            Integer sortOrder, String description) {
        this.messageType = messageType;
        this.messageName = messageName;
        this.includeByDefault = includeByDefault;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public MessageTypeTransfer getMessageType() {
        return messageType;
    }
    
    public void setMessageType(MessageTypeTransfer messageType) {
        this.messageType = messageType;
    }
    
    public String getMessageName() {
        return messageName;
    }
    
    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }
    
    public Boolean getIncludeByDefault() {
        return includeByDefault;
    }
    
    public void setIncludeByDefault(Boolean includeByDefault) {
        this.includeByDefault = includeByDefault;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public ListWrapper<MessageStringTransfer> getMessageStrings() {
        return messageStrings;
    }
    
    public void setMessageStrings(ListWrapper<MessageStringTransfer> messageStrings) {
        this.messageStrings = messageStrings;
    }
    
    public ListWrapper<MessageBlobTransfer> getMessageBlobs() {
        return messageBlobs;
    }
    
    public void setMessageBlobs(ListWrapper<MessageBlobTransfer> messageBlobs) {
        this.messageBlobs = messageBlobs;
    }
    
    public ListWrapper<MessageClobTransfer> getMessageClobs() {
        return messageClobs;
    }
    
    public void setMessageClobs(ListWrapper<MessageClobTransfer> messageClobs) {
        this.messageClobs = messageClobs;
    }
    
}
