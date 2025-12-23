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
        <title>Review (<c:out value="${tagScope.tagScopeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/TagScope/Main" />">Tag Scopes</a> &gt;&gt;
                Review (<c:out value="${tagScope.tagScopeName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="EntityTag.Search" />
            <et:hasSecurityRole securityRoles="EntityTag.Search">
                <c:set var="hasEntityTagSearch" value="true" />
            </et:hasSecurityRole>
            <c:choose>
                <c:when test="${tagScope.tagScopeName != tagScope.description}">
                    <p><font size="+2"><b><c:out value="${tagScope.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${tagScope.tagScopeName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${tagScope.tagScopeName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            <c:if test="${tagScope.tags.size > 0}">
                <p>
                    <c:forEach items="${tagScope.tags.list}" var="tag">
                        <c:choose>
                            <c:when test="${hasEntityTagSearch}">
                                <c:url var="entityTagsUrl" value="/action/Core/EntityTag/Search">
                                    <c:param name="TagScopeName" value="${tag.tagScope.tagScopeName}" />
                                    <c:param name="TagName" value="${tag.tagName}" />
                                </c:url>
                                <a href="${entityTagsUrl}"><span style="font-size: <fmt:formatNumber value="${((tag.usageCount - minimumUsageCount) / (maximumUsageCount - minimumUsageCount)) * 100 + 90}" maxFractionDigits="0"/>%"><c:out value="${tag.tagName}" /></span></a>
                            </c:when>
                            <c:otherwise>
                                <span style="font-size: <fmt:formatNumber value="${((tag.usageCount - minimumUsageCount) / (maximumUsageCount - minimumUsageCount)) * 100 + 90}" maxFractionDigits="0"/>%"><c:out value="${tag.tagName}" /></span>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </p>
            </c:if>
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${tagScope.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
