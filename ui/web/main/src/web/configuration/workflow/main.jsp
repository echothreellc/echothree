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
        <title>Workflows</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                Workflows
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Configuration/Workflow/Add" />">Add Workflow.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="workflows" id="workflow" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Configuration/Workflow/Review">
                        <c:param name="WorkflowName" value="${workflow.workflowName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${workflow.workflowName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${workflow.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column>
                    <c:url var="workflowEntityStatusesUrl" value="/action/Configuration/WorkflowEntityStatus/Main">
                        <c:param name="WorkflowName" value="${workflow.workflowName}" />
                    </c:url>
                    <a href="${workflowEntityStatusesUrl}">Entity Statuses</a>
                    <c:url var="workflowEntrancesUrl" value="/action/Configuration/WorkflowEntrance/Main">
                        <c:param name="WorkflowName" value="${workflow.workflowName}" />
                    </c:url>
                    <a href="${workflowEntrancesUrl}">Entrances</a>
                    <c:url var="workflowEntityTypesUrl" value="/action/Configuration/WorkflowEntityType/Main">
                        <c:param name="WorkflowName" value="${workflow.workflowName}" />
                    </c:url>
                    <a href="${workflowEntityTypesUrl}">Entity Types</a>
                    <c:url var="workflowStepsUrl" value="/action/Configuration/WorkflowStep/Main">
                        <c:param name="WorkflowName" value="${workflow.workflowName}" />
                    </c:url>
                    <a href="${workflowStepsUrl}">Steps</a>
                    <c:url var="workflowSelectorKindsUrl" value="/action/Configuration/WorkflowSelectorKind/Main">
                        <c:param name="WorkflowName" value="${workflow.workflowName}" />
                    </c:url>
                    <a href="${workflowSelectorKindsUrl}">Selector Kinds</a><br />
                    <c:url var="editUrl" value="/action/Configuration/Workflow/Edit">
                        <c:param name="OriginalWorkflowName" value="${workflow.workflowName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/Workflow/Description">
                        <c:param name="WorkflowName" value="${workflow.workflowName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/Workflow/Delete">
                        <c:param name="WorkflowName" value="${workflow.workflowName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${workflow.entityInstance.entityRef}" />
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
