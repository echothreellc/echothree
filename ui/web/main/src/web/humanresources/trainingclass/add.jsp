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
        <title>Training Classes</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="tinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/TrainingClass/Main" />">Training Classes</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/HumanResources/TrainingClass/Add" method="POST" focus="trainingClassName">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.trainingClassName" />:</td>
                        <td>
                            <html:text property="trainingClassName" size="40" maxlength="40" /> (*)
                            <et:validationErrors id="errorMessage" property="TrainingClassName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.estimatedReadingTime" />:</td>
                        <td>
                            <html:text property="estimatedReadingTime" size="12" maxlength="12" />
                            <html:select property="estimatedReadingTimeUnitOfMeasureTypeChoice">
                                <html:optionsCollection property="estimatedReadingTimeUnitOfMeasureTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="EstimatedReadingTime">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="EstimatedReadingTimeUnitOfMeasureTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.readingTimeAllowed" />:</td>
                        <td>
                            <html:text property="readingTimeAllowed" size="12" maxlength="12" />
                            <html:select property="readingTimeAllowedUnitOfMeasureTypeChoice">
                                <html:optionsCollection property="readingTimeAllowedUnitOfMeasureTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ReadingTimeAllowed">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="ReadingTimeAllowedUnitOfMeasureTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.estimatedTestingTime" />:</td>
                        <td>
                            <html:text property="estimatedTestingTime" size="12" maxlength="12" />
                            <html:select property="estimatedTestingTimeUnitOfMeasureTypeChoice">
                                <html:optionsCollection property="estimatedTestingTimeUnitOfMeasureTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="EstimatedTestingTime">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="EstimatedTestingTimeUnitOfMeasureTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.testingTimeAllowed" />:</td>
                        <td>
                            <html:text property="testingTimeAllowed" size="12" maxlength="12" />
                            <html:select property="testingTimeAllowedUnitOfMeasureTypeChoice">
                                <html:optionsCollection property="testingTimeAllowedUnitOfMeasureTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="TestingTimeAllowed">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="TestingTimeAllowedUnitOfMeasureTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.requiredCompletionTime" />:</td>
                        <td>
                            <html:text property="requiredCompletionTime" size="12" maxlength="12" />
                            <html:select property="requiredCompletionTimeUnitOfMeasureTypeChoice">
                                <html:optionsCollection property="requiredCompletionTimeUnitOfMeasureTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="RequiredCompletionTime">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="RequiredCompletionTimeUnitOfMeasureTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.workEffortScope" />:</td>
                        <td>
                            <html:select property="workEffortScopeChoice">
                                <html:optionsCollection property="workEffortScopeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="WorkEffortScopeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.defaultPercentageToPass" />:</td>
                        <td>
                            <html:text property="defaultPercentageToPass" size="12" maxlength="12" />
                            <et:validationErrors id="errorMessage" property="DefaultPercentageToPass">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.overallQuestionCount" />:</td>
                        <td>
                            <html:text property="overallQuestionCount" size="12" maxlength="12" />
                            <et:validationErrors id="errorMessage" property="OverallQuestionCount">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.testingValidityTime" />:</td>
                        <td>
                            <html:text property="testingValidityTime" size="12" maxlength="12" />
                            <html:select property="testingValidityTimeUnitOfMeasureTypeChoice">
                                <html:optionsCollection property="testingValidityTimeUnitOfMeasureTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="TestingValidityTime">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="TestingValidityTimeUnitOfMeasureTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.expiredRetentionTime" />:</td>
                        <td>
                            <html:text property="expiredRetentionTime" size="12" maxlength="12" />
                            <html:select property="expiredRetentionTimeUnitOfMeasureTypeChoice">
                                <html:optionsCollection property="expiredRetentionTimeUnitOfMeasureTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ExpiredRetentionTime">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="ExpiredRetentionTimeUnitOfMeasureTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.alwaysReassignOnExpiration" />:</td>
                        <td>
                            <html:checkbox property="alwaysReassignOnExpiration" /> (*)
                            <et:validationErrors id="errorMessage" property="AlwaysReassignOnExpiration">
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
                        <td align=right><fmt:message key="label.overviewMimeTypeChoice" />:</td>
                        <td>
                            <html:select onchange="overviewMimeTypeChoiceChange()" property="overviewMimeTypeChoice" styleId="overviewMimeTypeChoices">
                                <html:optionsCollection property="overviewMimeTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="OverviewMimeTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.overview" />:</td>
                        <td>
                            <html:textarea property="overview" cols="60" rows="5" styleId="overview" />
                            <et:validationErrors id="errorMessage" property="Overview">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.introductionMimeTypeChoice" />:</td>
                        <td>
                            <html:select onchange="introductionMimeTypeChoiceChange()" property="introductionMimeTypeChoice" styleId="introductionMimeTypeChoices">
                                <html:optionsCollection property="introductionMimeTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="IntroductionMimeTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.introduction" />:</td>
                        <td>
                            <html:textarea property="introduction" cols="60" rows="20" styleId="introduction" />
                            <et:validationErrors id="errorMessage" property="Introduction">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>