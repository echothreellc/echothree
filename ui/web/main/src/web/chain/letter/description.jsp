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
        <title>Letter Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/Main" />">Chains</a> &gt;&gt;
                <a href="<c:url value="/action/Chain/ChainKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="chainTypesUrl" value="/action/Chain/ChainType/Main">
                    <c:param name="ChainKindName" value="${letter.chainType.chainKind.chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}">Types</a> &gt;&gt;
                <c:url var="lettersUrl" value="/action/Chain/Letter/Main">
                    <c:param name="ChainKindName" value="${letter.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${letter.chainType.chainTypeName}" />
                </c:url>
                <a href="${lettersUrl}">Letters</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addDescriptionUrl" value="/action/Chain/Letter/DescriptionAdd">
                    <c:param name="ChainKindName" value="${letter.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${letter.chainType.chainTypeName}" />
                    <c:param name="LetterName" value="${letter.letterName}" />
            </c:url>
            <p><a href="${addDescriptionUrl}">Add Description.</a></p>
            <display:table name="letterDescriptions" id="letterDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${letterDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${letterDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editDescriptionUrl" value="/action/Chain/Letter/DescriptionEdit">
                        <c:param name="ChainKindName" value="${letterDescription.letter.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${letterDescription.letter.chainType.chainTypeName}" />
                        <c:param name="LetterName" value="${letterDescription.letter.letterName}" />
                        <c:param name="LanguageIsoName" value="${letterDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editDescriptionUrl}">Edit</a>
                    <c:url var="deleteDescriptionUrl" value="/action/Chain/Letter/DescriptionDelete">
                        <c:param name="ChainKindName" value="${letterDescription.letter.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${letterDescription.letter.chainType.chainTypeName}" />
                        <c:param name="LetterName" value="${letterDescription.letter.letterName}" />
                        <c:param name="LanguageIsoName" value="${letterDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteDescriptionUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
