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
        <title>Customers</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Customer/Main" />">Search</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Customer/Customer/Add" method="POST" focus="firstName">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.customerType" />:</td>
                        <td>
                            <html:select property="customerTypeChoice">
                                <html:optionsCollection property="customerTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="CustomerTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.cancellationPolicy" />:</td>
                        <td>
                            <html:select property="cancellationPolicyChoice">
                                <html:optionsCollection property="cancellationPolicyChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="CancellationPolicyName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.returnPolicy" />:</td>
                        <td>
                            <html:select property="returnPolicyChoice">
                                <html:optionsCollection property="returnPolicyChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ReturnPolicyName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.arGlAccount" />:</td>
                        <td>
                            <html:select property="arGlAccountChoice">
                                <html:optionsCollection property="arGlAccountChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ArGlAccountName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.initialSourceChoice" />:</td>
                        <td>
                            <html:select property="initialSourceChoice">
                                <html:optionsCollection property="initialSourceChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="InitialSourceName">
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
                            <et:validationErrors id="errorMessage" property="PersonalTitleId">
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
                            <et:validationErrors id="errorMessage" property="NameSuffixId">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.company" />:</td>
                        <td>
                            <html:text size="40" property="name" maxlength="60" />
                            <et:validationErrors id="errorMessage" property="Name">
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
                        <td align=right><fmt:message key="label.emailAddress" />:</td>
                        <td>
                            <html:text property="emailAddress" size="40" maxlength="80" />
                            <et:validationErrors id="errorMessage" property="EmailAddress">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.allowSolicitation" />:</td>
                        <td>
                            <html:checkbox property="allowSolicitation" value="true" /> (*)
                            <et:validationErrors id="errorMessage" property="AllowSolicitation">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.customerStatusChoice" />:</td>
                        <td>
                            <html:select property="customerStatusChoice">
                                <html:optionsCollection property="customerStatusChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="CustomerStatusChoice">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.customerCreditStatusChoice" />:</td>
                        <td>
                            <html:select property="customerCreditStatusChoice">
                                <html:optionsCollection property="customerCreditStatusChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="CustomerCreditStatusChoice">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
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
