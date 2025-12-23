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
        <title>Review (<c:out value="${contentCategoryItem.contentCatalogItem.item.itemName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />">Collections</a> &gt;&gt;
                <c:url var="contentCatalogsUrl" value="/action/Content/ContentCatalog/Main">
                    <c:param name="ContentCollectionName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                </c:url>
                <a href="${contentCatalogsUrl}">Catalogs</a> &gt;&gt;
                <c:url var="contentCategoriesUrl" value="/action/Content/ContentCategory/Main">
                    <c:param name="ContentCollectionName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                    <c:param name="ContentCatalogName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCatalogName}" />
                    <c:param name="ParentContentCategoryName" value="${contentCategoryItem.contentCategory.parentContentCategory.contentCategoryName}" />
                </c:url>
                <a href="${contentCategoriesUrl}">Categories</a> &gt;&gt;
                <c:url var="contentCategoryItemsUrl" value="/action/Content/ContentCategoryItem/Main">
                    <c:param name="ContentCollectionName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                    <c:param name="ContentCatalogName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCatalogName}" />
                    <c:param name="ContentCategoryName" value="${contentCategoryItem.contentCategory.contentCategoryName}" />
                </c:url>
                <a href="${contentCategoryItemsUrl}">Items</a> &gt;&gt;
                Review (<c:out value="${contentCategoryItem.contentCatalogItem.item.itemName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${contentCategoryItem.contentCatalogItem.item.description}" /></b></font></p>
            <br />
            <c:url var="itemUrl" value="/action/Item/Item/Review">
                <c:param name="ItemName" value="${contentCategoryItem.contentCatalogItem.item.itemName}" />
            </c:url>
            Item: <a href="${itemUrl}"><c:out value="${contentCategoryItem.contentCatalogItem.item.description}" /></a><br />
            <c:url var="inventoryConditionUrl" value="/action/Inventory/InventoryCondition/Review">
                <c:param name="InventoryConditionName" value="${contentCategoryItem.contentCatalogItem.inventoryCondition.inventoryConditionName}" />
            </c:url>
            Inventory Condition: <a href="${inventoryConditionUrl}"><c:out value="${contentCategoryItem.contentCatalogItem.inventoryCondition.description}" /></a><br />
            <c:url var="unitOfMeasureTypeUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                <c:param name="UnitOfMeasureKindName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                <c:param name="UnitOfMeasureTypeName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
            </c:url>
            Unit of Measure Type: <a href="${unitOfMeasureTypeUrl}"><c:out value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.description}" /></a><br />
            <c:url var="currencyUrl" value="/action/Accounting/Currency/Review">
                <c:param name="CurrencyIsoName" value="${contentCategoryItem.contentCatalogItem.currency.currencyIsoName}" />
            </c:url>
            Currency: <a href="${currencyUrl}"><c:out value="${contentCategoryItem.contentCatalogItem.currency.description}" /></a><br />
            <br />
            <br />
            <br />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
