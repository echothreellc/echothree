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
        <title>Review (<c:out value="${vendor.vendorName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Main" />">Purchasing</a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Main" />">Vendors</a> &gt;&gt;
                <et:countVendorResults searchTypeName="VENDOR_REVIEW" countVar="vendorResultsCount" commandResultVar="countVendorResultsCommandResult" logErrors="false" />
                <c:if test="${vendorResultsCount > 0}">
                    <a href="<c:url value="/action/Purchasing/Vendor/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Purchasing/Vendor/Review">
                    <c:param name="VendorName" value="${vendor.vendorName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${vendor.vendorName}" />)</a> &gt;&gt;
                Edit Vendor
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
                    <html:form action="/Purchasing/Vendor/VendorEdit" method="POST">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.vendorName" />:</td>
                                <td>
                                    <html:text property="vendorName" size="40" maxlength="40" /> (*)
                                    <et:validationErrors id="errorMessage" property="VendorName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.vendorType" />:</td>
                                <td>
                                    <html:select property="vendorTypeChoice">
                                        <html:optionsCollection property="vendorTypeChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="VendorTypeName">
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
                                <td align=right><fmt:message key="label.apGlAccount" />:</td>
                                <td>
                                    <html:select property="apGlAccountChoice">
                                        <html:optionsCollection property="apGlAccountChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="ApGlAccountName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.minimumPurchaseOrderLines" />:</td>
                                <td>
                                    <html:text property="minimumPurchaseOrderLines" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="MinimumPurchaseOrderLines">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.maximumPurchaseOrderLines" />:</td>
                                <td>
                                    <html:text property="maximumPurchaseOrderLines" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="MaximumPurchaseOrderLines">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.minimumPurchaseOrderAmount" />:</td>
                                <td>
                                    <html:text property="minimumPurchaseOrderAmount" size="15" maxlength="15" />
                                    <et:validationErrors id="errorMessage" property="MinimumPurchaseOrderAmount">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.maximumPurchaseOrderAmount" />:</td>
                                <td>
                                    <html:text property="maximumPurchaseOrderAmount" size="15" maxlength="15" />
                                    <et:validationErrors id="errorMessage" property="MaximumPurchaseOrderAmount">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.useItemPurchasingCategories" />:</td>
                                <td>
                                    <html:checkbox property="useItemPurchasingCategories" value="true" /> (*)
                                    <et:validationErrors id="errorMessage" property="UseItemPurchasingCategories">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultItemAliasType" />:</td>
                                <td>
                                    <html:select property="defaultItemAliasTypeChoice">
                                        <html:optionsCollection property="defaultItemAliasTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DefaultItemAliasTypeName">
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
                                    <html:text size="20" property="firstName" maxlength="20" />
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
                                    <html:text size="20" property="lastName" maxlength="20" />
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
                                    <html:hidden property="originalVendorName" />
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
