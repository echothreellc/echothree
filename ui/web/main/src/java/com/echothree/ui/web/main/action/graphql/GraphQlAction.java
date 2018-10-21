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

package com.echothree.ui.web.main.action.graphql;

import com.echothree.ui.web.main.framework.MainBaseGraphQlAction;
import com.echothree.control.user.graphql.common.GraphQlUtil;
import com.echothree.control.user.graphql.remote.form.ExecuteGraphQlForm;
import com.echothree.control.user.graphql.remote.result.ExecuteGraphQlResult;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import static com.echothree.view.client.web.struts.BaseAction.getUserVisitPK;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import com.google.common.base.Charsets;
import com.google.common.net.MediaType;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/graphql",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    }
)
public class GraphQlAction
        extends MainBaseGraphQlAction {
        
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        String forwardKey = null;
        boolean wasGet = wasGet(request);
        boolean wasPost = wasGet ? false : wasPost(request);
        String origin = request.getHeader("Origin");
        
        // Handle the request as specified here: http://graphql.org/learn/serving-over-http/
        if(wasGet || wasPost) {
            ExecuteGraphQlForm commandForm = GraphQlUtil.getHome().getExecuteGraphQlForm();
            String query = request.getParameter(ParameterConstants.GRAPHQL_QUERY);
            String operationName = request.getParameter(ParameterConstants.GRAPHQL_OPERATION_NAME);
            String json = null;
            
            if(wasPost) {
                String contentType = request.getContentType(); 
                
                try {
                    MediaType mediaType = MediaType.parse(contentType);
                    
                    // Everything is UTF-8.
                    if(mediaType.equals(JSON) || mediaType.equals(MediaType.JSON_UTF_8)) {
                        json = request.getReader().lines().collect(Collectors.joining());
                    } else if(mediaType.equals(GRAPHQL) || mediaType.equals(GRAPHQL_UTF_8)) {
                        query = request.getReader().lines().collect(Collectors.joining());
                    } else {
                        // If they used a Content-Type that we don't recognize, they're 404 bound.
                        forwardKey = ForwardConstants.ERROR_404;
                    }
                } catch (IllegalArgumentException iae) {
                    forwardKey = ForwardConstants.ERROR_500;
                }
            }
            
            if(forwardKey == null) {
                commandForm.setQuery(query); 
                commandForm.setOperationName(operationName); 
                commandForm.setJson(json);
                commandForm.setRemoteInet4Address(request.getRemoteAddr());

                CommandResult commandResult = GraphQlUtil.getHome().executeGraphQl(getUserVisitPK(request), commandForm);

                if(!commandResult.hasErrors()) {
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    ExecuteGraphQlResult result = (ExecuteGraphQlResult)executionResult.getResult();
                    String graphQlExecutionResult = result.getExecutionResult();

                    response.setContentType(MediaType.JSON_UTF_8.toString());
                    response.addHeader("Access-Control-Allow-Origin", origin == null ? "*" : origin);
                    response.addHeader("Access-Control-Allow-Credentials", "true");
                    response.getOutputStream().write(graphQlExecutionResult.getBytes(Charsets.UTF_8));
                } else {
                    forwardKey = ForwardConstants.ERROR_500;
                }
            }
        } else if(wasOptions(request)) {
            response.addHeader("Access-Control-Allow-Origin", origin == null ? "*" : origin);
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Origin");
            response.addHeader("Access-Control-Max-Age", "86400");
        } else {
            // If they used a method that we don't recognize, they're 404 bound.
            forwardKey = ForwardConstants.ERROR_404;
        }

        // Tell Struts that we are done with the response.
        return forwardKey == null ? null : mapping.findForward(forwardKey);
    }

}
