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

package com.echothree.model.control.party.server.logic;

import com.echothree.control.user.party.common.spec.RoleTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.exception.DuplicateRoleTypeNameException;
import com.echothree.model.control.party.common.exception.UnknownRoleTypeNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.RoleType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class RoleTypeLogic
        extends BaseLogic {

    protected RoleTypeLogic() {
        super();
    }

    public static RoleTypeLogic getInstance() {
        return CDI.current().select(RoleTypeLogic.class).get();
    }

    public RoleType createRoleType(final ExecutionErrorAccumulator eea, final String roleTypeName, final RoleType parentRoleType,
            final Language language, final String description) {
        var partyControl = Session.getModelController(PartyControl.class);
        var roleType = partyControl.getRoleTypeByName(roleTypeName);

        if(roleType == null) {
            roleType = partyControl.createRoleType(roleTypeName, parentRoleType);

            if(description != null) {
                partyControl.createRoleTypeDescription(roleType, language, description);
            }
        } else {
            handleExecutionError(DuplicateRoleTypeNameException.class, eea, ExecutionErrors.DuplicateRoleTypeName.name(), roleTypeName);
        }

        return roleType;
    }

    public RoleType getRoleTypeByName(final ExecutionErrorAccumulator eea, final String roleTypeName,
            final EntityPermission entityPermission) {
        var partyControl = Session.getModelController(PartyControl.class);
        var roleType = partyControl.getRoleTypeByName(roleTypeName, entityPermission);

        if(roleType == null) {
            handleExecutionError(UnknownRoleTypeNameException.class, eea, ExecutionErrors.UnknownRoleTypeName.name(), roleTypeName);
        }

        return roleType;
    }

    public RoleType getRoleTypeByName(final ExecutionErrorAccumulator eea, final String roleTypeName) {
        return getRoleTypeByName(eea, roleTypeName, EntityPermission.READ_ONLY);
    }

    public RoleType getRoleTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String roleTypeName) {
        return getRoleTypeByName(eea, roleTypeName, EntityPermission.READ_WRITE);
    }

    public RoleType getRoleTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final RoleTypeUniversalSpec universalSpec, final EntityPermission entityPermission) {
        RoleType roleType = null;
        var partyControl = Session.getModelController(PartyControl.class);
        var roleTypeName = universalSpec.getRoleTypeName();
        var parameterCount = (roleTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        if(parameterCount == 1) {
            if(roleTypeName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.RoleType.name());

                if(!eea.hasExecutionErrors()) {
                    roleType = partyControl.getRoleTypeByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                roleType = getRoleTypeByName(eea, roleTypeName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return roleType;
    }

    public RoleType getRoleTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final RoleTypeUniversalSpec universalSpec) {
        return getRoleTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public RoleType getRoleTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final RoleTypeUniversalSpec universalSpec) {
        return getRoleTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
