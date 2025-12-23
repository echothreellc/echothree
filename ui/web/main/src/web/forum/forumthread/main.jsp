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
        <title>Forum Threads</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Forum/Main" />">Forums</a> &gt;&gt;
                <a href="<c:url value="/action/Forum/Forum/Main" />">Forums</a> &gt;&gt;
                Forum Threads
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Forum/ForumThread/Add">
                <c:param name="ForumName" value="${forum.forumName}" />
            </c:url>
            <p><a href="${addUrl}">Add Forum Thread.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="forumThreads.list" id="forumThread" class="displaytag" partialList="true" pagesize="5" size="forumThreadCount" requestURI="/action/Forum/ForumThread/Main">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Forum/ForumThread/Review">
                        <c:param name="ForumThreadName" value="${forumThread.forumThreadName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${forumThread.forumThreadName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.postedTime">
                    <c:out value="${forumThread.postedTime}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.messages">
                    <c:set var="nestedMessagesName" value="forumThreads.list[${forumThread_rowNum - 1}].forumMessages.list" />
                    <display:table name="${nestedMessagesName}" id="forumMessage" class="displaytag">
                        <display:column titleKey="columnTitle.name">
                            <c:url var="reviewUrl" value="/action/Forum/ForumMessage/Review">
                                <c:param name="ForumMessageName" value="${forumMessage.forumMessageName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${forumMessage.forumMessageName}" /></a>
                        </display:column>
                        <display:column>
                            Title: <c:out value="${forumMessage.forumMessageParts.map['TITLE'].string}" /><br />
                            Posted Time: <c:out value="${forumMessage.postedTime}" />
                            <c:if test="${forumMessage.forumMessageRoles.size > 0}">
                                <br />
                                <c:forEach items="${forumMessage.forumMessageRoles.list}" var="forumMessageRole">
                                    <br /><c:out value="${forumMessageRole.forumRoleType.description}" />:
                                    <c:choose>
                                        <c:when test='${forumMessageRole.party.person.firstName != null || forumMessageRole.party.person.middleName != null || forumMessageRole.party.person.lastName != null}'>
                                            <c:out value="${forumMessageRole.party.person.personalTitle.description}" /> <c:out value="${forumMessageRole.party.person.firstName}" />
                                            <c:out value="${forumMessageRole.party.person.middleName}" /> <c:out value="${forumMessageRole.party.person.lastName}" />
                                            <c:out value="${forumMessageRole.party.person.nameSuffix.description}" />
                                            <c:if test='${forumMessageRole.party.profile.nickname != null}'>
                                                (<c:out value="${forumMessageRole.party.profile.nickname}" />)
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${forumMessageRole.party.partyType.description}" />
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:if>
                        </display:column>
                        <display:column>
                            <c:url var="replyUrl" value="/action/Forum/ForumMessage/Add">
                                <c:param name="ForumName" value="${forum.forumName}" />
                                <c:param name="ParentForumMessageName" value="${forumMessage.forumMessageName}" />
                            </c:url>
                            <a href="${replyUrl}">Reply</a><br />
                            <c:url var="editUrl" value="/action/Forum/ForumMessage/Edit">
                                <c:param name="ForumName" value="${forum.forumName}" />
                                <c:param name="ForumMessageName" value="${forumMessage.forumMessageName}" />
                                <c:if test="${dtIdAttribute != null}"><c:param name="DtIdAttribute" value="${dtIdAttribute}" /></c:if>
                                <c:if test="${dtSortParameter != null}"><c:param name="DtSortParameter" value="${dtSortParameter}" /></c:if>
                                <c:if test="${dtPageParameter != null}"><c:param name="DtPageParameter" value="${dtPageParameter}" /></c:if>
                                <c:if test="${dtOrderParameter != null}"><c:param name="DtOrderParameter" value="${dtOrderParameter}" /></c:if>
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Forum/ForumMessage/Delete">
                                <c:param name="ForumName" value="${forum.forumName}" />
                                <c:param name="ForumMessageName" value="${forumMessage.forumMessageName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column>
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${forumMessage.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                    </display:table>
                </display:column>
                <display:column>
                    <c:url var="forumForumThreadsUrl" value="/action/Forum/ForumForumThread/Main">
                        <c:param name="ForumThreadName" value="${forumThread.forumThreadName}" />
                    </c:url>
                    <a href="${forumForumThreadsUrl}">Forums</a><br />
                    <c:url var="editUrl" value="/action/Forum/ForumThread/Edit">
                        <c:param name="ForumName" value="${forum.forumName}" />
                        <c:param name="ForumThreadName" value="${forumThread.forumThreadName}" />
                        <c:if test="${dtIdAttribute != null}"><c:param name="DtIdAttribute" value="${dtIdAttribute}" /></c:if>
                        <c:if test="${dtSortParameter != null}"><c:param name="DtSortParameter" value="${dtSortParameter}" /></c:if>
                        <c:if test="${dtPageParameter != null}"><c:param name="DtPageParameter" value="${dtPageParameter}" /></c:if>
                        <c:if test="${dtOrderParameter != null}"><c:param name="DtOrderParameter" value="${dtOrderParameter}" /></c:if>
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Forum/ForumThread/Delete">
                        <c:param name="ForumName" value="${forum.forumName}" />
                        <c:param name="ForumThreadName" value="${forumThread.forumThreadName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${forumThread.entityInstance.entityRef}" />
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
