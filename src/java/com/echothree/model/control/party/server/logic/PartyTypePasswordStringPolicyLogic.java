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

import com.echothree.model.control.party.common.exception.UnknownPartyTypePasswordStringPolicyException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PartyTypePasswordStringPolicyLogic
        extends BaseLogic {

    @Inject
    PartyControl partyControl;

    protected PartyTypePasswordStringPolicyLogic() {
        super();
    }

    public PartyTypePasswordStringPolicy getPartyTypePasswordStringPolicy(final ExecutionErrorAccumulator eea, final PartyType partyType,
            final EntityPermission entityPermission) {
        var partyTypePasswordStringPolicy = partyControl.getPartyTypePasswordStringPolicy(partyType, entityPermission);

        if(partyTypePasswordStringPolicy == null) {
            handleExecutionError(UnknownPartyTypePasswordStringPolicyException.class, eea, ExecutionErrors.UnknownPartyTypePasswordStringPolicy.name(),
                    partyType.getPartyTypeName());
        }

        return partyTypePasswordStringPolicy;
    }

    public PartyTypePasswordStringPolicy getPartyTypePasswordStringPolicy(final ExecutionErrorAccumulator eea, final PartyType partyType) {
        return getPartyTypePasswordStringPolicy(eea, partyType, EntityPermission.READ_ONLY);
    }

    public PartyTypePasswordStringPolicy getPartyTypePasswordStringPolicyForUpdate(final ExecutionErrorAccumulator eea, final PartyType partyType) {
        return getPartyTypePasswordStringPolicy(eea, partyType, EntityPermission.READ_WRITE);
    }

}
