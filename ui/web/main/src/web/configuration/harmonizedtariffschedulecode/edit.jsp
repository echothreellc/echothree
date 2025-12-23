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
        <title><fmt:message key="pageTitle.harmonizedTariffScheduleCodes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="tinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Country/Main" />">Countries</a> &gt;&gt;
                <c:url var="harmonizedTariffScheduleCodeUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Main">
                    <c:param name="CountryName" value="${harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                </c:url>
                <a href="${harmonizedTariffScheduleCodeUrl}"><fmt:message key="navigation.harmonizedTariffScheduleCodes" /></a> &gt;&gt;
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
                    <html:form action="/Configuration/HarmonizedTariffScheduleCode/Edit" method="POST" focus="harmonizedTariffScheduleCodeName">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.harmonizedTariffScheduleCodeName" />:</td>
                                <td>
                                    <html:text property="harmonizedTariffScheduleCodeName" size="10" maxlength="10" /> (*)
                                    <et:validationErrors id="errorMessage" property="HarmonizedTariffScheduleCodeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.firstHarmonizedTariffScheduleCodeUnitChoice" />:</td>
                                <td>
                                    <html:select property="firstHarmonizedTariffScheduleCodeUnitChoice" styleId="firstHarmonizedTariffScheduleCodeUnitChoices">
                                        <html:optionsCollection property="firstHarmonizedTariffScheduleCodeUnitChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="FirstHarmonizedTariffScheduleCodeUnitName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.secondHarmonizedTariffScheduleCodeUnitChoice" />:</td>
                                <td>
                                    <html:select property="secondHarmonizedTariffScheduleCodeUnitChoice" styleId="secondHarmonizedTariffScheduleCodeUnitChoices">
                                        <html:optionsCollection property="secondHarmonizedTariffScheduleCodeUnitChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="SecondHarmonizedTariffScheduleCodeUnitName">
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
                                <td>
                                    <html:hidden property="countryName" />
                                    <html:hidden property="originalHarmonizedTariffScheduleCodeName" />
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
