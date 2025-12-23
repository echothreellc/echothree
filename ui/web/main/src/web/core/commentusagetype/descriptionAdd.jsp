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
        <title>Comment Usage Type Descriptions</title>
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
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}">Entity Types</a> &gt;&gt;
                <c:url var="commentTypesUrl" value="/action/Core/CommentType/Main">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityTypeName}" />
                </c:url>
                <a href="${commentTypesUrl}">Comment Types</a> &gt;&gt;
                <c:url var="commentUsageTypeUrl" value="/action/Core/CommentUsageType/Main">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityTypeName}" />
                    <c:param name="CommentTypeName" value="${commentTypeName}" />
                </c:url>
                <a href="${commentUsageTypeUrl}">Comment Usage Types</a> &gt;&gt;
                <c:url var="descriptionsUrl" value="/action/Core/CommentUsageType/Description">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityTypeName}" />
                    <c:param name="CommentTypeName" value="${commentTypeName}" />
                    <c:param name="CommentUsageTypeName" value="${commentUsageTypeName}" />
                </c:url>
                <a href="${descriptionsUrl}">Descriptions</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Core/CommentUsageType/DescriptionAdd" method="POST" focus="description">
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
                            <html:hidden property="commentTypeName" />
                            <html:hidden property="commentUsageTypeName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>