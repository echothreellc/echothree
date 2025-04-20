// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.user.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.user.common.transfer.UserVisitGroupTransfer;
import com.echothree.model.control.user.common.workflow.UserVisitGroupStatusConstants;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.user.server.entity.UserVisitGroup;
import com.echothree.util.server.persistence.Session;

public class UserVisitGroupTransferCache
        extends BaseUserTransferCache<UserVisitGroup, UserVisitGroupTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    /** Creates a new instance of UserVisitGroupTransferCache */
    public UserVisitGroupTransferCache(UserVisit userVisit, UserControl userControl) {
        super(userVisit, userControl);

        setIncludeEntityInstance(true);
    }

    public UserVisitGroupTransfer getUserVisitGroupTransfer(UserVisitGroup userVisitGroup) {
        var userVisitGroupTransfer = get(userVisitGroup);

        if(userVisitGroupTransfer == null) {
            var userVisitGroupDetail = userVisitGroup.getLastDetail();
            var userVisitGroupName = userVisitGroupDetail.getUserVisitGroupName();

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(userVisitGroup.getPrimaryKey());
            var userVisitGroupStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    UserVisitGroupStatusConstants.Workflow_USER_VISIT_GROUP_STATUS, entityInstance);

            userVisitGroupTransfer = new UserVisitGroupTransfer(userVisitGroupName, userVisitGroupStatusTransfer);
            put(userVisitGroup, userVisitGroupTransfer);
        }

        return userVisitGroupTransfer;
    }
}