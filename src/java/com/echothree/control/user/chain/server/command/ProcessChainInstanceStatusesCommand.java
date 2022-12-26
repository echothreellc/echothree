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

package com.echothree.control.user.chain.server.command;

import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.chain.server.logic.ChainInstanceStatusLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.chain.server.entity.ChainInstanceStatus;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;

public class ProcessChainInstanceStatusesCommand
        extends BaseSimpleCommand {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null)
                )));
    }
    
    /** Creates a new instance of ProcessChainInstanceStatusesCommand */
    public ProcessChainInstanceStatusesCommand(UserVisitPK userVisitPK) {
        super(userVisitPK, COMMAND_SECURITY_DEFINITION, false);
    }

    @Override
    protected BaseResult execute() {
        var chainControl = Session.getModelController(ChainControl.class);
        ChainInstanceStatusLogic chainInstanceStatusLogic = ChainInstanceStatusLogic.getInstance();
        BasePK processedBy = getPartyPK();
        long chainInstanceStatusesProcessed = 0;
        long remainingTime = (long) 2 * 60 * 1000; // 2 minutes
        
        for(ChainInstanceStatus chainInstanceStatus: chainControl.getChainInstanceStatusesByNextChainActionSetTimeForUpdate(session.START_TIME_LONG)) {
            long startTime = System.currentTimeMillis();

            chainInstanceStatusLogic.processChainInstanceStatus(session, chainControl, chainInstanceStatus, processedBy);
            
            chainInstanceStatusesProcessed++;
            remainingTime -= System.currentTimeMillis() - startTime;
            if(remainingTime < 0) {
                break;
            }
        }

        return null;
    }
}