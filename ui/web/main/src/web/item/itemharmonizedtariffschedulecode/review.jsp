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
        <title>Item Harmonized Tariff Schedule Codes (<c:out value="${itemHarmonizedTariffScheduleCode.item.itemName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />">Items</a> &gt;&gt;
                <a href="<c:url value="/action/Item/Item/Main" />">Search</a> &gt;&gt;
                <et:countItemResults searchTypeName="ITEM_MAINTENANCE" countVar="itemResultsCount" commandResultVar="countItemResultsCommandResult" logErrors="false" />
                <c:if test="${itemResultsCount > 0}">
                    <a href="<c:url value="/action/Item/Item/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Item/Item/Review">
                    <c:param name="ItemName" value="${itemHarmonizedTariffScheduleCode.item.itemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${itemHarmonizedTariffScheduleCode.item.itemName}" />)</a> &gt;&gt;
                <c:url var="itemHarmonizedTariffScheduleCodesUrl" value="/action/Item/ItemHarmonizedTariffScheduleCode/Main">
                    <c:param name="ItemName" value="${itemHarmonizedTariffScheduleCode.item.itemName}" />
                </c:url>
                <a href="${itemHarmonizedTariffScheduleCodesUrl}">Harmonized Tariff Schedule Codes</a> &gt;&gt;
                Review
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Item.Review:Country.Review:HarmonizedTariffScheduleCodeUseType.Review:HarmonizedTariffScheduleCode.Review" />
            <et:hasSecurityRole securityRole="Item.Review" var="includeItemUrl" />
            <et:hasSecurityRole securityRole="Country.Review" var="includeCountryUrl" />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUseType.Review" var="includeHarmonizedTariffScheduleCodeUseTypeUrl" />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.Review" var="includeHarmonizedTariffScheduleCodeUrl" />
            <c:choose>
                <c:when test="$itemHarmonizedTariffScheduleCode.{harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName != itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.description}">
                    <p><font size="+2"><b><c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            Item:
            <c:choose>
                <c:when test="${includeItemUrl}">
                    <c:url var="reviewUrl" value="/action/Item/Item/Review">
                        <c:param name="ItemName" value="${itemHarmonizedTariffScheduleCode.item.itemName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${itemHarmonizedTariffScheduleCode.item.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${itemHarmonizedTariffScheduleCode.item.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Country:
            <c:choose>
                <c:when test="${includeCountryUrl}">
                    <c:url var="reviewUrl" value="/action/Configuration/Country/Review">
                        <c:param name="CountryName" value="${itemHarmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${itemHarmonizedTariffScheduleCode.countryGeoCode.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${itemHarmonizedTariffScheduleCode.countryGeoCode.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Harmonized Tariff Schedule Code Use Type:
            <c:choose>
                <c:when test="${includeHarmonizedTariffScheduleCodeUseTypeUrl}">
                    <c:url var="reviewUrl" value="/action/Item/HarmonizedTariffScheduleCodeUseType/Review">
                        <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            
            Harmonized Tariff Schedule Code:
            <c:choose>
                <c:when test="${includeHarmonizedTariffScheduleCodeUrl}">
                    <c:url var="reviewUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Review">
                        <c:param name="CountryName" value="${itemHarmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                        <c:param name="HarmonizedTariffScheduleCodeName" value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />

            <c:set var="tagScopes" scope="request" value="${itemHarmonizedTariffScheduleCode.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${itemHarmonizedTariffScheduleCode.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${itemHarmonizedTariffScheduleCode.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Item/ItemHarmonizedTariffScheduleCode/Review">
                <c:param name="ItemName" value="${itemHarmonizedTariffScheduleCode.item.itemName}" />
                <c:param name="CountryName" value="${itemHarmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
