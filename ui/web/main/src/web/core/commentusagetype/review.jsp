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
        <title>Review (<c:out value="${commentUsageType.commentUsageTypeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />">Component Vendors</a> &gt;&gt;
                <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                    <c:param name="ComponentVendorName" value="${commentUsageType.commentType.entityType.componentVendor.componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}">Entity Types</a> &gt;&gt;
                <c:url var="commentTypesUrl" value="/action/Core/CommentType/Main">
                    <c:param name="ComponentVendorName" value="${commentUsageType.commentType.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${commentUsageType.commentType.entityType.entityTypeName}" />
                </c:url>
                <a href="${commentTypesUrl}">Comment Types</a> &gt;&gt;
                <c:url var="commentUsageTypeUrl" value="/action/Core/CommentUsageType/Main">
                    <c:param name="ComponentVendorName" value="${commentUsageType.commentType.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${commentUsageType.commentType.entityType.entityTypeName}" />
                    <c:param name="CommentTypeName" value="${commentUsageType.commentType.commentTypeName}" />
                </c:url>
                <a href="${commentUsageTypeUrl}">Comment Usage Types</a> &gt;&gt;
                Review (<c:out value="${commentUsageType.commentUsageTypeName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${commentUsageType.description}" /></b></font></p>
            <br />
            Comment Usage Type Name: ${commentUsageType.commentUsageTypeName}<br />
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${commentUsageType.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Core/CommentUsageType/Review">
                <c:param name="CommentUsageTypeName" value="${commentUsageType.commentUsageTypeName}" />
            </c:url>
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
