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
        <title>Cancellation Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/CancellationPolicy/Main" />">Cancellations</a> &gt;&gt;
                <a href="<c:url value="/action/CancellationPolicy/CancellationKind/Main" />">Cancellation Kinds</a> &gt;&gt;
                <c:url var="cancellationReasonsUrl" value="/action/CancellationPolicy/CancellationReason/Main">
                    <c:param name="CancellationKindName" value="${cancellationReason.cancellationKind.cancellationKindName}" />
                </c:url>
                <a href="${cancellationReasonsUrl}">Cancellation Reasons</a> &gt;&gt;
                Cancellation Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/CancellationPolicy/CancellationReasonType/Add">
                <c:param name="CancellationKindName" value="${cancellationReason.cancellationKind.cancellationKindName}" />
                <c:param name="CancellationReasonName" value="${cancellationReason.cancellationReasonName}" />
            </c:url>
            <p><a href="${addUrl}">Add Cancellation Type.</a></p>
            <display:table name="cancellationReasonTypes" id="cancellationReasonType" class="displaytag">
                <display:column titleKey="columnTitle.cancellationType">
                    <c:url var="cancellationTypeUrl" value="/action/CancellationPolicy/CancellationType/Review">
                        <c:param name="CancellationKindName" value="${cancellationReasonType.cancellationType.cancellationKind.cancellationKindName}" />
                        <c:param name="CancellationTypeName" value="${cancellationReasonType.cancellationType.cancellationTypeName}" />
                    </c:url>
                    <a href="${cancellationTypeUrl}"><c:out value="${cancellationReasonType.cancellationType.description}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${cancellationReasonType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/CancellationPolicy/CancellationReasonType/SetDefault">
                                <c:param name="CancellationKindName" value="${cancellationReasonType.cancellationReason.cancellationKind.cancellationKindName}" />
                                <c:param name="CancellationReasonName" value="${cancellationReasonType.cancellationReason.cancellationReasonName}" />
                                <c:param name="CancellationTypeName" value="${cancellationReasonType.cancellationType.cancellationTypeName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/CancellationPolicy/CancellationReasonType/Edit">
                        <c:param name="CancellationKindName" value="${cancellationReasonType.cancellationReason.cancellationKind.cancellationKindName}" />
                        <c:param name="CancellationReasonName" value="${cancellationReasonType.cancellationReason.cancellationReasonName}" />
                        <c:param name="CancellationTypeName" value="${cancellationReasonType.cancellationType.cancellationTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/CancellationPolicy/CancellationReasonType/Delete">
                        <c:param name="CancellationKindName" value="${cancellationReasonType.cancellationReason.cancellationKind.cancellationKindName}" />
                        <c:param name="CancellationReasonName" value="${cancellationReasonType.cancellationReason.cancellationReasonName}" />
                        <c:param name="CancellationTypeName" value="${cancellationReasonType.cancellationType.cancellationTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
