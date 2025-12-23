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
        <title>Review (${offerItem.offer.offerName}, ${offerItem.item.itemName})</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Main" />">Advertising</a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Offer/Main" />">Offers</a> &gt;&gt;
                <c:url var="offerItemsUrl" value="/action/Advertising/OfferItem/Main">
                    <c:param name="OfferName" value="${offerItem.offer.offerName}" />
                </c:url>
                <a href="${offerItemsUrl}">Offer Items</a> &gt;&gt;
                Review (${offerItem.offer.offerName}, ${offerItem.item.itemName})
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${offerItem.offer.description}" />, ${offerItem.item.itemName}</b></font></p>
            <br />
            Offer: ${offerItem.offer.offerName}<br />
            Item: ${offerItem.item.itemName}<br />
            <br />
            <br />
            <br />
            Created: <c:out value="${offerItem.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${offerItem.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${offerItem.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:if test='${offerItem.entityInstance.entityTime.deletedTime != null}'>
                Deleted: <c:out value="${offerItem.entityInstance.entityTime.deletedTime}" /><br />
            </c:if>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${offerItem.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
