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
        <title><fmt:message key="pageTitle.entityLongRanges" /></title>
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
                <fmt:message key="navigation.entityLongRanges" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:EntityLongRange.Create:EntityLongRange.Edit:EntityLongRange.Delete:EntityLongRange.Review:EntityLongRange.Description" />
            <et:hasSecurityRole securityRoles="EntityLongRange.Edit:EntityLongRange.Description:EntityLongRange.Delete">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityLongRange.Create">
                <c:url var="addUrl" value="/action/Core/EntityLongRange/Add">
                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Entity Long Range.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityLongRange.Review" var="includeReviewUrl" />
            <display:table name="entityLongRanges" id="entityLongRange" class="displaytag" export="true" sort="list" requestURI="/action/Core/EntityLongRange/Main">
                <display:setProperty name="export.csv.filename" value="EntityLongRanges.csv" />
                <display:setProperty name="export.excel.filename" value="EntityLongRanges.xls" />
                <display:setProperty name="export.pdf.filename" value="EntityLongRanges.pdf" />
                <display:setProperty name="export.rtf.filename" value="EntityLongRanges.rtf" />
                <display:setProperty name="export.xml.filename" value="EntityLongRanges.xml" />
                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="entityLongRangeName">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/EntityLongRange/Review">
                                <c:param name="ComponentVendorName" value="${entityLongRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityLongRange.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityLongRange.entityAttribute.entityAttributeName}" />
                                <c:param name="EntityLongRangeName" value="${entityLongRange.entityLongRangeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${entityLongRange.entityLongRangeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${entityLongRange.entityLongRangeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description" media="html" sortable="true" sortProperty="description">
                    <et:appearance appearance="${entityLongRange.entityInstance.entityAppearance.appearance}"><c:out value="${entityLongRange.description}" /></et:appearance>
                </display:column>
                <display:column property="minimumLongValue" titleKey="columnTitle.minimumLongValue" media="html" sortable="true" sortProperty="minimumLongValue" />
                <display:column property="maximumLongValue" titleKey="columnTitle.maximumLongValue" media="html" sortable="true" sortProperty="maximumLongValue" />
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="html" sortable="true" sortProperty="sortOrder" />
                <display:column titleKey="columnTitle.default" media="html" sortable="true" sortProperty="isDefault">
                    <c:choose>
                        <c:when test="${entityLongRange.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="EntityLongRange.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/EntityLongRange/SetDefault">
                                    <c:param name="ComponentVendorName" value="${entityLongRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                    <c:param name="EntityTypeName" value="${entityLongRange.entityAttribute.entityType.entityTypeName}" />
                                    <c:param name="EntityAttributeName" value="${entityLongRange.entityAttribute.entityAttributeName}" />
                                    <c:param name="EntityLongRangeName" value="${entityLongRange.entityLongRangeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow}">
                    <display:column media="html">
                        <et:hasSecurityRole securityRole="EntityLongRange.Edit">
                            <c:url var="editUrl" value="/action/Core/EntityLongRange/Edit">
                                <c:param name="ComponentVendorName" value="${entityLongRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityLongRange.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityLongRange.entityAttribute.entityAttributeName}" />
                                <c:param name="OriginalEntityLongRangeName" value="${entityLongRange.entityLongRangeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="EntityLongRange.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/EntityLongRange/Description">
                                <c:param name="ComponentVendorName" value="${entityLongRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityLongRange.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityLongRange.entityAttribute.entityAttributeName}" />
                                <c:param name="EntityLongRangeName" value="${entityLongRange.entityLongRangeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="EntityLongRange.Delete">
                            <c:url var="deleteUrl" value="/action/Core/EntityLongRange/Delete">
                                <c:param name="ComponentVendorName" value="${entityLongRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityLongRange.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityLongRange.entityAttribute.entityAttributeName}" />
                                <c:param name="EntityLongRangeName" value="${entityLongRange.entityLongRangeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column media="html">
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${entityLongRange.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
                <display:column property="entityLongRangeName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                <display:column property="description" titleKey="columnTitle.description" media="csv excel pdf rtf xml" />
                <display:column property="minimumLongValue" titleKey="columnTitle.minimumLongValue" media="csv excel pdf rtf xml" />
                <display:column property="maximumLongValue" titleKey="columnTitle.maximumLongValue" media="csv excel pdf rtf xml" />
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="csv excel pdf rtf xml" />
                <display:column property="isDefault" titleKey="columnTitle.default" media="csv excel pdf rtf xml" />
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
