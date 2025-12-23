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
        <title>Warehouse Aliases (<c:out value="${warehouse.warehouseName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <et:countWarehouseResults searchTypeName="EMPLOYEE" countVar="warehouseResultsCount" commandResultVar="countWarehouseResultsCommandResult" logErrors="false" />
                <c:if test="${warehouseResultsCount > 0}">
                    <a href="<c:url value="/action/Warehouse/Warehouse/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Warehouse/Warehouse/Review">
                    <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${warehouse.warehouseName}" />)</a> &gt;&gt;
                <fmt:message key="navigation.warehouseAliases" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <c:if test='${warehouse.person.firstName != null || warehouse.person.middleName != null || warehouse.person.lastName != null}'>
                <p><font size="+2"><b><c:out value="${warehouse.person.personalTitle.description}" /> <c:out value="${warehouse.person.firstName}" />
                <c:out value="${warehouse.person.middleName}" /> <c:out value="${warehouse.person.lastName}" />
                <c:out value="${warehouse.person.nameSuffix.description}" /></b></font></p>
            </c:if>
            <c:if test='${warehouse.partyGroup.name != null}'>
                <p><font size="+1"><c:out value="${warehouse.partyGroup.name}" /></font></p>
            </c:if>
            <br />
            <br />
            <c:set var="commonUrl" scope="request" value="Warehouse/WarehouseAlias" />
            <c:set var="partyAliases" scope="request" value="${warehouse.partyAliases}" />
            <c:set var="securityRoleGroupNamePrefix" scope="request" value="Warehouse" />
            <c:set var="party" scope="request" value="${warehouse}" />
            <jsp:include page="../../include/partyAliases.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
