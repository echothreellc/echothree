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
        <title>Command Messages</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/CommandMessageType/Main" />">Command Message Types</a> &gt;&gt;
                Command Messages
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:CommandMessage.Create:CommandMessage.Edit:CommandMessage.Delete:CommandMessage.Review:CommandMessage.Translation" />
            <et:hasSecurityRole securityRoles="CommandMessage.Edit:CommandMessage.Translation:CommandMessage.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="CommandMessage.Create">
                <c:url var="addUrl" value="/action/Core/CommandMessage/Add">
                    <c:param name="CommandMessageTypeName" value="${commandMessageType.commandMessageTypeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Command Message.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="CommandMessage.Review" var="includeReviewUrl" />
            <display:table name="commandMessages" id="commandMessage" class="displaytag">
                <display:column titleKey="columnTitle.key">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/CommandMessage/Review">
                                <c:param name="CommandMessageTypeName" value="${commandMessage.commandMessageType.commandMessageTypeName}" />
                                <c:param name="CommandMessageKey" value="${commandMessage.commandMessageKey}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${commandMessage.commandMessageKey}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${commandMessage.commandMessageKey}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.translation">
                    <c:out value="${commandMessage.translation}" />
                </display:column>
                <c:if test="${linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="CommandMessage.Edit">
                            <c:url var="editUrl" value="/action/Core/CommandMessage/Edit">
                                <c:param name="CommandMessageTypeName" value="${commandMessage.commandMessageType.commandMessageTypeName}" />
                                <c:param name="OriginalCommandMessageKey" value="${commandMessage.commandMessageKey}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="CommandMessage.Translation">
                            <c:url var="translationsUrl" value="/action/Core/CommandMessage/Translation">
                                <c:param name="CommandMessageTypeName" value="${commandMessage.commandMessageType.commandMessageTypeName}" />
                                <c:param name="CommandMessageKey" value="${commandMessage.commandMessageKey}" />
                            </c:url>
                            <a href="${translationsUrl}">Translations</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="CommandMessage.Delete">
                            <c:url var="deleteUrl" value="/action/Core/CommandMessage/Delete">
                                <c:param name="CommandMessageTypeName" value="${commandMessage.commandMessageType.commandMessageTypeName}" />
                                <c:param name="CommandMessageKey" value="${commandMessage.commandMessageKey}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${commandMessage.entityInstance.entityRef}" />
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
