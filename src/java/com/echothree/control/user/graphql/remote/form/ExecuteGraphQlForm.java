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

package com.echothree.control.user.graphql.remote.form;

import com.echothree.util.remote.form.BaseForm;

public interface ExecuteGraphQlForm
        extends BaseForm {
    
    String getQuery();
    void setQuery(String query);
    
    String getOperationName();
    void setOperationName(String operationName);

    String getJson();
    void setJson(String json);
    
    String getRemoteInet4Address();
    void setRemoteInet4Address(String remoteInet4Address);
    
}
