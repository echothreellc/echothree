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
        <title>Sales Order Batches</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/SalesOrder/Main" />">Sales Orders</a> &gt;&gt;
                Sales Order Batches &gt;&gt;
                Search
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/SalesOrder/SalesOrderBatch/Add" />">Add Sales Order Batch.</a></p>
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/SalesOrder/SalesOrderBatch/Main" method="POST" focus="batchName">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.salesOrderBatchName" />:</td>
                        <td>
                            <html:text size="20" property="batchName" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="BatchName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
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
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.paymentMethod" />:</td>
                        <td>
                            <html:select property="paymentMethodChoice">
                                <html:optionsCollection property="paymentMethodChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="PaymentMethodName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.salesOrderBatchStatus" />:</td>
                        <td>
                            <html:select property="salesOrderBatchStatusChoice">
                                <html:optionsCollection property="salesOrderBatchStatusChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="SalesOrderBatchStatusChoice">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.salesOrderBatchAliasType" />:</td>
                        <td>
                            <html:select property="batchAliasTypeChoice">
                                <html:optionsCollection property="batchAliasTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="BatchAliasTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.alias" />:</td>
                        <td>
                            <html:text size="20" property="alias" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="Alias">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.createdSince" />:</td>
                        <td>
                            <html:text size="60" property="createdSince" maxlength="30" />
                            <et:validationErrors id="errorMessage" property="CreatedSince">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.modifiedSince" />:</td>
                        <td>
                            <html:text size="60" property="modifiedSince" maxlength="30" />
                            <et:validationErrors id="errorMessage" property="ModifiedSince">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
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