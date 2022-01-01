<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2022 Echo Three, LLC                                              -->
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
        <title>Priority Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Wishlist/Main" />">Wishlists</a> &gt;&gt;
                <a href="<c:url value="/action/Wishlist/WishlistType/Main" />">Types</a> &gt;&gt;
                <c:url var="wishlistTypePrioritiesUrl" value="/action/Wishlist/WishlistTypePriority/Main">
                    <c:param name="WishlistTypeName" value="${wishlistTypePriority.wishlistType.wishlistTypeName}" />
                </c:url>
                <a href="${wishlistTypePrioritiesUrl}">Priorities</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Wishlist/WishlistTypePriority/DescriptionAdd">
                <c:param name="WishlistTypeName" value="${wishlistTypePriority.wishlistType.wishlistTypeName}" />
                <c:param name="WishlistTypePriorityName" value="${wishlistTypePriority.wishlistTypePriorityName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="wishlistTypePriorityDescriptions" id="wishlistTypePriorityDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${wishlistTypePriorityDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${wishlistTypePriorityDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Wishlist/WishlistTypePriority/DescriptionEdit">
                        <c:param name="WishlistTypeName" value="${wishlistTypePriorityDescription.wishlistTypePriority.wishlistType.wishlistTypeName}" />
                        <c:param name="WishlistTypePriorityName" value="${wishlistTypePriorityDescription.wishlistTypePriority.wishlistTypePriorityName}" />
                        <c:param name="LanguageIsoName" value="${wishlistTypePriorityDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Wishlist/WishlistTypePriority/DescriptionDelete">
                        <c:param name="WishlistTypeName" value="${wishlistTypePriorityDescription.wishlistTypePriority.wishlistType.wishlistTypeName}" />
                        <c:param name="WishlistTypePriorityName" value="${wishlistTypePriorityDescription.wishlistTypePriority.wishlistTypePriorityName}" />
                        <c:param name="LanguageIsoName" value="${wishlistTypePriorityDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
