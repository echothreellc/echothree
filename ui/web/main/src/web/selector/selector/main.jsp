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
        <title>Selectors</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Selector/Main" />">Selectors</a> &gt;&gt;
                <a href="<c:url value="/action/Selector/SelectorKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="selectorTypesUrl" value="/action/Selector/SelectorType/Main">
                    <c:param name="SelectorKindName" value="${selectorType.selectorKind.selectorKindName}" />
                </c:url>
                <a href="${selectorTypesUrl}">Types</a> &gt;&gt;
                Selectors
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Selector/Selector/Add">
                <c:param name="SelectorKindName" value="${selectorType.selectorKind.selectorKindName}" />
                <c:param name="SelectorTypeName" value="${selectorType.selectorTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Selector.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="selectors" id="selector" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Selector/Selector/Review">
                        <c:param name="SelectorKindName" value="${selector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${selector.selectorType.selectorTypeName}" />
                        <c:param name="SelectorName" value="${selector.selectorName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${selector.selectorName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${selector.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${selector.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Selector/Selector/SetDefault">
                                <c:param name="SelectorKindName" value="${selector.selectorType.selectorKind.selectorKindName}" />
                                <c:param name="SelectorTypeName" value="${selector.selectorType.selectorTypeName}" />
                                <c:param name="SelectorName" value="${selector.selectorName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="selectorNodesUrl" value="/action/Selector/SelectorNode/Main">
                        <c:param name="SelectorKindName" value="${selector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${selector.selectorType.selectorTypeName}" />
                        <c:param name="SelectorName" value="${selector.selectorName}" />
                    </c:url>
                    <a href="${selectorNodesUrl}">Nodes</a>
                    <c:if test='${selector.selectorType.selectorKind.selectorKindName == "EMPLOYEE"}'>
                        <c:url var="employeesUrl" value="/action/Selector/Selector/Party">
                            <c:param name="SelectorKindName" value="${selector.selectorType.selectorKind.selectorKindName}" />
                            <c:param name="SelectorTypeName" value="${selector.selectorType.selectorTypeName}" />
                            <c:param name="SelectorName" value="${selector.selectorName}" />
                        </c:url>
                        <a href="${employeesUrl}">Employees</a>
                    </c:if>
                    <br />
                    <c:url var="editUrl" value="/action/Selector/Selector/Edit">
                        <c:param name="SelectorKindName" value="${selector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${selector.selectorType.selectorTypeName}" />
                        <c:param name="OriginalSelectorName" value="${selector.selectorName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Selector/Selector/Description">
                        <c:param name="SelectorKindName" value="${selector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${selector.selectorType.selectorTypeName}" />
                        <c:param name="SelectorName" value="${selector.selectorName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Selector/Selector/Delete">
                        <c:param name="SelectorKindName" value="${selector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${selector.selectorType.selectorTypeName}" />
                        <c:param name="SelectorName" value="${selector.selectorName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${selector.entityInstance.entityRef}" />
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
