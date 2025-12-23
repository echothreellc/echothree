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
        <title>Work Effort Scopes</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/WorkEffortType/Main" />">Work Effort Types</a> &gt;&gt;
                Work Effort Scopes
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:WorkRequirementScope.List:WorkEffortScope.Create:WorkEffortScope.Edit:WorkEffortScope.Delete:WorkEffortScope.Review:WorkEffortScope.Description" />
            <et:hasSecurityRole securityRoles="WorkRequirementScope.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="WorkEffortScope.Edit:WorkEffortScope.Description:WorkEffortScope.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="WorkEffortScope.Create">
                <c:url var="addUrl" value="/action/Configuration/WorkEffortScope/Add">
                    <c:param name="WorkEffortTypeName" value="${workEffortType.workEffortTypeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Work Effort Scope.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="WorkEffortScope.Review" var="includeReviewUrl" />
            <display:table name="workEffortScopes" id="workEffortScope" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/WorkEffortScope/Review">
                                <c:param name="WorkEffortTypeName" value="${workEffortScope.workEffortType.workEffortTypeName}" />
                                <c:param name="WorkEffortScopeName" value="${workEffortScope.workEffortScopeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${workEffortScope.workEffortScopeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${workEffortScope.workEffortScopeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${workEffortScope.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${workEffortScope.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="WorkEffortScope.Edit">
                                <c:url var="setDefaultUrl" value="/action/Configuration/WorkEffortScope/SetDefault">
                                    <c:param name="WorkEffortTypeName" value="${workEffortScope.workEffortType.workEffortTypeName}" />
                                    <c:param name="WorkEffortScopeName" value="${workEffortScope.workEffortScopeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="WorkRequirementScope.List">
                            <c:url var="workEffortScopePagesUrl" value="/action/Configuration/WorkRequirementScope/Main">
                                <c:param name="WorkEffortTypeName" value="${workEffortScope.workEffortType.workEffortTypeName}" />
                                <c:param name="WorkEffortScopeName" value="${workEffortScope.workEffortScopeName}" />
                            </c:url>
                            <a href="${workEffortScopePagesUrl}">Work Requirement Scopes</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="WorkEffortScope.Edit">
                            <c:url var="editUrl" value="/action/Configuration/WorkEffortScope/Edit">
                                <c:param name="WorkEffortTypeName" value="${workEffortScope.workEffortType.workEffortTypeName}" />
                                <c:param name="OriginalWorkEffortScopeName" value="${workEffortScope.workEffortScopeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="WorkEffortScope.Description">
                            <c:url var="descriptionsUrl" value="/action/Configuration/WorkEffortScope/Description">
                                <c:param name="WorkEffortTypeName" value="${workEffortScope.workEffortType.workEffortTypeName}" />
                                <c:param name="WorkEffortScopeName" value="${workEffortScope.workEffortScopeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="WorkEffortScope.Delete">
                            <c:url var="deleteUrl" value="/action/Configuration/WorkEffortScope/Delete">
                                <c:param name="WorkEffortTypeName" value="${workEffortScope.workEffortType.workEffortTypeName}" />
                                <c:param name="WorkEffortScopeName" value="${workEffortScope.workEffortScopeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${workEffortScope.entityInstance.entityRef}" />
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
