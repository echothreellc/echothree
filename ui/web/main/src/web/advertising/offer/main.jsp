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
        <title>Offers</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Main" />">Advertising</a> &gt;&gt;
                Offers
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Advertising/Offer/Add/Step1" />">Add Offer.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="offers" id="offer" class="displaytag" sort="list" requestURI="/action/Advertising/Offer/Main">
                <display:column titleKey="columnTitle.name" sortable="true" sortProperty="offerName">
                    <c:url var="reviewUrl" value="/action/Advertising/Offer/Review">
                        <c:param name="OfferName" value="${offer.offerName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offer.offerName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description" sortable="true" sortProperty="description">
                    <c:out value="${offer.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${offer.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Advertising/Offer/SetDefault">
                                <c:param name="OfferName" value="${offer.offerName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="offerUsesUrl" value="/action/Advertising/OfferUse/Main">
                        <c:param name="OfferName" value="${offer.offerName}" />
                    </c:url>
                    <a href="${offerUsesUrl}">Offer Uses</a>
                    <c:url var="offerItemsUrl" value="/action/Advertising/OfferItem/Main">
                        <c:param name="OfferName" value="${offer.offerName}" />
                    </c:url>
                    <a href="${offerItemsUrl}">Offer Items</a>
                    <c:url var="offerCustomerTypesUrl" value="/action/Advertising/OfferCustomerType/Main">
                        <c:param name="OfferName" value="${offer.offerName}" />
                    </c:url>
                    <a href="${offerCustomerTypesUrl}">Customer Types</a>
                    <c:url var="offerChainTypesUrl" value="/action/Advertising/OfferChainType/Main">
                        <c:param name="OfferName" value="${offer.offerName}" />
                    </c:url>
                    <a href="${offerChainTypesUrl}">Chain Types</a><br />
                    <c:url var="editUrl" value="/action/Advertising/Offer/Edit">
                        <c:param name="OriginalOfferName" value="${offer.offerName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Advertising/Offer/Description">
                        <c:param name="OfferName" value="${offer.offerName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Advertising/Offer/Delete">
                        <c:param name="OfferName" value="${offer.offerName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${offer.entityInstance.entityRef}" />
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
