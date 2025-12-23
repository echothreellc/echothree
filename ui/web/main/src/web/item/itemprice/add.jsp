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
                <c:url var="itemPricesUrl" value="/action/Item/ItemPrice/Main">
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <a href="${itemPricesUrl}">Prices</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <c:choose>
                <c:when test="${item.itemPriceType.itemPriceTypeName == 'FIXED'}">
                    <html:form action="/Item/ItemPrice/Add" method="POST" focus="unitPrice">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.inventoryConditionChoice" />:</td>
                                <td>
                                    <html:select property="inventoryConditionChoice">
                                        <html:optionsCollection property="inventoryConditionChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="InventoryConditionName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.unitOfMeasureTypeChoice" />:</td>
                                <td>
                                    <html:select property="unitOfMeasureTypeChoice">
                                        <html:optionsCollection property="unitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="UnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.currencyChoice" />:</td>
                                <td>
                                    <html:select property="currencyChoice">
                                        <html:optionsCollection property="currencyChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="CurrencyIsoName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.unitPrice" />:</td>
                                <td>
                                    <html:text property="unitPrice" size="15" maxlength="15" /> (*)
                                    <et:validationErrors id="errorMessage" property="UnitPrice">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="itemName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                </c:when>
                <c:when test="${item.itemPriceType.itemPriceTypeName == 'VARIABLE'}">
                    <html:form action="/Item/ItemPrice/Add" method="POST" focus="minimumUnitPrice">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.inventoryConditionChoice" />:</td>
                                <td>
                                    <html:select property="inventoryConditionChoice">
                                        <html:optionsCollection property="inventoryConditionChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="InventoryConditionName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.unitOfMeasureTypeChoice" />:</td>
                                <td>
                                    <html:select property="unitOfMeasureTypeChoice">
                                        <html:optionsCollection property="unitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="UnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.currencyChoice" />:</td>
                                <td>
                                    <html:select property="currencyChoice">
                                        <html:optionsCollection property="currencyChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="CurrencyIsoName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.minimumUnitPrice" />:</td>
                                <td>
                                    <html:text property="minimumUnitPrice" size="15" maxlength="15" /> (*)
                                    <et:validationErrors id="errorMessage" property="MinimumUnitPrice">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.maximumUnitPrice" />:</td>
                                <td>
                                    <html:text property="maximumUnitPrice" size="15" maxlength="15" /> (*)
                                    <et:validationErrors id="errorMessage" property="MaximumUnitPrice">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.unitPriceIncrement" />:</td>
                                <td>
                                    <html:text property="unitPriceIncrement" size="15" maxlength="15" /> (*)
                                    <et:validationErrors id="errorMessage" property="UnitPriceIncrement">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="itemName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                </c:when>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
