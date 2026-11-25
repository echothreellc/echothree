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

import com.echothree.control.user.party.common.spec.GenderUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.exception.UnknownGenderNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Gender;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GenderLogic
        extends BaseLogic {

    @Inject
    PartyControl partyControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected GenderLogic() {
        super();
    }

    public Gender getGenderByName(final ExecutionErrorAccumulator eea, final String genderName,
            final EntityPermission entityPermission) {
        var gender = partyControl.getGenderByName(genderName, entityPermission);

        if(gender == null) {
            handleExecutionError(UnknownGenderNameException.class, eea, ExecutionErrors.UnknownGenderName.name(), genderName);
        }

        return gender;
    }

    public Gender getGenderByName(final ExecutionErrorAccumulator eea, final String genderName) {
        return getGenderByName(eea, genderName, EntityPermission.READ_ONLY);
    }

    public Gender getGenderByNameForUpdate(final ExecutionErrorAccumulator eea, final String genderName) {
        return getGenderByName(eea, genderName, EntityPermission.READ_WRITE);
    }

    public Gender getGenderByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GenderUniversalSpec universalSpec, final EntityPermission entityPermission) {
        Gender gender = null;
        var genderName = universalSpec.getGenderName();
        var parameterCount = (genderName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(parameterCount == 1) {
            if(genderName == null) {
                var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.Gender.name());

                if(eea == null || !eea.hasExecutionErrors()) {
                    gender = partyControl.getGenderByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                gender = getGenderByName(eea, genderName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return gender;
    }

    public Gender getGenderByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GenderUniversalSpec universalSpec) {
        return getGenderByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Gender getGenderByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final GenderUniversalSpec universalSpec) {
        return getGenderByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
