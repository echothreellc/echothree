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
        <title>Selector Node Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Selector/Main" />">Selectors</a> &gt;&gt;
                <a href="<c:url value="/action/Selector/SelectorKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="selectorTypesUrl" value="/action/Selector/SelectorType/Main">
                    <c:param name="SelectorKindName" value="${selectorKind.selectorKindName}" />
                </c:url>
                <a href="${selectorTypesUrl}">Types</a> &gt;&gt;
                <c:url var="selectorsUrl" value="/action/Selector/Selector/Main">
                    <c:param name="SelectorKindName" value="${selectorKind.selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${selectorType.selectorTypeName}" />
                </c:url>
                <a href="${selectorsUrl}">Selectors</a> &gt;&gt;
                <c:url var="selectorNodesUrl" value="/action/Selector/SelectorNode/Main">
                    <c:param name="SelectorKindName" value="${selectorKind.selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${selectorType.selectorTypeName}" />
                    <c:param name="SelectorName" value="${selector.selectorName}" />
                </c:url>
                <a href="${selectorNodesUrl}">Nodes</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addDescriptionUrl" value="/action/Selector/SelectorNode/DescriptionAdd">
                    <c:param name="SelectorKindName" value="${selectorKind.selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${selectorType.selectorTypeName}" />
                    <c:param name="SelectorName" value="${selector.selectorName}" />
                    <c:param name="SelectorNodeName" value="${selectorNode.selectorNodeName}" />
            </c:url>
            <p><a href="${addDescriptionUrl}">Add Description.</a></p>
            <display:table name="selectorNodeDescriptions" id="selectorNodeDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${selectorNodeDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${selectorNodeDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editDescriptionUrl" value="/action/Selector/SelectorNode/DescriptionEdit">
                        <c:param name="SelectorKindName" value="${selectorNodeDescription.selectorNode.selector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${selectorNodeDescription.selectorNode.selector.selectorType.selectorTypeName}" />
                        <c:param name="SelectorName" value="${selectorNodeDescription.selectorNode.selector.selectorName}" />
                        <c:param name="SelectorNodeName" value="${selectorNodeDescription.selectorNode.selectorNodeName}" />
                        <c:param name="LanguageIsoName" value="${selectorNodeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editDescriptionUrl}">Edit</a>
                    <c:url var="deleteDescriptionUrl" value="/action/Selector/SelectorNode/DescriptionDelete">
                        <c:param name="SelectorKindName" value="${selectorNodeDescription.selectorNode.selector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${selectorNodeDescription.selectorNode.selector.selectorType.selectorTypeName}" />
                        <c:param name="SelectorName" value="${selectorNodeDescription.selectorNode.selector.selectorName}" />
                        <c:param name="SelectorNodeName" value="${selectorNodeDescription.selectorNode.selectorNodeName}" />
                        <c:param name="LanguageIsoName" value="${selectorNodeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteDescriptionUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
