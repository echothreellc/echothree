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
        <title><fmt:message key="pageTitle.locationCapacities" /></title>
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
                <c:url var="locationsUrl" value="/action/Warehouse/Location/Main">
                    <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                </c:url>
                <a href="${locationsUrl}"><fmt:message key="navigation.locations" /></a> &gt;&gt;
                <fmt:message key="navigation.locationCapacities" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Warehouse/LocationCapacity/Add/Step1">
                <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                <c:param name="LocationName" value="${location.locationName}" />
            </c:url>
            <p><a href="${addUrl}">Add Capacity.</a></p>
            <display:table name="locationCapacities" id="locationCapacity" class="displaytag">
                <display:column titleKey="columnTitle.unitOfMeasureKind">
                    <c:out value="${locationCapacity.unitOfMeasureType.unitOfMeasureKind.description}" />
                </display:column>
                <display:column titleKey="columnTitle.unitOfMeasureType">
                    <c:out value="${locationCapacity.unitOfMeasureType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.capacity">
                    <c:out value="${locationCapacity.capacity}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Warehouse/LocationCapacity/Edit">
                        <c:param name="WarehouseName" value="${locationCapacity.location.warehouse.warehouseName}" />
                        <c:param name="LocationName" value="${locationCapacity.location.locationName}" />
                        <c:param name="UnitOfMeasureKindName" value="${locationCapacity.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${locationCapacity.unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Warehouse/LocationCapacity/Delete">
                        <c:param name="WarehouseName" value="${locationCapacity.location.warehouse.warehouseName}" />
                        <c:param name="LocationName" value="${locationCapacity.location.locationName}" />
                        <c:param name="UnitOfMeasureKindName" value="${locationCapacity.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${locationCapacity.unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
