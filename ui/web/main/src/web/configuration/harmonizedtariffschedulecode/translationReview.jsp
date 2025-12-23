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
            <fmt:message key="pageTitle.harmonizedTariffScheduleCodeTranslation">
                <fmt:param value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                <fmt:param value="${harmonizedTariffScheduleCodeTranslation.language.languageIsoName}" />
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
                    <c:param name="CountryName" value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                </c:url>
                <a href="${harmonizedTariffScheduleCodeUrl}"><fmt:message key="navigation.harmonizedTariffScheduleCodes" /></a> &gt;&gt;
                <c:url var="translationsUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Translation">
                    <c:param name="CountryName" value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                    <c:param name="HarmonizedTariffScheduleCodeName" value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                </c:url>
                <a href="${translationsUrl}">Translations</a> &gt;&gt;
                Review (<c:out value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />, <c:out value="${harmonizedTariffScheduleCodeTranslation.language.languageIsoName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Country.Review:HarmonizedTariffScheduleCode.Review:Language.Review" />
            <et:hasSecurityRole securityRole="Country.Review" var="includeCountryReviewUrl" />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.Review" var="includeHarmonizedTariffScheduleCodeReviewUrl" />
            <et:hasSecurityRole securityRole="Language.Review" var="includeLanguageReviewUrl" />
            <p><font size="+2"><b><c:out value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" /></b></font></p>
            <br />
            
            Country:
            <c:choose>
                <c:when test="${includeCountryReviewUrl}">
                    <c:url var="trainingClassUrl" value="/action/Configuration/Country/Review">
                        <c:param name="CountryName" value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                    </c:url>
                    <a href="${trainingClassUrl}"><c:out value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.countryGeoCode.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.countryGeoCode.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Harmonized Tariff Schedule Code:
            <c:choose>
                <c:when test="${includeHarmonizedTariffScheduleCodeReviewUrl}">
                    <c:url var="trainingClassUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Review">
                        <c:param name="CountryName" value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                        <c:param name="HarmonizedTariffScheduleCodeName" value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                    </c:url>
                    <a href="${trainingClassUrl}"><c:out value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${harmonizedTariffScheduleCodeTranslation.harmonizedTariffScheduleCode.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Language:
            <c:choose>
                <c:when test="${includeLanguageReviewUrl}">
                    <c:url var="languageUrl" value="/action/Configuration/Language/Review">
                        <c:param name="LanguageIsoName" value="${harmonizedTariffScheduleCodeTranslation.language.languageIsoName}" />
                    </c:url>
                    <a href="${languageUrl}"><c:out value="${harmonizedTariffScheduleCodeTranslation.language.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${harmonizedTariffScheduleCodeTranslation.language.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            <br />
            
            <c:if test='${harmonizedTariffScheduleCodeTranslation.overviewMimeType != null}'>
                <fmt:message key="label.overview" />:<br />
                <table class="displaytag">
                    <tbody>
                        <tr class="odd">
                            <td>
                                <et:out value="${harmonizedTariffScheduleCodeTranslation.overview}" mimeTypeName="${harmonizedTariffScheduleCodeTranslation.overviewMimeType.mimeTypeName}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <br />

                <br />
            </c:if>
            
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
