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

<%@ include file="../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title><fmt:message key="pageTitle.employees" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Employee/Main" />"><fmt:message key="navigation.employees" /></a> &gt;&gt;
                <et:countEmployeeResults searchTypeName="HUMAN_RESOURCES" countVar="employeeResultsCount" commandResultVar="countEmployeeResultsCommandResult" logErrors="false" />
                <c:if test="${employeeResultsCount > 0}">
                    <a href="<c:url value="/action/HumanResources/Employee/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/HumanResources/Employee/Review">
                    <c:param name="EmployeeName" value="${employee.employeeName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${employee.employeeName}" />)</a> &gt;&gt;
                Password
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/HumanResources/Employee/Password" method="POST" focus="newPassword1">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.newPassword1" />:</td>
                        <td>
                            <html:password property="newPassword1" size="20" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="NewPassword1">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.newPassword2" />:</td>
                        <td>
                            <html:password property="newPassword2" size="20" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="NewPassword2">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="returnUrl" />
                            <html:hidden property="employeeName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
            <jsp:include page="../../include/passwordRequirements.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>