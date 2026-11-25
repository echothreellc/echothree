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

package com.echothree.model.control.club.server.logic;

import com.echothree.control.user.club.common.spec.ClubUniversalSpec;
import com.echothree.model.control.club.common.exception.UnknownClubNameException;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ClubLogic
        extends BaseLogic {

    @Inject
    ClubControl clubControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected ClubLogic() {
        super();
    }

    public Club getClubByName(final ExecutionErrorAccumulator eea, final String clubName,
            final EntityPermission entityPermission) {
        var club = clubControl.getClubByName(clubName, entityPermission);

        if(club == null) {
            handleExecutionError(UnknownClubNameException.class, eea, ExecutionErrors.UnknownClubName.name(), clubName);
        }

        return club;
    }

    public Club getClubByName(final ExecutionErrorAccumulator eea, final String clubName) {
        return getClubByName(eea, clubName, EntityPermission.READ_ONLY);
    }

    public Club getClubByNameForUpdate(final ExecutionErrorAccumulator eea, final String clubName) {
        return getClubByName(eea, clubName, EntityPermission.READ_WRITE);
    }

    public Club getClubByUniversalSpec(final ExecutionErrorAccumulator eea, final ClubUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        Club club = null;
        var clubName = universalSpec.getClubName();
        var parameterCount = (clubName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(parameterCount == 1) {
            if(clubName == null) {
                var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.Club.name());

                if(eea == null || !eea.hasExecutionErrors()) {
                    club = clubControl.getClubByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                club = getClubByName(eea, clubName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return club;
    }

    public Club getClubByUniversalSpec(final ExecutionErrorAccumulator eea, final ClubUniversalSpec universalSpec) {
        return getClubByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Club getClubByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final ClubUniversalSpec universalSpec) {
        return getClubByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
