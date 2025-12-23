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
        <title><fmt:message key="pageTitle.securityRoleGroups" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <fmt:message key="navigation.securityRoleGroups" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:SecurityRole.List:SecurityRoleGroup.Create:SecurityRoleGroup.Edit:SecurityRoleGroup.Delete:SecurityRoleGroup.Review:SecurityRoleGroup.Description" />
            <et:hasSecurityRole securityRoles="SecurityRoleGroup.Edit:SecurityRoleGroup.Description:SecurityRoleGroup.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <c:if test="${parentSecurityRoleGroup.securityRoleGroupName != 'ROOT'}">
                <p>
                    <c:url var="parentSecurityRoleGroupsUrl" value="/action/Configuration/SecurityRoleGroup/Main">
                        <c:param name="ParentSecurityRoleGroupName" value="${parentSecurityRoleGroup.parentSecurityRoleGroup.securityRoleGroupName}" />
                    </c:url>
                    <c:choose>
                        <c:when test="${parentSecurityRoleGroup.parentSecurityRoleGroup.securityRoleGroupName == 'ROOT'}">
                            <a href="${parentSecurityRoleGroupsUrl}">Return.</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${parentSecurityRoleGroupsUrl}">Return to <c:out value="${parentSecurityRoleGroup.parentSecurityRoleGroup.description}" />.</a>
                        </c:otherwise>
                    </c:choose>
                </p>
            </c:if>
            <et:hasSecurityRole securityRole="SecurityRoleGroup.Create">
                <c:url var="addUrl" value="/action/Configuration/SecurityRoleGroup/Add">
                    <c:param name="ParentSecurityRoleGroupName" value="${parentSecurityRoleGroup.securityRoleGroupName}" />
                </c:url>
                <p><a href="${addUrl}">Add Security Role Group.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="SecurityRoleGroup.Review" var="includeReviewUrl" />
            <display:table name="securityRoleGroups" id="securityRoleGroup" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/SecurityRoleGroup/Review">
                                <c:param name="SecurityRoleGroupName" value="${securityRoleGroup.securityRoleGroupName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${securityRoleGroup.securityRoleGroupName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${securityRoleGroup.securityRoleGroupName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${securityRoleGroup.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${securityRoleGroup.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="SecurityRoleGroup.Edit">
                                <c:url var="setDefaultUrl" value="/action/Configuration/SecurityRoleGroup/SetDefault">
                                    <c:param name="ParentSecurityRoleGroupName" value="${securityRoleGroup.parentSecurityRoleGroup.securityRoleGroupName}" />
                                    <c:param name="SecurityRoleGroupName" value="${securityRoleGroup.securityRoleGroupName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="securityRoleGroupsUrl" value="/action/Configuration/SecurityRoleGroup/Main">
                        <c:param name="ParentSecurityRoleGroupName" value="${securityRoleGroup.securityRoleGroupName}" />
                    </c:url>
                    <a href="${securityRoleGroupsUrl}"><fmt:message key="navigation.securityRoleGroups" /></a>
                    <et:hasSecurityRole securityRole="SecurityRole.List">
                        <c:url var="securityRolesUrl" value="/action/Configuration/SecurityRole/Main">
                            <c:param name="SecurityRoleGroupName" value="${securityRoleGroup.securityRoleGroupName}" />
                        </c:url>
                        <a href="${securityRolesUrl}"><fmt:message key="navigation.securityRoles" /></a>
                    </et:hasSecurityRole>
                    <c:if test="${linksInSecondRow}">
                        <br />
                    </c:if>
                    <et:hasSecurityRole securityRole="SecurityRoleGroup.Edit">
                        <c:url var="editUrl" value="/action/Configuration/SecurityRoleGroup/Edit">
                            <c:param name="ParentSecurityRoleGroupName" value="${securityRoleGroup.parentSecurityRoleGroup.securityRoleGroupName}" />
                            <c:param name="OriginalSecurityRoleGroupName" value="${securityRoleGroup.securityRoleGroupName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="SecurityRoleGroup.Description">
                        <c:url var="descriptionsUrl" value="/action/Configuration/SecurityRoleGroup/Description">
                            <c:param name="SecurityRoleGroupName" value="${securityRoleGroup.securityRoleGroupName}" />
                        </c:url>
                        <a href="${descriptionsUrl}">Descriptions</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="SecurityRoleGroup.Delete">
                        <c:url var="deleteUrl" value="/action/Configuration/SecurityRoleGroup/Delete">
                            <c:param name="ParentSecurityRoleGroupName" value="${securityRoleGroup.parentSecurityRoleGroup.securityRoleGroupName}" />
                            <c:param name="SecurityRoleGroupName" value="${securityRoleGroup.securityRoleGroupName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </et:hasSecurityRole>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${securityRoleGroup.entityInstance.entityRef}" />
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
