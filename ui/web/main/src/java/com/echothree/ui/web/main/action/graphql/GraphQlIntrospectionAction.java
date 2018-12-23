// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.graphql;

import com.echothree.ui.web.main.framework.MainBaseGraphQlAction;
import com.echothree.control.user.graphql.common.GraphQlUtil;
import com.echothree.control.user.graphql.common.form.ExecuteGraphQlForm;
import com.echothree.control.user.graphql.common.result.ExecuteGraphQlResult;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import com.google.common.base.Charsets;
import com.google.common.net.MediaType;
import graphql.introspection.IntrospectionQuery;
import java.util.concurrent.Future;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/graphql/schema.json",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    }
)
public class GraphQlIntrospectionAction
        extends MainBaseGraphQlAction {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        String forwardKey = null;
        boolean wasGet = wasGet(request);
        
        if(wasGet) {
            ExecuteGraphQlForm commandForm = GraphQlUtil.getHome().getExecuteGraphQlForm();
            
            commandForm.setQuery(IntrospectionQuery.INTROSPECTION_QUERY); 
            commandForm.setRemoteInet4Address(request.getRemoteAddr());

            Future<CommandResult> futureCommandResult = GraphQlUtil.getHome().executeGraphQl(getUserVisitPK(request), commandForm);

            CommandResult commandResult = futureCommandResult.get();
            if(!commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                ExecuteGraphQlResult result = (ExecuteGraphQlResult)executionResult.getResult();

                response.setContentType(MediaType.JSON_UTF_8.toString());
                response.getOutputStream().write(result.getExecutionResult().getBytes(Charsets.UTF_8));
            } else {
                forwardKey = ForwardConstants.ERROR_500;
            }
        } else {
            // If they used a method that we don't recognize, they're 404 bound.
            forwardKey = ForwardConstants.ERROR_404;
        }

        // Tell Struts that we are done with the response.
        return forwardKey == null ? null : mapping.findForward(forwardKey);
    }

}
