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
        <title><fmt:message key="pageTitle.relatedItemTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />"><fmt:message key="navigation.items" /></a> &gt;&gt;
                <fmt:message key="navigation.relatedItemTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:RelatedItemType.Create:RelatedItemType.Edit:RelatedItemType.Delete:RelatedItemType.Review:RelatedItemType.Description" />
            <et:hasSecurityRole securityRole="RelatedItemType.Create">
                <p><a href="<c:url value="/action/Item/RelatedItemType/Add" />">Add Related Item Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="RelatedItemType.Review" var="includeReviewUrl" />
            <display:table name="relatedItemTypes" id="relatedItemType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Item/RelatedItemType/Review">
                                <c:param name="RelatedItemTypeName" value="${relatedItemType.relatedItemTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${relatedItemType.relatedItemTypeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${relatedItemType.relatedItemTypeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${relatedItemType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${relatedItemType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="RelatedItemType.Edit">
                                <c:url var="setDefaultUrl" value="/action/Item/RelatedItemType/SetDefault">
                                    <c:param name="RelatedItemTypeName" value="${relatedItemType.relatedItemTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="RelatedItemType.Edit:RelatedItemType.Description:RelatedItemType.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="RelatedItemType.Edit">
                            <c:url var="editUrl" value="/action/Item/RelatedItemType/Edit">
                                <c:param name="OriginalRelatedItemTypeName" value="${relatedItemType.relatedItemTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="RelatedItemType.Description">
                            <c:url var="descriptionsUrl" value="/action/Item/RelatedItemType/Description">
                                <c:param name="RelatedItemTypeName" value="${relatedItemType.relatedItemTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="RelatedItemType.Delete">
                            <c:url var="deleteUrl" value="/action/Item/RelatedItemType/Delete">
                                <c:param name="RelatedItemTypeName" value="${relatedItemType.relatedItemTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${relatedItemType.entityInstance.entityRef}" />
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
