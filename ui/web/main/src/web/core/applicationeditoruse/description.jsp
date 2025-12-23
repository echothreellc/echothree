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
        <title><fmt:message key="pageTitle.applicationEditorUseDescriptions" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Application/Main" />"><fmt:message key="navigation.applications" /></a> &gt;&gt;
                <c:url var="applicationEditorUsesUrl" value="/action/Core/ApplicationEditorUse/Main">
                    <c:param name="ApplicationName" value="${applicationEditorUse.application.applicationName}" />
                </c:url>
                <a href="${applicationEditorUsesUrl}"><fmt:message key="navigation.applicationEditorUses" /></a> &gt;&gt;
                <fmt:message key="navigation.applicationEditorUseDescriptions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Core/ApplicationEditorUse/DescriptionAdd">
                <c:param name="ApplicationName" value="${applicationEditorUse.application.applicationName}" />
                <c:param name="ApplicationEditorUseName" value="${applicationEditorUse.applicationEditorUseName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="applicationEditorUseDescriptions" id="applicationEditorUseDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${applicationEditorUseDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${applicationEditorUseDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Core/ApplicationEditorUse/DescriptionEdit">
                        <c:param name="ApplicationName" value="${applicationEditorUseDescription.applicationEditorUse.application.applicationName}" />
                        <c:param name="ApplicationEditorUseName" value="${applicationEditorUseDescription.applicationEditorUse.applicationEditorUseName}" />
                        <c:param name="LanguageIsoName" value="${applicationEditorUseDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Core/ApplicationEditorUse/DescriptionDelete">
                        <c:param name="ApplicationName" value="${applicationEditorUseDescription.applicationEditorUse.application.applicationName}" />
                        <c:param name="ApplicationEditorUseName" value="${applicationEditorUseDescription.applicationEditorUse.applicationEditorUseName}" />
                        <c:param name="LanguageIsoName" value="${applicationEditorUseDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
