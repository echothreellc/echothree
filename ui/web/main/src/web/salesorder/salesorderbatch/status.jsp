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
                <a href="<c:url value="/action/SalesOrder/SalesOrderBatch/Main" />">Search</a> &gt;&gt;
                <et:countSalesOrderBatchResults searchTypeName="SALES_ORDER_BATCH_MAINTENANCE" countVar="salesOrderBatchResultsCount" commandResultVar="countSalesOrderBatchResultsCommandResult" logErrors="false" />
                <c:if test="${salesOrderBatchResultsCount > 0}">
                    <a href="<c:url value="/action/SalesOrder/SalesOrderBatch/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                Edit Status
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.executionResult.hasErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <html:form action="/SalesOrder/SalesOrderBatch/Status" method="POST">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.salesOrderBatchStatus" />:</td>
                                <td>
                                    <html:select property="salesOrderBatchStatusChoice">
                                        <html:optionsCollection property="salesOrderBatchStatusChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="SalesOrderBatchStatusChoice">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="batchName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
