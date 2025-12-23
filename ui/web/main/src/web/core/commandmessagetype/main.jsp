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
        <title><fmt:message key="pageTitle.commandMessageTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <fmt:message key="navigation.commandMessageTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:CommandMessageType.Create:CommandMessageType.Edit:CommandMessageType.Delete:CommandMessageType.Review:CommandMessageType.Description:CommandMessage.List" />
            <et:hasSecurityRole securityRoles="CommandMessage.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="CommandMessageType.Edit:CommandMessageType.Description:CommandMessageType.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="CommandMessageType.Create">
                <p><a href="<c:url value="/action/Core/CommandMessageType/Add" />">Add Command Message Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="CommandMessageType.Review" var="includeReviewUrl" />
            <display:table name="commandMessageTypes" id="commandMessageType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/CommandMessageType/Review">
                                <c:param name="CommandMessageTypeName" value="${commandMessageType.commandMessageTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${commandMessageType.commandMessageTypeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${commandMessageType.commandMessageTypeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${commandMessageType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${commandMessageType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="CommandMessageType.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/CommandMessageType/SetDefault">
                                    <c:param name="CommandMessageTypeName" value="${commandMessageType.commandMessageTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="CommandMessage.List:CommandMessageType.Edit:CommandMessageType.Description:CommandMessageType.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="CommandMessage.List">
                            <c:url var="commandMessageUrl" value="/action/Core/CommandMessage/Main">
                                <c:param name="CommandMessageTypeName" value="${commandMessageType.commandMessageTypeName}" />
                            </c:url>
                            <a href="${commandMessageUrl}">Messages</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="CommandMessageType.Edit">
                            <c:url var="editUrl" value="/action/Core/CommandMessageType/Edit">
                                <c:param name="OriginalCommandMessageTypeName" value="${commandMessageType.commandMessageTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="CommandMessageType.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/CommandMessageType/Description">
                                <c:param name="CommandMessageTypeName" value="${commandMessageType.commandMessageTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="CommandMessageType.Delete">
                            <c:url var="deleteUrl" value="/action/Core/CommandMessageType/Delete">
                                <c:param name="CommandMessageTypeName" value="${commandMessageType.commandMessageTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${commandMessageType.entityInstance.entityRef}" />
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
