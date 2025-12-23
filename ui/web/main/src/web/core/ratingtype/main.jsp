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
        <title>Rating Types</title>
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
                Rating Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Core/RatingType/Add">
                <c:param name="ComponentVendorName" value="${componentVendorName}" />
                <c:param name="EntityTypeName" value="${entityTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Rating Type.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="ratingTypes" id="ratingType" class="displaytag">
                <display:column property="ratingTypeName" titleKey="columnTitle.name" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${ratingType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.ratingSequence">
                    <c:out value="${ratingType.ratingSequence.description}" />
                </display:column>
                <display:column titleKey="columnTitle.sortOrder">
                    <c:out value="${ratingType.sortOrder}" />
                </display:column>
                <display:column>
                    <c:url var="ratingTypeListItemsUrl" value="/action/Core/RatingTypeListItem/Main">
                        <c:param name="ComponentVendorName" value="${ratingType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${ratingType.entityType.entityTypeName}" />
                        <c:param name="RatingTypeName" value="${ratingType.ratingTypeName}" />
                    </c:url>
                    <a href="${ratingTypeListItemsUrl}">List Items</a><br />
                    <c:url var="editUrl" value="/action/Core/RatingType/Edit">
                        <c:param name="ComponentVendorName" value="${ratingType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${ratingType.entityType.entityTypeName}" />
                        <c:param name="OriginalRatingTypeName" value="${ratingType.ratingTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Core/RatingType/Description">
                        <c:param name="ComponentVendorName" value="${ratingType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${ratingType.entityType.entityTypeName}" />
                        <c:param name="RatingTypeName" value="${ratingType.ratingTypeName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Core/RatingType/Delete">
                        <c:param name="ComponentVendorName" value="${ratingType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${ratingType.entityType.entityTypeName}" />
                        <c:param name="RatingTypeName" value="${ratingType.ratingTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${ratingType.entityInstance.entityRef}" />
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
