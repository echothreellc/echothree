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
        <title>Review (<c:out value="${returnPolicyTranslation.returnPolicy.returnKind.returnKindName}" />, <c:out value="${returnPolicyTranslation.returnPolicy.returnPolicyName}" />, <c:out value="${returnPolicyTranslation.language.languageIsoName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/Main" />">Returns</a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/ReturnKind/Main" />">Return Kinds</a> &gt;&gt;
                <c:url var="returnPoliciesUrl" value="/action/ReturnPolicy/ReturnPolicy/Main">
                    <c:param name="ReturnKindName" value="${returnPolicyTranslation.returnPolicy.returnKind.returnKindName}" />
                </c:url>
                <a href="${returnPoliciesUrl}">Return Policies</a> &gt;&gt;
                <c:url var="translationsUrl" value="/action/ReturnPolicy/ReturnPolicy/Translation">
                    <c:param name="ReturnKindName" value="${returnPolicyTranslation.returnPolicy.returnKind.returnKindName}" />
                    <c:param name="ReturnPolicyName" value="${returnPolicyTranslation.returnPolicy.returnPolicyName}" />
                </c:url>
                <a href="${translationsUrl}">Translations</a> &gt;&gt;
                Review (<c:out value="${returnPolicyTranslation.returnPolicy.returnKind.returnKindName}" />, <c:out value="${returnPolicyTranslation.returnPolicy.returnPolicyName}" />, <c:out value="${returnPolicyTranslation.language.languageIsoName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="ReturnKind.Review:ReturnPolicy.Review:Language.Review" />
            <et:hasSecurityRole securityRole="ReturnKind.Review" var="includeReturnKindReviewUrl" />
            <et:hasSecurityRole securityRole="ReturnPolicy.Review" var="includeReturnPolicyReviewUrl" />
            <et:hasSecurityRole securityRole="Language.Review" var="includeLanguageReviewUrl" />
            <c:choose>
                <c:when test="${returnPolicyTranslation.returnPolicy.returnPolicyName != returnPolicy.description}">
                    <p><font size="+2"><b><c:out value="${returnPolicyTranslation.returnPolicy.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${returnPolicyTranslation.returnPolicy.returnPolicyName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${returnPolicyTranslation.returnPolicy.returnPolicyName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            
            Return Kind:
            <c:choose>
                <c:when test="${includeReturnKindReviewUrl}">
                    <c:url var="returnKindUrl" value="/action/ReturnPolicy/ReturnKind/Review">
                        <c:param name="ReturnKindName" value="${returnPolicyTranslation.returnPolicy.returnKind.returnKindName}" />
                    </c:url>
                    <a href="${returnKindUrl}"><c:out value="${returnPolicyTranslation.returnPolicy.returnKind.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${returnPolicyTranslation.returnPolicy.returnKind.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Return Policy:
            <c:choose>
                <c:when test="${includeReturnPolicyReviewUrl}">
                    <c:url var="returnPolicyUrl" value="/action/ReturnPolicy/ReturnPolicy/Review">
                        <c:param name="ReturnKindName" value="${returnPolicyTranslation.returnPolicy.returnKind.returnKindName}" />
                        <c:param name="ReturnPolicyName" value="${returnPolicyTranslation.returnPolicy.returnPolicyName}" />
                    </c:url>
                    <a href="${returnPolicyUrl}"><c:out value="${returnPolicyTranslation.returnPolicy.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${returnPolicyTranslation.returnPolicy.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Language:
            <c:choose>
                <c:when test="${includeLanguageReviewUrl}">
                    <c:url var="languageUrl" value="/action/Configuration/Language/Review">
                        <c:param name="LanguageIsoName" value="${returnPolicyTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${languageUrl}"><c:out value="${returnPolicyTranslation.language.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${returnPolicyTranslation.language.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            <c:if test="${returnPolicyTranslation.policy != null}">
                <br />

                <fmt:message key="label.policy" />:<br />
                <table class="displaytag">
                    <tbody>
                        <tr class="odd">
                            <td>
                                <et:out value="${returnPolicyTranslation.policy}" mimeTypeName="${returnPolicyTranslation.policyMimeType.mimeTypeName}" />
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
