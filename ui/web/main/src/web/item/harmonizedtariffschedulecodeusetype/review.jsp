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
        <title>
            <fmt:message key="pageTitle.harmonizedTariffScheduleCodeUseType">
                <fmt:param value="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />"><fmt:message key="navigation.items" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/HarmonizedTariffScheduleCodeUseType/Main" />"><fmt:message key="navigation.harmonizedTariffScheduleCodeUseTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.harmonizedTariffScheduleCodeUseType">
                    <fmt:param value="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName != harmonizedTariffScheduleCodeUseType.description}">
                    <p><font size="+2"><b><c:out value="${harmonizedTariffScheduleCodeUseType.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            Related Item Type Name: ${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}<br />
            <br />
            <br />            
            <br />
            <c:set var="entityInstance" scope="request" value="${harmonizedTariffScheduleCodeUseType.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
