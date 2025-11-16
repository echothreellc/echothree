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
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MessageTransferCaches
        extends BaseTransferCaches {
    
    protected MessageTypeTransferCache messageTypeTransferCache;
    protected MessageTypeDescriptionTransferCache messageTypeDescriptionTransferCache;
    protected MessageTransferCache messageTransferCache;
    protected MessageStringTransferCache messageStringTransferCache;
    protected MessageBlobTransferCache messageBlobTransferCache;
    protected MessageClobTransferCache messageClobTransferCache;
    protected MessageDescriptionTransferCache messageDescriptionTransferCache;
    protected EntityMessageTransferCache entityMessageTransferCache;
    
    /** Creates a new instance of MessageTransferCaches */
    protected MessageTransferCaches() {
        super();
    }
    
    public MessageTypeTransferCache getMessageTypeTransferCache() {
        if(messageTypeTransferCache == null)
            messageTypeTransferCache = CDI.current().select(MessageTypeTransferCache.class).get();
        
        return messageTypeTransferCache;
    }
    
    public MessageTypeDescriptionTransferCache getMessageTypeDescriptionTransferCache() {
        if(messageTypeDescriptionTransferCache == null)
            messageTypeDescriptionTransferCache = CDI.current().select(MessageTypeDescriptionTransferCache.class).get();
        
        return messageTypeDescriptionTransferCache;
    }
    
    public MessageTransferCache getMessageTransferCache() {
        if(messageTransferCache == null)
            messageTransferCache = CDI.current().select(MessageTransferCache.class).get();
        
        return messageTransferCache;
    }
    
    public MessageStringTransferCache getMessageStringTransferCache() {
        if(messageStringTransferCache == null)
            messageStringTransferCache = CDI.current().select(MessageStringTransferCache.class).get();
        
        return messageStringTransferCache;
    }
    
    public MessageBlobTransferCache getMessageBlobTransferCache() {
        if(messageBlobTransferCache == null)
            messageBlobTransferCache = CDI.current().select(MessageBlobTransferCache.class).get();
        
        return messageBlobTransferCache;
    }
    
    public MessageClobTransferCache getMessageClobTransferCache() {
        if(messageClobTransferCache == null)
            messageClobTransferCache = CDI.current().select(MessageClobTransferCache.class).get();
        
        return messageClobTransferCache;
    }
    
    public MessageDescriptionTransferCache getMessageDescriptionTransferCache() {
        if(messageDescriptionTransferCache == null)
            messageDescriptionTransferCache = CDI.current().select(MessageDescriptionTransferCache.class).get();
        
        return messageDescriptionTransferCache;
    }
    
    public EntityMessageTransferCache getEntityMessageTransferCache() {
        if(entityMessageTransferCache == null)
            entityMessageTransferCache = CDI.current().select(EntityMessageTransferCache.class).get();
        
        return entityMessageTransferCache;
    }
    
}
