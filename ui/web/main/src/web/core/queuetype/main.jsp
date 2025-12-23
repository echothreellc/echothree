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
        <title><fmt:message key="pageTitle.queueTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <fmt:message key="navigation.queueTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:QueueType.Create:QueueType.Edit:QueueType.Delete:QueueType.Review:QueueType.Description" />
            <et:hasSecurityRole securityRole="QueueType.Create">
                <p><a href="<c:url value="/action/Core/QueueType/Add" />">Add Queue Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="QueueType.Review" var="includeReviewUrl" />
            <display:table name="queueTypes" id="queueType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/QueueType/Review">
                                <c:param name="QueueTypeName" value="${queueType.queueTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${queueType.queueTypeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${queueType.queueTypeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${queueType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column property="queuedEntityCount" titleKey="columnTitle.queuedEntityCount" />
                <display:column property="oldestQueuedEntityTime" titleKey="columnTitle.oldestQueuedEntityTime" />
                <display:column property="latestQueuedEntityTime" titleKey="columnTitle.latestQueuedEntityTime" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${queueType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="QueueType.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/QueueType/SetDefault">
                                    <c:param name="QueueTypeName" value="${queueType.queueTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="QueueType.Edit:QueueType.Description:QueueType.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="QueueType.Edit">
                            <c:url var="editUrl" value="/action/Core/QueueType/Edit">
                                <c:param name="OriginalQueueTypeName" value="${queueType.queueTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="QueueType.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/QueueType/Description">
                                <c:param name="QueueTypeName" value="${queueType.queueTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="QueueType.Delete">
                            <c:url var="deleteUrl" value="/action/Core/QueueType/Delete">
                                <c:param name="QueueTypeName" value="${queueType.queueTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${queueType.entityInstance.entityRef}" />
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
