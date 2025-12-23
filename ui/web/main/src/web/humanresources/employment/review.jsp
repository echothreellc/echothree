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
            <fmt:message key="pageTitle.employment">
                <fmt:param value="${employment.employmentName}" />
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
                <c:url var="employmentsUrl" value="/action/HumanResources/Employment/Main">
                    <c:param name="PartyName" value="${employment.party.partyName}" />
                </c:url>
                <a href="${employmentsUrl}"><fmt:message key="navigation.employments" /></a> &gt;&gt;
                <fmt:message key="navigation.employment">
                    <fmt:param value="${employment.employmentName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Company.Review" />
            <et:hasSecurityRole securityRole="Company.Review" var="includeCompanyUrl" />

            <p><font size="+2"><b><c:out value="${employment.employmentName}" /></b></font></p>
            <br />

            Company:
            <c:choose>
                <c:when test="${includeCompanyUrl}">
                    <c:url var="reviewUrl" value="/action/Accounting/Company/Review">
                        <c:param name="CompanyName" value="${employment.company.companyName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${employment.company.partyGroup.name}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${employment.company.partyGroup.name}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />

            <fmt:message key="label.startTime" />: <c:out value="${employment.startTime}" /><br />
            <fmt:message key="label.endTime" />:
            <c:choose>
                <c:when test="${employment.endTime != null}">
                    <c:out value="${employment.endTime}" />
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <br />

            <fmt:message key="label.terminationType" />:
            <c:choose>
                <c:when test="${employment.terminationType != null}">
                    <c:url var="terminationTypeUrl" value="/action/HumanResources/TerminationType/Review">
                        <c:param name="TerminationTypeName" value="${employment.terminationType.terminationTypeName}" />
                    </c:url>
                    <a href="${terminationTypeUrl}"><c:out value="${employment.terminationType.description}" /></a>
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.terminationReason" />:
            <c:choose>
                <c:when test="${employment.terminationReason != null}">
                    <c:url var="terminationReasonUrl" value="/action/HumanResources/TerminationReason/Review">
                        <c:param name="TerminationReasonName" value="${employment.terminationReason.terminationReasonName}" />
                    </c:url>
                    <a href="${terminationReasonUrl}"><c:out value="${employment.terminationReason.description}" /></a>
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <br />

            <br />
            <c:set var="entityInstance" scope="request" value="${employment.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
