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

import com.echothree.control.user.party.common.spec.PartyAliasTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.exception.DuplicatePartyAliasTypeNameException;
import com.echothree.model.control.party.common.exception.UnknownDefaultPartyAliasTypeException;
import com.echothree.model.control.party.common.exception.UnknownDefaultPartyTypeException;
import com.echothree.model.control.party.common.exception.UnknownPartyAliasTypeNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.value.PartyAliasTypeDetailValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PartyAliasTypeLogic
        extends BaseLogic {

    protected PartyAliasTypeLogic() {
        super();
    }

    public static PartyAliasTypeLogic getInstance() {
        return CDI.current().select(PartyAliasTypeLogic.class).get();
    }

    public PartyAliasType createPartyAliasType(final ExecutionErrorAccumulator eea, final String partyTypeName, final String partyAliasTypeName,
            final String validationPattern, final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var partyType = PartyTypeLogic.getInstance().getPartyTypeByName(eea, partyTypeName);
        PartyAliasType partyAliasType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            partyAliasType = createPartyAliasType(eea, partyType, partyAliasTypeName, validationPattern, isDefault, sortOrder,
                    language, description, createdBy);
        }

        return partyAliasType;
    }

    public PartyAliasType createPartyAliasType(final ExecutionErrorAccumulator eea, final PartyType partyType, final String partyAliasTypeName,
            final String validationPattern, final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyAliasType = partyControl.getPartyAliasTypeByName(partyType, partyAliasTypeName);

        if(partyAliasType == null) {
            partyAliasType = partyControl.createPartyAliasType(partyType, partyAliasTypeName, validationPattern, isDefault,
                    sortOrder, createdBy);

            if(description != null) {
                partyControl.createPartyAliasTypeDescription(partyAliasType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicatePartyAliasTypeNameException.class, eea, ExecutionErrors.DuplicatePartyAliasTypeName.name(), partyAliasTypeName);
        }
        return partyAliasType;
    }

    public PartyAliasType getPartyAliasTypeByName(final ExecutionErrorAccumulator eea, final PartyType partyType, final String partyAliasTypeName,
            final EntityPermission entityPermission) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyAliasType = partyControl.getPartyAliasTypeByName(partyType, partyAliasTypeName, entityPermission);

        if(partyAliasType == null) {
            handleExecutionError(UnknownPartyAliasTypeNameException.class, eea, ExecutionErrors.UnknownPartyAliasTypeName.name(),
                    partyType.getPartyTypeName(), partyAliasTypeName);
        }

        return partyAliasType;
    }

    public PartyAliasType getPartyAliasTypeByName(final ExecutionErrorAccumulator eea, final PartyType partyType, final String partyAliasTypeName) {
        return getPartyAliasTypeByName(eea, partyType, partyAliasTypeName, EntityPermission.READ_ONLY);
    }

    public PartyAliasType getPartyAliasTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final PartyType partyType, final String partyAliasTypeName) {
        return getPartyAliasTypeByName(eea, partyType, partyAliasTypeName, EntityPermission.READ_WRITE);
    }

    public PartyAliasType getPartyAliasTypeByName(final ExecutionErrorAccumulator eea, final String partyTypeName, final String partyAliasTypeName,
            final EntityPermission entityPermission) {
        var partyType = PartyTypeLogic.getInstance().getPartyTypeByName(eea, partyTypeName);
        PartyAliasType partyAliasType = null;

        if(!eea.hasExecutionErrors()) {
            partyAliasType = getPartyAliasTypeByName(eea, partyType, partyAliasTypeName, entityPermission);
        }

        return partyAliasType;
    }

    public PartyAliasType getPartyAliasTypeByName(final ExecutionErrorAccumulator eea, final String partyTypeName, final String partyAliasTypeName) {
        return getPartyAliasTypeByName(eea, partyTypeName, partyAliasTypeName, EntityPermission.READ_ONLY);
    }

    public PartyAliasType getPartyAliasTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String partyTypeName, final String partyAliasTypeName) {
        return getPartyAliasTypeByName(eea, partyTypeName, partyAliasTypeName, EntityPermission.READ_WRITE);
    }

    public PartyAliasType getPartyAliasTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final PartyAliasTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyTypeName = universalSpec.getPartyTypeName();
        var partyAliasTypeName = universalSpec.getPartyAliasTypeName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(partyTypeName, partyAliasTypeName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        PartyAliasType partyAliasType = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            PartyType partyType = null;

            if(partyTypeName == null) {
                if(allowDefault) {
                    partyType = partyControl.getDefaultPartyType();

                    if(partyType == null) {
                        handleExecutionError(UnknownDefaultPartyTypeException.class, eea, ExecutionErrors.UnknownDefaultPartyType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                partyType = PartyTypeLogic.getInstance().getPartyTypeByName(eea, partyTypeName);
            }

            if(!eea.hasExecutionErrors()) {
                if(partyAliasTypeName == null) {
                    if(allowDefault) {
                        partyAliasType = partyControl.getDefaultPartyAliasType(partyType, entityPermission);

                        if(partyAliasType == null) {
                            handleExecutionError(UnknownDefaultPartyAliasTypeException.class, eea, ExecutionErrors.UnknownDefaultPartyAliasType.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    partyAliasType = getPartyAliasTypeByName(eea, partyType, partyAliasTypeName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.PartyAliasType.name());

            if(!eea.hasExecutionErrors()) {
                partyAliasType = partyControl.getPartyAliasTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return partyAliasType;
    }

    public PartyAliasType getPartyAliasTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final PartyAliasTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getPartyAliasTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PartyAliasType getPartyAliasTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final PartyAliasTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getPartyAliasTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updatePartyAliasTypeFromValue(final ExecutionErrorAccumulator eea, final PartyAliasTypeDetailValue partyAliasTypeDetailValue,
            final BasePK updatedBy) {
        var partyControl = Session.getModelController(PartyControl.class);

        partyControl.updatePartyAliasTypeFromValue(partyAliasTypeDetailValue, updatedBy);
    }

    public void deletePartyAliasType(final ExecutionErrorAccumulator eea, final PartyAliasType partyAliasType,
            final BasePK deletedBy) {
        var partyControl = Session.getModelController(PartyControl.class);

        partyControl.deletePartyAliasType(partyAliasType, deletedBy);
    }
}
