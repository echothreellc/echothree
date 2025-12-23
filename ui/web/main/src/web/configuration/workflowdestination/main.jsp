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
        <title>Destinations</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Workflow/Main" />">Workflows</a> &gt;&gt;
                <c:url var="workflowStepsUrl" value="/action/Configuration/WorkflowStep/Main">
                    <c:param name="WorkflowName" value="${workflowStep.workflow.workflowName}" />
                </c:url>
                <a href="${workflowStepsUrl}">Steps</a> &gt;&gt;
                Destinations
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/WorkflowDestination/Add">
                <c:param name="WorkflowName" value="${workflowStep.workflow.workflowName}" />
                <c:param name="WorkflowStepName" value="${workflowStep.workflowStepName}" />
            </c:url>
            <p><a href="${addUrl}">Add Destination.</a></p>
            <display:table name="workflowDestinations" id="workflowDestination" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Configuration/WorkflowDestination/Review">
                        <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
                        <c:param name="WorkflowDestinationName" value="${workflowDestination.workflowDestinationName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${workflowDestination.workflowDestinationName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${workflowDestination.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${workflowDestination.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Configuration/WorkflowDestination/SetDefault">
                                <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                                <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
                                <c:param name="WorkflowDestinationName" value="${workflowDestination.workflowDestinationName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="workflowDestinationStepsUrl" value="/action/Configuration/WorkflowDestinationStep/Main">
                        <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
                        <c:param name="WorkflowDestinationName" value="${workflowDestination.workflowDestinationName}" />
                    </c:url>
                    <a href="${workflowDestinationStepsUrl}">Steps</a>
                    <c:url var="workflowDestinationPartyTypesUrl" value="/action/Configuration/WorkflowDestinationPartyType/Main">
                        <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
                        <c:param name="WorkflowDestinationName" value="${workflowDestination.workflowDestinationName}" />
                    </c:url>
                    <a href="${workflowDestinationPartyTypesUrl}">Party Types</a><br />
                    <c:url var="editUrl" value="/action/Configuration/WorkflowDestination/Edit">
                        <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
                        <c:param name="OriginalWorkflowDestinationName" value="${workflowDestination.workflowDestinationName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/WorkflowDestination/Description">
                        <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
                        <c:param name="WorkflowDestinationName" value="${workflowDestination.workflowDestinationName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/WorkflowDestination/Delete">
                        <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
                        <c:param name="WorkflowDestinationName" value="${workflowDestination.workflowDestinationName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
