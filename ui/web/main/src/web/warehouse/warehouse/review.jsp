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
            <fmt:message key="pageTitle.warehouse">
                <fmt:param value="${warehouse.warehouseName}" />
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
                <a href="<c:url value="/action/Warehouse/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <et:countWarehouseResults searchTypeName="EMPLOYEE" countVar="warehouseResultsCount" commandResultVar="countWarehouseResultsCommandResult" logErrors="false" />
                <c:if test="${warehouseResultsCount > 0}">
                    <a href="<c:url value="/action/Warehouse/Warehouse/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <fmt:message key="navigation.warehouse">
                    <fmt:param value="${warehouse.warehouseName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="WarehouseType.Review:Location.List:Event.List" />
            <et:hasSecurityRole securityRole="WarehouseType.Review" var="includeWarehouseTypeReviewUrl" />
            <c:choose>
                <c:when test="${warehouse.partyGroup.name != null}">
                    <p><font size="+2"><b><et:appearance appearance="${warehouse.entityInstance.entityAppearance.appearance}"><c:out value="${warehouse.partyGroup.name}" /></et:appearance></b></font></p>
                    <p><font size="+1"><et:appearance appearance="${warehouse.entityInstance.entityAppearance.appearance}">${warehouse.warehouseName}</et:appearance></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><et:appearance appearance="${warehouse.entityInstance.entityAppearance.appearance}"><c:out value="${warehouse.warehouseName}" /></et:appearance></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.warehouseName" />: ${warehouse.warehouseName}<br />
            <fmt:message key="label.warehouseType" />:
            <c:choose>
                <c:when test="${includeWarehouseTypeReviewUrl}">
                    <c:url var="reviewUrl" value="/action/Warehouse/WarehouseType/Review">
                        <c:param name="WarehouseTypeName" value="${warehouse.warehouseType.warehouseTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><et:appearance appearance="${warehouse.warehouseType.entityInstance.entityAppearance.appearance}"><c:out value="${warehouse.warehouseType.description}" /></et:appearance></a>
                </c:when>
                <c:otherwise>
                    <et:appearance appearance="${warehouse.warehouseType.entityInstance.entityAppearance.appearance}"><c:out value="${warehouse.warehouseType.description}" /></et:appearance>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <br />
            <c:set var="commonUrl" scope="request" value="Warehouse/WarehouseContactMechanism" />
            <c:set var="party" scope="request" value="${warehouse}" />
            <c:set var="partyContactMechanisms" scope="request" value="${warehouse.partyContactMechanisms}" />
            <jsp:include page="../../include/partyContactMechanisms.jsp" />
            <c:set var="commonUrl" scope="request" value="Warehouse/WarehousePrinterGroupUse" />
            <c:set var="partyPrinterGroupUses" scope="request" value="${warehouse.partyPrinterGroupUses}" />
            <jsp:include page="../../include/partyPrinterGroupUses.jsp" />

            <et:hasSecurityRole securityRole="Location.List">
                <h2><fmt:message key="label.locations" /></h2>
                <c:url var="addUrl" value="/action/Warehouse/Location/Add">
                    <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                </c:url>
                <p><a href="${addUrl}">Add Location.</a></p>
                <c:choose>
                    <c:when test='${warehouse.locations.size == 0}'>
                        <br />
                    </c:when>
                    <c:otherwise>
                        <display:table name="warehouse.locations.list" id="location" class="displaytag">
                            <display:column titleKey="columnTitle.name">
                                <c:url var="reviewUrl" value="/action/Warehouse/Location/Review">
                                    <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                                    <c:param name="LocationName" value="${location.locationName}" />
                                </c:url>
                                <a href="${reviewUrl}"><et:appearance appearance="${location.entityInstance.entityAppearance.appearance}"><c:out value="${location.locationName}" /></et:appearance></a>
                            </display:column>
                            <display:column titleKey="columnTitle.description">
                                <et:appearance appearance="${location.entityInstance.entityAppearance.appearance}"><c:out value="${location.description}" /></et:appearance>
                            </display:column>
                            <display:column titleKey="columnTitle.status">
                                <c:url var="statusUrl" value="/action/Warehouse/Location/Status">
                                    <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                                    <c:param name="LocationName" value="${location.locationName}" />
                                </c:url>
                                <a href="${statusUrl}"><c:out value="${location.locationStatus.workflowStep.description}" /></a>
                            </display:column>
                            <display:column titleKey="columnTitle.type">
                                <c:out value="${location.locationType.description}" />
                            </display:column>
                            <display:column titleKey="columnTitle.use">
                                <c:out value="${location.locationUseType.description}" />
                            </display:column>
                            <display:column titleKey="columnTitle.velocity">
                                <c:out value="${location.velocity}" />
                            </display:column>
                            <display:column titleKey="columnTitle.volume">
                                <c:choose>
                                    <c:when test="${location.locationVolume == null}">
                                        <c:url var="createLocationVolumeUrl" value="/action/Warehouse/Location/VolumeAdd">
                                            <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                                            <c:param name="LocationName" value="${location.locationName}" />
                                        </c:url>
                                        <a href="${createLocationVolumeUrl}">Create</a>
                                    </c:when>
                                    <c:otherwise>
                                        H: <c:out value="${location.locationVolume.height}" />,
                                        W: <c:out value="${location.locationVolume.width}" />,
                                        D: <c:out value="${location.locationVolume.depth}" /><br />
                                        <c:url var="editLocationVolumeUrl" value="/action/Warehouse/Location/VolumeEdit">
                                            <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                                            <c:param name="LocationName" value="${location.locationName}" />
                                        </c:url>
                                        <a href="${editLocationVolumeUrl}">Edit</a>
                                        <c:url var="deleteLocationVolumeUrl" value="/action/Warehouse/Location/VolumeDelete">
                                            <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                                            <c:param name="LocationName" value="${location.locationName}" />
                                        </c:url>
                                        <a href="${deleteLocationVolumeUrl}">Delete</a>
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column media="html">
                                <c:url var="locationCapacitiesUrl" value="/action/Warehouse/LocationCapacity/Main">
                                    <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                                    <c:param name="LocationName" value="${location.locationName}" />
                                </c:url>
                                <a href="${locationCapacitiesUrl}">Capacities</a><br />
                                <c:url var="editUrl" value="/action/Warehouse/Location/Edit">
                                    <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                                    <c:param name="OriginalLocationName" value="${location.locationName}" />
                                </c:url>
                                <a href="${editUrl}">Edit</a>
                                <c:url var="descriptionsUrl" value="/action/Warehouse/Location/Description">
                                    <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                                    <c:param name="LocationName" value="${location.locationName}" />
                                </c:url>
                                <a href="${descriptionsUrl}">Descriptions</a>
                                <c:url var="deleteUrl" value="/action/Warehouse/Location/Delete">
                                    <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                                    <c:param name="LocationName" value="${location.locationName}" />
                                </c:url>
                                <a href="${deleteUrl}">Delete</a>
                            </display:column>
                            <et:hasSecurityRole securityRole="Event.List">
                                <display:column media="html">
                                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                        <c:param name="EntityRef" value="${location.entityInstance.entityRef}" />
                                    </c:url>
                                    <a href="${eventsUrl}">Events</a>
                                </display:column>
                            </et:hasSecurityRole>
                        </display:table>
                        <c:if test='${warehouse.locationsCount > 10}'>
                            <c:url var="locationsUrl" value="/action/Warehouse/Location/Main">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${locationsUrl}">More...</a> (<c:out value="${warehouse.locationsCount}" /> total)<br />
                        </c:if>
                        <br />
                    </c:otherwise>
                </c:choose>
            </et:hasSecurityRole>

            <c:set var="commonUrl" scope="request" value="Warehouse/WarehouseAlias" />
            <c:set var="partyAliases" scope="request" value="${warehouse.partyAliases}" />
            <c:set var="securityRoleGroupNamePrefix" scope="request" value="Warehouse" />
            <jsp:include page="../../include/partyAliases.jsp" />

            <c:set var="tagScopes" scope="request" value="${warehouse.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${warehouse.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${warehouse.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Warehouse/Warehouse/Review">
                <c:param name="PartyName" value="${warehouse.partyName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
