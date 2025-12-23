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
        <title>Zip Codes</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Country/Main" />">Countries</a> &gt;&gt;
                Zip Codes
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Configuration/ZipCode/Add" />">Add ZipCode.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="zipCodes" id="zipCode" class="displaytag">
                <display:column titleKey="columnTitle.zipCode">
                    <c:url var="reviewUrl" value="/action/Configuration/ZipCode/Review">
                        <c:param name="CountryName" value="${zipCode.country.geoCodeName}" />
                        <c:param name="ZipCodeName" value="${zipCode.geoCodeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${zipCode.description}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${zipCode.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Configuration/ZipCode/SetDefault">
                                <c:param name="CountryName" value="${zipCode.country.geoCodeName}" />
                                <c:param name="ZipCodeName" value="${zipCode.geoCodeName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="aliasesUrl" value="/action/Configuration/GeoCodeAlias/Main">
                        <c:param name="GeoCodeName" value="${zipCode.geoCodeName}" />
                    </c:url>
                    <a href="${aliasesUrl}">Aliases</a><br />
                    <c:url var="editUrl" value="/action/Configuration/ZipCode/Edit">
                        <c:param name="CountryName" value="${zipCode.country.geoCodeName}" />
                        <c:param name="ZipCodeName" value="${zipCode.geoCodeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/GeoCode/Description">
                        <c:param name="GeoCodeName" value="${zipCode.geoCodeName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/ZipCode/Delete">
                        <c:param name="CountryName" value="${zipCode.country.geoCodeName}" />
                        <c:param name="ZipCodeName" value="${zipCode.geoCodeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${zipCode.entityInstance.entityRef}" />
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
