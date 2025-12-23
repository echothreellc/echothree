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
        <title><fmt:message key="pageTitle.entityAttributeEntityTypes" /></title>
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
                <fmt:message key="navigation.entityAttributeEntityTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="EntityAttributeEntityType.Create:EntityAttributeEntityType.Delete" />
            <et:hasSecurityRole securityRole="EntityAttributeEntityType.Create">
                <c:url var="addUrl" value="/action/Core/EntityAttributeEntityType/Add/Step1">
                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Entity Type.</a></p>
            </et:hasSecurityRole>
            <display:table name="entityAttributeEntityTypes.list" id="entityAttributeEntityType" class="displaytag">
                <display:column titleKey="columnTitle.componentVendor">
                    <c:out value="${entityAttributeEntityType.allowedEntityType.componentVendor.description}" />
                </display:column>
                <display:column titleKey="columnTitle.entityType">
                    <c:out value="${entityAttributeEntityType.allowedEntityType.description}" />
                </display:column>
                <et:hasSecurityRole securityRoles="EntityAttributeEntityType.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="EntityAttributeEntityType.Delete">
                            <c:url var="deleteUrl" value="/action/Core/EntityAttributeEntityType/Delete">
                                <c:param name="ComponentVendorName" value="${entityAttributeEntityType.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityAttributeEntityType.entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityAttributeEntityType.entityAttribute.entityAttributeName}" />
                                <c:param name="AllowedComponentVendorName" value="${entityAttributeEntityType.allowedEntityType.componentVendor.componentVendorName}" />
                                <c:param name="AllowedEntityTypeName" value="${entityAttributeEntityType.allowedEntityType.entityTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
