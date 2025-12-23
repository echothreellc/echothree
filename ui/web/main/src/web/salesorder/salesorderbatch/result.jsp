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
                <fmt:message key="navigation.results" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="SalesOrderBatch.Create:SalesOrderBatch.Edit:SalesOrderBatch.Review" />
            <et:hasSecurityRole securityRole="SalesOrderBatch.Create">
                <p><a href="<c:url value="/action/SalesOrder/SalesOrderBatch/Add" />">Add Sales Order Batch.</a></p>
            </et:hasSecurityRole>
            <et:containsExecutionError key="UnknownUserVisitSearch">
                <c:set var="unknownUserVisitSearch" value="true" />
            </et:containsExecutionError>
            <c:choose>
                <c:when test="${unknownUserVisitSearch}">
                    <p>Your search results are no longer available, please perform your search again.</p>
                </c:when>
                <c:otherwise>
                    <et:hasSecurityRole securityRole="SalesOrderBatch.Review" var="includeReviewUrl" />
                    <c:choose>
                        <c:when test="${salesOrderBatchResultCount == null || salesOrderBatchResultCount < 21}">
                            <display:table name="salesOrderBatchResults.list" id="salesOrderBatchResult" class="displaytag">
                                <display:column titleKey="columnTitle.name">
                                    <c:url var="reviewUrl" value="/action/SalesOrder/SalesOrderBatch/Review">
                                        <c:param name="BatchName" value="${salesOrderBatchResult.salesOrderBatch.batchName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${salesOrderBatchResult.salesOrderBatch.batchName}" /></a>
                                </display:column>
                                <display:column titleKey="columnTitle.currency">
                                    <c:out value="${salesOrderBatchResult.salesOrderBatch.currency.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.paymentMethod">
                                    <c:out value="${salesOrderBatchResult.salesOrderBatch.paymentMethod.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.count">
                                    <c:out value="${salesOrderBatchResult.salesOrderBatch.count}" />
                                </display:column>
                                <display:column titleKey="columnTitle.amount" class="amount">
                                    <c:out value="${salesOrderBatchResult.salesOrderBatch.amount}" />
                                </display:column>
                                <display:column titleKey="columnTitle.status">
                                    <c:choose>
                                        <c:when test="${salesOrderBatchResult.salesOrderBatch.batchStatus.workflowStep.hasDestinations}">
                                            <c:url var="statusUrl" value="/action/SalesOrder/SalesOrderBatch/Status">
                                                <c:param name="BatchName" value="${salesOrderBatchResult.salesOrderBatch.batchName}" />
                                            </c:url>
                                            <a href="${statusUrl}"><c:out value="${salesOrderBatchResult.salesOrderBatch.batchStatus.workflowStep.description}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${salesOrderBatchResult.salesOrderBatch.batchStatus.workflowStep.description}" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column>
                                    <c:url var="editSalesOrderBatchUrl" value="/action/SalesOrder/SalesOrderBatch/Edit">
                                        <c:param name="BatchName" value="${salesOrderBatchResult.salesOrderBatch.batchName}" />
                                    </c:url>
                                    <a href="${editSalesOrderBatchUrl}">Edit</a>
                                </display:column>
                            </display:table>
                            <c:if test="${salesOrderBatchResults.size > 20}">
                                <c:url var="resultsUrl" value="/action/SalesOrder/SalesOrderBatch/Result" />
                                <a href="${resultsUrl}">Paged Results</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <display:table name="salesOrderBatchResults.list" id="salesOrderBatchResult" class="displaytag" partialList="true" pagesize="20" size="salesOrderBatchResultCount" requestURI="/action/SalesOrder/SalesOrderBatch/Result">
                                <display:column titleKey="columnTitle.name">
                                    <c:url var="reviewUrl" value="/action/SalesOrder/SalesOrderBatch/Review">
                                        <c:param name="BatchName" value="${salesOrderBatchResult.salesOrderBatch.batchName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${salesOrderBatchResult.salesOrderBatch.batchName}" /></a>
                                </display:column>
                                <display:column titleKey="columnTitle.currency">
                                    <c:out value="${salesOrderBatchResult.salesOrderBatch.currency.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.paymentMethod">
                                    <c:out value="${salesOrderBatchResult.salesOrderBatch.paymentMethod.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.count">
                                    <c:out value="${salesOrderBatchResult.salesOrderBatch.count}" />
                                </display:column>
                                <display:column titleKey="columnTitle.amount" class="amount">
                                    <c:out value="${salesOrderBatchResult.salesOrderBatch.amount}" />
                                </display:column>
                                <display:column titleKey="columnTitle.status">
                                    <c:choose>
                                        <c:when test="${salesOrderBatchResult.salesOrderBatch.batchStatus.workflowStep.hasDestinations}">
                                            <c:url var="statusUrl" value="/action/SalesOrder/SalesOrderBatch/Status">
                                                <c:param name="BatchName" value="${salesOrderBatchResult.salesOrderBatch.batchName}" />
                                            </c:url>
                                            <a href="${statusUrl}"><c:out value="${salesOrderBatchResult.salesOrderBatch.batchStatus.workflowStep.description}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${salesOrderBatchResult.salesOrderBatch.batchStatus.workflowStep.description}" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column>
                                    <c:url var="editSalesOrderBatchUrl" value="/action/SalesOrder/SalesOrderBatch/Edit">
                                        <c:param name="BatchName" value="${salesOrderBatchResult.salesOrderBatch.batchName}" />
                                    </c:url>
                                    <a href="${editSalesOrderBatchUrl}">Edit</a>
                                </display:column>
                            </display:table>
                            <c:url var="resultsUrl" value="/action/SalesOrder/SalesOrderBatch/Result">
                                <c:param name="Results" value="Complete" />
                            </c:url>
                            <a href="${resultsUrl}">All Results</a>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
