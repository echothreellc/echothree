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
        <title>Geo Code Alias Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/GeoCodeType/Main" />">Geo Code Types</a> &gt;&gt;
                Geo Code Alias Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/GeoCodeAliasType/Add">
                <c:param name="GeoCodeTypeName" value="${geoCodeType.geoCodeTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Geo Code Alias Type.</a></p>
            <display:table name="geoCodeAliasTypes" id="geoCodeAliasType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Configuration/GeoCodeAliasType/Review">
                        <c:param name="GeoCodeTypeName" value="${geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
                        <c:param name="GeoCodeAliasTypeName" value="${geoCodeAliasType.geoCodeAliasTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${geoCodeAliasType.geoCodeAliasTypeName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${geoCodeAliasType.description}" />
                </display:column>
                <display:column property="validationPattern" titleKey="columnTitle.validationPattern" />
                <display:column titleKey="columnTitle.required">
                    <c:choose>
                        <c:when test="${geoCodeAliasType.isRequired}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${geoCodeAliasType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Configuration/GeoCodeAliasType/SetDefault">
                                <c:param name="GeoCodeTypeName" value="${geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
                                <c:param name="GeoCodeAliasTypeName" value="${geoCodeAliasType.geoCodeAliasTypeName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/GeoCodeAliasType/Edit">
                        <c:param name="GeoCodeTypeName" value="${geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
                        <c:param name="OriginalGeoCodeAliasTypeName" value="${geoCodeAliasType.geoCodeAliasTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/GeoCodeAliasType/Description">
                        <c:param name="GeoCodeTypeName" value="${geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
                        <c:param name="GeoCodeAliasTypeName" value="${geoCodeAliasType.geoCodeAliasTypeName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/GeoCodeAliasType/Delete">
                        <c:param name="GeoCodeTypeName" value="${geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
                        <c:param name="GeoCodeAliasTypeName" value="${geoCodeAliasType.geoCodeAliasTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
