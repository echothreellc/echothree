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
        <title><fmt:message key="pageTitle.itemVolumeTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />"><fmt:message key="navigation.items" /></a> &gt;&gt;
                <fmt:message key="navigation.itemVolumeTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ItemVolumeType.Create:ItemVolumeType.Edit:ItemVolumeType.Delete:ItemVolumeType.Review:ItemVolumeType.Description" />
            <et:hasSecurityRole securityRole="ItemVolumeType.Create">
                <p><a href="<c:url value="/action/Item/ItemVolumeType/Add" />">Add Item Volume Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemVolumeType.Review" var="includeReviewUrl" />
            <display:table name="itemVolumeTypes" id="itemVolumeType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Item/ItemVolumeType/Review">
                                <c:param name="ItemVolumeTypeName" value="${itemVolumeType.itemVolumeTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemVolumeType.itemVolumeTypeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemVolumeType.itemVolumeTypeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${itemVolumeType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${itemVolumeType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ItemVolumeType.Edit">
                                <c:url var="setDefaultUrl" value="/action/Item/ItemVolumeType/SetDefault">
                                    <c:param name="ItemVolumeTypeName" value="${itemVolumeType.itemVolumeTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="ItemVolumeType.Edit:ItemVolumeType.Description:ItemVolumeType.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="ItemVolumeType.Edit">
                            <c:url var="editUrl" value="/action/Item/ItemVolumeType/Edit">
                                <c:param name="OriginalItemVolumeTypeName" value="${itemVolumeType.itemVolumeTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ItemVolumeType.Description">
                            <c:url var="descriptionsUrl" value="/action/Item/ItemVolumeType/Description">
                                <c:param name="ItemVolumeTypeName" value="${itemVolumeType.itemVolumeTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ItemVolumeType.Delete">
                            <c:url var="deleteUrl" value="/action/Item/ItemVolumeType/Delete">
                                <c:param name="ItemVolumeTypeName" value="${itemVolumeType.itemVolumeTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${itemVolumeType.entityInstance.entityRef}" />
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
