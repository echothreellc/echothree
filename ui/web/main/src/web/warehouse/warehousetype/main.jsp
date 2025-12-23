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
        <title><fmt:message key="pageTitle.warehouseTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <fmt:message key="navigation.warehouseTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="WarehouseType.Create:WarehouseType.Review:Event.List" />
            <et:hasSecurityRole securityRole="WarehouseType.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="WarehouseType.Create">
                <p><a href="<c:url value="/action/Warehouse/WarehouseType/Add" />">Add Warehouse Type.</a></p>
            </et:hasSecurityRole>
            <br />
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <c:choose>
                <c:when test="${warehouseTypeCount == null || warehouseTypeCount < 21}">
                    <display:table name="warehouseTypes.list" id="warehouseType" class="displaytag" export="true" sort="list" requestURI="/action/Warehouse/WarehouseType/Main">
                        <display:setProperty name="export.csv.filename" value="WarehouseTypes.csv" />
                        <display:setProperty name="export.excel.filename" value="WarehouseTypes.xls" />
                        <display:setProperty name="export.pdf.filename" value="WarehouseTypes.pdf" />
                        <display:setProperty name="export.rtf.filename" value="WarehouseTypes.rtf" />
                        <display:setProperty name="export.xml.filename" value="WarehouseTypes.xml" />
                        <display:column media="html">
                            <c:choose>
                                <c:when test="${warehouseType.entityInstance.entityVisit == null}">
                                    New
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${warehouseType.entityInstance.entityVisit.unformattedVisitedTime >= warehouseType.entityInstance.entityTime.unformattedModifiedTime}">
                                            Unchanged
                                        </c:when>
                                        <c:otherwise>
                                            Updated
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="warehouseTypeName">
                            <c:choose>
                                <c:when test="${includeReviewUrl}">
                                    <c:url var="reviewUrl" value="/action/Warehouse/WarehouseType/Review">
                                        <c:param name="WarehouseTypeName" value="${warehouseType.warehouseTypeName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><et:appearance appearance="${warehouseType.entityInstance.entityAppearance.appearance}"><c:out value="${warehouseType.warehouseTypeName}" /></et:appearance></a>
                                </c:when>
                                <c:otherwise>
                                    <et:appearance appearance="${warehouseType.entityInstance.entityAppearance.appearance}"><c:out value="${warehouseType.warehouseTypeName}" /></et:appearance>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.description" media="html" sortable="true" sortProperty="description">
                            <et:appearance appearance="${warehouseType.entityInstance.entityAppearance.appearance}"><c:out value="${warehouseType.description}" /></et:appearance>
                        </display:column>
                        <display:column property="priority" titleKey="columnTitle.priority" media="html" sortable="true" sortProperty="priority" />
                        <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="html" sortable="true" sortProperty="sortOrder" />
                        <display:column titleKey="columnTitle.default" media="html" sortable="true" sortProperty="isDefault">
                            <c:choose>
                                <c:when test="${warehouseType.isDefault}">
                                    Default
                                </c:when>
                                <c:otherwise>
                                    <c:url var="setDefaultUrl" value="/action/Warehouse/WarehouseType/SetDefault">
                                        <c:param name="WarehouseTypeName" value="${warehouseType.warehouseTypeName}" />
                                    </c:url>
                                    <a href="${setDefaultUrl}">Set Default</a>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column media="html">
                            <c:url var="editUrl" value="/action/Warehouse/WarehouseType/Edit">
                                <c:param name="OriginalWarehouseTypeName" value="${warehouseType.warehouseTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Warehouse/WarehouseType/Delete">
                                <c:param name="WarehouseTypeName" value="${warehouseType.warehouseTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column media="html">
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${warehouseType.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                        <display:column property="warehouseTypeName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                        <display:column property="description" titleKey="columnTitle.description" media="csv excel pdf rtf xml" />
                        <display:column property="priority" titleKey="columnTitle.sortOrder" media="csv excel pdf rtf xml" />
                        <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="csv excel pdf rtf xml" />
                        <display:column property="isDefault" titleKey="columnTitle.default" media="csv excel pdf rtf xml" />
                    </display:table>
                    <c:if test="${warehouseTypes.size > 20}">
                        <c:url var="resultsUrl" value="/action/Warehouse/WarehouseType/Main" />
                        <a href="${resultsUrl}">Paged Listing</a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <display:table name="warehouseTypes.list" id="warehouseType" class="displaytag" partialList="true" pagesize="20" size="warehouseTypeCount" requestURI="/action/Warehouse/WarehouseType/Main">
                        <display:column>
                            <c:choose>
                                <c:when test="${warehouseType.entityInstance.entityVisit == null}">
                                    New
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${warehouseType.entityInstance.entityVisit.unformattedVisitedTime >= warehouseType.entityInstance.entityTime.unformattedModifiedTime}">
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
                                    <c:url var="reviewUrl" value="/action/Warehouse/WarehouseType/Review">
                                        <c:param name="WarehouseName" value="${warehouseType.warehouseTypeName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><et:appearance appearance="${warehouseType.entityInstance.entityAppearance.appearance}"><c:out value="${warehouseType.warehouseTypeName}" /></et:appearance></a>
                                </c:when>
                                <c:otherwise>
                                    <et:appearance appearance="${warehouseType.entityInstance.entityAppearance.appearance}"><c:out value="${warehouseType.warehouseTypeName}" /></et:appearance>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.description">
                            <et:appearance appearance="${warehouseType.entityInstance.entityAppearance.appearance}"><c:out value="${warehouseType.description}" /></et:appearance>
                        </display:column>
                        <display:column property="priority" titleKey="columnTitle.priority" />
                        <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                        <display:column titleKey="columnTitle.default">
                            <c:choose>
                                <c:when test="${warehouseType.isDefault}">
                                    Default
                                </c:when>
                                <c:otherwise>
                                    <c:url var="setDefaultUrl" value="/action/Warehouse/WarehouseType/SetDefault">
                                        <c:param name="WarehouseName" value="${warehouseType.warehouseTypeName}" />
                                    </c:url>
                                    <a href="${setDefaultUrl}">Set Default</a>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column>
                            <c:url var="editUrl" value="/action/Warehouse/WarehouseType/Edit">
                                <c:param name="OriginalWarehouseTypeName" value="${warehouseType.warehouseTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Warehouse/WarehouseType/Delete">
                                <c:param name="WarehouseTypeName" value="${warehouseType.warehouseTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column>
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${warehouseType.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                    </display:table>
                    <c:url var="resultsUrl" value="/action/Warehouse/WarehouseType/Main">
                        <c:param name="Results" value="Complete" />
                    </c:url>
                    <a href="${resultsUrl}">All <fmt:message key="navigation.warehouseTypes" /></a>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
