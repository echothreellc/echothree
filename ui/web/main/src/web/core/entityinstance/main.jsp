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
        <title><fmt:message key="pageTitle.entityInstances" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />"><fmt:message key="navigation.componentVendors" /></a> &gt;&gt;
                <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                    <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}"><fmt:message key="navigation.entityTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.entityInstances" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="EntityInstance.Review:EntityInstance.Create:EntityInstance.Delete:EntityInstance.Remove:Event.Send" />
            <et:hasSecurityRole securityRole="EntityInstance.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRoles="EntityInstance.Delete:EntityInstance.Remove:Event.Send">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityInstance.Create">
                <c:if test="${entityType.componentVendor.componentVendorName != 'ECHO_THREE'}">
                    <c:url var="createUrl" value="/action/Core/EntityInstance/Create">
                        <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                    </c:url>
                    <p><a href="${createUrl}">Create Entity Instance.</a></p>
                </c:if>
            </et:hasSecurityRole>
            <display:table name="entityInstances" id="entityInstance" class="displaytag">
                <display:column titleKey="columnTitle.entity">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/EntityInstance/Review">
                                <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                            </c:url>
                            <a href="${reviewUrl}"> <et:appearance appearance="${entityInstance.entityAppearance.appearance}"><c:out value="${entityInstance.entityRef}" /></et:appearance></a>
                        </c:when>
                        <c:otherwise>
                            <et:appearance appearance="${entityInstance.entityAppearance.appearance}"><c:out value="${entityInstance.entityRef}" /></et:appearance>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${entityInstance.description}" />
                </display:column>
                <display:column titleKey="columnTitle.created">
                    <c:out value="${entityInstance.entityTime.createdTime}" />
                </display:column>
                <display:column titleKey="columnTitle.modified">
                    <c:out value="${entityInstance.entityTime.modifiedTime}" />
                </display:column>
                <display:column titleKey="columnTitle.deleted">
                    <c:out value="${entityInstance.entityTime.deletedTime}" />
                </display:column>
                <c:if test="${linksInFirstRow && entityInstance.entityType.componentVendor.componentVendorName != 'ECHO_THREE'}">
                    <display:column>
                        <c:if test="${entityInstance.entityTime.deletedTime == null}">
                            <et:hasSecurityRole securityRole="Event.Send">
                                <c:url var="sendUrl" value="/action/Core/Event/Send">
                                    <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                </c:url>
                                <a href="${sendUrl}">Send Event</a>
                            </et:hasSecurityRole>
                            <et:hasSecurityRole securityRole="EntityInstance.Delete">
                                <c:url var="deleteUrl" value="/action/Core/EntityInstance/Delete">
                                    <c:param name="ComponentVendorName" value="${entityInstance.entityType.componentVendor.componentVendorName}" />
                                    <c:param name="EntityTypeName" value="${entityInstance.entityType.entityTypeName}" />
                                    <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                </c:url>
                                <a href="${deleteUrl}">Delete</a>
                            </et:hasSecurityRole>
                        </c:if>
                        <et:hasSecurityRole securityRole="EntityInstance.Remove">
                            <c:url var="deleteUrl" value="/action/Core/EntityInstance/Remove">
                                <c:param name="ComponentVendorName" value="${entityInstance.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityInstance.entityType.entityTypeName}" />
                                <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                            </c:url>
                            <a href="${deleteUrl}">Remove</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <display:column>
                    <c:if test="${entityInstance.entityTime != null}">
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </c:if>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
