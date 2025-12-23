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
        <title>Forum Groups</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Forum/Main" />">Forums</a> &gt;&gt;
                Groups
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ForumGroup.Create:ForumGroup.Edit:ForumGroup.Delete:ForumGroup.Review:ForumGroup.Description" />
            <et:hasSecurityRole securityRole="ForumGroup.Create">
                <p><a href="<c:url value="/action/Forum/ForumGroup/Add" />">Add Group.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ForumGroup.Review" var="includeReviewUrl" />
            <display:table name="forumGroups" id="forumGroup" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Forum/ForumGroup/Review">
                                <c:param name="ForumGroupName" value="${forumGroup.forumGroupName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${forumGroup.forumGroupName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${forumGroup.forumGroupName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${forumGroup.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <et:hasSecurityRole securityRoles="ForumGroup.Edit:ForumGroup.Delete:ForumGroup.Description">
                    <display:column>
                        <et:hasSecurityRole securityRole="ForumGroup.Edit">
                            <c:url var="editUrl" value="/action/Forum/ForumGroup/Edit">
                                <c:param name="OriginalForumGroupName" value="${forumGroup.forumGroupName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ForumGroup.Description">
                            <c:url var="descriptionsUrl" value="/action/Forum/ForumGroup/Description">
                                <c:param name="ForumGroupName" value="${forumGroup.forumGroupName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ForumGroup.Delete">
                            <c:url var="deleteUrl" value="/action/Forum/ForumGroup/Delete">
                                <c:param name="ForumGroupName" value="${forumGroup.forumGroupName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${forumGroup.entityInstance.entityRef}" />
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
