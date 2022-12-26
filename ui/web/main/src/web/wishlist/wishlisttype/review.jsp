<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2023 Echo Three, LLC                                              -->
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
        <title>Review (<c:out value="${wishlistType.wishlistTypeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Wishlist/Main" />">Wishlists</a> &gt;&gt;
                <a href="<c:url value="/action/Wishlist/WishlistType/Main" />">Types</a> &gt;&gt;
                Review (<c:out value="${wishlistType.wishlistTypeName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${wishlistType.description}" /></b></font></p>
            <br />
            Wishlist Type Name: ${wishlistType.wishlistTypeName}<br />
            <br />
            <br />
            <br />
            <et:checkSecurityRoles securityRoles="Event.List" />
            <c:if test='${wishlistTypePriorities != null}'>
                <h2>Wishlist Type Priorities</h2>
                <display:table name="wishlistTypePriorities" id="wishlistTypePriority" class="displaytag">
                    <display:column titleKey="columnTitle.name">
                        <c:url var="reviewUrl" value="/action/Wishlist/WishlistTypePriority/Review">
                            <c:param name="WishlistTypeName" value="${wishlistTypePriority.wishlistType.wishlistTypeName}" />
                            <c:param name="WishlistTypePriorityName" value="${wishlistTypePriority.wishlistTypePriorityName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${wishlistTypePriority.wishlistTypePriorityName}" /></a>
                    </display:column>
                    <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                    <display:column titleKey="columnTitle.default">
                        <c:choose>
                            <c:when test="${wishlistTypePriority.isDefault}">
                                Default
                            </c:when>
                            <c:otherwise>
                                <c:url var="setDefaultUrl" value="/action/Wishlist/WishlistTypePriority/SetDefault">
                                    <c:param name="WishlistTypeName" value="${wishlistTypePriority.wishlistType.wishlistTypeName}" />
                                    <c:param name="WishlistTypePriorityName" value="${wishlistTypePriority.wishlistTypePriorityName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                    <display:column titleKey="columnTitle.description">
                        <c:out value="${wishlistTypePriority.description}" />
                    </display:column>
                    <display:column>
                        <c:url var="editUrl" value="/action/Wishlist/WishlistTypePriority/Edit">
                            <c:param name="WishlistTypeName" value="${wishlistTypePriority.wishlistType.wishlistTypeName}" />
                            <c:param name="OriginalWishlistTypePriorityName" value="${wishlistTypePriority.wishlistTypePriorityName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                        <c:url var="descriptionsUrl" value="/action/Wishlist/WishlistTypePriority/Description">
                            <c:param name="WishlistTypeName" value="${wishlistTypePriority.wishlistType.wishlistTypeName}" />
                            <c:param name="WishlistTypePriorityName" value="${wishlistTypePriority.wishlistTypePriorityName}" />
                        </c:url>
                        <a href="${descriptionsUrl}">Descriptions</a>
                        <c:url var="deleteUrl" value="/action/Wishlist/WishlistTypePriority/Delete">
                            <c:param name="WishlistTypeName" value="${wishlistTypePriority.wishlistType.wishlistTypeName}" />
                            <c:param name="WishlistTypePriorityName" value="${wishlistTypePriority.wishlistTypePriorityName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </display:column>
                    <et:hasSecurityRole securityRole="Event.List">
                        <display:column>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${wishlistTypePriority.entityInstance.entityRef}" />
                            </c:url>
                            <a href="${eventsUrl}">Events</a>
                        </display:column>
                    </et:hasSecurityRole>
                </display:table>
                <br />
            </c:if>
            Created: <c:out value="${wishlistType.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${wishlistType.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${wishlistType.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:if test='${wishlistType.entityInstance.entityTime.deletedTime != null}'>
                Deleted: <c:out value="${wishlistType.entityInstance.entityTime.deletedTime}" /><br />
            </c:if>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${wishlistType.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
