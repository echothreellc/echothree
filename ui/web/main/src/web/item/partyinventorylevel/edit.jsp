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
        <title>Inventory Levels (<c:out value="${partyInventoryLevel.item.itemName}" />)</title>
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
                    <c:param name="ItemName" value="${partyInventoryLevel.item.itemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${partyInventoryLevel.item.itemName}" />)</a> &gt;&gt;
                <c:url var="partyInventoryLevelsUrl" value="/action/Item/PartyInventoryLevel/Main">
                    <c:param name="ItemName" value="${partyInventoryLevel.item.itemName}" />
                </c:url>
                <a href="${partyInventoryLevelsUrl}">Inventory Levels</a> &gt;&gt;
                Edit
            </h2>
        </div>
        <div id="Content">
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
                    <html:form action="/Item/PartyInventoryLevel/Edit" method="POST" focus="minimumInventory">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.minimumInventory" />:</td>
                                <td>
                                    <html:text property="minimumInventory" size="12" maxlength="12" />
                                    <html:select property="minimumInventoryUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="minimumInventoryUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="MinimumInventory">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="MinimumInventoryUnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.maximumInventory" />:</td>
                                <td>
                                    <html:text property="maximumInventory" size="12" maxlength="12" />
                                    <html:select property="maximumInventoryUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="maximumInventoryUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="MaximumInventory">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="MaximumInventoryUnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.reorderQuantity" />:</td>
                                <td>
                                    <html:text property="reorderQuantity" size="12" maxlength="12" />
                                    <html:select property="reorderQuantityUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="reorderQuantityUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="ReorderQuantity">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="ReorderQuantityUnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="partyName" />
                                    <html:hidden property="itemName" />
                                    <html:hidden property="inventoryConditionName" />
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
