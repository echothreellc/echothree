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
        <title>Review (<c:out value="${trainingClass.trainingClassName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/TrainingClass/Main" />">Training Classes</a> &gt;&gt;
                Review (<c:out value="${trainingClass.trainingClassName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="WorkEffortScope.Review" />
            <et:hasSecurityRole securityRole="WorkEffortScope.Review" var="includeWorkEffortScopeReviewUrl" />
            <c:choose>
                <c:when test="${trainingClass.trainingClassName != trainingClass.description}">
                    <p><font size="+2"><b><c:out value="${trainingClass.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${trainingClass.trainingClassName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${trainingClass.trainingClassName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            
            Training Class: <c:out value="${trainingClass.description}" /><br />
            <br />
            
            <c:if test="${trainingClass.estimatedReadingTime != null || trainingClass.readingTimeAllowed != null}">
                <c:if test="${trainingClass.estimatedReadingTime != null}">
                    <fmt:message key="label.estimatedReadingTime" />: <c:out value="${trainingClass.estimatedReadingTime}" /><br />
                </c:if>
                <c:if test="${trainingClass.readingTimeAllowed != null}">
                    <fmt:message key="label.readingTimeAllowed" />: <c:out value="${trainingClass.readingTimeAllowed}" /><br />
                </c:if>
                <br />
            </c:if>
            <c:if test="${trainingClass.estimatedTestingTime != null || trainingClass.testingTimeAllowed != null}">
                <c:if test="${trainingClass.estimatedTestingTime != null}">
                    <fmt:message key="label.estimatedTestingTime" />: <c:out value="${trainingClass.estimatedTestingTime}" /><br />
                </c:if>
                <c:if test="${trainingClass.testingTimeAllowed != null}">
                    <fmt:message key="label.testingTimeAllowed" />: <c:out value="${trainingClass.testingTimeAllowed}" /><br />
                </c:if>
                <br />
            </c:if>
            <c:if test="${trainingClass.requiredCompletionTime != null || trainingClass.workEffortScope != null}">
                <c:if test="${trainingClass.requiredCompletionTime != null}">
                    <fmt:message key="label.requiredCompletionTime" />: <c:out value="${trainingClass.requiredCompletionTime}" /><br />
                </c:if>
                <c:if test="${trainingClass.workEffortScope != null}">
                    <fmt:message key="label.workEffortScope" />:
                    <c:choose>
                        <c:when test="${includeWorkEffortScopeReviewUrl}">
                            <c:url var="workEffortScopeUrl" value="/action/Configuration/WorkEffortScope/Review">
                                <c:param name="WorkEffortTypeName" value="${trainingClass.workEffortScope.workEffortType.workEffortTypeName}" />
                                <c:param name="WorkEffortScopeName" value="${trainingClass.workEffortScope.workEffortScopeName}" />
                            </c:url>
                            <a href="${workEffortScopeUrl}"><c:out value="${trainingClass.workEffortScope.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${trainingClass.workEffortScope.description}" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                </c:if>
                <br />
            </c:if>
            <c:if test="${trainingClass.defaultPercentageToPass != null || trainingClass.overallQuestionCount != null}">
                <c:if test="${trainingClass.defaultPercentageToPass != null}">
                    <fmt:message key="label.defaultPercentageToPass" />: <c:out value="${trainingClass.defaultPercentageToPass}" /><br />
                </c:if>
                <c:if test="${trainingClass.overallQuestionCount != null}">
                    <fmt:message key="label.overallQuestionCount" />: <c:out value="${trainingClass.overallQuestionCount}" /><br />
                </c:if>
                <br />
            </c:if>
            <c:if test="${trainingClass.testingValidityTime != null}">
                <fmt:message key="label.testingValidityTime" />: <c:out value="${trainingClass.testingValidityTime}" /><br />
                <br />
            </c:if>
            <c:if test="${trainingClass.expiredRetentionTime != null}">
                <fmt:message key="label.expiredRetentionTime" />: <c:out value="${trainingClass.expiredRetentionTime}" /><br />
                <br />
            </c:if>
            
            <fmt:message key="label.alwaysReassignOnExpiration" />:
            <c:choose>
                <c:when test="${trainingClassAnswer.alwaysReassignOnExpiration}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            
            <br />
            <c:set var="entityInstance" scope="request" value="${trainingClass.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
