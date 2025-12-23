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
        <title>Employee Companies</title>
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
                Companies
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/HumanResources/Company/Add">
                <c:param name="PartyName" value="${toParty.partyName}" />
            </c:url>
            <p><a href="${addUrl}">Add Company.</a></p>
            <display:table name="partyRelationships" id="partyRelationship" class="displaytag">
                <display:column titleKey="columnTitle.partyType">
                    <c:set var="partyTypeName" value="${partyRelationship.fromParty.partyType.partyTypeName}" />
                    <c:url var="reviewUrl" value="/action/Configuration/PartyType/Review">
                        <c:param name="PartyTypeName" value="${partyTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${partyRelationship.fromParty.partyType.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:set var="reviewUrlValue" value="unset"/>
                    <c:choose>
                        <c:when test="${partyTypeName == 'COMPANY'}">
                            <c:set var="reviewUrlValue" value="/action/Accounting/Company/Review" />
                        </c:when>
                        <c:when test="${partyTypeName == 'DIVISION'}">
                            <c:set var="reviewUrlValue" value="/action/Accounting/Division/Review" />
                        </c:when>
                        <c:when test="${partyTypeName == 'DEPARTMENT'}">
                            <c:set var="reviewUrlValue" value="/action/Accounting/Department/Review" />
                        </c:when>
                    </c:choose>
                    <c:choose>
                        <c:when test="${reviewUrlValue == 'unset'}">
                            <c:out value="${partyRelationship.fromParty.description}" />
                        </c:when>
                        <c:otherwise>
                            <c:url var="reviewUrl" value="${reviewUrlValue}">
                                <c:param name="PartyName" value="${partyRelationship.fromParty.partyName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${partyRelationship.fromParty.partyGroup.name}" /></a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:choose>
                    <c:when test="${partyTypeName == 'COMPANY'}">
                        <et:company partyName="${partyRelationship.fromParty.partyName}" var="company" />
                    </c:when>
                    <c:when test="${partyTypeName == 'DIVISION'}">
                        <et:division partyName="${partyRelationship.fromParty.partyName}" var="division" />
                    </c:when>
                    <c:when test="${partyTypeName == 'DEPARTMENT'}">
                        <et:department partyName="${partyRelationship.fromParty.partyName}" var="department" />
                    </c:when>
                </c:choose>
                <display:column>
                        <c:choose>
                            <c:when test="${partyTypeName == 'COMPANY'}">
                                <c:url var="addUrl" value="/action/HumanResources/Company/Add">
                                    <c:param name="CompanyName" value="${company.companyName}" />
                                    <c:param name="PartyName" value="${partyRelationship.toParty.partyName}" />
                                </c:url>
                                <a href="${addUrl}">Add Division</a>
                            </c:when>
                            <c:when test="${partyTypeName == 'DIVISION'}">
                                <c:url var="addUrl" value="/action/HumanResources/Company/Add">
                                    <c:param name="CompanyName" value="${division.company.companyName}" />
                                    <c:param name="DivisionName" value="${division.divisionName}" />
                                    <c:param name="PartyName" value="${partyRelationship.toParty.partyName}" />
                                </c:url>
                                <a href="${addUrl}">Add Department</a>
                            </c:when>
                        </c:choose>
                </display:column>
                <display:column>
                    <c:url var="deleteUrl" value="/action/HumanResources/Company/Delete">
                        <c:choose>
                            <c:when test="${partyTypeName == 'COMPANY'}">
                                <c:param name="CompanyName" value="${company.companyName}" />
                                <c:param name="PartyName" value="${partyRelationship.toParty.partyName}" />
                            </c:when>
                            <c:when test="${partyTypeName == 'DIVISION'}">
                                <c:param name="CompanyName" value="${division.company.companyName}" />
                                <c:param name="DivisionName" value="${division.divisionName}" />
                                <c:param name="PartyName" value="${partyRelationship.toParty.partyName}" />
                            </c:when>
                            <c:when test="${partyTypeName == 'DEPARTMENT'}">
                                <c:param name="CompanyName" value="${department.division.company.companyName}" />
                                <c:param name="DivisionName" value="${department.division.divisionName}" />
                                <c:param name="DepartmentName" value="${department.departmentName}" />
                                <c:param name="PartyName" value="${partyRelationship.toParty.partyName}" />
                            </c:when>
                        </c:choose>
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
