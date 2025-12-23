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
        <title><fmt:message key="pageTitle.chainActionSetDescriptions" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/Main" />"><fmt:message key="navigation.chains" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/ChainKind/Main" />"><fmt:message key="navigation.chainKinds" /></a> &gt;&gt;
                <c:url var="chainTypesUrl" value="/action/Chain/ChainType/Main">
                    <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}"><fmt:message key="navigation.chainTypes" /></a> &gt;&gt;
                <c:url var="chainsUrl" value="/action/Chain/Chain/Main">
                    <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainActionSet.chain.chainType.chainTypeName}" />
                </c:url>
                <a href="${chainsUrl}"><fmt:message key="navigation.chains" /></a> &gt;&gt;
                <c:url var="chainActionSetsUrl" value="/action/Chain/ChainActionSet/Main">
                    <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainActionSet.chain.chainType.chainTypeName}" />
                    <c:param name="ChainName" value="${chainActionSet.chain.chainName}" />
                </c:url>
                <a href="${chainActionSetsUrl}"><fmt:message key="navigation.chainActionSets" /></a> &gt;&gt;
                <fmt:message key="navigation.chainActionSetDescriptions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Chain/ChainActionSet/DescriptionAdd">
                <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                <c:param name="ChainTypeName" value="${chainActionSet.chain.chainType.chainTypeName}" />
                <c:param name="ChainName" value="${chainActionSet.chain.chainName}" />
                <c:param name="ChainActionSetName" value="${chainActionSet.chainActionSetName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="chainActionSetDescriptions" id="chainActionSetDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${chainActionSetDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${chainActionSetDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Chain/ChainActionSet/DescriptionEdit">
                        <c:param name="ChainKindName" value="${chainActionSetDescription.chainActionSet.chain.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${chainActionSetDescription.chainActionSet.chain.chainType.chainTypeName}" />
                        <c:param name="ChainName" value="${chainActionSetDescription.chainActionSet.chain.chainName}" />
                        <c:param name="ChainActionSetName" value="${chainActionSetDescription.chainActionSet.chainActionSetName}" />
                        <c:param name="LanguageIsoName" value="${chainActionSetDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Chain/ChainActionSet/DescriptionDelete">
                        <c:param name="ChainKindName" value="${chainActionSetDescription.chainActionSet.chain.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${chainActionSetDescription.chainActionSet.chain.chainType.chainTypeName}" />
                        <c:param name="ChainName" value="${chainActionSetDescription.chainActionSet.chain.chainName}" />
                        <c:param name="ChainActionSetName" value="${chainActionSetDescription.chainActionSet.chainActionSetName}" />
                        <c:param name="LanguageIsoName" value="${chainActionSetDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
