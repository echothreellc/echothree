<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2025 Echo Three, LLC                                              -->
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
        <title><fmt:message key="pageTitle.entityIntegerRangeDescriptions" /></title>
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
                    <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}"><fmt:message key="navigation.entityTypes" /></a> &gt;&gt;
                <c:url var="entityAttributesUrl" value="/action/Core/EntityAttribute/Main">
                    <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityIntegerRange.entityAttribute.entityType.entityTypeName}" />
                </c:url>
                <a href="${entityAttributesUrl}"><fmt:message key="navigation.entityAttributes" /></a> &gt;&gt;
                <c:url var="entityIntegerRangeUrl" value="/action/Core/EntityIntegerRange/Main">
                    <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityIntegerRange.entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityIntegerRange.entityAttribute.entityAttributeName}" />
                </c:url>
                <a href="${entityIntegerRangeUrl}"><fmt:message key="navigation.entityIntegerRanges" /></a> &gt;&gt;
                <c:url var="descriptionsUrl" value="/action/Core/EntityIntegerRange/Description">
                    <c:param name="ComponentVendorName" value="${entityIntegerRange.entityAttribute.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityIntegerRange.entityAttribute.entityType.entityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityIntegerRange.entityAttribute.entityAttributeName}" />
                    <c:param name="EntityIntegerRangeName" value="${entityIntegerRange.entityIntegerRangeName}" />
                </c:url>
                <a href="${descriptionsUrl}"><fmt:message key="navigation.entityIntegerRangeDescriptions" /></a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Core/EntityIntegerRange/DescriptionAdd" method="POST" focus="description">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.language" />:</td>
                        <td>
                            <html:select property="languageChoice">
                                <html:optionsCollection property="languageChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="LanguageIsoName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.description" />:</td>
                        <td>
                            <html:text property="description" size="60" maxlength="132" /> (*)
                            <et:validationErrors id="errorMessage" property="Description">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="componentVendorName" />
                            <html:hidden property="entityTypeName" />
                            <html:hidden property="entityAttributeName" />
                            <html:hidden property="entityIntegerRangeName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>