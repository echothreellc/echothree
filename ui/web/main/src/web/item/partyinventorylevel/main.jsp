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
        <title>Inventory Levels (<c:out value="${item.itemName}" />)</title>
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
                Inventory Levels
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Item/PartyInventoryLevel/Add">
                <c:param name="ItemName" value="${item.itemName}" />
            </c:url>
            <p><a href="${addUrl}">Add Inventory Level.</a></p>
            <display:table name="partyInventoryLevels" id="partyInventoryLevel" class="displaytag">
                <display:column titleKey="columnTitle.party">
                    <c:set var="party" scope="request" value="${partyInventoryLevel.party}" />
                    <jsp:include page="../../include/party.jsp" />
                </display:column>
                <display:column titleKey="columnTitle.inventoryCondition">
                    <c:out value="${partyInventoryLevel.inventoryCondition.description}" />
                </display:column>
                <display:column titleKey="columnTitle.minimumInventory">
                    <c:out value="${partyInventoryLevel.minimumInventory}" />
                </display:column>
                <display:column titleKey="columnTitle.maximumInventory">
                    <c:out value="${partyInventoryLevel.maximumInventory}" />
                </display:column>
                <display:column titleKey="columnTitle.reorderQuantity">
                    <c:out value="${partyInventoryLevel.reorderQuantity}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Item/PartyInventoryLevel/Edit">
                        <c:param name="PartyName" value="${partyInventoryLevel.party.partyName}" />
                        <c:param name="ItemName" value="${partyInventoryLevel.item.itemName}" />
                        <c:param name="InventoryConditionName" value="${partyInventoryLevel.inventoryCondition.inventoryConditionName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Item/PartyInventoryLevel/Delete">
                        <c:param name="PartyName" value="${partyInventoryLevel.party.partyName}" />
                        <c:param name="ItemName" value="${partyInventoryLevel.item.itemName}" />
                        <c:param name="InventoryConditionName" value="${partyInventoryLevel.inventoryCondition.inventoryConditionName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
