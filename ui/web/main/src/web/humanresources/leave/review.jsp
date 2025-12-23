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
        <title>
            <fmt:message key="pageTitle.leave">
                <fmt:param value="${leave.leaveName}" />
            </fmt:message>
        </title>
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
                <fmt:message key="navigation.leave">
                    <fmt:param value="${leave.leaveName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Employee.Review:Company.Review:LeaveStatus.Choices" />
            <et:hasSecurityRole securityRole="Employee.Review" var="includeEmployeeUrl" />
            <et:hasSecurityRole securityRole="Company.Review" var="includeCompanyUrl" />

            <p><font size="+2"><b><c:out value="${leave.leaveName}" /></b></font></p>
            <br />

            Employee:
            <c:choose>
                <c:when test="${includeEmployeeUrl}">
                    <c:url var="reviewUrl" value="/action/HumanResources/Employee/Review">
                        <c:param name="PartyName" value="${leave.party.partyName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${leave.party.person.personalTitle.description}" /> <c:out value="${leave.party.person.firstName}" />
                        <c:out value="${leave.party.person.middleName}" /> <c:out value="${leave.party.person.lastName}" />
                        <c:out value="${leave.party.person.nameSuffix.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${leave.party.person.personalTitle.description}" /> <c:out value="${leave.party.person.firstName}" />
                    <c:out value="${leave.party.person.middleName}" /> <c:out value="${leave.party.person.lastName}" />
                    <c:out value="${leave.party.person.nameSuffix.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Company:
            <c:choose>
                <c:when test="${includeCompanyUrl}">
                    <c:url var="reviewUrl" value="/action/Accounting/Company/Review">
                        <c:param name="CompanyName" value="${leave.company.companyName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${leave.company.partyGroup.name}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${leave.company.partyGroup.name}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />

            <fmt:message key="label.leaveType" />:
            <c:choose>
                <c:when test="${leave.leaveType != null}">
                    <c:url var="leaveTypeUrl" value="/action/HumanResources/LeaveType/Review">
                        <c:param name="LeaveTypeName" value="${leave.leaveType.leaveTypeName}" />
                    </c:url>
                    <a href="${leaveTypeUrl}"><c:out value="${leave.leaveType.description}" /></a>
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.leaveReason" />:
            <c:choose>
                <c:when test="${leave.leaveReason != null}">
                    <c:url var="leaveReasonUrl" value="/action/HumanResources/LeaveReason/Review">
                        <c:param name="LeaveReasonName" value="${leave.leaveReason.leaveReasonName}" />
                    </c:url>
                    <a href="${leaveReasonUrl}"><c:out value="${leave.leaveReason.description}" /></a>
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <br />

            <fmt:message key="label.startTime" />: <c:out value="${leave.startTime}" /><br />
            <fmt:message key="label.endTime" />:
            <c:choose>
                <c:when test="${leave.endTime != null}">
                    <c:out value="${leave.endTime}" />
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.totalTime" />:
            <c:choose>
                <c:when test="${leave.totalTime != null}">
                    <c:out value="${leave.totalTime}" />
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <br />

            Leave Status: <c:out value="${leave.leaveStatus.workflowStep.description}" />
            <et:hasSecurityRole securityRole="LeaveStatus.Choices">
                <c:url var="statusUrl" value="/action/HumanResources/Leave/Status">
                    <c:param name="ForwardKey" value="Review" />
                    <c:param name="LeaveName" value="${leave.leaveName}" />
                </c:url>
                <a href="${statusUrl}">Edit</a>
            </et:hasSecurityRole>
            <br />
            <br />

            <br />
            <c:set var="entityInstance" scope="request" value="${leave.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
