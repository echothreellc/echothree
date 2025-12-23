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
        <title>Review (<c:out value="${country.geoCodeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Country/Main" />">Countries</a> &gt;&gt;
                Review (<c:out value="${country.geoCodeName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${country.description}" /></b></font></p>
            <br />
            Geo Code Name: ${country.geoCodeName}<br />
            <br />
            Telephone Code:
            <c:choose>
                <c:when test="${country.telephoneCode == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${country.telephoneCode}" />
                </c:otherwise>
            </c:choose>
            <br />
            Area Code Pattern:
            <c:choose>
                <c:when test="${country.areaCodePattern == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${country.areaCodePattern}" />
                </c:otherwise>
            </c:choose>
            <br />
            Area Code Required:
            <c:choose>
                <c:when test="${country.areaCodeRequired}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            Area Code Example:
            <c:choose>
                <c:when test="${country.areaCodeExample == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${country.areaCodeExample}" />
                </c:otherwise>
            </c:choose>
            <br />
            Telephone Number Pattern:
            <c:choose>
                <c:when test="${country.telephoneNumberPattern == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${country.telephoneNumberPattern}" />
                </c:otherwise>
            </c:choose>
            <br />
            Telephone Number Example:
            <c:choose>
                <c:when test="${country.telephoneNumberExample == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${country.telephoneNumberExample}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <c:url var="postalAddressFormatUrl" value="/action/Configuration/PostalAddressFormat/Review">
                <c:param name="PostalAddressFormatName" value="${country.postalAddressFormat.postalAddressFormatName}" />
            </c:url>
            Postal Address Format: <a href="${postalAddressFormatUrl}"><c:out value="${country.postalAddressFormat.description}" /></a><br />
            <br />
            City Required:
            <c:choose>
                <c:when test="${country.cityRequired}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            City Geo Code Required:
            <c:choose>
                <c:when test="${country.cityGeoCodeRequired}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            State Required:
            <c:choose>
                <c:when test="${country.stateRequired}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            State Geo Code Required:
            <c:choose>
                <c:when test="${country.stateGeoCodeRequired}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Postal Code Pattern:
            <c:choose>
                <c:when test="${country.postalCodePattern == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${country.postalCodePattern}" />
                </c:otherwise>
            </c:choose>
            <br />
            Postal Code Required:
            <c:choose>
                <c:when test="${country.postalCodeRequired}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            Postal Code Geo Code Required:
            <c:choose>
                <c:when test="${country.postalCodeGeoCodeRequired}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            Postal Code Length:
            <c:choose>
                <c:when test="${country.postalCodeLength == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${country.postalCodeLength}" />
                </c:otherwise>
            </c:choose>
            <br />
            Postal Code Geo Code Length:
            <c:choose>
                <c:when test="${country.postalCodeGeoCodeLength == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${country.postalCodeGeoCodeLength}" />
                </c:otherwise>
            </c:choose>
            <br />
            Postal Code Example:
            <c:choose>
                <c:when test="${country.postalCodeExample == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${country.postalCodeExample}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <c:if test='${country.geoCodeAliases.size > 0}'>
                <h2>Geo Code Aliases</h2>
                <display:table name="country.geoCodeAliases.collection" id="geoCodeAlias" class="displaytag">
                    <display:column titleKey="columnTitle.geoCodeAliasType">
                        <c:out value="${geoCodeAlias.geoCodeAliasType.description}" />
                    </display:column>
                    <display:column property="alias" titleKey="columnTitle.alias" />
                </display:table>
                <br />
            </c:if>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${country.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
