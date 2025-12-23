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
        <title><fmt:message key="pageTitle.entityIntegerRanges" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />"><fmt:message key="navigation.componentVendors" /></a> &gt;&gt;
                <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}"><fmt:message key="navigation.entityTypes" /></a> &gt;&gt;
                <c:url var="entityAttributesUrl" value="/action/Core/EntityAttribute/Main">
                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                </c:url>
                <a href="${entityAttributesUrl}"><fmt:message key="navigation.entityAttributes" /></a> &gt;&gt;
                <fmt:message key="navigation.entityIntegerRanges" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:EntityIntegerRange.Create:EntityIntegerRange.Edit:EntityIntegerRange.Delete:EntityIntegerRange.Review:EntityIntegerRange.Description" />
            <et:hasSecurityRole securityRoles="EntityIntegerRange.Edit:EntityIntegerRange.Description:EntityIntegerRange.Delete">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityIntegerRange.Create">
                <c:url var="addUrl" value="/action/Core/EntityIntegerRange/Add">
                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Entity Integer Range.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityIntegerRange.Review" var="includeReviewUrl" />
            <display:table name="entityIntegerRanges" id="entityIntegerRange" class="displaytag" export="true" sort="list" requestURI="/action/Core/EntityIntegerRange/Main">
                <display:setProperty name="export.csv.filename" value="EntityIntegerRanges.csv" />
                <display:setProperty name="export.excel.filename" value="EntityIntegerRanges.xls" />
                <display:setProperty name="export.pdf.filename" value="EntityIntegerRanges.pdf" />
                <display:setProperty name="export.rtf.filename" value="EntityIntegerRanges.rtf" />
                <display:setProperty name="export.xml.filename" value="EntityIntegerRanges.xml" />
                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="entityIntegerRangeName">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/EntityIntegerRange/Review">
                                <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityIntegerRange.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityIntegerRange.entityAttribute.entityAttributeName}" />
                                <c:param name="EntityIntegerRangeName" value="${entityIntegerRange.entityIntegerRangeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${entityIntegerRange.entityIntegerRangeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${entityIntegerRange.entityIntegerRangeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description" media="html" sortable="true" sortProperty="description">
                    <et:appearance appearance="${entityIntegerRange.entityInstance.entityAppearance.appearance}"><c:out value="${entityIntegerRange.description}" /></et:appearance>
                </display:column>
                <display:column property="minimumIntegerValue" titleKey="columnTitle.minimumIntegerValue" media="html" sortable="true" sortProperty="minimumIntegerValue" />
                <display:column property="maximumIntegerValue" titleKey="columnTitle.maximumIntegerValue" media="html" sortable="true" sortProperty="maximumIntegerValue" />
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="html" sortable="true" sortProperty="sortOrder" />
                <display:column titleKey="columnTitle.default" media="html" sortable="true" sortProperty="isDefault">
                    <c:choose>
                        <c:when test="${entityIntegerRange.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="EntityIntegerRange.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/EntityIntegerRange/SetDefault">
                                    <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                    <c:param name="EntityTypeName" value="${entityIntegerRange.entityAttribute.entityType.entityTypeName}" />
                                    <c:param name="EntityAttributeName" value="${entityIntegerRange.entityAttribute.entityAttributeName}" />
                                    <c:param name="EntityIntegerRangeName" value="${entityIntegerRange.entityIntegerRangeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow}">
                    <display:column media="html">
                        <et:hasSecurityRole securityRole="EntityIntegerRange.Edit">
                            <c:url var="editUrl" value="/action/Core/EntityIntegerRange/Edit">
                                <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityIntegerRange.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityIntegerRange.entityAttribute.entityAttributeName}" />
                                <c:param name="OriginalEntityIntegerRangeName" value="${entityIntegerRange.entityIntegerRangeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="EntityIntegerRange.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/EntityIntegerRange/Description">
                                <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityIntegerRange.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityIntegerRange.entityAttribute.entityAttributeName}" />
                                <c:param name="EntityIntegerRangeName" value="${entityIntegerRange.entityIntegerRangeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="EntityIntegerRange.Delete">
                            <c:url var="deleteUrl" value="/action/Core/EntityIntegerRange/Delete">
                                <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityIntegerRange.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityIntegerRange.entityAttribute.entityAttributeName}" />
                                <c:param name="EntityIntegerRangeName" value="${entityIntegerRange.entityIntegerRangeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column media="html">
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${entityIntegerRange.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
                <display:column property="entityIntegerRangeName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                <display:column property="description" titleKey="columnTitle.description" media="csv excel pdf rtf xml" />
                <display:column property="minimumIntegerValue" titleKey="columnTitle.minimumIntegerValue" media="csv excel pdf rtf xml" />
                <display:column property="maximumIntegerValue" titleKey="columnTitle.maximumIntegerValue" media="csv excel pdf rtf xml" />
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="csv excel pdf rtf xml" />
                <display:column property="isDefault" titleKey="columnTitle.default" media="csv excel pdf rtf xml" />
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
