<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2023 Echo Three, LLC                                              -->
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
        <title>Review (<c:out value="${contentCatalogItem.contentCatalog.contentCollection.contentCollectionName}" />,
            <c:out value="${contentCatalogItem.contentCatalog.contentCatalogName}" />,
            <c:out value="${contentCatalogItem.item.itemName}" />,
            <c:out value="${contentCatalogItem.inventoryCondition.inventoryConditionName}" />,
            <c:out value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />,
            <c:out value="${contentCatalogItem.currency.currencyIsoName}" />)</title>
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
                    <c:param name="ContentCollectionName" value="${contentCatalogItem.contentCatalog.contentCollection.contentCollectionName}" />
                </c:url>
                <a href="${contentCatalogsUrl}">Catalogs</a> &gt;&gt;
                <c:url var="contentCatalogItemsUrl" value="/action/Content/ContentCatalogItem/Main">
                    <c:param name="ContentCollectionName" value="${contentCatalogItem.contentCatalog.contentCollection.contentCollectionName}" />
                    <c:param name="ContentCatalogName" value="${contentCatalogItem.contentCatalog.contentCatalogName}" />
                </c:url>
                <a href="${contentCatalogItemsUrl}">Items</a> &gt;&gt;
                Review (<c:out value="${contentCatalogItem.contentCatalog.contentCollection.contentCollectionName}" />,
                <c:out value="${contentCatalogItem.contentCatalog.contentCatalogName}" />,
                <c:out value="${contentCatalogItem.item.itemName}" />,
                <c:out value="${contentCatalogItem.inventoryCondition.inventoryConditionName}" />,
                <c:out value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />,
                <c:out value="${contentCatalogItem.currency.currencyIsoName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="ContentCollection.Review:ContentCatalog.Review:Item.Review:InventoryCondition.Review:UnitOfMeasureKind.Review:UnitOfMeasureType.Review:Currency.Review" />
            <et:hasSecurityRole securityRole="ContentCollection.Review" var="includeContentCollectionUrl" />
            <et:hasSecurityRole securityRole="ContentCatalog.Review" var="includeContentCatalogUrl" />
            <et:hasSecurityRole securityRole="Item.Review" var="includeItemUrl" />
            <et:hasSecurityRole securityRole="InventoryCondition.Review" var="includeInventoryConditionUrl" />
            <et:hasSecurityRole securityRole="UnitOfMeasureKind.Review" var="includeUnitOfMeasureKindUrl" />
            <et:hasSecurityRole securityRole="UnitOfMeasureType.Review" var="includeUnitOfMeasureTypeUrl" />
            <et:hasSecurityRole securityRole="Currency.Review" var="includeCurrencyUrl" />
            <p><font size="+2"><b><c:out value="${contentCatalogItem.contentCatalog.contentCollection.contentCollectionName}" />,
                <c:out value="${contentCatalogItem.contentCatalog.contentCatalogName}" />,
                <c:out value="${contentCatalogItem.item.itemName}" />,
                <c:out value="${contentCatalogItem.inventoryCondition.inventoryConditionName}" />,
                <c:out value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />,
                <c:out value="${contentCatalogItem.currency.currencyIsoName}" /></b></font></p>
            <br />
            Content Collection:
            <c:choose>
                <c:when test="${includeContentCollectionUrl}">
                    <c:url var="reviewUrl" value="/action/Content/ContentCollection/Review">
                        <c:param name="ContentCollectionName" value="${contentCatalogItem.contentCatalog.contentCollection.contentCollectionName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${contentCatalogItem.contentCatalog.contentCollection.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${contentCatalogItem.contentCatalog.contentCollection.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Content Catalog:
            <c:choose>
                <c:when test="${includeContentCatalogUrl}">
                    <c:url var="reviewUrl" value="/action/Content/ContentCatalog/Review">
                        <c:param name="ContentCollectionName" value="${contentCatalogItem.contentCatalog.contentCollection.contentCollectionName}" />
                        <c:param name="ContentCatalogName" value="${contentCatalogItem.contentCatalog.contentCatalogName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${contentCatalogItem.contentCatalog.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${contentCatalogItem.contentCatalog.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Item:
            <c:choose>
                <c:when test="${includeItemUrl}">
                    <c:url var="reviewUrl" value="/action/Item/Item/Review">
                        <c:param name="ItemName" value="${contentCatalogItem.item.itemName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${contentCatalogItem.item.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${contentCatalogItem.item.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Inventory Condition:
            <c:choose>
                <c:when test="${includeInventoryConditionUrl}">
                    <c:url var="reviewUrl" value="/action/Inventory/InventoryCondition/Review">
                        <c:param name="ItemName" value="${contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${contentCatalogItem.inventoryCondition.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${contentCatalogItem.inventoryCondition.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Unit of Measure Kind:
            <c:choose>
                <c:when test="${includeUnitOfMeasureKindUrl}">
                    <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureKind/Review">
                        <c:param name="UnitOfMeasureKindName" value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureKind.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureKind.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Unit of Measure Type:
            <c:choose>
                <c:when test="${includeUnitOfMeasureTypeUrl}">
                    <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                        <c:param name="UnitOfMeasureKindName" value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${contentCatalogItem.unitOfMeasureType.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${contentCatalogItem.unitOfMeasureType.description}" />
                </c:otherwise>
            </c:choose>
            <br />

            <br />
            <br />
            <br />

            <c:set var="tagScopes" scope="request" value="${contentCatalogItem.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${contentCatalogItem.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${contentCatalogItem.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Content/ContentCatalogItem/Review">
                <c:param name="ContentCollectionName" value="${contentCatalogItem.contentCatalog.contentCollection.contentCollectionName}" />
                <c:param name="ContentCatalogName" value="${contentCatalogItem.contentCatalog.contentCatalogName}" />
                <c:param name="ItemName" value="${contentCatalogItem.item.itemName}" />
                <c:param name="InventoryConditionName" value="${contentCatalogItem.inventoryCondition.inventoryConditionName}" />
                <c:param name="UnitOfMeasureTypeName" value="${contentCatalogItem.unitOfMeasureType.unitOfMeasureTypeName}" />
                <c:param name="CurrencyIsoName" value="${contentCatalogItem.currency.currencyIsoName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
