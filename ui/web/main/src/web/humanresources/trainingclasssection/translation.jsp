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
        <title>Training Class Sections</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/TrainingClass/Main" />">Training Classes</a> &gt;&gt;
                <c:url var="trainingClassSectionsUrl" value="/action/HumanResources/TrainingClassSection/Main">
                    <c:param name="TrainingClassName" value="${trainingClassSection.trainingClass.trainingClassName}" />
                </c:url>
                <a href="${trainingClassSectionsUrl}">Sections</a> &gt;&gt;
                Translations
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/HumanResources/TrainingClassSection/TranslationAdd">
                <c:param name="TrainingClassName" value="${trainingClassSection.trainingClass.trainingClassName}" />
                <c:param name="TrainingClassSectionName" value="${trainingClassSection.trainingClassSectionName}" />
            </c:url>
            <p><a href="${addUrl}">Add Translation.</a></p>
            <display:table name="trainingClassSectionTranslations" id="trainingClassSectionTranslation" class="displaytag">
                <display:column>
                    <c:url var="reviewUrl" value="/action/HumanResources/TrainingClassSection/TranslationReview">
                        <c:param name="TrainingClassName" value="${trainingClassSectionTranslation.trainingClassSection.trainingClass.trainingClassName}" />
                        <c:param name="TrainingClassSectionName" value="${trainingClassSectionTranslation.trainingClassSection.trainingClassSectionName}" />
                        <c:param name="LanguageIsoName" value="${trainingClassSectionTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${reviewUrl}">Review</a>
                </display:column>
                <display:column titleKey="columnTitle.language">
                    <c:out value="${trainingClassSectionTranslation.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${trainingClassSectionTranslation.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/HumanResources/TrainingClassSection/TranslationEdit">
                        <c:param name="TrainingClassName" value="${trainingClassSectionTranslation.trainingClassSection.trainingClass.trainingClassName}" />
                        <c:param name="TrainingClassSectionName" value="${trainingClassSectionTranslation.trainingClassSection.trainingClassSectionName}" />
                        <c:param name="LanguageIsoName" value="${trainingClassSectionTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/HumanResources/TrainingClassSection/TranslationDelete">
                        <c:param name="TrainingClassName" value="${trainingClassSectionTranslation.trainingClassSection.trainingClass.trainingClassName}" />
                        <c:param name="TrainingClassSectionName" value="${trainingClassSectionTranslation.trainingClassSection.trainingClassSectionName}" />
                        <c:param name="LanguageIsoName" value="${trainingClassSectionTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
