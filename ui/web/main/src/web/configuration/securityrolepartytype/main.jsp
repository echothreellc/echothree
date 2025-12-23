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
                    <c:param name="ParentSecurityRoleGroupName" value="${securityRole.securityRoleGroup.parentSecurityRoleGroup.securityRoleGroupName}" />
                </c:url>
                <a href="${securityRoleGroupsUrl}"><fmt:message key="navigation.securityRoleGroups" /></a> &gt;&gt;
                <c:url var="securityRolesUrl" value="/action/Configuration/SecurityRole/Main">
                    <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                </c:url>
                <a href="${securityRolesUrl}"><fmt:message key="navigation.securityRoles" /></a> &gt;&gt;
                <fmt:message key="navigation.securityRolePartyTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="PartyType.Review:Selector.Review:SecurityRolePartyType.Create:SecurityRolePartyType.Edit:SecurityRolePartyType.Delete" />
            <et:hasSecurityRole securityRole="SecurityRolePartyType.Create">
                <c:url var="addUrl" value="/action/Configuration/SecurityRolePartyType/Add/Step1">
                    <c:param name="SecurityRoleGroupName" value="${securityRole.securityRoleGroup.securityRoleGroupName}" />
                    <c:param name="SecurityRoleName" value="${securityRole.securityRoleName}" />
                </c:url>
                <p><a href="${addUrl}">Add Security Role Party Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="PartyType.Review" var="includePartyTypeReviewUrl" />
            <et:hasSecurityRole securityRole="Selector.Review" var="includeSelectorReviewUrl" />
            <display:table name="securityRolePartyTypes" id="securityRolePartyType" class="displaytag">
                <display:column titleKey="columnTitle.partyType">
                    <c:choose>
                        <c:when test="${includePartyTypeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/PartyType/Review">
                                <c:param name="PartyTypeName" value="${securityRolePartyType.partyType.partyTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${securityRolePartyType.partyType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${securityRolePartyType.partyType.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.partySelector">
                    <c:choose>
                        <c:when test="${includeSelectorReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Selector/Selector/Review">
                                <c:param name="SelectorKindName" value="${securityRolePartyType.partySelector.selectorType.selectorKind.selectorKindName}" />
                                <c:param name="SelectorTypeName" value="${securityRolePartyType.partySelector.selectorType.selectorTypeName}" />
                                <c:param name="SelectorName" value="${securityRolePartyType.partySelector.selectorName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${securityRolePartyType.partySelector.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${securityRolePartyType.partySelector.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="SecurityRolePartyType.Edit:SecurityRolePartyType.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="SecurityRolePartyType.Edit">
                            <c:url var="editUrl" value="/action/Configuration/SecurityRolePartyType/Edit">
                                <c:param name="SecurityRoleGroupName" value="${securityRolePartyType.securityRole.securityRoleGroup.securityRoleGroupName}" />
                                <c:param name="SecurityRoleName" value="${securityRolePartyType.securityRole.securityRoleName}" />
                                <c:param name="PartyTypeName" value="${securityRolePartyType.partyType.partyTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="SecurityRolePartyType.Delete">
                            <c:url var="deleteUrl" value="/action/Configuration/SecurityRolePartyType/Delete">
                                <c:param name="SecurityRoleGroupName" value="${securityRolePartyType.securityRole.securityRoleGroup.securityRoleGroupName}" />
                                <c:param name="SecurityRoleName" value="${securityRolePartyType.securityRole.securityRoleName}" />
                                <c:param name="PartyTypeName" value="${securityRolePartyType.partyType.partyTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
