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
        <title><fmt:message key="pageTitle.entityAttributeEntityTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />"><fmt:message key="navigation.componentVendors" /></a> &gt;&gt;
                <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                    <c:param name="ComponentVendorName" value="${entityAttributeEntityType.entityAttribute.entityType.componentVendor.componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}"><fmt:message key="navigation.entityTypes" /></a> &gt;&gt;
                <c:url var="entityAttributesUrl" value="/action/Core/EntityAttribute/Main">
                    <c:param name="ComponentVendorName" value="${entityAttributeEntityType.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityAttributeEntityType.entityAttribute.entityType.entityTypeName}" />
                </c:url>
                <a href="${entityAttributesUrl}"><fmt:message key="navigation.entityAttributes" /></a> &gt;&gt;
                <c:url var="entityAttributeEntityTypesUrl" value="/action/Core/EntityAttributeEntityType/Main">
                    <c:param name="ComponentVendorName" value="${entityAttributeEntityType.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityAttributeEntityType.entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityAttributeEntityType.entityAttribute.entityAttributeName}" />
                </c:url>
                <a href="${entityAttributeEntityTypesUrl}"><fmt:message key="navigation.entityAttributeEntityTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.delete" />
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.hasErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <p>You are about to delete the <c:out value="${fn:toLowerCase(partyEntityType.entityType.description)}" />:</p>
                    &nbsp;&nbsp;&nbsp;&nbsp;Component Vendor: <c:out value="${entityAttributeEntityType.entityAttribute.entityType.componentVendor.description}" /><br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Entity Type: <c:out value="${entityAttributeEntityType.entityAttribute.entityType.description}" /><br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Entity Attribute: <c:out value="${entityAttributeEntityType.entityAttribute.description}" /><br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Allowed Component Vendor: <c:out value="${entityAttributeEntityType.allowedEntityType.componentVendor.description}" /><br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Allowed Entity Type: <c:out value="${entityAttributeEntityType.allowedEntityType.description}" /><br />
                    <html:form action="/Core/EntityAttributeEntityType/Delete" method="POST">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.confirmDelete" />:</td>
                                <td>
                                    <html:checkbox property="confirmDelete" /> (*)
                                    <et:validationErrors id="errorMessage" property="ConfirmDelete">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </table>
                        <html:submit value="Delete" onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" />
                        <html:hidden property="componentVendorName" />
                        <html:hidden property="entityTypeName" />
                        <html:hidden property="entityAttributeName" />
                        <html:hidden property="allowedComponentVendorName" />
                        <html:hidden property="allowedEntityTypeName" />
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
