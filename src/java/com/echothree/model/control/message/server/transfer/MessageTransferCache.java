// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.message.common.MessageOptions;
import com.echothree.model.control.message.common.transfer.MessageTransfer;
import com.echothree.model.control.message.common.transfer.MessageTypeTransfer;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.message.server.entity.Message;
import com.echothree.model.data.message.server.entity.MessageDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.Set;

public class MessageTransferCache
        extends BaseMessageTransferCache<Message, MessageTransfer> {
    
    boolean includeString;
    boolean includeBlob;
    boolean includeClob;
    
    /** Creates a new instance of MessageTransferCache */
    public MessageTransferCache(UserVisit userVisit, MessageControl messageControl) {
        super(userVisit, messageControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeString = options.contains(MessageOptions.MessageIncludeString);
            includeBlob = options.contains(MessageOptions.MessageIncludeBlob);
            includeClob = options.contains(MessageOptions.MessageIncludeClob);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public MessageTransfer getMessageTransfer(Message message) {
        MessageTransfer messageTransfer = get(message);
        
        if(messageTransfer == null) {
            MessageDetail messageDetail = message.getLastDetail();
            MessageTypeTransfer messageTypeTransfer = messageControl.getMessageTypeTransfer(userVisit, messageDetail.getMessageType());
            String messageName = messageDetail.getMessageName();
            Boolean includeByDefault = messageDetail.getIncludeByDefault();
            Boolean isDefault = messageDetail.getIsDefault();
            Integer sortOrder = messageDetail.getSortOrder();
            String description = messageControl.getBestMessageDescription(message, getLanguage());
            
            messageTransfer = new MessageTransfer(messageTypeTransfer, messageName, includeByDefault, isDefault, sortOrder, description);
            put(message, messageTransfer);
            
            if(includeString) {
                messageTransfer.setMessageStrings(new ListWrapper<>(messageControl.getMessageStringTransfersByMessage(userVisit, message)));
            }
            
            if(includeBlob) {
                messageTransfer.setMessageBlobs(new ListWrapper<>(messageControl.getMessageBlobTransfersByMessage(userVisit, message)));
            }
            
            if(includeClob) {
                messageTransfer.setMessageClobs(new ListWrapper<>(messageControl.getMessageClobTransfersByMessage(userVisit, message)));
            }
            
        }
        
        return messageTransfer;
    }
    
}
