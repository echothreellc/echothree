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
        <title><fmt:message key="pageTitle.employees" /></title>
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
                Edit
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.executionResult.hasLockErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                    <html:form action="/HumanResources/Employee/Edit" method="POST" focus="firstName">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.employeeType" />:</td>
                                <td>
                                    <html:select property="employeeTypeChoice">
                                        <html:optionsCollection property="employeeTypeChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="EmployeeTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.personalTitle" />:</td>
                                <td>
                                    <html:select property="personalTitleChoice">
                                        <html:optionsCollection property="personalTitleChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="PersonalTitleChoice">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.firstName" />:</td>
                                <td>
                                    <html:text size="20" property="firstName" maxlength="20" /> (*)
                                    <et:validationErrors id="errorMessage" property="FirstName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.middleName" />:</td>
                                <td>
                                    <html:text size="20" property="middleName" maxlength="20" />
                                    <et:validationErrors id="errorMessage" property="MiddleName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.lastName" />:</td>
                                <td>
                                    <html:text size="20" property="lastName" maxlength="20" /> (*)
                                    <et:validationErrors id="errorMessage" property="LastName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.nameSuffix" />:</td>
                                <td>
                                    <html:select property="nameSuffixChoice">
                                        <html:optionsCollection property="nameSuffixChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="NameSuffixChoice">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.language" />:</td>
                                <td>
                                    <html:select property="languageChoice">
                                        <html:optionsCollection property="languageChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="LanguageIsoName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.currency" />:</td>
                                <td>
                                    <html:select property="currencyChoice">
                                        <html:optionsCollection property="currencyChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="CurrencyIsoName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.timeZone" />:</td>
                                <td>
                                    <html:select property="timeZoneChoice">
                                        <html:optionsCollection property="timeZoneChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="TimeZoneName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.dateTimeFormat" />:</td>
                                <td>
                                    <html:select property="dateTimeFormatChoice">
                                        <html:optionsCollection property="dateTimeFormatChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DateTimeFormatName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.partySecurityRoleTemplate" />:</td>
                                <td>
                                    <html:select property="partySecurityRoleTemplateChoice">
                                        <html:optionsCollection property="partySecurityRoleTemplateChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="PartySecurityRoleTemplateName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="employeeName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/entityLock.jsp" />
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
