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
        <title>
            <fmt:message key="pageTitle.harmonizedTariffScheduleCode">
                <fmt:param value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Country/Main" />">Countries</a> &gt;&gt;
                <c:url var="harmonizedTariffScheduleCodeUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Main">
                    <c:param name="CountryName" value="${harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                </c:url>
                <a href="${harmonizedTariffScheduleCodeUrl}"><fmt:message key="navigation.harmonizedTariffScheduleCodes" /></a> &gt;&gt;
                <fmt:message key="navigation.harmonizedTariffScheduleCode">
                    <fmt:param value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Country.Review:HarmonizedTariffScheduleCodeUnit.Review:HarmonizedTariffScheduleCode.HarmonizedTariffScheduleCodeUse:HarmonizedTariffScheduleCodeUseType.Review" />
            <et:hasSecurityRole securityRole="Country.Review" var="includeCountryUrl" />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUnit.Review" var="includeHarmonizedTariffScheduleCodeUnitUrl" />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUseType.Review" var="includeHarmonizedTariffScheduleCodeUseTypeUrl" />
            <c:choose>
                <c:when test="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName != harmonizedTariffScheduleCode.description}">
                    <p><font size="+2"><b><c:out value="${harmonizedTariffScheduleCode.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            Country:
            <c:choose>
                <c:when test="${includeCountryUrl}">
                    <c:url var="reviewUrl" value="/action/Configuration/Country/Review">
                        <c:param name="GeoCodeName" value="${harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${harmonizedTariffScheduleCode.countryGeoCode.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${harmonizedTariffScheduleCode.countryGeoCode.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Harmonized Tariff Schedule Code Name: ${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}<br />
            <br />
            First Harmonized Tariff Schedule Code Unit:
            <c:choose>
                <c:when test="${harmonizedTariffScheduleCode.firstHarmonizedTariffScheduleCodeUnit != null}">
                    <c:choose>
                        <c:when test="${includeHarmonizedTariffScheduleCodeUnitUrl}">
                            <c:url var="reviewUrl" value="/action/Item/HarmonizedTariffScheduleCodeUnit/Review">
                                <c:param name="HarmonizedTariffScheduleCodeUnitName" value="${harmonizedTariffScheduleCode.firstHarmonizedTariffScheduleCodeUnit.harmonizedTariffScheduleCodeUnitName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${harmonizedTariffScheduleCode.firstHarmonizedTariffScheduleCodeUnit.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${harmonizedTariffScheduleCode.firstHarmonizedTariffScheduleCodeUnit.description}" />
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            Second Harmonized Tariff Schedule Code Unit:
            <c:choose>
                <c:when test="${harmonizedTariffScheduleCode.secondHarmonizedTariffScheduleCodeUnit != null}">
                    <c:choose>
                        <c:when test="${includeHarmonizedTariffScheduleCodeUnitUrl}">
                            <c:url var="reviewUrl" value="/action/Item/HarmonizedTariffScheduleCodeUnit/Review">
                                <c:param name="HarmonizedTariffScheduleCodeUnitName" value="${harmonizedTariffScheduleCode.secondHarmonizedTariffScheduleCodeUnit.harmonizedTariffScheduleCodeUnitName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${harmonizedTariffScheduleCode.secondHarmonizedTariffScheduleCodeUnit.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${harmonizedTariffScheduleCode.secondHarmonizedTariffScheduleCodeUnit.description}" />
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.HarmonizedTariffScheduleCodeUse">
                <h2>Harmonized Tariff Schedule Code Uses</h2>
                <c:url var="addUrl" value="/action/Configuration/HarmonizedTariffScheduleCodeUse/Add">
                    <c:param name="CountryName" value="${harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                    <c:param name="HarmonizedTariffScheduleCodeName" value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Harmonized Tariff Schedule Code Use.</a></p>
                <c:choose>
                    <c:when test="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUses.size == 0}">
                        No harmonized tariff schedule code uses were found.<br />
                    </c:when>
                    <c:otherwise>
                    <display:table name="harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUses.list" id="harmonizedTariffScheduleCodeUse" class="displaytag">
                        <display:column titleKey="columnTitle.harmonizedTariffScheduleCodeUseType">
                            <c:choose>
                                <c:when test="${includeHarmonizedTariffScheduleCodeUseTypeUrl}">
                                    <c:url var="reviewUrl" value="/action/Item/HarmonizedTariffScheduleCodeUseType/Review">
                                        <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${harmonizedTariffScheduleCodeUse.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${harmonizedTariffScheduleCodeUse.harmonizedTariffScheduleCodeUseType.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${harmonizedTariffScheduleCodeUse.harmonizedTariffScheduleCodeUseType.description}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column>
                            <c:url var="deleteUrl" value="/action/Configuration/HarmonizedTariffScheduleCodeUse/Delete">
                                <c:param name="CountryName" value="${harmonizedTariffScheduleCodeUse.harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                <c:param name="HarmonizedTariffScheduleCodeName" value="${harmonizedTariffScheduleCodeUse.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                                <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${harmonizedTariffScheduleCodeUse.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                    </display:table>
                    </c:otherwise>
                </c:choose>
                <br />
            </et:hasSecurityRole>

            <c:set var="entityInstance" scope="request" value="${harmonizedTariffScheduleCode.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
