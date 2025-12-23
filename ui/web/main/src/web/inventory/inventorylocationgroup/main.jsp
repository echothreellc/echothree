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
        <title>Inventory Location Groups</title>
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
                Inventory Location Groups
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Inventory/InventoryLocationGroup/Add">
                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
            </c:url>
            <p><a href="${addUrl}">Add Inventory Location Group.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="inventoryLocationGroups" id="inventoryLocationGroup" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Inventory/InventoryLocationGroup/Review">
                        <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                        <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${inventoryLocationGroup.inventoryLocationGroupName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${inventoryLocationGroup.description}" />
                </display:column>
                <display:column titleKey="columnTitle.status">
                    <c:url var="statusUrl" value="/action/Inventory/InventoryLocationGroup/Status">
                        <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                        <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
                    </c:url>
                    <a href="${statusUrl}"><c:out value="${inventoryLocationGroup.inventoryLocationGroupStatus.workflowStep.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.volume" media="html" sortable="true" sortProperty="inventoryLocationGroupVolume.cubicVolume">
                    <c:choose>
                        <c:when test="${inventoryLocationGroup.inventoryLocationGroupVolume == null}">
                            <c:url var="createLocationVolumeUrl" value="/action/Inventory/InventoryLocationGroup/VolumeAdd">
                                <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                                <c:param name="LocationName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
                            </c:url>
                            <a href="${createLocationVolumeUrl}">Create</a>
                        </c:when>
                        <c:otherwise>
                            H: <c:out value="${inventoryLocationGroup.inventoryLocationGroupVolume.height}" />,
                            W: <c:out value="${inventoryLocationGroup.inventoryLocationGroupVolume.width}" />,
                            D: <c:out value="${inventoryLocationGroup.inventoryLocationGroupVolume.depth}" /><br />
                            <c:url var="editLocationVolumeUrl" value="/action/Inventory/InventoryLocationGroup/VolumeEdit">
                                <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                                <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
                            </c:url>
                            <a href="${editLocationVolumeUrl}">Edit</a>
                            <c:url var="deleteLocationVolumeUrl" value="/action/Inventory/InventoryLocationGroup/VolumeDelete">
                                <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                                <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
                            </c:url>
                            <a href="${deleteLocationVolumeUrl}">Delete</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${inventoryLocationGroup.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Inventory/InventoryLocationGroup/SetDefault">
                                <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                                <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="inventoryLocationGroupCapacitiesUrl" value="/action/Inventory/InventoryLocationGroupCapacity/Main">
                        <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                        <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
                    </c:url>
                    <a href="${inventoryLocationGroupCapacitiesUrl}">Capacities</a><br />
                    <c:url var="editUrl" value="/action/Inventory/InventoryLocationGroup/Edit">
                        <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                        <c:param name="OriginalInventoryLocationGroupName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Inventory/InventoryLocationGroup/Description">
                        <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                        <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Inventory/InventoryLocationGroup/Delete">
                        <c:param name="WarehouseName" value="${inventoryLocationGroup.warehouse.warehouseName}" />
                        <c:param name="InventoryLocationGroupName" value="${inventoryLocationGroup.inventoryLocationGroupName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${inventoryLocationGroup.entityInstance.entityRef}" />
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
