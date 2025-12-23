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
        <title>
            <fmt:message key="pageTitle.entityIntegerRange">
                <fmt:param value="${entityIntegerRange.entityIntegerRangeName}" />
            </fmt:message>
        </title>
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
                    <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}"><fmt:message key="navigation.entityTypes" /></a> &gt;&gt;
                <c:url var="entityAttributesUrl" value="/action/Core/EntityAttribute/Main">
                    <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityIntegerRange.entityAttribute.entityType.entityTypeName}" />
                </c:url>
                <a href="${entityAttributesUrl}"><fmt:message key="navigation.entityAttributes" /></a> &gt;&gt;
                <c:url var="entityIntegerRangeUrl" value="/action/Core/EntityIntegerRange/Main">
                    <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityIntegerRange.entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityIntegerRange.entityAttribute.entityAttributeName}" />
                </c:url>
                <a href="${entityIntegerRangeUrl}"><fmt:message key="navigation.entityIntegerRanges" /></a> &gt;&gt;
                <fmt:message key="navigation.entityIntegerRange">
                    <fmt:param value="${entityIntegerRange.entityIntegerRangeName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${entityIntegerRange.description}" /></b></font></p>
            <br />
            Entity Integer Range Name: ${entityIntegerRange.entityIntegerRangeName}<br />
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${entityIntegerRange.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Core/EntityIntegerRange/Review">
                    <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityIntegerRange.entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityIntegerRange.entityAttribute.entityAttributeName}" />
                    <c:param name="EntityIntegerRangeName" value="${entityIntegerRange.entityIntegerRangeName}" />
            </c:url>
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
