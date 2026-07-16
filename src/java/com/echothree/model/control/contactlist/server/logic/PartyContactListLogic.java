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

package com.echothree.model.control.contactlist.server.logic;

import com.echothree.control.user.contactlist.common.spec.PartyContactListUniversalSpec;
import com.echothree.model.control.contactlist.common.exception.UnknownPartyContactListException;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PartyContactListLogic
        extends BaseLogic {

    protected PartyContactListLogic() {
        super();
    }

    @Inject
    ContactListControl contactListControl;

    @Inject
    ContactListLogic contactListLogic;

    @Inject
    PartyLogic partyLogic;

    public PartyContactList getPartyContactListByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PartyContactListUniversalSpec universalSpec, final EntityPermission entityPermission) {
        PartyContactList partyContactList = null;
        var partyName = universalSpec.getPartyName();
        var contactListName = universalSpec.getContactListName();
        var parameterCount = (partyName == null && contactListName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(partyName == null && contactListName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.PartyContactList.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        partyContactList = contactListControl.getPartyContactListByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    if(partyName != null && contactListName != null) {
                        var party = partyLogic.getPartyByName(eea, partyName);

                        if(!eea.hasExecutionErrors()) {
                            var contactList = contactListLogic.getContactListByName(eea, contactListName);

                            if(!eea.hasExecutionErrors()) {
                                partyContactList = contactListControl.getPartyContactList(party, contactList, entityPermission);

                                if(partyContactList == null) {
                                    handleExecutionError(UnknownPartyContactListException.class, eea, ExecutionErrors.UnknownPartyContactList.name(), partyName, contactListName);
                                }
                            }
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return partyContactList;
    }

    public PartyContactList getPartyContactListByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PartyContactListUniversalSpec universalSpec) {
        return getPartyContactListByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public PartyContactList getPartyContactListByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PartyContactListUniversalSpec universalSpec) {
        return getPartyContactListByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
