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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.core.common.workflow.EventGroupStatusConstants;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.core.server.entity.EventGroup;
import com.echothree.model.data.core.server.entity.EventGroupDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("event group object")
@GraphQLName("EventGroup")
public class EventGroupObject
        extends BaseEntityInstanceObject {
    
    private final EventGroup eventGroup; // Always Present
    
    public EventGroupObject(EventGroup eventGroup) {
        super(eventGroup.getPrimaryKey());
        
        this.eventGroup = eventGroup;
    }

    private EventGroupDetail eventGroupDetail; // Optional, use getEventGroupDetail()
    
    private EventGroupDetail getEventGroupDetail() {
        if(eventGroupDetail == null) {
            eventGroupDetail = eventGroup.getLastDetail();
        }
        
        return eventGroupDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("event group name")
    @GraphQLNonNull
    public String getEventGroupName() {
        return getEventGroupDetail().getEventGroupName();
    }

    @GraphQLField
    @GraphQLDescription("event group status")
    public WorkflowEntityStatusObject getEventGroupStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, EventGroupStatusConstants.Workflow_EVENT_GROUP_STATUS);
    }

}
