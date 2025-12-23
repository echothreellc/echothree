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
        <title>Review (<c:out value="${entityListItem.entityListItemName}" />)</title>
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
                    <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}">Entity Types</a> &gt;&gt;
                <c:url var="entityAttributesUrl" value="/action/Core/EntityAttribute/Main">
                    <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                </c:url>
                <a href="${entityAttributesUrl}">Entity Attributes</a> &gt;&gt;
                <c:url var="entityAttributesUrl" value="/action/Core/EntityListItem/Main">
                    <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                </c:url>
                <a href="${entityAttributesUrl}">Entity List Items</a> &gt;&gt;
                Review (<c:out value="${entityListItem.entityListItemName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="ComponentVendor.Review:EntityType.Review:EntityAttribute.Review:Event.List" />
            <c:choose>
                <c:when test="${entityListItem.description != null}">
                    <p><font size="+2"><b><et:appearance appearance="${entityListItem.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.description}" /></et:appearance></b></font></p>
                    <p><font size="+1"><et:appearance appearance="${entityListItem.entityInstance.entityAppearance.appearance}">${entityListItem.entityListItemName}</et:appearance></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><et:appearance appearance="${entityListItem.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.entityListItemName}" /></et:appearance></b></font></p>
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
                        <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    </c:url>
                    <a href="${componentVendorUrl}"><et:appearance appearance="${entityListItem.entityAttribute.entityType.componentVendor.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.entityAttribute.entityType.componentVendor.description}" /></et:appearance></a>
                </c:when>
                <c:otherwise>
                    <et:appearance appearance="${entityListItem.entityAttribute.entityType.componentVendor.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.entityAttribute.entityType.componentVendor.description}" /></et:appearance>
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
                        <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                    </c:url>
                    <a href="${componentVendorUrl}"><et:appearance appearance="${entityListItem.entityAttribute.entityType.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.entityAttribute.entityType.description}" /></et:appearance></a>
                </c:when>
                <c:otherwise>
                    <et:appearance appearance="${entityListItem.entityAttribute.entityType.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.entityAttribute.entityType.description}" /></et:appearance>
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.entityAttribute" />:
            <et:hasSecurityRole securityRoles="EntityAttribute.Review">
                <c:set var="showEntityAttributeAsLink" value="true" />
            </et:hasSecurityRole>
            <c:choose>
                <c:when test="${showEntityAttributeAsLink}">
                    <c:url var="entityAttributeUrl" value="/action/Core/EntityAttribute/Review">
                        <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                        <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                    </c:url>
                    <a href="${entityAttributeUrl}"><et:appearance appearance="${entityListItem.entityAttribute.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.entityAttribute.description}" /></et:appearance></a>
                </c:when>
                <c:otherwise>
                    <et:appearance appearance="${entityListItem.entityAttribute.entityInstance.entityAppearance.appearance}"><c:out value="${entityListItem.entityAttribute.description}" /></et:appearance>
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.entityListItemName" />: ${entityListItem.entityListItemName}<br />
            <fmt:message key="label.isDefault" />:
            <c:choose>
                <c:when test="${entityListItem.isDefault}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.sortOrder" />: ${entityListItem.sortOrder}<br />
            <fmt:message key="label.description" />: ${entityListItem.description}<br />
            <br />
            <br />
            <br />
            <c:set var="tagScopes" scope="request" value="${entityListItem.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${entityListItem.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${entityListItem.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Core/EntityListItem/Review">
                <c:param name="ComponentVendorName" value="${entityListItem.entityAttribute.entityType.componentVendor.componentVendorName}" />
                <c:param name="EntityTypeName" value="${entityListItem.entityAttribute.entityType.entityTypeName}" />
                <c:param name="EntityAttributeName" value="${entityListItem.entityAttribute.entityAttributeName}" />
                <c:param name="EntityListItemName" value="${entityListItem.entityListItemName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
