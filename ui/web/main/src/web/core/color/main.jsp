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
        <title><fmt:message key="pageTitle.colors" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <fmt:message key="navigation.colors" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:Color.Create:Color.Edit:Color.Delete:Color.Review:Color.Description" />
            <et:hasSecurityRole securityRole="Color.Create">
                <p><a href="<c:url value="/action/Core/Color/Add" />">Add Color.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Color.Review" var="includeReviewUrl" />
            <display:table name="colors" id="color" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/Color/Review">
                                <c:param name="ColorName" value="${color.colorName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${color.colorName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${color.colorName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${color.description}" />
                </display:column>
                <display:column titleKey="columnTitle.red">
                    <c:out value="${color.red}" />
                </display:column>
                <display:column titleKey="columnTitle.green">
                    <c:out value="${color.green}" />
                </display:column>
                <display:column titleKey="columnTitle.blue">
                    <c:out value="${color.blue}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${color.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="Color.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/Color/SetDefault">
                                    <c:param name="ColorName" value="${color.colorName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="Color.Edit:Color.Description:Color.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="Color.Edit">
                            <c:url var="editUrl" value="/action/Core/Color/Edit">
                                <c:param name="OriginalColorName" value="${color.colorName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Color.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/Color/Description">
                                <c:param name="ColorName" value="${color.colorName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Color.Delete">
                            <c:url var="deleteUrl" value="/action/Core/Color/Delete">
                                <c:param name="ColorName" value="${color.colorName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${color.entityInstance.entityRef}" />
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
