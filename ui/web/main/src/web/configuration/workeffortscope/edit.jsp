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
        <title>Work Effort Scopes</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/WorkEffortType/Main" />">Work Effort Types</a> &gt;&gt;
                <c:url var="workEffortScopesUrl" value="/action/Configuration/WorkEffortScope/Main">
                    <c:param name="WorkEffortTypeName" value="${workEffortScope.workEffortType.workEffortTypeName}" />
                </c:url>
                <a href="${workEffortScopesUrl}">Work Effort Scopes</a> &gt;&gt;
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
                    <html:form action="/Configuration/WorkEffortScope/Edit" method="POST" focus="workEffortScopeName">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.workEffortScopeName" />:</td>
                                <td>
                                    <html:text property="workEffortScopeName" size="40" maxlength="40" /> (*)
                                    <et:validationErrors id="errorMessage" property="WorkEffortScopeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.scheduledTime" />:</td>
                                <td>
                                    <html:text property="scheduledTime" size="12" maxlength="12" />
                                    <html:select property="scheduledTimeUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="scheduledTimeUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="ScheduledTime">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="ScheduledTimeUnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.estimatedTimeAllowed" />:</td>
                                <td>
                                    <html:text property="estimatedTimeAllowed" size="12" maxlength="12" />
                                    <html:select property="estimatedTimeAllowedUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="estimatedTimeAllowedUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="EstimatedTimeAllowed">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="EstimatedTimeAllowedUnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.maximumTimeAllowed" />:</td>
                                <td>
                                    <html:text property="maximumTimeAllowed" size="12" maxlength="12" />
                                    <html:select property="maximumTimeAllowedUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="maximumTimeAllowedUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="MaximumTimeAllowed">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="MaximumTimeAllowedUnitOfMeasureTypeName">
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
                                    <html:hidden property="workEffortTypeName" />
                                    <html:hidden property="originalWorkEffortScopeName" />
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