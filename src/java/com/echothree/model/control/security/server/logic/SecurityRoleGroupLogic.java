// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.security.server.logic;

import com.echothree.model.control.security.common.exception.UnknownSecurityRoleGroupNameException;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class SecurityRoleGroupLogic
        extends BaseLogic {

    private SecurityRoleGroupLogic() {
        super();
    }

    private static class SecurityRoleGroupLogicHolder {
        static SecurityRoleGroupLogic instance = new SecurityRoleGroupLogic();
    }

    public static SecurityRoleGroupLogic getInstance() {
        return SecurityRoleGroupLogicHolder.instance;
    }

    public SecurityRoleGroup getSecurityRoleGroupByName(final ExecutionErrorAccumulator eea, final String securityRoleGroupName) {
        var securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
        var securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);

        if(securityRoleGroup == null) {
            handleExecutionError(UnknownSecurityRoleGroupNameException.class, eea, ExecutionErrors.UnknownSecurityRoleGroupName.name(), securityRoleGroupName);
        }

        return securityRoleGroup;
    }

}