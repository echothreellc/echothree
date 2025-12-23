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
        <title>
            <fmt:message key="pageTitle.searchType">
                <fmt:param value="${searchType.searchTypeName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/SearchKind/Main" />"><fmt:message key="navigation.searchKinds" /></a> &gt;&gt;
                <c:url var="searchTypesUrl" value="/action/Configuration/SearchType/Main">
                    <c:param name="SearchKindName" value="${searchType.searchKind.searchKindName}" />
                </c:url>
                <a href="${searchTypesUrl}"><fmt:message key="navigation.searchTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.searchType">
                    <fmt:param value="${searchType.searchTypeName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${searchType.description}" /></b></font></p>
            <br />
            Search Kind Name: ${searchType.searchKind.searchKindName}<br />
            Search Type Name: ${searchType.searchTypeName}<br />
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${searchType.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Configuration/SearchKind/Review">
                <c:param name="SearchKindName" value="${searchType.searchKind.searchKindName}" />
                <c:param name="SearchTypeName" value="${searchType.searchTypeName}" />
            </c:url>
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
