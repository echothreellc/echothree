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
        <title>Return Kinds</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/Main" />">Returns</a> &gt;&gt;
                Return Kinds
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/ReturnPolicy/ReturnKind/Add" />">Add Return Kind.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List:ReturnPolicy.List:ReturnReason.List:ReturnType.List" />
            <display:table name="returnKinds" id="returnKind" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/ReturnPolicy/ReturnKind/Review">
                        <c:param name="ReturnKindName" value="${returnKind.returnKindName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${returnKind.returnKindName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${returnKind.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${returnKind.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/ReturnPolicy/ReturnKind/SetDefault">
                                <c:param name="ReturnKindName" value="${returnKind.returnKindName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <et:hasSecurityRole securityRole="ReturnPolicy.List">
                        <c:url var="returnPoliciesUrl" value="/action/ReturnPolicy/ReturnPolicy/Main">
                            <c:param name="ReturnKindName" value="${returnKind.returnKindName}" />
                        </c:url>
                        <a href="${returnPoliciesUrl}">Return Policies</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="ReturnType.List">
                        <c:url var="returnReasonsUrl" value="/action/ReturnPolicy/ReturnReason/Main">
                            <c:param name="ReturnKindName" value="${returnKind.returnKindName}" />
                        </c:url>
                        <a href="${returnReasonsUrl}">Return Reasons</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="ReturnReason.List">
                        <c:url var="returnTypesUrl" value="/action/ReturnPolicy/ReturnType/Main">
                            <c:param name="ReturnKindName" value="${returnKind.returnKindName}" />
                        </c:url>
                        <a href="${returnTypesUrl}">Return Types</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRoles="ReturnPolicy.List:ReturnReason.List:ReturnType.List">
                        <br />
                    </et:hasSecurityRole>
                    <c:url var="editUrl" value="/action/ReturnPolicy/ReturnKind/Edit">
                        <c:param name="OriginalReturnKindName" value="${returnKind.returnKindName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/ReturnPolicy/ReturnKind/Description">
                        <c:param name="ReturnKindName" value="${returnKind.returnKindName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/ReturnPolicy/ReturnKind/Delete">
                        <c:param name="ReturnKindName" value="${returnKind.returnKindName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${returnKind.entityInstance.entityRef}" />
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
