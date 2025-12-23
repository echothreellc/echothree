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
        <title><fmt:message key="pageTitle.applicationEditorUses" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Application/Main" />"><fmt:message key="navigation.applications" /></a> &gt;&gt;
                <fmt:message key="navigation.applicationEditorUses" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ApplicationEditorUse.Create:ApplicationEditorUse.Edit:ApplicationEditorUse.Delete:ApplicationEditorUse.Review:ApplicationEditorUse.Description:ApplicationEditor.Review" />
            <et:hasSecurityRole securityRole="ApplicationEditorUse.Create">
                <c:url var="addUrl" value="/action/Core/ApplicationEditorUse/Add">
                    <c:param name="ApplicationName" value="${application.applicationName}" />
                </c:url>
                <p><a href="${addUrl}">Add Application Editor Use.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ApplicationEditorUse.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="ApplicationEditor.Review" var="includeApplicationEditorReviewUrl" />
            <display:table name="applicationEditorUses" id="applicationEditorUse" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/ApplicationEditorUse/Review">
                                <c:param name="ApplicationName" value="${applicationEditorUse.application.applicationName}" />
                                <c:param name="ApplicationEditorUseName" value="${applicationEditorUse.applicationEditorUseName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${applicationEditorUse.applicationEditorUseName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${applicationEditorUse.applicationEditorUseName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${applicationEditorUse.description}" />
                </display:column>
                <display:column titleKey="columnTitle.defaultApplicationEditor">
                    <c:if test="${applicationEditorUse.defaultApplicationEditor != null}">
                        <c:choose>
                            <c:when test="${includeApplicationEditorReviewUrl}">
                                <c:url var="applicationEditorReviewUrl" value="/action/Core/ApplicationEditor/Review">
                                    <c:param name="ApplicationName" value="${applicationEditorUse.defaultApplicationEditor.application.applicationName}" />
                                    <c:param name="EditorName" value="${applicationEditorUse.defaultApplicationEditor.editor.editorName}" />
                                </c:url>
                                <a href="${applicationEditorReviewUrl}"><c:out value="${applicationEditorUse.defaultApplicationEditor.editor.description}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${applicationEditorUse.defaultApplicationEditor.editor.description}" />
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.defaultSize">
                    <c:choose>
                        <c:when test="${applicationEditorUse.defaultHeight != null}">
                            <c:out value="${applicationEditorUse.defaultHeight}" />
                        </c:when>
                        <c:otherwise>
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:otherwise>
                    </c:choose>
                            x
                    <c:choose>
                        <c:when test="${applicationEditorUse.defaultWidth != null}">
                            <c:out value="${applicationEditorUse.defaultWidth}" />
                        </c:when>
                        <c:otherwise>
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${applicationEditorUse.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ApplicationEditorUse.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/ApplicationEditorUse/SetDefault">
                                    <c:param name="ApplicationName" value="${applicationEditorUse.application.applicationName}" />
                                    <c:param name="ApplicationEditorUseName" value="${applicationEditorUse.applicationEditorUseName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="ApplicationEditorUse.Edit:ApplicationEditorUse.Description:ApplicationEditorUse.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="ApplicationEditorUse.Edit">
                            <c:url var="editUrl" value="/action/Core/ApplicationEditorUse/Edit">
                                <c:param name="ApplicationName" value="${applicationEditorUse.application.applicationName}" />
                                <c:param name="OriginalApplicationEditorUseName" value="${applicationEditorUse.applicationEditorUseName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ApplicationEditorUse.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/ApplicationEditorUse/Description">
                                <c:param name="ApplicationName" value="${applicationEditorUse.application.applicationName}" />
                                <c:param name="ApplicationEditorUseName" value="${applicationEditorUse.applicationEditorUseName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ApplicationEditorUse.Delete">
                            <c:url var="deleteUrl" value="/action/Core/ApplicationEditorUse/Delete">
                                <c:param name="ApplicationName" value="${applicationEditorUse.application.applicationName}" />
                                <c:param name="ApplicationEditorUseName" value="${applicationEditorUse.applicationEditorUseName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${applicationEditorUse.entityInstance.entityRef}" />
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
