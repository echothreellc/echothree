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
        <title>Review Lockout Policy (<c:out value="${partyTypeLockoutPolicy.partyType.partyTypeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/PartyType/Main" />">Party Types</a> &gt;&gt;
                Review Lockout Policy (<c:out value="${partyTypeLockoutPolicy.partyType.partyTypeName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${partyTypeLockoutPolicy.partyType.description}" /> Lockout Policy</b></font></p>
            <br />
            Lockout failure count:
            <c:choose>
                <c:when test='${partyTypeLockoutPolicy.lockoutFailureCount == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypeLockoutPolicy.lockoutFailureCount}" />
                </c:otherwise>
            </c:choose>
            <br />
            Reset Failure Count Time:
            <c:choose>
                <c:when test='${partyTypeLockoutPolicy.resetFailureCountTime == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypeLockoutPolicy.resetFailureCountTime}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Manual Lockout Reset:
            <c:choose>
                <c:when test="${partyTypeLockoutPolicy.manualLockoutReset}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            Lockout Inactive Time:
            <c:choose>
                <c:when test='${partyTypeLockoutPolicy.lockoutInactiveTime == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypeLockoutPolicy.lockoutInactiveTime}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${partyTypeLockoutPolicy.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
