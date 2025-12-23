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
        <title><fmt:message key="pageTitle.chainTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/Main" />"><fmt:message key="navigation.chains" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/ChainKind/Main" />"><fmt:message key="navigation.chainKinds" /></a> &gt;&gt;
                <fmt:message key="navigation.chainTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ChainType.Create:ChainType.Edit:ChainType.Delete:ChainType.Review:ChainType.Description:Chain.List" />
            <et:hasSecurityRole securityRoles="Chain.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="ChainType.Edit:ChainType.Description:ChainType.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ChainType.Create">
                <c:url var="addUrl" value="/action/Chain/ChainType/Add">
                    <c:param name="ChainKindName" value="${chainKind.chainKindName}" />
                </c:url>
                <p><a href="${addUrl}">Add Chain Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ChainType.Review" var="includeReviewUrl" />
            <display:table name="chainTypes" id="chainType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Chain/ChainType/Review">
                                <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chainType.chainTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${chainType.chainTypeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${chainType.chainTypeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${chainType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${chainType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ChainType.Edit">
                                <c:url var="setDefaultUrl" value="/action/Chain/ChainType/SetDefault">
                                    <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                                    <c:param name="ChainTypeName" value="${chainType.chainTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="Chain.List">
                            <c:url var="chainActionsUrl" value="/action/Chain/Chain/Main">
                                <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chainType.chainTypeName}" />
                            </c:url>
                        </et:hasSecurityRole>
                        <a href="${chainActionsUrl}">Chains</a>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="ChainType.Edit">
                            <c:url var="editUrl" value="/action/Chain/ChainType/Edit">
                                <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                                <c:param name="OriginalChainTypeName" value="${chainType.chainTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ChainType.Description">
                            <c:url var="descriptionsUrl" value="/action/Chain/ChainType/Description">
                                <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chainType.chainTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ChainType.Delete">
                            <c:url var="deleteUrl" value="/action/Chain/ChainType/Delete">
                                <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chainType.chainTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:if test='${chainType.entityInstance.entityTime.createdTime != null}'>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${chainType.entityInstance.entityRef}" />
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
