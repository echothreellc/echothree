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

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.exception.InvalidPartyTypeException;
import com.echothree.model.control.party.common.exception.UnknownPartyNameException;
import com.echothree.model.control.party.common.exception.UnknownPartyTypeNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.factory.PartyFactory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PartyLogic
        extends BaseLogic {

    protected PartyLogic() {
        super();
    }

    public static PartyLogic getInstance() {
        return CDI.current().select(PartyLogic.class).get();
    }
    
    /** Assume that the entityInstance passed to this function is an ECHO_THREE.Party. */
    public Party getPartyFromEntityInstance(final EntityInstance entityInstance) {
        var pk = new PartyPK(entityInstance.getEntityUniqueId());
        
        return PartyFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public boolean isPartyType(final Party party, final String ... partyTypeNames) {
        var partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();
        var found = false;
        var length = partyTypeNames.length;

        for(var i = 0; i < length ; i++) {
            if(partyTypeName.equals(partyTypeNames[i])) {
                found = true;
                break;
            }
        }

        return found;
    }

    public String checkPartyType(final ExecutionErrorAccumulator eea, final Party party, final String ... partyTypeNames) {
        var partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();
        var found = false;
        var length = partyTypeNames.length;

        for(var i = 0; i < length ; i++) {
            if(partyTypeName.equals(partyTypeNames[i])) {
                found = true;
                break;
            }
        }

        if(!found) {
            handleExecutionError(InvalidPartyTypeException.class, eea, ExecutionErrors.InvalidPartyType.name(), partyTypeName);
        }

        return partyTypeName;
    }

    public PartyType getPartyTypeByName(final ExecutionErrorAccumulator eea, final String partyTypeName) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyType = partyControl.getPartyTypeByName(partyTypeName);

        if(partyType == null) {
            handleExecutionError(UnknownPartyTypeNameException.class, eea, ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
        }

        return partyType;
    }
    
    public Party getPartyByName(final ExecutionErrorAccumulator eea, final String partyName,
            final EntityPermission entityPermission) {
        var partyControl = Session.getModelController(PartyControl.class);
        var party = partyControl.getPartyByName(partyName, entityPermission);

        if(party == null) {
            handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return party;
    }
    
    public Party getPartyByName(final ExecutionErrorAccumulator eea, final String partyName) {
        return getPartyByName(eea, partyName, EntityPermission.READ_ONLY);
    }
    
    public Party getPartyByNameForUpdate(final ExecutionErrorAccumulator eea, final String partyName) {
        return getPartyByName(eea, partyName, EntityPermission.READ_WRITE);
    }
    
    public Party getPartyByName(final ExecutionErrorAccumulator eea, final String partyName, final String ... partyTypeNames) {
        var party = getPartyByName(eea, partyName);

        if(party != null) {
            checkPartyType(eea, party, partyTypeNames);
        }

        return party;
    }

    public Party getPartyByName(final ExecutionErrorAccumulator eea, final String partyName,
            final UniversalEntitySpec universalEntitySpec) {
        var parameterCount = (partyName == null ? 0 : 1) +
                EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalEntitySpec);
        Party party = null;

        if(parameterCount == 1) {
            var partyControl = Session.getModelController(PartyControl.class);

            if(partyName != null) {
                party = partyControl.getPartyByName(partyName);

                if(party == null) {
                    handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(universalEntitySpec != null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalEntitySpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name());

                if(!eea.hasExecutionErrors()) {
                    party = partyControl.getPartyByEntityInstance(entityInstance);
                }
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return party;
    }

}
