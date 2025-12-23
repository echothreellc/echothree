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
        <title>Security Role Templates</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                Security Role Templates
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Configuration/PartySecurityRoleTemplate/Add" />">Add Security Role Template.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="partySecurityRoleTemplates" id="partySecurityRoleTemplate" class="displaytag">
                <display:column property="partySecurityRoleTemplateName" titleKey="columnTitle.name" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${partySecurityRoleTemplate.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${partySecurityRoleTemplate.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Configuration/PartySecurityRoleTemplate/SetDefault">
                                <c:param name="PartySecurityRoleTemplateName" value="${partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="partySecurityRoleTemplateRolesUrl" value="/action/Configuration/PartySecurityRoleTemplateRole/Main">
                        <c:param name="PartySecurityRoleTemplateName" value="${partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                    </c:url>
                    <a href="${partySecurityRoleTemplateRolesUrl}">Roles</a>
                    <c:url var="partySecurityRoleTemplateTrainingClassesUrl" value="/action/Configuration/PartySecurityRoleTemplateTrainingClass/Main">
                        <c:param name="PartySecurityRoleTemplateName" value="${partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                    </c:url>
                    <a href="${partySecurityRoleTemplateTrainingClassesUrl}">Training Classes</a><br />
                    <c:url var="editUrl" value="/action/Configuration/PartySecurityRoleTemplate/Edit">
                        <c:param name="OriginalPartySecurityRoleTemplateName" value="${partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/PartySecurityRoleTemplate/Description">
                        <c:param name="PartySecurityRoleTemplateName" value="${partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/PartySecurityRoleTemplate/Delete">
                        <c:param name="PartySecurityRoleTemplateName" value="${partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${partySecurityRoleTemplate.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
