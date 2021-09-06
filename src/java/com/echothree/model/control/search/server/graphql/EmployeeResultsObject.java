// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.Collections;
import java.util.List;

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
    public List<EmployeeResultObject> getEmployees(final DataFetchingEnvironment env) {
        List<EmployeeResultObject> objects = null;
        var userVisitSearch = getUserVisitSearch(env);

        if(userVisitSearch != null) {
            var employeeControl = Session.getModelController(EmployeeControl.class);

            objects = employeeControl.getEmployeeResultObjects(userVisitSearch);
        }
        
        return objects == null ? Collections.emptyList() : objects;
    }
    
}
