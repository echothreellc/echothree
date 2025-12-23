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
                <fmt:message key="navigation.employees" />
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/HumanResources/Employee/Add" />">Add Employee.</a></p>
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/HumanResources/Employee/Main" method="POST" focus="firstName">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.firstName" />:</td>
                        <td>
                            <html:text size="20" property="firstName" maxlength="20" />
                            <fmt:message key="label.soundex" />: <html:checkbox property="firstNameSoundex" value="true" />
                            <et:validationErrors id="errorMessage" property="FirstName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="FirstNameSoundex">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <!--
                    <tr>
                        <td align=right><fmt:message key="label.middleName" />:</td>
                        <td>
                            <html:text size="20" property="middleName" maxlength="20" />
                            <fmt:message key="label.soundex" />: <html:checkbox property="middleNameSoundex" value="true" />
                            <et:validationErrors id="errorMessage" property="MiddleName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="MiddleNameSoundex">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    -->
                    <tr>
                        <td align=right><fmt:message key="label.lastName" />:</td>
                        <td>
                            <html:text size="20" property="lastName" maxlength="20" />
                            <fmt:message key="label.soundex" />: <html:checkbox property="lastNameSoundex" value="true" />
                            <et:validationErrors id="errorMessage" property="LastName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="LastNameSoundex">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.employeeName" />:</td>
                        <td>
                            <html:text size="20" property="employeeName" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="EmployeeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <!--
                    <tr>
                        <td align=right><fmt:message key="label.partyName" />:</td>
                        <td>
                            <html:text size="20" property="partyName" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="PartyName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    -->
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.employeeStatus" />:</td>
                        <td>
                            <html:select property="employeeStatusChoice">
                                <html:optionsCollection property="employeeStatusChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="EmployeeStatusChoice">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.employeeAvailability" />:</td>
                        <td>
                            <html:select property="employeeAvailabilityChoice">
                                <html:optionsCollection property="employeeAvailabilityChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="EmployeeAvailabilityChoice">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.createdSince" />:</td>
                        <td>
                            <html:text size="60" property="createdSince" maxlength="30" />
                            <et:validationErrors id="errorMessage" property="CreatedSince">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.modifiedSince" />:</td>
                        <td>
                            <html:text size="60" property="modifiedSince" maxlength="30" />
                            <et:validationErrors id="errorMessage" property="ModifiedSince">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
