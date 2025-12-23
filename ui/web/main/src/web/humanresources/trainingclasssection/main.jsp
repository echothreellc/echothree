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
                Sections
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:TrainingClassPage.List:TrainingClassQuestion.List:TrainingClassSection.Create:TrainingClassSection.Edit:TrainingClassSection.Delete:TrainingClassSection.Review:TrainingClassSection.Translation" />
            <et:hasSecurityRole securityRoles="TrainingClassPage.List:TrainingClassQuestion.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="TrainingClassSection.Edit:TrainingClassSection.Translation:TrainingClassSection.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TrainingClassSection.Create">
                <c:url var="addUrl" value="/action/HumanResources/TrainingClassSection/Add">
                    <c:param name="TrainingClassName" value="${trainingClass.trainingClassName}" />
                </c:url>
                <p><a href="${addUrl}">Add Training Class Section.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TrainingClassSection.Review" var="includeReviewUrl" />
            <display:table name="trainingClassSections" id="trainingClassSection" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/HumanResources/TrainingClassSection/Review">
                                <c:param name="TrainingClassName" value="${trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassSection.trainingClassSectionName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${trainingClassSection.trainingClassSectionName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${trainingClassSection.trainingClassSectionName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${trainingClassSection.description}" />
                </display:column>
                <display:column titleKey="columnTitle.percentageToPass" class="percent">
                    <c:out value="${trainingClassSection.percentageToPass}" />
                </display:column>
                <display:column titleKey="columnTitle.questionCount">
                    <c:out value="${trainingClassSection.questionCount}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="TrainingClassPage.List">
                            <c:url var="trainingClassSectionPagesUrl" value="/action/HumanResources/TrainingClassPage/Main">
                                <c:param name="TrainingClassName" value="${trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassSection.trainingClassSectionName}" />
                            </c:url>
                            <a href="${trainingClassSectionPagesUrl}">Pages</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TrainingClassQuestion.List">
                            <c:url var="trainingClassSectionQuestionsUrl" value="/action/HumanResources/TrainingClassQuestion/Main">
                                <c:param name="TrainingClassName" value="${trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassSection.trainingClassSectionName}" />
                            </c:url>
                            <a href="${trainingClassSectionQuestionsUrl}">Questions</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="TrainingClassSection.Edit">
                            <c:url var="editUrl" value="/action/HumanResources/TrainingClassSection/Edit">
                                <c:param name="TrainingClassName" value="${trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="OriginalTrainingClassSectionName" value="${trainingClassSection.trainingClassSectionName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TrainingClassSection.Translation">
                            <c:url var="translationsUrl" value="/action/HumanResources/TrainingClassSection/Translation">
                                <c:param name="TrainingClassName" value="${trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassSection.trainingClassSectionName}" />
                            </c:url>
                            <a href="${translationsUrl}">Translations</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TrainingClassSection.Delete">
                            <c:url var="deleteUrl" value="/action/HumanResources/TrainingClassSection/Delete">
                                <c:param name="TrainingClassName" value="${trainingClassSection.trainingClass.trainingClassName}" />
                                <c:param name="TrainingClassSectionName" value="${trainingClassSection.trainingClassSectionName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${trainingClassSection.entityInstance.entityRef}" />
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
