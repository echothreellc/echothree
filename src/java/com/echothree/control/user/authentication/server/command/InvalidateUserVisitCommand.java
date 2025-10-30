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

package com.echothree.control.user.authentication.server.command;

import com.echothree.model.control.user.server.logic.UserVisitLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InvalidateUserVisitCommand
        extends BaseSimpleCommand {
    
    /** Creates a new instance of InvalidateUserVisitCommand */
    public InvalidateUserVisitCommand() {
        super(null, false);

        // Prevent the possible creation of an extra UserSession when we're just going to be deleting it anyway.
        setCheckIdentityVerifiedTime(false);

        // This isn't really the user executing a command, don't bother with this. Also, UserVisit may have been
        // delete or removed at the point this is done.
        setUpdateLastCommandTime(false);
    }
    
    @Override
    protected BaseResult execute() {
        UserVisitLogic.getInstance().invalidateUserVisit(getUserVisitPK(), getPartyPK());
        
        return null;
    }
    
}
