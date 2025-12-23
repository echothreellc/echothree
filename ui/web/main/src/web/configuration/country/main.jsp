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
        <title>Countries</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                Countries
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:Country.Create:Country.Review:Country.Edit:State.List:ZipCode.List:HarmonizedTariffScheduleCode.List:GeoCodeAlias.List:GeoCodeLanguage.List:GeoCodeCurrency.List:GeoCodeTimeZone.List:GeoCodeDateTimeFormat.List:GeoCode.Description" />
            <et:hasSecurityRole securityRole="Country.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRoles="State.List:ZipCode.List:HarmonizedTariffScheduleCode.List:GeoCodeAlias.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="GeoCodeLanguage.List:GeoCodeCurrency.List:GeoCodeTimeZone.List:GeoCodeDateTimeFormat.List">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="Country.Edit:GeoCode.Description">
                <c:set var="linksInThirdRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Country.Create">
                <p><a href="<c:url value="/action/Configuration/Country/Add" />">Add Country.</a></p>
            </et:hasSecurityRole>
            <display:table name="countries" id="country" class="displaytag">
                <display:column titleKey="columnTitle.country">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/Country/Review">
                                <c:param name="CountryName" value="${country.geoCodeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${country.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${country.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${country.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="Country.Edit">
                                <c:url var="setDefaultUrl" value="/action/Configuration/Country/SetDefault">
                                    <c:param name="CountryName" value="${country.geoCodeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test='${linksInFirstRow || linksInSecondRow || linksInThirdRow}'>
                    <display:column>
                        <et:hasSecurityRole securityRole="State.List">
                            <c:url var="statesUrl" value="/action/Configuration/State/Main">
                                <c:param name="CountryName" value="${country.geoCodeName}" />
                            </c:url>
                            <a href="${statesUrl}">States</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ZipCode.List">
                            <c:url var="zipCodesUrl" value="/action/Configuration/ZipCode/Main">
                                <c:param name="CountryName" value="${country.geoCodeName}" />
                            </c:url>
                            <a href="${zipCodesUrl}">Zip Codes</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.List">
                            <c:url var="harmonizedTariffScheduleCodesUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Main">
                                <c:param name="CountryName" value="${country.geoCodeName}" />
                            </c:url>
                            <a href="${harmonizedTariffScheduleCodesUrl}">Harmonized Tariff Schedule Codes</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="GeoCodeAlias.List">
                            <c:url var="aliasesUrl" value="/action/Configuration/GeoCodeAlias/Main">
                                <c:param name="GeoCodeName" value="${country.geoCodeName}" />
                            </c:url>
                            <a href="${aliasesUrl}">Aliases</a>
                        </et:hasSecurityRole>
                        <c:if test='${linksInFirstRow && linksInSecondRow}'>
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="GeoCodeLanguage.List">
                            <c:url var="geoCodeLanguagesUrl" value="/action/Configuration/GeoCodeLanguage/Main">
                                <c:param name="GeoCodeName" value="${country.geoCodeName}" />
                            </c:url>
                            <a href="${geoCodeLanguagesUrl}">Languages</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="GeoCodeCurrency.List">
                            <c:url var="geoCodeCurrenciesUrl" value="/action/Configuration/GeoCodeCurrency/Main">
                                <c:param name="GeoCodeName" value="${country.geoCodeName}" />
                            </c:url>
                            <a href="${geoCodeCurrenciesUrl}">Currencies</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="GeoCodeTimeZone.List">
                            <c:url var="geoCodeTimeZonesUrl" value="/action/Configuration/GeoCodeTimeZone/Main">
                                <c:param name="GeoCodeName" value="${country.geoCodeName}" />
                            </c:url>
                            <a href="${geoCodeTimeZonesUrl}">Time Zones</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="GeoCodeDateTimeFormat.List">
                            <c:url var="geoCodeDateTimeFormatsUrl" value="/action/Configuration/GeoCodeDateTimeFormat/Main">
                                <c:param name="GeoCodeName" value="${country.geoCodeName}" />
                            </c:url>
                            <a href="${geoCodeDateTimeFormatsUrl}">Date Time Formats</a>
                        </et:hasSecurityRole>
                        <c:if test='${linksInSecondRow && linksInThirdRow}'>
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="Country.Edit">
                            <c:url var="editUrl" value="/action/Configuration/Country/Edit">
                                <c:param name="GeoCodeName" value="${country.geoCodeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="GeoCode.Description">
                            <c:url var="descriptionsUrl" value="/action/Configuration/GeoCode/Description">
                                <c:param name="GeoCodeName" value="${country.geoCodeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${country.entityInstance.entityRef}" />
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
