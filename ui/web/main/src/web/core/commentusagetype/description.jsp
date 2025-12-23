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
        <title>Comment Usage Type Descriptions</title>
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
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
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
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Core/CommentUsageType/DescriptionAdd">
                <c:param name="ComponentVendorName" value="${commentUsageType.commentType.entityType.componentVendor.componentVendorName}" />
                <c:param name="EntityTypeName" value="${commentUsageType.commentType.entityType.entityTypeName}" />
                <c:param name="CommentTypeName" value="${commentUsageType.commentType.commentTypeName}" />
                <c:param name="CommentUsageTypeName" value="${commentUsageType.commentUsageTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="commentUsageTypeDescriptions" id="commentUsageTypeDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${commentUsageTypeDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${commentUsageTypeDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Core/CommentUsageType/DescriptionEdit">
                        <c:param name="ComponentVendorName" value="${commentUsageTypeDescription.commentUsageType.commentType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${commentUsageTypeDescription.commentUsageType.commentType.entityType.entityTypeName}" />
                        <c:param name="CommentTypeName" value="${commentUsageTypeDescription.commentUsageType.commentType.commentTypeName}" />
                        <c:param name="CommentUsageTypeName" value="${commentUsageTypeDescription.commentUsageType.commentUsageTypeName}" />
                        <c:param name="LanguageIsoName" value="${commentUsageTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Core/CommentUsageType/DescriptionDelete">
                        <c:param name="ComponentVendorName" value="${commentUsageTypeDescription.commentUsageType.commentType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${commentUsageTypeDescription.commentUsageType.commentType.entityType.entityTypeName}" />
                        <c:param name="CommentTypeName" value="${commentUsageTypeDescription.commentUsageType.commentType.commentTypeName}" />
                        <c:param name="CommentUsageTypeName" value="${commentUsageTypeDescription.commentUsageType.commentUsageTypeName}" />
                        <c:param name="LanguageIsoName" value="${commentUsageTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
