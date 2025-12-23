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
        <title>Review (${workRequirementScope.workEffortScope.workEffortScopeName}:${workRequirementScope.workRequirementType.workRequirementTypeName})</title>
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
                    <c:param name="WorkEffortTypeName" value="${workRequirementScope.workEffortScope.workEffortType.workEffortTypeName}" />
                </c:url>
                <a href="${workEffortScopesUrl}">Work Effort Scopes</a> &gt;&gt;
                <c:url var="workRequirementScopesUrl" value="/action/Configuration/WorkRequirementScope/Main">
                    <c:param name="WorkEffortTypeName" value="${workRequirementScope.workEffortScope.workEffortType.workEffortTypeName}" />
                    <c:param name="WorkEffortScopeName" value="${workRequirementScope.workEffortScope.workEffortScopeName}" />
                </c:url>
                <a href="${workRequirementScopesUrl}">Work Requirement Scopes</a> &gt;&gt;
                Review (${workRequirementScope.workEffortScope.workEffortScopeName}:${workRequirementScope.workRequirementType.workRequirementTypeName})
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${workRequirementScope.workEffortScope.description}:${workRequirementScope.workRequirementType.description}" /></b></font></p>
            <br />
            <c:url var="workEffortScopeUrl" value="/action/Configuration/WorkEffortScope/Review">
                <c:param name="WorkEffortTypeName" value="${workRequirementScope.workEffortScope.workEffortType.workEffortTypeName}" />
                <c:param name="WorkEffortScopeName" value="${workRequirementScope.workEffortScope.workEffortScopeName}" />
            </c:url>
            Work Effort Scope: <a href="${workEffortScopeUrl}"><c:out value="${workRequirementScope.workEffortScope.description}" /></a><br />
            <c:url var="workRequirementTypeUrl" value="/action/Configuration/WorkRequirementType/Review">
                <c:param name="WorkEffortTypeName" value="${workRequirementScope.workEffortScope.workEffortType.workEffortTypeName}" />
                <c:param name="WorkRequirementTypeName" value="${workRequirementScope.workRequirementType.workRequirementTypeName}" />
            </c:url>
            Work Requirement Type: <a href="${workRequirementTypeUrl}"><c:out value="${workRequirementScope.workRequirementType.description}" /></a><br />
            <br />
            Work Requirement Sequence:
            <c:choose>
                <c:when test="${workRequirementScope.workRequirementSequence == null}">
                    Using Default
                </c:when>
                <c:otherwise>
                    <c:url var="workRequirementSequenceUrl" value="/action/Sequence/Sequence/Review">
                        <c:param name="SequenceTypeName" value="${workRequirementScope.workRequirementSequence.sequenceType.sequenceTypeName}" />
                        <c:param name="SequenceName" value="${workRequirementScope.workRequirementSequence.sequenceName}" />
                    </c:url>
                    <a href="${workRequirementSequenceUrl}"><c:out value="${workRequirementScope.workRequirementSequence.description}" /></a>
                </c:otherwise>
            </c:choose>
            <br />
            Work Time Sequence:
            <c:choose>
                <c:when test="${workRequirementScope.workTimeSequence == null}">
                    Using Default
                </c:when>
                <c:otherwise>
                    <c:url var="workTimeSequenceUrl" value="/action/Sequence/Sequence/Review">
                        <c:param name="SequenceTypeName" value="${workRequirementScope.workTimeSequence.sequenceType.sequenceTypeName}" />
                        <c:param name="SequenceName" value="${workRequirementScope.workTimeSequence.sequenceName}" />
                    </c:url>
                    <a href="${workTimeSequenceUrl}"><c:out value="${workRequirementScope.workTimeSequence.description}" /></a>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Work Assignment Selector:
            <c:choose>
                <c:when test="${workRequirementScope.workAssignmentSelector == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:url var="workAssignmentSelectorUrl" value="/action/Sequence/Sequence/Review">
                        <c:param name="SelectorKindName" value="${workRequirementScope.workAssignmentSelector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${workRequirementScope.workAssignmentSelector.selectorType.selectorTypeName}" />
                        <c:param name="SelectorName" value="${workRequirementScope.workAssignmentSelector.selectorName}" />
                    </c:url>
                    <a href="${workAssignmentSelectorUrl}"><c:out value="${workRequirementScope.workAssignmentSelector.description}" /></a>
                    
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Estimated Time Allowed:
            <c:choose>
                <c:when test="${workRequirementScope.estimatedTimeAllowed == null}">
                    <c:choose>
                        <c:when test="${workRequirementScope.workRequirementType.estimatedTimeAllowed == null}">
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            Using Default (<c:out value="${workRequirementScope.workRequirementType.estimatedTimeAllowed}" />)
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:out value="${workRequirementScope.estimatedTimeAllowed}" />
                </c:otherwise>
            </c:choose>
            <br />
            Maximum Time Allowed: 
            <c:choose>
                <c:when test="${workRequirementScope.maximumTimeAllowed == null}">
                    <c:choose>
                        <c:when test="${workRequirementScope.workRequirementType.maximumTimeAllowed == null}">
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            Using Default (<c:out value="${workRequirementScope.workRequirementType.maximumTimeAllowed}" />)
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:out value="${workRequirementScope.maximumTimeAllowed}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <br />
            Created: <c:out value="${workRequirementScope.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${workRequirementScope.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${workRequirementScope.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                <c:param name="EntityRef" value="${workRequirementScope.entityInstance.entityRef}" />
            </c:url>
            <a href="${eventsUrl}">Events</a>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
