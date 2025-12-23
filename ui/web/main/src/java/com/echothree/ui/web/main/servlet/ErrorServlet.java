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

package com.echothree.ui.web.main.servlet;

import com.echothree.view.client.web.WebConstants;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.Globals;

// Based on ideas from: http://livingtao.blogspot.com/2007/05/global-exception-handling-for-struts.html
public class ErrorServlet
        extends HttpServlet {
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            String stackTrace = null;

            // Check for Struts exception...
            var obj = request.getAttribute(Globals.EXCEPTION_KEY);

            // If not found, check for generic servlet exception...
            if(obj == null) {
                obj = request.getAttribute("javax.servlet.error.exception");
            }

            // If not found, check for generic JSP exception...
            if(obj == null) {
                obj = request.getAttribute("javax.servlet.jsp.jspException");
            }

            if((obj != null) && (obj instanceof Throwable)) {
                var sw = new StringWriter();

                ((Throwable)obj).printStackTrace(new PrintWriter(sw));

                stackTrace = sw.toString();
            }

            request.setAttribute(WebConstants.Attribute_STACK_TRACE, stackTrace);

            var errorUrl = getServletConfig().getInitParameter("errorUrl");
            var dispatcher = getServletContext().getRequestDispatcher(errorUrl);
            dispatcher.forward(request,response);
        } catch(Exception e) {
            // If another Exception is thrown, just dump a stacktrace.
            e.printStackTrace();
        }
    }
    
}
