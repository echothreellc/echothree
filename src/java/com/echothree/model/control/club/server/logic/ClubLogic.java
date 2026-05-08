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

import com.echothree.model.control.club.common.exception.UnknownClubNameException;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class ClubLogic
        extends BaseLogic {

    protected ClubLogic() {
        super();
    }

    public static ClubLogic getInstance() {
        return CDI.current().select(ClubLogic.class).get();
    }

    @Inject
    ClubControl clubControl;

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

}
