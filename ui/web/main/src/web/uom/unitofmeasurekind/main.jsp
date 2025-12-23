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
        <title>Unit Of Measure Kinds</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/Main" />">Units of Measure</a> &gt;&gt;
                Kinds
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/UnitOfMeasure/UnitOfMeasureKind/Add" />">Add Kind.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="unitOfMeasureKinds" id="unitOfMeasureKind" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureKind/Review">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${unitOfMeasureKind.unitOfMeasureKindName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${unitOfMeasureKind.description}" />
                </display:column>
                <display:column property="fractionDigits" titleKey="columnTitle.fractionDigits" />
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${unitOfMeasureKind.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/UnitOfMeasure/UnitOfMeasureKind/SetDefault">
                                <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="unitOfMeasureTypesUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Main">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                    </c:url>
                    <a href="${unitOfMeasureTypesUrl}">Types</a>
                    <c:url var="unitOfMeasureEquivalentsUrl" value="/action/UnitOfMeasure/UnitOfMeasureEquivalent/Main">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                    </c:url>
                    <a href="${unitOfMeasureEquivalentsUrl}">Equivalents</a>
                    <c:url var="unitOfMeasureKindUsesUrl" value="/action/UnitOfMeasure/UnitOfMeasureKindUse/Main">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                    </c:url>
                    <a href="${unitOfMeasureKindUsesUrl}">Uses</a><br />
                    <c:url var="editUrl" value="/action/UnitOfMeasure/UnitOfMeasureKind/Edit">
                        <c:param name="OriginalUnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/UnitOfMeasure/UnitOfMeasureKind/Description">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/UnitOfMeasure/UnitOfMeasureKind/Delete">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
               </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${unitOfMeasureKind.entityInstance.entityRef}" />
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
