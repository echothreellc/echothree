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
        <title><fmt:message key="pageTitle.partyTrainingClasses" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <c:choose>
                    <c:when test="${party == null}">
                        <a href="<c:url value="/action/HumanResources/TrainingClass/Main" />"><fmt:message key="navigation.trainingClasses" /></a>
                    </c:when>
                    <c:when test="${trainingClass == null}">
                        <a href="<c:url value="/action/HumanResources/Employee/Main" />"><fmt:message key="navigation.employees" /></a> &gt;&gt;
                        <et:countEmployeeResults searchTypeName="HUMAN_RESOURCES" countVar="employeeResultsCount" commandResultVar="countEmployeeResultsCommandResult" logErrors="false" />
                        <c:if test="${employeeResultsCount > 0}">
                            <a href="<c:url value="/action/HumanResources/Employee/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                        </c:if>
                        <c:url var="reviewUrl" value="/action/HumanResources/Employee/Review">
                            <c:param name="EmployeeName" value="${employee.employeeName}" />
                        </c:url>
                        <a href="${reviewUrl}">Review (<c:out value="${employee.employeeName}" />)</a> &gt;&gt;
                        <fmt:message key="navigation.trainingClasses" />
                    </c:when>
                </c:choose>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="TrainingClass.Review:PartyTrainingClassStatus.Choices:PartyTrainingClass.Create:PartyTrainingClass.Edit:PartyTrainingClass.Delete:PartyTrainingClass.Review:Event.List" />
            <et:hasSecurityRole securityRole="PartyTrainingClassStatus.Choices" var="includePartyTrainingClassStatus" />
            <et:hasSecurityRole securityRole="PartyTrainingClass.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="Employee.Review" var="includeEmployeeReviewUrl" />
            <et:hasSecurityRole securityRole="TrainingClass.Review" var="includeTrainingClassReviewUrl" />
            <et:hasSecurityRole securityRole="PartyTrainingClass.Create">
                <c:url var="addUrl" value="/action/HumanResources/PartyTrainingClass/Add">
                    <c:param name="PartyName" value="${party.partyName}" />
                </c:url>
                <p><a href="${addUrl}">Add Training Class.</a></p>
            </et:hasSecurityRole>
            <display:table name="partyTrainingClasses" id="partyTrainingClass" class="displaytag" export="true" sort="list" requestURI="/action/HumanResources/PartyTrainingClass/Main">
                <display:setProperty name="export.csv.filename" value="PartyTrainingClasses.csv" />
                <display:setProperty name="export.excel.filename" value="PartyTrainingClasses.xls" />
                <display:setProperty name="export.pdf.filename" value="PartyTrainingClasses.pdf" />
                <display:setProperty name="export.rtf.filename" value="PartyTrainingClasses.rtf" />
                <display:setProperty name="export.xml.filename" value="PartyTrainingClasses.xml" />
                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="partyTrainingClassName">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/HumanResources/PartyTrainingClass/Review">
                                <c:param name="PartyTrainingClassName" value="${partyTrainingClass.partyTrainingClassName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${partyTrainingClass.partyTrainingClassName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${partyTrainingClass.partyTrainingClassName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="partyTrainingClassName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                <c:choose>
                    <c:when test="${party == null}">
                        <display:column titleKey="columnTitle.employee" media="html" sortable="true" sortProperty="party.description">
                            <c:choose>
                                <c:when test="${includeEmployeeReviewUrl}">
                                    <c:url var="employeeReviewUrl" value="/action/HumanResources/Employee/Review">
                                        <c:param name="PartyName" value="${partyTrainingClass.party.partyName}" />
                                    </c:url>
                                    <a href="${employeeReviewUrl}"><c:out value="${partyTrainingClass.party.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${partyTrainingClass.party.description}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column property="party.description" titleKey="columnTitle.employee" media="csv excel pdf rtf xml" />
                    </c:when>
                    <c:when test="${trainingClass == null}">
                        <display:column titleKey="columnTitle.trainingClass" media="html" sortable="true" sortProperty="trainingClass.description">
                            <c:choose>
                                <c:when test="${includeTrainingClassReviewUrl}">
                                    <c:url var="trainingClassReviewUrl" value="/action/HumanResources/TrainingClass/Review">
                                        <c:param name="TrainingClassName" value="${partyTrainingClass.trainingClass.trainingClassName}" />
                                    </c:url>
                                    <a href="${trainingClassReviewUrl}"><c:out value="${partyTrainingClass.trainingClass.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${partyTrainingClass.trainingClass.description}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column property="trainingClass.description" titleKey="columnTitle.trainingClass" media="csv excel pdf rtf xml" />
                    </c:when>
                </c:choose>
                <display:column titleKey="columnTitle.completedTime" media="html" sortable="true" sortProperty="unformattedCompletedTime">
                    <c:out value="${partyTrainingClass.completedTime}" />
                </display:column>
                <display:column property="completedTime" titleKey="columnTitle.completedTime" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.validUntilTime" media="html" sortable="true" sortProperty="unformattedValidUntilTime">
                    <c:out value="${partyTrainingClass.validUntilTime}" />
                </display:column>
                <display:column property="validUntilTime" titleKey="columnTitle.validUntilTime" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.status" media="html" sortable="true" sortProperty="partyTrainingClassStatus.workflowStep.description">
                    <c:choose>
                        <c:when test="${includePartyTrainingClassStatus}">
                            <c:url var="statusUrl" value="/action/HumanResources/PartyTrainingClass/Status">
                                <c:param name="PartyName" value="${partyTrainingClass.party.partyName}" />
                                <c:param name="PartyTrainingClassName" value="${partyTrainingClass.partyTrainingClassName}" />
                            </c:url>
                            <a href="${statusUrl}"><c:out value="${partyTrainingClass.partyTrainingClassStatus.workflowStep.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${partyTrainingClass.partyTrainingClassStatus.workflowStep.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="partyTrainingClassStatus.workflowStep.description" titleKey="columnTitle.status" media="csv excel pdf rtf xml" />
                <et:hasSecurityRole securityRoles="PartyTrainingClass.Edit:TrainingClass.Delete">
                    <display:column media="html">
                        <et:hasSecurityRole securityRole="PartyTrainingClass.Edit">
                            <c:url var="editUrl" value="/action/HumanResources/PartyTrainingClass/Edit">
                                <c:param name="PartyName" value="${partyTrainingClass.party.partyName}" />
                                <c:param name="PartyTrainingClassName" value="${partyTrainingClass.partyTrainingClassName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="PartyTrainingClass.Delete">
                            <c:url var="deleteUrl" value="/action/HumanResources/PartyTrainingClass/Delete">
                                <c:param name="PartyName" value="${partyTrainingClass.party.partyName}" />
                                <c:param name="PartyTrainingClassName" value="${partyTrainingClass.partyTrainingClassName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column media="html">
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${partyTrainingClass.entityInstance.entityRef}" />
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
