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

package com.echothree.model.control.security.server.logic;

import com.echothree.control.user.security.common.spec.SecurityRoleGroupUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.security.common.exception.DuplicateSecurityRoleGroupNameException;
import com.echothree.model.control.security.common.exception.UnknownDefaultSecurityRoleGroupException;
import com.echothree.model.control.security.common.exception.UnknownSecurityRoleGroupNameException;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SecurityRoleGroupLogic
        extends BaseLogic {

    protected SecurityRoleGroupLogic() {
        super();
    }

    public static SecurityRoleGroupLogic getInstance() {
        return CDI.current().select(SecurityRoleGroupLogic.class).get();
    }

    public SecurityRoleGroup createSecurityRoleGroup(final ExecutionErrorAccumulator eea, final String securityRoleGroupName,
            final SecurityRoleGroup parentSecurityRoleGroup, final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);

        if(securityRoleGroup == null) {
            securityRoleGroup = securityControl.createSecurityRoleGroup(securityRoleGroupName, parentSecurityRoleGroup,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                securityControl.createSecurityRoleGroupDescription(securityRoleGroup, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateSecurityRoleGroupNameException.class, eea, ExecutionErrors.DuplicateSecurityRoleGroupName.name(), securityRoleGroupName);
        }

        return securityRoleGroup;
    }

    public SecurityRoleGroup getSecurityRoleGroupByName(final Class<? extends BaseException> unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final String securityRoleGroupName, final EntityPermission entityPermission) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName, entityPermission);

        if(securityRoleGroup == null) {
            handleExecutionError(unknownException, eea, unknownExecutionError.name(), securityRoleGroupName);
        }

        return securityRoleGroup;
    }

    public SecurityRoleGroup getSecurityRoleGroupByName(final ExecutionErrorAccumulator eea, final String securityRoleGroupName,
            final EntityPermission entityPermission) {
        return getSecurityRoleGroupByName(UnknownSecurityRoleGroupNameException.class, ExecutionErrors.UnknownSecurityRoleGroupName, eea,
                securityRoleGroupName, entityPermission);
    }

    public SecurityRoleGroup getSecurityRoleGroupByName(final ExecutionErrorAccumulator eea, final String securityRoleGroupName) {
        return getSecurityRoleGroupByName(eea, securityRoleGroupName, EntityPermission.READ_ONLY);
    }

    public SecurityRoleGroup getSecurityRoleGroupByNameForUpdate(final ExecutionErrorAccumulator eea, final String securityRoleGroupName) {
        return getSecurityRoleGroupByName(eea, securityRoleGroupName, EntityPermission.READ_WRITE);
    }

    public SecurityRoleGroup getSecurityRoleGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SecurityRoleGroupUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        SecurityRoleGroup securityRoleGroup = null;
        var securityControl = Session.getModelController(SecurityControl.class);
        var securityRoleGroupName = universalSpec.getSecurityRoleGroupName();
        var parameterCount = (securityRoleGroupName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    securityRoleGroup = securityControl.getDefaultSecurityRoleGroup(entityPermission);

                    if(securityRoleGroup == null) {
                        handleExecutionError(UnknownDefaultSecurityRoleGroupException.class, eea, ExecutionErrors.UnknownDefaultSecurityRoleGroup.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(securityRoleGroupName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.SecurityRoleGroup.name());

                    if(!eea.hasExecutionErrors()) {
                        securityRoleGroup = securityControl.getSecurityRoleGroupByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    securityRoleGroup = getSecurityRoleGroupByName(eea, securityRoleGroupName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return securityRoleGroup;
    }

    public SecurityRoleGroup getSecurityRoleGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SecurityRoleGroupUniversalSpec universalSpec, boolean allowDefault) {
        return getSecurityRoleGroupByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SecurityRoleGroup getSecurityRoleGroupByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final SecurityRoleGroupUniversalSpec universalSpec, boolean allowDefault) {
        return getSecurityRoleGroupByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteSecurityRoleGroup(final ExecutionErrorAccumulator eea, final SecurityRoleGroup securityRoleGroup,
            final BasePK deletedBy) {
        var securityControl = Session.getModelController(SecurityControl.class);

        securityControl.deleteSecurityRoleGroup(securityRoleGroup, deletedBy);
    }

}