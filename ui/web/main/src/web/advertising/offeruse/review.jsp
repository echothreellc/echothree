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
        <title>Review (${offerUse.offer.offerName}:${offerUse.use.useName})</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Main" />">Advertising</a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Offer/Main" />">Offers</a> &gt;&gt;
                <c:url var="offerUsesUrl" value="/action/Advertising/OfferUse/Main">
                    <c:param name="OfferName" value="${offerUse.offer.offerName}" />
                </c:url>
                <a href="${offerUsesUrl}">Offer Uses</a> &gt;&gt;
                Review (${offerUse.offer.offerName}:${offerUse.use.useName})
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${offerUse.offer.description}:${offerUse.use.description}" /></b></font></p>
            <br />
            <c:url var="offerUrl" value="/action/Advertising/Offer/Review">
                <c:param name="OfferName" value="${offerUse.offer.offerName}" />
            </c:url>
            Offer: <a href="${offerUrl}"><c:out value="${offerUse.offer.offerName}" /></a><br />
            <c:url var="useUrl" value="/action/Advertising/Use/Review">
                <c:param name="UseName" value="${offerUse.use.useName}" />
            </c:url>
            Use: <a href="${useUrl}"><c:out value="${offerUse.use.useName}" /></a><br />
            <br />
            <br />
            <br />
            Created: <c:out value="${offerUse.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${offerUse.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${offerUse.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:if test='${offerUse.entityInstance.entityTime.deletedTime != null}'>
                Deleted: <c:out value="${offerUse.entityInstance.entityTime.deletedTime}" /><br />
            </c:if>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${offerUse.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
