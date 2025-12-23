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
        <title>Review Audit Policy (<c:out value="${partyTypeAuditPolicy.partyType.partyTypeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/PartyType/Main" />">Party Types</a> &gt;&gt;
                Review Audit Policy (<c:out value="${partyTypeAuditPolicy.partyType.partyTypeName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${partyTypeAuditPolicy.partyType.description}" /> Audit Policy</b></font></p>
            <br />
            Audit Commands:
            <c:choose>
                <c:when test="${partyTypeAuditPolicy.auditCommands}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Retain User Visits Time:
            <c:choose>
                <c:when test='${partyTypeAuditPolicy.retainUserVisitsTime == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypeAuditPolicy.retainUserVisitsTime}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${partyTypeAuditPolicy.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
