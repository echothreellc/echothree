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
        <title><fmt:message key="pageTitle.locationNameElements" /></title>
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
                <c:url var="locationTypesUrl" value="/action/Warehouse/LocationType/Main">
                    <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                </c:url>
                <a href="${locationTypesUrl}"><fmt:message key="navigation.locationTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.locationNameElements" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Warehouse/LocationNameElement/Add">
                <c:param name="WarehouseName" value="${locationType.warehouse.warehouseName}" />
                <c:param name="LocationTypeName" value="${locationType.locationTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Name Element.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="locationNameElements" id="locationNameElement" class="displaytag">
                <display:column property="locationNameElementName" titleKey="columnTitle.name" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${locationNameElement.description}" />
                </display:column>
                <display:column property="offset" titleKey="columnTitle.offset" />
                <display:column property="length" titleKey="columnTitle.length" />
                <display:column property="validationPattern" titleKey="columnTitle.validationPattern" />
                <display:column>
                    <c:url var="editUrl" value="/action/Warehouse/LocationNameElement/Edit">
                        <c:param name="WarehouseName" value="${locationNameElement.locationType.warehouse.warehouseName}" />
                        <c:param name="LocationTypeName" value="${locationNameElement.locationType.locationTypeName}" />
                        <c:param name="OriginalLocationNameElementName" value="${locationNameElement.locationNameElementName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Warehouse/LocationNameElement/Description">
                        <c:param name="WarehouseName" value="${locationNameElement.locationType.warehouse.warehouseName}" />
                        <c:param name="LocationTypeName" value="${locationNameElement.locationType.locationTypeName}" />
                        <c:param name="LocationNameElementName" value="${locationNameElement.locationNameElementName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Warehouse/LocationNameElement/Delete">
                        <c:param name="WarehouseName" value="${locationNameElement.locationType.warehouse.warehouseName}" />
                        <c:param name="LocationTypeName" value="${locationNameElement.locationType.locationTypeName}" />
                        <c:param name="LocationNameElementName" value="${locationNameElement.locationNameElementName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${locationNameElement.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
