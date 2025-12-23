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
        <title>Forums</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />">Collections</a> &gt;&gt;
                Forums
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <c:url var="addUrl" value="/action/Content/ContentForum/Add">
                <c:param name="ContentCollectionName" value="${contentCollectionName}" />
            </c:url>
            <p><a href="${addUrl}">Add Forum.</a></p>
            <display:table name="contentForums" id="contentForum" class="displaytag">
                <display:column property="forum.forumName" titleKey="columnTitle.name" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${contentForum.forum.description}" />
                </display:column>
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${contentForum.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultContentForumUrl" value="/action/Content/ContentForum/SetDefault">
                                <c:param name="ContentCollectionName" value="${contentForum.contentCollection.contentCollectionName}" />
                                <c:param name="ForumName" value="${contentForum.forum.forumName}" />
                            </c:url>
                            <a href="${setDefaultContentForumUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="deleteContentForumUrl" value="/action/Content/ContentForum/Delete">
                        <c:param name="ContentCollectionName" value="${contentForum.contentCollection.contentCollectionName}" />
                        <c:param name="ForumName" value="${contentForum.forum.forumName}" />
                    </c:url>
                    <a href="${deleteContentForumUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${contentForum.entityInstance.entityRef}" />
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
