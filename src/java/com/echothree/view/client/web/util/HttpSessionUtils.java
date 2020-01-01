// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.view.client.web.util;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.authentication.common.AuthenticationService;
import com.echothree.control.user.authentication.common.form.GetUserVisitForm;
import com.echothree.control.user.authentication.common.result.GetUserVisitResult;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.WebConstants;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    
    private static final HttpSessionUtils instance = new HttpSessionUtils();
    
    protected HttpSessionUtils() {
        super();
    }
    
    public static HttpSessionUtils getInstance() {
        return instance;
    }

    public static final int DEFAULT_MAX_INACTIVE_INTERVAL = 15 * 3600; // 15 minutes

    private Cookie GetUserKeyCookie(final HttpServletRequest request) {
        Cookie cookie = null;
        Cookie []cookies = request.getCookies();

        if(cookies != null) {
            for(int i = 0; i < cookies.length; i++) {
                if(cookies[i].getName().equals(WebConstants.Cookie_USER_KEY)) {
                    cookie = cookies[i];
                }
            }
        }

        return cookie;
    }

    public UserVisitPK setupUserVisit(final HttpServletRequest request, final HttpServletResponse response,
              boolean secureUserKey) {
        // Get the HttpSession, create if it doesn't exist yet.
        HttpSession httpSession = request.getSession(true);
        
        // Get the existing UserVisit, create if it doesn't exist yet.
        UserVisitPK userVisitPK = (UserVisitPK)httpSession.getAttribute(WebConstants.Session_USER_VISIT);
        if(userVisitPK == null) {
            // Set the session timeout.
            httpSession.setMaxInactiveInterval(DEFAULT_MAX_INACTIVE_INTERVAL);

            try {
                AuthenticationService authenticationService = AuthenticationUtil.getHome();
                GetUserVisitForm commandForm = AuthenticationUtil.getHome().getGetUserVisitForm();
                Cookie cookie = GetUserKeyCookie(request);

                if(cookie != null) {
                    commandForm.setUserKeyName(cookie.getValue());
                }
                
                CommandResult commandResult = authenticationService.getUserVisit(commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetUserVisitResult getUserVisitResult = (GetUserVisitResult)executionResult.getResult();
                
                String userKeyName = getUserVisitResult.getUserKeyName();
                if(cookie == null) {
                    cookie = new Cookie(WebConstants.Cookie_USER_KEY, userKeyName);
                } else {
                    cookie.setValue(userKeyName);
                }
                
                cookie.setPath("/");
                cookie.setMaxAge(365 * 24 * 60 * 60); // 1 Year
                if(secureUserKey) {
                    cookie.setSecure(true);
                }
                response.addCookie(cookie);
                
                userVisitPK = getUserVisitResult.getUserVisitPK();
                httpSession.setAttribute(WebConstants.Session_USER_VISIT, userVisitPK);
                httpSession.setAttribute("bindings.listener", new CustomBindingListener(userVisitPK));
            } catch (NamingException ne) {
                ne.printStackTrace();
                // nothing right now
            }
        }
        
        return userVisitPK;
    }
    
}
