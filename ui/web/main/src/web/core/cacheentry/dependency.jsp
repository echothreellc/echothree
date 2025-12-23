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
        <title>Cache Entries</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/CacheEntry/Main" />">Cache Entries</a> &gt;&gt;
                Dependencies
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="ComponentVendor.Review:EntityType.Review" />
            <et:hasSecurityRole securityRole="ComponentVendor.Review" var="includeComponentVendorReviewUrl" />
            <et:hasSecurityRole securityRole="EntityType.Review" var="includeEntityTypeReviewUrl" />
            Cache Entry: <c:out value="${cacheEntry.cacheEntryKey}" /><br />
            <br />
            <display:table name="cacheEntryDependencies" id="cacheEntryDependency" class="displaytag">
                <display:column titleKey="columnTitle.componentVendor">
                    <c:choose>
                        <c:when test="${includeComponentVendorReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/ComponentVendor/Review">
                                <c:param name="ComponentVendorName" value="${cacheEntryDependency.entityInstance.entityType.componentVendor.componentVendorName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${cacheEntryDependency.entityInstance.entityType.componentVendor.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${cacheEntryDependency.entityInstance.entityType.componentVendor.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.entityType">
                    <c:choose>
                        <c:when test="${includeEntityTypeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/EntityType/Review">
                                <c:param name="ComponentVendorName" value="${cacheEntryDependency.entityInstance.entityType.entityTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${cacheEntryDependency.entityInstance.entityType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${cacheEntryDependency.entityInstance.entityType.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.entityInstance">
                    <c:set var="entityInstance" scope="request" value="${cacheEntryDependency.entityInstance}" />
                    <jsp:include page="../../include/targetAsReviewLink.jsp" />
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
