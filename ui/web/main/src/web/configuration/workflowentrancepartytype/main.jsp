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
        <title>Party Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Workflow/Main" />">Workflows</a> &gt;&gt;
                <c:url var="workflowEntrancesUrl" value="/action/Configuration/WorkflowEntrance/Main">
                    <c:param name="WorkflowName" value="${workflowEntrance.workflow.workflowName}" />
                </c:url>
                <a href="${workflowEntrancesUrl}">Entrances</a> &gt;&gt;
                Party Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/WorkflowEntrancePartyType/Add">
                <c:param name="WorkflowName" value="${workflowEntrance.workflow.workflowName}" />
                <c:param name="WorkflowEntranceName" value="${workflowEntrance.workflowEntranceName}" />
            </c:url>
            <p><a href="${addUrl}">Add Party Type.</a></p>
            <display:table name="workflowEntrancePartyTypes" id="workflowEntrancePartyType" class="displaytag">
                <display:column titleKey="columnTitle.partyType">
                    <c:out value="${workflowEntrancePartyType.partyType.description}" />
                </display:column>
                <display:column>
                    <c:url var="workflowEntranceSecurityRolesUrl" value="/action/Configuration/WorkflowEntranceSecurityRole/Main">
                        <c:param name="WorkflowName" value="${workflowEntrancePartyType.workflowEntrance.workflow.workflowName}" />
                        <c:param name="WorkflowEntranceName" value="${workflowEntrancePartyType.workflowEntrance.workflowEntranceName}" />
                        <c:param name="PartyTypeName" value="${workflowEntrancePartyType.partyType.partyTypeName}" />
                    </c:url>
                    <a href="${workflowEntranceSecurityRolesUrl}">Security Roles</a><br />
                    <c:url var="deleteUrl" value="/action/Configuration/WorkflowEntrancePartyType/Delete">
                        <c:param name="WorkflowName" value="${workflowEntrancePartyType.workflowEntrance.workflow.workflowName}" />
                        <c:param name="WorkflowEntranceName" value="${workflowEntrancePartyType.workflowEntrance.workflowEntranceName}" />
                        <c:param name="PartyTypeName" value="${workflowEntrancePartyType.partyType.partyTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
