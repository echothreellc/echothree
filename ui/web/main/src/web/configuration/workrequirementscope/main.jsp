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
        <title>Work Requirement Scopes</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/WorkEffortType/Main" />">Work Effort Types</a> &gt;&gt;
                <c:url var="workEffortScopesUrl" value="/action/Configuration/WorkEffortScope/Main">
                    <c:param name="WorkEffortTypeName" value="${workEffortType.workEffortTypeName}" />
                </c:url>
                <a href="${workEffortScopesUrl}">Work Effort Scopes</a> &gt;&gt;
                Work Requirement Scopes
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="workRequirementScopes" id="workRequirementScope" class="displaytag">
                <display:column>
                    <c:url var="reviewUrl" value="/action/Configuration/WorkRequirementScope/Review">
                        <c:param name="WorkEffortTypeName" value="${workRequirementScope.workEffortScope.workEffortType.workEffortTypeName}" />
                        <c:param name="WorkRequirementTypeName" value="${workRequirementScope.workRequirementType.workRequirementTypeName}" />
                        <c:param name="WorkEffortScopeName" value="${workRequirementScope.workEffortScope.workEffortScopeName}" />
                    </c:url>
                    <a href="${reviewUrl}">Review</a>
                </display:column>
                <display:column titleKey="columnTitle.workEffortScope">
                    <c:url var="workEffortScopeUrl" value="/action/Configuration/WorkEffortScope/Review">
                        <c:param name="WorkEffortTypeName" value="${workRequirementScope.workEffortScope.workEffortType.workEffortTypeName}" />
                        <c:param name="WorkEffortScopeName" value="${workRequirementScope.workEffortScope.workEffortScopeName}" />
                    </c:url>
                    <a href="${workEffortScopeUrl}"><c:out value="${workRequirementScope.workEffortScope.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.workRequirementType">
                    <c:url var="workRequirementTypeUrl" value="/action/Configuration/WorkRequirementType/Review">
                        <c:param name="WorkEffortTypeName" value="${workRequirementScope.workEffortScope.workEffortType.workEffortTypeName}" />
                        <c:param name="WorkRequirementTypeName" value="${workRequirementScope.workRequirementType.workRequirementTypeName}" />
                    </c:url>
                    <a href="${workRequirementTypeUrl}"><c:out value="${workRequirementScope.workRequirementType.description}" /></a>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/WorkRequirementScope/Edit">
                        <c:param name="WorkEffortTypeName" value="${workRequirementScope.workEffortScope.workEffortType.workEffortTypeName}" />
                        <c:param name="WorkRequirementTypeName" value="${workRequirementScope.workRequirementType.workRequirementTypeName}" />
                        <c:param name="WorkEffortScopeName" value="${workRequirementScope.workEffortScope.workEffortScopeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${workRequirementScope.entityInstance.entityRef}" />
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
