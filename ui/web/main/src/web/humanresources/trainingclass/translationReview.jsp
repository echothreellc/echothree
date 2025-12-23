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
        <title>Review (<c:out value="${trainingClassTranslation.trainingClass.trainingClassName}" />, <c:out value="${trainingClassTranslation.language.languageIsoName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/TrainingClass/Main" />">Training Classes</a> &gt;&gt;
                <c:url var="translationsUrl" value="/action/HumanResources/TrainingClass/Translation">
                    <c:param name="TrainingClassName" value="${trainingClassTranslation.trainingClass.trainingClassName}" />
                </c:url>
                <a href="${translationsUrl}">Translations</a> &gt;&gt;
                Review (<c:out value="${trainingClassTranslation.trainingClass.trainingClassName}" />, <c:out value="${trainingClassTranslation.language.languageIsoName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="TrainingClass.Review:Language.Review" />
            <et:hasSecurityRole securityRole="TrainingClass.Review" var="includeTrainingClassReviewUrl" />
            <et:hasSecurityRole securityRole="Language.Review" var="includeLanguageReviewUrl" />
            <c:choose>
                <c:when test="${trainingClassTranslation.trainingClass.trainingClassName != trainingClass.description}">
                    <p><font size="+2"><b><c:out value="${trainingClassTranslation.trainingClass.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${trainingClassTranslation.trainingClass.trainingClassName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${trainingClassTranslation.trainingClass.trainingClassName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            
            Training Class:
            <c:choose>
                <c:when test="${includeTrainingClassReviewUrl}">
                    <c:url var="trainingClassUrl" value="/action/HumanResources/TrainingClass/Review">
                        <c:param name="TrainingClassName" value="${trainingClassTranslation.trainingClass.trainingClassName}" />
                    </c:url>
                    <a href="${trainingClassUrl}"><c:out value="${trainingClassTranslation.trainingClass.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${trainingClassTranslation.trainingClass.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Language:
            <c:choose>
                <c:when test="${includeLanguageReviewUrl}">
                    <c:url var="languageUrl" value="/action/Configuration/Language/Review">
                        <c:param name="LanguageIsoName" value="${trainingClassTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${languageUrl}"><c:out value="${trainingClassTranslation.language.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${trainingClassTranslation.language.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            <br />
            
            <fmt:message key="label.overview" />:<br />
            <table class="displaytag">
                <tbody>
                    <tr class="odd">
                        <td>
                            <et:out value="${trainingClassTranslation.overview}" mimeTypeName="${trainingClassTranslation.overviewMimeType.mimeTypeName}" />
                        </td>
                    </tr>
                </tbody>
            </table>
            <br />
            
            <fmt:message key="label.introduction" />:<br />
            <table class="displaytag">
                <tbody>
                    <tr class="odd">
                        <td>
                            <et:out value="${trainingClassTranslation.introduction}" mimeTypeName="${trainingClassTranslation.introductionMimeType.mimeTypeName}" />
                        </td>
                    </tr>
                </tbody>
            </table>
            <br />
            
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
