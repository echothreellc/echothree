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
        <title>Component Vendors</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                Component Vendors
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ComponentVendor.Create:ComponentVendor.Edit:ComponentVendor.Delete:ComponentVendor.Review:EntityType.List" />
            <et:hasSecurityRole securityRoles="EntityType.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="ComponentVendor.Edit:ComponentVendor.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ComponentVendor.Create">
                <p><a href="<c:url value="/action/Core/ComponentVendor/Add" />">Add Component Vendor.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ComponentVendor.Review" var="includeReviewUrl" />
            <display:table name="componentVendors" id="componentVendor" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/ComponentVendor/Review">
                                <c:param name="ComponentVendorName" value="${componentVendor.componentVendorName}" />
                            </c:url>
                            <a href="${reviewUrl}"><et:appearance appearance="${componentVendor.entityInstance.entityAppearance.appearance}"><c:out value="${componentVendor.componentVendorName}" /></et:appearance></a>
                        </c:when>
                        <c:otherwise>
                            <et:appearance appearance="${componentVendor.entityInstance.entityAppearance.appearance}"><c:out value="${componentVendor.componentVendorName}" /></et:appearance>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <et:appearance appearance="${componentVendor.entityInstance.entityAppearance.appearance}"><c:out value="${componentVendor.description}" /></et:appearance>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="EntityType.List">
                            <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                                <c:param name="ComponentVendorName" value="${componentVendor.componentVendorName}" />
                            </c:url>
                            <a href="${entityTypesUrl}">Entity Types</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="ComponentVendor.Edit">
                            <c:url var="editUrl" value="/action/Core/ComponentVendor/Edit">
                                <c:param name="OriginalComponentVendorName" value="${componentVendor.componentVendorName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ComponentVendor.Delete">
                            <c:url var="deleteUrl" value="/action/Core/ComponentVendor/Delete">
                                <c:param name="ComponentVendorName" value="${componentVendor.componentVendorName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>                    
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:if test='${componentVendor.entityInstance.entityTime.createdTime != null}'>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${componentVendor.entityInstance.entityRef}" />
                            </c:url>
                            <a href="${eventsUrl}">Events</a>
                        </c:if>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
