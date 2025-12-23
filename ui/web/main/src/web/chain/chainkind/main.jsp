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
        <title><fmt:message key="pageTitle.chainKinds" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/Main" />"><fmt:message key="navigation.chains" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/ChainKind/Main" />"><fmt:message key="navigation.chainKinds" /></a>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ChainKind.Create:ChainKind.Edit:ChainKind.Delete:ChainKind.Review:ChainKind.Description:ChainType.List" />
            <et:hasSecurityRole securityRoles="ChainType.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="ChainKind.Edit:ChainKind.Description:ChainKind.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ChainKind.Create">
                <c:url var="addUrl" value="/action/Chain/ChainKind/Add" />
                <p><a href="${addUrl}">Add Chain Kind.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ChainKind.Review" var="includeReviewUrl" />
            <display:table name="chainKinds" id="chainKind" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Chain/ChainKind/Review">
                                <c:param name="ChainKindName" value="${chainKind.chainKindName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${chainKind.chainKindName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${chainKind.chainKindName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${chainKind.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${chainKind.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ChainKind.Edit">
                                <c:url var="setDefaultUrl" value="/action/Chain/ChainKind/SetDefault">
                                    <c:param name="ChainKindName" value="${chainKind.chainKindName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="ChainType.List">
                            <c:url var="chainTypesUrl" value="/action/Chain/ChainType/Main">
                                <c:param name="ChainKindName" value="${chainKind.chainKindName}" />
                            </c:url>
                        </et:hasSecurityRole>
                        <a href="${chainTypesUrl}">Chain Types</a>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="ChainKind.Edit">
                            <c:url var="editUrl" value="/action/Chain/ChainKind/Edit">
                                <c:param name="OriginalChainKindName" value="${chainKind.chainKindName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ChainKind.Description">
                            <c:url var="descriptionsUrl" value="/action/Chain/ChainKind/Description">
                                <c:param name="ChainKindName" value="${chainKind.chainKindName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ChainKind.Delete">
                            <c:url var="deleteUrl" value="/action/Chain/ChainKind/Delete">
                                <c:param name="ChainKindName" value="${chainKind.chainKindName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:if test='${chainKind.entityInstance.entityTime.createdTime != null}'>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${chainKind.entityInstance.entityRef}" />
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
