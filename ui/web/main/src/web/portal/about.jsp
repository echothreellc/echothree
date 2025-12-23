<!DOCTYPE html>
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

<%@ include file="../include/taglibs.jsp" %>

<html>
    <head>
        <title><fmt:message key="navigation.about" /></title>
        <%@ include file="../include/environment-b.jsp" %>
    </head>
    <body>
    <%@ include file="../include/body-start-b.jsp" %>
        <%@ include file="../include/breadcrumb/breadcrumbs-start.jsp" %>
            <jsp:include page="../include/breadcrumb/portal.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <li class="breadcrumb-item active" aria-current="page"><fmt:message key="navigation.about" /></li>
        <%@ include file="../include/breadcrumb/breadcrumbs-end.jsp" %>
        <fmt:setBundle basename="echothree-build" var="buildProperties" />
        <p>Git information:</p>
        <ul>
            <li>Branch: <fmt:message key="git.branch" bundle="${buildProperties}" /></li>
            <li>Tag: <fmt:message key="git.tag" bundle="${buildProperties}" /></li>
            <li>Revision: <fmt:message key="git.revision" bundle="${buildProperties}" /></li>
        </ul>
        <p>Build:</p>
        <ul>
            <li>Time: <fmt:message key="build.time" bundle="${buildProperties}" /></li>
            <li>User: <fmt:message key="build.user" bundle="${buildProperties}" /></li>
            <li>Instance: <fmt:message key="build.instance" bundle="${buildProperties}" /></li>
        </ul>
    <%@ include file="../include/body-end-b.jsp" %>
</html>
