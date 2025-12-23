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
        <title><fmt:message key="pageTitle.leaves" /></title>
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
                <c:url var="leavesUrl" value="/action/HumanResources/Leave/Main">
                    <c:param name="PartyName" value="${leave.party.partyName}" />
                </c:url>
                <a href="${leavesUrl}"><fmt:message key="navigation.leaves" /></a> &gt;&gt;
                Edit
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <c:if test="${displayForm}">
                <html:form action="/HumanResources/Leave/Edit" method="POST" focus="startTime">
                    <table>
                        <tr>
                            <td align=right><fmt:message key="label.company" />:</td>
                            <td>
                                <html:select property="companyChoice">
                                    <html:optionsCollection property="companyChoices" />
                                </html:select> (*)
                                <et:validationErrors id="errorMessage" property="CompanyName">
                                    <p><c:out value="${errorMessage}" /></p>
                                </et:validationErrors>
                            </td>
                        </tr>
                        <tr>
                            <td align=right><fmt:message key="label.leaveType" />:</td>
                            <td>
                                <html:select property="leaveTypeChoice">
                                    <html:optionsCollection property="leaveTypeChoices" />
                                </html:select> (*)
                                <et:validationErrors id="errorMessage" property="LeaveTypeName">
                                    <p><c:out value="${errorMessage}" /></p>
                                </et:validationErrors>
                            </td>
                        </tr>
                        <tr>
                            <td align=right><fmt:message key="label.leaveReason" />:</td>
                            <td>
                                <html:select property="leaveReasonChoice">
                                    <html:optionsCollection property="leaveReasonChoices" />
                                </html:select> (*)
                                <et:validationErrors id="errorMessage" property="LeaveReasonName">
                                    <p><c:out value="${errorMessage}" /></p>
                                </et:validationErrors>
                            </td>
                        </tr>
                        <tr>
                            <td align=right><fmt:message key="label.startTime" />:</td>
                            <td>
                                <html:text property="startTime" size="50" maxlength="50" /> (*)
                                <et:validationErrors id="errorMessage" property="StartTime">
                                    <p><c:out value="${errorMessage}" /></p>
                                </et:validationErrors>
                            </td>
                        </tr>
                        <tr>
                            <td align=right><fmt:message key="label.endTime" />:</td>
                            <td>
                                <html:text property="endTime" size="50" maxlength="50" />
                                <et:validationErrors id="errorMessage" property="EndTime">
                                    <p><c:out value="${errorMessage}" /></p>
                                </et:validationErrors>
                            </td>
                        </tr>
                        <tr>
                            <td align=right><fmt:message key="label.totalTime" />:</td>
                            <td>
                                <html:text property="totalTime" size="12" maxlength="12" />
                                <html:select property="totalTimeUnitOfMeasureTypeChoice">
                                    <html:optionsCollection property="totalTimeUnitOfMeasureTypeChoices" />
                                </html:select>
                                <et:validationErrors id="errorMessage" property="TotalTime">
                                    <p><c:out value="${errorMessage}" /></p>
                                </et:validationErrors>
                                <et:validationErrors id="errorMessage" property="TotalTimeUnitOfMeasureTypeName">
                                    <p><c:out value="${errorMessage}" /></p>
                                </et:validationErrors>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <html:hidden property="partyName" />
                                <html:hidden property="leaveName" />
                            </td>
                            <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                        </tr>
                    </table>
                </html:form>
            </c:if>
        </div>
        <c:if test="${displayForm || commandResult.executionResult.hasLockErrors}">
            <jsp:include page="../../include/entityLock.jsp" />
        </c:if>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>