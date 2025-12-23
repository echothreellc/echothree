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
        <title>Scales</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                Scales
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Configuration/Scale/Add" />">Add Scale.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List:ScaleType.Review:Server.Review:Service.Review" />
            <et:hasSecurityRole securityRole="ScaleType.Review" var="includeScaleTypeReviewUrl" />
            <et:hasSecurityRole securityRole="Server.Review" var="includeServerReviewUrl" />
            <et:hasSecurityRole securityRole="Service.Review" var="includeServiceReviewUrl" />
            <display:table name="scales" id="scale" class="displaytag">
                <display:column property="scaleName" titleKey="columnTitle.name" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${scale.description}" />
                </display:column>
                <display:column titleKey="columnTitle.scaleType">
                    <c:choose>
                        <c:when test="${includeScaleTypeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/ScaleType/Review">
                                <c:param name="ScaleTypeName" value="${scale.scaleType.scaleTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${scale.scaleType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${service.scaleType.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.server">
                    <c:choose>
                        <c:when test="${includeServerReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/Server/Review">
                                <c:param name="ServerName" value="${scale.serverService.server.serverName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${scale.serverService.server.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${service.server.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.service">
                    <c:choose>
                        <c:when test="${includeServiceReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/Service/Review">
                                <c:param name="ServiceName" value="${scale.serverService.service.serviceName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${scale.serverService.service.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${service.service.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${scale.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Configuration/Scale/SetDefault">
                                <c:param name="ScaleName" value="${scale.scaleName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/Scale/Edit">
                        <c:param name="OriginalScaleName" value="${scale.scaleName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/Scale/Description">
                        <c:param name="ScaleName" value="${scale.scaleName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/Scale/Delete">
                        <c:param name="ScaleName" value="${scale.scaleName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${scale.entityInstance.entityRef}" />
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
