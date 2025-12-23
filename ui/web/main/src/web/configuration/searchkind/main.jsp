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
        <title><fmt:message key="pageTitle.searchKinds" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/SearchKind/Main" />"><fmt:message key="navigation.searchKinds" /></a>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:SearchKind.Create:SearchKind.Edit:SearchKind.Delete:SearchKind.Review:SearchKind.Description:SearchType.List:SearchSortOrder.List" />
            <et:hasSecurityRole securityRoles="SearchType.List:SearchSortOrder.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="SearchKind.Edit:SearchKind.Description:SearchKind.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="SearchKind.Create">
                <c:url var="addUrl" value="/action/Configuration/SearchKind/Add" />
                <p><a href="${addUrl}">Add Search Kind.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="SearchKind.Review" var="includeReviewUrl" />
            <display:table name="searchKinds" id="searchKind" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/SearchKind/Review">
                                <c:param name="SearchKindName" value="${searchKind.searchKindName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${searchKind.searchKindName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${searchKind.searchKindName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${searchKind.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${searchKind.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="SearchKind.Edit">
                                <c:url var="setDefaultUrl" value="/action/Configuration/SearchKind/SetDefault">
                                    <c:param name="SearchKindName" value="${searchKind.searchKindName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="SearchType.List">
                            <c:url var="searchTypesUrl" value="/action/Configuration/SearchType/Main">
                                <c:param name="SearchKindName" value="${searchKind.searchKindName}" />
                            </c:url>
                        </et:hasSecurityRole>
                        <a href="${searchTypesUrl}">Search Types</a>
                        <et:hasSecurityRole securityRole="SearchSortOrder.List">
                            <c:url var="searchSortOrdersUrl" value="/action/Configuration/SearchSortOrder/Main">
                                <c:param name="SearchKindName" value="${searchKind.searchKindName}" />
                            </c:url>
                        </et:hasSecurityRole>
                        <a href="${searchSortOrdersUrl}">Search Sort Orders</a>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="SearchKind.Edit">
                            <c:url var="editUrl" value="/action/Configuration/SearchKind/Edit">
                                <c:param name="OriginalSearchKindName" value="${searchKind.searchKindName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="SearchKind.Description">
                            <c:url var="descriptionsUrl" value="/action/Configuration/SearchKind/Description">
                                <c:param name="SearchKindName" value="${searchKind.searchKindName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="SearchKind.Delete">
                            <c:url var="deleteUrl" value="/action/Configuration/SearchKind/Delete">
                                <c:param name="SearchKindName" value="${searchKind.searchKindName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:if test='${searchKind.entityInstance.entityTime.createdTime != null}'>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${searchKind.entityInstance.entityRef}" />
                            </c:url>
                            <a href="${eventsUrl}">Events</a>
                        </c:if>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
