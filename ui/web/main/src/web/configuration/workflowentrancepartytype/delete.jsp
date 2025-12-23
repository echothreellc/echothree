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
                Delete
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.hasErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <p>You are about to delete the <c:out value="${fn:toLowerCase(partyEntityType.entityType.description)}" />:</p>
                    &nbsp;&nbsp;&nbsp;&nbsp;Workflow: <c:out value="${workflowEntrancePartyType.workflowEntrance.workflow.description}" /><br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Workflow Entrance: <c:out value="${workflowEntrancePartyType.workflowEntrance.description}" /><br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Party Type: <c:out value="${workflowEntrancePartyType.partyType.description}" /><br />
                    <html:form action="/Configuration/WorkflowEntrancePartyType/Delete" method="POST">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.confirmDelete" />:</td>
                                <td>
                                    <html:checkbox property="confirmDelete" /> (*)
                                    <et:validationErrors id="errorMessage" property="ConfirmDelete">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </table>
                        <html:submit value="Delete" onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" />
                        <html:hidden property="workflowName" />
                        <html:hidden property="workflowEntranceName" />
                        <html:hidden property="partyTypeName" />
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
