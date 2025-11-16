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

package com.echothree.model.control.message.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class MessageTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    MessageTypeTransferCache messageTypeTransferCache;
    
    @Inject
    MessageTypeDescriptionTransferCache messageTypeDescriptionTransferCache;
    
    @Inject
    MessageTransferCache messageTransferCache;
    
    @Inject
    MessageStringTransferCache messageStringTransferCache;
    
    @Inject
    MessageBlobTransferCache messageBlobTransferCache;
    
    @Inject
    MessageClobTransferCache messageClobTransferCache;
    
    @Inject
    MessageDescriptionTransferCache messageDescriptionTransferCache;
    
    @Inject
    EntityMessageTransferCache entityMessageTransferCache;

    /** Creates a new instance of MessageTransferCaches */
    protected MessageTransferCaches() {
        super();
    }
    
    public MessageTypeTransferCache getMessageTypeTransferCache() {
        return messageTypeTransferCache;
    }
    
    public MessageTypeDescriptionTransferCache getMessageTypeDescriptionTransferCache() {
        return messageTypeDescriptionTransferCache;
    }
    
    public MessageTransferCache getMessageTransferCache() {
        return messageTransferCache;
    }
    
    public MessageStringTransferCache getMessageStringTransferCache() {
        return messageStringTransferCache;
    }
    
    public MessageBlobTransferCache getMessageBlobTransferCache() {
        return messageBlobTransferCache;
    }
    
    public MessageClobTransferCache getMessageClobTransferCache() {
        return messageClobTransferCache;
    }
    
    public MessageDescriptionTransferCache getMessageDescriptionTransferCache() {
        return messageDescriptionTransferCache;
    }
    
    public EntityMessageTransferCache getEntityMessageTransferCache() {
        return entityMessageTransferCache;
    }
    
}
