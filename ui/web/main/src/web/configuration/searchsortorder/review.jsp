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
            <fmt:message key="pageTitle.searchSortOrder">
                <fmt:param value="${searchSortOrder.searchSortOrderName}" />
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
                <c:url var="searchSortOrdersUrl" value="/action/Configuration/SearchSortOrder/Main">
                    <c:param name="SearchKindName" value="${searchSortOrder.searchKind.searchKindName}" />
                </c:url>
                <a href="${searchSortOrdersUrl}"><fmt:message key="navigation.searchSortOrders" /></a> &gt;&gt;
                <fmt:message key="navigation.searchSortOrder">
                    <fmt:param value="${searchSortOrder.searchSortOrderName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${searchSortOrder.description}" /></b></font></p>
            <br />
            Search Kind Name: ${searchSortOrder.searchKind.searchKindName}<br />
            Search Type Name: ${searchSortOrder.searchSortOrderName}<br />
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${searchSortOrder.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Configuration/SearchKind/Review">
                <c:param name="SearchKindName" value="${searchSortOrder.searchKind.searchKindName}" />
                <c:param name="SearchSortOrderName" value="${searchSortOrder.searchSortOrderName}" />
            </c:url>
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
