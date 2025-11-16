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

import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.message.common.transfer.MessageClobTransfer;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.message.server.entity.MessageClob;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MessageClobTransferCache
        extends BaseMessageTransferCache<MessageClob, MessageClobTransfer> {

    MessageControl messageControl = Session.getModelController(MessageControl.class);
    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of MessageClobTransferCache */
    public MessageClobTransferCache() {
        super();
    }
    
    public MessageClobTransfer getMessageClobTransfer(UserVisit userVisit, MessageClob messageClob) {
        var messageClobTransfer = get(messageClob);
        
        if(messageClobTransfer == null) {
            var message = messageControl.getMessageTransfer(userVisit, messageClob.getMessage());
            var language = partyControl.getLanguageTransfer(userVisit, messageClob.getLanguage());
            var mimeType = mimeTypeControl.getMimeTypeTransfer(userVisit, messageClob.getMimeType());
            var clob = messageClob.getClob();
            
            messageClobTransfer = new MessageClobTransfer(message, language, mimeType, clob);
            put(userVisit, messageClob, messageClobTransfer);
        }
        
        return messageClobTransfer;
    }
    
}
