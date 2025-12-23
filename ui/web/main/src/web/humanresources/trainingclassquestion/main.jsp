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
        <title>Training Class Questions</title>
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
                Questions
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:TrainingClassAnswer.List:TrainingClassQuestion.Create:TrainingClassQuestion.Edit:TrainingClassQuestion.Delete:TrainingClassQuestion.Review:TrainingClassQuestion.Translation" />
            <et:hasSecurityRole securityRoles="TrainingClassAnswer.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="TrainingClassQuestion.Edit:TrainingClassQuestion.Translation:TrainingClassQuestion.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TrainingClassQuestion.Create">
                <c:url var="addUrl" value="/action/HumanResources/TrainingClassQuestion/Add">
                    <c:param name="TrainingClassName" value="${trainingClassSection.trainingClass.trainingClassName}" />
                    <c:param name="TrainingClassSectionName" value="${trainingClassSection.trainingClassSectionName}" />
                </c:url>
                <p><a href="${addUrl}">Add Training Class Question.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TrainingClassQuestion.Review" var="includeReviewUrl" />
            <display:table name="trainingClassQuestions" id="trainingClassQuestion" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/HumanResources/TrainingClassQuestion/Review">
                                <c:param name="TrainingClassName" value="${trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="TrainingClassQuestionName" value="${trainingClassQuestion.trainingClassQuestionName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${trainingClassQuestion.trainingClassQuestionName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${trainingClassQuestion.trainingClassQuestionName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.askingRequired">
                    <c:choose>
                        <c:when test="${trainingClassQuestion.askingRequired}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.passingRequired">
                    <c:choose>
                        <c:when test="${trainingClassQuestion.passingRequired}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="TrainingClassAnswer.List">
                            <c:url var="trainingClassAnswersUrl" value="/action/HumanResources/TrainingClassAnswer/Main">
                                <c:param name="TrainingClassName" value="${trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="TrainingClassQuestionName" value="${trainingClassQuestion.trainingClassQuestionName}" />
                            </c:url>
                            <a href="${trainingClassAnswersUrl}">Answers</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="TrainingClassQuestion.Edit">
                            <c:url var="editUrl" value="/action/HumanResources/TrainingClassQuestion/Edit">
                                <c:param name="TrainingClassName" value="${trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="OriginalTrainingClassQuestionName" value="${trainingClassQuestion.trainingClassQuestionName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TrainingClassQuestion.Translation">
                            <c:url var="translationsUrl" value="/action/HumanResources/TrainingClassQuestion/Translation">
                                <c:param name="TrainingClassName" value="${trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="TrainingClassQuestionName" value="${trainingClassQuestion.trainingClassQuestionName}" />
                            </c:url>
                            <a href="${translationsUrl}">Translations</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TrainingClassQuestion.Delete">
                            <c:url var="deleteUrl" value="/action/HumanResources/TrainingClassQuestion/Delete">
                                <c:param name="TrainingClassName" value="${trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                                <c:param name="TrainingClassQuestionName" value="${trainingClassQuestion.trainingClassQuestionName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${trainingClassQuestion.entityInstance.entityRef}" />
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
