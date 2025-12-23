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
        <title>Items</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Club/Main" />">Clubs</a> &gt;&gt;
                <a href="<c:url value="/action/Club/Club/Main" />">Clubs</a> &gt;&gt;
                Items
            </h2>
        </div>
        <div id="Content">
            <display:table name="clubItems" id="clubItem" class="displaytag">
                <display:column titleKey="columnTitle.clubItemType">
                    <c:out value="${clubItem.clubItemType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.itemDescription">
                    <c:out value="${clubItem.item.description}" />
                </display:column>
                <display:column titleKey="columnTitle.subscriptionTime">
                    <c:out value="${clubItem.subscriptionTime}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Club/ClubItem/Edit">
                        <c:param name="ClubName" value="${clubItem.club.clubName}" />
                        <c:param name="ClubItemTypeName" value="${clubItem.clubItemType.clubItemTypeName}" />
                        <c:param name="ItemName" value="${clubItem.item.itemName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Club/ClubItem/Delete">
                        <c:param name="ClubName" value="${clubItem.club.clubName}" />
                        <c:param name="ClubItemTypeName" value="${clubItem.clubItemType.clubItemTypeName}" />
                        <c:param name="ItemName" value="${clubItem.item.itemName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
