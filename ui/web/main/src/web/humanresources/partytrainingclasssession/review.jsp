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
        <title>
            <fmt:message key="pageTitle.partyTrainingClassSession">
                <fmt:param value="${partyTrainingClassSession.partyTrainingClass.partyTrainingClassName}" />
                <fmt:param value="${partyTrainingClassSession.partyTrainingClassSessionSequence}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Employee/Main" />"><fmt:message key="navigation.employees" /></a> &gt;&gt;
                <et:countEmployeeResults searchTypeName="HUMAN_RESOURCES" countVar="employeeResultsCount" commandResultVar="countEmployeeResultsCommandResult" logErrors="false" />
                <c:if test="${employeeResultsCount > 0}">
                    <a href="<c:url value="/action/HumanResources/Employee/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/HumanResources/Employee/Review">
                    <c:param name="EmployeeName" value="${employee.employeeName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${employee.employeeName}" />)</a> &gt;&gt;
                <c:url var="partyTrainingClassesUrl" value="/action/HumanResources/PartyTrainingClass/Main">
                    <c:param name="PartyName" value="${partyTrainingClassSession.partyTrainingClass.party.partyName}" />
                </c:url>
                <a href="${partyTrainingClassesUrl}"><fmt:message key="navigation.partyTrainingClasses" /></a> &gt;&gt;
                <c:url var="partyTrainingClassUrl" value="/action/HumanResources/PartyTrainingClass/Review">
                    <c:param name="PartyTrainingClassName" value="${partyTrainingClassSession.partyTrainingClass.partyTrainingClassName}" />
                </c:url>
                <a href="${partyTrainingClassUrl}"><fmt:message key="navigation.partyTrainingClass">
                    <fmt:param value="${partyTrainingClassSession.partyTrainingClass.partyTrainingClassName}" />
                </fmt:message></a> &gt;&gt;
                <fmt:message key="navigation.partyTrainingClassSession">
                    <fmt:param value="${partyTrainingClassSession.partyTrainingClass.partyTrainingClassName}" />
                    <fmt:param value="${partyTrainingClassSession.partyTrainingClassSessionSequence}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="TrainingClass.Review:TrainingClassPage.Review:TrainingClassQuestion.Review:TrainingClassAnswer.Review:PartyTrainingClass.Review" />
            <et:hasSecurityRole securityRole="TrainingClass.Review" var="includeTrainingClassReviewUrl" />
            <et:hasSecurityRole securityRole="TrainingClassPage.Review" var="includeTrainingClassPageReviewUrl" />
            <et:hasSecurityRole securityRole="TrainingClassQuestion.Review" var="includeTrainingClassQuestionReviewUrl" />
            <et:hasSecurityRole securityRole="TrainingClassAnswer.Review" var="includeTrainingClassAnswerReviewUrl" />
            <et:hasSecurityRole securityRole="PartyTrainingClass.Review" var="includePartyTrainingClassReviewUrl" />
            <p><font size="+2"><b><c:out value="${partyTrainingClassSession.partyTrainingClass.partyTrainingClassName}" />, <c:out value="${partyTrainingClassSession.partyTrainingClassSessionSequence}" /></b></font></p>
            <br />

            <fmt:message key="label.trainingClass" />:
            <c:choose>
                <c:when test="${includeTrainingClassReviewUrl}">
                    <c:url var="trainingClassUrl" value="/action/HumanResources/TrainingClass/Review">
                        <c:param name="TrainingClassName" value="${partyTrainingClassSession.partyTrainingClass.trainingClass.trainingClassName}" />
                    </c:url>
                    <a href="${trainingClassUrl}"><c:out value="${partyTrainingClassSession.partyTrainingClass.trainingClass.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTrainingClassSession.partyTrainingClass.trainingClass.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.partyTrainingClassName" />:
            <c:choose>
                <c:when test="${includePartyTrainingClassReviewUrl}">
                    <c:url var="partyTrainingClassUrl" value="/action/HumanResources/PartyTrainingClass/Review">
                        <c:param name="PartyTrainingClassName" value="${partyTrainingClassSession.partyTrainingClass.partyTrainingClassName}" />
                    </c:url>
                    <a href="${partyTrainingClassUrl}"><c:out value="${partyTrainingClassSession.partyTrainingClass.partyTrainingClassName}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTrainingClassSession.partyTrainingClass.partyTrainingClassName}" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.partyTrainingClassSessionSequence" />: <c:out value="${partyTrainingClassSession.partyTrainingClassSessionSequence}" /><br />
            <br />

            <c:if test='${partyTrainingClassSession.partyTrainingClassSessionPages.size != 0}'>
                <h2><fmt:message key="label.partyTrainingClassSessionPages" /></h2>
                <display:table name="partyTrainingClassSession.partyTrainingClassSessionPages.list" id="partyTrainingClassSessionPage" class="displaytag">
                    <display:column titleKey="columnTitle.trainingClassPage">
                        <c:choose>
                            <c:when test="${includeTrainingClassPageReviewUrl}">
                                <c:url var="trainingClassPageUrl" value="/action/HumanResources/TrainingClassPage/Review">
                                    <c:param name="TrainingClassName" value="${partyTrainingClassSessionPage.trainingClassPage.trainingClass.trainingClassName}" />
                                    <c:param name="TrainingClassSectionName" value="${partyTrainingClassSessionPage.trainingClassPage.trainingClassSection.trainingClassSectionName}" />
                                    <c:param name="TrainingClassPageName" value="${partyTrainingClassSessionPage.trainingClassPage.trainingClassPageName}" />
                                </c:url>
                                <a href="${trainingClassPageUrl}"><c:out value="${partyTrainingClassSessionPage.trainingClassPage.description}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${partyTrainingClassSessionPage.trainingClassPage.description}" />
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                    <display:column titleKey="columnTitle.readingStartTime">
                        <c:out value="${partyTrainingClassSessionQuestion.readingStartTime}" />
                    </display:column>
                    <display:column titleKey="columnTitle.readingEndTime">
                        <c:out value="${partyTrainingClassSessionQuestion.readingEndTime}" />
                    </display:column>
                </display:table>
                <br />
            </c:if>

            <c:if test='${partyTrainingClassSession.partyTrainingClassSessionQuestions.size != 0}'>
                <h2><fmt:message key="label.partyTrainingClassSessionQuestions" /></h2>
                <display:table name="partyTrainingClassSession.partyTrainingClassSessionQuestions.list" id="partyTrainingClassSessionQuestion" class="displaytag">
                    <display:column titleKey="columnTitle.trainingClassQuestionName">
                        <c:choose>
                            <c:when test="${includeTrainingClassQuestionReviewUrl}">
                                <c:url var="trainingClassQuestionUrl" value="/action/HumanResources/TrainingClassQuestion/Review">
                                    <c:param name="TrainingClassName" value="${partyTrainingClassSessionQuestion.trainingClassQuestion.trainingClassSection.trainingClass.trainingClassName}" />
                                    <c:param name="TrainingClassSectionName" value="${partyTrainingClassSessionQuestion.trainingClassQuestion.trainingClassSection.trainingClassSectionName}" />
                                    <c:param name="TrainingClassQuestionName" value="${partyTrainingClassSessionQuestion.trainingClassQuestion.trainingClassQuestionName}" />
                                </c:url>
                                <a href="${trainingClassQuestionUrl}"><c:out value="${partyTrainingClassSessionQuestion.trainingClassQuestion.trainingClassQuestionName}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${partyTrainingClassSessionQuestion.trainingClassQuestion.trainingClassQuestionName}" />
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                    <display:column titleKey="columnTitle.passingRequired">
                        <c:choose>
                            <c:when test="${partyTrainingClassSessionQuestion.trainingClassQuestion.passingRequired}">
                                <fmt:message key="phrase.yes" />
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="phrase.no" />
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                </display:table>
                <br />
            </c:if>
            
            <br />
            <c:set var="entityInstance" scope="request" value="${partyTrainingClassSession.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
