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
        <title>Entity Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />">Component Vendors</a> &gt;&gt;
                Entity Types
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:EntityType.Create:EntityType.Edit:EntityType.Delete:EntityType.Review:EntityType.Description" />
            <et:hasSecurityRole securityRoles="EntityType.Edit:EntityType.Description:EntityType.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityType.Create">
                <c:url var="addUrl" value="/action/Core/EntityType/Add">
                    <c:param name="ComponentVendorName" value="${componentVendor.componentVendorName}" />
                </c:url>
                <p><a href="${addUrl}">Add Entity Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="EntityType.Review" var="includeReviewUrl" />
            <display:table name="entityTypes" id="entityType" class="displaytag" partialList="true" pagesize="20" size="entityTypeCount" requestURI="/action/Core/EntityType/Main">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/EntityType/Review">
                                <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><et:appearance appearance="${entityType.entityInstance.entityAppearance.appearance}"><c:out value="${entityType.entityTypeName}" /></et:appearance></a>
                        </c:when>
                        <c:otherwise>
                            <et:appearance appearance="${entityType.entityInstance.entityAppearance.appearance}"><c:out value="${entityType.entityTypeName}" /></et:appearance>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <et:appearance appearance="${entityType.entityInstance.entityAppearance.appearance}"><c:out value="${entityType.description}" /></et:appearance>
                </display:column>
                <display:column titleKey="columnTitle.keepAllHistory">
                    <c:choose>
                        <c:when test="${entityType.keepAllHistory}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.lockTimeout">
                    <c:choose>
                        <c:when test="${entityType.lockTimeout == null}">
                            <i>Default</i>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${entityType.lockTimeout}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.sortOrder">
                    <c:out value="${entityType.sortOrder}" />
                </display:column>
                <display:column>
                    <c:if test="${entityType.isExtensible}">
                        <c:url var="entityAttributesUrl" value="/action/Core/EntityAttribute/Main">
                            <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                        </c:url>
                        <a href="${entityAttributesUrl}">Entity Attributes</a>
                        <c:url var="commentTypesUrl" value="/action/Core/CommentType/Main">
                            <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                        </c:url>
                        <a href="${commentTypesUrl}">Comment Types</a>
                        <c:url var="ratingTypesUrl" value="/action/Core/RatingType/Main">
                            <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                        </c:url>
                        <a href="${ratingTypesUrl}">Rating Types</a>
                        <c:url var="messageTypesUrl" value="/action/Core/MessageType/Main">
                            <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                        </c:url>
                        <a href="${messageTypesUrl}">Message Types</a>
                    </c:if>
                    <c:url var="entityInstancesUrl" value="/action/Core/EntityInstance/Main">
                        <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                    </c:url>
                    <a href="${entityInstancesUrl}">Entity Instances</a>
                    <c:if test="${entityType.indexTypesCount > 0}">
                        <c:url var="reindexUrl" value="/action/Core/EntityType/Reindex">
                            <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                        </c:url>
                        <a href="${reindexUrl}">Reindex</a>
                    </c:if>
                    <c:if test="${linksInSecondRow}">
                        <br />
                    </c:if>
                    <et:hasSecurityRole securityRole="EntityType.Edit">
                        <c:url var="editUrl" value="/action/Core/EntityType/Edit">
                            <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                            <c:param name="OriginalEntityTypeName" value="${entityType.entityTypeName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="EntityType.Description">
                        <c:url var="descriptionsUrl" value="/action/Core/EntityType/Description">
                            <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                        </c:url>
                        <a href="${descriptionsUrl}">Descriptions</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="EntityType.Delete">
                        <c:url var="deleteUrl" value="/action/Core/EntityType/Delete">
                            <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </et:hasSecurityRole>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:if test='${entityType.entityInstance.entityTime.createdTime != null}'>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${entityType.entityInstance.entityRef}" />
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
