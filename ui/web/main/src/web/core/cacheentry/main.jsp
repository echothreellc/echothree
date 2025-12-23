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
                Cache Entries
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="CacheEntryDependency.List" />
            <p><a href="<c:url value="/action/Core/CacheEntry/Remove" />">Remove all cache entries.</a></p>
            <display:table name="cacheEntries" id="cacheEntry" class="displaytag" partialList="true" pagesize="20" size="cacheEntryCount" requestURI="/action/Core/CacheEntry/Main">
                <display:column titleKey="columnTitle.cacheEntryKey">
                    <c:out value="${cacheEntry.cacheEntryKey}" />
                </display:column>
                <display:column titleKey="columnTitle.mimeType">
                    <c:out value="${cacheEntry.mimeType.description}" /> (<c:out value="${cacheEntry.mimeType.mimeTypeName}" />)
                </display:column>
                <display:column titleKey="columnTitle.createdTime">
                    <c:out value="${cacheEntry.createdTime}" />
                </display:column>
                <display:column titleKey="columnTitle.validUntilTime">
                    <c:out value="${cacheEntry.validUntilTime}" />
                </display:column>
                <display:column>
                    <et:hasSecurityRole securityRole="CacheEntryDependency.List">
                        <c:url var="dependenciesUrl" value="/action/Core/CacheEntry/Dependency">
                            <c:param name="CacheEntryKey" value="${cacheEntry.cacheEntryKey}" />
                        </c:url>
                        <a href="${dependenciesUrl}">Dependencies</a>
                    </et:hasSecurityRole>
                    <c:url var="removeUrl" value="/action/Core/CacheEntry/Remove">
                        <c:param name="CacheEntryKey" value="${cacheEntry.cacheEntryKey}" />
                    </c:url>
                    <a href="${removeUrl}">Remove</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
