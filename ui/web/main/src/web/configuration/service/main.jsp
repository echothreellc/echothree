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
        <title><fmt:message key="pageTitle.services" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <fmt:message key="navigation.services" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:Service.Create:Service.Edit:Service.Delete:Service.Review:Protocol.Review:Service.Description" />
            <et:hasSecurityRole securityRole="Service.Create">
                <p><a href="<c:url value="/action/Configuration/Service/Add" />">Add Service.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Service.Review" var="includeServiceReviewUrl" />
            <et:hasSecurityRole securityRole="Protocol.Review" var="includeProtocolReviewUrl" />
            <display:table name="services" id="service" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeServiceReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/Service/Review">
                                <c:param name="ServiceName" value="${service.serviceName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${service.serviceName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${service.serviceName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${service.description}" />
                </display:column>
                <display:column property="port" titleKey="columnTitle.port" />
                <display:column titleKey="columnTitle.protocol">
                    <c:choose>
                        <c:when test="${includeProtocolReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/Protocol/Review">
                                <c:param name="ProtocolName" value="${service.protocol.protocolName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${service.protocol.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${service.protocol.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${service.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="Service.Edit">
                                <c:url var="setDefaultUrl" value="/action/Configuration/Service/SetDefault">
                                    <c:param name="ServiceName" value="${service.serviceName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="Service.Edit:Service.Description:Service.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="Service.Edit">
                            <c:url var="editUrl" value="/action/Configuration/Service/Edit">
                                <c:param name="OriginalServiceName" value="${service.serviceName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Service.Description">
                            <c:url var="descriptionsUrl" value="/action/Configuration/Service/Description">
                                <c:param name="ServiceName" value="${service.serviceName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Service.Delete">
                            <c:url var="deleteUrl" value="/action/Configuration/Service/Delete">
                                <c:param name="ServiceName" value="${service.serviceName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${service.entityInstance.entityRef}" />
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
