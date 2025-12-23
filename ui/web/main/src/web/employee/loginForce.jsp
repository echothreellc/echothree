<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2026 Echo Three, LLC                                              -->
<!--                                                                                  -->
<!-- Licensed under the Apache License, Version 2.0 (the "License");                  -->
<!-- you may not use this file except in compliance with the License.                 -->
<!-- You may obtain a copy of the License at                                          -->
<!--                                                                                  -->
<!--     http://www.apache.org/licenses/LICENSE-2.0                                   -->
<!--                                                                                  -->
<!-- Unless required by applicable law or agreed to in writing, software              -->
<!-- distributed under the License is distributed on an "AS IS" BASIS,                -->
<!-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.         -->
<!-- See the License for the specific language governing permissions and              -->
<!-- limitations under the License.                                                   -->
<!--                                                                                  -->

<%@ include file="../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>Employee Login</title>
        <html:base/>
        <%@ include file="../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>Employee Login</h2>
        </div>
        <div id="Content">
            <et:executionWarnings id="warningMessage">
                <p class="executionWarnings"><c:out value="${warningMessage}" /></p><br />
            </et:executionWarnings>
            <p>You are required to change your password at this time:</p>
            <p><sslext:link page="/action/Employee/Password">Change your password...</sslext:link></p>
        </div>
        <jsp:include page="../include/copyright.jsp" />
    </body>
</html:html>