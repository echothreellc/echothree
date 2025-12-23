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
        <title><fmt:message key="pageTitle.securityRoleDescriptions" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <c:url var="securityRoleGroupsUrl" value="/action/Configuration/SecurityRoleGroup/Main">
                    <c:if test="${securityRole.securityRoleGroup.parentSecurityRoleGroup.securityRoleGroupName != 'ROOT'}">
                        <c:param name="ParentSecurityRoleGroupName" value="${securityRole.securityRoleGroup.parentSecurityRoleGroup.securityRoleGroupName}" />
                    </c:if>
                </c:url>
                <a href="${securityRoleGroupsUrl}"><fmt:message key="navigation.securityRoleGroups" /></a> &gt;&gt;
                <c:url var="securityRolesUrl" value="/action/Configuration/SecurityRole/Main">
                     <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                </c:url>
                <a href="${securityRolesUrl}"><fmt:message key="navigation.securityRoles" /></a> &gt;&gt;
                <fmt:message key="navigation.securityRoleGroupDescriptions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/SecurityRole/DescriptionAdd">
                <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                <c:param name="SecurityRoleName" value="${securityRole.securityRoleName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="securityRoleDescriptions" id="securityRoleDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${securityRoleDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${securityRoleDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/SecurityRole/DescriptionEdit">
                        <c:param name="SecurityRoleGroupName" value="${securityRoleDescription.securityRole.securityRoleGroup.securityRoleGroupName}" />
                        <c:param name="SecurityRoleName" value="${securityRoleDescription.securityRole.securityRoleName}" />
                        <c:param name="LanguageIsoName" value="${securityRoleDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Configuration/SecurityRole/DescriptionDelete">
                        <c:param name="SecurityRoleGroupName" value="${securityRoleDescription.securityRole.securityRoleGroup.securityRoleGroupName}" />
                        <c:param name="SecurityRoleName" value="${securityRoleDescription.securityRole.securityRoleName}" />
                        <c:param name="LanguageIsoName" value="${securityRoleDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
