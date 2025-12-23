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
        <title>Cancellation Policy Translations</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/CancellationPolicy/Main" />">Cancellations</a> &gt;&gt;
                <a href="<c:url value="/action/CancellationPolicy/CancellationKind/Main" />">Cancellation Kinds</a> &gt;&gt;
                <c:url var="cancellationPoliciesUrl" value="/action/CancellationPolicy/CancellationPolicy/Main">
                    <c:param name="CancellationKindName" value="${cancellationPolicy.cancellationKind.cancellationKindName}" />
                </c:url>
                <a href="${cancellationPoliciesUrl}">Cancellation Policies</a> &gt;&gt;
                Translations
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/CancellationPolicy/CancellationPolicy/TranslationAdd">
                <c:param name="CancellationKindName" value="${cancellationPolicy.cancellationKind.cancellationKindName}" />
                <c:param name="CancellationPolicyName" value="${cancellationPolicy.cancellationPolicyName}" />
            </c:url>
            <p><a href="${addUrl}">Add Translation.</a></p>
            <display:table name="cancellationPolicyTranslations" id="cancellationPolicyTranslation" class="displaytag">
                <display:column>
                    <c:url var="reviewUrl" value="/action/CancellationPolicy/CancellationPolicy/TranslationReview">
                        <c:param name="CancellationKindName" value="${cancellationPolicyTranslation.cancellationPolicy.cancellationKind.cancellationKindName}" />
                        <c:param name="CancellationPolicyName" value="${cancellationPolicyTranslation.cancellationPolicy.cancellationPolicyName}" />
                        <c:param name="LanguageIsoName" value="${cancellationPolicyTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${reviewUrl}">Review</a>
                </display:column>
                <display:column titleKey="columnTitle.language">
                    <c:out value="${cancellationPolicyTranslation.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${cancellationPolicyTranslation.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/CancellationPolicy/CancellationPolicy/TranslationEdit">
                        <c:param name="CancellationKindName" value="${cancellationPolicyTranslation.cancellationPolicy.cancellationKind.cancellationKindName}" />
                        <c:param name="CancellationPolicyName" value="${cancellationPolicyTranslation.cancellationPolicy.cancellationPolicyName}" />
                        <c:param name="LanguageIsoName" value="${cancellationPolicyTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/CancellationPolicy/CancellationPolicy/TranslationDelete">
                        <c:param name="CancellationKindName" value="${cancellationPolicyTranslation.cancellationPolicy.cancellationKind.cancellationKindName}" />
                        <c:param name="CancellationPolicyName" value="${cancellationPolicyTranslation.cancellationPolicy.cancellationPolicyName}" />
                        <c:param name="LanguageIsoName" value="${cancellationPolicyTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
