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
                <c:url var="companiesUrl" value="/action/HumanResources/Company/Main">
                    <c:param name="PartyName" value="${employee.partyName}" />
                </c:url>
                <a href="${companiesUrl}">Companies</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/HumanResources/Company/Add" method="POST">
                <table>
                    <tr>
                        <c:choose>
                            <c:when test="${partyTypeName == 'COMPANY'}">
                                <td align=right><fmt:message key="label.company" />:</td>
                                <td>
                                    <html:select property="companyChoice">
                                        <html:optionsCollection property="companyChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="CompanyName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </c:when>
                            <c:when test="${partyTypeName == 'DIVISION'}">
                                <td align=right><fmt:message key="label.division" />:</td>
                                <td>
                                    <html:select property="divisionChoice">
                                        <html:optionsCollection property="divisionChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DivisionName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </c:when>
                            <c:when test="${partyTypeName == 'DEPARTMENT'}">
                                <td align=right><fmt:message key="label.department" />:</td>
                                <td>
                                    <html:select property="departmentChoice">
                                        <html:optionsCollection property="departmentChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DepartmentName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </c:when>
                        </c:choose>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="partyName" />
                            <c:if test="${partyTypeName == 'DIVISION' || partyTypeName == 'DEPARTMENT'}">
                                <html:hidden property="companyName" />
                            </c:if>
                            <c:if test="${partyTypeName == 'DEPARTMENT'}">
                                <html:hidden property="divisionName" />
                            </c:if>
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>