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
        <title>Cancellation Kinds</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/CancellationPolicy/Main" />">Cancellations</a> &gt;&gt;
                Cancellation Kinds
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/CancellationPolicy/CancellationKind/Add" />">Add Cancellation Kind.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List:CancellationPolicy.List:CancellationReason.List:CancellationType.List" />
            <display:table name="cancellationKinds" id="cancellationKind" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/CancellationPolicy/CancellationKind/Review">
                        <c:param name="CancellationKindName" value="${cancellationKind.cancellationKindName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${cancellationKind.cancellationKindName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${cancellationKind.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${cancellationKind.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/CancellationPolicy/CancellationKind/SetDefault">
                                <c:param name="CancellationKindName" value="${cancellationKind.cancellationKindName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <et:hasSecurityRole securityRole="CancellationPolicy.List">
                        <c:url var="cancellationPoliciesUrl" value="/action/CancellationPolicy/CancellationPolicy/Main">
                            <c:param name="CancellationKindName" value="${cancellationKind.cancellationKindName}" />
                        </c:url>
                        <a href="${cancellationPoliciesUrl}">Cancellation Policies</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="CancellationType.List">
                        <c:url var="cancellationReasonsUrl" value="/action/CancellationPolicy/CancellationReason/Main">
                            <c:param name="CancellationKindName" value="${cancellationKind.cancellationKindName}" />
                        </c:url>
                        <a href="${cancellationReasonsUrl}">Cancellation Reasons</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="CancellationReason.List">
                        <c:url var="cancellationTypesUrl" value="/action/CancellationPolicy/CancellationType/Main">
                            <c:param name="CancellationKindName" value="${cancellationKind.cancellationKindName}" />
                        </c:url>
                        <a href="${cancellationTypesUrl}">Cancellation Types</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRoles="CancellationPolicy.List:CancellationReason.List:CancellationType.List">
                        <br />
                    </et:hasSecurityRole>
                    <c:url var="editUrl" value="/action/CancellationPolicy/CancellationKind/Edit">
                        <c:param name="OriginalCancellationKindName" value="${cancellationKind.cancellationKindName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/CancellationPolicy/CancellationKind/Description">
                        <c:param name="CancellationKindName" value="${cancellationKind.cancellationKindName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/CancellationPolicy/CancellationKind/Delete">
                        <c:param name="CancellationKindName" value="${cancellationKind.cancellationKindName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${cancellationKind.entityInstance.entityRef}" />
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
