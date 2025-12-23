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
        <title><fmt:message key="pageTitle.protocols" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <fmt:message key="navigation.protocols" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:Protocol.Create:Protocol.Edit:Protocol.Delete:Protocol.Review:Protocol.Description" />
            <et:hasSecurityRole securityRole="Protocol.Create">
                <p><a href="<c:url value="/action/Configuration/Protocol/Add" />">Add Protocol.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Protocol.Review" var="includeReviewUrl" />
            <display:table name="protocols" id="protocol" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/Protocol/Review">
                                <c:param name="ProtocolName" value="${protocol.protocolName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${protocol.protocolName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${protocol.protocolName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${protocol.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${protocol.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="Protocol.Edit">
                                <c:url var="setDefaultUrl" value="/action/Configuration/Protocol/SetDefault">
                                    <c:param name="ProtocolName" value="${protocol.protocolName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="Protocol.Edit:Protocol.Description:Protocol.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="Protocol.Edit">
                            <c:url var="editUrl" value="/action/Configuration/Protocol/Edit">
                                <c:param name="OriginalProtocolName" value="${protocol.protocolName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Protocol.Description">
                            <c:url var="descriptionsUrl" value="/action/Configuration/Protocol/Description">
                                <c:param name="ProtocolName" value="${protocol.protocolName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Protocol.Delete">
                            <c:url var="deleteUrl" value="/action/Configuration/Protocol/Delete">
                                <c:param name="ProtocolName" value="${protocol.protocolName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${protocol.entityInstance.entityRef}" />
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
