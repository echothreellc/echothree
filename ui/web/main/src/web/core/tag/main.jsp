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
        <title>Tags</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/TagScope/Main" />">Tag Scopes</a> &gt;&gt;
                Tags
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:Tag.Create:Tag.Edit:Tag.Delete:Tag.Review" />
            <et:hasSecurityRole securityRole="Tag.Create">
                <c:url var="addUrl" value="/action/Core/Tag/Add">
                    <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Tag.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Tag.Review" var="includeReviewUrl" />
            <display:table name="tags.list" id="tag" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/EntityTag/Search">
                                <c:param name="TagScopeName" value="${tag.tagScope.tagScopeName}" />
                                <c:param name="TagName" value="${tag.tagName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${tag.tagName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${tag.tagName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="Tag.Edit:Tag.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="Tag.Edit">
                            <c:url var="editUrl" value="/action/Core/Tag/Edit">
                                <c:param name="TagScopeName" value="${tag.tagScope.tagScopeName}" />
                                <c:param name="OriginalTagName" value="${tag.tagName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Tag.Delete">
                            <c:url var="deleteUrl" value="/action/Core/Tag/Delete">
                                <c:param name="TagScopeName" value="${tag.tagScope.tagScopeName}" />
                                <c:param name="TagName" value="${tag.tagName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${tag.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
