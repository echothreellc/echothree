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
        <title>Unit of Measure Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/Main" />">Units of Measure</a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/UnitOfMeasureKind/Main" />">Kinds</a> &gt;&gt;
                Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Add">
                <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
            </c:url>
            <p><a href="${addUrl}">Add Type.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="unitOfMeasureTypes" id="unitOfMeasureType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Review">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${unitOfMeasureType.unitOfMeasureTypeName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${unitOfMeasureType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${unitOfMeasureType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/SetDefault">
                                <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.volume">
                    <c:choose>
                        <c:when test="${unitOfMeasureType.unitOfMeasureTypeVolume == null}">
                            <c:url var="createUnitOfMeasureTypeVolumeUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/VolumeAdd">
                                <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${createUnitOfMeasureTypeVolumeUrl}">Create</a>
                        </c:when>
                        <c:otherwise>
                            H: <c:out value="${unitOfMeasureType.unitOfMeasureTypeVolume.height}" />,
                            W: <c:out value="${unitOfMeasureType.unitOfMeasureTypeVolume.width}" />,
                            D: <c:out value="${unitOfMeasureType.unitOfMeasureTypeVolume.depth}" /><br />
                            <c:url var="editUnitOfMeasureTypeVolumeUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/VolumeEdit">
                                <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${editUnitOfMeasureTypeVolumeUrl}">Edit</a>
                            <c:url var="deleteUnitOfMeasureTypeVolumeUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/VolumeDelete">
                                <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${deleteUnitOfMeasureTypeVolumeUrl}">Delete</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.weight">
                    <c:choose>
                        <c:when test="${unitOfMeasureType.unitOfMeasureTypeWeight == null}">
                            <c:url var="createUnitOfMeasureTypeWeightUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/WeightAdd">
                                <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${createUnitOfMeasureTypeWeightUrl}">Create</a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${unitOfMeasureType.unitOfMeasureTypeWeight.weight}" /><br />
                            <c:url var="editUnitOfMeasureTypeWeightUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/WeightEdit">
                                <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${editUnitOfMeasureTypeWeightUrl}">Edit</a>
                            <c:url var="deleteUnitOfMeasureTypeWeightUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/WeightDelete">
                                <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
                            </c:url>
                            <a href="${deleteUnitOfMeasureTypeWeightUrl}">Delete</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Edit">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="OriginalUnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Description">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Delete">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${unitOfMeasureType.entityInstance.entityRef}" />
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
