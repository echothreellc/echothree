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

package com.echothree.model.control.picklist.server.logic;

import com.echothree.model.control.picklist.common.exception.UnknownPicklistNameException;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.data.picklist.server.entity.Picklist;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PicklistLogic
        extends BaseLogic {

    @Inject
    PicklistControl picklistControl;

    @Inject
    PicklistTypeLogic picklistTypeLogic;

    protected PicklistLogic() {
        super();
    }

    public Picklist getPicklistByName(final ExecutionErrorAccumulator eea, final PicklistType picklistType, final String picklistName,
            final EntityPermission entityPermission) {
        var picklist = picklistControl.getPicklistByName(picklistType, picklistName, entityPermission);

        if(picklist == null) {
            handleExecutionError(UnknownPicklistNameException.class, eea, ExecutionErrors.UnknownPicklistName.name(),
                    picklistType.getLastDetail().getPicklistTypeName(), picklistName);
        }

        return picklist;
    }

    public Picklist getPicklistByName(final ExecutionErrorAccumulator eea, final PicklistType picklistType, final String picklistName) {
        return getPicklistByName(eea, picklistType, picklistName, EntityPermission.READ_ONLY);
    }

    public Picklist getPicklistByNameForUpdate(final ExecutionErrorAccumulator eea, final PicklistType picklistType, final String picklistName) {
        return getPicklistByName(eea, picklistType, picklistName, EntityPermission.READ_WRITE);
    }

    public Picklist getPicklistByName(final ExecutionErrorAccumulator eea, final String picklistTypeName, final String picklistName,
            final EntityPermission entityPermission) {
        var picklistType = picklistTypeLogic.getPicklistTypeByName(eea, picklistTypeName);
        Picklist picklist = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            picklist = getPicklistByName(eea, picklistType, picklistName, entityPermission);
        }

        return picklist;
    }

    public Picklist getPicklistByName(final ExecutionErrorAccumulator eea, final String picklistTypeName, final String picklistName) {
        return getPicklistByName(eea, picklistTypeName, picklistName, EntityPermission.READ_ONLY);
    }

    public Picklist getPicklistByNameForUpdate(final ExecutionErrorAccumulator eea, final String picklistTypeName, final String picklistName) {
        return getPicklistByName(eea, picklistTypeName, picklistName, EntityPermission.READ_WRITE);
    }

}
