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
        <title>Tag Scopes</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                Tag Scopes
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:TagScopeEntityType.List:Tag.List:TagScope.Create:TagScope.Edit:TagScope.Delete:TagScope.Review:TagScope.Description" />
            <et:hasSecurityRole securityRoles="TagScopeEntityType.List:Tag.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="TagScope.Edit:TagScope.Description:TagScope.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TagScope.Create">
                <p><a href="<c:url value="/action/Core/TagScope/Add" />">Add Tag Scope.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TagScope.Review" var="includeReviewUrl" />
            <display:table name="tagScopes" id="tagScope" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/TagScope/Review">
                                <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${tagScope.tagScopeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${tagScope.tagScopeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${tagScope.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${tagScope.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="TagScope.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/TagScope/SetDefault">
                                    <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="TagScopeEntityType.List:Tag.List:TagScope.Edit:TagScope.Description:TagScope.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="TagScopeEntityType.List">
                            <c:url var="tagScopeEntityTypeUrl" value="/action/Core/TagScopeEntityType/Main">
                                <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                            </c:url>
                            <a href="${tagScopeEntityTypeUrl}">Entity Types</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Tag.List">
                            <c:url var="tagsUrl" value="/action/Core/Tag/Main">
                                <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                            </c:url>
                            <a href="${tagsUrl}">Tags</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="TagScope.Edit">
                            <c:url var="editUrl" value="/action/Core/TagScope/Edit">
                                <c:param name="OriginalTagScopeName" value="${tagScope.tagScopeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TagScope.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/TagScope/Description">
                                <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TagScope.Delete">
                            <c:url var="deleteUrl" value="/action/Core/TagScope/Delete">
                                <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${tagScope.entityInstance.entityRef}" />
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
