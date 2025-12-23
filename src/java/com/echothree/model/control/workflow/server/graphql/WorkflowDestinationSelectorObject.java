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
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationSelector;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("workflow destination selector object")
@GraphQLName("WorkflowDestinationSelector")
public class WorkflowDestinationSelectorObject
        extends BaseObject {
    
    private final WorkflowDestinationSelector workflowDestinationSelector; // Always Present
    
    public WorkflowDestinationSelectorObject(WorkflowDestinationSelector workflowDestinationSelector) {
        this.workflowDestinationSelector = workflowDestinationSelector;
    }

    @GraphQLField
    @GraphQLDescription("workflow destination")
    public WorkflowDestinationObject getWorkflowDestination(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getHasWorkflowDestinationAccess(env) ? new WorkflowDestinationObject(workflowDestinationSelector.getWorkflowDestination()) : null;
    }

    @GraphQLField
    @GraphQLDescription("selector")
    @GraphQLNonNull
    public SelectorObject getSelector(final DataFetchingEnvironment env) {
        return SelectorSecurityUtils.getHasSelectorAccess(env) ? new SelectorObject(workflowDestinationSelector.getSelector()) : null;
    }

}
