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
        <title>Review (<c:out value="${workflowDestination.workflowDestinationName}" />)</title>
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
                    <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                </c:url>
                <a href="${workflowStepsUrl}">Steps</a> &gt;&gt;
                <c:url var="workflowDestinationsUrl" value="/action/Configuration/WorkflowDestination/Main">
                    <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                    <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
                </c:url>
                <a href="${workflowDestinationsUrl}">Destinations</a> &gt;&gt;
                Review (<c:out value="${workflowDestination.workflowDestinationName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <p><font size="+2"><b><c:out value="${workflowDestination.description}" /></b></font></p>
            <br />
            <c:url var="workflowUrl" value="/action/Configuration/Workflow/Review">
                <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
            </c:url>
            Workflow Name: <a href="${workflowUrl}"><c:out value="${workflowDestination.workflowStep.workflow.workflowName}" /></a><br />
            <c:url var="workflowStepUrl" value="/action/Configuration/WorkflowStep/Review">
                <c:param name="WorkflowName" value="${workflowDestination.workflowStep.workflow.workflowName}" />
                <c:param name="WorkflowStepName" value="${workflowDestination.workflowStep.workflowStepName}" />
            </c:url>
            Workflow Step Name: <a href="${workflowStepUrl}"><c:out value="${workflowDestination.workflowStep.workflowStepName}" /></a><br />
            Workflow Destination Name: <c:out value="${workflowDestination.workflowDestinationName}" /><br />
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${workflowDestination.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
