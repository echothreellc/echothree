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
        <title>Workflow Selector Kinds</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Workflow/Main" />">Workflows</a> &gt;&gt;
                Selector Kinds
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/WorkflowSelectorKind/Add">
                <c:param name="WorkflowName" value="${workflow.workflowName}" />
            </c:url>
            <p><a href="${addUrl}">Add Selector Kind.</a></p>
            <display:table name="workflowSelectorKinds" id="workflowSelectorKind" class="displaytag">
                <display:column property="selectorKind.selectorKindName" titleKey="columnTitle.name" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${workflowSelectorKind.selectorKind.description}" />
                </display:column>
                <display:column>
                    <c:url var="deleteUrl" value="/action/Configuration/WorkflowSelectorKind/Delete">
                        <c:param name="WorkflowName" value="${workflowSelectorKind.workflow.workflowName}" />
                        <c:param name="SelectorKindName" value="${workflowSelectorKind.selectorKind.selectorKindName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
