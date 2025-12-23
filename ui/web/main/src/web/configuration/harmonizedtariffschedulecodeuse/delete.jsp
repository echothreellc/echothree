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
        <title><fmt:message key="pageTitle.harmonizedTariffScheduleCodeUses" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Country/Main" />"><fmt:message key="navigation.countries" /></a> &gt;&gt;
                <c:url var="harmonizedTariffScheduleCodesUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Main">
                    <c:param name="CountryName" value="${harmonizedTariffScheduleCodeUse.harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                </c:url>
                <a href="${harmonizedTariffScheduleCodesUrl}"><fmt:message key="navigation.harmonizedTariffScheduleCodes" /></a> &gt;&gt;
                <c:url var="harmonizedTariffScheduleCodeUsesUrl" value="/action/Configuration/HarmonizedTariffScheduleCodeUse/Main">
                    <c:param name="CountryName" value="${harmonizedTariffScheduleCodeUse.harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                    <c:param name="HarmonizedTariffScheduleCodeName" value="${harmonizedTariffScheduleCodeUse.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                </c:url>
                <a href="${harmonizedTariffScheduleCodeUsesUrl}"><fmt:message key="navigation.harmonizedTariffScheduleCodeUses" /></a> &gt;&gt;
                Delete
            </h2>
        </div>
        <div id="Content">
            <p>You are about to delete the <c:out value="${fn:toLowerCase(partyEntityType.entityType.description)}" />:</p>
            &nbsp;&nbsp;&nbsp;&nbsp;Country: <c:out value="${harmonizedTariffScheduleCodeUse.harmonizedTariffScheduleCode.countryGeoCode.description}" /><br />
            &nbsp;&nbsp;&nbsp;&nbsp;Harmonized Tariff Schedule Code: <c:out value="${harmonizedTariffScheduleCodeUse.harmonizedTariffScheduleCode.description}" /><br />
            &nbsp;&nbsp;&nbsp;&nbsp;Harmonized Tariff Schedule Code Use Type: <c:out value="${harmonizedTariffScheduleCodeUse.harmonizedTariffScheduleCodeUseType.description}" /><br />
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Configuration/HarmonizedTariffScheduleCodeUse/Delete" method="POST">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.confirmDelete" />:</td>
                        <td>
                            <html:checkbox property="confirmDelete" /> (*)
                            <et:validationErrors id="errorMessage" property="ConfirmDelete">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                </table>
                <html:submit value="Delete" onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" />
                <html:hidden property="countryName" />
                <html:hidden property="harmonizedTariffScheduleCodeName" />
                <html:hidden property="harmonizedTariffScheduleCodeUseTypeName" />
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>