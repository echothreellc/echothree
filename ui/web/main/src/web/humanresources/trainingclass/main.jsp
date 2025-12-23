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
        <title>Training Classes</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                Training Classes
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:TrainingClassSection.List:PartyTrainingClass.List:TrainingClass.Create:TrainingClass.Edit:TrainingClass.Delete:TrainingClass.Review:TrainingClass.Translation" />
            <et:hasSecurityRole securityRoles="TrainingClassSection.List:PartyTrainingClass.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="TrainingClass.Edit:TrainingClass.Translation:TrainingClass.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TrainingClass.Create">
                <p><a href="<c:url value="/action/HumanResources/TrainingClass/Add" />">Add Training Class.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TrainingClass.Review" var="includeReviewUrl" />
            <display:table name="trainingClasses" id="trainingClass" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/HumanResources/TrainingClass/Review">
                                <c:param name="TrainingClassName" value="${trainingClass.trainingClassName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${trainingClass.trainingClassName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${trainingClass.trainingClassName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${trainingClass.description}" />
                </display:column>
                <display:column titleKey="columnTitle.estimatedReadingTime">
                    <c:out value="${trainingClass.estimatedReadingTime}" />
                </display:column>
                <display:column titleKey="columnTitle.estimatedTestingTime">
                    <c:out value="${trainingClass.estimatedTestingTime}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${trainingClass.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="TrainingClass.Edit">
                                <c:url var="setDefaultUrl" value="/action/HumanResources/TrainingClass/SetDefault">
                                    <c:param name="TrainingClassName" value="${trainingClass.trainingClassName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="TrainingClassSection.List">
                            <c:url var="trainingClassSectionsUrl" value="/action/HumanResources/TrainingClassSection/Main">
                                <c:param name="TrainingClassName" value="${trainingClass.trainingClassName}" />
                            </c:url>
                            <a href="${trainingClassSectionsUrl}">Sections</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="PartyTrainingClass.List">
                            <c:url var="trainingClassPartiesUrl" value="/action/HumanResources/PartyTrainingClass/Main">
                                <c:param name="TrainingClassName" value="${trainingClass.trainingClassName}" />
                            </c:url>
                            <a href="${trainingClassPartiesUrl}">Employees</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="TrainingClass.Edit">
                            <c:url var="editUrl" value="/action/HumanResources/TrainingClass/Edit">
                                <c:param name="OriginalTrainingClassName" value="${trainingClass.trainingClassName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TrainingClass.Translation">
                            <c:url var="translationsUrl" value="/action/HumanResources/TrainingClass/Translation">
                                <c:param name="TrainingClassName" value="${trainingClass.trainingClassName}" />
                            </c:url>
                            <a href="${translationsUrl}">Translations</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TrainingClass.Delete">
                            <c:url var="deleteUrl" value="/action/HumanResources/TrainingClass/Delete">
                                <c:param name="TrainingClassName" value="${trainingClass.trainingClassName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${trainingClass.entityInstance.entityRef}" />
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
