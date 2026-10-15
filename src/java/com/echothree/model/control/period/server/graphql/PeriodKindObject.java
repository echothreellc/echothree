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

package com.echothree.model.control.period.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntranceObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowSecurityUtils;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodKindDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("period kind object")
@GraphQLName("PeriodKind")
public class PeriodKindObject
        extends BaseEntityInstanceObject {
    
    private final PeriodKind periodKind; // Always Present
    
    public PeriodKindObject(PeriodKind periodKind) {
        super(periodKind.getPrimaryKey());
        
        this.periodKind = periodKind;
    }

    private PeriodKindDetail periodKindDetail; // Optional, use getPeriodKindDetail()
    
    private PeriodKindDetail getPeriodKindDetail() {
        if(periodKindDetail == null) {
            periodKindDetail = periodKind.getLastDetail();
        }
        
        return periodKindDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("period kind name")
    @GraphQLNonNull
    public String getPeriodKindName() {
        return getPeriodKindDetail().getPeriodKindName();
    }

    @GraphQLField
    @GraphQLDescription("workflow entrance")
    public WorkflowEntranceObject getWorkflowEntrance(final DataFetchingEnvironment env) {
        if(WorkflowSecurityUtils.getHasWorkflowEntranceAccess(env)) {
            var workflowEntrance = getPeriodKindDetail().getWorkflowEntrance();

            return workflowEntrance == null ? null : new WorkflowEntranceObject(workflowEntrance);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPeriodKindDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPeriodKindDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var periodControl = Session.getModelController(PeriodControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return periodControl.getBestPeriodKindDescription(periodKind, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
