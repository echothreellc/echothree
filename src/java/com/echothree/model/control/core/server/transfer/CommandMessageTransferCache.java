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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.CommandMessageTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTranslationTransfer;
import com.echothree.model.control.core.server.control.CommandControl;
import com.echothree.model.data.core.server.entity.CommandMessage;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;

public class CommandMessageTransferCache
        extends BaseCoreTransferCache<CommandMessage, CommandMessageTransfer> {

    CommandControl commandControl = Session.getModelController(CommandControl.class);

    boolean includeTranslations;
    
    /** Creates a new instance of CommandMessageTransferCache */
    public CommandMessageTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeTranslations = options.contains(CoreOptions.CommandMessageIncludeTranslations);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public CommandMessageTransfer getCommandMessageTransfer(UserVisit userVisit, CommandMessage commandMessage) {
        var commandMessageTransfer = get(commandMessage);
        
        if(commandMessageTransfer == null) {
            var commandMessageDetail = commandMessage.getLastDetail();
            var commandMessageType = commandControl.getCommandMessageTypeTransfer(userVisit, commandMessageDetail.getCommandMessageType());
            var commandMessageKey = commandMessageDetail.getCommandMessageKey();
            var commandMessageTranslation = commandControl.getBestCommandMessageTranslation(commandMessage, getLanguage(userVisit));
            var translation = commandMessageTranslation == null ? null : commandMessageTranslation.getTranslation();
    
            commandMessageTransfer = new CommandMessageTransfer(commandMessageType, commandMessageKey, translation);
            put(userVisit, commandMessage, commandMessageTransfer);
            
            if(includeTranslations) {
                var commandMessageTranslationTransfers = commandControl.getCommandMessageTranslationTransfersByCommandMessage(userVisit, commandMessage);
                var commandMessageTranslations = new MapWrapper<CommandMessageTranslationTransfer>();

                commandMessageTranslationTransfers.forEach((commandMessageTranslationTransfer) -> {
                    commandMessageTranslations.put(commandMessageTranslationTransfer.getLanguage().getLanguageIsoName(), commandMessageTranslationTransfer);
                });

                commandMessageTransfer.setCommandMessageTranslations(commandMessageTranslations);
            }
        }
        
        return commandMessageTransfer;
    }
    
}
