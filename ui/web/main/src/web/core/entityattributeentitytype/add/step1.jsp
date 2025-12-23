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

<%@ include file="../../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title><fmt:message key="pageTitle.entityAttributeEntityTypes" /></title>
        <html:base/>
        <%@ include file="../../../include/environment.jsp" %>
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
                <c:url var="entityAttributeEntityTypesUrl" value="/action/Core/EntityAttributeEntityType/Main">
                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                </c:url>
                <a href="${entityAttributeEntityTypesUrl}"><fmt:message key="navigation.entityAttributeEntityTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.add" /> &gt;&gt;
                <fmt:message key="navigation.step">
                    <fmt:param value="1" />
                    <fmt:param value="2" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            Component Vendor:<br /><br />
            <c:forEach items="${componentVendors}" var="componentVendor">
                <c:url var="addUrl" value="/action/Core/EntityAttributeEntityType/Add/Step2">
                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                    <c:param name="AllowedComponentVendorName" value="${componentVendor.componentVendorName}" />
                </c:url>
                &nbsp;&nbsp;&nbsp;&nbsp;<a href="${addUrl}"><c:out value="${componentVendor.description}" /></a><br />
            </c:forEach>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
