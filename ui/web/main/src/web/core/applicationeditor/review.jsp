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
        <title>
            <fmt:message key="pageTitle.applicationEditor">
                <fmt:param value="${applicationEditor.application.applicationName}" />
                <fmt:param value="${applicationEditor.editor.editorName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Application/Main" />"><fmt:message key="navigation.applications" /></a> &gt;&gt;
                <c:url var="applicationEditorsUrl" value="/action/Core/ApplicationEditor/Main">
                    <c:param name="ApplicationName" value="${applicationEditor.application.applicationName}" />
                </c:url>
                <a href="${applicationEditorsUrl}"><fmt:message key="navigation.applicationEditors" /></a> &gt;&gt;
                <fmt:message key="navigation.applicationEditor">
                    <fmt:param value="${applicationEditor.application.applicationName}" />
                    <fmt:param value="${applicationEditor.editor.editorName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Application.Review:Editor.Review" />
            <et:hasSecurityRole securityRole="Application.Review" var="includeApplicationReviewUrl" />
            <et:hasSecurityRole securityRole="Editor.Review" var="includeEditorReviewUrl" />
            <p><font size="+2"><b><c:out value="${applicationEditor.application.applicationName}" />, <c:out value="${applicationEditor.editor.editorName}" /></b></font></p>
            <br />
            <fmt:message key="label.application" />:
            <c:choose>
                <c:when test="${includeApplicationReviewUrl}">
                    <c:url var="applicationUrl" value="/action/Core/Application/Review">
                        <c:param name="ApplicationName" value="${applicationEditor.application.applicationName}" />
                    </c:url>
                    <a href="${applicationUrl}"><c:out value="${applicationEditor.application.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${applicationEditor.application.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.editor" />:
            <c:choose>
                <c:when test="${includeEditorReviewUrl}">
                    <c:url var="editorUrl" value="/action/Core/Editor/Review">
                        <c:param name="EditorName" value="${applicationEditor.editor.editorName}" />
                    </c:url>
                    <a href="${editorUrl}"><c:out value="${applicationEditor.editor.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${applicationEditor.editor.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />            
            <br />
            <c:set var="entityInstance" scope="request" value="${applicationEditor.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
