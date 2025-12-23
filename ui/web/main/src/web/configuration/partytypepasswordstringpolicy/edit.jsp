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
        <title>Password Policy</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/PartyType/Main" />">Party Types</a> &gt;&gt;
                Password Policy &gt;&gt;
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
                    <html:form action="/Configuration/PartyTypePasswordStringPolicy/Edit" method="POST" focus="lockoutFailureCount">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.forceChangeAfterCreate" />:</td>
                                <td>
                                    <html:checkbox property="forceChangeAfterCreate" /> (*)
                                    <et:validationErrors id="errorMessage" property="ForceChangeAfterCreate">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.forceChangeAfterReset" />:</td>
                                <td>
                                    <html:checkbox property="forceChangeAfterReset" /> (*)
                                    <et:validationErrors id="errorMessage" property="ForceChangeAfterReset">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.allowChange" />:</td>
                                <td>
                                    <html:checkbox property="allowChange" /> (*)
                                    <et:validationErrors id="errorMessage" property="AllowChange">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.passwordHistory" />:</td>
                                <td>
                                    <html:text property="passwordHistory" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="PasswordHistory">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.minimumPasswordLifetime" />:</td>
                                <td>
                                    <html:text property="minimumPasswordLifetime" size="12" maxlength="12" />
                                    <html:select property="minimumPasswordLifetimeUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="minimumPasswordLifetimeUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="MinimumPasswordLifetime">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="MinimumPasswordLifetimeUnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.maximumPasswordLifetime" />:</td>
                                <td>
                                    <html:text property="maximumPasswordLifetime" size="12" maxlength="12" />
                                    <html:select property="maximumPasswordLifetimeUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="maximumPasswordLifetimeUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="MaximumPasswordLifetime">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="MaximumPasswordLifetimeUnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.expirationWarningTime" />:</td>
                                <td>
                                    <html:text property="expirationWarningTime" size="12" maxlength="12" />
                                    <html:select property="expirationWarningTimeUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="expirationWarningTimeUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="ExpirationWarningTime">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="ExpirationWarningTimeUnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.expiredLoginsPermitted" />:</td>
                                <td>
                                    <html:text property="expiredLoginsPermitted" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="ExpiredLoginsPermitted">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.minimumLength" />:</td>
                                <td>
                                    <html:text property="minimumLength" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="MinimumLength">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.maximumLength" />:</td>
                                <td>
                                    <html:text property="maximumLength" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="MaximumLength">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.requiredDigitCount" />:</td>
                                <td>
                                    <html:text property="requiredDigitCount" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="RequiredDigitCount">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.requiredLetterCount" />:</td>
                                <td>
                                    <html:text property="requiredLetterCount" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="RequiredLetterCount">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.requiredUpperCaseCount" />:</td>
                                <td>
                                    <html:text property="requiredUpperCaseCount" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="RequiredUpperCaseCount">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.requiredLowerCaseCount" />:</td>
                                <td>
                                    <html:text property="requiredLowerCaseCount" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="RequiredLowerCaseCount">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.maximumRepeated" />:</td>
                                <td>
                                    <html:text property="maximumRepeated" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="MaximumRepeated">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.minimumCharacterTypes" />:</td>
                                <td>
                                    <html:text property="minimumCharacterTypes" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="MinimumCharacterTypes">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="partyTypeName" />
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