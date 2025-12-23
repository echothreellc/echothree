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
        <title><fmt:message key="pageTitle.offerItemPrices" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <et:checkSecurityRoles securityRoles="Offer.List:Use.List:Source.List:UseType.List:OfferNameElement.List:UseNameElement.List:Offer.List:OfferItem.List:OfferItemPrice.Create:OfferItemPrice.History:OfferItemPrice.Edit:OfferItemPrice.Delete:Offer.Review:Item.Review:InventoryCondition.Review:UnitOfMeasureType.Review:Currency.Review" />
        <div id="Header">
            <et:hasSecurityRole securityRoles="Offer.List:Use.List:Source.List:UseType.List:OfferNameElement.List:UseNameElement.List" var="includeAdvertisingUrl" />
            <et:hasSecurityRole securityRole="Offer.List" var="includeOffersUrl" />
            <et:hasSecurityRole securityRole="OfferItem.List" var="includeOfferItemsUrl" />
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <c:choose>
                    <c:when test="${includeAdvertisingUrl}">
                        <a href="<c:url value="/action/Advertising/Main" />"><fmt:message key="navigation.advertising" /></a>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="navigation.advertising" />
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                <c:choose>
                    <c:when test="${includeOffersUrl}">
                        <a href="<c:url value="/action/Advertising/Offer/Main" />"><fmt:message key="navigation.offers" /></a>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="navigation.offers" />
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                <c:choose>
                    <c:when test="${includeOfferItemsUrl}">
                        <c:url var="offerItemsUrl" value="/action/Advertising/OfferItem/Main">
                            <c:param name="OfferName" value="${offerItem.offer.offerName}" />
                        </c:url>
                        <a href="${offerItemsUrl}"><fmt:message key="navigation.offerItems" /></a>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="navigation.offerItems" />
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                <fmt:message key="navigation.offerItemPrices" />
            </h2>
        </div>
        <div id="Content">
            <et:hasSecurityRole securityRole="Offer.Review" var="includeOfferUrl" />
            <et:hasSecurityRole securityRole="Item.Review" var="includeItemUrl" />
            <et:hasSecurityRole securityRole="InventoryCondition.Review" var="includeInventoryConditionUrl" />
            <et:hasSecurityRole securityRole="UnitOfMeasureType.Review" var="includeUnitOfMeasureTypeUrl" />
            <et:hasSecurityRole securityRole="Currency.Review" var="includeCurrencyUrl" />
            Offer:
            <c:choose>
                <c:when test="${includeOfferUrl}">
                    <c:url var="reviewUrl" value="/action/Advertising/Offer/Review">
                        <c:param name="OfferName" value="${offerItem.offer.offerName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offerItem.offer.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${offerItem.offer.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Item:
            <c:choose>
                <c:when test="${includeItemUrl}">
                    <c:url var="reviewUrl" value="/action/Item/Item/Review">
                        <c:param name="ItemName" value="${offerItem.item.itemName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offerItem.item.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${offerItem.item.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <et:hasSecurityRole securityRole="OfferItemPrice.Create">
                <c:url var="addUrl" value="/action/Advertising/OfferItemPrice/Add">
                    <c:param name="OfferName" value="${offer.offerName}" />
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <p><a href="${addUrl}">Add Offer Item Price.</a></p>
            </et:hasSecurityRole>
            <display:table name="offerItemPrices" id="offerItemPrice" class="displaytag">
                <display:column titleKey="columnTitle.inventoryCondition">
                    <c:choose>
                        <c:when test="${includeInventoryConditionUrl}">
                            <c:url var="reviewUrl" value="/action/Inventory/InventoryCondition/Review">
                                <c:param name="InventoryConditionName" value="${offerItemPrice.inventoryCondition.inventoryConditionName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${offerItemPrice.inventoryCondition.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${offerItemPrice.inventoryCondition.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.unitOfMeasureType">
                    <c:choose>
                        <c:when test="${includeUnitOfMeasureTypeUrl}">
                            <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                <c:param name="UnitOfMeasureKindName" value="${offerItemPrice.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${offerItemPrice.unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${offerItemPrice.unitOfMeasureType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${offerItemPrice.unitOfMeasureType.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.currency">
                    <c:choose>
                        <c:when test="${includeCurrencyUrl}">
                            <c:url var="reviewUrl" value="/action/Accounting/Currency/Review">
                                <c:param name="CurrencyIsoName" value="${offerItemPrice.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${offerItemPrice.currency.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${offerItemPrice.currency.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.price" class="amount">
                    <c:choose>
                        <c:when test="${offerItemPrice.offerItem.item.itemPriceType.itemPriceTypeName == 'FIXED'}">
                            <c:out value="${offerItemPrice.unitPrice}" />
                        </c:when>
                        <c:when test="${offerItemPrice.offerItem.item.itemPriceType.itemPriceTypeName == 'VARIABLE'}">
                            Minimum: <c:out value="${offerItemPrice.minimumUnitPrice}" /><br />
                            Maximum: <c:out value="${offerItemPrice.maximumUnitPrice}" /><br />
                            Increment: <c:out value="${offerItemPrice.unitPriceIncrement}" />
                        </c:when>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="OfferItemPrice.Edit:OfferItemPrice.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="OfferItemPrice.Edit">
                            <c:url var="editUrl" value="/action/Advertising/OfferItemPrice/Edit">
                                <c:param name="OfferName" value="${offerItemPrice.offerItem.offer.offerName}" />
                                <c:param name="ItemName" value="${offerItemPrice.offerItem.item.itemName}" />
                                <c:param name="InventoryConditionName" value="${offerItemPrice.inventoryCondition.inventoryConditionName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${offerItemPrice.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="CurrencyIsoName" value="${offerItemPrice.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="OfferItemPrice.Delete">
                            <c:url var="deleteUrl" value="/action/Advertising/OfferItemPrice/Delete">
                                <c:param name="OfferName" value="${offerItemPrice.offerItem.offer.offerName}" />
                                <c:param name="ItemName" value="${offerItemPrice.offerItem.item.itemName}" />
                                <c:param name="InventoryConditionName" value="${offerItemPrice.inventoryCondition.inventoryConditionName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${offerItemPrice.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="CurrencyIsoName" value="${offerItemPrice.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="OfferItemPrice.History">
                    <display:column>
                        <c:url var="historyUrl" value="/action/Advertising/OfferItemPrice/History">
                            <c:param name="OfferName" value="${offerItemPrice.offerItem.offer.offerName}" />
                            <c:param name="ItemName" value="${offerItemPrice.offerItem.item.itemName}" />
                            <c:param name="InventoryConditionName" value="${offerItemPrice.inventoryCondition.inventoryConditionName}" />
                            <c:param name="UnitOfMeasureTypeName" value="${offerItemPrice.unitOfMeasureType.unitOfMeasureTypeName}" />
                            <c:param name="CurrencyIsoName" value="${offerItemPrice.currency.currencyIsoName}" />
                        </c:url>
                        <a href="${historyUrl}">History</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
