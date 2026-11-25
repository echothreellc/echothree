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

}
