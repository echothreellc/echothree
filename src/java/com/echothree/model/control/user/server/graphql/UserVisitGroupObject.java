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

package com.echothree.model.control.user.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.common.workflow.UserVisitGroupStatusConstants;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.user.common.UserVisitConstants;
import com.echothree.model.data.user.server.entity.UserVisitGroup;
import com.echothree.model.data.user.server.entity.UserVisitGroupDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("user visit group object")
@GraphQLName("UserVisitGroup")
public class UserVisitGroupObject
        extends BaseEntityInstanceObject {
    
    private final UserVisitGroup userVisitGroup; // Always Present
    
    public UserVisitGroupObject(UserVisitGroup userVisitGroup) {
        super(userVisitGroup.getPrimaryKey());
        
        this.userVisitGroup = userVisitGroup;
    }

    private UserVisitGroupDetail userVisitGroupDetail; // Optional, use getUserVisitGroupDetail()
    
    private UserVisitGroupDetail getUserVisitGroupDetail() {
        if(userVisitGroupDetail == null) {
            userVisitGroupDetail = userVisitGroup.getLastDetail();
        }
        
        return userVisitGroupDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("user visit group name")
    @GraphQLNonNull
    public String getUserVisitGroupName() {
        return getUserVisitGroupDetail().getUserVisitGroupName();
    }

    @GraphQLField
    @GraphQLDescription("user visit group status")
    public WorkflowEntityStatusObject getUserVisitGroupStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, UserVisitGroupStatusConstants.Workflow_USER_VISIT_GROUP_STATUS);
    }

    @GraphQLField
    @GraphQLDescription("user visits")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<UserVisitObject> getUserVisits(final DataFetchingEnvironment env) {
//        if(UserSecurityUtils.getHasUserVisitsAccess(env)) {
            var userControl = Session.getModelController(UserControl.class);
            var totalCount = userControl.countUserVisitsByUserVisitGroup(userVisitGroup);

            try(var objectLimiter = new ObjectLimiter(env, UserVisitConstants.COMPONENT_VENDOR_NAME, UserVisitConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = userControl.getUserVisitsByUserVisitGroup(userVisitGroup);
                var userVisits = entities.stream().map(UserVisitObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, userVisits);
            }
//        } else {
//            return Connections.emptyConnection();
//        }
    }

}
