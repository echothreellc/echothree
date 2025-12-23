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
        <title><fmt:message key="pageTitle.searchSortOrders" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/SearchKind/Main" />"><fmt:message key="navigation.searchKinds" /></a> &gt;&gt;
                <fmt:message key="navigation.searchSortOrders" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:SearchSortOrder.Create:SearchSortOrder.Edit:SearchSortOrder.Delete:SearchSortOrder.Review:SearchSortOrder.Description" />
            <et:hasSecurityRole securityRoles="SearchSortOrder.Edit:SearchSortOrder.Description:SearchSortOrder.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="SearchSortOrder.Create">
                <c:url var="addUrl" value="/action/Configuration/SearchSortOrder/Add">
                    <c:param name="SearchKindName" value="${searchKind.searchKindName}" />
                </c:url>
                <p><a href="${addUrl}">Add Search Sort Order.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="SearchSortOrder.Review" var="includeReviewUrl" />
            <display:table name="searchSortOrders" id="searchSortOrder" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/SearchSortOrder/Review">
                                <c:param name="SearchKindName" value="${searchSortOrder.searchKind.searchKindName}" />
                                <c:param name="SearchSortOrderName" value="${searchSortOrder.searchSortOrderName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${searchSortOrder.searchSortOrderName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${searchSortOrder.searchSortOrderName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${searchSortOrder.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${searchSortOrder.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="SearchSortOrder.Edit">
                                <c:url var="setDefaultUrl" value="/action/Configuration/SearchSortOrder/SetDefault">
                                    <c:param name="SearchKindName" value="${searchSortOrder.searchKind.searchKindName}" />
                                    <c:param name="SearchSortOrderName" value="${searchSortOrder.searchSortOrderName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="SearchSortOrder.Edit">
                            <c:url var="editUrl" value="/action/Configuration/SearchSortOrder/Edit">
                                <c:param name="SearchKindName" value="${searchSortOrder.searchKind.searchKindName}" />
                                <c:param name="OriginalSearchSortOrderName" value="${searchSortOrder.searchSortOrderName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="SearchSortOrder.Description">
                            <c:url var="descriptionsUrl" value="/action/Configuration/SearchSortOrder/Description">
                                <c:param name="SearchKindName" value="${searchSortOrder.searchKind.searchKindName}" />
                                <c:param name="SearchSortOrderName" value="${searchSortOrder.searchSortOrderName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="SearchSortOrder.Delete">
                            <c:url var="deleteUrl" value="/action/Configuration/SearchSortOrder/Delete">
                                <c:param name="SearchKindName" value="${searchSortOrder.searchKind.searchKindName}" />
                                <c:param name="SearchSortOrderName" value="${searchSortOrder.searchSortOrderName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:if test='${searchSortOrder.entityInstance.entityTime.createdTime != null}'>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${searchSortOrder.entityInstance.entityRef}" />
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
