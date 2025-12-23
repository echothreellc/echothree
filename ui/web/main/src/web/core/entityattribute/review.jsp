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
        <title>Review (<c:out value="${entityAttribute.entityAttributeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />">Component Vendors</a> &gt;&gt;
                <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}">Entity Types</a> &gt;&gt;
                <c:url var="entityAttributesUrl" value="/action/Core/EntityAttribute/Main">
                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                </c:url>
                <a href="${entityAttributesUrl}">Entity Attributes</a> &gt;&gt;
                Review (<c:out value="${entityAttribute.entityAttributeName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="ComponentVendor.Review:EntityType.Review:Event.List" />
            <c:choose>
                <c:when test="${entityAttribute.description != null}">
                    <p><font size="+2"><b><et:appearance appearance="${entityAttribute.entityInstance.entityAppearance.appearance}"><c:out value="${entityAttribute.description}" /></et:appearance></b></font></p>
                    <p><font size="+1"><et:appearance appearance="${entityAttribute.entityInstance.entityAppearance.appearance}">${entityAttribute.entityAttributeName}</et:appearance></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><et:appearance appearance="${entityAttribute.entityInstance.entityAppearance.appearance}"><c:out value="${entityAttribute.entityAttributeName}" /></et:appearance></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.componentVendor" />:
            <et:hasSecurityRole securityRoles="ComponentVendor.Review">
                <c:set var="showComponentVendorAsLink" value="true" />
            </et:hasSecurityRole>
            <c:choose>
                <c:when test="${showComponentVendorAsLink}">
                    <c:url var="componentVendorUrl" value="/action/Core/ComponentVendor/Review">
                        <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                    </c:url>
                    <a href="${componentVendorUrl}"><et:appearance appearance="${entityAttribute.entityType.componentVendor.entityInstance.entityAppearance.appearance}"><c:out value="${entityAttribute.entityType.componentVendor.description}" /></et:appearance></a>
                </c:when>
                <c:otherwise>
                    <et:appearance appearance="${entityAttribute.entityType.componentVendor.entityInstance.entityAppearance.appearance}"><c:out value="${entityAttribute.entityType.componentVendor.description}" /></et:appearance>
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.entityType" />:
            <et:hasSecurityRole securityRoles="EntityType.Review">
                <c:set var="showEntityTypeAsLink" value="true" />
            </et:hasSecurityRole>
            <c:choose>
                <c:when test="${showEntityTypeAsLink}">
                    <c:url var="componentVendorUrl" value="/action/Core/EntityType/Review">
                        <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                    </c:url>
                    <a href="${componentVendorUrl}"><et:appearance appearance="${entityAttribute.entityType.entityInstance.entityAppearance.appearance}"><c:out value="${entityAttribute.entityType.description}" /></et:appearance></a>
                </c:when>
                <c:otherwise>
                    <et:appearance appearance="${entityAttribute.entityType.entityInstance.entityAppearance.appearance}"><c:out value="${entityAttribute.entityType.description}" /></et:appearance>
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.entityAttributeName" />: ${entityAttribute.entityAttributeName}<br />
            <fmt:message key="label.entityAttributeType" />: ${entityAttribute.entityAttributeType.description}<br />
            <fmt:message key="label.trackRevisions" />:
            <c:choose>
                <c:when test="${entityType.trackRevisions}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.sortOrder" />: ${entityAttribute.sortOrder}<br />
            <br />
            <c:if test="${entityAttribute.entityAttributeType.entityAttributeTypeName == 'LISTITEM' || entityAttribute.entityAttributeType.entityAttributeTypeName == 'MULTIPLELISTITEM'}">
                <h2>Entity List Items</h2>
                <et:checkSecurityRoles securityRoles="EntityListItem.Create:EntityListItem.Edit:EntityListItem.Delete:EntityListItem.Review:EntityListItem.Description" />
                <et:hasSecurityRole securityRoles="EntityListItem.Edit:EntityListItem.Description:EntityListItem.Delete">
                    <c:set var="linksInFirstRow" value="true" />
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="EntityListItem.Create">
                    <c:url var="addUrl" value="/action/Core/EntityListItem/Add">
                        <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                        <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Entity List Item.</a></p>
                </et:hasSecurityRole>
                <c:choose>
                    <c:when test='${entityAttribute.entityListItems.size == 0}'>
                        <br />
                    </c:when>
                    <c:otherwise>
                        <et:hasSecurityRole securityRole="EntityListItem.Review" var="includeReviewUrl" />
                        <display:table name="entityAttribute.entityListItems.list" id="entityListItem" class="displaytag">
                            <display:column titleKey="columnTitle.name">
                                <c:choose>
                                    <c:when test="${includeReviewUrl}">
                                        <c:url var="reviewUrl" value="/action/Core/EntityListItem/Review">
                                            <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                            <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                                            <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                                            <c:param name="EntityListItemName" value="${entityListItem.entityListItemName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><et:appearance appearance="${entityListItem.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.entityListItemName}" /></et:appearance></a>
                                    </c:when>
                                    <c:otherwise>
                                        <et:appearance appearance="${entityListItem.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.entityListItemName}" /></et:appearance>
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column titleKey="columnTitle.description">
                                <et:appearance appearance="${entityListItem.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.description}" /></et:appearance>
                            </display:column>
                            <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                            <display:column titleKey="columnTitle.default">
                                <c:choose>
                                    <c:when test="${entityListItem.isDefault}">
                                        Default
                                    </c:when>
                                    <c:otherwise>
                                        <et:hasSecurityRole securityRole="EntityListItem.Edit">
                                            <c:url var="setDefaultUrl" value="/action/Core/EntityListItem/SetDefault">
                                                <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                                <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                                                <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                                                <c:param name="EntityListItemName" value="${entityListItem.entityListItemName}" />
                                            </c:url>
                                            <a href="${setDefaultUrl}">Set Default</a>
                                        </et:hasSecurityRole>
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <c:if test="${linksInFirstRow}">
                                <display:column>
                                    <et:hasSecurityRole securityRole="EntityListItem.Edit">
                                        <c:url var="editUrl" value="/action/Core/EntityListItem/Edit">
                                            <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                            <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                                            <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                                            <c:param name="OriginalEntityListItemName" value="${entityListItem.entityListItemName}" />
                                        </c:url>
                                        <a href="${editUrl}">Edit</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="EntityListItem.Description">
                                        <c:url var="descriptionsUrl" value="/action/Core/EntityListItem/Description">
                                            <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                            <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                                            <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                                            <c:param name="EntityListItemName" value="${entityListItem.entityListItemName}" />
                                        </c:url>
                                        <a href="${descriptionsUrl}">Descriptions</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="EntityListItem.Delete">
                                        <c:url var="deleteUrl" value="/action/Core/EntityListItem/Delete">
                                            <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                                            <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                                            <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                                            <c:param name="EntityListItemName" value="${entityListItem.entityListItemName}" />
                                        </c:url>
                                        <a href="${deleteUrl}">Delete</a>
                                    </et:hasSecurityRole>
                                </display:column>
                            </c:if>
                            <et:hasSecurityRole securityRole="Event.List">
                                <display:column>
                                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                        <c:param name="EntityRef" value="${entityListItem.entityInstance.entityRef}" />
                                    </c:url>
                                    <a href="${eventsUrl}">Events</a>
                                </display:column>
                            </et:hasSecurityRole>
                        </display:table>
                        <c:if test='${entityAttribute.entityListItemsCount > 10}'>
                            <c:url var="entityListItemsUrl" value="/action/Core/EntityListItem/Main">
                                <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                            </c:url>
                            <a href="${entityListItemsUrl}">More...</a> (<c:out value="${entityAttribute.entityListItemsCount}" /> total)<br />
                        </c:if>
                    </c:otherwise>
                </c:choose>
                <br />
            </c:if>
            <br />
            <c:set var="entityInstance" scope="request" value="${entityAttribute.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Core/ComponentVendor/Review">
                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
            </c:url>
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
