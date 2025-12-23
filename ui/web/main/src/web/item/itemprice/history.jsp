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
        <title><fmt:message key="pageTitle.itemPrices" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <et:checkSecurityRoles securityRoles="Item.List:ItemCategory.List:ItemAliasType.List:ItemDescriptionType.List:ItemDescriptionTypeUseType.List:ItemImageType.List:RelatedItemType.List:HarmonizedTariffScheduleCodeUseType.List:HarmonizedTariffScheduleCodeUnit.List:Item.Search:Item.Review:InventoryCondition.Review:UnitOfMeasureType.Review:Currency.Review:ItemPrice.List:ItemPrice.Create:ItemPrice.Edit:ItemPrice.Delete:ItemPrice.History" />
        <div id="Header">
            <et:hasSecurityRole securityRoles="Item.List:ItemCategory.List:ItemAliasType.List:ItemDescriptionType.List:ItemDescriptionTypeUseType.List:ItemImageType.List:RelatedItemType.List:HarmonizedTariffScheduleCodeUseType.List:HarmonizedTariffScheduleCodeUnit.List" var="includeItemsUrl" />
            <et:hasSecurityRole securityRole="Item.Search" var="includeItemSearchUrl" />
            <et:hasSecurityRole securityRole="Item.Review" var="includeItemUrl" />
            <et:hasSecurityRole securityRole="ItemPrice.List" var="includeItemPricesUrl" />
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
                            <c:param name="ItemName" value="${itemPrice.item.itemName}" />
                        </c:url>
                        <a href="${reviewUrl}">Review (<c:out value="${itemPrice.item.itemName}" />)</a>
                    </c:when>
                    <c:otherwise>
                        Review (<c:out value="$itemPrice.{item.itemName}" />)
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                <c:choose>
                    <c:when test="${includeItemPricesUrl}">
                        <c:url var="itemPricesUrl" value="/action/Item/ItemPrice/Main">
                            <c:param name="ItemName" value="${itemPrice.item.itemName}" />
                        </c:url>
                        <a href="${itemPricesUrl}"><fmt:message key="navigation.itemPrices" /></a>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="navigation.itemPrices" />
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                <fmt:message key="navigation.history" />
            </h2>
        </div>
        <div id="Content">
            <et:hasSecurityRole securityRole="Item.Review" var="includeItemUrl" />
            <et:hasSecurityRole securityRole="InventoryCondition.Review" var="includeInventoryConditionUrl" />
            <et:hasSecurityRole securityRole="UnitOfMeasureType.Review" var="includeUnitOfMeasureTypeUrl" />
            <et:hasSecurityRole securityRole="Currency.Review" var="includeCurrencyUrl" />
            Item:
            <c:choose>
                <c:when test="${includeItemUrl}">
                    <c:url var="reviewUrl" value="/action/Item/Item/Review">
                        <c:param name="ItemName" value="${itemPrice.item.itemName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${itemPrice.item.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${itemPrice.item.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Inventory Condition:
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
            <br />
            Unit Of Measure Type:
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
            <br />
            Currency:
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
            <br />
            <br />
            <display:table name="itemPriceHistory.list" id="history" class="displaytag" export="true" sort="list" requestURI="/action/Item/ItemPrice/History">
                <display:setProperty name="export.csv.filename" value="ItemPriceHistory.csv" />
                <display:setProperty name="export.excel.filename" value="ItemPriceHistory.xls" />
                <display:setProperty name="export.pdf.filename" value="ItemPriceHistory.pdf" />
                <display:setProperty name="export.rtf.filename" value="ItemPriceHistory.rtf" />
                <display:setProperty name="export.xml.filename" value="ItemPriceHistory.xml" />
                <display:column titleKey="columnTitle.price" class="amount" media="html" sortable="true" sortProperty="snapshot.unformattedUnitPrice">
                    <c:choose>
                        <c:when test="${history.snapshot.item.itemPriceType.itemPriceTypeName == 'FIXED'}">
                            <c:out value="${history.snapshot.unitPrice}" />
                        </c:when>
                        <c:when test="${history.snapshot.item.itemPriceType.itemPriceTypeName == 'VARIABLE'}">
                            Minimum: <c:out value="${history.snapshot.minimumUnitPrice}" /><br />
                            Maximum: <c:out value="${history.snapshot.maximumUnitPrice}" /><br />
                            Increment: <c:out value="${history.snapshot.unitPriceIncrement}" />
                        </c:when>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.fromTime" media="html" sortable="true" sortProperty="unformattedFromTime">
                    <c:out value="${history.fromTime}" />
                </display:column>
                <display:column titleKey="columnTitle.thruTime" media="html" sortable="true" sortProperty="unformattedThruTime">
                    <c:if test="${history.unformattedThruTime != 9223372036854775807}">
                        <c:out value="${history.thruTime}" />
                    </c:if>
                </display:column>
                <display:column property="snapshot.item.itemPriceType.description" titleKey="columnTitle.itemPriceType" media="csv excel pdf rtf xml" />
                <display:column property="snapshot.unitPrice" titleKey="columnTitle.unitPrice" media="csv excel pdf rtf xml" />
                <display:column property="snapshot.minimumUnitPrice" titleKey="columnTitle.minimumUnitPrice" media="csv excel pdf rtf xml" />
                <display:column property="snapshot.maximumUnitPrice" titleKey="columnTitle.maximumUnitPrice" media="csv excel pdf rtf xml" />
                <display:column property="snapshot.unitPriceIncrement" titleKey="columnTitle.unitPriceIncrement" media="csv excel pdf rtf xml" />
                <display:column property="fromTime" titleKey="columnTitle.fromTime" media="csv excel pdf rtf xml" />
                <display:column property="thruTime" titleKey="columnTitle.thruTime" media="csv excel pdf rtf xml" />
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
