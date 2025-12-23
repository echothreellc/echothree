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
        <title>Party Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                Party Types
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:PartyTypePasswordStringPolicy.List:PartyTypeLockoutPolicy.List:PartyTypeAuditPolicy.List:PartyAliasType.List" />
            <display:table name="partyTypes" id="partyType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Configuration/PartyType/Review">
                        <c:param name="PartyTypeName" value="${partyType.partyTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${partyType.partyTypeName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.parent">
                    <c:out value="${partyType.parentPartyType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${partyType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:if test="${partyType.isDefault}">
                        Default
                    </c:if>
                </display:column>
                <et:hasSecurityRole securityRole="PartyTypeAuditPolicy.List">
                    <display:column titleKey="columnTitle.auditPolicy">
                        <c:if test="${partyType.allowUserLogins}">
                            <c:choose>
                                <c:when test="${partyType.partyTypeAuditPolicy == null}">
                                    <c:url var="createUrl" value="/action/Configuration/PartyTypeAuditPolicy/Add">
                                        <c:param name="PartyTypeName" value="${partyType.partyTypeName}" />
                                    </c:url>
                                    <a href="${createUrl}">Create</a>
                                </c:when>
                                <c:otherwise>
                                    <c:url var="reviewUrl" value="/action/Configuration/PartyTypeAuditPolicy/Review">
                                        <c:param name="PartyTypeName" value="${partyType.partyTypeName}" />
                                    </c:url>
                                    <a href="${reviewUrl}">Review</a>
                                    <c:url var="editUrl" value="/action/Configuration/PartyTypeAuditPolicy/Edit">
                                        <c:param name="PartyTypeName" value="${partyType.partyTypeName}" />
                                    </c:url>
                                    <a href="${editUrl}">Edit</a>
                                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                        <c:param name="EntityRef" value="${partyType.partyTypeAuditPolicy.entityInstance.entityRef}" />
                                    </c:url>
                                    <a href="${eventsUrl}">Events</a>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="PartyTypePasswordStringPolicy.List">
                    <display:column titleKey="columnTitle.passwordPolicy">
                        <c:if test="${partyType.allowUserLogins}">
                            <c:choose>
                                <c:when test="${partyType.partyTypePasswordStringPolicy == null}">
                                    <c:url var="createUrl" value="/action/Configuration/PartyTypePasswordStringPolicy/Add">
                                        <c:param name="PartyTypeName" value="${partyType.partyTypeName}" />
                                    </c:url>
                                    <a href="${createUrl}">Create</a>
                                </c:when>
                                <c:otherwise>
                                    <c:url var="reviewUrl" value="/action/Configuration/PartyTypePasswordStringPolicy/Review">
                                        <c:param name="PartyTypeName" value="${partyType.partyTypeName}" />
                                    </c:url>
                                    <a href="${reviewUrl}">Review</a>
                                    <c:url var="editUrl" value="/action/Configuration/PartyTypePasswordStringPolicy/Edit">
                                        <c:param name="PartyTypeName" value="${partyType.partyTypeName}" />
                                    </c:url>
                                    <a href="${editUrl}">Edit</a>
                                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                        <c:param name="EntityRef" value="${partyType.partyTypePasswordStringPolicy.entityInstance.entityRef}" />
                                    </c:url>
                                    <a href="${eventsUrl}">Events</a>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="PartyTypeLockoutPolicy.List">
                    <display:column titleKey="columnTitle.lockoutPolicy">
                        <c:if test="${partyType.allowUserLogins}">
                            <c:choose>
                                <c:when test="${partyType.partyTypeLockoutPolicy == null}">
                                    <c:url var="createUrl" value="/action/Configuration/PartyTypeLockoutPolicy/Add">
                                        <c:param name="PartyTypeName" value="${partyType.partyTypeName}" />
                                    </c:url>
                                    <a href="${createUrl}">Create</a>
                                </c:when>
                                <c:otherwise>
                                    <c:url var="reviewUrl" value="/action/Configuration/PartyTypeLockoutPolicy/Review">
                                        <c:param name="PartyTypeName" value="${partyType.partyTypeName}" />
                                    </c:url>
                                    <a href="${reviewUrl}">Review</a>
                                    <c:url var="editUrl" value="/action/Configuration/PartyTypeLockoutPolicy/Edit">
                                        <c:param name="PartyTypeName" value="${partyType.partyTypeName}" />
                                    </c:url>
                                    <a href="${editUrl}">Edit</a>
                                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                        <c:param name="EntityRef" value="${partyType.partyTypeLockoutPolicy.entityInstance.entityRef}" />
                                    </c:url>
                                    <a href="${eventsUrl}">Events</a>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="PartyAliasType.List">
                    <display:column>
                        <c:if test="${partyType.allowPartyAliases}">
                            <c:url var="partyAliasTypesUrl" value="/action/Configuration/PartyAliasType/Main">
                                <c:param name="PartyTypeName" value="${partyType.partyTypeName}" />
                            </c:url>
                            <a href="${partyAliasTypesUrl}">Alias Types</a>
                        </c:if>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
