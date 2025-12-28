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
        <title>Entity Attributes</title>
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
                Entity Alias Types
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:EntityAliasType.Create:EntityAliasType.Edit:EntityAliasType.Delete:EntityAliasType.Review:EntityAliasType.Description" />
            <et:hasSecurityRole securityRoles="EntityAliasType.Edit:EntityAliasType.Description:EntityAliasType.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityAliasType.Create">
                <c:url var="addUrl" value="/action/Core/EntityAliasType/Add">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityTypeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Entity Alias Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityAliasType.Review" var="includeReviewUrl" />
            <display:table name="entityAliasTypes" id="entityAliasType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/EntityAliasType/Review">
                                <c:param name="ComponentVendorName" value="${entityAliasType.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityAliasType.entityType.entityTypeName}" />
                                <c:param name="EntityAliasTypeName" value="${entityAliasType.entityAliasTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><et:appearance appearance="${entityAliasType.entityInstance.entityAppearance.appearance}"><c:out value="${entityAliasType.entityAliasTypeName}" /></et:appearance></a>
                        </c:when>
                        <c:otherwise>
                            <et:appearance appearance="${entityAliasType.entityInstance.entityAppearance.appearance}"><c:out value="${entityAliasType.entityAliasTypeName}" /></et:appearance>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <et:appearance appearance="${entityAliasType.entityInstance.entityAppearance.appearance}"><c:out value="${entityAliasType.description}" /></et:appearance>
                </display:column>
                <display:column titleKey="columnTitle.validationPattern">
                    <c:out value="${entityAliasType.validationPattern}" />
                </display:column>
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${entityAliasType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="EntityAliasType.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/EntityAliasType/SetDefault">
                                    <c:param name="ComponentVendorName" value="${entityAliasType.entityType.componentVendor.componentVendorName}" />
                                    <c:param name="EntityTypeName" value="${entityAliasType.entityType.entityTypeName}" />
                                    <c:param name="EntityAliasTypeName" value="${entityAliasType.entityAliasTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.sortOrder">
                    <c:out value="${entityAliasType.sortOrder}" />
                </display:column>
                <display:column>
                    <et:hasSecurityRole securityRole="EntityAliasType.Edit">
                        <c:url var="editUrl" value="/action/Core/EntityAliasType/Edit">
                            <c:param name="ComponentVendorName" value="${entityAliasType.entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityAliasType.entityType.entityTypeName}" />
                            <c:param name="OriginalEntityAliasTypeName" value="${entityAliasType.entityAliasTypeName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="EntityAliasType.Description">
                        <c:url var="descriptionsUrl" value="/action/Core/EntityAliasType/Description">
                            <c:param name="ComponentVendorName" value="${entityAliasType.entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityAliasType.entityType.entityTypeName}" />
                            <c:param name="EntityAliasTypeName" value="${entityAliasType.entityAliasTypeName}" />
                        </c:url>
                        <a href="${descriptionsUrl}">Descriptions</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="EntityAliasType.Delete">
                        <c:url var="deleteUrl" value="/action/Core/EntityAliasType/Delete">
                            <c:param name="ComponentVendorName" value="${entityAliasType.entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityAliasType.entityType.entityTypeName}" />
                            <c:param name="EntityAliasTypeName" value="${entityAliasType.entityAliasTypeName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </et:hasSecurityRole>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${entityAliasType.entityInstance.entityRef}" />
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
