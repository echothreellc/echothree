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
        <title>Item Harmonized Tariff Schedule Codes (<c:out value="${item.itemName}" />)</title>
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
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${item.itemName}" />)</a> &gt;&gt;
                Harmonized Tariff Schedule Codes
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="ItemHarmonizedTariffScheduleCode.Review:Country.Review:HarmonizedTariffScheduleCodeUseType.Review:HarmonizedTariffScheduleCode.Review:ItemHarmonizedTariffScheduleCode.Edit:ItemHarmonizedTariffScheduleCode.Delete:ItemHarmonizedTariffScheduleCode.Create" />
            <et:hasSecurityRole securityRole="ItemHarmonizedTariffScheduleCode.Review" var="includeItemHarmonizedTariffScheduleCodeUrl" />
            <et:hasSecurityRole securityRole="Country.Review" var="includeCountryUrl" />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUseType.Review" var="includeHarmonizedTariffScheduleCodeUseTypeUrl" />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCode.Review" var="includeHarmonizedTariffScheduleCodeUrl" />
            <et:hasSecurityRole securityRoles="ItemHarmonizedTariffScheduleCode.Edit:ItemHarmonizedTariffScheduleCode.Delete">
                <c:set var="linksInItemHarmonizedTariffScheduleCodeFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemHarmonizedTariffScheduleCode.Create">
                <c:url var="addUrl" value="/action/Item/ItemHarmonizedTariffScheduleCode/Add">
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <p><a href="${addUrl}">Add Harmonized Tariff Schedule Code.</a></p>
            </et:hasSecurityRole>
            <display:table name="itemHarmonizedTariffScheduleCodes" id="itemHarmonizedTariffScheduleCode" class="displaytag">
                <et:hasSecurityRole securityRole="ItemHarmonizedTariffScheduleCode.Review">
                    <display:column>
                        <c:url var="reviewUrl" value="/action/Item/ItemHarmonizedTariffScheduleCode/Review">
                            <c:param name="ItemName" value="${itemHarmonizedTariffScheduleCode.item.itemName}" />
                            <c:param name="CountryName" value="${itemHarmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                            <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                        </c:url>
                        <a href="${reviewUrl}">Review</a>
                    </display:column>
                </et:hasSecurityRole>
                <display:column titleKey="columnTitle.country">
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
                </display:column>
                <display:column titleKey="columnTitle.harmonizedTariffScheduleCodeUseType">
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
                </display:column>
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeHarmonizedTariffScheduleCodeUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/HarmonizedTariffScheduleCode/Review">
                                <c:param name="CountryName" value="${itemHarmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                <c:param name="HarmonizedTariffScheduleCodeName" value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.harmonizedTariffScheduleCodeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCode.description}" />
                </display:column>
                <c:if test='${linksInItemHarmonizedTariffScheduleCodeFirstRow}'>
                    <display:column>
                        <et:hasSecurityRole securityRole="ItemHarmonizedTariffScheduleCode.Edit">
                            <c:url var="editUrl" value="/action/Item/ItemHarmonizedTariffScheduleCode/Edit">
                                <c:param name="ItemName" value="${itemHarmonizedTariffScheduleCode.item.itemName}" />
                                <c:param name="CountryName" value="${itemHarmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ItemHarmonizedTariffScheduleCode.Delete">
                            <c:url var="deleteUrl" value="/action/Item/ItemHarmonizedTariffScheduleCode/Delete">
                                <c:param name="ItemName" value="${itemHarmonizedTariffScheduleCode.item.itemName}" />
                                <c:param name="CountryName" value="${itemHarmonizedTariffScheduleCode.countryGeoCode.geoCodeName}" />
                                <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${itemHarmonizedTariffScheduleCode.harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
