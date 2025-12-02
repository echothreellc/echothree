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

package com.echothree.control.user.core.server.command;

import com.echothree.model.control.core.server.control.EntityLockControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import javax.enterprise.context.Dependent;

@Dependent
public class RemovedExpiredEntityLocksCommand
        extends BaseSimpleCommand {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null)
                )));
    }
    
    /** Creates a new instance of RemovedExpiredEntityLocksCommand */
    public RemovedExpiredEntityLocksCommand() {
        super(COMMAND_SECURITY_DEFINITION, false);
    }

    @Override
    protected BaseResult execute() {
        var entityLockControl = Session.getModelController(EntityLockControl.class);
        Long lockExpirationTime = session.START_TIME - 24 * 60 * 60 * 1000; // 24 hours
        
        entityLockControl.removeEntityLocksByLockExpirationTime(lockExpirationTime);
        
        return null;
    }
    
}
