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
        <title><fmt:message key="pageTitle.items" /></title>
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
                <c:url var="reviewUrl" value="/action/Item/Item/Review">
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${item.itemName}" />)</a> &gt;&gt;
                Edit
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${item.description}" /></b></font></p>
            <p><font size="+1">${item.itemName}</font></p>
            <br />
            <c:choose>
                <c:when test="${commandResult.executionResult.hasLockErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                    <html:form action="/Item/Item/Edit" method="POST" focus="itemName">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.itemName" />:</td>
                                <td>
                                    <html:text property="itemName" size="40" maxlength="40" /> (*)
                                    <et:validationErrors id="errorMessage" property="ItemName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.itemCategory" />:</td>
                                <td>
                                    <html:select property="itemCategoryChoice">
                                        <html:optionsCollection property="itemCategoryChoices" />
                                    </html:select> (*)
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
                                <td>
                                    <html:hidden property="originalItemName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/entityLock.jsp" />
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>