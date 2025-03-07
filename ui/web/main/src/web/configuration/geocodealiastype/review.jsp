<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2025 Echo Three, LLC                                              -->
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
        <title>Review (<c:out value="${geoCodeAliasType.geoCodeAliasTypeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/GeoCodeType/Main" />">Geo Code Types</a> &gt;&gt;
                <c:url var="geoCodeAliasTypesUrl" value="/action/Configuration/GeoCodeAliasType/Main">
                    <c:param name="GeoCodeTypeName" value="${geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
                </c:url>
                <a href="${geoCodeAliasTypesUrl}">Geo Code Alias Types</a> &gt;&gt;
                Review (<c:out value="${geoCodeAliasType.geoCodeAliasTypeName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <p><font size="+2"><b><c:out value="${geoCodeAliasType.description}" /></b></font></p>
            <br />
            <c:url var="geoCodeTypeUrl" value="/action/Configuration/GeoCodeType/Review">
                <c:param name="GeoCodeTypeName" value="${geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
            </c:url>
            Geo Code Type Name: <a href="${geoCodeTypeUrl}"><c:out value="${geoCodeAliasType.geoCodeType.geoCodeTypeName}" /></a><br />
            Geo Code Alias Type Name: <c:out value="${geoCodeAliasType.geoCodeAliasTypeName}" /><br />
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${geoCodeAliasType.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
