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
        <title><fmt:message key="pageTitle.appearanceTextTransformations" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Appearance/Main" />"><fmt:message key="navigation.appearances" /></a> &gt;&gt;
                <fmt:message key="navigation.appearanceTextTransformations" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="TextTransformation.Review" />
            <c:url var="addUrl" value="/action/Core/Appearance/TextTransformationAdd">
                <c:param name="AppearanceName" value="${appearance.appearanceName}" />
            </c:url>
            <p><a href="${addUrl}">Add Text Transformation.</a></p>
            <et:hasSecurityRole securityRole="TextTransformation.Review" var="includeTextTransformationReviewUrl" />
            <display:table name="appearanceTextTransformations" id="appearanceTextTransformation" class="displaytag">
                <display:column titleKey="columnTitle.textTransformation">
                    <c:choose>
                        <c:when test="${includeTextTransformationReviewUrl}">
                            <c:url var="textTransformationReviewUrl" value="/action/Core/TextTransformation/Review">
                                <c:param name="TextTransformationName" value="${appearanceTextTransformation.textTransformation.textTransformationName}" />
                            </c:url>
                            <a href="${textTransformationReviewUrl}"><c:out value="${appearanceTextTransformation.textTransformation.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${appearanceTextTransformation.textTransformation.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="deleteUrl" value="/action/Core/Appearance/TextTransformationDelete">
                        <c:param name="AppearanceName" value="${appearanceTextTransformation.appearance.appearanceName}" />
                        <c:param name="TextTransformationName" value="${appearanceTextTransformation.textTransformation.textTransformationName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
