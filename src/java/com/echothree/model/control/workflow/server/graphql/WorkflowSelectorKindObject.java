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
import com.echothree.model.control.selector.server.graphql.SelectorKindObject;
import com.echothree.model.control.selector.server.graphql.SelectorSecurityUtils;
import com.echothree.model.data.workflow.server.entity.WorkflowSelectorKind;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("workflow selector kind object")
@GraphQLName("WorkflowSelectorKind")
public class WorkflowSelectorKindObject
        extends BaseObject {
    
    private final WorkflowSelectorKind workflowSelectorKind; // Always Present
    
    public WorkflowSelectorKindObject(WorkflowSelectorKind workflowSelectorKind) {
        this.workflowSelectorKind = workflowSelectorKind;
    }

    @GraphQLField
    @GraphQLDescription("workflow")
    public WorkflowObject getWorkflow(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getHasWorkflowAccess(env) ? new WorkflowObject(workflowSelectorKind.getWorkflow()) : null;
    }

    @GraphQLField
    @GraphQLDescription("selector kind")
    public SelectorKindObject getSelectorKind(final DataFetchingEnvironment env) {
        return SelectorSecurityUtils.getHasSelectorKindAccess(env) ? new SelectorKindObject(workflowSelectorKind.getSelectorKind()) : null;
    }

}
