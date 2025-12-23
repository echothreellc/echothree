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
            <fmt:message key="pageTitle.partyTrainingClass">
                <fmt:param value="${partyTrainingClass.partyTrainingClassName}" />
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
                <c:url var="partyTrainingClassesUrl" value="/action/HumanResources/PartyTrainingClass/Main">
                    <c:param name="PartyName" value="${partyTrainingClass.party.partyName}" />
                </c:url>
                <a href="${partyTrainingClassesUrl}"><fmt:message key="navigation.partyTrainingClasses" /></a> &gt;&gt;
                <fmt:message key="navigation.partyTrainingClass">
                    <fmt:param value="${partyTrainingClass.partyTrainingClassName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="TrainingClass.Review:WorkEffort.Review:WorkflowStep.Review:PartyTrainingClassSession.Review:PartyTrainingClassSession.Delete" />
            <et:hasSecurityRole securityRole="TrainingClass.Review" var="includeTrainingClassReviewUrl" />
            <et:hasSecurityRole securityRole="WorkEffort.Review" var="includeWorkEffortReviewUrl" />
            <et:hasSecurityRole securityRole="WorkflowStep.Review" var="includeWorkflowStepReviewUrl" />
            <p><font size="+2"><b><c:out value="${partyTrainingClass.partyTrainingClassName}" /></b></font></p>
            <br />
            
            <fmt:message key="label.trainingClass" />:
            <c:choose>
                <c:when test="${includeTrainingClassReviewUrl}">
                    <c:url var="trainingClassUrl" value="/action/HumanResources/TrainingClass/Review">
                        <c:param name="TrainingClassName" value="${partyTrainingClass.trainingClass.trainingClassName}" />
                    </c:url>
                    <a href="${trainingClassUrl}"><c:out value="${partyTrainingClass.trainingClass.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTrainingClass.trainingClass.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />

            <c:set var="workEffort" value="${partyTrainingClass.ownedWorkEfforts.map.TRAINING}" />
            <c:if test="${workEffort != null}">
                <fmt:message key="label.workEffort" />:
                <c:choose>
                    <c:when test="${includeWorkEffortReviewUrl}">
                        <c:url var="workEffortUrl" value="/action/Work/WorkEffort/Review">
                            <c:param name="WorkEffortName" value="${workEffort.workEffortName}" />
                        </c:url>
                        <a href="${workEffortUrl}"><c:out value="${workEffort.workEffortName}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${workEffort.workEffortName}" />
                    </c:otherwise>
                </c:choose>
                <br />
                <br />
            </c:if>

            <c:if test="${partyTrainingClass.completedTime != null || partyTrainingClass.validUntilTime != null}">
                <c:if test="${partyTrainingClass.completedTime != null}">
                    <fmt:message key="label.completedTime" />: <c:out value="${partyTrainingClass.completedTime}" /><br />
                </c:if>
                <c:if test="${partyTrainingClass.validUntilTime != null}">
                    <fmt:message key="label.validUntilTime" />: <c:out value="${partyTrainingClass.validUntilTime}" /><br />
                </c:if>
                <br />
            </c:if>

            <fmt:message key="label.partyTrainingClassStatus" />:
            <c:choose>
                <c:when test="${includeWorkflowStepReviewUrl}">
                    <c:url var="workflowStepUrl" value="/action/Configuration/WorkflowStep/Review">
                        <c:param name="WorkflowName" value="${partyTrainingClass.partyTrainingClassStatus.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${partyTrainingClass.partyTrainingClassStatus.workflowStep.workflowStepName}" />
                    </c:url>
                    <a href="${workflowStepUrl}"><c:out value="${partyTrainingClass.partyTrainingClassStatus.workflowStep.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTrainingClass.partyTrainingClassStatus.workflowStep.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />

            <c:if test='${partyTrainingClass.partyTrainingClassSessions.size != 0}'>
                <display:table name="partyTrainingClass.partyTrainingClassSessions.list" id="partyTrainingClassSession" class="displaytag">
                    <et:hasSecurityRole securityRole="PartyTrainingClassSession.Review">
                        <display:column>
                            <c:url var="reviewUrl" value="/action/HumanResources/PartyTrainingClassSession/Review">
                                <c:param name="PartyTrainingClassName" value="${partyTrainingClassSession.partyTrainingClass.partyTrainingClassName}" />
                                <c:param name="PartyTrainingClassSessionSequence" value="${partyTrainingClassSession.partyTrainingClassSessionSequence}" />
                            </c:url>
                            <a href="${reviewUrl}">Review</a>
                        </display:column>
                    </et:hasSecurityRole>
                    <display:column titleKey="columnTitle.partyTrainingClassSessionSequence">
                        <c:out value="${partyTrainingClassSession.partyTrainingClassSessionSequence}" />
                    </display:column>
                    <et:hasSecurityRole securityRole="PartyTrainingClassSession.Delete">
                        <display:column>
                            <c:url var="deleteUrl" value="/action/HumanResources/PartyTrainingClassSession/Delete">
                                <c:param name="PartyTrainingClassName" value="${partyTrainingClassSession.partyTrainingClass.partyTrainingClassName}" />
                                <c:param name="PartyTrainingClassSessionSequence" value="${partyTrainingClassSession.partyTrainingClassSessionSequence}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="Event.List">
                        <display:column>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${partyTrainingClassSession.entityInstance.entityRef}" />
                            </c:url>
                            <a href="${eventsUrl}">Events</a>
                        </display:column>
                    </et:hasSecurityRole>
                </display:table>
                <br />
            </c:if>

            <br />
            <c:set var="entityInstance" scope="request" value="${partyTrainingClass.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
