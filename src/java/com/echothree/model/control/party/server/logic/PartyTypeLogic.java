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

package com.echothree.model.control.party.server.logic;

import com.echothree.control.user.party.common.spec.PartyTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.exception.DuplicatePartyTypeNameException;
import com.echothree.model.control.party.common.exception.UnknownDefaultPartyTypeException;
import com.echothree.model.control.party.common.exception.UnknownPartyTypeNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PartyTypeLogic
        extends BaseLogic {

    protected PartyTypeLogic() {
        super();
    }

    public static PartyTypeLogic getInstance() {
        return CDI.current().select(PartyTypeLogic.class).get();
    }

    public PartyType createPartyType(final ExecutionErrorAccumulator eea, final String partyTypeName,
            final PartyType parentPartyType, final SequenceType billingAccountSequenceType, final Boolean allowUserLogins,
            final Boolean allowPartyAliases, final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyType = partyControl.getPartyTypeByName(partyTypeName);

        if(partyType == null) {
            partyType = partyControl.createPartyType(partyTypeName, parentPartyType, billingAccountSequenceType, allowUserLogins,
                    allowPartyAliases, isDefault, sortOrder);

            if(description != null) {
                partyControl.createPartyTypeDescription(partyType, language, description);
            }
        } else {
            handleExecutionError(DuplicatePartyTypeNameException.class, eea, ExecutionErrors.DuplicatePartyTypeName.name(), partyTypeName);
        }

        return partyType;
    }

    public PartyType getPartyTypeByName(final ExecutionErrorAccumulator eea, final String partyTypeName,
            final EntityPermission entityPermission) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyType = partyControl.getPartyTypeByName(partyTypeName, entityPermission);

        if(partyType == null) {
            handleExecutionError(UnknownPartyTypeNameException.class, eea, ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
        }

        return partyType;
    }

    public PartyType getPartyTypeByName(final ExecutionErrorAccumulator eea, final String partyTypeName) {
        return getPartyTypeByName(eea, partyTypeName, EntityPermission.READ_ONLY);
    }

    public PartyType getPartyTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String partyTypeName) {
        return getPartyTypeByName(eea, partyTypeName, EntityPermission.READ_WRITE);
    }

    public PartyType getPartyTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PartyTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        PartyType partyType = null;
        var partyControl = Session.getModelController(PartyControl.class);
        var partyTypeName = universalSpec.getPartyTypeName();
        var parameterCount = (partyTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    partyType = partyControl.getDefaultPartyType(entityPermission);

                    if(partyType == null) {
                        handleExecutionError(UnknownDefaultPartyTypeException.class, eea, ExecutionErrors.UnknownDefaultPartyType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(partyTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.PartyType.name());

                    if(!eea.hasExecutionErrors()) {
                        partyType = partyControl.getPartyTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    partyType = getPartyTypeByName(eea, partyTypeName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return partyType;
    }

    public PartyType getPartyTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PartyTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getPartyTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PartyType getPartyTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PartyTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getPartyTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
