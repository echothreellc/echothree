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
        <title>Review (<c:out value="${dateTimeFormat.dateTimeFormatName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/DateTimeFormat/Main" />">Date Time Formats</a> &gt;&gt;
                Review (<c:out value="${dateTimeFormat.dateTimeFormatName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${dateTimeFormat.description}" /></b></font></p>
            <br />
            Date Time Format Name: ${dateTimeFormat.dateTimeFormatName}<br />
            <br />
            <h2>Java</h2>
            Short Date Format: <c:out value="${dateTimeFormat.javaShortDateFormat}" /><br />
            Abbrev Date Format: <c:out value="${dateTimeFormat.javaAbbrevDateFormat}" /><br />
            Abbrev Date Format Weekday: <c:out value="${dateTimeFormat.javaAbbrevDateFormatWeekday}" /><br />
            Long Date Format: <c:out value="${dateTimeFormat.javaLongDateFormat}" /><br />
            Long Date Format Weekday: <c:out value="${dateTimeFormat.javaLongDateFormatWeekday}" /><br />
            Time Format: <c:out value="${dateTimeFormat.javaTimeFormat}" /><br />
            Time Format Seconds: <c:out value="${dateTimeFormat.javaTimeFormatSeconds}" /><br />
            <br />
            <h2>Unix</h2>
            Short Date Format: <c:out value="${dateTimeFormat.unixShortDateFormat}" /><br />
            Abbrev Date Format: <c:out value="${dateTimeFormat.unixAbbrevDateFormat}" /><br />
            Abbrev Date Format Weekday: <c:out value="${dateTimeFormat.unixAbbrevDateFormatWeekday}" /><br />
            Long Date Format: <c:out value="${dateTimeFormat.unixLongDateFormat}" /><br />
            Long Date Format Weekday: <c:out value="${dateTimeFormat.unixLongDateFormatWeekday}" /><br />
            Time Format: <c:out value="${dateTimeFormat.unixTimeFormat}" /><br />
            Time Format Seconds: <c:out value="${dateTimeFormat.unixTimeFormatSeconds}" /><br />
            <br />
            <h2>Miscellaneous</h2>
            Short Date Separator: <c:out value="${dateTimeFormat.shortDateSeparator}" /><br />
            Time Separator: <c:out value="${dateTimeFormat.timeSeparator}" /><br />
            <br />
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${dateTimeFormat.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
