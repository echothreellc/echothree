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

<%@ include file="../../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>Selector Nodes</title>
        <html:base/>
        <%@ include file="../../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Selector/Main" />">Selectors</a> &gt;&gt;
                <a href="<c:url value="/action/Selector/SelectorKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="selectorTypesUrl" value="/action/Selector/SelectorKind/Main">
                    <c:param name="SelectorKindName" value="${selectorKindName}" />
                </c:url>
                <a href="${selectorTypesUrl}">Types</a> &gt;&gt;
                <c:url var="selectorsUrl" value="/action/Selector/Selector/Main">
                    <c:param name="SelectorKindName" value="${selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${selectorTypeName}" />
                </c:url>
                <a href="${selectorsUrl}">Selectors</a> &gt;&gt;
                <c:url var="selectorNodesUrl" value="/action/Selector/SelectorNode/Main">
                    <c:param name="SelectorKindName" value="${selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${selectorTypeName}" />
                    <c:param name="SelectorName" value="${selectorName}" />
                </c:url>
                <a href="${selectorNodesUrl}">Nodes</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            Workflow:<br /><br />
            <c:forEach items="${workflows}" var="workflow">
                <c:url var="addUrl" value="/action/Selector/SelectorNode/Add/WorkflowStepStep3">
                    <c:param name="SelectorKindName" value="${selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${selectorTypeName}" />
                    <c:param name="SelectorName" value="${selectorName}" />
                    <c:param name="SelectorNodeTypeName" value="${selectorNodeTypeName}" />
                    <c:param name="WorkflowName" value="${workflow.workflowName}" />
                </c:url>
                &nbsp;&nbsp;&nbsp;&nbsp;<a href="${addUrl}"><c:out value="${workflow.description}" /></a><br />
            </c:forEach>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
