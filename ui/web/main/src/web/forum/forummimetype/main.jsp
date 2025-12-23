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
        <title>Mime Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Forum/Main" />">Forums</a> &gt;&gt;
                <a href="<c:url value="/action/Forum/Forum/Main" />">Forums</a> &gt;&gt;
                Mime Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Forum/ForumMimeType/Add">
                <c:param name="ForumName" value="${forum.forumName}" />
            </c:url>
            <p><a href="${addUrl}">Add Mime Type.</a></p>
            <display:table name="forumMimeTypes" id="forumMimeType" class="displaytag">
                <display:column titleKey="columnTitle.mimeType">
                    <c:url var="mimeTypeUrl" value="/action/Core/MimeType/Review">
                        <c:param name="MimeTypeName" value="${forumMimeType.mimeType.mimeTypeName}" />
                    </c:url>
                    <a href="${mimeTypeUrl}"><c:out value="${forumMimeType.mimeType.description}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${forumMimeType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Forum/ForumMimeType/SetDefault">
                                <c:param name="ForumName" value="${forumMimeType.forum.forumName}" />
                                <c:param name="MimeTypeName" value="${forumMimeType.mimeType.mimeTypeName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Forum/ForumMimeType/Edit">
                        <c:param name="ForumName" value="${forumMimeType.forum.forumName}" />
                        <c:param name="MimeTypeName" value="${forumMimeType.mimeType.mimeTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Forum/ForumMimeType/Delete">
                        <c:param name="ForumName" value="${forumMimeType.forum.forumName}" />
                        <c:param name="MimeTypeName" value="${forumMimeType.mimeType.mimeTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
