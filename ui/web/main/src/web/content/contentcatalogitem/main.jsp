<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2020 Echo Three, LLC                                              -->
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
        <title>Items</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />">Home</a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />">Collections</a> &gt;&gt;
                <c:url var="contentCatalogsUrl" value="/action/Content/ContentCatalog/Main">
                    <c:param name="ContentCollectionName" value="${contentCatalog.contentCollection.contentCollectionName}" />
                </c:url>
                <a href="${contentCatalogsUrl}">Catalogs</a> &gt;&gt;
                Items
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${contentCatalogItemCount == null || contentCatalogItemCount < 21}">
                    <display:table name="contentCatalogItems.list" id="contentCatalogItem" class="displaytag" sort="list" requestURI="/action/Content/ContentCatalogItem/Main">
                        <display:column titleKey="columnTitle.item" sortable="true" sortProperty="item.itemName">
                            <c:url var="itemUrl" value="/action/Item/Item/Review">
                                <c:param name="ItemName" value="${contentCatalogItem.item.itemName}" />
                            </c:url>
                            <a href="${itemUrl}"><c:out value="${contentCatalogItem.item.itemName}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.description" sortable="true" sortProperty="item.description">
                            <c:out value="${contentCatalogItem.item.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.inventoryCondition" sortable="true" sortProperty="inventoryCondition.inventoryConditionName">
                            <c:url var="inventoryConditionUrl" value="/action/Inventory/InventoryCondition/Review">
                                <c:param name="InventoryConditionName" value="${contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                            </c:url>
                            <a href="${inventoryConditionUrl}"><c:out value="${contentCatalogItem.inventoryCondition.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.unitOfMeasureType">
                            <c:url var="unitOfMeasureTypeUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                <c:param name="UnitOfMeasureKindName" value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${unitOfMeasureTypeUrl}"><c:out value="${contentCatalogItem.unitOfMeasureType.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.currency" sortable="true" sortProperty="currency.currencyIsoName">
                            <c:url var="currencyUrl" value="/action/Accounting/Currency/Review">
                                <c:param name="CurrencyIsoName" value="${contentCatalogItem.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${currencyUrl}"><c:out value="${contentCatalogItem.currency.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.price" class="amount" sortable="true" sortProperty="unformattedUnitPrice">
                            <c:choose>
                                <c:when test="${contentCatalogItem.item.itemPriceType.itemPriceTypeName == 'FIXED'}">
                                    <c:out value="${contentCatalogItem.unitPrice}" />
                                </c:when>
                                <c:when test="${contentCatalogItem.item.itemPriceType.itemPriceTypeName == 'VARIABLE'}">
                                    Minimum: <c:out value="${contentCatalogItem.minimumUnitPrice}" /><br />
                                    Maximum: <c:out value="${contentCatalogItem.maximumUnitPrice}" /><br />
                                    Increment: <c:out value="${contentCatalogItem.unitPriceIncrement}" />
                                </c:when>
                            </c:choose>
                        </display:column>
                    </display:table>
                    <c:if test="${contentCatalogItems.size > 20}">
                        <c:url var="resultsUrl" value="/action/Content/ContentCatalogItem/Main">
                            <c:param name="ContentCollectionName" value="${contentCatalog.contentCollection.contentCollectionName}" />
                            <c:param name="ContentCatalogName" value="${contentCatalog.contentCatalogName}" />
                        </c:url>
                        <a href="${resultsUrl}">Paged Results</a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <display:table name="contentCatalogItems.list" id="contentCatalogItem" class="displaytag" partialList="true" pagesize="20" size="contentCatalogItemCount" requestURI="/action/Content/ContentCatalogItem/Main">
                        <display:column titleKey="columnTitle.item">
                            <c:url var="itemUrl" value="/action/Item/Item/Review">
                                <c:param name="ItemName" value="${contentCatalogItem.item.itemName}" />
                            </c:url>
                            <a href="${itemUrl}"><c:out value="${contentCatalogItem.item.itemName}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.description">
                            <c:out value="${contentCatalogItem.item.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.inventoryCondition">
                            <c:url var="inventoryConditionUrl" value="/action/Inventory/InventoryCondition/Review">
                                <c:param name="InventoryConditionName" value="${contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                            </c:url>
                            <a href="${inventoryConditionUrl}"><c:out value="${contentCatalogItem.inventoryCondition.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.unitOfMeasureType">
                            <c:url var="unitOfMeasureTypeUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                <c:param name="UnitOfMeasureKindName" value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${unitOfMeasureTypeUrl}"><c:out value="${contentCatalogItem.unitOfMeasureType.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.currency">
                            <c:url var="currencyUrl" value="/action/Accounting/Currency/Review">
                                <c:param name="CurrencyIsoName" value="${contentCatalogItem.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${currencyUrl}"><c:out value="${contentCatalogItem.currency.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.price" class="amount">
                            <c:choose>
                                <c:when test="${contentCatalogItem.item.itemPriceType.itemPriceTypeName == 'FIXED'}">
                                    <c:out value="${contentCatalogItem.unitPrice}" />
                                </c:when>
                                <c:when test="${contentCatalogItem.item.itemPriceType.itemPriceTypeName == 'VARIABLE'}">
                                    Minimum: <c:out value="${contentCatalogItem.minimumUnitPrice}" /><br />
                                    Maximum: <c:out value="${contentCatalogItem.maximumUnitPrice}" /><br />
                                    Increment: <c:out value="${contentCatalogItem.unitPriceIncrement}" />
                                </c:when>
                            </c:choose>
                        </display:column>
                    </display:table>
                    <c:url var="resultsUrl" value="/action/Content/ContentCatalogItem/Main">
                        <c:param name="ContentCollectionName" value="${contentCatalog.contentCollection.contentCollectionName}" />
                        <c:param name="ContentCatalogName" value="${contentCatalog.contentCatalogName}" />
                        <c:param name="Results" value="Complete" />
                    </c:url>
                    <a href="${resultsUrl}">All Results</a>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
