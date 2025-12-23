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
        <title>Offer Items</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <et:checkSecurityRoles securityRoles="Offer.List:Use.List:Source.List:UseType.List:OfferNameElement.List:UseNameElement.List:Offer.List:Offer.Review:Item.Review:UnitOfMeasureKind.Review:OfferItemPrice.List:OfferItem.Delete:OfferItem.Create:Event.List" />
        <div id="Header">
            <et:hasSecurityRole securityRoles="Offer.List:Use.List:Source.List:UseType.List:OfferNameElement.List:UseNameElement.List" var="includeAdvertisingUrl" />
            <et:hasSecurityRole securityRole="Offer.List" var="includeOffersUrl" />
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <c:choose>
                    <c:when test="${includeAdvertisingUrl}">
                        <a href="<c:url value="/action/Advertising/Main" />"><fmt:message key="navigation.advertising" /></a>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="navigation.advertising" />
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                <c:choose>
                    <c:when test="${includeOffersUrl}">
                        <a href="<c:url value="/action/Advertising/Offer/Main" />"><fmt:message key="navigation.offers" /></a>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="navigation.offers" />
                    </c:otherwise>
                </c:choose>
                &gt;&gt;
                Offer Items
            </h2>
        </div>
        <div id="Content">
            <et:hasSecurityRole securityRole="Offer.Review" var="includeOfferUrl" />
            <et:hasSecurityRole securityRole="Item.Review" var="includeItemUrl" />
            <et:hasSecurityRole securityRole="UnitOfMeasureKind.Review" var="includeUnitOfMeasureKindUrl" />
            <et:hasSecurityRole securityRole="OfferItemPrice.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="OfferItem.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            Offer:
            <c:choose>
                <c:when test="${includeOfferUrl}">
                    <c:url var="reviewUrl" value="/action/Advertising/Offer/Review">
                        <c:param name="OfferName" value="${offer.offerName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offer.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${offer.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <et:hasSecurityRole securityRole="OfferItem.Create">
                <c:url var="addUrl" value="/action/Advertising/OfferItem/Add">
                    <c:param name="OfferName" value="${offer.offerName}" />
                </c:url>
                <p><a href="${addUrl}">Add Offer Item.</a></p>
            </et:hasSecurityRole>
            <display:table name="offerItems" id="offerItem" class="displaytag" partialList="true" pagesize="20" size="offerItemCount" requestURI="/action/Advertising/OfferItem/Main">
                <display:column titleKey="columnTitle.itemName">
                    <c:choose>
                        <c:when test="${includeItemUrl}">
                            <c:url var="reviewUrl" value="/action/Item/Item/Review">
                                <c:param name="ItemName" value="${offerItem.item.itemName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${offerItem.item.itemName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${offerItem.item.itemName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.itemDescription">
                    <c:out value="${offerItem.item.description}" />
                </display:column>
                <display:column titleKey="columnTitle.itemType">
                    <c:out value="${offerItem.item.itemType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.unitOfMeasureKind">
                    <c:choose>
                        <c:when test="${includeUnitOfMeasureKindUrl}">
                            <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureKind/Review">
                                <c:param name="UnitOfMeasureKindName" value="${offerItem.item.unitOfMeasureKind.unitOfMeasureKindName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${offerItem.item.unitOfMeasureKind.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${offerItem.item.unitOfMeasureKind.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.itemPriceType">
                    <c:out value="${offerItem.item.itemPriceType.description}" />
                </display:column>
                <c:if test='${linksInFirstRow || linksInSecondRow}'>
                    <display:column>
                        <et:hasSecurityRole securityRole="OfferItemPrice.List">
                            <c:url var="offerItemPricesUrl" value="/action/Advertising/OfferItemPrice/Main">
                                <c:param name="OfferName" value="${offerItem.offer.offerName}" />
                                <c:param name="ItemName" value="${offerItem.item.itemName}" />
                            </c:url>
                            <a href="${offerItemPricesUrl}">Offer Item Prices</a>
                        </et:hasSecurityRole>
                        <c:if test='${linksInFirstRow && linksInSecondRow}'>
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="OfferItem.Delete">
                            <c:url var="deleteUrl" value="/action/Advertising/OfferItem/Delete">
                                <c:param name="OfferName" value="${offerItem.offer.offerName}" />
                                <c:param name="ItemName" value="${offerItem.item.itemName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${offerItem.entityInstance.entityRef}" />
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
