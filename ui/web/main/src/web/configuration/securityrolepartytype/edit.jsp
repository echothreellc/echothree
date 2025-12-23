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
        <title><fmt:message key="pageTitle.securityRolePartyTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <c:url var="securityRoleGroupsUrl" value="/action/Configuration/SecurityRoleGroup/Main">
                    <c:param name="ParentSecurityRoleGroupName" value="${securityRolePartyType.securityRole.securityRoleGroup.parentSecurityRoleGroup.securityRoleGroupName}" />
                </c:url>
                <a href="${securityRoleGroupsUrl}"><fmt:message key="navigation.securityRoleGroups" /></a> &gt;&gt;
                <c:url var="securityRolesUrl" value="/action/Configuration/SecurityRole/Main">
                    <c:param name="SecurityRoleGroupName" value="${securityRolePartyType.securityRole.securityRoleGroup.securityRoleGroupName}" />
                </c:url>
                <a href="${securityRolesUrl}"><fmt:message key="navigation.securityRoles" /></a> &gt;&gt;
                <c:url var="securityRolePartyTypesUrl" value="/action/Configuration/SecurityRolePartyType/Main">
                    <c:param name="SecurityRoleGroupName" value="${securityRolePartyType.securityRole.securityRoleGroup.securityRoleGroupName}" />
                    <c:param name="SecurityRoleName" value="${securityRolePartyType.securityRole.securityRoleName}" />
                </c:url>
                <a href="${securityRolePartyTypesUrl}"><fmt:message key="navigation.securityRolePartyTypes" /></a> &gt;&gt;
                Edit
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.executionResult.hasLockErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                    <html:form action="/Configuration/SecurityRolePartyType/Edit" method="POST" focus="securityRoleGroupName">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.partySelectorChoice" />:</td>
                                <td>
                                    <html:select property="partySelectorChoice">
                                        <html:optionsCollection property="partySelectorChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="PartySelectorName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="securityRoleGroupName" />
                                    <html:hidden property="securityRoleName" />
                                    <html:hidden property="partyTypeName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/entityLock.jsp" />
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>