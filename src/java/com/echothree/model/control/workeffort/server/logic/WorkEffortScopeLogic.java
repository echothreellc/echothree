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

package com.echothree.model.control.workeffort.server.logic;

import com.echothree.model.control.workeffort.common.exception.UnknownWorkEffortScopeNameException;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class WorkEffortScopeLogic
        extends BaseLogic {

    @Inject
    WorkEffortControl workEffortControl;

    @Inject
    WorkEffortTypeLogic workEffortTypeLogic;

    protected WorkEffortScopeLogic() {
        super();
    }

    public WorkEffortScope getWorkEffortScopeByName(final ExecutionErrorAccumulator eea, final WorkEffortType workEffortType,
            final String workEffortScopeName, final EntityPermission entityPermission) {
        var workEffortScope = workEffortControl.getWorkEffortScopeByName(workEffortType, workEffortScopeName, entityPermission);

        if(workEffortScope == null) {
            handleExecutionError(UnknownWorkEffortScopeNameException.class, eea, ExecutionErrors.UnknownWorkEffortScopeName.name(),
                    workEffortType.getLastDetail().getWorkEffortTypeName(), workEffortScopeName);
        }

        return workEffortScope;
    }

    public WorkEffortScope getWorkEffortScopeByName(final ExecutionErrorAccumulator eea, final WorkEffortType workEffortType,
            final String workEffortScopeName) {
        return getWorkEffortScopeByName(eea, workEffortType, workEffortScopeName, EntityPermission.READ_ONLY);
    }

    public WorkEffortScope getWorkEffortScopeByNameForUpdate(final ExecutionErrorAccumulator eea, final WorkEffortType workEffortType,
            final String workEffortScopeName) {
        return getWorkEffortScopeByName(eea, workEffortType, workEffortScopeName, EntityPermission.READ_WRITE);
    }

    public WorkEffortScope getWorkEffortScopeByName(final ExecutionErrorAccumulator eea, final String workEffortTypeName,
            final String workEffortScopeName, final EntityPermission entityPermission) {
        var workEffortType = workEffortTypeLogic.getWorkEffortTypeByName(eea, workEffortTypeName);
        WorkEffortScope workEffortScope = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            workEffortScope = getWorkEffortScopeByName(eea, workEffortType, workEffortScopeName, entityPermission);
        }

        return workEffortScope;
    }

    public WorkEffortScope getWorkEffortScopeByName(final ExecutionErrorAccumulator eea, final String workEffortTypeName,
            final String workEffortScopeName) {
        return getWorkEffortScopeByName(eea, workEffortTypeName, workEffortScopeName, EntityPermission.READ_ONLY);
    }

    public WorkEffortScope getWorkEffortScopeByNameForUpdate(final ExecutionErrorAccumulator eea, final String workEffortTypeName,
            final String workEffortScopeName) {
        return getWorkEffortScopeByName(eea, workEffortTypeName, workEffortScopeName, EntityPermission.READ_WRITE);
    }

}
