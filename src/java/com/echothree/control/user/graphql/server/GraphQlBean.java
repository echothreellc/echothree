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

package com.echothree.control.user.graphql.server;

import com.echothree.control.user.graphql.common.GraphQlRemote;
import com.echothree.control.user.graphql.common.form.*;
import com.echothree.control.user.graphql.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.concurrent.Future;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class GraphQlBean
        extends GraphQlFormsImpl
        implements GraphQlRemote, GraphQlLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "GraphQlBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   GraphQl
    // --------------------------------------------------------------------------------

    @Override
    @Asynchronous
    public Future<CommandResult> executeGraphQl(UserVisitPK userVisitPK, ExecuteGraphQlForm form) {
        return CDI.current().select(ExecuteGraphQlCommand.class).get().runAsync(userVisitPK, form);
    }

}
