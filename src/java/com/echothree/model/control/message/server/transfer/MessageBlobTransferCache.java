// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.message.common.transfer.MessageBlobTransfer;
import com.echothree.model.control.message.common.transfer.MessageTransfer;
import com.echothree.model.control.message.server.MessageControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.message.server.entity.MessageBlob;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.Session;

public class MessageBlobTransferCache
        extends BaseMessageTransferCache<MessageBlob, MessageBlobTransfer> {
    
    CoreControl coreControl;
    PartyControl partyControl;
    
    /** Creates a new instance of MessageBlobTransferCache */
    public MessageBlobTransferCache(UserVisit userVisit, MessageControl messageControl) {
        super(userVisit, messageControl);
        
        coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    }
    
    public MessageBlobTransfer getMessageBlobTransfer(MessageBlob messageBlob) {
        MessageBlobTransfer messageBlobTransfer = get(messageBlob);
        
        if(messageBlobTransfer == null) {
            MessageTransfer message = messageControl.getMessageTransfer(userVisit, messageBlob.getMessage());
            LanguageTransfer language = partyControl.getLanguageTransfer(userVisit, messageBlob.getLanguage());
            MimeTypeTransfer mimeType = coreControl.getMimeTypeTransfer(userVisit, messageBlob.getMimeType());
            ByteArray blob = messageBlob.getBlob();
            
            messageBlobTransfer = new MessageBlobTransfer(message, language, mimeType, blob);
            put(messageBlob, messageBlobTransfer);
        }
        
        return messageBlobTransfer;
    }
    
}
