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
                    <c:param name="TrainingClassName" value="${trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                </c:url>
                <a href="${trainingClassSectionsUrl}">Sections</a> &gt;&gt;
                <c:url var="trainingClassSectionsUrl" value="/action/HumanResources/TrainingClassQuestion/Main">
                    <c:param name="TrainingClassName" value="${trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                    <c:param name="TrainingClassSectionName" value="${trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                </c:url>
                <a href="${trainingClassSectionsUrl}">Questions</a> &gt;&gt;
                Answers
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:TrainingClassAnswer.Create:TrainingClassAnswer.Edit:TrainingClassAnswer.Delete:TrainingClassAnswer.Review:TrainingClassAnswer.Translation" />
            <et:hasSecurityRole securityRole="TrainingClassAnswer.Create">
                <c:url var="addUrl" value="/action/HumanResources/TrainingClassAnswer/Add">
                    <c:param name="TrainingClassName" value="${trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                    <c:param name="TrainingClassSectionName" value="${trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                    <c:param name="TrainingClassQuestionName" value="${trainingClassQuestion.trainingClassQuestionName}" />
                </c:url>
                <p><a href="${addUrl}">Add Training Class Answer.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TrainingClassAnswer.Review" var="includeReviewUrl" />
            <display:table name="trainingClassAnswers" id="trainingClassAnswer" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/HumanResources/TrainingClassAnswer/Review">
                                <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="TrainingClassQuestionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" />
                                <c:param name="TrainingClassAnswerName" value="${trainingClassAnswer.trainingClassAnswerName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${trainingClassAnswer.trainingClassAnswerName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${trainingClassAnswer.trainingClassAnswerName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.correct">
                    <c:choose>
                        <c:when test="${trainingClassAnswer.isCorrect}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <et:hasSecurityRole securityRoles="TrainingClassAnswer.Edit:TrainingClassAnswer.Translation:TrainingClassAnswer.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="TrainingClassAnswer.Edit">
                            <c:url var="editUrl" value="/action/HumanResources/TrainingClassAnswer/Edit">
                                <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="TrainingClassQuestionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" />
                                <c:param name="OriginalTrainingClassAnswerName" value="${trainingClassAnswer.trainingClassAnswerName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TrainingClassAnswer.Translation">
                            <c:url var="translationsUrl" value="/action/HumanResources/TrainingClassAnswer/Translation">
                                <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="TrainingClassQuestionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" />
                                <c:param name="TrainingClassAnswerName" value="${trainingClassAnswer.trainingClassAnswerName}" />
                            </c:url>
                            <a href="${translationsUrl}">Translations</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TrainingClassAnswer.Delete">
                            <c:url var="deleteUrl" value="/action/HumanResources/TrainingClassAnswer/Delete">
                                <c:param name="TrainingClassName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="TrainingClassQuestionName" value="${trainingClassAnswer.trainingClassQuestion.trainingClassQuestionName}" />
                                <c:param name="TrainingClassAnswerName" value="${trainingClassAnswer.trainingClassAnswerName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${trainingClassAnswer.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
