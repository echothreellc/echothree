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
        <title>Review (<c:out value="${cancellationPolicyTranslation.cancellationPolicy.cancellationKind.cancellationKindName}" />, <c:out value="${cancellationPolicyTranslation.cancellationPolicy.cancellationPolicyName}" />, <c:out value="${cancellationPolicyTranslation.language.languageIsoName}" />)</title>
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
                    <c:param name="CancellationKindName" value="${cancellationPolicyTranslation.cancellationPolicy.cancellationKind.cancellationKindName}" />
                </c:url>
                <a href="${cancellationPoliciesUrl}">Cancellation Policies</a> &gt;&gt;
                <c:url var="translationsUrl" value="/action/CancellationPolicy/CancellationPolicy/Translation">
                    <c:param name="CancellationKindName" value="${cancellationPolicyTranslation.cancellationPolicy.cancellationKind.cancellationKindName}" />
                    <c:param name="CancellationPolicyName" value="${cancellationPolicyTranslation.cancellationPolicy.cancellationPolicyName}" />
                </c:url>
                <a href="${translationsUrl}">Translations</a> &gt;&gt;
                Review (<c:out value="${cancellationPolicyTranslation.cancellationPolicy.cancellationKind.cancellationKindName}" />, <c:out value="${cancellationPolicyTranslation.cancellationPolicy.cancellationPolicyName}" />, <c:out value="${cancellationPolicyTranslation.language.languageIsoName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="CancellationKind.Review:CancellationPolicy.Review:Language.Review" />
            <et:hasSecurityRole securityRole="CancellationKind.Review" var="includeCancellationKindReviewUrl" />
            <et:hasSecurityRole securityRole="CancellationPolicy.Review" var="includeCancellationPolicyReviewUrl" />
            <et:hasSecurityRole securityRole="Language.Review" var="includeLanguageReviewUrl" />
            <c:choose>
                <c:when test="${cancellationPolicyTranslation.cancellationPolicy.cancellationPolicyName != cancellationPolicy.description}">
                    <p><font size="+2"><b><c:out value="${cancellationPolicyTranslation.cancellationPolicy.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${cancellationPolicyTranslation.cancellationPolicy.cancellationPolicyName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${cancellationPolicyTranslation.cancellationPolicy.cancellationPolicyName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            
            Cancellation Kind:
            <c:choose>
                <c:when test="${includeCancellationKindReviewUrl}">
                    <c:url var="cancellationKindUrl" value="/action/CancellationPolicy/CancellationKind/Review">
                        <c:param name="CancellationKindName" value="${cancellationPolicyTranslation.cancellationPolicy.cancellationKind.cancellationKindName}" />
                    </c:url>
                    <a href="${cancellationKindUrl}"><c:out value="${cancellationPolicyTranslation.cancellationPolicy.cancellationKind.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${cancellationPolicyTranslation.cancellationPolicy.cancellationKind.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Cancellation Policy:
            <c:choose>
                <c:when test="${includeCancellationPolicyReviewUrl}">
                    <c:url var="cancellationPolicyUrl" value="/action/CancellationPolicy/CancellationPolicy/Review">
                        <c:param name="CancellationKindName" value="${cancellationPolicyTranslation.cancellationPolicy.cancellationKind.cancellationKindName}" />
                        <c:param name="CancellationPolicyName" value="${cancellationPolicyTranslation.cancellationPolicy.cancellationPolicyName}" />
                    </c:url>
                    <a href="${cancellationPolicyUrl}"><c:out value="${cancellationPolicyTranslation.cancellationPolicy.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${cancellationPolicyTranslation.cancellationPolicy.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Language:
            <c:choose>
                <c:when test="${includeLanguageReviewUrl}">
                    <c:url var="languageUrl" value="/action/Configuration/Language/Review">
                        <c:param name="LanguageIsoName" value="${cancellationPolicyTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${languageUrl}"><c:out value="${cancellationPolicyTranslation.language.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${cancellationPolicyTranslation.language.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            <c:if test="${cancellationPolicyTranslation.policy != null}">
                <br />

                <fmt:message key="label.policy" />:<br />
                <table class="displaytag">
                    <tbody>
                        <tr class="odd">
                            <td>
                                <et:out value="${cancellationPolicyTranslation.policy}" mimeTypeName="${cancellationPolicyTranslation.policyMimeType.mimeTypeName}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <br />
            </c:if>
            
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
