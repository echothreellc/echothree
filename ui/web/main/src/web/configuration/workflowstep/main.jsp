<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2025 Echo Three, LLC                                              -->
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
        <title>Steps</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Workflow/Main" />">Workflows</a> &gt;&gt;
                Steps
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/WorkflowStep/Add">
                <c:param name="WorkflowName" value="${workflow.workflowName}" />
            </c:url>
            <p><a href="${addUrl}">Add Step.</a></p>
            <display:table name="workflowSteps" id="workflowStep" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Configuration/WorkflowStep/Review">
                        <c:param name="WorkflowName" value="${workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowStep.workflowStepName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${workflowStep.workflowStepName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${workflowStep.description}" />
                </display:column>
                <display:column titleKey="columnTitle.type">
                    <c:out value="${workflowStep.workflowStepType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${workflowStep.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Configuration/WorkflowStep/SetDefault">
                                <c:param name="WorkflowName" value="${workflowStep.workflow.workflowName}" />
                                <c:param name="WorkflowStepName" value="${workflowStep.workflowStepName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="workflowDestinationsUrl" value="/action/Configuration/WorkflowDestination/Main">
                        <c:param name="WorkflowName" value="${workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowStep.workflowStepName}" />
                    </c:url>
                    <a href="${workflowDestinationsUrl}">Destinations</a><br />
                    <c:url var="editUrl" value="/action/Configuration/WorkflowStep/Edit">
                        <c:param name="WorkflowName" value="${workflowStep.workflow.workflowName}" />
                        <c:param name="OriginalWorkflowStepName" value="${workflowStep.workflowStepName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/WorkflowStep/Description">
                        <c:param name="WorkflowName" value="${workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowStep.workflowStepName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/WorkflowStep/Delete">
                        <c:param name="WorkflowName" value="${workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowStep.workflowStepName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <display:column>
                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                        <c:param name="EntityRef" value="${workflowStep.entityInstance.entityRef}" />
                    </c:url>
                    <a href="${eventsUrl}">Events</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
