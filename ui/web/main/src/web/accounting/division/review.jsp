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
            <fmt:message key="pageTitle.division">
                <fmt:param value="${division.divisionName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Company/Main" />"><fmt:message key="navigation.companies" /></a> &gt;&gt;
                <c:url var="divisionsUrl" value="/action/Accounting/Division/Main">
                    <c:param name="CompanyName" value="${division.company.companyName}" />
                </c:url>
                <a href="${divisionsUrl}"><fmt:message key="navigation.divisions" /></a> &gt;&gt;
                Review (<c:out value="${division.divisionName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <p><font size="+2"><b><c:out value="${division.partyGroup.name}" /></b></font></p>
            <br />
            <c:url var="companyUrl" value="/action/Accounting/Company/Review">
                <c:param name="CompanyName" value="${division.company.companyName}" />
            </c:url>
            <fmt:message key="label.companyName" />: <a href="${companyUrl}">${division.company.companyName}</a><br />
            <fmt:message key="label.divisionName" />: ${division.divisionName}<br />
            <br />
            <br />
            <br />
            <c:set var="commonUrl" scope="request" value="Accounting/DivisionContactMechanism" />
            <c:set var="party" scope="request" value="${division}" />
            <c:set var="partyContactMechanisms" scope="request" value="${division.partyContactMechanisms}" />
            <jsp:include page="../../include/partyContactMechanisms.jsp" />
            <c:set var="tagScopes" scope="request" value="${division.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${division.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${division.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Accounting/Division/Review">
                <c:param name="PartyName" value="${division.partyName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
