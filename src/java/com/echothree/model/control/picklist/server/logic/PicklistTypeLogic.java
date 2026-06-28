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

import com.echothree.model.control.picklist.common.exception.UnknownPicklistTypeNameException;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PicklistTypeLogic
        extends BaseLogic {

    @Inject
    PicklistControl picklistControl;

    protected PicklistTypeLogic() {
        super();
    }

    public PicklistType getPicklistTypeByName(final ExecutionErrorAccumulator eea, final String picklistTypeName) {
        var picklistType = picklistControl.getPicklistTypeByName(picklistTypeName);

        if(picklistType == null) {
            handleExecutionError(UnknownPicklistTypeNameException.class, eea, ExecutionErrors.UnknownPicklistTypeName.name(), picklistTypeName);
        }

        return picklistType;
    }

}
