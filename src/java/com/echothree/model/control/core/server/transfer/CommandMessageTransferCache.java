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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.CommandMessageTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTranslationTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.CommandMessage;
import com.echothree.model.data.core.server.entity.CommandMessageDetail;
import com.echothree.model.data.core.server.entity.CommandMessageTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import java.util.Set;

public class CommandMessageTransferCache
        extends BaseCoreTransferCache<CommandMessage, CommandMessageTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    boolean includeTranslations;
    
    /** Creates a new instance of CommandMessageTransferCache */
    public CommandMessageTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        var options = session.getOptions();
        if(options != null) {
            includeTranslations = options.contains(CoreOptions.CommandMessageIncludeTranslations);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public CommandMessageTransfer getCommandMessageTransfer(CommandMessage commandMessage) {
        CommandMessageTransfer commandMessageTransfer = get(commandMessage);
        
        if(commandMessageTransfer == null) {
            CommandMessageDetail commandMessageDetail = commandMessage.getLastDetail();
            CommandMessageTypeTransfer commandMessageType = coreControl.getCommandMessageTypeTransfer(userVisit, commandMessageDetail.getCommandMessageType());
            String commandMessageKey = commandMessageDetail.getCommandMessageKey();
            CommandMessageTranslation commandMessageTranslation = coreControl.getBestCommandMessageTranslation(commandMessage, getLanguage());
            String translation = commandMessageTranslation == null ? null : commandMessageTranslation.getTranslation();
    
            commandMessageTransfer = new CommandMessageTransfer(commandMessageType, commandMessageKey, translation);
            put(commandMessage, commandMessageTransfer);
            
            if(includeTranslations) {
                List<CommandMessageTranslationTransfer> commandMessageTranslationTransfers = coreControl.getCommandMessageTranslationTransfersByCommandMessage(userVisit, commandMessage);
                MapWrapper<CommandMessageTranslationTransfer> commandMessageTranslations = new MapWrapper<>();

                commandMessageTranslationTransfers.forEach((commandMessageTranslationTransfer) -> {
                    commandMessageTranslations.put(commandMessageTranslationTransfer.getLanguage().getLanguageIsoName(), commandMessageTranslationTransfer);
                });

                commandMessageTransfer.setCommandMessageTranslations(commandMessageTranslations);
            }
        }
        
        return commandMessageTransfer;
    }
    
}
