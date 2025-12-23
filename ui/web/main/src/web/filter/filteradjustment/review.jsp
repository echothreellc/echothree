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
        <title>Review (<c:out value="${filterAdjustment.filterAdjustmentName}" />)</title>
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
                Review (<c:out value="${filterAdjustment.filterAdjustmentName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${filterAdjustment.description}" /></b></font></p>
            <br />
            Filter Kind: <c:out value="${filterAdjustment.filterKind.description}" /><br />
            <br />
            Filter Adjustment Name: ${filterAdjustment.filterAdjustmentName}<br />
            Filter Adjustment Source: <c:out value="${filterAdjustment.filterAdjustmentSource.description}" /><br />
            <c:if test='${filterAdjustment.filterAdjustmentType != null}'>
                Filter Adjustment Type: <c:out value="${filterAdjustment.filterAdjustmentType.description}" /><br />
            </c:if>
            <br />
            <c:if test='${filterAdjustment.filterAdjustmentAmounts.size > 0}'>
                <display:table name="filterAdjustment.filterAdjustmentAmounts.list" id="filterAdjustmentAmount" class="displaytag">
                    <display:column titleKey="columnTitle.unitOfMeasure">
                        ${filterAdjustmentAmount.unitOfMeasureType.unitOfMeasureKind.description},
                        ${filterAdjustmentAmount.unitOfMeasureType.description}
                    </display:column>
                    <display:column property="currency.description" titleKey="columnTitle.currency" />
                    <display:column property="amount" titleKey="columnTitle.amount" class="amount" />
                    <display:column>
                        <c:url var="editUrl" value="/action/Filter/FilterAdjustmentAmount/Edit">
                            <c:param name="FilterKindName" value="${filterAdjustmentAmount.filterAdjustment.filterKind.filterKindName}" />
                            <c:param name="FilterAdjustmentName" value="${filterAdjustmentAmount.filterAdjustment.filterAdjustmentName}" />
                            <c:param name="UnitOfMeasureName"
                            value="${filterAdjustmentAmount.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}:${filterAdjustmentAmount.unitOfMeasureType.unitOfMeasureTypeName}" />
                            <c:param name="CurrencyIsoName" value="${filterAdjustmentAmount.currency.currencyIsoName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                        <c:url var="deleteUrl" value="/action/Filter/FilterAdjustmentAmount/Delete">
                            <c:param name="FilterKindName" value="${filterAdjustmentAmount.filterAdjustment.filterKind.filterKindName}" />
                            <c:param name="FilterAdjustmentName" value="${filterAdjustmentAmount.filterAdjustment.filterAdjustmentName}" />
                            <c:param name="UnitOfMeasureName"
                            value="${filterAdjustmentAmount.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}:${filterAdjustmentAmount.unitOfMeasureType.unitOfMeasureTypeName}" />
                            <c:param name="CurrencyIsoName" value="${filterAdjustmentAmount.currency.currencyIsoName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </display:column>
                </display:table>
                <br />
            </c:if>
            <c:if test='${filterAdjustment.filterAdjustmentFixedAmounts.size > 0}'>
                <display:table name="filterAdjustment.filterAdjustmentFixedAmounts.list" id="filterAdjustmentFixedAmount" class="displaytag">
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
                <br />
            </c:if>
            <c:if test='${filterAdjustment.filterAdjustmentPercents.size > 0}'>
                <display:table name="filterAdjustment.filterAdjustmentPercents.list" id="filterAdjustmentPercent" class="displaytag">
                    <display:column titleKey="columnTitle.unitOfMeasure">
                        ${filterAdjustmentPercent.unitOfMeasureType.unitOfMeasureKind.description},
                        ${filterAdjustmentPercent.unitOfMeasureType.description}
                    </display:column>
                    <display:column property="currency.description" titleKey="columnTitle.currency" />
                    <display:column property="percent" titleKey="columnTitle.percent" class="percent" />
                    <display:column>
                        <c:url var="editUrl" value="/action/Filter/FilterAdjustmentPercent/Edit">
                            <c:param name="FilterKindName" value="${filterAdjustmentPercent.filterAdjustment.filterKind.filterKindName}" />
                            <c:param name="FilterAdjustmentName" value="${filterAdjustmentPercent.filterAdjustment.filterAdjustmentName}" />
                            <c:param name="UnitOfMeasureName"
                            value="${filterAdjustmentPercent.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}:${filterAdjustmentPercent.unitOfMeasureType.unitOfMeasureTypeName}" />
                            <c:param name="CurrencyIsoName" value="${filterAdjustmentPercent.currency.currencyIsoName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                        <c:url var="deleteUrl" value="/action/Filter/FilterAdjustmentPercent/Delete">
                            <c:param name="FilterKindName" value="${filterAdjustmentPercent.filterAdjustment.filterKind.filterKindName}" />
                            <c:param name="FilterAdjustmentName" value="${filterAdjustmentPercent.filterAdjustment.filterAdjustmentName}" />
                            <c:param name="UnitOfMeasureName"
                            value="${filterAdjustmentPercent.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}:${filterAdjustmentPercent.unitOfMeasureType.unitOfMeasureTypeName}" />
                            <c:param name="CurrencyIsoName" value="${filterAdjustmentPercent.currency.currencyIsoName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </display:column>
                </display:table>
                <br />
            </c:if>
            Created: <c:out value="${filterAdjustment.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${filterAdjustment.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${filterAdjustment.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:if test='${filterAdjustment.entityInstance.entityTime.deletedTime != null}'>
                Deleted: <c:out value="${filterAdjustment.entityInstance.entityTime.deletedTime}" /><br />
            </c:if>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${filterAdjustment.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
