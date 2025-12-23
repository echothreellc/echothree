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
        <title>Items (<c:out value="${vendor.vendorName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Main" />">Purchasing</a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Vendor/Main" />">Vendors</a> &gt;&gt;
                <et:countVendorResults searchTypeName="VENDOR_REVIEW" countVar="vendorResultsCount" commandResultVar="countVendorResultsCommandResult" logErrors="false" />
                <c:if test="${vendorResultsCount > 0}">
                    <a href="<c:url value="/action/Purchasing/Vendor/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="vendorUrl" value="/action/Purchasing/Vendor/Review">
                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                </c:url>
                <a href="${vendorUrl}">Review (<c:out value="${vendorItem.vendor.vendorName}" />)</a> &gt;&gt;
                <c:url var="vendorItemsUrl" value="/action/Purchasing/VendorItem/Main">
                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                </c:url>
                <a href="${vendorItemsUrl}">Items</a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Purchasing/VendorItem/Review">
                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                    <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${vendorItem.vendorItemName}" />)</a> &gt;&gt;
                Costs
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Purchasing/VendorItemCost/Add">
                <c:param name="VendorName" value="${vendor.vendorName}" />
                <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
            </c:url>
            <p><a href="${addUrl}">Add Cost.</a></p>
            <display:table name="vendorItemCosts" id="vendorItemCost" class="displaytag" sort="list" requestURI="/action/Purchasing/VendorItemCost/Main">
                <display:column titleKey="columnTitle.inventoryCondition" sortable="true" sortProperty="inventoryCondition.description">
                    <c:out value="${vendorItemCost.inventoryCondition.description}" />
                </display:column>
                <display:column titleKey="columnTitle.unitOfMeasureType" sortable="true" sortProperty="unitOfMeasureType.description">
                    <c:out value="${vendorItemCost.unitOfMeasureType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.unitCost" class="amount" sortable="true" sortProperty="unitCost">
                    <c:out value="${vendorItemCost.unitCost}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Purchasing/VendorItemCost/Edit">
                        <c:param name="VendorName" value="${vendor.vendorName}" />
                        <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                        <c:param name="InventoryConditionName" value="${vendorItemCost.inventoryCondition.inventoryConditionName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${vendorItemCost.unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Purchasing/VendorItemCost/Delete">
                        <c:param name="VendorName" value="${vendor.vendorName}" />
                        <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                        <c:param name="InventoryConditionName" value="${vendorItemCost.inventoryCondition.inventoryConditionName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${vendorItemCost.unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
