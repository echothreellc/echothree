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

import com.echothree.model.control.core.common.transfer.CommandDescriptionTransfer;
import com.echothree.model.control.core.server.control.CommandControl;
import com.echothree.model.data.core.server.entity.CommandDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CommandDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<CommandDescription, CommandDescriptionTransfer> {

    CommandControl commandControl = Session.getModelController(CommandControl.class);

    /** Creates a new instance of CommandDescriptionTransferCache */
    public CommandDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public CommandDescriptionTransfer getCommandDescriptionTransfer(CommandDescription commandDescription) {
        var commandDescriptionTransfer = get(commandDescription);
        
        if(commandDescriptionTransfer == null) {
            var commandTransfer = commandControl.getCommandTransfer(userVisit, commandDescription.getCommand());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, commandDescription.getLanguage());
            
            commandDescriptionTransfer = new CommandDescriptionTransfer(languageTransfer, commandTransfer,
                    commandDescription.getDescription());
            put(commandDescription, commandDescriptionTransfer);
        }
        return commandDescriptionTransfer;
    }
    
}
