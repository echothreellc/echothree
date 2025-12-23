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
        <title>Review (<c:out value="${contentCatalog.contentCatalogName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />">Collections</a> &gt;&gt;
                <c:url var="contentCatalogsUrl" value="/action/Content/ContentCatalog/Main">
                    <c:param name="ContentCollectionName" value="${contentCatalog.contentCollection.contentCollectionName}" />
                </c:url>
                <a href="${contentCatalogsUrl}">Catalogs</a> &gt;&gt;
                Review (<c:out value="${contentCatalog.contentCatalogName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${contentCatalog.description}" /></b></font></p>
            <br />
            Content Collection Name: ${contentCatalog.contentCollection.contentCollectionName}<br />
            Content Catalog Name: ${contentCatalog.contentCatalogName}<br />
            <br />
            <br />
            <br />
            <c:set var="tagScopes" scope="request" value="${contentCatalog.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${contentCatalog.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${contentCatalog.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Content/ContentCatalog/Review">
                <c:param name="ContentCollectionName" value="${contentCatalog.contentCollection.contentCollectionName}" />
                <c:param name="ContentCatalogName" value="${contentCatalog.contentCatalogName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
