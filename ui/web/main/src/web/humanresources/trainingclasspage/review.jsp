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
        <title>Review (<c:out value="${trainingClassPage.trainingClassPageName}" />)</title>
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
                    <c:param name="TrainingClassName" value="${trainingClassPage.trainingClassSection.trainingClass.trainingClassName}" />
                </c:url>
                <a href="${trainingClassSectionsUrl}">Sections</a> &gt;&gt;
                <c:url var="trainingClassPagesUrl" value="/action/HumanResources/TrainingClassPage/Main">
                    <c:param name="TrainingClassName" value="${trainingClassPage.trainingClassSection.trainingClass.trainingClassName}" />
                    <c:param name="TrainingClassSectionName" value="${trainingClassPage.trainingClassSection.trainingClassSectionName}" />
                </c:url>
                <a href="${trainingClassPagesUrl}">Pages</a> &gt;&gt;
                Review (<c:out value="${trainingClassPage.trainingClassPageName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="TrainingClass.Review:TrainingClassSection.Review" />
            <et:hasSecurityRole securityRole="TrainingClass.Review" var="includeTrainingClassReviewUrl" />
            <et:hasSecurityRole securityRole="TrainingClassSection.Review" var="includeTrainingClassSectionReviewUrl" />
            <c:choose>
                <c:when test="${trainingClassPage.trainingClassPageName != trainingClassPage.description}">
                    <p><font size="+2"><b><c:out value="${trainingClassPage.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${trainingClassPage.trainingClassPageName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${trainingClassPage.trainingClassPageName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            
            Training Class:
            <c:choose>
                <c:when test="${includeTrainingClassReviewUrl}">
                    <c:url var="trainingClassUrl" value="/action/HumanResources/TrainingClass/Review">
                        <c:param name="TrainingClassName" value="${trainingClassPage.trainingClassSection.trainingClass.trainingClassName}" />
                    </c:url>
                    <a href="${trainingClassUrl}"><c:out value="${trainingClassPage.trainingClassSection.trainingClass.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${trainingClassPage.trainingClassSection.trainingClass.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Training Class Section:
            <c:choose>
                <c:when test="${includeTrainingClassSectionReviewUrl}">
                    <c:url var="trainingClassUrl" value="/action/HumanResources/TrainingClassSection/Review">
                        <c:param name="TrainingClassName" value="${trainingClassPage.trainingClassSection.trainingClass.trainingClassName}" />
                        <c:param name="TrainingClassSectionName" value="${trainingClassPage.trainingClassSection.trainingClassSectionName}" />
                    </c:url>
                    <a href="${trainingClassUrl}"><c:out value="${trainingClassPage.trainingClassSection.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${trainingClassPage.trainingClassSection.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Training Class Page: <c:out value="${trainingClassPage.description}" /><br />
            
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${trainingClassPage.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
