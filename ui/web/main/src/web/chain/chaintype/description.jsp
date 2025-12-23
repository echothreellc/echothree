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
        <title><fmt:message key="pageTitle.chainTypeDescriptions" /></title>
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
                    <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}"><fmt:message key="navigation.chainTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.chainTypeDescriptions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Chain/ChainType/DescriptionAdd">
                <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                <c:param name="ChainTypeName" value="${chainType.chainTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="chainTypeDescriptions" id="chainTypeDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${chainTypeDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${chainTypeDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Chain/ChainType/DescriptionEdit">
                        <c:param name="ChainKindName" value="${chainTypeDescription.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${chainTypeDescription.chainType.chainTypeName}" />
                        <c:param name="LanguageIsoName" value="${chainTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Chain/ChainType/DescriptionDelete">
                        <c:param name="ChainKindName" value="${chainTypeDescription.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${chainTypeDescription.chainType.chainTypeName}" />
                        <c:param name="LanguageIsoName" value="${chainTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
