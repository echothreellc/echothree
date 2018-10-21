// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.core.remote.transfer.CommandDescriptionTransfer;
import com.echothree.model.control.core.remote.transfer.CommandTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.core.server.entity.CommandDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CommandDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<CommandDescription, CommandDescriptionTransfer> {
    
    /** Creates a new instance of CommandDescriptionTransferCache */
    public CommandDescriptionTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public CommandDescriptionTransfer getCommandDescriptionTransfer(CommandDescription commandDescription) {
        CommandDescriptionTransfer commandDescriptionTransfer = get(commandDescription);
        
        if(commandDescriptionTransfer == null) {
            CommandTransfer commandTransfer = coreControl.getCommandTransfer(userVisit, commandDescription.getCommand());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, commandDescription.getLanguage());
            
            commandDescriptionTransfer = new CommandDescriptionTransfer(languageTransfer, commandTransfer,
                    commandDescription.getDescription());
            put(commandDescription, commandDescriptionTransfer);
        }
        return commandDescriptionTransfer;
    }
    
}
