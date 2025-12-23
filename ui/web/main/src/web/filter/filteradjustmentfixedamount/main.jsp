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
        <title>Filter Adjustments</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Filter/Main" />">Filters</a> &gt;&gt;
                <a href="<c:url value="/action/Filter/FilterKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="filterAdjustmentsUrl" value="/action/Filter/FilterAdjustment/Main">
                    <c:param name="FilterKindName" value="${filterAdjustment.filterKind.filterKindName}" />
                </c:url>
                <a href="${filterAdjustmentsUrl}">Adjustments</a> &gt;&gt;
                Details
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Filter/FilterAdjustmentFixedAmount/Add">
                <c:param name="FilterKindName" value="${filterAdjustment.filterKind.filterKindName}" />
                <c:param name="FilterAdjustmentName" value="${filterAdjustment.filterAdjustmentName}" />
            </c:url>
            <p><a href="${addUrl}">Add.</a></p>
            <display:table name="filterAdjustmentFixedAmounts" id="filterAdjustmentFixedAmount" class="displaytag">
                <display:column titleKey="columnTitle.unitOfMeasure">
                    ${filterAdjustmentFixedAmount.unitOfMeasureType.unitOfMeasureKind.description},
                    ${filterAdjustmentFixedAmount.unitOfMeasureType.description}
                </display:column>
                <display:column property="currency.description" titleKey="columnTitle.currency" />
                <display:column property="unitAmount" titleKey="columnTitle.unitAmount" class="amount" />
                <display:column>
                    <c:url var="editUrl" value="/action/Filter/FilterAdjustmentFixedAmount/Edit">
                        <c:param name="FilterKindName" value="${filterAdjustmentFixedAmount.filterAdjustment.filterKind.filterKindName}" />
                        <c:param name="FilterAdjustmentName" value="${filterAdjustmentFixedAmount.filterAdjustment.filterAdjustmentName}" />
                        <c:param name="UnitOfMeasureName"
                        value="${filterAdjustmentFixedAmount.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}:${filterAdjustmentFixedAmount.unitOfMeasureType.unitOfMeasureTypeName}" />
                        <c:param name="CurrencyIsoName" value="${filterAdjustmentFixedAmount.currency.currencyIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Filter/FilterAdjustmentFixedAmount/Delete">
                        <c:param name="FilterKindName" value="${filterAdjustmentFixedAmount.filterAdjustment.filterKind.filterKindName}" />
                        <c:param name="FilterAdjustmentName" value="${filterAdjustmentFixedAmount.filterAdjustment.filterAdjustmentName}" />
                        <c:param name="UnitOfMeasureName"
                        value="${filterAdjustmentFixedAmount.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}:${filterAdjustmentFixedAmount.unitOfMeasureType.unitOfMeasureTypeName}" />
                        <c:param name="CurrencyIsoName" value="${filterAdjustmentFixedAmount.currency.currencyIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
