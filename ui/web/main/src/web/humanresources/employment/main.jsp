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
        <title><fmt:message key="pageTitle.employments" /></title>
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
                <fmt:message key="navigation.employments" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Company.Review:Event.List" />
            <et:hasSecurityRole securityRole="Company.Review" var="includeCompanyReviewUrl" />
            <c:url var="addUrl" value="/action/HumanResources/Employment/Add">
                <c:param name="PartyName" value="${employee.partyName}" />
            </c:url>
            <p><a href="${addUrl}">Add Employment.</a></p>
            <display:table name="employments.list" id="employment" class="displaytag" export="true" sort="list" requestURI="/action/HumanResources/Employment/Main">
                <display:setProperty name="export.csv.filename" value="Employments.csv" />
                <display:setProperty name="export.excel.filename" value="Employments.xls" />
                <display:setProperty name="export.pdf.filename" value="Employments.pdf" />
                <display:setProperty name="export.rtf.filename" value="Employments.rtf" />
                <display:setProperty name="export.xml.filename" value="Employments.xml" />
                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="employmentName">
                    <c:url var="reviewUrl" value="/action/HumanResources/Employment/Review">
                        <c:param name="EmploymentName" value="${employment.employmentName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${employment.employmentName}" /></a>
                </display:column>
                <display:column property="employmentName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.company" media="html" sortable="true" sortProperty="company.companyName">
                    <c:choose>
                        <c:when test="${includeCompanyReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Accounting/Company/Review">
                                <c:param name="CompanyName" value="${employment.company.companyName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${employment.company.partyGroup.name}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${employment.company.partyGroup.name}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="company.companyName" titleKey="columnTitle.company" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.startTime" media="html" sortable="true" sortProperty="unformattedStartTime">
                    <c:out value="${employment.startTime}" />
                </display:column>
                <display:column property="startTime" titleKey="columnTitle.startTime" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.endTime" media="html" sortable="true" sortProperty="unformattedEndTime">
                    <c:out value="${employment.endTime}" />
                </display:column>
                <display:column property="endTime" titleKey="columnTitle.endTime" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.terminationType" media="html" sortable="true" sortProperty="terminationType.terminationTypeName">
                    <c:if test="${employment.terminationType != null}">
                        <c:url var="reviewUrl" value="/action/HumanResources/TerminationType/Review">
                            <c:param name="TerminationTypeName" value="${employment.terminationType.terminationTypeName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${employment.terminationType.description}" /></a>
                    </c:if>
                </display:column>
                <display:column property="terminationType.terminationTypeName" titleKey="columnTitle.terminationType" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.terminationReason" media="html" sortable="true" sortProperty="terminationReason.terminationReasonName">
                    <c:if test="${employment.terminationReason != null}">
                        <c:url var="reviewUrl" value="/action/HumanResources/TerminationReason/Review">
                            <c:param name="TerminationReasonName" value="${employment.terminationReason.terminationReasonName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${employment.terminationReason.description}" /></a>
                    </c:if>
                </display:column>
                <display:column property="terminationReason.terminationReasonName" titleKey="columnTitle.terminationReason" media="csv excel pdf rtf xml" />
                <display:column media="html">
                    <c:url var="editUrl" value="/action/HumanResources/Employment/Edit">
                        <c:param name="PartyName" value="${employment.party.partyName}" />
                        <c:param name="EmploymentName" value="${employment.employmentName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/HumanResources/Employment/Delete">
                        <c:param name="PartyName" value="${employment.party.partyName}" />
                        <c:param name="EmploymentName" value="${employment.employmentName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column media="html">
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${employment.entityInstance.entityRef}" />
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
