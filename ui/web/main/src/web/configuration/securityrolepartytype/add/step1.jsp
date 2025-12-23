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
        <title><fmt:message key="pageTitle.securityRolePartyTypes" /></title>
        <html:base/>
        <%@ include file="../../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <c:url var="securityRoleGroupsUrl" value="/action/Configuration/SecurityRoleGroup/Main">
                    <c:param name="ParentSecurityRoleGroupName" value="${securityRole.securityRoleGroup.parentSecurityRoleGroup.securityRoleGroupName}" />
                </c:url>
                <a href="${securityRoleGroupsUrl}"><fmt:message key="navigation.securityRoleGroups" /></a> &gt;&gt;
                <c:url var="securityRolesUrl" value="/action/Configuration/SecurityRole/Main">
                    <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                </c:url>
                <a href="${securityRolesUrl}"><fmt:message key="navigation.securityRoles" /></a> &gt;&gt;
                <c:url var="securityRolePartyTypesUrl" value="/action/Configuration/SecurityRolePartyType/Main">
                    <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                    <c:param name="SecurityRoleName" value="${securityRole.securityRoleName}" />
                </c:url>
                <a href="${securityRolePartyTypesUrl}"><fmt:message key="navigation.securityRolePartyTypes" /></a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            Party Type:<br /><br />
            <c:forEach items="${partyTypes}" var="partyType">
                <c:if test="${partyType.allowUserLogins}">
                    <c:url var="addUrl" value="/action/Configuration/SecurityRolePartyType/Add/Step2">
                        <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                        <c:param name="SecurityRoleName" value="${securityRole.securityRoleName}" />
                        <c:param name="PartyTypeName" value="${partyType.partyTypeName}" />
                    </c:url>
                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="${addUrl}"><c:out value="${partyType.description}" /></a><br />
                </c:if>
            </c:forEach>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
