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
        <title>Filter Steps</title>
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
                Steps
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Filter/FilterStep/Add">
                <c:param name="FilterKindName" value="${filter.filterType.filterKind.filterKindName}" />
                <c:param name="FilterTypeName" value="${filter.filterType.filterTypeName}" />
                <c:param name="FilterName" value="${filter.filterName}" />
            </c:url>
            <p><a href="${addUrl}">Add Step.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="filterSteps" id="filterStep" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Filter/FilterStep/Review">
                        <c:param name="FilterKindName" value="${filterStep.filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filterStep.filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filterStep.filter.filterName}" />
                        <c:param name="FilterStepName" value="${filterStep.filterStepName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${filterStep.filterStepName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${filterStep.description}" />
                </display:column>
                <display:column property="filterItemSelector.selectorName" titleKey="columnTitle.itemSelector" />
                <display:column>
                    <c:url var="filterStepElementsUrl" value="/action/Filter/FilterStepElement/Main">
                        <c:param name="FilterKindName" value="${filterStep.filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filterStep.filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filterStep.filter.filterName}" />
                        <c:param name="FilterStepName" value="${filterStep.filterStepName}" />
                    </c:url>
                    <a href="${filterStepElementsUrl}">Elements</a>
                    <c:url var="filterDestinationStepsUrl" value="/action/Filter/FilterStepDestination/Main">
                        <c:param name="FilterKindName" value="${filterStep.filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filterStep.filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filterStep.filter.filterName}" />
                        <c:param name="FilterStepName" value="${filterStep.filterStepName}" />
                    </c:url>
                    <a href="${filterDestinationStepsUrl}">Destinations</a><br />
                    <c:url var="editUrl" value="/action/Filter/FilterStep/Edit">
                        <c:param name="FilterKindName" value="${filterStep.filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filterStep.filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filterStep.filter.filterName}" />
                        <c:param name="OriginalFilterStepName" value="${filterStep.filterStepName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Filter/FilterStep/Description">
                        <c:param name="FilterKindName" value="${filterStep.filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filterStep.filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filterStep.filter.filterName}" />
                        <c:param name="FilterStepName" value="${filterStep.filterStepName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Filter/FilterStep/Delete">
                        <c:param name="FilterKindName" value="${filterStep.filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filterStep.filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filterStep.filter.filterName}" />
                        <c:param name="FilterStepName" value="${filterStep.filterStepName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${filterStep.entityInstance.entityRef}" />
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
