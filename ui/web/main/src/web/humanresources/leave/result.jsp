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
        <title>Leave Results</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <fmt:message key="navigation.leaves" /> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Leave/Search" />">Search</a> &gt;&gt
                <fmt:message key="navigation.results" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:containsExecutionError key="UnknownUserVisitSearch">
                <c:set var="unknownUserVisitSearch" value="true" />
            </et:containsExecutionError>
            <c:choose>
                <c:when test="${unknownUserVisitSearch}">
                    <p>Your search results are no longer available, please perform your search again.</p>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${leaveResultCount == null || leaveResultCount < 21}">
                            <display:table name="leaveResults.list" id="leaveResult" class="displaytag" sort="list" requestURI="/action/HumanResources/Leave/Result">
                                <display:column titleKey="columnTitle.name" sortable="true" sortProperty="leave.leaveName">
                                    <c:url var="reviewUrl" value="/action/HumanResources/Leave/Review">
                                        <c:param name="LeaveName" value="${leaveResult.leave.leaveName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${leaveResult.leave.leaveName}" /></a>
                                </display:column>
                                <display:column titleKey="columnTitle.status" sortable="true" sortProperty="leave.leaveStatus.workflowStep.description">
                                    <c:choose>
                                        <c:when test="${leaveResult.leave.leaveStatus.workflowStep.workflowStepName == 'TAKEN' || leaveResult.leave.leaveStatus.workflowStep.workflowStepName == 'NOT_TAKEN'}">
                                            <c:out value="${leaveResult.leave.leaveStatus.workflowStep.description}" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:url var="statusUrl" value="/action/HumanResources/Leave/Status">
                                                <c:param name="ForwardKey" value="Result" />
                                                <c:param name="LeaveName" value="${leaveResult.leave.leaveName}" />
                                            </c:url>
                                            <a href="${statusUrl}"><c:out value="${leaveResult.leave.leaveStatus.workflowStep.description}" /></a>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${leaveResult.leave.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                            <c:if test="${leaveResults.size > 20}">
                                <c:url var="resultsUrl" value="/action/HumanResources/Leave/Result" />
                                <a href="${resultsUrl}">Paged Results</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <display:table name="leaveResults.list" id="leaveResult" class="displaytag" partialList="true" pagesize="20" size="leaveResultCount" requestURI="/action/HumanResources/Leave/Result">
                                <display:column titleKey="columnTitle.name">
                                    <c:url var="reviewUrl" value="/action/HumanResources/Leave/Review">
                                        <c:param name="LeaveName" value="${leaveResult.leave.leaveName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${leaveResult.leave.leaveName}" /></a>
                                </display:column>
                                <display:column titleKey="columnTitle.status" sortable="true" sortProperty="leave.leaveStatus.workflowStep.description">
                                    <c:url var="statusUrl" value="/action/HumanResources/Leave/Status">
                                        <c:param name="ForwardKey" value="Result" />
                                        <c:param name="LeaveName" value="${leaveResult.leave.leaveName}" />
                                    </c:url>
                                    <a href="${statusUrl}"><c:out value="${leaveResult.leave.leaveStatus.workflowStep.description}" /></a>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${leaveResult.leave.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                            <c:url var="resultsUrl" value="/action/HumanResources/Leave/Result">
                                <c:param name="Results" value="Complete" />
                            </c:url>
                            <a href="${resultsUrl}">All Results</a>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
