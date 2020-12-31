<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2021 Echo Three, LLC                                              -->
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
        <title>Entity Attributes</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />">Component Vendors</a> &gt;&gt;
                <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}">Entity Types</a> &gt;&gt;
                Entity Attributes
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:EntityAttribute.Create:EntityAttribute.Edit:EntityAttribute.Delete:EntityAttribute.Review:EntityAttribute.Description:EntityAttribute.EntityAttributeEntityAttributeGroup:EntityAttributeEntityType.List:EntityListItem.List:EntityIntegerRange.List:EntityLongRange.List" />
            <et:hasSecurityRole securityRoles="EntityAttribute.Edit:EntityAttribute.Description:EntityAttribute.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityAttribute.Create">
                <c:url var="addUrl" value="/action/Core/EntityAttribute/Add">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityTypeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Entity Attribute.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityAttribute.Review" var="includeReviewUrl" />
            <display:table name="entityAttributes" id="entityAttribute" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/EntityAttribute/Review">
                                <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${entityAttribute.entityAttributeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${entityAttribute.entityAttributeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${entityAttribute.description}" />
                </display:column>
                <display:column titleKey="columnTitle.type">
                    <c:out value="${entityAttribute.entityAttributeType.description}" />
                </display:column>
                <display:column>
                    <et:hasSecurityRole securityRole="EntityAttribute.EntityAttributeEntityAttributeGroup">
                        <c:url var="entityAttributeGroupsUrl" value="/action/Core/EntityAttribute/EntityAttributeGroup">
                            <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                        </c:url>
                        <a href="${entityAttributeGroupsUrl}">Groups</a>
                        <c:set var="hasLinksInFirstRow" value="true" />
                    </et:hasSecurityRole>
                    <c:set value="${entityAttribute.entityAttributeType.entityAttributeTypeName}" var="entityAttributeTypeName" />
                    <et:hasSecurityRole securityRole="EntityAttributeEntityType.List">
                        <c:if test="${entityAttributeTypeName == 'ENTITY' || entityAttributeTypeName == 'COLLECTION'}">
                            <c:url var="entityListItemsUrl" value="/action/Core/EntityAttributeEntityType/Main">
                                <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                            </c:url>
                            <a href="${entityListItemsUrl}">Entity Types</a>
                            <c:set var="hasLinksInFirstRow" value="true" />
                        </c:if>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="EntityListItem.List">
                        <c:if test="${entityAttributeTypeName == 'LISTITEM' || entityAttributeTypeName == 'MULTIPLELISTITEM'}">
                            <c:url var="entityListItemsUrl" value="/action/Core/EntityListItem/Main">
                                <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                            </c:url>
                            <a href="${entityListItemsUrl}">List Items</a>
                            <c:set var="hasLinksInFirstRow" value="true" />
                        </c:if>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="EntityIntegerRange.List">
                        <c:if test="${entityAttributeTypeName == 'INTEGER'}">
                            <c:url var="entityIntegerRangesUrl" value="/action/Core/EntityIntegerRange/Main">
                                <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                            </c:url>
                            <a href="${entityIntegerRangesUrl}">Ranges</a>
                            <c:set var="hasLinksInFirstRow" value="true" />
                        </c:if>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="EntityLongRange.List">
                        <c:if test="${entityAttributeTypeName == 'LONG'}">
                            <c:url var="entityLongRangesUrl" value="/action/Core/EntityLongRange/Main">
                                <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                            </c:url>
                            <a href="${entityLongRangesUrl}">Ranges</a>
                            <c:set var="hasLinksInFirstRow" value="true" />
                        </c:if>
                    </et:hasSecurityRole>
                    <c:if test="${hasLinksInFirstRow && linksInSecondRow}">
                        <br />
                    </c:if>
                    <et:hasSecurityRole securityRole="EntityAttribute.Edit">
                        <c:url var="editUrl" value="/action/Core/EntityAttribute/Edit">
                            <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                            <c:param name="OriginalEntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="EntityAttribute.Description">
                        <c:url var="descriptionsUrl" value="/action/Core/EntityAttribute/Description">
                            <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                        </c:url>
                        <a href="${descriptionsUrl}">Descriptions</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="EntityAttribute.Delete">
                        <c:url var="deleteUrl" value="/action/Core/EntityAttribute/Delete">
                            <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </et:hasSecurityRole>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${entityAttribute.entityInstance.entityRef}" />
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
