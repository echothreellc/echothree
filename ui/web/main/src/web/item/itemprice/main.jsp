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
        <title>Prices (<c:out value="${item.itemName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <et:checkSecurityRoles securityRoles="Item.List:ItemCategory.List:ItemAliasType.List:ItemDescriptionType.List:ItemDescriptionTypeUseType.List:ItemImageType.List:RelatedItemType.List:HarmonizedTariffScheduleCodeUseType.List:HarmonizedTariffScheduleCodeUnit.List:Item.Search:Item.Review:InventoryCondition.Review:UnitOfMeasureType.Review:Currency.Review:ItemPrice.Create:ItemPrice.Edit:ItemPrice.Delete:ItemPrice.History" />
        <div id="Header">
            <et:hasSecurityRole securityRoles="Item.List:ItemCategory.List:ItemAliasType.List:ItemDescriptionType.List:ItemDescriptionTypeUseType.List:ItemImageType.List:RelatedItemType.List:HarmonizedTariffScheduleCodeUseType.List:HarmonizedTariffScheduleCodeUnit.List" var="includeItemsUrl" />
            <et:hasSecurityRole securityRole="Item.Search" var="includeItemSearchUrl" />
            <et:hasSecurityRole securityRole="Item.Review" var="includeItemUrl" />
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <c:choose>
                    <c:when test="${includeItemsUrl}">
                        <a href="<c:url value="/action/Item/Main" />">Items</a>
                    </c:when>
                    <c:otherwise>
                        Items
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                <c:choose>
                    <c:when test="${includeItemSearchUrl}">
                        <a href="<c:url value="/action/Item/Item/Main" />">Search</a>
                    </c:when>
                    <c:otherwise>
                        Search
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                <et:countItemResults searchTypeName="ITEM_MAINTENANCE" countVar="itemResultsCount" commandResultVar="countItemResultsCommandResult" logErrors="false" />
                <c:if test="${itemResultsCount > 0}">
                    <a href="<c:url value="/action/Item/Item/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:choose>
                    <c:when test="${includeItemUrl}">
                        <c:url var="reviewUrl" value="/action/Item/Item/Review">
                            <c:param name="ItemName" value="${item.itemName}" />
                        </c:url>
                        <a href="${reviewUrl}">Review (<c:out value="${item.itemName}" />)</a>
                    </c:when>
                    <c:otherwise>
                        Review (<c:out value="${item.itemName}" />)
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                Prices
            </h2>
        </div>
        <div id="Content">
            <et:hasSecurityRole securityRole="InventoryCondition.Review" var="includeInventoryConditionUrl" />
            <et:hasSecurityRole securityRole="UnitOfMeasureType.Review" var="includeUnitOfMeasureTypeUrl" />
            <et:hasSecurityRole securityRole="Currency.Review" var="includeCurrencyUrl" />
            Item:
            <c:choose>
                <c:when test="${includeItemUrl}">
                    <c:url var="reviewUrl" value="/action/Item/Item/Review">
                        <c:param name="ItemName" value="${item.itemName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${item.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${item.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <et:hasSecurityRole securityRole="ItemPrice.Create">
                <c:url var="addUrl" value="/action/Item/ItemPrice/Add">
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <p><a href="${addUrl}">Add Price.</a></p>
            </et:hasSecurityRole>
            <display:table name="itemPrices" id="itemPrice" class="displaytag">
                <display:column titleKey="columnTitle.inventoryCondition">
                    <c:choose>
                        <c:when test="${includeInventoryConditionUrl}">
                            <c:url var="reviewUrl" value="/action/Inventory/InventoryCondition/Review">
                                <c:param name="InventoryConditionName" value="${itemPrice.inventoryCondition.inventoryConditionName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemPrice.inventoryCondition.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemPrice.inventoryCondition.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.unitOfMeasureType">
                    <c:choose>
                        <c:when test="${includeUnitOfMeasureTypeUrl}">
                            <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                <c:param name="UnitOfMeasureKindName" value="${itemPrice.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${itemPrice.unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemPrice.unitOfMeasureType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemPrice.unitOfMeasureType.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.currency">
                    <c:choose>
                        <c:when test="${includeCurrencyUrl}">
                            <c:url var="reviewUrl" value="/action/Accounting/Currency/Review">
                                <c:param name="CurrencyIsoName" value="${itemPrice.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemPrice.currency.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemPrice.currency.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.price" class="amount">
                    <c:choose>
                        <c:when test="${itemPrice.item.itemPriceType.itemPriceTypeName == 'FIXED'}">
                            <c:out value="${itemPrice.unitPrice}" />
                        </c:when>
                        <c:when test="${itemPrice.item.itemPriceType.itemPriceTypeName == 'VARIABLE'}">
                            Minimum: <c:out value="${itemPrice.minimumUnitPrice}" /><br />
                            Maximum: <c:out value="${itemPrice.maximumUnitPrice}" /><br />
                            Increment: <c:out value="${itemPrice.unitPriceIncrement}" />
                        </c:when>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="ItemPrice.Edit:ItemPrice.Delete">
                    <display:column>
                        <c:url var="editUrl" value="/action/Item/ItemPrice/Edit">
                            <c:param name="ItemName" value="${itemPrice.item.itemName}" />
                            <c:param name="InventoryConditionName" value="${itemPrice.inventoryCondition.inventoryConditionName}" />
                            <c:param name="UnitOfMeasureTypeName" value="${itemPrice.unitOfMeasureType.unitOfMeasureTypeName}" />
                            <c:param name="CurrencyIsoName" value="${itemPrice.currency.currencyIsoName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                        <c:url var="deleteUrl" value="/action/Item/ItemPrice/Delete">
                            <c:param name="ItemName" value="${itemPrice.item.itemName}" />
                            <c:param name="InventoryConditionName" value="${itemPrice.inventoryCondition.inventoryConditionName}" />
                            <c:param name="UnitOfMeasureTypeName" value="${itemPrice.unitOfMeasureType.unitOfMeasureTypeName}" />
                            <c:param name="CurrencyIsoName" value="${itemPrice.currency.currencyIsoName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="ItemPrice.History">
                    <display:column>
                        <c:url var="historyUrl" value="/action/Item/ItemPrice/History">
                            <c:param name="ItemName" value="${itemPrice.item.itemName}" />
                            <c:param name="InventoryConditionName" value="${itemPrice.inventoryCondition.inventoryConditionName}" />
                            <c:param name="UnitOfMeasureTypeName" value="${itemPrice.unitOfMeasureType.unitOfMeasureTypeName}" />
                            <c:param name="CurrencyIsoName" value="${itemPrice.currency.currencyIsoName}" />
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
