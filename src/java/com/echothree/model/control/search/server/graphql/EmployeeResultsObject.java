// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.search.server.graphql;

import com.echothree.control.user.search.common.form.GetEmployeeResultsForm;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.employee.server.graphql.EmployeeObject;
import com.echothree.model.control.graphql.server.graphql.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.graphql.count.EmptyCountedObjects;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("employee results object")
@GraphQLName("EmployeeResults")
public class EmployeeResultsObject
        extends BaseResultsObject<GetEmployeeResultsForm> {

    public EmployeeResultsObject() {
        super(SearchConstants.SearchKind_EMPLOYEE);
    }

    @GraphQLField
    @GraphQLDescription("employees")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<EmployeeObject> getEmployees(final DataFetchingEnvironment env) {
        var userVisitSearch = getUserVisitSearch(env);

        if(userVisitSearch == null) {
            return EmptyCountedObjects.emptyCountedObjects();
        } else {
            var totalCount = getTotalCount(env);

            try(var objectLimiter = new ObjectLimiter(env, SearchResultConstants.ENTITY_TYPE_NAME, totalCount)) {
                var employeeControl = Session.getModelController(EmployeeControl.class);
                var employees = employeeControl.getEmployeeObjectsFromUserVisitSearch(userVisitSearch);

                return new CountedObjects<>(objectLimiter, employees);
            }
        }
    }
    
}
