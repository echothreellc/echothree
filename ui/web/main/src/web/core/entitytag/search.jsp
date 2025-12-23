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
        <title>Entity Tags (<c:out value="${tag.tagName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/TagScope/Main" />">Tag Scopes</a> &gt;&gt;
                <c:url var="tagsUrl" value="/action/Core/Tag/Main">
                    <c:param name="TagScopeName" value="${tag.tagScope.tagScopeName}" />
                </c:url>
                <a href="${tagsUrl}">Tags</a> &gt;&gt;
                Entity Tags &gt;&gt;
                Search (<c:out value="${tag.tagName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="TagScope.Review:EntityType.Review:EntityType.Delete" />
            <et:hasSecurityRole securityRole="TagScope.Review" var="includeTagScopeReviewUrl" />
            <et:hasSecurityRole securityRole="EntityType.Review" var="includeEntityTypeReviewUrl" />
            <p><font size="+2"><b><c:out value="${tag.tagName}" /></b></font></p>
            <br />
            Tag Scope:
            <c:choose>
                <c:when test="${includeTagScopeReviewUrl}">
                    <c:url var="tagScopeUrl" value="/action/Core/TagScope/Review">
                        <c:param name="TagScopeName" value="${tag.tagScope.tagScopeName}" />
                    </c:url>
                    <a href="${tagScopeUrl}"><c:out value="${tag.tagScope.description}" /></a>
                </c:when>
                <c:otherwise>
                    <<c:out value="${tag.tagScope.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <display:table name="entityTags.list" id="entityTag" class="displaytag" export="true" sort="list" requestURI="/action/Core/EntityTag/Search">
                <display:setProperty name="export.csv.filename" value="EntityTags.csv" />
                <display:setProperty name="export.excel.filename" value="EntityTags.xls" />
                <display:setProperty name="export.pdf.filename" value="EntityTags.pdf" />
                <display:setProperty name="export.rtf.filename" value="EntityTags.rtf" />
                <display:setProperty name="export.xml.filename" value="EntityTags.xml" />
                <display:column titleKey="columnTitle.entityType" media="html" sortable="true" sortProperty="taggedEntityInstance.entityType.description">
                    <c:choose>
                        <c:when test="${includeEntityTypeReviewUrl}">
                            <c:url var="entityTypeUrl" value="/action/Core/EntityType/Review">
                                <c:param name="ComponentVendorName" value="${entityTag.taggedEntityInstance.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityTag.taggedEntityInstance.entityType.entityTypeName}" />
                            </c:url>
                            <a href="${entityTypeUrl}"><c:out value="${entityTag.taggedEntityInstance.entityType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${entityTag.taggedEntityInstance.entityType.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.taggedEntityInstance" media="html" sortable="true" sortProperty="taggedEntityInstance.description">
                    <c:set var="entityInstance" scope="request" value="${entityTag.taggedEntityInstance}" />
                    <jsp:include page="../../include/targetAsReviewLink.jsp" />
                    <jsp:include page="../../include/targetAsEditLink.jsp" />
                </display:column>
                <et:hasSecurityRole securityRole="EntityType.Delete">
                    <display:column media="html">
                        <c:url var="returnUrl" scope="request" value="/../action/Core/EntityTag/Search">
                            <c:param name="TagScopeName" value="${entityTag.tag.tagScope.tagScopeName}" />
                            <c:param name="TagName" value="${entityTag.tag.tagName}" />
                        </c:url>
                        <c:url var="deleteUrl" value="/action/Core/EntityTag/Delete">
                            <c:param name="TagScopeName" value="${entityTag.tag.tagScope.tagScopeName}" />
                            <c:param name="ReturnUrl" value="${returnUrl}" />
                            <c:param name="EntityRef" value="${entityTag.taggedEntityInstance.entityRef}" />
                            <c:param name="TagName" value="${entityTag.tag.tagName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </display:column>
                </et:hasSecurityRole>
                <display:column property="taggedEntityInstance.entityType.description" titleKey="columnTitle.entityType" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.name" media="csv excel pdf rtf xml">
                    <c:set var="entityInstance" scope="request" value="${entityTag.taggedEntityInstance}" />
                    <jsp:include page="../../include/targetAsName.jsp" />
                </display:column>
                <display:column titleKey="columnTitle.taggedEntityInstance" media="csv excel pdf rtf xml">
                    <c:set var="entityInstance" scope="request" value="${entityTag.taggedEntityInstance}" />
                    <jsp:include page="../../include/targetAsText.jsp" />
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
