<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2025 Echo Three, LLC                                              -->
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
        <title>Training Class Answers</title>
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
                    <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                </c:url>
                <a href="${trainingClassSectionsUrl}">Sections</a> &gt;&gt;
                <c:url var="trainingClassSectionsUrl" value="/action/HumanResources/TrainingClassQuestion/Main">
                    <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                    <c:param name="TrainingClassSectionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                </c:url>
                <a href="${trainingClassSectionsUrl}">Questions</a> &gt;&gt;
                <c:url var="trainingClassAnswersUrl" value="/action/HumanResources/TrainingClassAnswer/Main">
                    <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                    <c:param name="TrainingClassSectionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                    <c:param name="TrainingClassQuestionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" />
                </c:url>
                <a href="${trainingClassAnswersUrl}">Answers</a> &gt;&gt;
                Translations
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/HumanResources/TrainingClassAnswer/TranslationAdd">
                <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                <c:param name="TrainingClassSectionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                <c:param name="TrainingClassQuestionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" />
                <c:param name="TrainingClassAnswerName" value="${trainingClassAnswer.trainingClassAnswerName}" />
            </c:url>
            <p><a href="${addUrl}">Add Translation.</a></p>
            <display:table name="trainingClassAnswerTranslations" id="trainingClassAnswerTranslation" class="displaytag">
                <display:column>
                    <c:url var="reviewUrl" value="/action/HumanResources/TrainingClassAnswer/TranslationReview">
                        <c:param name="TrainingClassName" value="${trainingClassAnswerTranslation.trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                        <c:param name="TrainingClassSectionName" value="${trainingClassAnswerTranslation.trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                        <c:param name="TrainingClassQuestionName" value="${trainingClassAnswerTranslation.trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" />
                        <c:param name="TrainingClassAnswerName" value="${trainingClassAnswerTranslation.trainingClassAnswer.trainingClassAnswerName}" />
                        <c:param name="LanguageIsoName" value="${trainingClassAnswerTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${reviewUrl}">Review</a>
                </display:column>
                <display:column titleKey="columnTitle.language">
                    <c:out value="${trainingClassAnswerTranslation.language.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/HumanResources/TrainingClassAnswer/TranslationEdit">
                        <c:param name="TrainingClassName" value="${trainingClassAnswerTranslation.trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                        <c:param name="TrainingClassSectionName" value="${trainingClassAnswerTranslation.trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                        <c:param name="TrainingClassQuestionName" value="${trainingClassAnswerTranslation.trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" />
                        <c:param name="TrainingClassAnswerName" value="${trainingClassAnswerTranslation.trainingClassAnswer.trainingClassAnswerName}" />
                        <c:param name="LanguageIsoName" value="${trainingClassAnswerTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/HumanResources/TrainingClassAnswer/TranslationDelete">
                        <c:param name="TrainingClassName" value="${trainingClassAnswerTranslation.trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                        <c:param name="TrainingClassSectionName" value="${trainingClassAnswerTranslation.trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                        <c:param name="TrainingClassQuestionName" value="${trainingClassAnswerTranslation.trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" />
                        <c:param name="TrainingClassAnswerName" value="${trainingClassAnswerTranslation.trainingClassAnswer.trainingClassAnswerName}" />
                        <c:param name="LanguageIsoName" value="${trainingClassAnswerTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
