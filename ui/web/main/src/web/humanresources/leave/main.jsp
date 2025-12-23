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
                <fmt:message key="navigation.leaves" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Company.Review:Event.List" />
            <et:hasSecurityRole securityRole="Company.Review" var="includeCompanyReviewUrl" />
            <c:url var="addUrl" value="/action/HumanResources/Leave/Add">
                <c:param name="PartyName" value="${party.partyName}" />
            </c:url>
            <p><a href="${addUrl}">Add Leave.</a></p>
            <display:table name="leaves.list" id="leave" class="displaytag" export="true" sort="list" requestURI="/action/HumanResources/Leave/Main">
                <display:setProperty name="export.csv.filename" value="Leaves.csv" />
                <display:setProperty name="export.excel.filename" value="Leaves.xls" />
                <display:setProperty name="export.pdf.filename" value="Leaves.pdf" />
                <display:setProperty name="export.rtf.filename" value="Leaves.rtf" />
                <display:setProperty name="export.xml.filename" value="Leaves.xml" />
                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="leaveName">
                    <c:url var="reviewUrl" value="/action/HumanResources/Leave/Review">
                        <c:param name="LeaveName" value="${leave.leaveName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${leave.leaveName}" /></a>
                </display:column>
                <display:column property="leaveName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.company" media="html" sortable="true" sortProperty="company.companyName">
                    <c:choose>
                        <c:when test="${includeCompanyReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Accounting/Company/Review">
                                <c:param name="CompanyName" value="${leave.company.companyName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${leave.company.partyGroup.name}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${leave.company.partyGroup.name}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="company.companyName" titleKey="columnTitle.company" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.startTime" media="html" sortable="true" sortProperty="unformattedStartTime">
                    <c:out value="${leave.startTime}" />
                </display:column>
                <display:column property="startTime" titleKey="columnTitle.startTime" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.endTime" media="html" sortable="true" sortProperty="unformattedEndTime">
                    <c:out value="${leave.endTime}" />
                </display:column>
                <display:column property="endTime" titleKey="columnTitle.endTime" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.leaveType" media="html" sortable="true" sortProperty="leaveType.leaveTypeName">
                    <c:if test="${leave.leaveType != null}">
                        <c:url var="reviewUrl" value="/action/HumanResources/LeaveType/Review">
                            <c:param name="LeaveTypeName" value="${leave.leaveType.leaveTypeName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${leave.leaveType.description}" /></a>
                    </c:if>
                </display:column>
                <display:column property="leaveType.leaveTypeName" titleKey="columnTitle.leaveType" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.leaveReason" media="html" sortable="true" sortProperty="leaveReason.leaveReasonName">
                    <c:if test="${leave.leaveReason != null}">
                        <c:url var="reviewUrl" value="/action/HumanResources/LeaveReason/Review">
                            <c:param name="LeaveReasonName" value="${leave.leaveReason.leaveReasonName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${leave.leaveReason.description}" /></a>
                    </c:if>
                </display:column>
                <display:column property="leaveReason.leaveReasonName" titleKey="columnTitle.leaveReason" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.totalTime" media="html" sortable="true" sortProperty="unformattedTotalTime">
                    <c:out value="${leave.totalTime}" />
                </display:column>
                <display:column property="totalTime" titleKey="columnTitle.totalTime" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.status" media="html" sortable="true" sortProperty="leave.leaveStatus.workflowStep.workflowStepName">
                    <c:choose>
                        <c:when test="${leave.leaveStatus.workflowStep.workflowStepName == 'TAKEN' || leave.leaveStatus.workflowStep.workflowStepName == 'NOT_TAKEN'}">
                            <c:out value="${leave.leaveStatus.workflowStep.description}" />
                        </c:when>
                        <c:otherwise>
                            <c:url var="statusUrl" value="/action/HumanResources/Leave/Status">
                                <c:param name="ForwardKey" value="Display" />
                                <c:param name="PartyName" value="${leave.party.partyName}" />
                                <c:param name="LeaveName" value="${leave.leaveName}" />
                            </c:url>
                            <a href="${statusUrl}"><c:out value="${leave.leaveStatus.workflowStep.description}" /></a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="leaveStatus.workflowStep.workflowStepName" titleKey="columnTitle.status" media="csv excel pdf rtf xml" />
                <display:column media="html">
                    <c:url var="editUrl" value="/action/HumanResources/Leave/Edit">
                        <c:param name="PartyName" value="${leave.party.partyName}" />
                        <c:param name="LeaveName" value="${leave.leaveName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/HumanResources/Leave/Delete">
                        <c:param name="PartyName" value="${leave.party.partyName}" />
                        <c:param name="LeaveName" value="${leave.leaveName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column media="html">
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${leave.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
