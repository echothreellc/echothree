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
        <title>Return Policies</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/Main" />">Returns</a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/ReturnKind/Main" />">Return Kinds</a> &gt;&gt;
                Return Policies
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/ReturnPolicy/ReturnPolicy/Add">
                <c:param name="ReturnKindName" value="${returnKind.returnKindName}" />
            </c:url>
            <p><a href="${addUrl}">Add Return Policy.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List:ReturnPolicyReason.List" />
            <display:table name="returnPolicies" id="returnPolicy" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/ReturnPolicy/ReturnPolicy/Review">
                        <c:param name="ReturnKindName" value="${returnPolicy.returnKind.returnKindName}" />
                        <c:param name="ReturnPolicyName" value="${returnPolicy.returnPolicyName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${returnPolicy.returnPolicyName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${returnPolicy.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${returnPolicy.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/ReturnPolicy/ReturnPolicy/SetDefault">
                                <c:param name="ReturnKindName" value="${returnPolicy.returnKind.returnKindName}" />
                                <c:param name="ReturnPolicyName" value="${returnPolicy.returnPolicyName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <et:hasSecurityRole securityRole="ReturnPolicyReason.List">
                        <c:url var="returnPolicyReasonsUrl" value="/action/ReturnPolicy/ReturnPolicyReason/Main">
                            <c:param name="ReturnKindName" value="${returnPolicy.returnKind.returnKindName}" />
                            <c:param name="ReturnPolicyName" value="${returnPolicy.returnPolicyName}" />
                        </c:url>
                        <a href="${returnPolicyReasonsUrl}">Return Reasons</a><br />
                    </et:hasSecurityRole>
                    <c:url var="editUrl" value="/action/ReturnPolicy/ReturnPolicy/Edit">
                        <c:param name="ReturnKindName" value="${returnPolicy.returnKind.returnKindName}" />
                        <c:param name="OriginalReturnPolicyName" value="${returnPolicy.returnPolicyName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="translationsUrl" value="/action/ReturnPolicy/ReturnPolicy/Translation">
                        <c:param name="ReturnKindName" value="${returnPolicy.returnKind.returnKindName}" />
                        <c:param name="ReturnPolicyName" value="${returnPolicy.returnPolicyName}" />
                    </c:url>
                    <a href="${translationsUrl}">Translations</a>
                    <c:url var="deleteUrl" value="/action/ReturnPolicy/ReturnPolicy/Delete">
                        <c:param name="ReturnKindName" value="${returnPolicy.returnKind.returnKindName}" />
                        <c:param name="ReturnPolicyName" value="${returnPolicy.returnPolicyName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${returnPolicy.entityInstance.entityRef}" />
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
