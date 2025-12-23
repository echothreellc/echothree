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
        <title>Date Time Formats</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                Date Time Formats
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Configuration/DateTimeFormat/Add" />">Add Date Time Format.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="dateTimeFormats" id="dateTimeFormat" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Configuration/DateTimeFormat/Review">
                        <c:param name="DateTimeFormatName" value="${dateTimeFormat.dateTimeFormatName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${dateTimeFormat.dateTimeFormatName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${dateTimeFormat.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${dateTimeFormat.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Configuration/DateTimeFormat/SetDefault">
                                <c:param name="DateTimeFormatName" value="${dateTimeFormat.dateTimeFormatName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/DateTimeFormat/Edit">
                        <c:param name="OriginalDateTimeFormatName" value="${dateTimeFormat.dateTimeFormatName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/DateTimeFormat/Description">
                        <c:param name="DateTimeFormatName" value="${dateTimeFormat.dateTimeFormatName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/DateTimeFormat/Delete">
                        <c:param name="DateTimeFormatName" value="${dateTimeFormat.dateTimeFormatName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${dateTimeFormat.entityInstance.entityRef}" />
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
