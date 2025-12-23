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
        <title>Lockout Policy</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/PartyType/Main" />">Party Types</a> &gt;&gt;
                Lockout Policy &gt;&gt;
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
                    <html:form action="/Configuration/PartyTypeLockoutPolicy/Edit" method="POST" focus="lockoutFailureCount">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.lockoutFailureCount" />:</td>
                                <td>
                                    <html:text property="lockoutFailureCount" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="LockoutFailureCount">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.resetFailureCountTime" />:</td>
                                <td>
                                    <html:text property="resetFailureCountTime" size="12" maxlength="12" />
                                    <html:select property="resetFailureCountTimeUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="resetFailureCountTimeUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="ResetFailureCountTime">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="ResetFailureCountTimeUnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.manualLockoutReset" />:</td>
                                <td>
                                    <html:checkbox property="manualLockoutReset" /> (*)
                                    <et:validationErrors id="errorMessage" property="ManualLockoutReset">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.lockoutInactiveTime" />:</td>
                                <td>
                                    <html:text property="lockoutInactiveTime" size="12" maxlength="12" />
                                    <html:select property="lockoutInactiveTimeUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="lockoutInactiveTimeUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="LockoutInactiveTime">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="LockoutInactiveTimeUnitOfMeasureTypeName">
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