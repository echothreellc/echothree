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

import com.echothree.model.control.core.common.transfer.CommandTransfer;
import com.echothree.model.control.core.server.control.ComponentVendorControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.Command;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CommandTransferCache
        extends BaseCoreTransferCache<Command, CommandTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    ComponentVendorControl componentVendorControl = Session.getModelController(ComponentVendorControl.class);

    /** Creates a new instance of CommandTransferCache */
    public CommandTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    public CommandTransfer getCommandTransfer(Command command) {
        var commandTransfer = get(command);
        
        if(commandTransfer == null) {
            var commandDetail = command.getLastDetail();
            var componentVendor = componentVendorControl.getComponentVendorTransfer(userVisit, commandDetail.getComponentVendor());
            var commandName = commandDetail.getCommandName();
            var sortOrder = commandDetail.getSortOrder();
            var description = coreControl.getBestCommandDescription(command, getLanguage());
    
            commandTransfer = new CommandTransfer(componentVendor, commandName, sortOrder, description);
            put(command, commandTransfer);
        }
        
        return commandTransfer;
    }
    
}
