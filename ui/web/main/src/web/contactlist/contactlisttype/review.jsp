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
        <title>
            <fmt:message key="pageTitle.contactListType">
                <fmt:param value="${contactListType.contactListTypeName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/Main" />"><fmt:message key="navigation.contactLists" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/ContactListType/Main" />"><fmt:message key="navigation.contactListTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.contactListType">
                    <fmt:param value="${contactListType.contactListTypeName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Chain.Review" />
            <et:hasSecurityRole securityRole="Chain.Review" var="includeChainReviewUrl" />
            <c:choose>
                <c:when test="${contactListType.contactListTypeName != contactListType.description}">
                    <p><font size="+2"><b><c:out value="${contactListType.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${contactListType.contactListTypeName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${contactListType.contactListTypeName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            Contact List Type Name: ${contactListType.contactListTypeName}<br />
            <br />
            <fmt:message key="label.confirmationRequestChain" />:
            <c:choose>
                <c:when test="${includeChainReviewUrl}">
                    <c:url var="reviewUrl" value="/action/Chain/Chain/Review">
                        <c:param name="ChainKindName" value="${contactListType.confirmationRequestChain.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${contactListType.confirmationRequestChain.chainType.chainTypeName}" />
                        <c:param name="ChainName" value="${contactListType.confirmationRequestChain.chainName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${contactListType.confirmationRequestChain.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${contactListType.confirmationRequestChain.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.subscribeChain" />:
            <c:choose>
                <c:when test="${includeChainReviewUrl}">
                    <c:url var="reviewUrl" value="/action/Chain/Chain/Review">
                        <c:param name="ChainKindName" value="${contactListType.subscribeChain.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${contactListType.subscribeChain.chainType.chainTypeName}" />
                        <c:param name="ChainName" value="${contactListType.subscribeChain.chainName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${contactListType.subscribeChain.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${contactListType.subscribeChain.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.unsubscribeChain" />:
            <c:choose>
                <c:when test="${includeChainReviewUrl}">
                    <c:url var="reviewUrl" value="/action/Chain/Chain/Review">
                        <c:param name="ChainKindName" value="${contactListType.unsubscribeChain.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${contactListType.unsubscribeChain.chainType.chainTypeName}" />
                        <c:param name="ChainName" value="${contactListType.unsubscribeChain.chainName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${contactListType.unsubscribeChain.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${contactListType.unsubscribeChain.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${contactListType.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
