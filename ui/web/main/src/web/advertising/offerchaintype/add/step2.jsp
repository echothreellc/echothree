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

<%@ include file="../../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>Chain Types</title>
        <html:base/>
        <%@ include file="../../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Main" />">Advertising</a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Offer/Main" />">Offers</a> &gt;&gt;
                <c:url var="offerChainTypeUrl" value="/action/Advertising/OfferChainType/Main">
                    <c:param name="OfferName" value="${offer.offerName}" />
                </c:url>
                <a href="${offerChainTypeUrl}">Chain Types</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            Chain Type:<br /><br />
            <c:forEach items="${chainTypes}" var="chainType">
                <c:url var="addUrl" value="/action/Advertising/OfferChainType/Add/Step3">
                    <c:param name="OfferName" value="${offer.offerName}" />
                    <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainType.chainTypeName}" />
                </c:url>
                &nbsp;&nbsp;&nbsp;&nbsp;<a href="${addUrl}"><c:out value="${chainType.description}" /></a><br />
             </c:forEach>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
