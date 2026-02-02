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
        <title>
            <c:choose>
                <c:when test="${entityInstance != null}">
                    Events
                </c:when>
                <c:when test="${createdByEntityInstance != null}">
                    Actions
                </c:when>
            </c:choose>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <c:choose>
                    <c:when test="${entityInstance != null}">
                        <a href="<c:url value="/action/Core/ComponentVendor/Main" />">Component Vendors</a> &gt;&gt;
                        <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                            <c:param name="ComponentVendorName" value="${entityInstance.entityType.componentVendor.componentVendorName}" />
                        </c:url>
                        <a href="${entityTypesUrl}">Entity Types</a> &gt;&gt;
                        <c:url var="entityInstancesUrl" value="/action/Core/EntityInstance/Main">
                            <c:param name="ComponentVendorName" value="${entityInstance.entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityInstance.entityType.entityTypeName}" />
                        </c:url>
                        <a href="${entityInstancesUrl}">Entity Instances</a> &gt;&gt;
                        Events
                    </c:when>
                    <c:when test="${createdByEntityInstance != null}">
                        Actions
                    </c:when>
                </c:choose>
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${entityInstance != null}">
                    <et:checkSecurityRoles securityRoles="EntityInstance.Review:ComponentVendor.Review:EntityType.Review:EntityAppearance.Create:Appearance.Review:EntityAppearance.Edit:EntityAppearance.Delete" />
                    <et:hasSecurityRole securityRole="EntityInstance.Review" var="includeEntityInstanceReviewUrl" />
                    <et:hasSecurityRole securityRole="ComponentVendor.Review" var="includeComponentVendorReviewUrl" />
                    <et:hasSecurityRole securityRole="EntityType.Review" var="includeEntityTypeReviewUrl" />
                    <et:hasSecurityRole securityRole="Appearance.Review" var="includeAppearanceReviewUrl" />
                    Entity:
                    <c:choose>
                        <c:when test="${includeEntityInstanceReviewUrl}">
                            <c:url var="entityInstanceUrl" value="/action/Core/EntityInstance/Review">
                                <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                            </c:url>
                            <a href="${entityInstanceUrl}"><c:out value="${entityInstance.entityRef}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${entityInstance.entityRef}" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Component Vendor:
                    <c:choose>
                        <c:when test="${includeComponentVendorReviewUrl}">
                            <c:url var="componentVendorUrl" value="/action/Core/ComponentVendor/Review">
                                <c:param name="ComponentVendorName" value="${entityInstance.entityType.componentVendor.componentVendorName}" />
                            </c:url>
                            <a href="${componentVendorUrl}"><c:out value="${entityInstance.entityType.componentVendor.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${entityInstance.entityType.componentVendor.description}" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Entity Type:
                    <c:choose>
                        <c:when test="${includeEntityTypeReviewUrl}">
                            <c:url var="entityTypeUrl" value="/action/Core/EntityType/Review">
                                <c:param name="ComponentVendorName" value="${entityInstance.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityInstance.entityType.entityTypeName}" />
                            </c:url>
                            <a href="${entityTypeUrl}"><c:out value="${entityInstance.entityType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${entityInstance.entityType.description}" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Entity Unique Id: <c:out value="${entityInstance.entityUniqueId}" /><br />
                    Appearance:
                    <c:choose>
                        <c:when test="${entityInstance.entityAppearance == null}">
                            <i><fmt:message key="phrase.notSet" /></i>
                            <et:hasSecurityRole securityRole="EntityAppearance.Create">
                                <c:url var="addUrl" value="/action/Core/Event/EntityAppearanceAdd">
                                    <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                </c:url>
                                [<a href="${addUrl}">Add</a>]
                            </et:hasSecurityRole>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${includeAppearanceReviewUrl}">
                                    <c:url var="appearanceUrl" value="/action/Core/Appearance/Review">
                                        <c:param name="AppearanceName" value="${entityInstance.entityAppearance.appearance.appearanceName}" />
                                    </c:url>
                                    <a href="${appearanceUrl}"><c:out value="${entityInstance.entityAppearance.appearance.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${entityInstance.entityAppearance.appearance.description}" />
                                </c:otherwise>
                            </c:choose>
                            <et:hasSecurityRole securityRole="EntityAppearance.Edit">
                                <c:url var="editUrl" value="/action/Core/Event/EntityAppearanceEdit">
                                    <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                </c:url>
                                [<a href="${editUrl}">Edit</a>]
                            </et:hasSecurityRole>
                            <et:hasSecurityRole securityRole="EntityAppearance.Delete">
                                <c:url var="clearUrl" value="/action/Core/Event/EntityAppearanceDelete">
                                    <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                </c:url>
                                [<a href="${clearUrl}">Clear</a>]
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Uuid:
                    <c:choose>
                        <c:when test="${entityInstance.uuid == null}">
                            <i><fmt:message key="phrase.notSet" /></i>
                            <c:url var="generateUrl" value="/action/Core/Event/GenerateUuid">
                                <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                            </c:url>
                            [<a href="${generateUrl}">Generate</a>]
                        </c:when>
                        <c:otherwise>
                            <c:out value="${entityInstance.uuid}" />
                            <c:url var="generateUrl" value="/action/Core/Event/GenerateUuid">
                                <c:param name="Uuid" value="${entityInstance.uuid}" />
                                <c:param name="ForceRegeneration" value="true" />
                            </c:url>
                            [<a href="${generateUrl}">Regenerate</a>]
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Description: <jsp:include page="../../include/targetAsReviewLink.jsp" /><br />
                    <c:if test='${entityInstance.entityTime != null}'>
                        Created: <c:out value="${entityInstance.entityTime.createdTime}" /><br />
                        <c:if test='${entityInstance.entityTime.modifiedTime != null}'>
                            Modified: <c:out value="${entityInstance.entityTime.modifiedTime}" /><br />
                        </c:if>
                        <c:if test='${entityInstance.entityTime.deletedTime != null}'>
                            Deleted: <c:out value="${entityInstance.entityTime.deletedTime}" /><br />
                        </c:if>
                    </c:if>
                    Visited Time:
                    <c:choose>
                        <c:when test='${entityInstance.entityVisit == null}'>
                            <i>Never.</i>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${entityInstance.entityVisit.visitedTime}" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    <br />
                    <display:table name="events" id="event" class="displaytag" partialList="true" pagesize="20" size="eventCount" requestURI="/action/Core/Event/Main">
                        <display:column titleKey="columnTitle.eventTime">
                            <c:out value="${event.eventTime}" />
                        </display:column>
                        <display:column titleKey="columnTitle.eventTimeSequence">
                            <c:out value="${event.eventTimeSequence}" />
                        </display:column>
                        <display:column titleKey="columnTitle.eventType">
                            <c:out value="${event.eventType.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.relatedEntity">
                            <c:if test="${event.relatedEntityInstance != null}">
                                <c:choose>
                                    <c:when test="${event.relatedEntityInstance.entityTime == null}">
                                        <c:out value="${event.relatedEntityInstance.description}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="relatedEntityInstanceUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${event.relatedEntityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${relatedEntityInstanceUrl}"><c:out value="${event.relatedEntityInstance.description}" /></a>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </display:column>
                        <display:column titleKey="columnTitle.relatedEventType">
                            <c:out value="${event.relatedEventType.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.createdBy">
                            <c:if test="${event.createdBy != null}">
                                <c:set var="entityInstance" scope="request" value="${event.createdBy}" />
                                <jsp:include page="../../include/targetAsReviewLink.jsp" />
                                <c:url var="createdByUrl" value="/action/Core/Event/Main">
                                    <c:param name="CreatedByEntityRef" value="${event.createdBy.entityRef}" />
                                </c:url>
                                [<a href="${createdByUrl}">Events</a>]
                            </c:if>
                        </display:column>
                    </display:table>
                </c:when>
                <c:when test="${createdByEntityInstance != null}">
                    <c:set var="entityInstance" scope="request" value="${createdByEntityInstance}" />
                    Description: <jsp:include page="../../include/targetAsReviewLink.jsp" /><br />
                    <br />
                    <display:table name="events" id="event" class="displaytag" partialList="true" pagesize="20" size="eventCount" requestURI="/action/Core/Event/Main">
                        <display:column titleKey="columnTitle.eventTime">
                            <c:out value="${event.eventTime}" />
                        </display:column>
                        <display:column titleKey="columnTitle.eventTimeSequence">
                            <c:out value="${event.eventTimeSequence}" />
                        </display:column>
                        <display:column titleKey="columnTitle.entityInstance">
                            <c:set var="entityInstance" scope="request" value="${event.entityInstance}" />
                            <jsp:include page="../../include/targetAsReviewLink.jsp" />
                            <c:url var="entityInstanceUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${event.entityInstance.entityRef}" />
                            </c:url>
                            [<a href="${entityInstanceUrl}">Events</a>]
                        </display:column>
                        <display:column titleKey="columnTitle.eventType">
                            <c:out value="${event.eventType.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.relatedEntity">
                            <c:if test="${event.relatedEntityInstance != null}">
                                <c:choose>
                                    <c:when test="${event.relatedEntityInstance.entityTime == null}">
                                        <c:out value="${event.relatedEntityInstance.description}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="relatedEntityInstanceUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${event.relatedEntityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${relatedEntityInstanceUrl}"><c:out value="${event.relatedEntityInstance.description}" /></a>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </display:column>
                        <display:column titleKey="columnTitle.relatedEventType">
                            <c:out value="${event.relatedEventType.description}" />
                        </display:column>
                    </display:table>
                </c:when>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
