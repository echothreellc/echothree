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

package com.echothree.model.control.user.server.logic;

import com.echothree.control.user.user.common.spec.UserVisitGroupUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.user.common.exception.UnknownUserVisitGroupNameException;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.UserVisitGroup;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class UserVisitGroupLogic
        extends BaseLogic {

    protected UserVisitGroupLogic() {
        super();
    }

    public static UserVisitGroupLogic getInstance() {
        return CDI.current().select(UserVisitGroupLogic.class).get();
    }

    public UserVisitGroup getUserVisitGroupByName(final ExecutionErrorAccumulator eea, final String userVisitGroupName,
            final EntityPermission entityPermission) {
        var userControl = Session.getModelController(UserControl.class);
        var userVisitGroup = userControl.getUserVisitGroupByName(userVisitGroupName, entityPermission);

        if(userVisitGroup == null) {
            handleExecutionError(UnknownUserVisitGroupNameException.class, eea, ExecutionErrors.UnknownUserVisitGroupName.name(), userVisitGroupName);
        }

        return userVisitGroup;
    }

    public UserVisitGroup getUserVisitGroupByName(final ExecutionErrorAccumulator eea, final String userVisitGroupName) {
        return getUserVisitGroupByName(eea, userVisitGroupName, EntityPermission.READ_ONLY);
    }

    public UserVisitGroup getUserVisitGroupByNameForUpdate(final ExecutionErrorAccumulator eea, final String userVisitGroupName) {
        return getUserVisitGroupByName(eea, userVisitGroupName, EntityPermission.READ_WRITE);
    }

    public UserVisitGroup getUserVisitGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final UserVisitGroupUniversalSpec universalSpec, final EntityPermission entityPermission) {
        UserVisitGroup userVisitGroup = null;
        var userControl = Session.getModelController(UserControl.class);
        var userVisitGroupName = universalSpec.getUserVisitGroupName();
        var parameterCount = (userVisitGroupName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1:
                if(userVisitGroupName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.UserVisitGroup.name());

                    if(!eea.hasExecutionErrors()) {
                        userVisitGroup = userControl.getUserVisitGroupByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    userVisitGroup = getUserVisitGroupByName(eea, userVisitGroupName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return userVisitGroup;
    }

    public UserVisitGroup getUserVisitGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final UserVisitGroupUniversalSpec universalSpec) {
        return getUserVisitGroupByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public UserVisitGroup getUserVisitGroupByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final UserVisitGroupUniversalSpec universalSpec) {
        return getUserVisitGroupByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
