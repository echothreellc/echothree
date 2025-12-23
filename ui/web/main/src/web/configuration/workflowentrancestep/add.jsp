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
        <title><fmt:message key="pageTitle.workflowEntrances" /></title>
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
                <c:url var="workflowEntranceStepsUrl" value="/action/Configuration/WorkflowEntranceStep/Main">
                    <c:param name="WorkflowName" value="${workflowEntrance.workflow.workflowName}" />
                    <c:param name="WorkflowEntranceName" value="${workflowEntrance.workflowEntranceName}" />
                </c:url>
                <a href="${workflowEntranceStepsUrl}"><fmt:message key="navigation.workflowEntranceSteps" /></a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Configuration/WorkflowEntranceStep/Add" method="POST">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.entranceWorkflowStep" />:</td>
                        <td>
                            <html:select property="entranceWorkflowStepChoice">
                                <html:optionsCollection property="entranceWorkflowStepChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="EntranceWorkflowStepName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="workflowName" />
                            <html:hidden property="workflowEntranceName" />
                            <html:hidden property="entranceWorkflowName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>