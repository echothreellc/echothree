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
            Type of Node:<br /><br />
            <c:forEach items="${selectorNodeTypes}" var="selectorNodeType">
                <c:choose>
                    <c:when test="${selectorNodeType.selectorNodeTypeName == 'ENTITY_LIST_ITEM'}">
                        <c:choose>
                            <c:when test="${selectorKindName == 'ITEM' || selectorKindName == 'CUSTOMER' || selectorKindName == 'EMPLOYEE' || selectorKindName == 'VENDOR'}">
                                <c:set var="addBaseUrl" scope="page" value="/action/Selector/SelectorNode/Add/EntityListItemStep4" />
                            </c:when>
                            <c:otherwise>
                                <c:set var="addBaseUrl" scope="page" value="/action/Selector/SelectorNode/Add/FinalStep" />
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${selectorNodeType.selectorNodeTypeName == 'WORKFLOW_STEP'}">
                        <c:set var="addBaseUrl" scope="page" value="/action/Selector/SelectorNode/Add/WorkflowStepStep2" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="addBaseUrl" scope="page" value="/action/Selector/SelectorNode/Add/FinalStep" />
                    </c:otherwise>
                </c:choose>
                <c:url var="addUrl" value="${addBaseUrl}">
                    <c:param name="SelectorKindName" value="${selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${selectorTypeName}" />
                    <c:param name="SelectorName" value="${selectorName}" />
                    <c:param name="SelectorNodeTypeName" value="${selectorNodeType.selectorNodeTypeName}" />
                    <c:if test="${selectorKindName == 'ITEM' || selectorKindName == 'CUSTOMER' || selectorKindName == 'EMPLOYEE' || selectorKindName == 'VENDOR'}">
                        <c:param name="ComponentVendorName" value="EchoThree" />
                        <c:choose>
                            <c:when test="${selectorKindName == 'ITEM'}">
                                <c:param name="EntityTypeName" value="Item" />
                            </c:when>
                            <c:when test="${selectorKindName == 'CUSTOMER' || selectorKindName == 'EMPLOYEE' || selectorKindName == 'VENDOR'}">
                                <c:param name="EntityTypeName" value="Party" />
                            </c:when>
                        </c:choose>
                    </c:if>
                </c:url>
                &nbsp;&nbsp;&nbsp;&nbsp;<a href="${addUrl}"><c:out value="${selectorNodeType.description}" /></a><br />
            </c:forEach>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
