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
        <title>Inventory Conditions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Inventory/Main" />">Inventory</a> &gt;&gt;
                Inventory Conditions
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Inventory/InventoryCondition/Add" />">Add Inventory Condition.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="inventoryConditions" id="inventoryCondition" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Inventory/InventoryCondition/Review">
                        <c:param name="InventoryConditionName" value="${inventoryCondition.inventoryConditionName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${inventoryCondition.inventoryConditionName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${inventoryCondition.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${inventoryCondition.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Inventory/InventoryCondition/SetDefault">
                                <c:param name="InventoryConditionName" value="${inventoryCondition.inventoryConditionName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Inventory/InventoryCondition/Edit">
                        <c:param name="OriginalInventoryConditionName" value="${inventoryCondition.inventoryConditionName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Inventory/InventoryCondition/Description">
                        <c:param name="InventoryConditionName" value="${inventoryCondition.inventoryConditionName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Inventory/InventoryCondition/Delete">
                        <c:param name="InventoryConditionName" value="${inventoryCondition.inventoryConditionName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${inventoryCondition.entityInstance.entityRef}" />
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
