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

package com.echothree.model.control.workflow.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.selector.server.graphql.SelectorObject;
import com.echothree.model.control.selector.server.graphql.SelectorSecurityUtils;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSelector;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("workflow entrance selector object")
@GraphQLName("WorkflowEntranceSelector")
public class WorkflowEntranceSelectorObject
        extends BaseObject {
    
    private final WorkflowEntranceSelector workflowEntranceSelector; // Always Present
    
    public WorkflowEntranceSelectorObject(WorkflowEntranceSelector workflowEntranceSelector) {
        this.workflowEntranceSelector = workflowEntranceSelector;
    }

    @GraphQLField
    @GraphQLDescription("workflow entrance")
    public WorkflowEntranceObject getWorkflowEntrance(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getHasWorkflowEntranceAccess(env) ? new WorkflowEntranceObject(workflowEntranceSelector.getWorkflowEntrance()) : null;
    }

    @GraphQLField
    @GraphQLDescription("selector")
    @GraphQLNonNull
    public SelectorObject getSelector(final DataFetchingEnvironment env) {
        return SelectorSecurityUtils.getHasSelectorAccess(env) ? new SelectorObject(workflowEntranceSelector.getSelector()) : null;
    }

}
