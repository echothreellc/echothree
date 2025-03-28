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
        <title>Unit Limits (<c:out value="${item.itemName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />">Items</a> &gt;&gt;
                <a href="<c:url value="/action/Item/Item/Main" />">Search</a> &gt;&gt;
                <et:countItemResults searchTypeName="ITEM_MAINTENANCE" countVar="itemResultsCount" commandResultVar="countItemResultsCommandResult" logErrors="false" />
                <c:if test="${itemResultsCount > 0}">
                    <a href="<c:url value="/action/Item/Item/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Item/Item/Review">
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${item.itemName}" />)</a> &gt;&gt;
                Unit Limits
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="InventoryCondition.Review:UnitOfMeasureType.Review:ItemUnitLimit.Create:ItemUnitLimit.Edit:ItemUnitLimit.Delete" />
            <et:hasSecurityRole securityRole="InventoryCondition.Review" var="includeInventoryConditionUrl" />
            <et:hasSecurityRole securityRole="UnitOfMeasureType.Review" var="includeUnitOfMeasureTypeUrl" />
            <et:hasSecurityRole securityRoles="ItemUnitLimit.Edit:ItemUnitLimit.Delete">
                <c:set var="linksInItemUnitLimitFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemUnitLimit.Create">
                <c:url var="addUrl" value="/action/Item/ItemUnitLimit/Add">
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <p><a href="${addUrl}">Add Unit Limit.</a></p>
            </et:hasSecurityRole>
            <display:table name="itemUnitLimits" id="itemUnitLimit" class="displaytag">
                <display:column titleKey="columnTitle.inventoryCondition">
                    <c:choose>
                        <c:when test="${includeInventoryConditionUrl}">
                            <c:url var="reviewUrl" value="/action/Inventory/InventoryCondition/Review">
                                <c:param name="InventoryConditionName" value="${itemUnitLimit.inventoryCondition.inventoryConditionName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemUnitLimit.inventoryCondition.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemUnitLimit.inventoryCondition.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.unitOfMeasureType">
                    <c:choose>
                        <c:when test="${includeUnitOfMeasureTypeUrl}">
                            <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                <c:param name="UnitOfMeasureKindName" value="${itemUnitLimit.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${itemUnitLimit.unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemUnitLimit.unitOfMeasureType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemUnitLimit.unitOfMeasureType.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.minimumQuantity" class="quantity">
                    <c:out value="${itemUnitLimit.minimumQuantity}" />
                </display:column>
                <display:column titleKey="columnTitle.maximumQuantity" class="quantity">
                    <c:out value="${itemUnitLimit.maximumQuantity}" />
                </display:column>
                <c:if test='${linksInItemUnitLimitFirstRow}'>
                    <display:column>
                        <et:hasSecurityRole securityRole="ItemUnitLimit.Edit">
                            <c:url var="editUrl" value="/action/Item/ItemUnitLimit/Edit">
                                <c:param name="ItemName" value="${itemUnitLimit.item.itemName}" />
                                <c:param name="InventoryConditionName" value="${itemUnitLimit.inventoryCondition.inventoryConditionName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${itemUnitLimit.unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ItemUnitLimit.Delete">
                            <c:url var="deleteUrl" value="/action/Item/ItemUnitLimit/Delete">
                                <c:param name="ItemName" value="${itemUnitLimit.item.itemName}" />
                                <c:param name="InventoryConditionName" value="${itemUnitLimit.inventoryCondition.inventoryConditionName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${itemUnitLimit.unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
