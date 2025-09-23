<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2025 Echo Three, LLC                                              -->
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
                <fmt:message key="navigation.warehouses" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="WarehouseType.Review:Warehouse.Create:Warehouse.Review:Event.List" />
            <et:hasSecurityRole securityRole="WarehouseType.Review" var="includeWarehouseTypeReviewUrl" />
            <et:hasSecurityRole securityRole="Warehouse.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="Warehouse.Create">
                <p><a href="<c:url value="/action/Warehouse/Warehouse/Add" />">Add Warehouse.</a></p>
            </et:hasSecurityRole>
            <br />
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Warehouse/Warehouse/Search" method="POST" focus="q">
                <html:text size="60" property="q" />
                <et:validationErrors id="errorMessage" property="q">
                    <p><c:out value="${errorMessage}" /></p>
                </et:validationErrors>
                <html:submit onclick="onSubmitDisable(this);" value="Search" /><html:hidden property="submitButton" />
            </html:form>
            <et:clearWarehouseResults searchTypeName="EMPLOYEE" />
            <c:choose>
                <c:when test="${warehouseCount == null || warehouseCount < 21}">
                    <display:table name="warehouses.list" id="warehouse" class="displaytag" export="true" sort="list" requestURI="/action/Warehouse/Warehouse/Main">
                        <display:setProperty name="export.csv.filename" value="Warehouses.csv" />
                        <display:setProperty name="export.excel.filename" value="Warehouses.xls" />
                        <display:setProperty name="export.pdf.filename" value="Warehouses.pdf" />
                        <display:setProperty name="export.rtf.filename" value="Warehouses.rtf" />
                        <display:setProperty name="export.xml.filename" value="Warehouses.xml" />
                        <display:column media="html">
                            <c:choose>
                                <c:when test="${warehouse.entityInstance.entityVisit == null}">
                                    New
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${warehouse.entityInstance.entityVisit.unformattedVisitedTime >= warehouse.entityInstance.entityTime.unformattedModifiedTime}">
                                            Unchanged
                                        </c:when>
                                        <c:otherwise>
                                            Updated
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="warehouseName">
                            <c:choose>
                                <c:when test="${includeReviewUrl}">
                                    <c:url var="reviewUrl" value="/action/Warehouse/Warehouse/Review">
                                        <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><et:appearance appearance="${warehouse.entityInstance.entityAppearance.appearance}"><c:out value="${warehouse.warehouseName}" /></et:appearance></a>
                                </c:when>
                                <c:otherwise>
                                    <et:appearance appearance="${warehouse.entityInstance.entityAppearance.appearance}"><c:out value="${warehouse.warehouseName}" /></et:appearance>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.description" media="html" sortable="true" sortProperty="partyGroup.name">
                            <et:appearance appearance="${warehouse.entityInstance.entityAppearance.appearance}"><c:out value="${warehouse.partyGroup.name}" /></et:appearance>
                        </display:column>
                        <display:column titleKey="columnTitle.type" media="html" sortable="true" sortProperty="warehouseType.description">
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
                        </display:column>
                        <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="html" sortable="true" sortProperty="sortOrder" />
                        <display:column titleKey="columnTitle.default" media="html" sortable="true" sortProperty="isDefault">
                            <c:choose>
                                <c:when test="${warehouse.isDefault}">
                                    Default
                                </c:when>
                                <c:otherwise>
                                    <c:url var="setDefaultUrl" value="/action/Warehouse/Warehouse/SetDefault">
                                        <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                                    </c:url>
                                    <a href="${setDefaultUrl}">Set Default</a>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column media="html">
                            <c:url var="warehousePrinterGroupUsesUrl" value="/action/Warehouse/WarehousePrinterGroupUse/Main">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${warehousePrinterGroupUsesUrl}">Printer Group Uses</a>
                            <c:url var="warehouseContactMechanismsUrl" value="/action/Warehouse/WarehouseContactMechanism/Main">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${warehouseContactMechanismsUrl}">Contact Mechanisms</a>
                            <c:url var="locationsUrl" value="/action/Warehouse/Location/Main">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url><br />
                            <a href="${locationsUrl}">Locations</a>
                            <c:url var="locationTypesUrl" value="/action/Warehouse/LocationType/Main">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${locationTypesUrl}">Location Types</a>
                            <c:url var="inventoryLocationGroupsUrl" value="/action/Inventory/InventoryLocationGroup/Main">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${inventoryLocationGroupsUrl}">Inventory Location Groups</a><br />
                            <c:url var="editUrl" value="/action/Warehouse/Warehouse/WarehouseEdit">
                                <c:param name="OriginalWarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Warehouse/Warehouse/Delete">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column media="html">
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${warehouse.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                        <display:column property="warehouseName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                        <display:column property="warehouseType.warehouseTypeName" titleKey="columnTitle.type" media="csv excel pdf rtf xml" />
                        <display:column property="partyGroup.name" titleKey="columnTitle.description" media="csv excel pdf rtf xml" />
                        <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="csv excel pdf rtf xml" />
                        <display:column property="isDefault" titleKey="columnTitle.default" media="csv excel pdf rtf xml" />
                    </display:table>
                    <c:if test="${warehouses.size > 20}">
                        <c:url var="resultsUrl" value="/action/Warehouse/Warehouse/Main" />
                        <a href="${resultsUrl}">Paged Listing</a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <display:table name="warehouses.list" id="warehouse" class="displaytag" partialList="true" pagesize="20" size="warehouseCount" requestURI="/action/Warehouse/Warehouse/Main">
                        <display:column>
                            <c:choose>
                                <c:when test="${warehouse.entityInstance.entityVisit == null}">
                                    New
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${warehouse.entityInstance.entityVisit.unformattedVisitedTime >= warehouse.entityInstance.entityTime.unformattedModifiedTime}">
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
                                        <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><et:appearance appearance="${warehouse.entityInstance.entityAppearance.appearance}"><c:out value="${warehouse.warehouseName}" /></et:appearance></a>
                                </c:when>
                                <c:otherwise>
                                    <et:appearance appearance="${warehouse.entityInstance.entityAppearance.appearance}"><c:out value="${warehouse.warehouseName}" /></et:appearance>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.description">
                            <et:appearance appearance="${warehouse.entityInstance.entityAppearance.appearance}"><c:out value="${warehouse.partyGroup.name}" /></et:appearance>
                        </display:column>
                        <display:column titleKey="columnTitle.type">
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
                        </display:column>
                        <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                        <display:column titleKey="columnTitle.default">
                            <c:choose>
                                <c:when test="${warehouse.isDefault}">
                                    Default
                                </c:when>
                                <c:otherwise>
                                    <c:url var="setDefaultUrl" value="/action/Warehouse/Warehouse/SetDefault">
                                        <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                                    </c:url>
                                    <a href="${setDefaultUrl}">Set Default</a>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column>
                            <c:url var="warehousePrinterGroupUsesUrl" value="/action/Warehouse/WarehousePrinterGroupUse/Main">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${warehousePrinterGroupUsesUrl}">Printer Group Uses</a>
                            <c:url var="warehouseContactMechanismsUrl" value="/action/Warehouse/WarehouseContactMechanism/Main">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${warehouseContactMechanismsUrl}">Contact Mechanisms</a>
                            <c:url var="locationsUrl" value="/action/Warehouse/Location/Main">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url><br />
                            <a href="${locationsUrl}">Locations</a>
                            <c:url var="locationTypesUrl" value="/action/Warehouse/LocationType/Main">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${locationTypesUrl}">Location Types</a>
                            <c:url var="inventoryLocationGroupsUrl" value="/action/Inventory/InventoryLocationGroup/Main">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${inventoryLocationGroupsUrl}">Inventory Location Groups</a><br />
                            <c:url var="editUrl" value="/action/Warehouse/Warehouse/WarehouseEdit">
                                <c:param name="OriginalWarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Warehouse/Warehouse/Delete">
                                <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column>
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${warehouse.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                    </display:table>
                    <c:url var="resultsUrl" value="/action/Warehouse/Warehouse/Main">
                        <c:param name="Results" value="Complete" />
                    </c:url>
                    <a href="${resultsUrl}">All <fmt:message key="navigation.warehouses" /></a>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
