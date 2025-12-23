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
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Country/Main" />"><fmt:message key="navigation.countries" /></a> &gt;&gt;
                <fmt:message key="navigation.harmonizedTariffScheduleCodes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:HarmonizedTariffScheduleCode.Create:HarmonizedTariffScheduleCode.Edit:HarmonizedTariffScheduleCode.Delete:HarmonizedTariffScheduleCode.Review:HarmonizedTariffScheduleCode.Translation:HarmonizedTariffScheduleCode.HarmonizedTariffScheduleCodeUse" />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRoles="HarmonizedTariffScheduleCode.HarmonizedTariffScheduleCodeUse">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="HarmonizedTariffScheduleCode.Edit:HarmonizedTariffScheduleCode.Delete:HarmonizedTariffScheduleCode.Translation">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.Create">
                <c:url var="addUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Add">
                    <c:param name="CountryName" value="${country.geoCodeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Harmonized Tariff Schedule Code.</a></p>
            </et:hasSecurityRole>
            <br />
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Configuration/HarmonizedTariffScheduleCode/Search" method="POST" focus="q">
                <html:text size="60" property="q" />
                <et:validationErrors id="errorMessage" property="q">
                    <p><c:out value="${errorMessage}" /></p>
                </et:validationErrors>
                <html:hidden property="countryName" />
                <html:submit onclick="onSubmitDisable(this);" value="Search" /><html:hidden property="submitButton" />
            </html:form>
            <display:table name="harmonizedTariffScheduleCodes" id="harmonizedTariffScheduleCode" class="displaytag" partialList="true" pagesize="20" size="harmonizedTariffScheduleCodeCount" requestURI="/action/Configuration/HarmonizedTariffScheduleCode/Main">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Review">
                                <c:param name="CountryName" value="${harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                <c:param name="HarmonizedTariffScheduleCodeName" value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${harmonizedTariffScheduleCode.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${harmonizedTariffScheduleCode.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.Edit">
                                <c:url var="setDefaultUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/SetDefault">
                                    <c:param name="CountryName" value="${harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                    <c:param name="HarmonizedTariffScheduleCodeName" value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test='${linksInFirstRow || linksInSecondRow}'>
                    <display:column>
                        <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.HarmonizedTariffScheduleCodeUse">
                            <c:url var="harmonizedTariffScheduleCodeUseUrl" value="/action/Configuration/HarmonizedTariffScheduleCodeUse/Main">
                                <c:param name="CountryName" value="${harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                <c:param name="HarmonizedTariffScheduleCodeName" value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                            </c:url>
                            <a href="${harmonizedTariffScheduleCodeUseUrl}">Uses</a>
                        </et:hasSecurityRole>
                        <c:if test='${linksInFirstRow && linksInSecondRow}'>
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.Edit">
                            <c:url var="editUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Edit">
                                <c:param name="CountryName" value="${harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                <c:param name="OriginalHarmonizedTariffScheduleCodeName" value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.Translation">
                            <c:url var="translationsUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Translation">
                                <c:param name="CountryName" value="${harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                <c:param name="HarmonizedTariffScheduleCodeName" value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                            </c:url>
                            <a href="${translationsUrl}">Translations</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.Delete">
                            <c:url var="deleteUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Delete">
                                <c:param name="CountryName" value="${harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                <c:param name="HarmonizedTariffScheduleCodeName" value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${harmonizedTariffScheduleCode.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
