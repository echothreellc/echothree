// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.CommandMessageTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTranslationTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.CommandMessageTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CommandMessageTranslationTransferCache
        extends BaseCoreTransferCache<CommandMessageTranslation, CommandMessageTranslationTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of CommandMessageTranslationTransferCache */
    public CommandMessageTranslationTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        partyControl = Session.getModelController(PartyControl.class);
    }
    
    public CommandMessageTranslationTransfer getCommandMessageTranslationTransfer(CommandMessageTranslation commandMessageTranslation) {
        CommandMessageTranslationTransfer commandMessageTranslationTransfer = get(commandMessageTranslation);
        
        if(commandMessageTranslationTransfer == null) {
            CommandMessageTransfer commandMessage = coreControl.getCommandMessageTransfer(userVisit, commandMessageTranslation.getCommandMessage());
            LanguageTransfer language = partyControl.getLanguageTransfer(userVisit, commandMessageTranslation.getLanguage());
            String translation = commandMessageTranslation.getTranslation();
            
            commandMessageTranslationTransfer = new CommandMessageTranslationTransfer(commandMessage, language, translation);
            put(commandMessageTranslation, commandMessageTranslationTransfer);
        }
        
        return commandMessageTranslationTransfer;
    }
    
}
