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

package com.echothree.model.control.user.server.graphql;

import com.echothree.model.data.user.server.entity.UserLogin;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

@GraphQLDescription("user login object")
@GraphQLName("UserLogin")
public class UserLoginObject {
    
    private final UserLogin userLogin; // Always Present

    public UserLoginObject(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    @GraphQLField
    @GraphQLDescription("username")
    public String getUsername() {
        return userLogin == null ? null : userLogin.getUsername();
    }
    
}
