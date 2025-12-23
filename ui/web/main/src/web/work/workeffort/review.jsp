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
            <fmt:message key="pageTitle.workEffort">
                <fmt:param value="${workEffort.workEffortName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <fmt:message key="navigation.workEffort">
                    <fmt:param value="${workEffort.workEffortName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="WorkEffortType.Review:WorkEffortScope.Review:WorkRequirement.Review:WorkflowStep.Review" />
            <et:hasSecurityRole securityRole="WorkEffortType.Review" var="includeWorkEffortTypeReviewUrl" />
            <et:hasSecurityRole securityRole="WorkEffortScope.Review" var="includeWorkEffortScopeReviewUrl" />
            <et:hasSecurityRole securityRole="WorkRequirement.Review" var="includeWorkRequirementReviewUrl" />
            <et:hasSecurityRole securityRole="WorkflowStep.Review" var="includeWorkflowStepReviewUrl" />
            <p><font size="+2"><b><c:out value="${workEffort.workEffortName}" /></b></font></p>
            <br />
            <fmt:message key="label.workEffortName" />: ${workEffort.workEffortName}<br />
            <br />
            <fmt:message key="label.owningEntityInstance" />:
            <c:set var="entityInstance" scope="request" value="${workEffort.owningEntityInstance}" />
            <jsp:include page="../../include/targetAsReviewLink.jsp" />
            <br />
            <br />
            <fmt:message key="label.workEffortType" />:
            <c:choose>
                <c:when test="${includeWorkEffortTypeReviewUrl}">
                    <c:url var="workEffortTypeUrl" value="/action/Configuration/WorkEffortType/Review">
                        <c:param name="WorkEffortTypeName" value="${workEffort.workEffortScope.workEffortType.workEffortTypeName}" />
                    </c:url>
                    <a href="${workEffortTypeUrl}"><c:out value="${workEffort.workEffortScope.workEffortType.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${workEffort.workEffortScope.workEffortType.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.workEffortScope" />:
            <c:choose>
                <c:when test="${includeWorkEffortScopeReviewUrl}">
                    <c:url var="workEffortScopeUrl" value="/action/Configuration/WorkEffortScope/Review">
                        <c:param name="WorkEffortTypeName" value="${workEffort.workEffortScope.workEffortType.workEffortTypeName}" />
                        <c:param name="WorkEffortScopeName" value="${workEffort.workEffortScope.workEffortScopeName}" />
                    </c:url>
                    <a href="${workEffortScopeUrl}"><c:out value="${workEffort.workEffortScope.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${workEffort.workEffortScope.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <fmt:message key="label.scheduledTime" />: <c:out value="${workEffort.scheduledTime}" /><br />
            <br />
            <fmt:message key="label.scheduledStartTime" />:
            <c:choose>
                <c:when test="${workEffort.scheduledStartTime == null}">
                    <i>Not Scheduled.</i>
                </c:when>
                <c:otherwise>
                    <c:out value="${workEffort.scheduledStartTime}" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.scheduledEndTime" />:
            <c:choose>
                <c:when test="${workEffort.scheduledEndTime == null}">
                    <i>Not Scheduled.</i>
                </c:when>
                <c:otherwise>
                    <c:out value="${workEffort.scheduledEndTime}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />

            <fmt:message key="label.estimatedTimeAllowed" />: <c:out value="${workEffort.estimatedTimeAllowed}" /><br />
            <fmt:message key="label.maximumTimeAllowed" />: <c:out value="${workEffort.maximumTimeAllowed}" /><br />
            <br />

            <c:if test="${workEffort.workRequirements.size != 0}">
                <h2><fmt:message key="label.workRequirements" /></h2>
                <display:table name="workEffort.workRequirements.list" id="workRequirement" class="displaytag">
                    <display:column titleKey="columnTitle.name">
                        <c:choose>
                            <c:when test="${includeWorkRequirementReviewUrl}">
                                <c:url var="workRequirementUrl" value="/action/Work/WorkRequirement/Review">
                                    <c:param name="WorkRequirementName" value="${workRequirement.workRequirementName}" />
                                </c:url>
                                <a href="${workRequirementUrl}"><c:out value="${workRequirement.workRequirementName}" /></a><br />
                            </c:when>
                            <c:otherwise>
                                <c:out value="${workRequirement.workRequirementName}" />
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                    <display:column titleKey="columnTitle.workRequirementType">
                        <c:out value="${workRequirement.workRequirementScope.workRequirementType.description}" />
                    </display:column>
                    <display:column titleKey="columnTitle.startTime">
                        <c:out value="${workRequirement.startTime}" />
                    </display:column>
                    <display:column titleKey="columnTitle.requiredTime">
                        <c:out value="${workRequirement.requiredTime}" />
                    </display:column>
                    <display:column titleKey="columnTitle.workRequirementStatus">
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
                    </display:column>
                </display:table>
                <br />
            </c:if>

            <br />
            <c:set var="entityInstance" scope="request" value="${workEffort.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
