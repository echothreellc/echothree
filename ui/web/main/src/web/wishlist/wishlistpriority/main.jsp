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
        <title>Priorities</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Wishlist/Main" />">Wishlists</a> &gt;&gt;
                <a href="<c:url value="/action/Wishlist/WishlistType/Main" />">Types</a> &gt;&gt;
                Priorities
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Wishlist/WishlistPriority/Add">
                <c:param name="WishlistTypeName" value="${wishlistType.wishlistTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Priority.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="wishlistPriorities" id="wishlistPriority" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Wishlist/WishlistPriority/Review">
                        <c:param name="WishlistTypeName" value="${wishlistPriority.wishlistType.wishlistTypeName}" />
                        <c:param name="WishlistPriorityName" value="${wishlistPriority.wishlistPriorityName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${wishlistPriority.wishlistPriorityName}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${wishlistPriority.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Wishlist/WishlistPriority/SetDefault">
                                <c:param name="WishlistTypeName" value="${wishlistPriority.wishlistType.wishlistTypeName}" />
                                <c:param name="WishlistPriorityName" value="${wishlistPriority.wishlistPriorityName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${wishlistPriority.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Wishlist/WishlistPriority/Edit">
                        <c:param name="WishlistTypeName" value="${wishlistPriority.wishlistType.wishlistTypeName}" />
                        <c:param name="OriginalWishlistPriorityName" value="${wishlistPriority.wishlistPriorityName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Wishlist/WishlistPriority/Description">
                        <c:param name="WishlistTypeName" value="${wishlistPriority.wishlistType.wishlistTypeName}" />
                        <c:param name="WishlistPriorityName" value="${wishlistPriority.wishlistPriorityName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Wishlist/WishlistPriority/Delete">
                        <c:param name="WishlistTypeName" value="${wishlistPriority.wishlistType.wishlistTypeName}" />
                        <c:param name="WishlistPriorityName" value="${wishlistPriority.wishlistPriorityName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${wishlistPriority.entityInstance.entityRef}" />
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
