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
        <title>Contact Mechanism Purposes</title>
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
                Contact Mechanism Purposes
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Chain/LetterContactMechanismPurpose/Add">
                <c:param name="ChainKindName" value="${letter.chainType.chainKind.chainKindName}" />
                <c:param name="ChainTypeName" value="${letter.chainType.chainTypeName}" />
                <c:param name="LetterName" value="${letter.letterName}" />
            </c:url>
            <p><a href="${addUrl}">Add Contact Mechanism Purpose.</a></p>
            <display:table name="letterContactMechanismPurposes" id="letterContactMechanismPurpose" class="displaytag">
                <display:column titleKey="columnTitle.priority">
                    <c:out value="${letterContactMechanismPurpose.priority}" />
                </display:column>
                <display:column titleKey="columnTitle.contactMechanismPurpose">
                    <c:out value="${letterContactMechanismPurpose.contactMechanismPurpose.description}" />
                </display:column>
                <display:column titleKey="columnTitle.contactMechanismType">
                    <c:out value="${letterContactMechanismPurpose.contactMechanismPurpose.contactMechanismType.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Chain/LetterContactMechanismPurpose/Edit">
                        <c:param name="ChainKindName" value="${letterContactMechanismPurpose.letter.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${letterContactMechanismPurpose.letter.chainType.chainTypeName}" />
                        <c:param name="LetterName" value="${letterContactMechanismPurpose.letter.letterName}" />
                        <c:param name="Priority" value="${letterContactMechanismPurpose.priority}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Chain/LetterContactMechanismPurpose/Delete">
                        <c:param name="ChainKindName" value="${letterContactMechanismPurpose.letter.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${letterContactMechanismPurpose.letter.chainType.chainTypeName}" />
                        <c:param name="LetterName" value="${letterContactMechanismPurpose.letter.letterName}" />
                        <c:param name="Priority" value="${letterContactMechanismPurpose.priority}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
