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
        <title>Entrances</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Workflow/Main" />">Workflows</a> &gt;&gt;
                Entrances
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/WorkflowEntrance/Add">
                <c:param name="WorkflowName" value="${workflow.workflowName}" />
            </c:url>
            <p><a href="${addUrl}">Add Entrance.</a></p>
            <display:table name="workflowEntrances" id="workflowEntrance" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Configuration/WorkflowEntrance/Review">
                        <c:param name="WorkflowName" value="${workflowEntrance.workflow.workflowName}" />
                        <c:param name="WorkflowEntranceName" value="${workflowEntrance.workflowEntranceName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${workflowEntrance.workflowEntranceName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${workflowEntrance.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${workflowEntrance.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Configuration/WorkflowEntrance/SetDefault">
                                <c:param name="WorkflowName" value="${workflowEntrance.workflow.workflowName}" />
                                <c:param name="WorkflowEntranceName" value="${workflowEntrance.workflowEntranceName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="workflowEntranceStepsUrl" value="/action/Configuration/WorkflowEntranceStep/Main">
                        <c:param name="WorkflowName" value="${workflowEntrance.workflow.workflowName}" />
                        <c:param name="WorkflowEntranceName" value="${workflowEntrance.workflowEntranceName}" />
                    </c:url>
                    <a href="${workflowEntranceStepsUrl}">Steps</a>
                    <c:url var="workflowEntrancePartyTypesUrl" value="/action/Configuration/WorkflowEntrancePartyType/Main">
                        <c:param name="WorkflowName" value="${workflowEntrance.workflow.workflowName}" />
                        <c:param name="WorkflowEntranceName" value="${workflowEntrance.workflowEntranceName}" />
                    </c:url>
                    <a href="${workflowEntrancePartyTypesUrl}">Party Types</a><br />
                    <c:url var="editUrl" value="/action/Configuration/WorkflowEntrance/Edit">
                        <c:param name="WorkflowName" value="${workflowEntrance.workflow.workflowName}" />
                        <c:param name="OriginalWorkflowEntranceName" value="${workflowEntrance.workflowEntranceName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/WorkflowEntrance/Description">
                        <c:param name="WorkflowName" value="${workflowEntrance.workflow.workflowName}" />
                        <c:param name="WorkflowEntranceName" value="${workflowEntrance.workflowEntranceName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/WorkflowEntrance/Delete">
                        <c:param name="WorkflowName" value="${workflowEntrance.workflow.workflowName}" />
                        <c:param name="WorkflowEntranceName" value="${workflowEntrance.workflowEntranceName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <display:column>
                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                        <c:param name="EntityRef" value="${workflowEntrance.entityInstance.entityRef}" />
                    </c:url>
                    <a href="${eventsUrl}">Events</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
