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
                <c:url var="harmonizedTariffScheduleCodeUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Main">
                    <c:param name="CountryName" value="${harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                </c:url>
                <a href="${harmonizedTariffScheduleCodeUrl}"><fmt:message key="navigation.harmonizedTariffScheduleCodes" /></a> &gt;&gt;
                <fmt:message key="navigation.harmonizedTariffScheduleCodeUses" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="HarmonizedTariffScheduleCodeUseType.Review" />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUseType.Review" var="includeHarmonizedTariffScheduleCodeUseTypeReviewUrl" />
            <c:url var="addUrl" value="/action/Configuration/HarmonizedTariffScheduleCodeUse/Add">
                <c:param name="CountryName" value="${harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                <c:param name="HarmonizedTariffScheduleCodeName" value="${harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Harmonized Tariff Schedule Code Use.</a></p>
            <display:table name="harmonizedTariffScheduleCodeUses" id="harmonizedTariffScheduleCodeUse" class="displaytag">
                <display:column titleKey="columnTitle.harmonizedTariffScheduleCodeUseType">
                    <c:choose>
                        <c:when test="${includeHarmonizedTariffScheduleCodeUseTypeReviewUrl}">
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
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
