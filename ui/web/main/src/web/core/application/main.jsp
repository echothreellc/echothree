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
        <title><fmt:message key="pageTitle.applications" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <fmt:message key="navigation.applications" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:Application.Create:Application.Edit:Application.Delete:Application.Review:Application.Description:ApplicationEditor.List:ApplicationEditorUse.List" />
            <et:hasSecurityRole securityRole="Application.Create">
                <p><a href="<c:url value="/action/Core/Application/Add" />">Add Application.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Application.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRoles="ApplicationEditor.List:ApplicationEditorUse.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="Application.Edit:Application.Description:Application.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <display:table name="applications" id="application" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/Application/Review">
                                <c:param name="ApplicationName" value="${application.applicationName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${application.applicationName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${application.applicationName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${application.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${application.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="Application.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/Application/SetDefault">
                                    <c:param name="ApplicationName" value="${application.applicationName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="ApplicationEditor.List">
                            <c:url var="applicationEditorsUrl" value="/action/Core/ApplicationEditor/Main">
                                <c:param name="ApplicationName" value="${application.applicationName}" />
                            </c:url>
                            <a href="${applicationEditorsUrl}">Editors</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ApplicationEditorUse.List">
                            <c:url var="applicationEditorUsesUrl" value="/action/Core/ApplicationEditorUse/Main">
                                <c:param name="ApplicationName" value="${application.applicationName}" />
                            </c:url>
                            <a href="${applicationEditorUsesUrl}">Editor Uses</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="Application.Edit">
                            <c:url var="editUrl" value="/action/Core/Application/Edit">
                                <c:param name="OriginalApplicationName" value="${application.applicationName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Application.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/Application/Description">
                                <c:param name="ApplicationName" value="${application.applicationName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Application.Delete">
                            <c:url var="deleteUrl" value="/action/Core/Application/Delete">
                                <c:param name="ApplicationName" value="${application.applicationName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${application.entityInstance.entityRef}" />
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
