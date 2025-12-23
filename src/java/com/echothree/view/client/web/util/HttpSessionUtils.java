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

package com.echothree.view.client.web.util;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.authentication.common.result.GetUserVisitResult;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.view.client.web.WebConstants;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpSessionUtils {

    private static final HttpSessionUtils instance = new HttpSessionUtils();
    
    protected HttpSessionUtils() {
        super();
    }
    
    public static HttpSessionUtils getInstance() {
        return instance;
    }

    protected static Log log = LogFactory.getLog(HttpSessionUtils.class);

    public static final int DEFAULT_MAX_INACTIVE_INTERVAL = 15 * 60; // 15 minutes

    private Cookie GetUserKeyCookie(final HttpServletRequest request) {
        final var cookies = request.getCookies();
        Cookie result = null;

        if(cookies != null) {
            for(var cookie : cookies) {
                if(cookie.getName().equals(WebConstants.Cookie_USER_KEY)) {
                    result = cookie;
                }
            }
        }

        return result;
    }

    public UserVisitPK setupUserVisit(final HttpServletRequest request, final HttpServletResponse response,
            final boolean secureUserKey) {
        // Get the HttpSession, create if it doesn't exist yet.
        final var httpSession = request.getSession(true);

        // Get the existing UserVisit, create if it doesn't exist yet.
        var userVisitPK = (UserVisitPK)httpSession.getAttribute(WebConstants.Session_USER_VISIT);
        if(userVisitPK == null) {
            // Set the session timeout.
            httpSession.setMaxInactiveInterval(DEFAULT_MAX_INACTIVE_INTERVAL);

            try {
                final var authenticationService = AuthenticationUtil.getHome();
                final var commandForm = AuthenticationUtil.getHome().getGetUserVisitForm();
                var cookie = GetUserKeyCookie(request);

                if(cookie != null) {
                    commandForm.setUserKeyName(cookie.getValue());
                }

                final var commandResult = authenticationService.getUserVisit(commandForm);
                final var executionResult = commandResult.getExecutionResult();
                final var getUserVisitResult = (GetUserVisitResult)executionResult.getResult();

                var userKeyName = getUserVisitResult.getUserKeyName();
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

                if(log.isDebugEnabled()) {
                    log.debug("HttpSessionUtils.setupUserVisit: new UserVisit created: " + userVisitPK.getEntityRef());
                }
            } catch (NamingException ne) {
                log.error("HttpSessionUtils.setupUserVisit encountered an Exception", ne);
            }
        }
        
        return userVisitPK;
    }
    
}
