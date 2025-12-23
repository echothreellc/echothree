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
        <title>Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/CustomerType/Main" />">Types</a> &gt;&gt;
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
                    <html:form action="/Customer/CustomerType/Edit" method="POST" focus="customerTypeName">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.customerTypeName" />:</td>
                                <td>
                                    <html:text property="customerTypeName" size="40" maxlength="40" /> (*)
                                    <et:validationErrors id="errorMessage" property="CustomerTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.customerSequence" />:</td>
                                <td>
                                    <html:select property="customerSequenceChoice">
                                        <html:optionsCollection property="customerSequenceChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="CustomerSequenceName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultSource" />:</td>
                                <td>
                                    <html:select property="defaultSourceChoice">
                                        <html:optionsCollection property="defaultSourceChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DefaultSourceName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultTerm" />:</td>
                                <td>
                                    <html:select property="defaultTermChoice">
                                        <html:optionsCollection property="defaultTermChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DefaultTermName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultFreeOnBoard" />:</td>
                                <td>
                                    <html:select property="defaultFreeOnBoardChoice">
                                        <html:optionsCollection property="defaultFreeOnBoardChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DefaultFreeOnBoardName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultCancellationPolicy" />:</td>
                                <td>
                                    <html:select property="defaultCancellationPolicyChoice">
                                        <html:optionsCollection property="defaultCancellationPolicyChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DefaultCancellationPolicyName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultReturnPolicy" />:</td>
                                <td>
                                    <html:select property="defaultReturnPolicyChoice">
                                        <html:optionsCollection property="defaultReturnPolicyChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DefaultReturnPolicyName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultCustomerStatus" />:</td>
                                <td>
                                    <html:select property="defaultCustomerStatusChoice">
                                        <html:optionsCollection property="defaultCustomerStatusChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DefaultCustomerStatusChoice">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultCustomerCreditStatus" />:</td>
                                <td>
                                    <html:select property="defaultCustomerCreditStatusChoice">
                                        <html:optionsCollection property="defaultCustomerCreditStatusChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DefaultCustomerCreditStatusChoice">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultArGlAccount" />:</td>
                                <td>
                                    <html:select property="defaultArGlAccountChoice">
                                        <html:optionsCollection property="defaultArGlAccountChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="DefaultArGlAccountName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultHoldUntilComplete" />:</td>
                                <td>
                                    <html:checkbox property="defaultHoldUntilComplete" /> (*)
                                    <et:validationErrors id="errorMessage" property="DefaultHoldUntilComplete">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultAllowBackorders" />:</td>
                                <td>
                                    <html:checkbox property="defaultAllowBackorders" /> (*)
                                    <et:validationErrors id="errorMessage" property="DefaultAllowBackorders">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultAllowSubstitutions" />:</td>
                                <td>
                                    <html:checkbox property="defaultAllowSubstitutions" /> (*)
                                    <et:validationErrors id="errorMessage" property="DefaultAllowSubstitutions">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultAllowCombiningShipments" />:</td>
                                <td>
                                    <html:checkbox property="defaultAllowCombiningShipments" /> (*)
                                    <et:validationErrors id="errorMessage" property="DefaultAllowCombiningShipments">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultRequireReference" />:</td>
                                <td>
                                    <html:checkbox property="defaultRequireReference" /> (*)
                                    <et:validationErrors id="errorMessage" property="DefaultRequireReference">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultAllowReferenceDuplicates" />:</td>
                                <td>
                                    <html:checkbox property="defaultAllowReferenceDuplicates" /> (*)
                                    <et:validationErrors id="errorMessage" property="DefaultAllowReferenceDuplicates">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultReferenceValidationPattern" />:</td>
                                <td>
                                    <html:text property="defaultReferenceValidationPattern" size="40" maxlength="128" />
                                    <et:validationErrors id="errorMessage" property="DefaultReferenceValidationPattern">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultTaxable" />:</td>
                                <td>
                                    <html:checkbox property="defaultTaxable" /> (*)
                                    <et:validationErrors id="errorMessage" property="DefaultTaxable">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.allocationPriority" />:</td>
                                <td>
                                    <html:select property="allocationPriorityChoice">
                                        <html:optionsCollection property="allocationPriorityChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="AllocationPriorityName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.isDefault" />:</td>
                                <td>
                                    <html:checkbox property="isDefault" /> (*)
                                    <et:validationErrors id="errorMessage" property="IsDefault">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.sortOrder" />:</td>
                                <td>
                                    <html:text property="sortOrder" size="12" maxlength="12" /> (*)
                                    <et:validationErrors id="errorMessage" property="SortOrder">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.description" />:</td>
                                <td>
                                    <html:text property="description" size="60" maxlength="132" />
                                    <et:validationErrors id="errorMessage" property="Description">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="originalCustomerTypeName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
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
