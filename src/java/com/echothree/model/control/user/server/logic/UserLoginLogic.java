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

package com.echothree.model.control.user.server.logic;

import com.echothree.model.control.user.common.exception.UnknownUsernameException;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class UserLoginLogic
        extends BaseLogic {

    protected UserLoginLogic() {
        super();
    }

    public static UserLoginLogic getInstance() {
        return CDI.current().select(UserLoginLogic.class).get();
    }
    
    public UserLogin getUserLoginByUsername(final ExecutionErrorAccumulator eea, final String username) {
        var userControl = Session.getModelController(UserControl.class);
        var userLogin = userControl.getUserLoginByUsername(username);

        if(userLogin == null) {
            handleExecutionError(UnknownUsernameException.class, eea, ExecutionErrors.UnknownUsername.name(), username);
        }

        return userLogin;
    }
    
}
