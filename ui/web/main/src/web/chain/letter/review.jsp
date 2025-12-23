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
        <title>Review (<c:out value="${letter.letterName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/Main" />">Chains</a> &gt;&gt;
                <a href="<c:url value="/action/Chain/ChainKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="chainTypesUrl" value="/action/Chain/ChainType/Main">
                    <c:param name="ChainKindName" value="${letter.chainType.chainKind.chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}">Types</a> &gt;&gt;
                <c:url var="lettersUrl" value="/action/Chain/Letter/Main">
                    <c:param name="ChainKindName" value="${letter.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${letter.chainType.chainTypeName}" />
                </c:url>
                <a href="${lettersUrl}">Letters</a> &gt;&gt;
                Review (<c:out value="${letter.letterName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${letter.description}" /></b></font></p>
            <br />
            Letter Name: ${letter.letterName}<br />
            <br />
            <br />
            <br />
            Created: <c:out value="${letter.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${letter.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${letter.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:if test='${letter.entityInstance.entityTime.deletedTime != null}'>
                Deleted: <c:out value="${letter.entityInstance.entityTime.deletedTime}" /><br />
            </c:if>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${letter.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
