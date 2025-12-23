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
        <title>Roles</title>
        <html:base/>
        <%@ include file="../../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/PartySecurityRoleTemplate/Main" />">Security Role Templates</a> &gt;&gt;
                <c:url var="partySecurityRoleTemplateRolesUrl" value="/action/Configuration/PartySecurityRoleTemplateRole/Main">
                    <c:param name="PartySecurityRoleTemplateName" value="${partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                </c:url>
                <a href="${partySecurityRoleTemplateRolesUrl}">Roles</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <c:if test='${securityRoleGroups != null}'>
                Security Role Groups:<br /><br />
                <c:forEach items="${securityRoleGroups}" var="securityRoleGroup">
                    <c:url var="addUrl" value="/action/Configuration/PartySecurityRoleTemplateRole/Add/Step1">
                        <c:param name="PartySecurityRoleTemplateName" value="${partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                        <c:param name="ParentSecurityRoleGroupName" value="${securityRoleGroup.securityRoleGroupName}" />
                    </c:url>
                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="${addUrl}"><c:out value="${securityRoleGroup.description}" /></a>
                    <c:if test="${securityRoleGroup.securityRoleCount != 0}">
                        <c:url var="addAllUrl" value="/action/Configuration/PartySecurityRoleTemplateRole/Add/Step2">
                            <c:param name="PartySecurityRoleTemplateName" value="${partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                            <c:param name="SecurityRoleGroupName" value="${securityRoleGroup.securityRoleGroupName}" />
                        </c:url>
                        (<a href="${addAllUrl}">add all</a>)
                    </c:if>
                    <br />
                </c:forEach>
            </c:if>
            <c:if test='${securityRoleGroups != null && securityRoles != null}'>
                <br />
            </c:if>
            <c:if test='${securityRoles != null}'>
                Security Roles:<br /><br />
                <c:forEach items="${securityRoles}" var="securityRole">
                    <c:url var="addUrl" value="/action/Configuration/PartySecurityRoleTemplateRole/Add/Step2">
                        <c:param name="PartySecurityRoleTemplateName" value="${partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                        <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                        <c:param name="SecurityRoleName" value="${securityRole.securityRoleName}" />
                    </c:url>
                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="${addUrl}"><c:out value="${securityRole.description}" /></a><br />
                </c:forEach>
                <br />
                <c:url var="addAllUrl" value="/action/Configuration/PartySecurityRoleTemplateRole/Add/Step2">
                    <c:param name="PartySecurityRoleTemplateName" value="${partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                    <c:param name="SecurityRoleGroupName" value="${parentSecurityRoleGroup.securityRoleGroupName}" />
                </c:url>
                &nbsp;&nbsp;&nbsp;&nbsp;<a href="${addAllUrl}">Add all roles.</a>
            </c:if>
        </div>
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
