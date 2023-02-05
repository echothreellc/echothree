// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.order.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.sequence.server.graphql.SequenceTypeObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowSecurityUtils;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.entity.OrderTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("order type object")
@GraphQLName("OrderType")
public class OrderTypeObject
        extends BaseEntityInstanceObject {
    
    private final OrderType orderType; // Always Present

    public OrderTypeObject(final OrderType orderType) {
        super(orderType.getPrimaryKey());

        this.orderType = orderType;
    }

    private OrderTypeDetail orderTypeDetail; // Optional, use getOrderTypeDetail()
    
    private OrderTypeDetail getOrderTypeDetail() {
        if(orderTypeDetail == null) {
            orderTypeDetail = orderType.getLastDetail();
        }
        
        return orderTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("order type name")
    @GraphQLNonNull
    public String getOrderTypeName() {
        return getOrderTypeDetail().getOrderTypeName();
    }

    @GraphQLField
    @GraphQLDescription("parent order type")
    public OrderTypeObject getParentOrderType(final DataFetchingEnvironment env) {
        var parentOrderType = getOrderTypeDetail().getParentOrderType();

        return parentOrderType == null ? null : (OrderSecurityUtils.getInstance().getHasOrderTypeAccess(env) ? new OrderTypeObject(parentOrderType) : null);
    }

    @GraphQLField
    @GraphQLDescription("order sequence type")
    public SequenceTypeObject getOrderSequenceType(final DataFetchingEnvironment env) {
        var orderSequenceType = getOrderTypeDetail().getOrderSequenceType();

        return orderSequenceType == null ? null : (SequenceSecurityUtils.getInstance().getHasSequenceTypeAccess(env) ? new SequenceTypeObject(orderSequenceType) : null);
    }

    @GraphQLField
    @GraphQLDescription("order workflow")
    public WorkflowObject getOrderWorkflow(final DataFetchingEnvironment env) {
        var orderWorkflow = getOrderTypeDetail().getOrderWorkflow();

        return orderWorkflow == null ? null : (WorkflowSecurityUtils.getInstance().getHasWorkflowAccess(env) ? new WorkflowObject(orderWorkflow) : null);
    }

//    @GraphQLField
//    @GraphQLDescription("order workflow entrance")
//    public WorkflowEntranceObject getOrderWorkflowEntrance(final DataFetchingEnvironment env) {
//        var orderWorkflowEntrance = getOrderTypeDetail().getOrderWorkflowEntrance();
//
//        return orderWorkflowEntrance == null ? null : (WorkflowSecurityUtils.getInstance().getHasWorkflowEntranceAccess(env) ? new WorkflowEntranceObject(orderWorkflowEntrance) : null);
//    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getOrderTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getOrderTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return orderTypeControl.getBestOrderTypeDescription(orderType, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

}
