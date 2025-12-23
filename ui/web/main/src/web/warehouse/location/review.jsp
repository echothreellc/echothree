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
            <fmt:message key="pageTitle.location">
                <fmt:param value="${location.locationName}" />
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
                <c:url var="locationsUrl" value="/action/Warehouse/Location/Main">
                    <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                </c:url>
                <a href="${locationsUrl}"><fmt:message key="navigation.locations" /></a> &gt;&gt;
                Review (<c:out value="${location.locationName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><et:appearance appearance="${warehouse.entityInstance.entityAppearance.appearance}"><c:out value="${location.description}" /></et:appearance></b></font></p>
            <p><font size="+1"><et:appearance appearance="${warehouse.entityInstance.entityAppearance.appearance}">${location.locationName}</et:appearance></font></p>
            <br />
            <fmt:message key="label.warehouse" />: <c:out value="${location.warehouse.partyGroup.name}" /><br />
            <fmt:message key="label.locationName" />: ${location.locationName}<br />
            <fmt:message key="label.locationType" />: <c:out value="${location.locationType.description}" /><br />
            <fmt:message key="label.locationUseType" />: <c:out value="${location.locationUseType.description}" /><br />
            <fmt:message key="label.velocity" />: ${location.velocity}<br />
            <fmt:message key="label.inventoryLocationGroup" />: <c:out value="${location.inventoryLocationGroup.description}" /><br />
            <fmt:message key="label.locationStatus" />: <c:out value="${location.locationStatus.workflowStep.description}" />
            <c:url var="editUrl" value="/action/Warehouse/Location/LocationStatus">
                <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                <c:param name="LocationName" value="${location.locationName}" />
            </c:url>
            <a href="${editUrl}">Edit</a>
            <br />
            <br />
            <br />
            <fmt:message key="label.locationVolume" />:
            <c:choose>
                <c:when test="${locationVolume == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    Height: <c:out value="${locationVolume.height}" />,
                    Width: <c:out value="${locationVolume.width}" />,
                    Depth: <c:out value="${locationVolume.depth}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <h2><fmt:message key="label.locationCapacities" /></h2>
            <c:url var="addUrl" value="/action/Warehouse/Location/LocationCapacityAdd">
                <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                <c:param name="LocationName" value="${location.locationName}" />
            </c:url>
            <a href="${addUrl}">Add Location Capacity.</a>
            <c:choose>
                <c:when test="${locationCapacities != null}">
                    <display:table name="locationCapacities" id="locationCapacity" class="displaytag">
                        <display:column titleKey="columnTitle.kind">
                            <c:out value="${locationCapacity.unitOfMeasureType.unitOfMeasureKind.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.type">
                            <c:out value="${locationCapacity.unitOfMeasureType.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.capacity" class="quantity">
                            <c:out value="${locationCapacity.capacity}" />
                        </display:column>
                        <display:column>
                            <c:url var="editUrl" value="/action/Warehouse/Location/LocationCapacityEdit">
                                <c:param name="WarehouseName" value="${locationCapacity.location.warehouse.warehouseName}" />
                                <c:param name="LocationName" value="${locationCapacity.location.locationName}" />
                                <c:param name="UnitOfMeasureKindName" value="${locationCapacity.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${locationCapacity.unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Warehouse/Location/LocationCapacityDelete">
                                <c:param name="WarehouseName" value="${locationCapacity.location.warehouse.warehouseName}" />
                                <c:param name="LocationName" value="${locationCapacity.location.locationName}" />
                                <c:param name="UnitOfMeasureKindName" value="${locationCapacity.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${locationCapacity.unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                    </display:table>
                    <br />
                </c:when>
                <c:otherwise>
                    <br />
                    <br />
                    <br />
                </c:otherwise>
            </c:choose>
            <h2><fmt:message key="label.locationNameElements" /></h2>
            <display:table name="locationNameElements" id="locationNameElement" class="displaytag">
                <display:column titleKey="columnTitle.locationNameElement">
                    <c:out value="${locationNameElement.description}" />
                </display:column>
                <display:column titleKey="columnTitle.value">
                    <c:out value="${fn:substring(location.locationName, locationNameElement.offset, locationNameElement.offset + locationNameElement.length)}" />
                </display:column>
            </display:table>
            <br />
            <c:set var="tagScopes" scope="request" value="${location.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${location.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${location.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Warehouse/Location/Review">
                <c:param name="WarehouseName" value="${location.warehouse.warehouseName}" />
                <c:param name="LocationName" value="${location.locationName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
