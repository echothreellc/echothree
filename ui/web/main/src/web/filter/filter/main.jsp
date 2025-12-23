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
        <title>Filters</title>
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
                    <c:param name="FilterKindName" value="${filterType.filterKind.filterKindName}" />
                </c:url>
                <a href="${filterTypesUrl}">Types</a> &gt;&gt;
                Filters
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Filter/Filter/Add">
                <c:param name="FilterKindName" value="${filterType.filterKind.filterKindName}" />
                <c:param name="FilterTypeName" value="${filterType.filterTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Filter.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="filters" id="filter" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Filter/Filter/Review">
                        <c:param name="FilterKindName" value="${filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filter.filterName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${filter.filterName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${filter.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${filter.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Filter/Filter/SetDefault">
                                <c:param name="FilterKindName" value="${filter.filterType.filterKind.filterKindName}" />
                                <c:param name="FilterTypeName" value="${filter.filterType.filterTypeName}" />
                                <c:param name="FilterName" value="${filter.filterName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="filterEntranceStepsUrl" value="/action/Filter/FilterEntranceStep/Main">
                        <c:param name="FilterKindName" value="${filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filter.filterName}" />
                    </c:url>
                    <a href="${filterEntranceStepsUrl}">Entrance Steps</a>
                    <c:url var="filterStepsUrl" value="/action/Filter/FilterStep/Main">
                        <c:param name="FilterKindName" value="${filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filter.filterName}" />
                    </c:url>
                    <a href="${filterStepsUrl}">Steps</a><br />
                    <c:url var="editUrl" value="/action/Filter/Filter/Edit">
                        <c:param name="FilterKindName" value="${filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filter.filterType.filterTypeName}" />
                        <c:param name="OriginalFilterName" value="${filter.filterName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Filter/Filter/Description">
                        <c:param name="FilterKindName" value="${filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filter.filterName}" />
                    </c:url>
                    <a href="${editUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Filter/Filter/Delete">
                        <c:param name="FilterKindName" value="${filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filter.filterName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${filter.entityInstance.entityRef}" />
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
