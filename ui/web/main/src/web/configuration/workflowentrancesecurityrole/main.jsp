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
                    <c:param name="WorkflowName" value="${workflowEntrancePartyType.workflowEntrance.workflow.workflowName}" />
                </c:url>
                <a href="${workflowEntrancesUrl}">Entrances</a> &gt;&gt;
                <c:url var="workflowEntrancePartyTypesUrl" value="/action/Configuration/WorkflowEntrancePartyType/Main">
                    <c:param name="WorkflowName" value="${workflowEntrancePartyType.workflowEntrance.workflow.workflowName}" />
                    <c:param name="WorkflowEntranceName" value="${workflowEntrancePartyType.workflowEntrance.workflowEntranceName}" />
                </c:url>
                <a href="${workflowEntrancePartyTypesUrl}">Party Types</a> &gt;&gt;
                Security Roles
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/WorkflowEntranceSecurityRole/Add">
                <c:param name="WorkflowName" value="${workflowEntrancePartyType.workflowEntrance.workflow.workflowName}" />
                <c:param name="WorkflowEntranceName" value="${workflowEntrancePartyType.workflowEntrance.workflowEntranceName}" />
                <c:param name="PartyTypeName" value="${workflowEntrancePartyType.partyType.partyTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Security Role.</a></p>
            <display:table name="workflowEntranceSecurityRoles" id="workflowEntranceSecurityRole" class="displaytag">
                <display:column titleKey="columnTitle.securityRoleGroup">
                    <c:out value="${workflowEntranceSecurityRole.securityRole.securityRoleGroup.description}" />
                </display:column>
                <display:column titleKey="columnTitle.securityRole">
                    <c:out value="${workflowEntranceSecurityRole.securityRole.description}" />
                </display:column>
                <display:column>
                    <c:url var="deleteUrl" value="/action/Configuration/WorkflowEntranceSecurityRole/Delete">
                        <c:param name="WorkflowName" value="${workflowEntranceSecurityRole.workflowEntrancePartyType.workflowEntrance.workflow.workflowName}" />
                        <c:param name="WorkflowEntranceName" value="${workflowEntranceSecurityRole.workflowEntrancePartyType.workflowEntrance.workflowEntranceName}" />
                        <c:param name="PartyTypeName" value="${workflowEntranceSecurityRole.workflowEntrancePartyType.partyType.partyTypeName}" />
                        <c:param name="SecurityRoleGroupName" value="${workflowEntranceSecurityRole.securityRole.securityRoleGroup.securityRoleGroupName}" />
                        <c:param name="SecurityRoleName" value="${workflowEntranceSecurityRole.securityRole.securityRoleName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
