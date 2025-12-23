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
        <title><fmt:message key="pageTitle.partySecurityRoleTemplateTrainingClasses" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/PartySecurityRoleTemplate/Main" />"><fmt:message key="navigation.partySecurityRoleTemplates" /></a> &gt;&gt;
                <fmt:message key="navigation.partySecurityRoleTemplateTrainingClasses" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="PartySecurityRoleTemplateTrainingClass.Create:PartySecurityRoleTemplateTrainingClass.Delete:TrainingClass.Review" />
            <et:hasSecurityRole securityRole="PartySecurityRoleTemplateTrainingClass.Create">
                <c:url var="addUrl" value="/action/Configuration/PartySecurityRoleTemplateTrainingClass/Add">
                    <c:param name="PartySecurityRoleTemplateName" value="${partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                </c:url>
                <p><a href="${addUrl}">Add Training Class.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TrainingClass.Review" var="includeTrainingClassReviewUrl" />
            <display:table name="partySecurityRoleTemplateTrainingClasses" id="partySecurityRoleTemplateTrainingClass" class="displaytag">
                <display:column titleKey="columnTitle.trainingClass">
                    <c:choose>
                        <c:when test="${includeTrainingClassReviewUrl}">
                            <c:url var="reviewUrl" value="/action/HumanResources/TrainingClass/Review">
                                <c:param name="TrainingClassName" value="${partySecurityRoleTemplateTrainingClass.trainingClass.trainingClassName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${partySecurityRoleTemplateTrainingClass.trainingClass.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${partySecurityRoleTemplateTrainingClass.trainingClass.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRole="PartySecurityRoleTemplateTrainingClass.Delete">
                    <display:column>
                        <c:url var="deleteUrl" value="/action/Configuration/PartySecurityRoleTemplateTrainingClass/Delete">
                            <c:param name="PartySecurityRoleTemplateName" value="${partySecurityRoleTemplateTrainingClass.partySecurityRoleTemplate.partySecurityRoleTemplateName}" />
                            <c:param name="TrainingClassName" value="${partySecurityRoleTemplateTrainingClass.trainingClass.trainingClassName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
