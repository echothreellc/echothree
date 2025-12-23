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
        <title>Chains</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Subscription/Main" />">Subscriptions</a> &gt;&gt;
                <a href="<c:url value="/action/Subscription/SubscriptionKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="subscriptionTypesUrl" value="/action/Subscription/SubscriptionType/Main">
                    <c:param name="SubscriptionKindName" value="${subscriptionKind.subscriptionKindName}" />
                </c:url>
                <a href="${subscriptionTypesUrl}">Types</a> &gt;&gt;
                Chains
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Subscription/SubscriptionTypeChain/Add/Step1">
                <c:param name="SubscriptionKindName" value="${subscriptionKind.subscriptionKindName}" />
                <c:param name="SubscriptionTypeName" value="${subscriptionType.subscriptionTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Chain.</a></p>
            <display:table name="subscriptionTypeChains" id="subscriptionTypeChain" class="displaytag">
                <display:column titleKey="columnTitle.chainType">
                    <c:url var="chainTypeUrl" value="/action/Chain/ChainType/Review">
                        <c:param name="ChainKindName" value="${subscriptionTypeChain.chain.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${subscriptionTypeChain.chain.chainType.chainTypeName}" />
                    </c:url>
                    <a href="${chainTypeUrl}"><c:out value="${subscriptionTypeChain.chain.chainType.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.chainDescription">
                    <c:url var="chainUrl" value="/action/Chain/Chain/Review">
                        <c:param name="ChainKindName" value="${subscriptionTypeChain.chain.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${subscriptionTypeChain.chain.chainType.chainTypeName}" />
                        <c:param name="ChainName" value="${subscriptionTypeChain.chain.chainName}" />
                    </c:url>
                    <a href="${chainUrl}"><c:out value="${subscriptionTypeChain.chain.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.remainingTime">
                    <c:out value="${subscriptionTypeChain.remainingTime}" />
                </display:column>
                <display:column>
                    <c:if test="${subscriptionTypeChain.chain.chainType.chainTypeName == 'EXPIRATION_WARNING'}">
                        <c:url var="editUrl" value="/action/Subscription/SubscriptionTypeChain/Edit">
                            <c:param name="SubscriptionKindName" value="${subscriptionTypeChain.subscriptionType.subscriptionKind.subscriptionKindName}" />
                            <c:param name="SubscriptionTypeName" value="${subscriptionTypeChain.subscriptionType.subscriptionTypeName}" />
                            <c:param name="ChainName" value="${subscriptionTypeChain.chain.chainName}" />
                       </c:url>
                       <a href="${editUrl}">Edit</a>
                    </c:if>
                    <c:url var="deleteUrl" value="/action/Subscription/SubscriptionTypeChain/Delete">
                        <c:param name="SubscriptionKindName" value="${subscriptionTypeChain.subscriptionType.subscriptionKind.subscriptionKindName}" />
                        <c:param name="SubscriptionTypeName" value="${subscriptionTypeChain.subscriptionType.subscriptionTypeName}" />
                        <c:param name="ChainName" value="${subscriptionTypeChain.chain.chainName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
