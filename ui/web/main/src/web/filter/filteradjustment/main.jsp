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
                Adjustments
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Filter/FilterAdjustment/Add">
                <c:param name="FilterKindName" value="${filterKind.filterKindName}" />
            </c:url>
            <p><a href="${addUrl}">Add Adjustment.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="filterAdjustments" id="filterAdjustment" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Filter/FilterAdjustment/Review">
                        <c:param name="FilterKindName" value="${filterAdjustment.filterKind.filterKindName}" />
                        <c:param name="FilterAdjustmentName" value="${filterAdjustment.filterAdjustmentName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${filterAdjustment.filterAdjustmentName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${filterAdjustment.description}" />
                </display:column>
                <display:column titleKey="columnTitle.source">
                    <c:out value="${filterAdjustment.filterAdjustmentSource.description}" />
                </display:column>
                <display:column titleKey="columnTitle.type">
                    <c:out value="${filterAdjustment.filterAdjustmentType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${filterAdjustment.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Filter/FilterAdjustment/SetDefault">
                                <c:param name="FilterKindName" value="${filterAdjustment.filterKind.filterKindName}" />
                                <c:param name="FilterAdjustmentName" value="${filterAdjustment.filterAdjustmentName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:choose>
                        <c:when test="${filterAdjustment.filterAdjustmentType.filterAdjustmentTypeName == 'AMOUNT'}">
                            <c:url var="detailsUrl" value="/action/Filter/FilterAdjustmentAmount/Main">
                                <c:param name="FilterKindName" value="${filterAdjustment.filterKind.filterKindName}" />
                                <c:param name="FilterAdjustmentName" value="${filterAdjustment.filterAdjustmentName}" />
                            </c:url>
                            <a href="${detailsUrl}">Details</a>
                        </c:when>
                        <c:when test="${filterAdjustment.filterAdjustmentType.filterAdjustmentTypeName == 'FIXED_AMOUNT'}">
                            <c:url var="detailsUrl" value="/action/Filter/FilterAdjustmentFixedAmount/Main">
                                <c:param name="FilterKindName" value="${filterAdjustment.filterKind.filterKindName}" />
                                <c:param name="FilterAdjustmentName" value="${filterAdjustment.filterAdjustmentName}" />
                            </c:url>
                            <a href="${detailsUrl}">Details</a>
                        </c:when>
                        <c:when test="${filterAdjustment.filterAdjustmentType.filterAdjustmentTypeName == 'PERCENT'}">
                            <c:url var="detailsUrl" value="/action/Filter/FilterAdjustmentPercent/Main">
                                <c:param name="FilterKindName" value="${filterAdjustment.filterKind.filterKindName}" />
                                <c:param name="FilterAdjustmentName" value="${filterAdjustment.filterAdjustmentName}" />
                            </c:url>
                            <a href="${detailsUrl}">Details</a>
                        </c:when>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Filter/FilterAdjustment/Edit">
                        <c:param name="FilterKindName" value="${filterAdjustment.filterKind.filterKindName}" />
                        <c:param name="OriginalFilterAdjustmentName" value="${filterAdjustment.filterAdjustmentName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Filter/FilterAdjustment/Description">
                        <c:param name="FilterKindName" value="${filterAdjustment.filterKind.filterKindName}" />
                        <c:param name="FilterAdjustmentName" value="${filterAdjustment.filterAdjustmentName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Filter/FilterAdjustment/Delete">
                        <c:param name="FilterKindName" value="${filterAdjustment.filterKind.filterKindName}" />
                        <c:param name="FilterAdjustmentName" value="${filterAdjustment.filterAdjustmentName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${filterAdjustment.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
