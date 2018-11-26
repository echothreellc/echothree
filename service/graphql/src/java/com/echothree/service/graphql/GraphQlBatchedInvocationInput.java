// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.service.graphql;

import com.echothree.control.user.graphql.common.form.ExecuteGraphQlForm;

import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphQlBatchedInvocationInput
        extends GraphQlInvocationInput {

    private final List<GraphQlRequest> requests;

    public GraphQlBatchedInvocationInput(List<GraphQlRequest> requests) {
        this.requests = Collections.unmodifiableList(requests);
    }

    public List<ExecuteGraphQlForm> getExecuteGraphQlForms()
            throws NamingException {
        List<ExecuteGraphQlForm> list = new ArrayList<>();

        for (GraphQlRequest request : requests) {
            ExecuteGraphQlForm executeGraphQlForm = createExecuteGraphQlForm(request);
            list.add(executeGraphQlForm);
        }

        return list;
    }
}
