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
            <fmt:message key="pageTitle.chain">
                <fmt:param value="${chain.chainName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/Main" />"><fmt:message key="navigation.chains" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/ChainKind/Main" />"><fmt:message key="navigation.chainKinds" /></a> &gt;&gt;
                <c:url var="chainTypesUrl" value="/action/Chain/ChainType/Main">
                    <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}"><fmt:message key="navigation.chainTypes" /></a> &gt;&gt;
                <c:url var="chainsUrl" value="/action/Chain/Chain/Main">
                    <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chain.chainType.chainTypeName}" />
                </c:url>
                <a href="${chainsUrl}"><fmt:message key="navigation.chains" /></a> &gt;&gt;
                <fmt:message key="navigation.chain">
                    <fmt:param value="${chain.chainName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Sequence.Review" />
            <et:hasSecurityRole securityRole="Sequence.Review" var="includeSequenceReviewUrl" />
            <p><font size="+2"><b><c:out value="${chain.description}" /></b></font></p>
            <br />
            Chain Name: ${chain.chainName}<br />
            <br />
            Chain Instance Sequence:
            <c:choose>
                <c:when test="${chain.chainInstanceSequence == null}">
                    <i><fmt:message key="phrase.notSetUsingDefault" /></i>.
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${includeSequenceReviewUrl}">
                            <c:url var="chainInstanceSequenceUrl" value="/action/Sequence/Sequence/Review">
                                <c:param name="SequenceTypeName" value="${chain.chainInstanceSequence.sequenceType.sequenceTypeName}" />
                                <c:param name="SequenceName" value="${chain.chainInstanceSequence.sequenceName}" />
                            </c:url>
                            <a href="${chainInstanceSequenceUrl}">${chain.chainInstanceSequence.description}</a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${chain.chainInstanceSequence.description}" />
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${chain.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Chain/ChainKind/Review">
                <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                <c:param name="ChainTypeName" value="${chain.chainType.chainTypeName}" />
                <c:param name="ChainName" value="${chain.chainName}" />
            </c:url>
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
