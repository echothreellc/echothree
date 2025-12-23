// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.CommandMessageTypeTransfer;
import com.echothree.model.control.core.server.control.CommandControl;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CommandMessageTypeTransferCache
        extends BaseCoreTransferCache<CommandMessageType, CommandMessageTypeTransfer> {

    CommandControl commandControl = Session.getModelController(CommandControl.class);

    /** Creates a new instance of CommandMessageTypeTransferCache */
    protected CommandMessageTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public CommandMessageTypeTransfer getCommandMessageTypeTransfer(UserVisit userVisit, CommandMessageType commandMessageType) {
        var commandMessageTypeTransfer = get(commandMessageType);
        
        if(commandMessageTypeTransfer == null) {
            var commandMessageTypeDetail = commandMessageType.getLastDetail();
            var commandMessageTypeName = commandMessageTypeDetail.getCommandMessageTypeName();
            var isDefault = commandMessageTypeDetail.getIsDefault();
            var sortOrder = commandMessageTypeDetail.getSortOrder();
            var description = commandControl.getBestCommandMessageTypeDescription(commandMessageType, getLanguage(userVisit));
            
            commandMessageTypeTransfer = new CommandMessageTypeTransfer(commandMessageTypeName, isDefault, sortOrder, description);
            put(userVisit, commandMessageType, commandMessageTypeTransfer);
        }
        
        return commandMessageTypeTransfer;
    }
    
}
