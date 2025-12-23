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
        <title><fmt:message key="pageTitle.geoCodeTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <fmt:message key="navigation.geoCodeTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:GeoCodeType.Create:GeoCodeType.Edit:GeoCodeType.Delete:GeoCodeType.Review:GeoCodeType.Description:GeoCodeAliasType.List" />
            <et:hasSecurityRole securityRoles="GeoCodeAliasType.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="GeoCodeType.Edit:GeoCodeType.Description:GeoCodeType.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="GeoCodeType.Create">
                <p><a href="<c:url value="/action/Configuration/GeoCodeType/Add" />">Add Geo Code Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="GeoCodeType.Review" var="includeReviewUrl" />
            <display:table name="geoCodeTypes" id="geoCodeType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/GeoCodeType/Review">
                                <c:param name="GeoCodeTypeName" value="${geoCodeType.geoCodeTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${geoCodeType.geoCodeTypeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${geoCodeType.geoCodeTypeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${geoCodeType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${geoCodeType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="GeoCodeType.Edit">
                                <c:url var="setDefaultUrl" value="/action/Configuration/GeoCodeType/SetDefault">
                                    <c:param name="ParentGeoCodeTypeName" value="${geoCodeType.parentGeoCodeType.geoCodeTypeName}" />
                                    <c:param name="GeoCodeTypeName" value="${geoCodeType.geoCodeTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="GeoCodeAliasType.List:GeoCodeType.Edit:GeoCodeType.Description:GeoCodeType.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="GeoCodeAliasType.List">
                            <c:url var="geoCodeAliasTypeUrl" value="/action/Configuration/GeoCodeAliasType/Main">
                                <c:param name="GeoCodeTypeName" value="${geoCodeType.geoCodeTypeName}" />
                            </c:url>
                            <a href="${geoCodeAliasTypeUrl}">Alias Types</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="GeoCodeType.Edit">
                            <c:url var="editUrl" value="/action/Configuration/GeoCodeType/Edit">
                                <c:param name="OriginalGeoCodeTypeName" value="${geoCodeType.geoCodeTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="GeoCodeType.Description">
                            <c:url var="descriptionsUrl" value="/action/Configuration/GeoCodeType/Description">
                                <c:param name="GeoCodeTypeName" value="${geoCodeType.geoCodeTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="GeoCodeType.Delete">
                            <c:url var="deleteUrl" value="/action/Configuration/GeoCodeType/Delete">
                                <c:param name="GeoCodeTypeName" value="${geoCodeType.geoCodeTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${geoCodeType.entityInstance.entityRef}" />
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
