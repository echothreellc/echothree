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
        <title>Chain Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Main" />">Advertising</a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Offer/Main" />">Offers</a> &gt;&gt;
                Chain Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Advertising/OfferChainType/Add/Step1">
                <c:param name="OfferName" value="${offer.offerName}" />
            </c:url>
            <p><a href="${addUrl}">Add Chain Type.</a></p>
            <display:table name="offerChainTypes" id="offerChainType" class="displaytag" sort="list" requestURI="/action/Advertising/OfferChainType/Main">
                <display:column titleKey="columnTitle.chainKind" sortable="true" sortProperty="chainKind.chainKindName">
                    <c:url var="reviewUrl" value="/action/Chain/ChainKind/Review">
                        <c:param name="ChainKindName" value="${offerChainType.chainType.chainKind.chainKindName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offerChainType.chainType.chainKind.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.chainType" sortable="true" sortProperty="chainKind.chainType.chainTypeName">
                    <c:url var="reviewUrl" value="/action/Chain/ChainType/Review">
                        <c:param name="ChainKindName" value="${offerChainType.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${offerChainType.chainType.chainTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offerChainType.chainType.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.chain" sortable="true" sortProperty="chain.chainName">
                    <c:url var="reviewUrl" value="/action/Chain/Chain/Review">
                        <c:param name="ChainKindName" value="${offerChainType.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${offerChainType.chainType.chainTypeName}" />
                        <c:param name="ChainName" value="${offerChainType.chain.chainName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offerChainType.chain.description}" /></a>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Advertising/OfferChainType/Edit">
                        <c:param name="OfferName" value="${offerChainType.offer.offerName}" />
                        <c:param name="ChainKindName" value="${offerChainType.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${offerChainType.chainType.chainTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Advertising/OfferChainType/Delete">
                        <c:param name="OfferName" value="${offerChainType.offer.offerName}" />
                        <c:param name="ChainKindName" value="${offerChainType.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${offerChainType.chainType.chainTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
