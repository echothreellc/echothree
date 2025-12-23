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
            <fmt:message key="pageTitle.warehouseType">
                <fmt:param value="${warehouseType.warehouseTypeName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/WarehouseType/Main" />"><fmt:message key="navigation.warehouseTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.warehouseType">
                    <fmt:param value="${warehouseType.warehouseTypeName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Location.List:Event.List" />
            <c:choose>
                <c:when test="${warehouseType.description != null}">
                    <p><font size="+2"><b><et:appearance appearance="${warehouseType.entityInstance.entityAppearance.appearance}"><c:out value="${warehouseType.description}" /></et:appearance></b></font></p>
                    <p><font size="+1"><et:appearance appearance="${warehouseType.entityInstance.entityAppearance.appearance}">${warehouseType.warehouseTypeName}</et:appearance></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><et:appearance appearance="${warehouseType.entityInstance.entityAppearance.appearance}"><c:out value="${warehouseType.warehouseTypeName}" /></et:appearance></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.warehouseTypeName" />: ${warehouseType.warehouseTypeName}<br />
            <fmt:message key="label.priority" />: ${warehouseType.priority}<br />
            <br />
            <br />
            <br />

            <c:set var="tagScopes" scope="request" value="${warehouseType.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${warehouseType.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${warehouseType.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Warehouse/WarehouseType/Review">
                <c:param name="WarehouseTypeType" value="${warehouseType.warehouseTypeName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
