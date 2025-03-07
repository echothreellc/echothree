<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2025 Echo Three, LLC                                              -->
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
                <a href="<c:url value="/action/Item/Main" />">Items</a> &gt;&gt;
                <a href="<c:url value="/action/Item/Item/Main" />">Search</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Item/Item/Add" method="POST" focus="itemName">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.itemName" />:</td>
                        <td>
                            <html:text property="itemName" size="40" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="ItemName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.itemType" />:</td>
                        <td>
                            <html:select property="itemTypeChoice">
                                <html:optionsCollection property="itemTypeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="ItemTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.itemUseType" />:</td>
                        <td>
                            <html:select property="itemUseTypeChoice">
                                <html:optionsCollection property="itemUseTypeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="ItemUseTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.itemCategory" />:</td>
                        <td>
                            <html:select property="itemCategoryChoice">
                                <html:optionsCollection property="itemCategoryChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ItemCategoryName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.itemAccountingCategory" />:</td>
                        <td>
                            <html:select property="itemAccountingCategoryChoice">
                                <html:optionsCollection property="itemAccountingCategoryChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ItemAccountingCategoryName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.itemPurchasingCategory" />:</td>
                        <td>
                            <html:select property="itemPurchasingCategoryChoice">
                                <html:optionsCollection property="itemPurchasingCategoryChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ItemPurchasingCategoryName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.company" />:</td>
                        <td>
                            <html:select property="companyChoice">
                                <html:optionsCollection property="companyChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="CompanyName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.itemDeliveryType" />:</td>
                        <td>
                            <html:select property="itemDeliveryTypeChoice">
                                <html:optionsCollection property="itemDeliveryTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ItemDeliveryTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.itemInventoryType" />:</td>
                        <td>
                            <html:select property="itemInventoryTypeChoice">
                                <html:optionsCollection property="itemInventoryTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ItemInventoryTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.inventorySerialized" />:</td>
                        <td>
                            <html:checkbox property="inventorySerialized" />
                            <et:validationErrors id="errorMessage" property="InventorySerialized">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.shippingChargeExempt" />:</td>
                        <td>
                            <html:checkbox property="shippingChargeExempt" /> (*)
                            <et:validationErrors id="errorMessage" property="ShippingChargeExempt">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.salesOrderStartTime" />:</td>
                        <td>
                            <html:text property="salesOrderStartTime" size="50" maxlength="50" />
                            <et:validationErrors id="errorMessage" property="SalesOrderStartTime">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.salesOrderEndTime" />:</td>
                        <td>
                            <html:text property="salesOrderEndTime" size="50" maxlength="50" />
                            <et:validationErrors id="errorMessage" property="SalesOrderEndTime">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.purchaseOrderStartTime" />:</td>
                        <td>
                            <html:text property="purchaseOrderStartTime" size="50" maxlength="50" />
                            <et:validationErrors id="errorMessage" property="PurchaseOrderStartTime">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.purchaseOrderEndTime" />:</td>
                        <td>
                            <html:text property="purchaseOrderEndTime" size="50" maxlength="50" />
                            <et:validationErrors id="errorMessage" property="PurchaseOrderEndTime">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.shippingStartTime" />:</td>
                        <td>
                            <html:text property="shippingStartTime" size="50" maxlength="50" />
                            <et:validationErrors id="errorMessage" property="ShippingStartTime">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.shippingEndTime" />:</td>
                        <td>
                            <html:text property="shippingEndTime" size="50" maxlength="50" />
                            <et:validationErrors id="errorMessage" property="ShippingEndTime">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.allowClubDiscounts" />:</td>
                        <td>
                            <html:checkbox property="allowClubDiscounts" /> (*)
                            <et:validationErrors id="errorMessage" property="AllowClubDiscounts">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.allowCouponDiscounts" />:</td>
                        <td>
                            <html:checkbox property="allowCouponDiscounts" /> (*)
                            <et:validationErrors id="errorMessage" property="AllowCouponDiscounts">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.allowAssociatePayments" />:</td>
                        <td>
                            <html:checkbox property="allowAssociatePayments" /> (*)
                            <et:validationErrors id="errorMessage" property="AllowAssociatePayments">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.itemStatus" />:</td>
                        <td>
                            <html:select property="itemStatusChoice">
                                <html:optionsCollection property="itemStatusChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="ItemStatus">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.unitOfMeasureKindChoice" />:</td>
                        <td>
                            <html:select property="unitOfMeasureKindChoice">
                                <html:optionsCollection property="unitOfMeasureKindChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="UnitOfMeasureKindName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.itemPriceTypeChoice" />:</td>
                        <td>
                            <html:select property="itemPriceTypeChoice">
                                <html:optionsCollection property="itemPriceTypeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="ItemPriceTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.cancellationPolicy" />:</td>
                        <td>
                            <html:select property="cancellationPolicyChoice">
                                <html:optionsCollection property="cancellationPolicyChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="CancellationPolicyName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.returnPolicy" />:</td>
                        <td>
                            <html:select property="returnPolicyChoice">
                                <html:optionsCollection property="returnPolicyChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ReturnPolicyName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>