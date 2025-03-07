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

import com.echothree.model.control.message.common.transfer.MessageStringTransfer;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.message.server.entity.MessageString;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class MessageStringTransferCache
        extends BaseMessageTransferCache<MessageString, MessageStringTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of MessageStringTransferCache */
    public MessageStringTransferCache(UserVisit userVisit, MessageControl messageControl) {
        super(userVisit, messageControl);
        
        partyControl = Session.getModelController(PartyControl.class);
    }
    
    public MessageStringTransfer getMessageStringTransfer(MessageString messageString) {
        var messageStringTransfer = get(messageString);
        
        if(messageStringTransfer == null) {
            var message = messageControl.getMessageTransfer(userVisit, messageString.getMessage());
            var language = partyControl.getLanguageTransfer(userVisit, messageString.getLanguage());
            var string = messageString.getString();
            
            messageStringTransfer = new MessageStringTransfer(message, language, string);
            put(messageString, messageStringTransfer);
        }
        
        return messageStringTransfer;
    }
    
}
