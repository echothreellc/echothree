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
        <title><fmt:message key="pageTitle.chainActionSets" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/Main" />"><fmt:message key="navigation.chains" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/ChainKind/Main" />"><fmt:message key="navigation.chainKinds" /></a> &gt;&gt;
                <c:url var="chainTypesUrl" value="/action/Chain/ChainType/Main">
                    <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}"><fmt:message key="navigation.chainTypes" /></a> &gt;&gt;
                <c:url var="chainsUrl" value="/action/Chain/Chain/Main">
                    <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chain.chainType.chainTypeName}" />
                </c:url>
                <a href="${chainsUrl}"><fmt:message key="navigation.chains" /></a> &gt;&gt;
                <fmt:message key="navigation.chainActionSets" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ChainActionSet.Create:ChainActionSet.Edit:ChainActionSet.Delete:ChainActionSet.Review:ChainActionSet.Description:ChainAction.List" />
            <et:hasSecurityRole securityRoles="ChainAction.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="ChainActionSet.Edit:ChainActionSet.Description:ChainActionSet.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ChainActionSet.Create">
                <c:url var="addUrl" value="/action/Chain/ChainActionSet/Add">
                    <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chain.chainType.chainTypeName}" />
                    <c:param name="ChainName" value="${chain.chainName}" />
                </c:url>
                <p><a href="${addUrl}">Add Chain Action Set.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ChainActionSet.Review" var="includeReviewUrl" />
            <display:table name="chainActionSets" id="chainActionSet" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Chain/ChainActionSet/Review">
                                <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chainActionSet.chain.chainType.chainTypeName}" />
                                <c:param name="ChainName" value="${chainActionSet.chain.chainName}" />
                                <c:param name="ChainActionSetName" value="${chainActionSet.chainActionSetName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${chainActionSet.chainActionSetName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${chainActionSet.chainActionSetName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${chainActionSet.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${chainActionSet.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ChainActionSet.Edit">
                                <c:url var="setDefaultUrl" value="/action/Chain/ChainActionSet/SetDefault">
                                    <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                                    <c:param name="ChainTypeName" value="${chainActionSet.chain.chainType.chainTypeName}" />
                                    <c:param name="ChainName" value="${chainActionSet.chain.chainName}" />
                                    <c:param name="ChainActionSetName" value="${chainActionSet.chainActionSetName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="ChainAction.List">
                            <c:url var="chainActionsUrl" value="/action/Chain/ChainAction/Main">
                                <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chainActionSet.chain.chainType.chainTypeName}" />
                                <c:param name="ChainName" value="${chainActionSet.chain.chainName}" />
                                <c:param name="ChainActionSetName" value="${chainActionSet.chainActionSetName}" />
                            </c:url>
                        </et:hasSecurityRole>
                        <a href="${chainActionsUrl}">Chain Actions</a>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="ChainActionSet.Edit">
                            <c:url var="editUrl" value="/action/Chain/ChainActionSet/Edit">
                                <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chainActionSet.chain.chainType.chainTypeName}" />
                                <c:param name="ChainName" value="${chainActionSet.chain.chainName}" />
                                <c:param name="OriginalChainActionSetName" value="${chainActionSet.chainActionSetName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ChainActionSet.Description">
                            <c:url var="descriptionsUrl" value="/action/Chain/ChainActionSet/Description">
                                <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chainActionSet.chain.chainType.chainTypeName}" />
                                <c:param name="ChainName" value="${chainActionSet.chain.chainName}" />
                                <c:param name="ChainActionSetName" value="${chainActionSet.chainActionSetName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ChainActionSet.Delete">
                            <c:url var="deleteUrl" value="/action/Chain/ChainActionSet/Delete">
                                <c:param name="ChainKindName" value="${chainActionSet.chain.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chainActionSet.chain.chainType.chainTypeName}" />
                                <c:param name="ChainName" value="${chainActionSet.chain.chainName}" />
                                <c:param name="ChainActionSetName" value="${chainActionSet.chainActionSetName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:if test='${chainActionSet.entityInstance.entityTime.createdTime != null}'>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${chainActionSet.entityInstance.entityRef}" />
                            </c:url>
                            <a href="${eventsUrl}">Events</a>
                        </c:if>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
