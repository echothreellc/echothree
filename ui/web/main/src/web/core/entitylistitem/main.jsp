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
        <title>Entity List Items</title>
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
                <c:url var="entityAttributesUrl" value="/action/Core/EntityAttribute/Main">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityTypeName}" />
                </c:url>
                <a href="${entityAttributesUrl}">Entity Attributes</a> &gt;&gt;
                Entity List Items
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:EntityListItem.Create:EntityListItem.Edit:EntityListItem.Delete:EntityListItem.Review:EntityListItem.Description" />
            <et:hasSecurityRole securityRoles="EntityListItem.Edit:EntityListItem.Description:EntityListItem.Delete">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityListItem.Create">
                <c:url var="addUrl" value="/action/Core/EntityListItem/Add">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityAttributeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Entity List Item.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityListItem.Review" var="includeReviewUrl" />
            <display:table name="entityListItems" id="entityListItem" class="displaytag" export="true" sort="list" requestURI="/action/Core/EntityListItem/Main">
                <display:setProperty name="export.csv.filename" value="EntityListItems.csv" />
                <display:setProperty name="export.excel.filename" value="EntityListItems.xls" />
                <display:setProperty name="export.pdf.filename" value="EntityListItems.pdf" />
                <display:setProperty name="export.rtf.filename" value="EntityListItems.rtf" />
                <display:setProperty name="export.xml.filename" value="EntityListItems.xml" />
                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="entityListItemName">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/EntityListItem/Review">
                                <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                                <c:param name="EntityListItemName" value="${entityListItem.entityListItemName}" />
                            </c:url>
                            <a href="${reviewUrl}"><et:appearance appearance="${entityListItem.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.entityListItemName}" /></et:appearance></a>
                        </c:when>
                        <c:otherwise>
                            <et:appearance appearance="${entityListItem.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.entityListItemName}" /></et:appearance>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description" media="html" sortable="true" sortProperty="description">
                    <et:appearance appearance="${entityListItem.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.description}" /></et:appearance>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="html" sortable="true" sortProperty="sortOrder" />
                <display:column titleKey="columnTitle.default" media="html" sortable="true" sortProperty="isDefault">
                    <c:choose>
                        <c:when test="${entityListItem.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="EntityListItem.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/EntityListItem/SetDefault">
                                    <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                    <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                                    <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                                    <c:param name="EntityListItemName" value="${entityListItem.entityListItemName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow}">
                    <display:column media="html">
                        <et:hasSecurityRole securityRole="EntityListItem.Edit">
                            <c:url var="editUrl" value="/action/Core/EntityListItem/Edit">
                                <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                                <c:param name="OriginalEntityListItemName" value="${entityListItem.entityListItemName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="EntityListItem.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/EntityListItem/Description">
                                <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                                <c:param name="EntityListItemName" value="${entityListItem.entityListItemName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="EntityListItem.Delete">
                            <c:url var="deleteUrl" value="/action/Core/EntityListItem/Delete">
                                <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                                <c:param name="EntityListItemName" value="${entityListItem.entityListItemName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column media="html">
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${entityListItem.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
                <display:column property="entityListItemName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                <display:column property="description" titleKey="columnTitle.description" media="csv excel pdf rtf xml" />
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="csv excel pdf rtf xml" />
                <display:column property="isDefault" titleKey="columnTitle.default" media="csv excel pdf rtf xml" />
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
