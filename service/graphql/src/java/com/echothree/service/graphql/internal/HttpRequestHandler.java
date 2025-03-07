// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.service.graphql.internal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.function.BiConsumer;

public interface HttpRequestHandler
        extends BiConsumer<HttpServletRequest, HttpServletResponse> {

    @Override
    default void accept(HttpServletRequest request, HttpServletResponse response) {
        try {
            handle(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void handle(HttpServletRequest request, HttpServletResponse response)
            throws Exception;
}
