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

import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.message.common.transfer.MessageTypeTransfer;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class MessageTypeTransferCache
        extends BaseMessageTransferCache<MessageType, MessageTypeTransfer> {
    
    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);
    MessageControl messageControl = Session.getModelController(MessageControl.class);
    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);

    /** Creates a new instance of MessageTypeTransferCache */
    public MessageTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public MessageTypeTransfer getMessageTypeTransfer(UserVisit userVisit, MessageType messageType) {
        var messageTypeTransfer = get(messageType);
        
        if(messageTypeTransfer == null) {
            var messageTypeDetail = messageType.getLastDetail();
            var entityTypeTransfer = entityTypeControl.getEntityTypeTransfer(userVisit, messageTypeDetail.getEntityType());
            var messageTypeName = messageTypeDetail.getMessageTypeName();
            var mimeTypeUsageType = messageTypeDetail.getMimeTypeUsageType();
            var mimeTypeUsageTypeTransfer = mimeTypeUsageType == null? null: mimeTypeControl.getMimeTypeUsageTypeTransfer(userVisit, mimeTypeUsageType);
            var sortOrder = messageTypeDetail.getSortOrder();
            var description = messageControl.getBestMessageTypeDescription(messageType, getLanguage(userVisit));
            
            messageTypeTransfer = new MessageTypeTransfer(entityTypeTransfer, messageTypeName, mimeTypeUsageTypeTransfer, sortOrder, description);
            put(userVisit, messageType, messageTypeTransfer);
        }
        
        return messageTypeTransfer;
    }
    
}
