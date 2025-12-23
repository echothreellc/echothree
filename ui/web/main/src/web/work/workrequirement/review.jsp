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
            <fmt:message key="pageTitle.workRequirement">
                <fmt:param value="${workRequirement.workRequirementName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <fmt:message key="navigation.workRequirement">
                    <fmt:param value="${workRequirement.workRequirementName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="WorkEffort.Review:WorkEffortType.Review:WorkEffortScope.Review:WorkRequirementType.Review:WorkflowStep.Review:Event.List" />
            <et:hasSecurityRole securityRole="WorkEffort.Review" var="includeWorkEffortReviewUrl" />
            <et:hasSecurityRole securityRole="WorkEffortType.Review" var="includeWorkEffortTypeReviewUrl" />
            <et:hasSecurityRole securityRole="WorkEffortScope.Review" var="includeWorkEffortScopeReviewUrl" />
            <et:hasSecurityRole securityRole="WorkRequirementType.Review" var="includeWorkRequirementTypeReviewUrl" />
            <et:hasSecurityRole securityRole="WorkflowStep.Review" var="includeWorkflowStepReviewUrl" />
            <p><font size="+2"><b><c:out value="${workRequirement.workRequirementName}" /></b></font></p>
            <br />
            <fmt:message key="label.workRequirementName" />: ${workRequirement.workRequirementName}<br />
            <br />
            <fmt:message key="label.workEffort" />:
            <c:choose>
                <c:when test="${includeWorkEffortReviewUrl}">
                    <c:url var="workEffortUrl" value="/action/Work/WorkEffort/Review">
                        <c:param name="WorkEffortName" value="${workRequirement.workEffort.workEffortName}" />
                    </c:url>
                    <a href="${workEffortUrl}"><c:out value="${workRequirement.workEffort.workEffortName}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${workRequirement.workEffort.workEffortName}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <fmt:message key="label.workEffortType" />:
            <c:choose>
                <c:when test="${includeWorkEffortTypeReviewUrl}">
                    <c:url var="workEffortTypeUrl" value="/action/Configuration/WorkEffortType/Review">
                        <c:param name="WorkEffortTypeName" value="${workRequirement.workRequirementScope.workRequirementType.workEffortType.workEffortTypeName}" />
                    </c:url>
                    <a href="${workEffortTypeUrl}"><c:out value="${workRequirement.workRequirementScope.workRequirementType.workEffortType.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${workRequirement.workRequirementScope.workRequirementType.workEffortType.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.workEffortScope" />:
            <c:choose>
                <c:when test="${includeWorkEffortScopeReviewUrl}">
                    <c:url var="workEffortScopeUrl" value="/action/Configuration/WorkEffortScope/Review">
                        <c:param name="WorkEffortTypeName" value="${workRequirement.workRequirementScope.workEffortScope.workEffortType.workEffortTypeName}" />
                        <c:param name="WorkEffortScopeName" value="${workRequirement.workRequirementScope.workEffortScope.workEffortScopeName}" />
                    </c:url>
                    <a href="${workEffortScopeUrl}"><c:out value="${workRequirement.workRequirementScope.workEffortScope.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${workRequirement.workRequirementScope.workEffortScope.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.workRequirementType" />:
            <c:choose>
                <c:when test="${includeWorkRequirementTypeReviewUrl}">
                    <c:url var="workRequirementTypeUrl" value="/action/Configuration/WorkRequirementType/Review">
                        <c:param name="WorkEffortTypeName" value="${workRequirement.workRequirementScope.workRequirementType.workEffortType.workEffortTypeName}" />
                        <c:param name="WorkRequirementTypeName" value="${workRequirement.workRequirementScope.workRequirementType.workRequirementTypeName}" />
                    </c:url>
                    <a href="${workRequirementTypeUrl}"><c:out value="${workRequirement.workRequirementScope.workRequirementType.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${workRequirement.workRequirementScope.workRequirementType.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <fmt:message key="label.startTime" />: <c:out value="${workRequirement.startTime}" /><br />
            <fmt:message key="label.requiredTime" />: <c:out value="${workRequirement.requiredTime}" /><br />
            <br />

            <fmt:message key="label.workRequirementStatus" />:
            <c:choose>
                <c:when test="${includeWorkflowStepReviewUrl}">
                    <c:url var="workflowStepUrl" value="/action/Configuration/WorkflowStep/Review">
                        <c:param name="WorkflowName" value="${workRequirement.workRequirementStatus.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workRequirement.workRequirementStatus.workflowStep.workflowStepName}" />
                    </c:url>
                    <a href="${workflowStepUrl}"><c:out value="${workRequirement.workRequirementStatus.workflowStep.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${workRequirement.workRequirementStatus.workflowStep.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />

            <c:if test="${workRequirement.workAssignments.size != 0}">
                <h2><fmt:message key="label.workAssignments" /></h2>
                <display:table name="workRequirement.workAssignments.list" id="workAssignment" class="displaytag">
                    <display:column titleKey="columnTitle.workAssignmentSequence">
                        <c:out value="${workAssignment.workAssignmentSequence}" />
                    </display:column>
                    <display:column titleKey="columnTitle.party">
                        <c:set var="party" scope="request" value="${workAssignment.party}" />
                        <jsp:include page="../../include/party.jsp" />
                    </display:column>
                    <display:column titleKey="columnTitle.startTime">
                        <c:out value="${workAssignment.startTime}" />
                    </display:column>
                    <display:column titleKey="columnTitle.endTime">
                        <c:out value="${workAssignment.endTime}" />
                    </display:column>
                    <display:column titleKey="columnTitle.workAssignmentStatus">
                        <c:choose>
                            <c:when test="${includeWorkflowStepReviewUrl}">
                                <c:url var="workflowStepUrl" value="/action/Configuration/WorkflowStep/Review">
                                    <c:param name="WorkflowName" value="${workAssignment.workAssignmentStatus.workflowStep.workflow.workflowName}" />
                                    <c:param name="WorkflowStepName" value="${workAssignment.workAssignmentStatus.workflowStep.workflowStepName}" />
                                </c:url>
                                <a href="${workflowStepUrl}"><c:out value="${workAssignment.workAssignmentStatus.workflowStep.description}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${workAssignment.workAssignmentStatus.workflowStep.description}" />
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                    <et:hasSecurityRole securityRole="Event.List">
                        <display:column>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${workAssignment.entityInstance.entityRef}" />
                            </c:url>
                            <a href="${eventsUrl}">Events</a>
                        </display:column>
                    </et:hasSecurityRole>
                </display:table>
                <br />
            </c:if>

            <c:if test="${workRequirement.workTimes.size != 0}">
                <h2><fmt:message key="label.workTimes" /></h2>
                <display:table name="workRequirement.workTimes.list" id="workTime" class="displaytag">
                    <display:column titleKey="columnTitle.workTimeSequence">
                        <c:out value="${workTime.workTimeSequence}" />
                    </display:column>
                    <display:column titleKey="columnTitle.party">
                        <c:out value="${workTime.party.description}" />
                    </display:column>
                    <display:column titleKey="columnTitle.startTime">
                        <c:out value="${workTime.startTime}" />
                    </display:column>
                    <display:column titleKey="columnTitle.endTime">
                        <c:out value="${workTime.endTime}" />
                    </display:column>
                    <display:column titleKey="columnTitle.workTimeStatus">
                        <c:choose>
                            <c:when test="${includeWorkflowStepReviewUrl}">
                                <c:url var="workflowStepUrl" value="/action/Configuration/WorkflowStep/Review">
                                    <c:param name="WorkflowName" value="${workTime.workTimeStatus.workflowStep.workflow.workflowName}" />
                                    <c:param name="WorkflowStepName" value="${workTime.workTimeStatus.workflowStep.workflowStepName}" />
                                </c:url>
                                <a href="${workflowStepUrl}"><c:out value="${workTime.workTimeStatus.workflowStep.description}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${workTime.workTimeStatus.workflowStep.description}" />
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                    <et:hasSecurityRole securityRole="Event.List">
                        <display:column>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${workTime.entityInstance.entityRef}" />
                            </c:url>
                            <a href="${eventsUrl}">Events</a>
                        </display:column>
                    </et:hasSecurityRole>
                </display:table>
                <br />
            </c:if>

            <br />
            <c:set var="entityInstance" scope="request" value="${workRequirement.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
