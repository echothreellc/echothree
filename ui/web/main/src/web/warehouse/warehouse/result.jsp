<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2023 Echo Three, LLC                                              -->
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
        <title><fmt:message key="pageTitle.warehouses" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                Results
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Warehouse.Create:Warehouse.Review:Event.List" />
            <et:hasSecurityRole securityRole="Warehouse.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="Warehouse.Create">
                <p><a href="<c:url value="/action/Warehouse/Warehouse/Add" />">Add Warehouse.</a></p>
            </et:hasSecurityRole>
            <br />
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <et:containsExecutionError key="UnknownUserVisitSearch">
                <c:set var="unknownUserVisitSearch" value="true" />
            </et:containsExecutionError>
            <c:choose>
                <c:when test="${unknownUserVisitSearch}">
                    <p>Your search results are no longer available, please perform your search again.</p>
                </c:when>
                <c:otherwise>
                    <display:table name="warehouseResults.list" id="warehouseResult" class="displaytag" partialList="true" pagesize="20" size="warehouseResultCount" requestURI="/action/Warehouse/Warehouse/Result">
                        <display:column>
                            <c:choose>
                                <c:when test="${warehouseResult.warehouse.entityInstance.entityVisit == null}">
                                    New
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${warehouseResult.warehouse.entityInstance.entityVisit.unformattedVisitedTime >= warehouseResult.warehouse.entityInstance.entityTime.unformattedModifiedTime}">
                                            Unchanged
                                        </c:when>
                                        <c:otherwise>
                                            Updated
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.name">
                            <c:choose>
                                <c:when test="${includeReviewUrl}">
                                    <c:url var="reviewUrl" value="/action/Warehouse/Warehouse/Review">
                                        <c:param name="WarehouseName" value="${warehouseResult.warehouse.warehouseName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><et:appearance appearance="${warehouseResult.warehouse.entityInstance.entityAppearance.appearance}"><c:out value="${warehouseResult.warehouse.warehouseName}" /></et:appearance></a>
                                </c:when>
                                <c:otherwise>
                                    <et:appearance appearance="${warehouseResult.warehouse.entityInstance.entityAppearance.appearance}"><c:out value="${warehouseResult.warehouse.warehouseName}" /></et:appearance>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.description">
                            <et:appearance appearance="${warehouseResult.warehouse.entityInstance.entityAppearance.appearance}"><c:out value="${warehouseResult.warehouse.partyGroup.name}" /></et:appearance>
                        </display:column>
                        <display:column titleKey="columnTitle.sortOrder">
                            <c:out value="${warehouseResult.warehouse.sortOrder}" />
                        </display:column>
                        <display:column titleKey="columnTitle.default">
                            <c:choose>
                                <c:when test="${warehouseResult.warehouse.isDefault}">
                                    Default
                                </c:when>
                                <c:otherwise>
                                    <c:url var="setDefaultUrl" value="/action/Warehouse/Warehouse/SetDefault">
                                        <c:param name="WarehouseName" value="${warehouseResult.warehouse.warehouseName}" />
                                    </c:url>
                                    <a href="${setDefaultUrl}">Set Default</a>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column>
                            <c:url var="warehousePrinterGroupUsesUrl" value="/action/Warehouse/WarehousePrinterGroupUse/Main">
                                <c:param name="WarehouseName" value="${warehouseResult.warehouse.warehouseName}" />
                            </c:url>
                            <a href="${warehousePrinterGroupUsesUrl}">Printer Group Uses</a>
                            <c:url var="warehouseContactMechanismsUrl" value="/action/Warehouse/WarehouseContactMechanism/Main">
                                <c:param name="WarehouseName" value="${warehouseResult.warehouse.warehouseName}" />
                            </c:url>
                            <a href="${warehouseContactMechanismsUrl}">Contact Mechanisms</a>
                            <c:url var="locationsUrl" value="/action/Warehouse/Location/Main">
                                <c:param name="WarehouseName" value="${warehouseResult.warehouse.warehouseName}" />
                            </c:url><br />
                            <a href="${locationsUrl}">Locations</a>
                            <c:url var="locationTypesUrl" value="/action/Warehouse/LocationType/Main">
                                <c:param name="WarehouseName" value="${warehouseResult.warehouse.warehouseName}" />
                            </c:url>
                            <a href="${locationTypesUrl}">Location Types</a>
                            <c:url var="inventoryLocationGroupsUrl" value="/action/Inventory/InventoryLocationGroup/Main">
                                <c:param name="WarehouseName" value="${warehouseResult.warehouse.warehouseName}" />
                            </c:url>
                            <a href="${inventoryLocationGroupsUrl}">Inventory Location Groups</a><br />
                            <c:url var="editUrl" value="/action/Warehouse/Warehouse/WarehouseEdit">
                                <c:param name="OriginalWarehouseName" value="${warehouseResult.warehouse.warehouseName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Warehouse/Warehouse/Delete">
                                <c:param name="WarehouseName" value="${warehouseResult.warehouse.warehouseName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column>
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${warehouseResult.warehouse.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                    </display:table>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
