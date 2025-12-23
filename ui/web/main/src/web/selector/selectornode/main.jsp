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
        <title>Selector Nodes</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Selector/Main" />">Selectors</a> &gt;&gt;
                <a href="<c:url value="/action/Selector/SelectorKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="selectorTypesUrl" value="/action/Selector/SelectorKind/Main">
                    <c:param name="SelectorKindName" value="${selectorKind.selectorKindName}" />
                </c:url>
                <a href="${selectorTypesUrl}">Types</a> &gt;&gt;
                <c:url var="selectorsUrl" value="/action/Selector/Selector/Main">
                    <c:param name="SelectorKindName" value="${selectorKind.selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${selectorType.selectorTypeName}" />
                </c:url>
                <a href="${selectorsUrl}">Selectors</a> &gt;&gt;
                Nodes
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Selector/SelectorNode/Add/Step1">
                <c:param name="SelectorKindName" value="${selectorKind.selectorKindName}" />
                <c:param name="SelectorTypeName" value="${selectorType.selectorTypeName}" />
                <c:param name="SelectorName" value="${selector.selectorName}" />
            </c:url>
            <p><a href="${addUrl}">Add Selector Node.</a></p>
            <display:table name="selectorNodes" id="selectorNode" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Selector/SelectorNode/Review">
                        <c:param name="SelectorKindName" value="${selectorNode.selector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${selectorNode.selector.selectorType.selectorTypeName}" />
                        <c:param name="SelectorName" value="${selectorNode.selector.selectorName}" />
                        <c:param name="SelectorNodeName" value="${selectorNode.selectorNodeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${selectorNode.selectorNodeName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${selectorNode.description}" />
                </display:column>
                <display:column titleKey="columnTitle.root">
                    <c:choose>
                        <c:when test="${selectorNode.isRootSelectorNode}">
                            Root
                        </c:when>
                        <c:otherwise>
                            <c:url var="setRootUrl" value="/action/Selector/SelectorNode/SetRootSelectorNode">
                                <c:param name="SelectorKindName" value="${selectorNode.selector.selectorType.selectorKind.selectorKindName}" />
                                <c:param name="SelectorTypeName" value="${selectorNode.selector.selectorType.selectorTypeName}" />
                                <c:param name="SelectorName" value="${selectorNode.selector.selectorName}" />
                                <c:param name="SelectorNodeName" value="${selectorNode.selectorNodeName}" />
                            </c:url>
                            <a href="${setRootUrl}">Set Root</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.type">
                    <c:out value="${selectorNode.selectorNodeType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.negate">
                    <c:choose>
                        <c:when test="${selectorNode.negate}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Selector/SelectorNode/Edit">
                        <c:param name="SelectorKindName" value="${selectorNode.selector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${selectorNode.selector.selectorType.selectorTypeName}" />
                        <c:param name="SelectorName" value="${selectorNode.selector.selectorName}" />
                        <c:param name="SelectorNodeName" value="${selectorNode.selectorNodeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Selector/SelectorNode/Description">
                        <c:param name="SelectorKindName" value="${selectorNode.selector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${selectorNode.selector.selectorType.selectorTypeName}" />
                        <c:param name="SelectorName" value="${selectorNode.selector.selectorName}" />
                        <c:param name="SelectorNodeName" value="${selectorNode.selectorNodeName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Selector/SelectorNode/Delete">
                        <c:param name="SelectorKindName" value="${selectorNode.selector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${selectorNode.selector.selectorType.selectorTypeName}" />
                        <c:param name="SelectorName" value="${selectorNode.selector.selectorName}" />
                        <c:param name="SelectorNodeName" value="${selectorNode.selectorNodeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
