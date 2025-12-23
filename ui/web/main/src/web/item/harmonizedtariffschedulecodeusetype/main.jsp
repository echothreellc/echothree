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
        <title><fmt:message key="pageTitle.harmonizedTariffScheduleCodeUseTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />"><fmt:message key="navigation.items" /></a> &gt;&gt;
                <fmt:message key="navigation.harmonizedTariffScheduleCodeUseTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:HarmonizedTariffScheduleCodeUseType.Create:HarmonizedTariffScheduleCodeUseType.Edit:HarmonizedTariffScheduleCodeUseType.Delete:HarmonizedTariffScheduleCodeUseType.Review:HarmonizedTariffScheduleCodeUseType.Description" />
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUseType.Create">
                <p><a href="<c:url value="/action/Item/HarmonizedTariffScheduleCodeUseType/Add" />">Add Harmonized Tariff Schedule Code Use Types.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUseType.Review" var="includeReviewUrl" />
            <display:table name="harmonizedTariffScheduleCodeUseTypes" id="harmonizedTariffScheduleCodeUseType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Item/HarmonizedTariffScheduleCodeUseType/Review">
                                <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${harmonizedTariffScheduleCodeUseType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${harmonizedTariffScheduleCodeUseType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUseType.Edit">
                                <c:url var="setDefaultUrl" value="/action/Item/HarmonizedTariffScheduleCodeUseType/SetDefault">
                                    <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="HarmonizedTariffScheduleCodeUseType.Edit:HarmonizedTariffScheduleCodeUseType.Description:HarmonizedTariffScheduleCodeUseType.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUseType.Edit">
                            <c:url var="editUrl" value="/action/Item/HarmonizedTariffScheduleCodeUseType/Edit">
                                <c:param name="OriginalHarmonizedTariffScheduleCodeUseTypeName" value="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUseType.Description">
                            <c:url var="descriptionsUrl" value="/action/Item/HarmonizedTariffScheduleCodeUseType/Description">
                                <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUseType.Delete">
                            <c:url var="deleteUrl" value="/action/Item/HarmonizedTariffScheduleCodeUseType/Delete">
                                <c:param name="HarmonizedTariffScheduleCodeUseTypeName" value="${harmonizedTariffScheduleCodeUseType.harmonizedTariffScheduleCodeUseTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${harmonizedTariffScheduleCodeUseType.entityInstance.entityRef}" />
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
