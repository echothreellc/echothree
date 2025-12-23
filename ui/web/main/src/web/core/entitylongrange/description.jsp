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
        <title><fmt:message key="pageTitle.entityLongRangeDescriptions" /></title>
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
                    <c:param name="ComponentVendorName" value="${entityLongRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}"><fmt:message key="navigation.entityTypes" /></a> &gt;&gt;
                <c:url var="entityAttributesUrl" value="/action/Core/EntityAttribute/Main">
                    <c:param name="ComponentVendorName" value="${entityLongRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityLongRange.entityAttribute.entityType.entityTypeName}" />
                </c:url>
                <a href="${entityAttributesUrl}"><fmt:message key="navigation.entityAttributes" /></a> &gt;&gt;
                <c:url var="entityLongRangeUrl" value="/action/Core/EntityLongRange/Main">
                    <c:param name="ComponentVendorName" value="${entityLongRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityLongRange.entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityLongRange.entityAttribute.entityAttributeName}" />
                </c:url>
                <a href="${entityLongRangeUrl}"><fmt:message key="navigation.entityLongRanges" /></a> &gt;&gt;
                <fmt:message key="navigation.entityLongRangeDescriptions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Core/EntityLongRange/DescriptionAdd">
                <c:param name="ComponentVendorName" value="${entityLongRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                <c:param name="EntityTypeName" value="${entityLongRange.entityAttribute.entityType.entityTypeName}" />
                <c:param name="EntityAttributeName" value="${entityLongRange.entityAttribute.entityAttributeName}" />
                <c:param name="EntityLongRangeName" value="${entityLongRange.entityLongRangeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="entityLongRangeDescriptions" id="entityLongRangeDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${entityLongRangeDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${entityLongRangeDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Core/EntityLongRange/DescriptionEdit">
                        <c:param name="ComponentVendorName" value="${entityLongRangeDescription.entityLongRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityLongRangeDescription.entityLongRange.entityAttribute.entityType.entityTypeName}" />
                        <c:param name="EntityAttributeName" value="${entityLongRangeDescription.entityLongRange.entityAttribute.entityAttributeName}" />
                        <c:param name="EntityLongRangeName" value="${entityLongRangeDescription.entityLongRange.entityLongRangeName}" />
                        <c:param name="LanguageIsoName" value="${entityLongRangeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Core/EntityLongRange/DescriptionDelete">
                        <c:param name="ComponentVendorName" value="${entityLongRangeDescription.entityLongRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityLongRangeDescription.entityLongRange.entityAttribute.entityType.entityTypeName}" />
                        <c:param name="EntityAttributeName" value="${entityLongRangeDescription.entityLongRange.entityAttribute.entityAttributeName}" />
                        <c:param name="EntityLongRangeName" value="${entityLongRangeDescription.entityLongRange.entityLongRangeName}" />
                        <c:param name="LanguageIsoName" value="${entityLongRangeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
