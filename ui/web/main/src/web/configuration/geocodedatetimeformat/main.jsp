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
        <title>Countries</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Country/Main" />">Countries</a> &gt;&gt;
                Date Time Formats
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/GeoCodeDateTimeFormat/Add">
                <c:param name="GeoCodeName" value="${geoCode.geoCodeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Date Time Format.</a></p>
            <display:table name="geoCodeDateTimeFormats" id="geoCodeDateTimeFormat" class="displaytag">
                <display:column titleKey="columnTitle.dateTimeFormat">
                    <c:url var="reviewUrl" value="/action/Configuration/DateTimeFormat/Review">
                        <c:param name="DateTimeFormatName" value="${geoCodeDateTimeFormat.dateTimeFormat.dateTimeFormatName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${geoCodeDateTimeFormat.dateTimeFormat.description}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${geoCodeDateTimeFormat.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Configuration/GeoCodeDateTimeFormat/SetDefault">
                                <c:param name="GeoCodeName" value="${geoCodeDateTimeFormat.geoCode.geoCodeName}" />
                                <c:param name="DateTimeFormatName" value="${geoCodeDateTimeFormat.dateTimeFormat.dateTimeFormatName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/GeoCodeDateTimeFormat/Edit">
                        <c:param name="GeoCodeName" value="${geoCodeDateTimeFormat.geoCode.geoCodeName}" />
                        <c:param name="DateTimeFormatName" value="${geoCodeDateTimeFormat.dateTimeFormat.dateTimeFormatName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Configuration/GeoCodeDateTimeFormat/Delete">
                        <c:param name="GeoCodeName" value="${geoCodeDateTimeFormat.geoCode.geoCodeName}" />
                        <c:param name="DateTimeFormatName" value="${geoCodeDateTimeFormat.dateTimeFormat.dateTimeFormatName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
