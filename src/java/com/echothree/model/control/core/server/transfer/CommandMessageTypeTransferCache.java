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

import com.echothree.model.control.core.remote.transfer.CommandMessageTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.core.server.entity.CommandMessageTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CommandMessageTypeTransferCache
        extends BaseCoreTransferCache<CommandMessageType, CommandMessageTypeTransfer> {
    
    /** Creates a new instance of CommandMessageTypeTransferCache */
    public CommandMessageTypeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        setIncludeEntityInstance(true);
    }
    
    public CommandMessageTypeTransfer getCommandMessageTypeTransfer(CommandMessageType commandMessageType) {
        CommandMessageTypeTransfer commandMessageTypeTransfer = get(commandMessageType);
        
        if(commandMessageTypeTransfer == null) {
            CommandMessageTypeDetail commandMessageTypeDetail = commandMessageType.getLastDetail();
            String commandMessageTypeName = commandMessageTypeDetail.getCommandMessageTypeName();
            Boolean isDefault = commandMessageTypeDetail.getIsDefault();
            Integer sortOrder = commandMessageTypeDetail.getSortOrder();
            String description = coreControl.getBestCommandMessageTypeDescription(commandMessageType, getLanguage());
            
            commandMessageTypeTransfer = new CommandMessageTypeTransfer(commandMessageTypeName, isDefault, sortOrder, description);
            put(commandMessageType, commandMessageTypeTransfer);
        }
        
        return commandMessageTypeTransfer;
    }
    
}
