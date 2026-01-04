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

package com.echothree.model.control.inventory.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.sequence.server.graphql.SequenceTypeObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntranceObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowSecurityUtils;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory type object")
@GraphQLName("InventoryTransactionType")
public class InventoryTransactionTypeObject
        extends BaseEntityInstanceObject {
    
    private final InventoryTransactionType inventoryTransactionType; // Always Present

    public InventoryTransactionTypeObject(final InventoryTransactionType inventoryTransactionType) {
        super(inventoryTransactionType.getPrimaryKey());

        this.inventoryTransactionType = inventoryTransactionType;
    }

    private InventoryTransactionTypeDetail inventoryTransactionTypeDetail; // Optional, use getInventoryTransactionTypeDetail()
    
    private InventoryTransactionTypeDetail getInventoryTransactionTypeDetail() {
        if(inventoryTransactionTypeDetail == null) {
            inventoryTransactionTypeDetail = inventoryTransactionType.getLastDetail();
        }
        
        return inventoryTransactionTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("inventory type name")
    @GraphQLNonNull
    public String getInventoryTransactionTypeName() {
        return getInventoryTransactionTypeDetail().getInventoryTransactionTypeName();
    }

    @GraphQLField
    @GraphQLDescription("inventory sequence type")
    public SequenceTypeObject getInventoryTransactionSequenceType(final DataFetchingEnvironment env) {
        var inventoryTransactionSequenceType = getInventoryTransactionTypeDetail().getInventoryTransactionSequenceType();

        return inventoryTransactionSequenceType == null ? null : (SequenceSecurityUtils.getHasSequenceTypeAccess(env) ? new SequenceTypeObject(inventoryTransactionSequenceType) : null);
    }

    @GraphQLField
    @GraphQLDescription("inventory workflow")
    public WorkflowObject getInventoryTransactionWorkflow(final DataFetchingEnvironment env) {
        var inventoryTransactionWorkflow = getInventoryTransactionTypeDetail().getInventoryTransactionWorkflow();

        return inventoryTransactionWorkflow == null ? null : (WorkflowSecurityUtils.getHasWorkflowAccess(env) ? new WorkflowObject(inventoryTransactionWorkflow) : null);
    }

    @GraphQLField
    @GraphQLDescription("inventory workflow entrance")
    public WorkflowEntranceObject getInventoryTransactionWorkflowEntrance(final DataFetchingEnvironment env) {
        var inventoryTransactionWorkflowEntrance = getInventoryTransactionTypeDetail().getInventoryTransactionWorkflowEntrance();

        return inventoryTransactionWorkflowEntrance == null ? null : (WorkflowSecurityUtils.getHasWorkflowEntranceAccess(env) ? new WorkflowEntranceObject(inventoryTransactionWorkflowEntrance) : null);
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getInventoryTransactionTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort inventory")
    @GraphQLNonNull
    public int getSortOrder() {
        return getInventoryTransactionTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryTransactionTypeControl.getBestInventoryTransactionTypeDescription(inventoryTransactionType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }


}
