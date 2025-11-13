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

import com.echothree.model.control.message.common.transfer.MessageDescriptionTransfer;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.message.server.entity.MessageDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class MessageDescriptionTransferCache
        extends BaseMessageDescriptionTransferCache<MessageDescription, MessageDescriptionTransfer> {
    
    /** Creates a new instance of MessageDescriptionTransferCache */
    public MessageDescriptionTransferCache(MessageControl messageControl) {
        super(messageControl);
    }
    
    public MessageDescriptionTransfer getMessageDescriptionTransfer(UserVisit userVisit, MessageDescription messageDescription) {
        var messageDescriptionTransfer = get(messageDescription);
        
        if(messageDescriptionTransfer == null) {
            var messageTransferCache = messageControl.getMessageTransferCaches().getMessageTransferCache();
            var messageTransfer = messageTransferCache.getMessageTransfer(messageDescription.getMessage());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, messageDescription.getLanguage());
            
            messageDescriptionTransfer = new MessageDescriptionTransfer(languageTransfer, messageTransfer, messageDescription.getDescription());
            put(userVisit, messageDescription, messageDescriptionTransfer);
        }
        
        return messageDescriptionTransfer;
    }
    
}
