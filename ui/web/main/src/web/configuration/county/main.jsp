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
        <title>Counties</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Country/Main" />">Countries</a> &gt;&gt;
                <c:url var="statesUrl" value="/action/Configuration/State/Main">
                    <c:param name="CountryName" value="${state.country.geoCodeName}" />
                </c:url>
                <a href="${statesUrl}">States</a> &gt;&gt;
                Counties
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Configuration/County/Add" />">Add County.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="counties" id="county" class="displaytag">
                <display:column titleKey="columnTitle.county">
                    <c:url var="reviewUrl" value="/action/Configuration/County/Review">
                        <c:param name="CountryName" value="${county.state.country.geoCodeName}" />
                        <c:param name="StateName" value="${county.state.geoCodeName}" />
                        <c:param name="CountyName" value="${county.geoCodeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${county.description}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${county.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Configuration/County/SetDefault">
                                <c:param name="CountryName" value="${county.state.country.geoCodeName}" />
                                <c:param name="StateName" value="${county.state.geoCodeName}" />
                                <c:param name="CountyName" value="${county.geoCodeName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="aliasesUrl" value="/action/Configuration/GeoCodeAlias/Main">
                        <c:param name="GeoCodeName" value="${county.geoCodeName}" />
                    </c:url>
                    <a href="${aliasesUrl}">Aliases</a><br />
                    <c:url var="editUrl" value="/action/Configuration/County/Edit">
                        <c:param name="CountryName" value="${county.state.country.geoCodeName}" />
                        <c:param name="StateName" value="${county.state.geoCodeName}" />
                        <c:param name="CountyName" value="${county.geoCodeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/GeoCode/Description">
                        <c:param name="GeoCodeName" value="${county.geoCodeName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/County/Delete">
                        <c:param name="CountryName" value="${county.state.country.geoCodeName}" />
                        <c:param name="StateName" value="${county.state.geoCodeName}" />
                        <c:param name="CountyName" value="${county.geoCodeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${county.entityInstance.entityRef}" />
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
