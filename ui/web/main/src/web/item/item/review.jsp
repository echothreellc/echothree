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
        <title>Review (<c:out value="${item.itemName}" />)</title>
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
                Review (<c:out value="${item.itemName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Item.Edit:ItemStatus.Choices:Company.Review:ItemCategory.Review:ItemAccountingCategory.Review:ItemPurchasingCategory.Review:UnitOfMeasureKind.Review:CancellationPolicy.Review:ReturnPolicy.Review:ItemDescription.Create:ItemImageType.Review:ItemUnitOfMeasureType.List:ItemUnitOfMeasureType.Create:ItemUnitOfMeasureType.Edit:ItemUnitOfMeasureType.Delete:ItemPrice.List:ItemPrice.Create:ItemPrice.Edit:ItemPrice.Delete:ItemPrice.History:ItemUnitPriceLimit.List:ItemUnitPriceLimit.Create:ItemUnitPriceLimit.Edit:ItemUnitPriceLimit.Delete:ItemKitMember.List:ItemUnitLimit.List:ItemUnitLimit.Create:ItemUnitLimit.Edit:ItemUnitLimit.Delete:ItemUnitCustomerTypeLimit.List:ItemUnitCustomerTypeLimit.Create:ItemUnitCustomerTypeLimit.Edit:ItemUnitCustomerTypeLimit.Delete:ItemDescription.List:ItemAlias.List:ItemAlias.Create:ItemAlias.Edit:ItemAlias.Delete:ItemShippingTime.List:ItemPackCheckRequirement.List:ItemPackCheckRequirement.Create:ItemPackCheckRequirement.Edit:ItemPackCheckRequirement.Delete:ItemWeight.List:ItemWeightType.Review:ItemVolume.List:ItemVolumeType.Review:ItemCountryOfOrigin.List:ItemHarmonizedTariffScheduleCode.List:ItemHarmonizedTariffScheduleCode.Create:ItemHarmonizedTariffScheduleCode.Review:ItemHarmonizedTariffScheduleCode.Edit:ItemHarmonizedTariffScheduleCode.Delete:Country.Review:HarmonizedTariffScheduleCodeUseType.Review:HarmonizedTariffScheduleCode.Review:OfferItem.List:Vendor.Review:VendorItem.Review:VendorItem.List:VendorItemStatus.Choices:Offer.Review:OfferItemPrice.List:OfferItem.Delete:UnitOfMeasureType.Review:Currency.Review:InventoryCondition.Review:CustomerType.Review:Event.List" />
            <et:hasSecurityRole securityRole="Company.Review" var="includeCompanyUrl" />
            <et:hasSecurityRole securityRole="ItemCategory.Review" var="includeItemCategoryUrl" />
            <et:hasSecurityRole securityRole="ItemAccountingCategory.Review" var="includeItemAccountingCategoryUrl" />
            <et:hasSecurityRole securityRole="ItemPurchasingCategory.Review" var="includeItemPurchasingCategoryUrl" />
            <et:hasSecurityRole securityRole="UnitOfMeasureKind.Review" var="includeUnitOfMeasureKindUrl" />
            <et:hasSecurityRole securityRole="UnitOfMeasureType.Review" var="includeUnitOfMeasureTypeUrl" />
            <et:hasSecurityRole securityRole="ItemWeightType.Review" var="includeItemWeightTypeReviewUrl" />
            <et:hasSecurityRole securityRole="ItemVolumeType.Review" var="includeItemVolumeTypeReviewUrl" />
            <et:hasSecurityRole securityRole="Currency.Review" var="includeCurrencyUrl" />
            <et:hasSecurityRole securityRole="InventoryCondition.Review" var="includeInventoryConditionUrl" />
            <et:hasSecurityRole securityRole="CustomerType.Review" var="includeCustomerTypeUrl" />
            <et:hasSecurityRole securityRole="CancellationPolicy.Review" var="includeCancellationPolicyUrl" />
            <et:hasSecurityRole securityRole="ReturnPolicy.Review" var="includeReturnPolicyUrl" />
            <et:hasSecurityRole securityRole="ItemHarmonizedTariffScheduleCode.Review" var="includeItemHarmonizedTariffScheduleCodeUrl" />
            <et:hasSecurityRole securityRole="Country.Review" var="includeCountryUrl" />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUseType.Review" var="includeHarmonizedTariffScheduleCodeUseTypeUrl" />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.Review" var="includeHarmonizedTariffScheduleCodeUrl" />
            <et:hasSecurityRole securityRole="Vendor.Review" var="includeVendorUrl" />
            <et:hasSecurityRole securityRole="VendorItemStatus.Choices" var="includeEditableVendorItemStatus" />
            <et:hasSecurityRole securityRoles="ItemHarmonizedTariffScheduleCode.Edit:ItemHarmonizedTariffScheduleCode.Delete">
                <c:set var="linksInItemHarmonizedTariffScheduleCodeFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="ItemAlias.Edit:ItemAlias.Delete">
                <c:set var="linksInItemAliasFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="ItemPackCheckRequirement.Edit:ItemPackCheckRequirement.Delete">
                <c:set var="linksInItemPackCheckRequirementFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Offer.Review" var="includeOfferUrl" />
            <et:hasSecurityRole securityRole="OfferItemPrice.List">
                <c:set var="linksInOfferItemFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="OfferItem.Delete">
                <c:set var="linksInOfferItemSecondRow" value="true" />
            </et:hasSecurityRole>
            <c:url var="returnUrl" scope="request" value="/../action/Item/Item/Review">
                <c:param name="ItemName" value="${item.itemName}" />
            </c:url>
            <p><font size="+2"><b><et:appearance appearance="${item.entityInstance.entityAppearance.appearance}"><c:out value="${item.description}" /></et:appearance></b></font></p>
            <p>
                <font size="+1"><et:appearance appearance="${item.entityInstance.entityAppearance.appearance}">${item.itemName}</et:appearance></font>
                <et:hasSecurityRole securityRole="Item.Edit">
                    <c:url var="editUrl" value="/action/Item/Item/Edit">
                        <c:param name="OriginalItemName" value="${item.itemName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                </et:hasSecurityRole>
            </p>
            <br />
            Item Type: <c:out value="${item.itemType.description}" /><br />
            Item Use Type: <c:out value="${item.itemUseType.description}" /><br />
            Item Status: <et:appearance appearance="${item.itemStatus.workflowStep.entityInstance.entityAppearance.appearance}"><c:out value="${item.itemStatus.workflowStep.description}" /></et:appearance>
            <et:hasSecurityRole securityRole="ItemStatus.Choices">
                <c:url var="statusUrl" value="/action/Item/Item/Status">
                    <c:param name="ForwardKey" value="Review" />
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <a href="${statusUrl}">Edit</a>
            </et:hasSecurityRole>
            <br />
            <br />
            Company:
            <c:choose>
                <c:when test="${includeCompanyUrl}">
                    <c:url var="reviewUrl" value="/action/Accounting/Company/Review">
                        <c:param name="CompanyName" value="${item.company.companyName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${item.company.partyGroup.name}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${item.company.partyGroup.name}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Item Category:
            <c:choose>
                <c:when test="${includeItemCategoryUrl}">
                    <c:url var="reviewUrl" value="/action/Item/ItemCategory/Review">
                        <c:param name="ItemCategoryName" value="${item.itemCategory.itemCategoryName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${item.itemCategory.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${item.itemCategory.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <c:if test='${item.itemAccountingCategory != null}'>
                Item Accounting Category:
                <c:choose>
                    <c:when test="${includeItemAccountingCategoryUrl}">
                        <c:url var="reviewUrl" value="/action/Accounting/ItemAccountingCategory/Review">
                            <c:param name="ItemAccountingCategoryName" value="${item.itemAccountingCategory.itemAccountingCategoryName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${item.itemAccountingCategory.description}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${item.itemAccountingCategory.description}" />
                    </c:otherwise>
                </c:choose>
                <br />
            </c:if>
            <c:if test='${item.itemPurchasingCategory != null}'>
                Item Purchasing Category:
                <c:choose>
                    <c:when test="${includeItemPurchasingCategoryUrl}">
                        <c:url var="reviewUrl" value="/action/Purchasing/ItemPurchasingCategory/Review">
                            <c:param name="ItemPurchasingCategoryName" value="${item.itemPurchasingCategory.itemPurchasingCategoryName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${item.itemPurchasingCategory.description}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${item.itemPurchasingCategory.description}" />
                    </c:otherwise>
                </c:choose>
                <br />
            </c:if>
            <br />
            <c:if test='${item.itemDeliveryType != null}'>
                Item Delivery Type: <c:out value="${item.itemDeliveryType.description}" /><br />
            </c:if>
            <c:if test='${item.itemInventoryType != null}'>
                Item Inventory Type: <c:out value="${item.itemInventoryType.description}" /><br />
            </c:if>
            <c:if test='${item.itemDeliveryType != null || item.itemInventoryType != null}'>
                <br />
            </c:if>
            Unit of Measure Kind:
            <c:choose>
                <c:when test="${includeUnitOfMeasureKindUrl}">
                    <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureKind/Review">
                        <c:param name="UnitOfMeasureKindName" value="${item.unitOfMeasureKind.unitOfMeasureKindName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${item.unitOfMeasureKind.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${item.unitOfMeasureKind.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Shipping Charge Exempt:
            <c:choose>
                <c:when test="${item.shippingChargeExempt}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Sales Order Start Time: <c:out value="${item.salesOrderStartTime}" /><br />
            Sales Order End Time:
            <c:choose>
                <c:when test="${item.salesOrderEndTime != null}">
                    <c:out value="${item.salesOrderEndTime}" />
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            Purchase Order Start Time:
            <c:choose>
                <c:when test="${item.purchaseOrderStartTime != null}">
                    <c:out value="${item.purchaseOrderStartTime}" />
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            Purchase Order End Time:
            <c:choose>
                <c:when test="${item.purchaseOrderEndTime != null}">
                    <c:out value="${item.purchaseOrderEndTime}" />
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            Shipping Start Time:
            <c:choose>
                <c:when test="${item.shippingStartTime != null}">
                    <c:out value="${item.shippingStartTime}" />
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            Shipping End Time:
            <c:choose>
                <c:when test="${item.shippingEndTime != null}">
                    <c:out value="${item.shippingEndTime}" />
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Allow Club Discounts:
            <c:choose>
                <c:when test="${item.allowClubDiscounts}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            Allow Coupon Discounts:
            <c:choose>
                <c:when test="${item.allowCouponDiscounts}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            Allow Associate Payments:
            <c:choose>
                <c:when test="${item.allowAssociatePayments}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Cancellation Policy:
            <c:choose>
                <c:when test="${item.cancellationPolicy != null}">
                    <c:choose>
                        <c:when test="${includeCancellationPolicyUrl}">
                            <c:url var="reviewUrl" value="/action/CancellationPolicy/CancellationPolicy/Review">
                                <c:param name="CancellationKindName" value="${item.cancellationPolicy.cancellationKind.cancellationKindName}" />
                                <c:param name="CancellationPolicyName" value="${item.cancellationPolicy.cancellationPolicyName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${item.cancellationPolicy.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${item.cancellationPolicy.description}" />
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            Return Policy:
            <c:choose>
                <c:when test="${item.returnPolicy != null}">
                    <c:choose>
                        <c:when test="${includeReturnPolicyUrl}">
                            <c:url var="reviewUrl" value="/action/ReturnPolicy/ReturnPolicy/Review">
                                <c:param name="ReturnKindName" value="${item.returnPolicy.returnKind.returnKindName}" />
                                <c:param name="ReturnPolicyName" value="${item.returnPolicy.returnPolicyName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${item.returnPolicy.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${item.returnPolicy.description}" />
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <et:hasSecurityRole securityRole="ItemUnitOfMeasureType.List">
                <h2>Item Unit Of Measure Types</h2>
                <et:hasSecurityRole securityRole="ItemUnitOfMeasureType.Create">
                    <c:url var="addUrl" value="/action/Item/ItemUnitOfMeasureType/Add">
                        <c:param name="ItemName" value="${item.itemName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Unit Of Measure Type.</a></p>
                </et:hasSecurityRole>
                <c:choose>
                    <c:when test="${item.itemUnitOfMeasureTypes.size == 0}">
                        No unit of measure types were found.<br />
                    </c:when>
                    <c:otherwise>
                        <display:table name="item.itemUnitOfMeasureTypes.list" id="itemUnitOfMeasureType" class="displaytag">
                            <display:column titleKey="columnTitle.unitOfMeasureType">
                                <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                    <c:param name="UnitOfMeasureKindName" value="${itemUnitOfMeasureType.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                    <c:param name="UnitOfMeasureTypeName" value="${itemUnitOfMeasureType.unitOfMeasureType.unitOfMeasureTypeName}" />
                                </c:url>
                                <a href="${reviewUrl}"><c:out value="${itemUnitOfMeasureType.unitOfMeasureType.description}" /></a>
                            </display:column>
                            <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                            <display:column titleKey="columnTitle.default">
                                <c:choose>
                                    <c:when test="${itemUnitOfMeasureType.isDefault}">
                                        Default
                                    </c:when>
                                    <c:otherwise>
                                        <et:hasSecurityRole securityRole="ItemUnitOfMeasureType.Edit">
                                            <c:url var="setDefaultUrl" value="/action/ReturnPolicy/ReturnTypeShippingMethod/SetDefault">
                                                <c:param name="ItemName" value="${itemUnitOfMeasureType.item.itemName}" />
                                                <c:param name="UnitOfMeasureTypeName" value="${itemUnitOfMeasureType.unitOfMeasureType.unitOfMeasureTypeName}" />
                                            </c:url>
                                            <a href="${setDefaultUrl}">Set Default</a>
                                        </et:hasSecurityRole>
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <et:hasSecurityRole securityRoles="ItemUnitOfMeasureType.Edit:ItemUnitOfMeasureType.Delete">
                                <display:column>
                                    <et:hasSecurityRole securityRole="ItemUnitOfMeasureType.Edit">
                                        <c:url var="editUrl" value="/action/Item/ItemUnitOfMeasureType/Edit">
                                            <c:param name="ItemName" value="${itemUnitOfMeasureType.item.itemName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemUnitOfMeasureType.unitOfMeasureType.unitOfMeasureTypeName}" />
                                        </c:url>
                                        <a href="${editUrl}">Edit</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="ItemUnitOfMeasureType.Delete">
                                        <c:url var="deleteUrl" value="/action/Item/ItemUnitOfMeasureType/Delete">
                                            <c:param name="ItemName" value="${itemUnitOfMeasureType.item.itemName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemUnitOfMeasureType.unitOfMeasureType.unitOfMeasureTypeName}" />
                                        </c:url>
                                        <a href="${deleteUrl}">Delete</a>
                                    </et:hasSecurityRole>
                                </display:column>
                            </et:hasSecurityRole>
                        </display:table>
                    </c:otherwise>
                </c:choose>
                <br />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemPrice.List">
                <h2>Prices</h2>
                <et:hasSecurityRole securityRole="ItemPrice.Create">
                    <c:url var="addUrl" value="/action/Item/ItemPrice/Add">
                        <c:param name="ItemName" value="${item.itemName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Price.</a></p>
                </et:hasSecurityRole>
                <c:choose>
                    <c:when test="${item.itemPrices.size == 0}">
                        No prices were found.<br />
                    </c:when>
                    <c:otherwise>
                        <display:table name="item.itemPrices.list" id="itemPrice" class="displaytag">
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
                                    <et:hasSecurityRole securityRole="ItemPrice.Edit">
                                        <c:url var="editUrl" value="/action/Item/ItemPrice/Edit">
                                            <c:param name="ItemName" value="${itemPrice.item.itemName}" />
                                            <c:param name="InventoryConditionName" value="${itemPrice.inventoryCondition.inventoryConditionName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemPrice.unitOfMeasureType.unitOfMeasureTypeName}" />
                                            <c:param name="CurrencyIsoName" value="${itemPrice.currency.currencyIsoName}" />
                                        </c:url>
                                        <a href="${editUrl}">Edit</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="ItemPrice.Delete">
                                        <c:url var="deleteUrl" value="/action/Item/ItemPrice/Delete">
                                            <c:param name="ItemName" value="${itemPrice.item.itemName}" />
                                            <c:param name="InventoryConditionName" value="${itemPrice.inventoryCondition.inventoryConditionName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemPrice.unitOfMeasureType.unitOfMeasureTypeName}" />
                                            <c:param name="CurrencyIsoName" value="${itemPrice.currency.currencyIsoName}" />
                                        </c:url>
                                        <a href="${deleteUrl}">Delete</a>
                                    </et:hasSecurityRole>
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
                    </c:otherwise>
                </c:choose>
                <br />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemUnitPriceLimit.List">
                <h2>Unit Price Limits</h2>
                <et:hasSecurityRole securityRole="ItemUnitPriceLimit.Create">
                    <c:url var="addUrl" value="/action/Item/ItemUnitPriceLimit/Add">
                        <c:param name="ItemName" value="${item.itemName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Unit Price Limit.</a></p>
                </et:hasSecurityRole>
                <c:choose>
                    <c:when test="${item.itemUnitPriceLimits.size == 0}">
                        No unit prices limits were found.<br />
                    </c:when>
                    <c:otherwise>
                        <display:table name="item.itemUnitPriceLimits.list" id="itemUnitPriceLimit" class="displaytag">
                            <display:column titleKey="columnTitle.inventoryCondition">
                                <c:choose>
                                    <c:when test="${includeInventoryConditionUrl}">
                                        <c:url var="reviewUrl" value="/action/Inventory/InventoryCondition/Review">
                                            <c:param name="InventoryConditionName" value="${itemUnitPriceLimit.inventoryCondition.inventoryConditionName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${itemUnitPriceLimit.inventoryCondition.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${itemUnitPriceLimit.inventoryCondition.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.unitOfMeasureType">
                                <c:choose>
                                    <c:when test="${includeUnitOfMeasureTypeUrl}">
                                        <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                            <c:param name="UnitOfMeasureKindName" value="${itemUnitPriceLimit.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemUnitPriceLimit.unitOfMeasureType.unitOfMeasureTypeName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${itemUnitPriceLimit.unitOfMeasureType.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${itemUnitPriceLimit.unitOfMeasureType.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.currency">
                                <c:choose>
                                    <c:when test="${includeCurrencyUrl}">
                                        <c:url var="reviewUrl" value="/action/Accounting/Currency/Review">
                                            <c:param name="CurrencyIsoName" value="${itemUnitPriceLimit.currency.currencyIsoName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${itemUnitPriceLimit.currency.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${itemUnitPriceLimit.currency.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.minimum" class="amount">
                                <c:out value="${itemUnitPriceLimit.minimumUnitPrice}" />
                            </display:column>
                            <display:column titleKey="columnTitle.maximum" class="amount">
                                <c:out value="${itemUnitPriceLimit.maximumUnitPrice}" />
                            </display:column>
                            <et:hasSecurityRole securityRoles="ItemUnitPriceLimit.Edit:ItemUnitPriceLimit.Delete">
                                <display:column>
                                    <et:hasSecurityRole securityRole="ItemUnitPriceLimit.Edit">
                                        <c:url var="editUrl" value="/action/Item/ItemUnitPriceLimit/Edit">
                                            <c:param name="ItemName" value="${itemUnitPriceLimit.item.itemName}" />
                                            <c:param name="InventoryConditionName" value="${itemUnitPriceLimit.inventoryCondition.inventoryConditionName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemUnitPriceLimit.unitOfMeasureType.unitOfMeasureTypeName}" />
                                            <c:param name="CurrencyIsoName" value="${itemUnitPriceLimit.currency.currencyIsoName}" />
                                        </c:url>
                                        <a href="${editUrl}">Edit</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="ItemUnitPriceLimit.Delete">
                                        <c:url var="deleteUrl" value="/action/Item/ItemUnitPriceLimit/Delete">
                                            <c:param name="ItemName" value="${itemUnitPriceLimit.item.itemName}" />
                                            <c:param name="InventoryConditionName" value="${itemUnitPriceLimit.inventoryCondition.inventoryConditionName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemUnitPriceLimit.unitOfMeasureType.unitOfMeasureTypeName}" />
                                            <c:param name="CurrencyIsoName" value="${itemUnitPriceLimit.currency.currencyIsoName}" />
                                        </c:url>
                                        <a href="${deleteUrl}">Delete</a>
                                    </et:hasSecurityRole>
                                </display:column>
                            </et:hasSecurityRole>
                        </display:table>
                    </c:otherwise>
                </c:choose>
                <br />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemKitMember.List">
                <c:if test='${item.itemKitMembers.size > 0}'>
                    <h2>Item Kit Members</h2>
                    <display:table name="item.itemKitMembers.list" id="itemKitMember" class="displaytag">
                        <display:column titleKey="columnTitle.inventoryCondition">
                            <c:url var="reviewUrl" value="/action/Inventory/InventoryCondition/Review">
                                <c:param name="InventoryConditionName" value="${itemKitMember.inventoryCondition.inventoryConditionName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemKitMember.inventoryCondition.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.unitOfMeasureType">
                            <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                <c:param name="UnitOfMeasureKindName" value="${itemKitMember.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${itemKitMember.unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemKitMember.unitOfMeasureType.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.memberItem">
                            <c:url var="reviewUrl" value="/action/Item/Item/Review">
                                <c:param name="ItemName" value="${itemKitMember.memberItem.itemName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemKitMember.memberItem.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.inventoryCondition">
                            <c:url var="reviewUrl" value="/action/Inventory/InventoryCondition/Review">
                                <c:param name="InventoryConditionName" value="${itemKitMember.memberInventoryCondition.inventoryConditionName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemKitMember.memberInventoryCondition.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.unitOfMeasureType">
                            <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                <c:param name="UnitOfMeasureKindName" value="${itemKitMember.memberUnitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${itemKitMember.memberUnitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemKitMember.memberUnitOfMeasureType.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.quantity" class="amount">
                            <c:out value="${itemKitMember.quantity}" />
                        </display:column>
                        <display:column>
                            <c:url var="editUrl" value="/action/Item/Item/ItemKitMemberEdit">
                                <c:param name="ItemName" value="${itemKitMember.item.itemName}" />
                                <c:param name="InventoryConditionName" value="${itemKitMember.inventoryCondition.inventoryConditionName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${itemKitMember.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="MemberItemName" value="${itemKitMember.memberItem.itemName}" />
                                <c:param name="MemberInventoryConditionName" value="${itemKitMember.memberInventoryCondition.inventoryConditionName}" />
                                <c:param name="MemberUnitOfMeasureTypeName" value="${itemKitMember.memberUnitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Item/Item/ItemKitMemberDelete">
                                <c:param name="ItemName" value="${itemKitMember.item.itemName}" />
                                <c:param name="InventoryConditionName" value="${itemKitMember.inventoryCondition.inventoryConditionName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${itemKitMember.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="MemberItemName" value="${itemKitMember.memberItem.itemName}" />
                                <c:param name="MemberInventoryConditionName" value="${itemKitMember.memberInventoryCondition.inventoryConditionName}" />
                                <c:param name="MemberUnitOfMeasureTypeName" value="${itemKitMember.memberUnitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                    </display:table>
                    <br />
                </c:if>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemUnitLimit.List">
                <h2>Unit Limits</h2>
                <et:hasSecurityRole securityRole="ItemUnitLimit.Create">
                    <c:url var="addUrl" value="/action/Item/ItemUnitLimit/Add">
                        <c:param name="ItemName" value="${item.itemName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Unit Limit.</a></p>
                </et:hasSecurityRole>
                <c:choose>
                    <c:when test="${item.itemUnitLimits.size == 0}">
                        No unit limits were found.<br />
                    </c:when>
                    <c:otherwise>
                        <display:table name="item.itemUnitLimits.list" id="itemUnitLimit" class="displaytag">
                            <display:column titleKey="columnTitle.inventoryCondition">
                                <c:choose>
                                    <c:when test="${includeInventoryConditionUrl}">
                                        <c:url var="reviewUrl" value="/action/Inventory/InventoryCondition/Review">
                                            <c:param name="InventoryConditionName" value="${itemUnitLimit.inventoryCondition.inventoryConditionName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${itemUnitLimit.inventoryCondition.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${itemUnitLimit.inventoryCondition.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.unitOfMeasureType">
                                <c:choose>
                                    <c:when test="${includeUnitOfMeasureTypeUrl}">
                                        <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                            <c:param name="UnitOfMeasureKindName" value="${itemUnitLimit.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemUnitLimit.unitOfMeasureType.unitOfMeasureTypeName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${itemUnitLimit.unitOfMeasureType.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${itemUnitLimit.unitOfMeasureType.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                        <display:column titleKey="columnTitle.minimum" class="quantity">
                            <c:out value="${itemUnitLimit.minimumQuantity}" />
                        </display:column>
                        <display:column titleKey="columnTitle.maximum" class="quantity">
                            <c:out value="${itemUnitLimit.maximumQuantity}" />
                        </display:column>
                            <et:hasSecurityRole securityRoles="ItemUnitLimit.Edit:ItemUnitLimit.Delete">
                                <display:column>
                                    <et:hasSecurityRole securityRole="ItemUnitLimit.Edit">
                                        <c:url var="editUrl" value="/action/Item/ItemUnitLimit/Edit">
                                            <c:param name="ItemName" value="${itemUnitLimit.item.itemName}" />
                                            <c:param name="InventoryConditionName" value="${itemUnitLimit.inventoryCondition.inventoryConditionName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemUnitLimit.unitOfMeasureType.unitOfMeasureTypeName}" />
                                        </c:url>
                                        <a href="${editUrl}">Edit</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="ItemUnitLimit.Delete">
                                        <c:url var="deleteUrl" value="/action/Item/ItemUnitLimit/Delete">
                                            <c:param name="ItemName" value="${itemUnitLimit.item.itemName}" />
                                            <c:param name="InventoryConditionName" value="${itemUnitLimit.inventoryCondition.inventoryConditionName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemUnitLimit.unitOfMeasureType.unitOfMeasureTypeName}" />
                                        </c:url>
                                        <a href="${deleteUrl}">Delete</a>
                                    </et:hasSecurityRole>
                                </display:column>
                            </et:hasSecurityRole>
                        </display:table>
                    </c:otherwise>
                </c:choose>
                <br />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemUnitCustomerTypeLimit.List">
                <h2>Unit Customer Type Limits</h2>
                <et:hasSecurityRole securityRole="ItemUnitCustomerTypeLimit.Create">
                    <c:url var="addUrl" value="/action/Item/ItemUnitCustomerTypeLimit/Add">
                        <c:param name="ItemName" value="${item.itemName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Unit Customer Type Limit.</a></p>
                </et:hasSecurityRole>
                <c:choose>
                    <c:when test="${item.itemUnitCustomerTypeLimits.size == 0}">
                        No unit customer type limits were found.<br />
                    </c:when>
                    <c:otherwise>
                        <display:table name="item.itemUnitCustomerTypeLimits.list" id="itemUnitCustomerTypeLimit" class="displaytag">
                            <display:column titleKey="columnTitle.inventoryCondition">
                                <c:choose>
                                    <c:when test="${includeInventoryConditionUrl}">
                                        <c:url var="reviewUrl" value="/action/Inventory/InventoryCondition/Review">
                                            <c:param name="InventoryConditionName" value="${itemUnitCustomerTypeLimit.inventoryCondition.inventoryConditionName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${itemUnitCustomerTypeLimit.inventoryCondition.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${itemUnitCustomerTypeLimit.inventoryCondition.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.unitOfMeasureType">
                                <c:choose>
                                    <c:when test="${includeUnitOfMeasureTypeUrl}">
                                        <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                            <c:param name="UnitOfMeasureKindName" value="${itemUnitCustomerTypeLimit.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemUnitCustomerTypeLimit.unitOfMeasureType.unitOfMeasureTypeName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${itemUnitCustomerTypeLimit.unitOfMeasureType.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${itemUnitCustomerTypeLimit.unitOfMeasureType.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.customerType">
                                <c:choose>
                                    <c:when test="${includeCustomerTypeUrl}">
                                        <c:url var="reviewUrl" value="/action/Customer/CustomerType/Review">
                                            <c:param name="CustomerTypeName" value="${itemUnitCustomerTypeLimit.customerType.customerTypeName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${itemUnitCustomerTypeLimit.customerType.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${itemUnitCustomerTypeLimit.customerType.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.minimum" class="quantity">
                                <c:out value="${itemUnitCustomerTypeLimit.minimumQuantity}" />
                            </display:column>
                            <display:column titleKey="columnTitle.maximum" class="quantity">
                                <c:out value="${itemUnitCustomerTypeLimit.maximumQuantity}" />
                            </display:column>
                            <et:hasSecurityRole securityRoles="ItemUnitCustomerTypeLimit.Edit:ItemUnitCustomerTypeLimit.Delete">
                                <display:column>
                                    <et:hasSecurityRole securityRole="ItemUnitCustomerTypeLimit.Edit">
                                        <c:url var="editUrl" value="/action/Item/ItemUnitCustomerTypeLimit/Edit">
                                            <c:param name="ItemName" value="${itemUnitCustomerTypeLimit.item.itemName}" />
                                            <c:param name="InventoryConditionName" value="${itemUnitCustomerTypeLimit.inventoryCondition.inventoryConditionName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemUnitCustomerTypeLimit.unitOfMeasureType.unitOfMeasureTypeName}" />
                                            <c:param name="CustomerTypeName" value="${itemUnitCustomerTypeLimit.customerType.customerTypeName}" />
                                        </c:url>
                                        <a href="${editUrl}">Edit</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="ItemUnitCustomerTypeLimit.Delete">
                                        <c:url var="deleteUrl" value="/action/Item/ItemUnitCustomerTypeLimit/Delete">
                                            <c:param name="ItemName" value="${itemUnitCustomerTypeLimit.item.itemName}" />
                                            <c:param name="InventoryConditionName" value="${itemUnitCustomerTypeLimit.inventoryCondition.inventoryConditionName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemUnitCustomerTypeLimit.unitOfMeasureType.unitOfMeasureTypeName}" />
                                            <c:param name="CustomerTypeName" value="${itemUnitCustomerTypeLimit.customerType.customerTypeName}" />
                                        </c:url>
                                        <a href="${deleteUrl}">Delete</a>
                                    </et:hasSecurityRole>
                                </display:column>
                            </et:hasSecurityRole>
                        </display:table>
                    </c:otherwise>
                </c:choose>
                <br />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemDescription.List">
                <h2>Item Descriptions</h2>
                <et:hasSecurityRole securityRole="ItemDescription.Create">
                    <c:url var="addUrl" value="/action/Item/Item/DescriptionAdd/Step1">
                        <c:param name="ItemName" value="${item.itemName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Description.</a></p>
                </et:hasSecurityRole>
                <c:choose>
                    <c:when test="${item.itemDescriptions.size == 0}">
                        No descriptions were found.<br />
                    </c:when>
                    <c:otherwise>
                        <et:hasSecurityRole securityRole="ItemImageType.Review" var="includeItemImageTypeReviewUrl" />
                        <display:table name="item.itemDescriptions.list" id="itemDescription" class="displaytag">
                            <display:column titleKey="columnTitle.type">
                                <et:appearance appearance="${itemDescription.itemDescriptionType.entityInstance.entityAppearance.appearance}"><c:out value="${itemDescription.itemDescriptionType.description}" /></et:appearance>
                            </display:column>
                            <display:column titleKey="columnTitle.language">
                                <c:out value="${itemDescription.language.description}" />
                            </display:column>
                            <display:column titleKey="columnTitle.description">
                                <c:choose>
                                    <c:when test="${itemDescription.mimeType == null}">
                                        <c:out value="${itemDescription.stringDescription}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test='${itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "TEXT" || itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "IMAGE"}'>
                                                <c:url var="viewUrl" value="/action/Item/Item/ItemDescriptionReview">
                                                    <c:param name="ItemDescriptionTypeName" value="${itemDescription.itemDescriptionType.itemDescriptionTypeName}" />
                                                    <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                                                    <c:param name="LanguageIsoName" value="${itemDescription.language.languageIsoName}" />
                                                </c:url>
                                            </c:when>
                                            <c:otherwise>
                                                <c:url var="viewUrl" value="/action/Item/Item/ItemBlobDescriptionView">
                                                    <c:param name="ItemDescriptionTypeName" value="${itemDescription.itemDescriptionType.itemDescriptionTypeName}" />
                                                    <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                                                    <c:param name="LanguageIsoName" value="${itemDescription.language.languageIsoName}" />
                                                </c:url>
                                            </c:otherwise>
                                        </c:choose>
                                        <a href="${viewUrl}">View</a>
                                        <c:if test='${itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "IMAGE"}'>
                                            <c:url var="downloadUrl" value="/action/Item/Item/ItemBlobDescriptionView">
                                                <c:param name="ItemDescriptionTypeName" value="${itemDescription.itemDescriptionType.itemDescriptionTypeName}" />
                                                <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                                                <c:param name="LanguageIsoName" value="${itemDescription.language.languageIsoName}" />
                                                <c:param name="Disposition" value="attachment" />
                                            </c:url>
                                            <a href="${downloadUrl}">Download</a>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.mimeType">
                                <c:if test='${itemDescription.mimeType != null}'>
                                    <c:out value="${itemDescription.mimeType.description}" /> (<c:out value="${itemDescription.mimeType.mimeTypeName}" />)
                                </c:if>
                            </display:column>
                            <display:column titleKey="columnTitle.itemImageDescription">
                                <c:if test='${itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "IMAGE"}'>
                                    <c:choose>
                                        <c:when test="${includeItemImageTypeReviewUrl}">
                                            <c:url var="itemImageTypeUrl" value="/action/Item/ItemImageType/Review">
                                                <c:param name="ItemImageTypeName" value="${itemDescription.itemImageType.itemImageTypeName}" />
                                            </c:url>
                                            <a href="${itemImageTypeUrl}"><c:out value="${itemDescription.itemImageType.description}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${itemDescription.itemImageType.description}" />
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test='${itemDescription.width != null && itemDescription.height != null}'>
                                        <c:out value="${itemDescription.width}" /> x <c:out value="${itemDescription.height}" />
                                    </c:if>
                                    <c:if test='${itemDescription.scaledFromParent}'>
                                        <i>(Scaled)</i>
                                    </c:if>
                                </c:if>
                            </display:column>
                            <display:column>
                                <c:url var="editUrl" value="/action/Item/Item/DescriptionEdit">
                                    <c:param name="ItemDescriptionTypeName" value="${itemDescription.itemDescriptionType.itemDescriptionTypeName}" />
                                    <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                                    <c:param name="LanguageIsoName" value="${itemDescription.language.languageIsoName}" />
                                </c:url>
                                <a href="${editUrl}">Edit</a>
                                <c:url var="deleteUrl" value="/action/Item/Item/DescriptionDelete">
                                    <c:param name="ItemDescriptionTypeName" value="${itemDescription.itemDescriptionType.itemDescriptionTypeName}" />
                                    <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                                    <c:param name="LanguageIsoName" value="${itemDescription.language.languageIsoName}" />
                                </c:url>
                                <a href="${deleteUrl}">Delete</a>
                            </display:column>
                        </display:table>
                    </c:otherwise>
                </c:choose>
                <br />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemAlias.List">
                <h2>Item Aliases</h2>
                <et:hasSecurityRole securityRole="ItemAlias.Create">
                    <c:url var="addUrl" value="/action/Item/ItemAlias/Add">
                        <c:param name="ItemName" value="${item.itemName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Item Alias.</a></p>
                </et:hasSecurityRole>
                <c:choose>
                    <c:when test="${item.itemAliases.size == 0}">
                        No item aliases were found.<br />
                    </c:when>
                    <c:otherwise>
                        <display:table name="item.itemAliases.list" id="itemAlias" class="displaytag">
                            <display:column titleKey="columnTitle.unitOfMeasureType">
                                <c:out value="${itemAlias.unitOfMeasureType.description}" />
                            </display:column>
                            <display:column titleKey="columnTitle.itemAliasType">
                                <c:out value="${itemAlias.itemAliasType.description}" />
                            </display:column>
                            <display:column property="alias" titleKey="columnTitle.alias" />
                            <c:if test='${linksInItemAliasFirstRow}'>
                                <display:column>
                                    <et:hasSecurityRole securityRole="ItemAlias.Edit">
                                        <c:url var="editUrl" value="/action/Item/ItemAlias/Edit">
                                            <c:param name="ItemName" value="${itemAlias.item.itemName}" />
                                            <c:param name="OriginalAlias" value="${itemAlias.alias}" />
                                        </c:url>
                                        <a href="${editUrl}">Edit</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="ItemAlias.Delete">
                                        <c:url var="deleteUrl" value="/action/Item/ItemAlias/Delete">
                                            <c:param name="ItemName" value="${itemAlias.item.itemName}" />
                                            <c:param name="Alias" value="${itemAlias.alias}" />
                                        </c:url>
                                        <a href="${deleteUrl}">Delete</a>
                                    </et:hasSecurityRole>
                                </display:column>
                            </c:if>
                        </display:table>
                    </c:otherwise>
                </c:choose>
                <br />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemShippingTime.List">
                <c:if test='${item.itemShippingTimes.size > 0}'>
                    <h2>Item Shipping Times</h2>
                    <display:table name="item.itemShippingTimes.list" id="itemShippingTime" class="displaytag">
                        <display:column titleKey="columnTitle.customerType">
                            <c:out value="${itemShippingTime.customerType.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.shippingStartTime">
                            <c:out value="${itemShippingTime.shippingStartTime}" />
                        </display:column>
                        <display:column titleKey="columnTitle.shippingEndTime">
                            <c:out value="${itemShippingTime.shippingEndTime}" />
                        </display:column>
                        <display:column>
                            <c:url var="editUrl" value="/action/Item/ItemShippingTime/Edit">
                                <c:param name="ItemName" value="${itemShippingTime.item.itemName}" />
                                <c:param name="CustomerTypeName" value="${itemShippingTime.customerType.customerTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </display:column>
                    </display:table>
                    <br />
                </c:if>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemPackCheckRequirement.List">
                <h2>Item Pack Check Requirements</h2>
                <et:hasSecurityRole securityRole="ItemPackCheckRequirement.Create">
                    <c:url var="addUrl" value="/action/Item/ItemPackCheckRequirement/Add">
                        <c:param name="ItemName" value="${item.itemName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Item Pack Check Requirement.</a></p>
                </et:hasSecurityRole>
                <c:choose>
                    <c:when test="${item.itemPackCheckRequirements.size == 0}">
                        No item pack check requirements were found.<br />
                    </c:when>
                    <c:otherwise>
                        <display:table name="item.itemPackCheckRequirements.list" id="itemPackCheckRequirement" class="displaytag">
                            <display:column titleKey="columnTitle.unitOfMeasureType">
                                <c:choose>
                                    <c:when test="${includeUnitOfMeasureTypeUrl}">
                                        <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                                            <c:param name="UnitOfMeasureKindName" value="${itemPackCheckRequirement.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemPackCheckRequirement.unitOfMeasureType.unitOfMeasureTypeName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${itemPackCheckRequirement.unitOfMeasureType.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${itemPackCheckRequirement.unitOfMeasureType.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.minimumQuantity" class="quantity">
                                <c:out value="${itemPackCheckRequirement.minimumQuantity}" />
                            </display:column>
                            <display:column titleKey="columnTitle.maximumQuantity" class="quantity">
                                <c:out value="${itemPackCheckRequirement.maximumQuantity}" />
                            </display:column>
                            <c:if test='${linksInItemPackCheckRequirementFirstRow}'>
                                <display:column>
                                    <et:hasSecurityRole securityRole="ItemPackCheckRequirement.Edit">
                                        <c:url var="editUrl" value="/action/Item/ItemPackCheckRequirement/Edit">
                                            <c:param name="ItemName" value="${itemPackCheckRequirement.item.itemName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemPackCheckRequirement.unitOfMeasureType.unitOfMeasureTypeName}" />
                                        </c:url>
                                        <a href="${editUrl}">Edit</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="ItemPackCheckRequirement.Delete">
                                        <c:url var="deleteUrl" value="/action/Item/ItemPackCheckRequirement/Delete">
                                            <c:param name="ItemName" value="${itemPackCheckRequirement.item.itemName}" />
                                            <c:param name="UnitOfMeasureTypeName" value="${itemPackCheckRequirement.unitOfMeasureType.unitOfMeasureTypeName}" />
                                        </c:url>
                                        <a href="${deleteUrl}">Delete</a>
                                    </et:hasSecurityRole>
                                </display:column>
                            </c:if>
                        </display:table>
                    </c:otherwise>
                </c:choose>
                <br />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemWeight.List">
                <c:if test='${item.itemWeights.size > 0}'>
                    <h2>Item Weights</h2>
                    <display:table name="item.itemWeights.list" id="itemWeight" class="displaytag">
                        <display:column titleKey="columnTitle.unitOfMeasureType">
                            <c:out value="${itemWeight.unitOfMeasureType.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.type">
                            <c:choose>
                                <c:when test="${includeItemWeightTypeReviewUrl}">
                                    <c:url var="reviewUrl" value="/action/Item/ItemWeightType/Review">
                                        <c:param name="ItemWeightTypeTypeName" value="${itemWeight.itemWeightType.itemWeightTypeName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><et:appearance appearance="${itemWeight.itemWeightType.entityInstance.entityAppearance.appearance}"><c:out value="${itemWeight.itemWeightType.description}" /></et:appearance></a>
                                </c:when>
                                <c:otherwise>
                                    <et:appearance appearance="${itemWeight.itemWeightType.entityInstance.entityAppearance.appearance}"><c:out value="${itemWeight.itemWeightType.description}" /></et:appearance>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.weight">
                            <c:out value="${itemWeight.weight}" />
                        </display:column>
                        <display:column>
                            <c:url var="editUrl" value="/action/Item/ItemWeight/Edit">
                                <c:param name="ItemName" value="${itemWeight.item.itemName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${itemWeight.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="ItemWeightTypeName" value="${itemWeight.itemWeightType.itemWeightTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Item/ItemWeight/Delete">
                                <c:param name="ItemName" value="${itemWeight.item.itemName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${itemWeight.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="ItemWeightTypeName" value="${itemWeight.itemWeightType.itemWeightTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                    </display:table>
                    <br />
                </c:if>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemVolume.List">
                <c:if test='${item.itemVolumes.size > 0}'>
                    <h2>Item Volumes</h2>
                    <display:table name="item.itemVolumes.list" id="itemVolume" class="displaytag">
                        <display:column titleKey="columnTitle.unitOfMeasureType">
                            <c:out value="${itemVolume.unitOfMeasureType.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.type">
                            <c:choose>
                                <c:when test="${includeItemVolumeTypeReviewUrl}">
                                    <c:url var="reviewUrl" value="/action/Item/ItemVolumeType/Review">
                                        <c:param name="ItemVolumeTypeTypeName" value="${itemVolume.itemVolumeType.itemVolumeTypeName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><et:appearance appearance="${itemVolume.itemVolumeType.entityInstance.entityAppearance.appearance}"><c:out value="${itemVolume.itemVolumeType.description}" /></et:appearance></a>
                                </c:when>
                                <c:otherwise>
                                    <et:appearance appearance="${itemVolume.itemVolumeType.entityInstance.entityAppearance.appearance}"><c:out value="${itemVolume.itemVolumeType.description}" /></et:appearance>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.height">
                            <c:out value="${itemVolume.height}" />
                        </display:column>
                        <display:column titleKey="columnTitle.width">
                            <c:out value="${itemVolume.width}" />
                        </display:column>
                        <display:column titleKey="columnTitle.depth">
                            <c:out value="${itemVolume.depth}" />
                        </display:column>
                        <display:column>
                            <c:url var="editUrl" value="/action/Item/ItemVolume/Edit">
                                <c:param name="ItemName" value="${itemVolume.item.itemName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${itemVolume.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="ItemVolumeTypeName" value="${itemVolume.itemVolumeType.itemVolumeTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Item/ItemVolume/Delete">
                                <c:param name="ItemName" value="${itemVolume.item.itemName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${itemVolume.unitOfMeasureType.unitOfMeasureTypeName}" />
                                <c:param name="ItemVolumeTypeName" value="${itemVolume.itemVolumeType.itemVolumeTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                    </display:table>
                    <br />
                </c:if>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemCountryOfOrigin.List">
                <c:if test='${item.itemCountryOfOrigins.size > 0}'>
                    <h2>Item Country of Origins</h2>
                    <display:table name="item.itemCountryOfOrigins.list" id="itemCountryOfOrigin" class="displaytag">
                        <display:column titleKey="columnTitle.country">
                            <c:out value="${itemCountryOfOrigin.countryGeoCode.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.percent" class="percent">
                            <c:out value="${itemCountryOfOrigin.percent}" />
                        </display:column>
                        <display:column>
                            <c:url var="editUrl" value="/action/Item/ItemCountryOfOrigin/Edit">
                                <c:param name="ItemName" value="${itemCountryOfOrigin.item.itemName}" />
                                <c:param name="CountryName" value="${itemCountryOfOrigin.countryGeoCode.geoCodeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Item/ItemCountryOfOrigin/Delete">
                                <c:param name="ItemName" value="${itemCountryOfOrigin.item.itemName}" />
                                <c:param name="CountryName" value="${itemCountryOfOrigin.countryGeoCode.geoCodeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                    </display:table>
                    <br />
                </c:if>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemHarmonizedTariffScheduleCode.List">
                <h2>Item Harmonized Tariff Schedule Codes</h2>
                <et:hasSecurityRole securityRole="ItemHarmonizedTariffScheduleCode.Create">
                    <c:url var="addUrl" value="/action/Item/ItemHarmonizedTariffScheduleCode/Add">
                        <c:param name="ItemName" value="${item.itemName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Item Harmonized Tariff Schedule Code.</a></p>
                </et:hasSecurityRole>
                <c:choose>
                    <c:when test="${item.itemHarmonizedTariffScheduleCodes.size == 0}">
                        No item harmonized tariff schedule codes were found.<br />
                    </c:when>
                    <c:otherwise>
                        <display:table name="item.itemHarmonizedTariffScheduleCodes.list" id="itemHarmonizedTariffScheduleCode" class="displaytag">
                            <et:hasSecurityRole securityRole="ItemHarmonizedTariffScheduleCode.Review">
                                <display:column>
                                    <c:url var="reviewUrl" value="/action/Item/ItemHarmonizedTariffScheduleCode/Review">
                                        <c:param name="ItemName" value="${itemHarmonizedTariffScheduleCode.item.itemName}" />
                                        <c:param name="CountryName" value="${itemHarmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                        <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                                    </c:url>
                                    <a href="${reviewUrl}">Review</a>
                                </display:column>
                            </et:hasSecurityRole>
                            <display:column titleKey="columnTitle.country">
                                <c:choose>
                                    <c:when test="${includeCountryUrl}">
                                        <c:url var="reviewUrl" value="/action/Configuration/Country/Review">
                                            <c:param name="CountryName" value="${itemHarmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${itemHarmonizedTariffScheduleCode.countryGeoCode.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${itemHarmonizedTariffScheduleCode.countryGeoCode.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.harmonizedTariffScheduleCodeUseType">
                                <c:choose>
                                    <c:when test="${includeHarmonizedTariffScheduleCodeUseTypeUrl}">
                                        <c:url var="reviewUrl" value="/action/Item/HarmonizedTariffScheduleCodeUseType/Review">
                                            <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.name">
                                <c:choose>
                                    <c:when test="${includeHarmonizedTariffScheduleCodeUrl}">
                                        <c:url var="reviewUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Review">
                                            <c:param name="CountryName" value="${itemHarmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                            <c:param name="HarmonizedTariffScheduleCodeName" value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.description">
                                <c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.description}" />
                            </display:column>
                            <c:if test='${linksInItemHarmonizedTariffScheduleCodeFirstRow}'>
                                <display:column>
                                    <et:hasSecurityRole securityRole="ItemHarmonizedTariffScheduleCode.Edit">
                                        <c:url var="editUrl" value="/action/Item/ItemHarmonizedTariffScheduleCode/Edit">
                                            <c:param name="ItemName" value="${itemHarmonizedTariffScheduleCode.item.itemName}" />
                                            <c:param name="CountryName" value="${itemHarmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                            <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                                        </c:url>
                                        <a href="${editUrl}">Edit</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="ItemHarmonizedTariffScheduleCode.Delete">
                                        <c:url var="deleteUrl" value="/action/Item/ItemHarmonizedTariffScheduleCode/Delete">
                                            <c:param name="ItemName" value="${itemHarmonizedTariffScheduleCode.item.itemName}" />
                                            <c:param name="CountryName" value="${itemHarmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                            <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                                        </c:url>
                                        <a href="${deleteUrl}">Delete</a>
                                    </et:hasSecurityRole>
                                </display:column>
                            </c:if>
                        </display:table>
                    </c:otherwise>
                </c:choose>
                <br />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="OfferItem.List">
                <c:if test='${item.offerItems.size > 0}'>
                    <h2>Offer Items</h2>
                    <display:table name="item.offerItems.list" id="offerItem" class="displaytag" sort="list" requestURI="/action/Item/Item/Review">
                        <display:column titleKey="columnTitle.offer" sortable="true" sortProperty="offer.offerName">
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
                        </display:column>
                        <c:if test='${linksInOfferItemFirstRow || linksInOfferItemSecondRow}'>
                            <display:column>
                                <et:hasSecurityRole securityRole="OfferItemPrice.List">
                                    <c:url var="offerItemPricesUrl" value="/action/Advertising/OfferItemPrice/Main">
                                        <c:param name="OfferName" value="${offerItem.offer.offerName}" />
                                        <c:param name="ItemName" value="${offerItem.item.itemName}" />
                                    </c:url>
                                    <a href="${offerItemPricesUrl}">Offer Item Prices</a>
                                </et:hasSecurityRole>
                                <c:if test='${linksInOfferItemFirstRow && linksInOfferItemSecondRow}'>
                                    <br />
                                </c:if>
                                <et:hasSecurityRole securityRole="OfferItem.Delete">
                                    <c:url var="deleteUrl" value="/action/Advertising/OfferItem/Delete">
                                        <c:param name="OfferName" value="${offerItem.offer.offerName}" />
                                        <c:param name="ItemName" value="${offerItem.item.itemName}" />
                                    </c:url>
                                    <a href="${deleteUrl}">Delete</a>
                                </et:hasSecurityRole>
                            </display:column>
                        </c:if>
                    </display:table>
                    <br />
                </c:if>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="VendorItem.List">
                <h2>Vendor Items</h2>
                <c:choose>
                    <c:when test="${item.vendorItems.size == 0}">
                        No vendor items were found.<br />
                        <br />
                    </c:when>
                    <c:otherwise>
                        <display:table name="item.vendorItems.list" id="vendorItem" class="displaytag" >
                            <et:hasSecurityRole securityRole="VendorItem.Review">
                                <display:column>
                                    <c:url var="reviewUrl" value="/action/Purchasing/VendorItem/Review">
                                        <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                                        <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                                    </c:url>
                                    <a href="${reviewUrl}">Review</a>
                                </display:column>
                            </et:hasSecurityRole>
                            <display:column titleKey="columnTitle.vendor">
                                <c:choose>
                                    <c:when test="${vendorItem.vendor.partyGroup.name != null}">
                                        <c:set var="vendorDescription" value="${vendorItem.vendor.partyGroup.name}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="vendorDescription" value="${vendorItem.vendor.vendorName}" />
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${includeVendorUrl}">
                                        <c:url var="vendorUrl" value="/action/Purchasing/Vendor/Review">
                                            <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                                        </c:url>
                                        <a href="${vendorUrl}"><c:out value="${vendorDescription}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${vendorDescription}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.vendorItemName">
                                <c:out value="${vendorItem.vendorItemName}" />
                            </display:column>
                            <display:column titleKey="columnTitle.description">
                                <c:out value="${vendorItem.description}" />
                            </display:column>
                            <display:column titleKey="columnTitle.priority">
                                <c:out value="${vendorItem.priority}" />
                            </display:column>
                            <display:column titleKey="columnTitle.cancellationPolicy">
                                <c:if test="${vendorItem.cancellationPolicy != null}">
                                    <c:choose>
                                        <c:when test="${includeCancellationPolicyUrl}">
                                            <c:url var="reviewUrl" value="/action/CancellationPolicy/CancellationPolicy/Review">
                                                <c:param name="CancellationKindName" value="${vendorItem.cancellationPolicy.cancellationKind.cancellationKindName}" />
                                                <c:param name="CancellationPolicyName" value="${vendorItem.cancellationPolicy.cancellationPolicyName}" />
                                            </c:url>
                                            <a href="${reviewUrl}"><c:out value="${vendorItem.cancellationPolicy.description}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${vendorItem.cancellationPolicy.description}" />
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </display:column>
                            <display:column titleKey="columnTitle.returnPolicy">
                                <c:if test="${vendorItem.returnPolicy != null}">
                                    <c:choose>
                                        <c:when test="${includeReturnPolicyUrl}">
                                            <c:url var="reviewUrl" value="/action/ReturnPolicy/ReturnPolicy/Review">
                                                <c:param name="ReturnKindName" value="${vendorItem.returnPolicy.returnKind.returnKindName}" />
                                                <c:param name="ReturnPolicyName" value="${vendorItem.returnPolicy.returnPolicyName}" />
                                            </c:url>
                                            <a href="${reviewUrl}"><c:out value="${vendorItem.returnPolicy.description}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${vendorItem.returnPolicy.description}" />
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </display:column>
                            <display:column titleKey="columnTitle.status">
                                <c:choose>
                                    <c:when test="${includeEditableVendorItemStatus}">
                                        <c:url var="statusUrl" value="/action/Purchasing/VendorItem/Status">
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                            <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                                            <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                                        </c:url>
                                        <a href="${statusUrl}"><c:out value="${vendorItem.vendorItemStatus.workflowStep.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${vendorItem.vendorItemStatus.workflowStep.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column>
                                <c:url var="vendorItemCostsUrl" value="/action/Purchasing/VendorItemCost/Main">
                                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                                    <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                                </c:url>
                                <a href="${vendorItemCostsUrl}">Costs</a><br />
                                <c:url var="editUrl" value="/action/Purchasing/VendorItem/Edit">
                                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                                    <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                                </c:url>
                                <a href="${editUrl}">Edit</a>
                                <c:url var="deleteUrl" value="/action/Purchasing/VendorItem/Delete">
                                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                                    <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                                </c:url>
                                <a href="${deleteUrl}">Delete</a>
                            </display:column>
                            <et:hasSecurityRole securityRole="Event.List">
                                <display:column>
                                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                        <c:param name="EntityRef" value="${vendorItem.entityInstance.entityRef}" />
                                    </c:url>
                                    <a href="${eventsUrl}">Events</a>
                                </display:column>
                            </et:hasSecurityRole>
                        </display:table>
                    </c:otherwise>
                </c:choose>
                <br />
            </et:hasSecurityRole>

            <c:set var="commonUrl" scope="request" value="Item/Item" />
            <c:set var="comments" scope="request" value="${item.comments.map['ITEM_CUSTOMER']}" />
            <jsp:include page="../../include/itemComments.jsp" />

            <c:set var="comments" scope="request" value="${item.comments.map['ITEM_CUSTOMER_SERVICE']}" />
            <jsp:include page="../../include/itemComments.jsp" />

            <c:set var="comments" scope="request" value="${item.comments.map['ITEM_PURCHASING']}" />
            <jsp:include page="../../include/itemComments.jsp" />

            <c:set var="ratings" scope="request" value="${item.ratings.map['ITEM_CUSTOMER']}" />
            <jsp:include page="../../include/itemRatings.jsp" />

            <c:set var="tagScopes" scope="request" value="${item.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${item.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${item.entityInstance}" />
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
