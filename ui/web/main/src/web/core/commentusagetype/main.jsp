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
        <title>Comment Usage Types</title>
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
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityTypeName}" />
                </c:url>
                <a href="${commentTypesUrl}">Comment Types</a> &gt;&gt;
                Comment Usage Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Core/CommentUsageType/Add">
                <c:param name="ComponentVendorName" value="${componentVendorName}" />
                <c:param name="EntityTypeName" value="${entityTypeName}" />
                <c:param name="CommentTypeName" value="${commentTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Comment Usage Type.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="commentUsageTypes" id="commentUsageType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Core/CommentUsageType/Review">
                        <c:param name="ComponentVendorName" value="${commentUsageType.commentType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${commentUsageType.commentType.entityType.entityTypeName}" />
                        <c:param name="CommentTypeName" value="${commentUsageType.commentType.commentTypeName}" />
                        <c:param name="CommentUsageTypeName" value="${commentUsageType.commentUsageTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${commentUsageType.commentUsageTypeName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${commentUsageType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.selectedByDefault">
                    <c:choose>
                        <c:when test="${commentUsageType.selectedByDefault}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Core/CommentUsageType/Edit">
                        <c:param name="ComponentVendorName" value="${commentUsageType.commentType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${commentUsageType.commentType.entityType.entityTypeName}" />
                        <c:param name="CommentTypeName" value="${commentUsageType.commentType.commentTypeName}" />
                        <c:param name="OriginalCommentUsageTypeName" value="${commentUsageType.commentUsageTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Core/CommentUsageType/Description">
                        <c:param name="ComponentVendorName" value="${commentUsageType.commentType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${commentUsageType.commentType.entityType.entityTypeName}" />
                        <c:param name="CommentTypeName" value="${commentUsageType.commentType.commentTypeName}" />
                        <c:param name="CommentUsageTypeName" value="${commentUsageType.commentUsageTypeName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Core/CommentUsageType/Delete">
                        <c:param name="ComponentVendorName" value="${commentUsageType.commentType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${commentUsageType.commentType.entityType.entityTypeName}" />
                        <c:param name="CommentTypeName" value="${commentUsageType.commentType.commentTypeName}" />
                        <c:param name="CommentUsageTypeName" value="${commentUsageType.commentUsageTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${commentUsageType.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
