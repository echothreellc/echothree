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
        <title>Party Roles</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Forum/Main" />">Forums</a> &gt;&gt;
                <a href="<c:url value="/action/Forum/Forum/Main" />">Forums</a> &gt;&gt;
                Party Roles
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Forum/ForumPartyRole/Add">
                <c:param name="ForumName" value="${forum.forumName}" />
            </c:url>
            <p><a href="${addUrl}">Add Party Role.</a></p>
            <display:table name="forumPartyRoles" id="forumPartyRole" class="displaytag">
                <display:column titleKey="columnTitle.party">
                    <c:set var="party" scope="request" value="${forumPartyRole.party}" />
                    <jsp:include page="../../include/party.jsp" />
                </display:column>
                <display:column titleKey="columnTitle.forumRole">
                    <c:out value="${forumPartyRole.forumRole.description}" />
                </display:column>
                <display:column>
                    <c:url var="deleteUrl" value="/action/Forum/ForumPartyRole/Delete">
                        <c:param name="ForumName" value="${forumPartyRole.forum.forumName}" />
                        <c:param name="PartyName" value="${forumPartyRole.party.partyName}" />
                        <c:param name="ForumRoleName" value="${forumPartyRole.forumRole.forumRoleName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
