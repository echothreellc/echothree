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
        <title>Review (<c:out value="${filter.filterName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Filter/Main" />">Filters</a> &gt;&gt;
                <a href="<c:url value="/action/Filter/FilterKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="filterTypesUrl" value="/action/Filter/FilterType/Main">
                    <c:param name="FilterKindName" value="${filter.filterType.filterKind.filterKindName}" />
                </c:url>
                <a href="${filterTypesUrl}">Types</a> &gt;&gt;
                <c:url var="filtersUrl" value="/action/Filter/Filter/Main">
                    <c:param name="FilterKindName" value="${filter.filterType.filterKind.filterKindName}" />
                    <c:param name="FilterTypeName" value="${filter.filterType.filterTypeName}" />
                </c:url>
                <a href="${filtersUrl}">Filters</a> &gt;&gt;
                Review (<c:out value="${filter.filterName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${filter.description}" /></b></font></p>
            <br />
            Filter Kind: <c:out value="${filter.filterType.filterKind.description}" /><br />
            Filter Type: <c:out value="${filter.filterType.description}" /><br />
            <br />
            Filter Name: ${filter.filterName}<br />
            <br />
            <c:if test='${filter.initialFilterAdjustment != null}'>
                <c:url var="filterAdjustmentUrl" value="/action/Filter/FilterAdjustment/Review">
                    <c:param name="FilterKindName" value="${filter.initialFilterAdjustment.filterKind.filterKindName}" />
                    <c:param name="FilterAdjustmentName" value="${filter.initialFilterAdjustment.filterAdjustmentName}" />
                </c:url>
                Initial Filter Adjustment: <a href="${filterAdjustmentUrl}"><c:out value="${filter.initialFilterAdjustment.description}" /></a><br />
                <br />
            </c:if>
            <c:if test='${filter.filterItemSelector.description != null}'>
                <c:url var="selectorUrl" value="/action/Selector/Selector/Review">
                    <c:param name="SelectorKindName" value="${filter.filterItemSelector.selectorType.selectorKind.selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${filter.filterItemSelector.selectorType.selectorTypeName}" />
                    <c:param name="SelectorName" value="${filter.filterItemSelector.selectorName}" />
                </c:url>
                Filter Item Selector: <a href="${selectorUrl}"><c:out value="${filter.filterItemSelector.description}" /></a><br />
                <br />
            </c:if>
            <c:if test='${filter.filterEntranceSteps.size > 0}'>
                <h2>Filter Entrance Steps</h2>
                <display:table name="filter.filterEntranceSteps.list" id="filterEntranceStep" class="displaytag">
                    <display:column titleKey="columnTitle.name">
                        <c:url var="reviewUrl" value="/action/Filter/FilterStep/Review">
                            <c:param name="FilterKindName" value="${filterEntranceStep.filterStep.filter.filterType.filterKind.filterKindName}" />
                            <c:param name="FilterTypeName" value="${filterEntranceStep.filterStep.filter.filterType.filterTypeName}" />
                            <c:param name="FilterName" value="${filterEntranceStep.filterStep.filter.filterName}" />
                            <c:param name="FilterStepName" value="${filterEntranceStep.filterStep.filterStepName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${filterEntranceStep.filterStep.filterStepName}" /></a>
                    </display:column>
                    <display:column titleKey="columnTitle.description">
                        <c:out value="${filterEntranceStep.filterStep.description}" />
                    </display:column>
                </display:table>
                <br />
            </c:if>
            <c:if test='${filter.filterSteps.size > 0}'>
                <h2>Filter Steps</h2>
                <display:table name="filter.filterSteps.list" id="filterStep" class="displaytag">
                    <display:column titleKey="columnTitle.step">
                        <c:url var="reviewUrl" value="/action/Filter/FilterStep/Review">
                            <c:param name="FilterKindName" value="${filterStep.filter.filterType.filterKind.filterKindName}" />
                            <c:param name="FilterTypeName" value="${filterStep.filter.filterType.filterTypeName}" />
                            <c:param name="FilterName" value="${filterStep.filter.filterName}" />
                            <c:param name="FilterStepName" value="${filterStep.filterStepName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${filterStep.description}" /></a>
                    </display:column>
                    <display:column titleKey="columnTitle.elements">
                        <c:if test='${filterStep.filterStepElements.size > 0}'>
                            <c:set var="nestedElementsName" value="filter.filterSteps.list[${filterStep_rowNum - 1}].filterStepElements.list" />
                            <display:table name="${nestedElementsName}" id="filterStepElement" class="displaytag">
                                <display:column titleKey="columnTitle.element">
                                    <c:url var="reviewUrl" value="/action/Filter/FilterStepElement/Review">
                                        <c:param name="FilterKindName" value="${filterStepElement.filterStep.filter.filterType.filterKind.filterKindName}" />
                                        <c:param name="FilterTypeName" value="${filterStepElement.filterStep.filter.filterType.filterTypeName}" />
                                        <c:param name="FilterName" value="${filterStepElement.filterStep.filter.filterName}" />
                                        <c:param name="FilterStepName" value="${filterStepElement.filterStep.filterStepName}" />
                                        <c:param name="FilterStepElementName" value="${filterStepElement.filterStepElementName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${filterStepElement.description}" /></a>
                                </display:column>
                            </display:table>
                        </c:if>
                    </display:column>
                    <display:column titleKey="columnTitle.destinations">
                        <c:if test='${filterStep.filterStepDestinations.size > 0}'>
                            <c:set var="nestedDestinationsName" value="filter.filterSteps.list[${filterStep_rowNum - 1}].filterStepDestinations.list" />
                            <display:table name="${nestedDestinationsName}" id="filterStepDestination" class="displaytag">
                                <display:column titleKey="columnTitle.destination">
                                    <c:url var="reviewUrl" value="/action/Filter/FilterStep/Review">
                                        <c:param name="FilterKindName" value="${filterStepDestination.toFilterStep.filter.filterType.filterKind.filterKindName}" />
                                        <c:param name="FilterTypeName" value="${filterStepDestination.toFilterStep.filter.filterType.filterTypeName}" />
                                        <c:param name="FilterName" value="${filterStepDestination.toFilterStep.filter.filterName}" />
                                        <c:param name="FilterStepName" value="${filterStepDestination.toFilterStep.filterStepName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${filterStepDestination.toFilterStep.description}" /></a>
                                </display:column>
                            </display:table>
                        </c:if>
                    </display:column>
                </display:table>
                <br />
            </c:if>
            Created: <c:out value="${filter.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${filter.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${filter.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:if test='${filter.entityInstance.entityTime.deletedTime != null}'>
                Deleted: <c:out value="${filter.entityInstance.entityTime.deletedTime}" /><br />
            </c:if>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${filter.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
