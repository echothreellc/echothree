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
        <title>Clubs</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Club/Main" />">Clubs</a> &gt;&gt;
                Clubs
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Club/Club/Add" />">Add Club.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="clubs" id="club" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Club/Club/Review">
                        <c:param name="ClubName" value="${club.clubName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${club.clubName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${club.description}" />
                </display:column>
                <display:column titleKey="columnTitle.subscriptionType">
                    <c:url var="subscriptionTypeUrl" value="/action/Subscription/SubscriptionType/Review">
                        <c:param name="SubscriptionKindName" value="${club.subscriptionType.subscriptionKind.subscriptionKindName}" />
                        <c:param name="SubscriptionTypeName" value="${club.subscriptionType.subscriptionTypeName}" />
                    </c:url>
                    <a href="${subscriptionTypeUrl}"><c:out value="${club.subscriptionType.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.filter">
                    <c:url var="clubPriceFilterUrl" value="/action/Filter/Filter/Review">
                        <c:param name="FilterKindName" value="${club.clubPriceFilter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${club.clubPriceFilter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${club.clubPriceFilter.filterName}" />
                    </c:url>
                    <a href="${clubPriceFilterUrl}"><c:out value="${club.clubPriceFilter.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.currency">
                    <c:url var="currencyUrl" value="/action/Accounting/Currency/Review">
                        <c:param name="CurrencyIsoName" value="${club.currency.currencyIsoName}" />
                    </c:url>
                    <a href="${currencyUrl}"><c:out value="${club.currency.description}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${club.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Club/Club/SetDefault">
                                <c:param name="ClubName" value="${club.clubName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="chainItemsUrl" value="/action/Club/ClubItem/Main">
                        <c:param name="ClubName" value="${club.clubName}" />
                    </c:url>
                    <a href="${chainItemsUrl}">Items</a><br />
                    <c:url var="editUrl" value="/action/Club/Club/Edit">
                        <c:param name="OriginalClubName" value="${club.clubName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Club/Club/Description">
                        <c:param name="ClubName" value="${club.clubName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Club/Club/Delete">
                        <c:param name="ClubName" value="${club.clubName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${club.entityInstance.entityRef}" />
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
