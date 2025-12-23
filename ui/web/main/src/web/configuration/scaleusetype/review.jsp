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
            <fmt:message key="pageTitle.scaleUseType">
                <fmt:param value="${scaleUseType.scaleUseTypeName}" />
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
                <a href="<c:url value="/action/Configuration/ScaleUseType/Main" />"><fmt:message key="navigation.scaleUseTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.scaleUseType">
                    <fmt:param value="${scaleUseType.scaleUseTypeName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${scaleUseType.scaleUseTypeName != scaleUseType.description}">
                    <p><font size="+2"><b><c:out value="${scaleUseType.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${scaleUseType.scaleUseTypeName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${scaleUseType.scaleUseTypeName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            Scale Use Type Name: ${scaleUseType.scaleUseTypeName}<br />
            <br />
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${scaleUseType.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
