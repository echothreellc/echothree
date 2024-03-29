// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.message.common.transfer.MessageClobTransfer;
import com.echothree.model.control.message.common.transfer.MessageTransfer;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.message.server.entity.MessageClob;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class MessageClobTransferCache
        extends BaseMessageTransferCache<MessageClob, MessageClobTransfer> {
    
    CoreControl coreControl;
    PartyControl partyControl;
    
    /** Creates a new instance of MessageClobTransferCache */
    public MessageClobTransferCache(UserVisit userVisit, MessageControl messageControl) {
        super(userVisit, messageControl);
        
        coreControl = Session.getModelController(CoreControl.class);
        partyControl = Session.getModelController(PartyControl.class);
    }
    
    public MessageClobTransfer getMessageClobTransfer(MessageClob messageClob) {
        MessageClobTransfer messageClobTransfer = get(messageClob);
        
        if(messageClobTransfer == null) {
            MessageTransfer message = messageControl.getMessageTransfer(userVisit, messageClob.getMessage());
            LanguageTransfer language = partyControl.getLanguageTransfer(userVisit, messageClob.getLanguage());
            MimeTypeTransfer mimeType = coreControl.getMimeTypeTransfer(userVisit, messageClob.getMimeType());
            String clob = messageClob.getClob();
            
            messageClobTransfer = new MessageClobTransfer(message, language, mimeType, clob);
            put(messageClob, messageClobTransfer);
        }
        
        return messageClobTransfer;
    }
    
}
