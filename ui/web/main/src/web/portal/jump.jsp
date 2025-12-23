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
        <title><fmt:message key="navigation.portal" /></title>
        <%@ include file="../include/environment-b.jsp" %>
    </head>
    <%@ include file="../include/body-start-b.jsp" %>
        <%@ include file="../include/breadcrumb/breadcrumbs-start.jsp" %>
            <jsp:include page="../include/breadcrumb/portal.jsp">
                <jsp:param name="showAsLink" value="true" />
            </jsp:include>
            <jsp:include page="../include/breadcrumb/jump.jsp" />
        <%@ include file="../include/breadcrumb/breadcrumbs-end.jsp" %>
        <c:choose>
            <c:when test="${entityInstances.size > 0}">
                Found:<br />
                <br />
                <c:forEach items="${entityInstances.list}" var="entityInstance">
                    <c:out value="${entityInstance.entityType.description}" />:
                    <c:set var="entityInstance" scope="request" value="${entityInstance}" />
                    <jsp:include page="../include/targetAsReviewLink.jsp" /><br />
                </c:forEach>
            </c:when>
            <c:otherwise>
                Nothing found.
            </c:otherwise>
        </c:choose>
    <%@ include file="../include/body-end-b.jsp" %>
</html>
