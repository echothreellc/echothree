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
        <title><fmt:message key="pageTitle.itemDescriptionTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />"><fmt:message key="navigation.items" /></a> &gt;&gt;
                <fmt:message key="navigation.itemDescriptionTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ItemDescriptionType.Create:ItemDescriptionType.Edit:ItemDescriptionType.Delete:ItemDescriptionType.Review:ItemDescriptionType.Description:ItemDescriptionTypeUse.List" />
            <et:hasSecurityRole securityRoles="ItemDescriptionTypeUse.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="ItemDescriptionType.Edit:ItemDescriptionType.Description:ItemDescriptionType.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemDescriptionType.Create">
                <p><a href="<c:url value="/action/Item/ItemDescriptionType/Add/Step1" />">Add Item Description Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemDescriptionType.Review" var="includeReviewUrl" />
            <display:table name="itemDescriptionTypes" id="itemDescriptionType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Item/ItemDescriptionType/Review">
                                <c:param name="ItemDescriptionTypeName" value="${itemDescriptionType.itemDescriptionTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><et:appearance appearance="${itemDescriptionType.entityInstance.entityAppearance.appearance}"><c:out value="${itemDescriptionType.itemDescriptionTypeName}" /></et:appearance></a>
                        </c:when>
                        <c:otherwise>
                            <et:appearance appearance="${itemDescriptionType.entityInstance.entityAppearance.appearance}"><c:out value="${itemDescriptionType.itemDescriptionTypeName}" /></et:appearance>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <et:appearance appearance="${itemDescriptionType.entityInstance.entityAppearance.appearance}"><c:out value="${itemDescriptionType.description}" /></et:appearance>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${itemDescriptionType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ItemDescriptionType.Edit">
                                <c:url var="setDefaultUrl" value="/action/Item/ItemDescriptionType/SetDefault">
                                    <c:param name="ItemDescriptionTypeName" value="${itemDescriptionType.itemDescriptionTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="ItemDescriptionTypeUse.List">
                            <c:url var="itemDescriptionTypeUsesUrl" value="/action/Item/ItemDescriptionTypeUse/Main">
                                <c:param name="ItemDescriptionTypeName" value="${itemDescriptionType.itemDescriptionTypeName}" />
                            </c:url>
                            <a href="${itemDescriptionTypeUsesUrl}">Uses</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="ItemDescriptionType.Edit">
                            <c:url var="editUrl" value="/action/Item/ItemDescriptionType/Edit">
                                <c:param name="OriginalItemDescriptionTypeName" value="${itemDescriptionType.itemDescriptionTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ItemDescriptionType.Description">
                            <c:url var="descriptionsUrl" value="/action/Item/ItemDescriptionType/Description">
                                <c:param name="ItemDescriptionTypeName" value="${itemDescriptionType.itemDescriptionTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ItemDescriptionType.Delete">
                            <c:url var="deleteUrl" value="/action/Item/ItemDescriptionType/Delete">
                                <c:param name="ItemDescriptionTypeName" value="${itemDescriptionType.itemDescriptionTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${itemDescriptionType.entityInstance.entityRef}" />
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
