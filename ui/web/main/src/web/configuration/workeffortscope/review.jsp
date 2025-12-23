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
        <title>Review (<c:out value="${workEffortScope.workEffortScopeName}" />)</title>
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
                    <c:param name="WorkEffortTypeName" value="${workEffortScope.workEffortType.workEffortTypeName}" />
                </c:url>
                <a href="${workEffortScopesUrl}">Work Effort Scopes</a> &gt;&gt;
                Review (<c:out value="${workEffortScope.workEffortScopeName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${workEffortScope.description}" /></b></font></p>
            <br />
            <c:url var="workEffortTypeUrl" value="/action/Configuration/WorkEffortType/Review">
                <c:param name="WorkEffortTypeName" value="${workEffortScope.workEffortType.workEffortTypeName}" />
            </c:url>
            Work Effort Type:<a href="${workEffortTypeUrl}"><c:out value="${workEffortScope.workEffortType.description}" /></a><br />
            Work Effort Scope Name: ${workEffortScope.workEffortScopeName}<br />
            <br />
            Work Effort Sequence:
            <c:choose>
                <c:when test="${workEffortScope.workEffortSequence == null}">
                    Using Default
                </c:when>
                <c:otherwise>
                    <c:url var="workEffortSequenceUrl" value="/action/Sequence/Sequence/Review">
                        <c:param name="SequenceScopeName" value="${workEffortScope.workEffortSequence.sequenceScope.sequenceScopeName}" />
                        <c:param name="SequenceName" value="${workEffortScope.workEffortSequence.sequenceName}" />
                    </c:url>
                    <a href="${workEffortSequenceUrl}"><c:out value="${workEffortScope.workEffortSequence.description}" /></a>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Scheduled Time:
            <c:choose>
                <c:when test="${workEffortScope.scheduledTime == null}">
                    <c:choose>
                        <c:when test="${workEffortScope.workEffortType.scheduledTime == null}">
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            Using Default (<c:out value="${workEffortScope.workEffortType.scheduledTime}" />)
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:out value="${workEffortScope.scheduledTime}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Estimated Time Allowed:
            <c:choose>
                <c:when test="${workEffortScope.estimatedTimeAllowed == null}">
                    <c:choose>
                        <c:when test="${workEffortScope.workEffortType.estimatedTimeAllowed == null}">
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            Using Default (<c:out value="${workEffortScope.workEffortType.estimatedTimeAllowed}" />)
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:out value="${workEffortScope.estimatedTimeAllowed}" />
                </c:otherwise>
            </c:choose>
            <br />
            Maximum Time Allowed: 
            <c:choose>
                <c:when test="${workEffortScope.maximumTimeAllowed == null}">
                    <c:choose>
                        <c:when test="${workEffortScope.workEffortType.maximumTimeAllowed == null}">
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            Using Default (<c:out value="${workEffortScope.workEffortType.maximumTimeAllowed}" />)
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:out value="${workEffortScope.maximumTimeAllowed}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />

            <br />
            <c:set var="entityInstance" scope="request" value="${workEffortScope.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
