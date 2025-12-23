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
        <title><fmt:message key="pageTitle.applicationEditors" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Application/Main" />"><fmt:message key="navigation.applications" /></a> &gt;&gt;
                <fmt:message key="navigation.applicationEditors" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ApplicationEditor.Create:ApplicationEditor.Edit:ApplicationEditor.Delete:ApplicationEditor.Review:Editor.Review" />
            <et:hasSecurityRole securityRole="ApplicationEditor.Create">
                <c:url var="addUrl" value="/action/Core/ApplicationEditor/Add">
                    <c:param name="ApplicationName" value="${application.applicationName}" />
                </c:url>
                <p><a href="${addUrl}">Add Editor.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Editor.Review" var="includeEditorReviewUrl" />
            <display:table name="applicationEditors" id="applicationEditor" class="displaytag">
                <et:hasSecurityRole securityRole="ApplicationEditor.Review">
                    <display:column>
                        <c:url var="reviewUrl" value="/action/Core/ApplicationEditor/Review">
                            <c:param name="ApplicationName" value="${applicationEditor.application.applicationName}" />
                            <c:param name="EditorName" value="${applicationEditor.editor.editorName}" />
                        </c:url>
                        <a href="${reviewUrl}">Review</a>
                    </display:column>
                </et:hasSecurityRole>
                <display:column titleKey="columnTitle.editor">
                    <c:choose>
                        <c:when test="${includeEditorReviewUrl}">
                            <c:url var="editorReviewUrl" value="/action/Core/Editor/Review">
                                <c:param name="EditorName" value="${applicationEditor.editor.editorName}" />
                            </c:url>
                            <a href="${editorReviewUrl}"><c:out value="${applicationEditor.editor.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${applicationEditor.editor.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${applicationEditor.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ApplicationEditor.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/ApplicationEditor/SetDefault">
                                    <c:param name="ApplicationName" value="${applicationEditor.application.applicationName}" />
                                    <c:param name="EditorName" value="${applicationEditor.editor.editorName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="ApplicationEditor.Edit:ApplicationEditor.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="ApplicationEditor.Edit">
                            <c:url var="editUrl" value="/action/Core/ApplicationEditor/Edit">
                                <c:param name="ApplicationName" value="${applicationEditor.application.applicationName}" />
                                <c:param name="EditorName" value="${applicationEditor.editor.editorName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ApplicationEditor.Delete">
                            <c:url var="deleteUrl" value="/action/Core/ApplicationEditor/Delete">
                                <c:param name="ApplicationName" value="${applicationEditor.application.applicationName}" />
                                <c:param name="EditorName" value="${applicationEditor.editor.editorName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${applicationEditor.entityInstance.entityRef}" />
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
