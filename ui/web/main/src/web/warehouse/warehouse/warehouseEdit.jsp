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
        <title><fmt:message key="pageTitle.warehouses" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <et:countWarehouseResults searchTypeName="EMPLOYEE" countVar="warehouseResultsCount" commandResultVar="countWarehouseResultsCommandResult" logErrors="false" />
                <c:if test="${warehouseResultsCount > 0}">
                    <a href="<c:url value="/action/Warehouse/Warehouse/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
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
                    <html:form action="/Warehouse/Warehouse/WarehouseEdit" method="POST" focus="warehouseName">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.warehouseName" />:</td>
                                <td>
                                    <html:text property="warehouseName" size="40" maxlength="40" /> (*)
                                    <et:validationErrors id="errorMessage" property="WarehouseName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.warehouseType" />:</td>
                                <td>
                                    <html:select property="warehouseTypeChoice">
                                        <html:optionsCollection property="warehouseTypeChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="WarehouseTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.description" />:</td>
                                <td>
                                    <html:text size="40" property="name" maxlength="60" />
                                    <et:validationErrors id="errorMessage" property="Name">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.language" />:</td>
                                <td>
                                    <html:select property="languageChoice">
                                        <html:optionsCollection property="languageChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="LanguageIsoName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.currency" />:</td>
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
                                <td align=right><fmt:message key="label.timeZone" />:</td>
                                <td>
                                    <html:select property="timeZoneChoice">
                                        <html:optionsCollection property="timeZoneChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="TimeZoneName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.dateTimeFormat" />:</td>
                                <td>
                                    <html:select property="dateTimeFormatChoice">
                                        <html:optionsCollection property="dateTimeFormatChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DateTimeFormatName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.isDefault" />:</td>
                                <td>
                                    <html:checkbox property="isDefault" /> (*)
                                    <et:validationErrors id="errorMessage" property="IsDefault">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.sortOrder" />:</td>
                                <td>
                                    <html:text property="sortOrder" size="12" maxlength="12" /> (*)
                                    <et:validationErrors id="errorMessage" property="SortOrder">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.inventoryMovePrinterGroup" />:</td>
                                <td>
                                    <html:select property="inventoryMovePrinterGroupChoice">
                                        <html:optionsCollection property="inventoryMovePrinterGroupChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="InventoryMovePrinterGroupName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.picklistPrinterGroup" />:</td>
                                <td>
                                    <html:select property="picklistPrinterGroupChoice">
                                        <html:optionsCollection property="picklistPrinterGroupChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="PicklistPrinterGroupName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.packingListPrinterGroup" />:</td>
                                <td>
                                    <html:select property="packingListPrinterGroupChoice">
                                        <html:optionsCollection property="packingListPrinterGroupChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="PackingListPrinterGroupName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.shippingManifestPrinterGroup" />:</td>
                                <td>
                                    <html:select property="shippingManifestPrinterGroupChoice">
                                        <html:optionsCollection property="shippingManifestPrinterGroupChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="ShippingManifestPrinterGroupName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="originalWarehouseName" />
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
