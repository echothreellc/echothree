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
            <fmt:message key="pageTitle.service">
                <fmt:param value="${service.serviceName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Service/Main" />"><fmt:message key="navigation.services" /></a> &gt;&gt;
                <fmt:message key="navigation.service">
                    <fmt:param value="${service.serviceName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${service.serviceName != service.description}">
                    <p><font size="+2"><b><c:out value="${service.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${service.serviceName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${service.serviceName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            Service Name: ${service.serviceName}<br />
            <br />
            Port <c:out value="${service.port}" /><br />
            Protocol: <c:out value="${service.protocol.description}" /><br />
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${service.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
