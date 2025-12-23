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
                <a href="<c:url value="/action/Forum/Main" />">Forums</a> &gt;&gt;
                Forums
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:Forum.Create:Forum.Edit:Forum.Delete:Forum.Review:Forum.Description" />
            <et:hasSecurityRole securityRole="Forum.Create">
                <p><a href="<c:url value="/action/Forum/Forum/Add" />">Add Forum.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Forum.Review" var="includeReviewUrl" />
            <display:table name="forums" id="forum" class="displaytag">
                <display:column>
                    <c:url var="feedUrl" value="/action/Forum/Forum/Feed">
                        <c:param name="ForumName" value="${forum.forumName}" />
                    </c:url>
                    <a href="${feedUrl}"><img src="../../stylesheets/feedicons-standard/feed-icon-14x14.png" width="14" height="14"></a>
                </display:column>
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Forum/Forum/Review">
                                <c:param name="ForumName" value="${forum.forumName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${forum.forumName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${forum.forumName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${forum.description}" />
                </display:column>
                <display:column titleKey="columnTitle.type">
                    <c:out value="${forum.forumType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <et:hasSecurityRole securityRoles="Forum.Edit:Forum.Delete:Forum.Description">
                    <display:column>
                        <c:url var="forumThreadsUrl" value="/action/Forum/ForumThread/Main">
                            <c:param name="ForumName" value="${forum.forumName}" />
                        </c:url>
                        <a href="${forumThreadsUrl}">Threads</a>
                        <c:url var="forumGroupForumsUrl" value="/action/Forum/ForumGroupForum/Main">
                            <c:param name="ForumName" value="${forum.forumName}" />
                        </c:url>
                        <a href="${forumGroupForumsUrl}">Forum Groups</a>
                        <c:url var="forumPartyRolesUrl" value="/action/Forum/ForumPartyRole/Main">
                            <c:param name="ForumName" value="${forum.forumName}" />
                        </c:url>
                        <a href="${forumPartyRolesUrl}">Party Roles</a>
                        <c:url var="forumPartyTypeRolesUrl" value="/action/Forum/ForumPartyTypeRole/Main">
                            <c:param name="ForumName" value="${forum.forumName}" />
                        </c:url>
                        <a href="${forumPartyTypeRolesUrl}">Party Type Roles</a>
                        <c:url var="forumMimeTypesUrl" value="/action/Forum/ForumMimeType/Main">
                            <c:param name="ForumName" value="${forum.forumName}" />
                        </c:url>
                        <a href="${forumMimeTypesUrl}">Mime Types</a>
                        <br />
                        <et:hasSecurityRole securityRole="Forum.Edit">
                            <c:url var="editUrl" value="/action/Forum/Forum/Edit">
                                <c:param name="OriginalForumName" value="${forum.forumName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Forum.Description">
                            <c:url var="descriptionsUrl" value="/action/Forum/Forum/Description">
                                <c:param name="ForumName" value="${forum.forumName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Forum.Delete">
                            <c:url var="deleteUrl" value="/action/Forum/Forum/Delete">
                                <c:param name="ForumName" value="${forum.forumName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${forum.entityInstance.entityRef}" />
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
