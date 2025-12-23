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
        <title>Items</title>
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
                    <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                </c:url>
                <a href="${contentCatalogsUrl}">Catalogs</a> &gt;&gt;
                <c:url var="contentCategoriesUrl" value="/action/Content/ContentCategory/Main">
                    <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                    <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                    <c:param name="ParentContentCategoryName" value="${contentCategory.parentContentCategory.contentCategoryName}" />
                </c:url>
                <a href="${contentCategoriesUrl}">Categories</a> &gt;&gt;
                Items
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Content/ContentCategoryItem/Add/Step1">
                <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
            </c:url>
            <p><a href="${addUrl}">Add Item.</a></p>
            <c:choose>
                <c:when test="${contentCategoryItemCount == null || contentCategoryItemCount < 21}">
                    <display:table name="contentCategoryItems.list" id="contentCategoryItem" class="displaytag" sort="list" requestURI="/action/Content/ContentCategoryItem/Main">
                        <display:column>
                            <c:url var="reviewUrl" value="/action/Content/ContentCategoryItem/Review">
                                <c:param name="ContentCollectionName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ContentCategoryName" value="${contentCategoryItem.contentCategory.contentCategoryName}" />
                                <c:param name="ItemName" value="${contentCategoryItem.contentCatalogItem.item.itemName}" />
                                <c:param name="InventoryConditionName" value="${contentCategoryItem.contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="CurrencyIsoName" value="${contentCategoryItem.contentCatalogItem.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${reviewUrl}">Review</a>
                        </display:column>
                        <display:column titleKey="columnTitle.item" sortable="true" sortProperty="contentCatalogItem.item.itemName">
                            <c:url var="itemUrl" value="/action/Item/Item/Review">
                                <c:param name="ItemName" value="${contentCategoryItem.contentCatalogItem.item.itemName}" />
                            </c:url>
                            <a href="${itemUrl}"><c:out value="${contentCategoryItem.contentCatalogItem.item.itemName}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.description" sortable="true" sortProperty="contentCatalogItem.item.description">
                            <c:out value="${contentCategoryItem.contentCatalogItem.item.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.inventoryCondition" sortable="true" sortProperty="contentCatalogItem.inventoryCondition.inventoryConditionName">
                            <c:url var="inventoryConditionUrl" value="/action/Inventory/InventoryCondition/Review">
                                <c:param name="InventoryConditionName" value="${contentCategoryItem.contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                            </c:url>
                            <a href="${inventoryConditionUrl}"><c:out value="${contentCategoryItem.contentCatalogItem.inventoryCondition.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.unitOfMeasureType">
                            <c:url var="unitOfMeasureTypeUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                <c:param name="UnitOfMeasureKindName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${unitOfMeasureTypeUrl}"><c:out value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.currency" sortable="true" sortProperty="contentCatalogItem.currency.currencyIsoName">
                            <c:url var="currencyUrl" value="/action/Accounting/Currency/Review">
                                <c:param name="CurrencyIsoName" value="${contentCategoryItem.contentCatalogItem.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${currencyUrl}"><c:out value="${contentCategoryItem.contentCatalogItem.currency.description}" /></a>
                        </display:column>
                        <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                        <display:column titleKey="columnTitle.default">
                            <c:choose>
                                <c:when test="${contentCategoryItem.isDefault}">
                                    Default
                                </c:when>
                                <c:otherwise>
                                    <c:url var="setDefaultContentCategoryItemUrl" value="/action/Content/ContentCategoryItem/SetDefault">
                                        <c:param name="ContentCollectionName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                        <c:param name="ContentCatalogName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCatalogName}" />
                                        <c:param name="ContentCategoryName" value="${contentCategoryItem.contentCategory.contentCategoryName}" />
                                        <c:param name="ItemName" value="${contentCategoryItem.contentCatalogItem.item.itemName}" />
                                        <c:param name="InventoryConditionName" value="${contentCategoryItem.contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                                        <c:param name="UnitOfMeasureTypeName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                                        <c:param name="CurrencyIsoName" value="${contentCategoryItem.contentCatalogItem.currency.currencyIsoName}" />
                                    </c:url>
                                    <a href="${setDefaultContentCategoryItemUrl}">Set Default</a>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column>
                            <c:url var="editContentCategoryItemUrl" value="/action/Content/ContentCategoryItem/Edit">
                                <c:param name="ContentCollectionName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ContentCategoryName" value="${contentCategoryItem.contentCategory.contentCategoryName}" />
                                <c:param name="ItemName" value="${contentCategoryItem.contentCatalogItem.item.itemName}" />
                                <c:param name="InventoryConditionName" value="${contentCategoryItem.contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="CurrencyIsoName" value="${contentCategoryItem.contentCatalogItem.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${editContentCategoryItemUrl}">Edit</a>
                            <c:url var="deleteContentCategoryItemUrl" value="/action/Content/ContentCategoryItem/Delete">
                                <c:param name="ContentCollectionName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ContentCategoryName" value="${contentCategoryItem.contentCategory.contentCategoryName}" />
                                <c:param name="ItemName" value="${contentCategoryItem.contentCatalogItem.item.itemName}" />
                                <c:param name="InventoryConditionName" value="${contentCategoryItem.contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="CurrencyIsoName" value="${contentCategoryItem.contentCatalogItem.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${deleteContentCategoryItemUrl}">Delete</a>
                        </display:column>
                    </display:table>
                    <c:if test="${contentCategoryItems.size > 20}">
                        <c:url var="resultsUrl" value="/action/Content/ContentCategoryItem/Main">
                            <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                            <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                            <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
                        </c:url>
                        <a href="${resultsUrl}">Paged Results</a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <display:table name="contentCategoryItems.list" id="contentCategoryItem" class="displaytag" partialList="true" pagesize="20" size="contentCategoryItemCount" requestURI="/action/Content/ContentCategoryItem/Main">
                        <display:column>
                            <c:url var="reviewUrl" value="/action/Content/ContentCategoryItem/Review">
                                <c:param name="ContentCollectionName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ContentCategoryName" value="${contentCategoryItem.contentCategory.contentCategoryName}" />
                                <c:param name="ItemName" value="${contentCategoryItem.contentCatalogItem.item.itemName}" />
                                <c:param name="InventoryConditionName" value="${contentCategoryItem.contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="CurrencyIsoName" value="${contentCategoryItem.contentCatalogItem.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${reviewUrl}">Review</a>
                        </display:column>
                        <display:column titleKey="columnTitle.item">
                            <c:url var="itemUrl" value="/action/Item/Item/Review">
                                <c:param name="ItemName" value="${contentCategoryItem.contentCatalogItem.item.itemName}" />
                            </c:url>
                            <a href="${itemUrl}"><c:out value="${contentCategoryItem.contentCatalogItem.item.itemName}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.description">
                            <c:out value="${contentCategoryItem.contentCatalogItem.item.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.inventoryCondition">
                            <c:url var="inventoryConditionUrl" value="/action/Inventory/InventoryCondition/Review">
                                <c:param name="InventoryConditionName" value="${contentCategoryItem.contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                            </c:url>
                            <a href="${inventoryConditionUrl}"><c:out value="${contentCategoryItem.contentCatalogItem.inventoryCondition.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.unitOfMeasureType">
                            <c:url var="unitOfMeasureTypeUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                <c:param name="UnitOfMeasureKindName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${unitOfMeasureTypeUrl}"><c:out value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.currency">
                            <c:url var="currencyUrl" value="/action/Accounting/Currency/Review">
                                <c:param name="CurrencyIsoName" value="${contentCategoryItem.contentCatalogItem.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${currencyUrl}"><c:out value="${contentCategoryItem.contentCatalogItem.currency.description}" /></a>
                        </display:column>
                        <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                        <display:column titleKey="columnTitle.default">
                            <c:choose>
                                <c:when test="${contentCategoryItem.isDefault}">
                                    Default
                                </c:when>
                                <c:otherwise>
                                    <c:url var="setDefaultContentCategoryItemUrl" value="/action/Content/ContentCategoryItem/SetDefault">
                                        <c:param name="ContentCollectionName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                        <c:param name="ContentCatalogName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCatalogName}" />
                                        <c:param name="ContentCategoryName" value="${contentCategoryItem.contentCategory.contentCategoryName}" />
                                        <c:param name="ItemName" value="${contentCategoryItem.contentCatalogItem.item.itemName}" />
                                        <c:param name="InventoryConditionName" value="${contentCategoryItem.contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                                        <c:param name="UnitOfMeasureTypeName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                                        <c:param name="CurrencyIsoName" value="${contentCategoryItem.contentCatalogItem.currency.currencyIsoName}" />
                                    </c:url>
                                    <a href="${setDefaultContentCategoryItemUrl}">Set Default</a>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column>
                            <c:url var="editContentCategoryItemUrl" value="/action/Content/ContentCategoryItem/Edit">
                                <c:param name="ContentCollectionName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ContentCategoryName" value="${contentCategoryItem.contentCategory.contentCategoryName}" />
                                <c:param name="ItemName" value="${contentCategoryItem.contentCatalogItem.item.itemName}" />
                                <c:param name="InventoryConditionName" value="${contentCategoryItem.contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="CurrencyIsoName" value="${contentCategoryItem.contentCatalogItem.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${editContentCategoryItemUrl}">Edit</a>
                            <c:url var="deleteContentCategoryItemUrl" value="/action/Content/ContentCategoryItem/Delete">
                                <c:param name="ContentCollectionName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategoryItem.contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ContentCategoryName" value="${contentCategoryItem.contentCategory.contentCategoryName}" />
                                <c:param name="ItemName" value="${contentCategoryItem.contentCatalogItem.item.itemName}" />
                                <c:param name="InventoryConditionName" value="${contentCategoryItem.contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${contentCategoryItem.contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="CurrencyIsoName" value="${contentCategoryItem.contentCatalogItem.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${deleteContentCategoryItemUrl}">Delete</a>
                        </display:column>
                    </display:table>
                    <c:url var="resultsUrl" value="/action/Content/ContentCategoryItem/Main">
                        <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                        <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                        <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
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
