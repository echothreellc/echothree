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
        <title>Employee Password</title>
        <html:base/>
        <%@ include file="../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>Employee Password</h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Employee/Password" method="POST" focus="oldPassword">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.oldPassword" />:</td>
                        <td>
                            <html:password property="oldPassword" size="20" maxlength="40" /> (*)
                            <et:validationErrors id="errorMessage" property="OldPassword">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.newPassword1" />:</td>
                        <td>
                            <html:password property="newPassword1" size="20" maxlength="40" /> (*)
                            <et:validationErrors id="errorMessage" property="NewPassword1">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.newPassword2" />:</td>
                        <td>
                            <html:password property="newPassword2" size="20" maxlength="40" /> (*)
                            <et:validationErrors id="errorMessage" property="NewPassword2">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
            <jsp:include page="../include/passwordRequirements.jsp" />
        </div>
        <jsp:include page="../include/userSession.jsp" />
        <jsp:include page="../include/copyright.jsp" />
    </body>
</html:html>