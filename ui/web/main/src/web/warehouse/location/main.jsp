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
        <title><fmt:message key="pageTitle.locations" /></title>
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
                <fmt:message key="navigation.locations" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Warehouse/Location/Add">
                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
            </c:url>
            <p><a href="${addUrl}">Add Location.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="locations" id="location" class="displaytag" export="true" sort="list" requestURI="/action/Warehouse/Location/Main">
                <display:setProperty name="export.csv.filename" value="Locations.csv" />
                <display:setProperty name="export.excel.filename" value="Locations.xls" />
                <display:setProperty name="export.pdf.filename" value="Locations.pdf" />
                <display:setProperty name="export.rtf.filename" value="Locations.rtf" />
                <display:setProperty name="export.xml.filename" value="Locations.xml" />
                <display:column media="html">
                    <c:choose>
                        <c:when test="${location.entityInstance.entityVisit == null}">
                            New
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${location.entityInstance.entityVisit.unformattedVisitedTime >= location.entityInstance.entityTime.unformattedModifiedTime}">
                                    Unchanged
                                </c:when>
                                <c:otherwise>
                                    Updated
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="locationName">
                    <c:url var="reviewUrl" value="/action/Warehouse/Location/Review">
                        <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                        <c:param name="LocationName" value="${location.locationName}" />
                    </c:url>
                    <a href="${reviewUrl}"><et:appearance appearance="${location.entityInstance.entityAppearance.appearance}"><c:out value="${location.locationName}" /></et:appearance></a>
                </display:column>
                <display:column titleKey="columnTitle.description" media="html" sortable="true" sortProperty="description">
                    <et:appearance appearance="${location.entityInstance.entityAppearance.appearance}"><c:out value="${location.description}" /></et:appearance>
                </display:column>
                <display:column titleKey="columnTitle.status" media="html" sortable="true" sortProperty="locationStatus.workflowStep.description">
                    <c:url var="statusUrl" value="/action/Warehouse/Location/Status">
                        <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                        <c:param name="LocationName" value="${location.locationName}" />
                    </c:url>
                    <a href="${statusUrl}"><c:out value="${location.locationStatus.workflowStep.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.type" media="html" sortable="true" sortProperty="locationType.description">
                    <c:out value="${location.locationType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.use" media="html" sortable="true" sortProperty="locationUseType.description">
                    <c:out value="${location.locationUseType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.velocity" media="html" sortable="true">
                    <c:out value="${location.velocity}" />
                </display:column>
                <display:column titleKey="columnTitle.volume" media="html" sortable="true" sortProperty="locationVolume.cubicVolume">
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
                <display:column property="locationName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                <display:column property="description" titleKey="columnTitle.description" media="csv excel pdf rtf xml" />
                <display:column property="locationStatus.workflowStep.description" titleKey="columnTitle.status" media="csv excel pdf rtf xml" />
                <display:column property="locationType.description" titleKey="columnTitle.type" media="csv excel pdf rtf xml" />
                <display:column property="locationUseType.description" titleKey="columnTitle.use" media="csv excel pdf rtf xml" />
                <display:column property="velocity" titleKey="columnTitle.velocity" media="csv excel pdf rtf xml" />
                <display:column property="locationVolume.height" titleKey="columnTitle.height" media="csv excel pdf rtf xml" />
                <display:column property="locationVolume.width" titleKey="columnTitle.width" media="csv excel pdf rtf xml" />
                <display:column property="locationVolume.depth" titleKey="columnTitle.depth" media="csv excel pdf rtf xml" />
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
