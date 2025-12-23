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
        <title>Review (<c:out value="${workEffortType.workEffortTypeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/WorkEffortType/Main" />">Work Effort Types</a> &gt;&gt;
                Review (<c:out value="${workEffortType.workEffortTypeName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${workEffortType.description}" /></b></font></p>
            <br />
            Work Effort Type Name: <c:out value="${workEffortType.workEffortTypeName}" /><br />
            <br />
            Component Vendor:
            <c:url var="componentVendorUrl" value="/action/Core/ComponentVendor/Review">
                <c:param name="ComponentVendorName" value="${workEffortType.entityType.componentVendor.componentVendorName}" />
            </c:url>
            <a href="${componentVendorUrl}"><c:out value="${workEffortType.entityType.componentVendor.description}" /></a><br />
            Entity Type:
            <c:url var="entityTypeUrl" value="/action/Core/EntityType/Review">
                <c:param name="ComponentVendorName" value="${workEffortType.entityType.componentVendor.componentVendorName}" />
                <c:param name="EntityTypeName" value="${workEffortType.entityType.entityTypeName}" />
            </c:url>
            <a href="${entityTypeUrl}"><c:out value="${workEffortType.entityType.description}" /></a><br />
            <br />
            Work Effort Sequence:
            <c:choose>
                <c:when test="${workEffortType.workEffortSequence == null}">
                    Using Default
                </c:when>
                <c:otherwise>
                    <c:url var="workEffortSequenceUrl" value="/action/Sequence/Sequence/Review">
                        <c:param name="SequenceTypeName" value="${workEffortType.workEffortSequence.sequenceType.sequenceTypeName}" />
                        <c:param name="SequenceName" value="${workEffortType.workEffortSequence.sequenceName}" />
                    </c:url>
                    <a href="${workEffortSequenceUrl}"><c:out value="${workEffortType.workEffortSequence.description}" /></a>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Scheduled Time:
            <c:choose>
                <c:when test="${workEffortType.scheduledTime == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${workEffortType.scheduledTime}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Estimated Time Allowed:
            <c:choose>
                <c:when test="${workEffortType.estimatedTimeAllowed == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${workEffortType.estimatedTimeAllowed}" />
                </c:otherwise>
            </c:choose>
            <br />
            Maximum Time Allowed:
            <c:choose>
                <c:when test="${workEffortType.maximumTimeAllowed == null}">
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${workEffortType.maximumTimeAllowed}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            
            <br />
            <c:set var="entityInstance" scope="request" value="${workEffortType.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
