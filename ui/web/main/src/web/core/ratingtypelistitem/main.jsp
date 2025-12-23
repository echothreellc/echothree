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
        <title>List Items</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />">Component Vendors</a> &gt;&gt;
                <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}">Entity Types</a> &gt;&gt;
                <c:url var="ratingTypesUrl" value="/action/Core/RatingType/Main">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityTypeName}" />
                </c:url>
                <a href="${ratingTypesUrl}">Rating Types</a> &gt;&gt;
                List Items
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Core/RatingTypeListItem/Add">
                <c:param name="ComponentVendorName" value="${componentVendorName}" />
                <c:param name="EntityTypeName" value="${entityTypeName}" />
                <c:param name="RatingTypeName" value="${ratingTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add List Item.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="ratingTypeListItems" id="ratingTypeListItem" class="displaytag">
                <display:column property="ratingTypeListItemName" titleKey="columnTitle.name" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${ratingTypeListItem.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${ratingTypeListItem.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Core/RatingTypeListItem/SetDefault">
                                <c:param name="ComponentVendorName" value="${ratingTypeListItem.ratingType.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${ratingTypeListItem.ratingType.entityType.entityTypeName}" />
                                <c:param name="RatingTypeName" value="${ratingTypeListItem.ratingType.ratingTypeName}" />
                                <c:param name="RatingTypeListItemName" value="${ratingTypeListItem.ratingTypeListItemName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Core/RatingTypeListItem/Edit">
                        <c:param name="ComponentVendorName" value="${ratingTypeListItem.ratingType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${ratingTypeListItem.ratingType.entityType.entityTypeName}" />
                        <c:param name="RatingTypeName" value="${ratingTypeListItem.ratingType.ratingTypeName}" />
                        <c:param name="OriginalRatingTypeListItemName" value="${ratingTypeListItem.ratingTypeListItemName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Core/RatingTypeListItem/Description">
                        <c:param name="ComponentVendorName" value="${ratingTypeListItem.ratingType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${ratingTypeListItem.ratingType.entityType.entityTypeName}" />
                        <c:param name="RatingTypeName" value="${ratingTypeListItem.ratingType.ratingTypeName}" />
                        <c:param name="RatingTypeListItemName" value="${ratingTypeListItem.ratingTypeListItemName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Core/RatingTypeListItem/Delete">
                        <c:param name="ComponentVendorName" value="${ratingTypeListItem.ratingType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${ratingTypeListItem.ratingType.entityType.entityTypeName}" />
                        <c:param name="RatingTypeName" value="${ratingTypeListItem.ratingType.ratingTypeName}" />
                        <c:param name="RatingTypeListItemName" value="${ratingTypeListItem.ratingTypeListItemName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${ratingTypeListItem.entityInstance.entityRef}" />
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
