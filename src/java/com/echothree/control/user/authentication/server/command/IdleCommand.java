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

package com.echothree.control.user.authentication.server.command;

import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import javax.enterprise.context.Dependent;

@Dependent
public class IdleCommand
        extends BaseSimpleCommand {
    
    /** Creates a new instance of IdleCommand */
    public IdleCommand() {
        super(null, false);
        
        // This command has no possible side effects beyond updating the LastCommandTime for the UserVisit. Do not log it.
        setLogCommand(false);
    }
    
    @Override
    protected BaseResult execute() {
        return null;
    }
    
}
