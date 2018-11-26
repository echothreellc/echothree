// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
// Copyright 2016 Yurii Rashkovskii and Contributors
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

public class GraphQlSingleInvocationInput
        extends GraphQlInvocationInput {

    private final GraphQlRequest request;

    public GraphQlSingleInvocationInput(GraphQlRequest request) {
        this.request = request;
    }

    public ExecuteGraphQlForm getExecuteGraphQlForm()
            throws NamingException {
        return createExecuteGraphQlForm(request);
    }
}
