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

package com.echothree.view.client.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

// Sample code posted at:
//   http://seamframework.org/Documentation/RemovingJSESSIONIDFromYourURLsAndFixingScache
// No license appeared to be attached to the sample code.

/** Example usage:
 *    <filter>
 *        <filter-name>JSessionIdFilter</filter-name>
 *        <filter-class>com.echothree.view.client.web.filter.JSessionIdFilter</filter-class>
 *    </filter>
 *    <filter-mapping>
 *        <filter-name>JSessionIdFilter</filter-name>
 *        <url-pattern>/*</url-pattern>
 *    </filter-mapping>
 */
public class JSessionIdFilter
        implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        // No initialization required.
    }
    
    @Override
    public void destroy() {
        // No cleanup required.
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if(!(req instanceof HttpServletRequest)) {
            chain.doFilter(req, res);
            
            return;
        }

        var request = (HttpServletRequest) req;
        var response = (HttpServletResponse) res;

        // Redirect requests with JSESSIONID in URL to clean version (old links 
        // bookmarked/stored by bots). This is ONLY triggered if the request did 
        // not also contain a JSESSIONID cookie! Which should be fine for bots...
        if(request.isRequestedSessionIdFromURL()) {
            var url = request.getRequestURL()
                         .append(request.getQueryString() != null ? "?"+request.getQueryString() : "")
                         .toString();
            
            // TODO: The url is clean, at least in Tomcat, which strips out 
            // the JSESSIONID path parameter automatically (Jetty does not?!)
            response.setHeader("Location", url);
            response.sendError(HttpServletResponse.SC_MOVED_PERMANENTLY);
            
            return;
        }

        // Prevent rendering of JSESSIONID in URLs for all outgoing links
        var wrappedResponse = new HttpServletResponseWrapper(response) {
            
            @Override
            public String encodeRedirectUrl(String url) {
                return url;
            }

            @Override
            public String encodeRedirectURL(String url) {
                return url;
            }

            @Override
            public String encodeUrl(String url) {
                return url;
            }

            @Override
            public String encodeURL(String url) {
                return url;
            }
                
        };
        
        chain.doFilter(req, wrappedResponse);
    }
    
}
