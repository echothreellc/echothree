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
        <title><fmt:message key="pageTitle.appearanceTextDecorations" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Appearance/Main" />"><fmt:message key="navigation.appearances" /></a> &gt;&gt;
                <fmt:message key="navigation.appearanceTextDecorations" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="TextDecoration.Review" />
            <c:url var="addUrl" value="/action/Core/Appearance/TextDecorationAdd">
                <c:param name="AppearanceName" value="${appearance.appearanceName}" />
            </c:url>
            <p><a href="${addUrl}">Add Text Decoration.</a></p>
            <et:hasSecurityRole securityRole="TextDecoration.Review" var="includeTextDecorationReviewUrl" />
            <display:table name="appearanceTextDecorations" id="appearanceTextDecoration" class="displaytag">
                <display:column titleKey="columnTitle.textDecoration">
                    <c:choose>
                        <c:when test="${includeTextDecorationReviewUrl}">
                            <c:url var="textDecorationReviewUrl" value="/action/Core/TextDecoration/Review">
                                <c:param name="TextDecorationName" value="${appearanceTextDecoration.textDecoration.textDecorationName}" />
                            </c:url>
                            <a href="${textDecorationReviewUrl}"><c:out value="${appearanceTextDecoration.textDecoration.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${appearanceTextDecoration.textDecoration.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="deleteUrl" value="/action/Core/Appearance/TextDecorationDelete">
                        <c:param name="AppearanceName" value="${appearanceTextDecoration.appearance.appearanceName}" />
                        <c:param name="TextDecorationName" value="${appearanceTextDecoration.textDecoration.textDecorationName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
