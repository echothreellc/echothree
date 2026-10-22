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

import com.echothree.model.control.accounting.common.workflow.FiscalPeriodStatusConstants;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.period.server.entity.Period;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("fiscal period object")
@GraphQLName("FiscalPeriod")
public class FiscalPeriodObject
        extends PeriodObject
        implements PeriodInterface {

    public FiscalPeriodObject(Period period) {
        super(period);
    }

    @GraphQLField
    @GraphQLDescription("fiscal period status")
    public WorkflowEntityStatusObject getStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, period.getPrimaryKey(), FiscalPeriodStatusConstants.Workflow_FISCAL_PERIOD_STATUS);
    }

}
