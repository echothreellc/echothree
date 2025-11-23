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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MessageStringTransferCache
        extends BaseMessageTransferCache<MessageString, MessageStringTransfer> {

    MessageControl messageControl = Session.getModelController(MessageControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of MessageStringTransferCache */
    protected MessageStringTransferCache() {
        super();
    }
    
    public MessageStringTransfer getMessageStringTransfer(UserVisit userVisit, MessageString messageString) {
        var messageStringTransfer = get(messageString);
        
        if(messageStringTransfer == null) {
            var message = messageControl.getMessageTransfer(userVisit, messageString.getMessage());
            var language = partyControl.getLanguageTransfer(userVisit, messageString.getLanguage());
            var string = messageString.getString();
            
            messageStringTransfer = new MessageStringTransfer(message, language, string);
            put(userVisit, messageString, messageStringTransfer);
        }
        
        return messageStringTransfer;
    }
    
}
