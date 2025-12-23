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
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/TagScope/Main" />">Tag Scopes</a> &gt;&gt;
                <c:url var="tagScopeEntityTypesUrl" value="/action/Core/TagScopeEntityType/Main">
                    <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                </c:url>
                <a href="${tagScopeEntityTypesUrl}">Entity Types</a> &gt;&gt;
                Add &gt;&gt;
                Step 2 of 2
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            Entity Types:<br /><br />
            <c:forEach items="${entityTypes}" var="entityType">
                <c:url var="addUrl" value="/action/Core/TagScopeEntityType/Add/Step2">
                    <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                    <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                </c:url>
                &nbsp;&nbsp;&nbsp;&nbsp;<a href="${addUrl}"><c:out value="${entityType.description}" /></a><br />
            </c:forEach>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
