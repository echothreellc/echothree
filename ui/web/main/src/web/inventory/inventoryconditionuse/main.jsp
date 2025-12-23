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
        <title>Inventory Condition Uses</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Inventory/Main" />">Inventory</a> &gt;&gt;
                <a href="<c:url value="/action/Inventory/InventoryConditionUseType/Main" />">Inventory Condition Use Types</a> &gt;&gt;
                Conditions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Inventory/InventoryConditionUse/Add">
                <c:param name="InventoryConditionUseTypeName" value="${inventoryConditionUseType.inventoryConditionUseTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Condition.</a></p>
            <display:table name="inventoryConditionUses" id="inventoryConditionUse" class="displaytag">
                <display:column titleKey="columnTitle.condition">
                    <c:out value="${inventoryConditionUse.inventoryCondition.description}" />
                </display:column>
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${inventoryConditionUse.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Inventory/InventoryConditionUse/SetDefault">
                                <c:param name="InventoryConditionName" value="${inventoryConditionUse.inventoryCondition.inventoryConditionName}" />
                                <c:param name="InventoryConditionUseTypeName" value="${inventoryConditionUse.inventoryConditionUseType.inventoryConditionUseTypeName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="deleteUrl" value="/action/Inventory/InventoryConditionUse/Delete">
                        <c:param name="InventoryConditionName" value="${inventoryConditionUse.inventoryCondition.inventoryConditionName}" />
                        <c:param name="InventoryConditionUseTypeName" value="${inventoryConditionUse.inventoryConditionUseType.inventoryConditionUseTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
