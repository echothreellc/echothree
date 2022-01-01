<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2022 Echo Three, LLC                                              -->
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
            <fmt:message key="pageTitle.partyAliasType">
                <fmt:param value="${partyAliasType.partyAliasTypeName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/PartyType/Main" />"><fmt:message key="navigation.partyTypes" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/PartyAliasType/Main" />"><fmt:message key="navigation.partyAliasTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.partyAliasType">
                    <fmt:param value="${partyAliasType.partyAliasTypeName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${partyAliasType.partyAliasTypeName != partyAliasType.description}">
                    <p><font size="+2"><b><c:out value="${partyAliasType.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${partyAliasType.partyAliasTypeName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${partyAliasType.partyAliasTypeName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            Party Alias Type Name: ${partyAliasType.partyAliasTypeName}<br />
            <br />
            Validation Pattern: <c:out value="${partyAliasType.validationPattern}" /><br />
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${partyAliasType.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
