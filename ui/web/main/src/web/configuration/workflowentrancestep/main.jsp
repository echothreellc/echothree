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
        <title><fmt:message key="pageTitle.workflowEntranceSteps" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Workflow/Main" />"><fmt:message key="navigation.workflows" /></a> &gt;&gt;
                <c:url var="workflowEntrancesUrl" value="/action/Configuration/WorkflowEntrance/Main">
                    <c:param name="WorkflowName" value="${workflowEntrance.workflow.workflowName}" />
                </c:url>
                <a href="${workflowEntrancesUrl}"><fmt:message key="navigation.workflowEntrances" /></a> &gt;&gt;
                <fmt:message key="navigation.workflowEntranceSteps" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="WorkflowEntrance.WorkflowStep" />
            <et:hasSecurityRole securityRoles="WorkflowEntrance.WorkflowStep">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="WorkflowEntrance.WorkflowStep">
                <c:url var="addUrl" value="/action/Configuration/WorkflowEntranceStep/Add">
                    <c:param name="WorkflowName" value="${workflowEntrance.workflow.workflowName}" />
                    <c:param name="WorkflowEntranceName" value="${workflowEntrance.workflowEntranceName}" />
                </c:url>
                <p><a href="${addUrl}">Add Step.</a></p>
            </et:hasSecurityRole>
            <display:table name="workflowEntranceSteps" id="workflowEntranceStep" class="displaytag">
                <display:column titleKey="columnTitle.workflow">
                    <c:out value="${workflowEntranceStep.workflowStep.workflow.description}" />
                </display:column>
                <display:column titleKey="columnTitle.step">
                    <c:out value="${workflowEntranceStep.workflowStep.description}" />
                </display:column>
                <c:if test="${linksInFirstRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="WorkflowEntrance.WorkflowStep">
                            <c:url var="deleteUrl" value="/action/Configuration/WorkflowEntranceStep/Delete">
                                <c:param name="WorkflowName" value="${workflowEntranceStep.workflowEntrance.workflow.workflowName}" />
                                <c:param name="WorkflowEntranceName" value="${workflowEntranceStep.workflowEntrance.workflowEntranceName}" />
                                <c:param name="EntranceWorkflowName" value="${workflowEntranceStep.workflowStep.workflow.workflowName}" />
                                <c:param name="EntranceWorkflowStepName" value="${workflowEntranceStep.workflowStep.workflowStepName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
