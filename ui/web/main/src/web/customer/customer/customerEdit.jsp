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
        <title>Review (<c:out value="${customer.customerName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Customer/Main" />">Search</a> &gt;&gt;
                <et:countCustomerResults searchTypeName="ORDER_ENTRY" countVar="customerResultsCount" commandResultVar="countCustomerResultsCommandResult" logErrors="false" />
                <c:if test="${customerResultsCount > 0}">
                    <a href="<c:url value="/action/Customer/Customer/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Customer/Customer/Review">
                    <c:param name="CustomerName" value="${customer.customerName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${customer.customerName}" />)</a> &gt;&gt;
                Edit Customer
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
                    <html:form action="/Customer/Customer/CustomerEdit" method="POST">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.customerType" />:</td>
                                <td>
                                    <html:select property="customerTypeChoice">
                                        <html:optionsCollection property="customerTypeChoices" />
                                    </html:select> (*)
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
                                <td align=right><fmt:message key="label.holdUntilComplete" />:</td>
                                <td>
                                    <html:checkbox property="holdUntilComplete" /> (*)
                                    <et:validationErrors id="errorMessage" property="HoldUntilComplete">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.allowBackorders" />:</td>
                                <td>
                                    <html:checkbox property="allowBackorders" /> (*)
                                    <et:validationErrors id="errorMessage" property="AllowBackorders">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.allowSubstitutions" />:</td>
                                <td>
                                    <html:checkbox property="allowSubstitutions" /> (*)
                                    <et:validationErrors id="errorMessage" property="AllowSubstitutions">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.allowCombiningShipments" />:</td>
                                <td>
                                    <html:checkbox property="allowCombiningShipments" /> (*)
                                    <et:validationErrors id="errorMessage" property="AllowCombiningShipments">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.requireReference" />:</td>
                                <td>
                                    <html:checkbox property="requireReference" /> (*)
                                    <et:validationErrors id="errorMessage" property="RequireReference">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.allowReferenceDuplicates" />:</td>
                                <td>
                                    <html:checkbox property="allowReferenceDuplicates" /> (*)
                                    <et:validationErrors id="errorMessage" property="AllowReferenceDuplicates">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.referenceValidationPattern" />:</td>
                                <td>
                                    <html:text property="referenceValidationPattern" size="40" maxlength="128" />
                                    <et:validationErrors id="errorMessage" property="ReferenceValidationPattern">
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
                                <td>
                                    <html:hidden property="customerName" />
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
