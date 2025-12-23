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
        <title>Wishlist (<c:out value="${wishlistName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Customer/Main" />">Search</a> &gt;&gt;
                <et:countCustomerResults searchTypeName="ORDER_ENTRY" countVar="customerResultsCount" commandResultVar="countCustomerResultsCommandResult" logErrors="false" />
                <c:if test="${customerResultsCount > 0}">
                    <a href="<c:url value="/action/Customer/Customer/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Customer/Customer/Review">
                    <c:param name="CustomerName" value="${customerName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${customerName}" />)</a> &gt;&gt;
                Wishlist (<c:out value="${wishlistName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="wishlistLines" id="wishlistLine" class="displaytag" sort="list" requestURI="/action/Customer/Customer/WishlistReview">
                <display:column titleKey="columnTitle.line" sortable="true" sortProperty="orderLineSequence">
                    <c:out value="${wishlistLine.orderLineSequence}" />
                </display:column>
                <display:column titleKey="columnTitle.offerUse">
                    <c:url var="offerUseUrl" value="/action/Advertising/OfferUse/Review">
                        <c:param name="OfferName" value="${wishlistLine.offerUse.offer.offerName}" />
                        <c:param name="UseName" value="${wishlistLine.offerUse.use.useName}" />
                    </c:url>
                    <a href="${offerUseUrl}"><c:out value="${wishlistLine.offerUse.offer.offerName}" />:<c:out value="${wishlistLine.offerUse.use.useName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.item" sortable="true" sortProperty="item.itemName">
                    <c:url var="itemUrl" value="/action/Item/Item/Review">
                        <c:param name="ItemName" value="${wishlistLine.item.itemName}" />
                    </c:url>
                    <a href="${itemUrl}"><c:out value="${wishlistLine.item.itemName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description" sortable="true" sortProperty="item.description">
                    <c:out value="${wishlistLine.item.description}" />
                    <c:if test='${wishlistLine.comment != null}'>
                        <br />
                        Comment: <c:out value="${wishlistLine.comment}" />
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.condition" sortable="true" sortProperty="inventoryCondition.inventoryConditionName">
                    <c:url var="inventoryConditionUrl" value="/action/Inventory/InventoryCondition/Review">
                        <c:param name="InventoryConditionName" value="${wishlistLine.inventoryCondition.inventoryConditionName}" />
                    </c:url>
                    <a href="${inventoryConditionUrl}"><c:out value="${wishlistLine.inventoryCondition.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.uoM" sortable="true" sortProperty="unitOfMeasureType.unitOfMeasureTypeName">
                    <c:url var="unitOfMeasureTypeUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                        <c:param name="UnitOfMeasureKindName" value="${wishlistLine.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${wishlistLine.unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${unitOfMeasureTypeUrl}"><c:out value="${wishlistLine.unitOfMeasureType.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.quantity" sortable="true" sortProperty="quantity">
                    <c:out value="${wishlistLine.quantity}" />
                </display:column>
                <display:column titleKey="columnTitle.unitPrice" class="amount">
                    <c:out value="${wishlistLine.unitAmount}" />
                </display:column>
                <display:column titleKey="columnTitle.priority" sortable="true" sortProperty="wishlistPriority.wishlistPriorityName">
                    <c:url var="wishlistPriorityUrl" value="/action/Inventory/InventoryCondition/Review">
                        <c:param name="WishlistTypeName" value="${wishlistLine.wishlistPriority.wishlistType.wishlistTypeName}" />
                        <c:param name="WishlistPriorityName" value="${wishlistLine.wishlistPriority.wishlistPriorityName}" />
                    </c:url>
                    <a href="${wishlistPriorityUrl}"><c:out value="${wishlistLine.wishlistPriority.description}" /></a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${wishlistLine.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
                <display:column titleKey="columnTitle.test">
                    <c:url var="testUrl" value="/action/Customer/Customer/WishlistReview">
                        <c:param name="ItemName" value="${wishlistLine.item.itemName}" />
                    </c:url>
                    <a href="${testUrl}">Test</a>
                    <c:url var="wishlistUrl" value="/action/Customer/Customer/WishlistReview">
                        <c:param name="WishlistName" value="${wishlistLine.wishlist.wishlistName}" />
                    </c:url>
                    <a href="${wishlistUrl}"><c:out value="${wishlistLine.wishlist.wishlistName}" /></a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
