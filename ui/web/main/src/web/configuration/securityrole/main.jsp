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
        <title><fmt:message key="pageTitle.securityRoles" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <c:url var="securityRoleGroupsUrl" value="/action/Configuration/SecurityRoleGroup/Main">
                    <c:param name="ParentSecurityRoleGroupName" value="${securityRoleGroup.parentSecurityRoleGroup.securityRoleGroupName}" />
                </c:url>
                <a href="${securityRoleGroupsUrl}"><fmt:message key="navigation.securityRoleGroups" /></a> &gt;&gt;
                <fmt:message key="navigation.securityRoles" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:SecurityRolePartyType.List:SecurityRole.Create:SecurityRole.Edit:SecurityRole.Delete:SecurityRole.Review:SecurityRole.Description" />
            <et:hasSecurityRole securityRoles="SecurityRolePartyType.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="SecurityRole.Edit:SecurityRole.Description:SecurityRole.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="SecurityRole.Create">
                <c:url var="addUrl" value="/action/Configuration/SecurityRole/Add">
                    <c:param name="SecurityRoleGroupName" value="${securityRoleGroup.securityRoleGroupName}" />
                </c:url>
                <p><a href="${addUrl}">Add Security Role.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="SecurityRole.Review" var="includeReviewUrl" />
            <display:table name="securityRoles" id="securityRole" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/SecurityRole/Review">
                                <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                                <c:param name="SecurityRoleName" value="${securityRole.securityRoleName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${securityRole.securityRoleName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${securityRole.securityRoleName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${securityRole.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${securityRole.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="SecurityRole.Edit">
                                <c:url var="setDefaultUrl" value="/action/Configuration/SecurityRole/SetDefault">
                                    <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                                    <c:param name="SecurityRoleName" value="${securityRole.securityRoleName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <et:hasSecurityRole securityRole="SecurityRolePartyType.List">
                        <c:url var="securityRolePartyTypesUrl" value="/action/Configuration/SecurityRolePartyType/Main">
                            <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                            <c:param name="SecurityRoleName" value="${securityRole.securityRoleName}" />
                        </c:url>
                        <a href="${securityRolePartyTypesUrl}"><fmt:message key="navigation.securityRolePartyTypes" /></a>
                    </et:hasSecurityRole>
                    <c:if test="${linksInFirstRow && linksInSecondRow}">
                        <br />
                    </c:if>
                    <et:hasSecurityRole securityRole="SecurityRole.Edit">
                        <c:url var="editUrl" value="/action/Configuration/SecurityRole/Edit">
                            <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                            <c:param name="OriginalSecurityRoleName" value="${securityRole.securityRoleName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="SecurityRole.Description">
                        <c:url var="descriptionsUrl" value="/action/Configuration/SecurityRole/Description">
                            <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                            <c:param name="SecurityRoleName" value="${securityRole.securityRoleName}" />
                        </c:url>
                        <a href="${descriptionsUrl}">Descriptions</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="SecurityRole.Delete">
                        <c:url var="deleteUrl" value="/action/Configuration/SecurityRole/Delete">
                            <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                            <c:param name="SecurityRoleName" value="${securityRole.securityRoleName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </et:hasSecurityRole>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${securityRole.entityInstance.entityRef}" />
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
