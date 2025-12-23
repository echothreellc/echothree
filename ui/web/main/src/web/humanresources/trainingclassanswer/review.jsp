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
        <title>Review (<c:out value="${trainingClassAnswer.trainingClassAnswerName}" />)</title>
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
                Review (<c:out value="${trainingClassAnswer.trainingClassAnswerName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="TrainingClass.Review:TrainingClassSection.Review:TrainingClassQuestion.Review" />
            <et:hasSecurityRole securityRole="TrainingClass.Review" var="includeTrainingClassReviewUrl" />
            <et:hasSecurityRole securityRole="TrainingClassSection.Review" var="includeTrainingClassSectionReviewUrl" />
            <et:hasSecurityRole securityRole="TrainingClassQuestion.Review" var="includeTrainingClassQuestionReviewUrl" />
            <p><font size="+2"><b><c:out value="${trainingClassAnswer.trainingClassAnswerName}" /></b></font></p>
            <br />
            
            Training Class:
            <c:choose>
                <c:when test="${includeTrainingClassReviewUrl}">
                    <c:url var="trainingClassUrl" value="/action/HumanResources/TrainingClass/Review">
                        <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                    </c:url>
                    <a href="${trainingClassUrl}"><c:out value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Training Class Section:
            <c:choose>
                <c:when test="${includeTrainingClassSectionReviewUrl}">
                    <c:url var="trainingClassUrl" value="/action/HumanResources/TrainingClassSection/Review">
                        <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                        <c:param name="TrainingClassSectionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                    </c:url>
                    <a href="${trainingClassUrl}"><c:out value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Training Class Question:
            <c:choose>
                <c:when test="${includeTrainingClassQuestionReviewUrl}">
                    <c:url var="trainingClassUrl" value="/action/HumanResources/TrainingClassQuestion/Review">
                        <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                        <c:param name="TrainingClassSectionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                        <c:param name="TrainingClassQuestionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" />
                    </c:url>
                    <a href="${trainingClassUrl}"><c:out value="${trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Training Class Answer: <c:out value="${trainingClassAnswer.trainingClassAnswerName}" /><br />
            
            <br />
            
            <fmt:message key="label.isCorrect" />:
            <c:choose>
                <c:when test="${trainingClassAnswer.isCorrect}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            
            <br />
            <c:set var="entityInstance" scope="request" value="${trainingClassAnswer.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
