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
                <c:url var="commandMessagesUrl" value="/action/Core/CommandMessage/Main">
                    <c:param name="CommandMessageTypeName" value="${commandMessage.commandMessageType.commandMessageTypeName}" />
                </c:url>
                <a href="${commandMessagesUrl}">Command Messages</a> &gt;&gt;
                Translations
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Core/CommandMessage/TranslationAdd">
                <c:param name="CommandMessageTypeName" value="${commandMessage.commandMessageType.commandMessageTypeName}" />
                <c:param name="CommandMessageKey" value="${commandMessage.commandMessageKey}" />
            </c:url>
            <p><a href="${addUrl}">Add Translation.</a></p>
            <display:table name="commandMessageTranslations" id="commandMessageTranslation" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${commandMessageTranslation.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.translation">
                    <c:out value="${commandMessageTranslation.translation}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Core/CommandMessage/TranslationEdit">
                        <c:param name="CommandMessageTypeName" value="${commandMessageTranslation.commandMessage.commandMessageType.commandMessageTypeName}" />
                        <c:param name="CommandMessageKey" value="${commandMessageTranslation.commandMessage.commandMessageKey}" />
                        <c:param name="LanguageIsoName" value="${commandMessageTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Core/CommandMessage/TranslationDelete">
                        <c:param name="CommandMessageTypeName" value="${commandMessageTranslation.commandMessage.commandMessageType.commandMessageTypeName}" />
                        <c:param name="CommandMessageKey" value="${commandMessageTranslation.commandMessage.commandMessageKey}" />
                        <c:param name="LanguageIsoName" value="${commandMessageTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
