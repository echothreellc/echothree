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

import com.echothree.control.user.security.common.spec.SecurityRoleUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.security.common.exception.MissingRequiredSecurityRoleGroupNameException;
import com.echothree.model.control.security.common.exception.UnknownDefaultSecurityRoleException;
import com.echothree.model.control.security.common.exception.UnknownSecurityRoleNameException;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class SecurityRoleLogic
        extends BaseLogic {

    @Inject
    protected SecurityControl securityControl;

    @Inject
    protected SecurityRoleGroupLogic securityRoleGroupLogic;
    
    protected SecurityRoleLogic() {
        super();
    }

    public static SecurityRoleLogic getInstance() {
        return CDI.current().select(SecurityRoleLogic.class).get();
    }

    public SecurityRole getSecurityRoleByName(final Class<? extends BaseException> unknownSecurityRoleGroupException, final ExecutionErrors unknownSecurityRoleGroupExecutionError,
            final Class<? extends BaseException>  unknownSecurityRoleException, final ExecutionErrors unknownSecurityRoleExecutionError,
            final ExecutionErrorAccumulator eea, final String securityRoleGroupName, final String securityRoleName) {
        var securityRoleGroup = securityRoleGroupLogic.getSecurityRoleGroupByName(unknownSecurityRoleGroupException, unknownSecurityRoleGroupExecutionError,
                eea, securityRoleGroupName, EntityPermission.READ_ONLY);
        SecurityRole securityRole = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            securityRole = getSecurityRoleByName(unknownSecurityRoleException, unknownSecurityRoleExecutionError, eea,
                    securityRoleGroup, securityRoleName);
        }

        return securityRole;
    }

    public SecurityRole getSecurityRoleByName(final Class<? extends BaseException> unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final SecurityRoleGroup securityRoleGroup, final String securityRoleName, EntityPermission entityPermission) {
        var securityRole = securityControl.getSecurityRoleByName(securityRoleGroup, securityRoleName, entityPermission);

        if(securityRole == null) {
            handleExecutionError(unknownException, eea, unknownExecutionError.name(), securityRoleGroup.getLastDetail().getSecurityRoleGroupName(),
                    securityRoleName);
        }

        return securityRole;
    }

    public SecurityRole getSecurityRoleByName(final Class<? extends BaseException> unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final SecurityRoleGroup securityRoleGroup, final String securityRoleName) {
        return getSecurityRoleByName(unknownException, unknownExecutionError, eea, securityRoleGroup, securityRoleName, EntityPermission.READ_ONLY);
    }

    public SecurityRole getSecurityRoleByNameForUpdate(final Class<? extends BaseException> unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final SecurityRoleGroup securityRoleGroup, final String securityRoleName) {
        return getSecurityRoleByName(unknownException, unknownExecutionError, eea, securityRoleGroup, securityRoleName, EntityPermission.READ_WRITE);
    }

    public SecurityRole getSecurityRoleByName(final ExecutionErrorAccumulator eea, final SecurityRoleGroup securityRoleGroup, final String securityRoleName,
            final EntityPermission entityPermission) {
        return getSecurityRoleByName(UnknownSecurityRoleNameException.class, ExecutionErrors.UnknownSecurityRoleName,
                eea, securityRoleGroup, securityRoleName, entityPermission);
    }

    public SecurityRole getSecurityRoleByName(final ExecutionErrorAccumulator eea, final SecurityRoleGroup securityRoleGroup, final String securityRoleName) {
        return getSecurityRoleByName(UnknownSecurityRoleNameException.class, ExecutionErrors.UnknownSecurityRoleName,
                eea, securityRoleGroup, securityRoleName);
    }

    public SecurityRole getSecurityRoleByNameForUpdate(final ExecutionErrorAccumulator eea, final SecurityRoleGroup securityRoleGroup, final String securityRoleName) {
        return getSecurityRoleByNameForUpdate(UnknownSecurityRoleNameException.class, ExecutionErrors.UnknownSecurityRoleName,
                eea, securityRoleGroup, securityRoleName);
    }

    public SecurityRole getSecurityRoleByName(final ExecutionErrorAccumulator eea, final String securityRoleGroupName, final String securityRoleName,
            final EntityPermission entityPermission) {
        var securityRoleGroup = securityRoleGroupLogic.getSecurityRoleGroupByName(eea, securityRoleGroupName);
        SecurityRole securityRole = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            securityRole = getSecurityRoleByName(UnknownSecurityRoleNameException.class, ExecutionErrors.UnknownSecurityRoleName,
                    eea, securityRoleGroup, securityRoleName, entityPermission);
        }

        return securityRole;
    }

    public SecurityRole getSecurityRoleByName(final ExecutionErrorAccumulator eea, final String securityRoleGroupName, final String securityRoleName) {
        return getSecurityRoleByName(eea, securityRoleGroupName, securityRoleName, EntityPermission.READ_ONLY);
    }

    public SecurityRole getSecurityRoleByNameForUpdate(final ExecutionErrorAccumulator eea, final String securityRoleGroupName, final String securityRoleName) {
        return getSecurityRoleByName(eea, securityRoleGroupName, securityRoleName, EntityPermission.READ_WRITE);
    }

    public SecurityRole getSecurityRoleByUniversalSpec(final ExecutionErrorAccumulator eea, final SecurityRoleUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var securityRoleGroupName = universalSpec.getSecurityRoleGroupName();
        var securityRoleName = universalSpec.getSecurityRoleName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(securityRoleGroupName, securityRoleName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        SecurityRole securityRole = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            SecurityRoleGroup securityRoleGroup = null;

            if(securityRoleGroupName != null) {
                securityRoleGroup = securityRoleGroupLogic.getSecurityRoleGroupByName(eea, securityRoleGroupName);
            } else {
                handleExecutionError(MissingRequiredSecurityRoleGroupNameException.class, eea, ExecutionErrors.MissingRequiredSecurityRoleGroupName.name());
            }

            if(!eea.hasExecutionErrors()) {
                if(securityRoleName == null) {
                    if(allowDefault) {
                        securityRole = securityControl.getDefaultSecurityRole(securityRoleGroup, entityPermission);

                        if(securityRole == null) {
                            handleExecutionError(UnknownDefaultSecurityRoleException.class, eea, ExecutionErrors.UnknownDefaultSecurityRole.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    securityRole = getSecurityRoleByName(eea, securityRoleGroup, securityRoleName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.SecurityRole.name());

            if(!eea.hasExecutionErrors()) {
                securityRole = securityControl.getSecurityRoleByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return securityRole;
    }

    public SecurityRole getSecurityRoleByUniversalSpec(final ExecutionErrorAccumulator eea, final SecurityRoleUniversalSpec universalSpec,
            boolean allowDefault) {
        return getSecurityRoleByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SecurityRole getSecurityRoleByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final SecurityRoleUniversalSpec universalSpec,
            boolean allowDefault) {
        return getSecurityRoleByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }
    
    public boolean hasSecurityRole(final Party party, final SecurityRole securityRole) {
        var result = false;

        if(party != null) {
            var partySecurityRole = securityControl.getPartySecurityRole(party, securityRole);

            if(partySecurityRole != null) {
                var securityRolePartyType = securityControl.getSecurityRolePartyType(securityRole, party.getLastDetail().getPartyType());

                if(securityRolePartyType == null) {
                    result = true;
                } else {
                    var partySelector = securityRolePartyType.getPartySelector();

                    if(partySelector != null) {
                        var selectorControl = Session.getModelController(SelectorControl.class);

                        if(selectorControl.getSelectorParty(partySelector, party) != null) {
                            result = true;
                        }
                    }
                }
            }
        }
        
        return result;
    }

    public boolean hasSecurityRoleUsingNames(final ExecutionErrorAccumulator eea, final Party party, final String securityRoleGroupName,
            final String securityRoleName) {
        var securityRole = getSecurityRoleByName(eea, securityRoleGroupName, securityRoleName);
        var result = false;
        
        if(!hasExecutionErrors(eea)) {
            result = hasSecurityRole(party, securityRole);
        }
        
        return result;
    }
    
}